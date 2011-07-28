package net.violet.db.cache;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.violet.common.StringShop;
import net.violet.db.DBConstantes;
import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.DBCollection;
import net.violet.db.records.Record;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLSpecification;
import net.violet.db.records.Record.State;
import net.violet.probes.ProbesHandler;

import org.apache.log4j.Logger;

/**
 * Classe pour le cache des objets. Ce cache contient une référence vers tous
 * les objets dans la heap actuelle du process java. Lorsque ces objets sont
 * modifiés ou supprimés, les autres membres du cache distribué sont prévenus.
 */
public final class Cache implements CacheNodeListener {

	private static final Logger LOGGER = Logger.getLogger(Cache.class);

	/**
	 * Single instance.
	 */
	private static Cache gSingleInstance;

	/**
	 * Paire clé sql/valeur pour faire référence aux row SQL.
	 */
	public static final class CacheRecordRef<T extends Record> implements Serializable {

		private static final long serialVersionUID = 1L;

		/**
		 * Référence sur la classe.
		 */
		private String mClassCanonicalName;

		/**
		 * Références sur les clés.
		 */
		private SQLKey mKey;

		private Object[] mKeyVals;

		/**
		 * Valeur de hachage.
		 */
		private int mHashCode;

		/**
		 * Constructeur à partir d'une classe et de la valeur de hachage.
		 */
		CacheRecordRef(Class<T> inClass, SQLKey inKey, Object[] inKeyVals) {
			this.mClassCanonicalName = inClass.getSimpleName();
			this.mKey = inKey;
			this.mKeyVals = CacheRecordRef.normalizeValues(inKeyVals);
			this.mHashCode = calcHashCode();
		}

		private static Object[] normalizeValues(Object[] inKeyVals) {
			final int length = inKeyVals.length;
			final Object[] normalizedValues = new Object[length];
			for (int i = 0; i < length; i++) {
				if (inKeyVals[i] instanceof Integer) {
					normalizedValues[i] = new Long(((Integer) inKeyVals[i]).intValue());
				} else {
					normalizedValues[i] = inKeyVals[i];
				}
			}
			return normalizedValues;
		}

		private int calcHashCode() {
			return this.mClassCanonicalName.hashCode() * 17 + this.mKey.hashCode() + Arrays.hashCode(this.mKeyVals);
		}

		@Override
		public int hashCode() {
			return this.mHashCode;
		}

		@Override
		public boolean equals(Object inObject) {
			return (this == inObject) || ((inObject instanceof CacheRecordRef) && ((CacheRecordRef) inObject).mKey.equals(this.mKey) && Arrays.equals(((CacheRecordRef) inObject).mKeyVals, this.mKeyVals) && ((CacheRecordRef) inObject).mClassCanonicalName.equals(this.mClassCanonicalName));
		}

		/**
		 * Représentation sous la forme d'une chaîne.
		 */
		@Override
		public String toString() {
			return "CacheRecordRef<" + this.mClassCanonicalName + ":" + Arrays.asList(this.mKey.getKeys()) + ":" + Arrays.asList(this.mKeyVals) + ">";
		}

		/**
		 * Ecriture de l'objet.
		 *
		 * @param inOutputStream flux pour l'écriture.
		 * @throws IOException
		 */
		private void writeObject(ObjectOutputStream inOutputStream) throws IOException {
			inOutputStream.writeUTF(this.mClassCanonicalName);
			inOutputStream.writeObject(this.mKey);
			inOutputStream.writeObject(this.mKeyVals);
		}

		/**
		 * Ecriture de l'objet.
		 *
		 * @param inInputStream flux pour la lecture.
		 * @throws IOException
		 * @throws ClassNotFoundException
		 */
		private void readObject(ObjectInputStream inInputStream) throws IOException, ClassNotFoundException {
			this.mClassCanonicalName = inInputStream.readUTF();
			this.mKey = (SQLKey) inInputStream.readObject();
			this.mKeyVals = (Object[]) inInputStream.readObject();
			this.mHashCode = calcHashCode();
		}
	}

	/**
	 * Paire clé sql/valeur pour faire référence aux row SQL.
	 */
	public static final class CacheCollectionRef implements Serializable {

		private static final long serialVersionUID = 1L;

		/**
		 * Références sur les clés.
		 */
		private SQLKey mKey;

		private Object[] mKeyVals;

		/**
		 * Valeur de hachage.
		 */
		private int mHashCode;

		/**
		 * Nom de la table d'association.
		 */
		private String mAssociationTableName;

		/**
		 * Nom de la colonne avec l'ID des parents dans la table d'association.
		 */
		private String mParentIdColName;

		/**
		 * Nom de la colonne avec l'ID des enfants dans la table d'association.
		 */
		private String mChildIdColName;

		/**
		 * Constructeur à partir d'une classe et de la valeur de hachage.
		 */
		CacheCollectionRef(SQLKey inParentRecordKey, Object[] inParentRecordKeyVals, String inAssociationTableName, String inParentIdColName, String inChildIdColName) {
			// Stocker toutes ces valeurs dans l'objet.
			this.mKey = inParentRecordKey;
			this.mKeyVals = inParentRecordKeyVals;
			this.mAssociationTableName = inAssociationTableName;
			this.mParentIdColName = inParentIdColName;
			this.mChildIdColName = inChildIdColName;
			this.mHashCode = calcHashCode();
		}

		private int calcHashCode() {
			return this.mChildIdColName.hashCode() * 17 + this.mAssociationTableName.hashCode() * 17 * 17 + this.mParentIdColName.hashCode() * 7 + this.mChildIdColName.hashCode() * 7 * 7 + this.mKey.hashCode() + Arrays.hashCode(this.mKeyVals);
		}

		@Override
		public int hashCode() {
			return this.mHashCode;
		}

		@Override
		public boolean equals(Object inObject) {
			return (this == inObject) || ((inObject instanceof CacheCollectionRef) && ((CacheCollectionRef) inObject).mKey.equals(this.mKey) && Arrays.equals(((CacheCollectionRef) inObject).mKeyVals, this.mKeyVals) && ((CacheCollectionRef) inObject).mAssociationTableName.equals(this.mAssociationTableName) && ((CacheCollectionRef) inObject).mParentIdColName.equals(this.mParentIdColName) && ((CacheCollectionRef) inObject).mChildIdColName.equals(this.mChildIdColName));
		}

		/**
		 * Représentation sous la forme d'une chaîne.
		 */
		@Override
		public String toString() {
			return "CacheCollectionRef<" + getClass().getSimpleName() + " " + this.mAssociationTableName + ":" + this.mParentIdColName + ":" + this.mChildIdColName + ":" + this.mKey + ":" + Arrays.asList(this.mKeyVals) + ">";
		}

		/**
		 * Ecriture de l'objet.
		 *
		 * @param inOutputStream flux pour l'écriture.
		 * @throws IOException
		 */
		private void writeObject(ObjectOutputStream inOutputStream) throws IOException {
			inOutputStream.writeObject(this.mKey);
			inOutputStream.writeObject(this.mKeyVals);
			inOutputStream.writeObject(this.mAssociationTableName);
			inOutputStream.writeObject(this.mParentIdColName);
			inOutputStream.writeObject(this.mChildIdColName);
		}

		/**
		 * Ecriture de l'objet.
		 *
		 * @param inInputStream flux pour la lecture.
		 * @throws IOException
		 * @throws ClassNotFoundException
		 */
		private void readObject(ObjectInputStream inInputStream) throws IOException, ClassNotFoundException {
			this.mKey = (SQLKey) inInputStream.readObject();
			this.mKeyVals = (Object[]) inInputStream.readObject();
			this.mAssociationTableName = (String) inInputStream.readObject();
			this.mParentIdColName = (String) inInputStream.readObject();
			this.mChildIdColName = (String) inInputStream.readObject();
			this.mHashCode = calcHashCode();
		}
	}

	/**
	 * Notification.
	 */
	private static final class CacheRecordMessage implements CacheMessage {

		private static final long serialVersionUID = 1L;

		static final byte MODIFY = 0;
		static final byte DELETE = 1;
		static final byte INVALID_KEY = 3;

		private final byte mMessageCode;
		private final CacheRecordRef mObjectRef;

		/**
		 * Constructeur à partir d'un code et d'une référence.
		 */
		public CacheRecordMessage(byte inMessageCode, CacheRecordRef inObjectRef) {
			this.mMessageCode = inMessageCode;
			this.mObjectRef = inObjectRef;
		}

		/**
		 * Accesseur sur le code.
		 */
		byte getMessageCode() {
			return this.mMessageCode;
		}

		/**
		 * Accesseur sur la référence.
		 *
		 * @return la référence sur l'objet.
		 */
		CacheRecordRef getObjectRef() {
			return this.mObjectRef;
		}
	}

	/**
	 * Cache sur une collection de messages
	 */
	private static final class CacheCollectionMessage implements CacheMessage {

		private static final long serialVersionUID = 1L;

		static final byte MODIFY = CacheRecordMessage.MODIFY;

		private final byte mMessageCode;
		private final CacheCollectionRef mCollectionRefs;

		/**
		 * Constructeur à partir d'un code et d'une collection de références.
		 */
		public CacheCollectionMessage(byte inMessageCode, CacheCollectionRef inCollectionRefs) {
			this.mMessageCode = inMessageCode;
			this.mCollectionRefs = inCollectionRefs;
		}

		/**
		 * Accesseur sur le code.
		 */
		byte getMessageCode() {
			return this.mMessageCode;
		}

		/**
		 * Accesseur sur la collection de références.
		 *
		 * @return une collection de références sur l'objet.
		 */
		CacheCollectionRef getCollectionRef() {
			return this.mCollectionRefs;
		}
	}

	private static Timer PROBES_TIMER = new Timer("PROBES TIMER", true);

	/**
	 * Hook pour fermer le CacheNode.
	 */
	private final Thread mShutdownHook = new Thread() {

		@Override
		public void run() {
			shutdownFromHook();
			PROBES_TIMER.cancel();
		}
	};

	private final CacheNode mCacheNode;

	/**
	 * Cache local des rows (référence sur tous les rows dans la heap).
	 */
	private final CacheMap<CacheRecordRef, AbstractSQLRecord> mObjectCacheMap;

	/**
	 * Cache local des associations (référence sur toutes les associations dans
	 * la heap).
	 */
	private final CacheMap<CacheCollectionRef, DBCollection> mCollectionCacheMap;

	/**
	 * Statistiques sur le cache: misses.
	 */
	private long mCacheMissCount;

	/**
	 * Statistiques sur le cache: hits.
	 */
	private long mCacheHitCount;

	/**
	 * Date de réception du dernier message pulse.
	 */
	private long mLastPulseMessageDate;

	/**
	 * Temps de réception du dernier message pulse
	 */
	private long mLastPulseMessageDelay;

	/**
	 * Mutex pour le message pulse.
	 */
	private final Object mPulseMessageMutex = new Object();

	/**
	 * Constructeur par défaut.
	 */
	private Cache() {
		this.mObjectCacheMap = new CacheMap<CacheRecordRef, AbstractSQLRecord>(DBConstantes.MAX_CACHE_ABSTRACT_RECORD_ENTRIES);
		this.mCollectionCacheMap = new CacheMap<CacheCollectionRef, DBCollection>(DBConstantes.MAX_CACHE_DB_COLLECTION_ENTRIES);

		if (System.getProperty("silence") == null) {
			this.mCacheNode = new SpreadCacheNode();
			this.mCacheNode.setListener(this);
		} else {
			this.mCacheNode = new CacheNode() {

				private final String[] members = {};

				public void setListener(CacheNodeListener inListener) {

				}

				public String[] getMembers() {
					return members;
				}

				public void close() {

				}

				public void broadcastMessage(CacheMessage inMessage) {

				}
			};
		}
		this.mLastPulseMessageDate = System.currentTimeMillis();
		Runtime.getRuntime().addShutdownHook(this.mShutdownHook);
		final TimerTask theTask = new TimerTask() {

			@Override
			public void run() {
				ProbesHandler.CACHE_MAP.updateCacheMapsStats(getObjectCacheMapSizes(), getPulseMessageStats());
			}
		};

		PROBES_TIMER.schedule(theTask, 300000L, 300000L);
	}

	/**
	 * Returns the size of the CacheMap 's Map and List (in that order)
	 *
	 * @return
	 */
	public static Integer[] getObjectCacheMapSizes() {
		final Cache theInstance = Cache.getSingleInstance();
		synchronized (theInstance.mObjectCacheMap) {
			return new Integer[] { theInstance.mObjectCacheMap.getMapSize(), theInstance.mObjectCacheMap.getListSize() };
		}
	}

	/**
	 * Accesseur sur les données sur le dernier message pulse (date d'arrivée,
	 * délai).
	 *
	 * @return
	 */
	public static Long[] getPulseMessageStats() {
		final Cache theInstance = Cache.getSingleInstance();
		synchronized (theInstance.mPulseMessageMutex) {
			return new Long[] { theInstance.mLastPulseMessageDate, theInstance.mLastPulseMessageDelay };
		}
	}

	public static void sendPulseMessage() {
		final PulseMessage theMessage = PulseMessage.getDefault();
		Cache.broadcastMessage(theMessage);
	}

	public static <RIntf extends Record<RIntf>, T extends AbstractSQLRecord<RIntf, T>> T get(SQLSpecification<T> inSpec, ResultSet inResultSet) {
		try {
			final SQLKey thePrimaryKey = inSpec.getPrimaryKey();
			String[] keyFieldNames = thePrimaryKey.getKeys();
			final List<Object> keyValues = new ArrayList<Object>(keyFieldNames.length);

			for (final String keyFieldName : keyFieldNames) {
				keyValues.add(inResultSet.getObject(inSpec.getTablePrefix() + keyFieldName));
			}

			final T record = Cache.get(inSpec.getRecordClass(), thePrimaryKey, keyValues.toArray());
			if (record != null) {
				return record;
			}
		} catch (final SQLException e) {
			Cache.LOGGER.fatal(e, e);
		}
		return null;
	}

	/**
	 * Accesseur sur un objet en cache.
	 *
	 * @param <T> classe de l'objet en cache.
	 * @param inClass classe de l'objet en cache.
	 * @param inId Id de l'objet en cache.
	 * @return l'objet en cache ou <code>null</code> s'il n'est pas en cache.
	 */
	public static <T extends AbstractSQLRecord<?, T>> T get(Class<T> inClass, SQLKey inKey, Object[] inId) {
		final Cache theInstance = Cache.getSingleInstance();
		return theInstance.doGet(inClass, inKey, inId);
	}

	private <T extends AbstractSQLRecord<?, T>> T doGet(Class<T> inClass, SQLKey inKey, Object[] inKeyValues) {

		final CacheRecordRef<T> theRef = new CacheRecordRef<T>(inClass, inKey, inKeyValues);

		T theResult;
		synchronized (this.mObjectCacheMap) {
			theResult = (T) this.mObjectCacheMap.get(theRef);

			if ((theResult != null) && !theResult.isValid()) {
				if (LOGGER.isDebugEnabled()) {
					Cache.LOGGER.debug("CACHE FOUND INVALID " + theRef);
				}
				// Nettoyage de la map.
				this.mObjectCacheMap.remove(theRef);
				theResult = null;
			}
		}

		if (theResult == null) {
			if (LOGGER.isDebugEnabled()) {
				Cache.LOGGER.debug("CACHE MISSED " + theRef);
			}
			this.mCacheMissCount++;

		} else {
			if (LOGGER.isDebugEnabled()) {
				Cache.LOGGER.debug("CACHE HIT " + theRef);
			}
			this.mCacheHitCount++;
		}

		return theResult;
	}

	/**
	 * Accesseur sur une collection en cache.
	 *
	 * @param <T> classe de la collection en cache.
	 * @param inId Id de l'objet en cache.
	 * @return l'objet en cache ou <code>null</code> s'il n'est pas en cache.
	 */
	public static DBCollection get(SQLKey inKey, Object[] inId, String inAssociationTableName, String inParentIdColName, String inChildIdColName) {
		final Cache theInstance = Cache.getSingleInstance();
		return theInstance.doGet(inKey, inId, inAssociationTableName, inParentIdColName, inChildIdColName);
	}

	private DBCollection doGet(SQLKey inKey, Object[] inId, String inAssociationTableName, String inParentIdColName, String inChildIdColName) {
		final CacheCollectionRef theCollectionRef = new CacheCollectionRef(inKey, inId, inAssociationTableName, inParentIdColName, inChildIdColName);
		if (LOGGER.isDebugEnabled()) {
			Cache.LOGGER.debug("CACHE COLLECTION GET " + theCollectionRef);
		}
		DBCollection theResult;
		synchronized (this.mCollectionCacheMap) {
			theResult = this.mCollectionCacheMap.get(theCollectionRef);

			if (theResult == null) {
				// Nettoyage de la map.
				this.mCollectionCacheMap.remove(theCollectionRef);
				this.mCacheMissCount++;
			} else {
				this.mCacheHitCount++;
			}
		}

		return theResult;
	}

	/**
	 * Ajout d'un record.
	 *
	 * @param <T> classe du record à ajouter.
	 * @param inObject record à ajouter.
	 */

	public static <T extends AbstractSQLRecord<?, T>> void add(T inObject) {
		final Cache theInstance = Cache.getSingleInstance();
		if (inObject.getSpecification().isCacheable()) {
			theInstance.doAddRecord(inObject);
		}
	}

	/**
	 * Ajout d'un record.
	 *
	 * @param <T> classe du record à ajouter.
	 * @param inObject record à ajouter.
	 */
	private <T extends AbstractSQLRecord<?, T>> void doAddRecord(T inObject) {
		try {
			final Class<T> inClass = inObject.getSpecification().getRecordClass();
			final SQLKey thePKKey = inObject.getSpecification().getPrimaryKey();
			final CacheRecordRef<T> thePKRef = new CacheRecordRef<T>(inClass, thePKKey, inObject.getValuesFromSQLKey(thePKKey).toArray());
			final List<CacheRecordRef> listCacheRecordRef = new ArrayList<CacheRecordRef>();
			listCacheRecordRef.add(thePKRef);
			for (final SQLKey theSQLKey : inObject.getSpecification().getTableKeys()) {
				final List<Object> theKeyVals = new ArrayList<Object>();
				for (final String theKeyField : theSQLKey.getKeys()) {
					final Field theField = inClass.getDeclaredField(theKeyField);
					Object theFieldValue = inObject.getFieldValue(theField);
					// Cette ligne pour éviter d'enregistrer de mettre en cache
					// avec comme clé 0, cas des NabLife par scenario_id par
					// exemple
					if (((theFieldValue instanceof Long) && (((Long) theFieldValue).longValue() == 0)) || ((theFieldValue instanceof String) && !StringShop.EMPTY_STRING.equals(theFieldValue))) {
						theFieldValue = null;
					}
					if (theFieldValue != null) {
						theKeyVals.add(theFieldValue);
					}
				}
				if (!theKeyVals.isEmpty()) {
					final CacheRecordRef<T> theRef = new CacheRecordRef<T>(inClass, theSQLKey, theKeyVals.toArray());
					if (!theSQLKey.equals(thePKKey)) {
						listCacheRecordRef.add(theRef);
					}
				}
			}
			synchronized (this.mObjectCacheMap) {
				this.mObjectCacheMap.putAll(listCacheRecordRef, inObject);
			}
		} catch (final NoSuchFieldException e) {
			Cache.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			Cache.LOGGER.fatal(e, e);
		} catch (final SQLException e) {
			Cache.LOGGER.fatal(e, e);
		}
	}

	/**
	 * Ajout d'une collection.
	 *
	 * @param inObject record à ajouter.
	 */

	public static void add(DBCollection inCollection) {
		final Cache theInstance = Cache.getSingleInstance();
		theInstance.doAddCollection(inCollection);
	}

	/**
	 * Ajout d'une collection.
	 *
	 * @param inObject record à ajouter.
	 */

	private void doAddCollection(DBCollection inCollection) {
		final SQLKey theKey = inCollection.getParentRecordKey();
		final Object[] theKeyVals = inCollection.getParentRecordKeyVals();
		final String theAssociationTableName = inCollection.getAssociationTableName();
		final String theParentIdColName = inCollection.getParentIdColName();
		final String theChildIdColName = inCollection.getChildIdColName();

		final CacheCollectionRef theRef = new CacheCollectionRef(theKey, theKeyVals, theAssociationTableName, theParentIdColName, theChildIdColName);
		synchronized (this.mCollectionCacheMap) {
			this.mCollectionCacheMap.put(theRef, inCollection);
		}
		if (LOGGER.isDebugEnabled()) {
			Cache.LOGGER.debug("CACHE COLLECTION PUT NEW " + theRef);
		}
	}

	/**
	 * Publication d'une modification d'un objet.
	 *
	 * @param inObject Objet dont on publie la modification.
	 */
	public static <T extends AbstractSQLRecord> void broadcastModification(T inRecord) {
		final Cache theInstance = Cache.getSingleInstance();
		if (inRecord.haveInvalidKeys()) {
			theInstance.doBroadcast(inRecord, CacheRecordMessage.INVALID_KEY);
		} else {
			theInstance.doBroadcast(inRecord, CacheRecordMessage.MODIFY);
		}
	}

	public static <T extends AbstractSQLRecord> void broadcastDelete(T inRecord) {
		final Cache theInstance = Cache.getSingleInstance();
		theInstance.doBroadcast(inRecord, CacheRecordMessage.DELETE);
	}

	/**
	 * Publication d'une collection invalide.
	 *
	 * @param inObject Objet dont on publie la modification.
	 */
	public static void broadcastModification(SQLKey inKey, Object[] inKeysVals, String inAssociationTableName, String inParentIdColName, String inChidIdColName) {
		final Cache theInstance = Cache.getSingleInstance();
		theInstance.doBroadcast(inKey, inKeysVals, inAssociationTableName, inParentIdColName, inChidIdColName, CacheCollectionMessage.MODIFY);
	}

	/**
	 * Publication d'un message de maintenance
	 *
	 * @param inMessage le message à broadcaster
	 */
	public static void broadcastMessage(CacheMessage inMessage) {

		final Cache theInstance = Cache.getSingleInstance();
		theInstance.mCacheNode.broadcastMessage(inMessage);
	}

	/**
	 * Publication d'une modification d'un objet.
	 *
	 * @param inObject Objet dont on publie la modification.
	 */

	private <T extends AbstractSQLRecord> void doBroadcast(T inRecord, byte inMessageCode) {
		try {
			final SQLSpecification<T> theSpec = inRecord.getSpecification();
			final SQLKey theKey = theSpec.getPrimaryKey();
			final Object[] theValues = inRecord.getValuesFromSQLKey(theKey).toArray();
			final CacheRecordRef<T> theRef = new CacheRecordRef<T>((Class<T>) inRecord.getClass(), theKey, theValues);
			if (LOGGER.isDebugEnabled()) {
				Cache.LOGGER.debug("CACHE SEND (Record) code = " + inMessageCode + " ref = " + theRef);
			}
			this.mCacheNode.broadcastMessage(new CacheRecordMessage(inMessageCode, theRef));
			// Je supprime dans le cache local aussi dans le cas où les clés on
			// été modifiées
			if (inMessageCode == CacheRecordMessage.INVALID_KEY) {
				if (LOGGER.isDebugEnabled()) {
					Cache.LOGGER.debug("CACHE DEBUG Local deletion of record referencing by " + theRef + " into cache.");
				}
				remove(inRecord);
			}
		} catch (final SecurityException e) {
			Cache.LOGGER.fatal(e, e);
		} catch (final IllegalArgumentException e) {
			Cache.LOGGER.fatal(e, e);
		} catch (final SQLException e) {
			Cache.LOGGER.fatal(e, e);
		}
	}

	private <T extends AbstractSQLRecord> void remove(T inRecord) throws SQLException {
		final SQLSpecification<T> theSpec = inRecord.getSpecification();
		final SQLKey theKey = theSpec.getPrimaryKey();

		synchronized (this.mObjectCacheMap) {

			for (final SQLKey oneKey : theSpec.getTableKeys()) {
				if (!oneKey.equals(theKey)) {
					final CacheRecordRef<T> oneRef = new CacheRecordRef<T>((Class<T>) inRecord.getClass(), oneKey, inRecord.getValuesFromSQLKey(oneKey).toArray());
					this.mObjectCacheMap.remove(oneRef);

				}
			}
		}
	}

	/**
	 * Publication d'une collection invalide.
	 *
	 * @param inObject Objet dont on publie la modification.
	 */

	private void doBroadcast(SQLKey inKey, Object[] inKeysVals, String inAssociationTableName, String inParentIdColName, String inChidIdColName, byte inMessageCode) {
		try {
			final CacheCollectionRef theCollectionRef = new CacheCollectionRef(inKey, inKeysVals, inAssociationTableName, inParentIdColName, inChidIdColName);
			if (LOGGER.isDebugEnabled()) {
				Cache.LOGGER.debug("CACHE SEND (Collection) code = " + inMessageCode + " ref = " + theCollectionRef);
			}
			this.mCacheNode.broadcastMessage(new CacheCollectionMessage(inMessageCode, theCollectionRef));
		} catch (final SecurityException e) {
			Cache.LOGGER.fatal(e, e);
		} catch (final IllegalArgumentException e) {
			Cache.LOGGER.fatal(e, e);
		}
	}

	public void receiveMessage(CacheMessage inObjectMessage) {

		if (inObjectMessage instanceof CacheRecordMessage) {

			final CacheRecordMessage theMessage = (CacheRecordMessage) inObjectMessage;
			final CacheRecordRef theRef = theMessage.getObjectRef();
			if (LOGGER.isDebugEnabled()) {
				Cache.LOGGER.debug("CACHE RECEIVE (Record) code = " + theMessage.getMessageCode() + " ref = " + theRef);
			}

			final AbstractSQLRecord theRecord = this.mObjectCacheMap.get(theRef);
			if (theRecord != null) {
				this.mObjectCacheMap.remove(theRef);
				switch (theMessage.getMessageCode()) {
				case CacheRecordMessage.MODIFY:
					theRecord.setState(State.INVALID);
					break;

				case CacheRecordMessage.DELETE:
					theRecord.setState(State.DELETED);
					break;

				case CacheRecordMessage.INVALID_KEY:
					try {
						remove(theRecord);
					} catch (final SQLException e) {
						Cache.LOGGER.fatal(e, e);
					}
					break;
				}
			}

		} else if (inObjectMessage instanceof CacheCollectionMessage) {
			final CacheCollectionMessage theMessage = (CacheCollectionMessage) inObjectMessage;
			final CacheCollectionRef theCollectionRef = theMessage.getCollectionRef();
			if (LOGGER.isDebugEnabled()) {
				Cache.LOGGER.debug("CACHE RECEIVE (Collection) code = " + theMessage.getMessageCode() + " ref = " + theCollectionRef);
			}
			final DBCollection theCollection = this.mCollectionCacheMap.get(theCollectionRef);
			if (theCollection != null) {
				switch (theMessage.getMessageCode()) {
				case CacheCollectionMessage.MODIFY:
					theCollection.setModified();
					break;
				}
			}
		} else if (inObjectMessage instanceof PulseMessage) {
			final PulseMessage theMessage = (PulseMessage) inObjectMessage;
			final long now = System.currentTimeMillis();
			synchronized (this.mPulseMessageMutex) {
				this.mLastPulseMessageDate = now;
				this.mLastPulseMessageDelay = theMessage.getTime() - now;
				if (this.mLastPulseMessageDelay < 0) {
					this.mLastPulseMessageDelay = 0;
				}
			}
		}
	}

	private static Cache getSingleInstance() {
		synchronized (Cache.class) {
			if (Cache.gSingleInstance == null) {
				Cache.gSingleInstance = new Cache();
			}
		}
		return Cache.gSingleInstance;
	}

	public static void shutdownCache() {
		synchronized (Cache.class) {
			final Cache theCache = Cache.gSingleInstance;
			Cache.gSingleInstance = null;

			if (theCache != null) {
				theCache.shutdown();
			}
		}
	}

	private void shutdown() {
		shutdownFromHook();
		Runtime.getRuntime().removeShutdownHook(this.mShutdownHook);
	}

	private void shutdownFromHook() {
		this.mCacheNode.close();
	}

	public void addedMember(String inName) {
		Cache.LOGGER.info("CACHE NEW NODE : " + inName);
	}

	public void disappearedMember(String inName) {
		Cache.LOGGER.info("CACHE NODE DISAPPEARED : " + inName);
	}

	/**
	 * Vide le cache (utilisé pour les tests).
	 */
	public static void clear() {
		Cache.getSingleInstance().mCollectionCacheMap.clear();
		Cache.getSingleInstance().mObjectCacheMap.clear();
	}

	public static void clearForTest() {
		Cache.getSingleInstance().mCollectionCacheMap.clearForTests();
		Cache.getSingleInstance().mObjectCacheMap.clearForTests();
	}

}

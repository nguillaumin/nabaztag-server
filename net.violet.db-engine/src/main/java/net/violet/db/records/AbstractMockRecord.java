package net.violet.db.records;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public abstract class AbstractMockRecord<Intf extends Record<Intf>, Impl extends AbstractMockRecord<Intf, Impl>> implements Record<Intf> {

	private static final Logger LOGGER = Logger.getLogger(AbstractMockRecord.class);

	private static Map<String, Map<Long, Record>> INSTANCES_LIST = new HashMap<String, Map<Long, Record>>();
	private static Map<String, Long> MAX_IDS = new HashMap<String, Long>();

	public abstract static class MockBuilder<Intf> {

		private static String PATTERN_STARTER = "\\(";
		private static String PATTERN_ENDER = "\\)";
		private static String PATTERN_VALUE_SEPARATOR = net.violet.common.StringShop.COMMA;
		private static String PATTERN_ELEMENT = "(.*)";

		public List<Intf> generateValuesFromInitFile(int inAmountOfValues, String inPath2File) {
			final StringBuilder theBuilder = new StringBuilder();
			final List<Intf> resultList = new LinkedList<Intf>();
			try {
				if (inAmountOfValues > 0) {
					theBuilder.append(MockBuilder.PATTERN_STARTER);

					for (int i = inAmountOfValues - 1; i > 0; i--, theBuilder.append(MockBuilder.PATTERN_ELEMENT).append(MockBuilder.PATTERN_VALUE_SEPARATOR)) {
						;
					}

					theBuilder.append(MockBuilder.PATTERN_ELEMENT).append(MockBuilder.PATTERN_ENDER);

					final Pattern thePattern = Pattern.compile(theBuilder.toString());

					final BufferedReader theReader = new BufferedReader(new FileReader(inPath2File));

					String aLine = null;

					while ((aLine = theReader.readLine()) != null) {

						final Matcher theMatcher = thePattern.matcher(aLine);

						if (theMatcher.matches() && (theMatcher.groupCount() == inAmountOfValues)) {
							final String[] theParams = new String[inAmountOfValues];

							for (int i = 0, groupCounter = i + 1; i < inAmountOfValues; i++, groupCounter++) {
								theParams[i] = theMatcher.group(groupCounter).equals("NULL") ? null : theMatcher.group(groupCounter);
							}

							resultList.add(build(theParams));
						} else {
							AbstractMockRecord.LOGGER.error("aLine = " + aLine);
						}

					}

				}
			} catch (final Exception e) {
				LOGGER.fatal(e, e);
			}

			return resultList;
		}

		protected Long parseLong(String inValue) {
			try {
				return null == inValue ? 0L : Long.parseLong(inValue);
			} catch (NumberFormatException e) {
				LOGGER.fatal(e.getMessage() + " value : " + inValue, e);
			}
			return 0L;
		}

		protected abstract Intf build(String[] inParamValues);

	}

	public static <Intf extends Record<Intf>> Map<Long, Intf> getInstancesList(String inClassName) {

		if (AbstractMockRecord.INSTANCES_LIST.get(inClassName) == null) {
			synchronized (AbstractMockRecord.INSTANCES_LIST) {

				AbstractMockRecord.INSTANCES_LIST.put(inClassName, new HashMap<Long, Record>());
			}
		}

		return (Map<Long, Intf>) (Object) AbstractMockRecord.INSTANCES_LIST.get(inClassName); // Ne pas retirer le cast sur Object
	}

	public static final void clearForTest() {

		final Iterator<Map<Long, Record>> theListIterator = AbstractMockRecord.INSTANCES_LIST.values().iterator();
		Map<Long, Record> aMap;
		final List<Record> theRecords = new LinkedList<Record>();

		while (theListIterator.hasNext()) {
			aMap = theListIterator.next();

			for (final Record aRecord : aMap.values()) {
				theRecords.add(aRecord);
			}
		}

		for (final Record aRecord : theRecords) {
			aRecord.delete();
		}

		AbstractMockRecord.MAX_IDS.clear();
	}

	private final String mClassName;

	private final long mId;

	/**
	 * Etat de l'enregistrement.
	 */
	private State mState;

	/**
	 * Liste des listeners à mettre au courant de la suppression.
	 */
	private final List<RecordDeletionListener<Intf>> mRecordDeletionListener;

	/**
	 * Constructeur à partir d'un ID.
	 * 
	 * @param inId ID du record. Si c'est 0, le record récupère un ID
	 *            auto-incrémenté.
	 */
	protected AbstractMockRecord(long inId) {
		this.mRecordDeletionListener = new LinkedList<RecordDeletionListener<Intf>>();
		this.mClassName = getClass().getName();
		synchronized (AbstractMockRecord.MAX_IDS) {
			final Long thePreviousID = AbstractMockRecord.MAX_IDS.get(this.mClassName);
			final long thePreviousIDAsLong;
			if (thePreviousID == null) {
				thePreviousIDAsLong = 0;
			} else {
				thePreviousIDAsLong = thePreviousID.longValue();
			}
			if (inId != 0) {
				if (inId > thePreviousIDAsLong) {
					AbstractMockRecord.MAX_IDS.put(this.mClassName, inId);
				}
				this.mId = inId;
			} else {
				this.mId = thePreviousIDAsLong + 1;
				AbstractMockRecord.MAX_IDS.put(this.mClassName, this.mId);
			}
		}
		synchronized (AbstractMockRecord.INSTANCES_LIST) {
			Map<Long, Record> theMap = AbstractMockRecord.INSTANCES_LIST.get(this.mClassName);

			if (theMap == null) {
				theMap = new HashMap<Long, Record>();
			}

			theMap.put(this.mId, this);

			AbstractMockRecord.INSTANCES_LIST.put(this.mClassName, theMap);
		}
	}

	public final boolean delete() {
		final boolean theResult;
		synchronized (AbstractMockRecord.INSTANCES_LIST) {
			theResult = AbstractMockRecord.INSTANCES_LIST.get(this.mClassName).remove(this.getId()) != null;
		}

		// On prévient les listeners.
		for (final RecordDeletionListener<Intf> theListener : this.mRecordDeletionListener) {
			theListener.recordDeleted((Intf) this);
		}

		this.mState = State.DELETED;

		return theResult;
	}

	public final Long getId() {
		return this.mId;
	}

	@Override
	public boolean equals(Object obj) {
		return this.getClass().isInstance(obj) && (this.getClass().cast(obj).getId().longValue() == getId().longValue());
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	/**
	 * Ajoute un listener pour la suppression.
	 * 
	 * @param inListener listener à ajouter.
	 */
	public final void addDeletionListener(RecordDeletionListener<Intf> inListener) {
		this.mRecordDeletionListener.add(inListener);
	}

	/**
	 * Supprime un listener pour la suppression.
	 * 
	 * @param inListener listener à supprimer.
	 */
	public final void removeDeletionListener(RecordDeletionListener<Intf> inListener) {
		this.mRecordDeletionListener.remove(inListener);
	}

	public final boolean isDeleted() {
		return this.mState == State.DELETED;
	}

	public final boolean isInvalid() {
		return this.mState == State.INVALID;
	}

	public final void update(Map<String, Object> inUpdateMap) {
		// This space for rent.
	}

	public void updateFromDatabase() {
		// This space for rent.
	}
}

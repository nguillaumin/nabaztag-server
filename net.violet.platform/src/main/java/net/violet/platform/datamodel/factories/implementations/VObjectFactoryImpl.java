package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.violet.db.records.Record.RecordWalker;
import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.AnnuImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Hardware;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.ObjectPreferences;
import net.violet.platform.datamodel.ObjectPreferencesImpl;
import net.violet.platform.datamodel.ObjectProfile;
import net.violet.platform.datamodel.ObjectProfileImpl;
import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.UserPwdImpl;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.VObjectImpl;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.ObjectSleep.SleepAction;
import net.violet.platform.datamodel.factories.VObjectFactory;
import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.util.CCalendar;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.util.StringUtils;

public class VObjectFactoryImpl extends RecordFactoryImpl<VObject, VObjectImpl> implements VObjectFactory {

	private static final Logger LOGGER = Logger.getLogger(VObjectFactoryImpl.class);

	public VObjectFactoryImpl() {
		super(VObjectImpl.SPECIFICATION);
	}

	public VObject findByName(String inName) {
		return findByKey(2, inName);
	}

	public List<VObject> findByOwner(User owner) {
		// ordre obligatoire afin d 'etre sur que le premier de la liste est bien un objet Nabaztag.
		return findAll("object_owner = ?", Arrays.asList(new Object[] { owner.getId() }), " object_id ");
	}

	/**
	 * Accesseur à partir d'un numéro de série.
	 * 
	 * @param inName nom de l'objet.
	 * @return l'enregistrement (ou <code>null</code>).
	 */
	public VObject findBySerial(String inSerial) {
		return findByKey(1, inSerial);
	}

	/**
	 * Accesseur à partir du JID.
	 * 
	 * @param inJID jid ou <code>null</code>
	 * @return l'enregistrement (ou <code>null</code>).
	 */
	public VObject findByJID(String inJID) {
		VObject theResult = null;
		if (inJID != null) {
			final String theMac = StringUtils.parseName(inJID);
			theResult = findBySerial(theMac);
		}
		return theResult;
	}

	/**
	 * Accesseur à partir d'un proprietaire et du type d'objet.
	 * 
	 * @param owner user proprietaire de l'objet.
	 * @param hardware type de l'objet (3=>V1/4=>v2/5=>Ztamps)
	 * @return l'enregistrement (ou <code>null</code>).
	 */
	public VObject findByOwnerAndHardware(User owner, HARDWARE inHardware) {
		final List<Object> theValues = Arrays.asList(new Object[] { owner.getId(), inHardware.getId() });
		return find("object_owner = ? AND object_hardware = ?", theValues);
	}

	/**
	 * Retourne un objet recupéré aléatoirement de la table
	 * 
	 * @return
	 */
	public VObject findRandomObject() {
		// select object_id from object , user, annu where LIMIT 1
		final String theCondition = "object_owner > 0 and object_creation > 0 and ( object_mode = ? or object_mode = ? ) and object_owner = annu_user and annu_confirm = 1 ORDER BY RAND()";
		final List<Object> theValues = Arrays.asList(new Object[] { VObject.MODE_PING, VObject.MODE_XMPP });
		final String[] theTables = new String[] { AnnuImpl.SPECIFICATION.getTableName() };
		return find(theTables, theCondition, theValues);
	}

	public List<VObject> findLastCreatedByHardwares(int count, Set<HARDWARE> hardwares) {

		if ((hardwares != null) && !hardwares.isEmpty()) {
			final String orderNLimit = " object_creation desc limit 0," + count;
			final StringBuilder condition = new StringBuilder(" object_hardware = ? ");
			final List<Object> values = new LinkedList<Object>();
			final Iterator<Hardware.HARDWARE> theIterator = hardwares.iterator();

			values.add(theIterator.next().getId());

			while (theIterator.hasNext()) {
				condition.append(" or object_hardware = ? ");
				values.add(theIterator.next().getId());
			}

			return findAll(condition.toString(), values, orderNLimit);
		}

		return Collections.emptyList();
	}

	public List<VObject> searchObjects(String inName, Set<HARDWARE> inHardwares, String inCity, String inCountry, int skip, int count) {

		final StringBuilder theCondition = new StringBuilder(" object.object_id = object_preferences.object_id AND object_preferences.visible = ? AND object.object_id = object_profile.object_id ");
		final List<String> joinTables = new LinkedList<String>();
		final List<Object> theValues = new LinkedList<Object>();
		joinTables.add("object_preferences");
		joinTables.add("object_profile");
		theValues.add(true);

		if (inName != null) {
			theCondition.append(" and object_label like ? ");
			theValues.add(inName + "%");
		}

		if ((inHardwares != null) && !inHardwares.isEmpty()) {
			final Iterator<Hardware.HARDWARE> theIterator = inHardwares.iterator();
			if (theIterator.hasNext()) {
				theCondition.append(" and object_hardware in (" + theIterator.next().getId());

				while (theIterator.hasNext()) {
					theCondition.append(" , " + theIterator.next().getId());
				}

				theCondition.append(" ) ");
			}
		}

		if ((inCity != null) || (inCountry != null)) {
			joinTables.add("annu");
			theCondition.append(" and object.object_owner = annu.annu_user ");
			if (inCity != null) {
				theCondition.append(" AND annu.annu_city = ? ");
				theValues.add(inCity);
			}
			if (inCountry != null) {
				theCondition.append(" AND annu_country = ? ");
				theValues.add(inCountry);
			}
		}

		return findAll(joinTables.toArray(new String[joinTables.size()]), theCondition.toString(), theValues, "object_label", skip, count);

	}

	public ObjectProfile createObjectProfile(VObject inObject) {
		try {
			return new ObjectProfileImpl(inObject, null, net.violet.common.StringShop.EMPTY_STRING);
		} catch (final SQLException e) {
			VObjectFactoryImpl.LOGGER.fatal(e, e);
		}
		return null;
	}

	public ObjectPreferences createObjectPreferences(VObject inObject, Lang inLang) {
		try {
			return new ObjectPreferencesImpl(inObject, true, false, inLang);
		} catch (final SQLException e) {
			VObjectFactoryImpl.LOGGER.fatal(e, e);
		}
		return null;
	}

	/**
	 * 
	 * @param inHardware
	 * @param inSerial
	 * @param inName
	 * @param inLabel
	 * @param inUser
	 * @param inLocation
	 * @param inMode
	 * @param inState
	 * @param inPicture : may be null 
	 * @return
	 */
	public VObject createObject(HARDWARE inHardware, String inSerial, String inName, String inLabel, User inUser, String inLocation, int inMode, int inState, Files inPicture) {
		try {
			final Lang theUserLang = inUser.getAnnu().getLangPreferences();
			final VObject theObject = new VObjectImpl(inSerial, inName, inUser, inHardware, (System.currentTimeMillis()) / 1000, inUser.getTimezone(), ObjectLangData.getDefaultObjectLanguage(theUserLang.getIsoCode()).getReference(), inLocation, inMode, inState, inPicture);
			theObject.getProfile().setLabel(inLabel);

			// TODO to be removed as soon as my.nabaztag.com is shutdown and dies in hell !!
			if ((inHardware == HARDWARE.V1) || (inHardware == HARDWARE.V2)) {
				new UserPwdImpl(inUser.getId(), inName, inUser.getPassword());
			}
			//

			return theObject;

		} catch (final SQLException e) {
			VObjectFactoryImpl.LOGGER.fatal(e, e);
		}
		return null;
	}

	public ObjectPreferences createObjectPreferences(VObject inObject, Lang inLang, boolean inVisibilty) {
		try {
			return new ObjectPreferencesImpl(inObject, inVisibilty, false, inLang);
		} catch (final SQLException e) {
			VObjectFactoryImpl.LOGGER.fatal(e, e);
			return null;
		}
	}

	public VObject findByNameAndMD5Password(String inName, String inPassword) {
		final String[] joinTables = { "user_pwd" };
		final String condition = " object_login = pseudo AND user_id = object_owner AND pwd = ? AND pseudo = ? ";
		final List<Object> values = Arrays.asList(new Object[] { inPassword, inName });
		return find(joinTables, condition, values);
	}

	/**
	 * Itérateur (statique) sur des objets déjà dans la base, à partir d'une
	 * condition. Elle permet de récupèrer le status des lapins en semaine ou
	 * week end selon sa timezone
	 * 
	 * @param inWalker : itérateur
	 * @param inJavaIdTimezone : timezone
	 * @param postStateActif : état recherché => <true> qui passe en actif ;
	 *            <false> qui passe en veille
	 * @return le nombre d'événements traités.
	 */

	public int walkObjectsStatus(RecordWalker<VObject> inWalker, Timezone inTimezone, boolean postStateActif) {

		final CCalendar theCurrentCalendarByTimezone = new CCalendar(false, inTimezone.getJavaTimeZone());

		final int hour = theCurrentCalendarByTimezone.getHour();
		final int minute = theCurrentCalendarByTimezone.getMinute();
		final Time now = CCalendar.getSQLTime(hour, minute);
		final long dayOfWeek = theCurrentCalendarByTimezone.get(Calendar.DAY_OF_WEEK);

		final List<Object> theValues = Arrays.asList(new Object[] { VObject.MODE_XMPP, VObject.MODE_PING, dayOfWeek, now, now });
		final String[] inJoinTables = new String[] { "object_sleep" };
		final StringBuilder condition = new StringBuilder(" object.time_zone IN " + TimezoneFactoryImpl.getTimeZonesCondition(inTimezone) + " AND object_id = sleep_object and object_mode IN(?, ?) ");
		condition.append(" and week_day = ? and ? >= time_from and ? < time_to ");

		if (postStateActif) {
			// objets qui étaient en veille ou en actif forcé
			condition.append(" and (object_state = " + VObject.STATUS_FORCE_ACTIF + " or object_state = " + VObject.STATUS_VEILLE + " or object_state = " + VObject.STATUS_WILLBE_ACTIF + " or object_state = " + VObject.STATUS_WILLBE_VEILLE + " or object_state = " + VObject.STATUS_WILLBE_FORCE_ACTIF + " or object_state = " + VObject.STATUS_WILLBE_FORCE_VEILLE + ")");
			condition.append(" and time_action = '" + SleepAction.WAKE + "' ");
		} else {
			// objets qui sont actifs ou en veille forcé
			condition.append(" and (object_state = " + VObject.STATUS_FORCE_VEILLE + " or object_state = " + VObject.STATUS_ACTIF + " or object_state = " + VObject.STATUS_WILLBE_ACTIF + " or object_state = " + VObject.STATUS_WILLBE_VEILLE + " or object_state = " + VObject.STATUS_WILLBE_FORCE_ACTIF + " or object_state = " + VObject.STATUS_WILLBE_FORCE_VEILLE + ")");
			condition.append(" and time_action = '" + SleepAction.SLEEP + "' ");
		}

		return walk(condition.toString(), theValues, inJoinTables, null, 0, null, null, inWalker);
	}
}

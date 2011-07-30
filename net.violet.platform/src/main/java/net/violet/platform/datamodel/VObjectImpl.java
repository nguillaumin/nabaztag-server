package net.violet.platform.datamodel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.DecoratedAssociation;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.db.records.associations.SingleAssociationNull;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.message.Message;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.MessageServices;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.SleepTime;
import net.violet.platform.xmpp.JabberMessageFactory;

import org.apache.log4j.Logger;

public final class VObjectImpl extends ObjectRecord<VObject, VObjectImpl> implements VObject {

	private static final Logger LOGGER = Logger.getLogger(VObjectImpl.class);

	public static final SQLObjectSpecification<VObjectImpl> SPECIFICATION = new SQLObjectSpecification<VObjectImpl>("object", VObjectImpl.class, new SQLKey[] { new SQLKey("object_id"), new SQLKey("object_serial"), new SQLKey("object_login") });

	private static final int TIME_TO_UPDATE_LAST_ACTIVITY = 3600 * 12;
	private static final String[] NEW_COLUMNS = new String[] { "object_serial", "object_login", "object_owner", "object_creation", "object_hardware", "object_lastping", "time_zone", "object_loc", "object_mode", "object_state", };

	protected long object_id;
	protected String object_serial;
	protected String object_login;
	protected Long object_owner;
	protected Long object_creation;
	protected Long object_hardware;
	protected Long object_lastping;
	protected Integer object_test;
	protected Integer object_left;
	protected Integer object_right;
	protected int object_n1;
	protected int object_n2;
	protected int object_nbmsg;
	protected int object_mode;
	protected int object_delay;
	protected String object_loc;
	protected int object_state;
	protected int object_bc_version;
	protected long time_zone;
	

	private final SingleAssociationNotNull<VObject, Timezone, TimezoneImpl> timeZone;

	private final SingleAssociationNotNull<VObject, User, UserImpl> mOwner;

	private final SingleAssociationNotNull<VObject, Hardware, HardwareImpl> mHardware;

	private Map<Nabcast, Subscriber> uSubscribedNabcast;

	// private SrvDialogData unitedOrWaiting;

	private final List<AppletSettings> appletServices = new ArrayList<AppletSettings>();

	private final SingleAssociationNull<VObject, ObjectProfile, ObjectProfileImpl> mProfile;

	private final SingleAssociationNull<VObject, ObjectPreferences, ObjectPreferencesImpl> mPreferences;

	//private Map<VAction, List<ObjectHasReadContent>> objectHasReadContent;

	/**
	 * Pour reconnaitre les ztamps doublonné
	 */
	private Boolean isDuplicated;

	@Override
	public SQLObjectSpecification<VObjectImpl> getSpecification() {
		return VObjectImpl.SPECIFICATION;
	}

	public VObjectImpl(String object_serial, String object_login, User owner, HARDWARE inObjectHardware, long object_lastping, Timezone inTimezone, Lang inLang, String object_loc, int object_mode, int object_state, Files inPicture) throws SQLException {
		this.object_serial = object_serial;
		this.object_login = object_login;
		this.object_owner = owner.getId();
		this.object_creation = Long.valueOf(CCalendar.getCurrentTimeInSecond());
		this.object_hardware = inObjectHardware.getId();
		this.object_lastping = object_lastping;
		this.object_test = 0;
		this.time_zone = inTimezone.getId();
		this.object_loc = object_loc;
		this.object_mode = object_mode;
		this.object_state = object_state;
		this.timeZone = new SingleAssociationNotNull<VObject, Timezone, TimezoneImpl>(this, "time_zone", TimezoneImpl.SPECIFICATION);
		this.mOwner = new SingleAssociationNotNull<VObject, User, UserImpl>(this, "object_owner", UserImpl.SPECIFICATION);
		this.mHardware = new SingleAssociationNotNull<VObject, Hardware, HardwareImpl>(this, "object_hardware", HardwareImpl.SPECIFICATION);

		// Enregistrement
		init(VObjectImpl.NEW_COLUMNS);
		final Files theCurrentPicture = (inPicture != null ? inPicture : inObjectHardware.getPictureFile());

		new ObjectProfileImpl(this, theCurrentPicture, net.violet.common.StringShop.EMPTY_STRING);
		new ObjectPreferencesImpl(this, true, false, inLang);

		this.mProfile = new SingleAssociationNull<VObject, ObjectProfile, ObjectProfileImpl>(this, "object_id", ObjectProfileImpl.SPECIFICATION, ObjectProfileImpl.SPECIFICATION.getPrimaryKey());
		this.mPreferences = new SingleAssociationNull<VObject, ObjectPreferences, ObjectPreferencesImpl>(this, "object_id", ObjectPreferencesImpl.SPECIFICATION, ObjectPreferencesImpl.SPECIFICATION.getPrimaryKey());
	}

	protected VObjectImpl(long id) throws SQLException {
		init(id);
		this.timeZone = new SingleAssociationNotNull<VObject, Timezone, TimezoneImpl>(this, "time_zone", TimezoneImpl.SPECIFICATION);
		this.mOwner = new SingleAssociationNotNull<VObject, User, UserImpl>(this, "object_owner", UserImpl.SPECIFICATION);
		this.mHardware = new SingleAssociationNotNull<VObject, Hardware, HardwareImpl>(this, "object_hardware", HardwareImpl.SPECIFICATION);

		this.mProfile = new SingleAssociationNull<VObject, ObjectProfile, ObjectProfileImpl>(this, "object_id", ObjectProfileImpl.SPECIFICATION, ObjectProfileImpl.SPECIFICATION.getPrimaryKey());
		this.mPreferences = new SingleAssociationNull<VObject, ObjectPreferences, ObjectPreferencesImpl>(this, "object_id", ObjectPreferencesImpl.SPECIFICATION, ObjectPreferencesImpl.SPECIFICATION.getPrimaryKey());
	}

	protected VObjectImpl() {
		this.timeZone = new SingleAssociationNotNull<VObject, Timezone, TimezoneImpl>(this, "time_zone", TimezoneImpl.SPECIFICATION);
		this.mOwner = new SingleAssociationNotNull<VObject, User, UserImpl>(this, "object_owner", UserImpl.SPECIFICATION);
		this.mHardware = new SingleAssociationNotNull<VObject, Hardware, HardwareImpl>(this, "object_hardware", HardwareImpl.SPECIFICATION);

		this.mProfile = new SingleAssociationNull<VObject, ObjectProfile, ObjectProfileImpl>(this, "object_id", ObjectProfileImpl.SPECIFICATION, ObjectProfileImpl.SPECIFICATION.getPrimaryKey());
		this.mPreferences = new SingleAssociationNull<VObject, ObjectPreferences, ObjectPreferencesImpl>(this, "object_id", ObjectPreferencesImpl.SPECIFICATION, ObjectPreferencesImpl.SPECIFICATION.getPrimaryKey());
	}

	public boolean isConnected() {
		final int theMode = getObject_mode();
		return (theMode != VObject.MODE_PING_INACTIVE) && (theMode != VObject.MODE_XMPP_INACTIVE);
	}

	@Override
	public Long getId() {
		return this.object_id;
	}

	/**
	 * Regex pour vérifier si il n'y a pas de mauvais caractère dans le jid
	 */
	private static final Pattern JID_REGEX = Pattern.compile("[<>'\"]");

	/**
	 * Vérifie s'il n'y a pas de mauvais caratère dans le jid
	 * 
	 * @param inValue (dans notre cas la serial)
	 * @return true si ok
	 */
	public static boolean checkJid(String inValue) {
		return (inValue != null) && !VObjectImpl.JID_REGEX.matcher(inValue).find();
	}

	public String getXmppAddress() {
		// Le domaine dépend du hardware.
		final String theDomain = Hardware.HARDWARE.MIRROR.is(this) ? Constantes.XMPP_MIRROR_DOMAIN : Constantes.XMPP_NABAZTAG_DOMAIN;
		String inSerial = getObject_serial().toLowerCase();
		if (!VObjectImpl.checkJid(inSerial)) {
			inSerial = "invalid";
			VObjectImpl.LOGGER.fatal("Invalid Serial in Object:" + inSerial);
		}
		String theUser;
		if (HARDWARE.MIRROR.is(this)) {
			theUser = VObjectImpl.makeMirrorXmppUser(inSerial);
		} else {
			theUser = inSerial;
		}
		return theUser + "@" + theDomain;
	}

//	public Map<VAction, List<ObjectHasReadContent>> getContents() {
//		if (this.objectHasReadContent == null) {
//			try {
//				this.objectHasReadContent = MultipleDecoratedAssociation.createMultipleDecoratedAssociation(this, VActionImpl.SPECIFICATION, StringShop.ACTION_ID, ObjectHasReadContentImpl.SPECIFICATION, "object_id");
//			} catch (final SQLException e) {
//				VObjectImpl.LOGGER.fatal(e, e);
//			}
//		}
//		return this.objectHasReadContent;
//	}

	public static String makeMirrorXmppUser(String inSerial) {
		// encode the mac address as described in the VioletOS Specification
		// 11.1.2.
		// TODO this is quite heavy computation, it should be done only once
		// TODO the code should maybe be moved somewhere else, in a special
		// Mir:ror class ?

		String theUser;
		try {
			final String theSecretKey = "423bef3588d1328b571584e4f7d29d46";
			final byte[] hash = MessageDigest.getInstance("MD5").digest((inSerial + theSecretKey).getBytes());

			// get the first 4 characters of the hash to a string
			final StringBuilder hashString = new StringBuilder();
			for (int i = 0; i < 2; ++i) {
				final String hex = Integer.toHexString(hash[i]);
				if (hex.length() == 1) {
					hashString.append('0');
					hashString.append(hex.charAt(hex.length() - 1));
				} else {
					hashString.append(hex.substring(hex.length() - 2));
				}
			}
			theUser = inSerial + hashString;
		} catch (final NoSuchAlgorithmException e) {
			VObjectImpl.LOGGER.fatal("Could not get a MD5 MessageDigest. Can't compute xmpp address of a mir:ror.");
			theUser = inSerial;
		}
		return theUser;
	}

	public long getObject_creation() {
		return this.object_creation;
	}

	public int getObject_delay() {
		return this.object_delay;
	}

	public HARDWARE getHardware() {
		return HARDWARE.findById(this.mHardware.get(this.object_hardware).getId());
	}

	public int getObject_left() {
		return this.object_left;
	}

	public ObjectProfile getProfile() {
		return this.mProfile.get(this.object_id);
	}

	public void setLeftAndRight(int inLeft, int inRight) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setObject_left(theUpdateMap, inLeft);
		setObject_right(theUpdateMap, inRight);
		update(theUpdateMap);
	}

	private void setObject_left(Map<String, Object> inUpdateMap, int inValue) {
		if (this.object_left != inValue) {
			this.object_left = inValue;
			inUpdateMap.put("object_left", inValue);
		}
	}

	private void setObject_right(Map<String, Object> inUpdateMap, int inValue) {
		if (this.object_right != inValue) {
			this.object_right = inValue;
			inUpdateMap.put("object_right", inValue);
		}
	}

	public String getObject_loc() {
		return this.object_loc;
	}

	public String getObject_login() {
		return this.object_login;
	}

	private void setObject_login(Map<String, Object> inUpdateMap, String inValue) {
		if (!this.object_login.equals(inValue)) {

			final UserPwd thePwd = UserPwdImpl.findByPseudoAndUser(this.object_login, getOwner());
			if (thePwd != null) {
				final String thePassword = thePwd.getPwd();
				thePwd.delete();
				try {
					new UserPwdImpl(getOwner().getId(), inValue, thePassword, false);
				} catch (final SQLException e) {
					VObjectImpl.LOGGER.fatal(e, e);
				}
			}

			this.object_login = inValue;
			inUpdateMap.put("object_login", this.object_login);
		}
	}

	private void setObject_serial(Map<String, Object> inUpdateMap, String inValue) {
		if (this.object_serial != inValue) {
			this.object_serial = inValue;
			inUpdateMap.put("object_serial", inValue);
		}
	}

	public void setLogin(String inLogin) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setObject_login(theUpdateMap, inLogin);
		update(theUpdateMap);
		updateKey("object_login");
	}

	public int getObject_mode() {
		return this.object_mode;
	}

	private void setObject_mode(Map<String, Object> inUpdateMap, int object_mode) {
		if (this.object_mode != object_mode) {
			this.object_mode = object_mode;
			inUpdateMap.put("object_mode", object_mode);
		}
	}

	public void setMode(int inMode) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setObject_mode(theUpdateMap, inMode);
		update(theUpdateMap);
	}

	private void setLastActivityTime(Map<String, Object> inUpdateMap, long time) {
		if (time > this.object_lastping + VObjectImpl.TIME_TO_UPDATE_LAST_ACTIVITY) {
			this.object_lastping = time;
			inUpdateMap.put("object_lastping", this.object_lastping);
		}
	}

	public void setLastActivityTime() {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setLastActivityTime(theUpdateMap, System.currentTimeMillis() / 1000);
		update(theUpdateMap);
	}

	public int getObject_n1() {
		return this.object_n1;
	}

	public int getObject_n2() {
		return this.object_n2;
	}

	public int getObject_nbmsg() {
		return this.object_nbmsg;
	}

	public void setPingCache(int inFirstEventID, int inSecondEventID) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setObject_n1(theUpdateMap, inFirstEventID);
		setObject_n2(theUpdateMap, inSecondEventID);
		update(theUpdateMap);
	}

	public void clearPingCache() {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setObject_n1(theUpdateMap, 0);
		update(theUpdateMap);
	}

	private void setObject_n1(Map<String, Object> inUpdateMap, int inValue) {
		if (this.object_n1 != inValue) {
			this.object_n1 = inValue;
			inUpdateMap.put("object_n1", inValue);
		}
	}

	private void setObject_n2(Map<String, Object> inUpdateMap, int inValue) {
		if (this.object_n2 != inValue) {
			this.object_n2 = inValue;
			inUpdateMap.put("object_n2", inValue);
		}
	}

	private void setObject_nbmsg(Map<String, Object> inUpdateMap, int inValue) {
		if (this.object_nbmsg != inValue) {
			this.object_nbmsg = inValue;
			inUpdateMap.put("object_nbmsg", inValue);
		}
	}

	//Use for Ping Object
	public void setNbMsg(int inNbMessage) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setObject_nbmsg(theUpdateMap, inNbMessage);
		update(theUpdateMap);
	}

	public User getOwner() {
		return this.mOwner.get(this.object_owner);
	}

	public int getObject_right() {
		return this.object_right;
	}

	public String getObject_serial() {
		return this.object_serial;
	}

	public int getObject_state() {
		return this.object_state;
	}

	private void setObject_state(Map<String, Object> inUpdateMap, int object_state) {
		if (this.object_state != object_state) {
			this.object_state = object_state;
			inUpdateMap.put("object_state", object_state);
		}
	}

	public void setState(int object_state) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setObject_state(theUpdateMap, object_state);
		update(theUpdateMap);
	}

	public void overrideState(int inObject_state) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		this.object_state = inObject_state;
		theUpdateMap.put("object_state", inObject_state);
		update(theUpdateMap);
	}

	public void setModeAndState(int inMode, int inState) {
		final Map<String, Object> inUpdateMap = new HashMap<String, Object>();
		setObject_mode(inUpdateMap, inMode);
		setObject_state(inUpdateMap, inState);
		update(inUpdateMap);
	}

	public int getObject_test() {
		return this.object_test;
	}

	private void setObject_test(Map<String, Object> inUpdateMap, int object_test) {
		if (this.object_test != object_test) {
			this.object_test = object_test;
			inUpdateMap.put("object_test", object_test);
		}
	}

	public void resetRebootState() {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setObject_test(theUpdateMap, 0);
		update(theUpdateMap);
	}

	public void setSleepTime(SleepTime inSleepTime) {
		setSleepTime(inSleepTime, true);
	}

	public void setSleepTime(SleepTime inSleepTime, boolean inUpdateState) {

		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();

		Factories.OBJECT_SLEEP.setObjectSleepTime(this, inSleepTime);

		if (inUpdateState) {
			// calcul s'il est en veille ou pas
			final boolean isSleeping = ObjectSleep.ObjectSleepCommon.asleep(this);
			if (isXMPP()) {
				final List<String> resources = getResources(); //IQResourcesQuery.getClientResources(getXmppAddress());
				if (isSleeping) {
					if (!resources.contains("asleep")) {
						sendXmppStatus(Message.MODE_VEILLE, JabberMessageFactory.IQ_STATUS_IDLE_MODE);
					} else {
						setObject_state(theUpdateMap, VObject.STATUS_VEILLE);
					}
				} else {
					if (resources.contains("asleep")) {
						sendXmppStatus(Message.MODE.ACTIF.getId(), JabberMessageFactory.IQ_STATUS_ASLEEP_MODE);
					} else {
						setObject_state(theUpdateMap, VObject.STATUS_ACTIF);
					}
				}
			} else {
				setObject_state(theUpdateMap, (isSleeping ? VObject.STATUS_VEILLE : VObject.STATUS_ACTIF));
			}
		}

		update(theUpdateMap);
	}

	public Timezone getTimeZone() {
		return this.timeZone.get(this.time_zone);
	}

	private void setTimeZone(Map<String, Object> inUpdateMap, Timezone inValue) {
		if ((inValue != null) && !this.timeZone.equals(inValue)) {
			this.time_zone = inValue.getId();
			inUpdateMap.put("time_zone", this.time_zone);
			this.timeZone.set(inValue);
		}
	}

	public void setTimeZone(Timezone inTimeZone) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setTimeZone(theUpdateMap, inTimeZone);
		update(theUpdateMap);
	}

	public long getObject_bc_version() {
		return this.object_bc_version;
	}

	public void setObject_bc_version(long inValue) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setObject_bc_version(theUpdateMap, inValue);
		update(theUpdateMap);
	}

	private void setObject_bc_version(Map<String, Object> inUpdateMap, long inValue) {
		if (this.object_bc_version != inValue) {
			this.object_bc_version = (int) inValue;
			inUpdateMap.put("object_bc_version", inValue);
		}
	}

	// /* (non-Javadoc)
	// * @see net.violet.platform.datamodel.VObject#setVeilleObjetDeactivated()
	// */
	public void setVeilleObjetDeactivated() {

		Factories.OBJECT_SLEEP.resetSleepTime(this);

		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();

		if (isXMPP()) {
			// TODO: changer la FSM pour spammer le lapin.
			sendXmppStatus(Message.MODE.ACTIF.getId(), JabberMessageFactory.ASLEEP_MODE);
		}
		setObject_state(theUpdateMap, VObject.STATUS_ACTIF);

		update(theUpdateMap);
	}

	public boolean isXMPP() {
		return (this.object_mode == VObject.MODE_XMPP) || (this.object_mode == VObject.MODE_XMPP_INACTIVE);
	}

	public Map<Nabcast, Subscriber> getSubscribedNabcast() {
		if (this.uSubscribedNabcast == null) {
			try {
				this.uSubscribedNabcast = DecoratedAssociation.createDecoratedAssociation(this, NabcastImpl.SPECIFICATION, "subscriber_nabcast", SubscriberImpl.SPECIFICATION, "subscriber_user");
			} catch (final SQLException e) {
				VObjectImpl.LOGGER.fatal(e, e);
			}
		}
		return this.uSubscribedNabcast;
	}

	public List<AppletSettings> getAppletServices() {
		return this.appletServices;
	}

	/**
	 * Itérateur (statique) sur les objets en mode XMPP qui ne sont plus actifs.
	 * 
	 * @param inWalker itérateur
	 * @return le nombre d'événements traités.
	 */

	public static int walkCheckJabber(RecordWalker<VObject> inWalker) {
		final List<Object> theValues = Arrays.asList(new Object[] { VObject.MODE_XMPP, VObject.MODE_XMPP_INACTIVE });
		final String condition = " object_mode IN (?,  ?) ";
		int theResult = 0;
		try {
			theResult = AbstractSQLRecord.walk(VObjectImpl.SPECIFICATION, condition, theValues, null /*
																																																																																																																																																																																																																																																																																																																																										 * order
																																																																																																																																																																																																																																																																																																																																										 * by
																																																																																																																																																																																																																																																																																																																																										 */, 0 /* skip */, inWalker);

		} catch (final SQLException anException) {
			VObjectImpl.LOGGER.fatal(anException, anException);
		}
		return theResult;
	}

	/**
	 * Itérateur (statique) sur les objets qui ne sont plus actifs.
	 * 
	 * @param inWalker itérateur
	 * @param inTimeNoPing délai ou l'on considère que l'objet n'est plus actif
	 *            en seconde
	 * @return le nombre d'événements traités.
	 */
	public static int walkCheckPing(RecordWalker<VObject> inWalker, long inTimeNoPing) {
		final long timeLastPing = CCalendar.getCurrentTimeInSecond() - inTimeNoPing;
		final List<Object> theValues = Arrays.asList(new Object[] { VObject.MODE_PING, timeLastPing });
		try {
			return AbstractSQLRecord.walk(VObjectImpl.SPECIFICATION, " object_mode = ?  and object_lastping < ? ", theValues, null, 0, inWalker);
		} catch (final SQLException anException) {
			VObjectImpl.LOGGER.fatal(anException, anException);
		}

		return 0;
	}

	/**
	 * Itérateur (statique) sur les objets auxquels on peut demander des infos
	 * 
	 * @param inWalker itérateur
	 * @return le nombre d'événements traités.
	 */
	public static int walkCheckServerUrl(RecordWalker<VObject> inWalker) {
		// 12650 est la version minimale du bytecode nécessaire pour pouvoir
		// demander la config
		// un bug qui peut bloquer la réponse de certains lapins est corrigé
		// dans 13045
		final int MINBCREVISION = 13045; // 12650

		final List<Object> theValues = Arrays.asList(new Object[] { VObject.MODE_XMPP, MINBCREVISION });
		final String condition = "(object_mode = ?) and object_bc_version >= ? ";

		int theResult = 0;
		try {
			theResult = AbstractSQLRecord.walk(VObjectImpl.SPECIFICATION, condition, theValues, null, 0, inWalker);
		} catch (final SQLException anException) {
			VObjectImpl.LOGGER.fatal(anException, anException);
		}
		return theResult;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.VObject#sendXmppStatus(int, int, int)
	 */
	public void sendXmppStatus(int inModeStatus, int inCurrentStatus, int inMessagePacketMode) {
		sendXmppStatus(inModeStatus, inMessagePacketMode);
		// setState(inCurrentStatus);
	}

	/**
	 * Envoie le paquet de changement d'état.
	 * 
	 * @param inModeStatus : etat dans lequel l'objet va passé
	 * @param inMessagePacketMode :
	 */
	private void sendXmppStatus(int inModeStatus, int inMessagePacketMode) {
		final MessageDraft theMessage = new MessageDraft(this);
		theMessage.setStatus(inModeStatus);
		theMessage.setEars(this.getObject_left(), this.getObject_right());
		theMessage.setSourceUpdate(true);
		MessageServices.sendUsingXmpp(theMessage, inMessagePacketMode);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.VObject#getXMPPLock()
	 */
	public Object getXMPPLock() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.VObject#getObject_lastping()
	 */
	public long getObject_lastping() {
		return this.object_lastping;
	}

	public ObjectPreferences getPreferences() {
		return this.mPreferences.get(this.object_id);
	}

	public boolean isDuplicated() {
		if (this.isDuplicated == null) {
			final Ztamp theZtamp = Factories.ZTAMP.findBySerial(this.object_serial);
			this.isDuplicated = false;
			if (theZtamp != null) {
				this.isDuplicated = theZtamp.isDuplicated();
			}

		}
		return this.isDuplicated;
	}

	public String getMD5Password() {
		final UserPwd theUserPwd = UserPwdImpl.findByPseudoAndUser(this.object_login, getOwner());
		if (theUserPwd != null) {
			return theUserPwd.getPwd();
		}

		return null;
	}

	public void setSerial(String inSerial) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setObject_serial(theUpdateMap, inSerial);
		update(theUpdateMap);
		updateKey("object_serial");

	}

	public List<String> getResources() {
		final List<String> resources = new ArrayList<String>();
		try {
			final List<Presences> presences = PresencesImpl.findByUsername(getObject_serial());
			for (final Presences presence : presences) {
				resources.add(presence.getResource());
			}
		} catch (final SQLException anException) {
			VObjectImpl.LOGGER.fatal(anException, anException);
		}
		return resources;
	}

	@Deprecated
	public static int walkOnNabaztagObject(RecordWalker<VObject> inWalker) {
		final List<Object> theValues = Arrays.asList(new Object[] { Hardware.HARDWARE.V1.getId(), Hardware.HARDWARE.V2.getId() });
		final String condition = " object_hardware = ? or object_hardware = ?";
		int theResult = 0;
		try {
			theResult = AbstractSQLRecord.walk(VObjectImpl.SPECIFICATION, condition, theValues, null /* order by */, 0 /* skip */, inWalker);
		} catch (final SQLException anException) {
			VObjectImpl.LOGGER.fatal(anException, anException);
		}
		return theResult;
	}

	public void deabonne2Nabcast(Nabcast nabcast) {
		final Map<Nabcast, Subscriber> subscribes = getSubscribedNabcast();
		if (subscribes.containsKey(nabcast)) {
			subscribes.remove(nabcast);
		}
	}

	public void changeStatus(int status) {
		if (this.isXMPP()) {
			final int inObjectState = this.getObject_state();
			if (status == VObject.STATUS_FORCE_ACTIF) {

				if ((inObjectState == VObject.STATUS_VEILLE) || (inObjectState == VObject.STATUS_WILLBE_VEILLE)) {
					// l'objet est en veille et recoit un message pour se mettre en actif forcé
					this.sendXmppStatus(Message.MODE_FORCE_ACTIF, VObject.STATUS_FORCE_ACTIF, JabberMessageFactory.IQ_STATUS_ASLEEP_MODE);
				} else if ((inObjectState == VObject.STATUS_FORCE_VEILLE) || (inObjectState == VObject.STATUS_WILLBE_FORCE_VEILLE)) {
					// l'objet est en veille forcé et reçoit un message pour se remettre actif
					this.sendXmppStatus(Message.MODE.ACTIF.getId(), VObject.STATUS_ACTIF, JabberMessageFactory.IQ_STATUS_ASLEEP_MODE);
				}
			} else if (status == VObject.STATUS_FORCE_VEILLE) {

				if ((inObjectState == VObject.STATUS_ACTIF) || (inObjectState == VObject.STATUS_WILLBE_ACTIF)) {
					// l'objet est actif et recoit un message pour se mettre en veille forcé
					this.sendXmppStatus(Message.MODE_FORCE_VEILLE, VObject.STATUS_FORCE_VEILLE, JabberMessageFactory.IQ_STATUS_IDLE_MODE);
				} else if ((inObjectState == VObject.STATUS_FORCE_ACTIF) || (inObjectState == VObject.STATUS_WILLBE_FORCE_ACTIF)) {
					// l'objet est en actif forcé et reçoit un message pour se remettre en veille
					this.sendXmppStatus(Message.MODE_VEILLE, VObject.STATUS_VEILLE, JabberMessageFactory.IQ_STATUS_IDLE_MODE);
				}
			}
		} else {
			this.setState(status);
		}
	}

}

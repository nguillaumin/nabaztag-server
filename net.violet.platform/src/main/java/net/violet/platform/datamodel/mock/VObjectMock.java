/**
 *
 */
package net.violet.platform.datamodel.mock;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.AppletSettings;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Hardware;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Nabcast;
import net.violet.platform.datamodel.ObjectPreferences;
import net.violet.platform.datamodel.ObjectProfile;
import net.violet.platform.datamodel.Subscriber;
import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.VObjectImpl;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.SleepTime;

public class VObjectMock extends AbstractMockRecord<VObject, VObjectMock> implements VObject {

	/**
	 * Champs de l'enregistrement.
	 */
	private final String object_serial;
	private String object_login;
	private final long object_creation;
	private final HARDWARE mHardware;
	private long object_lastping;
	// private int object_test;
	// private int object_left;
	// private int object_right;
	// private int object_n1;
	private int object_nbmsg;
	private int object_mode;
	private Timezone mTimezone;
	// private int object_delay;
	private final String object_loc;
	private int object_state;
	// private long object_bc_version;
	//private final Map<VAction, List<ObjectHasReadContent>> contents = new HashMap<VAction, List<ObjectHasReadContent>>();

	private final Map<Nabcast, Subscriber> uSubscribedNabcast = new HashMap<Nabcast, Subscriber>();

	private final User mOwner;

	private final ObjectProfile mProfile;
	private ObjectPreferences mPreferences;

	public VObjectMock(long inId, String inSerial, String inObjectLogin, User inOwner, HARDWARE inHardware, Timezone inTimezone, Lang inLang) {
		this(inId, inSerial, inObjectLogin, inOwner, inHardware, 0, inTimezone, inLang, "", Integer.MIN_VALUE, 0, System.currentTimeMillis(), null);
	}

	public VObjectMock(long inId, String inSerial, String inObjectLogin, User inOwner, HARDWARE inHardware, Timezone inTimezone, Lang inLang, long inTime) {
		this(inId, inSerial, inObjectLogin, inOwner, inHardware, 0, inTimezone, inLang, "", Integer.MIN_VALUE, 0, inTime, null);
	}

	public VObjectMock(long inId, String inSerial, String inObjectLogin, User inOwner, HARDWARE inHardware, Timezone inTimezone, Lang inLang, long inTime, Files pictureId) {
		this(inId, inSerial, inObjectLogin, inOwner, inHardware, 0, inTimezone, inLang, "", Integer.MIN_VALUE, 0, inTime, pictureId);
	}

	// Uniquement résrvé aux mirrors
	public VObjectMock(String inSerial) {
		super(0);
		this.object_serial = inSerial;
		this.object_login = null;
		this.mOwner = null;
		this.mTimezone = null;
		this.object_creation = 0;
		this.mHardware = HARDWARE.MIRROR;
		this.object_lastping = 0;
		this.object_loc = "";
		this.object_state = VObject.STATUS_ACTIF;
		this.object_mode = VObject.MODE_XMPP;
		this.mProfile = new ObjectProfileMock(this, "", null);
		this.mPreferences = new ObjectPreferencesMock(this, null);
	}

	public VObjectMock(long inId, String inSerial, String inObjectLogin, User inOwner, HARDWARE inHardware, long inObjectLastPing, Timezone inTimezone, Lang inLang, String inObjectLoc, int inObjectMode, int inObjectState, long inCreationDate, Files picture) {
		super(inId);
		this.object_serial = inSerial;
		this.object_login = inObjectLogin;
		this.mOwner = inOwner;
		this.mTimezone = inTimezone;
		this.object_creation = inCreationDate / 1000;
		this.mHardware = inHardware;
		this.object_lastping = inObjectLastPing;
		this.object_loc = inObjectLoc;
		this.object_state = inObjectState;

		if (inObjectMode == Integer.MIN_VALUE) {
			this.object_mode = (this.mHardware == HARDWARE.V2) ? VObject.MODE_XMPP : VObject.MODE_PING;
		} else {
			this.object_mode = inObjectMode;
		}
		this.mProfile = new ObjectProfileMock(this, "", (picture != null ? picture : inHardware.getPictureFile()));
		this.mPreferences = new ObjectPreferencesMock(this, inLang);
	}

	public void clearPingCache() {
	}

	public List<AppletSettings> getAppletServices() {
		return Collections.emptyList();
	}

	public long getObject_bc_version() {
		throw new UnsupportedOperationException();
	}

	public long getObject_creation() {
		return this.object_creation;
	}

	public int getObject_delay() {
		if (this.mHardware == HARDWARE.V1) {
			return 10;
		}
		throw new UnsupportedOperationException();
	}

	public HARDWARE getHardware() {
		return this.mHardware;
	}

	public long getObject_lastping() {
		return this.object_lastping;
	}

	public int getObject_left() {
		throw new UnsupportedOperationException();
	}

	public String getObject_loc() {
		return this.object_loc;
	}

	public String getObject_login() {
		return this.object_login;
	}

	public int getObject_mode() {
		return this.object_mode;
	}

//	public Map<VAction, List<ObjectHasReadContent>> getContents() {
//		return this.contents;
//	}

	public int getObject_n1() {
		throw new UnsupportedOperationException();
	}

	public int getObject_n2() {
		throw new UnsupportedOperationException();
	}

	public int getObject_right() {
		throw new UnsupportedOperationException();
	}

	public String getObject_serial() {
		return this.object_serial;
	}

	public int getObject_state() {
		return this.object_state;
	}

	public int getObject_test() {
		throw new UnsupportedOperationException();
	}

	public User getOwner() {
		return this.mOwner;
	}

	public Map<Nabcast, Subscriber> getSubscribedNabcast() {
		return this.uSubscribedNabcast;
	}

	public Object getXMPPLock() {
		throw new UnsupportedOperationException();
	}

	public String getXmppAddress() {
		final String theDomain = (getHardware().equals(Hardware.HARDWARE.MIRROR)) ? Constantes.XMPP_MIRROR_DOMAIN : Constantes.XMPP_NABAZTAG_DOMAIN;
		String inSerial = getObject_serial().toLowerCase();
		if (!VObjectImpl.checkJid(inSerial)) {
			inSerial = "invalid";
		}
		return VObjectImpl.makeMirrorXmppUser(inSerial) + "@" + theDomain;
	}

	public boolean isConnected() {
		return true;
	}

	public boolean isXMPP() {
		return (this.object_mode == VObject.MODE_XMPP) || (this.object_mode == VObject.MODE_XMPP_INACTIVE);
	}

	public void resetRebootState() {
		throw new UnsupportedOperationException();
	}

	public void sendXmppStatus(int inModeStatus, int inCurrentStatus, int inMessagePacketMode) {
		throw new UnsupportedOperationException();
	}

	public void setLeftAndRight(int inLeft, int inRight) {
		// TODO Auto-generated method stub

	}

	public void setLogin(String inLogin) {
		this.object_login = inLogin;

	}

	public void setMode(int inMode) {
		this.object_mode = inMode;
	}

	public void setLastActivityTime() {
		this.object_lastping = System.currentTimeMillis() / 1000;
	}

	public void setModeAndState(int inMode, int inState) {
		// TODO Auto-generated method stub

	}

	public void setObject_bc_version(long inValue) {
		// TODO Auto-generated method stub

	}

	public void setPingCache(int inFirstEventID, int inSecondEventID) {
		// TODO Auto-generated method stub

	}

	public void setSleepTime(SleepTime inSleepTime) {
		setSleepTime(inSleepTime, true);
	}

	public void setSleepTime(SleepTime inSleepTime, boolean inUpdateState) {
		Factories.OBJECT_SLEEP.setObjectSleepTime(this, inSleepTime);
	}

	public void setState(int object_state) {
		this.object_state = object_state;
	}

	public void overrideState(int inObject_state) {
		this.object_state = inObject_state;
	}

	public void setTimeZone(Timezone inTimeZone) {
		this.mTimezone = inTimeZone;
	}

	public Timezone getTimeZone() {
		return this.mTimezone;
	}

	public void setVeilleObjetDeactivated() {
		// TODO Auto-generated method stub

	}

	/**
	 * Méthode utilisé pour la migration du couché/debout
	 */
	public void resetVeille() {
		// TODO Auto-generated method stub

	}

	public ObjectProfile getProfile() {
		return this.mProfile;
	}

	public ObjectPreferences getPreferences() {
		return this.mPreferences;
	}

	public void setPreferences(ObjectPreferences preferences) {
		this.mPreferences = preferences;
	}

	public boolean isDuplicated() {
		return false;
	}

	public String getMD5Password() {
		return null;
	}

	public void setSerial(String inSerial) {
		// TODO Auto-generated method stub

	}

	public List<String> getResources() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getObject_nbmsg() {
		return this.object_nbmsg;
	}

	public void setNbMsg(int inNbMessage) {
		this.object_nbmsg = inNbMessage;
	}

	public void deabonne2Nabcast(Nabcast nabcast) {
		// TODO Auto-generated method stub

	}

	public void changeStatus(int status) {
		this.object_state = status;

	}

}

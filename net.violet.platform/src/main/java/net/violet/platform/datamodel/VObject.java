package net.violet.platform.datamodel;

import java.util.List;
import java.util.Map;

import net.violet.db.records.Record;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.util.SleepTime;

public interface VObject extends Record<VObject> {

	/**
	 * Constantes pour le mode: ping.
	 */
	int MODE_PING = 0;
	/**
	 * Constantes pour le mode: démo.
	 */
	int MODE_DEMO = 1;
	/**
	 * Constantes pour le mode: xmpp, mais pas encore connecté.
	 */
	int MODE_XMPP_TR = 3;
	/**
	 * Constantes pour le mode: xmpp.
	 */
	int MODE_XMPP = 4;
	/**
	 * Constantes pour le mode: ping (inactif).
	 */
	int MODE_PING_INACTIVE = 5;
	/**
	 * Constantes pour le mode: xmpp (inactif).
	 */
	int MODE_XMPP_INACTIVE = 6;
	/**
	 * Constantes pour la ressource XMPP: actif (debout).
	 */
	String XMPP_RESOURCE_ACTIVE = "idle";
	/**
	 * Constantes pour la ressource XMPP: couché.
	 */
	String XMPP_RESOURCE_ASLEEP = "asleep";
	/**
	 * Constantes pour la ressource XMPP: mode interactif.
	 */
	String XMPP_RESOURCE_ITMODE = "itmode";
	/**
	 * Permet de forcer le status de l'objet en veille
	 */
	int STATUS_FORCE_VEILLE = 1;
	/**
	 * Permet de forcer le status de l'objet en actif
	 */
	int STATUS_FORCE_ACTIF = 2;
	/**
	 * Status de l'objet en veille
	 */
	int STATUS_VEILLE = 3;
	/**
	 * Status de l'objet actif
	 */
	int STATUS_ACTIF = 4;
	/**
	 * Status de l'objet à passer en veille
	 */
	int STATUS_WILLBE_VEILLE = 5;
	/**
	 * Status de l'objet à passer en actif
	 */
	int STATUS_WILLBE_ACTIF = 6;
	/**
	 * Status de l'objet à passer en veille forcé
	 */
	int STATUS_WILLBE_FORCE_VEILLE = 7;
	/**
	 * Status de l'objet à passer en actif forcé
	 */
	int STATUS_WILLBE_FORCE_ACTIF = 8;

	/**
	 * rfid default status
	 */
	int STATUS_NORMAL = 0;

	/**
	 * check si il est connecté ( au delà de trente minutes on le considère
	 * déconnecté)
	 * 
	 * @return
	 */
	boolean isConnected();

	/**
	 * Accesseur sur l'adresse XMPP.
	 * 
	 * @return l'adresse de l'objet.
	 */
	String getXmppAddress();

	long getObject_creation();

	int getObject_delay();

	HARDWARE getHardware();

	/**
	 * Récupère la position de l'oreille gauche (requête dans la base ad hoc)
	 * 
	 * @return (position)
	 */
	int getObject_left();

	void setLeftAndRight(int inLeft, int inRight);

	String getObject_loc();

	String getObject_login();

	void setLogin(String inLogin);

	void setSerial(String inSerial);

	int getObject_mode();

	void setMode(int inMode);

	void setLastActivityTime();

	int getObject_n1();

	int getObject_n2();

	// use for Ping object ( V1 and daldal)
	int getObject_nbmsg();

	void setPingCache(int inFirstEventID, int inSecondEventID);

	void clearPingCache();

	/**
	 * Accesseur sur le possesseur de l'objet.
	 * 
	 * @return le possesseur de l'objet (ou <code>null</code>).
	 */
	User getOwner();

	/**
	 * Récupère la position de l'oreille droite (requête dans la base ad hoc)
	 * 
	 * @return (position)
	 */
	int getObject_right();

	String getObject_serial();

	/**
	 * Récupère l'état du lapin (requête dans la base ad hoc)
	 * 
	 * @return (1 sommeil,2 levé,0 selon ces heures de couché)
	 */
	int getObject_state();

	void setState(int object_state);

	// TODO : special case because cache notification is slow/missing so i override this value in db.
	// delete this method when new cache is available
	void overrideState(int object_state);

	void setModeAndState(int inMode, int inState);

	int getObject_test();

	void resetRebootState();

	void setNbMsg(int inNbMessage);

	void setSleepTime(SleepTime inSleepTime);

	void setSleepTime(SleepTime inSleepTime, boolean inUpdateState);

	void setTimeZone(Timezone inTimeZone);

	Timezone getTimeZone();

	long getObject_bc_version();

	void setObject_bc_version(long inValue);

	/**
	 * Permet de supprimer la mise en vieille.
	 */
	void setVeilleObjetDeactivated();

	/**
	 * Détermine si l'objet est un objet jabber.
	 */
	boolean isXMPP();

	Map<Nabcast, Subscriber> getSubscribedNabcast();

	List<AppletSettings> getAppletServices();

	/**
	 * Envoie le paquet de changement d'état et enregistre l'état dans la base.
	 * 
	 * @param inModeStatus : etat dans lequel l'objet va passé
	 * @param inCurrentStatus : etat dans lequel l'objet est situé (
	 *            actif/debout/forcer debout/forcer couché)
	 * @param inMessagePacketMode :
	 */
	void sendXmppStatus(int inModeStatus, int inCurrentStatus, int inMessagePacketMode);

	/**
	 * Accesseur sur un mutex pour XMPP.
	 */
	Object getXMPPLock();

	long getObject_lastping();

	//Map<VAction, List<ObjectHasReadContent>> getContents();

	boolean delete();

	ObjectProfile getProfile();

	ObjectPreferences getPreferences();

	public boolean isDuplicated();

	String getMD5Password();

	List<String> getResources();

	@Deprecated
	void deabonne2Nabcast(Nabcast nabcast);

	void changeStatus(int status);
}

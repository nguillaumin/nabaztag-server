package net.violet.platform.datamodel;

import java.util.List;
import java.util.Map;

import net.violet.db.records.Record;

/**
 * Acounts creations
 *
 */
public interface User extends Record<User> {

	boolean use24();

	// / ACCESSEURS & SELECTEURS
	@Deprecated
	String getUser_firstName();

	@Deprecated
	String getUser_lastName();

	int getUser_24h();

	Timezone getTimezone();

	void setTimeInformation(int is24, Timezone timezone);

	void setMessageParameters(int authmsg, int inNotifyPlayed, int inNotifyReceived, int newsletter);

	void setNotifyMessageReceived(boolean inNotifyMessagePlayed);

	void setSignatureInformation(long inAnimId, String colorSign, long inMusicId);

	void setComment(String comment);

	void setEmail(String email);

	void setExtConnect(long extConnect);

	void setIsGood(int isGood);

	void setImage(int image);

	/**
	 * new method : annu.setLangPreferences()
	 */
	@Deprecated
	void setLang(Lang inLang);

	void setHasObject();

	void setUserWithoutObject();

	/**
	 * Modifie le mot de passe (en clair).
	 * 
	 * @param inValue le mot de passe en clair.
	 */
	void setPassword(String inValue);

	long getUser_authmsg();

	boolean getNotifyMessagePlayed();

	boolean getNotifyMessageReceived();

	Integer getUser_newsletter();

	Anim getColor();

	String getColorSign();

	Music getMusic();

	String getUser_comment();

	long getCreationDate();

	String getUser_email();

	long getUser_extconnect();

	long getUser_good();

	long getUser_image();

	/**
	 * Accesseur sur la langue principale de l'utilisateur. new method :
	 * annu.getLangPreferences()
	 * 
	 * @return la langue principale.
	 */
	@Deprecated
	Lang getLang();

	/**
	 * Accesseur sur la liste des langues de l'utilisateur.
	 * 
	 * @return la liste des langues de l'utilisateur.
	 */
	List<Lang> getLangs();

	/**
	 * Vérifie si l'utilisateur a au moins un objet le champ user_main sert
	 * desormais a dire si l'utilisateur a au moins un objet
	 */
	boolean getUserHasObject();

	/**
	 * Vérifie le mot de passe.
	 * 
	 * @param inPassword mot de passe (en clair)
	 * @return <code>true</code> si le mot de passe est correct.
	 */
	boolean checkPasswordPlain(String inPassword);

	/**
	 * Vérifie le mot de passe
	 * 
	 * @param inPassword mot de passe (somme MD5)
	 * @return <code>true</code> si le mot de passe est correct.
	 */
	boolean checkPasswordMD5Sum(String inPassword);

	String getPassword();

	UserPrefs getUPreferences();

	/**
	 * Get all the user's friends
	 */
	List<User> getFriends();

	/**
	 * Get all the user's friends with object
	 */
	List<User> getFriendsWithObject();

	Map<User, Black> getBlackList();

	/**
	 * verifies si un utilisateur est deja dans les amis.
	 * 
	 * @param friend : l'utilisateur dont on veut verifier s'il est deja dans
	 *            les amis
	 * @return 1 si c'est un ami, 0 sinon
	 */
	boolean existFriend(User friend);

	/**
	 * verifies si un utilisateur est deja dans la blackliste.
	 * 
	 * @param friend : l'utilisateur dont on veut verifier s'il est deja dans la
	 *            blackliste
	 * @return 1 si c'est un ami, 0 sinon
	 */
	int existInBlackliste(User friend);

	/**
	 * @param delete : liste des langues à retirer
	 * @param add : liste des langues à ajouter
	 * @param update : liste des langues à modifier???
	 */
	void updateUserSpokenLanguages(List<Lang> delete, List<Lang> add, List<List<Lang>> update);

	Annu getAnnu();

	/**
	 * This is the user's signature. for the flash
	 */
	String getSignature(boolean reload);

	/**
	 * Returns the mod to use to display hours. This method uses the user_24h
	 * parameter and returns a usefull value. user_24h value can be 1 or 0, but
	 * we often use 24 or 12
	 * 
	 * @return 24 if user_24 = 1, 12 otherwise
	 */
	int getHourMod();

	/**
	 * Liste des amis ou des utilisateurs blacklistés
	 * 
	 * @param type ( 0 : amis - 1 : blakclist)
	 * @param msg utilisation uniquement pour les messages, donc on ne liste que
	 *            les gens avec lapin
	 * @return
	 */
	List<User> getFriendBlackList(int type, int msg);

	List<Nabcast> getCreatedNabcasts();

	void addCreatedNabcast(Nabcast newNabcast);

	void setPreferences(Timezone inTimezone, int inTimeFormat, boolean inIsNewsLetter, boolean inNotifyPlayed);

	List<Contact> getAllContacts();

	void addContact(Contact inContact);

	void removeContact(Contact inContact);

	void removeAlternateTTSLang(Lang reference);

	void setTTSLang(Lang reference);

	String getUserColorSign();
}

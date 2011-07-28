package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.violet.platform.datamodel.Black;
import net.violet.platform.datamodel.Contact;
import net.violet.platform.datamodel.Event;
import net.violet.platform.datamodel.FriendsLists;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.MessageSent;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.UserEmail;
import net.violet.platform.datamodel.UserFriendsAddress;
import net.violet.platform.datamodel.UserImpl;
import net.violet.platform.datamodel.UserPrefs;
import net.violet.platform.datamodel.UserPwd;
import net.violet.platform.datamodel.Signature.ColorType;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.ConvertTools;

import org.apache.log4j.Logger;

public class UserData extends APIData<User> {

	private static final Logger LOGGER = Logger.getLogger(UserData.class);

	private static final CCalendar LAST_REFRESH_CACHE = new CCalendar(false);
	private static List<UserData> gUserDataCacheList = new ArrayList<UserData>();

	private static final int CACHE_TTL = 1200000;

	private final AnnuData annu_data;

	private ColorType colorType;

	public static UserData getData(User inUser) {
		try {
			return RecordData.getData(inUser, UserData.class, User.class);
		} catch (final InstantiationException e) {
			UserData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			UserData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			UserData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			UserData.LOGGER.fatal(e, e);
		}

		return null;
	}

	/**
	 * Construct a UserData container USED BY REFLECTION
	 */
	protected UserData(User inUser) {
		super(inUser);
		this.annu_data = new AnnuData(inUser);
	}

	/**
	 * Finds a UserImpl from his/her id
	 * 
	 * @param inId
	 * @return
	 */
	public static UserData find(long inId) {
		return UserData.getData(Factories.USER.find(inId));
	}

	/**
	 * Search users according to the given parameters
	 * 
	 * @param tri
	 * @param inTypeTri
	 * @param pseudo
	 * @param agemin
	 * @param agemax
	 * @param sexe
	 * @param ville
	 * @param pays
	 * @param lettre_deb
	 * @param fullFriend
	 * @return
	 */
	public static List<UserData> searchUsers(int tri, String inTypeTri, String pseudo, int agemin, int agemax, String sexe, String ville, String pays) {
		return UserData.generateList(UserImpl.searchUsers(tri, inTypeTri, pseudo, agemin, agemax, sexe, ville, pays));
	}

	/**
	 * Find the list fo the last rabbits to have registered
	 * 
	 * @param nbr
	 * @return
	 */
	public static List<UserData> findListLastRabbits(int nbr) {
		synchronized (UserData.LAST_REFRESH_CACHE) {
			if ((UserData.gUserDataCacheList.size() < nbr) || (System.currentTimeMillis() - UserData.LAST_REFRESH_CACHE.getTimeInMillis() > UserData.CACHE_TTL)) {
				UserData.gUserDataCacheList = UserData.generateList(UserImpl.recuperationDesDerniersInscrits(nbr, true, false));
				UserData.LAST_REFRESH_CACHE.setTimeInMillis(Calendar.getInstance().getTimeInMillis());
			}
		}

		if (nbr >= UserData.gUserDataCacheList.size()) {
			return UserData.gUserDataCacheList;
		}

		return UserData.gUserDataCacheList.subList(0, nbr);
	}

	/**
	 * Retourne la liste des derniers inscrits (selon le nombre) dans les
	 * profils utilisateurs ayant un lapin
	 * 
	 * @param nombre de profils
	 * @param langue principale de l'utilisateur
	 * @param boolean si vrai, retourne seulement les Lapins possedants une
	 *        image
	 * @return la liste des userRef
	 */
	public static List<UserData> recuperationDesDerniersInscrits(int nombre, Lang inLang, boolean bImage) {
		if (inLang != null) {
			return UserData.generateList(UserImpl.recuperationDesDerniersInscrits(nombre, inLang.getId(), bImage));
		}
		return Collections.emptyList();
	}

	/**
	 * Generates a list of UserData with the given UserImpl Set
	 * 
	 * @param inLang
	 * @return
	 */
	public static List<UserData> generateList(Set<User> inUser) {
		if ((inUser != null) && (inUser.size() != 0)) {
			return UserData.generateList(new ArrayList<User>(inUser));
		}

		return Collections.emptyList();
	}

	/**
	 * Generates a list of UserData with the given UserImpl list
	 * 
	 * @param inLang
	 * @return
	 */
	public static List<UserData> generateList(List<User> inUser) {
		if ((inUser != null) && (inUser.size() != 0)) {
			final List<UserData> categDataList = new ArrayList<UserData>();

			for (final User tempUser : inUser) {
				categDataList.add(UserData.getData(tempUser));
			}

			return categDataList;
		}
		return Collections.emptyList();
	}

	/**
	 * Accesseur à partir d'un ID application.
	 * 
	 * @return l'objet ou <code>null</code> si l'objet n'existe pas.
	 */
	public static UserData findByAPIId(String inAPIId, String inAPIKey) {
		UserData theResult = null;

		final long theID = APIData.fromObjectID(inAPIId, ObjectClass.USER, inAPIKey);
		if (theID != 0) {
			final User theUser = Factories.USER.find(theID);
			if (theUser != null) {
				theResult = UserData.getData(theUser);
			}
		}

		return theResult;
	}

	public static UserData findByEmail(String email) {
		UserData theResult = null;

		final User theUser = Factories.USER.findByEmail(email);
		if (theUser != null) {
			theResult = UserData.getData(theUser);
		}

		return theResult;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		final User theUser = getRecord();
		if (theUser != null) {
			return theUser.getId();
		}

		return 0;
	}

	/**
	 * @return the user_id
	 */
	public long getUser_id() {
		return getId();
	}

	/**
	 * return the user_lang UTILISE DANS LES JSP
	 */
	@Deprecated
	public long getUser_lang() {
		final User theUser = getRecord();
		if ((theUser != null) && (theUser.getAnnu().getLangPreferences() != null)) {
			return theUser.getAnnu().getLangPreferences().getId();
		}
		return 2;
	}

	public SiteLangData getUserLang() {
		return this.annu_data.getLang();
	}

	/**
	 * return the user_24
	 */
	public int getUser_24() {
		final User theUser = getRecord();
		if (theUser != null) {
			return theUser.getUser_24h();
		}

		return 0;
	}

	public boolean use24h() {
		final User theUser = getRecord();
		if (theUser != null) {
			return theUser.getUser_24h() == 1;
		}

		return true;
	}

	/**
	 * return the user_good
	 */
	public long getUser_good() {
		final User theUser = getRecord();
		if (theUser != null) {
			return theUser.getUser_good();
		}

		return 0;
	}

	/**
	 * @return the user_image
	 */
	public long getUser_image() {
		final User theUser = getRecord();
		if (theUser != null) {
			return theUser.getUser_image();
		}

		return 0;
	}

	/**
	 * @return the user_picture
	 */
	public long getUser_picture() {
		return getUser_image();
	}

	@Deprecated
	public String getUser_pseudo() {
		String pseudo = this.annu_data.getFirstName();
		if (((pseudo == null) || pseudo.equals(net.violet.common.StringShop.EMPTY_STRING)) && (getRecord() != null)) {
			pseudo = getRecord().getUser_firstName();
		}

		return ((pseudo == null) || pseudo.equals(net.violet.common.StringShop.EMPTY_STRING)) ? net.violet.common.StringShop.EMPTY_STRING : pseudo;
	}

	/**
	 * @return the user_comment
	 */
	public String getUser_comment() {
		final User theUser = getRecord();
		if ((theUser != null) && (theUser.getUser_comment() != null)) {
			return theUser.getUser_comment();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return the user_email
	 */
	public String getUser_email() {
		final User theUser = getRecord();
		if ((theUser != null) && (null != theUser.getUser_email())) {
			return theUser.getUser_email();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return user_signature
	 */
	public String getUser_signature() {
		final User theUser = getRecord();
		if (theUser != null) {
			return theUser.getSignature(false);
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return user_has_object
	 */
	public String getUser_has_object() {
		final User theUser = getRecord();
		if (theUser != null) {
			if (theUser.getUserHasObject()) {
				return "true";
			}
		}
		return "false";
	}

	/**
	 * vérifie si l'utilisateur a au moins un objet return si oui > 0
	 */
	public long getUserWithAtLeastOneObject() {
		final User theUser = getRecord();
		if ((theUser != null) && theUser.getUserHasObject()) {
			return 1;
		}
		return 0;
	}

	public AnnuData getAnnu() {
		return this.annu_data;
	}

	/**
	 * @return the pays_nom
	 */
	public String getPays_nom() {
		return this.annu_data.getPays().get("pays_nom");
	}

	/**
	 * @return the annu_city
	 */
	public String getAnnu_city() {
		return this.annu_data.getAnnu_city();
	}

	public String getAnnu_desc() {
		return this.annu_data.getDescription();
	}

	public FilesData getAnnu_pictureFiles() {
		return this.annu_data.getPictureFile();
	}

	/**
	 * @return the PaysImpl
	 */
	public Map<String, String> getPays() {
		return this.annu_data.getPays();
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return this.annu_data.getAge();
	}

	public String getAnnu_sexe() {
		return this.annu_data.getAnnu_sexe();
	}

	public String getFirstName() {
		final User theUser = getRecord();
		if ((theUser != null) && (theUser.getAnnu() != null)) {
			final String firstName = theUser.getAnnu().getAnnu_prenom();
			if (firstName != null) {
				return firstName;
			}
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getLastName() {
		final User theUser = getRecord();
		if ((theUser != null) && (theUser.getAnnu() != null)) {
			final String lastName = theUser.getAnnu().getAnnu_nom();
			if (lastName != null) {
				return lastName;
			}
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public Date getBirthDate() {
		final User theUser = getRecord();
		if ((theUser != null) && (theUser.getAnnu() != null)) {
			return getRecord().getAnnu().getAnnu_datebirth();
		}

		return null;
	}

	public String getAnnu_zip() {
		return this.annu_data.getAnnuCp();
	}

	public boolean isNewsletterSubscriber() {
		final User theUser = getRecord();
		if (theUser != null) {
			return theUser.getUser_newsletter() == 1;
		}

		return false;
	}

	public String getTimeZoneName() {
		final User theUser = getRecord();
		if (theUser != null) {
			return theUser.getTimezone().getTimezone_name();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public Date getCreationDate() {
		final User theUser = getRecord();
		if (theUser != null) {
			return new Date(theUser.getCreationDate() * 1000);
		}

		return null;
	}

	/**
	 * Returns the Timezone object used by the user. Caution : this method can
	 * return null.
	 */
	public Timezone getTimezone() {
		final User theUser = getRecord();
		if (theUser != null) {
			return theUser.getTimezone();
		}

		return null;
	}

	public boolean checkPasswordPlain(String inPassword) {
		final User theUser = getRecord();
		if (theUser != null) {
			return theUser.checkPasswordPlain(inPassword);
		}

		return false;
	}

	public String getPassword() {
		final User theUser = getRecord();
		if (theUser != null) {
			return theUser.getPassword();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * Creates a new User according to the provided parameters. This method does
	 * NOT check if some parameters (such as email address) already exists in
	 * the database.
	 * 
	 * @param inEmail
	 * @param inPassword
	 * @param inLang
	 * @param inCountry
	 * @param inCity
	 * @param inFirstName
	 * @param inLastName
	 * @param inTimezone
	 * @return the UserData, or null if the creation failed.
	 */
	public static UserData createNewUser(String inEmail, String inPassword, SiteLangData inLang, CountryData inCountry, String inCity, String inFirstName, String inLastName, TimezoneData inTimezone) {
		final User theUser = Factories.USER.createNewUser(inEmail, inPassword, inLang.getReference(), inCountry.getPaysCode(), inFirstName, inLastName, inTimezone.getId());
		if (theUser != null) {
			theUser.getAnnu().setCity(inCity);
			return UserData.getData(theUser);
		}
		return null;
	}

	public void setPassword(String password) {
		final User theUser = getRecord();
		if (theUser != null) {
			theUser.setPassword(password);
		}
	}

	public void setHasObject() {
		final User theUser = getRecord();
		if (theUser != null) {
			theUser.setHasObject();
		}
	}

	public void setEmail(String email) {
		final User theUser = getRecord();
		if (theUser != null) {
			theUser.setEmail(email);
		}
	}

	public User getReference() {
		return getRecord();
	}

	public void setPreferences(TimezoneData inTimezone, int inTimeFormat, SiteLangData inLanguage, boolean inIsNewsLetter, boolean inNotifyPlayed) {
		final User theUser = getRecord();
		if (theUser != null) {
			theUser.setPreferences(inTimezone.getRecord(), inTimeFormat, inIsNewsLetter, inNotifyPlayed);
			theUser.getAnnu().setLangPreferences(inLanguage.getReference());
		}
	}

	/**
	 * Searches for User objects according to the provided criteria. Some parameters can be null. Only users matching ALL the provided parameters
	 * (i.e. parameters which are not null) are returned.
	 * 
	 * If a user did not filled his/her first name and last name information (i.e he/she does not have any names) he/she is not added to the returned list.
	 * 
	 * @param theFirstName
	 * @param theLastName
	 * @param olderThan
	 * @param youngerThan
	 * @param gender note : pass UNKNOW to search for people with no gender
	 * @param theCity
	 * @param theCountry
	 * @return a list of UserData, can be empty.
	 */
	public static List<UserData> searchUsers(String theFirstName, String theLastName, Integer olderThan, Integer youngerThan, AnnuData.Gender gender, String theCity, String theCountry, int skip, int count) {

		// note : when the passed param gender is null, we do NOT search for
		// people with unknown gender : we must not apply the param
		return UserData.generateList(Factories.USER.searchUsers(theFirstName, theLastName, olderThan, youngerThan, gender, theCity, theCountry, skip, count));
	}

	public boolean notifyIfReceived() {
		final User theUser = getRecord();
		if (theUser != null) {
			return theUser.getNotifyMessageReceived();
		}

		return false;
	}

	public boolean notifyIfPlayed() {
		final User theUser = getRecord();
		if (theUser != null) {
			return theUser.getNotifyMessagePlayed();
		}

		return false;
	}

	public List<UserData> getBlackedUsers() {
		final List<UserData> result = new ArrayList<UserData>();
		final User theUser = getRecord();
		if (theUser != null) {
			for (final User u : theUser.getBlackList().keySet()) {
				result.add(UserData.getData(u));
			}
		}

		return result;
	}

	public void removeAlternateTTS_Langs(TtsLangData inLangData) {
		final User theUser = getRecord();
		if (theUser != null) {
			theUser.removeAlternateTTSLang(inLangData.getReference());
		}
	}

	public void addTTS_Language(TtsLangData inLangData) {
		final User theUser = getRecord();
		if (theUser != null) {
			theUser.setTTSLang(inLangData.getReference());
		}
	}

	public List<Lang> getLangs() {
		final User theUser = getRecord();
		if (theUser != null) {
			return theUser.getLangs();
		}
		return Collections.emptyList();
	}

	public AnimData getAnimation() {
		final User theUser = getRecord();
		if (theUser != null) {
			return AnimData.getData(theUser.getColor());
		}
		return null;
	}

	public MusicData getMusicData() {
		final User theUser = getRecord();
		if (theUser != null) {
			return MusicData.getData(theUser.getMusic());
		}
		return null;
	}

	public ColorType getColorType() {
		final User theUser = getRecord();
		if (theUser != null) {
			this.colorType = ColorType.getColorByValue(theUser.getUserColorSign());
		}
		return this.colorType;
	}

	public void setSignatureInformation(AnimData theAnimData, ColorType colorType, MusicData theMusicData) {
		final User theUser = getRecord();
		long anim_id = 0;
		long music_id = 0;
		if (theAnimData != null) {
			anim_id = theAnimData.getId();
		}
		if (theMusicData != null) {
			music_id = theMusicData.getId();
		}
		if (theUser != null) {
			theUser.setSignatureInformation(anim_id, colorType.getValue(), music_id);
		}
	}

	public long getToken() {
		final User theUser = getRecord();
		if (theUser != null) {
			return theUser.getUser_extconnect();
		}
		return 0;
	}

	public boolean hasToken() {
		return getToken() > 0;
	}

	public long generateToken() {
		final User theUser = getRecord();
		if (theUser != null) {
			final long token = CCalendar.getCurrentTimeInSecond() + ConvertTools.randCodeInt();
			theUser.setExtConnect(token);
			return token;
		}

		return 0;
	}

	public void clearToken() {
		final User theUser = getRecord();
		if (theUser != null) {
			theUser.setExtConnect(0);
		}
	}

	@Override
	protected boolean doDelete() {

		synchronized (this) { //Attempt to handle the race condition although it does not handle the multiple servers

			if (this.getId() == 1) {
				return false;
			}

			final User theUserToExterminate = this.getReference();
//			The Extermination Begins... 

			final AnnuData theUserAnnu = this.getAnnu();
			if (theUserAnnu != null) {
				theUserAnnu.delete();
			}

//			Users he blacklisted
			for (final UserData theBlackListedUser : this.getBlackedUsers()) {
				BlackData.removeFromBlackList(this, theBlackListedUser);
			}

//			Users who blacklisted him
			for (final Black theUsers : Factories.BLACK.whoBlackListedMe(theUserToExterminate)) {
				BlackData.removeFromBlackList(UserData.getData(theUsers.getUser()), UserData.getData(theUsers.getBlacked()));
			}

//			Delete all the contacts
			for (final Contact aContact : Factories.CONTACT.findAllContactByUser(theUserToExterminate, 0, 0)) {
				aContact.delete();
			}

//			Delete all the contacts who have me on their list
			for (final Contact aContact : Factories.CONTACT.findAllWhoHaveMeOnTheirFriendList(theUserToExterminate)) {
				aContact.delete();
			}

//			Delete all the sent contact requests
			for (final Contact aContact : Factories.CONTACT.findAllSentContactRequest(theUserToExterminate, 0, 0)) {
				aContact.delete();
			}

//			Delete all the received contact requests
			for (final Contact aContact : Factories.CONTACT.findAllReceivedContactRequest(theUserToExterminate, 0, 0)) {
				aContact.delete();
			}

//			Delete the user's friend list
			final FriendsLists theUserFriendList = Factories.FRIENDS_LISTS.findByUser(theUserToExterminate);
			if (theUserFriendList != null) {
				theUserFriendList.delete();
			}

//			Delete all the nabascat created by the user
			for (final NabcastData aCreatedNabcast : NabcastData.findAllCreatedByUser(theUserToExterminate)) {
				for (final NabcastValData aNabCastValData : NabcastValData.findSongsNabcast(aCreatedNabcast.getReference(), 0, 0, 0, 0, theUserToExterminate)) {
					aNabCastValData.delete();
				}
				aCreatedNabcast.delete();
			}

			for (final UserFriendsAddress aUserFriendAddress : Factories.USER_FRIENDS_ADDRESS.findFriendsAddressByUser(theUserToExterminate)) {
				aUserFriendAddress.delete();
			}

//			Delete language
			final List<Lang> theUserLangs = theUserToExterminate.getLangs();
			for (final TtsLangData aLang : TtsLangData.findAll(theUserToExterminate)) {
				theUserLangs.remove(aLang);
			}

			final UserPrefs theUserPref = Factories.USER_PREFS.find(theUserToExterminate.getId());
			if (theUserPref != null) {
				theUserPref.delete();
			}

			for (final UserEmail aUserAlternateEmail : Factories.USER_EMAIL.findAllByUser(theUserToExterminate)) {
				aUserAlternateEmail.delete();
			}

			for (final UserPwd aUserPwd : Factories.USER_PWD.getAllByUser(theUserToExterminate)) {
				aUserPwd.delete();
			}

			final Messenger theMessenger = Factories.MESSENGER.findByUser(theUserToExterminate);
			if (theMessenger != null) {
				for (final MessageReceived aMessageReceived : Factories.MESSAGE_RECEIVED.findMessageReceivedByRecipientOrSender(theMessenger)) {
					final Message aMessage = aMessageReceived.getMessage();
					aMessageReceived.delete();
					if (aMessage != null) {
						final Integer event_id = aMessage.getEvent_id();
						if (event_id != null) {
							final Event anEvent = Factories.EVENT.find(event_id);
							if (anEvent != null) {
								anEvent.delete();
							}
						}
						if (aMessage.isOrphan()) {
							aMessage.delete();
						}
					}
				}

				for (final MessageSent aMessageSent : Factories.MESSAGE_SENT.findMessageSentBySenderOrRecipient(theMessenger)) {
					final Message aMessage = aMessageSent.getMessage();
					aMessageSent.delete();
					if ((aMessage != null) && aMessage.isOrphan()) {
						aMessage.delete();
					}
				}
				theMessenger.delete();
			}

//			Kill all his objets
			for (final VObjectData aVObjectData : VObjectData.findByOwner(this)) {
				aVObjectData.delete();
			}

//			Delete all the music of the user
			for (final Music aMusic : Factories.MUSIC.findAllPersoByUser(theUserToExterminate)) {
				aMusic.delete();
			}

//			Final extermination
			return super.doDelete();

		}
	}

	@Override
	protected ObjectClass getObjectClass() {
		return ObjectClass.USER;
	}
}

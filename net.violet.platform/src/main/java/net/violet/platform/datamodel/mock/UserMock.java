package net.violet.platform.datamodel.mock;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.common.utils.DigestTools;
import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Anim;
import net.violet.platform.datamodel.Annu;
import net.violet.platform.datamodel.Black;
import net.violet.platform.datamodel.Contact;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.Nabcast;
import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.UserEmail;
import net.violet.platform.datamodel.UserPrefs;
import net.violet.platform.datamodel.factories.Factories;

/**
 * @author slorg1
 */
public class UserMock extends AbstractMockRecord<User, UserMock> implements User {

	/**
	 * Champs de l'enregistrement.
	 */
	private String login;
	private String password;
	private String email;
	private Lang mLang;
	private final String country;
	private final String firstName;
	private final String lastName;
	private String user_color_sign;
	private Timezone mTimeZone;
	private int mIsGood;
	private boolean is24;
	private final long creationDate;
	private String mComment;
	private Integer user_newsletter;
	private long mExtConnect;
	private boolean mNotifyMessagePlayed;
	private boolean mNotifyMessageReceived;
	private int user_authmsg;
	private final int mImage;
	private int user_main;
	private long user_color = 5L;
	private long user_music;

	private final Map<User, UserEmail> alternateEmails = new HashMap<User, UserEmail>();
	private final Map<User, Black> blackList = new HashMap<User, Black>();

	private final Annu annu;

	/**
	 * Liste des contacts
	 */
	private final List<Contact> uAllContacts;

	/**
	 * Liste des contacts
	 */
	private final List<Lang> uTTS_Lang;

	public UserMock(long inId, String inLogin, String inPassword, String inEmail, Lang inLang, String inCountry, String inFirstName, String inLastName, Timezone inTimezone, String inSexe, String inCp, String inCity, int inConfirm, int newsLetter) {
		super(inId);
		this.login = inLogin;
		this.password = inPassword;
		this.email = inEmail;
		this.mLang = inLang;
		this.country = inCountry;
		this.firstName = inFirstName;
		this.lastName = inLastName;
		this.mTimeZone = inTimezone;
		this.mIsGood = 0;
		this.is24 = true;
		this.creationDate = Calendar.getInstance().getTimeInMillis();
		this.user_newsletter = newsLetter;
		this.mNotifyMessagePlayed = true;
		this.mNotifyMessageReceived = true;
		this.mImage = 1;
		this.user_main = 0;

		this.annu = new AnnuMock(this, inSexe, inCp, inCity, inConfirm, new Date(Calendar.getInstance().getTimeInMillis()), this.country, inLang);
		this.uAllContacts = new ArrayList<Contact>();
		this.uTTS_Lang = new ArrayList<Lang>();
	}

	public UserMock(long inId, String inLogin, String inPassword, String inEmail, Lang inLang, List<Lang> inSpokenLanguages, String inCountry, String inFirstName, String inLastName, Timezone inTimezone, String inSexe, String inCp, String inCity, int inConfirm, int newsLetter) {
		super(inId);
		this.login = inLogin;
		this.password = inPassword;
		this.email = inEmail;
		this.mLang = inLang;
		this.country = inCountry;
		this.firstName = inFirstName;
		this.lastName = inLastName;
		this.mTimeZone = inTimezone;
		this.mIsGood = 0;
		this.is24 = true;
		this.creationDate = Calendar.getInstance().getTimeInMillis();
		this.user_newsletter = newsLetter;
		this.mNotifyMessagePlayed = true;
		this.mNotifyMessageReceived = true;
		this.mImage = 1;
		this.user_main = 0;

		this.annu = new AnnuMock(this, inSexe, inCp, inCity, inConfirm, new Date(Calendar.getInstance().getTimeInMillis()), this.country, inLang);

		this.uAllContacts = new ArrayList<Contact>();
		this.uTTS_Lang = inSpokenLanguages;
	}

	public UserMock(long inId, String inLogin, String inPassword, String inEmail, Lang inLang, String inCountry, String inFirstName, String inLastName, Timezone inTimezone, String inSexe, String inCp, String inCity, int inConfirm) {
		this(inId, inLogin, inPassword, inEmail, inLang, inCountry, inFirstName, inLastName, inTimezone, inSexe, inCp, inCity, inConfirm, 0);
	}

	public UserMock(long inId, String inLogin, String inPassword, String inEmail, Lang inLang, String inCountry, String inFirstName, String inLastName, Timezone inTimezone) {
		this(inId, inLogin, inPassword, inEmail, inLang, inCountry, inFirstName, inLastName, inTimezone, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, 0);
	}

	public void addCreatedNabcast(Nabcast newNabcast) {
		throw new UnsupportedOperationException();
	}

	public void addFriend(User friend) {
		throw new UnsupportedOperationException();
	}

	public boolean existFriend(User friend) {
		throw new UnsupportedOperationException();
	}

	public int existInBlackliste(User friend) {
		return 0;
	}

	public Annu getAnnu() {
		return this.annu;
	}

	public Map<User, Black> getBlackList() {
		return this.blackList;
	}

	public Anim getColor() {
		return Factories.ANIM.find(this.user_color);
	}

	public String getColorSign() {
		return this.user_color_sign;
	}

	public List<Nabcast> getCreatedNabcasts() {
		return Collections.emptyList();
	}

	public long getCreationDate() {
		return this.creationDate;
	}

	public List<User> getFriendBlackList(int type, int msg) {
		throw new UnsupportedOperationException();
	}

	public int getHourMod() {
		throw new UnsupportedOperationException();
	}

	public Lang getLang() {
		return this.mLang;
	}

	public List<Lang> getLangs() {
		return this.uTTS_Lang;
	}

	public Music getMusic() {
		return Factories.MUSIC.find(this.user_music);
	}

	public String getPassword() {
		return this.password;
	}

	public long get_user_music() {
		return this.user_music;
	}

	public String get_user_color_sign() {
		return this.user_color_sign;
	}

	public long get_user_color() {
		return this.user_color;
	}

	public boolean checkPasswordPlain(String inPassword) {
		return inPassword.equals(this.password);
	}

	public boolean checkPasswordMD5Sum(String inPassword) {
		return inPassword.equals(DigestTools.digest(this.password, DigestTools.Algorithm.MD5));
	}

	public String getSignature(boolean reload) {
		return getColor().getAnim_signature();
	}

	public Timezone getTimezone() {
		return this.mTimeZone;
	}

	public UserPrefs getUPreferences() {
		return null;
	}

	public int getUser_24h() {
		if (this.is24) {
			return 1;
		}

		return 0;
	}

	public long getUser_authmsg() {
		return this.user_authmsg;
	}

	public boolean getNotifyMessagePlayed() {
		return this.mNotifyMessagePlayed;
	}

	public boolean getNotifyMessageReceived() {
		return this.mNotifyMessageReceived;
	}

	public String getUser_comment() {
		return this.mComment;
	}

	public String getUser_email() {
		return this.email;
	}

	public long getUser_extconnect() {
		return this.mExtConnect;
	}

	public String getUser_firstName() {
		return this.firstName;
	}

	public String getUserFullName() {
		return this.firstName + " " + this.lastName;
	}

	public long getUser_good() {
		return this.mIsGood;
	}

	public long getUser_image() {
		return this.mImage;
	}

	public String getUser_lastName() {
		return this.lastName;
	}

	public long getUser_mainTmp() {
		return this.getId();
	}

	public Integer getUser_newsletter() {
		return this.user_newsletter;
	}

	public long getUser_picture() {
		throw new UnsupportedOperationException();
	}

	public String getUser_pseudo() {
		return this.login;
	}

	public void setComment(String comment) {
		this.mComment = comment;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setExtConnect(long extConnect) {
		this.mExtConnect = extConnect;
	}

	public void setImage(int image) {
		throw new UnsupportedOperationException();
	}

	public void setIsGood(int isGood) {
		this.mIsGood = isGood;
	}

	public void setLang(Lang inLang) {
		this.mLang = inLang;
	}

	public void setHasObject() {
		this.user_main = 1;
	}

	public void setMessageParameters(int authmsg, int authmsgrcv, int authmsgrcvme, int newsletter) {
		this.user_authmsg = authmsg;
		this.mNotifyMessagePlayed = authmsgrcv != 0;
		this.mNotifyMessageReceived = authmsgrcvme != 0;
		this.user_newsletter = newsletter;
	}

	public void setNotifyMessageReceived(boolean inNotifyMessageReceived) {
		this.mNotifyMessageReceived = inNotifyMessageReceived;
	}

	public void setPassword(String inValue) {
		this.password = inValue;
	}

	public void setPseudo(String pseudo) {
		this.login = pseudo;
	}

	public void setTimeInformation(int is24, Timezone timezone) {
		this.mTimeZone = timezone;
	}

	public void updateUserSpokenLanguages(List<Lang> delete, List<Lang> add, List<List<Lang>> update) {
		this.uTTS_Lang.removeAll(delete);

		if (!update.isEmpty()) {
			for (final List<Lang> langToUpdate : update) {
				final Lang langOld = langToUpdate.get(0);
				final Lang langNew = langToUpdate.get(1);
				this.uTTS_Lang.set(this.uTTS_Lang.indexOf(langOld), langNew);
			}
		}

		this.uTTS_Lang.addAll(add);
	}

	public boolean use24() {
		return this.is24;
	}

	public Map<User, UserEmail> getAlternateEmails() {
		return this.alternateEmails;
	}

	public void setPreferences(Timezone inTimezone, int inTimeFormat, boolean inIsNewsLetter, boolean inNotifyMessagePlayed) {
		this.mTimeZone = inTimezone;
		this.is24 = (inTimeFormat == 1) ? true : false;
		this.user_newsletter = inIsNewsLetter ? 1 : 0;
		this.mNotifyMessagePlayed = inNotifyMessagePlayed;
	}

	public void addContact(Contact inContact) {
		this.uAllContacts.add(inContact);
	}

	public List<Contact> getAllContacts() {
		return this.uAllContacts;
	}

	public List<Lang> getSpokenLanguges() {
		return this.uTTS_Lang;
	}

	public void removeContact(Contact inContact) {
		this.uAllContacts.remove(inContact);
	}

	public List<User> getAllContactsWithObject() {
		final List<User> theResult = new ArrayList<User>();
		for (final Contact thecontact : getAllContacts()) {
			final User theFriend = thecontact.getContact();
			if (theFriend.getUserHasObject()) {
				theResult.add(theFriend);
			}
		}
		return theResult;
	}

	public List<User> getFriends() {
		final List<User> theResult = new ArrayList<User>();
		for (final Contact thecontact : getAllContacts()) {
			theResult.add(thecontact.getContact());
		}
		return theResult;
	}

	public List<User> getFriendsWithObject() {
		final List<User> theResult = new ArrayList<User>();
		for (final Contact thecontact : getAllContacts()) {
			final User theFriend = thecontact.getContact();
			if (theFriend.getUserHasObject()) {
				theResult.add(theFriend);
			}
		}
		return theResult;
	}

	public boolean getUserHasObject() {
		return this.user_main > 0;
	}

	public void removeAlternateTTSLang(Lang reference) {
		this.uTTS_Lang.remove(reference);
	}

	public void setTTSLang(Lang reference) {
		this.uTTS_Lang.add(reference);
	}

	/*
	 * public ColorType getColorType(){ if( user_color_sign != null) { return
	 * ColorType.getColorByRGB(user_color_sign); } return null; }
	 */
	public String getUserColorSign() {
		return this.user_color_sign;
	}

	public void setSignatureInformation(long inAnimId, String colorSign, long inMusicId) {
		this.user_color_sign = colorSign;
		this.user_color = inAnimId;
		this.user_music = inMusicId;
		//
	}

	public void setUserWithoutObject() {
		// TODO Auto-generated method stub

	}
}

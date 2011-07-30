package net.violet.platform.datamodel;

import java.awt.Color;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.violet.common.utils.DigestTools;
import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.DecoratedAssociation;
import net.violet.db.records.associations.ListAssociation;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.db.records.associations.SingleAssociationNull;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.util.DBGenerator;
import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.CipherTools;
import net.violet.platform.util.ConvertTools;
import net.violet.platform.util.StringShop;
import net.violet.platform.util.StringTools;

import org.apache.log4j.Logger;

public final class UserImpl extends ObjectRecord<User, UserImpl> implements User {

	private static final Logger LOGGER = Logger.getLogger(UserImpl.class);

	public static final SQLObjectSpecification<UserImpl> SPECIFICATION = new SQLObjectSpecification<UserImpl>("user", UserImpl.class, new SQLKey("user_id"));

	private static final String[] NEW_COLUMNS = new String[] { "user_password", "user_email", "user_lang", "user_firstName", "user_lastName", "user_timezone", "user_main", "user_creationDate", "user_identnumnab", "user_extconnect", "user_newsletter" };

	protected long user_id;
	protected String user_password;
	
	/**
	 * This field wasn't the the class, but was in the DB
	 * so it was created for the {@link DBGenerator} to create
	 * the corresponding column.
	 * 
	 * @author nabaztag-server-project
	 */
	protected String user_pseudo;
	
	protected String user_email;
	protected long user_lang;
	protected long user_main; // il sert desormais a dire si l'utilisateur a au moins un objet
	protected long user_color;
	protected long user_music;
	protected long user_authmsg;
	protected long user_authmsgrcv;
	protected String user_identnumnab;
	protected long user_image;
	protected long user_authmsgrcvme;
	protected long user_extconnect;
	protected long user_creationDate;
	protected long user_good;
	protected String user_comment;
	protected String user_color_sign;

	/**
	 * This field wasn't the the class, but was in the DB
	 * so it was created for the {@link DBGenerator} to create
	 * the corresponding column.
	 * 
	 * @author nabaztag-server-project
	 */
	protected Date user_show_date;

	protected Integer user_newsletter;
	protected long user_timezone;
	protected int user_24h;
	protected String user_firstName;
	protected String user_lastName;

	/**
	 * Liste des langues de cet utilisateur.
	 */
	private List<Lang> mLangs;

	private final SingleAssociationNotNull<User, Timezone, TimezoneImpl> timeZone;

	/**
	 * Langue principale de cet utilisateur.
	 */
	private final SingleAssociationNull<User, Lang, LangImpl> mLang;

	/**
	 * Layout de l'utilisateur
	 */
	private final SingleAssociationNull<User, UserPrefs, UserPrefsImpl> uPreferences;

	/**
	 * Liste des blacklistés par le user
	 */
	private Map<User, Black> uBlackList;

	/**
	 * Musique pour la signature.
	 */
	private final SingleAssociationNull<User, Music, MusicImpl> mMusic;

	/**
	 * Animation pour la signature.
	 */
	private final SingleAssociationNull<User, Anim, AnimImpl> mColor;

	/**
	 * Enregistrement dans l'annuaire pour cet utilisateur
	 */
	private final SingleAssociationNull<User, Annu, AnnuImpl> mAnnu;

	/**
	 * Signature de l'utilisateur.
	 */
	private String mSignature;

	/**
	 * Liste des nabcast crées par le user
	 */
	private List<Nabcast> uCreatedNabcasts;

	/**
	 * Liste des contacts
	 */
	private List<Contact> uAllContacts;

	/**
	 * On liste ce nombre d'ami coté site en attendant de passé sur la nouvelle
	 * api
	 */
	public static final int MAX_CONTACT = 1000;

	/**
	 * Constructeur par défaut (enregistrement existant).
	 */
	public UserImpl() {
		// This space for rent.
		this.mLang = new SingleAssociationNull<User, Lang, LangImpl>(this, "user_lang", LangImpl.SPECIFICATION, LangImpl.SPECIFICATION.getPrimaryKey());
		this.uPreferences = new SingleAssociationNull<User, UserPrefs, UserPrefsImpl>(this, "user_id", UserPrefsImpl.SPECIFICATION, UserPrefsImpl.SPECIFICATION.getPrimaryKey());
		this.mMusic = new SingleAssociationNull<User, Music, MusicImpl>(this, "user_music", MusicImpl.SPECIFICATION, MusicImpl.SPECIFICATION.getPrimaryKey());
		this.mColor = new SingleAssociationNull<User, Anim, AnimImpl>(this, "user_color", AnimImpl.SPECIFICATION, AnimImpl.SPECIFICATION.getPrimaryKey());
		this.mAnnu = new SingleAssociationNull<User, Annu, AnnuImpl>(this, "user_id", AnnuImpl.SPECIFICATION, AnnuImpl.SPECIFICATION.getPrimaryKey());
		this.timeZone = new SingleAssociationNotNull<User, Timezone, TimezoneImpl>(this, "user_timezone", TimezoneImpl.SPECIFICATION);
	}

	/**
	 * Constructeur à partir d'un id (enregistrement existant).
	 */
	public UserImpl(long inId) throws SQLException {
		init(inId);
		this.mLang = new SingleAssociationNull<User, Lang, LangImpl>(this, "user_lang", LangImpl.SPECIFICATION, LangImpl.SPECIFICATION.getPrimaryKey());
		this.uPreferences = new SingleAssociationNull<User, UserPrefs, UserPrefsImpl>(this, "user_id", UserPrefsImpl.SPECIFICATION, UserPrefsImpl.SPECIFICATION.getPrimaryKey());
		this.mMusic = new SingleAssociationNull<User, Music, MusicImpl>(this, "user_music", MusicImpl.SPECIFICATION, MusicImpl.SPECIFICATION.getPrimaryKey());
		this.mColor = new SingleAssociationNull<User, Anim, AnimImpl>(this, "user_color", AnimImpl.SPECIFICATION, AnimImpl.SPECIFICATION.getPrimaryKey());
		this.mAnnu = new SingleAssociationNull<User, Annu, AnnuImpl>(this, "user_id", AnnuImpl.SPECIFICATION, AnnuImpl.SPECIFICATION.getPrimaryKey());
		this.timeZone = new SingleAssociationNotNull<User, Timezone, TimezoneImpl>(this, "user_timezone", TimezoneImpl.SPECIFICATION);
	}

	/**
	 * Constructeur à partir de valeurs (nouvel utilisateur).
	 */
	public UserImpl(String inPassword, String inEmail, Lang inLang, String inCountry, String inFirstName, String inLastName, long inTimeZone) throws SQLException {
		// TODO: gestion des transactions pour supprimer l'utilisateur si
		// l'ajout
		// de l'association échoue (par exemple).

		this.user_password = CipherTools.cipher(inPassword);
		this.user_email = inEmail;
		this.user_lang = ObjectLangData.getDefaultObjectLanguage(inLang.getIsoCode()).getId();
		this.user_firstName = StringTools.truncate(inFirstName, 45);
		this.user_lastName = StringTools.truncate(inLastName, 45);
		this.user_timezone = inTimeZone;
		this.user_newsletter = 1;
		this.user_main = 0;
		this.user_creationDate = CCalendar.getCurrentTimeInSecond();
		this.user_identnumnab = net.violet.common.StringShop.EMPTY_STRING;
		this.user_extconnect = CCalendar.getCurrentTimeInSecond() + ConvertTools.randCodeInt();

		init(UserImpl.NEW_COLUMNS);

		// Création de l'entrée dans l'annuaire.
		new AnnuImpl(this, inCountry, inLang);

		this.mLang = new SingleAssociationNull<User, Lang, LangImpl>(this, "user_lang", LangImpl.SPECIFICATION, LangImpl.SPECIFICATION.getPrimaryKey());
		this.timeZone = new SingleAssociationNotNull<User, Timezone, TimezoneImpl>(this, "user_timezone", TimezoneImpl.SPECIFICATION);
		this.uPreferences = new SingleAssociationNull<User, UserPrefs, UserPrefsImpl>(this, "user_id", UserPrefsImpl.SPECIFICATION, UserPrefsImpl.SPECIFICATION.getPrimaryKey());
		this.mMusic = new SingleAssociationNull<User, Music, MusicImpl>(this, "user_music", MusicImpl.SPECIFICATION, MusicImpl.SPECIFICATION.getPrimaryKey());
		this.mColor = new SingleAssociationNull<User, Anim, AnimImpl>(this, "user_color", AnimImpl.SPECIFICATION, AnimImpl.SPECIFICATION.getPrimaryKey());
		this.mAnnu = new SingleAssociationNull<User, Annu, AnnuImpl>(this, "user_id", AnnuImpl.SPECIFICATION, AnnuImpl.SPECIFICATION.getPrimaryKey());

	}

	public boolean use24() {
		return (this.getUser_24h() == 1);
	}

	@Override
	public Long getId() {
		return this.user_id;
	}

	@Override
	public SQLObjectSpecification<UserImpl> getSpecification() {
		return UserImpl.SPECIFICATION;
	}

	// / ACCESSEURS & SELECTEURS
	public String getUser_firstName() {
		return this.user_firstName;
	}

	public String getUser_lastName() {
		return this.user_lastName;
	}

	public String getUserFullName() {
		return this.user_lastName + " " + this.user_firstName;
	}

	public int getUser_24h() {
		return this.user_24h;
	}

	public Timezone getTimezone() {
		return this.timeZone.get(this.user_timezone);
	}

	public void setTimeInformation(int is24, Timezone timezone) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setUser_24h(theUpdateMap, is24);
		setTimezone(theUpdateMap, timezone);
		update(theUpdateMap);
	}

	public void setMessageParameters(int authmsg, int inNotifyPlayed, int inNotifyReceived, int newsletter) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setUser_authmsg(theUpdateMap, authmsg);
		setUser_authmsgrcv(theUpdateMap, inNotifyPlayed);
		setUser_authmsgrcvme(theUpdateMap, inNotifyReceived);
		setUser_newsletter(theUpdateMap, newsletter);
		update(theUpdateMap);
	}

	public void setNotifyMessageReceived(boolean inNewValue) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		if (inNewValue) {
			setUser_authmsgrcvme(theUpdateMap, 1);
		} else {
			setUser_authmsgrcvme(theUpdateMap, 0);
		}
		update(theUpdateMap);
	}

	public void setSignatureInformation(long inAnimId, String colorSign, long inMusicId) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setColor(theUpdateMap, AnimImpl.find(inAnimId));
		setUser_color_sign(theUpdateMap, colorSign);
		setMusic(theUpdateMap, Factories.MUSIC.find(inMusicId));
		update(theUpdateMap);
	}

	public void setComment(String comment) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setUser_comment(theUpdateMap, comment);
		update(theUpdateMap);
	}

	public void setEmail(String email) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setUser_email(theUpdateMap, email);
		update(theUpdateMap);
	}

	public void setExtConnect(long extConnect) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setUser_extconnect(theUpdateMap, extConnect);
		update(theUpdateMap);
	}

	public void setIsGood(int isGood) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setUser_good(theUpdateMap, isGood);
		update(theUpdateMap);
	}

	public void setImage(int image) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setUser_image(theUpdateMap, image);
		update(theUpdateMap);
	}

	public void setLang(Lang inLang) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setUser_lang(theUpdateMap, inLang);
		update(theUpdateMap);
	}

	public void setHasObject() {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setUser_hasObject(theUpdateMap);
		update(theUpdateMap);
	}

	public void setPassword(String inValue) {
		final String thePasswordCipher = CipherTools.cipher(inValue);
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setUser_password(theUpdateMap, thePasswordCipher);
		update(theUpdateMap);
	}

	public long getUser_authmsg() {
		return this.user_authmsg;
	}

	public boolean getNotifyMessagePlayed() {
		return this.user_authmsgrcv != 0;
	}

	public boolean getNotifyMessageReceived() {
		return this.user_authmsgrcvme != 0;
	}

	public Integer getUser_newsletter() {
		if (this.user_newsletter == null) {
			this.user_newsletter = 0;
		}
		return this.user_newsletter;
	}

	public Anim getColor() {
		return this.mColor.get(this.user_color);
	}

	public String getColorSign() {
		return this.user_color_sign;
	}

	public Music getMusic() {
		return this.mMusic.get(this.user_music);
	}

	public String getUser_comment() {
		return this.user_comment;
	}

	public long getCreationDate() {
		return this.user_creationDate;
	}

	public String getUser_email() {
		return this.user_email;
	}

	public long getUser_extconnect() {
		return this.user_extconnect;
	}

	public long getUser_good() {
		return this.user_good;
	}

	public long getUser_image() {
		return this.user_image;
	}

	public Lang getLang() {
		return this.mLang.get(this.user_lang);
	}

	public List<Lang> getLangs() {
		if (this.mLangs == null) {
			try {
				this.mLangs = ListAssociation.createListAssociation(this, LangImpl.SPECIFICATION, "userLang", "user_id", StringShop.LANG_ID);
			} catch (final SQLException anException) {
				UserImpl.LOGGER.fatal(anException, anException);
			}
		}
		return this.mLangs;
	}

	public String getPassword() {
		return CipherTools.uncipher(this.user_password);
	}

	public boolean checkPasswordPlain(String inPassword) {
		return CipherTools.cipher(inPassword).equals(this.user_password);
	}

	public boolean checkPasswordMD5Sum(String inPassword) {
		return inPassword.equals(DigestTools.digest(CipherTools.uncipher(this.user_password), DigestTools.Algorithm.MD5));
	}

	public UserPrefs getUPreferences() {
		return this.uPreferences.get(this.user_id);
	}

	public Map<User, Black> getBlackList() {
		if (this.uBlackList == null) {
			try {
				this.uBlackList = DecoratedAssociation.createDecoratedAssociation(this, UserImpl.SPECIFICATION, "black_blacked", BlackImpl.SPECIFICATION, "black_user");
			} catch (final SQLException e) {
				UserImpl.LOGGER.fatal(e, e);
			}
		}
		return this.uBlackList;
	}

	public void removeAlternateTTSLang(Lang reference) {
		if (reference != null) {
			this.mLangs = getLangs();
			this.mLangs.remove(reference);
		}
	}

	public List<User> getFriends() {
		final List<User> theFriendsList = new ArrayList<User>();
		for (final Contact theContact : getAllContacts()) {
			theFriendsList.add(theContact.getContact());
		}
		return theFriendsList;
	}

	public boolean existFriend(User friend) {
		return getFriends().contains(friend);
	}

	public int existInBlackliste(User friend) {
		final Map<User, Black> userFriends = getBlackList();
		if (userFriends.containsKey(friend)) {
			return 1;
		}
		return 0;
	}

	public void updateUserSpokenLanguages(List<Lang> delete, List<Lang> add, List<List<Lang>> update) {

		if (!delete.isEmpty()) {
			for (final Lang lang : delete) {
				getLangs().remove(lang);
			}
		}

		if (!update.isEmpty()) {
			for (final List<Lang> langToUpdate : update) {
				final Lang langOld = langToUpdate.get(0);
				final Lang langNew = langToUpdate.get(1);
				getLangs().set(getLangs().indexOf(langOld), langNew);
			}
		}

		if (!add.isEmpty()) {
			for (final Lang lang : add) {
				getLangs().add(lang);
			}
		}
	}

	public Annu getAnnu() {
		return this.mAnnu.get(this.user_id);
	}

	/**
	 * Retourne la liste des derniers inscrits (selon le nombre) dans les
	 * profils utilisateurs ayant un lapin
	 * 
	 * @param nombre de profils
	 * @param boolean si vrai, retourne seulement les Lapins possedants une
	 *        image
	 * @param boolean si vrai, retourne seulement si l'utilisateur a renseigné
	 *        sa description
	 * @return la liste des user
	 */
	public static List<User> recuperationDesDerniersInscrits(int nombre, boolean bImage, boolean bTexte) {
		final List<User> list = new ArrayList<User>();
		final String[] inJoinTables = new String[] { "annu" };
		String condition = "user_main>? and annu_user=user_id and annu_confirm=? ";
		final List<Object> values = new ArrayList<Object>();
		values.add(0);
		values.add(1);
		if (bImage) {
			condition += " and user_image>? and user_good<>? ";
			values.add(0);
			values.add(0);
		}
		if (bTexte) {
			condition += " and u.user_comment IS NOT NULL";
		}
		final String order = " user_show_date desc";
		final String limit = " limit 0," + nombre + net.violet.common.StringShop.SPACE;
		try {
			list.addAll(AbstractSQLRecord.findAll(UserImpl.SPECIFICATION, inJoinTables, condition, values, order + limit));
		} catch (final SQLException e) {
			UserImpl.LOGGER.fatal(e, e);
		}
		return list;
	}

	/**
	 * Retourne la liste des derniers inscrits (selon le nombre) dans les
	 * profils utilisateurs ayant un lapin
	 * 
	 * @param nombre de profils
	 * @param langue principale de l'utilisateur
	 * @param boolean si vrai, retourne seulement les Lapins possedants une
	 *        image
	 * @return la liste des user
	 */
	public static List<User> recuperationDesDerniersInscrits(int nombre, long langid, boolean bImage) {
		final List<User> list = new ArrayList<User>();
		final String[] inJoinTables = new String[] { "annu" };
		String condition = "user_main>? and annu_user=user_id and annu_confirm=? and user.user_lang=?";
		final List<Object> values = new ArrayList<Object>();
		values.add(0);
		values.add(langid);
		if (bImage) {
			condition += " and user_image>? and user_good<>? ";
			values.add(0);
			values.add(0);
			values.add(1);
		}
		final String order = " user_show_date desc";
		final String limit = " limit 0," + nombre + net.violet.common.StringShop.SPACE;
		try {
			list.addAll(AbstractSQLRecord.findAll(UserImpl.SPECIFICATION, inJoinTables, condition, values, order + limit));
		} catch (final SQLException e) {
			UserImpl.LOGGER.fatal(e, e);
		}
		return list;
	}

	public String getSignature(boolean reload) {
		if ((this.mSignature == null) || reload) {
			this.mSignature = computeSignature();
		}
		return this.mSignature;
	}

	/**
	 * Retourne le nomnre d'utilisateurs souhaitant apparatre dans l'annuaire et
	 * qui ont un lapin
	 * 
	 * @param String pseudo : Si diffrent de StringShop.EMPTY_STRING, permet de
	 *            filtrer en fonction du pseudo
	 * @param int agemin : Si diffrent de -1, permet de filtrer en fonction d'un
	 *        age minimal
	 * @param int agemax : Si diffrent de -1, permet de filtrer en fonction
	 *        d'une age maximal
	 * @param String sexe : Si diffrent de StringShop.EMPTY_STRING, permet de
	 *            filtrer en fonction du sexe
	 * @param String ville : Si diffrent de StringShop.EMPTY_STRING, permet de
	 *            filtrer en fonction de la ville
	 * @param String pays : Si diffrent de StringShop.EMPTY_STRING, permet de
	 *            filtrer en fonction du pays
	 * @param int index : Si diffrent de -1, retourne seulement les 10 premiers
	 *        rsultats partir de l'index
	 * @param String lettre_deb : Si diffrent de StringShop.EMPTY_STRING, permet
	 *            de filtrer sur la premiere lettre d'un mot (ou par les
	 *            premieres lettres)
	 * @return le nomnre d'utilisateurs souhaitant apparatre dans l'annuaire et
	 *         qui ont un lapin
	 * @see ProfilData.
	 * @author Brice Lantenois
	 */
	public static List<User> searchUsers(int tri, String inTypeTri, String pseudo, int agemin, int agemax, String sexe, String ville, String pays) {
		final List<Object> values = new ArrayList<Object>();
		final List<User> list = new ArrayList<User>();
		final String[] inJoinTables = new String[] { "annu" };
		final StringBuilder where = new StringBuilder(" annu.annu_user=user.user_id and annu.annu_confirm = ? and user.user_main > ?");

		String typeTri = inTypeTri;
		String date_min = net.violet.common.StringShop.EMPTY_STRING;
		String date_max = net.violet.common.StringShop.EMPTY_STRING;

		values.add(1);
		values.add(0);

		if (!pseudo.trim().equals(net.violet.common.StringShop.EMPTY_STRING)) {
			where.append(" and user.user_pseudo like ?"); // '%"+pseudo+"%'
			values.add(pseudo + "%");
		}
		if (!sexe.trim().equals(net.violet.common.StringShop.EMPTY_STRING)) {
			where.append(" and annu.annu_sexe = ?");
			values.add(sexe);
		}
		if (!ville.trim().equals(net.violet.common.StringShop.EMPTY_STRING)) {
			where.append(" and annu.annu_city = ?"); // '%"+ville+"%'
			values.add(ville);
		}
		if (!pays.trim().equals(net.violet.common.StringShop.EMPTY_STRING)) {
			where.append(" and annu.annu_country = ?"); // '%"+pays+"%'
			values.add(pays);
		}

		if (agemin > -1) {
			final CCalendar myCalendar = new CCalendar(false);
			myCalendar.roll(Calendar.YEAR, -agemin);
			date_min = myCalendar.getDateOfBirthFormatted();
		}

		if (agemax > -1) {
			final CCalendar myCalendar = new CCalendar(false);
			myCalendar.roll(Calendar.YEAR, -agemax);
			date_max = myCalendar.getDateOfBirthFormatted();
		}

		if ((agemin > -1) && (agemax > -1)) {
			where.append(" and annu.annu_datebirth between ? and ?");
			values.add(date_max);
			values.add(date_min);
		} else if (agemin > -1) {
			where.append(" and annu.annu_datebirth < ?");
			values.add(date_min);
		} else if (agemax > -1) {
			where.append(" and annu.annu_datebirth >= ?");
			values.add(date_max);
		}

		final StringBuilder order_by = new StringBuilder();
		switch (tri) {
		case 1:
			order_by.append("user.user_pseudo");
			break;
		case 2:
			order_by.append("annu.annu_datebirth");
			break;
		case 3:
			order_by.append("annu.annu_sexe");
			break;
		case 4:
			order_by.append("annu.annu_city");
			break;
		case 5:
			order_by.append("annu.annu_country");
			break;
		default:
			order_by.append("user.user_id");
			break;
		}

		if ((typeTri == null) || typeTri.equals(net.violet.common.StringShop.EMPTY_STRING)) {
			typeTri = StringShop.ASC;
		}

		order_by.append(net.violet.common.StringShop.SPACE).append(typeTri);

		try {
			list.addAll(AbstractSQLRecord.findAll(UserImpl.SPECIFICATION, inJoinTables, where.toString(), values, order_by.toString()));
		} catch (final SQLException e) {
			UserImpl.LOGGER.fatal(e, e);
		}

		return list;
	}

	public int getHourMod() {
		if (this.user_24h == 1) {
			return 24;
		}

		return 12;
	}

	public List<User> getFriendBlackList(int type, int msg) {
		final List<User> list = new LinkedList<User>();
		Set<User> resultSet = new HashSet<User>();

		if (type == 0) {
			// FriendsImpl
			for (final Contact theContact : getAllContacts()) {
				resultSet.add(theContact.getContact());
			}
		} else {
			// Blacklist
			resultSet = getBlackList().keySet();
		}
		for (final User user : resultSet) {
			if (msg == 1) {
				if (!Factories.VOBJECT.findByOwner(user).isEmpty()) {
					list.add(user);
				}
			} else {
				list.add(user);
			}
		}
		return list;
	}

	/**
	 * Calcule la signature de l'utilisateur.
	 */
	private String computeSignature() {
		String theResult;
		final Map<String, String> replacements = new HashMap<String, String>();
		final Anim theAnim = getColor();
		if (theAnim != null) {
			theResult = theAnim.getAnim_signature();

			final Color user_sign_color = Color.decode("#" + getColorSign());

			replacements.put("BBB", String.valueOf(user_sign_color.getBlue()));
			replacements.put("VVV", String.valueOf(user_sign_color.getGreen()));
			replacements.put("RRR", String.valueOf(user_sign_color.getRed()));
		} else {
			theResult = "long,3,none,nomusic.mid";
		}
		final Music theMusic = getMusic();
		if ((theMusic != null) && (theMusic.getFile() != null)) {
			replacements.put("nomusic.mid", theMusic.getFile().getPath().replace(Files.CATEGORIES.BROADCAST.getCategory(), net.violet.common.StringShop.EMPTY_STRING));
		}

		for (final Map.Entry<String, String> theReplacement : replacements.entrySet()) {
			theResult = theResult.replace(theReplacement.getKey(), theReplacement.getValue());
		}

		return theResult;
	}

	/**
	 * Liste des contacts
	 */
	@Deprecated
	public List<Contact> getAllContacts() {
		if (this.uAllContacts == null) {
			this.uAllContacts = Factories.CONTACT.findAllContactByUser(this, 0, UserImpl.MAX_CONTACT);
		}

		return this.uAllContacts;
	}

	/**
	 * Ajoute un nouveau contact
	 * 
	 * @param inNewContact
	 */
	@Deprecated
	public void addContact(Contact inNewContact) {
		getAllContacts().add(inNewContact);
	}

	/**
	 * retire un contact
	 * 
	 * @param inContact
	 */
	@Deprecated
	public void removeContact(Contact inContact) {
		getAllContacts().remove(inContact);
	}

	@Deprecated
	public List<Nabcast> getCreatedNabcasts() {
		if (this.uCreatedNabcasts == null) {
			this.uCreatedNabcasts = NabcastImpl.findAllByAuthor(this);
		}

		return this.uCreatedNabcasts;
	}

	@Deprecated
	public void addCreatedNabcast(Nabcast newNabcast) {
		getCreatedNabcasts().add(newNabcast);
	}

	private void setUser_24h(Map<String, Object> inUpdateMap, int inValue) {
		if (inValue != this.user_24h) {
			this.user_24h = inValue;
			inUpdateMap.put("user_24h", inValue);
		}
	}

	private void setTimezone(Map<String, Object> inUpdateMap, Timezone inValue) {
		if ((inValue != null) && !inValue.getId().equals(this.user_timezone)) {
			this.user_timezone = inValue.getId();
			inUpdateMap.put("user_timezone", this.user_timezone);
			this.timeZone.set(inValue);
		}
	}

	private void setUser_authmsgrcvme(Map<String, Object> inUpdateMap, int inValue) {
		if (inValue != this.user_authmsgrcvme) {
			this.user_authmsgrcvme = inValue;
			inUpdateMap.put("user_authmsgrcvme", inValue);
		}
	}

	private void setUser_newsletter(Map<String, Object> inUpdateMap, Integer inValue) {
		if (inValue != this.user_newsletter) {
			this.user_newsletter = inValue;
			inUpdateMap.put("user_newsletter", inValue);
		}
	}

	private void setUser_authmsgrcv(Map<String, Object> inUpdateMap, int inValue) {
		if (inValue != this.user_authmsgrcv) {
			this.user_authmsgrcv = inValue;
			inUpdateMap.put("user_authmsgrcv", inValue);
		}
	}

	private void setUser_authmsg(Map<String, Object> inUpdateMap, int inValue) {
		if (inValue != this.user_authmsg) {
			this.user_authmsg = inValue;
			inUpdateMap.put("user_authmsg", inValue);
		}
	}

	private void setColor(Map<String, Object> inUpdateMap, Anim inValue) {
		if ((inValue == null) || (inValue.getId() != this.user_color)) {
			if (inValue == null) {
				this.user_color = 1;
			} else {
				this.user_color = inValue.getId();
			}
			this.mColor.set(inValue);
			inUpdateMap.put("user_color", this.user_color);
		}
	}

	private void setUser_color_sign(Map<String, Object> inUpdateMap, String inValue) {
		if (!inValue.equals(this.user_color_sign)) {
			this.user_color_sign = inValue;
			inUpdateMap.put("user_color_sign", inValue);
		}
	}

	private void setMusic(Map<String, Object> inUpdateMap, Music inValue) {
		if ((inValue == null) || (inValue.getId() != this.user_music)) {
			if (inValue == null) {
				this.user_music = 0;
			} else {
				this.user_music = inValue.getId();
			}
			this.mMusic.set(inValue);
			inUpdateMap.put("user_music", this.user_music);
		}
	}

	private void setUser_comment(Map<String, Object> inUpdateMap, String inValue) {
		final String theComment = StringTools.truncate(inValue, 600);
		if (!theComment.equals(this.user_comment)) {
			this.user_comment = theComment;
			inUpdateMap.put("user_comment", inValue);
		}
	}

	private void setUser_email(Map<String, Object> inUpdateMap, String inValue) {
		if (!inValue.equals(this.user_email)) {
			this.user_email = inValue;
			inUpdateMap.put("user_email", inValue);
		}
	}

	private void setUser_extconnect(Map<String, Object> inUpdateMap, long inValue) {
		if (inValue != this.user_extconnect) {
			this.user_extconnect = inValue;
			inUpdateMap.put("user_extconnect", inValue);
		}
	}

	private void setUser_good(Map<String, Object> inUpdateMap, int inValue) {
		if (inValue != this.user_good) {
			this.user_good = inValue;
			inUpdateMap.put("user_good", inValue);
		}
	}

	private void setUser_image(Map<String, Object> inUpdateMap, int inValue) {
		if (inValue != this.user_image) {
			this.user_image = inValue;
			inUpdateMap.put("user_image", inValue);
		}
	}

	private void setUser_lang(Map<String, Object> inUpdateMap, Lang inLang) {
		if (this.user_lang != inLang.getId()) {
			this.user_lang = inLang.getId();
			this.mLang.set(inLang);
			inUpdateMap.put("user_lang", this.user_lang);
		}
	}

	// TODO :
	public void setTTSLang(Lang reference) {
		if (reference != null) {
			this.mLangs.add(reference);
		}
	}

	private void setUser_hasObject(Map<String, Object> inUpdateMap) {
		if (this.user_main == 0) {
			this.user_main = 1;
			inUpdateMap.put("user_main", this.user_main);
		}
	}

	private void setUser_hasNotObject(Map<String, Object> inUpdateMap) {
		if (this.user_main > 0) {
			this.user_main = 0;
			inUpdateMap.put("user_main", this.user_main);
		}
	}

	/**
	 * Modifie le mot de passe (crypté).
	 * 
	 * @param inValue le mot de passe crypté.
	 */
	private void setUser_password(Map<String, Object> inUpdateMap, String inValue) {
		if (!inValue.equals(this.user_password)) {
			this.user_password = inValue;
			inUpdateMap.put("user_password", inValue);
		}
	}

	public void setPreferences(Timezone inTimezone, int inTimeFormat, boolean inIsNewsLetter, boolean inNotifyMessagePlayed) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setUser_24h(theUpdateMap, inTimeFormat);
		setTimezone(theUpdateMap, inTimezone);
		setUser_newsletter(theUpdateMap, inIsNewsLetter ? 1 : 0);
		setUser_authmsgrcv(theUpdateMap, inNotifyMessagePlayed ? 1 : 0);
		update(theUpdateMap);

	}

	@Deprecated
	public List<User> getFriendsWithObject() {
		final List<User> theResult = new ArrayList<User>();
		for (final Contact thecontact : getAllContacts()) {
			final User theFriend = thecontact.getContact();
			if ((theFriend != null) && theFriend.getUserHasObject()) {
				theResult.add(theFriend);
			}
		}
		return theResult;
	}

	public boolean getUserHasObject() {
		return this.user_main > 0;
	}

	public String getUserColorSign() {
		return this.user_color_sign;
	}

	public void setUserWithoutObject() {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setUser_hasNotObject(theUpdateMap);
		update(theUpdateMap);
	}
}

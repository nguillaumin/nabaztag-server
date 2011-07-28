package net.violet.platform.datamodel;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNull;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.StringTools;

import org.apache.log4j.Logger;

public class AnnuImpl extends ObjectRecord<Annu, AnnuImpl> implements Annu {

	private static final Logger LOGGER = Logger.getLogger(AnnuImpl.class);

	public static final SQLObjectSpecification<AnnuImpl> SPECIFICATION = new SQLObjectSpecification<AnnuImpl>("annu", AnnuImpl.class, new SQLKey("annu_user"), "annu_update_time");
	private static final String[] NEW_COLUMNS = new String[] { "annu_user", "annu_country", "annu_city_id", "annu_prenom", "annu_nom", "description", "user_lang", };

	protected long annu_user;
	protected String annu_sexe;
	protected String annu_cp;
	protected String annu_city;
	protected String annu_country;
	protected long annu_city_id;
	protected int annu_confirm;
	protected Date annu_datebirth;
	protected String annu_prenom;
	protected String annu_nom;
	protected String description;
	protected Long picture_file_id;
	protected int user_lang;
	protected Timestamp annu_update_time;

	private int age;

	private SingleAssociationNull<Annu, City, CityImpl> city;
	private final SingleAssociationNull<Annu, Files, FilesImpl> mPictureFile;

	/**
	 * Langue principale de cet utilisateur.
	 */
	private final SingleAssociationNull<Annu, Lang, LangImpl> mLang;

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected AnnuImpl(long id) throws SQLException {
		init(id);
		this.city = new SingleAssociationNull<Annu, City, CityImpl>(this, "annu_city_id", CityImpl.SPECIFICATION, CityImpl.SPECIFICATION.getTableKeys()[0]);
		this.mPictureFile = new SingleAssociationNull<Annu, Files, FilesImpl>(this, "picture_file_id", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
		this.mLang = new SingleAssociationNull<Annu, Lang, LangImpl>(this, "user_lang", LangImpl.SPECIFICATION, LangImpl.SPECIFICATION.getPrimaryKey());
	}

	/**
	 * Constructeur par défaut (enregistrement existant).
	 */
	protected AnnuImpl() {
		// This space for rent.
		this.city = new SingleAssociationNull<Annu, City, CityImpl>(this, "annu_city_id", CityImpl.SPECIFICATION, CityImpl.SPECIFICATION.getTableKeys()[0]);
		this.mPictureFile = new SingleAssociationNull<Annu, Files, FilesImpl>(this, "picture_file_id", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
		this.mLang = new SingleAssociationNull<Annu, Lang, LangImpl>(this, "user_lang", LangImpl.SPECIFICATION, LangImpl.SPECIFICATION.getPrimaryKey());
	}

	/**
	 * Constructeur pour un nouvel enregistrement.
	 * 
	 * @param
	 */
	public AnnuImpl(User inUser, String inCountry, Lang inLang) throws SQLException {
		this(inUser, inCountry, inLang, inUser.getUser_firstName(), inUser.getUser_lastName());
	}

	/**
	 * Constructeur pour un nouvel enregistrement.
	 * 
	 * @param
	 */
	public AnnuImpl(User inUser, String inCountry, Lang inLang, String firstName, String lastName) throws SQLException {
		this.annu_user = inUser.getId();
		this.annu_country = inCountry;
		this.annu_city_id = 0;
		this.annu_prenom = StringTools.truncate(firstName, 30);
		this.annu_nom = StringTools.truncate(lastName, 30);
		this.description = net.violet.common.StringShop.EMPTY_STRING;
		this.user_lang = Factories.LANG.getParentByIsocode(inLang.getIsoCode()).getId().intValue(); // TODO temporaire lors du passage à la nouvelle
		// api
		// on
		// passera
		// la
		// bonne
		// langue
		init(AnnuImpl.NEW_COLUMNS);

		this.city = new SingleAssociationNull<Annu, City, CityImpl>(this, "annu_city_id", CityImpl.SPECIFICATION, CityImpl.SPECIFICATION.getTableKeys()[0]);
		this.mPictureFile = new SingleAssociationNull<Annu, Files, FilesImpl>(this, "picture_file_id", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
		this.mLang = new SingleAssociationNull<Annu, Lang, LangImpl>(this, "user_lang", LangImpl.SPECIFICATION, LangImpl.SPECIFICATION.getPrimaryKey());
	}

	/**
	 * Accesseur à partir d'un id.
	 * 
	 * @param id id de l'objet.
	 * @return l'enregistrement (ou <code>null</code>).
	 */
	public static Annu find(long id) {
		Annu annu = null;
		try {
			annu = AbstractSQLRecord.findByKey(AnnuImpl.SPECIFICATION, AnnuImpl.SPECIFICATION.getTableKeys()[0], id);
		} catch (final SQLException se) {
			AnnuImpl.LOGGER.fatal(se, se);
		}
		return annu;
	}

	@Override
	public Long getId() {
		return this.annu_user;
	}

	@Override
	public SQLObjectSpecification<AnnuImpl> getSpecification() {
		return AnnuImpl.SPECIFICATION;
	}

	public String getAnnu_city() {
		return this.annu_city;
	}

	public Files getPictureFile() {
		return this.mPictureFile.get(this.picture_file_id);
	}

	public Lang getLangPreferences() {
		return this.mLang.get(this.user_lang);
	}

	public void setLangPreferences(Lang inLang) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setUser_lang(theUpdateMap, inLang);
		update(theUpdateMap);
	}

	private void setUser_lang(Map<String, Object> inUpdateMap, Lang inLang) {
		if (this.user_lang != inLang.getId()) {
			this.user_lang = inLang.getId().intValue();
			this.mLang.set(inLang);
			inUpdateMap.put("user_lang", this.user_lang);
		}
	}

	public void setPictureFile(Files inPictureFile) {
		this.picture_file_id = (Long) this.mPictureFile.set(inPictureFile);
	}

	public void setCity(String city) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setAnnu_city(theUpdateMap, city);
		this.city = new SingleAssociationNull<Annu, City, CityImpl>(this, "annu_city_id", CityImpl.SPECIFICATION, CityImpl.SPECIFICATION.getTableKeys()[0]);

		update(theUpdateMap);
	}

	private void setAnnu_city(Map<String, Object> inUpdateMap, String annu_city) {
		// TODO enlever annu_city dans code v3
		if (!this.annu_city.equals(annu_city)) {
			this.annu_city = annu_city;
		}

		try {
			City theCity = Factories.CITY.getByCityAndCountry(this.annu_country, this.annu_city);
			if (theCity == null) {
				theCity = new CityImpl(this.annu_country, this.annu_city);
			}
			this.annu_city_id = theCity.getId();
			inUpdateMap.put("annu_city_id", this.annu_city_id);
		} catch (final SQLException e) {
			AnnuImpl.LOGGER.fatal(e, e);
		}
		inUpdateMap.put("annu_city", annu_city);

	}

	public void setAllInformation(String gender, String city, int annuConfirm, String lastname, String firstname, String inDescription) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setAnnu_sexe(theUpdateMap, gender);
		setAnnu_city(theUpdateMap, city);
		setAnnu_confirm(theUpdateMap, annuConfirm);
		setDescription(theUpdateMap, inDescription);
		setAnnu_nom(theUpdateMap, lastname);
		setAnnu_prenom(theUpdateMap, firstname);
		update(theUpdateMap);
	}

	public void setAnnuConfirm(int inConfirm) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setAnnu_confirm(theUpdateMap, inConfirm);
		update(theUpdateMap);
	}

	public void setAllInformation(String gender, String city, String lastname, String firstname, String inDescription) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setAnnu_sexe(theUpdateMap, gender);
		setAnnu_city(theUpdateMap, city);
		setDescription(theUpdateMap, inDescription);
		setAnnu_nom(theUpdateMap, lastname);
		setAnnu_prenom(theUpdateMap, firstname);
		update(theUpdateMap);
	}

	public void setAddress(String country, String cp) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setAnnu_country(theUpdateMap, country);
		setAnnu_cp(theUpdateMap, cp);
		update(theUpdateMap);

	}

	public int getAnnu_confirm() {
		return this.annu_confirm;
	}

	private void setAnnu_confirm(Map<String, Object> inUpdateMap, int annu_confirm) {
		if (this.annu_confirm != annu_confirm) {
			this.annu_confirm = annu_confirm;
			inUpdateMap.put("annu_confirm", annu_confirm);
		}
	}

	public String getAnnu_country() {
		return this.annu_country;
	}

	private void setAnnu_country(Map<String, Object> inUpdateMap, String annu_country) {
		if (!this.annu_country.equals(annu_country)) {
			this.annu_country = annu_country;
			inUpdateMap.put("annu_country", annu_country);
		}
	}

	public String getAnnu_cp() {
		return this.annu_cp;
	}

	private void setAnnu_cp(Map<String, Object> inUpdateMap, String annu_cp) {
		if (!this.annu_cp.equals(annu_cp)) {
			this.annu_cp = annu_cp;
			inUpdateMap.put("annu_cp", annu_cp);
		}
	}

	public Date getAnnu_datebirth() {
		return this.annu_datebirth;
	}

	public void setDateBirth(Date date) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setAnnu_datebirth(theUpdateMap, date);
		update(theUpdateMap);
	}

	private void setAnnu_datebirth(Map<String, Object> inUpdateMap, Date annu_datebirth) {
		if ((this.annu_datebirth == null) || !this.annu_datebirth.equals(annu_datebirth)) {
			this.annu_datebirth = annu_datebirth;
			inUpdateMap.put("annu_datebirth", annu_datebirth);
		}
	}

	public String getAnnu_nom() {
		return this.annu_nom;
	}

	private void setAnnu_nom(Map<String, Object> inUpdateMap, String inAnnuNom) {
		String theAnnuNom;
		if (inAnnuNom != null) {
			theAnnuNom = StringTools.truncate(inAnnuNom, 30);
		} else {
			theAnnuNom = null;
		}
		if (((this.annu_nom == null) && (theAnnuNom != null)) || ((theAnnuNom != null) && !theAnnuNom.equals(this.annu_nom))) {
			this.annu_nom = theAnnuNom;
			inUpdateMap.put("annu_nom", theAnnuNom);
		}
	}

	public String getAnnu_prenom() {
		return this.annu_prenom;
	}

	private void setAnnu_prenom(Map<String, Object> inUpdateMap, String inAnnuPreNom) {
		String theAnnuPreNom;
		if (inAnnuPreNom != null) {
			theAnnuPreNom = StringTools.truncate(inAnnuPreNom, 30);
		} else {
			theAnnuPreNom = null;
		}
		if (((this.annu_prenom == null) && (theAnnuPreNom != null)) || ((theAnnuPreNom != null) && !theAnnuPreNom.equals(this.annu_prenom))) {
			this.annu_prenom = theAnnuPreNom;
			inUpdateMap.put("annu_prenom", theAnnuPreNom);
		}
	}

	public String getAnnu_sexe() {
		return this.annu_sexe;
	}

	public void setGender(String gender) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setAnnu_sexe(theUpdateMap, gender);
		update(theUpdateMap);
	}

	private void setAnnu_sexe(Map<String, Object> inUpdateMap, String annu_sexe) {
		if ((annu_sexe != null) && !annu_sexe.equals(this.annu_sexe)) {
			this.annu_sexe = annu_sexe;
			inUpdateMap.put("annu_sexe", annu_sexe);
		}
	}

	public void setDescription(String inDesc) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setDescription(theUpdateMap, inDesc);
		update(theUpdateMap);
	}

	private void setDescription(Map<String, Object> inUpdateMap, String inDesc) {
		if (!this.description.equals(inDesc)) {
			this.description = inDesc;
			inUpdateMap.put("description", inDesc);
		}
	}

	public int getAge() {
		if ((this.age == 0) && (this.annu_datebirth != null)) {
			final Calendar cal = Calendar.getInstance();
			final Calendar calBirth = Calendar.getInstance();
			calBirth.setTime(this.annu_datebirth);
			this.age = cal.get(Calendar.YEAR) - calBirth.get(Calendar.YEAR) - 1;
			if ((cal.get(Calendar.MONTH) > calBirth.get(Calendar.MONTH)) || ((cal.get(Calendar.MONTH) == calBirth.get(Calendar.MONTH)) && (cal.get(Calendar.DATE) >= calBirth.get(Calendar.DATE)))) {
				this.age++;
			}
		}
		return this.age;
	}

	public City getCity() {
		// TODO
		return this.city.get(this.annu_city_id);
	}

	public static List<Annu> findConfirmed() {
		try {
			final String[] inJoinTables = new String[] { "user" };
			return new LinkedList<Annu>(AbstractSQLRecord.findAllDistinct(AnnuImpl.SPECIFICATION, inJoinTables, "annu_user = user_id and annu_confirm = ? and user_main > ? GROUP BY annu_city", Arrays.asList(new Object[] { 1, 0 }), "annu_city"));
		} catch (final SQLException se) {
			AnnuImpl.LOGGER.fatal(se, se);
		}
		return Collections.emptyList();
	}

	public Timestamp getUpdateTime() {
		return this.annu_update_time;
	}

	public String getDescription() {
		return this.description;
	}

}

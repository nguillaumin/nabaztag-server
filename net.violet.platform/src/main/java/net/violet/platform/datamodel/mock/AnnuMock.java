package net.violet.platform.datamodel.mock;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Annu;
import net.violet.platform.datamodel.City;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.util.StringTools;

public class AnnuMock extends AbstractMockRecord<Annu, AnnuMock> implements Annu {

	protected long annu_user;
	protected String annu_sexe;
	protected String annu_cp;
	protected String annu_city;
	protected String annu_country;
	protected int annu_confirm;
	protected Date annu_datebirth;
	protected String annu_prenom;
	protected String annu_nom;
	protected String description;
	protected Files picture;
	protected Timestamp annu_update_time;
	private Lang mLang;

	public AnnuMock(User inUser, String annu_sexe, String annu_cp, String annu_city, int annu_confirm, Date annu_datebirth, String annu_country, Lang inLang) {
		super(inUser.getId());
		this.annu_user = inUser.getId();
		this.annu_sexe = annu_sexe;
		this.annu_cp = annu_cp;
		this.annu_city = annu_city;
		this.annu_country = annu_country;
		this.annu_confirm = annu_confirm;
		this.annu_datebirth = annu_datebirth;
		this.mLang = inLang;
		this.annu_prenom = StringTools.truncate(inUser.getUser_firstName(), 30);
		this.annu_nom = StringTools.truncate(inUser.getUser_lastName(), 30);
		this.annu_update_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	public int getAge() {
		throw new UnsupportedOperationException();
	}

	public String getAnnu_city() {
		return this.annu_city;
	}

	public int getAnnu_confirm() {
		return this.annu_confirm;
	}

	public String getAnnu_country() {
		return this.annu_country;
	}

	public String getAnnu_cp() {
		return this.annu_cp;
	}

	public Date getAnnu_datebirth() {
		return this.annu_datebirth;
	}

	public String getAnnu_nom() {
		return this.annu_nom;
	}

	public String getAnnu_prenom() {
		return this.annu_prenom;
	}

	public String getAnnu_sexe() {
		return this.annu_sexe;
	}

	public long getAnnu_user() {
		return this.annu_user;
	}

	public City getCity() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void setAddress(String country, String cp) {
		this.annu_country = country;
		this.annu_cp = cp;
	}

	public void setAllInformation(String gender, String city, int annuConfirm, String lastname, String firstname, String description) {
		this.annu_sexe = gender;
		this.annu_city = city;
		this.annu_confirm = annuConfirm;
		this.annu_nom = lastname;
		this.annu_prenom = firstname;
		this.description = description;
	}

	public void setAllInformation(String gender, String city, String lastname, String firstname, String description) {
		this.annu_sexe = gender;
		this.annu_city = city;
		this.annu_nom = lastname;
		this.annu_prenom = firstname;
		this.description = description;
	}

	public void setCity(String city) {
		this.annu_city = city;
	}

	public void setDateBirth(Date date) {
		this.annu_datebirth = date;
	}

	public void setGender(String gender) {
		this.annu_sexe = gender;
	}

	public Timestamp getUpdateTime() {
		return this.annu_update_time;
	}

	public void setAnnuConfirm(int inConfirm) {
		this.annu_confirm = inConfirm;

	}

	public String getDescription() {
		return this.description;
	}

	public Files getPictureFile() {
		return this.picture;
	}

	public void setDescription(String inDesc) {
		this.description = inDesc;
	}

	public void setPictureFile(Files inFile) {
		this.picture = inFile;
	}

	public Lang getLangPreferences() {
		return this.mLang;
	}

	public void setLangPreferences(Lang inLang) {
		this.mLang = inLang;
	}
}

package net.violet.platform.datamodel;

import java.sql.Date;
import java.sql.Timestamp;

import net.violet.db.records.Record;

/**
 * User profile. Most informations are not mandatory. 
 *
 */
public interface Annu extends Record<Annu> {

	String MALE = "H";

	String FEMALE = "F";

	String getAnnu_city();

	void setCity(String city);

	void setAllInformation(String gender, String city, int confirm, String lastname, String firstname, String inComment);

	void setAllInformation(String gender, String city, String lastname, String firstname, String inComment);

	void setAddress(String country, String cp);

	int getAnnu_confirm();

	String getAnnu_country();

	String getAnnu_cp();

	Date getAnnu_datebirth();

	void setDateBirth(Date date);

	String getAnnu_nom();

	String getAnnu_prenom();

	String getAnnu_sexe();

	String getDescription();

	void setDescription(String inDesc);

	Files getPictureFile();

	void setPictureFile(Files inFile);

	void setGender(String gender);

	int getAge();

	City getCity();

	Timestamp getUpdateTime();

	void setAnnuConfirm(int inConfirm);

	/**
	 * Accesseur sur la langue principale de l'utilisateur.
	 * 
	 * @return la langue principale primaire.
	 */
	Lang getLangPreferences();

	/**
	 * set la langue principale primaire de l'utilisateur.
	 * 
	 * @param inLang
	 */
	void setLangPreferences(Lang inLang);

}

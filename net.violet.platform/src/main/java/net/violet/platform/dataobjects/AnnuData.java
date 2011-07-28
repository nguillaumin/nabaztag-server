package net.violet.platform.dataobjects;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.Annu;
import net.violet.platform.datamodel.AnnuImpl;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.geocoding.Coordinates;
import net.violet.platform.geocoding.GeoCoder;
import net.violet.platform.geocoding.GeoCodingException;

import org.apache.log4j.Logger;

public class AnnuData extends RecordData<Annu> implements Serializable {

	/**
	 * An enumeration for the MALE FEMALE encoding values
	 */
	public static enum Gender {
		MALE("M", "H"),
		FEMALE("F", "F"),
		UNKNOWN(null, null), ;
	
		private final String codeAnnu;
		private final String codeProfile;
	
		Gender(String inCodeAnnu, String inCodeProfile) {
			this.codeAnnu = inCodeAnnu;
			this.codeProfile = inCodeProfile;
		}
	
		/**
		 * Known genders is a subset of the whole enum
		 */
		public static final Gender[] KNOWNS = { MALE, FEMALE };
	
		/**
		 * Use this code to insert into Annu table
		 */
		public String getCodeAnnu() {
			return this.codeAnnu;
		}
	
		/**
		 * Use this code to insert into PersonProfileMap
		 */
		public String getProfileCode() {
			return this.codeProfile;
		}
	
		/**
		 * Determine the Gender from a Profile code OR from an Annu code..
		 * 
		 * @param inLetter
		 * @return UNKNOWN if the Gender couldn't be recognized..
		 */
		public static Gender getValue(String inLetter) {
			for (final Gender x : Gender.KNOWNS) {
				if (x.codeAnnu.equals(inLetter) || x.codeProfile.equals(inLetter)) {
					return x;
				}
			}
			return UNKNOWN;
		}
	}

	private static final Logger LOGGER = Logger.getLogger(AnnuData.class);

	public static AnnuData getData(Annu inAnnu) {
		try {
			return RecordData.getData(inAnnu, AnnuData.class, Annu.class);
		} catch (final InstantiationException e) {
			AnnuData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			AnnuData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			AnnuData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			AnnuData.LOGGER.fatal(e, e);
		}

		return null;
	}

	/**
	 * Constructeur à partir d'un user.
	 */
	public AnnuData(User inUser) {
		this((inUser != null) ? inUser.getAnnu() : null);
	}

	/**
	 * Constructeur à partir d'un user.
	 */
	public AnnuData(Annu inAnnu) {
		super(inAnnu);
	}

	/**
	 * Retourne la liste des villes des utilisateurs souhaitant apparaitre dans
	 * l'annuaire ayant un lapin
	 * 
	 * @return
	 */
	public static List<AnnuData> findAllCities() {
		return AnnuData.generateList(AnnuImpl.findConfirmed());
	}

	/**
	 * Generates a list of AnnuData with the given Annu list
	 * 
	 * @param inAnnu Annu list
	 * @return
	 */
	private static List<AnnuData> generateList(List<Annu> inAnnu) {
		final List<AnnuData> annuDataList = new ArrayList<AnnuData>();

		for (final Annu tempAnnu : inAnnu) {
			annuDataList.add(new AnnuData(tempAnnu));
		}

		return annuDataList;
	}

	public String getAnnuCity() {
		final Annu theAnnu = getRecord();
		if (theAnnu != null) {
			return theAnnu.getAnnu_city();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getAnnu_cityFormatted() {
		if (getAnnu_city().length() > 1) {
			final String annuCity = getAnnu_city();
			return annuCity.substring(0, 1).toUpperCase() + annuCity.substring(1, annuCity.length()).toLowerCase();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getAnnu_city() {
		return getAnnuCity();
	}

	public String getDescription() {
		final Annu theAnnu = getRecord();
		if ((theAnnu != null) && (theAnnu.getDescription() != null)) {
			return theAnnu.getDescription();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public FilesData getPictureFile() {
		final Annu theAnnu = getRecord();
		if (theAnnu != null) {
			return FilesData.getData(theAnnu.getPictureFile());
		}
		return null;
	}

	public String getAnnuCountry() {
		final Annu theAnnu = getRecord();
		if (theAnnu != null) {
			return theAnnu.getAnnu_country();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getAnnuCp() {
		final Annu theAnnu = getRecord();
		if (theAnnu != null) {
			return theAnnu.getAnnu_cp();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getAnnuDateBirth() {
		final Annu theAnnu = getRecord();
		if (theAnnu != null) {
			final Date theDateBirth = getRecord().getAnnu_datebirth();
			if (theDateBirth != null) {
				return theDateBirth.toString();
			}
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getAnnu_sexe() {
		return getAnnuSexe();
	}
	
	public Gender getGender(){
		return Gender.getValue(getAnnu_sexe());
	}

	public String getAnnuSexe() {
		final Annu theAnnu = getRecord();
		if (theAnnu != null) {
			return theAnnu.getAnnu_sexe();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public int getAnnuConfirm() {
		final Annu theAnnu = getRecord();
		if (theAnnu != null) {
			return theAnnu.getAnnu_confirm();
		}
		return 0;
	}

	public void setAnnuConfirm(int inConfirm) {
		final Annu theAnnu = getRecord();
		if (theAnnu != null) {
			theAnnu.setAnnuConfirm(inConfirm);
		}
	}

	public String getFirstName() {
		final Annu theAnnu = getRecord();
		if ((theAnnu != null) && (theAnnu.getAnnu_prenom() != null)) {
			return theAnnu.getAnnu_prenom();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getLastName() {
		final Annu theAnnu = getRecord();
		if ((theAnnu != null) && (theAnnu.getAnnu_nom() != null)) {
			return theAnnu.getAnnu_nom();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public int getAge() {
		final Annu theAnnu = getRecord();
		if (theAnnu != null) {
			return theAnnu.getAge();
		}
		return 0;
	}

	// TODO: supprimer et modifier toutes les JSP.
	public Map<String, String> getPays() {
		return Collections.singletonMap("pays_nom", getAnnuCountry());
	}

	public Timestamp getUpdateTime() {
		final Annu theAnnu = getRecord();
		if (theAnnu != null) {
			return theAnnu.getUpdateTime();
		}

		return null;
	}

	public Date getBirthDate() {
		final Annu theAnnu = getRecord();
		if (theAnnu != null) {
			return theAnnu.getAnnu_datebirth();
		}

		return null;
	}

	public SiteLangData getLang() {
		final Annu theAnnu = getRecord();
		if ((theAnnu != null) && (theAnnu.getLangPreferences() != null)) {
			return SiteLangData.get(theAnnu.getLangPreferences());
		}

		return SiteLangData.DEFAULT_SITE_LANGUAGE;
	}

	public void updateProfile(String inFirstName, String inLastName, Date inBirthDate, String inGender, String inCity, String inZip, CountryData inCountry, String inDesc) {
		final Annu theAnnu = getRecord();
		if (theAnnu != null) {

			//TODO weird behavior here : updating the user's profile changes the objects information
			// if country is valid and city or country has changed we update the objects location
			if ((inCountry != null) && inCountry.isValid() && (!inCountry.getPaysCode().equals(theAnnu.getAnnu_country()) || ((inCity == null) && (getAnnu_city() != null)) || ((inCity != null) && !inCity.equals(getAnnu_city())))) {
				BigDecimal latitude;
				BigDecimal longitude;

				try {
					final Coordinates coord = GeoCoder.geoCodeAddress(inCountry.getPaysCode(), inCity);
					latitude = coord != null ? coord.getLatitude() : null;
					longitude = coord != null ? coord.getLongitude() : null;
				} catch (final GeoCodingException e) {
					AnnuData.LOGGER.fatal(e, e);
					latitude = null;
					longitude = null;
				}

				for (final VObjectData object : VObjectData.findByOwner(UserData.find(theAnnu.getId()))) {
					object.getProfile().setGPSInformation(latitude, longitude);
				}

			}

			theAnnu.setAllInformation(inGender, inCity, inLastName, inFirstName, inDesc);
			if (inBirthDate != null) {
				theAnnu.setDateBirth(new java.sql.Date(inBirthDate.getTime()));
			}
			theAnnu.setAddress(inCountry.getPaysCode(), inZip);
		}
	}

	public void updateProfilePicture(FilesData inFile) {
		final Annu theAnnu = getRecord();
		if (theAnnu != null) {
			theAnnu.setPictureFile(inFile.getRecord());
		}
	}

	public void setCity(String city) {
		final Annu theAnnu = getRecord();
		if (theAnnu != null) {
			theAnnu.setCity(city);
		}
	}

	public void setDateBirth(java.sql.Date date) {
		final Annu theAnnu = getRecord();
		if (theAnnu != null) {
			theAnnu.setDateBirth(date);
		}
	}

	public static AnnuData create(UserData user, String country, Lang lang) {
		if ((user != null) && user.isValid()) {
			return AnnuData.getData(Factories.ANNU.create(user.getRecord(), country, lang));
		}
		return null;
	}

	public void setPictureFile(FilesData newPicture) {
		if (isValid() && newPicture.isValid()) {
			getRecord().setPictureFile(newPicture.getRecord());
		}

	}
}

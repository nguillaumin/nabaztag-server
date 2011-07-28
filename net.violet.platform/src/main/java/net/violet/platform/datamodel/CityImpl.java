package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.platform.util.StringTools;

public class CityImpl extends ObjectRecord<City, CityImpl> implements City {

	public static final SQLObjectSpecification<CityImpl> SPECIFICATION = new SQLObjectSpecification<CityImpl>("city", CityImpl.class, new SQLKey("city_id"));

	private static final String[] NEW_COLUMNS = new String[] { "city_country_code", "city_name", };

	protected long city_id;
	protected String city_country_code;
	protected String city_name;

	protected CityImpl(long id) throws SQLException {
		init(id);
	}

	protected CityImpl() {
		// This space for rent.
	}

	public CityImpl(String inCountryCode, String inCityName) throws SQLException {

		this.city_country_code = inCountryCode;
		this.city_name = inCityName;

		init(CityImpl.NEW_COLUMNS);
	}

	public CityImpl(AnnuImpl annuImpl, String city) throws SQLException {
		this.city_country_code = annuImpl.annu_country;
		this.city_name = city;
		init(CityImpl.NEW_COLUMNS);
	}

	public CityImpl(AnnuImpl annuImpl) throws SQLException {
		this.city_country_code = annuImpl.annu_country;
		this.city_name = annuImpl.annu_city;
		init(CityImpl.NEW_COLUMNS);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.City#getId()
	 */
	@Override
	public Long getId() {
		return this.city_id;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.City#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<CityImpl> getSpecification() {
		return CityImpl.SPECIFICATION;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.City#getCity_city()
	 */
	public String getName() {
		return this.city_name;
	}

	private void setCity_name(Map<String, Object> inUpdateMap, String inCityName) {
		String theCityName;
		if (inCityName != null) {
			theCityName = StringTools.truncate(inCityName, 50);
		} else {
			theCityName = null;
		}
		if (((this.city_name == null) && (theCityName != null)) || ((theCityName != null) && !theCityName.equals(this.city_name))) {
			this.city_name = theCityName;
			inUpdateMap.put("city_name", theCityName);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.violet.platform.datamodel.City#setAllInformation(java.lang.String,
	 * java.lang.String, int, java.lang.String, java.lang.String)
	 */
	public void setAllInformation(String cityCountryCode, String cityName) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setCity_name(theUpdateMap, cityName);
		setCountry_code(theUpdateMap, cityCountryCode);
		update(theUpdateMap);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.City#getCity_country()
	 */
	public String getCountryCode() {
		return this.city_country_code;
	}

	private void setCountry_code(Map<String, Object> inUpdateMap, String cityCountryCode) {
		if (!this.city_country_code.equals(cityCountryCode)) {
			this.city_country_code = cityCountryCode;
			inUpdateMap.put("city_country_code", cityCountryCode);
		}
	}

}

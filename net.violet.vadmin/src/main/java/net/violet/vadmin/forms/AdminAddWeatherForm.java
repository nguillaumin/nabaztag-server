package net.violet.vadmin.forms;

import java.util.Collections;
import java.util.List;

import net.violet.commondev.utils.StringShop;
import net.violet.vadmin.objects.data.ContinentData;
import net.violet.vadmin.objects.data.CountryData;
import net.violet.vadmin.objects.data.StoreCityData;

public class AdminAddWeatherForm extends AdminLocationForm {

	private static final long serialVersionUID = 1L;
	private String dispatch;
	private String display;

	private String city = StringShop.EMPTY_STRING;
	private String newCity = StringShop.EMPTY_STRING;
	private String url = StringShop.EMPTY_STRING;
	private String country = StringShop.EMPTY_STRING;
	private String continent = StringShop.EMPTY_STRING;
	private String dicoCreated = StringShop.EMPTY_STRING;

	private List<ContinentData> continents = Collections.emptyList();
	private List<CountryData> countries = Collections.emptyList();
	private List<StoreCityData> cities = Collections.emptyList();

	@Override
	public String getDispatch() {
		return dispatch;
	}

	@Override
	public void setDispatch(String dispatch) {
		this.dispatch = dispatch;
	}

	@Override
	public String getDisplay() {
		return display;
	}

	@Override
	public void setDisplay(String display) {
		this.display = display;
	}

	@Override
	public String getCity() {
		return city;
	}

	@Override
	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String getCountry() {
		return country;
	}

	@Override
	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String getContinent() {
		return continent;
	}

	@Override
	public void setContinent(String continent) {
		this.continent = continent;
	}

	@Override
	public List<ContinentData> getContinents() {
		return continents;
	}

	@Override
	public void setContinents(List<ContinentData> continents) {
		this.continents = continents;
	}

	@Override
	public List<CountryData> getCountries() {
		return countries;
	}

	@Override
	public void setCountries(List<CountryData> countries) {
		this.countries = countries;
	}

	@Override
	public List<StoreCityData> getCities() {
		return cities;
	}

	@Override
	public void setCities(List<StoreCityData> cities) {
		this.cities = cities;
	}

	public String getNewCity() {
		return newCity;
	}

	public void setNewCity(String newCity) {
		this.newCity = newCity;
	}

	public String getDicoCreated() {
		return dicoCreated;
	}

	public void setDicoCreated(String dicoCreated) {
		this.dicoCreated = dicoCreated;
	}

}

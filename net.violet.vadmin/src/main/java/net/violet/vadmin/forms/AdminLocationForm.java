package net.violet.vadmin.forms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.violet.commondev.utils.StringShop;
import net.violet.platform.datamodel.Store.STORE_STATUS;
import net.violet.platform.datamodel.Store.STORE_TYPE;
import net.violet.vadmin.objects.data.ContinentData;
import net.violet.vadmin.objects.data.CountryData;
import net.violet.vadmin.objects.data.StoreCityData;

import org.apache.struts.util.LabelValueBean;

public class AdminLocationForm extends AdminForm {

	private static final long serialVersionUID = 1L;
	private String dispatch;
	private String display;

	private String name = StringShop.EMPTY_STRING;
	private String address = StringShop.EMPTY_STRING;
	private String zipCode  = StringShop.EMPTY_STRING;
	private String city = StringShop.EMPTY_STRING;
	private String type = StringShop.EMPTY_STRING;
	private String status = StringShop.EMPTY_STRING;
	private String url = StringShop.EMPTY_STRING;
	private String rank = StringShop.EMPTY_STRING;
	private String comment = StringShop.EMPTY_STRING;
	private String country = "";
	private String continent = "";

	private List<ContinentData> continents = Collections.emptyList();
	private List<CountryData> countries = Collections.emptyList();
	private List<StoreCityData> cities = Collections.emptyList();
	private ArrayList<LabelValueBean> typeList = new ArrayList<LabelValueBean>();
	private ArrayList<LabelValueBean> statusList = new ArrayList<LabelValueBean>();

	public String getDispatch() {
		return dispatch;
	}

	public void setDispatch(String dispatch) {
		this.dispatch = dispatch;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<StoreCityData> getCities() {
		return cities;
	}

	public void setCities(List<StoreCityData> cities) {
		this.cities = cities;
	}

	public ArrayList<LabelValueBean> getTypeList() {
		for (STORE_TYPE aStoreType : STORE_TYPE.values()) {
			this.typeList.add(new LabelValueBean(aStoreType.name(), aStoreType.name()));
		}
		return typeList;
	}

	public void setTypeList(ArrayList<LabelValueBean> typeList) {
		this.typeList = typeList;
	}

	public ArrayList<LabelValueBean> getStatusList() {
		for (STORE_STATUS aStoreStatus : STORE_STATUS.values()) {
			this.statusList.add(new LabelValueBean(aStoreStatus.name(), aStoreStatus.name()));
		}
		return statusList;
	}

	public void setStatusList(ArrayList<LabelValueBean> statusList) {
		this.statusList = statusList;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public List<ContinentData> getContinents() {
		return continents;
	}

	public void setContinents(List<ContinentData> continents) {
		this.continents = continents;
	}

	public List<CountryData> getCountries() {
		return countries;
	}

	public void setCountries(List<CountryData> countries) {
		this.countries = countries;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}

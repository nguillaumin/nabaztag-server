package net.violet.vadmin.actions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.commondev.utils.StringShop;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.stores.Create;
import net.violet.platform.api.actions.stores.Delete;
import net.violet.platform.api.actions.stores.Get;
import net.violet.platform.api.actions.stores.GetStores;
import net.violet.platform.api.actions.stores.Update;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.press.PressInformationMap;
import net.violet.platform.api.maps.store.StoreInformationMap;
import net.violet.vadmin.exceptions.InvalidFile;
import net.violet.vadmin.forms.AdminSearchStoreForm;
import net.violet.vadmin.objects.data.GetStoreData;
import net.violet.vadmin.util.SessionTools;
import net.violet.vadmin.util.UploadTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

public class AdminSearchStoreAction extends AdminLocationAction {

	private static final String DISPLAY_STORES = "displayStores";
	private static final String DISPLAY_INFO = "infoStore";

	public ActionForward displayStores(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		AdminSearchStoreForm myForm = (AdminSearchStoreForm) form;
		final ActionMessages errors = new ActionMessages();
		final String theCountryCodeSelected = myForm.getCountry();
		final String theContinentSelected = myForm.getContinent();

		final Action theAction = new GetStores();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("id", theCountryCodeSelected);

		try {

			final List<StoreInformationMap> theInformationMap = (List<StoreInformationMap>) Admin.processRequest(theAction, theParams);
			if (theInformationMap != null) {
				final List<GetStoreData> newsList = new ArrayList<GetStoreData>();
				for (final StoreInformationMap inStoreInformation : theInformationMap) {
					newsList.add(buildGetStoreData(inStoreInformation));
				}
				myForm.setStoreList(newsList);
			}
		} catch (final InvalidParameterException e) {
			errors.add("invalidParam", new ActionMessage(StringShop.EMPTY_STRING));
		} catch (final APIException e) {
			errors.add("invalidParam", new ActionMessage(StringShop.EMPTY_STRING));
		}

		myForm.setContinent(theContinentSelected);
		myForm.setCountry(theCountryCodeSelected);
		myForm.setCountries(getCountries(theContinentSelected));
		myForm.setDisplay(DISPLAY_STORES);
		return load(mapping, form, request, response);
	}

	public ActionForward displayInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		AdminSearchStoreForm myForm = (AdminSearchStoreForm) form;
		final String continent = myForm.getContinent();
		final String country = myForm.getCountry();

		final Action theAction = new Get();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("id", myForm.getIdStore());
		try {
			final StoreInformationMap theInformationMap = (StoreInformationMap) Admin.processRequest(theAction, theParams);;
			myForm.setTheStoreData(buildGetStoreData(theInformationMap));
			myForm.setDisplay(DISPLAY_INFO);
		} catch (APIException e) {}

		myForm.setContinent(continent);
		myForm.setCountries(getCountries(continent));
		myForm.setCountry(country);
		myForm.setCities(getCities(country));
		return load(mapping, form, request, response);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final String currentSessionId = SessionTools.getSessionId(request.getSession());
		AdminSearchStoreForm myForm = (AdminSearchStoreForm) form;
		final ActionMessages errors = new ActionMessages();
		final String continent = myForm.getContinent();
		final String country = myForm.getCountry();
		final FormFile imageFile = myForm.getImageFile();
		String fileIdCreated = null;

		final String name = myForm.getName();
		final String type = myForm.getType();
		final String status = myForm.getStatus();
		final String address = myForm.getAddress();
		final String zipCode = myForm.getZipCode();
		final String city = myForm.getCity();
		final String url = myForm.getUrl();
		final String rank = myForm.getRank();
		final String comment = myForm.getComment();

		if (StringShop.EMPTY_STRING.equals(myForm.getName())) {
			errors.add("emptyName", new ActionMessage(StringShop.EMPTY_STRING));
		}

		if (errors.isEmpty()) {
			if (imageFile.getFileSize() != 0 && currentSessionId != null) {
				try {
					fileIdCreated = UploadTools.uploadFile(imageFile, currentSessionId);
				} catch (final FileNotFoundException e1) {
					errors.add("uploadFailed", new ActionMessage(StringShop.EMPTY_STRING));
				} catch (final IOException e1) {
					errors.add("uploadFailed", new ActionMessage(StringShop.EMPTY_STRING));
				} catch (InvalidFile e) {
					errors.add("incorrectSize", new ActionMessage(StringShop.EMPTY_STRING));
				}
			}
		}

		if (errors.isEmpty()) {
			final Action theAction = new Update();
			final Map<String, Object> theParams = new HashMap<String, Object>();
			theParams.put(StoreInformationMap.NAME, name);
			theParams.put(StoreInformationMap.ADDRESS, address);
			theParams.put(StoreInformationMap.ZIPCODE, zipCode);
			theParams.put(StoreInformationMap.CITY, city);
			theParams.put(StoreInformationMap.COUNTRY, country);
			theParams.put(StoreInformationMap.URL, url);
			theParams.put(StoreInformationMap.RANK, rank);
			theParams.put(StoreInformationMap.COMMENT, comment);
			theParams.put(StoreInformationMap.STATUS, status);
			theParams.put(StoreInformationMap.TYPE, type);
			theParams.put(StoreInformationMap.PICTURE, fileIdCreated);

			final Map<String, Object> theMap = new HashMap<String, Object>();
			theMap.put("id", myForm.getIdStore());
			theMap.put(Create.STORE_PARAM, theParams);

			try {
				final StoreInformationMap theInformationMap = (StoreInformationMap) Admin.processRequest(theAction, theMap);;
				myForm.setTheStoreData(buildGetStoreData(theInformationMap));
				myForm.setDisplay(DISPLAY_INFO);
				errors.add("updateSucceeded", new ActionMessage(StringShop.EMPTY_STRING));
			} catch (APIException e) {
				errors.add("updateFailed", new ActionMessage(StringShop.EMPTY_STRING));
			}
		}

		myForm.setContinent(continent);
		myForm.setCountries(getCountries(continent));
		myForm.setCountry(country);
		myForm.setCities(getCities(country));

		myForm.setErrors(errors);
		if (!errors.isEmpty()) {
			myForm.setIdStore(myForm.getIdStore());
			return displayInfo(mapping, myForm, request, response);
		}
		return load(mapping, form, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		AdminSearchStoreForm myForm = (AdminSearchStoreForm) form;
		final String idStore = myForm.getIdStore();
		final String continent = myForm.getContinent();
		final String country = myForm.getCountry();

		final Action theAction = new Delete();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("id", idStore);
		try {
			Admin.processRequest(theAction, theParams);
		} catch (APIException e) {}

		myForm.setContinent(continent);
		myForm.setCountry(country);
		return displayStores(mapping, myForm, request, response);
	}

	private GetStoreData buildGetStoreData(StoreInformationMap inMapInformation) {
		final GetStoreData theFormData = new GetStoreData();
		if (inMapInformation != null) {
			theFormData.setId((String) inMapInformation.get(StoreInformationMap.ID));
			theFormData.setName((String) inMapInformation.get(StoreInformationMap.NAME));
			theFormData.setType((String) inMapInformation.get(StoreInformationMap.TYPE));
			theFormData.setAddress((String) inMapInformation.get(StoreInformationMap.ADDRESS));
			theFormData.setZipCode((String) inMapInformation.get(StoreInformationMap.ZIPCODE));
			theFormData.setCity((String) inMapInformation.get(StoreInformationMap.CITY));
			theFormData.setStatus((String) inMapInformation.get(StoreInformationMap.STATUS));
			theFormData.setUrl((String) inMapInformation.get(StoreInformationMap.URL));
			theFormData.setComment((String) inMapInformation.get(StoreInformationMap.COMMENT));
			theFormData.setPicture((String) inMapInformation.get(PressInformationMap.PICTURE));
		}
		return theFormData;
	}

}

package net.violet.vadmin.actions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.commondev.utils.StringShop;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.stores.Create;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.maps.store.StoreInformationMap;
import net.violet.vadmin.exceptions.InvalidFile;
import net.violet.vadmin.forms.AdminAddStoreForm;
import net.violet.vadmin.util.SessionTools;
import net.violet.vadmin.util.UploadTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

public class AdminAddStoreAction extends AdminLocationAction {

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final String currentSessionId = SessionTools.getSessionId(request.getSession());
		final AdminAddStoreForm myForm = (AdminAddStoreForm) form;
		final ActionMessages errors = new ActionMessages();

		final String name = myForm.getName();
		final String type = myForm.getType();
		final String status = myForm.getStatus();
		final String address = myForm.getAddress();
		final String zipCode = myForm.getZipCode();
		final String city = myForm.getCity();
		final String country = myForm.getCountry();
		final String url = myForm.getUrl();
		final String rank = myForm.getRank();
		final String comment = myForm.getComment();
		final FormFile file = myForm.getImageFile();

		if (StringShop.EMPTY_STRING.equals(name)) {
			errors.add("emptyName", new ActionMessage(StringShop.EMPTY_STRING));
		}

		String fileIdCreated = null;
		if (file.getFileSize() != 0 && currentSessionId != null) {
			try {
				fileIdCreated = UploadTools.uploadFile(file, currentSessionId);
			} catch (final FileNotFoundException e1) {
				errors.add("uploadFailed", new ActionMessage(StringShop.EMPTY_STRING));
			} catch (final IOException e1) {
				errors.add("uploadFailed", new ActionMessage(StringShop.EMPTY_STRING));
			} catch (InvalidFile e) {
				errors.add("incorrectSize", new ActionMessage(StringShop.EMPTY_STRING));
			}
		}

		if (errors.isEmpty()) {
			final Action theAction = new Create();
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
			theMap.put(Create.STORE_PARAM, theParams);
			try {
				Admin.processRequest(theAction, theParams);
				errors.add("storeCreated", new ActionMessage(StringShop.EMPTY_STRING));
			} catch (final APIException e) {
				errors.add("newsNotCreated", new ActionMessage(StringShop.EMPTY_STRING));
			}
		} else {
			myForm.setName(name);
			myForm.setType(type);
			myForm.setStatus(status);
			myForm.setAddress(address);
			myForm.setZipCode(zipCode);
			myForm.setUrl(url);
			myForm.setRank(rank);
			myForm.setComment(comment);
			myForm.setCity(city);
		}

		myForm.setErrors(errors);
		return displayCities(mapping, myForm, request, response);
	}
}

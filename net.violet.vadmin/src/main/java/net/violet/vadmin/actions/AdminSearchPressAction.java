package net.violet.vadmin.actions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.common.StringShop;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.press.Delete;
import net.violet.platform.api.actions.press.Get;
import net.violet.platform.api.actions.press.GetPressClip;
import net.violet.platform.api.actions.press.Update;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.maps.press.PressInformationMap;
import net.violet.vadmin.exceptions.InvalidFile;
import net.violet.vadmin.forms.AdminSearchPressForm;
import net.violet.vadmin.objects.data.GetPressData;
import net.violet.vadmin.util.SessionTools;
import net.violet.vadmin.util.UploadTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

public class AdminSearchPressAction extends AdminAction {

	private static final String DISPLAY_PRESS = "displayPress";
	private static final String INFO_PRESS = "infoPress";

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final AdminSearchPressForm myForm = (AdminSearchPressForm) form;

		myForm.setLangList(generateLanguagesList());
		myForm.setProductList(generateProductsList());

		saveErrors(request, myForm.getErrors());
		return mapping.getInputForward();
	}

	public ActionForward displayContent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final AdminSearchPressForm myForm = (AdminSearchPressForm) form;
		final ActionMessages errors = new ActionMessages();
		final String nameProduct = myForm.getProductPress();
		final String theLanguage = myForm.getLanguage();

		final Action theAction = new GetPressClip();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(GetPressClip.LANGUAGE_PARAM, theLanguage);
		theParams.put(GetPressClip.PRODUCT_PARAM, nameProduct);

		try {
			final List<PressInformationMap> theInformationMap = (List<PressInformationMap>) Admin.processRequest(theAction, theParams);
			if (theInformationMap != null) {
				final List<GetPressData> newsList = new ArrayList<GetPressData>();
				for (final PressInformationMap inPressInformation : theInformationMap) {
					newsList.add(buildGetPressData(inPressInformation));
				}
				myForm.setPressList(newsList);
			}
		} catch (final APIException e) {
			errors.add("contentFailed", new ActionMessage(e.toString()));
		}

		myForm.setLanguage(theLanguage);
		myForm.setProductPress(nameProduct);
		myForm.setDisplay(AdminSearchPressAction.DISPLAY_PRESS);
		myForm.setErrors(errors);

		return load(mapping, form, request, response);
	}

	public ActionForward displayInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final AdminSearchPressForm myForm = (AdminSearchPressForm) form;
		final ActionMessages errors = myForm.getErrors();
		final String idPress = myForm.getIdPress();
		final String nameProduct = myForm.getProductPress();
		final String theLanguage = myForm.getLanguage();

		final Action theAction = new Get();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("id", idPress);

		try {
			final PressInformationMap theInformationMap = (PressInformationMap) Admin.processRequest(theAction, theParams);;
			myForm.setThePressData(buildGetPressData(theInformationMap));
			myForm.setDisplay(AdminSearchPressAction.INFO_PRESS);
		} catch (final APIException e) {
			errors.add("infoFailed", new ActionMessage(e.toString()));
		}

		myForm.setLanguage(theLanguage);
		myForm.setProductPress(nameProduct);
		myForm.setDisplay(AdminSearchPressAction.INFO_PRESS);
		myForm.setErrors(errors);
		return load(mapping, form, request, response);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final String currentSessionId = SessionTools.getSessionId(request.getSession());
		final AdminSearchPressForm myForm = (AdminSearchPressForm) form;
		final ActionMessages errors = new ActionMessages();
		final FormFile imageFile = myForm.getImageFile();
		String fileIdCreated = null;

		if (StringShop.EMPTY_STRING.equals(myForm.getTitle())) {
			errors.add("emptyTitle", new ActionMessage(StringShop.EMPTY_STRING));
		}
		if (StringShop.EMPTY_STRING.equals(myForm.getUrl())) {
			errors.add("emptyUrl", new ActionMessage(StringShop.EMPTY_STRING));
		}
		if (StringShop.EMPTY_STRING.equals(myForm.getSummary())) {
			errors.add("emptySummary", new ActionMessage(StringShop.EMPTY_STRING));
		}

		// Upload
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
			theParams.put(PressInformationMap.TITLE, myForm.getTitle().trim());
			theParams.put(PressInformationMap.ABSTRACT, myForm.getSummary().trim());
			theParams.put(PressInformationMap.URL, myForm.getUrl().trim());
			theParams.put(PressInformationMap.LANGUAGE, myForm.getLanguage());
			theParams.put(PressInformationMap.PRODUCT, myForm.getProductPress());
			theParams.put(PressInformationMap.PICTURE, fileIdCreated);
			final Map<String, Object> theMap = new HashMap<String, Object>();
			theMap.put("id", myForm.getIdPress());
			theMap.put(Update.PRESS_PARAM, theParams);
			try {
				final PressInformationMap theInformationMap = (PressInformationMap) Admin.processRequest(theAction, theMap);;
				myForm.setThePressData(buildGetPressData(theInformationMap));
				myForm.setDisplay(AdminSearchPressAction.INFO_PRESS);
				errors.add("updateSucceeded", new ActionMessage(StringShop.EMPTY_STRING));
			} catch (final APIException e) {
				errors.add("updateFailed", new ActionMessage(e.toString()));
			}
		}

		myForm.setErrors(errors);
		if (!errors.isEmpty()) {
			myForm.setIdPress(myForm.getIdPress());
			myForm.setProductPress(myForm.getProductPress());
			myForm.setLanguage(myForm.getLanguage());
			return displayInfo(mapping, myForm, request, response);
		}
		return load(mapping, form, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final AdminSearchPressForm myForm = (AdminSearchPressForm) form;
		final String idPress = myForm.getIdPress();
		final String nameProduct = myForm.getProductPress();
		final String theLanguage = myForm.getLanguage();

		final Action theAction = new Delete();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("id", idPress);
		try {
			Admin.processRequest(theAction, theParams);
		} catch (final APIException e) {
		}

		myForm.setLanguage(theLanguage);
		myForm.setProductPress(nameProduct);
		return displayContent(mapping, form, request, response);
	}

	private GetPressData buildGetPressData(PressInformationMap inPressInformation) {
		final GetPressData theFormData = new GetPressData();
		if (inPressInformation != null) {
			theFormData.setId((String) inPressInformation.get(PressInformationMap.ID));
			theFormData.setTitle((String) inPressInformation.get(PressInformationMap.TITLE));
			theFormData.setSummary((String) inPressInformation.get(PressInformationMap.ABSTRACT));
			theFormData.setUrl((String) inPressInformation.get(PressInformationMap.URL));
			theFormData.setPicture((String) inPressInformation.get(PressInformationMap.PICTURE));
			theFormData.setProduct((String) inPressInformation.get(PressInformationMap.PRODUCT));
			theFormData.setLang((String) inPressInformation.get(PressInformationMap.LANGUAGE));
		}
		return theFormData;
	}
}

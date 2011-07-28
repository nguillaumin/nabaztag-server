package net.violet.vadmin.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.common.StringShop;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.actions.admin.applications.GetAdmin;
import net.violet.platform.api.actions.applications.GetApplications;
import net.violet.platform.api.actions.applications.Update;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.applications.ApplicationAdminMap;
import net.violet.platform.api.maps.applications.ApplicationInformationMap;
import net.violet.platform.api.maps.applications.ApplicationProfileMap;
import net.violet.vadmin.forms.AdminApplicationForm;
import net.violet.vadmin.objects.data.GetAdminData;
import net.violet.vadmin.objects.data.GetApplicationData;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class AdminSearchApplicationAction extends AdminApplicationAction {

	private static final String DISPLAY_APPLICATION = "displayApplication";
	private static List<GetApplicationData> applicationList = Collections.emptyList();

	public ActionForward displayContent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final AdminApplicationForm myForm = (AdminApplicationForm) form;
		final ActionMessages errors = myForm.getErrors();
		final String nameService = myForm.getNameService();
		final String theLanguage = myForm.getLanguage();

		final Action theAction = new GetApplications();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, nameService);
		theParams.put(GetApplications.LANGUAGE_PARAM, theLanguage);

		try {
			final Object theResult = Admin.processRequest(theAction, theParams);

			if (theResult != null) {
				applicationList = new ArrayList<GetApplicationData>();
				for (final ApplicationInformationMap anAppliInformation : (List<ApplicationInformationMap>) theResult) {
					final GetApplicationData theFormData = new GetApplicationData((String) anAppliInformation.get(ApplicationInformationMap.ID), (String) anAppliInformation.get(ApplicationInformationMap.NAME), StringShop.EMPTY_STRING);
					applicationList.add(theFormData);
				}
				myForm.setApplicationList(applicationList);
			}
		} catch (final InvalidParameterException e) {
			errors.add("invalidParam", new ActionMessage(StringShop.EMPTY_STRING));
		} catch (final APIException e) {
			errors.add("APIException", new ActionMessage(e.toString()));
		}

		myForm.setErrors(errors);
		return load(mapping, form, request, response);
	}

	public ActionForward displayApplication(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final AdminApplicationForm myForm = (AdminApplicationForm) form;
		final ActionMessages errors = myForm.getErrors();
		final String applicationId = myForm.getApplicationId();
		final String nameService = myForm.getNameService();
		final String language = myForm.getLanguage();

		final Action theAction = new GetAdmin();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, applicationId);

		try {
			final ApplicationAdminMap theAppli = (ApplicationAdminMap) Admin.processRequest(theAction, theParams);
			final GetAdminData inGetAppliData = buildGetAdminData(theAppli);
			myForm.setApplicationId(applicationId);
			myForm.setNameService(nameService);
			myForm.setLanguage(language);
			myForm.setIsVisible(inGetAppliData.getVisible());
			myForm.setAppliInfo(inGetAppliData);
			myForm.setCat(inGetAppliData.getCategory());
			myForm.setLanguages(inGetAppliData.getLanguages().toArray(new String[] {}));
			myForm.setHardware(inGetAppliData.getSupported_hardware().toArray(new String[] {}));
			myForm.setApplicationList(applicationList);
			myForm.setPictureFileId(inGetAppliData.getPicture());
			myForm.setIconFileId(inGetAppliData.getIcon());
			myForm.setDisplay(AdminSearchApplicationAction.DISPLAY_APPLICATION);
		}catch (APIException e) {
			errors.add("APIExceptionURL", new ActionMessage(e.toString()));
		}

		myForm.setErrors(errors);
		return load(mapping, myForm, request, response);
	}

	private GetAdminData buildGetAdminData(ApplicationAdminMap inApplicationAdmin) {
		final GetAdminData theFormData = new GetAdminData();

		if (inApplicationAdmin != null) {
			ApplicationProfileMap inAppliProfile = (ApplicationProfileMap) inApplicationAdmin.get(ApplicationAdminMap.PROFILE);
			if (inAppliProfile != null) {
				theFormData.setTitle((String) inAppliProfile.get("title"));
				theFormData.setDescription((String) inAppliProfile.get("description"));
				theFormData.setInstructions((String) inAppliProfile.get("instructions"));
				theFormData.setCategory((String) inAppliProfile.get("category"));
				theFormData.setPictureURL((String) inAppliProfile.get("picture"));
				theFormData.setIconURL((String) inAppliProfile.get("icon"));
			}

			ApplicationInformationMap inAppliInfo = (ApplicationInformationMap) inApplicationAdmin.get(ApplicationAdminMap.INFO);
			if (inAppliInfo != null) {
				String name = (String) inAppliInfo.get(ApplicationInformationMap.NAME);
				theFormData.setName(name.replaceAll("^net\\.violet\\.[^.]+\\.", StringShop.EMPTY_STRING));
				theFormData.setId((String) inAppliInfo.get(ApplicationInformationMap.ID));
				theFormData.setOwner((String) inAppliInfo.get(ApplicationInformationMap.OWNER));
				theFormData.setSupported_hardware((List<String>) inAppliInfo.get("supported_hardware"));
			}

			theFormData.setPicture((String) inApplicationAdmin.get(ApplicationAdminMap.PICTURE_ID));
			theFormData.setIcon((String) inApplicationAdmin.get(ApplicationAdminMap.ICON_ID));
			theFormData.setAnnouncement((String)inApplicationAdmin.get(ApplicationAdminMap.ANNOUNCEMENT_ID));
			theFormData.setUrl((String) inApplicationAdmin.get(ApplicationAdminMap.URL));
			theFormData.setLink((String) inApplicationAdmin.get(ApplicationAdminMap.LINK));
			theFormData.setShortcut((String) inApplicationAdmin.get(ApplicationAdminMap.SHORTCUT));
			theFormData.setVisible((String) inApplicationAdmin.get(ApplicationAdminMap.VISIBLE));

			theFormData.setLanguages((List<String>) inApplicationAdmin.get("languages"));

		}
		return theFormData;
	}

	@Override
	Action getAction() {
		return new Update();
	}

	@Override
	ActionForward getForward(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		return displayApplication(mapping, form, request, response);
	}
	
	@Override
	String getSuccessMessage() {
		return "success.updated";
	}

}

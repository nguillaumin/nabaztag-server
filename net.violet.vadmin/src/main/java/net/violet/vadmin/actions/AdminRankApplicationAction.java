package net.violet.vadmin.actions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.common.StringShop;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.admin.applications.GetWithRank;
import net.violet.platform.api.actions.admin.applications.SetRank;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.maps.applications.ApplicationInformationMap;
import net.violet.vadmin.forms.AdminApplicationForm;
import net.violet.vadmin.objects.data.GetApplicationData;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class AdminRankApplicationAction extends AdminApplicationAction {

	/**
	 * Display all the applications found from a language and a category.
	 * @param mapping The ActionMapping used to select this instance.
	 * @param form The optional ActionForm bean for this request.
	 * @param request The HTTP Request we are processing.
	 * @param response The HTTP Response we are processing.
	 * @return the load <code>ActionForward</code>
	 */
	public ActionForward displayApplications(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final AdminApplicationForm myForm = (AdminApplicationForm) form;
		
		final Action theAction = new GetWithRank();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("id", myForm.getCat());
		theParams.put("language", myForm.getLanguage());

		try {
			List<GetApplicationData> applicationList = new LinkedList<GetApplicationData>();
			for (final ApplicationInformationMap anAppliInformation : ( List<ApplicationInformationMap>)Admin.processRequest(theAction, theParams)) {
				final GetApplicationData theFormData = new GetApplicationData((String) anAppliInformation.get(ApplicationInformationMap.ID), (String) anAppliInformation.get(ApplicationInformationMap.NAME), (String) anAppliInformation.get("rank"));
				applicationList.add(theFormData);
			}
			myForm.setApplicationList(applicationList);
		}
		catch (APIException e) {

		}
		return load(mapping, myForm, request, response);
	}


	/**
	 * Update the rank of each applications
	 * @param mapping The ActionMapping used to select this instance.
	 * @param form The optional ActionForm bean for this request.
	 * @param request The HTTP Request we are processing.
	 * @param response The HTTP Response we are processing.
	 * @return the displayApplications <code>ActionForward</code>
	 */
	public ActionForward updateRank(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final AdminApplicationForm myForm = (AdminApplicationForm) form;
		final ActionMessages errors = myForm.getErrors();
		String errorsMsg = "";
		
		final Map<String, Object> theParams = new HashMap<String, Object>();

		for(GetApplicationData anAppData : myForm.getApplicationList()){
			try {
				theParams.put("id", anAppData.getId());
				theParams.put("language", myForm.getLanguage());
				theParams.put("rank", anAppData.getRank());
				Admin.processRequest(getAction(), theParams);
			}catch (Exception e) {
				errorsMsg += "The rank of <em>"+anAppData.getName()+"</em> is not a number.<br />";
			}
		}
		if(!StringShop.EMPTY_STRING.equals(errorsMsg)){
			errors.add("wrongValue", new ActionMessage("error.test", errorsMsg));
			myForm.setErrors(errors);
		}else{
			errors.add("success", new ActionMessage(getSuccessMessage(), "rank"));
		}
		return displayApplications(mapping, myForm, request, response);
	}

	@Override
	Action getAction() {
		return new SetRank();
	}

	@Override
	ActionForward getForward(@SuppressWarnings("unused") ActionMapping mapping, @SuppressWarnings("unused") ActionForm form, @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		return null;
	}

	@Override
	String getSuccessMessage() {
		return "success.updated";
	}
}

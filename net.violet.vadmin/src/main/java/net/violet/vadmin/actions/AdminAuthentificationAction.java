package net.violet.vadmin.actions;

import java.io.FileNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.vadmin.forms.AdminAuthentificationForm;
import net.violet.vadmin.util.AdminAuthorization;
import net.violet.vadmin.util.SessionTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class AdminAuthentificationAction extends DispatchActionWithLog {

	/**
	 * Load the page.
	 * @param mapping The ActionMapping used to select this instance.
	 * @param form The optional ActionForm bean for this request.
	 * @param request The HTTP Request we are processing.
	 * @param response The HTTP Response we are processing.
	 * @return
	 */
	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final AdminAuthentificationForm myForm = (AdminAuthentificationForm) form;

		saveErrors(request, myForm.getErrors());
		return mapping.getInputForward();
	}
	
	/**
	 * Try to connect a user and to determine all his authorization.
	 * @param mapping The ActionMapping used to select this instance.
	 * @param form The optional ActionForm bean for this request.
	 * @param request The HTTP Request we are processing.
	 * @param response The HTTP Response we are processing.
	 * @return
	 */
	public ActionForward connect(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		final AdminAuthentificationForm myForm = (AdminAuthentificationForm) form;
		final ActionMessages errors = new ActionMessages();
		
		final String email = myForm.getEmail();
		final String password = myForm.getPassword();
		String redirect = "adminrefused";
		
		try {
			if(AdminAuthorization.getAuthorization(email)){
				String currentSessionId = SessionTools.createSession(email, password);
				if(currentSessionId != null){
					SessionTools.logUser(request, response, currentSessionId, email, password);
					redirect = "goHome";
				}
			}
		} catch (NumberFormatException e) {
			errors.add("wrongValue", new ActionMessage("error.authentification.incorrect_values"));
		} catch (FileNotFoundException e) {
			errors.add("inexistingFile", new ActionMessage("error.authentification.inexisting_file"));
		}
		
//		If there is an error, we redirect to the loading page with an error message.
		if(!errors.isEmpty()){
			myForm.setErrors(errors);
			return load(mapping, myForm, request, response);
		}
		
		return mapping.findForward(redirect);
	}

	/**
	 * Unlog the user
	 * @param mapping The ActionMapping used to select this instance.
	 * @param form The optional ActionForm bean for this request.
	 * @param request The HTTP Request we are processing.
	 * @param response The HTTP Response we are processing.
	 * @return
	 */
	public ActionForward disconnect(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		SessionTools.unlogUser(request, response);
		return load(mapping, form, request, response);
	}
}

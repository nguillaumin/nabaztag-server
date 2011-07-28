package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyNablifeForm;
import net.violet.platform.struts.ActionWithLog;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyNablifeResultSearchAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final MyNablifeForm myForm = (MyNablifeForm) form; //session.getAttribute ( "listResultForm") ;
		final MyNablifeForm savedForm = (MyNablifeForm) session.getAttribute("listResultForm");

		myForm.setUserData(savedForm.getUserData());
		myForm.setNabcastData(savedForm.getNabcastData());
		myForm.setSrvListData(savedForm.getSrvListData());
		myForm.setUserId(savedForm.getUserId());
		myForm.setLangUser(savedForm.getLangUser());
		myForm.setUserTimeZone(savedForm.getUserTimeZone());
		myForm.setSearched(savedForm.getSearched());
		myForm.setBadLogin(savedForm.getBadLogin());

		return mapping.getInputForward();
	}
}

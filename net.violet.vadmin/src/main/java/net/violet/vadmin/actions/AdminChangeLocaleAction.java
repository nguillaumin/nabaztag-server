package net.violet.vadmin.actions;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.vadmin.forms.AdminChangeLocaleForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AdminChangeLocaleAction extends DispatchActionWithLog {

	public ActionForward english(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final AdminChangeLocaleForm myForm = (AdminChangeLocaleForm) form;
		HttpSession session = request.getSession();
		session.setAttribute("org.apache.struts.action.LOCALE", Locale.ENGLISH);
		return mapping.findForward(myForm.getRedirect());
	}

	public ActionForward french(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final AdminChangeLocaleForm myForm = (AdminChangeLocaleForm) form;
		HttpSession session = request.getSession();
		session.setAttribute("org.apache.struts.action.LOCALE", Locale.FRENCH);
		return mapping.findForward(myForm.getRedirect());
	}

	public ActionForward japanese(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final AdminChangeLocaleForm myForm = (AdminChangeLocaleForm) form;
		HttpSession session = request.getSession();
		session.setAttribute("org.apache.struts.action.LOCALE", Locale.JAPANESE);
		return mapping.findForward(myForm.getRedirect());
	}

}

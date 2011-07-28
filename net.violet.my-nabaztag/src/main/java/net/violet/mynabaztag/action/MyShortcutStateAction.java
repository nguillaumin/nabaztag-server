package net.violet.mynabaztag.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyNablifeForm;
import net.violet.platform.dataobjects.NabcastData;
import net.violet.platform.struts.ActionWithLog;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyShortcutStateAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final MyNablifeForm myForm = (MyNablifeForm) form; //session.getAttribute ( "listResultForm") ;
		final MyNablifeForm savedForm = (MyNablifeForm) session.getAttribute("listResultForm");

		myForm.setNabcastData(new ArrayList<NabcastData>());

		if (savedForm != null) {
			myForm.setUserData(savedForm.getUserData());
			myForm.setSrvListData(savedForm.getSrvListData());
			myForm.setUserId(savedForm.getUserId());
			myForm.setLangUser(savedForm.getLangUser());
			myForm.setUserTimeZone(savedForm.getUserTimeZone());
			myForm.setSearched(savedForm.getSearched());

			if (savedForm.getNabcastData() != null) {
				if (savedForm.getNabcastData().size() > 0) {
					myForm.getNabcastData().add(savedForm.getNabcastData().get(0));
				}
			}
		}

		return mapping.getInputForward();
	}
}

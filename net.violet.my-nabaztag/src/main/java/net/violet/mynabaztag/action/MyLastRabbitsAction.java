package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.mynabaztag.form.MyLastRabbitsForm;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.struts.ActionWithLog;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyLastRabbitsAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final MyLastRabbitsForm myForm = (MyLastRabbitsForm) form;

		myForm.setUserLang(myForm.getUserLang());

		myForm.setRabbitsList(UserData.findListLastRabbits(100));

		return mapping.getInputForward();
	}
}

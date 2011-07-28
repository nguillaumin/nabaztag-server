package net.violet.vadmin.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.vadmin.util.SessionTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AdminHomeAction extends AdminAction {

	@Override
	public ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		if(!SessionTools.isLogged(request)){
			return mapping.findForward("adminrefused");
		}

		return mapping.getInputForward();
	}
	
}

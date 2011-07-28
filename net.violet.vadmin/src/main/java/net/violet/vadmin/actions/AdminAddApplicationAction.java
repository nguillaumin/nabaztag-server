package net.violet.vadmin.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.applications.Create;
import net.violet.vadmin.util.DicoAPI;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AdminAddApplicationAction extends AdminApplicationAction {

	@Override
	protected boolean isAnotherUrl(String formerURL, String newURL){
		return true;
	}
	
	@Override
	protected boolean hasToCreateTTS(String inText){
		return true;
	}
	
	@Override
	protected String getTitle(String inTitle){
		return DicoAPI.LOC+inTitle;
	}
	
	@Override
	protected String getDescription(String inDescription){
		return DicoAPI.LOC+inDescription;
	}
	
	@Override
	protected String getHowTo(String inHowTo){
		return DicoAPI.LOC+inHowTo;
	}

	@Override
	Action getAction() {
		return new Create();
	}

	@Override
	ActionForward getForward(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		return load(mapping, form, request, response);
	}

	@Override
	String getSuccessMessage() {
		return "success.created";
	}

}

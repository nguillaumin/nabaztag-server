package net.violet.vadmin.forms;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

abstract class AbstractForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(AbstractForm.class);
	
	private ActionMessages errors = new ActionMessages();

	/**
	 * Used to set the encoding for the form this fixes problems with accents
	 * when text's going from the form (JSP) to the form object (JAVA) *
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (final UnsupportedEncodingException e) {
			AbstractForm.LOGGER.fatal(e, e);
		}
		super.reset(mapping, request);
	}

	
	public ActionMessages getErrors() {
		return errors;
	}

	
	public void setErrors(ActionMessages errors) {
		this.errors = errors;
	}
}

package net.violet.mynabaztag.form;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

abstract class AbstractForm extends ActionForm {

	private static final Logger LOGGER = Logger.getLogger(AbstractForm.class);

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
}

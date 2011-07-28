package net.violet.mynabaztag.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

public class MySrvRssFreeForm extends MySrvAbstractForm {

	public static final long serialVersionUID = 1;

	/**
	 * Validate the form server side
	 */
	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

		final ActionErrors errors = super.validate(mapping, request);
		if (super.getSrvModeListener().equals("1")) {
			super.setSrvFrequencyListening("0");
		}

		return errors;
	}
}

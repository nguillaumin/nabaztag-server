package net.violet.mynabaztag.form;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public final class MySrvPodcastFreeForm extends MySrvPodcastAbstractForm {

	/**
	 * Validate the form server side
	 */
	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

		final ActionErrors errors = super.validate(mapping, request);
		final HttpSession session = request.getSession(true);
		final VObject object = SessionTools.getRabbitFromSession(session);

		if (null != object) {
			if (object.getHardware() == HARDWARE.V1) {
				setIsV1("true");
				errors.add("userNotV2", new ActionMessage("errors.key", StringShop.EMPTY_STRING));
				return errors;
			}

			setIsV1("false");
		}

		return errors;
	}

}

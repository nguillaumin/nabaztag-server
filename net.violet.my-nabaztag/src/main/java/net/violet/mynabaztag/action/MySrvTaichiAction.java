package net.violet.mynabaztag.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvTaichiForm;
import net.violet.platform.applications.SubscriptionManager;
import net.violet.platform.applications.TaichiHandler;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.TaichiDataFactory;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.TaichiDataFactory.TAICHI_FREQUENCY;
import net.violet.platform.struts.DispatchActionForLoggedUserWithObject;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class MySrvTaichiAction extends DispatchActionForLoggedUserWithObject {

	private static final Logger LOGGER = Logger.getLogger(MySrvTaichiAction.class);

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final Lang lang = SessionTools.getLangFromSession(session, request);
		final MySrvTaichiForm myForm = (MySrvTaichiForm) form;

		myForm.setFreqSrvList(TaichiDataFactory.generateListFrequence(lang));

		final List<SubscriptionData> theSubscriptions = SubscriptionData.findByApplicationAndObject(Application.NativeApplication.TAICHI.getApplication(), theObject);

		myForm.setIsReg(theSubscriptions.isEmpty() ? 0 : 1);

		if (myForm.getIsReg() >= 1) {
			myForm.setFreqSrv(theSubscriptions.get(0).getSettings().get(TaichiHandler.SOURCE).toString());
		} else {
			myForm.setFreqSrv(StringShop.EMPTY_STRING);
		}

		return mapping.getInputForward();
	}

	public ActionForward activate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvTaichiForm myForm = (MySrvTaichiForm) form;

		try {
			TaichiHandler.createOrUpdateSubscription(null, VObjectData.getData(theObject), TAICHI_FREQUENCY.findByLabel(myForm.getFreqSrv()));
		} catch (final Exception e) {
			MySrvTaichiAction.LOGGER.fatal(e, e);
		}

		return load(mapping, form, request, response);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvTaichiForm myForm = (MySrvTaichiForm) form;

		for (final SubscriptionData aSubscription : SubscriptionData.findByApplicationAndObject(Application.NativeApplication.TAICHI.getApplication(), theObject)) {
			try {
				TaichiHandler.createOrUpdateSubscription(aSubscription, VObjectData.getData(theObject), TAICHI_FREQUENCY.findByLabel(myForm.getFreqSrv()));
			} catch (final Exception e) {
				MySrvTaichiAction.LOGGER.fatal(e, e);
			}
		}

		return load(mapping, form, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);

		for (final SubscriptionData aSubscription : SubscriptionData.findByApplicationAndObject(Application.NativeApplication.TAICHI.getApplication(), theObject)) {
			try {
				SubscriptionManager.deleteSubscription(aSubscription);
			} catch (final Exception e) {
				MySrvTaichiAction.LOGGER.fatal(e, e);
			}
		}

		return load(mapping, form, request, response);
	}
}

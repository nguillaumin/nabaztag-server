package net.violet.mynabaztag.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvBilanForm;
import net.violet.platform.applications.BilanHandler;
import net.violet.platform.applications.SubscriptionManager;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.SubscriptionSchedulingSettings;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.BilanDataFactory;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.schedulers.DailyHandler;
import net.violet.platform.struts.DispatchActionForLoggedUserWithObject;
import net.violet.platform.util.SessionTools;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class MySrvBilanAction extends DispatchActionForLoggedUserWithObject {

	private static final Logger LOGGER = Logger.getLogger(MySrvBilanAction.class);

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final Lang lang = SessionTools.getLangFromSession(session, request);
		final MySrvBilanForm myForm = (MySrvBilanForm) form;

		final List<Subscription> subscriptionsList = Factories.SUBSCRIPTION.findByApplicationAndObject(Application.NativeApplication.BILAN.getApplication(), theObject);
		final int isReg = (subscriptionsList.isEmpty()) ? 0 : 1;

		// The user subscribed to the service using the new model...
		if (isReg == 1) {
			for (final Subscription subscription : subscriptionsList) {
				final SubscriptionScheduling scheduling = Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscriptionAndType(subscription, SchedulingType.SCHEDULING_TYPE.Weekly).get(0);
				final List<SubscriptionSchedulingSettings> schedulingSettings = Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.findAllBySubscriptionScheduling(scheduling);
				if (schedulingSettings != null) {
					for (final DailyHandler.Weekday aDay : DailyHandler.Weekday.values()) {
						if (aDay.toString().equals(schedulingSettings.get(0).getKey())) {
							myForm.setFreqSrv(aDay.getBilanId());
						}
						myForm.setHorraire(schedulingSettings.get(0).getValue());
						myForm.setServiceName(subscription.getApplication().getProfile().getTitle());
					}
				}
			}
		}

		// Init values
		myForm.setIsReg(isReg);
		myForm.setFreqSrvList(BilanDataFactory.generateListdays(lang));

		return mapping.getInputForward();
	}

	public ActionForward activate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvBilanForm myForm = (MySrvBilanForm) form;

		try {
			BilanHandler.createOrUpdateSubscription(null, VObjectData.getData(theObject), myForm.getHorraire(), DailyHandler.Weekday.findByBilanId(myForm.getFreqSrv()));
		} catch (final Exception e) {
			MySrvBilanAction.LOGGER.fatal(e, e);
		}

		return load(mapping, form, request, response);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvBilanForm myForm = (MySrvBilanForm) form;
		final SubscriptionData theSubscription = SubscriptionData.findByApplicationAndObject(Application.NativeApplication.BILAN.getApplication(), theObject).get(0);

		try {
			BilanHandler.createOrUpdateSubscription(theSubscription, VObjectData.getData(theObject), myForm.getHorraire(), DailyHandler.Weekday.findByBilanId(myForm.getFreqSrv()));
		} catch (final Exception e) {
			MySrvBilanAction.LOGGER.fatal(e, e);
		}

		return load(mapping, form, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);

		for (final SubscriptionData aSubscription : SubscriptionData.findByApplicationAndObject(Application.NativeApplication.BILAN.getApplication(), theObject)) {
			try {
				SubscriptionManager.deleteSubscription(aSubscription);
			} catch (final Exception e) {
				MySrvBilanAction.LOGGER.fatal(e, e);
			}
		}

		return load(mapping, form, request, response);
	}

}

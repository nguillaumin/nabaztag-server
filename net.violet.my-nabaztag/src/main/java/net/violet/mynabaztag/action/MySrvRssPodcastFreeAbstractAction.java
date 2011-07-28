package net.violet.mynabaztag.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvAbstractForm;
import net.violet.platform.applications.SubscriptionManager;
import net.violet.platform.applications.VActionFreeHandler;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Service;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData.SchedulingAtomData;
import net.violet.platform.schedulers.DailyHandler;
import net.violet.platform.schedulers.FrequencyHandler;
import net.violet.platform.struts.DispatchActionForLoggedUserWithObject;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public abstract class MySrvRssPodcastFreeAbstractAction extends DispatchActionForLoggedUserWithObject {

	private static final Logger LOGGER = Logger.getLogger(MySrvRssPodcastFreeAbstractAction.class);
	private static final String MIDNIGHT_STRING = StringShop.MIDNIGHT;

	public ActionForward activate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final MySrvAbstractForm myForm = (MySrvAbstractForm) form;
		final HttpSession session = request.getSession(true);
		final VObject object = SessionTools.getRabbitFromSession(session);
		final Lang userLang = SessionTools.getLangFromSession(session, request);
		final ActionMessages errors = new ActionMessages();

		// Creates the subscription
		final ApplicationData theApplication = ApplicationData.findById(myForm.getApplicationId());

		String scheduleW = null;
		String scheduleWE = null;
		if (myForm.getSrvModeListener().equals("1")) {
			scheduleW = (myForm.getScheduleW().equals(StringShop.EMPTY_STRING)) ? StringShop.EMPTY_STRING : myForm.getScheduleW();
			scheduleWE = (myForm.getScheduleWE().equals(StringShop.EMPTY_STRING)) ? StringShop.EMPTY_STRING : myForm.getScheduleWE();
		}

		try {
			VActionFreeHandler.createOrUpdateSubscription(null, VObjectData.getData(object), theApplication, myForm.getSrvNbNews(), scheduleW, scheduleWE, myForm.getSrvFrequencyListening());
		} catch (final Exception e) {
			errors.add("scenarioNotCreated", new ActionMessage("errors.addTwitter", DicoTools.dico(userLang, "srv_podcast/scenario_not_created")));
			saveErrors(request, errors);
			MySrvRssPodcastFreeAbstractAction.LOGGER.fatal(e, e);
		}

		errors.add("registerSucceed", new ActionMessage("errors.addRss", DicoTools.dico(userLang, "srv_podcast/subscription_succeed")));
		saveErrors(request, errors);

		return load(mapping, form, request, response);
	}

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObjectData object = VObjectData.getData(SessionTools.getRabbitFromSession(session));
		final User theUser = SessionTools.getUserFromSession(request);
		final MySrvAbstractForm myForm = (MySrvAbstractForm) form;

		SubscriptionData theSubscription = SubscriptionData.find(myForm.getSubscriptionId());
		if ((theSubscription == null) || (theSubscription.getReference() == null)) {
			final List<SubscriptionData> theSubscriptions = SubscriptionData.findByApplicationAndObject(Factories.APPLICATION.find(myForm.getApplicationId()), object.getReference());
			if (!theSubscriptions.isEmpty()) {
				theSubscription = theSubscriptions.get(0);
			}
		}
		if ((theSubscription != null) && (theSubscription.getReference() != null) && theSubscription.getObject().equals(object)) {
			myForm.setSubscribed(true);
			final Object newsSetting = theSubscription.getSettings().get(VActionFreeHandler.NB_NEWS);
			myForm.setSrvNbNews(Integer.parseInt(newsSetting.toString()));
			final SubscriptionSchedulingData scheduling = SubscriptionSchedulingData.findAllBySubscription(theSubscription).get(0);
			// cas d'une inscription par frequence et non par horaire
			if (SchedulingType.SCHEDULING_TYPE.NewContentWithFrequency.equals(scheduling.getType())) {

				myForm.setScheduleW(null);
				myForm.setScheduleWE(null);
				myForm.setSrvModeListener("2");
				try {
					final SubscriptionSchedulingSettingsData frequencySetting = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(scheduling, FrequencyHandler.FREQUENCY);
					final String period = FrequencyHandler.Frequency.findByLabel(frequencySetting.getValue()).getTimeAsString();
					myForm.setSrvFrequencyListening(period);
				} catch (final NullPointerException npe) {
					myForm.setSrvFrequencyListening("");
				}
			}
			// cas d'une inscription par horaire
			else {
				myForm.setSrvModeListener("1");
				myForm.setSrvFrequencyListening(MySrvRssPodcastFreeAbstractAction.MIDNIGHT_STRING);

				final SchedulingAtomData atomW = scheduling.getSchedulingAtom(DailyHandler.Weekday.MONDAY, theUser.getTimezone().getJavaTimeZone());
				final SchedulingAtomData atomWE = scheduling.getSchedulingAtom(DailyHandler.Weekday.SUNDAY, theUser.getTimezone().getJavaTimeZone());

				if (atomW != null) {
					myForm.setScheduleW(atomW.getTimeFormated(theUser.use24()));
				} else {
					myForm.setScheduleW(null);
				}
				if (atomWE != null) {
					myForm.setScheduleWE(atomWE.getTimeFormated(theUser.use24()));
				} else {
					myForm.setScheduleWE(null);
				}
			}

		}

		return mapping.getInputForward();
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final MySrvAbstractForm myForm = (MySrvAbstractForm) form;
		final HttpSession session = request.getSession(true);
		final VObjectData object = VObjectData.getData(SessionTools.getRabbitFromSession(session));

		final Lang userLang = SessionTools.getLangFromSession(session, request);

		SubscriptionData theSubscription = SubscriptionData.find(myForm.getSubscriptionId());
		if ((theSubscription == null) || (theSubscription.getReference() == null)) {
			final List<SubscriptionData> theSubscriptions = SubscriptionData.findByApplicationAndObject(Factories.APPLICATION.find(myForm.getApplicationId()), object.getReference());
			if (!theSubscriptions.isEmpty()) {
				theSubscription = theSubscriptions.get(0);
			}
		}
		final ActionMessages errors = new ActionMessages();

		if ((theSubscription != null) && (theSubscription.getReference() != null) && theSubscription.getObject().equals(object)) {
			try {
				// Creates the subscription
				final ApplicationData theApplication = theSubscription.getApplication();

				String scheduleW = null;
				String scheduleWE = null;
				if (myForm.getSrvModeListener().equals("1")) {
					scheduleW = (myForm.getScheduleW().equals(StringShop.EMPTY_STRING)) ? StringShop.EMPTY_STRING : myForm.getScheduleW();
					scheduleWE = (myForm.getScheduleWE().equals(StringShop.EMPTY_STRING)) ? StringShop.EMPTY_STRING : myForm.getScheduleWE();

				}
				VActionFreeHandler.createOrUpdateSubscription(theSubscription, object, theApplication, myForm.getSrvNbNews(), scheduleW, scheduleWE, myForm.getSrvFrequencyListening());
				errors.add("registerSucceed", new ActionMessage("errors.addRss", DicoTools.dico(userLang, "srv_podcast/subscription_succeed")));
				saveErrors(request, errors);
				return load(mapping, form, request, response);
			} catch (final Exception e) {
				MySrvRssPodcastFreeAbstractAction.LOGGER.fatal(e, e);
			}
		}
		errors.add("scenarioNotCreated", new ActionMessage("errors.addTwitter", DicoTools.dico(userLang, "srv_podcast/scenario_not_created")));
		saveErrors(request, errors);
		return load(mapping, form, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final MySrvAbstractForm myForm = (MySrvAbstractForm) form;
		final HttpSession session = request.getSession(true);
		final VObjectData object = VObjectData.getData(SessionTools.getRabbitFromSession(session));

		SubscriptionData theSubscription = SubscriptionData.find(myForm.getSubscriptionId());
		if (theSubscription == null) {
			final List<SubscriptionData> theSubscriptions = SubscriptionData.findByApplicationAndObject(Factories.APPLICATION.find(myForm.getApplicationId()), object.getReference());
			if (!theSubscriptions.isEmpty()) {
				theSubscription = theSubscriptions.get(0);
			}
		}

		if ((theSubscription != null) && (theSubscription.getReference() != null) && theSubscription.getObject().equals(object)) {
			try {
				SubscriptionManager.deleteSubscription(theSubscription);
			} catch (final Exception e) {
				MySrvRssPodcastFreeAbstractAction.LOGGER.fatal(e, e);
			}
		}

		return load(mapping, form, request, response);
	}

	protected abstract Service getService();

	protected abstract String getApplicationName(MySrvAbstractForm myForm);

}

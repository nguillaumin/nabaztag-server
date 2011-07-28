package net.violet.mynabaztag.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvAbstractForm;
import net.violet.platform.applications.SubscriptionManager;
import net.violet.platform.applications.VActionFreeHandler;
import net.violet.platform.applications.VActionFullHandler;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.ServiceFactory;
import net.violet.platform.datamodel.factories.ServiceFactory.SERVICE;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.dataobjects.TtsLangData;
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

public abstract class MySrvRssPodcastFullAbstractAction extends DispatchActionForLoggedUserWithObject {

	private static final Logger LOGGER = Logger.getLogger(MySrvRssPodcastFullAbstractAction.class);

	public static final String MIDNIGHT_STRING = StringShop.MIDNIGHT;

	public ActionForward config(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final User theUser = SessionTools.getUserFromSession(request);
		final Lang userLang = SessionTools.getLangFromSession(session, request);

		final MySrvAbstractForm myForm = (MySrvAbstractForm) form;
		myForm.setSrvVoice(userLang.getId().intValue());
		myForm.setLangList(TtsLangData.findAll(theUser));

		return mapping.getInputForward();
	}

	public ActionForward activate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final MySrvAbstractForm myForm = (MySrvAbstractForm) form;
		final HttpSession session = request.getSession(true);
		final VObject object = SessionTools.getRabbitFromSession(session);
		final Lang userLang = SessionTools.getLangFromSession(session, request);

		String timeW = null;
		String timeWE = null;
		if (myForm.getSrvModeListener().equals("1")) {
			timeW = (myForm.getScheduleW().equals(StringShop.EMPTY_STRING)) ? StringShop.EMPTY_STRING : myForm.getScheduleW();
			timeWE = (myForm.getScheduleWE().equals(StringShop.EMPTY_STRING)) ? StringShop.EMPTY_STRING : myForm.getScheduleWE();

		}

		final ActionMessages errors = new ActionMessages();
		try {
			VActionFullHandler.createOrUpdateSubscription(null, VObjectData.getData(object), myForm.getSrvNbNews(), timeW, timeWE, myForm.getSrvFrequencyListening(), myForm.getName(), myForm.getUrl(), Factories.LANG.find(myForm.getSrvVoice()), getService() == SERVICE.PODCAST);
		} catch (final Exception e) {
			errors.add("scenarioNotCreated", new ActionMessage("errors.addTwitter", DicoTools.dico(userLang, "srv_podcast/scenario_not_created")));
			saveErrors(request, errors);
			MySrvRssPodcastFullAbstractAction.LOGGER.fatal(e, e);
			return config(mapping, form, request, response);
		}

		errors.add("registerSucceed", new ActionMessage("errors.addRss", DicoTools.dico(userLang, "srv_podcast/subscription_succeed")));
		saveErrors(request, errors);

		return config(mapping, form, request, response);
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
			final Object newsSetting = theSubscription.getSettings().get(VActionFreeHandler.NB_NEWS);
			myForm.setSrvNbNews(Integer.parseInt(newsSetting.toString()));
			final SubscriptionSchedulingData scheduling = SubscriptionSchedulingData.findAllBySubscription(theSubscription).get(0);
			// cas d'une inscription par frequence et non par horaire
			if (SchedulingType.SCHEDULING_TYPE.NewContentWithFrequency.equals(scheduling.getType())) {

				myForm.setScheduleW(null);
				myForm.setScheduleWE(null);
				myForm.setSrvModeListener("2");
				final SubscriptionSchedulingSettingsData frequencySetting = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(scheduling, FrequencyHandler.FREQUENCY);
				final String period = FrequencyHandler.Frequency.findByLabel(frequencySetting.getValue()).getTimeAsString();
				myForm.setSrvFrequencyListening(period);
			}
			// cas d'une inscription par horaire
			else {
				myForm.setSrvModeListener("1");
				myForm.setSrvFrequencyListening(MySrvRssPodcastFullAbstractAction.MIDNIGHT_STRING);

				final SchedulingAtomData atomW = scheduling.getSchedulingAtom(DailyHandler.Weekday.MONDAY, theUser.getTimezone().getJavaTimeZone());
				final SchedulingAtomData atomWE = scheduling.getSchedulingAtom(DailyHandler.Weekday.SUNDAY, theUser.getTimezone().getJavaTimeZone());
				myForm.setScheduleW((atomW != null) ? atomW.getTimeFormated(theUser.use24()) : null);
				myForm.setScheduleWE((atomWE != null) ? atomWE.getTimeFormated(theUser.use24()) : null);
			}
		}

		return mapping.findForward("edit");
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final MySrvAbstractForm myForm = (MySrvAbstractForm) form;
		final HttpSession session = request.getSession(true);
		final VObjectData object = VObjectData.getData(SessionTools.getRabbitFromSession(session));
		final Lang userLang = SessionTools.getLangFromSession(session, request);

		String scheduleW = null;
		String scheduleWE = null;
		if (myForm.getSrvModeListener().equals("1")) {
			scheduleW = (myForm.getScheduleW().equals(StringShop.EMPTY_STRING)) ? StringShop.EMPTY_STRING : myForm.getScheduleW();
			scheduleWE = (myForm.getScheduleWE().equals(StringShop.EMPTY_STRING)) ? StringShop.EMPTY_STRING : myForm.getScheduleWE();
		}

		final SubscriptionData theSubscription = SubscriptionData.find(myForm.getSubscriptionId());

		final ActionMessages errors = new ActionMessages();

		if ((theSubscription != null) && (theSubscription.getReference() != null) && theSubscription.getObject().equals(object)) {
			try {
				final Map<String, Object> theSettings = theSubscription.getSettings();

				final Feed feed = Factories.FEED.find(Long.parseLong(theSettings.get(VActionFullHandler.FEED).toString()));
				final Object theLabelSetting = theSettings.get(VActionFullHandler.LABEL);
				final Object theLangSetting = theSettings.get(VActionFullHandler.LANG);
				final Lang theLang = Factories.LANG.findByIsoCode(theLangSetting.toString());

				VActionFullHandler.createOrUpdateSubscription(theSubscription, object, myForm.getSrvNbNews(), scheduleW, scheduleWE, myForm.getSrvFrequencyListening(), theLabelSetting.toString(), feed.getUrl(), Factories.LANG.find(theLang.getId()), getService() == SERVICE.PODCAST);
				errors.add("registerSucceed", new ActionMessage("errors.addRss", DicoTools.dico(userLang, "srv_podcast/subscription_succeed")));
				saveErrors(request, errors);
				return load(mapping, form, request, response);
			} catch (final Exception e) {
				MySrvRssPodcastFullAbstractAction.LOGGER.fatal(e, e);
			}
		}
		errors.add("scenarioNotCreated", new ActionMessage("errors.addTwitter", DicoTools.dico(userLang, "srv_podcast/scenario_not_created")));
		saveErrors(request, errors);
		return config(mapping, form, request, response);

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
				MySrvRssPodcastFullAbstractAction.LOGGER.fatal(e, e);
			}
		}

		return load(mapping, form, request, response);
	}

	protected abstract ServiceFactory.SERVICE getService();

	protected abstract String getApplicationName();

}

package net.violet.mynabaztag.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvHumeurForm;
import net.violet.platform.applications.MoodHandler;
import net.violet.platform.applications.SubscriptionManager;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.HumeurDataFactory;
import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.schedulers.FrequencyHandler;
import net.violet.platform.struts.DispatchActionForLoggedUserWithObject;
import net.violet.platform.util.SessionTools;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class MySrvHumeurAction extends DispatchActionForLoggedUserWithObject {

	private static final Logger LOGGER = Logger.getLogger(MySrvHumeurAction.class);

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final Lang lang = SessionTools.getLangFromSession(session, request);
		final MySrvHumeurForm myForm = (MySrvHumeurForm) form;

		myForm.setLangList(ObjectLangData.getAllObjectLanguages());
		myForm.setFreqSrvList(HumeurDataFactory.generateListFrequence(lang));

		final List<SubscriptionData> theSubscriptions = SubscriptionData.findByApplicationAndObject(Application.NativeApplication.MOOD.getApplication(), theObject);

		String[] checkListLang = { Long.toString(lang.getId()) };
		int freqSrv = 0;
		myForm.setIsReg(theSubscriptions.isEmpty() ? 0 : 1);

		if (!theSubscriptions.isEmpty()) {
			final List<SubscriptionSchedulingData> schedulings = SubscriptionSchedulingData.findAllBySubscriptionAndType(theSubscriptions.get(0), SchedulingType.SCHEDULING_TYPE.RandomWithFrequency);
			if (!schedulings.isEmpty()) {
				final SubscriptionSchedulingData theScheduling = schedulings.get(0);
				final SubscriptionSchedulingSettingsData frequency = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(theScheduling, FrequencyHandler.FREQUENCY);
				final List<String> languages = (List<String>) theSubscriptions.get(0).getSettings().get(MoodHandler.LANGUAGES);

				checkListLang = new String[languages.size()];
				for (int i = 0; i < checkListLang.length; i++) {
					checkListLang[i] = Factories.LANG.findByIsoCode(languages.get(i)).getId().toString();
				}

				freqSrv = FrequencyHandler.Frequency.findByLabel(frequency.getValue()).getMoodFrequency();
			}
		}

		myForm.setFreqSrv(freqSrv);
		myForm.setCheckListLang(checkListLang);

		return mapping.getInputForward();
	}

	public ActionForward activate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvHumeurForm myForm = (MySrvHumeurForm) form;

		final String[] checkListLang = myForm.getCheckListLang();
		final FrequencyHandler.Frequency theFrequency = FrequencyHandler.Frequency.findByMoodFrequency(myForm.getFreqSrv());

		if (theFrequency != null) {
			try {
				MoodHandler.createOrUpdateSubscription(null, VObjectData.getData(theObject), checkListLang, theFrequency);
			} catch (final Exception e) {
				MySrvHumeurAction.LOGGER.fatal(e, e);
			}
		}

		return load(mapping, form, request, response);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvHumeurForm myForm = (MySrvHumeurForm) form;

		final String[] checkListLang = myForm.getCheckListLang();
		final FrequencyHandler.Frequency theFrequency = FrequencyHandler.Frequency.findByMoodFrequency(myForm.getFreqSrv());

		if (theFrequency != null) {
			final SubscriptionData theSubscription = SubscriptionData.findByApplicationAndObject(Application.NativeApplication.MOOD.getApplication(), theObject).get(0);

			try {
				MoodHandler.createOrUpdateSubscription(theSubscription, VObjectData.getData(theObject), checkListLang, theFrequency);
			} catch (final Exception e) {
				MySrvHumeurAction.LOGGER.fatal(e, e);
			}
		}

		return load(mapping, form, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);

		for (final SubscriptionData aSubscription : SubscriptionData.findByApplicationAndObject(Application.NativeApplication.MOOD.getApplication(), theObject)) {
			try {
				SubscriptionManager.deleteSubscription(aSubscription);
			} catch (final Exception e) {
				MySrvHumeurAction.LOGGER.fatal(e, e);
			}
		}

		return load(mapping, form, request, response);
	}
}

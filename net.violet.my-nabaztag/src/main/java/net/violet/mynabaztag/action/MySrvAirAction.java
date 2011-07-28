package net.violet.mynabaztag.action;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvAirForm;
import net.violet.platform.applications.AirHandler;
import net.violet.platform.applications.SubscriptionManager;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.dataobjects.SourceData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData.SchedulingAtomData;
import net.violet.platform.schedulers.DailyHandler;
import net.violet.platform.struts.DispatchActionForLoggedUserWithObject;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class MySrvAirAction extends DispatchActionForLoggedUserWithObject {

	private static final Logger LOGGER = Logger.getLogger(MySrvAirAction.class);

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final HttpSession session = request.getSession(true);
		final User theUser = SessionTools.getUserFromSession(request);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final Lang lang = SessionTools.getLangFromSession(session, request);

		final MySrvAirForm myForm = (MySrvAirForm) form;
		final List<SubscriptionData> subscriptionsList = SubscriptionData.findByApplicationAndObject(Application.NativeApplication.AIR.getApplication(), theObject);

		int language = lang.getId().intValue();
		int passiveAlert = 0;
		int vocal = 0;
		final int isReg = (subscriptionsList.isEmpty()) ? 0 : 1;

		myForm.setHorraire1(StringShop.EMPTY_STRING);
		myForm.setHorraire2(StringShop.EMPTY_STRING);

		// The user subscribed to the service using the new model...
		if (isReg == 1) {
			for (final SubscriptionData subscription : subscriptionsList) {
				final Map<String, Object> settings = subscription.getSettings();
				myForm.setVille(settings.get(AirHandler.SOURCE_SETTING).toString());

				final Object languageSetting = settings.get(AirHandler.LANGUAGE_SETTING);
				final Lang theLang;
				if (languageSetting == null) {
					theLang = theObject.getPreferences().getLangPreferences();
				} else {
					theLang = Factories.LANG.findByIsoCode(languageSetting.toString());
				}

				language = theLang.getId().intValue();

				boolean firstFlash = true;
				for (final SubscriptionSchedulingData scheduling : SubscriptionSchedulingData.findAllBySubscription(subscription)) {
					if (SchedulingType.SCHEDULING_TYPE.Ambiant.equals(scheduling.getType())) {
						passiveAlert = 1;
					}
					if (SchedulingType.SCHEDULING_TYPE.VoiceTrigger.equals(scheduling.getType())) {
						vocal = 1;
					}
					if (SchedulingType.SCHEDULING_TYPE.Daily.equals(scheduling.getType())) {
						final SchedulingAtomData atom = scheduling.getSchedulingAtom(DailyHandler.Weekday.MONDAY, theUser.getTimezone().getJavaTimeZone());
						if (firstFlash) {
							myForm.setHorraire1(atom.getTimeFormated(theUser.use24()));
							firstFlash = false;
						} else {
							myForm.setHorraire2(atom.getTimeFormated(theUser.use24()));
						}
					}
				}

			}
		}

		myForm.setVocal(vocal);
		myForm.setLumiere(passiveAlert);
		myForm.setLangSrv(language);
		myForm.setIsReg(isReg);
		myForm.setVilleList(SourceData.findAllCitiesForSrvAirInLang(lang));
		myForm.setLangList(ObjectLangData.getAllObjectLanguages());

		return mapping.getInputForward();
	}

	public ActionForward activate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvAirForm myForm = (MySrvAirForm) form;
		final Lang theLang = Factories.LANG.find(myForm.getLangSrv());
		final String src = myForm.getVille();
		final String firstFlash = myForm.getHorraire1();
		final String secondFlash = myForm.getHorraire2();

		try {
			AirHandler.createOrUpdateSubscription(null, VObjectData.getData(theObject), myForm.getLumiere() > 0, theLang.getIsoCode(), src, Arrays.asList(firstFlash, secondFlash));
		} catch (final Exception e) {
			MySrvAirAction.LOGGER.fatal(e, e);
		}

		return load(mapping, form, request, response);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvAirForm myForm = (MySrvAirForm) form;
		final Lang theLang = Factories.LANG.find(myForm.getLangSrv());
		final String src = myForm.getVille();
		final String firstFlash = myForm.getHorraire1();
		final String secondFlash = myForm.getHorraire2();

		// the new version
		for (final SubscriptionData subscription : SubscriptionData.findByApplicationAndObject(Application.NativeApplication.AIR.getApplication(), theObject)) {

			try {
				AirHandler.createOrUpdateSubscription(subscription, VObjectData.getData(theObject), myForm.getLumiere() > 0, theLang.getIsoCode(), src, Arrays.asList(firstFlash, secondFlash));
			} catch (final Exception e) {
				MySrvAirAction.LOGGER.fatal(e, e);
			}

		}

		return load(mapping, form, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);

		for (final SubscriptionData aSubscriptionData : SubscriptionData.findByApplicationAndObject(Application.NativeApplication.AIR.getApplication(), theObject)) {
			try {
				SubscriptionManager.deleteSubscription(aSubscriptionData);
			} catch (final Exception e) {
				MySrvAirAction.LOGGER.fatal(e, e);
			}
		}

		return load(mapping, form, request, response);
	}
}

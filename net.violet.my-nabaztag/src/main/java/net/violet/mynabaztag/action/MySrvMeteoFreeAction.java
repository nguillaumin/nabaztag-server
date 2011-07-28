package net.violet.mynabaztag.action;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvMeteoFreeForm;
import net.violet.platform.applications.SubscriptionManager;
import net.violet.platform.applications.WeatherHandler;
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

public final class MySrvMeteoFreeAction extends DispatchActionForLoggedUserWithObject {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(MySrvMeteoFreeAction.class);

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final User theUser = SessionTools.getUserFromSession(request);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final Lang lang = SessionTools.getLangFromSession(session, request);
		final MySrvMeteoFreeForm myForm = (MySrvMeteoFreeForm) form;
		final List<SubscriptionData> subscriptionsList = SubscriptionData.findByApplicationAndObject(Application.NativeApplication.WEATHER.getApplication(), theObject);

		int language = lang.getId().intValue();
		int passiveAlert = 0;
		int vocal = 0;
		final int isReg = (subscriptionsList.isEmpty()) ? 0 : 1;

		myForm.setHorraire1(StringShop.EMPTY_STRING);
		myForm.setHorraire2(StringShop.EMPTY_STRING);

		for (final SubscriptionData aSubscription : subscriptionsList) {

			final Map<String, Object> theSettings = aSubscription.getSettings();
			myForm.setIdVille(theSettings.get(WeatherHandler.SOURCE).toString());
			final ObjectLangData theLang = ObjectLangData.getDefaultObjectLanguage(theSettings.get(WeatherHandler.LANGUAGE).toString());
			language = (int) theLang.getLang_id();

			myForm.setTypedeg(Integer.parseInt(theSettings.get(WeatherHandler.UNIT).toString()));

			boolean firstFlash = true;
			for (final SubscriptionSchedulingData scheduling : SubscriptionSchedulingData.findAllBySubscription(aSubscription)) {
				if (SchedulingType.SCHEDULING_TYPE.Ambiant == scheduling.getType()) {
					passiveAlert = 1;
				}
				if (SchedulingType.SCHEDULING_TYPE.VoiceTrigger == scheduling.getType()) {
					vocal = 1;
				}
				if (SchedulingType.SCHEDULING_TYPE.Daily == scheduling.getType()) {

					final SchedulingAtomData atom = scheduling.getSchedulingAtom(DailyHandler.Weekday.MONDAY, theUser.getTimezone().getJavaTimeZone());
					if (atom != null) {
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

		myForm.setLangSrv(language);
		myForm.setLumiere(passiveAlert);
		myForm.setVocal(vocal);

		myForm.setIsReg(isReg);
		myForm.setVilleList(SourceData.findAllByLang(lang));
		myForm.setLangList(ObjectLangData.getAllObjectLanguages());

		return mapping.getInputForward();
	}

	public ActionForward activate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final User theUser = SessionTools.getUserFromSession(request);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvMeteoFreeForm myForm = (MySrvMeteoFreeForm) form;
		final Lang theLang = Factories.LANG.find(myForm.getLangSrv());

		final String src = myForm.getIdVille();

		int okSrc = -1; // check si la source est ok
		for (final SourceData theData : SourceData.findAllByLang(theLang)) {
			if (theData.getSource_path().equals(src)) {
				okSrc = 1; // source existe bien
				break;
			}
		}

		if (-1 == okSrc) {
			MySrvMeteoFreeAction.LOGGER.fatal("Source not ok, configuration weather = " + src + " user :" + theUser.getId() + " - " + theUser.getUser_email());
			return load(mapping, form, request, response);
		}

		final String firstFlash = myForm.getHorraire1();
		final String secondFlash = myForm.getHorraire2();

		try {
			WeatherHandler.createOrUpdateSubscription(null, VObjectData.getData(theObject), myForm.getLumiere() > 0, theLang.getIsoCode(), src, myForm.getTypedeg(), Arrays.asList(firstFlash, secondFlash));
		} catch (final Exception e) {
			MySrvMeteoFreeAction.LOGGER.fatal(e, e);
		}

		return load(mapping, form, request, response);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final User theUser = SessionTools.getUserFromSession(request);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvMeteoFreeForm myForm = (MySrvMeteoFreeForm) form;
		final Lang theLang = Factories.LANG.find(myForm.getLangSrv());

		final String src = myForm.getIdVille();

		int okSrc = -1; // check si la source est ok
		final List<SourceData> theSource = SourceData.findAllByLang(theLang);
		for (final SourceData theData : theSource) {
			if (theData.getSource_path().equals(src)) {
				okSrc = 1; // source existe bien
				break;
			}
		}
		if (-1 == okSrc) {
			MySrvMeteoFreeAction.LOGGER.fatal("Source not ok, configuration weather = " + src + " user :" + theUser.getId() + " - " + theUser.getUser_email());
			return load(mapping, form, request, response);
		}

		final String firstFlash = myForm.getHorraire1();
		final String secondFlash = myForm.getHorraire2();

		// the new version
		for (final SubscriptionData subscription : SubscriptionData.findByApplicationAndObject(Application.NativeApplication.WEATHER.getApplication(), theObject)) {
			try {
				WeatherHandler.createOrUpdateSubscription(subscription, VObjectData.getData(theObject), myForm.getLumiere() > 0, theLang.getIsoCode(), src, myForm.getTypedeg(), Arrays.asList(firstFlash, secondFlash));
			} catch (final Exception e) {
				MySrvMeteoFreeAction.LOGGER.fatal(e, e);
			}
		}

		return load(mapping, form, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);

		for (final SubscriptionData aSubscription : SubscriptionData.findByApplicationAndObject(Application.NativeApplication.WEATHER.getApplication(), theObject)) {
			try {
				SubscriptionManager.deleteSubscription(aSubscription);
			} catch (final Exception e) {
				MySrvMeteoFreeAction.LOGGER.fatal(e, e);
			}
		}

		return load(mapping, form, request, response);
	}
}

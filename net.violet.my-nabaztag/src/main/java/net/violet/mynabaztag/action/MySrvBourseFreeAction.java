package net.violet.mynabaztag.action;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvBourseFreeForm;
import net.violet.platform.applications.SubscriptionManager;
import net.violet.platform.applications.TradeFreeHandler;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.dataobjects.BourseDataFactory;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData.SchedulingAtomData;
import net.violet.platform.schedulers.DailyHandler;
import net.violet.platform.struts.DispatchActionForLoggedUserWithObject;
import net.violet.platform.util.SessionTools;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class MySrvBourseFreeAction extends DispatchActionForLoggedUserWithObject {

	private static final Logger LOGGER = Logger.getLogger(MySrvBourseFreeAction.class);

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final HttpSession session = request.getSession(true);
		final User theUser = SessionTools.getUserFromSession(request);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final Lang lang = SessionTools.getLangFromSession(session, request);

		final MySrvBourseFreeForm myForm = (MySrvBourseFreeForm) form;

		final List<SubscriptionData> subscriptionsList = SubscriptionData.findByApplicationAndObject(Application.NativeApplication.BOURSE_FREE.getApplication(), theObject);
		final int isReg = (subscriptionsList.isEmpty()) ? 0 : 1;

		int passiveAlert = 0;
		int vocal = 0;
		int weekend = 0;

		myForm.setIndicList(BourseDataFactory.generateListFrequence(lang));

		if (isReg >= 1) {
			for (final SubscriptionData aSubscription : subscriptionsList) {

				final Map<String, Object> theSettings = aSubscription.getSettings();
				myForm.setIndic(theSettings.get(TradeFreeHandler.SOURCE).toString());

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
						final SchedulingAtomData weekEndAtom = scheduling.getSchedulingAtom(DailyHandler.Weekday.SATURDAY, theUser.getTimezone().getJavaTimeZone());
						if (weekEndAtom == null) {
							weekend = 1;
						}
					}
				}
			}
		}

		myForm.setServiceName(Application.NativeApplication.BOURSE_FREE.getApplication().getName());
		myForm.setLumiere(passiveAlert);
		myForm.setVocal(vocal);
		myForm.setWeekend(weekend);
		myForm.setIsReg(isReg);

		return mapping.getInputForward();
	}

	public ActionForward activate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvBourseFreeForm myForm = (MySrvBourseFreeForm) form;

		final String firstFlash = myForm.getHorraire1();
		final String secondFlash = myForm.getHorraire2();

		try {
			TradeFreeHandler.createOrUpdateSubscription(null, VObjectData.getData(theObject), myForm.getLumiere() > 0, myForm.getIndic(), Arrays.asList(firstFlash, secondFlash), !(myForm.getWeekend() > 0));
		} catch (final Exception e) {
			MySrvBourseFreeAction.LOGGER.fatal(e, e);
		}

		return load(mapping, form, request, response);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvBourseFreeForm myForm = (MySrvBourseFreeForm) form;

		final String firstFlash = myForm.getHorraire1();
		final String secondFlash = myForm.getHorraire2();

		// the new version
		for (final SubscriptionData subscription : SubscriptionData.findByApplicationAndObject(Application.NativeApplication.BOURSE_FREE.getApplication(), theObject)) {

			try {
				TradeFreeHandler.createOrUpdateSubscription(subscription, VObjectData.getData(theObject), myForm.getLumiere() > 0, myForm.getIndic(), Arrays.asList(firstFlash, secondFlash), !(myForm.getWeekend() > 0));
			} catch (final Exception e) {
				MySrvBourseFreeAction.LOGGER.fatal(e, e);
			}
		}

		return load(mapping, form, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);

		for (final SubscriptionData aSubscription : SubscriptionData.findByApplicationAndObject(Application.NativeApplication.BOURSE_FREE.getApplication(), theObject)) {
			try {
				SubscriptionManager.deleteSubscription(aSubscription);
			} catch (final Exception e) {
				MySrvBourseFreeAction.LOGGER.fatal(e, e);
			}
		}

		return load(mapping, form, request, response);
	}

}

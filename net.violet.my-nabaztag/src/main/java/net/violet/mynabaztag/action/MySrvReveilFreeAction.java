package net.violet.mynabaztag.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvReveilFreeForm;
import net.violet.platform.applications.ApplicationHandlerHelper;
import net.violet.platform.applications.SubscriptionManager;
import net.violet.platform.applications.ApplicationHandlerHelper.ExternalSettingToolBox;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.schedulers.DailyHandler;
import net.violet.platform.schedulers.DailyWithMediaHandler;
import net.violet.platform.struts.DispatchActionForLoggedUserWithObject;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class MySrvReveilFreeAction extends DispatchActionForLoggedUserWithObject {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(MySrvReveilFreeAction.class);

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvReveilFreeForm myForm = (MySrvReveilFreeForm) form;

		final List<SubscriptionData> theSubscriptions = SubscriptionData.findByApplicationAndObject(Application.NativeApplication.ALARM_CLOCK.getApplication(), theObject);
		myForm.setIsReg(theSubscriptions.isEmpty() ? 0 : 1);

		long musicId = 0;
		String musicName = StringShop.EMPTY_STRING;
		String horaire = StringShop.EMPTY_STRING;

		if (myForm.getIsReg() > 0) {
			final SubscriptionData theSubscription = theSubscriptions.get(0);

			final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.findAllBySubscriptionAndType(theSubscription, SchedulingType.SCHEDULING_TYPE.DailyWithMedia).get(0);
			final SubscriptionSchedulingSettingsData timeSetting = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(theScheduling, DailyHandler.Weekday.MONDAY.getValue());
			final SubscriptionSchedulingSettingsData musicSetting = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(theScheduling, DailyHandler.Weekday.MONDAY.getValue() + DailyWithMediaHandler.MEDIA_SUFFIXE);

			horaire = timeSetting.getValue();

			musicId = Long.parseLong(musicSetting.getValue());
			final Music theMusic = Factories.MUSIC.find(musicId);
			if (theMusic != null) {
				musicName = theMusic.getMusic_name();
			}
		}

		myForm.setMusic_id(musicId);
		myForm.setSonNom(musicName);
		myForm.setHorraire(horaire);

		return mapping.getInputForward();
	}

	public ActionForward activate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvReveilFreeForm myForm = (MySrvReveilFreeForm) form;

		final ApplicationData theApplication = ApplicationData.getData(Application.NativeApplication.ALARM_CLOCK.getApplication());

		final Music theMusic = Factories.MUSIC.find(myForm.getMusic_id());
		if (theMusic != null) {

			final List<Map<String, Object>> theSchedulings = new ArrayList<Map<String, Object>>();
			final CCalendar theTime = new CCalendar(true);
			try {
				theTime.setTimeFormatted(myForm.getHorraire());
				final Map<String, Object> theSched = ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithMediaScheduling(theTime, DailyHandler.Weekday.getAllDays(), theMusic.getId().toString());
				theSchedulings.add(theSched);

				SubscriptionManager.createSubscription(theApplication, VObjectData.getData(theObject), Collections.<String, Object> emptyMap(), theSchedulings, null);
			} catch (final ParseException e1) {
				MySrvReveilFreeAction.LOGGER.fatal(e1);
			} catch (final Exception e) {
				MySrvReveilFreeAction.LOGGER.fatal(e, e);
			}
		}

		return load(mapping, form, request, response);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvReveilFreeForm myForm = (MySrvReveilFreeForm) form;

		final Music theMusic = Factories.MUSIC.find(myForm.getMusic_id());
		if (theMusic != null) {
			final SubscriptionData theSubscription = SubscriptionData.findByApplicationAndObject(Application.NativeApplication.ALARM_CLOCK.getApplication(), theObject).get(0);

			final List<Map<String, Object>> theSchedulings = new ArrayList<Map<String, Object>>();
			final CCalendar theTime = new CCalendar(true);
			try {
				theTime.setTimeFormatted(myForm.getHorraire());
				final Map<String, Object> theSched = ExternalSettingToolBox.buildDailyWithMediaScheduling(theTime, DailyHandler.Weekday.getAllDays(), theMusic.getId().toString());
				theSchedulings.add(theSched);

				SubscriptionManager.updateSubscription(theSubscription, Collections.<String, Object> emptyMap(), theSchedulings, null);
			} catch (final Exception e) {
				MySrvReveilFreeAction.LOGGER.fatal(e, e);
			}
		}

		return load(mapping, form, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);

		for (final SubscriptionData aSubscription : SubscriptionData.findByApplicationAndObject(Application.NativeApplication.ALARM_CLOCK.getApplication(), theObject)) {
			try {
				SubscriptionManager.deleteSubscription(aSubscription);
			} catch (final Exception e) {
				MySrvReveilFreeAction.LOGGER.fatal(e, e);
			}
		}

		return load(mapping, form, request, response);
	}
}

package net.violet.mynabaztag.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvReveilFullForm;
import net.violet.platform.applications.ApplicationHandlerHelper;
import net.violet.platform.applications.SubscriptionManager;
import net.violet.platform.datamodel.Application;
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

public final class MySrvReveilFullAction extends DispatchActionForLoggedUserWithObject {

	private static final Logger LOGGER = Logger.getLogger(MySrvReveilFullAction.class);

	public static enum Reveil_Days {
		MONDAY(Calendar.MONDAY, StringShop.EMPTY_STRING, 0),
		TUESDAY(Calendar.TUESDAY, StringShop.EMPTY_STRING, 0),
		WEDNESDAY(Calendar.WEDNESDAY, StringShop.EMPTY_STRING, 0),
		THURSDAY(Calendar.THURSDAY, StringShop.EMPTY_STRING, 0),
		FRIDAY(Calendar.FRIDAY, StringShop.EMPTY_STRING, 0),
		SATURDAY(Calendar.SATURDAY, StringShop.EMPTY_STRING, 0),
		SUNDAY(Calendar.SUNDAY, StringShop.EMPTY_STRING, 0);

		private final int calendar;
		private String schedule;
		private long music_id;

		Reveil_Days(int i, String schedule, long music) {
			this.calendar = i;
			this.schedule = schedule;
			this.music_id = music;
		}

		public int getCalendar() {
			return this.calendar;
		}

		public String getSchedule() {
			return this.schedule;
		}

		public long getMusic() {
			return this.music_id;
		}

		public void setValues(String schedule, long music) {
			this.schedule = schedule;
			this.music_id = music;
		}
	}

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		// TODO: dans la JSP, 7 appels à SrvSoundAction sont effectués via le
		// javascript (class='songPicker') - Voir pour une amélioration
		// ultérieure
		final MySrvReveilFullForm myForm = (MySrvReveilFullForm) form;

		final Application reminder = Application.NativeApplication.REMINDER.getApplication();
		final List<SubscriptionData> subscriptions = SubscriptionData.findByApplicationAndObject(reminder, theObject);

		myForm.setServiceName(reminder.getProfile().getTitle());
		myForm.setIsReg(subscriptions.isEmpty() ? 0 : 1);

		for (final SubscriptionData subscription : subscriptions) {
			final List<SubscriptionSchedulingData> schedulings = SubscriptionSchedulingData.findAllBySubscriptionAndType(subscription, SchedulingType.SCHEDULING_TYPE.DailyWithMedia);
			for (final SubscriptionSchedulingData scheduling : schedulings) {
				final List<SubscriptionSchedulingSettingsData> settings = SubscriptionSchedulingSettingsData.findAllBySubscriptionScheduling(scheduling);
				for (final SubscriptionSchedulingSettingsData setting : settings) {
					if (DailyHandler.Weekday.MONDAY.getValue().equals(setting.getKey())) {
						final SubscriptionSchedulingSettingsData musicSetting = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(scheduling, DailyHandler.Weekday.MONDAY.getValue() + DailyWithMediaHandler.MEDIA_SUFFIXE);
						myForm.setMusic_id1((musicSetting == null) ? 0 : Integer.parseInt(musicSetting.getValue()));
						myForm.setSonNom1((musicSetting == null) ? StringShop.EMPTY_STRING : Factories.MUSIC.find(Integer.parseInt(musicSetting.getValue())).getMusic_name());
						myForm.setHorraire1(setting.getValue());
					}
					if (DailyHandler.Weekday.TUESDAY.getValue().equals(setting.getKey())) {
						final SubscriptionSchedulingSettingsData musicSetting = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(scheduling, DailyHandler.Weekday.TUESDAY.getValue() + DailyWithMediaHandler.MEDIA_SUFFIXE);
						myForm.setMusic_id2((musicSetting == null) ? 0 : Integer.parseInt(musicSetting.getValue()));
						myForm.setSonNom2((musicSetting == null) ? StringShop.EMPTY_STRING : Factories.MUSIC.find(Integer.parseInt(musicSetting.getValue())).getMusic_name());
						myForm.setHorraire2(setting.getValue());
					}
					if (DailyHandler.Weekday.WEDNESDAY.getValue().equals(setting.getKey())) {
						final SubscriptionSchedulingSettingsData musicSetting = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(scheduling, DailyHandler.Weekday.WEDNESDAY.getValue() + DailyWithMediaHandler.MEDIA_SUFFIXE);
						myForm.setMusic_id3((musicSetting == null) ? 0 : Integer.parseInt(musicSetting.getValue()));
						myForm.setSonNom3((musicSetting == null) ? StringShop.EMPTY_STRING : Factories.MUSIC.find(Integer.parseInt(musicSetting.getValue())).getMusic_name());
						myForm.setHorraire3(setting.getValue());
					}
					if (DailyHandler.Weekday.THURSDAY.getValue().equals(setting.getKey())) {
						final SubscriptionSchedulingSettingsData musicSetting = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(scheduling, DailyHandler.Weekday.THURSDAY.getValue() + DailyWithMediaHandler.MEDIA_SUFFIXE);
						myForm.setMusic_id4((musicSetting == null) ? 0 : Integer.parseInt(musicSetting.getValue()));
						myForm.setSonNom4((musicSetting == null) ? StringShop.EMPTY_STRING : Factories.MUSIC.find(Integer.parseInt(musicSetting.getValue())).getMusic_name());
						myForm.setHorraire4(setting.getValue());
					}
					if (DailyHandler.Weekday.FRIDAY.getValue().equals(setting.getKey())) {
						final SubscriptionSchedulingSettingsData musicSetting = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(scheduling, DailyHandler.Weekday.FRIDAY.getValue() + DailyWithMediaHandler.MEDIA_SUFFIXE);
						myForm.setMusic_id5((musicSetting == null) ? 0 : Integer.parseInt(musicSetting.getValue()));
						myForm.setSonNom5((musicSetting == null) ? StringShop.EMPTY_STRING : Factories.MUSIC.find(Integer.parseInt(musicSetting.getValue())).getMusic_name());
						myForm.setHorraire5(setting.getValue());
					}
					if (DailyHandler.Weekday.SATURDAY.getValue().equals(setting.getKey())) {
						final SubscriptionSchedulingSettingsData musicSetting = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(scheduling, DailyHandler.Weekday.SATURDAY.getValue() + DailyWithMediaHandler.MEDIA_SUFFIXE);
						myForm.setMusic_id6((musicSetting == null) ? 0 : Integer.parseInt(musicSetting.getValue()));
						myForm.setSonNom6((musicSetting == null) ? StringShop.EMPTY_STRING : Factories.MUSIC.find(Integer.parseInt(musicSetting.getValue())).getMusic_name());
						myForm.setHorraire6(setting.getValue());
					}
					if (DailyHandler.Weekday.SUNDAY.getValue().equals(setting.getKey())) {
						final SubscriptionSchedulingSettingsData musicSetting = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(scheduling, DailyHandler.Weekday.SUNDAY.getValue() + DailyWithMediaHandler.MEDIA_SUFFIXE);
						myForm.setMusic_id7((musicSetting == null) ? 0 : Integer.parseInt(musicSetting.getValue()));
						myForm.setSonNom7((musicSetting == null) ? StringShop.EMPTY_STRING : Factories.MUSIC.find(Integer.parseInt(musicSetting.getValue())).getMusic_name());
						myForm.setHorraire7(setting.getValue());
					}
				}
			}
		}
		return mapping.getInputForward();
	}

	public ActionForward activate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);

		final MySrvReveilFullForm myForm = (MySrvReveilFullForm) form;

		final ApplicationData reminder = ApplicationData.getData(Application.NativeApplication.REMINDER.getApplication());

		final List<Map<String, Object>> theSchedulings = new ArrayList<Map<String, Object>>();
		final Map<String, Object> theSched = new HashMap<String, Object>();
		theSched.put(SchedulingType.TYPE_KEY, SchedulingType.SCHEDULING_TYPE.DailyWithMedia.getLabel());

		try {
			final CCalendar theTime = new CCalendar(true);
			if ((myForm.getHorraire1() != null) && !myForm.getHorraire1().equals(StringShop.EMPTY_STRING)) {
				theTime.setTimeFormatted(myForm.getHorraire1());
				theSched.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithMediaScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.MONDAY), String.valueOf(myForm.getMusic_id1())));
			}
			if ((myForm.getHorraire2() != null) && !myForm.getHorraire2().equals(StringShop.EMPTY_STRING)) {
				theTime.setTimeFormatted(myForm.getHorraire2());
				theSched.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithMediaScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.TUESDAY), String.valueOf(myForm.getMusic_id2())));
			}
			if ((myForm.getHorraire3() != null) && !myForm.getHorraire3().equals(StringShop.EMPTY_STRING)) {
				theTime.setTimeFormatted(myForm.getHorraire3());
				theSched.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithMediaScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.WEDNESDAY), String.valueOf(myForm.getMusic_id3())));

			}
			if ((myForm.getHorraire4() != null) && !myForm.getHorraire4().equals(StringShop.EMPTY_STRING)) {
				theTime.setTimeFormatted(myForm.getHorraire4());
				theSched.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithMediaScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.THURSDAY), String.valueOf(myForm.getMusic_id4())));

			}
			if ((myForm.getHorraire5() != null) && !myForm.getHorraire5().equals(StringShop.EMPTY_STRING)) {
				theTime.setTimeFormatted(myForm.getHorraire5());
				theSched.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithMediaScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.FRIDAY), String.valueOf(myForm.getMusic_id5())));

			}
			if ((myForm.getHorraire6() != null) && !myForm.getHorraire6().equals(StringShop.EMPTY_STRING)) {
				theTime.setTimeFormatted(myForm.getHorraire6());
				theSched.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithMediaScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.SATURDAY), String.valueOf(myForm.getMusic_id6())));

			}
			if ((myForm.getHorraire7() != null) && !myForm.getHorraire7().equals(StringShop.EMPTY_STRING)) {
				theTime.setTimeFormatted(myForm.getHorraire7());
				theSched.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithMediaScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.SUNDAY), String.valueOf(myForm.getMusic_id7())));

			}

			theSchedulings.add(theSched);

			SubscriptionManager.createSubscription(reminder, VObjectData.getData(theObject), Collections.<String, Object> emptyMap(), theSchedulings, null);
		} catch (final ParseException e) {
			MySrvReveilFullAction.LOGGER.fatal(e, e);
		} catch (final Exception e) {
			MySrvReveilFullAction.LOGGER.fatal(e);
		}

		return load(mapping, form, request, response);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);

		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvReveilFullForm myForm = (MySrvReveilFullForm) form;

		Reveil_Days.MONDAY.setValues(myForm.getHorraire1(), myForm.getMusic_id1());
		Reveil_Days.TUESDAY.setValues(myForm.getHorraire2(), myForm.getMusic_id2());
		Reveil_Days.WEDNESDAY.setValues(myForm.getHorraire3(), myForm.getMusic_id3());
		Reveil_Days.THURSDAY.setValues(myForm.getHorraire4(), myForm.getMusic_id4());
		Reveil_Days.FRIDAY.setValues(myForm.getHorraire5(), myForm.getMusic_id5());
		Reveil_Days.SATURDAY.setValues(myForm.getHorraire6(), myForm.getMusic_id6());
		Reveil_Days.SUNDAY.setValues(myForm.getHorraire7(), myForm.getMusic_id7());

		final Application reminder = Application.NativeApplication.REMINDER.getApplication();
		final List<SubscriptionData> subscriptions = SubscriptionData.findByApplicationAndObject(reminder, theObject);

		for (final SubscriptionData subscription : subscriptions) {

			final List<Map<String, Object>> theSchedulings = new ArrayList<Map<String, Object>>();
			final Map<String, Object> theSched = new HashMap<String, Object>();
			theSched.put(SchedulingType.TYPE_KEY, SchedulingType.SCHEDULING_TYPE.DailyWithMedia.getLabel());

			try {
				final CCalendar theTime = new CCalendar(true);
				if ((myForm.getHorraire1() != null) && !myForm.getHorraire1().equals(StringShop.EMPTY_STRING)) {
					theTime.setTimeFormatted(myForm.getHorraire1());
					theSched.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithMediaScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.MONDAY), String.valueOf(myForm.getMusic_id1())));
				}
				if ((myForm.getHorraire2() != null) && !myForm.getHorraire2().equals(StringShop.EMPTY_STRING)) {
					theTime.setTimeFormatted(myForm.getHorraire2());
					theSched.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithMediaScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.TUESDAY), String.valueOf(myForm.getMusic_id2())));
				}
				if ((myForm.getHorraire3() != null) && !myForm.getHorraire3().equals(StringShop.EMPTY_STRING)) {
					theTime.setTimeFormatted(myForm.getHorraire3());
					theSched.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithMediaScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.WEDNESDAY), String.valueOf(myForm.getMusic_id3())));

				}
				if ((myForm.getHorraire4() != null) && !myForm.getHorraire4().equals(StringShop.EMPTY_STRING)) {
					theTime.setTimeFormatted(myForm.getHorraire4());
					theSched.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithMediaScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.THURSDAY), String.valueOf(myForm.getMusic_id4())));

				}
				if ((myForm.getHorraire5() != null) && !myForm.getHorraire5().equals(StringShop.EMPTY_STRING)) {
					theTime.setTimeFormatted(myForm.getHorraire5());
					theSched.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithMediaScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.FRIDAY), String.valueOf(myForm.getMusic_id5())));

				}
				if ((myForm.getHorraire6() != null) && !myForm.getHorraire6().equals(StringShop.EMPTY_STRING)) {
					theTime.setTimeFormatted(myForm.getHorraire6());
					theSched.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithMediaScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.SATURDAY), String.valueOf(myForm.getMusic_id6())));

				}
				if ((myForm.getHorraire7() != null) && !myForm.getHorraire7().equals(StringShop.EMPTY_STRING)) {
					theTime.setTimeFormatted(myForm.getHorraire7());
					theSched.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithMediaScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.SUNDAY), String.valueOf(myForm.getMusic_id7())));

				}

				theSchedulings.add(theSched);

				SubscriptionManager.updateSubscription(subscription, Collections.<String, Object> emptyMap(), theSchedulings, null);
			} catch (final Exception e) {
				MySrvReveilFullAction.LOGGER.fatal(e, e);
			}
		}

		return load(mapping, form, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);

		final Application reminder = Application.NativeApplication.REMINDER.getApplication();
		final List<SubscriptionData> subscriptions = SubscriptionData.findByApplicationAndObject(reminder, theObject);

		for (final SubscriptionData aSubscription : subscriptions) {
			try {
				SubscriptionManager.deleteSubscription(aSubscription);
			} catch (final Exception e) {
				MySrvReveilFullAction.LOGGER.fatal(e, e);
			}
		}

		return load(mapping, form, request, response);
	}

}

package net.violet.mynabaztag.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvWebRadioFreeConfigForm;
import net.violet.mynabaztag.form.MySrvWebRadioFreePlayerForm;
import net.violet.platform.applications.ApplicationHandlerHelper;
import net.violet.platform.applications.SubscriptionManager;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ApplicationSettingData;
import net.violet.platform.dataobjects.PeriodWebRadioDataFactory;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.application.factories.AbstractMessageFactory;
import net.violet.platform.message.application.factories.WebRadioMessageFactory;
import net.violet.platform.message.application.factories.AbstractMessageFactory.Message2Send;
import net.violet.platform.schedulers.DailyHandler;
import net.violet.platform.schedulers.DailyWithMediaHandler;
import net.violet.platform.struts.DispatchActionWithLog;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class MySrvWebRadioFreeAction extends DispatchActionWithLog {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(MySrvWebRadioFreeAction.class);

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final HttpSession session = request.getSession(true);
		ActionForward forward = null;

		final User theUser = SessionTools.getUserFromSession(request);
		final Lang lang = SessionTools.getLangFromSession(session, request);
		final VObject theObject = SessionTools.getRabbitFromSession(session);

		/**
		 * Check if the user really exists
		 */
		if (theUser == null) {
			return mapping.findForward("login");
		}
		/**
		 * Check if the user really has a rabbit
		 */
		if (theObject == null) {
			return mapping.findForward("login");
		}

		String haveV1 = "false";
		if (theObject.getHardware() == HARDWARE.V1) {
			haveV1 = "true";
		}

		if (form instanceof MySrvWebRadioFreeConfigForm) {

			final MySrvWebRadioFreeConfigForm myForm = (MySrvWebRadioFreeConfigForm) form;

			final ApplicationData application = ApplicationData.findById(myForm.getWradioId());

			// test pour savoir si le lapin est un V1
			myForm.setIsV1(haveV1);
			myForm.setPeriodList(PeriodWebRadioDataFactory.generateListFrequence(lang));

			myForm.setIsRegistered((SubscriptionData.findByApplicationAndObject(application.getReference(), theObject).isEmpty()) ? "" : "true");

			if (!myForm.getDispatch().equals(StringShop.EMPTY_STRING)) {
				try {
					forward = dispatchMethod(mapping, myForm, request, response, myForm.getDispatch());
				} catch (final Exception e) {
					MySrvWebRadioFreeAction.LOGGER.fatal(e, e);
				}
			}
		}

		if (form instanceof MySrvWebRadioFreePlayerForm) {

			final MySrvWebRadioFreePlayerForm myForm = (MySrvWebRadioFreePlayerForm) form;

			final ApplicationData application = ApplicationData.findById(myForm.getWradioId());

			// test pour savoir si le lapin est v1
			myForm.setIsV1(haveV1);

			myForm.setRabbitName(theObject.getObject_login());

			myForm.setWebRadioUrl(ApplicationSettingData.findByApplicationAndKey(application, "url").getValue());

			if (!myForm.getDispatch().equals(StringShop.EMPTY_STRING) && !myForm.getDispatch().equals("load")) {
				try {
					forward = dispatchMethod(mapping, myForm, request, response, myForm.getDispatch());
				} catch (final Exception e) {
					MySrvWebRadioFreeAction.LOGGER.fatal(e, e);
				}
			}
		}

		if ((forward != null) && !forward.getName().equals(StringShop.EMPTY_STRING)) {
			forward = mapping.findForward(forward.getName());
		} else {
			forward = mapping.getInputForward();
		}

		return forward;
	}

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final User theUser = SessionTools.getUserFromSession(request);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvWebRadioFreeConfigForm myForm = (MySrvWebRadioFreeConfigForm) form;

		/**
		 * Check if the user really exists
		 */
		if (theUser == null) {
			return mapping.findForward("login");
		}
		/**
		 * Check if the user really has a rabbit
		 */
		if (theObject == null) {
			return mapping.findForward("login");
		}

		final int wradioId = myForm.getWradioId();

		final String[] checkListHours = myForm.getCheckListHours();
		final String[] listeningPeriod = myForm.getListeningPeriodeSelected();

		final ApplicationData application = ApplicationData.findById(wradioId);

		myForm.setApplicationId(wradioId);

		final List<SubscriptionData> subscriptions = SubscriptionData.findByApplicationAndObject(application.getReference(), theObject);

		for (final SubscriptionData subscriptionData : subscriptions) {
			final List<SubscriptionSchedulingData> schedulings = SubscriptionSchedulingData.findAllBySubscriptionAndType(subscriptionData, SchedulingType.SCHEDULING_TYPE.DailyWithDuration);

			for (final SubscriptionSchedulingData scheduling : schedulings) {

				for (final SubscriptionSchedulingSettingsData setting : SubscriptionSchedulingSettingsData.findAllBySubscriptionScheduling(scheduling)) {

					if (DailyHandler.Weekday.MONDAY.getValue().equals(setting.getKey())) {
						checkListHours[0] = setting.getValue();
						final int duration = Integer.parseInt(SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(scheduling, DailyHandler.Weekday.MONDAY.getValue() + DailyWithMediaHandler.MEDIA_SUFFIXE).getValue()) * 60000;
						listeningPeriod[0] = String.valueOf(duration);
					}
					if (DailyHandler.Weekday.TUESDAY.getValue().equals(setting.getKey())) {
						checkListHours[1] = setting.getValue();
						final int duration = Integer.parseInt(SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(scheduling, DailyHandler.Weekday.TUESDAY.getValue() + DailyWithMediaHandler.MEDIA_SUFFIXE).getValue()) * 60000;
						listeningPeriod[1] = String.valueOf(duration);
					}
					if (DailyHandler.Weekday.WEDNESDAY.getValue().equals(setting.getKey())) {
						checkListHours[2] = setting.getValue();
						final int duration = Integer.parseInt(SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(scheduling, DailyHandler.Weekday.WEDNESDAY.getValue() + DailyWithMediaHandler.MEDIA_SUFFIXE).getValue()) * 60000;
						listeningPeriod[2] = String.valueOf(duration);
					}
					if (DailyHandler.Weekday.THURSDAY.getValue().equals(setting.getKey())) {
						checkListHours[3] = setting.getValue();
						final int duration = Integer.parseInt(SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(scheduling, DailyHandler.Weekday.THURSDAY.getValue() + DailyWithMediaHandler.MEDIA_SUFFIXE).getValue()) * 60000;
						listeningPeriod[3] = String.valueOf(duration);
					}
					if (DailyHandler.Weekday.FRIDAY.getValue().equals(setting.getKey())) {
						checkListHours[4] = setting.getValue();
						final int duration = Integer.parseInt(SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(scheduling, DailyHandler.Weekday.FRIDAY.getValue() + DailyWithMediaHandler.MEDIA_SUFFIXE).getValue()) * 60000;
						listeningPeriod[4] = String.valueOf(duration);
					}
					if (DailyHandler.Weekday.SATURDAY.getValue().equals(setting.getKey())) {
						checkListHours[5] = setting.getValue();
						final int duration = Integer.parseInt(SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(scheduling, DailyHandler.Weekday.SATURDAY.getValue() + DailyWithMediaHandler.MEDIA_SUFFIXE).getValue()) * 60000;
						listeningPeriod[5] = String.valueOf(duration);
					}
					if (DailyHandler.Weekday.SUNDAY.getValue().equals(setting.getKey())) {
						checkListHours[6] = setting.getValue();
						final int duration = Integer.parseInt(SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(scheduling, DailyHandler.Weekday.SUNDAY.getValue() + DailyWithMediaHandler.MEDIA_SUFFIXE).getValue()) * 60000;
						listeningPeriod[6] = String.valueOf(duration);
					}
				}
			}
		}

		myForm.setCheckListHours(checkListHours);
		myForm.setListeningPeriodeSelected(listeningPeriod);

		return new ActionForward(StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, false);
	}

	/**
	 * Add several times to a web radio
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward activate(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final User theUser = SessionTools.getUserFromSession(request);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvWebRadioFreeConfigForm myForm = (MySrvWebRadioFreeConfigForm) form;
		/**
		 * Check if the user really exists
		 */
		if (theUser == null) {
			return mapping.findForward("login");
		}
		/**
		 * Check if the user really has a rabbit
		 */
		if (theObject == null) {
			return mapping.findForward("login");
		}

		final int wradioId = myForm.getWradioId();

		final String[] listHours = myForm.getCheckListHours();
		final String[] listeningPeriod = myForm.getListeningPeriodeSelected();

		if (listHours.length > 0) {

			final ApplicationData application = ApplicationData.findById(wradioId);

			// creates schedulings
			final List<Map<String, Object>> theSchedulings = new ArrayList<Map<String, Object>>();

			final Map<String, Object> scheduling = new HashMap<String, Object>();
			scheduling.put("type", SchedulingType.SCHEDULING_TYPE.DailyWithDuration.getLabel());
			try {
				final CCalendar theTime = new CCalendar(theObject.getTimeZone().getJavaTimeZone());

				for (int index = 0; index < listHours.length; ++index) {
					if (!listHours[index].equals(StringShop.EMPTY_STRING) && !listHours[index].contains("null")) {
						if (index == 0) {
							theTime.setTimeFormatted(listHours[index]);
							final int duration = Integer.parseInt(listeningPeriod[index]) / 60000;
							scheduling.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithDurationScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.MONDAY), duration));
						}
						if (index == 1) {
							theTime.setTimeFormatted(listHours[index]);
							final int duration = Integer.parseInt(listeningPeriod[index]) / 60000;
							scheduling.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithDurationScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.TUESDAY), duration));
						}
						if (index == 2) {
							theTime.setTimeFormatted(listHours[index]);
							final int duration = Integer.parseInt(listeningPeriod[index]) / 60000;
							scheduling.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithDurationScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.WEDNESDAY), duration));
						}
						if (index == 3) {
							theTime.setTimeFormatted(listHours[index]);
							final int duration = Integer.parseInt(listeningPeriod[index]) / 60000;
							scheduling.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithDurationScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.THURSDAY), duration));
						}
						if (index == 4) {
							theTime.setTimeFormatted(listHours[index]);
							final int duration = Integer.parseInt(listeningPeriod[index]) / 60000;
							scheduling.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithDurationScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.FRIDAY), duration));
						}
						if (index == 5) {
							theTime.setTimeFormatted(listHours[index]);
							final int duration = Integer.parseInt(listeningPeriod[index]) / 60000;
							scheduling.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithDurationScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.SATURDAY), duration));
						}
						if (index == 6) {
							theTime.setTimeFormatted(listHours[index]);
							final int duration = Integer.parseInt(listeningPeriod[index]) / 60000;
							scheduling.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithDurationScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.SUNDAY), duration));
						}
					}
				}

				theSchedulings.add(scheduling);

				SubscriptionManager.createSubscription(application, VObjectData.getData(theObject), Collections.<String, Object> emptyMap(), theSchedulings, null);
			} catch (final Exception e) {
				MySrvWebRadioFreeAction.LOGGER.fatal(e, e);
			}

		}

		return new ActionForward(StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, false);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final MySrvWebRadioFreeConfigForm myForm = (MySrvWebRadioFreeConfigForm) form;

		final VObject theObject = SessionTools.getRabbitFromSession(session);
		if (theObject == null) {
			return mapping.findForward("login");
		}

		final int wradioId = myForm.getWradioId();

		final String[] listHours = myForm.getCheckListHours();
		final String[] listeningPeriod = myForm.getListeningPeriodeSelected();

		final ApplicationData application = ApplicationData.findById(wradioId);

		if (listHours.length > 0) {
			for (final SubscriptionData subscription : SubscriptionData.findByApplicationAndObject(application.getReference(), theObject)) {

				// creates schedulings
				final List<Map<String, Object>> theSchedulings = new ArrayList<Map<String, Object>>();

				final Map<String, Object> scheduling = new HashMap<String, Object>();
				scheduling.put("type", SchedulingType.SCHEDULING_TYPE.DailyWithDuration.getLabel());
				try {
					final CCalendar theTime = new CCalendar(theObject.getTimeZone().getJavaTimeZone());

					for (int index = 0; index < listHours.length; ++index) {
						if (!listHours[index].equals(StringShop.EMPTY_STRING) && !listHours[index].contains("null")) {
							if (index == 0) {
								theTime.setTimeFormatted(listHours[index]);
								final int duration = Integer.parseInt(listeningPeriod[index]) / 60000;
								scheduling.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithDurationScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.MONDAY), duration));
							}
							if (index == 1) {
								theTime.setTimeFormatted(listHours[index]);
								final int duration = Integer.parseInt(listeningPeriod[index]) / 60000;
								scheduling.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithDurationScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.TUESDAY), duration));
							}
							if (index == 2) {
								theTime.setTimeFormatted(listHours[index]);
								final int duration = Integer.parseInt(listeningPeriod[index]) / 60000;
								scheduling.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithDurationScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.WEDNESDAY), duration));
							}
							if (index == 3) {
								theTime.setTimeFormatted(listHours[index]);
								final int duration = Integer.parseInt(listeningPeriod[index]) / 60000;
								scheduling.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithDurationScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.THURSDAY), duration));
							}
							if (index == 4) {
								theTime.setTimeFormatted(listHours[index]);
								final int duration = Integer.parseInt(listeningPeriod[index]) / 60000;
								scheduling.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithDurationScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.FRIDAY), duration));
							}
							if (index == 5) {
								theTime.setTimeFormatted(listHours[index]);
								final int duration = Integer.parseInt(listeningPeriod[index]) / 60000;
								scheduling.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithDurationScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.SATURDAY), duration));
							}
							if (index == 6) {
								theTime.setTimeFormatted(listHours[index]);
								final int duration = Integer.parseInt(listeningPeriod[index]) / 60000;
								scheduling.putAll(ApplicationHandlerHelper.ExternalSettingToolBox.buildDailyWithDurationScheduling(theTime, Collections.singletonList(DailyHandler.Weekday.SUNDAY), duration));
							}
						}
					}

					theSchedulings.add(scheduling);
					SubscriptionManager.updateSubscription(subscription, Collections.<String, Object> emptyMap(), theSchedulings, null);
				} catch (final Exception e) {
					MySrvWebRadioFreeAction.LOGGER.fatal(e, e);
				}
			}
		}

		return new ActionForward(StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, false);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		if (theObject == null) {
			return mapping.findForward("login");
		}

		final MySrvWebRadioFreeConfigForm myForm = (MySrvWebRadioFreeConfigForm) form;

		final ApplicationData application = ApplicationData.findById(myForm.getWradioId());

		for (final SubscriptionData aSubscription : SubscriptionData.findByApplicationAndObject(application.getReference(), theObject)) {
			try {
				SubscriptionManager.deleteSubscription(aSubscription);
			} catch (final Exception e) {
				MySrvWebRadioFreeAction.LOGGER.fatal(e, e);
			}
		}

		return new ActionForward(StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, false);
	}

	public ActionForward playNow(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		if (theObject == null) {
			return mapping.findForward("login");
		}

		final MySrvWebRadioFreePlayerForm myForm = (MySrvWebRadioFreePlayerForm) form;

		try {
			MySrvWebRadioFreeAction.sendWebradio(myForm.getWebRadioUrl(), theObject);
		} catch (final IllegalArgumentException e) {
			MySrvWebRadioFreeAction.LOGGER.fatal(e, e);
		}

		return mapping.findForward("genericOK");
	}

	public ActionForward stopPlay(ActionMapping mapping, @SuppressWarnings("unused") ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		if (theObject == null) {
			return mapping.findForward("login");
		}

		try {
			MySrvWebRadioFreeAction.stopWebradio(theObject);
		} catch (final IllegalArgumentException e) {
			MySrvWebRadioFreeAction.LOGGER.fatal(e, e);
		}

		return mapping.findForward("genericOK");
	}

	/**
	 * Fonction permettant d'envoyer une URL de streaming sur un objet.
	 * 
	 * @param url url de la radio
	 * @param inRecipient l'objet qui lit la radio
	 */
	private static void sendWebradio(String url, VObject inRecipient) {
		// Stop anything already playing (cannot play 2 things at once)
		MySrvWebRadioFreeAction.stopWebradio(inRecipient);

		AbstractMessageFactory.sendMessage(WebRadioMessageFactory.getMessage2Send(url, inRecipient, null, 0, System.currentTimeMillis()));
	}

	private static final int QUEUE_TTL_FIVE_MINUTES = 300;

	/**
	 * Fonction permettant de stopper le streaming sur un objet.
	 * 
	 * @param inRecipient
	 * @param inListeningTime
	 */
	private static void stopWebradio(final VObject inRecipient) {
		AbstractMessageFactory.sendMessage(Collections.singletonList((Message2Send) new WebRadioMessageFactory.Message2SendWR(null, inRecipient, null) {

			@Override
			public MessageDraft generateMessageDraft(MessageDraft inMessageDraft) {
				inMessageDraft.addStreamId("stop");
				inMessageDraft.addStreamStop("stop");
				inMessageDraft.setTTLInSecond(QUEUE_TTL_FIVE_MINUTES);

				return inMessageDraft;
			}
		}));
	}
}

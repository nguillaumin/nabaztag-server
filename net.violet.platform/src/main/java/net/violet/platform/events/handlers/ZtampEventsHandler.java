package net.violet.platform.events.handlers;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.violet.platform.applets.AppletDispatcher;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.FilesImpl;
import net.violet.platform.datamodel.InterruptionLogImpl;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.FeedItemData;
import net.violet.platform.dataobjects.GroupData;
import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.dataobjects.TtsLangData;
import net.violet.platform.dataobjects.TtsVoiceData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.events.TriggerEvent;
import net.violet.platform.events.ZtampEvent;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.MessageServices;
import net.violet.platform.message.MessageServices.Body;
import net.violet.platform.object.Provisionning;
import net.violet.platform.schedulers.DailyHandler;
import net.violet.platform.schedulers.InteractionTriggerHandler;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.DicoTools;
import net.violet.platform.voice.TTSServices;
import net.violet.platform.xmpp.JabberMessageFactory;

import org.apache.log4j.Logger;

class ZtampEventsHandler {

	private static final Logger LOGGER = Logger.getLogger(ZtampEventsHandler.class);
	private static final String DUPLICATED_ZTAMP = "net.violet.application.messages.duplicated_ztamp";
	private static final ApplicationData APPLICATION_INSTALLER = ApplicationData.findByName("net.violet.js.applicationinstaller");

	static void processEvent(ZtampEvent event) {

		final VObjectData theReader = event.getReader();
		VObjectData ztampData = event.getEmitter();

		if ((ztampData == null) || !ztampData.isValid()) {
			ztampData = Provisionning.provisionRfid(theReader, event.getEmitterSerial());
		}

		if (ztampData == null) {
			ZtampEventsHandler.LOGGER.fatal("Provisionning failed for reader " + theReader.getSerial() + " and ztamp " + event.getEmitterSerial());
			InterruptionLogImpl.insert(theReader.getReference(), InterruptionLogImpl.WHAT_OPTION.RFID, "Failure : sn " + event.getEmitterSerial());
			return;
		}

		if (ztampData.isDuplicated()) {
			InterruptionLogImpl.insert(theReader.getReference(), InterruptionLogImpl.WHAT_OPTION.RFID, "serial duplicated" + ztampData.getSerial());
			final ObjectLangData lang = theReader.getPreferences().getLang();
			final String title = DicoTools.dico(lang.getReference(), ZtampEventsHandler.DUPLICATED_ZTAMP);
			final TtsVoiceData inTTSVoice = TtsVoiceData.findTtsVoiceByLang(TtsLangData.getDefaultTtsLanguage(lang.getLang_iso_code()));
			final Files theFile = TTSServices.getDefaultInstance().postTTS(title, false, false, inTTSVoice.getReference());
			final Body[] bodies = new Body[] { new Body(theFile, null, false) };
			final MessageDraft msgDraft = MessageServices.createMessageDraft(theReader.getReference(), title, bodies, null);
			MessageServices.sendUsingXmpp(msgDraft, JabberMessageFactory.IDLE_MODE);
			return;
		}

		InterruptionLogImpl.insert(theReader.getReference(), InterruptionLogImpl.WHAT_OPTION.RFID, ztampData.getSerial());
		theReader.setLastActivityTime();
		ztampData.setLastActivityTime();

		ZtampEventsHandler.processSubscriptions(ztampData, event);

		ZtampEventsHandler.processGroups(ztampData, theReader);

	}

	private static void processGroups(VObjectData ztampData, VObjectData theReader) {
		final List<GroupData> groups = GroupData.findAllByMember(ztampData);
		for (final GroupData aGroup : groups) {
			for (final FeedItemData anItem : FeedItemData.findAllByFeed(aGroup.getFeed())) {
				MessageServices.sendServiceMessage(anItem.getContents().toArray(new FilesImpl[0]), theReader.getReference(), anItem.getTitle(), null, 60, Palette.FLASH, null, false, JabberMessageFactory.IDLE_MODE);
			}
		}
	}

	private static void processSubscriptions(VObjectData ztampData, ZtampEvent inEvent) {
		final List<SubscriptionSchedulingData> triggeredSchedulings = SubscriptionSchedulingData.findAllByObjectAndType(ztampData, SchedulingType.SCHEDULING_TYPE.InteractionTrigger);

		if (triggeredSchedulings.isEmpty()) {

			// find if they are ANY subscriptions for this ZTAMP
			final List<SubscriptionData> globalSubscriptions = SubscriptionData.findAllByObject(ztampData.getReference());

			if (globalSubscriptions.isEmpty()) {
				// When a new ztamp is detected, we launch (without any subscriptions) the ApplicationInstaller 
				// AND we pre-install the inbox application (but there is no need to launch it right away)
				final SubscriptionData theInboxSubscription = SubscriptionData.create(ApplicationData.getData(Application.NativeApplication.INBOX.getApplication()), ztampData);
				final SubscriptionSchedulingData theInteractionScheduling = SubscriptionSchedulingData.create(theInboxSubscription, SCHEDULING_TYPE.InteractionTrigger);
				theInteractionScheduling.createSetting(InteractionTriggerHandler.EVENT, inEvent.getName());

				final TriggerEvent trigger = new TriggerEvent(ZtampEventsHandler.APPLICATION_INSTALLER, inEvent, SCHEDULING_TYPE.InteractionTrigger);
				AppletDispatcher.dispatchApplication(trigger);
			}

		} else {

			for (final SubscriptionSchedulingData aScheduling : triggeredSchedulings) {

				final Map<String, SubscriptionSchedulingSettingsData> theSettings = SubscriptionSchedulingSettingsData.findAllBySubscriptionSchedulingAsMap(aScheduling);

				// checks if the events are equal and if the time period is correct
				if (theSettings.get(InteractionTriggerHandler.EVENT).getValue().equals(inEvent.getName()) && ZtampEventsHandler.isInActivePeriod(theSettings)) {
					try {
						final ZtampEvent theEvent = (ZtampEvent) inEvent.clone();
						final TriggerEvent trigger = new TriggerEvent(aScheduling.getSubscription(), theEvent, SchedulingType.SCHEDULING_TYPE.InteractionTrigger);
						theEvent.setExecutionTarget(ZtampEventsHandler.getExecutionTarget(theEvent, theSettings));
						AppletDispatcher.dispatchApplication(trigger);

					} catch (final CloneNotSupportedException e) {
						ZtampEventsHandler.LOGGER.fatal(e, e);
					}
				}

			}

		}
	}

	private static final Pattern TIME_PERIOD = Pattern.compile("^(\\d{1,2}):(\\d{1,2})-(\\d{1,2}):(\\d{1,2})$");

	protected static boolean isInActivePeriod(Map<String, SubscriptionSchedulingSettingsData> settings) {
		// First checks if the validity is activated (at least a setting with a day name as key)
		boolean isValidityActivated = false;
		for (final DailyHandler.Weekday aDay : DailyHandler.Weekday.getAllDays()) {
			if (settings.containsKey(aDay.getValue())) {
				isValidityActivated = true;
				break;
			}
		}

		if (!isValidityActivated) {
			return true;
		}

		final VObjectData theObject = settings.values().iterator().next().getSubscription().getObject();
		final CCalendar now = new CCalendar(theObject.getJavaTimeZone());
		final SubscriptionSchedulingSettingsData theDaySetting = settings.get(DailyHandler.Weekday.findByCalendarId(now.get(Calendar.DAY_OF_WEEK)).getValue());
		if (theDaySetting == null) {
			return false;
		}

		final Matcher theMatcher = ZtampEventsHandler.TIME_PERIOD.matcher(theDaySetting.getValue());
		if (theMatcher.matches()) {
			final CCalendar from = (CCalendar) now.clone();
			final CCalendar to = (CCalendar) now.clone();
			from.setHour(Integer.valueOf(theMatcher.group(1)));
			from.setMinute(Integer.valueOf(theMatcher.group(2)));
			to.setHour(Integer.valueOf(theMatcher.group(3)));
			to.setMinute(Integer.valueOf(theMatcher.group(4)));
			return (from.before(now)) && (to.after(now));
		}

		return false;
	}

	private static VObjectData getExecutionTarget(ZtampEvent inEvent, Map<String, SubscriptionSchedulingSettingsData> inSettings) {

		final SubscriptionSchedulingSettingsData theTargetSetting = inSettings.get(InteractionTriggerHandler.TARGET);

		if ((theTargetSetting != null) && theTargetSetting.isValid()) {
			final VObjectData target = VObjectData.find(Long.parseLong(theTargetSetting.getValue()));
			if (target.isValid()) {
				return target;
			}
		}
		return inEvent.getReader();

	}
}

package net.violet.platform.message.application.factories;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.violet.platform.daemons.schedulers.AbstractScheduler.MessageNSettingProcessUnit;
import net.violet.platform.daemons.schedulers.AbstractScheduler.MessageProcessUnit;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.dataobjects.ApplicationSettingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.message.MessageServices.Body;
import net.violet.platform.schedulers.DailyWithDurationHandler;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.JabberMessageFactory;

import org.apache.log4j.Logger;

public class WebRadioMessageFactory extends AbstractMessageFactory {

	private static final Logger LOGGER = Logger.getLogger(WebRadioMessageFactory.class);

	static final String TITLE = "Webradio";
	public static final String URL = "url";

	@Override
	public List<Message2Send> getMessage(MessageProcessUnit inUnit) {

		if (inUnit instanceof MessageNSettingProcessUnit) {
			final SubscriptionSchedulingData theSubscriptionSchedulingData = inUnit.get();
			final ApplicationSettingData urlSetting = ApplicationSettingData.findByApplicationAndKey(theSubscriptionSchedulingData.getSubscription().getApplication(), WebRadioMessageFactory.URL);

			if ((urlSetting == null) || !urlSetting.isValid()) {
				WebRadioMessageFactory.LOGGER.fatal("This webradio does not have any url : " + theSubscriptionSchedulingData.getSubscription().getApplication().getName());
			} else {

				final VObject theObject = theSubscriptionSchedulingData.getSubscription().getObject().getReference();

				final SubscriptionSchedulingSettingsData theSetting = ((MessageNSettingProcessUnit) inUnit).getSchedulingSettingsData();
				final int listeningTime = Integer.parseInt(SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(theSubscriptionSchedulingData, theSetting.getKey() + DailyWithDurationHandler.DURATION_SUFFIXE).getValue());

				return WebRadioMessageFactory.getMessage2Send(urlSetting.getValue(), theObject, inUnit.getProcessConditioner(), listeningTime, System.currentTimeMillis());
			}
		}

		return Collections.emptyList();

	}

	/**
	 * 
	 * @param inUrl
	 * @param inVObject
	 * @param inDeliveryDate
	 * @param inListeningTime (time unit is minutes)
	 * @param nowTimeStamp
	 * @return
	 */
	public static List<Message2Send> getMessage2Send(final String inUrl, VObject inVObject, CCalendar inDeliveryDate, int inListeningTime, long nowTimeStamp) {

		final List<Message2Send> theList = new LinkedList<Message2Send>();
		final String idStream = Long.toString(nowTimeStamp);

		theList.add(new Message2SendWR(inUrl, inVObject, inDeliveryDate) {

			@Override
			public MessageDraft generateMessageDraft(MessageDraft inMessageDraft) {
				inMessageDraft.addWebRadio(inUrl);
				inMessageDraft.addStreamId(idStream);
				return inMessageDraft;
			}

		});

		if (inListeningTime > 0) {
			final CCalendar theScheduledDeliveryDate = (inDeliveryDate == null ? CCalendar.getTimeInFuture(nowTimeStamp, inListeningTime * 60000L) : CCalendar.getTimeInFuture(inDeliveryDate, inListeningTime * 60000L));
			theList.add(new Message2SendWR(inUrl, inVObject, theScheduledDeliveryDate) {

				@Override
				protected MessageDraft generateMessageDraft(MessageDraft inMessageDraft) {
					inMessageDraft.addStreamStop(idStream);
					return inMessageDraft;
				}

				@Override
				protected int getMode() {
					return JabberMessageFactory.STREAMING_MODE;
				}

			});
		}

		return theList;
	}

	public static abstract class Message2SendWR extends Message2Send {

		private final String mUrl;

		public Message2SendWR(String inUrl, VObject inVObject, CCalendar inDeliveryDate) {
			super(null, inVObject, inDeliveryDate);
			this.mUrl = inUrl;
		}

		@Override
		protected MessageSignature getSignature() {
			return null;
		}

		@Override
		protected Files[] getBody() {
			return null;
		}

		@Override
		protected Body[] getBodies() {
			return null;
		}

		@Override
		protected String getTitle() {
			return WebRadioMessageFactory.TITLE;
		}

		@Override
		protected int getTTL() {
			return Constantes.QUEUE_TTL_FIVE_MINUTES;
		}

		protected String getUrl() {
			return this.mUrl;
		}

		@Override
		public MessageDraft generateMessageDraft() {
			final MessageDraft theMessage = new MessageDraft(getRecipient());
			final CCalendar deliveryDate = getDeliveryDate();
			if (deliveryDate != null) {
				theMessage.setDeliveryDate(deliveryDate);
			}

			return generateMessageDraft(theMessage);
		}

		protected abstract MessageDraft generateMessageDraft(MessageDraft inMessageDraft);

		@Override
		protected int getMode() {
			return JabberMessageFactory.IDLE_MODE;
		}
	}

	public static void sendMessage(String inURL, VObject inObject, CCalendar inDeliveryDate) {
		AbstractMessageFactory.sendMessage(WebRadioMessageFactory.getMessage2Send(inURL, inObject, inDeliveryDate, 0, System.currentTimeMillis()));
	}
}

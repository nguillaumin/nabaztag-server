package net.violet.platform.applets;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.datamodel.VObject;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ApplicationSettingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.events.InteractionEvent;
import net.violet.platform.events.TriggerEvent;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.MessageServices;
import net.violet.platform.message.application.factories.WebRadioMessageFactory;
import net.violet.platform.message.application.factories.AbstractMessageFactory.Message2Send;
import net.violet.platform.util.Constantes;

import org.apache.log4j.Logger;

public class WebradioApplet extends NativeApplet {

	private static final Logger LOGGER = Logger.getLogger(WebradioApplet.class);

	@Override
	public void processTrigger(TriggerEvent inEvent) {

		final VObject target = ((InteractionEvent) inEvent.getEvent()).getTarget().getReference();
		final ApplicationData theApplication = inEvent.getApplication();
		final ApplicationSettingData urlSetting = ApplicationSettingData.findByApplicationAndKey(theApplication, WebRadioMessageFactory.URL);

		final List<SubscriptionSchedulingData> theSchedulings = SubscriptionSchedulingData.findAllBySubscriptionAndType(inEvent.getSubscription(), inEvent.getSchedulingType());

		if (!theSchedulings.isEmpty()) {

			if ((urlSetting == null) || !urlSetting.isValid()) {
				WebradioApplet.LOGGER.fatal("This webradio does not have any url ! " + theApplication.getName() + " (" + theApplication.getId() + ")");
			} else {

				final List<Message2Send> theMessages = new ArrayList<Message2Send>();

				theMessages.add(new WebRadioMessageFactory.Message2SendWR(null, target, null) {

					@Override
					public MessageDraft generateMessageDraft(MessageDraft inMessageDraft) {
						inMessageDraft.addStreamId("stop");
						inMessageDraft.addStreamStop("stop");
						inMessageDraft.setTTLInSecond(Constantes.QUEUE_TTL_FIVE_MINUTES);

						return inMessageDraft;
					}
				});

				theMessages.addAll(WebRadioMessageFactory.getMessage2Send(urlSetting.getValue(), target, null, 0, System.currentTimeMillis()));
				for (final Message2Send aMessage : theMessages) {
					aMessage.setRecipient(target);
					MessageServices.send(aMessage.generateMessageDraft());
				}
			}

		}

	}

}

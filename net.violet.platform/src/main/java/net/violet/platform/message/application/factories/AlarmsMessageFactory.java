package net.violet.platform.message.application.factories;

import java.util.Collections;
import java.util.List;

import net.violet.platform.daemons.schedulers.AbstractScheduler.MessageNSettingProcessUnit;
import net.violet.platform.daemons.schedulers.AbstractScheduler.MessageProcessUnit;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.schedulers.DailyWithMediaHandler;

public class AlarmsMessageFactory extends AbstractMessageFactory {

	static final String TITLE = "Service Reveil";

	@Override
	public List<Message2Send> getMessage(final MessageProcessUnit inUnit) {

		if (inUnit instanceof MessageNSettingProcessUnit) {
			final Message2Send theMessage = new Message2Send(inUnit.get(), inUnit.getProcessConditioner()) {

				@Override
				protected Files[] getBody() {

					final MessageNSettingProcessUnit theUnit = (MessageNSettingProcessUnit) inUnit;
					final SubscriptionSchedulingSettingsData theSetting = theUnit.getSchedulingSettingsData();
					final String media = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(theUnit.get(), theSetting.getKey() + DailyWithMediaHandler.MEDIA_SUFFIXE).getValue();
					final Music theMusic = Factories.MUSIC.find(Long.parseLong(media));

					if (theMusic != null) {
						return new Files[] { theMusic.getFile() };
					}

					return null;
				}

				@Override
				protected MessageSignature getSignature() {
					return MessageSignature.EMPTY_SIGNATURE;
				}

				@Override
				protected String getTitle() {
					return AlarmsMessageFactory.TITLE;
				}

			};

			return Collections.singletonList(theMessage);
		}

		return Collections.emptyList();
	}

}

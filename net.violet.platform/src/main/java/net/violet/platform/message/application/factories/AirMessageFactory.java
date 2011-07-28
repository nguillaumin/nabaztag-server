package net.violet.platform.message.application.factories;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.violet.platform.applications.AirHandler;
import net.violet.platform.daemons.schedulers.AbstractScheduler.MessageProcessUnit;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Source;
import net.violet.platform.datamodel.ConfigFiles.SERVICES;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.message.Message;
import net.violet.platform.message.MessageSignature;

//public pour les tests!
public class AirMessageFactory extends AbstractMessageFactory {

	protected static final String TITLE = "Service Air";

	public static enum STATES {
		GOOD,
		MIDDLE,
		BAD
	};

	@Override
	public List<Message2Send> getMessage(MessageProcessUnit inUnit) {

		return Collections.singletonList((Message2Send) new Message2Send(inUnit.get(), inUnit.getProcessConditioner()) {

			@Override
			protected String getTitle() {
				return AirMessageFactory.TITLE;
			}

			@Override
			protected MessageSignature getSignature() {
				return MessageSignature.AIR_SIGNATURE;
			}

			@Override
			protected Files[] getBody() {
				final Map<String, Object> theSettings = getSubscription().getSettings();

				if ((theSettings != null) && !theSettings.isEmpty() && theSettings.containsKey(AirHandler.SOURCE_SETTING) && theSettings.containsKey(AirHandler.LANGUAGE_SETTING)) {
					final String sourcePath = theSettings.get(AirHandler.SOURCE_SETTING).toString();
					final Source source = Factories.SOURCE.findByPath(sourcePath);

					if (source != null) {
						final long val = source.getSource_val();
						final Lang theLang = Factories.LANG.findByIsoCode(theSettings.get(AirHandler.LANGUAGE_SETTING).toString());
						final STATES theState;

						if (val == 6) {
							theState = AirMessageFactory.STATES.MIDDLE;
						} else if (val == 9) {
							theState = AirMessageFactory.STATES.BAD;
						} else {
							theState = AirMessageFactory.STATES.GOOD;
						}

						return new Files[] { Factories.CONFIG_FILES.findAllByServiceAndLang(SERVICES.AIR, theLang).get(theState.toString()).get(0).getFiles() };
					}
				}
				return null;
			}

		});
	}

	@Override
	protected Message.SOURCE getSource() {
		return Message.SOURCE.AIR;
	}

}

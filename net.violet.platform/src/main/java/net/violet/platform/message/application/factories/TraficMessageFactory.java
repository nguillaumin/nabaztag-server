package net.violet.platform.message.application.factories;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.violet.platform.applications.TraficHandler;
import net.violet.platform.daemons.schedulers.AbstractScheduler.MessageProcessUnit;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Source;
import net.violet.platform.datamodel.ConfigFiles.SERVICES;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.message.Message;
import net.violet.platform.message.MessageSignature;

class TraficMessageFactory extends AbstractMessageFactory {

	protected static final String TITLE = "Service Trafic";

	@Override
	public List<Message2Send> getMessage(MessageProcessUnit inUnit) {

		return Collections.singletonList((Message2Send) new Message2Send(inUnit.get(), inUnit.getProcessConditioner()) {

			@Override
			protected String getTitle() {
				return TraficMessageFactory.TITLE;
			}

			@Override
			protected MessageSignature getSignature() {
				return MessageSignature.TRAFFIC_SIGNATURE;
			}

			@Override
			protected Files[] getBody() {
				final Map<String, Object> theSettings = getSubscription().getSettings();

				if ((theSettings != null) && !theSettings.isEmpty() && theSettings.containsKey(TraficHandler.SOURCE)) {

					final Source source = Factories.SOURCE.findByPath(theSettings.get(TraficHandler.SOURCE).toString() + ".time");
					final Lang theLang = getSubscription().getObject().getPreferences().getLang().getReference();
					final String key;

					if (source != null) {
						final int val = (int) source.getSource_val();
						if (val <= 12) {
							key = "1";
						} else if ((val > 12) && (val <= 17)) {
							key = "2";
						} else if ((val > 17) && (val <= 22)) {
							key = "3";
						} else if ((val > 22) && (val <= 32)) {
							key = "4";
						} else if ((val > 32) && (val <= 42)) {
							key = "5";
						} else if ((val > 42) && (val <= 47)) {
							key = "6";
						} else if ((val > 47) && (val <= 52)) {
							key = "7";
						} else if ((val > 52) && (val <= 62)) {
							key = "8";
						} else if ((val > 62) && (val <= 77)) {
							key = "9";
						} else {
							key = "10";
						}

						return new Files[] { Factories.CONFIG_FILES.findAllByServiceAndLang(SERVICES.TRAFFIC, theLang).get(key).get(0).getFiles() };
					}
				}

				return null;
			}
		});

	}

	@Override
	protected Message.SOURCE getSource() {
		return Message.SOURCE.TRAFFIC;
	}

}

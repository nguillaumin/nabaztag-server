package net.violet.platform.message.application.factories;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.violet.platform.applications.BilanHandler;
import net.violet.platform.daemons.schedulers.AbstractScheduler.MessageProcessUnit;
import net.violet.platform.datamodel.ConfigFiles;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.ConfigFiles.SERVICES;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.message.MessageSignature;

import org.apache.log4j.Logger;

class BilanMessageFactory extends AbstractMessageFactory {

	private static final Logger LOGGER = Logger.getLogger(BilanMessageFactory.class);

	protected static final String TITLE = "Service Bilan";

	private static final Lang DEFAULT_LANGUAGE = Factories.LANG.find(Lang.LANG_US_ID);

	@Override
	public List<Message2Send> getMessage(MessageProcessUnit inUnit) {

		return Collections.singletonList((Message2Send) new Message2Send(inUnit.get(), inUnit.getProcessConditioner()) {

			@Override
			protected String getTitle() {
				return BilanMessageFactory.TITLE;
			}

			@Override
			protected MessageSignature getSignature() {
				return MessageSignature.BILAN_SIGNATURE;
			}

			@Override
			protected Files[] getBody() {
				final Map<String, Object> theSettings = getSubscription().getSettings();

				if ((theSettings != null) && !theSettings.isEmpty() && theSettings.containsKey(BilanHandler.NBR)) {
					final int bilanNbr = Integer.parseInt(theSettings.get(BilanHandler.NBR).toString());
					Lang theLang = getSubscription().getObject().getReference().getPreferences().getLangPreferences();

					// fix : only fr-FR and en-US are supported
					if ((theLang.getId() != 1) && (theLang.getId() != 2)) {
						theLang = BilanMessageFactory.DEFAULT_LANGUAGE;
					}

					final String key;

					if (bilanNbr == 0) {
						key = "1";
					} else if ((bilanNbr > 0) && (bilanNbr < 6)) {
						key = "2";
					} else if ((bilanNbr >= 6) && (bilanNbr < 13)) {
						key = "3";
					} else if ((bilanNbr >= 13) && (bilanNbr < 26)) {
						key = "4";
					} else {
						key = "5";
					}

					theSettings.put(BilanHandler.NBR, "0");
					getSubscription().setSettings(theSettings);

					final Map<String, List<ConfigFiles>> configFiles = Factories.CONFIG_FILES.findAllByServiceAndLang(SERVICES.BILAN, theLang);
					try {
						return new Files[] { configFiles.get(key).get(0).getFiles() };
					} catch (final NullPointerException e) {
						BilanMessageFactory.LOGGER.fatal("Involved subscription : " + getSubscription().getId());
						throw e;
					}
				}
				return null;
			}

		});
	}
}

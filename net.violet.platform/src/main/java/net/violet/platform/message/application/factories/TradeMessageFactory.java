package net.violet.platform.message.application.factories;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.violet.platform.applications.TradeFreeHandler;
import net.violet.platform.applications.TradeFullHandler;
import net.violet.platform.daemons.schedulers.AbstractScheduler.MessageProcessUnit;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ConfigFiles;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.Source;
import net.violet.platform.datamodel.ConfigFiles.SERVICES;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.message.Message;
import net.violet.platform.message.MessageSignature;

class TradeMessageFactory extends AbstractMessageFactory {

	protected static final String TITLE = "Service Bourse";

	@Override
	public List<Message2Send> getMessage(MessageProcessUnit inUnit) {

		return Collections.singletonList((Message2Send) new Message2Send(inUnit.get(), inUnit.getProcessConditioner()) {

			@Override
			protected String getTitle() {
				return TradeMessageFactory.TITLE;
			}

			@Override
			protected MessageSignature getSignature() {
				return MessageSignature.BOURSE_SIGNATURE;
			}

			@Override
			protected Files[] getBody() {

				final Map<String, Object> settings = getSubscription().getSettings();
				final Object sourceSetting = settings.get(TradeFreeHandler.SOURCE);

				if (sourceSetting != null) {

					final Source theSource = Factories.SOURCE.findByPath(sourceSetting.toString());

					if ((theSource != null) && (theSource.getSource_val() > 0)) {
						final Application theApplication = getSubscription().getApplication().getReference();
						final SERVICES theService = SERVICES.findByApplication(theApplication);

						if (theService != null) {
							final Lang theLang = getSubscription().getObject().getPreferences().getLang().getReference();
							final long val = theSource.getSource_val() - 1;
							final Map<String, List<ConfigFiles>> theConfigFiles = Factories.CONFIG_FILES.findAllByServiceAndLang(theService, theLang);
							final Files dataFiles = theConfigFiles.get(Long.toString(val)).get(0).getFiles();
							final Object musicSetting;

							// If the application is bourseFull then we add the
							// personal signature (if it actually exists)
							if ((theService == SERVICES.BOURSE_FULL) && ((musicSetting = settings.get(TradeFullHandler.MUSIC)) != null)) {
								final long musicId = Long.parseLong(musicSetting.toString());
								final Music theMusic = Factories.MUSIC.find(musicId);

								return new Files[] { theMusic.getFile(), dataFiles };
							}
							return new Files[] { dataFiles };

						}
					}
				}
				return null;
			}
		});
	}

	@Override
	protected Message.SOURCE getSource() {
		return Message.SOURCE.TRADE;
	}
}

package net.violet.platform.message.application.factories;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import net.violet.platform.applications.WeatherHandler;
import net.violet.platform.daemons.schedulers.AbstractScheduler.MessageProcessUnit;
import net.violet.platform.datamodel.ConfigFiles;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.ConfigFiles.SERVICES;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.message.Message;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.util.CCalendar;
import net.violet.vlisp.services.ManageMessageServices.WEATHER_OPTIONS;

class WeatherMessageFactory extends AbstractMessageFactory {

	protected static final String TITLE = "Service meteo";

	static final int TEMP_MAX = 110;
	static final int TEMP_MIN = -9;
	static final int TEMP_JP_MIN = -30;

	@Override
	public List<Message2Send> getMessage(final MessageProcessUnit inUnit) {

		return Collections.singletonList((Message2Send) new Message2Send(inUnit.get(), inUnit.getProcessConditioner()) {

			@Override
			protected String getTitle() {
				return WeatherMessageFactory.TITLE;
			}

			@Override
			protected MessageSignature getSignature() {
				return MessageSignature.WEATHER_SIGNATURE;
			}

			@Override
			protected Files[] getBody() {
				final SubscriptionData theSubscription = getSubscription();
				final TimeZone theTZ = theSubscription.getObject().getJavaTimeZone();
				final CCalendar theCalendar = (inUnit.getProcessConditioner() != null) ? inUnit.getProcessConditioner() : new CCalendar(false, theTZ);

				final Map<String, Object> theSettings = getSubscription().getSettings();

				if ((theSettings != null) && !theSettings.isEmpty() && theSettings.containsKey(WeatherHandler.LANGUAGE) && theSettings.containsKey(WeatherHandler.UNIT) && theSettings.containsKey(WeatherHandler.SOURCE)) {
					final Lang language = Factories.LANG.findByIsoCode(theSettings.get(WeatherHandler.LANGUAGE).toString());
					final long unit = Long.parseLong(theSettings.get(WeatherHandler.UNIT).toString());
					final String source = theSettings.get(WeatherHandler.SOURCE).toString();

					final String srv_src = source.substring(0, source.lastIndexOf("."));
					final Files[] meteoFiles = new Files[4];

					final Map<String, String> mysource = Factories.SOURCE.myMeteoSource(srv_src);

					final int weather;

					if (!mysource.isEmpty() && ((weather = Integer.parseInt(mysource.get("weather"))) != -1)) { // bien récupérer les sources ! !
						int temp = Integer.parseInt(mysource.get("temp"));

						if (unit == 2) {
							temp = (int) (temp * 1.8 + 32); // conversion farenheit
						}

						if (temp > WeatherMessageFactory.TEMP_MAX) {
							temp = WeatherMessageFactory.TEMP_MAX;
						}
						if (temp < WeatherMessageFactory.TEMP_MIN) {
							temp = WeatherMessageFactory.TEMP_MIN;
						}

						final Map<String, List<ConfigFiles>> meteoConfigFiles = Factories.CONFIG_FILES.findAllByServiceAndLang(SERVICES.WEATHER, language);

						if (theCalendar.get(Calendar.HOUR_OF_DAY) < 14) {
							meteoFiles[0] = meteoConfigFiles.get(WEATHER_OPTIONS.TODAY.toString()).get(0).getFiles();
						} else {
							meteoFiles[0] = meteoConfigFiles.get(WEATHER_OPTIONS.TOMORROW.toString()).get(0).getFiles();
						}

						meteoFiles[1] = meteoConfigFiles.get(WEATHER_OPTIONS.SKY.toString()).get(weather).getFiles();

						final boolean japanese = ObjectLangData.JAPANESE_OBJECT_LANGUAGE_ISOCODE.equals(language.getIsoCode());

						int i = 2;
						int tempIndex = temp - (WeatherMessageFactory.TEMP_MIN);
						if (japanese) {
							i = 3;
							tempIndex = temp - (WeatherMessageFactory.TEMP_JP_MIN);
						}

						meteoFiles[i] = meteoConfigFiles.get(WEATHER_OPTIONS.TEMP.toString()).get(tempIndex).getFiles();

						i = 3;
						if (japanese) {
							i = 2;
						}

						if ((unit == 0) || (unit == 1)) {
							meteoFiles[i] = meteoConfigFiles.get(WEATHER_OPTIONS.DEGREE.toString()).get(0).getFiles();
						} else {
							meteoFiles[i] = meteoConfigFiles.get(WEATHER_OPTIONS.FARENHEIT.toString()).get(0).getFiles();
						}
						return meteoFiles;
					}
				}
				return null;
			}

		});

	}

	@Override
	protected Message.SOURCE getSource() {
		return Message.SOURCE.WEATHER;
	}

}

package net.violet.platform.message.application.factories;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.platform.applications.MoodHandler;
import net.violet.platform.daemons.schedulers.AbstractScheduler.MessageProcessUnit;
import net.violet.platform.datamodel.ConfigFiles;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.ConfigFiles.SERVICES;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.util.CCalendar;

class MoodMessageFactory extends AbstractMessageFactory {

	private static final String TITLE = "Service Surprise";

	public static final Random RANDOM_MOOD_GENERATOR = new Random(CCalendar.getCurrentTimeInSecond());

	private static final String[] MOODS = new String[] { "broadcast/broad/config/respiration/54655Respiration12.mp3", "broadcast/broad/config/respiration/54653Respiration11.mp3", "broadcast/broad/config/respiration/54652Respiration10.mp3", "broadcast/broad/config/respiration/54649Respiration09.mp3", "broadcast/broad/config/respiration/54646Respiration08.mp3", "broadcast/broad/config/respiration/54643Respiration07.mp3", "broadcast/broad/config/respiration/54639Respiration06.mp3", "broadcast/broad/config/respiration/54635Respiration05.mp3", "broadcast/broad/config/respiration/54628Respiration04.mp3", "broadcast/broad/config/respiration/54627Respiration03.mp3", "broadcast/broad/config/respiration/54626Respiration02.mp3", "broadcast/broad/config/respiration/54625Respiration01.mp3", };

	private static final String SERVICE_MOODS = SERVICES.MOODS.name().toUpperCase();

	private static ConfigFiles randomHumeur(Lang inLang) {
		return AbstractSQLRecord.randomElementFromList(Factories.CONFIG_FILES.findAllByServiceAndLang(SERVICES.MOODS, inLang).get(MoodMessageFactory.SERVICE_MOODS));
	}

	@Override
	public List<Message2Send> getMessage(MessageProcessUnit inUnit) {

		return Collections.singletonList((Message2Send) new Message2Send(inUnit.get(), inUnit.getProcessConditioner()) {

			@Override
			protected Files[] getBody() {

				final Object languageSetting = getSubscription().getSettings().get(MoodHandler.LANGUAGES);

				final List<String> languages = (List<String>) languageSetting;
				if (!languages.isEmpty()) {
					final Lang langRand = ObjectLangData.getDefaultObjectLanguage(AbstractMessageFactory.randomElementFromArray(languages.toArray(new String[0]))).getReference();
					return new Files[] { MoodMessageFactory.randomHumeur(langRand).getFiles() };
				}

				return null;
			}

			@Override
			protected MessageSignature getSignature() {
				final String musicsign = MoodMessageFactory.MOODS[MoodMessageFactory.RANDOM_MOOD_GENERATOR.nextInt(MoodMessageFactory.MOODS.length)];
				return new MessageSignature(MessageSignature.RANDOM_COLOR, null, musicsign);
			}

			@Override
			protected String getTitle() {
				return MoodMessageFactory.TITLE;
			}

		});

	}

}

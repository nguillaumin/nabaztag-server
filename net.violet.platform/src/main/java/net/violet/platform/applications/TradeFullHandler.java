package net.violet.platform.applications;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.common.StringShop;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.BadMimeTypeException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSchedulingsException;
import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.daemons.crawlers.source.CrawlerSourceBourseAdvanced;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Crawl;
import net.violet.platform.datamodel.CrawlImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Source;
import net.violet.platform.datamodel.TtsVoice;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.message.Message;
import net.violet.platform.voice.TTSServices;

import org.apache.log4j.Logger;

public class TradeFullHandler implements ApplicationHandler {

	private static final Logger LOGGER = Logger.getLogger(TradeFullHandler.class);

	private static final ApplicationData TRADE_FULL_APPLICATION = ApplicationData.getData(Application.NativeApplication.BOURSE_FULL.getApplication());

	private static final String BOURSE_LINK = "http://download.finance.yahoo.com/d/quotes?f=c&s=";

	public static final String STOCK_CODE = "stock_code";
	public static final String SOURCE = "source";
	public static final String ALERT_NAME = "alert_name";

	public static final String MUSIC = "music";

	private static Music getMusic(VObjectData object, String text) {
		final TtsVoice theVoice = Factories.TTSVOICE.findByLang(object.getPreferences().getLang().getReference());
		final boolean needsSpecialTreatment = object.getObjectType().instanceOf(ObjectType.NABAZTAG_V1);
		final Files files = TTSServices.getDefaultInstance().postTTS(text, needsSpecialTreatment, needsSpecialTreatment, theVoice);
		if (files == null) {
			TradeFullHandler.LOGGER.fatal("TTS generation failed : " + text);
			return null;
		}

		final Music theMusic;
		try {
			theMusic = Factories.MUSIC.createNewMusic(object.getOwner().getReference(), files);
		} catch (final BadMimeTypeException e) {
			TradeFullHandler.LOGGER.fatal(e, e);
			return null;
		}

		return theMusic;
	}

	public SubscriptionData create(VObjectData object, Map<String, Object> settings) {

		final Map<String, Object> theSettings = new HashMap<String, Object>(settings);

		final Music theMusic = TradeFullHandler.getMusic(object, theSettings.get(TradeFullHandler.ALERT_NAME).toString());
		if (theMusic == null) {
			return null;
		}

		theSettings.put(TradeFullHandler.MUSIC, theMusic.getId());

		final SubscriptionData theSubscription = SubscriptionData.create(TradeFullHandler.TRADE_FULL_APPLICATION, object);
		final String source;

		// personal code
		if (theSettings.containsKey(TradeFullHandler.STOCK_CODE)) {
			source = buildPersonalSource(theSubscription, theSettings.get(TradeFullHandler.STOCK_CODE).toString());

		} else { // source
			source = theSettings.get(TradeFullHandler.SOURCE).toString();
		}

		theSettings.put(TradeFullHandler.SOURCE, source);
		theSubscription.setSettings(theSettings);

		return theSubscription;
	}

	public void update(SubscriptionData subscription, Map<String, Object> settings) {

		final Map<String, Object> theSettings = new HashMap<String, Object>(settings);

		final Music theMusic;
		// the new alert is not the same as the old one.
		if (!settings.get(TradeFullHandler.ALERT_NAME).equals(subscription.getSettings().get(TradeFullHandler.ALERT_NAME))) {
			theMusic = TradeFullHandler.getMusic(subscription.getObject(), settings.get(TradeFullHandler.ALERT_NAME).toString());
			if (theMusic != null) {
				deleteMusic(subscription);
			}
		} else {
			theMusic = Factories.MUSIC.find(Long.parseLong(subscription.getSettings().get(TradeFullHandler.MUSIC).toString()));
		}

		if (theMusic == null) {
			return;
		}

		theSettings.put(TradeFullHandler.MUSIC, theMusic.getId());

		String source = null;
		final Map<String, Object> previousSettings = subscription.getSettings();

		if (previousSettings.containsKey(TradeFullHandler.STOCK_CODE)) { // previous subscription had code

			if (previousSettings.get(TradeFullHandler.STOCK_CODE).equals(theSettings.get(TradeFullHandler.STOCK_CODE))) { // the new code is the same
				source = previousSettings.get(TradeFullHandler.SOURCE).toString();
			} else { // the previous source is no longer up to date
				deleteSource(subscription);
			}
		}

		if (source == null) {
			// personal code
			if (theSettings.containsKey(TradeFullHandler.STOCK_CODE)) {
				source = buildPersonalSource(subscription, theSettings.get(TradeFullHandler.STOCK_CODE).toString());

			} else { // source
				source = theSettings.get(TradeFullHandler.SOURCE).toString();
			}

		}

		theSettings.put(TradeFullHandler.SOURCE, source);
		subscription.setSettings(theSettings);
	}

	private String buildPersonalSource(SubscriptionData subscription, String stockCode) {
		final String source = StringShop.DOLLAR + subscription.getId() + ".money";

		final String url = TradeFullHandler.BOURSE_LINK + stockCode;
		final int valIndice = CrawlerSourceBourseAdvanced.getValueBourseSource(url);
		try {
			new CrawlImpl(source, Message.SOURCE.ADVANCED_TRADE.getId(), url, subscription.getId());
		} catch (final SQLException e) {
			TradeFullHandler.LOGGER.fatal(e, e);
		}

		Factories.SOURCE.createNewSource(source, valIndice, subscription.getId(), null);
		return source;
	}

	private void deleteMusic(SubscriptionData subscription) {
		final Object music = subscription.getSettings().get(TradeFullHandler.MUSIC);
		if (music != null) {
			final Music theMusic = Factories.MUSIC.find(Long.parseLong(music.toString()));
			if (theMusic != null) {
				final Files theFile = theMusic.getFile();
				if (theFile != null) {
					theFile.scheduleDeletion();
				}
				theMusic.delete();
			}
		}
	}

	private void deleteSource(SubscriptionData subscription) {
		final Crawl theCrawl = CrawlImpl.findByCrawlSrv(subscription.getId());
		if (theCrawl != null) {
			theCrawl.delete();
		}

		final Map<String, Object> theSettings = subscription.getSettings();

		final Object sourcePath = theSettings.get(TradeFullHandler.SOURCE);
		if ((sourcePath != null) && sourcePath.toString().startsWith(StringShop.DOLLAR)) {
			final Source theSource = Factories.SOURCE.findByPath(sourcePath.toString());
			if (theSource != null) {
				theSource.delete();
			}
		}

	}

	public void checkSettings(VObjectData object, Map<String, Object> settings) throws InvalidSettingException, MissingSettingException {
		final Object theCode = settings.get(TradeFullHandler.STOCK_CODE);
		final Object theSource = settings.get(TradeFullHandler.SOURCE);

		if ((theCode == null) && (theSource == null)) {
			throw new MissingSettingException(TradeFullHandler.STOCK_CODE + " or " + TradeFullHandler.SOURCE);
		}

		final Object alertName = settings.get(TradeFullHandler.ALERT_NAME);
		if (alertName == null) {
			throw new MissingSettingException(TradeFullHandler.ALERT_NAME);
		}

		// checks if the given code is valid
		if (theCode != null) {
			if (CrawlerSourceBourseAdvanced.getValueBourseSource(TradeFullHandler.BOURSE_LINK + theCode.toString()) <= 0) {
				throw new InvalidSettingException(TradeFullHandler.STOCK_CODE, theCode.toString());
			}
		} else { // or if the source is valid
			if (!TradeFreeHandler.SOURCE_PATTERN.matcher(theSource.toString()).matches() || (Factories.SOURCE.findByPath(theSource.toString()) == null)) {
				throw new InvalidSettingException(TradeFullHandler.SOURCE, theSource.toString());
			}
		}

	}

	public void delete(SubscriptionData subscription) {
		deleteMusic(subscription);
		deleteSource(subscription);
		subscription.delete();
	}

	public Map<String, Object> getUISettings(SubscriptionData subscription, APICaller inApiCaller) {
		final Map<String, Object> theSettings = new HashMap<String, Object>();

		final Map<String, Object> subSettings = subscription.getSettings();
		theSettings.put(TradeFullHandler.ALERT_NAME, subSettings.get(TradeFullHandler.ALERT_NAME).toString());

		if (subSettings.containsKey(TradeFullHandler.STOCK_CODE)) {
			theSettings.put(TradeFullHandler.STOCK_CODE, subSettings.get(TradeFullHandler.STOCK_CODE).toString());
			theSettings.put(TradeFullHandler.SOURCE, StringShop.EMPTY_STRING);
		} else {
			theSettings.put(TradeFullHandler.SOURCE, subSettings.get(TradeFullHandler.SOURCE).toString());
			theSettings.put(TradeFullHandler.STOCK_CODE, StringShop.EMPTY_STRING);

		}

		return theSettings;
	}

	public static SubscriptionData createOrUpdateSubscription(SubscriptionData subscription, VObjectData object, String alertName, String indic, String valCode, boolean ambiant, List<String> flashes, boolean allWeek) throws InvalidParameterException, InvalidSettingException, InvalidSchedulingsException, MissingSettingException {
		// creates settings
		final Map<String, Object> theSettings = new HashMap<String, Object>();

		theSettings.put(TradeFullHandler.ALERT_NAME, alertName);

		if ((indic != null) && !indic.equals(StringShop.EMPTY_STRING)) {
			theSettings.put(TradeFullHandler.SOURCE, indic);
		} else {
			theSettings.put(TradeFullHandler.STOCK_CODE, valCode);
		}

		// creates schedulings
		final List<Map<String, Object>> theSchedulings = new LinkedList<Map<String, Object>>();

		if (ambiant) {
			final Map<String, Object> scheduling = new HashMap<String, Object>();
			scheduling.put(SchedulingType.TYPE_KEY, SchedulingType.SCHEDULING_TYPE.Ambiant.getLabel());
			theSchedulings.add(scheduling);
		}

		theSchedulings.addAll(ApplicationHandlerHelper.generateDailySchedulings(flashes, object.getReference().getTimeZone().getJavaTimeZone(), allWeek));

		if (subscription == null) {
			return SubscriptionManager.createSubscription(TradeFullHandler.TRADE_FULL_APPLICATION, object, theSettings, theSchedulings, null);
		}

		SubscriptionManager.updateSubscription(subscription, theSettings, theSchedulings, null);
		return subscription;
	}

	public String getSubscriptionInformation(SubscriptionData subscription) {
		final Map<String, Object> settings = subscription.getSettings();
		if (settings.containsKey(TradeFullHandler.STOCK_CODE)) {
			return settings.get(TradeFullHandler.STOCK_CODE).toString();
		}

		return null;
	}
}

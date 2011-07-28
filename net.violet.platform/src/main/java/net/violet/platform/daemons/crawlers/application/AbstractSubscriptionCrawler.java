package net.violet.platform.daemons.crawlers.application;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.applications.GmailTwitterHandler;
import net.violet.platform.applications.MailAlertHandler;
import net.violet.platform.daemons.crawlers.feedHandler.ConnectionHandler.ConnectionSettings;
import net.violet.platform.daemons.crawlers.vaction.AbstractFeedCrawler;
import net.violet.platform.datamodel.Content;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.CipherTools;
import net.violet.platform.util.concurrent.units.AbstractCrawlerProcessUnit;

import org.apache.log4j.Logger;

public abstract class AbstractSubscriptionCrawler<T extends AbstractCrawlerProcessUnit> extends AbstractFeedCrawler<T> {

	private static final Logger LOGGER = Logger.getLogger(AbstractSubscriptionCrawler.class);

	public class SubscriptionUnit implements FeedUnit {

		private final Subscription mSubscription;
		private final String mEtag;
		private final String mIdLastNews;
		private final String mUsername;
		private final String mPassword;
		private Date mLastModified;
		private final Lang mLang;

		public SubscriptionUnit(Subscription inSubscription) {
			this.mSubscription = inSubscription;
			final PojoMap theSettings = this.mSubscription.getSettings();
			Object aSetting;

			if ((aSetting = theSettings.get(GmailTwitterHandler.ETAG)) != null) {
				this.mEtag = aSetting.toString();
			} else {
				this.mEtag = null;
			}

			if ((aSetting = theSettings.get(MailAlertHandler.ID_LAST_NEWS)) != null) {
				this.mIdLastNews = aSetting.toString();
			} else {
				this.mIdLastNews = null;
			}

			if ((aSetting = theSettings.get(GmailTwitterHandler.LOGIN)) != null) {
				this.mUsername = aSetting.toString();
			} else {
				this.mUsername = null;
			}

			if ((aSetting = theSettings.get(GmailTwitterHandler.PASSWORD)) != null) {
				this.mPassword = CipherTools.uncipher(aSetting.toString());
			} else {
				this.mPassword = null;
			}

			try {
				this.mLastModified = theSettings.getDate(GmailTwitterHandler.LAST_MODIFIED, null);
			} catch (final InvalidParameterException e) {
				this.mLastModified = null;
			}

			aSetting = theSettings.get(GmailTwitterHandler.LANGUAGE);
			this.mLang = Factories.LANG.findByIsoCode(aSetting.toString());

		}

		public Subscription getSubscription() {
			return this.mSubscription;
		}

		public String getETag() {
			return this.mEtag;
		}

		public String getIdLastNews() {
			return this.mIdLastNews;
		}

		public Lang getLang() {
			return this.mLang;
		}

		public Date getLastModified() {
			return this.mLastModified;
		}

		public MessageSignature getMessageSignature() {
			return new MessageSignature(getService());
		}

		public String getPassword() {
			return this.mPassword;
		}

		public String getUrl() {
			return AbstractSubscriptionCrawler.this.getUrl();
		}

		public String getUsername() {
			return this.mUsername;
		}

	}

	/**
	 * Constructeur à partir du service et des paramètres en ligne de commande.
	 * 
	 * @param inArgs
	 */
	protected AbstractSubscriptionCrawler(String[] inArgs, boolean isFree) {
		super(inArgs.clone(), isFree);
	}

	protected abstract String getUrl();

	@Override
	protected void doProcess() {

		for (final Subscription aSubscription : Factories.SUBSCRIPTION.findAllByApplication(Factories.APPLICATION.find(getApplicationId()))) {
			final Map<String, Object> settings = aSubscription.getSettings();
			if (settings.containsKey(GmailTwitterHandler.LOGIN)) {

				SubscriptionUnit theSubscriptionUnit = null;
				try {
					theSubscriptionUnit = new SubscriptionUnit(aSubscription);
				} catch (final Exception e) {
					AbstractSubscriptionCrawler.LOGGER.fatal("Invalid Subscription on Gmail or Twitter => id : " + aSubscription.getId(), e);
				}

				if (theSubscriptionUnit != null) {
					try {
						addFeedUnit2Process(theSubscriptionUnit);
					} catch (final InterruptedException e) {
						AbstractSubscriptionCrawler.LOGGER.fatal(e, e);
					}
				}
			}
		}
	}

	protected abstract long getApplicationId();

	@Override
	protected List<Content> getMostRecents(FeedUnit inFeedUnit) {
		return Collections.emptyList();
	}

	@Override
	protected void updateConnectionSettingAndIdXMl(FeedUnit inUnit, ConnectionSettings inConnectionSettings, String inIdXml) {
		if (inUnit instanceof AbstractSubscriptionCrawler.SubscriptionUnit) {
			final SubscriptionUnit theUnit = (SubscriptionUnit) inUnit;
			final SubscriptionData theSubscriptionData = SubscriptionData.getData(theUnit.getSubscription());

			final Map<String, Object> theSettings = theSubscriptionData.getSettings();

			if (inConnectionSettings.getETag() != null) {
				theSettings.put(GmailTwitterHandler.ETAG, inConnectionSettings.getETag());
			}

			if (inIdXml != null) {
				theSettings.put(MailAlertHandler.ID_LAST_NEWS, inIdXml);
			}

			if (inConnectionSettings.getLastModified() != null) {
				final String lastModified = new CCalendar(inConnectionSettings.getLastModifiedDate().getTime()).getTimestampUTC();
				theSettings.put(GmailTwitterHandler.LAST_MODIFIED, lastModified);
			}

			theSubscriptionData.setSettings(theSettings);
		}
	}

	protected String getMsgTitle(String inNewsContent) {
		return getService().getLabel() + " : " + inNewsContent;
	}
}

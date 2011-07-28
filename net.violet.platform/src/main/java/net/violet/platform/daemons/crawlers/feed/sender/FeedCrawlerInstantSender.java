package net.violet.platform.daemons.crawlers.feed.sender;

import java.util.List;
import java.util.Map;

import net.violet.platform.applications.GmailTwitterHandler;
import net.violet.platform.applications.VActionFullHandler;
import net.violet.platform.daemons.crawlers.application.AbstractSubscriptionCrawler.SubscriptionUnit;
import net.violet.platform.daemons.crawlers.vaction.AbstractFeedCrawler.FeedUnit;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.FilesImpl;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.message.MessageServices;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.util.concurrent.units.AbstractCrawlerProcessUnit;
import net.violet.platform.xmpp.JabberMessageFactory;

import org.apache.log4j.Logger;

public abstract class FeedCrawlerInstantSender<T extends AbstractCrawlerProcessUnit> extends FeedCrawlerSender<T> {

	private static final Logger LOGGER = Logger.getLogger(FeedCrawlerInstantSender.class);

	/**
	 * Constructeur à partir du service et des paramètres en ligne de commande.
	 * 
	 * @param inArgs
	 */
	public FeedCrawlerInstantSender(String[] inArgs) {
		super(inArgs);
	}

	@Override
	public void processNews(final List<Files> inNewsFiles, final List<T> inNews, final MessageSignature inMessageSignature, final String inTitle, final FeedUnit inFeedUnit) {
		execute(new Runnable() {

			public void run() {
				if (inFeedUnit instanceof SubscriptionUnit) {
					final SubscriptionUnit theFeedUnit = (SubscriptionUnit) inFeedUnit;

					for (final Subscription aSubscription : Factories.SUBSCRIPTION.findAllByApplication(theFeedUnit.getSubscription().getApplication())) {
						final Map<String, Object> settings = aSubscription.getSettings();
						final Object loginSetting = settings.get(GmailTwitterHandler.LOGIN);
						final Object nbNewsSetting = settings.get(VActionFullHandler.NB_NEWS);
						if ((loginSetting != null) && loginSetting.toString().equals(theFeedUnit.getUsername()) && (nbNewsSetting != null)) {
							processObject(inMessageSignature, aSubscription.getObject(), nbNewsSetting, inNewsFiles, inTitle, Palette.RANDOM);

						}
					}
				}
			}
		});
	}

	public void processObject(final MessageSignature inActionsSignature, final VObject inObject, final Object nbNews, final List<Files> ioFiles, final String inMessageTitle, final Palette inColorPal) {
		execute(new Runnable() {

			public void run() {
				try {
					final List<Files> theNews2Read;
					// On envoie les Nb_news_to_read première(s) news à lire à
					// l'utilisateur
					final int nbNews2Read;
					if ((nbNews != null) && (ioFiles.size() > (nbNews2Read = Integer.parseInt(nbNews.toString())))) {
						theNews2Read = ioFiles.subList(0, nbNews2Read);
					} else {
						theNews2Read = ioFiles;
					}

					MessageServices.sendServiceMessage(theNews2Read.toArray(new FilesImpl[theNews2Read.size()]), inObject, inMessageTitle, null, getTTL(), inColorPal, inActionsSignature, false, JabberMessageFactory.IDLE_MODE);
				} catch (final IllegalArgumentException e) {
					FeedCrawlerInstantSender.LOGGER.info(e, e);
				}
			}
		});
	}

}

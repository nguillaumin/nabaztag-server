package net.violet.platform.daemons.crawlers.feed.sender;

import java.util.List;
import java.util.ListIterator;

import net.violet.platform.daemons.crawlers.vaction.AbstractFeedCrawler.FeedUnit;
import net.violet.platform.daemons.crawlers.vaction.AbstractVActionCrawler.VActionUnit;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.VAction;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.util.concurrent.units.AbstractCrawlerProcessUnit;

public abstract class FeedCrawlerDBSender<T extends AbstractCrawlerProcessUnit> extends FeedCrawlerSender<T> {

	/**
	 * Constructeur à partir du service et des paramètres en ligne de commande.
	 * 
	 * @param inArgs
	 */
	public FeedCrawlerDBSender(String[] inArgs) {
		super(inArgs);
	}

	@Override
	public void processNews(final List<Files> inNewsFiles, final List<T> inNews, final MessageSignature inMessageSignature, final String inTitle, final FeedUnit inFeedUnit) {
		execute(new Runnable() {

			public void run() {
				if (inFeedUnit instanceof VActionUnit) {
					doProcessNews(inNews, ((VActionUnit) inFeedUnit).getVAction());
				}
			}
		});
	}

	private void doProcessNews(final List<T> inNews, final VAction inAction) {
		final ListIterator<T> theListIterator = inNews.listIterator(inNews.size());

		while (theListIterator.hasPrevious()) {
			final AbstractCrawlerProcessUnit aNews = theListIterator.previous();
			Factories.CONTENT.insert(inAction, aNews.getResult(), aNews.getTitle(), aNews.getLink(), aNews.getId_xml());
		}
	}
}

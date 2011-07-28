package net.violet.platform.daemons.crawlers.gestion;

import net.violet.platform.daemons.crawlers.AbstractCrawler;
import net.violet.platform.datamodel.Event;
import net.violet.platform.datamodel.EventImpl;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

// permet de purger les messages 

public class CrawlerPurge extends AbstractCrawler {

	private static final Logger LOGGER = Logger.getLogger(CrawlerPurge.class);

	/**
	 * Walker sur les EventImpl.
	 */
	private final EventImpl.RecordWalker<Event> mWalker;

	private static void deleteEvent(Event inEvent) {
		try {
			final long theEventId = inEvent.getId();
			inEvent.delete();
			Factories.EVSEQ.deleteByEventID(theEventId);
		} catch (final Exception e) {
			CrawlerPurge.LOGGER.warn("main()Boucle / Exception :" + e);
		}
	}

	public CrawlerPurge(String[] inArgs) {
		super(inArgs);

		this.mWalker = new EventImpl.RecordWalker<Event>() {

			public void process(Event inObject) {
				CrawlerPurge.deleteEvent(inObject);
			}
		};
	}

	@Override
	public void process() {
		final long timeBegin = System.currentTimeMillis();
		final int nbEvents = EventImpl.walkPurgeable(this.mWalker);
		final long delta = System.currentTimeMillis() - timeBegin;
		CrawlerPurge.LOGGER.info(" total : " + nbEvents + " | time : " + delta);
	}
}

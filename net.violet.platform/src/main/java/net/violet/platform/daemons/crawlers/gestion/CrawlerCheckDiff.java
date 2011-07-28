package net.violet.platform.daemons.crawlers.gestion;

import net.violet.common.utils.concurrent.BlockingExecutor;
import net.violet.common.utils.concurrent.BlockingExecutorLight.Worker;
import net.violet.platform.daemons.crawlers.AbstractCrawler;
import net.violet.platform.datamodel.Event;
import net.violet.platform.datamodel.EventImpl;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.message.MessageServices;
import net.violet.platform.message.StoredMessage;
import net.violet.platform.xmpp.JabberMessageFactory;

import org.apache.log4j.Logger;

// permet de gérer les messages différés

public class CrawlerCheckDiff extends AbstractCrawler {

	private static final Logger LOGGER = Logger.getLogger(CrawlerCheckDiff.class);

	private static final int THREADNBR = 20; // number of threads executing in the same time
	private final BlockingExecutor<Event> mBlockingExecutor;

	/**
	 * Walker sur les EventImpl.
	 */
	private final EventImpl.RecordWalker<Event> mWalker;

	public CrawlerCheckDiff(String[] inArgs) {
		super(inArgs);

		this.mBlockingExecutor = new BlockingExecutor<Event>(CrawlerCheckDiff.THREADNBR, new Worker<Event>() {

			public void process(Event inEvent) {
				doProcessEventDiff(inEvent);
			}
		}, "CrawlerCheckDiff");
		this.mWalker = new EventImpl.RecordWalker<Event>() {

			public void process(Event inEvent) {
				try {
					CrawlerCheckDiff.this.mBlockingExecutor.put(inEvent);
				} catch (final InterruptedException ie) {
					CrawlerCheckDiff.LOGGER.fatal(ie, ie);
				}
			}
		};
	}

	private void doProcessEventDiff(Event inEvent) {
		try {
			// On change le signe de obj dans la table event.
			final int theEventObj = -inEvent.getEvent_obj();
			inEvent.setObject(theEventObj);

			final VObject theObject = inEvent.getEventObject();
			// Si l'objet est XMPP, on lui envoie le message.
			if (theObject.isXMPP()) {
				final StoredMessage theMessage = new StoredMessage(theObject, inEvent.getId());
				MessageServices.sendUsingXmpp(theMessage, JabberMessageFactory.NOTIFY_MODE);
			} else {
				// On prévient l'objet qu'il y a un nouveau message.
				theObject.clearPingCache();
			}
		} catch (final Throwable aThrowable) {
			CrawlerCheckDiff.LOGGER.fatal(aThrowable, aThrowable);
		}
	}

	@Override
	protected void process() {

		final int nbProcessed = EventImpl.walkDeferredMessages(this.mWalker);
		try {
			this.mBlockingExecutor.waitTermination();
		} catch (final InterruptedException e) {
			// This space for rent.
		}

		CrawlerCheckDiff.LOGGER.info(" total : " + nbProcessed);

	}
}

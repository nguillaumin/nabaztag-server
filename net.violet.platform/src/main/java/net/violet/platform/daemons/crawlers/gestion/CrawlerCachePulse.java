package net.violet.platform.daemons.crawlers.gestion;

import net.violet.db.cache.Cache;
import net.violet.platform.daemons.crawlers.AbstractCrawler;

/**
 * Crawler pour envoyer des message pulse à intervalles réguliers (pour vérifier
 * que les noeuds sont bien connectés).
 */
public class CrawlerCachePulse extends AbstractCrawler {

	public CrawlerCachePulse(String[] inArgs) {
		super(inArgs);
	}

	@Override
	protected void process() {
		Cache.sendPulseMessage();
	}
}

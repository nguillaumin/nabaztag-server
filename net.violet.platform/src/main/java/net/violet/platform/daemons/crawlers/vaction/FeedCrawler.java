package net.violet.platform.daemons.crawlers.vaction;

import java.util.List;

import net.violet.common.utils.concurrent.units.AbstractProcessUnit;
import net.violet.platform.daemons.crawlers.vaction.AbstractFeedCrawler.FeedUnit;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Service;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.util.concurrent.units.AbstractCrawlerProcessUnit;

public interface FeedCrawler<T extends AbstractCrawlerProcessUnit> {

	enum SERVICE_ACCESSRIGHT {
		FREE,
		FULL;

		private final String mName = this.name().toUpperCase();

		@Override
		public String toString() {
			return this.mName;
		}
	};

	void processNews(List<Files> inNewsFiles, List<T> inNews, MessageSignature inMessageSignature, String inTitle, FeedUnit inFeedUnit);

	void processUnit(T inUnit) throws InterruptedException;

	/**
	 * Returns a {@link AbstractProcessUnit} generated from the parameters
	 * 
	 * @param inLang
	 * @return a {@link AbstractProcessUnit}
	 */
	T getProcessUnit(String inTitle, String inLink, String inUri, Lang inLang, Integer inTTL);

	Service getService();
}

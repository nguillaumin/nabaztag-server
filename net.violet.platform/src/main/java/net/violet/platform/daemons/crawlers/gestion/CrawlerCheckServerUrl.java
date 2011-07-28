package net.violet.platform.daemons.crawlers.gestion;

import java.util.List;
import java.util.Map;

import net.violet.common.utils.concurrent.BlockingExecutor;
import net.violet.common.utils.concurrent.BlockingExecutorLight.Worker;
import net.violet.platform.daemons.crawlers.AbstractCrawler;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.VObjectImpl;
import net.violet.platform.xmpp.IQConfigQuery;

import org.apache.log4j.Logger;

// Sur les lapins dont le bytecode est assez récent, vérifie l'adresse à laquelle ils vont chercher leur bytecode 
// 
public class CrawlerCheckServerUrl extends AbstractCrawler {

	private static final Logger LOGGER = Logger.getLogger(CrawlerCheckServerUrl.class);

	private static final int THREADNBR = 100; // number of threads executing in the same time
	private final BlockingExecutor<VObject> mBlockingExecutor;

	/**
	 * Ce crawler sert à interroger chacun des lapins capables de répondre
	 * concernant sur la valeur server_url configurée.
	 * 
	 * @param inArgs
	 */
	public CrawlerCheckServerUrl(String[] inArgs) {
		super(inArgs);

		this.mBlockingExecutor = new BlockingExecutor<VObject>(CrawlerCheckServerUrl.THREADNBR, new Worker<VObject>() {

			public void process(VObject inObject) {
				doProcessObject(inObject);
			}
		}, "ServerUrl");
	}

	/**
	 * Récupération du serveur sur lequel le lapin va chercher son bytecode
	 * 
	 * @param inObject
	 */
	private void doProcessObject(VObject inObject) {
		// inObject.getXmppAddress();
		CrawlerCheckServerUrl.LOGGER.info("Gonna get info for " + inObject.getXmppAddress());
		// récupérer la resource
		final List<String> resources = inObject.getResources(); //IQResourcesQuery.getClientResources(inObject.getXmppAddress());
		if (resources.size() > 0) {
			final String resource = resources.get(0);
			final Map<String, String> res = IQConfigQuery.getClientConfig(inObject.getXmppAddress(), resource, IQConfigQuery.ConfigType.staticConfig);
			CrawlerCheckServerUrl.LOGGER.info("RESPONSE: " + inObject.getObject_login() + " : " + res.get("server_url"));
		} else {
			CrawlerCheckServerUrl.LOGGER.info("Problem getting info for " + inObject.getObject_login());
		}
	}

	/**
	 * Ajoute un objet à la liste des objets à traiter
	 * 
	 * @param inObject l'objet à ajouter
	 */
	private void processObject(VObject inObject) {
		try {
			this.mBlockingExecutor.put(inObject);
		} catch (final InterruptedException ie) {
			CrawlerCheckServerUrl.LOGGER.fatal(ie, ie);
		}
	}

	@Override
	protected void process() {
		final long before = System.currentTimeMillis();

		final VObjectImpl.RecordWalker<VObject> theWalker = new VObjectImpl.RecordWalker<VObject>() {

			public void process(VObject inObject) {
				processObject(inObject);
			}
		};

		final int nbProcessed = VObjectImpl.walkCheckServerUrl(theWalker);

		try {
			this.mBlockingExecutor.waitTermination();
		} catch (final InterruptedException ie) {
			CrawlerCheckServerUrl.LOGGER.fatal(ie, ie);
		}

		final long after = System.currentTimeMillis();

		CrawlerCheckServerUrl.LOGGER.info("SUMMARY : processed " + nbProcessed + " rabbits in " + ((after - before) / 1000) + " s");
	}
}

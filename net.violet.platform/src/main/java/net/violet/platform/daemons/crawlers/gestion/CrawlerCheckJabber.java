package net.violet.platform.daemons.crawlers.gestion;

import java.util.List;

import net.violet.platform.daemons.crawlers.AbstractCrawler;
import net.violet.platform.datamodel.InterruptionLogImpl;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.VObjectImpl;

import org.apache.log4j.Logger;

/** vérifie si un lapin en mode jabber n'est plus connecté */

public class CrawlerCheckJabber extends AbstractCrawler {


	private static final Logger LOGGER = Logger.getLogger(CrawlerCheckJabber.class);

	/**
	 * Walker sur les VObjectImpl.
	 */
	private final VObjectImpl.RecordWalker<VObject> mWalker;

	public CrawlerCheckJabber(String[] inArgs) {
		super(inArgs);
		this.mWalker = new VObjectImpl.RecordWalker<VObject>() {

			public void process(VObject inObject) {
				processObject(inObject);
			}
		};
	}

	/**
	 * Passage de l'objet jabber en mode inactif
	 * 
	 * @param inObject
	 */
	private void processObject(VObject inObject) {
		final List<String> resources = inObject.getResources(); //IQResourcesQuery.getClientResources(inObject.getXmppAddress());
		if (resources.isEmpty()) {
			if (inObject.getObject_mode() != VObject.MODE_XMPP_INACTIVE) {
				inObject.setMode(VObject.MODE_XMPP_INACTIVE);
				InterruptionLogImpl.insert(inObject, InterruptionLogImpl.WHAT_OPTION.OFFLINE, "CrawlerCheckJabber");
			}
		} else {
			inObject.setMode(VObject.MODE_XMPP);
		}
	}

	@Override
	protected void process() {
		final int nbProcessed = VObjectImpl.walkCheckJabber(this.mWalker);
		CrawlerCheckJabber.LOGGER.info(" total : " + nbProcessed);
	}
}

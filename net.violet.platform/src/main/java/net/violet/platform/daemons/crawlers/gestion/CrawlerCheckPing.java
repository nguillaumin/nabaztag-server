package net.violet.platform.daemons.crawlers.gestion;

import net.violet.platform.daemons.crawlers.AbstractCrawler;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.VObjectImpl;
import net.violet.platform.util.Constantes;

import org.apache.log4j.Logger;

// vérifie si un lapin en mode normal pingue bien la platform 

public class CrawlerCheckPing extends AbstractCrawler {


	private static final Logger LOGGER = Logger.getLogger(CrawlerCheckPing.class);

	/**
	 * Walker sur les VObjectImpl.
	 */
	private final VObjectImpl.RecordWalker<VObject> mWalker;

	public CrawlerCheckPing(String[] inArgs) {
		super(inArgs);

		this.mWalker = new VObjectImpl.RecordWalker<VObject>() {

			public void process(VObject inObject) {
				processObject(inObject);
			}
		};
	}

	/**
	 * Passage de l'objet en mode inactif
	 * 
	 * @param inObject
	 */
	private void processObject(VObject inObject) {
		inObject.setMode(VObject.MODE_PING_INACTIVE);
	}

	@Override
	protected void process() {
		processCheckPing();
	}

	int processCheckPing() {
		final int nbProcessed = VObjectImpl.walkCheckPing(this.mWalker, Constantes.TIMEOUT_PING_IN_SEC);
		CrawlerCheckPing.LOGGER.info(" total : " + nbProcessed);
		return nbProcessed;
	}
}

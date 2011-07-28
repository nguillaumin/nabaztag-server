package net.violet.platform.daemons.crawlers;

import net.violet.db.records.Record.RecordWalker;
import net.violet.platform.datamodel.Content;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.ObjectHasReadContent;
import net.violet.platform.datamodel.VAction;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.ServiceFactory;

import org.apache.log4j.Logger;

public class PurgeContentDaemon extends AbstractCrawler {

	private static final Logger LOGGER = Logger.getLogger(PurgeContentDaemon.class);

	private static final int NB_SAFE_CONTENT_PODCAST = 2;
	private static final int NB_SAFE_CONTENT_RSS = 31;

	/**
	 * Processeur des contenus.
	 */
	private static final RecordWalker<Content> CONTENT_WALKER = new RecordWalker<Content>() {

		private VAction action;

		public void process(Content inContent) {

			if ((inContent.getAction() != null) && ((this.action == null) || !inContent.getAction().equals(this.action))) {
				this.action = inContent.getAction();
				final Content content = Factories.CONTENT.findOldestByAction(this.action, (this.action.getService().equals(ServiceFactory.SERVICE.PODCAST.getService())) ? PurgeContentDaemon.NB_SAFE_CONTENT_PODCAST : PurgeContentDaemon.NB_SAFE_CONTENT_RSS);
				for (final ObjectHasReadContent anOHRC : Factories.OBJECT_HAS_READ_CONTENT.findAllByAction(this.action)) {
					if (content.getId() > anOHRC.getContent().getId()) {
						anOHRC.setContent(content);
					}
				}
			}

			final Files theFile = inContent.getFile();
			if (inContent.countObjectHasReadContent() == 0) {
				PurgeContentDaemon.LOGGER.info("Deleting content " + inContent.getId());
				if (inContent.delete() && (theFile != null)) {
					theFile.scheduleDeletion();
				}
			}
		}
	};

	/**
	 * Constructeur à partir des paramètres en ligne de commande.
	 */
	public PurgeContentDaemon(String[] inArgs) {
		super(inArgs);
	}

	/**
	 * Itération.
	 */
	@Override
	protected void process() {
		Factories.VACTION.walk(new RecordWalker<VAction>() {

			public void process(VAction inAction) {
				PurgeContentDaemon.processAction(inAction, (inAction.getService().equals(ServiceFactory.SERVICE.PODCAST.getService())) ? PurgeContentDaemon.NB_SAFE_CONTENT_PODCAST : PurgeContentDaemon.NB_SAFE_CONTENT_RSS);
			}
		});

		// 1/ purge des contenus sans action
		Factories.CONTENT.walkWithoutAction(PurgeContentDaemon.CONTENT_WALKER);
	}

	/**
	 * Travaille sur une action.
	 */
	public static void processAction(VAction inAction, int inNbContent2Keep) {
		// TODO:
		// 2/ purge des actions sans personne d'abonné.
		// 3/ récupération d'un nombre variable de contenus.

		PurgeContentDaemon.LOGGER.info("Processing action " + inAction.getId() + "(" + inAction.getUrl() + ")");

		Factories.CONTENT.walkOlderByAction(inAction, inNbContent2Keep, PurgeContentDaemon.CONTENT_WALKER);
	}

}

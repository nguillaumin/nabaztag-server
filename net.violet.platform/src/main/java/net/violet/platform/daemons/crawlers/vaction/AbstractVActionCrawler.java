package net.violet.platform.daemons.crawlers.vaction;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import net.violet.platform.daemons.crawlers.feedHandler.ConnectionHandler.ConnectionSettings;
import net.violet.platform.datamodel.Content;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.VAction;
import net.violet.platform.datamodel.VActionImpl;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.util.concurrent.units.AbstractCrawlerProcessUnit;

import org.apache.log4j.Logger;

public abstract class AbstractVActionCrawler<T extends AbstractCrawlerProcessUnit> extends AbstractFeedCrawler<T> {

	private static final Logger LOGGER = Logger.getLogger(AbstractVActionCrawler.class);

	public static class VActionUnit implements FeedUnit {

		private static final Map<VAction, VActionUnit> CACHE = new WeakHashMap<VAction, VActionUnit>();

		public static VActionUnit getVActionUnit(VAction inVAction) {

			VActionUnit theUnit = VActionUnit.CACHE.get(inVAction);

			if (theUnit == null) {
				VActionUnit.CACHE.put(inVAction, theUnit = new VActionUnit(inVAction));
			}

			return theUnit;
		}

		private final VAction mVAction;

		private VActionUnit(VAction inVAction) {
			this.mVAction = inVAction;
		}

		public VAction getVAction() {
			return this.mVAction;
		}

		public Date getLastModified() {
			return this.mVAction.getLastModified();
		}

		public String getUsername() {
			return null;
		}

		public String getPassword() {
			return null;
		}

		public String getETag() {
			return this.mVAction.getETag();
		}

		public String getUrl() {
			return this.mVAction.getUrl();
		}

		public String getIdLastNews() {
			return this.mVAction.getIdLastNews();
		}

		public Lang getLang() {
			return this.mVAction.getLang();
		}

		public MessageSignature getMessageSignature() {
			return new MessageSignature(this.mVAction.getService());
		}
	}

	/**
	 * Constructeur à partir du service et des paramètres en ligne de commande.
	 * 
	 * @param inArgs
	 */
	protected AbstractVActionCrawler(String[] inArgs, boolean isFree) {
		super(inArgs.clone(), isFree);
	}

	@Override
	protected void doProcess() {
		final VActionImpl.RecordWalker<VAction> theWalker = new VActionImpl.RecordWalker<VAction>() {

			public void process(VAction inAction) {
				try {
					addFeedUnit2Process(VActionUnit.getVActionUnit(inAction));
				} catch (final InterruptedException e) {
					AbstractVActionCrawler.LOGGER.fatal(e, e);
				}
			}
		};

		VActionImpl.walkByService(theWalker, getService().getId(), isFree());
	}

	@Override
	protected List<Content> getMostRecents(FeedUnit inFeedUnit) {
		if (inFeedUnit instanceof VActionUnit) {
			return Factories.CONTENT.findMostRecentsByAction(((VActionUnit) inFeedUnit).getVAction(), getNbItems2Read(), true);
		}
		return Collections.emptyList();
	}

	@Override
	protected void updateConnectionSettingAndIdXMl(FeedUnit inUnit, ConnectionSettings inSettings, String inIdXml) {
		if (inUnit instanceof VActionUnit) {
			final VActionUnit theVActionUnit = (VActionUnit) inUnit;
			theVActionUnit.getVAction().setVActionConnectionSettingsAndIdXML(inSettings, inIdXml);

		}
	}

}

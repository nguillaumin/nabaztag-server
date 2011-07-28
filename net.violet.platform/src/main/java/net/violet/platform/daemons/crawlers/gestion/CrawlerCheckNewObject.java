package net.violet.platform.daemons.crawlers.gestion;

import net.violet.platform.daemons.crawlers.AbstractCrawler;
import net.violet.platform.datamodel.TagTmpSite;
import net.violet.platform.datamodel.TagTmpSiteImpl;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.object.Provisionning;
import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.JabberComponent;
import net.violet.platform.xmpp.JabberComponentManager;
import net.violet.platform.xmpp.JabberMessageFactory;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.packet.Packet;

/* 
 * Vérifie si l'objet Jabber doit être associé à l'utilisateur 
 * et envoi d'un son d'inscription régulièrement
 */

public class CrawlerCheckNewObject extends AbstractCrawler {


	private static final Logger LOGGER = Logger.getLogger(CrawlerCheckNewObject.class);

	/**
	 * Walker sur tag_tmp_site.
	 */
	private final TagTmpSiteImpl.RecordWalker<TagTmpSite> mWalker;

	/**
	 * Référence sur le client.
	 */
	private final JabberComponent mComponent;

	public CrawlerCheckNewObject(String[] inArgs) {
		super(inArgs);

		this.mWalker = new TagTmpSiteImpl.RecordWalker<TagTmpSite>() {

			public void process(TagTmpSite inTagTmpSite) {
				processTagTmpSite(inTagTmpSite);
			}
		};

		this.mComponent = JabberComponentManager.getComponent(Constantes.XMPP_PLATFORM_COMPONENT);
	}

	private void processTagTmpSite(TagTmpSite inTagTmpSite) {

		final String theSerial = inTagTmpSite.getSerial();
		final HARDWARE theHardware = inTagTmpSite.getHardware();
		final String theIp = inTagTmpSite.getIp();
		if (theHardware.checkIdentifier(theSerial)) { // pour blinder les mac très bien pourrite même si elle est vérifié en amont!!

			final VObject theObject = Factories.VOBJECT.findBySerial(theSerial);
			if (theObject == null) { // il n'existe pas donc on l'essaye de l'inscrire
				final MessageDraft theMessageDraft = Provisionning.addObjectInQueue(theIp, theSerial, theHardware);
				if (!theMessageDraft.getSequenceList().isEmpty()) {
					// Envoi du message pour s'inscrire
					final Packet thePacket = this.mComponent.getPacket(theMessageDraft, JabberMessageFactory.IDLE_MODE);
					String thePacketXml = thePacket.toXML();
					thePacketXml = thePacketXml.replaceAll(Provisionning.VIRTUAL_SERIAL_HARDWARE_4.toLowerCase(), theSerial);
					this.mComponent.sendPacket(thePacketXml);
				}
			} else { // l'objet est déjà associé
				inTagTmpSite.delete();
			}
		} else {
			CrawlerCheckNewObject.LOGGER.fatal(" Bad serial in tag_tmp_site : " + theSerial);
		}

	}

	@Override
	protected void process() {
		CrawlerCheckNewObject.LOGGER.info(" total : " + TagTmpSiteImpl.walkCheckNewObject(this.mWalker));
	}
}

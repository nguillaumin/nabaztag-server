package net.violet.platform.daemons.crawlers.gestion;

import java.io.StringReader;

import net.violet.common.utils.concurrent.BlockingExecutor;
import net.violet.common.utils.concurrent.BlockingExecutorLight.Worker;
import net.violet.platform.daemons.crawlers.AbstractCrawler;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.ScheduledMessage;
import net.violet.platform.datamodel.ScheduledMessageImpl;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.ping.EventMng;
import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.JabberComponent;
import net.violet.platform.xmpp.JabberComponentManager;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.PacketParserUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

// permet d'envoyer au objet jabber les messages dans le futur

public class CrawlerScheduledMessage extends AbstractCrawler {

	private static final Logger LOGGER = Logger.getLogger(CrawlerScheduledMessage.class);

	private static final int THREADNBR = 20; // number of threads executing in the same time
	private final BlockingExecutor<ScheduledMessage> mBlockingExecutor;

	/**
	 * Walker sur les ScheduledMessageImpl.
	 */
	private final ScheduledMessageImpl.RecordWalker<ScheduledMessage> mWalker;

	/**
	 * Référence sur le client.
	 */
	private final JabberComponent mComponent;

	public CrawlerScheduledMessage(String[] inArgs) {
		super(inArgs);

		this.mBlockingExecutor = new BlockingExecutor<ScheduledMessage>(CrawlerScheduledMessage.THREADNBR, new Worker<ScheduledMessage>() {

			public void process(ScheduledMessage inScheduledMessage) {
				processScheduledMessage(inScheduledMessage);
			}
		}, "CrawlerScheduledMessage");

		this.mWalker = new ScheduledMessageImpl.RecordWalker<ScheduledMessage>() {

			public void process(ScheduledMessage inScheduledMessage) {
				try {
					CrawlerScheduledMessage.this.mBlockingExecutor.put(inScheduledMessage);
				} catch (final InterruptedException ie) {
					CrawlerScheduledMessage.LOGGER.fatal(ie, ie);
				}
			}
		};

		this.mComponent = JabberComponentManager.getComponent(Constantes.XMPP_PLATFORM_COMPONENT);
	}

	/**
	 * Envoi du message
	 * 
	 * @param inScheduledMessage
	 */
	void processScheduledMessage(ScheduledMessage inScheduledMessage) {
		final Long theMessageID = inScheduledMessage.getMessage_id();
		if (theMessageID != null) {
			final MessageReceived theMessageReceived = Factories.MESSAGE_RECEIVED.findMessageReceivedByMessageId(theMessageID);
			// On prévient l'objet qu'il y a un nouveau message. (pas les
			// message de service)
			if ((theMessageReceived != null) && (theMessageReceived.getRecipient() != null)) {
				EventMng.refreshCountInboxMessagesAfterSending(theMessageReceived.getRecipient());
			}
		}
		final String xmlPacket = inScheduledMessage.getPacket();
		try {
			final Packet packet = CrawlerScheduledMessage.getPacketFormXmlStr(xmlPacket);
			packet.setFrom(this.mComponent.getDefaultFromAddress());
			this.mComponent.sendPacket(packet);
		} catch (final Exception e) {
			CrawlerScheduledMessage.LOGGER.fatal(e, e);
		} finally {
			inScheduledMessage.delete();
		}
	}

	@Override
	protected void process() {
		processMessageInFuture();
	}

	int processMessageInFuture() {
		final int nbProcessed = ScheduledMessageImpl.walkScheduledMessage(this.mWalker);
		try {
			this.mBlockingExecutor.waitTermination();
		} catch (final InterruptedException e) {
			// This space for rent.
		}
		CrawlerScheduledMessage.LOGGER.info(" total : " + nbProcessed);
		return nbProcessed;
	}

	private static Packet getPacketFormXmlStr(String inXml) throws Exception {
		XmlPullParserFactory factory;
		factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		final XmlPullParser xParser = factory.newPullParser();
		xParser.setInput(new StringReader(inXml));
		int eventType = xParser.getEventType();
		Packet packet = null;
		do {
			if (eventType == XmlPullParser.START_TAG) {
				if (xParser.getName().equals("message")) {
					packet = PacketParserUtils.parseMessage(xParser);
				}
			}
			eventType = xParser.next();
		} while (eventType != XmlPullParser.END_DOCUMENT);
		return packet;
	}
}

package net.violet.platform.xmpp;

import net.violet.platform.xmpp.packet.AbstractPacketListener;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.IQ.Type;

public class ComponentPingPongPacketListenerTest extends AbstractPacketListener {


	private static final Logger LOGGER = Logger.getLogger(ComponentPingPongPacketListenerTest.class);

	private static final int NB_THREADS = 500;

	/**
	 * référence sur le composant
	 */
	private final JabberPacketSender mComponent;

	public ComponentPingPongPacketListenerTest(JabberPacketSender inComponent) {
		super(ComponentPingPongPacketListenerTest.NB_THREADS);
		this.mComponent = inComponent;
	}

	@Override
	protected void doProcessPacket(Packet inPacket) {
		ComponentPingPongPacketListenerTest.LOGGER.debug("PingPong doProcessPingPongPacket :\n" + inPacket.toXML());
		if (inPacket instanceof PingPongPacketTest) {
			ComponentPingPongPacketListenerTest.processPingPongExtension(this.mComponent, (PingPongPacketTest) inPacket);
			ComponentPingPongPacketListenerTest.LOGGER.debug("Process PingPongPacket");
		}
	}

	public static void processPingPongExtension(JabberPacketSender inComponent, PingPongPacketTest inExtension) {
		try {
			System.out.println("[" + ((JabberComponent) inComponent).getDefaultFromAddress() + "] Received : " + inExtension.toXML());
			Packet.setDefaultXmlns(PingPongPacketTest.NAMESPACE);
			final String to = inExtension.getFrom();
			final String from = inExtension.getTo();
			final Type type = (inExtension.getType().equals(Type.RESULT)) ? Type.GET : Type.RESULT;
			final JabberComponent theComponent = JabberComponentManager.getComponent(from.substring(from.indexOf("@") + 1, from.indexOf("/")));
			Thread.sleep(2000);
			final PingPongPacketTest theMsg = new PingPongPacketTest(from, to, type);
			System.out.println("[" + theComponent.getDefaultFromAddress() + "] Send : " + theMsg.toXML());
			theComponent.sendPacket(theMsg);
		} catch (final InterruptedException e) {
			
			e.printStackTrace();
		}
	}

}

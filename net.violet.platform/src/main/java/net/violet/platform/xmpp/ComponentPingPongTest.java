package net.violet.platform.xmpp;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.util.Pair;

import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.IQ.Type;

public class ComponentPingPongTest {

	// Liste des ping
	private final List<JabberComponent> listPingComponents = new ArrayList<JabberComponent>();
	// Liste des pong
	private final List<JabberComponent> listPongComponents = new ArrayList<JabberComponent>();

	public ComponentPingPongTest(List<Pair<String, String>> inListPingPongComponents) {
		// On initialise la liste des composants ping et des pong
		for (final Pair<String, String> pairOfPingPong : inListPingPongComponents) {
			// Récup des composants
			final JabberComponent ping = JabberComponentManager.getComponent(pairOfPingPong.getFirst());
			final JabberComponent pong = JabberComponentManager.getComponent(pairOfPingPong.getSecond());
			// Ajout des listeners
			ping.addPacketListener(new ComponentPingPongPacketListenerTest(ping), null);
			pong.addPacketListener(new ComponentPingPongPacketListenerTest(pong), null);
			// Ajout dans les liste de composants ping et pong de notre objet
			this.listPingComponents.add(ping);
			this.listPongComponents.add(pong);
		}
	}

	public void play() {
		for (int i = 0; i < this.listPingComponents.size(); i++) {
			final JabberComponent ping = this.listPingComponents.get(i);
			final JabberComponent pong = this.listPongComponents.get(i);
			Packet.setDefaultXmlns(PingPongPacketTest.NAMESPACE);
			final PingPongPacketTest thePingPongMsg = new PingPongPacketTest(ping.getDefaultFromAddress(), pong.getDefaultFromAddress(), Type.SET);
			ping.sendPacket(thePingPongMsg);
		}
	}

	// args doit être de la forme :
	// {[componentPing1,componentPong1],[componentPing2,componentPong3], ... }
	public static void main(String[] args) {
		final List<Pair<String, String>> listPingPongComponents = new ArrayList<Pair<String, String>>();
		for (final String arg : args) {
			final String[] elmts = arg.split(net.violet.common.StringShop.COMMA);
			listPingPongComponents.add(new Pair<String, String>(elmts[0], elmts[1]));
		}

		final ComponentPingPongTest pingPong = new ComponentPingPongTest(listPingPongComponents);
		pingPong.play();

		try {
			Thread.sleep(Integer.MAX_VALUE);
		} catch (final InterruptedException e) {
			
			e.printStackTrace();
		}

	}

}

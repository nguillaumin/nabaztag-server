package net.violet.platform.xmpp;

import java.util.LinkedList;
import java.util.List;

import org.jivesoftware.smack.packet.Packet;

/**
 * Mock pour un sender de paquets Jabber.
 */
public class JabberPacketSenderMock implements JabberPacketSender {

	private final List<Packet> mPacketStream;

	public JabberPacketSenderMock() {
		this.mPacketStream = new LinkedList<Packet>();
	}

	public void sendPacket(Packet inPacket) {
		this.mPacketStream.add(inPacket);
	}

	public Packet getSentPacket(int inIndex) {
		return this.mPacketStream.get(inIndex);
	}

	public int getSentPacketCount() {
		return this.mPacketStream.size();
	}

	public String getDefaultFromAddress() {
		throw new UnsupportedOperationException();
	}
}

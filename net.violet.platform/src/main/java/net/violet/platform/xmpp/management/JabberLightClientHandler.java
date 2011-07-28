package net.violet.platform.xmpp.management;

public interface JabberLightClientHandler {

	void handleReceivedPacket(JabberLightClient inFromSocket, String inPacket);
}

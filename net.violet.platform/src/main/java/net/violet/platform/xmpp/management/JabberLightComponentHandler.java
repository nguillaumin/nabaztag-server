package net.violet.platform.xmpp.management;

public interface JabberLightComponentHandler {

	void handleReceivedPacket(JabberLightComponent inFromSocket, String inPacket);
}

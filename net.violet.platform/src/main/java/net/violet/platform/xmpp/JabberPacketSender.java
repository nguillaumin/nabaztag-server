package net.violet.platform.xmpp;

import org.jivesoftware.smack.packet.Packet;

/**
 * Interface pour un composant ou un client qui envoie des paquets Jabber.
 */
public interface JabberPacketSender {

	/**
	 * Envoie un paquet.
	 */
	void sendPacket(Packet inPacket);

	/**
	 * Accesseur sur l'adresse From par défaut.
	 */
	String getDefaultFromAddress();
}

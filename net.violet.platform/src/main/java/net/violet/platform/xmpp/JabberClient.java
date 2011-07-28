package net.violet.platform.xmpp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import net.violet.platform.message.Message;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.JabberMessageFactory.IQVioletPacket;
import net.violet.platform.xmpp.packet.AmpPacket;
import net.violet.platform.xmpp.packet.ButtonPacket;
import net.violet.platform.xmpp.packet.EarsPacket;
import net.violet.platform.xmpp.packet.IQCommandPacket;
import net.violet.platform.xmpp.packet.IQPresenceOfflinePacket;
import net.violet.platform.xmpp.packet.IQResourcesPacket;
import net.violet.platform.xmpp.packet.IQSources;
import net.violet.platform.xmpp.packet.JabberRPCListerner;
import net.violet.platform.xmpp.packet.PingPacket;
import net.violet.platform.xmpp.packet.PlatformPacketListener;
import net.violet.probes.ProbesHandler;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.provider.ProviderManager;

/**
 * Jabber client.
 */
public class JabberClient implements ConnectionListener, JabberPacketSender {

	private static final Logger LOGGER = Logger.getLogger(JabberClient.class);

	static {
		JabberClient.initXmpp();
	}

	/**
	 * Queue of packets to send.
	 */
	private final Queue<Packet> queue;

	/**
	 * Connexion to jabber server
	 */
	private final XMPPConnection mConnection;

	/**
	 * Jabber user's login
	 */
	private final String xmppId;

	/**
	 * Jabber user's password
	 */
	private final String xmppPassword;

	/**
	 * Jabber user's resource
	 */
	private final String resource;

	public static enum JABBER_LISTENING_MODES {
		STANDARD,
		XMLRPC
	};

	/**
	 * Constructor
	 */
	public JabberClient(String inResource) {
		this(Constantes.XMPP_SERVER_LIST, Constantes.XMPP_SERVER_PORT, Constantes.XMPP_PLATFORM_ID, Constantes.XMPP_PLATFORM_PASSWORD, inResource, JABBER_LISTENING_MODES.STANDARD);
	}

	public JabberClient(String inResource, String inXmppServer) {
		this(Arrays.asList(new String[] { inXmppServer }), Constantes.XMPP_SERVER_PORT, Constantes.XMPP_PLATFORM_ID, Constantes.XMPP_PLATFORM_PASSWORD, inResource, JABBER_LISTENING_MODES.STANDARD);
	}

	public JabberClient(String inResource, JABBER_LISTENING_MODES mode) {
		this(Constantes.XMPP_SERVER_LIST, Constantes.XMPP_SERVER_PORT, (mode == JABBER_LISTENING_MODES.XMLRPC) ? Constantes.XMPP_XMLRPC_ID : Constantes.XMPP_PLATFORM_ID, Constantes.XMPP_PLATFORM_PASSWORD, inResource, JABBER_LISTENING_MODES.XMLRPC);
	}

	public JabberClient(List<String> inListXmppServer, int inXmppServerPort, String inXmppId, String inXmppPassword, String inResource) {
		this(inListXmppServer, inXmppServerPort, inXmppId, inXmppPassword, inResource, JABBER_LISTENING_MODES.STANDARD);
	}

	/**
	 * Constructeur à partir d'un serveur, d'un port, d'un domaine, d'un ID et
	 * d'un mot de passe.
	 */
	public JabberClient(List<String> inListXmppServer, int inXmppServerPort, String inXmppId, String inXmppPassword, String inResource, JABBER_LISTENING_MODES mode) {
		this.queue = new LinkedList<Packet>();

		this.xmppId = inXmppId;
		this.xmppPassword = inXmppPassword;
		this.resource = inResource;

		final List<ConnectionConfiguration> mListConfig = new ArrayList<ConnectionConfiguration>(inListXmppServer.size());

		JabberClient.LOGGER.debug("[" + inResource + "] Connexion sur " + inListXmppServer + ":" + inXmppServerPort);
		for (final String inXmppServer : inListXmppServer) {
			final ConnectionConfiguration theConfig = new ConnectionConfiguration(inXmppServer, inXmppServerPort, Constantes.XMPP_NABAZTAG_DOMAIN, false);
			theConfig.setLoginInfo(this.xmppId, this.xmppPassword, this.resource, true);
			mListConfig.add(theConfig);
		}

		this.mConnection = new XMPPConnection(mListConfig);
		try {
			this.mConnection.connect();
			this.mConnection.addConnectionListener(this);
			this.mConnection.login(this.xmppId, this.xmppPassword, this.resource);
			ProbesHandler.CLIENT.addConnectedClient(getConnectionName());
		} catch (final XMPPException anException) {
			JabberClient.LOGGER.debug("[Exception] " + anException.toString());
			JabberClient.LOGGER.fatal(anException, anException);
			JabberClient.LOGGER.debug("[" + inResource + "] Echec de la connexion");
		}

		if (mode == JABBER_LISTENING_MODES.XMLRPC) {
			addPacketListener(new JabberRPCListerner(), null);
		} else {
			addPacketListener(new PlatformPacketListener(this), null);
		}
	}

	private String getConnectionName() {
		return this.mConnection.getUser() + "@" + this.mConnection.getHost() + "/" + this.resource;
	}

	/**
	 * Ajoute un listener.
	 */
	public void addPacketListener(PacketListener inListener, PacketFilter inFilter) {
		this.mConnection.addPacketListener(inListener, inFilter);
	}

	/**
	 * Envoi un message à un objet.
	 */
	public void sendMessage(Message inMessage, int mode) {
		final Packet thePacket;
		thePacket = JabberClient.getPacket(inMessage, mode);
		sendPacket(thePacket);
	}

	public static Packet getPacket(Message inMessage, int mode) {
		return JabberMessageFactory.getJabberMessagePacket(inMessage, null, mode, null);
	}

	public static Packet getPacket(Message inMessage, int mode, String from) {
		return JabberMessageFactory.getJabberMessagePacket(inMessage, null, mode, from);
	}

	public static Packet getPacket(Message inMessage, CCalendar inTimeOfDelivery, int mode) {
		return JabberMessageFactory.getJabberMessagePacket(inMessage, inTimeOfDelivery, mode, null);
	}

	/**
	 * Envoie un paquet.
	 */
	public void sendPacket(Packet inPacket) {
		if (JabberClient.LOGGER.isDebugEnabled()) {
			JabberClient.LOGGER.debug("[" + this.resource + "] Packet: " + inPacket.toXML());
		}
		if (this.mConnection.isConnected()) {
			try {
				this.mConnection.sendPacket(inPacket);
			} catch (final RuntimeException anException) {
				this.queue.add(inPacket);
			}
		} else {
			this.queue.add(inPacket);
		}
	}

	/**
	 * Send a message
	 */
	public void sendPacket(String inPacket) {
		final String theXML = inPacket;
		final Packet thePacket = new Packet() {

			@Override
			public String toXML() {
				return theXML;
			}
		};
		sendPacket(thePacket);
	}

	/**
	 * Accesseur sur la connection XMPP
	 * 
	 * @return la connection XMPP.
	 */
	public XMPPConnection getXMPPConnection() {
		return this.mConnection;
	}

	/**
	 * 
	 * @return jabber client's resource.
	 */
	public String getResource() {
		return this.resource;
	}

	public void connectionClosed() {
		JabberClient.LOGGER.info("[" + this.resource + "] XMPP Connection Closed");
		ProbesHandler.CLIENT.delConnectedClient(getConnectionName());
	}

	public void connectionClosedOnError(Exception inException) {
		JabberClient.LOGGER.fatal(inException, inException);
	}

	public void reconnectingIn(int inArg) {
		JabberClient.LOGGER.info("[" + this.resource + "] XMPP Reconnecting in " + inArg + " secs");
	}

	public void reconnectionFailed(Exception inException) {
		JabberClient.LOGGER.fatal(inException, inException);
	}

	public void reconnectionSuccessful() {
		JabberClient.LOGGER.info("[" + this.resource + "] XMPP Reconnection Successful");
		ProbesHandler.CLIENT.addConnectedClient(getConnectionName());
		synchronized (this.queue) {
			while (true) {
				final Packet thePacket = this.queue.poll();
				if (thePacket == null) {
					break;
				}
				try {
					this.mConnection.sendPacket(thePacket);
				} catch (final RuntimeException anException) {
					JabberClient.LOGGER.fatal(anException, anException);
					this.queue.add(thePacket);
					break;
				}
			}
		}
	}

	/**
	 * Initialisation de Xmpp.
	 */
	private static void initXmpp() {
		final ProviderManager theProviderManager = ProviderManager.getInstance();
		theProviderManager.addExtensionProvider(PingPacket.ELEMENT, PingPacket.NAMESPACE, PingPacket.EXTENSION_PROVIDER);
		theProviderManager.addIQProvider(IQSources.ELEMENT, IQSources.NAMESPACE, IQSources.IQ_PROVIDER);
		theProviderManager.addExtensionProvider(EarsPacket.ELEMENT, EarsPacket.NAMESPACE, EarsPacket.EXTENSION_PROVIDER);
		theProviderManager.addExtensionProvider(ButtonPacket.ELEMENT, ButtonPacket.NAMESPACE, ButtonPacket.EXTENSION_PROVIDER);
		theProviderManager.addExtensionProvider(AmpPacket.ELEMENT, AmpPacket.NAMESPACE, AmpPacket.EXTENSION_PROVIDER);
		theProviderManager.addIQProvider(IQResourcesPacket.ELEMENT, IQResourcesPacket.NAMESPACE, IQResourcesPacket.IQ_PROVIDER);
		theProviderManager.addIQProvider(IQPresenceOfflinePacket.ELEMENT, IQPresenceOfflinePacket.NAMESPACE, IQPresenceOfflinePacket.IQ_PROVIDER);
		theProviderManager.addIQProvider(PingPacket.ELEMENT, PingPacket.NAMESPACE, IQVioletPacket.IQ_PROVIDER);
		theProviderManager.addIQProvider(IQCommandPacket.ELEMENT, IQCommandPacket.NAMESPACE, IQCommandPacket.IQ_PROVIDER);
	}

	/**
	 * Ferme la connexion XMPP.
	 */
	public void close() {
		synchronized (this.queue) {
			this.queue.clear();
			this.mConnection.disconnect();
		}
		ProbesHandler.CLIENT.delConnectedClient(getConnectionName());
	}

	public String getDefaultFromAddress() {
		return net.violet.common.StringShop.EMPTY_STRING;
	}

}

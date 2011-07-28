package net.violet.platform.xmpp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import net.violet.common.utils.server.ProcessName;
import net.violet.platform.message.Message;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.Pair;
import net.violet.platform.xmpp.JabberMessageFactory.IQVioletPacket;
import net.violet.platform.xmpp.packet.AmpPacket;
import net.violet.platform.xmpp.packet.ButtonPacket;
import net.violet.platform.xmpp.packet.EarsPacket;
import net.violet.platform.xmpp.packet.IQCommandPacket;
import net.violet.platform.xmpp.packet.IQPresenceOfflinePacket;
import net.violet.platform.xmpp.packet.IQResourcesPacket;
import net.violet.platform.xmpp.packet.IQSources;
import net.violet.platform.xmpp.packet.JID;
import net.violet.platform.xmpp.packet.JabberComponentApiPacketListener;
import net.violet.platform.xmpp.packet.JabberComponentAppletPacketListener;
import net.violet.platform.xmpp.packet.JabberComponentObjectsPacketListener;
import net.violet.platform.xmpp.packet.JabberComponentPlatformPacketListener;
import net.violet.platform.xmpp.packet.PingPacket;
import net.violet.platform.xmpp.packet.VioletApiPacket;
import net.violet.platform.xmpp.packet.VioletAppletPacket;
import net.violet.probes.ProbesHandler;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.provider.ProviderManager;

import com.ericsson.otp.erlang.OtpErlangPid;
import com.ericsson.otp.erlang.OtpSelf;

public class JabberComponent implements ConnectionListener, JabberPacketSender {

	private static final Logger LOGGER = Logger.getLogger(JabberComponent.class);

	static {
		JabberComponent.initXmpp();
	}

	// private final List<Pair<String,Integer>> mListHostName = new
	// ArrayList<Pair<String,Integer>>();
	private final String mComponentName;
	private final String mComponentFromAddress;
	private final String mPassword;
	private final OtpSelf mComponentErlangNode = null;
	private final OtpErlangPid mComponentErlangPid = null;

	/**
	 * Liste des paquets à envoyer.
	 */
	private final Queue<Packet> mQueue = new LinkedList<Packet>();

	private final XMPPConnection mConnection;
	private static final String OTP_OK = "ok";
	private static final int THREAD_POOL_SIZE = 100;

	/**
	 * Référence sur les connexions sur le serveur.
	 */
	// private final Map<String, XMPPConnection> mMapConnections = new
	// HashMap<String, XMPPConnection>();
	/**
	 * Référence sur les connexions sur le serveur.
	 */
	// private final List<XMPPConnection> mListConnections = new
	// ArrayList<XMPPConnection>();
	/**
	 * @param listHosts
	 * @param componentName
	 * @param inPassword
	 */
	public JabberComponent(List<Pair<String, Integer>> listHosts, String componentName, String inPassword) {
		JabberComponent.LOGGER.debug("JabberComponent will be connected to : " + listHosts);
		// mListHostName.addAll(listHosts);
		this.mComponentName = componentName;
		this.mComponentFromAddress = JabberComponent.getDefaultFromAddress(componentName);
		this.mPassword = inPassword;

		final List<String> listHostName = new ArrayList<String>();
		for (final Pair<String, Integer> hostInfos : listHosts) {
			listHostName.add(hostInfos.getFirst());
		}

		// hack temporaire : impose que les ports soient les mêmes pour les
		// mêmes composants sur differents hosts
		this.mConnection = new XMPPConnection(listHostName, listHosts.get(0).getSecond(), this.mComponentName, this.mPassword);

		try {
			this.mConnection.connect();
			this.mConnection.addConnectionListener(this);
			// mMapConnections.put(mConnection.getHost(), mConnection);
			// mListConnections.add(mConnection);
			ProbesHandler.COMPONENT.addConnectedComponent(this.mComponentName);
		} catch (final XMPPException anException) {
			JabberComponent.LOGGER.debug("[Exception] " + anException.toString());
			JabberComponent.LOGGER.fatal(anException, anException);
			JabberComponent.LOGGER.debug("[" + this.mComponentName + "] Echec de la connexion");
		}

		if (this.mComponentName.equals(Constantes.XMPP_API_COMPONENT)) {
			addPacketListener(new JabberComponentApiPacketListener(this), null);

		} else if (this.mComponentName.equals(Constantes.XMPP_APPLET_COMPONENT)) {
			addPacketListener(new JabberComponentAppletPacketListener(this), null);

			/*
			try {
				final String otpNodeName = JabberComponent.getDefaultOtpNodeName(componentName);

				this.mComponentErlangNode = new OtpSelf(otpNodeName);
				this.mComponentErlangPid = this.mComponentErlangNode.pid();
				this.mComponentErlangNode.publishPort();
				//this.mComponentErlangConnection = this.mComponentErlangNode.connect(new OtpPeer(Constantes.OTP_PEER_NODE_NAME));

				// Init mnesia table
				final OtpConnection selfConnection = this.mComponentErlangNode.connect(new OtpPeer(Constantes.OTP_PEER_NODE_NAME));

				final OtpErlangList params = new OtpErlangList(new OtpErlangObject[] { new OtpErlangAtom(IQAbstractQuery.PENDING_REQUEST_RECORD), new OtpErlangList(new OtpErlangObject[] { new OtpErlangTuple(new OtpErlangObject[] { new OtpErlangAtom("ram_copies"), new OtpErlangList(new OtpErlangObject[] { new OtpErlangAtom(this.mComponentErlangPid.node()) }) }), new OtpErlangTuple(new OtpErlangObject[] { new OtpErlangAtom("index"), new OtpErlangList(new OtpErlangObject[] {}) }), new OtpErlangTuple(new OtpErlangObject[] { new OtpErlangAtom("attributes"), new OtpErlangList(new OtpErlangObject[] { new OtpErlangAtom("packet_id"), new OtpErlangAtom("pid") }) }), }), });
				selfConnection.sendRPC("mnesia", "create_table", params);
				final OtpErlangObject result = selfConnection.receive();
				if (!(result instanceof OtpErlangTuple) || !JabberComponent.OTP_OK.equals(((OtpErlangTuple) ((OtpErlangTuple) result).elementAt(1)).elementAt(1).toString())) {
					JabberComponent.LOGGER.info("Failed of mmesia:create_table [ result : " + result.toString() + "]");
				}
				selfConnection.close();

			} catch (final IOException e) {
				JabberComponent.LOGGER.fatal(e, e);
			} catch (final OtpAuthException e) {
				JabberComponent.LOGGER.fatal(e, e);
			} catch (final OtpErlangExit e) {
				JabberComponent.LOGGER.fatal(e, e);
			}
			*/

		} else if (this.mComponentName.equals(Constantes.XMPP_OBJECTS_COMPONENT)) {
			addPacketListener(new JabberComponentObjectsPacketListener(this), null);
		} else if (this.mComponentName.equals(Constantes.XMPP_PLATFORM_COMPONENT)) {
			addPacketListener(new JabberComponentPlatformPacketListener(this), null);
		}
	}

	/**
	 * Ajoute un listener.
	 */
	public void addPacketListener(PacketListener inListener, PacketFilter inFilter) {
		// for (XMPPConnection mConnection : mListConnections) {
		this.mConnection.addPacketListener(inListener, inFilter);
		// }
	}

	/**
	 * Envoie un paquet. Utilise le composant indiqué dans le from, s'il y en a
	 * un.
	 */
	/*
	 * public void sendPacket(Packet inPacket) { if (LOGGER.isDebugEnabled()) {
	 * LOGGER.debug("[" + mComponentName + "] Packet: " + inPacket.toXML()); }
	 * final String theFromStr = inPacket.getFrom(); XMPPConnection
	 * firstConnection = null; final String theUnqualifiedFrom; if (theFromStr
	 * != null) { final JID theFromJID = new JID(theFromStr); final String
	 * theResource = theFromJID.getResource(); if (theResource == null) {
	 * theUnqualifiedFrom = inPacket.getFrom() + "/"; } else { final String[]
	 * theResourceBits = theResource.split(":"); if (theResourceBits.length ==
	 * 2) { theUnqualifiedFrom = theFromJID.getUserName() + "@" +
	 * theFromJID.getDomain() + "/" + theResourceBits[0]; firstConnection =
	 * mMapConnections.get(theResourceBits[1]); } else { theUnqualifiedFrom =
	 * inPacket.getFrom(); } } } else { // On ne modifie pas le from.
	 * theUnqualifiedFrom = null; } boolean sent = false; if (firstConnection !=
	 * null) { sent = trySendPacket(inPacket, theUnqualifiedFrom,
	 * firstConnection); } if (!sent) { synchronized (mListConnections) {
	 * Collections.shuffle(mListConnections); for (XMPPConnection theConnection
	 * : mListConnections) { sent = trySendPacket(inPacket, theUnqualifiedFrom,
	 * theConnection); if (sent) { break; } } if (!sent) {
	 * LOGGER.info("Packet added in queue"); mQueue.add(inPacket); } } } }
	 * private boolean trySendPacket(Packet inPacket, String inUnqualifiedFrom,
	 * XMPPConnection inConnection) { boolean sent = false; if
	 * (inConnection.isConnected() ) { if ( inConnection.isHandshaked() ) {
	 * LOGGER.debug("Connection established"); try { if (inUnqualifiedFrom !=
	 * null) { inPacket.setFrom(inUnqualifiedFrom + ":" +
	 * inConnection.getHost()); } inConnection.sendPacket(inPacket); sent =
	 * true; } catch (RuntimeException anException) { // This space for rent. }
	 * } else { try { inConnection.login(mPassword); } catch (XMPPException e) {
	 *  LOGGER.fatal(e, e); } } } return sent;
	 * }
	 */

	/**
	 * Envoie un paquet.
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

	public void sendPacket(Packet inPacket) {
		if (isErlangNode() && (inPacket instanceof VioletApiPacket)) {
			// let's write in mnesia's table
			/*
			try {
				final OtpErlangPid selfPid = getErlangPid();
				final OtpConnection selfConnection = this.mComponentErlangNode.connect(new OtpPeer(Constantes.OTP_PEER_NODE_NAME));
				final OtpErlangTuple record = new OtpErlangTuple(new OtpErlangObject[] { new OtpErlangAtom(IQAbstractQuery.PENDING_REQUEST_RECORD), new OtpErlangAtom(inPacket.getPacketID()), selfPid });
				selfConnection.sendRPC("mnesia", "dirty_write", new OtpErlangObject[] { record });
				final OtpErlangObject response = selfConnection.receive();
				if (!(response instanceof OtpErlangTuple) || !JabberComponent.OTP_OK.equals(((OtpErlangAtom) ((OtpErlangTuple) response).elementAt(1)).toString())) {
					JabberComponent.LOGGER.fatal("Unable to write the record into the pending_request mnesia's table");
				}
				if (selfConnection.isConnected()) {
					selfConnection.close();
				}
			} catch (final IOException e) {
				JabberComponent.LOGGER.fatal(e, e);
			} catch (final OtpErlangExit e) {
				JabberComponent.LOGGER.fatal(e, e);
			} catch (final OtpAuthException e) {
				JabberComponent.LOGGER.fatal(e, e);
			}
			*/
		}

		final String theFromStr = inPacket.getFrom();
		final String theUnqualifiedFrom;
		JabberComponent.LOGGER.info("sendMessage: id = " + inPacket.getPacketID() + " from = " + theFromStr + " to = " + inPacket.getTo());

		if (theFromStr != null) {
			final JID theFromJID = new JID(theFromStr);
			final String theResource = theFromJID.getResource();
			if (theResource == null) {
				theUnqualifiedFrom = getDefaultFromAddress();
			} else {
				theUnqualifiedFrom = theFromStr;
			}
		} else {
			// On ne modifie pas le from.
			theUnqualifiedFrom = null;
		}

		if (this.mConnection.isConnected()) {
			if (this.mConnection.isHandshaked()) {
				JabberComponent.LOGGER.debug("Connection established");
				try {
					if (theUnqualifiedFrom != null) {
						inPacket.setFrom(theUnqualifiedFrom);
					}
					this.mConnection.sendPacket(inPacket);
				} catch (final RuntimeException anException) {
					this.mQueue.add(inPacket);
				}
			}
		} else {
			this.mQueue.add(inPacket);
		}
	}

	/**
	 * Accesseur sur la connection XMPP
	 * 
	 * @return la connection XMPP.
	 */
	public XMPPConnection getXMPPConnection() {
		/*
		 * Collections.shuffle(mListConnections); for (final
		 * Iterator<XMPPConnection> listConnectionsIterator =
		 * mListConnections.iterator(); listConnectionsIterator.hasNext(); ) {
		 * final XMPPConnection mConnection = listConnectionsIterator.next(); if
		 * (mConnection.isConnected() && mConnection.isHandshaked()) { return
		 * mConnection; } mListConnections.remove(mConnection); } // No good
		 * connection availlable for (Pair<String,Integer> hostInfos :
		 * mListHostName) { final XMPPConnection mConnection = new
		 * XMPPConnection(hostInfos.getFirst(), hostInfos.getSecond(),
		 * mComponentName, mPassword); try { mConnection.connect();
		 * mConnection.addConnectionListener(this);
		 * mMapConnections.put(mConnection.getHost(), mConnection);
		 * mListConnections.add(mConnection);
		 * ProbesHandler.COMPONENT.addConnectedComponent(mComponentName); //
		 * Let's go break; } catch (XMPPException anException) {
		 * LOGGER.debug("[Exception] " + anException.toString());
		 * LOGGER.fatal(anException, anException); LOGGER.debug("[" +
		 * mComponentName + "] Echec de la connexion"); } } return null;
		 */
		return this.mConnection;
	}

	/**
	 * Envoi un message à un objet.
	 */
	public void sendMessage(Message inMessage, int mode) {
		final Packet thePacket = getPacket(inMessage, mode);
		sendPacket(thePacket);
	}

	public Packet getPacket(Message inMessage, int mode) {
		return JabberComponent.getPacket(inMessage, mode, this.mComponentFromAddress);
	}

	public String getDefaultFromAddress() {
		return this.mComponentFromAddress;
	}

	public static String getDefaultFromAddress(String componentName) {
		return ProcessName.getProcessName().replace("/", "-") + "@" + componentName + "/" + ProcessName.getProcessName().replace("/", "-");
	}

	public static String getDefaultOtpNodeName(String componentName) {
		return ProcessName.getProcessName().replace("/", "-");
	}

	public static Packet getPacket(Message inMessage, int mode, String from) {
		return JabberMessageFactory.getJabberMessagePacket(inMessage, null, mode, from);
	}

	public void connectionClosed() {
		JabberComponent.LOGGER.info("[" + this.mComponentName + "] XMPP Connection Closed");
		ProbesHandler.COMPONENT.delConnectedComponent(this.mComponentName);
	}

	public void connectionClosedOnError(Exception e) {
		JabberComponent.LOGGER.fatal(e, e);
	}

	public void reconnectingIn(int seconds) {
		JabberComponent.LOGGER.info("[" + this.mComponentName + "] XMPP Reconnecting in " + seconds + " secs");
	}

	public void reconnectionFailed(Exception e) {
		JabberComponent.LOGGER.fatal(e, e);
	}

	/*
	 * public void reconnectionSuccessful() { LOGGER.info("[" + mComponentName +
	 * "] XMPP Reconnection Successful");
	 * ProbesHandler.COMPONENT.addConnectedComponent(mComponentName);
	 * synchronized (mQueue) { while (true) { final Packet thePacket =
	 * mQueue.poll(); if (thePacket == null) {
	 * System.out.println("No packet 2 send"); break; } try { final
	 * XMPPConnection theConnection = getXMPPConnection(); if
	 * (theConnection==null) { throw new RuntimeException(mComponentName +
	 * " is not connected."); } theConnection.sendPacket(thePacket); } catch
	 * (RuntimeException anException) { LOGGER.fatal(anException, anException);
	 * mQueue.add(thePacket); break; } } } }
	 */

	public void reconnectionSuccessful() {
		JabberComponent.LOGGER.info("[" + this.mComponentName + "] XMPP Reconnection Successful");
		ProbesHandler.COMPONENT.addConnectedComponent(this.mComponentName);
		synchronized (this.mQueue) {
			while (true) {
				final Packet thePacket = this.mQueue.poll();
				if (thePacket == null) {
					break;
				}
				try {
					this.mConnection.sendPacket(thePacket);
				} catch (final RuntimeException anException) {
					JabberComponent.LOGGER.fatal(anException, anException);
					this.mQueue.add(thePacket);
					break;
				}
			}
		}
	}

	/**
	 * Ferme la connexion XMPP.
	 */
	public void close() {
		synchronized (this.mQueue) {
			this.mQueue.clear();
			// for (XMPPConnection mConnection : mListConnections) {
			this.mConnection.disconnect();
			// }
		}
		ProbesHandler.COMPONENT.delConnectedComponent(this.mComponentName);
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
		theProviderManager.addIQProvider(VioletApiPacket.API_QUERY_TAG, VioletApiPacket.API_JSON_NAMESPACE, VioletApiPacket.IQ_PROVIDER);
		theProviderManager.addIQProvider(VioletAppletPacket.APPLET_QUERY_TAG, VioletAppletPacket.APPLET_JSON_NAMESPACE, VioletAppletPacket.IQ_PROVIDER);
		// Ajout du support pour les packets de type violet:packet:pingpong
		theProviderManager.addIQProvider(PingPongPacketTest.ELEMENT, PingPongPacketTest.NAMESPACE, PingPongPacketTest.IQ_PROVIDER);
	}

	public static Packet getPacket(Message inMessage, CCalendar inTimeOfDelivery, int mode, String from) {
		return JabberMessageFactory.getJabberMessagePacket(inMessage, inTimeOfDelivery, mode, from);
	}

	public boolean isErlangNode() {
		return (this.mComponentErlangNode == null) ? false : true;
	}

	public OtpErlangPid getErlangPid() {
		return this.mComponentErlangPid;
	}

	public OtpSelf getErlangNode() {
		return this.mComponentErlangNode;
	}

	@Override
	public String toString() {
		return this.mComponentName + " [" + this.mComponentFromAddress + "]";
	}

}

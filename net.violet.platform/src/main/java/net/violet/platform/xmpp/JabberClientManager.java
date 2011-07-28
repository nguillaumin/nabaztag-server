package net.violet.platform.xmpp;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe pour gérer les clients jabber dans un process java donné.
 */
public class JabberClientManager {

	/**
	 * Référence sur les clients, par ressource.
	 */
	private static final Map<String, JabberClient> JABBER_INSTANCES = new HashMap<String, JabberClient>();

	/**
	 * Référence sur le client jabber par défaut (ressource nulle)
	 */
	// private static JabberClient gDefaultClient;
	/**
	 * Accesseur sur le client.
	 * 
	 * @return le client jabber.
	 */
	public static JabberClient getClient() {
		return JabberClientManager.getClient(null);
	}

	/**
	 * Accesseur sur le client à partir d'une ressource.
	 * 
	 * @return le client jabber.
	 */
	/*
	 * public static JabberClient getClient(String inResource) { JabberClient
	 * theClient = null; synchronized (JABBER_INSTANCES) { String theResource =
	 * inResource; if (theResource == null) { theClient = gDefaultClient; if
	 * (theClient == null) { theResource = ProcessName.getProcessName() +
	 * StringShop.UNDERSCORE + System.currentTimeMillis() +
	 * StringShop.UNDERSCORE + System.nanoTime(); theClient = new
	 * JabberClient(theResource); gDefaultClient = theClient; } } else {
	 * theClient = JABBER_INSTANCES.get(theResource); if (theClient == null) {
	 * theClient = new JabberClient(theResource);
	 * JABBER_INSTANCES.put(inResource, theClient); } } } return theClient; }
	 */

	public static JabberClient getClient(String inResourceServer) {
		JabberClient theClient = null;
		final String infos[] = inResourceServer.split(";");
		final String inResource = infos[0];
		final String inXmppServer = (infos.length > 1) ? infos[1] : null;
		synchronized (JabberClientManager.JABBER_INSTANCES) {
			if (inResource != null) {
				theClient = JabberClientManager.JABBER_INSTANCES.get(inResource);
				if (theClient == null) {
					if (inXmppServer != null) {
						theClient = new JabberClient(inResource, inXmppServer);
					} else {
						theClient = new JabberClient(inResource);
					}
					JabberClientManager.JABBER_INSTANCES.put(inResource, theClient);
				}
			}
		}
		return theClient;
	}

	/*
	 * public static JabberClient getXmlRpcClient(String inResource) {
	 * JabberClient theClient; synchronized (JABBER_INSTANCES) { String
	 * theResource = inResource; if (theResource == null) { theClient =
	 * gDefaultClient; if (theClient == null) { theResource =
	 * ProcessName.getProcessName() + StringShop.UNDERSCORE +
	 * System.currentTimeMillis() + StringShop.UNDERSCORE + System.nanoTime();
	 * theClient = new JabberClient(theResource,
	 * JabberClient.JABBER_LISTENING_MODES.XMLRPC); gDefaultClient = theClient;
	 * } } else { theClient = JABBER_INSTANCES.get(theResource); if (theClient
	 * == null) { theClient = new JabberClient(theResource,
	 * JabberClient.JABBER_LISTENING_MODES.XMLRPC);
	 * JABBER_INSTANCES.put(inResource, theClient); } } } return theClient; }
	 */

	public static JabberClient getXmlRpcClient(String inResource) {
		JabberClient theClient = null;
		synchronized (JabberClientManager.JABBER_INSTANCES) {
			if (inResource != null) {
				theClient = JabberClientManager.JABBER_INSTANCES.get(inResource);
				if (theClient == null) {
					theClient = new JabberClient(inResource, JabberClient.JABBER_LISTENING_MODES.XMLRPC);
					JabberClientManager.JABBER_INSTANCES.put(inResource, theClient);
				}
			}
		}
		return theClient;
	}

	public static void shutdown() {
		synchronized (JabberClientManager.JABBER_INSTANCES) {
			for (final JabberClient theClient : JabberClientManager.JABBER_INSTANCES.values()) {
				theClient.close();
			}
			/*
			 * if (gDefaultClient != null) { gDefaultClient.close(); }
			 */
		}
	}
}

package net.violet.platform.xmpp;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.xmpp.packet.JabberRPC;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.xml.sax.SAXException;

public final class JabberRPCClientQuery {


	private static final Logger LOGGER = Logger.getLogger(JabberRPCClientQuery.class);

	/**
	 * Ensemble des requêtes en cours.
	 */
	private final Map<String, Object> mRequests = new HashMap<String, Object>();

	/**
	 * Singleton.
	 */
	private static final JabberRPCClientQuery SINGLE_INSTANCE = new JabberRPCClientQuery();

	private JabberRPCClientQuery() {
	}

	private Object doGetJabberRPCResult(final String from, final String methodName, final List<Object> params) {
		String theRequestID;
		final Object theResult = new Object(); // empty object
		synchronized (this.mRequests) {
			// Choix d'un id unique de requête.
			do {
				theRequestID = "JabberRPC-" + System.currentTimeMillis();
			} while (this.mRequests.containsKey(theRequestID));

			this.mRequests.put(theRequestID, theResult);
		}

		synchronized (theResult) {
			try {
				new JabberRPC(from, methodName, params);
			} catch (final XmlRpcException e1) {
				JabberRPCClientQuery.LOGGER.fatal(e1, e1);
			} catch (final SAXException e1) {
				JabberRPCClientQuery.LOGGER.fatal(e1, e1);
			} catch (final IOException e1) {
				JabberRPCClientQuery.LOGGER.fatal(e1, e1);
			}

			// Attente de la réponse.
			try {
				theResult.wait();
			} catch (final InterruptedException e) {
				JabberRPCClientQuery.LOGGER.fatal(e, e);
			}

		}

		return theResult;
	}

	/**
	 * Envoie une requête.
	 */
	public static Object getClientResult(final String from, final String methodName, final List<Object> params) {
		return JabberRPCClientQuery.SINGLE_INSTANCE.doGetJabberRPCResult(from, methodName, params);
	}

	public static void notifyResult(String inPacketId, Object inResult) {
		JabberRPCClientQuery.SINGLE_INSTANCE.doNotifyResult(inPacketId, inResult);
	}

	private void doNotifyResult(String inPacketId, Object inResult) {
		synchronized (this.mRequests) {
			Object theResultHolder = this.mRequests.get(inPacketId);
			if (theResultHolder != null) {
				theResultHolder = inResult;
				synchronized (theResultHolder) {
					theResultHolder.notify();
				}
			}
		}

	}

}

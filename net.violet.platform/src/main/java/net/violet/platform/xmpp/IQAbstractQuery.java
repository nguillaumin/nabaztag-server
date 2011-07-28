package net.violet.platform.xmpp;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.util.Pair;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.XMPPError;

/**
 * Classe abstraite pour les requètes IQ ()
 */
public abstract class IQAbstractQuery<T extends Packet, ResultHolder> {

	private static final Logger LOGGER = Logger.getLogger(IQAbstractQuery.class);
	public static final String PENDING_REQUEST_RECORD = "pending_iq_request";
	/**
	 * This Map is used to associate each class implementing the IQAbstractQuery
	 * with its instance. For each implementation (such as IQApiQuery or
	 * IQResourcesQuery) can only have one instance for each kind of
	 * JabberPacketSender.
	 */
	//private static final Map<Class<? extends IQAbstractQuery>, Map<String, ? extends IQAbstractQuery>> INSTANCES = new HashMap<Class<? extends IQAbstractQuery>, Map<String, ? extends IQAbstractQuery>>();
	/**
	 * Référence sur le client.
	 */
	private final JabberPacketSender mSender;

	/**
	 * Ensemble des requêtes en cours.
	 */
	protected static final Map<String, Pair<? extends IQAbstractQuery, Object>> PENDING_REQUESTS = new HashMap<String, Pair<? extends IQAbstractQuery, Object>>();

	/**
	 * @param inSender
	 */
	protected IQAbstractQuery(JabberPacketSender inSender) {
		this.mSender = inSender;
	}

	/**
	 * Envoie un requête IQ et attend la réponse
	 * 
	 * @param inXMPPAddress adresse xmpp du lapin
	 * @param ressource la ressource actuelle du lapin
	 * @param type le type de demande qu'on veut faire, getConfig ou
	 *            getRunningState
	 * @return
	 */
	protected static <H extends IQAbstractQuery, T extends Packet, ResultHolder> void sendPacketAndWaitForResult(H inQuery, Packet inPacket, ResultHolder inResult) {

		inQuery.mSender.sendPacket(inPacket);

		// Attente de la réponse.
		final long before = System.currentTimeMillis();
		try {
			// wait to be notified
			inResult.wait(10000);
		} catch (final InterruptedException e) {
			IQAbstractQuery.LOGGER.fatal(e, e);
		}

		// were we really woken up or did the time out occured ?
		final long after = System.currentTimeMillis();
		if (after - before >= 10000) {
			IQAbstractQuery.LOGGER.fatal("LOST PACKET sendPacketAndWaitForResult: waited more than 10 seconds for packet (" + inPacket.getPacketID() + ") to " + inPacket.getTo() + " - Awaiting requests : " + IQAbstractQuery.PENDING_REQUESTS.size());
			// notify timeout will remove this pending request with nothing in
			// the result holder
			IQAbstractQuery.doNotifyTimeout((JabberComponent) inQuery.mSender, inQuery.getClass(), (T) inPacket);
		}
	}

	/**
	 * Response received or timeout
	 * 
	 * @param inPacket
	 * @param inSuccess
	 */
	private static <T extends Packet, ResultHolder> void doNotify(T inPacket, JabberComponent inComponent, boolean inSuccess) {

		final String thePacketID = inPacket.getPacketID();
		final Pair<? extends IQAbstractQuery, Object> pendingRequest;

		//final OtpErlangPid selfPid = inComponent.getErlangPid();

		final boolean hitPacket = true; //false;

		IQAbstractQuery.LOGGER.info("doNotify: Packet received " + inPacket.toXML());

		/*
		if ((inPacket instanceof VioletApiPacket) && ((((VioletApiPacket) inPacket).getType() == Type.ERROR) || (((VioletApiPacket) inPacket).getType() == Type.RESULT))) {
			try {
				final OtpConnection selfConnection = inComponent.getErlangNode().connect(new OtpPeer(Constantes.OTP_PEER_NODE_NAME));
				selfConnection.sendRPC("mnesia", "dirty_read", new OtpErlangObject[] { new OtpErlangAtom(IQAbstractQuery.PENDING_REQUEST_RECORD), new OtpErlangAtom(inPacket.getPacketID()) });
				final OtpErlangTuple result = (OtpErlangTuple) selfConnection.receive();
				IQAbstractQuery.LOGGER.info("Mnesia record : " + result);
				if ((result.arity() > 0) && (result.elementAt(1) instanceof OtpErlangList)) {
					final OtpErlangTuple resultTuple = (OtpErlangTuple) ((OtpErlangList) result.elementAt(1)).elementAt(0);
					if ((resultTuple.arity() == 3) && (resultTuple.elementAt(2) instanceof OtpErlangPid)) {
						final OtpErlangPid destPid = (OtpErlangPid) resultTuple.elementAt(2);
						if (destPid.equals(selfPid)) {
							hitPacket = true;
						} else {
							final OtpErlangTuple msg = new OtpErlangTuple(new OtpErlangObject[] { new OtpErlangList(inPacket.getPacketID()), new OtpErlangList(inPacket.toXML()) });
							// Send packect to the good node
							selfConnection.send(destPid, msg);
						}
					}
				} else {
					IQAbstractQuery.LOGGER.info("Failed of mmesia:dirty_read [ result : " + result.toString() + "]");
				}
				selfConnection.close();
			} catch (final IOException e) {
				IQAbstractQuery.LOGGER.fatal(e, e);
			} catch (final OtpErlangExit e) {
				IQAbstractQuery.LOGGER.fatal(e, e);
			} catch (final OtpAuthException e) {
				IQAbstractQuery.LOGGER.fatal(e, e);
			}
		} else {
			hitPacket = true;
		}
		*/

		IQAbstractQuery.LOGGER.info("Packet hit in this applet listener ? " + hitPacket);

		if (hitPacket) {
			/*
			try {
				final OtpConnection selfConnection = inComponent.getErlangNode().connect(new OtpPeer(Constantes.OTP_PEER_NODE_NAME));
				selfConnection.sendRPC("mnesia", "dirty_delete", new OtpErlangObject[] { new OtpErlangAtom(IQAbstractQuery.PENDING_REQUEST_RECORD), new OtpErlangAtom(inPacket.getPacketID()) });
				selfConnection.close();
			} catch (final UnknownHostException e) {
				IQAbstractQuery.LOGGER.fatal(e, e);
			} catch (final OtpAuthException e) {
				IQAbstractQuery.LOGGER.fatal(e, e);
			} catch (final IOException e) {
				IQAbstractQuery.LOGGER.fatal(e, e);
			}
			*/

			synchronized (IQAbstractQuery.PENDING_REQUESTS) {
				pendingRequest = IQAbstractQuery.PENDING_REQUESTS.remove(thePacketID);
			}

			if (pendingRequest != null) {
				final ResultHolder resultHolder = (ResultHolder) pendingRequest.getSecond();
				//final ResultHolder resultHolder = (ResultHolder) IQAbstractQuery.PENDING_REQUESTS.remove(thePacketID);

				if (resultHolder != null) {
					synchronized (resultHolder) {
						if (inSuccess) {
							// take the packet result content and store it in the result
							// holder
							IQAbstractQuery.extractResultFromPacket(pendingRequest.getFirst(), inPacket, resultHolder);
						} else {
							IQAbstractQuery.setTimeoutError(pendingRequest.getFirst(), resultHolder);
						}
						// wake up the result holder
						resultHolder.notify();
					}
				} else {
					IQAbstractQuery.LOGGER.info("ResultHolder is null");
				}
			}
		}
	}

	public static <T extends Packet> void notifyResult(JabberComponent inComponent, T inPacket) {
		IQAbstractQuery.doNotify(inPacket, inComponent, true);
	}

	public static <T extends Packet> void notifyResult(JabberPacketSender inComponent, T inPacket) {
		IQAbstractQuery.doNotify(inPacket, (JabberComponent) inComponent, true);
	}

	protected void doNotifyResult(JabberComponent inComponent, T inPacket) {
		//IQAbstractQuery.removeFromInstanceMap(this.getClass(), inPacket.getPacketID()).doNotify(inPacket, true);
		IQAbstractQuery.doNotify(inPacket, inComponent, true);
	}

	protected static <T extends Packet, FinalClass extends IQAbstractQuery> void doNotifyTimeout(JabberComponent inComponent, Class<FinalClass> inClass, T inPacket) {
		//IQAbstractQuery.removeFromInstanceMap(inClass, inPacket.getPacketID()).doNotify(inPacket, false);
		IQAbstractQuery.doNotify(inPacket, inComponent, false);
	}

	/**
	 * Each concrete IQQuery class will have to provide the way they extract the
	 * packet content and store it in the result holder
	 * 
	 * @param inPacket
	 * @param inHolder
	 */
	protected abstract void extractResultFromPacket(T inPacket, ResultHolder inHolder);

	protected abstract void setTimeoutError(ResultHolder inHolder);

	protected static <T extends Packet, ResultHolder, FinalClass extends IQAbstractQuery> void extractResultFromPacket(FinalClass inQueryInstance, T inPacket, ResultHolder inHolder) {
		inQueryInstance.extractResultFromPacket(inPacket, inHolder);
	}

	protected static <ResultHolder, FinalClass extends IQAbstractQuery> void setTimeoutError(FinalClass inQueryInstance, ResultHolder inHolder) {
		inQueryInstance.setTimeoutError(inHolder);
	}

	/**
	 * @param <FinalClass>
	 * @param <T>
	 * @param inClass
	 * @return
	 */
	/*
	protected static <FinalClass extends IQAbstractQuery, T extends Packet> Map<String, FinalClass> getInstanceMap(Class<FinalClass> inClass) {
		Map<String, FinalClass> theResult;
		synchronized (IQAbstractQuery.INSTANCES) {
			theResult = (Map<String, FinalClass>) IQAbstractQuery.INSTANCES.get(inClass);
			if (theResult == null) {
				theResult = new HashMap<String, FinalClass>();
				IQAbstractQuery.INSTANCES.put(inClass, theResult);
			}
		}
		return theResult;
	}
	*/

	/**
	 * @param <FinalClass>
	 * @param inClass
	 * @param inPacketId
	 * @return
	 */
	/*
	protected static <FinalClass extends IQAbstractQuery> FinalClass removeFromInstanceMap(Class<FinalClass> inClass, String inPacketId) {
		final Map<String, FinalClass> theMap = IQAbstractQuery.getInstanceMap(inClass);
		final FinalClass theInstance = theMap.get(inPacketId);
		theMap.remove(theInstance);
		return theInstance;
	}
	*/

	protected static String getRequestID(String inPacketIdPrefix) {
		return inPacketIdPrefix + System.currentTimeMillis() + Math.random();
	}

	/**
	 * @param <T>
	 * @param <Q>
	 * @param <ResultHolder>
	 * @param inClass
	 * @param inPacketIdPrefix
	 * @param inSender
	 * @param inPacket
	 * @param inResultHolder
	 */
	protected static <T extends Packet, Q extends IQAbstractQuery, ResultHolder> void doSendPacket(Class<Q> inClass, String inPacketIdPrefix, JabberPacketSender inSender, T inPacket, ResultHolder inResultHolder) {

		final String requestID = IQAbstractQuery.getRequestID(inPacketIdPrefix);

		if (IQAbstractQuery.LOGGER.isInfoEnabled()) {
			IQAbstractQuery.LOGGER.info("We will send packet " + inPacket.getClass().getName() + " with id " + requestID);
		}

		/*
		synchronized (IQAbstractQuery.PENDING_REQUESTS) {
			do {
				requestID = inPacketIdPrefix + System.currentTimeMillis() + Math.random();
			} while (IQAbstractQuery.PENDING_REQUESTS.containsKey(requestID));
			IQAbstractQuery.PENDING_REQUESTS.put(requestID, inResultHolder);
		}
		*/

		Q iqQuery = null;

		final Class[] signature = new Class[] { JabberPacketSender.class };
		Constructor<Q> theConstructor = null;
		try {
			theConstructor = inClass.getDeclaredConstructor(signature);
		} catch (final SecurityException e) {
			IQAbstractQuery.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			IQAbstractQuery.LOGGER.fatal(e, e);
		}

		try {
			if (theConstructor != null) {
				iqQuery = theConstructor.newInstance(inSender);
			}
		} catch (final IllegalArgumentException e) {
			IQAbstractQuery.LOGGER.fatal(e, e);
		} catch (final InstantiationException e) {
			IQAbstractQuery.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			IQAbstractQuery.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			IQAbstractQuery.LOGGER.fatal(e, e);
		}

		final Pair<Q, Object> theQueryAndResult = new Pair<Q, Object>(iqQuery, inResultHolder);

		IQAbstractQuery.LOGGER.info("IQAbstractQuery : size of PENDING_REQUESTS before putting new request (" + requestID + ") : " + IQAbstractQuery.PENDING_REQUESTS.size());
		synchronized (IQAbstractQuery.PENDING_REQUESTS) {
			IQAbstractQuery.PENDING_REQUESTS.put(requestID, theQueryAndResult);
		}

		inPacket.setPacketID(requestID);

		//final Map<String, Q> theInstanceMap = IQAbstractQuery.getInstanceMap(inClass);

		/*
		synchronized (theInstanceMap) {
			theQuery = theInstanceMap.get(inPacket.getPacketID());
			if (theQuery == null) {
				final Class[] signature = new Class[] { JabberPacketSender.class };
				Constructor<Q> theConstructor = null;
				try {
					theConstructor = inClass.getDeclaredConstructor(signature);
				} catch (final SecurityException e) {
					IQAbstractQuery.LOGGER.fatal(e, e);
				} catch (final NoSuchMethodException e) {
					IQAbstractQuery.LOGGER.fatal(e, e);
				}

				try {
					if (theConstructor != null) {
						theQuery = theConstructor.newInstance(inSender);
						theInstanceMap.put(inPacket.getPacketID(), theQuery);
					}
				} catch (final IllegalArgumentException e) {
					IQAbstractQuery.LOGGER.fatal(e, e);
				} catch (final InstantiationException e) {
					IQAbstractQuery.LOGGER.fatal(e, e);
				} catch (final IllegalAccessException e) {
					IQAbstractQuery.LOGGER.fatal(e, e);
				} catch (final InvocationTargetException e) {
					IQAbstractQuery.LOGGER.fatal(e, e);
				}
			}
		}
		*/

		synchronized (inResultHolder) {
			//IQAbstractQuery.sendPacketAndWaitForResult(IQAbstractQuery.getInstanceMap(inClass).get(requestID), inPacket, inResultHolder);
			IQAbstractQuery.sendPacketAndWaitForResult(iqQuery, inPacket, inResultHolder);
		}
	}

	protected final JabberPacketSender getSender() {
		return this.mSender;
	}

	public static Map<String, Object> getPojoTimeoutError() {
		final Map<String, Object> errorMap = new HashMap<String, Object>(3);
		final XMPPError error = new XMPPError(XMPPError.Condition.request_timeout);
		errorMap.put("type", "error");
		errorMap.put("code", error.getCode());
		errorMap.put("title", error.getCondition());
		errorMap.put("message", error.getMessage());
		return errorMap;
	}

}

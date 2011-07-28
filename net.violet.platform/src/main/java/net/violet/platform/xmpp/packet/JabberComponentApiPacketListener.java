package net.violet.platform.xmpp.packet;

import java.util.Map;

import net.violet.platform.api.actions.APIController;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.callers.ComponentAPICaller;
import net.violet.platform.api.callers.ObjectAPICaller;
import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.ComponentPingPongPacketListenerTest;
import net.violet.platform.xmpp.JabberComponent;
import net.violet.platform.xmpp.PingPongPacketTest;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.XMPPError;
import org.jivesoftware.smack.packet.IQ.Type;

public class JabberComponentApiPacketListener extends AbstractPacketListener {

	private static final Logger LOGGER = Logger.getLogger(JabberComponentApiPacketListener.class);

	private static final int NB_THREADS = Constantes.NB_COMPOSANT_API_THREADS;

	/**
	 * référence sur le composant
	 */
	private final JabberComponent mComponent;

	public JabberComponentApiPacketListener(JabberComponent inComponent) {
		super(JabberComponentApiPacketListener.NB_THREADS);

		this.mComponent = inComponent;
	}

	@Override
	protected void doProcessPacket(Packet inPacket) {

		if (inPacket instanceof VioletApiPacket) {
			if (JabberComponentApiPacketListener.LOGGER.isDebugEnabled()) {
				JabberComponentApiPacketListener.LOGGER.debug("API PACKET RECEIVED : " + inPacket.toXML());
			}
			processVioletApiPacket((VioletApiPacket) inPacket);

		} else if (inPacket instanceof PingPongPacketTest) {
			JabberComponentApiPacketListener.LOGGER.debug("PING PONG PACKET RECEIVED");
			ComponentPingPongPacketListenerTest.processPingPongExtension(this.mComponent, (PingPongPacketTest) inPacket);

		} else {
			if (JabberComponentApiPacketListener.LOGGER.isDebugEnabled()) {
				JabberComponentApiPacketListener.LOGGER.debug("UNKNOWN PACKET RECEIVED (will not be treated ) : " + inPacket.toXML());
			}
		}
	}

	/**
	 * @param inApiPacket
	 */
	private void processVioletApiPacket(VioletApiPacket inApiPacket) {

		final Type packetType = inApiPacket.getType();

		if ((packetType == Type.GET) || (packetType == Type.SET)) {

			try {
				final JID fromJID = new JID(inApiPacket.getFrom());
				final String apiKey = fromJID.getUserName(); // hyper important selon Christophe: "6992873d28d86925325dc52d15d6feec30bb2da5" ;
				final String fromDomain = fromJID.getDomain();

				final String methodName = new JID(inApiPacket.getTo()).getUserName();

				APICaller apiCaller = null;

				if (fromDomain.equals(Constantes.XMPP_MIRROR_DOMAIN)) {
					final String serial = apiKey.substring(0, 8);
					final VObject theObject = Factories.VOBJECT.findBySerial(serial);
					if (theObject == null) {
						JabberComponentApiPacketListener.LOGGER.error("Unknown API caller : " + fromJID);
						throw new ForbiddenException();
					}
					apiCaller = new ObjectAPICaller(theObject, apiKey);

				} else {
					final ApplicationCredentialsData credentials = ApplicationCredentialsData.findByPublicKey(apiKey);
					if ((credentials == null) || !credentials.isValid()) {
						JabberComponentApiPacketListener.LOGGER.error("Unknown API caller : " + fromDomain);
						throw new ForbiddenException();
					}

					final APICaller.CallerClass callerClass = credentials.getCallerClass();

					/*
					 * Application receive encrypted Ids, so they must be
					 * identified to decrypt the ID
					 */
					if (callerClass == APICaller.CallerClass.APPLICATION) {
						apiCaller = new ApplicationAPICaller(credentials);

					} else if (callerClass == APICaller.CallerClass.COMPONENT) {
						/*
						 * Alternatively, a component like AppResourcesLoader
						 * receives directly the application public key
						 */
						apiCaller = new ComponentAPICaller(apiKey);
					}
				}
				final Map<String, Object> methodParams = inApiPacket.getAPICallParams();
				final ActionParam theParams = new ActionParam(apiCaller, methodParams);
				final Action action = APIController.getAction(methodName);

				final long timeElapsed = System.currentTimeMillis();
				final Object apiResponse = action.processRequest(theParams);
				JabberComponentApiPacketListener.LOGGER.info("Request [ID : " + inApiPacket.getPacketID() + " ] call for " + methodName + " lasted " + (System.currentTimeMillis() - timeElapsed));

				// Sends the response
				inApiPacket.setType(Type.RESULT);
				inApiPacket.setPojoContent(apiResponse);
				final String from = inApiPacket.getTo();
				inApiPacket.setTo(inApiPacket.getFrom());
				inApiPacket.setFrom(from);
				this.mComponent.sendPacket(inApiPacket);

			} catch (final Exception e) {
				JabberComponentApiPacketListener.LOGGER.error("Error when processing API packet.", e);

				// Return an iq with error
				final String from = inApiPacket.getTo();
				inApiPacket.setTo(inApiPacket.getFrom());
				inApiPacket.setFrom(from);
				inApiPacket.setType(Type.ERROR);

				if (e instanceof ConversionException) {
					inApiPacket.setError(new XMPPError(XMPPError.Condition.no_acceptable, e.getMessage()));
				} else if (e instanceof ForbiddenException) {
					inApiPacket.setError(new XMPPError(XMPPError.Condition.forbidden, e.getMessage()));
				} else {
					inApiPacket.setError(new XMPPError(XMPPError.Condition.bad_request, e.getMessage()));
				}
				this.mComponent.sendPacket(inApiPacket);

			}

		}
	}

}

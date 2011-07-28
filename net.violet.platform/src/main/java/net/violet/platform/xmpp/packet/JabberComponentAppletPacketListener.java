package net.violet.platform.xmpp.packet;

import java.util.List;
import java.util.Map;

import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.applets.api.ScriptableApplet;
import net.violet.platform.applets.api.ScriptableAppletFactory;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.MessageServices;
import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.ComponentPingPongPacketListenerTest;
import net.violet.platform.xmpp.IQAbstractQuery;
import net.violet.platform.xmpp.JabberComponent;
import net.violet.platform.xmpp.JabberPacketSender;
import net.violet.platform.xmpp.PingPongPacketTest;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.IQ.Type;

public class JabberComponentAppletPacketListener extends AbstractPacketListener {

	private static final Logger LOGGER = Logger.getLogger(JabberComponentAppletPacketListener.class);

	private static final int NB_THREADS = Constantes.NB_COMPOSANT_APPLET_THREADS;

	/**
	 * référence sur le composant
	 */
	private final JabberPacketSender mComponent;

	public JabberComponentAppletPacketListener(JabberPacketSender inComponent) {
		super(JabberComponentAppletPacketListener.NB_THREADS);
		this.mComponent = inComponent;
		/*
		if (((JabberComponent) this.mComponent).isErlangNode()) {
			new Thread(new OtpApiPacketListener((JabberComponent) this.mComponent)).start();
		}
		*/
	}

	@Override
	protected void doProcessPacket(Packet inPacket) {

		if (JabberComponentAppletPacketListener.LOGGER.isInfoEnabled()) {
			JabberComponentAppletPacketListener.LOGGER.info("AppletPacketListener received " + inPacket.getPacketID());
		}

		if (inPacket instanceof VioletAppletPacket) {
			processVioletAppletPacket((VioletAppletPacket) inPacket);

		} else if (inPacket instanceof VioletApiPacket) {
			processVioletApiPacket((VioletApiPacket) inPacket);

		} else if (inPacket instanceof PingPongPacketTest) {
			ComponentPingPongPacketListenerTest.processPingPongExtension(this.mComponent, (PingPongPacketTest) inPacket);
		}// ...
	}

	/**
	 * Receive an event packet from the platform >> find the application to
	 * process it
	 * 
	 * @param inPacket
	 */
	private void processVioletAppletPacket(VioletAppletPacket inPacket) {
		if (JabberComponentAppletPacketListener.LOGGER.isInfoEnabled()) {
			JabberComponentAppletPacketListener.LOGGER.info("Process VioletAppletPacket : " + inPacket.getPacketID() + " type " + ((inPacket.getType() == Type.GET) ? "GET" : "RESULT/ERROR"));
		}

		if (inPacket.getType() == Type.GET) {

			final String apiKey = inPacket.getTo().split("@")[0];

			try {

				final PojoMap eventMap = inPacket.getEventMap();
				final ScriptableApplet app = ScriptableAppletFactory.getApplet(apiKey, eventMap.getDate("application.publication"));
				JabberComponentAppletPacketListener.LOGGER.debug("ScriptableApplet received = " + app);

				// Call application processEvent
				final List<Object> appResponse = app.processEvent(eventMap);

				for (final Object oneResponse : appResponse) {
					final Map<String, Object> aPojoMessage = (Map<String, Object>) oneResponse;

					for (final MessageDraft msg : MessageDraft.createFromPojo(aPojoMessage, null, apiKey)) {
						if (eventMap.get("ttl") != null) {
							msg.setTTLInSecond(eventMap.getInteger("ttl"));
						}
						MessageServices.send(msg, (JabberComponent) this.mComponent);
					}
				}

				// Sends the response
				/*
				 * inPacket.setType(Type.RESULT);
				 * inPacket.setPojoContent(appResponse); final String from =
				 * inPacket.getTo(); inPacket.setTo(inPacket.getFrom());
				 * inPacket.setFrom(from); mComponent.sendPacket(inPacket);
				 */

			} catch (final Exception e) {
				JabberComponentAppletPacketListener.LOGGER.fatal("Unexpected error when treating packet " + inPacket.toXML(), e);
				/*
				 * // Return an iq with error inPacket.setType(Type.ERROR);
				 * inPacket.setError(new XMPPError(XMPPError.Condition.no_acceptable, e.getMessage())); 
				 * final String from = inPacket.getTo();
				 * inPacket.setTo(inPacket.getFrom()); 
				 * inPacket.setFrom(from);
				 * mComponent.sendPacket(inPacket);
				 */
			} finally {
				VioletAppletPacket.sendDefaultResponse((JabberComponent) this.mComponent, inPacket);
			}

		}
	}

	/**
	 * @param inApiPacket
	 */
	private void processVioletApiPacket(VioletApiPacket inApiPacket) {
		if (JabberComponentAppletPacketListener.LOGGER.isInfoEnabled()) {
			JabberComponentAppletPacketListener.LOGGER.info("Process VioletApiPacket : " + inApiPacket.getPacketID());
		}

		final Type packetType = inApiPacket.getType();

		if ((packetType == Type.ERROR) || (packetType == Type.RESULT)) {
			IQAbstractQuery.notifyResult(this.mComponent, inApiPacket);
		}

	}

	/**
	 * 
	 * @param inComponent
	 * @param inApiPacket
	 */
	public static void processVioletApiPacket(JabberComponent inComponent, VioletApiPacket inApiPacket) {

		final Type packetType = inApiPacket.getType();

		if ((packetType == Type.ERROR) || (packetType == Type.RESULT)) {
			IQAbstractQuery.notifyResult(inComponent, inApiPacket);
		}

	}

}

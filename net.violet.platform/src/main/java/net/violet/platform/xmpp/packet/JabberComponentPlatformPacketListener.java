package net.violet.platform.xmpp.packet;

import net.violet.platform.datamodel.InterruptionLogImpl;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.ping.EventMng;
import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.ComponentPingPongPacketListenerTest;
import net.violet.platform.xmpp.IQAbstractQuery;
import net.violet.platform.xmpp.JabberComponent;
import net.violet.platform.xmpp.PingPongPacketTest;
import net.violet.platform.xmpp.JabberMessageFactory.IQVioletPacket;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.packet.XMPPError;
import org.jivesoftware.smack.packet.IQ.Type;

public class JabberComponentPlatformPacketListener extends AbstractPacketListener {

	private static final Logger LOGGER = Logger.getLogger(JabberComponentPlatformPacketListener.class);

	private static final int NB_THREADS = Constantes.NB_COMPOSANT_PLATFORM_THREADS;

	/**
	 * référence sur le composant
	 */
	private final JabberComponent mComponent;

	public JabberComponentPlatformPacketListener(JabberComponent inComponent) {
		super(JabberComponentPlatformPacketListener.NB_THREADS);

		this.mComponent = inComponent;
	}

	@Override
	protected void doProcessPacket(Packet inPacket) {

		if (inPacket instanceof IQResourcesPacket) {
			JabberComponentPlatformPacketListener.LOGGER.debug("Process IQResourcesPacket : " + inPacket.toXML());
			processIQResourcesPacket((IQResourcesPacket) inPacket);

		} else if (inPacket instanceof IQPresenceOfflinePacket) {
			JabberComponentPlatformPacketListener.LOGGER.debug("Process IQPresenceOfflinePacket : " + inPacket.toXML());
			processIQPresenceOfflinePacket((IQPresenceOfflinePacket) inPacket);

		} else if (inPacket instanceof PingPongPacketTest) {
			JabberComponentPlatformPacketListener.LOGGER.debug("Process PingPongPacket : " + inPacket.toXML());
			ComponentPingPongPacketListenerTest.processPingPongExtension(this.mComponent, (PingPongPacketTest) inPacket);

		} else if (inPacket instanceof Message) {
			JabberComponentPlatformPacketListener.LOGGER.debug("Process Message : " + inPacket.toXML());
			processMessagePacket((Message) inPacket);

		} else if (inPacket instanceof IQCommandPacket) {
			JabberComponentPlatformPacketListener.LOGGER.debug("Process IQCommandPacket : " + inPacket.toXML());
			processIQConfigPacket((IQCommandPacket) inPacket);

		} else if (inPacket instanceof IQVioletPacket) {
			JabberComponentPlatformPacketListener.LOGGER.debug("Process IQVioletPacket : " + inPacket.toXML());
			processIQVioletPacket((IQVioletPacket) inPacket);

		} else if (inPacket instanceof VioletAppletPacket) {
			JabberComponentPlatformPacketListener.LOGGER.debug("Process VioletAppletPacket : " + inPacket.toXML());
			processVioletAppletPacket((VioletAppletPacket) inPacket);
		} else if (inPacket instanceof VioletApiPacket) {
			JabberComponentPlatformPacketListener.LOGGER.debug("Process VioletApiPacket : " + inPacket.toXML());
			processVioletApiPacket((VioletApiPacket) inPacket);

		} else if (inPacket instanceof IQ) {
			JabberComponentPlatformPacketListener.LOGGER.debug("Process Unknown packet type : " + inPacket.toXML());
			processUnknownIQPacket((IQ) inPacket);

		} else {
			JabberComponentPlatformPacketListener.LOGGER.debug("RECEIVED UNKNOWN PACKET " + inPacket.toXML());
		}

	}

	private void processIQResourcesPacket(IQResourcesPacket inPacket) {
		IQAbstractQuery.notifyResult(this.mComponent, inPacket);
	}

	private void processMessagePacket(Message inPacket) {
		// On cherche les extensions.
		for (final PacketExtension theExtension : inPacket.getExtensions()) {
			if (theExtension instanceof AmpPacket) {
				JabberComponentPlatformPacketListener.LOGGER.debug("Process AmpPacket");
				processAmpExtension(inPacket, (AmpPacket) theExtension);
			}
		}
	}

	private void processAmpExtension(Message inMessage, AmpPacket inExtension) {
		if ("notify".equals(inExtension.getStatus())) {
			JabberComponentPlatformPacketListener.LOGGER.debug("Process Notify for AmpPacket");
			final String theID = inMessage.getPacketID();
			final net.violet.platform.datamodel.Message theMessage = Factories.MESSAGE.findByXMPPID(theID);
			if (theMessage != null) {
				VObject theTo = Factories.VOBJECT.findByJID(inExtension.getTo());
				final MessageReceived theMsgReceived = Factories.MESSAGE_RECEIVED.findMessageReceivedByMessageId(theMessage.getId());
				if (!theTo.equals(theMsgReceived.getRecipient().getObject())) {
					theTo = theMsgReceived.getRecipient().getObject();
				}
				EventMng.played(theTo, theMessage, true);
			}
		}
	}

	private void processIQVioletPacket(IQVioletPacket inPacket) {
		JabberComponentPlatformPacketListener.LOGGER.info("From [" + inPacket.getFrom() + "] To [" + inPacket.getTo() + "]");
		final String[] violetIQInfos = inPacket.getPacketID().split("_");
		final int status = Integer.parseInt(violetIQInfos[2]);
		final VObject theObject = Factories.VOBJECT.find(new Long(violetIQInfos[1]));
		if (theObject != null) {
			if (inPacket.getType() == Type.ERROR) {
				JabberComponentPlatformPacketListener.LOGGER.info("processIQVioletPacket : IQ response of " + theObject.getObject_login() + " for the status [" + status + "] is error");
				// Echec
				switch (status) {

				case net.violet.platform.message.Message.MODE_ACTIF:
					theObject.setState(VObject.STATUS_WILLBE_ACTIF);
					break;
				case net.violet.platform.message.Message.MODE_VEILLE:
					theObject.setState(VObject.STATUS_WILLBE_VEILLE);
					break;
				case net.violet.platform.message.Message.MODE_FORCE_ACTIF:
					theObject.setState(VObject.STATUS_WILLBE_FORCE_ACTIF);
					break;
				case net.violet.platform.message.Message.MODE_FORCE_VEILLE:
					theObject.setState(VObject.STATUS_WILLBE_FORCE_VEILLE);
					break;
				}
			}
		}
	}

	private void processUnknownIQPacket(IQ inPacket) {
		if (inPacket.getType() == Type.RESULT) {
			final String packetId = inPacket.getPacketID();
			if (packetId.contains("IQVioletPacket")) {
				final String[] violetIQInfos = packetId.split("_");
				final VObject violetObj = Factories.VOBJECT.find(Long.parseLong(violetIQInfos[1]));
				final int violetObjStatus = Integer.parseInt(violetIQInfos[2]);
				JabberComponentPlatformPacketListener.LOGGER.info("processIQVioletPacket : IQ response of " + violetObj.getObject_login() + " for the status [" + violetObjStatus + "] is good");
				switch (violetObjStatus) {

				case net.violet.platform.message.Message.MODE_ACTIF:
					violetObj.setState(VObject.STATUS_ACTIF);
					break;
				case net.violet.platform.message.Message.MODE_VEILLE:
					violetObj.setState(VObject.STATUS_VEILLE);
					break;
				case net.violet.platform.message.Message.MODE_FORCE_ACTIF:
					violetObj.setState(VObject.STATUS_FORCE_ACTIF);
					break;
				case net.violet.platform.message.Message.MODE_FORCE_VEILLE:
					violetObj.setState(VObject.STATUS_FORCE_VEILLE);
					break;
				}
			}
		} else if ((inPacket.getType() == Type.GET) || (inPacket.getType() == Type.SET)) {
			bounceIQPacket(inPacket);
		}
	}

	private void processIQPresenceOfflinePacket(IQPresenceOfflinePacket inPacket) {
		final VObject objectOffline = Factories.VOBJECT.findByJID(inPacket.getFrom());
		JabberComponentPlatformPacketListener.LOGGER.debug("new IQPresenceOfflinePacket for [" + inPacket.getFrom() + "]");
		if (objectOffline != null) {
			objectOffline.setMode(VObject.MODE_XMPP_INACTIVE);
			InterruptionLogImpl.insert(objectOffline, InterruptionLogImpl.WHAT_OPTION.OFFLINE, "OfflinePacket");
			JabberComponentPlatformPacketListener.LOGGER.debug(objectOffline.getObject_login() + " is now offline (IQPresenceOfflinePacket)");
		}
	}

	private void processIQConfigPacket(IQCommandPacket inPacket) {
		IQAbstractQuery.notifyResult(this.mComponent, inPacket);
	}

	private void bounceIQPacket(IQ inPacket) {
		inPacket.setType(Type.ERROR);
		final String oldFrom = inPacket.getFrom();
		inPacket.setFrom(inPacket.getTo());
		inPacket.setTo(oldFrom);
		inPacket.setError(new XMPPError(XMPPError.Condition.bad_request));
		this.mComponent.sendPacket(inPacket);
	}

	/**
	 * Receive an event packet from the platform >> find the application to
	 * process it
	 * 
	 * @param inPacket
	 */
	private void processVioletAppletPacket(VioletAppletPacket inPacket) {
		if ((inPacket.getType() == Type.ERROR) || (inPacket.getType() == Type.RESULT)) {
			JabberComponentPlatformPacketListener.LOGGER.info("NOTIFY " + inPacket.getType());
			IQAbstractQuery.notifyResult(this.mComponent, inPacket);
		}
	}

	/**
	 * @param inApiPacket
	 */
	private void processVioletApiPacket(VioletApiPacket inApiPacket) {

		final Type packetType = inApiPacket.getType();

		if ((packetType == Type.ERROR) || (packetType == Type.RESULT)) {
			IQAbstractQuery.notifyResult(this.mComponent, inApiPacket);
		}

	}

}

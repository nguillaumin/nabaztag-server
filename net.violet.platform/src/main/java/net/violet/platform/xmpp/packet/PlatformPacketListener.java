package net.violet.platform.xmpp.packet;

import net.violet.platform.datamodel.InterruptionLogImpl;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.object.EarMng;
import net.violet.platform.ping.EventMng;
import net.violet.platform.xmpp.JabberClient;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.packet.IQ.Type;

public class PlatformPacketListener implements PacketListener {

	private static final Logger LOGGER = Logger.getLogger(PlatformPacketListener.class);

	/**
	 * Référence sur le client.
	 */
	private final JabberClient mClient;

	/**
	 * Constructeur par défaut.
	 */
	public PlatformPacketListener(JabberClient inClient) {
		this.mClient = inClient;
	}

	public void processPacket(Packet inPacket) {
		LOGGER.debug("Received packet: " + inPacket.toXML());
		doProcessPacket(inPacket);
	}

	private void doProcessPacket(Packet inPacket) {
		if (inPacket instanceof IQSources) {
			processIQSourcesPacket((IQSources) inPacket);
			PlatformPacketListener.LOGGER.debug("PROCESS " + inPacket.toXML());
		} else if (inPacket instanceof Message) {
			processMessagePacket((Message) inPacket);
		}
		PlatformPacketListener.LOGGER.debug("PROCESS " + inPacket.toXML());
	}

	private void processMessagePacket(Message inPacket) {
		// On cherche les extensions.
		for (final PacketExtension theExtension : inPacket.getExtensions()) {
			if (theExtension instanceof EarsPacket) {
				processEarsExtension(inPacket, (EarsPacket) theExtension);
			} else if (theExtension instanceof ButtonPacket) {
				processButtonExtension(inPacket, (ButtonPacket) theExtension);
			} // ...
		}
	}

	private void processEarsExtension(Message inMessage, EarsPacket inExtension) {
		final String theFrom = inMessage.getFrom();
		if (theFrom != null) {
			final Integer leftEarPosObj = inExtension.getLeftEar();
			final Integer rightEarPosObj = inExtension.getRightEar();
			if ((leftEarPosObj != null) || (rightEarPosObj != null)) {
				// Les oreilles ont bougé en mode actif.
				// -> communion d'oreille.
				final VObject theObject = Factories.VOBJECT.findByJID(inMessage.getFrom());
				if (theObject != null) {
					theObject.setLastActivityTime();
					int earl;
					int earr;
					if (leftEarPosObj != null) {
						earl = leftEarPosObj;
						if (earl >= 17) {
							earl = 0;
						}
					} else {
						earl = theObject.getObject_left();
					}
					if (rightEarPosObj != null) {
						earr = rightEarPosObj;
						if (earr >= 17) {
							earr = 0;
						}
					} else {
						earr = theObject.getObject_right();
					}
					// Permet de repasser le lapin jabber en mode xmpp actif
					if (theObject.getObject_mode() == VObject.MODE_XMPP_INACTIVE) {
						theObject.setMode(VObject.MODE_XMPP);
					}

					InterruptionLogImpl.insert(theObject, InterruptionLogImpl.WHAT_OPTION.EARS, null);
					EarMng.earNotify(theObject, earl, earr);
				}
			}
		}
	}

	private void processButtonExtension(Message inMessage, ButtonPacket inExtension) {
		final String theFrom = inMessage.getFrom();
		if (theFrom != null) {
			// pour le futur , il sera peut etre possible de dire à son lapin de
			// se lever par la reco, ou
			// en simulant la reco. En attendant on regarde si le lapin est
			// actif pour prendre en compte
			// l'appel sur le bouton
			final Integer clicAction = inExtension.getClicAction();
			final Integer messageId = inExtension.getEventId();

			final VObject theObject = Factories.VOBJECT.findByJID(inMessage.getFrom());
			if (theObject != null) {
				synchronized (theObject.getXMPPLock()) {
					if (clicAction != null) { // il faut un simple ou double clic
						theObject.setLastActivityTime();
						Enum<InterruptionLogImpl.WHAT_OPTION> inValue = null;
						if (clicAction == net.violet.platform.message.Message.CLIC_SIMPLE) {
							if (inExtension.isEventPresent()) {
								// Pendant la lecture.
								// Ici, rien à faire, le message a déjà été
								// marqué comme lu.
								inValue = InterruptionLogImpl.WHAT_OPTION.BUTTON_SIMPLE_CLIC_ONREAD;
							} else { // lecture des message en attente
								EventMng.reRead(theObject);
								inValue = InterruptionLogImpl.WHAT_OPTION.BUTTON_SIMPLE_CLIC;
							}
						} else if (clicAction == net.violet.platform.message.Message.CLIC_DOUBLE) {
							if (inExtension.isEventPresent()) {
								if (messageId != null) { // le message est archivé
									EventMng.ackOneByMessage(theObject, messageId);
									inValue = InterruptionLogImpl.WHAT_OPTION.BUTTON_DOUBLE_CLIC_ONREAD;
								} // on ne peut pas archiver ce message, il n'a pas d'ID.
							} else { // archivage de tous les messages en attente
								EventMng.ackAll(theObject);
								inValue = InterruptionLogImpl.WHAT_OPTION.BUTTON_DOUBLE_CLIC;
							}
						}

						if (inValue != null) {
							// Loggue des interruptions sur les lapin jabber
							InterruptionLogImpl.insert(theObject, inValue, null);
						}

						// Permet de repasser le lapin jabber en mode xmpp actif
						if (theObject.getObject_mode() == VObject.MODE_XMPP_INACTIVE) {
							theObject.setMode(VObject.MODE_XMPP);
						}
					}
				}
			}
		}
	}

	private void processIQSourcesPacket(IQSources inPacket) {
		if (inPacket.getType() == Type.GET) {
			final IQSources theReply = new IQSources();
			final VObject theObject = Factories.VOBJECT.findByJID(inPacket.getFrom());
			if (theObject != null) {
				theObject.setLastActivityTime();

				// xmpp object is connected
				theObject.setMode(VObject.MODE_XMPP);
				PlatformPacketListener.LOGGER.info("XMPP OBJECT BOOT " + theObject.getObject_login() + " (" + theObject.getObject_serial() + ")");

				final MessageDraft theMessage = new MessageDraft(theObject);
				theMessage.loadSourcesFromDatabase();
				// Mettre le status
				theMessage.setStatusFromObjectRecord();
				theMessage.setNbMessages(Factories.MESSAGE_COUNTER.getRabbitStateByRecipient(Factories.MESSENGER.getByObject(theObject)));
				theMessage.setEars(theObject.getObject_left(), theObject.getObject_right());
				theMessage.setSourceUpdate(true);
				theReply.setPingPacket(new PingPacket(theMessage));
			} else {
				PlatformPacketListener.LOGGER.info("IQ SOURCES: OBJET INCONNU, FROM = " + inPacket.getFrom());
				final MessageDraft theMessage = new MessageDraft(null);
				theMessage.setStatus(net.violet.platform.message.Message.MODE.ACTIF.getId());
				theReply.setPingPacket(new PingPacket(theMessage));
			}
			theReply.setType(Type.RESULT);
			theReply.setTo(inPacket.getFrom());
			theReply.setFrom(inPacket.getTo());
			theReply.setPacketID(inPacket.getPacketID());

			this.mClient.sendPacket(theReply);
		}
	}

}

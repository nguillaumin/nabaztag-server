package net.violet.platform.xmpp;

import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.packet.AmpPacket;
import net.violet.platform.xmpp.packet.PingPacket;
import net.violet.platform.xmpp.serialization.SerializerFactory;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.provider.IQProvider;
import org.xmlpull.v1.XmlPullParser;

public class JabberMessageFactory extends Packet {

	private static final Logger LOGGER = Logger.getLogger(JabberMessageFactory.class);

	public static class IQVioletPacket extends IQ {

		public static final IQProvider IQ_PROVIDER = new Provider();

		private static final class Provider implements IQProvider {

			/**
			 * Constructeur par défaut.
			 */
			private Provider() {
				// This space for rent.
			}

			public IQ parseIQ(XmlPullParser inParser) throws Exception {
				try {
					JabberMessageFactory.LOGGER.debug("parseIQVioletPacket...");
					IQVioletPacket theIQPacket = null;

					final KeyValueImpl<net.violet.platform.message.Message, String> infoPacket = PingPacket.getPacketInfo(inParser);
					theIQPacket = new IQVioletPacket(infoPacket.getKey(), infoPacket.getValue());

					JabberMessageFactory.LOGGER.debug("...parseIQVioletPacket");
					return theIQPacket;
				} catch (final Exception anException) {
					anException.printStackTrace();
					throw anException;
				}
			}
		}

		/**
		 * Message
		 */
		private final net.violet.platform.message.Message mMessage;

		/**
		 * Format du message.
		 */
		private final String mFormat;

		/**
		 * Constructeur à partir d'un message et d'un format (depuis le parseur
		 * xml).
		 */
		private IQVioletPacket(net.violet.platform.message.Message inMessage, String inFormat) {
			this.mMessage = inMessage;
			this.mFormat = inFormat;
		}

		/**
		 * Constructeur à partir d'un message et du mode (public).
		 * 
		 * @param inMessage
		 * @param mode
		 */
		public IQVioletPacket(net.violet.platform.message.Message inMessage, int mode) {
			this.mMessage = inMessage;
			this.mFormat = PingPacket.FORMAT;

			final VObject receiver = inMessage.getReceiver();
			final int status = inMessage.getStatus();
			setPacketID("IQVioletPacket_" + receiver.getId() + "_" + status + "_" + CCalendar.getCurrentTimeInSecond());
			setTo(receiver.getXmppAddress());
			setFrom(JabberComponentManager.getComponentDefaultFromAddress(Constantes.XMPP_PLATFORM_COMPONENT));
			setType(Type.SET);
			JabberMessageFactory.LOGGER.debug("Initializing new IQVioletPacket : " + toXML());
		}

		public net.violet.platform.message.Message getMessage() {
			return this.mMessage;
		}

		public String getFormat() {
			return this.mFormat;
		}

		@Override
		public String getChildElementXML() {
			return PingPacket.getXml(this);
		}

	}

	public static final int DEFAULT_MODE = 1;
	public static final int IDLE_MODE = 2;
	public static final int NOTIFY_MODE = 3;
	public static final int SOURCES_MODE = 4;
	public static final int STREAMING_MODE = 5;
	public static final int ASLEEP_MODE = 6;
	public static final int URGENT_MODE = 7;
	public static final int IQ_STATUS_IDLE_MODE = 8;
	public static final int IQ_STATUS_ASLEEP_MODE = 9;

	/**
	 * Getter de Message
	 * 
	 * @param inMessage
	 * @param inTimeOfDelivery
	 * @param mode
	 * @return
	 */
	public static Packet getJabberMessagePacket(net.violet.platform.message.Message inMessage, CCalendar inTimeOfDelivery, int mode, String from) {
		final Packet packet;
		if ((mode == JabberMessageFactory.IQ_STATUS_IDLE_MODE) || (mode == JabberMessageFactory.IQ_STATUS_ASLEEP_MODE)) {
			// IQ
			packet = new IQVioletPacket(inMessage, mode);
		} else {
			packet = new Message(inMessage.getReceiver().getXmppAddress());
		}
		JabberMessageFactory.prepareJabberPacket(packet, inMessage, inTimeOfDelivery, mode, from);
		return packet;
	}

	private static void prepareJabberPacket(Packet inPacket, net.violet.platform.message.Message inMessage, CCalendar inTimeOfDelivery, int mode, String from) {
		final int theTTLInSecond = inMessage.getTTLInSecond();
		final CCalendar theExpirationDate;
		if (theTTLInSecond > 0) {
			if (inTimeOfDelivery != null) {
				theExpirationDate = new CCalendar(inTimeOfDelivery.getTimeInMillis());
			} else {
				theExpirationDate = new CCalendar(false);
			}
			theExpirationDate.addSeconds(theTTLInSecond);
		} else {
			theExpirationDate = null;
		}
		if (from == null) {
			inPacket.setFrom(Constantes.XMPP_PLATFORM_ADDRESS);
		} else {
			inPacket.setFrom(from);
		}
		boolean needAmpExtension = false;
		if ((mode == JabberMessageFactory.IDLE_MODE) || (mode == JabberMessageFactory.NOTIFY_MODE)) {
			inPacket.setTo(inPacket.getTo() + "/idle");
			needAmpExtension = true;
			if (mode == JabberMessageFactory.NOTIFY_MODE) {
				final Long theDBMessageID = inMessage.getMessageID();
				net.violet.platform.datamodel.Message theDBMessage = null;
				if (theDBMessageID != null) {
					theDBMessage = Factories.MESSAGE.find(theDBMessageID);
				}
				// Cas de transition: les messages du futur enregistres dans la
				// base
				// lorsque le lapin etait encore un lapin ping.
				if (theDBMessage == null) {
					final Long theDBEventID = inMessage.getEventID();
					if (theDBEventID != null) {
						theDBMessage = Factories.MESSAGE.findByEventID(theDBEventID);
					}
				}
				if (theDBMessage != null) {
					inPacket.setPacketID(theDBMessage.getXMPPID());
				}
			}
		} else if (mode == JabberMessageFactory.SOURCES_MODE) {
			inPacket.setTo(inPacket.getTo() + "/sources");
			needAmpExtension = true;
		} else if (mode == JabberMessageFactory.STREAMING_MODE) {
			inPacket.setTo(inPacket.getTo() + "/streaming");
			needAmpExtension = true;
		} else if (mode == JabberMessageFactory.ASLEEP_MODE) {
			inPacket.setTo(inPacket.getTo() + "/asleep");
			needAmpExtension = true;
		} else if (mode == JabberMessageFactory.IQ_STATUS_ASLEEP_MODE) {
			inPacket.setTo(inPacket.getTo() + "/asleep");
		} else if (mode == JabberMessageFactory.IQ_STATUS_IDLE_MODE) {
			inPacket.setTo(inPacket.getTo() + "/idle");
		} else if (mode == JabberMessageFactory.URGENT_MODE) {
			needAmpExtension = true;
			inPacket.setTo(inPacket.getTo() + "/urgent");
		}
		if (inPacket instanceof Message) {
			if ((theExpirationDate != null) || needAmpExtension) {
				inPacket.addExtension(new AmpPacket(theExpirationDate, inPacket.getFrom(), inPacket.getTo(), mode, inMessage));
			}
			inPacket.addExtension(new PingPacket(inMessage, SerializerFactory.getFormatByObject(inMessage.getReceiver()).getLabel()));
		}
	}

	/*
	 * Méthode à ne pas utiliser (non-Javadoc)
	 * @see org.jivesoftware.smack.packet.Packet#toXML()
	 */
	@Override
	public String toXML() {
		return net.violet.common.StringShop.EMPTY_STRING;
	}

}

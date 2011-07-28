package net.violet.platform.xmpp.packet;

import net.violet.platform.message.Message;
import net.violet.platform.message.MessageDumper;
import net.violet.platform.util.StringShop;
import net.violet.platform.xmpp.KeyValueImpl;
import net.violet.platform.xmpp.JabberMessageFactory.IQVioletPacket;
import net.violet.platform.xmpp.serialization.Serializer;
import net.violet.platform.xmpp.serialization.SerializerFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.provider.PacketExtensionProvider;
import org.xmlpull.v1.XmlPullParser;

/**
 * Classe pour l'extension XMPP <packet> qui contient les (ex) paquets de ping.
 */
public class PingPacket implements PacketExtension {


	private static final Logger LOGGER = Logger.getLogger(PingPacket.class);

	public static final String NAMESPACE = "violet:packet";
	public static final String ELEMENT = "packet";
	public static final PacketExtensionProvider EXTENSION_PROVIDER = new Provider();
	public static final String FORMAT = Serializer.FORMAT.V1.getLabel();
	public static final String ATTR_FORMAT = "format";
	public static final String ATTR_TTL = "ttl";

	public static KeyValueImpl<Message, String> getPacketInfo(XmlPullParser inParser) throws Exception {
		final KeyValueImpl<Message, String> infoMap = new KeyValueImpl<Message, String>();
		String theFormat = PingPacket.FORMAT;
		String theTTL = null;
		Message theMessage = null;
		int eventType = inParser.getEventType();
		while (true) {
			if (eventType == XmlPullParser.START_TAG) {
				if (inParser.getName().equals(PingPacket.ELEMENT)) {
					// Détection du format.
					final int nbAttr = inParser.getAttributeCount();
					for (int indexAttr = 0; indexAttr < nbAttr; indexAttr++) {
						final String theAttrName = inParser.getAttributeName(indexAttr);
						if (theAttrName.equals(PingPacket.ATTR_FORMAT)) {
							theFormat = inParser.getAttributeValue(indexAttr);
						} else if (theAttrName.equals(PingPacket.ATTR_TTL)) {
							theTTL = inParser.getAttributeValue(indexAttr);
						}
					}
				}
				if (inParser.isEmptyElementTag()) {
					break;
				}
			} else if (eventType == XmlPullParser.TEXT) {
				final String theText = inParser.getText();
				if (StringShop.EMPTY_STRING.equals(theText.trim())) {
					theMessage = MessageDumper.dump(new byte[0]);
					break;
				}
				try {
					theMessage = MessageDumper.dump(Base64.decodeBase64(theText.getBytes()));
				} catch (final Exception e) {
					PingPacket.LOGGER.fatal("Unable to parse PingPacket with text : " + inParser.getText(), e);
					break;
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				if (inParser.getName().equals(PingPacket.ELEMENT)) {
					break;
				}
			}
			eventType = inParser.next();
		}

		Integer theTTLVal;
		if (theTTL == null) {
			theTTLVal = null;
		} else {
			try {
				theTTLVal = Integer.parseInt(theTTL);
			} catch (final NumberFormatException anException) {
				theTTLVal = null;
			}
		}
		if ((theTTLVal != null) && (theMessage != null)) {
			theMessage.setTTLInSecond(theTTLVal.intValue());
		}
		infoMap.put(theMessage, theFormat);
		return infoMap;
	}

	private static final class Provider implements PacketExtensionProvider {

		/**
		 * Constructeur par défaut.
		 */
		private Provider() {
			// This space for rent.
		}

		public PacketExtension parseExtension(XmlPullParser inParser) throws Exception {
			final KeyValueImpl<Message, String> infoPacket = PingPacket.getPacketInfo(inParser);
			final PingPacket pingPacket = new PingPacket(infoPacket.getKey(), infoPacket.getValue());
			return pingPacket;
		}
	}

	private final Message mMessage;

	/**
	 * Format du message.
	 */
	private final String mFormat;

	/**
	 * Constructeur par défaut.
	 */
	public PingPacket() {
		this(null);
	}

	/**
	 * Constructeur à partir d'un message.
	 */
	public PingPacket(Message inMessage) {
		this(inMessage, PingPacket.FORMAT);
	}

	/**
	 * Constructeur à partir d'un message et d'un format.
	 */
	public PingPacket(Message inMessage, String inFormat) {
		this.mMessage = inMessage;
		this.mFormat = inFormat;
	}

	public String getElementName() {
		return PingPacket.ELEMENT;
	}

	public String getNamespace() {
		return PingPacket.NAMESPACE;
	}

	public Message getMessage() {
		return this.mMessage;
	}

	public String getFormat() {
		return this.mFormat;
	}

	public String toXML() {
		return PingPacket.getXml(this);
	}

	public static String getXml(Packet inPacket) {
		String theResult = StringShop.EMPTY_STRING;

		if (inPacket instanceof IQVioletPacket) {
			final Message message = ((IQVioletPacket) inPacket).getMessage();
			final String format = ((IQVioletPacket) inPacket).getFormat();

			theResult = PingPacket.buildXML(message, format);
		}

		return theResult;
	}

	private static String getXml(PacketExtension inPacket) {
		String theResult = StringShop.EMPTY_STRING;

		if (inPacket instanceof PingPacket) {
			final Message message = ((PingPacket) inPacket).getMessage();
			final String format = ((PingPacket) inPacket).getFormat();

			theResult = PingPacket.buildXML(message, format);
		}

		return theResult;
	}

	private static String buildXML(Message inMessage, String inFormat) {

		final StringBuilder theResult = new StringBuilder("<" + PingPacket.ELEMENT + " xmlns='" + PingPacket.NAMESPACE + "' format='" + inFormat + "'");

		if (inMessage == null) {
			theResult.append("/>");
		} else {
			final int theTTL = inMessage.getTTLInSecond();

			if (theTTL > 0) {
				theResult.append(" ttl='").append(theTTL).append("'>");
			} else {
				theResult.append(">");
			}

			final String serializedContent = SerializerFactory.serialize(inMessage, inFormat);
			theResult.append(serializedContent).append("</").append(PingPacket.ELEMENT).append(">");
		}

		return theResult.toString();
	}

}

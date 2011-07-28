package net.violet.platform.xmpp.packet;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import net.violet.platform.message.Message;
import net.violet.platform.message.MessageDumper;
import net.violet.platform.util.CCalendar;
import net.violet.platform.xmpp.JabberMessageFactory;

import org.apache.commons.codec.binary.Base64;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.provider.PacketExtensionProvider;
import org.xmlpull.v1.XmlPullParser;

/**
 * Classe pour l'extension XMPP <amp> qui contient les notifications des
 * messages
 */
public class AmpPacket implements PacketExtension {

	public static String NAMESPACE = "http://jabber.org/protocol/amp";
	public static String ELEMENT = "amp";
	public static String RULE_ELEMENT = "rule";
	public static String EXPIRE_AT = "expire-at";
	public static String MATCH_RESOURCE = "match-resource";
	public static String DELIVER = "deliver";
	public static String DROP_ACTION = "drop";
	public static String STORE_ACTION = "store";
	public static String NOTIFY_ACTION = "notify";
	public static String BODY = "<" + PingPacket.ELEMENT + " xmlns='" + PingPacket.NAMESPACE + "' format='" + PingPacket.FORMAT + "'>";
	public static String END_BODY = "</" + PingPacket.ELEMENT + ">";
	public static String OTHER = "other";
	public static String EXACT = "exact";
	public static String DIRECT = "direct";
	public static String STORED = "stored";
	public static final PacketExtensionProvider EXTENSION_PROVIDER = new Provider();
	private static String STATUS = "status";
	private static String FROM = "from";
	private static String TO = "to";

	private static final class Provider implements PacketExtensionProvider {

		/**
		 * Constructeur par defaut.
		 */
		private Provider() {
			// This space for rent.
		}

		public PacketExtension parseExtension(XmlPullParser inParser) throws Exception {
			String theStatus = null;
			String from = null;
			String to = null;
			Message theMessage = null;
			int eventType = inParser.getEventType();
			while (true) {
				if (eventType == XmlPullParser.START_TAG) {
					if (inParser.getName().equals(AmpPacket.ELEMENT)) {
						// Detection du status.
						final int nbAttr = inParser.getAttributeCount();
						for (int indexAttr = 0; indexAttr < nbAttr; indexAttr++) {
							final String theAttrName = inParser.getAttributeName(indexAttr);
							if (AmpPacket.STATUS.equals(theAttrName)) {
								theStatus = inParser.getAttributeValue(indexAttr);
							} else if (AmpPacket.FROM.equals(theAttrName)) {
								from = inParser.getAttributeValue(indexAttr);
							} else if (AmpPacket.TO.equals(theAttrName)) {
								to = inParser.getAttributeValue(indexAttr);
							}

						}
					}
					if (inParser.isEmptyElementTag()) {
						break;
					}
				} else if (eventType == XmlPullParser.TEXT) {
					final String theText = inParser.getText();
					theMessage = MessageDumper.dump(Base64.decodeBase64(theText.getBytes()));
				} else if (eventType == XmlPullParser.END_TAG) {
					if (inParser.getName().equals(AmpPacket.ELEMENT)) {
						break;
					}
				}
				eventType = inParser.next();
			}

			return new AmpPacket(theStatus, from, to, theMessage);
		}
	}

	private final Message mMessage;

	private final CCalendar mExpirationDate;

	private int mMode = JabberMessageFactory.DEFAULT_MODE;

	/**
	 * etat du status.
	 */
	private final String mStatus;

	/**
	 * Emmetteur
	 */
	private final String mFrom;

	/**
	 * Destinateur
	 */
	private final String mTo;

	/**
	 * Constructeur par defaut.
	 */
	public AmpPacket() {
		this(null, null);
	}

	/**
	 * Constructeur à partir d'un status.
	 */
	public AmpPacket(String inStatus, Message inMessage) {
		this(inStatus, null, null, inMessage);
	}

	public AmpPacket(String inStatus, String inFrom, String inTo, Message inMessage) {
		this.mStatus = inStatus;
		this.mFrom = inFrom;
		this.mTo = inTo;
		this.mExpirationDate = null;
		this.mMessage = inMessage;
	}

	public AmpPacket(CCalendar expirationDate, int mode, Message inMessage) {
		this(expirationDate, null, null, mode, inMessage);
	}

	public AmpPacket(CCalendar expirationDate, String inFrom, String inTo, int mode, Message inMessage) {
		this.mStatus = null;
		this.mExpirationDate = expirationDate;
		this.mFrom = inFrom;
		this.mMode = mode;
		this.mTo = inTo;
		this.mMessage = inMessage;
	}

	public String getElementName() {
		return AmpPacket.ELEMENT;
	}

	public String getNamespace() {
		return AmpPacket.NAMESPACE;
	}

	public String getStatus() {
		return this.mStatus;
	}

	public String getFrom() {
		return this.mFrom;
	}

	public String getTo() {
		return this.mTo;
	}

	public String toXML() {
		String theResult = "<" + AmpPacket.ELEMENT + " xmlns='" + AmpPacket.NAMESPACE + "'";
		theResult += " from='" + this.mFrom + "' to='" + this.mTo + "'";

		if (this.mStatus != null) {
			theResult += " status='" + this.mStatus + "'";
		}

		if (this.mExpirationDate == null) {
			theResult += ">";
		} else {
			theResult += ">";
			theResult += getRuleExpireAt(this.mExpirationDate);
		}

		switch (this.mMode) {
		case JabberMessageFactory.IDLE_MODE:
			theResult += getRuleMatchResource(AmpPacket.STORE_ACTION, AmpPacket.OTHER);
			break;

		case JabberMessageFactory.IQ_STATUS_IDLE_MODE:
			theResult += getRuleDeliver(AmpPacket.DROP_ACTION, AmpPacket.STORED);
			theResult += getRuleMatchResource(AmpPacket.STORE_ACTION, AmpPacket.OTHER);
			break;

		case JabberMessageFactory.SOURCES_MODE:
			theResult += getRuleDeliver(AmpPacket.DROP_ACTION, AmpPacket.STORED);
			break;

		case JabberMessageFactory.NOTIFY_MODE:
			theResult += getRuleMatchResource(AmpPacket.STORE_ACTION, AmpPacket.OTHER);
			theResult += getRuleDeliver(AmpPacket.NOTIFY_ACTION, AmpPacket.DIRECT);
			break;

		case JabberMessageFactory.ASLEEP_MODE:
			theResult += getRuleDeliver(AmpPacket.DROP_ACTION, AmpPacket.STORED);
			theResult += getRuleMatchResource(AmpPacket.STORE_ACTION, AmpPacket.OTHER);
			break;

		case JabberMessageFactory.STREAMING_MODE:
			theResult += getRuleDeliver(AmpPacket.DROP_ACTION, AmpPacket.STORED);
			break;

		case JabberMessageFactory.URGENT_MODE:
			theResult += getRuleDeliver(AmpPacket.DROP_ACTION, AmpPacket.STORED);
			break;
		}
		theResult += "</" + AmpPacket.ELEMENT + ">";
		if (this.mMessage != null) {
			theResult += getXExpireAt(this.mMessage.getTTLInSecond());
		}
		return theResult;
	}

	private String getRuleExpireAt(CCalendar expirationDate) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		final String time = sdf.format(expirationDate.getTime());
		return "<" + AmpPacket.RULE_ELEMENT + " action='" + AmpPacket.DROP_ACTION + "' condition='" + AmpPacket.EXPIRE_AT + "' value='" + time + "'/>";
	}

	private String getXExpireAt(long theTTL) {
		return "<x xmlns='jabber:x:expire' seconds='" + theTTL + "'/>";
	}

	private String getRuleMatchResource(String action, String value) {
		return "<" + AmpPacket.RULE_ELEMENT + " action='" + action + "' condition='" + AmpPacket.MATCH_RESOURCE + "' value='" + value + "'/>";
	}

	private String getRuleDeliver(String action, String value) {
		return "<" + AmpPacket.RULE_ELEMENT + " action='" + action + "' condition='" + AmpPacket.DELIVER + "' value='" + value + "'/>";
	}

}

package net.violet.platform.xmpp;

import net.violet.platform.util.Triplet;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.provider.IQProvider;
import org.xmlpull.v1.XmlPullParser;

/**
 * Classe pour l'extension XMPP <packet> qui contient les (ex) paquets de ping.
 */
public class PingPongPacketTest extends IQ {

	public static String NAMESPACE = "violet:packet:ping:pong";
	public static String ELEMENT = "query";
	public static final IQProvider IQ_PROVIDER = new Provider();
	public static String ATTR_FROM = "from";
	public static String ATTR_TO = "to";
	public static String ATTR_TYPE = "type";

	public static Triplet<String, String, String> getPacketInfo(XmlPullParser inParser) throws Exception {
		int eventType = inParser.getEventType();
		String from = null;
		String to = null;
		String theType = null;
		while (true) {
			if (eventType == XmlPullParser.START_TAG) {
				if (inParser.getName().equals(PingPongPacketTest.ELEMENT)) {
					// Détection du format.
					final int nbAttr = inParser.getAttributeCount();
					for (int indexAttr = 0; indexAttr < nbAttr; indexAttr++) {
						final String theAttrName = inParser.getAttributeName(indexAttr);
						if (theAttrName.equals(PingPongPacketTest.ATTR_FROM)) {
							from = inParser.getAttributeValue(indexAttr);
						} else if (theAttrName.equals(PingPongPacketTest.ATTR_TO)) {
							to = inParser.getAttributeValue(indexAttr);
						} else if (theAttrName.equals(PingPongPacketTest.ATTR_TYPE)) {
							theType = inParser.getAttributeValue(indexAttr);
						}
					}
				}
				if (inParser.isEmptyElementTag()) {
					break;
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				if (inParser.getName().equals(PingPongPacketTest.ELEMENT)) {
					break;
				}
			}
			eventType = inParser.next();
		}

		final Triplet<String, String, String> fromToType = new Triplet<String, String, String>(to, from, theType);

		return fromToType;
	}

	private static final class Provider implements IQProvider {

		/**
		 * Constructeur par défaut.
		 */
		private Provider() {
			// This space for rent.
		}

		public IQ parseIQ(XmlPullParser inParser) throws Exception {
			final Triplet<String, String, String> fromToType = PingPongPacketTest.getPacketInfo(inParser);
			final PingPongPacketTest pingPacket = new PingPongPacketTest(fromToType.getFirst(), fromToType.getSecond(), fromToType.getThird());
			return pingPacket;
		}
	}

	private final String mTo;
	private final String mFrom;

	/**
	 * Constructeur par défaut.
	 */
	public PingPongPacketTest(String from, String to, String type) {
		setFrom(from);
		setTo(to);
		if ("set".equals(type)) {
			setType(Type.SET);
		} else if ("get".equals(type)) {
			setType(Type.GET);
		} else if ("result".equals(type)) {
			setType(Type.RESULT);
		} else if ("error".equals(type)) {
			setType(Type.ERROR);
		}
		this.mFrom = from;
		this.mTo = to;
	}

	public PingPongPacketTest(String from, String to, Type type) {
		setFrom(from);
		setTo(to);
		setType(type);
		this.mFrom = from;
		this.mTo = to;
	}

	public String getElementName() {
		return PingPongPacketTest.ELEMENT;
	}

	public String getNamespace() {
		return PingPongPacketTest.NAMESPACE;
	}

	// public String toXML() {
	@Override
	public String getChildElementXML() {
		return "<" + PingPongPacketTest.ELEMENT + " xmlns='" + PingPongPacketTest.NAMESPACE + "' from='" + this.mFrom + "' to='" + this.mTo + "' />";
	}

	@Override
	public String getFrom() {
		return this.mFrom;
	}

	@Override
	public String getTo() {
		return this.mTo;
	}

}

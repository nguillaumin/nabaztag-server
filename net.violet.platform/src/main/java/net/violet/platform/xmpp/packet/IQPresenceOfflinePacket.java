package net.violet.platform.xmpp.packet;

import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.provider.IQProvider;
import org.xmlpull.v1.XmlPullParser;

/**
 * Classe pour l'extension XMPP <presence:offline> qui donne indique le passage
 * offline de l'objet xmpp
 */
public class IQPresenceOfflinePacket extends IQ {


	private static final Logger LOGGER = Logger.getLogger(IQPresenceOfflinePacket.class);

	public static String NAMESPACE = "violet:iq:presence:offline";
	public static String ELEMENT = "query";
	private static String FROM = "from";

	private String mFrom;

	public static final IQProvider IQ_PROVIDER = new Provider();

	private static final class Provider implements IQProvider {

		/**
		 * Constructeur par défaut.
		 */
		private Provider() {
			// This space for rent.
		}

		public IQ parseIQ(XmlPullParser inParser) throws Exception {
			IQPresenceOfflinePacket.LOGGER.debug("parseIQ..." + inParser);
			String from = null;
			boolean goodNamespace = false;
			int eventType = inParser.getEventType();
			while (true) {
				IQPresenceOfflinePacket.LOGGER.debug("parseIQ:eventType = " + eventType);
				if (eventType == XmlPullParser.START_TAG) {
					if (inParser.getNamespace().equals(IQPresenceOfflinePacket.NAMESPACE)) {
						goodNamespace = true;
						final int nbAttr = inParser.getAttributeCount();
						for (int indexAttr = 0; indexAttr < nbAttr; indexAttr++) {
							final String theAttrName = inParser.getAttributeName(indexAttr);
							if (IQPresenceOfflinePacket.FROM.equals(theAttrName)) {
								from = inParser.getAttributeValue(indexAttr);
							}
						}
					}
				}

				if ((eventType == XmlPullParser.END_TAG) && IQPresenceOfflinePacket.ELEMENT.equals(inParser.getName())) {
					break;
				}

				eventType = inParser.next();
			}

			if (goodNamespace && (from != null)) {
				IQPresenceOfflinePacket.LOGGER.debug("...parseExtension IQPresenceOfflinePacket");
				return new IQPresenceOfflinePacket(from);
			}
			IQPresenceOfflinePacket.LOGGER.debug("...parseIQ");
			return null;
		}

	}

	/**
	 * Constructeur par défaut.
	 */
	public IQPresenceOfflinePacket() {

	}

	/**
	 * Constructeur.
	 */
	public IQPresenceOfflinePacket(String from) {
		this.mFrom = from;
	}

	public String getNamespace() {
		return IQPresenceOfflinePacket.NAMESPACE;
	}

	@Override
	public String getChildElementXML() {
		return StringShop.EMPTY_STRING;
	}

	@Override
	public String toXML() {
		String theResult = "<" + IQPresenceOfflinePacket.ELEMENT + " xmlns='" + IQPresenceOfflinePacket.NAMESPACE + "' " + "from='" + this.mFrom + "' to='" + getTo() + "' id='" + getPacketID() + "'>";
		theResult += "</" + IQPresenceOfflinePacket.ELEMENT + ">";
		return theResult;
	}

	@Override
	public String getFrom() {
		return this.mFrom;
	}

}

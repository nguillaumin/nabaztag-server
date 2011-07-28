package net.violet.platform.xmpp.packet;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.provider.IQProvider;
import org.xmlpull.v1.XmlPullParser;

/**
 * Classe pour les paquets IQ de source.
 */
public class IQSources extends IQ {

	public static final String ELEMENT = "query";
	public static final String NAMESPACE = "violet:iq:sources";
	public static final IQProvider IQ_PROVIDER = new Provider();

	private static final class Provider implements IQProvider {

		/**
		 * Constructeur par défaut.
		 */
		private Provider() {
			// This space for rent.
		}

		public IQ parseIQ(XmlPullParser inParser) throws Exception {
			final IQSources theResult = new IQSources();

			int eventType = inParser.getEventType();
			while (true) {
				if (eventType == XmlPullParser.START_TAG) {
					if (inParser.getName().equals(PingPacket.ELEMENT)) {
						final PingPacket theExtension = (PingPacket) PingPacket.EXTENSION_PROVIDER.parseExtension(inParser);
						theResult.setPingPacket(theExtension);
					}
				}

				if ((eventType == XmlPullParser.END_TAG) && IQSources.ELEMENT.equals(inParser.getName())) {
					break;
				}

				eventType = inParser.next();
			}

			return theResult;
		}
	}

	/**
	 * Référence sur les données de ping.
	 */
	private PingPacket mPingPacket;

	/**
	 * Constructeur par défaut.
	 */
	public IQSources() {
		// This space for rent.
	}

	/**
	 * Définit les données ping associées.
	 */
	public void setPingPacket(PingPacket inPingPacket) {
		this.mPingPacket = inPingPacket;
	}

	/**
	 * Accesseur sur les données ping.
	 */
	public PingPacket getPingPacket() {
		return this.mPingPacket;
	}

	@Override
	public String getChildElementXML() {
		final String theResultStart = "<" + IQSources.ELEMENT + " xmlns='" + IQSources.NAMESPACE + "'";
		String theResult;
		if (this.mPingPacket == null) {
			theResult = theResultStart + "/>";
		} else {
			theResult = theResultStart + ">" + this.mPingPacket.toXML() + "</" + IQSources.ELEMENT + ">";
		}
		return theResult;
	}
}

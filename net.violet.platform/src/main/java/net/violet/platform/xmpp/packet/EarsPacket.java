package net.violet.platform.xmpp.packet;

import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.provider.PacketExtensionProvider;
import org.xmlpull.v1.XmlPullParser;

/**
 * Classe pour l'extension XMPP <ears> qui contient les positions d'oreilles
 * envoyés par le lapin.
 */
public class EarsPacket implements PacketExtension {

	public static String NAMESPACE = "violet:nabaztag:ears";
	public static String ELEMENT = "ears";
	public static String ELEMENT_RIGHT = "right";
	public static String ELEMENT_LEFT = "left";
	public static final PacketExtensionProvider EXTENSION_PROVIDER = new Provider();

	private static final class Provider implements PacketExtensionProvider {

		/**
		 * Constructeur par défaut.
		 */
		private Provider() {
			// This space for rent.
		}

		public PacketExtension parseExtension(XmlPullParser inParser) throws Exception {
			Integer leftEar = null;
			Integer rightEar = null;
			int eventType = inParser.getEventType();
			boolean inLeft = false;
			boolean inRight = false;
			while (true) {
				if (eventType == XmlPullParser.START_TAG) {
					if (inParser.getName().equals(EarsPacket.ELEMENT)) {
						if (inParser.isEmptyElementTag()) {
							// rien du tout.
							break;
						}
					} else if (inParser.getName().equals(EarsPacket.ELEMENT_LEFT)) {
						inLeft = true;
					} else if (inParser.getName().equals(EarsPacket.ELEMENT_RIGHT)) {
						inRight = true;
					}
				} else if (eventType == XmlPullParser.TEXT) {
					if (inLeft) {
						leftEar = new Integer(Integer.parseInt(inParser.getText()));
					} else if (inRight) {
						rightEar = new Integer(Integer.parseInt(inParser.getText()));
					}
				} else if (eventType == XmlPullParser.END_TAG) {
					if (inParser.getName().equals(EarsPacket.ELEMENT)) {
						break;
					} else if (inParser.getName().equals(EarsPacket.ELEMENT_LEFT)) {
						inLeft = false;
					} else if (inParser.getName().equals(EarsPacket.ELEMENT_RIGHT)) {
						inRight = false;
					}
				}
				eventType = inParser.next();
			}

			return new EarsPacket(leftEar, rightEar);
		}
	}

	private final Integer mLeftEar;

	private final Integer mRightEar;

	/**
	 * Constructeur par défaut.
	 */
	public EarsPacket() {
		this(null, null);
	}

	/**
	 * Constructeur à partir des positions des oreilles.
	 */
	public EarsPacket(Integer inLeftEar, Integer inRightEar) {
		this.mLeftEar = inLeftEar;
		this.mRightEar = inRightEar;
	}

	public String getElementName() {
		return EarsPacket.ELEMENT;
	}

	public String getNamespace() {
		return EarsPacket.NAMESPACE;
	}

	public String toXML() {
		String theResult;
		final String theResultStart = "<" + EarsPacket.ELEMENT + " xmlns='" + EarsPacket.NAMESPACE + "'";
		if ((this.mLeftEar == null) && (this.mRightEar == null)) {
			theResult = theResultStart + "/>";
		} else {
			theResult = theResultStart + ">";
			if (this.mLeftEar != null) {
				theResult += "<" + EarsPacket.ELEMENT_LEFT + ">" + this.mLeftEar + "</" + EarsPacket.ELEMENT_LEFT + ">";
			} else {
				theResult += "</" + EarsPacket.ELEMENT_LEFT + ">";
			}

			if (this.mRightEar != null) {
				theResult += "<" + EarsPacket.ELEMENT_RIGHT + ">" + this.mRightEar + "</" + EarsPacket.ELEMENT_RIGHT + ">";
			} else {
				theResult += "</" + EarsPacket.ELEMENT_RIGHT + ">";
			}

			theResult += "</" + EarsPacket.ELEMENT + ">";
		}
		return theResult;
	}

	public Integer getLeftEar() {
		return this.mLeftEar;
	}

	public Integer getRightEar() {
		return this.mRightEar;
	}

}

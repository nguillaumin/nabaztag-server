package net.violet.platform.xmpp.packet;

import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.provider.PacketExtensionProvider;
import org.xmlpull.v1.XmlPullParser;

/**
 * Classe pour l'extension XMPP <button> qui contient les actions sur le bouton
 * du lapin (simple ou double clic).
 */
public class ButtonPacket implements PacketExtension {

	public static String NAMESPACE = "violet:nabaztag:button";
	public static String ELEMENT = "button";
	public static String ELEMENT_CLIC = "clic";
	public static String ELEMENT_EVENT = "event";
	public static final PacketExtensionProvider EXTENSION_PROVIDER = new Provider();

	private static final class Provider implements PacketExtensionProvider {

		/**
		 * Constructeur par défaut.
		 */
		private Provider() {
			// This space for rent.
		}

		public PacketExtension parseExtension(XmlPullParser inParser) throws Exception {
			Integer clicAtion = null;
			Integer eventId = null;
			int eventType = inParser.getEventType();
			boolean inClicAction = false;
			boolean inEventId = false;
			boolean eventIsPresent = false;
			while (true) {
				if (eventType == XmlPullParser.START_TAG) {
					if (inParser.getName().equals(ButtonPacket.ELEMENT)) {
						if (inParser.isEmptyElementTag()) {
							// rien du tout.
							break;
						}
					} else if (inParser.getName().equals(ButtonPacket.ELEMENT_CLIC)) {
						inClicAction = true;
					} else if (inParser.getName().equals(ButtonPacket.ELEMENT_EVENT)) {
						inEventId = true;
						eventIsPresent = true;
					}
				} else if (eventType == XmlPullParser.TEXT) {
					if (inClicAction) {
						clicAtion = new Integer(Integer.parseInt(inParser.getText()));
					} else if (inEventId) {
						eventId = new Integer(Integer.parseInt(inParser.getText()));
					}
				} else if (eventType == XmlPullParser.END_TAG) {
					if (inParser.getName().equals(ButtonPacket.ELEMENT)) {
						break;
					} else if (inParser.getName().equals(ButtonPacket.ELEMENT_CLIC)) {
						inClicAction = false;
					} else if (inParser.getName().equals(ButtonPacket.ELEMENT_EVENT)) {
						inEventId = false;
					}
				}
				eventType = inParser.next();
			}

			return new ButtonPacket(clicAtion, eventId, eventIsPresent);
		}
	}

	private final Integer mCLicAction;
	private final Integer mEventId;
	private final boolean mEventIsPresent;

	/**
	 * Constructeur par défaut.
	 */
	public ButtonPacket() {
		this(null, null, false);
	}

	/**
	 * Constructeur à partir des infos du bouton.
	 */
	public ButtonPacket(Integer inClicAction, Integer inEventid, boolean inEventIsPresent) {
		this.mCLicAction = inClicAction;
		this.mEventId = inEventid;
		this.mEventIsPresent = inEventIsPresent;
	}

	public String getElementName() {
		return ButtonPacket.ELEMENT;
	}

	public String getNamespace() {
		return ButtonPacket.NAMESPACE;
	}

	public String toXML() {
		String theResult;
		final String theResultStart = "<" + ButtonPacket.ELEMENT + " xmlns='" + ButtonPacket.NAMESPACE + "'";
		if ((this.mCLicAction == null) && (this.mEventId == null)) {
			theResult = theResultStart + "/>";
		} else {
			theResult = theResultStart + ">";
			if (this.mCLicAction != null) {
				theResult += "<" + ButtonPacket.ELEMENT_CLIC + ">" + this.mCLicAction + "</" + ButtonPacket.ELEMENT_CLIC + ">";
			} else {
				theResult += "</" + ButtonPacket.ELEMENT_CLIC + ">";
			}

			if (this.mEventId != null) {
				theResult += "<" + ButtonPacket.ELEMENT_EVENT + ">" + this.mEventId + "</" + ButtonPacket.ELEMENT_EVENT + ">";
			} else {
				theResult += "</" + ButtonPacket.ELEMENT_EVENT + ">";
			}

			theResult += "</" + ButtonPacket.ELEMENT + ">";
		}
		return theResult;
	}

	public Integer getClicAction() {
		return this.mCLicAction;
	}

	public Integer getEventId() {
		return this.mEventId;
	}

	public boolean isEventPresent() {
		return this.mEventIsPresent;
	}
}

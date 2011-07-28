package net.violet.platform.xmpp.packet;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.provider.IQProvider;
import org.xmlpull.v1.XmlPullParser;

/**
 * Classe pour l'extension XMPP <resource> qui donne la resource de l'objet xmpp
 */
public class IQResourcesPacket extends IQ {

	public static String NAMESPACE = "violet:iq:resources";
	public static String ELEMENT = "query";
	public static String IQ_ELEMENT = "iq";
	public static final IQProvider IQ_PROVIDER = new Provider();
	private static String RESOURCE = "resource";

	private static final class Provider implements IQProvider {

		/**
		 * Constructeur par défaut.
		 */
		private Provider() {
			// This space for rent.
		}

		public IQ parseIQ(XmlPullParser inParser) throws Exception {
			final List<String> resources = new ArrayList<String>();
			int eventType = inParser.getEventType();
			boolean inResource = false;
			while (true) {
				if (eventType == XmlPullParser.START_TAG) {
					if (inParser.getName().equals(IQResourcesPacket.ELEMENT)) {
						if (inParser.isEmptyElementTag()) {
							// rien du tout.
							break;
						}
					} else if (inParser.getName().equals(IQResourcesPacket.RESOURCE)) {
						inResource = true;
					}
				} else if (eventType == XmlPullParser.TEXT) {
					if (inResource) {
						resources.add(inParser.getText());
					}
				} else if (eventType == XmlPullParser.END_TAG) {
					if (inParser.getName().equals(IQResourcesPacket.ELEMENT)) {
						break;
					} else if (inParser.getName().equals(IQResourcesPacket.RESOURCE)) {
						inResource = false;
					}
				}
				eventType = inParser.next();
			}

			return new IQResourcesPacket(resources);
		}
	}

	/**
	 * etat de la ressource.
	 */
	private final List<String> mResources;

	/**
	 * Constructeur par défaut.
	 */
	public IQResourcesPacket() {
		this.mResources = new ArrayList<String>();
	}

	/**
	 * Constructeur à partir d'une ressource.
	 */
	public IQResourcesPacket(String inResource) {
		(this.mResources = new ArrayList<String>()).add(inResource);
	}

	/**
	 * Constructeur à partir d'une liste de ressources.
	 */
	public IQResourcesPacket(List<String> inResources) {
		this.mResources = inResources;
	}

	/**
	 * Constructeur.
	 */
	public IQResourcesPacket(String from, String to) {
		this.mResources = new ArrayList<String>();
		setFrom(from);
		setTo(to);
	}

	public String getElementName() {
		return IQResourcesPacket.ELEMENT;
	}

	public String getNamespace() {
		return IQResourcesPacket.NAMESPACE;
	}

	public List<String> getResources() {
		return this.mResources;
	}

	@Override
	public String getChildElementXML() {
		String theResult = "<" + IQResourcesPacket.ELEMENT + " xmlns='" + IQResourcesPacket.NAMESPACE + "'";
		if ((this.mResources == null) || this.mResources.isEmpty()) {
			theResult += "/>";
		} else {
			for (final String mResource : this.mResources) {
				theResult += theResult + "><" + IQResourcesPacket.RESOURCE + ">" + mResource + "</" + IQResourcesPacket.RESOURCE + "></" + IQResourcesPacket.ELEMENT + ">";
			}
		}
		return theResult;
	}

	@Override
	public String toXML() {
		String theResult = "<" + IQResourcesPacket.IQ_ELEMENT + " xmlns='" + IQResourcesPacket.NAMESPACE + "' type='get' " + "from='" + getFrom() + "' to='" + getTo() + "' id='" + getPacketID() + "'>";
		theResult += getChildElementXML();
		theResult += "</" + IQResourcesPacket.IQ_ELEMENT + ">";
		return theResult;
	}

}

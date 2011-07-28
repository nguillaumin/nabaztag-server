package net.violet.platform.xmpp.packet;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.provider.IQProvider;
import org.xmlpull.v1.XmlPullParser;

/**
 * Classe pour gérer les IQ de type "command" <command
 * xmlns='http://jabber.org/protocol/commands' node='getconfig'
 * action='execute'/>
 * 
 * @author pgerlach
 */
public class IQCommandPacket extends IQ {

	/**
	 * Les différents type de commandes comprises par le lapin
	 */
	public enum Type {
		getConfig,
		getRunningState
	};

	public static String ELEMENT = "command";
	public static String NAMESPACE = "http://jabber.org/protocol/commands";

	protected Type mType;

	public static final IQProvider IQ_PROVIDER = new Provider();

	private static final class Provider implements IQProvider {

		/**
		 * Constructeur par défaut.
		 */
		private Provider() {
			// This space for rent.
		}

		public IQ parseIQ(XmlPullParser inParser) throws Exception {

			final StringBuilder var = new StringBuilder();
			boolean inValue = false;
			Type type = null;

			int eventType = inParser.getEventType();

			final Map<String, String> result = new HashMap<String, String>();

			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					if ("command".equals(inParser.getName())) {
						final String node = inParser.getAttributeValue(null, "node");
						if ("getconfig".equals(node)) {
							type = Type.getConfig;
						} else if ("getrunningstate".equals(node)) {
							type = Type.getRunningState;
						}
					}
					if ("field".equals(inParser.getName())) {
						var.append(inParser.getAttributeValue(null, "var"));
					} else if ("value".equals(inParser.getName())) {
						inValue = true;
					}
				} else if (eventType == XmlPullParser.TEXT) {
					if (inValue && (var.length() > 0)) {
						result.put(var.toString(), inParser.getText());
						var.setLength(0);
					}
				} else if (eventType == XmlPullParser.END_TAG) {
					if ("x".equals(inParser.getName())) {
						break;
					} else if ("value".equals(inParser.getName())) {
						inValue = false;
					}
				} else if (eventType == XmlPullParser.END_DOCUMENT) {
					break;
				}
				eventType = inParser.next();
			}

			if (type == Type.getConfig) {
				// désencoder certains champs qui sont en base64 (voir le
				// bytecode du lapin pour savoir lesquels)
				final String[] toDecode = { "wifi_ssid", "login", "password" };
				for (final String key : toDecode) {
					if (result.containsKey(key)) {
						final String val = result.get(key);
						if (null != val) {
							final byte[] decoded = Base64.decodeBase64(val.getBytes());
							result.put(key, new String(decoded));
						}
					}
				}
			}

			IQCommandPacket res = null;
			if (null != type) {
				res = new IQCommandPacket(type, result);
			}
			return res;
		}
	}

	/**
	 * chaine de description de la config
	 */
	private final Map<String, String> mInfos = new HashMap<String, String>();

	/**
	 * Constructeur par défaut.
	 */
	public IQCommandPacket(Type type) {
		this.mType = type;
	}

	public IQCommandPacket(Type type, Map<String, String> config) {
		this.mType = type;
		this.mInfos.putAll(config);
	}

	public String getElementName() {
		return IQCommandPacket.ELEMENT;
	}

	public String getNamespace() {
		return IQCommandPacket.NAMESPACE;
	}

	public Map<String, String> getInfos() {
		return this.mInfos;
	}

	@Override
	public String getChildElementXML() {
		final StringBuilder xmlChild = new StringBuilder();

		xmlChild.append("<command xmlns='http://jabber.org/protocol/commands' node='");
		switch (this.mType) {
		case getConfig:
			xmlChild.append("getconfig");
			break;
		case getRunningState:
			xmlChild.append("getrunningstate");
			break;
		}
		xmlChild.append("' action='execute'/>");

		return xmlChild.toString();
	}

	/**
	 * Fonction utilitaire pour permettre d'afficher un paquet sous forme HTML
	 * 
	 * @return la représentation du paquet en html
	 */
	public static String formatToHtml(Map<String, String> infos, Type type) {
		final StringBuilder res = new StringBuilder();
		final String[] fields;
		final String[] fieldsGetConfig = { "bytecode_revision", "wifi_ssid", "wifi_crypt", "net_dhcp", "net_ip", "net_mask", "net_gateway", "net_dns", "server_url", "login", "proxy_enabled", "proxy_ip", "proxy_port" };
		final String[] fieldsGetRunningState = { "connection_mode", "net_ip", "net_mask", "net_gateway", "net_dns", "sState", "sResource", "gItState", "gSleepState", "gStreamingState", "gProcessingState", "gProcessingWaitState", "gBusyState", "gItApp" };

		switch (type) {
		case getConfig:
			fields = fieldsGetConfig;
			break;
		case getRunningState:
			fields = fieldsGetRunningState;
			break;
		default:
			fields = new String[0];
		}

		res.append("<table>");

		for (final String key : fields) {
			res.append("<tr><td>");
			res.append(key);
			res.append("</td><td>");
			res.append(infos.get(key));
			res.append("</td></tr>");
		}

		res.append("</table>");

		return res.toString();

	}

}

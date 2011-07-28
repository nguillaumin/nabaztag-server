package net.violet.platform.xmpp.packet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.events.Event;
import net.violet.platform.xmpp.JabberComponent;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.XMPPError;
import org.jivesoftware.smack.provider.IQProvider;
import org.xmlpull.v1.XmlPullParser;

public class VioletAppletPacket extends IQ {

	public static final String APPLET_QUERY_TAG = "query";
	public static final String APPLET_JSON_NAMESPACE = "violet:iq:applet:json";

	public static final IQProvider IQ_PROVIDER = new Provider();

	private static final Logger LOGGER = Logger.getLogger(VioletAppletPacket.class);

	public static void sendDefaultResponse(JabberComponent inComponent, IQ inIQ) {
		final String to = inIQ.getFrom();
		inIQ.setFrom(inIQ.getTo());
		inIQ.setTo(to);
		inIQ.setType(Type.RESULT);
		inComponent.sendPacket(inIQ);
	}

	/**
	 * Parse APPLET IQ packets : (event from applications) <iq
	 * id="applet-12203619811670.3895098676609807"
	 * to="AppName@xmpp.applet.violet.net"
	 * from="server@xmpp.platform.violet.net/server" type="get"> <query
	 * xmlns='violet:iq:applet:json' >
	 * "JSON application event map" </query> </iq>
	 */
	private static final class Provider implements IQProvider {

		/**
		 * Constructeur par défaut.
		 */
		private Provider() {
			// This space for rent.
		}

		public IQ parseIQ(XmlPullParser inParser) throws Exception {

			final VioletAppletPacket appletPacket = new VioletAppletPacket();

			int eventType = inParser.getEventType();

			while (eventType != XmlPullParser.END_TAG) {

				if ((eventType == XmlPullParser.START_TAG) && (inParser.getName().equals(VioletAppletPacket.APPLET_QUERY_TAG))) {
					inParser.next(); // next parser event must be the text content of <query> tag
					if (inParser.getEventType() == XmlPullParser.TEXT) {
						appletPacket.setTextContent(inParser.getText().trim());
					}
				}

				// we must reach the end of the XML content
				eventType = inParser.next();
			}

			if (VioletAppletPacket.LOGGER.isDebugEnabled()) {
				VioletAppletPacket.LOGGER.debug("VioletAppletPacket.Provider.parseIQ() : " + appletPacket);
			}
			return appletPacket;
		}

	}

	private String mContent;

	/**
	 * Empty (private) constructor
	 */
	private VioletAppletPacket() {
		this.mContent = net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * Public constructor. Event map will be internally stored in JSON format
	 * 
	 * @param event
	 * @param inObjectId
	 * @throws ConversionException
	 */
	public VioletAppletPacket(Event event) throws ConversionException {
		this.mContent = ConverterFactory.JSON.convertTo(event.getPojoMap(null), false);
		VioletAppletPacket.LOGGER.info("VioletAppletPacket : content=" + this.mContent);
	}

	@Override
	public String getChildElementXML() {
		return "<" + VioletAppletPacket.APPLET_QUERY_TAG + " xmlns='" + VioletAppletPacket.APPLET_JSON_NAMESPACE + "' ><![CDATA[" + this.mContent + "]]></" + VioletAppletPacket.APPLET_QUERY_TAG + ">";
	}

	@Override
	public String toString() {
		return getChildElementXML();
	}

	/**
	 * @return the POJO converted packet content
	 * @throws ConversionException
	 */
	public Object getPojoContent() throws ConversionException {
		return ConverterFactory.JSON.convertFrom(this.mContent);
	}

	public void setPojoContent(Object inContent) throws ConversionException {
		this.mContent = ConverterFactory.JSON.convertTo(inContent, false);;
	}

	/**
	 * @return the application event map that when packet's type is GET
	 * @throws ConversionException
	 */
	public PojoMap getEventMap() throws ConversionException {
		if (this.getType() == Type.GET) {
			return new PojoMap((Map<String, Object>) getPojoContent());
		}
		return null;
	}

	/**
	 * @return the list of objects messages that the application, has returned
	 *         in response to the event or NULL if the packet is not of type
	 *         RESULT
	 * @throws ConversionException
	 */
	public List<Map<String, Object>> getAppResponse() throws ConversionException {
		return (this.getType() == Type.RESULT) ? (List<Map<String, Object>>) getPojoContent() : null;
	}

	/**
	 * @return the error as a map, NULL if the packet is not of type ERROR
	 */
	public Map<String, Object> getPojoError() {
		if (this.getType() != Type.ERROR) {
			return null;
		}
		final XMPPError error = this.getError();
		final Map<String, Object> errorMap = new HashMap<String, Object>(5);
		errorMap.put("type", "error");
		errorMap.put("code", error.getCode());
		errorMap.put("title", error.getCondition());
		errorMap.put("message", error.getMessage());
		return errorMap;
	}

	private void setTextContent(String inJSONContent) {
		this.mContent = inJSONContent;
	}

}

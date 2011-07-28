package net.violet.platform.xmpp.packet;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.ConverterFactory;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.XMPPError;
import org.jivesoftware.smack.provider.IQProvider;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class VioletApiPacket extends IQ {

	public static final String API_QUERY_TAG = "query";
	public static final String API_JSON_NAMESPACE = "violet:iq:api:json";

	private final static String CDATA_PRFX = "<![CDATA[";
	private final static String CDATA_SFX = "]]>";

	public static final IQProvider IQ_PROVIDER = new Provider();

	private static final Logger LOGGER = Logger.getLogger(VioletApiPacket.class);

	/**
	 * Parse the XML packet to retrieve the VioletApiPacket inside it
	 */
	private static final class Provider implements IQProvider {

		/**
		 * Constructeur par défaut.
		 */
		private Provider() {
			// This space for rent.
		}

		public IQ parseIQ(XmlPullParser inParser) throws Exception {

			final VioletApiPacket apiPacket = new VioletApiPacket();

			int eventType = inParser.getEventType();

			while (eventType != XmlPullParser.END_TAG) {

				if ((eventType == XmlPullParser.START_TAG) && (inParser.getName().equals(VioletApiPacket.API_QUERY_TAG))) {
					inParser.next();
					apiPacket.setTextContent(inParser.getText().trim());
				}

				// we must reach the end of the XML content
				eventType = inParser.next();
			}

			if (VioletApiPacket.LOGGER.isDebugEnabled()) {
				VioletApiPacket.LOGGER.debug("VioletApiPacket.Provider.parseIQ() : " + apiPacket);
			}
			return apiPacket;
		}
	}

	private String mData;

	/**
	 * Public constructor from xml string
	 * @param inXml
	 * @throws Exception 
	 * @throws Exception 
	 */
	public static VioletApiPacket getVioletApiPacket(String inXml) throws Exception {
		final XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		final XmlPullParser xParser = factory.newPullParser();
		xParser.setInput(new StringReader(inXml));
		final Provider provider = new Provider();
		return (VioletApiPacket) provider.parseIQ(xParser);
	}

	/**
	 * Private (empty) constructor
	 */
	private VioletApiPacket() {
		this.mData = net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * Create an API call packet
	 * 
	 * @param inAPIParams
	 * @throws ConversionException
	 */
	public VioletApiPacket(Map<String, Object> inAPIParams) throws ConversionException {
		this.mData = ConverterFactory.JSON.convertTo(inAPIParams, false);
	}

	/**
	 * @return the POJO converted packet content
	 * @throws ConversionException
	 */
	private <T> T getPojoContent() throws ConversionException {
		return ConverterFactory.JSON.<T> convertFrom(this.mData);
	}

	/**
	 * @return the API query params when the packet is of type GET NULL
	 *         otherwise
	 * @throws ConversionException
	 */
	public Map<String, Object> getAPICallParams() throws ConversionException {
		return (this.getType() == Type.GET) ? this.<Map<String, Object>> getPojoContent() : null;
	}

	/**
	 * @return the API response when the packet is of type RESULT NULL otherwise
	 * @throws ConversionException
	 */
	public Object getAPIResponse() throws ConversionException {
		return (this.getType() == Type.RESULT) ? getPojoContent() : null;
	}

	/**
	 * @return the error as a map, NULL if the packet is not of type ERROR
	 */
	public Map<String, Object> getPojoError() {
		if (this.getType() != Type.ERROR) {
			return null;
		}
		final Map<String, Object> errorMap = new HashMap<String, Object>(3);
		final XMPPError error = this.getError();
		errorMap.put("type", "error");
		errorMap.put("code", error.getCode());
		errorMap.put("title", error.getCondition());
		errorMap.put("message", error.getMessage());
		return errorMap;
	}

	public void setPojoContent(Object inPojoResponse) throws ConversionException {
		this.mData = ConverterFactory.JSON.convertTo(inPojoResponse, false);
	}

	private void setTextContent(String inJSONData) {
		if (inJSONData.startsWith("<![CDATA[")) {
			VioletApiPacket.LOGGER.info("Warning : Found CDATA in the text content (dumb XmlPullParser !)");
			this.mData = inJSONData.substring(VioletApiPacket.CDATA_PRFX.length(), inJSONData.length() - VioletApiPacket.CDATA_SFX.length());
		} else {
			this.mData = inJSONData;
		}
	}

	@Override
	public String getChildElementXML() {
		return "<" + VioletApiPacket.API_QUERY_TAG + " xmlns='" + VioletApiPacket.API_JSON_NAMESPACE + "'><![CDATA[" + this.mData + "]]></" + VioletApiPacket.API_QUERY_TAG + ">";
	}

	@Override
	public String toString() {
		return getChildElementXML();
	}

}

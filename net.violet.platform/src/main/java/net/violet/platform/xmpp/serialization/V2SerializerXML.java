package net.violet.platform.xmpp.serialization;

import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.message.Message;

import org.apache.log4j.Logger;

/**
 * Serialize a {@link Message} using the 2.0 format and returns the pojo object
 * as a XML formatted {@link String}.
 */
public class V2SerializerXML extends AbstractV2Serializer {

	private static final Logger LOGGER = Logger.getLogger(V2SerializerXML.class);

	public String serialize(Message message) {
		try {
			return ConverterFactory.XML.convertTo(generatePojo(message));
		} catch (final ConversionException e) {
			V2SerializerXML.LOGGER.error(e, e);
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

}

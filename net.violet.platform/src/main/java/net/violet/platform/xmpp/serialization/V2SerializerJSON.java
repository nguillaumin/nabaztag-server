package net.violet.platform.xmpp.serialization;

import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.message.Message;

/**
 * Serialize a {@link Message} using the 2.0 format and returns the pojo object
 * as a JSON formatted {@link String}.
 */
public class V2SerializerJSON extends AbstractV2Serializer {

	public String serialize(Message message) {
		return "<![CDATA[" + ConverterFactory.JSON.convertTo(generatePojo(message)) + "]]>";
	}

}

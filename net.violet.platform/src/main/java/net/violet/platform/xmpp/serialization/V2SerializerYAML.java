package net.violet.platform.xmpp.serialization;

import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.message.Message;

import org.apache.log4j.Logger;

/**
 * Serialize a {@link Message} using the 2.0 format and returns the pojo object
 * as a YAML formatted {@link String}.
 */
public class V2SerializerYAML extends AbstractV2Serializer {

	private static final Logger LOGGER = Logger.getLogger(V2SerializerYAML.class);

	public String serialize(Message message) {
		try {
			final String content = ConverterFactory.YAML.convertTo(generatePojo(message));
			return "<![CDATA[" + content + "]]>";
		} catch (final ConversionException e) {
			V2SerializerYAML.LOGGER.error(e, e);
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

}

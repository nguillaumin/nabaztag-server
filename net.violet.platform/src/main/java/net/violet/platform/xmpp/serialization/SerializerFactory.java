package net.violet.platform.xmpp.serialization;

import java.util.HashMap;
import java.util.Map;

import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.message.Message;
import net.violet.platform.util.StringShop;
import net.violet.platform.xmpp.serialization.Serializer.FORMAT;

/**
 * This factory is the entry point to access to the available {@link Serializer}
 * objects.
 */
public class SerializerFactory {

	private static final Map<FORMAT, Serializer> SERIALIZERS_MAP;

	static {
		SERIALIZERS_MAP = new HashMap<FORMAT, Serializer>();
		SerializerFactory.SERIALIZERS_MAP.put(FORMAT.V1, new V1Serializer());
		SerializerFactory.SERIALIZERS_MAP.put(FORMAT.V2_JSON, new V2SerializerJSON());
		SerializerFactory.SERIALIZERS_MAP.put(FORMAT.V2_YAML, new V2SerializerYAML());
		SerializerFactory.SERIALIZERS_MAP.put(FORMAT.V2_XML, new V2SerializerXML());
	}

	public static FORMAT getFormatByObject(VObject inObject) {
		if (HARDWARE.MIRROR.is(inObject)) {
			return Serializer.FORMAT.V2_JSON;
		}

		return Serializer.FORMAT.V1;
	}

	public static String serialize(Message message, String format) {
		final FORMAT theFormat = FORMAT.findByLabel(format);
		final Serializer theSerializer = SerializerFactory.SERIALIZERS_MAP.get(theFormat);
		if (theSerializer != null) {
			return theSerializer.serialize(message);
		}

		return StringShop.EMPTY_STRING;
	}

}

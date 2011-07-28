package net.violet.platform.xmpp.serialization;

import net.violet.platform.message.Message;

/**
 * A Serializer object is used to convert a {@link Message} object to a
 * {@link String} object according to the specified format.
 */
public interface Serializer {

	enum FORMAT {
		V1("1.0"),
		V2_XML("2.0/xml"),
		V2_YAML("2.0/yaml"),
		V2_JSON("2.0/json");

		private final String label;

		private FORMAT(String label) {
			this.label = label;
		}

		public String getLabel() {
			return this.label;
		}

		public static FORMAT findByLabel(String label) {
			for (final FORMAT aFormat : FORMAT.values()) {
				if (aFormat.getLabel().equals(label)) {
					return aFormat;
				}
			}
			return null;
		}
	}

	String serialize(Message message);
}

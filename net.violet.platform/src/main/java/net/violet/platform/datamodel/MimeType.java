package net.violet.platform.datamodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.content.converters.ContentType;
import net.violet.db.records.Record;
import net.violet.platform.datamodel.factories.Factories;

public interface MimeType extends Record<MimeType> {

	enum MIME_TYPES {
		JSON(1),
		OCTET_STREAM(2),
		JS(3),
		XML(4),
		PLAIN_TXT(5),
		PNG(6),
		YAML(7),
		A_MPEG(8) {

			@Override
			public ContentType getContentType() {
				return ContentType.MP3;
			}
		},
		ADP(9),
		CHOR(10),
		GIF(11),
		VASM(12),
		A_MIDI(13),
		JPEG(14);

		public static final List<MIME_TYPES> PICTURE = Arrays.asList(JPEG, GIF, PNG);

		public static final List<MIME_TYPES> AUDIO = Arrays.asList(A_MPEG, ADP);

		public static final List<MIME_TYPES> ALL = new ArrayList<MIME_TYPES>();

		private static final Map<String, MIME_TYPES> LABEL_TYPES_MAP = new HashMap<String, MIME_TYPES>();
		private static final Map<MimeType, MIME_TYPES> MIME_TYPES_MAP = new HashMap<MimeType, MIME_TYPES>();
		static {
			for (final MIME_TYPES aType : MIME_TYPES.values()) {
				MIME_TYPES.LABEL_TYPES_MAP.put(aType.getLabel().toLowerCase(), aType);
				MIME_TYPES.MIME_TYPES_MAP.put(aType.type, aType);
				MIME_TYPES.ALL.add(aType);
			}
		}

		private final MimeType type;

		private MIME_TYPES(long inId) {
			this.type = Factories.MIME_TYPE.find(inId);
		}

		@Override
		public String toString() {
			return getLabel();
		}

		public String getLabel() {
			return this.type.getLabel();
		}

		public long getId() {
			return this.type.getId();
		}

		public String getFullExtension() {
			return net.violet.common.StringShop.POINT + getExtension();
		}

		public String getExtension() {
			return this.type.getExtension();
		}

		public static MIME_TYPES findByLabel(String mime_type) {
			return MIME_TYPES.LABEL_TYPES_MAP.get(mime_type.toLowerCase());
		}

		public static MIME_TYPES find(MimeType mimeType) {
			return MIME_TYPES.MIME_TYPES_MAP.get(mimeType);
		}

		public ContentType getContentType() {
			return null;
		}

	}

	String getLabel();

	String getExtension();

}

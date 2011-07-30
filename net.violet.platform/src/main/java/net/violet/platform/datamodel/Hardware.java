package net.violet.platform.datamodel;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.violet.db.records.Record;
import net.violet.platform.datamodel.MimeType.MIME_TYPES;
import net.violet.platform.datamodel.factories.Factories;

/**
 * Hardware types
 *
 */
public interface Hardware extends Record<Hardware> {

	enum HARDWARE {

		DALDAL(7), // initialisé avant V1

		ZTAMP(8) { // initialisé avant BOOK

			@Override
			public boolean checkIdentifier(String inIdentifier) {
				return HARDWARE.checkRfidIdentifier(inIdentifier);
			}

			@Override
			public String getLabel() {
				return "Ztamp";
			}
		},

		NANOZTAG(9) { // initialisé avant BOOK

			@Override
			public boolean checkIdentifier(String inIdentifier) {
				return HARDWARE.checkRfidIdentifier(inIdentifier);
			}

			@Override
			public String getLabel() {
				return "Nano:ztag";
			}
		},

		OTHER_RFID(10) { // initialisé avant BOOK

			@Override
			public boolean checkIdentifier(String inIdentifier) {
				return HARDWARE.checkRfidIdentifier(inIdentifier);
			}
		},

		V1(3),

		V2(4),

		BOOK(5) {

			@Override
			public boolean checkIdentifier(String inIdentifier) {
				return HARDWARE.checkRfidIdentifier(inIdentifier);
			}
		},

		MIRROR(6) {

			@Override
			public boolean checkIdentifier(String inIdentifier) {
				return (inIdentifier != null) && HARDWARE.SERIAL_MIRROR_REGEX.matcher(inIdentifier).matches();
			}
		};

		// Nabaztags and Daldal regex
		private static final Pattern MAC_REGEX = Pattern.compile("^(?:[0-9a-f]{2}:?){5}(?:[0-9a-f]){2}(?:8080)?$", Pattern.CASE_INSENSITIVE);

		// Mirror regex
		private static final Pattern SERIAL_MIRROR_REGEX = Pattern.compile("^[0-9a-f]{8}$", Pattern.CASE_INSENSITIVE);

		//Rfid regex
		private static final Pattern SERIAL_RFID_REGEX = Pattern.compile("^(?:[0-9a-f]{2}:?){7}(?:[0-9a-f]){2}$", Pattern.CASE_INSENSITIVE);

		//Regex que les MAC ne doivent PAS matcher (empeche la repetition 6 fois successives du meme caractere)
		private static final Pattern INVALID_MAC_REGEX = Pattern.compile(".*(?:([0-9a-f]):?(?:\\1:?){5}).*", Pattern.CASE_INSENSITIVE);

		private static Map<Long, HARDWARE> ID_HARDWARE;

		static {
			final Map<Long, HARDWARE> theMap = new HashMap<Long, HARDWARE>();

			for (final HARDWARE aHardware : HARDWARE.values()) {
				theMap.put(aHardware.getId(), aHardware);
			}

			HARDWARE.ID_HARDWARE = Collections.unmodifiableMap(theMap);
		}

		private final Hardware mHardware;

		private HARDWARE(long id) {
			this.mHardware = Factories.HARDWARE.find(id);
		}

		public Long getId() {
			return this.mHardware.getId();
		}

		public boolean checkIdentifier(String inIdentifier) {
			return (inIdentifier != null) && HARDWARE.MAC_REGEX.matcher(inIdentifier).matches() && !HARDWARE.INVALID_MAC_REGEX.matcher(inIdentifier).matches();
		}

		public String getLabel() {
			return this.mHardware.getLabel();
		}

		public boolean isNot(VObject inObject) {
			return inObject.getHardware() != this;
		}

		public boolean is(VObject inObject) {
			return inObject.getHardware() == this;
		}

		public static HARDWARE findById(Long inHardware) {
			return HARDWARE.ID_HARDWARE.get(inHardware);
		}

		public Files getPictureFile() {
			return this.mHardware.getPictureFile();
		}

		public String getType() {
			return this.mHardware.getType();
		}

		public String getModelName() {
			return this.mHardware.getModelName();
		}

		Hardware getHardware() {
			return this.mHardware;
		}

		public boolean isMimeTypeSupported(MIME_TYPES inType) {
			return this.mHardware.getSupportedMimeTypes().contains(inType);
		}

		private static boolean checkRfidIdentifier(String inIdentifier) {
			return (inIdentifier != null) && HARDWARE.SERIAL_RFID_REGEX.matcher(inIdentifier).matches();

		}

		public List<MIME_TYPES> getMimeTypes() {
			return this.mHardware.getSupportedMimeTypes();
		}

	}

	String getType();

	String getModelName();

	String getLabel();

	Files getPictureFile();

	List<MimeType.MIME_TYPES> getSupportedMimeTypes();

}

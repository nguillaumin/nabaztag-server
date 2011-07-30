package net.violet.platform.datamodel;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.Record;

/**
 * Nabcast signature system, unused ?
 * 
 *
 */
public interface Signature extends Record<Signature> {

	Files getFile();

	Anim getAnim();

	ColorType getColor();

	enum ColorType {
		RED("red", "FF0000"),
		YELLOW("yellow", "FFFF00"),
		BLUE("blue", "0000FF"),
		GREEN("green", "00FF00"),
		MAGENTA("magenta", "FF00FF"),
		CYAN("cyan", "00FFFF");

		private final static Map<String, ColorType> COLOR_BY_LABEL;
		private final static Map<String, ColorType> COLOR_BY_VALUE;

		static {
			final Map<String, ColorType> labelsMap = new HashMap<String, ColorType>();
			final Map<String, ColorType> valuesMap = new HashMap<String, ColorType>();

			for (final ColorType aColor : ColorType.values()) {
				labelsMap.put(aColor.getLabel(), aColor);
				valuesMap.put(aColor.getValue(), aColor);
			}

			COLOR_BY_LABEL = Collections.unmodifiableMap(labelsMap);
			COLOR_BY_VALUE = Collections.unmodifiableMap(valuesMap);
		}

		private final String label;
		private final String value;

		private ColorType(String inLabel, String inValue) {
			this.label = inLabel;
			this.value = inValue;
		}

		public String getLabel() {
			return this.label.toLowerCase();
		}

		public String getValue() {
			return this.value.toUpperCase();
		}

		public static ColorType getColorByValue(String inValue) {
			return (inValue != null) ? ColorType.COLOR_BY_VALUE.get(inValue.toUpperCase()) : null;
		}

		public static ColorType getColorTypeByLabel(String inLabel) {
			return (inLabel != null) ? ColorType.COLOR_BY_LABEL.get(inLabel.toLowerCase()) : null;
		}
	}

}

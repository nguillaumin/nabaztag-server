package net.violet.platform.datamodel.util;

import java.util.HashMap;

public class UpdateMap extends HashMap<String, Object> {

	public <T> T updateField(String inFieldName, T inFieldValue, T inNewValue) {

		if (shouldBeUpdated(inFieldValue, inNewValue)) {
			this.put(inFieldName, inNewValue);
		}

		return inNewValue;
	}

	private <T> boolean shouldBeUpdated(T inFieldValue, T inNewValue) {
		return ((inFieldValue == null) && (inNewValue != null)) || ((inFieldValue != null) && !inFieldValue.equals(inNewValue));
	}

}

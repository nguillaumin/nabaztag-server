package net.violet.probes;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;

import org.apache.log4j.Logger;

class AbstractProbe {


	private static final Logger LOGGER = Logger.getLogger(AbstractProbe.class);

	private final Map<String, CompositeType> mCompositeTypes = new HashMap<String, CompositeType>();
	private final Map<String, List<Field>> mFields = new HashMap<String, List<Field>>();

	protected void initGroups(Map<String, String> inGroupDescriptions) throws OpenDataException {
		for (final Map.Entry<String, String> theGroupEntry : inGroupDescriptions.entrySet()) {
			final List<Field> theFields = AbstractProbe.getGroupFields(getClass(), theGroupEntry.getKey());
			this.mFields.put(theGroupEntry.getKey(), theFields);
			this.mCompositeTypes.put(theGroupEntry.getKey(), AbstractProbe.getCompositeType(theFields, theGroupEntry.getKey(), theGroupEntry.getValue()));
		}
	}

	protected static List<Field> getGroupFields(Class<? extends AbstractProbe> inClass, String inGroup) {
		final List<Field> theResult = new LinkedList<Field>();
		for (final Field aField : inClass.getDeclaredFields()) {
			final CompositeDataField theAnnotation = aField.getAnnotation(CompositeDataField.class);
			if ((theAnnotation != null) && theAnnotation.group().equals(inGroup)) {
				theResult.add(aField);
			}
		}
		return theResult;
	}

	protected static CompositeType getCompositeType(final List<Field> inFields, String inGroup, String inDescription) throws OpenDataException {
		final String[] theNames = new String[inFields.size()];
		final String[] theDescriptions = new String[inFields.size()];
		final OpenType[] theTypes = new OpenType[inFields.size()];

		for (int indexFields = 0; indexFields < inFields.size(); indexFields++) {
			final Field theField = inFields.get(indexFields);
			final CompositeDataField theAnnotation = theField.getAnnotation(CompositeDataField.class);
			theNames[indexFields] = theField.getName();
			theDescriptions[indexFields] = theAnnotation.description();
			theTypes[indexFields] = AbstractProbe.getOpenType(theField.getType());
		}
		return new CompositeType(inGroup, inDescription, theNames, theDescriptions, theTypes);
	}

	protected synchronized CompositeData getStat(String inGroup) throws OpenDataException {
		final Map<String, Object> theStats = new HashMap<String, Object>();

		try {
			for (final Field aField : this.mFields.get(inGroup)) {
				theStats.put(aField.getName(), aField.get(this));
			}
		} catch (final IllegalAccessException e) {
			AbstractProbe.LOGGER.fatal(e, e);
		}

		return new CompositeDataSupport(this.mCompositeTypes.get(inGroup), theStats);
	}

	public static OpenType getOpenType(Class inTypeName) {
		if (inTypeName.equals(Integer.class) || inTypeName.equals(int.class)) {
			return SimpleType.INTEGER;
		}

		if (inTypeName.equals(Long.class) || inTypeName.equals(long.class)) {
			return SimpleType.LONG;
		}

		if (inTypeName.equals(Double.class)) {
			return SimpleType.DOUBLE;
		}

		return SimpleType.VOID;
	}

}

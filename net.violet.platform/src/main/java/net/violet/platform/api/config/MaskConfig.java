package net.violet.platform.api.config;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.Converter;
import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.MissingParameterException;
import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.files.FilesManagerFactory;

import org.apache.log4j.Logger;

public class MaskConfig {

	private static final Logger LOGGER = Logger.getLogger(MaskConfig.class);

	/**
	 * @param reference
	 * @param hardwareType
	 * @return
	 * @throws IOException 
	 * @throws ConversionException 
	 * @throws InvalidParameterException 
	 * @throws IOException 
	 */
	public static List<SchedulingMaskMap> loadConfig(Files inConfigFile, ObjectType inType) throws InvalidParameterException {
		try {
			return MaskConfig.loadConfig(FilesManagerFactory.FILE_MANAGER.getReaderFor(inConfigFile), inConfigFile.getType(), inType);
		} catch (final IOException e) {
			MaskConfig.LOGGER.fatal(e, e);
		}

		return Collections.emptyList();
	}

	protected static List<SchedulingMaskMap> loadConfig(Reader inReader, MimeType.MIME_TYPES inFileType, ObjectType inObjectType) throws InvalidParameterException {

		final List<SchedulingMaskMap> schedulingMasksForHardware = new ArrayList<SchedulingMaskMap>();

		final Converter theConverter = ConverterFactory.getConverter(inFileType);
		if (theConverter != null) {
			try {
				final Map<String, Object> configMap = (Map<String, Object>) theConverter.convertFrom(inReader);

				for (final Map<String, Object> aSchedulingMask : (List<Map<String, Object>>) configMap.get("scheduling_masks")) {
					final ObjectType currentType = ObjectType.findByName(aSchedulingMask.get("hw_type").toString());
					if (inObjectType.instanceOf(currentType)) {
						schedulingMasksForHardware.add(new SchedulingMaskMap(aSchedulingMask));
					}
				}
			} catch (final ConversionException e) {
				MaskConfig.LOGGER.fatal(e, e);
			} catch (final InternalErrorException e) {
				MaskConfig.LOGGER.fatal(e, e);
			}
		}

		return schedulingMasksForHardware;
	}

	protected static class SchedulingMaskMap extends AbstractAPIMap {

		private static final String SCHEDULING_TYPE = "scheduling_type";

		private static final List<String> VALID_SCHEDULING_TYPES = Arrays.asList("Daily", "DailyWithMedia", "DailyWithDuration", "Weekly", "RandomWithFrequency", "VoiceTrigger", "InteractionTrigger", "NewContentWithFrequency", "NewContentWithKeywordAndMedia", "Ambiant", "AmbiantWithKeyword", "Frequency", "NewContent");
		private static final String[] AVAILABLE_PARAMETERS = { SchedulingMaskMap.SCHEDULING_TYPE, "event", "label", "parameters", "maximum", "minimum", "keyword", "supports_target" };

		/**
		 * Take the already built map
		 * 
		 * @param inMap
		 * @throws InvalidParameterException
		 */
		public SchedulingMaskMap(Map<String, Object> inMap) throws InvalidParameterException {
			for (final String aParameter : SchedulingMaskMap.AVAILABLE_PARAMETERS) {
				final Object value = inMap.get(aParameter);
				if (value != null) {
					put(aParameter, value);
				}
			}

			checkConformance();
		}

		@Override
		public void checkConformance() throws InvalidParameterException {

			// check some more conformance (the super constructor has already tested
			// the POJO conformance)
			final String type = (String) this.get(SchedulingMaskMap.SCHEDULING_TYPE);
			if (type == null) {
				throw new MissingParameterException(SchedulingMaskMap.SCHEDULING_TYPE);

			} else if (!SchedulingMaskMap.VALID_SCHEDULING_TYPES.contains(type)) {
				throw new InvalidParameterException(SchedulingMaskMap.SCHEDULING_TYPE, type);
			}

		}

	}

}

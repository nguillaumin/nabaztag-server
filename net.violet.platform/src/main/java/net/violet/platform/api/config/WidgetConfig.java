package net.violet.platform.api.config;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.violet.common.StringShop;
import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.Converter;
import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.MissingParameterException;
import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.files.FilesManagerFactory;
import net.violet.platform.util.DicoTools;

import org.apache.log4j.Logger;

/**
 * Classe permettant de charger les différentes interfaces de configuration
 * d'une application
 */
public class WidgetConfig {

	private static final Logger LOGGER = Logger.getLogger(WidgetConfig.class);

	/**
	 * Replace DicoTools.dico entries of the label and descriptions of a specific widget
	 * 
	 * @param appName
	 * @param widgetKey
	 * @param widgetMap
	 */
	private static void replaceDicoEntries(String widgetKey, Map<String, Object> widgetMap, String appName) {

		// replace the language entries in the label and descriptions keys
		if (widgetMap.containsKey("label")) {
			if (!(widgetMap.get("label") instanceof String)) {
				// label contains a map of multilingual entries..
				// > replace the translations by a DicoTools.dico entry
				final String labelDicoEntry = DicoTools.LOC_PREFIX + StringShop.SLASH + appName + "/settings/" + widgetKey;
				widgetMap.put("label", labelDicoEntry);

			}
		}

		if (widgetMap.containsKey("description")) {
			final String descrDicoEntry = DicoTools.LOC_PREFIX + StringShop.SLASH + appName + "/settings/" + widgetKey + "/description";
			widgetMap.put("description", descrDicoEntry);
		}

		// Now look if we have more levels
		// (case of the Group, RadioGroup widgets)
		if (widgetMap.containsKey("items")) {
			int i = 0;

			for (final Object subitem : (List<Object>) widgetMap.get("items")) {
				final Map<String, Object> subItemMap = (Map<String, Object>) subitem;
				/*
				 * determine the subitem key (subitems from RadioGroup and
				 * Popup) don't have their own key, so we just index them.
				 * otherwise, we create a key of type <parent>.<child>
				 */
				String subItemKey = (String) subItemMap.get("key");
				subItemKey = (subItemKey == null) ? widgetKey + "[" + i + "]" : widgetKey + "." + subItemKey;

				WidgetConfig.replaceDicoEntries(subItemKey, subItemMap, appName);
				i = i + 1;
			}
		}
	}

	public static List<ConfigurationWidgetMap> loadConfig(FilesData inFile, boolean withLocalization) throws InvalidParameterException {
		try {
			return WidgetConfig.loadConfig(FilesManagerFactory.FILE_MANAGER.getReaderFor(inFile.getReference()), inFile.getReference().getType(), withLocalization);
		} catch (final IOException e) {
			WidgetConfig.LOGGER.fatal(e, e);
		}

		return Collections.emptyList();
	}

	protected static List<ConfigurationWidgetMap> loadConfig(Reader inReader, MimeType.MIME_TYPES inFileType, boolean withLocalization) throws InvalidParameterException {
		final List<ConfigurationWidgetMap> theWidgets = new ArrayList<ConfigurationWidgetMap>();

		final Converter converter = ConverterFactory.getConverter(inFileType);

		Map<String, Object> configMap;
		try {
			configMap = (Map<String, Object>) converter.convertFrom(inReader);
			for (final Map<String, Object> item : (List<Map<String, Object>>) configMap.get("settings")) {
				if (withLocalization) {
					final String widgetKey = (String) item.get("key");
					WidgetConfig.replaceDicoEntries(widgetKey, item, (new PojoMap(configMap)).getString("app.name", true));
				}
				theWidgets.add(new ConfigurationWidgetMap(item));
			}
		} catch (final ConversionException e) {
			WidgetConfig.LOGGER.fatal(e, e);
		} catch (final InternalErrorException e) {
			WidgetConfig.LOGGER.fatal(e, e);
		}

		return theWidgets;
	}

	protected static class ConfigurationWidgetMap extends AbstractAPIMap {

		private static final String TYPE = "type";
		private static final List<String> VALID_TYPES = Arrays.asList("Label", "InputLine", "TextArea", "CheckBox", "RadioGroup", "RadioButton", "Popup", "PopupItem", "ObjectPicker", "PeoplePicker", "MediaPicker", "Group");

		public ConfigurationWidgetMap(Map<String, Object> inMap) throws InvalidParameterException {
			super(inMap, true);
		}

		@Override
		public void checkConformance() throws InvalidParameterException {

			// type required
			final String type = (String) this.get(ConfigurationWidgetMap.TYPE);
			if (type == null) {
				throw new MissingParameterException(ConfigurationWidgetMap.TYPE);

			} else if (!ConfigurationWidgetMap.VALID_TYPES.contains(type)) {
				throw new InvalidParameterException(ConfigurationWidgetMap.TYPE, type);
			}
		}

	}
}

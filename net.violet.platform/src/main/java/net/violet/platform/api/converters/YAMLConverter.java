package net.violet.platform.api.converters;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.util.SafeBase64;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Dumper;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Loader;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

/**
 * A converter between YAML strings and a POJO structure (Plain Old Java Objects) 
 * that is equally YAML conformant and is based upon types : 
 * Map, List, String, Date, Integer, Double, Boolean, byte[]
 */
public class YAMLConverter implements Converter {

	private static final Logger LOGGER = Logger.getLogger(YAMLConverter.class);

	public static final String YAML_FORMAT = "yaml";
	private static final Yaml YAML;

	/**
	 * Create the unique instance of Yaml parser/emitter
	 * Use custom loader for POJO maps
	 * And custom representer for binaries
	 */
	static {
		final Loader yamlLoader = new Loader(new CustomPOJOConstructor());

		final CustomPOJORepresenter pojoRepresenter = new CustomPOJORepresenter();
		final DumperOptions options = new DumperOptions();
		options.setExplicitStart(true);
		options.setDefaultFlowStyle(false); //options.setDefaultFlowStyle(DumperOptions.DefaultFlowStyle.BLOCK);
		//options.setDefaultStyle('"');
		//options.setVersion(new Integer[] { 1, 0 });
		final Map<String, String> violetTags = new HashMap<String, String>(3);
		violetTags.put("!!", "tag:violet.net:");
		options.setTags(violetTags);
		final Dumper yamlDumper = new Dumper(pojoRepresenter, options);

		YAML = new Yaml(yamlLoader, yamlDumper);
	}

	/**
	 * @see net.violet.platform.api.converters.Converter#getFormatName()
	 */
	public String getFormatName() {
		return YAMLConverter.YAML_FORMAT;
	}

	/**
	 * @param inYAMLString
	 * @return
	 * @throws ConversionException
	 * @throws APIException
	 */
	public <T> T convertFrom(String inYAMLString) throws ConversionException {
		try {
			return (T) YAMLConverter.YAML.load(inYAMLString);
		} catch (final Exception e) {
			final String strErrorMessage = "Conversion of YAML input :\n" + inYAMLString + "\nfailed !\n" + e.getMessage();
			YAMLConverter.LOGGER.error(strErrorMessage, e);
			throw new ConversionException(e, strErrorMessage);
		}
	}

	/**
	 * @param reader
	 * @return
	 * @throws InternalErrorException 
	 * @throws ConversionException 
	 */
	public <T> T convertFrom(Reader inReader) throws InternalErrorException, ConversionException {
		final StringWriter reqBody = new StringWriter();
		try { // read all body content and free resources
			IOUtils.copy(inReader, reqBody);
		} catch (final IOException e) {
			throw new InternalErrorException(e.getMessage());
		} finally {
			IOUtils.closeQuietly(inReader);
		}
		return this.<T> convertFrom(reqBody.toString());
	}

	/**
	 * Serialize an object in the JavaScript Object Notation Supported Data
	 * types are : Map, List, String, Date, Number, Boolean, null Illegal
	 * members will be dropped
	 * 
	 * @return a String conformant to the JavaScript Objects Notation
	 * @throws ConversionException
	 */
	public String convertTo(Object inPojo) throws ConversionException {
		try {
			return YAMLConverter.YAML.dump(inPojo);
		} catch (final Exception e) {
			final String strErrorMessage = "Conversion of " + inPojo + " (" + inPojo.getClass().getName() + ") to YAML failed !\n" + e.getMessage();
			YAMLConverter.LOGGER.error(strErrorMessage, e);
			throw new ConversionException(e, strErrorMessage);
		}
	}

	/**
	 * @throws ConversionException
	 */
	public void convertTo(Object inPojo, Writer file_name) throws ConversionException {
		try {
			YAMLConverter.YAML.dump(inPojo, file_name);
		} catch (final Exception e) {
			throw new ConversionException(e, "Conversion of " + inPojo + " (" + inPojo.getClass().getName() + ") to YAML failed !\n" + e.getMessage());
		}
	}

	/**
	 * The inOption parameter is useless, calling this method is equivalent to
	 * calling the other convertTo method.
	 */
	public String convertTo(Object inPojo, boolean inOption) throws ConversionException {
		return convertTo(inPojo);
	}

	/**
	 * This interface is used by objects that want to provide their own YAML tag name
	 */
	public interface YAMLTagged {

		String getYAMLTag();
	}

	/**
	 * A customized YAML constructors that override SnakeYAML default mechanism 
	 * of converting binaries into Strings
	 * > We want byte[] instead, and we use our Base64 decoder..
	 */
	public static class CustomPOJOConstructor extends Constructor {

		public CustomPOJOConstructor() {
			super();
			final Construct binaryConstructor = new ConstructYamlBinary();
			this.yamlConstructors.put("tag:yaml.org,2002:binary", binaryConstructor);
			this.yamlConstructors.put("tag:violet.net:binary", binaryConstructor);
		}

		private class ConstructYamlBinary implements Construct {

			public Object construct(Node node) {
				try {
					return SafeBase64.decode(node.getValue().toString());
				} catch (final Exception e) {
					return null;
				}
			}
		}
	}

	/**
	 * A customized YAML representer that add the native support of byte[]
	 * and check the map structures for the getYAMLTag() method 
	 * @author christophe - Violet
	 */
	public static class CustomPOJORepresenter extends Representer {

		public CustomPOJORepresenter() {
			super(null, false); //super('"', false);
			this.representers.put(byte[].class, new RepresentBinary());
			this.multiRepresenters.put(Map.class, new RepresentPojoMap());
		}

		private class RepresentBinary implements Represent {

			public Node representData(Object data) {
				final String tag = "tag:yaml.org,2002:binary";
				final String value = SafeBase64.encode((byte[]) data);

				return representScalar(tag, value);
			}
		}

		private class RepresentPojoMap implements Represent {

			public Node representData(Object data) {
				final Map<Object, Object> map = (Map<Object, Object>) data;
				String tag = "tag:yaml.org,2002:map";

				if (map instanceof YAMLTagged) {
					tag = ((YAMLTagged) map).getYAMLTag();
				} else {
					if (YAMLConverter.LOGGER.isDebugEnabled()) {
						YAMLConverter.LOGGER.debug("We do not expect a map that is not a POJO map :" + map);
					}
				}

				return representMapping(tag, map, null);
			}
		}
	}

}

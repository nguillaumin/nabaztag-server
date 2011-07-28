package net.violet.platform.api.converters;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.violet.platform.api.converters.pojo.VioletPojoFixer;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.SafeBase64;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.xml.sax.InputSource;

/**
 * Converter between the violet XML format and POJO structures. <small>
 * 
 * <pre>
 * &lt;map&gt;
 *   &lt;to&gt;&lt;list&gt;&lt;item&gt;&lt;map&gt;&lt;object_name&gt;&quot;Solipsiste&quot;&lt;/object_name&gt;&lt;/map&gt;&lt;/item&gt;&lt;/list&gt;&lt;/to&gt;
 *   &lt;application&gt;&quot;Sample Application&quot;&lt;/application&gt;
 *   &lt;sequence&gt;&lt;list&gt;
 *     &lt;item&gt;&lt;map&gt;
 *        &lt;type&gt;&quot;expression&quot;&lt;/type&gt;
 *        &lt;modality&gt;&quot;net.violet.tts.en&quot;&lt;/modality&gt;
 *        &lt;text&gt;&lt;string&gt;Hello. What does &quot;Solipsiste&quot; mean?&lt;/string&gt;&lt;/text&gt;
 *        &lt;gender&gt;&quot;Male&quot;&lt;/gender&gt;
 *        &lt;quality&gt;1.0&lt;/quality&gt;
 *        &lt;async&gt;true&lt;/async&gt;
 *     &lt;/map&gt;&lt;/item&gt;
 *     &lt;item&gt;&lt;map&gt;
 *        &lt;type&gt;&quot;expression&quot;&lt;/type&gt;
 *        &lt;modality&gt;&quot;net.violet.choregraphy.nabaztag&quot;&lt;/modality&gt;
 *        &lt;choregraphy&gt;
 *          &lt;data&gt;
 *            AAAAVwABAwEOAPQSDgD3BQ4E8gMOAPQADgHwAA4C8AAOA/AFDgH1AQ4C9QAOA/UC
 *            DgD3CA4B9wAOAvcADgP3BA4E9wYOAPQ1DgD3AA4B9wAOAvcADgP3AA4E9wAAAAA=
 *          &lt;/data&gt;
 *        &lt;/choregraphy&gt;
 *     &lt;/map&gt;&lt;/item&gt;
 *   &lt;/list&gt;&lt;/sequence&gt;
 * &lt;/map&gt;
 * </pre>
 * 
 * </small>
 */
public class PojoXMLConverter implements Converter {

	// the XML format as described in VioletOSSpecification §2.3.6
	public static final String POJO_XML_FORMAT = "xml";

	PojoXMLConverter() {
		// for the use of ConverterFactory only.
	}

	/**
	 * @see net.violet.platform.api.converters.Converter#getFormatName()
	 */
	public String getFormatName() {
		return PojoXMLConverter.POJO_XML_FORMAT;
	}

	/**
	 * This method provides a way to convert an XML document (provided as a
	 * String object) in a POJO. See the violetOS specification to learn more
	 * about the used XML format.
	 * 
	 * @param inXMLString the XML content to convert.
	 * @return the POJO.
	 * @throws ConversionException if a parsing error occurs (e.g. if the XML
	 *             content does not fit the specified format)
	 */
	public <T> T convertFrom(String inXMLString) throws ConversionException {
		return this.<T> convertFrom(new StringReader(inXMLString));
	}

	/**
	 * Converts the given reader object content (which must be an xml valid
	 * document/element) in a POJO.
	 */
	public <T> T convertFrom(Reader inXMLReader) throws ConversionException {
		try {
			return (T) VioletPojoFixer.fixPojo(XMLParser.parse(inXMLReader));
		} catch (final Exception e) {
			throw new ConversionException(e);
		}
	}

	/**
	 * This method is used to convert a POJO in a String object containing an
	 * equivalent XML document. The withHeader parameter indicates if the xml
	 * header (i.e. <?xml ...) must be present or not, i.e. if we are waiting
	 * for a full xml document or just a sample which should be added to an
	 * other xml content.
	 */
	public String convertTo(Object inObject, boolean withHeader) throws ConversionException {
		try {
			return XMLParser.convert(inObject, withHeader);
		} catch (final IOException e) {
			throw new ConversionException(e);
		}

	}

	/**
	 * This method converts the given POJO in a String object using the XML
	 * format. The returned XML is NOT a full document, but only a sample
	 * describing the provided object, i.e. the String does NOT contain any XML
	 * header.
	 */
	public String convertTo(Object inPojo) throws ConversionException {
		return convertTo(inPojo, false);
	}

	/**
	 * This class provided useful methods to map POJO and XML documents. It has
	 * to be used to convert a XML document into a POJO or to write a POJO as
	 * XML document.
	 */
	private static class XMLParser {

		// ////////////////// PARSING PART ////////////////////

		/**
		 * Uses the provided reader and creates a POJO according to its content.
		 * 
		 * @param inReader
		 * @return
		 * @throws JDOMException
		 * @throws IOException
		 */
		public static Object parse(Reader inReader) throws JDOMException, IOException {
			final SAXBuilder builder = new SAXBuilder();
			final Document document = builder.build(new InputSource(inReader));
			return XMLParser.createObject(document.getRootElement());
		}

		/**
		 * This method is used to recursively create a java object based on the
		 * provided element. Available elements : list, item, map, string and
		 * data. To create simple object see the convertSimpleObject method.
		 * 
		 * @param element the element to convert.
		 * @return the created POJO, null if the element does not fit.
		 */
		private static Object createObject(Element element) {

			final String name = element.getName();

			// List element : it contains 'item' nodes
			if (name.equals("list")) {
				final List<Object> result = new LinkedList<Object>();
				final List<Element> children = element.getChildren("item");
				for (final Element child : children) {
					if (child.getChildren().isEmpty()) {
						result.add(XMLParser.convertSimpleObject(child.getText()));
					} else {
						result.add(XMLParser.createObject((Element) child.getChildren().get(0)));
					}
				}
				return result;
			}

			// TaggedString element
			if (name.equals("string")) {
				return element.getText();
			}

			// Map element
			if (name.equals("map")) {
				final Map<String, Object> result = new TreeMap<String, Object>();
				final List<Element> children = element.getChildren();
				for (final Element child : children) {
					final String key = child.getName(); // the name of the tag is used as key
					Object value;
					if (child.getChildren().isEmpty()) {
						value = XMLParser.convertSimpleObject(child.getText());
					} else {
						value = XMLParser.createObject((Element) child.getChildren().get(0));
					}
					result.put(key, value);
				}

				return result;
			}

			// Data element
			if (name.equals("data")) {
				return SafeBase64.decode(element.getText());
			}

			// The element does not match
			return null;
		}

		/**
		 * This method is used to convert a simple object, i.e. an object which
		 * has no child.
		 * 
		 * @param data
		 * @return
		 */
		private static Object convertSimpleObject(String data) {

			// a string object
			if (data.startsWith("\"") && data.endsWith("\"")) {
				return data.substring(1, data.length() - 1);
			}

			// an integer object
			try {
				return Integer.parseInt(data);
			} catch (final NumberFormatException e) {}

			// a double object
			try {
				return Double.parseDouble(data);
			} catch (final NumberFormatException e) {}

			// null value
			if (data.equals("null")) {
				return null;
			}

			// boolean values
			if (data.equals("true") || data.equals("false")) {
				return new Boolean(data);
			}

			final CCalendar calendar = CCalendar.parseISODate(data);
			if (calendar != null) {
				return calendar.getTime();
			}

			return data;
		}

		// ////////////////// CONVERTING PART ////////////////////

		/**
		 * Call this method to convert a Pojo into a String containing the XML
		 * representation of this POJO.
		 * 
		 * @param inPojo the POJO to convert.
		 * @return a String containing the POJO as XML document.
		 */
		public static String convert(Object inPojo, boolean withHeader) throws IOException {

			final Element theElement = XMLParser.convertToElement(inPojo);
			final String theResult;

			if (theElement != null) {
				final XMLOutputter theOutput = new XMLOutputter(Format.getPrettyFormat());
				final Writer theWriter = new StringWriter();

				if (withHeader) {
					final Document theDocument = new Document(theElement);
					theOutput.output(theDocument, theWriter);
				} else {
					theOutput.output(theElement, theWriter);
				}

				theResult = theWriter.toString();

			} else {
				theResult = PojoHelper.convertToText(inPojo);
			}

			return theResult;
		}

		/**
		 * Converts the provided Object (must be a POJO according to the
		 * VioletOS specification) and returns an Element to create a XML
		 * representation.
		 * 
		 * @param inPojo the POJO to convert.
		 * @return a JDOM Element object, null if the provided object is not a
		 *         correct POJO.
		 */
		private static Element convertToElement(Object inPojo) {

			// data object
			if (inPojo instanceof byte[]) {
				final Element dataElement = new Element("data");
				dataElement.setText(SafeBase64.encode((byte[]) inPojo));
				return dataElement;
			}

			// list object
			if (inPojo instanceof List) {
				final List<Object> list = (List<Object>) inPojo;
				final Element listElement = new Element("list");

				for (final Object listItem : list) {
					final Element itemElement = new Element("item");

					// we have to check if the item is a primitive object
					if (!(listItem instanceof String) && PojoHelper.isPrimitiveType(listItem)) {
						itemElement.setText(PojoHelper.convertToText(listItem));

					} else {
						itemElement.setContent(XMLParser.convertToElement(listItem));
					}

					listElement.addContent(itemElement);
				}

				return listElement;
			}

			// map object
			if (inPojo instanceof Map) {
				final Map<String, Object> map = (Map<String, Object>) inPojo;
				final Element mapElement = new Element("map");

				for (final String key : map.keySet()) {
					final Element keyElement = new Element(key);
					final Object value = map.get(key);

					if (!(value instanceof String) && PojoHelper.isPrimitiveType(value)) {
						// Note : Strings are not considered here as primitive
						// types because they are wrapped inside a <string> tag
						keyElement.setText(PojoHelper.convertToText(value));

					} else {
						keyElement.setContent(XMLParser.convertToElement(value));
					}

					mapElement.addContent(keyElement);
				}

				return mapElement;
			}

			// string object
			if (inPojo instanceof String) {
				final Element stringElement = new Element("string");
				stringElement.setText(inPojo.toString());
				return stringElement;
			}

			return null;
		}
	}

}

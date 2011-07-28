package net.violet.platform.api.converters;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

/**
 * Convert <i>ANY</i> unknown XML format into a POJO representation<br/> 
 * Can be used to translate various XML sources (ex : ATOM, RSS) into a valid POJO
 * structure without knowing the structure of the data. 
 * The conversion is made in an agnostic way. (we know that we know nothing..) 
 * Exemple : 
 * (taken from https://www.projectzero.org/javadoc/latest/CORE/API/zero/json/xml/XMLToJSONTransformer.html) 
 * <small><pre>
 * &lt;getValuesReturn return=&quot;true&quot;&gt;
 *   &lt;attribute value=&quot;value&quot;/&gt;
 *   &lt;String&gt;First item&lt;/String&gt;
 *   &lt;String&gt;Second item&lt;/String&gt;
 *   &lt;String&gt;Third item&lt;/String&gt;
 *   &lt;TextTag&gt;Text!&lt;/TextTag&gt;
 *   &lt;EmptyTag/&gt;
 *   &lt;TagWithAttrs attr1=&quot;value1&quot; attr2=&quot;value2&quot; attr3=&quot;value3&quot;/&gt;
 *   &lt;TagWithAttrsAndText attr1=&quot;value1&quot; attr2=&quot;value2&quot; attr3=&quot;value3&quot;&gt;Text!&lt;/TagWithAttrsAndText&gt;
 * &lt;/getValuesReturn&gt;
 * </pre></small> 
 * would become the POJO structure (using JSON representation) :
 * <small><pre>
 * {
 *   &quot;getValuesReturn&quot; : {
 *     &quot;attr:return&quot; : &quot;true&quot;,
 *     &quot;TextTag&quot; : &quot;Text!&quot;,
 *     &quot;String&quot; : [ &quot;First item&quot;, &quot;Second item&quot;, &quot;Third item&quot; ],
 *     &quot;TagWithAttrsAndText&quot; : {
 *       &quot;#text&quot; : &quot;Text!&quot;,
 *       &quot;attr3&quot; : &quot;value3&quot;,
 *       &quot;attr2&quot; : &quot;value2&quot;,
 *       &quot;attr1&quot; : &quot;value1&quot;
 *     },
 *     &quot;EmptyTag&quot; : &quot;&quot;,
 *     &quot;attribute&quot; : { &quot;attr:value&quot; : &quot;value&quot; },
 *     &quot;TagWithAttrs&quot; : {
 *       &quot;attr:attr3&quot; : &quot;value3&quot;,
 *       &quot;attr:attr2&quot; : &quot;value2&quot;,
 *       &quot;attr:attr1&quot; : &quot;value1&quot;
 *     }
 *   }
 * }
 * </pre></small>
 *  
 * NOTE : Of course, we cannot make the reverse operation ! 
 * (translate a POJO structure into <i>any</i> XML jargon..) 
 * > Use the XMLConverter to convert POJO into violet.pojo.xml
 */
public class AgnosticXMLConverter implements Converter {

	// the only thing we know is that the format is an XML jargon
	public static final String AGNOSTIC_XML_FORMAT = "agnostic_xml";

	// attribute prefix
	private final String mAttrPrefix = "attr_";
	private final String mTextAttrName = "_text";

	AgnosticXMLConverter() {
		// for the use of ConverterFactory only.
	}

	/**
	 * @param inAttrPrefix
	 */
	AgnosticXMLConverter(String inAttrPrefix) {
		// for the use of ConverterFactory only.
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
	public Object convertFrom(String inXMLString) throws ConversionException {
		return convertFrom(new StringReader(inXMLString));
	}

	/**
	 * Converts the given reader object content (which must be an xml valid
	 * document/element) in a POJO.
	 */
	public Object convertFrom(Reader inXMLReader) throws ConversionException {
		try {
			final SAXBuilder builder = new SAXBuilder();
			final Document document = builder.build(new InputSource(inXMLReader));

			final Map<String, Object> jsonRoot = new HashMap<String, Object>(1);
			appendObject(jsonRoot, document.getRootElement());

			return jsonRoot; // VioletPojoFixer.fixPojo();

		} catch (final Exception e) {
			throw new ConversionException(e);
		}
	}

	/**
	 * This method is used to recursively create a java object based on the
	 * provided element. Available elements : list, item, map, string and data.
	 * To create simple object see the convertSimpleObject method.
	 * 
	 * @param element the element to convert.
	 * @return the created POJO, null if the element does not fit.
	 */
	private void appendObject(Map<String, Object> container, Element element) {

		final String name = element.getQualifiedName();
		final List<Attribute> attributes = element.getAttributes();
		final List<Element> childElements = element.getChildren();

		/*
		 * The simplest tag containing only a text value !
		 */
		if ((childElements.size() == 0) && (attributes.size() == 0)) {
			container.put(name, element.getTextNormalize());
			return;
		}

		/*
		 * Element is composed of attributes and child elements that we put in a map
		 */
		final Map<String, Object> objectMap = new HashMap<String, Object>();

		// add attributes, using the predefined prefix to avoid collisions with
		// elements names
		for (final Attribute attr : attributes) {
			objectMap.put(this.mAttrPrefix + attr.getQualifiedName(), attr.getValue());
		}

		// add child elements under their names (recursive call)
		for (final Element elt : childElements) {
			appendObject(objectMap, elt);
		}

		// add all the text content concatenated in one
		final String textContent = element.getTextNormalize();

		if (textContent.length() > 0) {
			objectMap.put(this.mTextAttrName, textContent);
		}

		/*
		 * now its time to add our object in the parent container but.. are we
		 * the only one with the given name ?
		 */
		if (container.containsKey(name)) {
			// we are not alone !
			final Object existingMappedElement = container.get(name);
			List<Object> fraternity;

			if (existingMappedElement instanceof List) {
				// the fraternity is allready organized
				fraternity = (List) existingMappedElement;
				fraternity.add(objectMap);

			} else {
				// we have one self-ignoring brother
				// > create the fraternity
				fraternity = new ArrayList<Object>();
				fraternity.add(existingMappedElement);
				fraternity.add(objectMap);
			}
			container.put(name, fraternity);

		} else {
			// we are truly unique.. (until now)
			container.put(name, objectMap);
		}

	}

	/**
	 * @see net.violet.platform.api.converters.Converter#convertTo(java.lang.Object)
	 */
	public String convertTo(Object inPojo) {
		throw new UnsupportedOperationException("Use XMLConverter to convert POJO structure to violet.pojo.xml");
	}

	/**
	 * @see net.violet.platform.api.converters.Converter#convertTo(java.lang.Object,
	 *      boolean)
	 */
	public String convertTo(Object inPojo, boolean inOption) {
		throw new UnsupportedOperationException("Use XMLConverter to convert POJO structure to violet.pojo.xml");
	}

	/**
	 * @see net.violet.platform.api.converters.Converter#getFormatName()
	 */
	public String getFormatName() {
		return AgnosticXMLConverter.AGNOSTIC_XML_FORMAT;
	}
}

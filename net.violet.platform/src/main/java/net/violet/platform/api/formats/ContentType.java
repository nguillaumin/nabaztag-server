package net.violet.platform.api.formats;

/* Copyright (c) 2006 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simple class for parsing and generating Content-Type header values, per RFC
 * 2045 (MIME) and 2616 (HTTP 1.1).
 */
public final class ContentType {

	private static String TOKEN = "[\\p{ASCII}&&[^\\p{Cntrl} ;/=\\[\\]\\(\\)\\<\\>\\@\\,\\:\\\"\\?\\=]]+";

	// Precisely matches a token
	private static Pattern TOKEN_PATTERN = Pattern.compile("^" + ContentType.TOKEN + "$");

	// Matches a media type value
	private static Pattern TYPE_PATTERN = Pattern.compile("(" + ContentType.TOKEN + ")" + // type ( G1 )
	"/" + // separator
	"(" + ContentType.TOKEN + ")" + // subtype (G2)
	"\\s*(.*)\\s*", Pattern.DOTALL);

	// Matches an attribute value
	private static Pattern ATTR_PATTERN = Pattern.compile("\\s*;\\s*" + "(" + ContentType.TOKEN + ")" + // attr name ( G1 )
	"\\s*=\\s*" + "(?:" + "\"([^\"]*)\"" + // value as quoted string (G3)
	"|" + "(" + ContentType.TOKEN + ")?" + // value as token (G2)
	")");

	/**
	 * Name of the attribute that contains the encoding character set for the
	 * content type.
	 * 
	 * @see #getCharset()
	 */
	public static final String ATTR_CHARSET = "charset";

	/**
	 * Special "*" character to match any type or subtype.
	 */
	private static final String STAR = "*";

	/**
	 * The UTF-8 charset encoding is used by default for all text and xml based
	 * MIME types.
	 */
	private static final String DEFAULT_CHARSET = ContentType.ATTR_CHARSET + "=UTF-8";
	private static final String ISO_CHARSET = ContentType.ATTR_CHARSET + "=ISO-8859-1";

	/**
	 * A ContentType constant that describes unknown mime type content..
	 */
	public static final ContentType APPLICATION_OCTET_STREAM = new ContentType("application/octet-stream;" + ContentType.ISO_CHARSET);

	/**
	 * A ContentType constant that describes the usual POST request format.
	 */
	public static final ContentType FORM_URL_ENCODED = new ContentType("application/x-www-form-urlencoded;" + ContentType.DEFAULT_CHARSET);

	/**
	 * A ContentType constant that describes the Atom feed/entry content type.
	 */
	public static final ContentType ATOM = new ContentType("application/atom+xml;" + ContentType.DEFAULT_CHARSET);

	/**
	 * A ContentType constant that describes the Atom Service content type.
	 */
	public static final ContentType ATOM_SERVICE = new ContentType("application/atomserv+xml;" + ContentType.DEFAULT_CHARSET);

	/**
	 * A ContentType constant that describes the RSS channel/item content type.
	 */
	public static final ContentType RSS = new ContentType("application/rss+xml;" + ContentType.DEFAULT_CHARSET);

	/**
	 * A ContentType constant that describes the extended JSON + meta content type.
	 */
	public static final ContentType JSON_WMETA = new ContentType("application/json-meta;" + ContentType.DEFAULT_CHARSET);

	/**
	 * A ContentType constant that describes the JSON content type.
	 */
	public static final ContentType JSON = new ContentType("application/javascript;" + ContentType.DEFAULT_CHARSET);

	/**
	 * A ContentType constant that describes the Javascript content type.
	 */
	public static final ContentType JAVASCRIPT = new ContentType("text/javascript;" + ContentType.DEFAULT_CHARSET);

	/**
	 * A ContentType constant that describes the generic text/xml content type.
	 */
	public static final ContentType TEXT_XML = new ContentType("text/xml;" + ContentType.DEFAULT_CHARSET);

	/**
	 * A ContentType constant that describes the generic text/html content type.
	 */
	public static final ContentType TEXT_HTML = new ContentType("text/html;" + ContentType.DEFAULT_CHARSET);

	/**
	 * A ContentType constant that describes the generic text/plain content
	 * type.
	 */
	public static final ContentType TEXT_PLAIN = new ContentType("text/plain;" + ContentType.DEFAULT_CHARSET);

	/**
	 * A ContentType constant that describes the generic text/xml content type.
	 */
	public static final ContentType TEXT_YAML = new ContentType("text/yaml;" + ContentType.DEFAULT_CHARSET);

	/**
	 * A ContentType constant that describes the MIME multipart/related content
	 * type.
	 */
	public static final ContentType MULTIPART_RELATED = new ContentType("multipart/related");

	/**
	 * Determines the best "Content-Type" header to use in a servlet response
	 * based on the "Accept" header from a servlet request.
	 * 
	 * @param acceptHeader "Accept" header value from a servlet request (not
	 *            <code>null</code>)
	 * @param actualContentTypes actual content types in descending order of
	 *            preference (non-empty, and each entry is of the form
	 *            "type/subtype" without the wildcard char '*') or
	 *            <code>null</code> if no "Accept" header was specified
	 * @return the best content type to use (or <code>null</code> on no match).
	 */
	public static ContentType getBestContentType(String acceptHeader, List<ContentType> actualContentTypes) {

		// If not accept header is specified, return the first actual type
		if (acceptHeader == null) {
			return actualContentTypes.get(0);
		}

		// iterate over all of the accepted content types to find the best match
		float bestQ = 0;
		ContentType bestContentType = null;

		for (final String acceptedTypeString : acceptHeader.split(net.violet.common.StringShop.COMMA)) {

			// create the content type object
			ContentType acceptedContentType;
			try {
				acceptedContentType = new ContentType(acceptedTypeString.trim());

			} catch (final IllegalArgumentException ex) {
				// ignore exception
				continue;
			}

			// parse the "q" value (default of 1)
			float curQ = 1;
			try {
				final String qAttr = acceptedContentType.getAttribute("q");
				if (qAttr != null) {
					final float qValue = Float.valueOf(qAttr);
					if ((qValue <= 0) || (qValue > 1)) {
						continue;
					}
					curQ = qValue;
				}

			} catch (final NumberFormatException ex) {
				// ignore exception
				continue;
			}

			// only check it if it's at least as good ("q") as the best one so
			// far
			if (curQ < bestQ) {
				continue;
			}

			/*
			 * iterate over the actual content types in order to find the best
			 * match to the current accepted content type
			 */
			for (final ContentType actualContentType : actualContentTypes) {

				/*
				 * if the "q" value is the same as the current best, only check
				 * for better content types
				 */
				if ((curQ == bestQ) && (bestContentType == actualContentType)) {
					break;
				}

				/*
				 * check if the accepted content type matches the current actual
				 * content type
				 */
				if (actualContentType.match(acceptedContentType)) {
					bestContentType = actualContentType;
					bestQ = curQ;
					break;
				}
			}
		}

		// if found an acceptable content type, return the best one
		if (bestQ != 0) {
			return bestContentType;
		}

		// Return null if no match
		return null;
	}

	/**
	 * Constructs a new instance with default media type
	 */
	public ContentType() {
		this(null);
	}

	/**
	 * Constructs a new instance from a content-type header value parsing the
	 * MIME content type (RFC2045) format. If the type is {@code null}, then
	 * media type and charset will be initialized to default values.
	 * 
	 * @param typeHeader content type value in RFC2045 header format.
	 */
	public ContentType(String typeHeader) {

		// If the type header is no provided, then use the HTTP defaults.
		if (typeHeader == null) {
			this.type = "application";
			this.subType = "octet-stream";
			this.attributes.put(ContentType.ATTR_CHARSET, "iso-8859-1"); // http default
			return;
		}

		// Get type and subtype
		final Matcher typeMatch = ContentType.TYPE_PATTERN.matcher(typeHeader);

		if (!typeMatch.matches()) {
			throw new IllegalArgumentException("Invalid media type:" + typeHeader);
		}

		this.type = typeMatch.group(1).toLowerCase();
		this.subType = typeMatch.group(2).toLowerCase();

		if (typeMatch.groupCount() < 3) {
			return;
		}

		// Get attributes (if any)
		final Matcher attrMatch = ContentType.ATTR_PATTERN.matcher(typeMatch.group(3));

		while (attrMatch.find()) {
			String value = attrMatch.group(2);

			if (value == null) {
				value = attrMatch.group(3);
				if (value == null) {
					value = net.violet.common.StringShop.EMPTY_STRING;
				}
			}

			this.attributes.put(attrMatch.group(1).toLowerCase(), value);
		}

		// Infer a default charset encoding if unspecified.
		if (!this.attributes.containsKey(ContentType.ATTR_CHARSET)) {

			this.inferredCharset = true;

			if (this.subType.endsWith("xml")) {

				if (this.type.equals("application")) {
					// BUGBUG: Actually have need to look at the raw stream here, 
					// but if client omitted the charset for "application/xml", they
					// are ignoring the STRONGLY RECOMMEND language in RFC 3023, sec 3.2.
					// I have little sympathy.
					this.attributes.put(ContentType.ATTR_CHARSET, "utf-8"); // best guess

				} else {
					this.attributes.put(ContentType.ATTR_CHARSET, "us-ascii"); // RFC3023, sec 3.1
				}

			} else if (this.subType.equals("json")) {
				this.attributes.put(ContentType.ATTR_CHARSET, "utf-8"); // RFC4627, sec 3

			} else {
				this.attributes.put(ContentType.ATTR_CHARSET, "iso-8859-1"); // http default
			}
		}
	}

	/** {code True} if parsed input didn't contain charset encoding info */
	private boolean inferredCharset;

	private String type;

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private String subType;

	public String getSubType() {
		return this.subType.toLowerCase();
	}

	public void setSubType(String subType) {
		this.subType = subType.toLowerCase();
	}

	/** Returns the full media type */
	public String getMediaType() {
		return new StringBuilder(32).append(this.type).append("/").append(this.subType).toString();
	}

	private final HashMap<String, String> attributes = new HashMap<String, String>();

	/**
	 * Returns the additional attributes of the content type.
	 */
	public HashMap<String, String> getAttributes() {
		return this.attributes;
	}

	/**
	 * Returns the additional attribute by name of the content type.
	 * 
	 * @param name attribute name
	 */
	public String getAttribute(String name) {
		return this.attributes.get(name);
	}

	/*
	 * Returns the charset attribute of the content type or null if the
	 * attribute has not been set.
	 */
	public String getCharset() {
		return this.attributes.get(ContentType.ATTR_CHARSET);
	}

	/**
	 * Returns whether this content type is match by the content type found in
	 * the "Accept" header field of an HTTP request.
	 * 
	 * @param acceptedContentType content type found in the "Accept" header
	 *            field of an HTTP request
	 */
	public boolean match(ContentType acceptedContentType) {
		final String acceptedType = acceptedContentType.getType();
		final String acceptedSubType = acceptedContentType.getSubType();

		return ContentType.STAR.equals(acceptedType) || (this.type.equals(acceptedType) && (ContentType.STAR.equals(acceptedSubType) || this.subType.equals(acceptedSubType)));
	}

	/**
	 * Returns whether this content type is match by the content type found in
	 * the "Accept" header field of an HTTP request.
	 * 
	 * @param acceptedContentType content type found in the "Accept" header
	 *            field of an HTTP request
	 */
	public boolean match(List<ContentType> acceptedContentTypes) {

		for (final ContentType accepted : acceptedContentTypes) {
			if (this.match(accepted)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Generates the Content-Type value
	 */
	@Override
	public String toString() {

		final StringBuilder sb = new StringBuilder();
		sb.append(this.type);
		sb.append("/");
		sb.append(this.subType);

		for (final String name : this.attributes.keySet()) {

			// Don't include any inferred charset attribute in output.
			if (this.inferredCharset && ContentType.ATTR_CHARSET.equals(name)) {
				continue;
			}

			sb.append(";");
			sb.append(name);
			sb.append("=");
			final String value = this.attributes.get(name);
			final Matcher tokenMatcher = ContentType.TOKEN_PATTERN.matcher(value);

			if (tokenMatcher.matches()) {
				sb.append(value);
			} else {
				sb.append("\"" + value + "\"");
			}
		}
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		final ContentType that = (ContentType) o;
		return this.type.equals(that.type) && this.subType.equals(that.subType) && this.attributes.equals(that.attributes);
	}

	@Override
	public int hashCode() {
		return (this.type.hashCode() * 31 + this.subType.hashCode()) * 31 + this.attributes.hashCode();
	}

	/**
	 * application/xml, text/xml and all others ending with xml
	 * 
	 * @return TRUE is format is an XML dialect
	 */
	public boolean isXml() {
		return this.subType.endsWith("xml");
	}

	/**
	 * application/json, text/json and some others ending with json
	 * 
	 * @return TRUE if the format is a JSON string
	 */
	public boolean isJson() {
		return this.subType.endsWith("json");
	}

	/**
	 * @return TRUE if the format is a a text format (text/*)
	 */
	public boolean isText() {
		return this.type.equals("text");
	}

	/**
	 * @return TRUE if the format is a a text/plain
	 */
	public boolean isTextPlain() {
		return this.type.equals("text") && this.subType.equals("plain");
	}

	/**
	 * application/yaml, text/yaml and some others ending with yaml
	 * 
	 * @return TRUE if the format is a YAML string
	 */
	public boolean isYaml() {
		return this.subType.endsWith("yaml");
	}

	/**
	 * @return TRUE if the charset is UTF-8
	 */
	public boolean isUTF8() {
		return "UTF-8".equalsIgnoreCase(this.getCharset());
	}

}

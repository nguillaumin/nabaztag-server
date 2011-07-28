package net.violet.platform.xmpp.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.IQResourcesQuery;
import net.violet.platform.xmpp.JabberClient;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcClientRequestImpl;
import org.apache.xmlrpc.common.TypeFactoryImpl;
import org.apache.xmlrpc.parser.XmlRpcRequestParser;
import org.apache.xmlrpc.serializer.DefaultXMLWriterFactory;
import org.apache.xmlrpc.serializer.XmlRpcWriter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.provider.IQProvider;
import org.jivesoftware.smack.util.StringUtils;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * @author Thierry Bomandouki <thierry@violet.net>
 */

public class JabberRPC extends IQ {

	public static final String IQ_ELEMENT = "iq";
	public static final String ELEMENT = "query";
	public static final String NAMESPACE = "jabber:iq:rpc";
	public static final IQProvider IQ_PROVIDER = new JabberRPCProvider();
	public static final String ATTR_METHOD_CALL = "methodCall";
	public static final String ATTR_METHOD_NAME = "methodName";
	public static final String ATTR_PARAMS = "params";
	public static final String ATTR_PARAM = "param";

	private static final class JabberRPCProvider implements IQProvider {

		/**
		 * Constructeur par défaut.
		 */
		private JabberRPCProvider() {
			// This space for rent.
		}

		public IQ parseIQ(final XmlPullParser parser) throws Exception {

			final StringBuilder buffer = new StringBuilder();

			// skip the <query> tag by calling parser.next()
			while (true) {

				switch (parser.next()) {

				case XmlPullParser.TEXT:
					// We need to escape characters like & and <
					buffer.append(StringUtils.escapeForXML(parser.getText()));
					break;

				case XmlPullParser.START_TAG:
					buffer.append('<' + parser.getName() + '>');
					break;

				case XmlPullParser.END_TAG:
					if ("query".equals(parser.getName())) {
						System.out.println("Attibut de query : " + parser.getAttributeValue(0));
						if (parser.getAttributeValue(0).equals(JabberRPC.NAMESPACE)) {
							// don't save the </query> end tag
							final JabberRPC jabberRPC = new JabberRPC(buffer.toString().trim());
							jabberRPC.process();
							return jabberRPC;
						}
					}

					buffer.append("</" + parser.getName() + '>');
					break;

				default:

				}

			}

		}

	}

	private static final SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss.SSS");

	public static final String JABBERXMLRPC_PLATEFORM = Constantes.XMPP_XMLRPC_ID + '@' + Constantes.XMPP_NABAZTAG_DOMAIN;

	/**
	 * Liste des ressources connectées pour la plateforme
	 */
	private static final List<String> RESOURCES = IQResourcesQuery.getClientResources(JabberRPC.JABBERXMLRPC_PLATEFORM);

	/**
	 * client JABBERXMLRPC
	 */
	private final JabberClient jabberClient;

	private static final Logger LOGGER = Logger.getLogger(JabberRPC.class);

	/**
	 * Le resultat de l'appel JabberXMLRPC
	 */
	private Object theResult;

	/**
	 * 
	 */

	private String xml;
	private String resultXml;

	/**
	 * @return
	 */
	private static JabberClient createXmlRpcClient() {
		Collections.shuffle(JabberRPC.RESOURCES);
		for (final String resource : JabberRPC.RESOURCES) {
			final JabberClient jC = new JabberClient(resource);
			if (jC.getXMPPConnection().isConnected()) {
				return jC;
			}
		}
		return null;
	}

	private void sendXmlRpcRequest() {
		this.jabberClient.sendPacket(this);
	}

	private static final DefaultXMLWriterFactory DEFAULT_XML_WRITER_FACTORY = new DefaultXMLWriterFactory();

	/**
	 * Constructeur : privé
	 * 
	 * @throws IOException
	 * @throws SAXException
	 * @throws XmlRpcException
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private JabberRPC(final String inXml) throws SecurityException, IllegalArgumentException {
		System.out.println("inXml : " + inXml);
		this.jabberClient = JabberRPC.createXmlRpcClient();
		if (getType() == Type.SET) {
			this.xml = inXml;
			// process();
		} else if (getType() == Type.RESULT) {
			this.resultXml = inXml;
		}
	}

	/**
	 * Constructeur : quand on fait l'appel XMLRPC over JABBER
	 * 
	 * @param from
	 * @param methodName
	 * @param params
	 * @throws XmlRpcException
	 * @throws SAXException
	 * @throws IOException
	 */
	public JabberRPC(final String from, final String methodName, final List<Object> params) throws XmlRpcException, SAXException, IOException {
		this.jabberClient = JabberRPC.createXmlRpcClient();
		this.xml = JabberRPC.makeXmlRequest(methodName, params);
		setFrom(from);
		setTo(JabberRPC.JABBERXMLRPC_PLATEFORM + "/" + this.jabberClient.getResource());
		setType(Type.SET);
		sendXmlRpcRequest();
	}

	public JabberRPC() {
		this.jabberClient = JabberRPC.createXmlRpcClient();
	}

	/**
	 * Construit le XML pour la requete XMLRPC
	 * 
	 * @param methodName
	 * @param params
	 * @return
	 * @throws XmlRpcException
	 * @throws SAXException
	 * @throws IOException
	 */
	private static String makeXmlRequest(final String methodName, final List<Object> params) throws XmlRpcException, SAXException, IOException {
		final String theXmlRequest;
		final XmlRpcClient theXmlRpcClient = new XmlRpcClient();
		final TypeFactoryImpl theTypeFactoryImpl = new TypeFactoryImpl(theXmlRpcClient);
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		final ContentHandler xmlHandler = JabberRPC.DEFAULT_XML_WRITER_FACTORY.getXmlWriter((XmlRpcClientConfigImpl) theXmlRpcClient.getClientConfig(), bos);
		final XmlRpcWriter theXmlRpcWriter = new XmlRpcWriter((XmlRpcClientConfigImpl) theXmlRpcClient.getClientConfig(), xmlHandler, theTypeFactoryImpl);
		theXmlRpcWriter.write(new XmlRpcClientRequestImpl(theXmlRpcClient.getClientConfig(), methodName, params));
		theXmlRequest = bos.toString();
		bos.close();
		return theXmlRequest;
	}

	/**
	 * Construit le XML pour le resultat de la requete XMLRPC
	 * 
	 * @return
	 * @throws XmlRpcException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	private String makeXmlResult() throws XmlRpcException, SAXException, IOException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		final String theXmlResult;
		final XmlRpcClient theXmlRpcClient = new XmlRpcClient();
		final TypeFactoryImpl theTypeFactoryImpl = new TypeFactoryImpl(theXmlRpcClient);
		final XmlRpcRequestParser theXmlRpcParser = new XmlRpcRequestParser((XmlRpcClientConfigImpl) theXmlRpcClient.getClientConfig(), theTypeFactoryImpl);

		// Infos de l'appel XMLRPC
		final String[] methodInfos = theXmlRpcParser.getMethodName().split(".");
		final Object[] params = theXmlRpcParser.getParams().toArray();

		final Class<?>[] paramsClass = new Class<?>[params.length];

		final Class<?> theClass = Class.forName(methodInfos[0]);
		int i = 0;
		for (final Object param : params) {
			paramsClass[i++] = param.getClass();
		}

		final Method theMethod = theClass.getMethod(methodInfos[1], paramsClass);
		this.theResult = theMethod.invoke(theClass.newInstance(), params);

		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		final ContentHandler xmlHandler = JabberRPC.DEFAULT_XML_WRITER_FACTORY.getXmlWriter((XmlRpcClientConfigImpl) theXmlRpcClient.getClientConfig(), bos);
		final XmlRpcWriter theXmlRpcWriter = new XmlRpcWriter((XmlRpcClientConfigImpl) theXmlRpcClient.getClientConfig(), xmlHandler, theTypeFactoryImpl);

		theXmlRpcWriter.write(theXmlRpcClient.getClientConfig(), this.theResult);

		theXmlResult = bos.toString();
		bos.close();
		return theXmlResult;
	}

	/**
	 * Processes JABBERXMLRPC query
	 * 
	 * @throws XmlRpcException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public void process() throws XmlRpcException, SAXException, IOException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		if (getType() == Type.SET) {
			// Si c'est un packet de type SET, on fait exécuter l'appel XMLRPC
			// et on construit la réponse XML de cet appel
			this.resultXml = makeXmlResult();
			JabberRPC.LOGGER.debug(this.resultXml);
			// En renvoie la réponse
			final String from = getFrom();
			setType(Type.RESULT);
			setFrom(getTo());
			setTo(from);
			this.jabberClient.sendPacket(this);
		}
	}

	/** Accesseurs **/
	@Override
	public String toXML() {
		String theXml = "<" + JabberRPC.IQ_ELEMENT + " xmlns='" + JabberRPC.NAMESPACE + "' type='" + ((getType() == Type.SET) ? "set" : "result") + "' " + "from='" + getFrom() + "' to='" + getTo() + "' id='" + getPacketID() + "'>";
		theXml += getChildElementXML();
		theXml += "</" + JabberRPC.IQ_ELEMENT + ">";
		return theXml;
	}

	@Override
	public String getChildElementXML() {
		return "<" + JabberRPC.ELEMENT + " xmlns='" + JabberRPC.NAMESPACE + "'>\n" + ((this.resultXml == null) ? this.xml : this.resultXml) + "\n</" + JabberRPC.ELEMENT + ">";
	}

	public Object getResult() {
		if ((this.theResult == null) && (this.resultXml != null)) {
			// On parse le xml
			try {
				final InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(this.resultXml.getBytes()));

				// TODO: Parser le xml du résultat pour retourner l'objet
				final XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				factory.setNamespaceAware(true);
				final XmlPullParser xpp = factory.newPullParser();
				xpp.setInput(reader);
				// xpp.setInput(arg0, arg1)
				int eventType = xpp.getEventType();
				while (eventType != XmlPullParser.END_DOCUMENT) {
					if (eventType == XmlPullParser.START_TAG) {
						if ("value".equals(xpp.getName())) {
							System.out.println("Start param tag " + xpp.getName());
							// Detection du type
							eventType = xpp.next();
							System.out.println("Data type tag " + xpp.getName());
							if ("string".equals(xpp.getName())) {
								eventType = xpp.next();
								this.theResult = xpp.getText();
								break;
							} else if ("boolean".equals(xpp.getName())) {
								eventType = xpp.next();
								this.theResult = Boolean.valueOf((xpp.getText().equals("0")) ? false : true);
								break;
							} else if ("i4".equals(xpp.getName()) || "int".equals(xpp.getName())) {
								xpp.next();
								this.theResult = new Integer(xpp.getText());
								break;
							} else if ("double".equals(xpp.getName())) {
								eventType = xpp.next();
								this.theResult = new Double(xpp.getText());
								break;
							} else if ("dateTime.iso8601".equals(xpp.getName())) {
								eventType = xpp.next();
								this.theResult = JabberRPC.dateFormater.parse(xpp.getText());
								break;
							} else if ("Base64".equals(xpp.getName())) {
								eventType = xpp.next();
								this.theResult = Base64.decodeBase64(xpp.getText().getBytes());
								break;
							} else if ("array".equals(xpp.getName())) {
								System.out.println("It's an array");
								// C'est une liste
								this.theResult = new ArrayList<Object>();
								while (!"data".equals(xpp.getName()) && (eventType != XmlPullParser.END_TAG)) {
									eventType = xpp.next();
									if ("value".equals(xpp.getName()) && (eventType == XmlPullParser.END_TAG)) {
										eventType = xpp.next();
									}
									System.out.println("Ici 1 : " + xpp.getName() + " value " + xpp.getText());
									if (!"value".equals(xpp.getName()) && !"data".equals(xpp.getName()) && (eventType != XmlPullParser.END_TAG)) {
										((List<Object>) this.theResult).add(JabberRPC.getActualValue(xpp));
									} else if ("data".equals(xpp.getName())) {
										eventType = xpp.next();
									}
								}
								break;
							} else if ("struct".equals(xpp.getName())) {
								// C'est une map
								this.theResult = new HashMap<String, Object>();
								eventType = xpp.next();
								while (!"struct".equals(xpp.getName()) && (eventType != XmlPullParser.END_TAG)) {
									if (!"name".equals(xpp.getName())) {
										eventType = xpp.next();
									} else {
										final String key = xpp.getText();
										eventType = xpp.next();
										Object value;
										value = JabberRPC.getActualValue(xpp);
										((Map<String, Object>) this.theResult).put(key, value);
										eventType = xpp.next();
										System.out.println("Tag ici : " + xpp.getName());
									}
								}
								break;
							}
						}
					}
					eventType = xpp.next();
				}

			} catch (final XmlPullParserException e) {
				JabberRPC.LOGGER.fatal(e, e);
			} catch (final IOException e) {
				JabberRPC.LOGGER.fatal(e, e);
			} catch (final ParseException e) {
				JabberRPC.LOGGER.fatal(e, e);
			}
		}
		return this.theResult;
	}

	private static Object getActualValue(XmlPullParser inParser) throws XmlPullParserException, IOException, ParseException {
		final String actualType = inParser.getName();
		int eventType = inParser.getEventType();
		System.out.println("Inside getActualValue for type " + actualType);
		if ("string".equals(actualType) && (eventType != XmlPullParser.END_TAG)) {
			inParser.next();
			return inParser.getText();
		} else if ("boolean".equals(actualType) && (eventType != XmlPullParser.END_TAG)) {
			inParser.next();
			return Boolean.valueOf((inParser.getText().equals("0")) ? false : true);
		} else if (("i4".equals(actualType) || "int".equals(actualType)) && (eventType != XmlPullParser.END_TAG)) {
			inParser.next();
			return new Integer(inParser.getText());
		} else if ("double".equals(actualType) && (eventType != XmlPullParser.END_TAG)) {
			inParser.next();
			return new Double(inParser.getText());
		} else if ("dateTime.iso8601".equals(actualType) && (eventType != XmlPullParser.END_TAG)) {
			inParser.next();
			return JabberRPC.dateFormater.parse(inParser.getText());
		} else if ("Base64".equals(actualType) && (eventType != XmlPullParser.END_TAG)) {
			inParser.next();
			return Base64.decodeBase64(inParser.getText().getBytes());
		} else if ("array".equals(actualType) && (eventType != XmlPullParser.END_TAG)) {
			// C'est une liste
			final List<Object> result = new ArrayList<Object>();
			eventType = inParser.getEventType();
			while (!"data".equals(inParser.getName()) && (eventType != XmlPullParser.END_TAG)) {
				eventType = inParser.next();
				System.out.println("Ici : " + inParser.getName());
				if (!"value".equals(inParser.getName()) && !"data".equals(inParser.getName())) {
					result.add(JabberRPC.getActualValue(inParser));
				} else if ("data".equals(inParser.getName())) {
					eventType = inParser.next();
				}
			}
			return result;
		} else if ("struct".equals(inParser.getName()) && (eventType != XmlPullParser.END_TAG)) {
			// C'est une map
			System.out.println("It's a map");
			final Map<String, Object> result = new HashMap<String, Object>();
			eventType = inParser.getEventType();
			if (eventType == XmlPullParser.END_TAG) {
				eventType = inParser.next();
				return result;
			}
			eventType = inParser.next();
			while (!"struct".equals(inParser.getName()) && (eventType != XmlPullParser.END_TAG)) {
				// eventType = inParser.next();
				System.out.println("Actual tag : " + inParser.getName());
				if (!"name".equals(inParser.getName())) {
					eventType = inParser.next();
				} else {
					eventType = inParser.next();
					final String key = inParser.getText();
					eventType = inParser.next();
					System.out.println("The tag1 is now : " + inParser.getName());
					eventType = inParser.next();
					System.out.println("The tag2 is now : " + inParser.getName());
					eventType = inParser.next();
					Object value;
					value = JabberRPC.getActualValue(inParser);
					System.out.println("Key : " + key + " / Value : " + value);
					result.put(key, value);
					eventType = inParser.next();
					if ("struct".equals(inParser.getName()) && (eventType == XmlPullParser.END_TAG)) {
						break;
					}
					System.out.println("Le Tag à la fin1 : " + inParser.getName() + " value " + inParser.getText());
					eventType = inParser.next();
					if ("struct".equals(inParser.getName()) && (eventType == XmlPullParser.END_TAG)) {
						break;
					}
					System.out.println("Le Tag à la fin2 : " + inParser.getName() + " value " + inParser.getText());
					eventType = inParser.next();
					if ("struct".equals(inParser.getName()) && (eventType == XmlPullParser.END_TAG)) {
						break;
					}
					System.out.println("Le Tag à la fin3 : " + inParser.getName() + " value " + inParser.getText());
					eventType = inParser.next();
					if ("struct".equals(inParser.getName()) && (eventType == XmlPullParser.END_TAG)) {
						break;
					}
				}
				System.out.println("Le Tag à la fin5 : " + inParser.getName() + " value " + inParser.getText());
				if ("struct".equals(inParser.getName()) && (eventType == XmlPullParser.END_TAG)) {
					System.out.println("Je passe ici");
					eventType = inParser.next();
					if ("value".equals(inParser.getName()) && (eventType == XmlPullParser.END_TAG)) {
						System.out.println("Je passe là");
						eventType = inParser.next();
					} else {
						break;
					}
				}
			}
			return result;
		}
		return null;
	}

	public static void main(String[] args) {
		final JabberRPC rpc = new JabberRPC();
		rpc.resultXml = "<?xml version=\"1.0\"?>" +
		/*
		 * "<methodResponse>" + "<params>" + "<param>" +
		 * "<value><string>1332 East Fork Road, Lexington KY 11238</string></value>"
		 * + "</param>" + "</params>" + "</methodResponse>";
		 */
		"<methodResponse>" + "<params>" + "<param>" + "<value>" + "<array>" + "<data>" + "<value>" + "<struct>" + "<member>" + "<name>jid</name>" + "<value>" + "<string>zorncj@jabber.itlab.musc.edu</string>" + "</value>" + "</member>" + "<member>" + "<name>groups</name>" + "<value>" + "<struct/>" + "</value>" + "</member>" + "<member>" + "<name>name</name>" + "<value><string>Test Nick</string></value>" + "</member>" + "<member>" + "<name>id</name>" + "<value><int>11</int></value>" + "</member>" + "<member>" + "<name>subscription</name>" + "<value><string>to</string></value>" + "</member>" + "<member>" + "<name>ask</name>" + "<value/>" + "</member>" + "</struct>" + "</value>" + "<value><string>1332 East Fork Road, Lexington KY 11238</string></value>" + "</data>" + "</array>" + "</value>"
				+ "</param>" + "</params>" + "</methodResponse>";
		System.out.println("Resultat : " + rpc.getResult());
	}

}

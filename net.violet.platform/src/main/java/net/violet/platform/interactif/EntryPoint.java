package net.violet.platform.interactif;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import net.violet.common.utils.io.StreamTools;
import net.violet.platform.applets.AppletDispatcher;
import net.violet.platform.applets.interactive.InteractiveApplet;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.MessageCounter.RABBIT_STATE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.interactif.Status.Source;
import net.violet.platform.message.Message;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.MessageServices;
import net.violet.platform.message.Message.SOURCE;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.ConvertTools;
import net.violet.platform.xmpp.IQConfigQuery;
import net.violet.platform.xmpp.JabberComponent;
import net.violet.platform.xmpp.JabberComponentManager;
import net.violet.platform.xmpp.JabberMessageFactory;
import net.violet.platform.xmpp.packet.IQCommandPacket;
import net.violet.platform.xmpp.serialization.V1Serializer;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * Classe pour gérer la connexion HTTP (via la JSP) pour le mode interactif.
 */
public class EntryPoint {

	private static final Logger LOGGER = Logger.getLogger(EntryPoint.class);

	/**
	 * Liste permettant de mettre tout les paramètres de l'url
	 */
	private Map<String, String> parameters = new HashMap<String, String>();

	public EntryPoint(HttpServletRequest request) {
		this.parameters = setParameter(request);
	}

	/**
	 * permet de recupèrer les parametres passés dans url
	 * 
	 * @param request
	 * @return Map des paramètres récupère
	 */
	private Map<String, String> setParameter(HttpServletRequest request) {

		final Map<String, String> theParameters = new HashMap<String, String>();

		try {
			request.setCharacterEncoding("UTF-8");

			final Enumeration t = request.getParameterNames();
			final String url = request.getRequestURL().toString(); // récupère l'url absolu pour le calcul du checkSum
			while (t.hasMoreElements()) {
				final String tmp = t.nextElement().toString();
				theParameters.put(tmp, request.getParameter(tmp));
			}
			theParameters.put("completeUrl", url + "?" + request.getQueryString()); // utiliser pour le checkSum dans le secureStreaming
		} catch (final UnsupportedEncodingException e) {
			EntryPoint.LOGGER.fatal(e, e);
		}
		return theParameters;
	}

	public static byte[] resolveInteractiveAppli(HttpServletRequest request) {
		byte[] theResult = null;
		try {
			theResult = EntryPoint.doResolveInteractiveAppli(request);
		} catch (final Throwable aThrowable) {
			EntryPoint.LOGGER.fatal(aThrowable, aThrowable);
		}
		return theResult;
	}

	private static byte[] doResolveInteractiveAppli(HttpServletRequest request) {

		final EntryPoint theInfo = new EntryPoint(request);
		byte[] theResult = null;

		try {
			// Retrieves the involved application
			String appName = theInfo.parameters.get("a");
			if (appName == null) {
				throw new IllegalArgumentException("missing a");
			}
			appName = URLDecoder.decode(appName, "UTF-8");
			final ApplicationData app = ApplicationData.findByName(appName);
			if ((app == null) || !app.isValid()) {
				throw new IllegalArgumentException("Invalid a");
			}

			// Retrieves the reader (active object, such as a rabbit)
			final String objectSerial = theInfo.parameters.get("sn");
			if (objectSerial == null) {
				throw new IllegalArgumentException("missing sn");
			}
			final VObject theObject = Factories.VOBJECT.findBySerial(objectSerial);
			if (theObject == null) {
				throw new IllegalArgumentException("invalid sn");
			}

			// Retrieves the ztamp object
			final String rfidSerial = theInfo.parameters.get("zn");
			final VObject theZtamp;
			if (rfidSerial != null) {
				theZtamp = Factories.VOBJECT.findBySerial(rfidSerial);
				if (theZtamp == null) {
					throw new IllegalArgumentException("invalid zn");
				}
			} else {
				theZtamp = null;
			}

			final InteractiveApplet theInteractiveApplet = AppletDispatcher.getInteractiveAppletByApplication(app);

			Subscription theSubscription = null;
			if (theZtamp != null) {
				theSubscription = Factories.SUBSCRIPTION.findByApplicationAndObject(app.getReference(), theZtamp).get(0);
			}

			final String inST = theInfo.parameters.get("st");
			if (inST == null) {
				throw new IllegalArgumentException("missing st");
			}
			final Source theSource = Source.getByName(inST);
			if (theSource == null) {
				throw new IllegalArgumentException("invalid st");
			}

			final int btnStatus = ConvertTools.atoi(theInfo.parameters.get("btn"));
			final int earLeft = ConvertTools.atoi(theInfo.parameters.get("el"));
			final int earRight = ConvertTools.atoi(theInfo.parameters.get("er"));
			final Status theStatus = new Status(theSource, btnStatus, earLeft, earRight);

			final String theCookie = theInfo.parameters.get("c"); // Cookie
			final int position = ConvertTools.atoi(theInfo.parameters.get("pos"));
			final int idx = ConvertTools.atoi(theInfo.parameters.get("idx"));

			byte[] recoFile = null;
			if (theStatus.getSource().equals(Source.RECO)) {
				InputStream theStream = null;
				try {
					recoFile = StreamTools.readBytes(theStream = request.getInputStream(), request.getContentLength());
					theStatus.setRecoFile(recoFile);
				} catch (final IOException e) {
					EntryPoint.LOGGER.fatal(e, e);
				} finally {
					IOUtils.closeQuietly(theStream);
				}
			}
			final List<Message> messagesList = theInteractiveApplet.processItMode(theObject, theSubscription, theCookie, position, idx, theStatus);
			for (final Message aMessage : messagesList) {
				if ((theResult == null) && aMessage.getReceiver().equals(theObject) && (aMessage.getDeliveryDate() == null)) {
					theResult = V1Serializer.getContentAsBytes(aMessage);

				} else {
					MessageServices.send(aMessage);
				}
			}

		} catch (final IllegalArgumentException anException) {
			EntryPoint.LOGGER.fatal(anException, anException);
		} catch (final UnsupportedEncodingException e) {
			EntryPoint.LOGGER.fatal(e, e);
		}

		return theResult;
	}

	// utilisé par la JSP sendJabberCommand
	public static String sendJabberCommand(HttpServletRequest request) {
		String theResult;

		try {
			theResult = EntryPoint.doSendJabberCommand(request);
		} catch (final Throwable aThrowable) {
			EntryPoint.LOGGER.fatal(aThrowable, aThrowable);
			theResult = aThrowable.getMessage();
		}
		return theResult;
	}

	private static String doSendJabberCommand(HttpServletRequest request) {
		final String what = request.getParameter("what");
		String theResult = "OK";
		if ("send".equals(what) || "src".equals(what) || "resources".equals(what) || "getconfig".equals(what) || "getrunningstate".equals(what) || "gosleeporactive".equals(what)) {

			final String nabname = request.getParameter("nabname");

			final String ttl = request.getParameter("ttl");

			VObject theObject = Factories.VOBJECT.findByName(nabname);

			if (theObject == null) {
				theObject = Factories.VOBJECT.findBySerial(nabname);
			}

			if (theObject != null) { // object ok

				if ("gosleeporactive".equals(what)) {
					final int status;
					final int messageMode;
					final String gotoParam = request.getParameter("goto");
					if (("sleep".equals(gotoParam))) {
						status = Message.MODE.VEILLE.getId();
						messageMode = JabberMessageFactory.IQ_STATUS_IDLE_MODE;
					} else {
						status = Message.MODE.ACTIF.getId();
						messageMode = JabberMessageFactory.IQ_STATUS_ASLEEP_MODE;
					}
					final MessageDraft theMessage = new MessageDraft(theObject);
					theMessage.setStatus(status);
					theMessage.setEars(theObject.getObject_left(), theObject.getObject_right());
					theMessage.setSourceUpdate(true);
					MessageServices.sendUsingXmpp(theMessage, messageMode);
				} else if ("resources".equals(what)) {
					// final String from = request.getParameter("from");
					// final JabberClient theClient =
					// JabberClientManager.getClient();
					final List<String> resources = theObject.getResources(); //IQResourcesQuery.getClientResources(theObject.getXmppAddress());
					theResult = resources.toString();
				} else if ("getconfig".equals(what)) {
					// recuperer la resource
					final List<String> resources = theObject.getResources(); //IQResourcesQuery.getClientResources(theObject.getXmppAddress());
					if (resources.size() > 0) {
						final String resource = resources.get(0);
						final Map<String, String> config = IQConfigQuery.getClientConfig(theObject.getXmppAddress(), resource, IQConfigQuery.ConfigType.staticConfig);
						theResult = IQCommandPacket.formatToHtml(config, IQCommandPacket.Type.getConfig);
					} else {
						theResult = "L'objet n'a pas de ressource, impossible de faire la demande.";
					}
				} else if ("getrunningstate".equals(what)) {
					// recuperer la resource
					final List<String> resources = theObject.getResources(); //IQResourcesQuery.getClientResources(theObject.getXmppAddress());
					if (resources.size() > 0) {
						final String resource = resources.get(0);
						final Map<String, String> config = IQConfigQuery.getClientConfig(theObject.getXmppAddress(), resource, IQConfigQuery.ConfigType.runningState);
						theResult = IQCommandPacket.formatToHtml(config, IQCommandPacket.Type.getRunningState);
					} else {
						theResult = "L'objet n'a pas de ressource, impossible de faire la demande.";
					}
				} else {
					final String listCommandes = request.getParameter("commandes");
					final int mode = Integer.parseInt(request.getParameter("mode"));
					final int rsrc = Integer.parseInt(request.getParameter("rsrc"));
					final String[] splitListCommande = listCommandes.split("\r?\n");
					final String format = request.getParameter("format");

					if (1 == mode) {
						final MessageDraft theMessage = new MessageDraft(theObject);
						theMessage.addSequences(splitListCommande, true);
						try {
							theMessage.setTTLInSecond(Integer.parseInt(ttl));
						} catch (final NumberFormatException nfe) {}
						MessageServices.sendUsingXmpp(theMessage, rsrc);
					} else if (2 == mode) {
						final MessageDraft theMessage = new MessageDraft(theObject);

						for (final String sequence : splitListCommande) {
							if (sequence.startsWith("MODE")) {// MODE 0 ou 1
								final StringTokenizer theOption = new StringTokenizer(sequence, net.violet.common.StringShop.SPACE);
								final int nb = theOption.countTokens();
								if (nb == 2) {
									theOption.nextToken(); // retire MODE
									theMessage.setStatus(Integer.parseInt(theOption.nextToken()));
									theMessage.setEars(theObject.getObject_left(), theObject.getObject_right());
									theMessage.setSourceUpdate(true);
								}
							} else if (sequence.startsWith("SOURCE")) { // SOURCE 1 5
								final StringTokenizer theOption = new StringTokenizer(sequence, net.violet.common.StringShop.SPACE);
								final int nb = theOption.countTokens();
								if (nb == 3) {
									theOption.nextToken(); // retire SOURCE
									final SOURCE theSource = SOURCE.findById(Integer.parseInt(theOption.nextToken()));
									theMessage.setSourceValue(theSource, Integer.parseInt(theOption.nextToken()));
									theMessage.setSourceUpdate(true);
								}
							} else if (sequence.startsWith("EAR")) { // EAR 12 13
								final StringTokenizer theOption = new StringTokenizer(sequence, net.violet.common.StringShop.SPACE);
								final int nb = theOption.countTokens();
								if (nb == 3) {
									theOption.nextToken(); // retire EAR
									theMessage.setEars(Integer.parseInt(theOption.nextToken()), Integer.parseInt(theOption.nextToken()));
									theMessage.setSourceUpdate(true);
								}
							} else if (sequence.startsWith("MSG")) { // MSG 2
								final StringTokenizer theOption = new StringTokenizer(sequence, net.violet.common.StringShop.SPACE);
								final int nb = theOption.countTokens();
								if (nb == 2) {
									theOption.nextToken(); // retire MSG
									theMessage.setNbMessages(RABBIT_STATE.getState(Integer.parseInt(theOption.nextToken())));
									theMessage.setSourceUpdate(true);
								}
							}
						}
						try {
							theMessage.setTTLInSecond(Integer.parseInt(ttl));
						} catch (final NumberFormatException nfe) {}
						MessageServices.sendUsingXmpp(theMessage, rsrc);
					} else if (mode == 3) {
						final MessageDraft theMessage = new MessageDraft(theObject);
						try {
							theMessage.addPojo(listCommandes, MimeType.MIME_TYPES.findByLabel(format));
							theMessage.setTTLInSecond(Integer.parseInt(ttl));
							MessageServices.sendUsingXmpp(theMessage, rsrc);
						} catch (final Exception e) {
							EntryPoint.LOGGER.fatal(e, e);
						}
					}

				}
			} else {
				theResult = "Objet inconnu!";
			}
		} else if ("raw".equals(what)) {
			final String thePacket = request.getParameter("packet");
			final JabberComponent theComponent = JabberComponentManager.getComponent(Constantes.XMPP_PLATFORM_COMPONENT);
			if ((thePacket == null) || thePacket.equals(net.violet.common.StringShop.EMPTY_STRING)) {
				theResult = "Empty packet";
			} else {
				theComponent.sendPacket(thePacket);
			}
		} else {
			theResult = "Unknown command.";
		}
		return theResult;
	}

}

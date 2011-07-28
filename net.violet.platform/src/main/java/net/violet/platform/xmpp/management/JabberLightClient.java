package net.violet.platform.xmpp.management;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.violet.platform.util.Constantes;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.util.Base64;

/**
 * Client XMPP de test.
 */
public class JabberLightClient {

	private static final Logger LOGGER = Logger.getLogger(JabberTestClient.class);

	public static final int ERROR = -1;
	public static final int INIT_STREAM = 0;
	public static final int WAITING_OPEN_STREAM = 1;
	/**
	 * Regex pour le paquet d'ouverture de stream.
	 */
	private static final Pattern OPEN_STREAM_XML_REGEX = Pattern.compile("<\\?xml version='1\\.0'\\?><stream:stream xmlns='jabber:client'([^>]*)>");

	public static final int WAITING_AUTH_MODE = 2;
	public static final int WAITING_LOGIN = 3;
	/**
	 * Regex pour le paquet d'information sur les authentifications disponibles.
	 */
	private static final Pattern AVAILABLE_AUTH_XML_REGEX = Pattern.compile("<stream:features><mechanisms xmlns='urn:ietf:params:xml:ns:xmpp-sasl'>(.*?)</mechanisms><register xmlns='http://.*?/features/.*?-register'/>.*</stream:features>");

	public static final int WAITING_REGISTRATION_FIELDS = 4;
	/**
	 * Regex pour le paquet d'information sur les authentifications disponibles.
	 */
	private static final Pattern REGISTRATION_FIELDS_XML_REGEX = Pattern.compile("<iq([^>]*)><query xmlns='jabber:iq:register'><instructions>(.*?)</instructions>(.*?)</query></iq>");

	public static final int WAITING_RESULT_SUCCESS = 5;
	/**
	 * Regex pour le paquet de validation de la creation de compte.
	 */
	private static final Pattern REGISTRATION_SUCCESS_XML_REGEX = Pattern.compile("<iq from='(.*?)' id='reg2' type='result'><query xmlns='jabber:iq:register'>(.*?)</query></iq>");

	public static final int WAITING_CHALLENGE_PART1 = 6;
	/**
	 * Regex pour le paquet challenge.
	 */
	private static final Pattern CHALLENGE_XML_REGEX = Pattern.compile("<challenge xmlns='urn:ietf:params:xml:ns:xmpp-sasl'>(.*)</challenge>");

	/**
	 * Regex pour déterminer le nonce du paquet challenge 1.
	 */
	private static final Pattern CHALLENGE_NONCE_REGEX = Pattern.compile("((.*,)?)nonce=((\"|')?)(.*?)\\3((,.*)?)");

	/**
	 * Regex pour déterminer le realm du paquet challenge 1.
	 */
	private static final Pattern CHALLENGE_REALM_REGEX = Pattern.compile("((.*,)?)realm=((\"|')?)(.*?)\\3((,.*)?)");

	/**
	 * Regex pour déterminer le qop du paquet challenge 1.
	 */
	private static final Pattern CHALLENGE_QOP_REGEX = Pattern.compile("((.*,)?)qop=((\"|')?)(.*?)\\3((,.*)?)");

	/**
	 * Regex pour l'echec de l'authentification.
	 */
	private static final Pattern AUTH_FAILED_REGEX = Pattern.compile("<failure xmlns='urn:ietf:params:xml:ns:xmpp-sasl'>(.*)</failure>");

	public static final int WAITING_CHALLENGE_PART2 = 7;
	/**
	 * Regex pour déterminer le rspauth du paquet challenge 2.
	 */
	private static final Pattern CHALLENGE_RSPAUTH_REGEX = Pattern.compile("rspauth=([A-Fa-f0-9]*)");

	public static final int WAITING_SUCCESS_AUTH = 8;
	/**
	 * Regex pour la fin du SASL.
	 */
	private static final Pattern SUCCESS_XML_REGEX = Pattern.compile("<success xmlns='urn:ietf:params:xml:ns:xmpp-sasl'/>");

	public static final int WAITING_STREAM_HEADER = 9;
	private static final Pattern FINAL_OPEN_STREAM_XML_REGEX = Pattern.compile("(.*?)<stream:stream xmlns='jabber:client'[^>]*>");

	public static final int WAITING_STREAM_HEADER_FEATURE = 10;
	private static final Pattern FINAL_FEATURE_STREAM_XML_REGEX = Pattern.compile("<stream:features>(.*?)</stream:features>");

	/**
	 * Récupération de la notification de changement de resources.
	 */
	public static final int WAITING_BOOT_NOTIFICATION = 11;
	/**
	 * Regex pour l'obtention de la ressource boot.
	 */
	private static final Pattern BIND_BOOT_REGEX = Pattern.compile("<iq(.*?) type='result'(.*?)><bind xmlns='urn:ietf:params:xml:ns:xmpp-bind'><jid>(.*?)/boot</jid></bind></iq>");

	/**
	 * Récupération de la notification de l'ouverture de session.
	 */
	public static final int WAITING_BOOT_SESSION_NOTIFICATION = 12;
	private static final Pattern START_SESSION_REGEX = Pattern.compile("<iq(.*?) type='result'(.*?)><session xmlns='urn:ietf:params:xml:ns:xmpp-session'/></iq>");

	/**
	 * Récupération de la notification de la presence.
	 */
	public static final int WAITING_BOOT_PRESENCE_NOTIFICATION = 13;
	private static final Pattern RESULT_PRESENCE_REGEX = Pattern.compile("(.*?)<presence(.*?)/>");

	/**
	 * Récupération des sources.
	 */
	public static final int WAITING_SOURCES = 14;
	/**
	 * Regex pour l'obtention des sources.
	 */
	private static final Pattern GET_SOURCES_REGEX = Pattern.compile("(.*?)<iq(.*?) type='(error|result)'(.*?)><query xmlns='violet:iq:sources'><packet xmlns='violet:packet' format='1.0'/></query>(.*?)</iq>");

	/**
	 * Récupération de la resource idle.
	 */
	public static final int WAITING_IDLE_NOTIFICATION = 15;
	private static final Pattern BIND_IDLE_REGEX = Pattern.compile("<iq(.*?) type='result'(.*?)><bind xmlns='urn:ietf:params:xml:ns:xmpp-bind'><jid>(.*?)/(.*?)</jid></bind></iq>(.*?)");

	/**
	 * Récupération de la notification de l'unbind sur boot.
	 */
	public static final int WAITING_UNBIND_BOOT_NOTIFICATION = 16;
	/**
	 * Regex pour l'unbind de la ressource boot.
	 */
	private static final Pattern UNBIND_BOOT_REGEX = Pattern.compile("<iq(.*?) type='result'(.*?)/>");

	/**
	 * Récupération de la notification de l'ouverture de session.
	 */
	public static final int WAITING_IDLE_SESSION_NOTIFICATION = 17;

	/**
	 * Récupération de la notification de la presence.
	 */
	public static final int WAITING_IDLE_PRESENCE_NOTIFICATION = 18;

	/**
	 * Prêt.
	 */
	public static final int READY = 42;
	/**
	 * Regex pour les iq recus : 2 = type 3 = to 4 = from
	 */
	private static final Pattern IQ_REGEX = Pattern.compile("<iq(.*?) type='(result|error|get|set)'(.*?) to='([^']*)' from='([^']*)'.*>");

	/**
	 * Taille du buffer.
	 */
	private static final int BUFFER_SIZE = 1024;

	/**
	 * Référence sur le domaine. Mise en static pour un gain de mémoire, on
	 * estime que deux JabberLightClient au sein d'un même programme n'auront
	 * pas pour vocation de joindre 2 plateformes en même temps.
	 */
	private static String mDomain = "xmpp.nabaztag.com";

	/**
	 * Référence sur la classe effectuant un handle sur les packets recus.
	 */
	private final JabberLightClientHandler mPacketHandler;

	/**
	 * Référence sur la socket.
	 */
	private final SocketChannel mSocket;

	/**
	 * Buffer pour les donnees (en entree)
	 */
	private final ByteBuffer mInputBuffer;

	/**
	 * Besoin d'enregistrer le compte ?
	 */
	private boolean mNeedRegistration;
	/**
	 * Login.
	 */
	private String mLogin;

	/**
	 * Mot de passe.
	 */
	private String mPassword;

	/**
	 * Résultat de computeAuthResponse.
	 */
	private String mAuthResponse;

	private int mCurrentState;

	/**
	 * Donnees non traitees.
	 */
	private String mRemaining;

	/**
	 * Ressource actuelle.
	 */
	private String mResource;

	private static String mFinalResource = "idle";

	/**
	 * Constructeur à partir du domaine, du serveur et du port.
	 * 
	 * @throws IOException En cas de problème I/O.
	 * @throws UnknownHostException Si le serveur n'existe pas.
	 */
	JabberLightClient(String inDomain, String inHost, int inPort) throws UnknownHostException, IOException {
		this(inDomain, inHost, inPort, null, null);
	}

	JabberLightClient(String inDomain, String inHost, int inPort, JabberLightClientHandler inPacketHandler) throws UnknownHostException, IOException {
		this(inDomain, inHost, inPort, inPacketHandler, null);
	}

	JabberLightClient(String inDomain, String inHost, int inPort, JabberLightClientHandler inPacketHandler, String inFinalResource) throws UnknownHostException, IOException {
		if (inFinalResource != null) {
			JabberLightClient.mFinalResource = inFinalResource;
		}
		// Ouverture de la socket.
		this.mSocket = SocketChannel.open(new InetSocketAddress(inHost, inPort));
		this.mSocket.socket().setSoTimeout(0);
		this.mSocket.configureBlocking(false);
		JabberLightClient.mDomain = inDomain;
		this.mInputBuffer = ByteBuffer.allocate(JabberLightClient.BUFFER_SIZE);
		write("<?xml version='1.0'?>");
		writeStreamHeader();
		this.mCurrentState = JabberLightClient.WAITING_OPEN_STREAM;
		this.mNeedRegistration = false;
		this.mResource = null;
		this.mPacketHandler = inPacketHandler;
	}

	/**
	 * Methode appelee lorsqu'il y a des donnees à lire sur la socket.
	 */
	public void processSocketData() throws IOException {
		processSocketData(true);
	}

	public void processSocketData(boolean inDataToRead) throws IOException {
		try {
			final boolean remaining;
			final String theString;
			if (inDataToRead) {
				final int read = this.mSocket.read(this.mInputBuffer);
				if (read < 0) {
					throw new IOException("Read -1 : Socket Closed");
				}
				final byte[] theBytes = new byte[read];
				this.mInputBuffer.rewind();
				this.mInputBuffer.get(theBytes, 0, read);
				this.mInputBuffer.clear();
				if (this.mRemaining != null) {
					remaining = true;
					theString = this.mRemaining + new String(theBytes);
					this.mRemaining = null;
				} else {
					remaining = false;
					theString = new String(theBytes);
					if (theString.equals(net.violet.common.StringShop.EMPTY_STRING)) {
						return;
					}
				}
				System.out.println("S: " + theString);
			} else {
				remaining = false;
				theString = this.mRemaining;
			}
			final int strLen = theString.length();
			final int theEnd;
			Matcher theMatcher;

			// Analyse de la ligne.
			switch (this.mCurrentState) {
			case WAITING_OPEN_STREAM:
				theMatcher = JabberLightClient.OPEN_STREAM_XML_REGEX.matcher(theString);
				if (theMatcher.find()) {
					theEnd = theMatcher.end();
					this.mCurrentState = JabberLightClient.WAITING_AUTH_MODE;
				} else {
					theEnd = 0;
					if (remaining) {
						throw new IllegalArgumentException("[" + this.mLogin + "] Unattended packet : " + theString + "\nAttended : " + theMatcher.toString());
					}
				}
				break;
			case WAITING_AUTH_MODE:
				theMatcher = JabberLightClient.AVAILABLE_AUTH_XML_REGEX.matcher(theString);
				if (theMatcher.find()) {
					theEnd = theMatcher.end();
					if (this.mLogin != null) {
						if (this.mNeedRegistration) {
							write("<iq type='get' id='reg1'><query xmlns='jabber:iq:register'/></iq>");
							this.mCurrentState = JabberLightClient.WAITING_REGISTRATION_FIELDS;
						} else {
							write("<auth xmlns='urn:ietf:params:xml:ns:xmpp-sasl' mechanism='DIGEST-MD5'/>");
							this.mCurrentState = JabberLightClient.WAITING_CHALLENGE_PART1;
						}
					} else {
						this.mCurrentState = JabberLightClient.WAITING_LOGIN;
					}
				} else {
					theEnd = 0;
					if (remaining) {
						throw new IllegalArgumentException("[" + this.mLogin + "] Unattended packet : " + theString + "\nAttended : " + theMatcher.toString());
					}
				}
				break;
			case WAITING_REGISTRATION_FIELDS:
				theMatcher = JabberLightClient.REGISTRATION_FIELDS_XML_REGEX.matcher(theString);
				if (theMatcher.find()) {
					theEnd = theMatcher.end();
					write("<iq type='set' id='reg2'><query xmlns='jabber:iq:register'><username>" + this.mLogin + "</username><password>" + this.mPassword + "</password></query></iq>");
					this.mCurrentState = JabberLightClient.WAITING_RESULT_SUCCESS;
				} else {
					theEnd = 0;
					if (remaining) {
						throw new IllegalArgumentException("[" + this.mLogin + "] Unattended packet : " + theString + "\nAttended : " + theMatcher.toString());
					}
				}
				break;
			case WAITING_RESULT_SUCCESS:
				theMatcher = JabberLightClient.REGISTRATION_SUCCESS_XML_REGEX.matcher(theString);
				if (theMatcher.find()) {
					theEnd = theMatcher.end();
					write("<auth xmlns='urn:ietf:params:xml:ns:xmpp-sasl' mechanism='DIGEST-MD5'/>");
					this.mCurrentState = JabberLightClient.WAITING_CHALLENGE_PART1;
				} else {
					theEnd = 0;
					if (remaining) {
						throw new IllegalArgumentException("[" + this.mLogin + "] Unattended packet : " + theString + "\nAttended : " + theMatcher.toString());
					}
				}
				break;
			case WAITING_CHALLENGE_PART1:
				theMatcher = JabberLightClient.CHALLENGE_XML_REGEX.matcher(theString);
				if (theMatcher.find()) {
					theEnd = theMatcher.end();
					final String theDataB64 = theMatcher.group(1);
					final String theData = new String(Base64.decode(theDataB64));
					final String theNonce;
					final String theRealm;
					final String theQop;
					theMatcher = JabberLightClient.CHALLENGE_NONCE_REGEX.matcher(theData);
					if (theMatcher.find()) {
						theNonce = theMatcher.group(5);

						theMatcher = JabberLightClient.CHALLENGE_REALM_REGEX.matcher(theData);
						if (theMatcher.find()) {
							theRealm = theMatcher.group(5);
						} else {
							theRealm = null;
						}

						theMatcher = JabberLightClient.CHALLENGE_QOP_REGEX.matcher(theData);
						if (theMatcher.find()) {
							theQop = theMatcher.group(5);
							if (theQop.equals("auth")) {
								write(buildAuthResponse(theNonce, theRealm, theQop));
							} else {
								throw new IllegalArgumentException("[" + this.mLogin + "] ERROR: qop is not 'auth' in challenge '" + theData + "' (qop = " + theQop + ")");
							}
						} else {
							throw new IllegalArgumentException("[" + this.mLogin + "] ERROR: cannot find qop in challenge '" + theData + "'");
						}
					} else {
						throw new IllegalArgumentException("[" + this.mLogin + "] ERROR: cannot find nonce in challenge '" + theData + "'");
					}
					this.mCurrentState = JabberLightClient.WAITING_CHALLENGE_PART2;
				} else {
					theEnd = 0;
					if (remaining) {
						throw new IllegalArgumentException("[" + this.mLogin + "] Unattended packet : " + theString + "\nAttended : " + theMatcher.toString());
					}
				}
				break;
			case WAITING_CHALLENGE_PART2:
				theMatcher = JabberLightClient.AUTH_FAILED_REGEX.matcher(theString);
				/* Echec de l'authentification : Création de compte */
				if (theMatcher.find()) {
					theEnd = theMatcher.end();
					write("<iq type='get' id='reg1'><query xmlns='jabber:iq:register'/></iq>");
					this.mCurrentState = JabberLightClient.WAITING_REGISTRATION_FIELDS;
					this.mNeedRegistration = true;
				} else {
					/* Challenge 1 OK */
					theMatcher = JabberLightClient.CHALLENGE_XML_REGEX.matcher(theString);
					if (theMatcher.find()) {
						theEnd = theMatcher.end();
						final String theDataB64 = theMatcher.group(1);
						final String theData = new String(Base64.decode(theDataB64));
						theMatcher = JabberLightClient.CHALLENGE_RSPAUTH_REGEX.matcher(theData);
						if (theMatcher.find()) {
							final String rspauth = theMatcher.group(1);
							if (checkRspauth(rspauth)) {
								write("<response xmlns='urn:ietf:params:xml:ns:xmpp-sasl'/>");
							} else {
								throw new IllegalArgumentException("[" + this.mLogin + "] ERROR: wrong rspauth in challenge 2");
							}
						} else {
							System.err.println("ERROR: cannot find rspauth in challenge '" + theData + "'");
						}
						this.mCurrentState = JabberLightClient.WAITING_SUCCESS_AUTH;
					} else {
						theEnd = 0;
						if (remaining) {
							throw new IllegalArgumentException("[" + this.mLogin + "] Unattended packet : " + theString + "\nAttended : " + theMatcher.toString());
						}
					}
				}
				break;
			case WAITING_SUCCESS_AUTH:
				theMatcher = JabberLightClient.SUCCESS_XML_REGEX.matcher(theString);
				if (theMatcher.find()) {
					theEnd = theMatcher.end();
					writeStreamHeader();
					this.mCurrentState = JabberLightClient.WAITING_STREAM_HEADER;
				} else {
					theEnd = 0;
					if (remaining) {
						throw new IllegalArgumentException("[" + this.mLogin + "] Unattended packet : " + theString + "\nAttended : " + theMatcher.toString());
					}
				}
				break;
			case WAITING_STREAM_HEADER:
				theMatcher = JabberLightClient.FINAL_OPEN_STREAM_XML_REGEX.matcher(theString);
				if (theMatcher.find()) {
					theEnd = theMatcher.end();
					/* Libération de cette ressource. */
					this.mAuthResponse = null;
					this.mCurrentState = JabberLightClient.WAITING_STREAM_HEADER_FEATURE;
				} else {
					theEnd = 0;
					if (remaining) {
						throw new IllegalArgumentException("[" + this.mLogin + "] Unattended packet : " + theString + "\nAttended : " + theMatcher.toString());
					}
				}
				break;
			case WAITING_STREAM_HEADER_FEATURE:
				theMatcher = JabberLightClient.FINAL_FEATURE_STREAM_XML_REGEX.matcher(theString);
				if (theMatcher.find()) {
					theEnd = theMatcher.end();
					if (this.mLogin.equals(Constantes.XMPP_PLATFORM_ID)) {
						changeResource(JabberLightClient.mFinalResource);
						this.mCurrentState = JabberLightClient.WAITING_IDLE_SESSION_NOTIFICATION;
					} else {
						changeResource("boot");
						this.mCurrentState = JabberLightClient.WAITING_BOOT_NOTIFICATION;
					}
				} else {
					theEnd = 0;
					if (remaining) {
						throw new IllegalArgumentException("[" + this.mLogin + "] Unattended packet : " + theString + "\nAttended : " + theMatcher.toString());
					}
				}
				break;
			case WAITING_BOOT_NOTIFICATION:
				theMatcher = JabberLightClient.BIND_BOOT_REGEX.matcher(theString);
				if (theMatcher.find()) {
					theEnd = theMatcher.end();
					this.mCurrentState = JabberLightClient.WAITING_BOOT_SESSION_NOTIFICATION;
				} else {
					theEnd = 0;
					if (remaining) {
						throw new IllegalArgumentException("[" + this.mLogin + "] Unattended packet : " + theString + "\nAttended : " + theMatcher.toString());
					}
				}
				break;
			case WAITING_BOOT_SESSION_NOTIFICATION:
				theMatcher = JabberLightClient.START_SESSION_REGEX.matcher(theString);
				if (theMatcher.find()) {
					theEnd = theMatcher.end();
					this.mCurrentState = JabberLightClient.WAITING_BOOT_PRESENCE_NOTIFICATION;
				} else {
					theEnd = 0;
					if (remaining) {
						throw new IllegalArgumentException("[" + this.mLogin + "] Unattended packet : " + theString + "\nAttended : " + theMatcher.toString());
					}
				}
				break;
			case WAITING_BOOT_PRESENCE_NOTIFICATION:
				theMatcher = JabberLightClient.RESULT_PRESENCE_REGEX.matcher(theString);
				if (theMatcher.find()) {
					theEnd = theMatcher.end();
					getSources();
					this.mCurrentState = JabberLightClient.WAITING_SOURCES;
				} else {
					theEnd = 0;
					if (remaining) {
						throw new IllegalArgumentException("[" + this.mLogin + "] Unattended packet : " + theString + "\nAttended : " + theMatcher.toString());
					}
				}
				break;
			case WAITING_SOURCES:
				// Bug de la plateforme qui envoi 2 fois les sources
				theMatcher = JabberLightClient.RESULT_PRESENCE_REGEX.matcher(theString);
				if (theMatcher.find()) {
					theEnd = theMatcher.end();
				} else {
					theMatcher = JabberLightClient.GET_SOURCES_REGEX.matcher(theString);
					if (theMatcher.find()) {
						theEnd = theMatcher.end();
						changeResource(JabberLightClient.mFinalResource);
						this.mCurrentState = JabberLightClient.WAITING_IDLE_NOTIFICATION;
					} else {
						theEnd = 0;
						if (remaining) {
							throw new IllegalArgumentException("[" + this.mLogin + "] Unattended packet : " + theString + "\nAttended : " + theMatcher.toString());
						}
					}
				}
				break;
			case WAITING_IDLE_NOTIFICATION:
				theMatcher = JabberLightClient.BIND_IDLE_REGEX.matcher(theString);
				if (theMatcher.find()) {
					theEnd = theMatcher.end();
					this.mCurrentState = JabberLightClient.WAITING_UNBIND_BOOT_NOTIFICATION;
				} else {
					theEnd = 0;
					if (remaining) {
						throw new IllegalArgumentException("[" + this.mLogin + "] Unattended packet : " + theString + "\nAttended : " + theMatcher.toString());
					}
				}
				break;
			case WAITING_UNBIND_BOOT_NOTIFICATION:
				theMatcher = JabberLightClient.UNBIND_BOOT_REGEX.matcher(theString);
				if (theMatcher.find()) {
					theEnd = theMatcher.end();
					this.mCurrentState = JabberLightClient.WAITING_IDLE_SESSION_NOTIFICATION;
				} else {
					theEnd = 0;
					if (remaining) {
						throw new IllegalArgumentException("[" + this.mLogin + "] Unattended packet : " + theString + "\nAttended : " + theMatcher.toString());
					}
				}
				break;

			case WAITING_IDLE_SESSION_NOTIFICATION:
				theMatcher = JabberLightClient.START_SESSION_REGEX.matcher(theString);
				if (theMatcher.find()) {
					theEnd = theMatcher.end();
					this.mCurrentState = JabberLightClient.WAITING_IDLE_PRESENCE_NOTIFICATION;
				} else {
					theEnd = 0;
					if (remaining) {
						throw new IllegalArgumentException("[" + this.mLogin + "] Unattended packet : " + theString + "\nAttended : " + theMatcher.toString());
					}
				}
				break;
			case WAITING_IDLE_PRESENCE_NOTIFICATION:
				theMatcher = JabberLightClient.RESULT_PRESENCE_REGEX.matcher(theString);
				if (theMatcher.find()) {
					theEnd = theMatcher.end();
					System.out.println("[" + this.mLogin + "] Step READY !");
					this.mCurrentState = JabberLightClient.READY;
				} else {
					theEnd = 0;
					if (remaining) {
						throw new IllegalArgumentException("[" + this.mLogin + "] Unattended packet : " + theString + "\nAttended : " + theMatcher.toString());
					}
				}
				break;
			case READY:
				theMatcher = JabberLightClient.IQ_REGEX.matcher(theString);
				if (theMatcher.find()) {
					final String theType = theMatcher.group(2);
					if (theType.equals("set")) {
						write(theString.replaceAll("to='.*?'", "to='" + theMatcher.group(5) + "'").replaceAll("from='.*?'", "from='" + theMatcher.group(4) + "'").replaceAll("type='.*?'", "type='result'").replaceAll(">.*", "/>").replaceAll("//>", "/>"));
					} else if (theType.equals("get")) {
						write(theString.replaceAll("to='.*?'", "to='" + theMatcher.group(5) + "'").replaceAll("from='.*?'", "from='" + theMatcher.group(4) + "'").replaceAll("type='.*?'", "type='error'").replaceAll(">.*", "/>").replaceAll("//>", "/>"));
					}
				}
				if (this.mPacketHandler != null) {
					this.mPacketHandler.handleReceivedPacket(this, theString);
				}
				theEnd = 0;
				break;
			default:
				JabberLightClient.LOGGER.fatal(new IllegalArgumentException("[FATAL][" + this.mLogin + "] Unattended packet in default state : " + theString));
				theEnd = -1;
			}
			if (theEnd >= 0) {
				if (theEnd < strLen) {
					this.mRemaining = theString.substring(theEnd);
					if (theEnd > 0) {
						processSocketData(false);
					}
				} else {
					this.mRemaining = null;
				}
			}
		} catch (final IllegalArgumentException e) {
			JabberLightClient.LOGGER.warn(e);
		}
	}

	/**
	 * Envoie des données sur la socket.
	 */
	void write(String inData) throws IOException {
		final ByteBuffer theOutputBuffer = ByteBuffer.allocate(inData.length());
		System.out.println("[" + this.mLogin + "] C: " + inData);
		theOutputBuffer.put(inData.getBytes("UTF-8"));
		theOutputBuffer.rewind();
		this.mSocket.write(theOutputBuffer);
	}

	/**
	 * Commande login.
	 */
	public final void login(String inLogin, String inPassword) throws IOException {
		this.mLogin = inLogin;
		this.mPassword = inPassword;
		if (this.mCurrentState == JabberLightClient.WAITING_LOGIN) {
			if (this.mNeedRegistration) {
				write("<iq type='get' id='reg1'><query xmlns='jabber:iq:register'/></iq>");
				this.mCurrentState = JabberLightClient.WAITING_REGISTRATION_FIELDS;
			} else {
				write("<auth xmlns='urn:ietf:params:xml:ns:xmpp-sasl' mechanism='DIGEST-MD5'/>");
				this.mCurrentState = JabberLightClient.WAITING_CHALLENGE_PART1;
			}
		}
	}

	public String getLogin() {
		return this.mLogin;
	}

	public String getDomain() {
		return JabberLightClient.mDomain;
	}

	public void getSources() throws IOException {
		write("<iq from='" + this.mLogin + "@" + JabberLightClient.mDomain + "/" + this.mResource + "' to='net.violet.platform@" + JabberLightClient.mDomain + "/sources' type='get' id='source-1'><query xmlns='violet:iq:sources'><packet xmlns='violet:packet' format='1.0'/></query></iq>");
	}

	public void doSimpleClick() throws IOException {
		write("<message id='click-" + System.currentTimeMillis() + "' from='" + this.mLogin + "@" + JabberLightClient.mDomain + "/" + this.mResource + "' to='net.violet.platform@" + JabberLightClient.mDomain + "/int'><button xmlns='violet:nabaztag:button'><clic>1</clic></button></message>");
	}

	public void doPing() throws IOException {
		write(StringShop.SPACE);
	}

	public void changeResource(String inNewResource) {
		try {
			bind(inNewResource);
			if (this.mResource != null) {
				unbind(this.mResource);
			}
			this.mResource = inNewResource;
			startSession();
			// Envoi de la presence
			write("<presence from='" + this.mLogin + "@" + JabberLightClient.mDomain + "/" + this.mResource + "' />");
		} catch (final IOException e) {
			JabberLightClient.LOGGER.fatal(e, e);
		}
	}

	public void startSession() throws IOException {
		write("<iq from='" + this.mLogin + "@" + JabberLightClient.mDomain + "/" + this.mResource + "' to='net.violet.platform@" + JabberLightClient.mDomain + "/sources' type='set' id='session-" + System.currentTimeMillis() + "'>" + "<session xmlns='urn:ietf:params:xml:ns:xmpp-session'/>" + "</iq>");
	}

	public void bind(String inResource) throws IOException {
		write("<iq from='" + this.mLogin + "@" + JabberLightClient.mDomain + "/" + inResource + "' type='set' id='bind-" + System.currentTimeMillis() + "'>" + "<bind xmlns='urn:ietf:params:xml:ns:xmpp-bind'>" + "<resource>" + inResource + "</resource></bind></iq>");
	}

	public void unbind(String inResource) throws IOException {
		write("<iq from='" + this.mLogin + "@" + JabberLightClient.mDomain + "/" + inResource + "' type='set' id='unbind-" + System.currentTimeMillis() + "'>" + "<unbind xmlns='urn:ietf:params:xml:ns:xmpp-bind'>" + "<resource>" + inResource + "</resource></unbind></iq>");
	}

	public int getState() {
		return this.mCurrentState;
	}

	/**
	 * Crée la réponse auth.
	 */
	private String buildAuthResponse(String inNonce, String inRealm, String inQop) {
		String theResponse = "username=\"" + this.mLogin + "\",";
		if (inRealm != null) {
			theResponse += "realm=\"" + inRealm + "\",";
		}
		theResponse += "nonce=\"" + inNonce + "\",";
		final String theCnonce = Long.toString(System.currentTimeMillis());
		theResponse += "cnonce=\"" + theCnonce + "\",";
		theResponse += "nc=00000001,qop=" + inQop + ",digest-uri=\"xmpp/" + JabberLightClient.mDomain + "\",";
		theResponse += "response=" + computeAuthResponse(inNonce, inRealm, inQop, theCnonce) + ",charset=utf-8";
		return "<response xmlns='urn:ietf:params:xml:ns:xmpp-sasl'>" + Base64.encodeBytes(theResponse.getBytes()) + "</response>";
	}

	/**
	 * Calcule la réponse.
	 */
	private String computeAuthResponse(String inNonce, String inRealm, String inQop, String inCnonce) {
		String theValue = null;

		String theRealm = inRealm;
		if (theRealm == null) {
			theRealm = StringShop.EMPTY_STRING;
		}
		final byte[] a1Value_1 = JabberLightClient.computeMD5Sum(this.mLogin + ":" + theRealm + ":" + this.mPassword);
		final byte[] a1Value_2 = (":" + inNonce + ":" + inCnonce).getBytes();
		final byte[] a1Value = new byte[a1Value_1.length + a1Value_2.length];
		System.arraycopy(a1Value_1, 0, a1Value, 0, a1Value_1.length);
		System.arraycopy(a1Value_2, 0, a1Value, a1Value_1.length, a1Value_2.length);
		theValue = JabberLightClient.bytesToHexa(JabberLightClient.computeMD5Sum(a1Value)) + ":" + inNonce + ":" + "00000001" + ":" + inCnonce + ":" + inQop + ":";
		this.mAuthResponse = JabberLightClient.bytesToHexa(JabberLightClient.computeMD5Sum(theValue + JabberLightClient.bytesToHexa(JabberLightClient.computeMD5Sum(":" + "xmpp/" + JabberLightClient.mDomain))));
		theValue = JabberLightClient.bytesToHexa(JabberLightClient.computeMD5Sum(theValue + JabberLightClient.bytesToHexa(JabberLightClient.computeMD5Sum("AUTHENTICATE" + ":" + "xmpp/" + JabberLightClient.mDomain))));

		return theValue;
	}

	/**
	 * Vérifie la réponse du serveur.
	 */
	private boolean checkRspauth(String inResponse) {
		final String myAuth = this.mAuthResponse;
		final boolean theResult = myAuth.equals(inResponse);
		if (!theResult) {
			System.err.println("[" + this.mLogin + "] SASL DEBUG: rspauth from server = " + inResponse);
			System.err.println("[" + this.mLogin + "] SASL DEBUG: my rspauth = " + myAuth);
		}
		return theResult;
	}

	/**
	 * Construit l'entête du flux.
	 * 
	 * @throws IOException
	 */
	private void writeStreamHeader() throws IOException {
		write("<stream:stream " + "xmlns='jabber:client' " + "xmlns:stream='http://etherx.jabber.org/streams' " + "to='" + JabberLightClient.mDomain + "' " + "version='1.0'>");
	}

	/**
	 * Retourne la somme MD5 d'une chaîne.
	 * 
	 * @param inString chaîne dont on veut la somme MD5
	 * @return la somme MD5 (sous la forme de valeurs hexa).
	 */
	public static byte[] computeMD5Sum(String inString) {
		return JabberLightClient.computeMD5Sum(inString.getBytes());
	}

	/**
	 * Retourne la somme MD5 d'octets.
	 * 
	 * @param inBytes octets dont on veut la somme MD5.
	 * @return la somme MD5 (sous la forme de valeurs hexa).
	 */
	public static byte[] computeMD5Sum(byte[] inBytes) {
		byte[] theResult = null;
		try {
			final MessageDigest theDigest = MessageDigest.getInstance("MD5");
			theDigest.update(inBytes);
			theResult = theDigest.digest();
		} catch (final NoSuchAlgorithmException e) {
			JabberLightClient.LOGGER.fatal(e, e);
		}
		return theResult;
	}

	/**
	 * Transforme un tableau de byte en hexa (minuscule). Fonction H de la RFC.
	 * 
	 * @param inBytes tableau.
	 * @return la chaîne correspondante.
	 */
	public static String bytesToHexa(byte[] inBytes) {
		final StringBuilder theResultBuffer = new StringBuilder();
		for (int i = 0; i < inBytes.length; ++i) {
			int value = inBytes[i];
			if (value < 0) {
				value += 256;
			}
			if (value < 16) {
				theResultBuffer.append("0");
			}
			theResultBuffer.append(Integer.toHexString(value));
		}
		return theResultBuffer.toString();
	}

	public SocketChannel getSocket() {
		return this.mSocket;
	}
}

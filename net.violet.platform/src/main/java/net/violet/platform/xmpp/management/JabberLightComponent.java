package net.violet.platform.xmpp.management;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.violet.common.utils.DigestTools;

import org.apache.log4j.Logger;

public class JabberLightComponent {

	private static final Logger LOGGER = Logger.getLogger(JabberTestComponent.class);

	public static final int ERROR = -1;
	public static final int INIT_STREAM = 0;
	public static final int WAITING_OPEN_STREAM = 1;

	/**
	 * Regex pour le paquet d'ouverture de stream.
	 */
	private static final Pattern OPEN_STREAM_XML_REGEX = Pattern.compile("<\\?xml version='1\\.0'\\?><stream:stream xmlns='jabber:component:accept' xmlns:stream='http://etherx.jabber.org/streams' to='(.*?)'[^>]*>");

	public static final int WAITING_FOR_ID = 2;

	private static final Pattern RESPONSE_STREAM_XML_REGEX = Pattern.compile("<\\?xml version='1\\.0'\\?><stream:stream xmlns:stream='http://etherx.jabber.org/streams' xmlns='jabber:component:accept' id='(.*?)' from='.*'>");

	private static final Pattern ROUTE_REGISTERED_REGEX = Pattern.compile("Route registered for service \"(.*?)\"");

	public static final int HANDSHAKE_SENDED = 3;

	public static final int HANDSHAKED = 4;

	/**
	 * Reférence sur le nom du comosant
	 */
	private static String mName;

	/**
	 * Référence sur la socket.
	 */
	private final SocketChannel mSocket;

	/**
	 * Buffer pour les donnees (en entree)
	 */
	private final ByteBuffer mInputBuffer;

	/**
	 * Mot de passe
	 */
	private final String mPassword;

	/**
	 * Taille du buffer.
	 */
	private static final int BUFFER_SIZE = 1024;

	/**
	 * Donnees non traitees.
	 */
	private String mRemaining;

	private int mCurrentState;

	/**
	 * Constructeur à partir du domaine, du serveur et du port.
	 * 
	 * @throws IOException En cas de problème I/O.
	 * @throws UnknownHostException Si le serveur n'existe pas.
	 */
	JabberLightComponent(String inName, String inPassword, String inHost, int inPort) throws UnknownHostException, IOException {
		this(inName, inPassword, inHost, inPort, null);
	}

	JabberLightComponent(String inName, String inPassword, String inHost, int inPort, JabberLightComponentHandler inPacketHandler) throws UnknownHostException, IOException {
		this.mSocket = SocketChannel.open(new InetSocketAddress(inHost, inPort));
		this.mSocket.socket().setSoTimeout(0);
		this.mSocket.configureBlocking(false);
		JabberLightComponent.mName = inName;
		this.mPassword = inPassword;
		this.mInputBuffer = ByteBuffer.allocate(JabberLightComponent.BUFFER_SIZE);
		write("<?xml version='1.0'?>");
		writeStreamHeader();
		this.mCurrentState = JabberLightComponent.WAITING_FOR_ID;
	}

	/**
	 * Envoie des données sur la socket.
	 */
	void write(String inData) throws IOException {
		final ByteBuffer theOutputBuffer = ByteBuffer.allocate(inData.length());
		System.out.println("[" + JabberLightComponent.mName + "] C: " + inData);
		theOutputBuffer.put(inData.getBytes("UTF-8"));
		theOutputBuffer.rewind();
		this.mSocket.write(theOutputBuffer);
	}

	/**
	 * Construit l'entête du flux.
	 * 
	 * @throws IOException
	 */
	private void writeStreamHeader() throws IOException {
		write("<stream:stream " + "xmlns='jabber:component:accept' " + "xmlns:stream='http://etherx.jabber.org/streams' " + "to='" + JabberLightComponent.mName + "' " + "version='1.0'>");
	}

	public int getState() {
		return this.mCurrentState;
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
			String theId = null;
			Matcher theMatcher;

			// Analyse de la ligne.
			switch (this.mCurrentState) {
			case WAITING_OPEN_STREAM:
				theMatcher = JabberLightComponent.OPEN_STREAM_XML_REGEX.matcher(theString);
				if (theMatcher.find()) {
					theEnd = theMatcher.end();
					this.mCurrentState = JabberLightComponent.WAITING_FOR_ID;
				} else {
					theEnd = 0;
					if (remaining) {
						throw new IllegalArgumentException("[" + JabberLightComponent.mName + "] Unattended packet : " + theString + "\nAttended : " + theMatcher.toString());
					}
				}
				break;
			case WAITING_FOR_ID:
				theMatcher = JabberLightComponent.RESPONSE_STREAM_XML_REGEX.matcher(theString);
				if (theMatcher.find()) {
					theId = theMatcher.group(1);
					System.out.println("theId : " + theId);
					theEnd = theMatcher.end();
					final String theHandshake = DigestTools.digest(theId + this.mPassword, DigestTools.Algorithm.SHA1);
					write("<handshake>" + theHandshake + "</handshake>");
					this.mCurrentState = JabberLightComponent.HANDSHAKE_SENDED;
				} else {
					theEnd = 0;
					if (remaining) {
						throw new IllegalArgumentException("[" + JabberLightComponent.mName + "] Unattended packet : " + theString + "\nAttended : " + theMatcher.toString());
					}
				}
				break;
			case HANDSHAKE_SENDED:
				theMatcher = JabberLightComponent.ROUTE_REGISTERED_REGEX.matcher(theString);
				if (theMatcher.find()) {
					theEnd = theMatcher.end();
					this.mCurrentState = JabberLightComponent.HANDSHAKED;
				} else {
					theEnd = 0;
					if (remaining) {
						throw new IllegalArgumentException("[" + JabberLightComponent.mName + "] Unattended packet : " + theString + "\nAttended : " + theMatcher.toString());
					}
				}
				break;
			case HANDSHAKED:
				System.out.println("The component named " + JabberLightComponent.mName + " is now connected to jabber's server");
				/*
				 * theMatcher = IQ_REGEX.matcher(theString); if
				 * (theMatcher.find()) { final String theType =
				 * theMatcher.group(2); if (theType.equals("set")) {
				 * write(theString.replaceAll("to='.*?'", "to='" +
				 * theMatcher.group(5) + "'").replaceAll("from='.*?'", "from='"
				 * + theMatcher.group(4) + "'").replaceAll("type='.*?'",
				 * "type='result'").replaceAll(">.*", "/>").replaceAll("//>",
				 * "/>")); } else if (theType.equals("get")) {
				 * write(theString.replaceAll("to='.*?'", "to='" +
				 * theMatcher.group(5) + "'").replaceAll("from='.*?'", "from='"
				 * + theMatcher.group(4) + "'").replaceAll("type='.*?'",
				 * "type='error'").replaceAll(">.*", "/>").replaceAll("//>",
				 * "/>")); } } if (mPacketHandler != null) {
				 * mPacketHandler.handleReceivedPacket(this, theString); }
				 */
				theEnd = 0;
				break;
			default:
				JabberLightComponent.LOGGER.fatal(new IllegalArgumentException("[FATAL][" + JabberLightComponent.mName + "] Unattended packet in default state : " + theString));
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
			JabberLightComponent.LOGGER.warn(e);
		}
	}

	public SocketChannel getSocket() {
		return this.mSocket;
	}

}

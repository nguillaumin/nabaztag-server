package net.violet.platform.xmpp.vo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.message.Message;
import net.violet.platform.message.Sequence;
import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.JabberClient;
import net.violet.platform.xmpp.packet.IQSources;
import net.violet.platform.xmpp.packet.PingPacket;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;

/**
 * Classe pour un lapin virtuel, qui vérifie les services qu'il reçoit du
 * serveur.
 */
class VirtualXMPPObject implements PacketListener {


	private static final Logger LOGGER = Logger.getLogger(VirtualXMPPObject.class);

	private static final String BOOT_RESSSOURCE = "boot";

	/**
	 * Mot de passe des objets virtuels.
	 */
	private static final String JABBER_PASSWORD = "violet";
	private static final Pattern mContentPattern = Pattern.compile(".*/config/([\\w\\/]+).mp3$|^http://(.*)");
	private static final Pattern mSignaturePattern = Pattern.compile(".*/config/([\\w]+[^\\/])/.*");
	private static final String OUTPUT_ROOT_FOLDER = "/var/run/virtual_objects/";

	private static void sendConnectionMessage(JabberClient inJabberClient, String inSerial, String inXmppDomain) {
		VirtualXMPPObject.LOGGER.info("Connected");
		inJabberClient.sendPacket("<iq from=\"" + inSerial + "@" + inXmppDomain + "/" + VirtualXMPPObject.BOOT_RESSSOURCE + "\" to=\"net.violet.platform@" + inXmppDomain + "/sources\" type=\"get\" id=\"source-1\"><query xmlns=\"violet:iq:sources\"><packet xmlns=\"violet:packet\" format=\"1.0\"/></query></iq>");
	}

	private final XMPPConnection mConnection;
	private final JabberClient mJabberClient;
	private final String mOutputFolder;
	private final List<String> mXmppHost = Constantes.XMPP_SERVER_LIST;
	private final String mSerial;
	private final String mXmppDomain = Constantes.XMPP_NABAZTAG_DOMAIN;
	private final Integer mXmppPort = Constantes.XMPP_SERVER_PORT;

	private String mResource = VirtualXMPPObject.BOOT_RESSSOURCE;

	/**
	 * Constructeur à partir d'un numéro de série.
	 * 
	 * @param inSerial numéro de série du lapin
	 * @throws XMPPException
	 */
	VirtualXMPPObject(String inSerial) throws XMPPException {
		this.mSerial = inSerial;
		this.mJabberClient = new JabberClient(this.mXmppHost, this.mXmppPort, this.mSerial, VirtualXMPPObject.JABBER_PASSWORD, this.mResource) {

			@Override
			public void reconnectionSuccessful() {
				super.reconnectionSuccessful();
				VirtualXMPPObject.sendConnectionMessage(VirtualXMPPObject.this.mJabberClient, VirtualXMPPObject.this.mSerial, VirtualXMPPObject.this.mXmppDomain);
			}
		};
		this.mJabberClient.addPacketListener(this, null);
		this.mConnection = this.mJabberClient.getXMPPConnection();
		if (!this.mConnection.isAuthenticated()) {
			// Cas où on n'a pas réussi à s'authentifier (compte inexistant?)
			final AccountManager mAccountManager = this.mConnection.getAccountManager();
			if (mAccountManager.supportsAccountCreation()) {
				VirtualXMPPObject.LOGGER.debug("Creating account on jabber server");
				mAccountManager.createAccount(this.mSerial, VirtualXMPPObject.JABBER_PASSWORD);
			}
			this.mConnection.disconnect();
			this.mConnection.connect();
			if (!this.mConnection.isAuthenticated()) {
				throw new XMPPException("Couldn't register on the jabber server");
			}
		}
		VirtualXMPPObject.sendConnectionMessage(this.mJabberClient, this.mSerial, this.mXmppDomain);
		final VObject theObject = Factories.VOBJECT.findBySerial(inSerial);
		this.mOutputFolder = VirtualXMPPObject.OUTPUT_ROOT_FOLDER + theObject.getObject_login() + "/";
		final File theOutputFolder = new File(this.mOutputFolder);
		theOutputFolder.mkdirs();
	}

	private void analyze(String inSignature, List<String> inFilesToRead) {

		if (inSignature != null) {
			final Matcher theSignatureMatcher = VirtualXMPPObject.mSignaturePattern.matcher(Matcher.quoteReplacement(inSignature));
			VirtualXMPPObject.LOGGER.info("[" + this.mSerial + "] sign = " + inSignature);

			if (theSignatureMatcher.matches()) {
				try {
					final FileWriter theWriter = new FileWriter(new File(this.mOutputFolder + theSignatureMatcher.group(1) + ".time"));
					theWriter.write(String.valueOf(Calendar.getInstance().getTimeInMillis() / 1000));
					theWriter.write('\n');
					theWriter.close();
				} catch (final IOException e) {
					VirtualXMPPObject.LOGGER.fatal(e, e);
				}
			}
		}

		for (final String aPath2mp3 : inFilesToRead) {
			VirtualXMPPObject.LOGGER.info("[" + this.mSerial + "] path2mp3 = " + aPath2mp3);
			final Matcher theContentMatcher = VirtualXMPPObject.mContentPattern.matcher(Matcher.quoteReplacement(aPath2mp3));
			if (theContentMatcher.matches()) {
				try {
					String value = theContentMatcher.group(1);
					if (value == null) {
						value = theContentMatcher.group(2);
					}

					final FileWriter theWriter = new FileWriter(new File(this.mOutputFolder + value.replace('/', '_') + ".time"));
					theWriter.write(String.valueOf(Calendar.getInstance().getTimeInMillis() / 1000));
					theWriter.write('\n');
					theWriter.close();
				} catch (final IOException e) {
					VirtualXMPPObject.LOGGER.fatal(e, e);
				}
			}

		}
	}

	private void changeResource(String inNewResource) {
		if ((this.mResource == null) || (!this.mResource.equals(inNewResource))) {
			// bind
			this.mJabberClient.sendPacket("<iq from=\"" + this.mSerial + "@" + this.mXmppDomain + "/" + inNewResource + "\" type='set' id='bind" + System.currentTimeMillis() + "'>" + "<bind xmlns='urn:ietf:params:xml:ns:xmpp-bind'>" + "<resource>" + inNewResource + "</resource></bind></iq>");
			if (this.mResource != null) {
				// unbind
				this.mJabberClient.sendPacket("<iq from=\"" + this.mSerial + "@" + this.mXmppDomain + "/" + this.mResource + "\" type='set' id='unbind" + System.currentTimeMillis() + "'>" + "<unbind xmlns='urn:ietf:params:xml:ns:xmpp-bind'>" + "<resource>" + this.mResource + "</resource></unbind></iq>");
			}
			this.mResource = inNewResource;
			// Ouverture de la nouvelle session.
			this.mJabberClient.sendPacket("<iq from=\"" + this.mSerial + "@" + this.mXmppDomain + "/" + this.mResource + "\" to='" + this.mXmppDomain + "' type='set' id='session-" + System.currentTimeMillis() + "'>" + "<session xmlns='urn:ietf:params:xml:ns:xmpp-session'/>" + "</iq>");
			// Envoi de la presence
			this.mJabberClient.sendPacket("<presence from=\"" + this.mSerial + "@" + this.mXmppDomain + "/" + this.mResource + "\" />");
		}

	}

	/**
	 * Déconnexion.
	 */
	void disconnect() {
		this.mJabberClient.close();
	}

	private void processMessage(Message inMessage) {

		final Integer modeId = inMessage.getStatus();
		if (modeId != null) {
			final Message.MODE theMode = Message.MODE.findById(modeId);
			final String theStatus;
			if (theMode == Message.MODE.VEILLE) {
				theStatus = "asleep";
			} else {
				theStatus = "idle";
			}
			changeResource(theStatus);
		}

		processSequenceList(inMessage);
	}

	public void processPacket(Packet inPacket) {
		try {
			boolean parseMessage = true;
			PingPacket myPingPacket = null;
			if (inPacket instanceof IQSources) {
				final IQSources myIQ = (IQSources) inPacket;
				if (myIQ.getType() == IQ.Type.RESULT) {
					myPingPacket = myIQ.getPingPacket();
				} else if (myIQ.getType() == IQ.Type.SET) {
					this.mJabberClient.sendPacket("<iq from=\"" + this.mSerial + "@" + this.mXmppDomain + "/" + this.mResource + "\" to='" + myIQ.getFrom() + "' type='result' id='" + myIQ.getPacketID() + "' />");
				} else if (myIQ.getType() == IQ.Type.GET) {
					final String from = myIQ.getFrom();
					myIQ.setFrom(myIQ.getTo());
					myIQ.setTo(from);
					myIQ.setType(IQ.Type.ERROR);
					this.mJabberClient.sendPacket(myIQ);
				}
			} else if (inPacket instanceof org.jivesoftware.smack.packet.Message) {
				myPingPacket = (PingPacket) inPacket.getExtension(PingPacket.NAMESPACE);
			} else {
				parseMessage = false;
			}

			if (parseMessage && (myPingPacket != null)) {
				final Message theMessage = myPingPacket.getMessage();
				if (theMessage != null) {
					processMessage(theMessage);
				}
			}
		} catch (final Exception e) {
			VirtualXMPPObject.LOGGER.fatal(e, e);
		}
	}

	private String processSequenceList(Message inMessage) {
		final StringBuilder myReturn = new StringBuilder();

		/* Trame 10 : Bytecode v2 */
		final List<Sequence> mySequences = inMessage.getSequenceList();
		if ((mySequences != null) && !mySequences.isEmpty()) {
			String theSignature = null;
			final List<String> myMp3ToRead = new LinkedList<String>();
			for (final Sequence mySeq : mySequences) {
				final Integer mySeqType = mySeq.getType();
				final String mySeqData = mySeq.getData();

				if ((mySeqType == Sequence.SEQ_MUSICSIGN) && (theSignature == null)) {
					theSignature = mySeqData;
				} else if ((mySeqType == Sequence.SEQ_MUSICDOWNLOAD) || (mySeqType == Sequence.SEQ_MUSIC_STREAMING) || (mySeqType == Sequence.SEQ_MUSICSHOULDSTREAM)) {
					myMp3ToRead.add(mySeqData);
				}
			}

			analyze(theSignature, myMp3ToRead);

			// si le chemin commence par broadcast/broad/config, Ecrire
			// chemin*.time avec chemin* = chemin où / est remplacé par _ et
			// sans .mp3 et sans broadcast/broad/config/
		}
		return myReturn.toString();
	}

}

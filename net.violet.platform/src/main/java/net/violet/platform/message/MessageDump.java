package net.violet.platform.message;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.MessageCounter.RABBIT_STATE;
import net.violet.platform.message.elements.SequencePart;
import net.violet.platform.util.ConvertTools;
import net.violet.platform.util.LibBasic;
import net.violet.platform.util.StringShop;

/**
 * Classe pour stocker les differents éléments d'un message
 */
public class MessageDump extends AbstractMessage {

	/* Stockage des informations supplémentaires */
	private Integer mHeader;
	private final Map<Integer, byte[]> mFrame = new HashMap<Integer, byte[]>();
	private Integer mSourceType;
	private String[] mBytecodeV2;
	private final List<Sequence> mSequenceList = new ArrayList<Sequence>();

	/* Constantes */
	private static final String[] mFrameType = { "Unknown", "Unknown", "Unknown", "Timeout", "Source", "Bytecode v1", "Kill", "Unknown", "Unknown", "Reboot", "Bytecode v2", "Mode" };

	static final Map<Integer, String> dicoSequence = new HashMap<Integer, String>();

	static {
		MessageDump.dicoSequence.put(Sequence.SEQ_MUSICSIGN, "MC");
		MessageDump.dicoSequence.put(Sequence.SEQ_MUSICDOWNLOAD, "MU");
		MessageDump.dicoSequence.put(Sequence.SEQ_MUSIC_STREAMING, "MS");
		MessageDump.dicoSequence.put(Sequence.SEQ_MUSICSHOULDSTREAM, "ST");
		MessageDump.dicoSequence.put(Sequence.SEQ_SECURESTREAMING, "SP");
		MessageDump.dicoSequence.put(Sequence.SEQ_BEGIN_INTERACTIVE_MODE, "IS");
		MessageDump.dicoSequence.put(Sequence.SEQ_END_INTERACTIVE_MODE, "IE");
		MessageDump.dicoSequence.put(Sequence.SEQ_SETTING, "IC");
		MessageDump.dicoSequence.put(Sequence.SEQ_CHORDOWNLOAD, "CH");
		MessageDump.dicoSequence.put(Sequence.SEQ_PALETTE, "PL");
		MessageDump.dicoSequence.put(Sequence.SEQ_COLOR, "CL");
	}

	/**
	 * Initialisation à partir des données binaires brutes
	 * 
	 * @param inSource : la paquet binaire envoyé à un lapin
	 */
	public MessageDump() {
		this.mBytecodeV2 = null;
	}

	public void setHeader(Integer inHeader) {
		this.mHeader = inHeader;
	}

	/**
	 * Défini que la source a été traité de facon normale.
	 */
	public void setMessageFinalized() {
	}

	/**
	 * Ajoute une frame. La fonction identifie les paquets reconnus (bytecodeV2,
	 * source), et les traites
	 */
	public final void setFrame(Integer inFrameType, byte[] inFrameSrc) throws IllegalArgumentException {
		this.mFrame.put(inFrameType, inFrameSrc);
		if (MessageDump.frameTypeToString(inFrameType) == null) {
			throw new IllegalArgumentException("Unknown frame type " + inFrameType + " length " + inFrameSrc.length + "\n-- Dump Hexa --\n" + MessageDump.dumpHexa(inFrameSrc));
		}
	}

	/**
	 * Récupérer les frames
	 * 
	 * @return Une Map<Integer, byte[]> où la clé est le type de frame, et la
	 *         valeur, la frame en elle meme
	 */
	private Map<Integer, byte[]> getFrame() {
		return this.mFrame;
	}

	/**
	 * Récupérer la frame demandée ou retourne null.
	 * 
	 * @return Un byte[] représentant la frame en elle meme
	 */
	public byte[] getFrame(Integer inFrameType) {
		return this.mFrame.get(inFrameType);
	}

	/**
	 * @return Le header
	 */
	private Integer getHeader() {
		return this.mHeader;
	}

	/**
	 * Decrypte le bytecodeV2 pour y extraire la priorité et les différentes
	 * actions qui le compose.
	 * 
	 * @param inSrcBytecode : Le paquet correspondant au bytecodeV2
	 */
	public void dumpBytecodeV2(byte[] inSrcBytecode) {
		final byte[] theBytes = new byte[inSrcBytecode.length - 1];
		System.arraycopy(inSrcBytecode, 1, theBytes, 0, theBytes.length);
		final String uncryptedBytecode = LibBasic.uncrypt8(theBytes, 0x47, 47);
		this.mBytecodeV2 = uncryptedBytecode.split("\n");

		for (final String line : this.mBytecodeV2) {
			final String[] lineArgs = line.split(StringShop.SPACE, 2);
			String sequence;
			String command;
			if (lineArgs.length == 2) {
				sequence = lineArgs[0];
				command = lineArgs[1];
				if (lineArgs[0].equals("ID")) {
					sequence = null; /* L'element ID n'est pas une sequence. */
					setEventID((long) ConvertTools.atoi(lineArgs[1]));
					if (!lineArgs[1].equals(((Integer) Message.EV_ASLEEP).toString()) || (!lineArgs[1].equals(((Integer) Message.EV_DUMMY).toString()))) {
						setMessageID((long) ConvertTools.atoi(lineArgs[1]));
					}
				}
			} else if (lineArgs.length == 1) {
				sequence = lineArgs[0];
				command = null;
			} else {
				sequence = null;
				command = null;
			}
			if (sequence != null) {
				final SequenceImpl tmpSeq = new SequenceImpl(sequence, command);
				if (tmpSeq.getType() != -1) {
					this.mSequenceList.add(tmpSeq);
				}
			}
		}
	}

	/**
	 * Décrypte la source pour en extraire le type de message, la position des
	 * oreilles, le nombre de message, et les services.
	 * 
	 * @param inSrc : Le paquet correspondant aux sources
	 * @throws IOException
	 * @throws EOFException
	 */
	public void dumpSource(byte[] inSrc) throws IOException, EOFException, IllegalArgumentException {
		final InputStream myFrame = new ByteArrayInputStream(inSrc);
		this.mSourceType = LibBasic.bin4toi(myFrame);
		setSourceUpdate(this.mSourceType == Message.ENTETE_MAJ_TRAME_4);
		if (this.mSourceType == -1) {
			new EOFException();
		} else if ((this.mSourceType != Message.EV_DUMMY) && (this.mSourceType != Message.EV_ASLEEP)) {
			throw new IllegalArgumentException("MessageType: Unknown (" + Integer.toHexString(this.mSourceType) + ")\nDump Hexa :\n" + MessageDump.dumpHexa(inSrc));
		}
		int maxSource = 8;
		if (this.mSourceType == Message.ENTETE_MAJ_TRAME_4) {
			maxSource = (inSrc.length - 4) / 2;
		}
		for (int i = 0; i < maxSource; i++) {
			final int srvtp = myFrame.read();
			final int srvval = myFrame.read();
			if ((srvtp == -1) || (srvval == -1)) {
				throw new EOFException();
			}
			if (srvtp > 0) {
				final SOURCE theSource = SOURCE.findById(srvtp);
				setSourceValue(theSource, srvval);
				if (theSource == SOURCE.LEFT_EAR) {
					setLeftEarPosition(srvval);
				} else if (theSource == SOURCE.RIGHT_EAR) {
					setRightEarPosition(srvval);
				} else if (theSource == SOURCE.MESSAGE) {
					setNbMessages(RABBIT_STATE.getState(srvval));
				}
			}
		}

		if (this.mSourceType != Message.ENTETE_MAJ_TRAME_4) {
			int earPosLeft;
			int earPosRight;
			earPosLeft = myFrame.read();
			earPosRight = myFrame.read();
			if ((earPosLeft == -1) || (earPosRight == -1)) {
				throw new EOFException();
			}
			setEars(earPosLeft, earPosRight);
			int readChar;
			int nbMessages = 0;
			do {
				readChar = myFrame.read();
				if (readChar == -1) {
					throw new EOFException();
				} else if (readChar == 5) {
					nbMessages++;
				}
			} while (readChar != 0);
			setNbMessages(RABBIT_STATE.getState(nbMessages));
		}
	}

	public static String dumpHexa(byte[] theBytes) {
		int theIndex;
		final StringBuilder output = new StringBuilder(StringShop.EMPTY_STRING);
		for (theIndex = 0; theIndex < theBytes.length; theIndex++) {
			if (theIndex % 16 == 0) {
				if (theIndex < 16) {
					output.append("0");
				}
				output.append(Integer.toString(theIndex, 16).toUpperCase() + StringShop.SPACE);
			}
			int theByte = theBytes[theIndex];
			if (theByte < 0) {
				theByte += 256;
			}
			if (theByte < 16) {
				output.append("0");
			}
			output.append(Integer.toString(theByte, 16).toUpperCase());
			if ((theIndex + 1) % 16 == 0) {
				output.append("\n");
			}
		}
		if (theIndex % 16 > 0) {
			output.append("\n");
		}
		return output.toString();
	}

	public List<Sequence> getSequenceList() {
		return this.mSequenceList;
	}

	private static String frameTypeToString(Integer inType) {
		if (inType < MessageDump.mFrameType.length) {
			return MessageDump.mFrameType[inType];
		}
		return null;
	}

	private static String sourceSrvToString(int inType) {

		final Message.SOURCE theSource = Message.SOURCE.findById(inType);
		if (theSource == null) {
			return "Unknown";
		}

		return theSource.getDumpValue();
	}

	public static Integer stringToSequence(String inSeqType) {
		for (final Integer theKey : MessageDump.dicoSequence.keySet()) {
			final String strSeqType = MessageDump.dicoSequence.get(theKey);
			if ((strSeqType != null) && (strSeqType.equals(inSeqType))) {
				return theKey;
			}
		}
		return -1;
	}

	@Override
	public String toString() {
		final StringBuilder srvTypeStr = new StringBuilder("\nXMPP Object: ");
		srvTypeStr.append("\nheader: ").append(getHeader());
		srvTypeStr.append("\neventID: ").append(getEventID());
		srvTypeStr.append("\ntitle: ").append(getTitle());
		srvTypeStr.append("\nstatus: ").append(getStatus());
		srvTypeStr.append("\nleft ear position: ").append(getLeftEarPosition());
		srvTypeStr.append("\nright ear position: ").append(getRightEarPosition());
		srvTypeStr.append("\nnb messages: ").append(getNbMessages());
		srvTypeStr.append("\nsender: ").append(getSender());

		for (final Integer myKey : getFrame().keySet()) {
			srvTypeStr.append("\n" + myKey + ":\t");
			srvTypeStr.append(MessageDump.sourceSrvToString(myKey));
			srvTypeStr.append("  \t" + getFrame().get(myKey) + ":\t");

			for (final byte b : getFrame().get(myKey)) {
				srvTypeStr.append(Integer.toHexString(b).toUpperCase()).append(StringShop.SPACE);
			}
		}
		return srvTypeStr + "\n";
	}

	public List<SequencePart> getSequencePart() {
		throw new UnsupportedOperationException();
	}
}

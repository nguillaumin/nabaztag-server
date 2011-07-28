package net.violet.platform.message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.MessageCounter.RABBIT_STATE;
import net.violet.platform.util.ConvertTools;
import net.violet.platform.util.LibBasic;
import net.violet.platform.util.StringShop;
import net.violet.platform.vasm.Vasm;
import net.violet.platform.vasm.VasmException;
import net.violet.platform.vasm.Vasm.VASM_FILE;

import org.apache.log4j.Logger;

/**
 * Classe utilitaire pour sérialiser les messages.
 */
public final class MessageSerializer {

	private static final Logger LOGGER = Logger.getLogger(MessageSerializer.class);

	private static final byte PACKET_HEADER = 0x7F;
	private static final byte TIMEOUT_FRAME_ID = 3;
	private static final byte SOURCE_FRAME_ID = 4;
	private static final byte KILL_FRAME_ID = 6;
	private static final byte REBOOT_FRAME_ID = 9;
	private static final byte PACKET_FOOTER = -1;

	/**
	 * Paquet de reboot.
	 */
	public static final byte[] REBOOT_PACKET = new byte[] { MessageSerializer.PACKET_HEADER, MessageSerializer.REBOOT_FRAME_ID, 0, 0, 1, // reboot frame size
			0, // value = 0
			MessageSerializer.PACKET_FOOTER };

	/**
	 * Génère le paquet pour le ping.
	 * 
	 * @param inPriority
	 *            priorité du message.
	 * @param inFirmwareVersion
	 *            numéro de version du firmware
	 * @param inHardware
	 *            type d'hardware
	 * @param inToKill
	 *            si le message (précédent?) doit être supprimé.
	 * @param inLastPing
	 *            date du dernier ping.
	 * @return le paquet pour le ping.
	 */
	public static byte[] generatePingPacket(Message inMessage, int inPriority, String inFirmwareVersion, int inToKill, HARDWARE inHardware) {
		final ByteArrayOutputStream theResultBuffer = new ByteArrayOutputStream();
		theResultBuffer.write(MessageSerializer.PACKET_HEADER);
		final VObject theReceiver = inMessage.getReceiver();

		final Long theEventIDObj = inMessage.getEventID();
		long theEventID = 0;
		if (theEventIDObj != null) {
			theEventID = theEventIDObj.longValue();
		}

		// priority = 1 : bytecode+kill+timeout+src+status+fin
		// priority != 1: src+status+bytecode+kill+timeout+fin
		if (inPriority == 1) {
			if (theEventID != 0) {
				MessageSerializer.computeBytecode(theResultBuffer, inMessage, theEventID, inPriority, inHardware);
			}
			MessageSerializer.computeKill(theResultBuffer, inToKill);
			if (theReceiver != null) {
				MessageSerializer.computetimeout(theResultBuffer, inMessage, inFirmwareVersion);
			}
			MessageSerializer.computeSources(theResultBuffer, inMessage);
		} else {
			MessageSerializer.computeSources(theResultBuffer, inMessage);
			if (theEventID != 0) {
				MessageSerializer.computeBytecode(theResultBuffer, inMessage, theEventID, inPriority, inHardware);
			}
			MessageSerializer.computeKill(theResultBuffer, inToKill);
			if (theReceiver != null) {
				MessageSerializer.computetimeout(theResultBuffer, inMessage, inFirmwareVersion);
			}
		}

		theResultBuffer.write(MessageSerializer.PACKET_FOOTER);

		return theResultBuffer.toByteArray();
	}

	private static void computeSources(ByteArrayOutputStream inResultBuffer, Message inMessage) {
		if (inMessage.isSourceModeUpdate()) {
			MessageSerializer.computeSourcesUpdate(inResultBuffer, inMessage);
		} else {
			MessageSerializer.computeSourcesOld(inResultBuffer, inMessage);
		}
	}

	private static void computeSourcesOld(ByteArrayOutputStream inResultBuffer, Message inMessage) {
		// On ne génère la source que si on a quelque chose à envoyer.
		final Map<String, Integer> theSources = inMessage.getSources();
		final VObject theReceiver = inMessage.getReceiver();
		final Integer theLeftEarPosition = inMessage.getLeftEarPosition();
		final Integer theRightEarPosition = inMessage.getRightEarPosition();
		final RABBIT_STATE theNbMessages = inMessage.getNbMessages();

		if ((theSources != null) || (theLeftEarPosition != null) || (theRightEarPosition != null) || (theNbMessages != null)) {
			final ByteArrayOutputStream theSourceFrameBuffer = new ByteArrayOutputStream();
			LibBasic.writeIntTo4Bytes(theSourceFrameBuffer, Message.EV_DUMMY);
			int ear_left;
			int ear_right;
			if (theLeftEarPosition != null) {
				ear_left = theLeftEarPosition;
			} else {
				ear_left = theReceiver.getObject_left();
			}
			if (theRightEarPosition != null) {
				ear_right = theRightEarPosition;
			} else {
				ear_right = theReceiver.getObject_right();
			}

			int i = 0;
			int srvtp = 0;
			int srvval = 0;

			if (theSources != null) {
				for (final Map.Entry<String, Integer> source : theSources.entrySet()) {
					try {
						// FIXME a temporary solution which should be fixed
						int newsrvtp = Integer.parseInt(source.getKey());
						final int newsrvval = source.getValue();
						// Traduction bourse full -> bourse
						if (newsrvtp == 20) {
							newsrvtp = 2;
						}
						if (newsrvtp == srvtp) {
							srvval += newsrvval;
						} else {
							if (srvtp != 0) {
								i++;
								srvval = MessageSerializer.filtersrcval(srvtp, srvval);
								theSourceFrameBuffer.write(srvtp);
								theSourceFrameBuffer.write(srvval);
							}
							srvtp = newsrvtp;
							srvval = newsrvval;
						}
					} catch (final NumberFormatException e) {
						MessageSerializer.LOGGER.fatal(e, e);
					}
				}
			}
			if (srvtp != 0) {
				i++;
				srvval = MessageSerializer.filtersrcval(srvtp, srvval);
				theSourceFrameBuffer.write(srvtp);
				theSourceFrameBuffer.write(srvval);
			}
			while (i < 8) {
				i++;
				theSourceFrameBuffer.write(0);
				theSourceFrameBuffer.write(0);
			}
			// ajout des oreilles
			theSourceFrameBuffer.write(ear_left);
			theSourceFrameBuffer.write(ear_right);

			if ((theNbMessages != null) && (theNbMessages != RABBIT_STATE.INVALIDE)) {
				final int nbMessages = theNbMessages.getValue();
				if (nbMessages > 0) {
					theSourceFrameBuffer.write(5);
					if (nbMessages > 1) {
						theSourceFrameBuffer.write(5);
					}
				}
			}
			theSourceFrameBuffer.write(0); // fin des messages

			inResultBuffer.write(MessageSerializer.SOURCE_FRAME_ID);
			LibBasic.writeIntTo3Bytes(inResultBuffer, theSourceFrameBuffer.size());
			try {
				theSourceFrameBuffer.writeTo(inResultBuffer);
			} catch (final IOException anException) {
				MessageSerializer.LOGGER.fatal(anException, anException);
			}
		}
	}

	private static void computeSourcesUpdate(ByteArrayOutputStream inResultBuffer, Message inMessage) {
		// On ne génère la source que si on a quelque chose à envoyer.
		final Map<String, Integer> theSources = inMessage.getSources();
		final Integer theLeftEarPosition = inMessage.getLeftEarPosition();
		final Integer theRightEarPosition = inMessage.getRightEarPosition();
		final RABBIT_STATE theNbMessages = inMessage.getNbMessages();

		if ((theSources != null) || (theLeftEarPosition != null) || (theRightEarPosition != null) || (theNbMessages != null)) {
			final ByteArrayOutputStream theSourceFrameBuffer = new ByteArrayOutputStream();
			LibBasic.writeIntTo4Bytes(theSourceFrameBuffer, Message.ENTETE_MAJ_TRAME_4);

			// ajout des oreilles
			if (theLeftEarPosition != null) {
				theSourceFrameBuffer.write(Message.SOURCE.LEFT_EAR.getId());
				theSourceFrameBuffer.write(theLeftEarPosition);
			}
			if (theRightEarPosition != null) {
				theSourceFrameBuffer.write(Message.SOURCE.RIGHT_EAR.getId());
				theSourceFrameBuffer.write(theRightEarPosition);
			}

			// ajout des alertes passives
			if (theSources != null) {
				for (final Map.Entry<String, Integer> source : theSources.entrySet()) {
					try {
						// FIXME
						final int srvSrcId = MessageSerializer.filterSrcID(Integer.parseInt(source.getKey()));
						final int srvSrcValue = MessageSerializer.filtersrcval(srvSrcId, source.getValue());

						// Quand srvSrcId vaut 0, alors srvSrcValue vaut l'id de
						// la source a supprimé
						theSourceFrameBuffer.write(srvSrcId);
						theSourceFrameBuffer.write(srvSrcValue);
					} catch (final NumberFormatException e) {
						MessageSerializer.LOGGER.fatal(e, e);
					}
				}
			}

			// nombre de message en attente
			if ((theNbMessages != null) && (theNbMessages != RABBIT_STATE.INVALIDE)) {
				int nbMessages = theNbMessages.getValue();
				if (nbMessages > 2) {
					nbMessages = 2;
				}
				theSourceFrameBuffer.write(Message.SOURCE.MESSAGE.getId());
				theSourceFrameBuffer.write(nbMessages);
			}

			inResultBuffer.write(MessageSerializer.SOURCE_FRAME_ID);
			LibBasic.writeIntTo3Bytes(inResultBuffer, theSourceFrameBuffer.size());
			try {
				theSourceFrameBuffer.writeTo(inResultBuffer);
			} catch (final IOException anException) {
				MessageSerializer.LOGGER.fatal(anException, anException);
			}
		}
	}

	/**
	 * change la bourse advancé avec l'id 20 en id 2
	 * 
	 */
	private static int filterSrcID(int srvSrcId) {
		int result = srvSrcId;
		if (srvSrcId == Message.SOURCE.ADVANCED_TRADE.getId()) {
			result = Message.SOURCE.TRADE.getId();
		}

		return result;
	}

	private static ByteArrayOutputStream computeVasm(Message inMessage, int inEventID, int inPriority, VASM_FILE inVasmFile) throws VasmException {
		final String filename;
		String data = null;

		if (inMessage.getReceiver().getObject_mode() == 2) {
			filename = inVasmFile.getDemoFileName();
		} else {
			final Long theEventIDObj = inMessage.getEventID();

			if ((theEventIDObj != null) && (theEventIDObj == Message.EV_DUMMY)) {
				filename = inVasmFile.getWaitFileName();
			} else if ((theEventIDObj != null) && (theEventIDObj == Message.EV_ASLEEP)) {
				filename = inVasmFile.getSleepFileName();
			} else {

				data = MessageSerializer.generateV1Commands(inMessage);
				if (data == null) {
					data = net.violet.common.StringShop.EMPTY_STRING;
				}
				filename = inVasmFile.getMsgFileName();
			}
		}

		return Vasm.maketrame(filename, data, inEventID, inPriority, inVasmFile);
	}

	/**
	 * Génère la séquence (bytecode) pour un objet v1 et daldal.
	 * 
	 * @param inResultBuffer
	 *            buffer pour le résultat.
	 * @param inMessage
	 *            message.
	 * @param inEventID
	 *            id de l'événement.
	 * @param inPriority
	 *            priorité pour l'événement.
	 * @param inHardware
	 *            type d'hardware
	 * @return le texte d'erreur ou <code>null</code> s'il n'y a aucune erreur.
	 */
	private static void computeBytecode(ByteArrayOutputStream inResultBuffer, Message inMessage, long inEventID, int inPriority, HARDWARE inHardware) {
		try {
			final VASM_FILE theVasmFile = VASM_FILE.findByHardware(inHardware);
			try {
				final ByteArrayOutputStream tr = MessageSerializer.computeVasm(inMessage, (int) inEventID, inPriority, theVasmFile);
				tr.writeTo(inResultBuffer);
			} catch (final VasmException anException) {
				MessageSerializer.LOGGER.error(anException, anException);
				try {
					final ByteArrayOutputStream tr = Vasm.maketrame(theVasmFile.getBluePointFileName(), null, (int) inEventID, inPriority, theVasmFile);
					tr.writeTo(inResultBuffer);
				} catch (final VasmException aSecondException) {
					MessageSerializer.LOGGER.error(aSecondException, aSecondException);
				}
			}
		} catch (final IOException anException) {
			MessageSerializer.LOGGER.fatal(anException, anException);
		}
	}

	private static void computeKill(ByteArrayOutputStream inResultBuffer, int inToKill) {
		if (inToKill != 0) {
			inResultBuffer.write(MessageSerializer.KILL_FRAME_ID);
			LibBasic.writeIntTo3Bytes(inResultBuffer, 4);
			LibBasic.writeIntTo4Bytes(inResultBuffer, inToKill);
		}
	}

	/**
	 * Détermine le temps du prochain ping. Appelé uniquement si on a réussi à
	 * récupérer le lapin.
	 */
	private static void appendTimeout(ByteArrayOutputStream inResultBuffer, int inTimeout) {
		MessageSerializer.appendSingleByteFrame(inResultBuffer, MessageSerializer.TIMEOUT_FRAME_ID, inTimeout);
	}

	/**
	 * Détermine le temps du prochain ping. Appelé uniquement si on a réussi à
	 * récupérer le lapin.
	 */
	private static void appendSingleByteFrame(ByteArrayOutputStream inResultBuffer, byte inFrameId, int inValue) {
		inResultBuffer.write(inFrameId);
		LibBasic.writeIntTo3Bytes(inResultBuffer, 1);
		inResultBuffer.write(inValue);
	}

	/**
	 * Détermine le temps du prochain ping. Appelé uniquement si on a réussi à
	 * récupérer le lapin.
	 */
	private static void computetimeout(ByteArrayOutputStream inResultBuffer, Message inMessage, String inFirmwareVersion) {
		int val;
		final VObject theReceiver = inMessage.getReceiver();

		// Dans le cas des V1:
		// - s'ils sont en mode demo ou mode FT, on a un cas particulier
		// - sinon, on gère en fonction de la valeur du firmware.
		val = 24;
		final int mode = theReceiver.getObject_mode();
		final int theReceiverDelay = theReceiver.getObject_delay();
		if ((mode == 1) || (mode == 2)) {
			val = 6; // old val=1
		} else if (inFirmwareVersion != null) {
			// firmware 14 & 20: facteur 10
			// firmware 12: facteur 5, max 30s.
			if ("14".equals(inFirmwareVersion)) {
				val = theReceiverDelay / 10;
			} else if ("20".equals(inFirmwareVersion)) {
				val = theReceiverDelay / 10;
			} else if ("12".equals(inFirmwareVersion)) {
				val = theReceiverDelay / 5;
				if (val > 6) {
					val = 6;
				}
			}
		}

		MessageSerializer.appendTimeout(inResultBuffer, val);
	}

	/**
	 * Filtre la valeur des sources.
	 * 
	 * @param srvtp
	 *            le numéro de la source;
	 * @param val
	 *            la valeur (initiale) de la source.
	 * @return la valeur (filtrée) de la source.
	 */
	private static int filtersrcval(int srvtp, int val) {
		if (srvtp == Message.SOURCE.MAIL.getId()) {
			if (val > 3) {
				return 3;
			}
			if (val < 0) {
				return 0;
			}
		}
		return val;
	}

	private static final Pattern EXTENSION_PATTERN = Pattern.compile(".+(\\.(?:mp3|mid))$", Pattern.CASE_INSENSITIVE);

	/**
	 * Génère la séquence pour un lapin v1.
	 * 
	 * @return <code>null</code> en cas d'erreur, la chorégraphie pour
	 *         l'intérprête de chorégraphie dans le cas contraire.
	 */
	private static String generateV1Commands(Message inMessage) {
		final int i = 100;
		int n = 0;
		boolean music = false;
		final StringBuilder out = new StringBuilder();
		final List<Sequence> theSequenceList = inMessage.getSequenceList();
		if (theSequenceList != null) {
			for (final Sequence theSequence : theSequenceList) {
				n++;
				final int type = theSequence.getType();
				String val = theSequence.getData();

				switch (type) {
				case Sequence.SEQ_MUSICDOWNLOAD:
				case Sequence.SEQ_MUSICSHOULDSTREAM:
				case Sequence.SEQ_MUSICSIGN:
					if (music) {
						out.append(" fcb 1,wait_music\n");
					}
					String verb = "file";
					if (type == Sequence.SEQ_MUSICSIGN) {
						verb = "filecut";
					}

					final Matcher theMatcher = MessageSerializer.EXTENSION_PATTERN.matcher(val);
					final int offset = out.length();

					if (theMatcher.matches()) {
						final String ext = theMatcher.group(1);
						if (ext.equalsIgnoreCase(StringShop.MP3_EXT)) {
							val = (val != null) ? val + MimeType.MIME_TYPES.ADP.getFullExtension() : null;
							out.append(" fcb 0,play_sound,/u" + i + ",0\n");
						} else {
							out.append(" fcb 0,play_midi,/u" + i + ",0\n");
						}
					} else {
						out.append(" fcb 0,play_sound,/u" + i + ",0\n");
					}
					out.insert(offset, "/u" + i + net.violet.common.StringShop.SPACE + verb + net.violet.common.StringShop.SPACE + val + "\n");
					music = true;
					break;

				case Sequence.SEQ_CHORDOWNLOAD:
				case Sequence.SEQ_CHORSTREAMING:
					out.append(" binary " + val + "\n");
					if (music) {
						out.append(" fcb 1,wait_music\n");
						music = false;
					}
					break;

				case Sequence.SEQ_PALETTE:
					out.append(" fcb 0,set_palette," + val + "\n");
					break;

				case Sequence.SEQ_COLOR:
					final int color = ConvertTools.atoi(val);
					out.append(" fcb 0,set_color," + ((color >> 24) & 255) + net.violet.common.StringShop.COMMA + ((color >> 16) & 255) + net.violet.common.StringShop.COMMA + ((color >> 8) & 255) + net.violet.common.StringShop.COMMA + (color & 255) + "\n");
					break;

				case Sequence.SEQ_WAIT:
					/* do nothing, just skip */
					break;

				default:
					MessageSerializer.LOGGER.debug("Unknown sequence type (solveV1) " + type);
				}
			}
		}
		if (n == 0) {
			return null;
		}
		if (music) {
			out.append(" fcb 1,wait_music\n");
		}
		return out.toString();
	}
}

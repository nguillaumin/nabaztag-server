package net.violet.platform.xmpp.serialization;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.MessageCounter.RABBIT_STATE;
import net.violet.platform.message.Message;
import net.violet.platform.message.Sequence;
import net.violet.platform.util.ConvertTools;
import net.violet.platform.util.LibBasic;
import net.violet.platform.util.StringShop;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 * A concret {@link Serializer} which read a {@link Message} and converts it
 * according to the 1.0 format. This format is used by the famous rabbit known
 * as Nabaztag/tag. CAUTION : The 'V1' prefix in the class name does NOT refer
 * to the NabzatagV1 ! V1 refers to the message format.
 */
public class V1Serializer implements Serializer {

	private static final Logger LOGGER = Logger.getLogger(V1Serializer.class);

	private static final byte PACKET_HEADER = 0x7F;
	private static final byte PACKET_FOOTER = -1;
	private static final byte SOURCE_FRAME_ID = 4;
	private static final byte STATUS_FRAME_ID = 11;
	private static final byte PROGRAM_V2_FRAME_ID = 10;

	public String serialize(Message message) {
		final byte[] thePingPacketAsBytes = V1Serializer.getContentAsBytes(message);
		final byte[] thePingPacketAsBase64 = Base64.encodeBase64(thePingPacketAsBytes);

		return new String(thePingPacketAsBase64);
	}

	public static byte[] getContentAsBytes(Message message) {
		final ByteArrayOutputStream theResultBuffer = new ByteArrayOutputStream();
		theResultBuffer.write(V1Serializer.PACKET_HEADER);
		V1Serializer.computeSources(theResultBuffer, message);
		V1Serializer.computeStatus(theResultBuffer, message);
		V1Serializer.computeseq(theResultBuffer, message);
		theResultBuffer.write(V1Serializer.PACKET_FOOTER);

		return theResultBuffer.toByteArray();
	}

	private static void computeSources(ByteArrayOutputStream inResultBuffer, Message inMessage) {
		if (inMessage.isSourceModeUpdate()) {
			V1Serializer.computeSourcesUpdate(inResultBuffer, inMessage);
		} else {
			V1Serializer.computeSourcesOld(inResultBuffer, inMessage);
		}
	}

	private static void computeSourcesOld(ByteArrayOutputStream inResultBuffer, Message inMessage) {
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
								srvval = V1Serializer.filtersrcval(srvtp, srvval);
								theSourceFrameBuffer.write(srvtp);
								theSourceFrameBuffer.write(srvval);
							}
							srvtp = newsrvtp;
							srvval = newsrvval;
						}
					} catch (final NumberFormatException e) {
						V1Serializer.LOGGER.fatal(e, e);
					}
				}
			}
			if (srvtp != 0) {
				i++;
				srvval = V1Serializer.filtersrcval(srvtp, srvval);
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

			inResultBuffer.write(V1Serializer.SOURCE_FRAME_ID);
			LibBasic.writeIntTo3Bytes(inResultBuffer, theSourceFrameBuffer.size());
			try {
				theSourceFrameBuffer.writeTo(inResultBuffer);
			} catch (final IOException anException) {
				V1Serializer.LOGGER.fatal(anException, anException);
			}
		}
	}

	private static void computeSourcesUpdate(ByteArrayOutputStream inResultBuffer, Message inMessage) {
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
						final int srvSrcId = V1Serializer.filterSrcID(Integer.parseInt(source.getKey()));
						final int srvSrcValue = V1Serializer.filtersrcval(srvSrcId, source.getValue());

						// Quand srvSrcId vaut 0, alors srvSrcValue vaut l'id de
						// la source a supprimé
						theSourceFrameBuffer.write(srvSrcId);
						theSourceFrameBuffer.write(srvSrcValue);
					} catch (final NumberFormatException e) {
						V1Serializer.LOGGER.fatal(e, e);
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

			inResultBuffer.write(V1Serializer.SOURCE_FRAME_ID);
			LibBasic.writeIntTo3Bytes(inResultBuffer, theSourceFrameBuffer.size());
			try {
				theSourceFrameBuffer.writeTo(inResultBuffer);
			} catch (final IOException anException) {
				V1Serializer.LOGGER.fatal(anException, anException);
			}
		}
	}

	private static void computeStatus(ByteArrayOutputStream inResultBuffer, Message inMessage) {
		Integer theStatus = inMessage.getStatus();
		if (theStatus != null) {
			if (theStatus == Message.MODE_FORCE_ACTIF) {
				theStatus = Message.MODE.ACTIF.getId();
			} else if (theStatus == Message.MODE_FORCE_VEILLE) {
				theStatus = Message.MODE_VEILLE;
			}

			V1Serializer.appendSingleByteFrame(inResultBuffer, V1Serializer.STATUS_FRAME_ID, theStatus);
		}
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
	 * Génère la séquence pour un lapin v2.
	 * 
	 * @return la séquence pour le ping.
	 */
	private static void computeseq(ByteArrayOutputStream inResultBuffer, Message inMessage) {
		final Long theEventIDObj = inMessage.getMessageID();
		String src;
		if ((theEventIDObj != null) && ((theEventIDObj.longValue() == Message.EV_DUMMY) || (theEventIDObj.longValue() == Message.EV_ASLEEP))) {
			src = "ID " + theEventIDObj + "\n";
		} else {
			src = V1Serializer.generateV2Commands(inMessage);
		}

		if (!src.equals(StringShop.EMPTY_STRING)) {
			inResultBuffer.write(V1Serializer.PROGRAM_V2_FRAME_ID);
			LibBasic.writeIntTo3Bytes(inResultBuffer, 1 + src.length());
			inResultBuffer.write(0); /* priority */
			try {
				inResultBuffer.write(LibBasic.crypt8(src, 0x47, 47));
			} catch (final IOException anException) {
				V1Serializer.LOGGER.fatal(anException, anException);
			}
		}
	}

	/**
	 * Filtre la valeur des sources.
	 * 
	 * @param srvtp le numéro de la source;
	 * @param val la valeur (initiale) de la source.
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

	/**
	 * change la bourse advancé avec l'id 20 en id 2
	 */
	private static int filterSrcID(int srvSrcId) {
		int result = srvSrcId;
		if (srvSrcId == Message.SOURCE.ADVANCED_TRADE.getId()) {
			result = Message.SOURCE.TRADE.getId();
		}

		return result;
	}

	private static String generateV2Commands(Message inMessage) {
		boolean music = false;
		final StringBuilder out = new StringBuilder();
		final Long theEventID = inMessage.getMessageID();
		if ((theEventID != null) && (theEventID.longValue() != 0)) {
			out.append("ID " + theEventID + "\n");
		}

		for (final Sequence theSequence : inMessage.getSequenceList()) {
			final int type = theSequence.getType();
			final String val = theSequence.getData();
			switch (type) {
			case Sequence.SEQ_MUSICSIGN:
				if (music) {
					out.append("MW\n");
				}
				out.append("MC " + val + "\n");
				music = true;
				break;
			case Sequence.SEQ_MUSICDOWNLOAD:
				if (music) {
					out.append("MW\n");
				}
				out.append("MU " + val + "\n");
				music = true;
				break;
			case Sequence.SEQ_MUSIC_STREAMING:
				if (music) {
					out.append("MW\n");
				}
				out.append("MS " + val + "\n");
				music = true;
				break;
			case Sequence.SEQ_MUSICSHOULDSTREAM:
				if (music) {
					out.append("MW\n");
				}
				out.append("ST " + val + "\n");
				music = true;
				break;
			case Sequence.SEQ_SECURESTREAMING:
				if (music) {
					out.append("MW\n");
				}
				out.append("SP " + val + "\n");
				music = true;
				break;
			case Sequence.SEQ_BEGIN_INTERACTIVE_MODE:
				if (music) {
					out.append("MW\n");
					music = false;
				}
				out.append("IS " + val + "\n");
				break;
			case Sequence.SEQ_END_INTERACTIVE_MODE:
				if (music) {
					out.append("MW\n");
					music = false;
				}
				out.append("IE\n");
				break;
			case Sequence.SEQ_SETTING:
				out.append("IC " + val + "\n");
				break;
			case Sequence.SEQ_STREAMING_ID:
				out.append("SI " + val + "\n");
				break;
			case Sequence.SEQ_STREAMING_STOP:
				out.append("SE " + val + "\n");
				break;
			case Sequence.SEQ_CHORDOWNLOAD:
				out.append("CH " + val + "\n");
				if (music) {
					out.append("MW\n");
					music = false;
				}
				break;
			case Sequence.SEQ_PALETTE:
				out.append("PL " + val + "\n");
				break;
			case Sequence.SEQ_COLOR:
				final int color = ConvertTools.atoi(val);
				out.append("CL " + color + "\n");
				break;
			case Sequence.SEQ_CHORSTREAMING:
				// Les V2 ne font pas de chorégraphie lorsqu'ils font du
				// streaming.
				break;
			case Sequence.SEQ_WAIT:
				final int nbMs = ConvertTools.atoi(val);
				out.append("WT " + nbMs + "\n");
				break;
			default:
				V1Serializer.LOGGER.debug("Unknown sequence type (solveV2): " + type);
			}
		}
		if (music) {
			out.append("MW\n");
		}
		return out.toString();
	}
}

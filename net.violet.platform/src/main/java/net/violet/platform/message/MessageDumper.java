package net.violet.platform.message;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import net.violet.common.utils.io.StreamTools;
import net.violet.platform.util.LibBasic;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jivesoftware.smack.util.Base64;

/**
 * Classe dumper un message, prend en entré le paquet recu par un Nabaztag et
 * renvoi un object MessageDump contenant le message décrypté.
 */
public class MessageDumper {

	private static final Logger LOGGER = Logger.getLogger(MessageDumper.class);

	private static final String CHARSET = "ISO-8859-1";

	/**
	 * Référence sur le flux contenant le message.
	 */
	private final InputStream mInputStream;

	/**
	 * Référence sur le message.
	 */
	private final MessageDump mMessageDump;

	/**
	 * Constructeur par défaut. Lit un paquet sur stdin.
	 * 
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws EOFException
	 */
	public MessageDumper() throws EOFException, IllegalArgumentException, IOException {
		final byte[] theBytes = IOUtils.toByteArray(System.in);

		if (theBytes != null) {
			this.mInputStream = new ByteArrayInputStream(theBytes);
			this.mMessageDump = new MessageDump();
		} else {
			this.mInputStream = new ByteArrayInputStream(net.violet.common.StringShop.EMPTY_STRING.getBytes());
			this.mMessageDump = new MessageDump();
		}

		this.doDumpMessage();
	}

	/**
	 * Constructeur à partir d'une chaîne. Lit un paquet à partir d'une chaîne
	 * et retourne le résultat dans une chaîne.
	 * 
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws EOFException
	 */
	public MessageDumper(String inMessage) throws EOFException, IllegalArgumentException, IOException {
		byte[] theBytes = null;
		try {
			theBytes = inMessage.getBytes(MessageDumper.CHARSET);
		} catch (final UnsupportedEncodingException e) {
			MessageDumper.LOGGER.fatal(e, e);
		}
		this.mInputStream = new ByteArrayInputStream(theBytes);
		this.mMessageDump = new MessageDump();

		this.doDumpMessage();
	}

	/**
	 * Constructeur à partir d'un tableau de byte. Lit un paquet et retourne le
	 * résultat dans une chaîne.
	 * 
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws EOFException
	 */
	public MessageDumper(byte[] inBytes) throws EOFException, IllegalArgumentException, IOException {
		this.mInputStream = new ByteArrayInputStream(inBytes);
		this.mMessageDump = new MessageDump();
		this.doDumpMessage();
	}

	/**
	 * Décrypte les éléments du message et les stock;
	 */
	private void doDumpMessage() throws EOFException, IOException, IllegalArgumentException {

		//try {
		final Integer header = this.mInputStream.read();
		if (header == -1) {
			throw new EOFException();
		}
		this.mMessageDump.setHeader(header);
		int theFrameType;
		do {
			theFrameType = dumpFrame();
		} while (theFrameType != 255);
		/*
		 * } catch (final EOFException e) { MessageDumper.LOGGER.fatal(e, e); }
		 * catch (final IOException e) { MessageDumper.LOGGER.fatal(e, e); }
		 * catch (final IllegalArgumentException e) {
		 * MessageDumper.LOGGER.fatal(e, e); }
		 */
	}

	/**
	 * Décrypte les élément d'une trame et les stock.
	 * 
	 * @return le type de la trame ou <code>255</code> si on est arrivé à la fin
	 *         du paquet.
	 */
	private int dumpFrame() throws IOException, IllegalArgumentException {
		final int theFrameType = this.mInputStream.read();
		if (theFrameType == -1) {
			throw new EOFException();
		} else if (theFrameType != 255) {

			final int theFrameLen = LibBasic.bin3toi(this.mInputStream);
			final byte[] theBytes = new byte[theFrameLen];
			final int nbRead = StreamTools.readBytes(this.mInputStream, theBytes);
			if (nbRead == -1) {
				throw new EOFException();
			} else if (nbRead != theFrameLen) {
				final byte[] debugBytes = new byte[nbRead];

				System.arraycopy(theBytes, 0, debugBytes, 0, nbRead);
				throw new IllegalArgumentException("\n\n Read size mismatch for frame " + theFrameType + ",\n read " + nbRead + ",\n need " + theFrameLen + "\n Dump hexa : " + MessageDump.dumpHexa(debugBytes));
			}
			this.mMessageDump.setFrame(theFrameType, theBytes);

			switch (theFrameType) {
			case 4: // source
				this.mMessageDump.dumpSource(theBytes);
				break;
			case 10: // bytecodeV2
				this.mMessageDump.dumpBytecodeV2(theBytes);
				break;
			case 11:
				this.mMessageDump.setStatus(theBytes[0]);
				break;
			}
		}

		return theFrameType;
	}

	/**
	 * Retourne le résultat du dump (sous la forme d'un MessageDump).
	 */
	public MessageDump getDump() {
		return this.mMessageDump;
	}

	public static MessageDump dump(byte[] inBytes) throws EOFException, IllegalArgumentException, IOException {
		final MessageDumper theMessageDumper = new MessageDumper(inBytes);
		return theMessageDumper.getDump();
	}

	public static MessageDump dump(String inMessage) throws EOFException, IllegalArgumentException, IOException {
		final MessageDumper theMessageDumper = new MessageDumper(inMessage);
		return theMessageDumper.getDump();
	}

	/**
	 * Gestion d'une erreur dans l'appel.
	 */
	private static void syntaxError() {
		System.err.println("Syntax error");
		System.err.println("try MessageDumper -h for help");
	}

	/**
	 * Gestion d'une erreur dans l'appel.
	 */
	private static void printHelp() {
		System.out.println("MessageDumper fwoAACoAHltP4QxrzjunEomzHM32u0+XY4MfqcJgQ7YsxlPXbhybhuJ/wpWIJEUDAAABeP8=");
	}

	/**
	 * Point d'entrée pour l'outil en ligne.
	 * 
	 * @throws IOException en cas d'erreur d'analyse
	 */
	public static void main(String[] inArgs) {
		if (inArgs.length == 1) {
			if (inArgs[0].equals("-h")) {
				MessageDumper.printHelp();
			} else {
				final byte[] message = Base64.decode(inArgs[0]);
				//final MessageDumper myMD = new MessageDumper(message);
				//final MessageDump theMessageDump = myMD.getDump();
				System.out.println(LibBasic.uncrypt8(message, 0x47, 47));
			}
		} else {
			MessageDumper.syntaxError();
		}
	}
}

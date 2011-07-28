package net.violet.platform.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import net.violet.db.connector.Connector;
import net.violet.db.records.SgbdConnection;
import net.violet.platform.vasm.Vasm;
import net.violet.platform.vasm.VasmException;
import net.violet.platform.vasm.Vasm.VASM_FILE;

import org.apache.log4j.Logger;

/**
 * Crée une démo pour les V1.
 */
public class V1DemoGenerator {

	private static final Logger LOGGER = Logger.getLogger(V1DemoGenerator.class);

	/**
	 * Point d'entrée. L'argument est le chemin vers le fichier vasm à compiler.
	 * 
	 * @param args
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static void main(String[] args) {
		try {
			// On crée le message.
			SgbdConnection.setConnectionMode(Connector.ConnectionMode.LOCAL);
			final ByteArrayOutputStream theBinary = Vasm.maketrame(args[0], null, 0, 0, VASM_FILE.V1);

			final BufferedOutputStream theStream = new BufferedOutputStream(new FileOutputStream(args[1]));
			theStream.write((byte) 0x7F);
			theBinary.writeTo(theStream);
			theStream.write((byte) 0xFF);
			theStream.flush();
			theStream.close();
		} catch (final UnsupportedEncodingException anException) {
			V1DemoGenerator.LOGGER.fatal(anException, anException);
		} catch (final IOException anException) {
			V1DemoGenerator.LOGGER.fatal(anException, anException);
		} catch (final VasmException anException) {
			V1DemoGenerator.LOGGER.fatal(anException, anException);
		}
	}
}

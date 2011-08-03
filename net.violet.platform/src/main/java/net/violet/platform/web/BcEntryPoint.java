package net.violet.platform.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Hardware;
import net.violet.platform.datamodel.InterruptionLogImpl;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.files.FilesManagerFactory;
import net.violet.platform.util.LibBasic;

import org.apache.log4j.Logger;

/**
 * Classe pour bc.jsp. les objets récupèrent le bytecode nominal
 */
public class BcEntryPoint extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(BcEntryPoint.class);

	private static final Map<String, Long> CACHE_DATE = new HashMap<String, Long>();
	private static final Map<String, String> CACHE_DATA = new HashMap<String, String>();
	private static final Semaphore SEMAPHORE = new Semaphore(100);

	/**
	 * Point d'entrée de la servlet.
	 */
	@Override
	public void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) throws IOException {
		final PrintWriter out = inResponse.getWriter();
		final String theSerial = inRequest.getParameter("m");
		final String theFirmware = inRequest.getParameter("v");
		final String hardware = inRequest.getParameter("h");
		final Hardware.HARDWARE theHardware;
		LOGGER.debug("Connection from serial '"+theSerial+"' with firmware '"+theFirmware+"' and hardware '"+hardware+"'");
		if (BcEntryPoint.isVersion(theFirmware) && (net.violet.common.utils.RegexTools.isInt(hardware) && (HARDWARE.V2 == (theHardware = HARDWARE.findById(Long.parseLong(hardware)))) && theHardware.checkIdentifier(theSerial))) {
			try {
				out.print(BcEntryPoint.process(theSerial));
			} catch (final InterruptedException e) {
				BcEntryPoint.LOGGER.fatal(e, e);
				out.print(net.violet.common.StringShop.EMPTY_STRING);
				inResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		} else {
			inResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
		out.close();
	}

	private static final Pattern VERSION_REGEX = Pattern.compile("(\\d[.]+){3}\\d+");

	public static boolean isVersion(String arg) {
		return (arg != null) && BcEntryPoint.VERSION_REGEX.matcher(arg).matches();
	}

	/**
	 * Point d'entrée de la jsp.
	 * 
	 * @param inSerial : serial de l'objet
	 * @param inHardware : hardware de l'objet
	 * @param inType : type de bytecode a récupérer selon le mode de celui ci
	 * @return
	 * @throws InterruptedException
	 */
	private static String process(String inSerial) throws InterruptedException {
		String theResult = net.violet.common.StringShop.EMPTY_STRING;

		BcEntryPoint.SEMAPHORE.acquire();
		try {

			int mode = -1;
			long bc_version = 0;
			final String cleanSerial = inSerial.replace(":", net.violet.common.StringShop.EMPTY_STRING);
			final VObject theObject = Factories.VOBJECT.findBySerial(cleanSerial);

			if (theObject != null) {
				mode = theObject.getObject_mode();
				bc_version = theObject.getObject_bc_version();

				// si l'objet V2 jabber était inactif on le repasse en actif
				if ((mode == VObject.MODE_XMPP_INACTIVE) || (mode == VObject.MODE_PING_INACTIVE)) { // les anciens v2 ping déjà inactif avant le
					// passage
					// en
					// jabber
					mode = VObject.MODE_XMPP;
					theObject.setMode(mode);
				}

				InterruptionLogImpl.insert(theObject, InterruptionLogImpl.WHAT_OPTION.BC, String.valueOf(mode));
				theObject.setLastActivityTime();
			}
			BcEntryPoint.LOGGER.info(Files.CATEGORIES.BYTE_CODE.getPath() + "bc-nominal-h4-" + bc_version + ".bin");
			switch (mode) {
			case VObject.MODE_XMPP:
				if (bc_version < 0) {
					bc_version *= -1;
					theResult = BcEntryPoint.getCachedFile(Files.CATEGORIES.BYTE_CODE.getPath() + "bc-nominal-h4-" + bc_version + ".bin");
				} else {
					theResult = BcEntryPoint.getCachedFile(Files.CATEGORIES.BYTE_CODE.getPath() + "bc-nominal-h4.bin");
				}
				break;
			default:
				theResult = BcEntryPoint.getCachedFile(Files.CATEGORIES.BYTE_CODE.getPath() + "bc-nominal-h4.bin");
				break;
			}
		} finally {
			BcEntryPoint.SEMAPHORE.release();
		}
		return theResult;
	}

	private static String getCachedFile(String inPath) {
		final File theFile = new File(inPath);
		final long theFileDate = theFile.lastModified();
		final String theResult;
		synchronized (BcEntryPoint.CACHE_DATA) {
			final Long theCachedDate = BcEntryPoint.CACHE_DATE.get(inPath);
			if ((theCachedDate == null) || (theCachedDate.longValue() != theFileDate)) {
				byte[] theFileContent;
				try {
					theFileContent = FilesManagerFactory.FILE_MANAGER.getFilesContent(inPath);
				} catch (final IOException e) {
					BcEntryPoint.LOGGER.fatal(e, e);
					theFileContent = null;
				}
				theResult = LibBasic.getStringFromBytes(theFileContent);
				BcEntryPoint.CACHE_DATE.put(inPath, new Long(theFileDate));
				BcEntryPoint.CACHE_DATA.put(inPath, theResult);
			} else {
				theResult = BcEntryPoint.CACHE_DATA.get(inPath);
			}
		}
		return theResult;
	}
}

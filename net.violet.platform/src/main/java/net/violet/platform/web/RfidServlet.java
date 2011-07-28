package net.violet.platform.web;

import java.rmi.NoSuchObjectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.common.StringShop;
import net.violet.common.utils.RegexTools;
import net.violet.platform.api.endpoints.HTTPEndpoint;
import net.violet.platform.datamodel.Hardware;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.events.ZtampDetectionEvent;
import net.violet.platform.events.ZtampEvent;
import net.violet.platform.events.ZtampRemovalEvent;
import net.violet.platform.events.handlers.EventsDispatcher;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.ConvertTools;

import org.apache.log4j.Logger;

/**
 * This Servlet received HttpRequests from an object (such as a Nabaztag/tag and and Mirror) when it 
 * reads a ztamp object. It uses the request's parameters to create an Event and sends it to the
 * EventDispatcher.
 */
public class RfidServlet extends HTTPEndpoint {

	private static final Logger LOGGER = Logger.getLogger(RfidServlet.class);
	public static final String AUDIO_BOOK = "net.violet.audiobooks.";

	private static final String RFID_SERIAL = "t";
	private static final String READER_SERIAL = "sn";
	private static final String HARDWARE = "h";
	private static final String ACTION = "action"; // available values : 'detected' and 'removed'
	private static final String FEED_BACK = "feedback"; // feedback on default violet html page
	private static final String FEED_BACK_URL = Constantes.OS_SERVLET_ROOT + "/processing.html";

	@Override
	public void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) {

		final String strRequestUrl = inRequest.getRequestURL() + "?" + inRequest.getQueryString();
		RfidServlet.LOGGER.info(strRequestUrl);

		try {
			final String rfidSerial = getParameter(inRequest, RfidServlet.RFID_SERIAL, true);
			final String readerSerial = getParameter(inRequest, RfidServlet.READER_SERIAL, true);
			final String readerHardware = getParameter(inRequest, RfidServlet.HARDWARE, true);
			final String action = getParameter(inRequest, RfidServlet.ACTION, null);
			final String feedBack = inRequest.getParameter(RfidServlet.FEED_BACK);

			if (RegexTools.isInt(readerHardware) && Hardware.HARDWARE.findById(Long.parseLong(readerHardware)).checkIdentifier(readerSerial)) {
				final VObjectData reader = VObjectData.findBySerial(readerSerial);
				if ((reader == null) || !reader.isValid()) {
					throw new NoSuchObjectException(readerSerial);
				}

				final ZtampEvent event;
				if ((action == null) || action.equals(StringShop.EMPTY_STRING) || action.equals("detected")) {
					event = new ZtampDetectionEvent(reader, rfidSerial);
				} else if (action.equals("removed")) {
					event = new ZtampRemovalEvent(reader, rfidSerial);
				} else {
					throw new UnsupportedOperationException(action + " is not a valid action for ztamp event");
				}

				EventsDispatcher.processEvent(event);

				if (ConvertTools.htoi(feedBack) > 0) {
					inResponse.sendRedirect(RfidServlet.FEED_BACK_URL);
				}
			}

		} catch (final Exception e) {
			RfidServlet.LOGGER.fatal("CALL to " + strRequestUrl + " FAILED !", e);
			writeErrorMessage(inResponse, e);
		}
	}

}

package net.violet.platform.web;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.ping.EventPing;

/**
 * Classe pour p7.jsp les objets daldal ping et récupèrent une trame
 * correspondant au message.
 */
public class PingDalDalEntryPoint extends HttpServlet {

	/**
	 * Point d'entrée de la servlet.
	 */
	@Override
	public void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) throws IOException {
		final byte[] thePacket = EventPing.processPing(inRequest, HARDWARE.DALDAL);
		if (thePacket != null) {
			final OutputStream theStream = inResponse.getOutputStream();
			theStream.write(thePacket);
			theStream.flush();
		}
	}
}

package net.violet.platform.web;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.platform.interactif.EntryPoint;

/**
 * Classe pour itmode.jsp. l'objet est en mode interactif.
 */
public class ItModeEntryPoint extends HttpServlet {

	/**
	 * Point d'entrée de la servlet.
	 */
	@Override
	public void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) throws IOException {
		final byte[] thePacket = EntryPoint.resolveInteractiveAppli(inRequest);
		if (thePacket != null) {
			final OutputStream out = inResponse.getOutputStream();
			out.write(thePacket);
			out.flush();
		}
	}

	@Override
	public void doPost(HttpServletRequest inRequest, HttpServletResponse inResponse) throws IOException {
		doGet(inRequest, inResponse);
	}
}

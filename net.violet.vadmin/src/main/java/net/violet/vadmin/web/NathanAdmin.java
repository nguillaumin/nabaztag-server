package net.violet.vadmin.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.common.utils.net.NetTools;
import net.violet.platform.datamodel.NathanVersion;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.NathanMp3Data;
import net.violet.platform.dataobjects.NathanVersionData;

public class NathanAdmin extends HttpServlet {

	private static final String IP_NAB = "192.168";
	private static final String IP_ACCES_PROD = "88.166.25.120"; // accès a partir de Violet

	@Override
	public void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) throws ServletException, IOException {

		if (NetTools.getIP(inRequest).startsWith(NathanAdmin.IP_ACCES_PROD) || NetTools.getIP(inRequest).startsWith(NathanAdmin.IP_NAB) || "127.0.0.1".equals(NetTools.getIP(inRequest))) {

			final String command = inRequest.getParameter("action");
			if (command != null) {

				if (command.equals("list")) {
					final List<NathanVersion> list = Factories.NATHAN_VERSION.findWaitingVersions();
					inRequest.setAttribute("resultsList", list);
				} else {
					final Long versionId = Long.parseLong(inRequest.getParameter("versionId"));
					final NathanVersionData theVersionData = NathanVersionData.find(versionId);

					if (command.equals("accept")) {
						theVersionData.setStatus(NathanVersion.Status.AUTHORIZED);
					}

					if (command.equals("refuse")) {
						theVersionData.setStatus(NathanVersion.Status.REFUSED);
					}

					if (command.equals("info")) {
						inRequest.setAttribute("theVersion", theVersionData);
						inRequest.setAttribute("mp3List", NathanMp3Data.findAllByVersion(theVersionData));
						inRequest.setAttribute("theAuthor", theVersionData.getAuthor());
					}
				}

			}

			final String nextJSP = "/nathanadmin.jsp";
			final RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			dispatcher.forward(inRequest, inResponse);

		}

	}

}

package net.violet.platform.web.apps;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet works like a dispatcher for the demo applications. It's designed to receive at least one parameter 'application' which
 * is the name of the application to trigger (the application does not need to be stored as a traditional application).
 */
public class ZtampAppsServlet extends HttpServlet {

	private static final Map<String, DemoApplication> SERVLET_APPLICATIONS = new HashMap<String, DemoApplication>();
	private static final Map<String, String> ONLY_JSPS = new HashMap<String, String>();

	static {
		ZtampAppsServlet.SERVLET_APPLICATIONS.put("counter", new CounterApplication());

		ZtampAppsServlet.ONLY_JSPS.put("youtube", "youtube.jsp");
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		final String applicationName = request.getParameter("application");

		final String redirect;
		if (ZtampAppsServlet.SERVLET_APPLICATIONS.containsKey(applicationName)) {
			redirect = ZtampAppsServlet.SERVLET_APPLICATIONS.get(applicationName).process(request, response);
		} else if (ZtampAppsServlet.ONLY_JSPS.containsKey(applicationName)) {
			redirect = ZtampAppsServlet.ONLY_JSPS.get(applicationName);
		} else {
			redirect = null;
		}

		if (redirect != null) {
			final RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/" + redirect);
			try {
				dispatcher.forward(request, response);
			} catch (final ServletException e) {
				System.err.println(e);
			}
		}
	}

	@Override
	public void doPost(HttpServletRequest inRequest, HttpServletResponse inResponse) throws IOException {
		doGet(inRequest, inResponse);
	}

}

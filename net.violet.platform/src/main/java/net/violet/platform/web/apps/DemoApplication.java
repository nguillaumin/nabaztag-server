package net.violet.platform.web.apps;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The interface for a demo application handled by the ZtampDemoServlet
 * @author vincent
 *
 */
public interface DemoApplication {

	/**
	 * Executes the application. This method has side effects : it fills up the request with meaningful attributes which have to 
	 * be displayed by the jsp.
	 * It returns the name of the jsp to be displayed
	 * @param request
	 * @param response
	 * @return
	 */
	String process(HttpServletRequest request, HttpServletResponse response);

}

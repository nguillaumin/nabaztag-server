package net.violet.platform.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.violet.platform.datamodel.MockTestBase;

/**
 * Classe de base pour les tests des actions (struts).
 * 
 * truncat object_has_read_content; truncate object_has_scenario; truncate
 * scenario; truncate action; truncate object; truncate user; truncate annu;
 * truncate object_has_read_content; truncate object_has_scenario;
 * truncatescenario; truncate action; truncate object;
 */
public abstract class ServletTestBase extends MockTestBase {

	/**
	 * Crée une requête avec une session.
	 */
	protected static ServletTestRequest createRequest(HttpSession inSession) {
		return ServletTestRequest.createFromSession(inSession);
	}

	/**
	 * Crée une session vide.
	 */
	protected static HttpSession createEmptySession() {
		return new ServletTestSession();
	}

	/**
	 * Crée une requête avec une session vide.
	 */
	protected static HttpServletRequest createEmptySessionRequest() {
		return ServletTestRequest.createFromSession(ServletTestBase.createEmptySession());
	}

	/**
	 * Crée une réponse (vide).
	 */
	protected static ServletTestResponse createResponse() {
		return new ServletTestResponse();
	}
}

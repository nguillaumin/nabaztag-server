package net.violet.mynabaztag.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.common.utils.DigestTools;
import net.violet.mynabaztag.form.MySessionForm;
import net.violet.platform.datamodel.User;
import net.violet.platform.struts.DispatchActionWithLog;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MySessionAction extends DispatchActionWithLog {

	private static final Logger LOGGER = Logger.getLogger(MySessionAction.class);

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = null;
		final MySessionForm myForm = (MySessionForm) form;

		final HttpSession session = request.getSession(true);
		final User user = SessionTools.getUserFromSession(request);
		int userId;
		if (user == null) {
			userId = 0;
		} else {
			userId = user.getId().intValue();
		}
		final String languser = Long.toString(SessionTools.getLangFromSession(session, request).getId());

		String forward2 = myForm.getForward();
		if (forward2.equals(StringShop.EMPTY_STRING)) {
			forward2 = "login";
		}

		if (!myForm.getAction().trim().equals(StringShop.EMPTY_STRING)) {
			try {
				forward = dispatchMethod(mapping, myForm, request, response, myForm.getAction().trim());
			} catch (final Exception e) {
				MySessionAction.LOGGER.fatal("Forward to " + myForm.getAction().trim() + " miserably failed");
				MySessionAction.LOGGER.fatal(e, e);
			}
		}

		if (null == forward) {
			userId = 0;
			request.getSession().setAttribute("page", "home");
			myForm.setRedirectUrl(request.getParameter("goTo"));
			forward = mapping.findForward("login");
		}

		myForm.setLangUser(languser);
		myForm.setUserId(userId);
		myForm.setForward(forward2);

		return forward;
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward connect(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final MySessionForm myForm = (MySessionForm) form;
		int loginError = 0;
		final String forward = "goNablife";
		if (null == SessionTools.logUser(request, response, myForm.getPseudo(), DigestTools.digest(myForm.getPassword(), DigestTools.Algorithm.MD5))) {
			loginError = 1;
			String redirectUrl = StringShop.EMPTY_STRING;

			if (!myForm.getRedirectUrlBadLogin().equals(StringShop.EMPTY_STRING)) {
				redirectUrl = myForm.getRedirectUrlBadLogin();
			} else if (!myForm.getRedirectUrl().equals(StringShop.EMPTY_STRING)) {
				redirectUrl = buildRedirect(request, myForm.getRedirectUrl(), "1");
			}

			MySessionAction.LOGGER.debug("On va renvoyer vers : " + redirectUrl);
			firstRedirection(request, response, redirectUrl);
			myForm.setLoginError(loginError);
			return mapping.findForward(forward);
		}
		// Login OK
		if (!myForm.getRedirectUrl().equals(StringShop.EMPTY_STRING)) {
			MySessionAction.LOGGER.debug("login ok");
			final String redirectUrl = buildRedirect(request, myForm.getRedirectUrl(), "0");
			firstRedirection(request, response, redirectUrl);
			return mapping.findForward(forward);
		}
		MySessionAction.LOGGER.debug("forward : " + myForm.getUrl());
		return mapping.findForward(myForm.getUrl());
	}

	/**
	 * Unlog the user.
	 * 
	 * @param mapping the Mapping.
	 * @param form the ActionForm.
	 * @param request the Request.
	 * @param response the Response.
	 * @return
	 */
	public ActionForward disconnect(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		MySessionAction.LOGGER.debug("Disconnect");
		SessionTools.unlogUser(request, response);
		return mapping.findForward("login");
	}

	/**
	 * Do the first redirection, switch if the login succeeded or not.
	 * 
	 * @param request the Request.
	 * @param response the Response.
	 * @param redirection the URL to redirect.
	 */
	private void firstRedirection(HttpServletRequest request, HttpServletResponse response, String redirection) {
		final RequestDispatcher reqDispatcher = request.getRequestDispatcher(redirection);
		try {
			reqDispatcher.forward(request, response);
		} catch (final IOException anException) {
			MySessionAction.LOGGER.fatal(anException, anException);
		} catch (final ServletException anException) {
			MySessionAction.LOGGER.fatal(anException, anException);
		}
	}

	/**
	 * Build the URL we have to redirect.
	 * 
	 * @param request the Request.
	 * @param redirect the beginning of the URL.
	 * @param badLogin <code>0</code> if the login is correct and <code>1</code>
	 *            if not.
	 * @return the complete URL redirection
	 */
	private String buildRedirect(HttpServletRequest request, String redirect, String badLogin) {
		String value = redirect;
		if (value.indexOf("@") > -1) {
			value = value.replace("@", "?");
		}
		if (request.getParameterMap().containsKey("badLogin")) {
			if (value.indexOf("?") > -1) {
				value += value + "&badLogin=" + badLogin;
			} else {
				value += value + "?badLogin=" + badLogin;
			}
		}
		return value;
	}
}

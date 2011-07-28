package net.violet.mynabaztag.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyNablifeForm;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MessageReceivedData;
import net.violet.platform.dataobjects.MotherTongueLangData;
import net.violet.platform.dataobjects.NabcastData;
import net.violet.platform.dataobjects.NablifeServicesData;
import net.violet.platform.dataobjects.SrvDialogData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.ConvertTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyNablifeAction extends ActionWithLog {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(MyNablifeAction.class);

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		// initialisation des objets
		final MyNablifeForm myForm = (MyNablifeForm) form;
		final HttpSession session = request.getSession(true);

		final Map parameters = request.getParameterMap();

		String languser = request.getParameter("langUser");

		if ((languser == null) || languser.equals(StringShop.EMPTY_STRING)) {
			languser = Long.toString(SessionTools.getLangFromSession(session, request).getId());
		} else {
			SessionTools.setLangInSession(session, Factories.LANG.find(ConvertTools.atol(languser)));
		}

		final User theUser = SessionTools.getUserFromSession(request);
		final VObject rabbit = SessionTools.getRabbitFromSession(session);

		// available languages list
		myForm.setLangList(MotherTongueLangData.findAllMotherTongue());

		if (parameters.containsKey("badLogin")) {
			if (request.getParameter("badLogin").equals("1")) {
				myForm.setBadLogin(1);
			} else {
				myForm.setBadLogin(-1);
			}
		}

		if (theUser == null) {
			myForm.setNotLogged(1);
		} else {
			if (SrvDialogData.hasSomethingToDisplay(rabbit) > 0) {
				MyNablifeAction.LOGGER.debug("Something to display");
				myForm.setPopup("popup");
				myForm.setNotLogged(0);
			}
		}

		myForm.setUserData(UserData.getData(theUser));
		myForm.setObjectId((int) VObjectData.getData(rabbit).getId());

		if (!myForm.getOnglet().equals(StringShop.EMPTY_STRING)) {
			myForm.setOnglet(myForm.getOnglet());
		}

		// permet de passer dans l'url quel service on veut configurer
		try {
			myForm.setServiceToConfigure(URLDecoder.decode(myForm.getServiceToConfigure(), "UTF-8"));
		} catch (final UnsupportedEncodingException e) {
			MyNablifeAction.LOGGER.fatal(e, e);
		}

		myForm.setNbMsgReceived(MessageReceivedData.countReceivedMessagesByVObject(rabbit, MessageReceived.MESSAGE_RECEIVED_STATES.INBOX, true));

		final String userPseudo = VObjectData.getData(rabbit).getObject_login();
		myForm.setUserPseudo(userPseudo);
		myForm.setLangUser(languser);
		myForm.setUserId((int) UserData.getData(theUser).getId());

		// On charge un nabcast par son id
		if (parameters.containsKey("idNabcast")) {
			final int idNabcast = Integer.parseInt(request.getParameter("idNabcast"));
			final List<NabcastData> listNabcast = new ArrayList<NabcastData>();
			listNabcast.add(NabcastData.find(idNabcast));
			myForm.setNabcastData(listNabcast);
			myForm.setFromSearch(true);
		} else {
			if (parameters.containsKey("service")) {
				final String serviceName = request.getParameter("service");
				final NablifeServicesData srvListData = NablifeServicesData.findByShortcut(rabbit, serviceName);

				if ((srvListData != null) && (srvListData.getId() > 0)) {
					myForm.setSrvListData(srvListData);
					myForm.setFromSearch(true);
				}

				if (srvListData != null) {
					if (theUser != null) {
						myForm.setUserTimeZone(theUser.getTimezone().getTimezone_javaId());
					} else {
						myForm.setUserTimeZone("Europe/London");
					}

				}
			} else if (parameters.containsKey("nabcast")) {
				final String nabcastName = request.getParameter("nabcast");
				final List<NabcastData> listNabcast = NabcastData.findAllByShortcut(nabcastName);
				if (!listNabcast.isEmpty()) {
					myForm.setNabcastData(listNabcast.subList(0, 1));
					myForm.setFromSearch(true);
					myForm.setSrvListData(null);

					if (theUser != null) {
						myForm.setUserTimeZone(theUser.getTimezone().getTimezone_javaId());
					} else {
						myForm.setUserTimeZone("Europe/London");
					}
				}
			}
			if (myForm.isFromSearch() && ((theUser == null) || (rabbit == null))) {
				request.setAttribute("goToListResult", "yes");
				myForm.setFromSearch(false);
				session.setAttribute("listResultForm", myForm);
			}

		}

		return mapping.getInputForward();
	}
}

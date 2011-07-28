package net.violet.mynabaztag.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.BlocForm;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Dico;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.TagTmpSite;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.NabcastData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.struts.DispatchActionWithLog;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class BlocAction extends DispatchActionWithLog {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(BlocAction.class);

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final BlocForm myForm = (BlocForm) form;
		final HttpSession session = request.getSession(true);

		final String languser = Long.toString(SessionTools.getLangFromSession(session, request).getId());
		final User theUser = SessionTools.getUserFromSession(request);
		int user_main = 0;

		// save user logue information
		myForm.setUser_lang(languser);

		if (theUser != null) {
			myForm.setUser_id(Long.toString(theUser.getId()));
			final VObject theFirstRabbit = SessionTools.getRabbitFromSession(session);
			if (theFirstRabbit != null) {
				user_main = theFirstRabbit.getId().intValue();
				myForm.setUser_main(user_main);
			}
		}

		ActionForward myForward = null;
		try {
			BlocAction.LOGGER.info("Param action : " + request.getParameter("action"));
			myForward = dispatchMethod(mapping, myForm, request, response, request.getParameter("action"));
		} catch (final Exception e) {
			BlocAction.LOGGER.fatal(e, e);
		}

		if ((myForward != null) && !myForward.getName().equals(StringShop.EMPTY_STRING)) {
			return myForward;
		}

		/* Error */
		myForm.setTitle("Error");
		myForm.setDesc("Error loading");

		return mapping.findForward("Error");
	}

	/**
	 * For the friends bloc
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward friends(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final BlocForm myForm = (BlocForm) form;
		myForm.setTitle("Liste d'amis");

		final User theUser = SessionTools.getUserFromSession(request);
		int id = (theUser != null) ? theUser.getId().intValue() : 0;

		if (!myForm.getParam1().equals("0")) {
			try {
				id = Integer.parseInt(myForm.getParam1());
			} catch (final NumberFormatException e) {
				BlocAction.LOGGER.debug("BlocAction" + e);
			}
		}

		final User user = Factories.USER.find(id);

		if (user != null) {
			myForm.setResult(UserData.generateList(user.getFriends()));
		}

		return mapping.findForward("friends");
	}

	/**
	 * For the DernierAnnuaire bloc
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward DernierAnnuaire(ActionMapping mapping, ActionForm form, @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final BlocForm myForm = (BlocForm) form;
		myForm.setResult(UserData.findListLastRabbits(10));
		return mapping.findForward("DernierAnnuaire");
	}

	/**
	 * For the MyNabcast bloc
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward MyNabcast(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final User theUser = SessionTools.getUserFromSession(request);
		final BlocForm myForm = (BlocForm) form;
		myForm.setResult(NabcastData.findAllCreatedByUser(theUser));
		return mapping.findForward("MyNabcast");
	}

	/**
	 * For the updateMac bloc
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward updateMac(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final BlocForm myForm = (BlocForm) form;
		final User theUser = SessionTools.getUserFromSession(request);

		if (theUser != null) {

			if (myForm.getParam1().equals("true")) { // récupère la mac que l'utilisateur a setté dans l'inscription
			} else {// update de mac
				TagTmpSite lonelyRabbit = null;

				// check si son lapin a pingué notre plateforme
				lonelyRabbit = Factories.TAG_TMP_SITE.findBySerial(myForm.getMacAddress());

				if (lonelyRabbit == null) {
					// rafrachie le cache avec la nouvelle mac
					myForm.setErrors("recheckMac");
				}
			}
		} else {
			myForm.setMacAddress(StringShop.EMPTY_STRING);
		}

		return mapping.findForward("UpdateMac");
	}

	/**
	 * For the MyServices bloc
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward MyServices(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final HttpSession session = request.getSession(true);

		final BlocForm myForm = (BlocForm) form;
		myForm.setTitle("Mes Services");
		final List<SubscriptionData> result = new ArrayList<SubscriptionData>();

		final VObject theRabbit = SessionTools.getRabbitFromSession(session);

		if (theRabbit != null) {
			result.addAll(SubscriptionData.findListServicesByObject(theRabbit));
			myForm.setUser_main(theRabbit.getId().intValue());
			// patch pour les livres
			final User theRabbitOwner = theRabbit.getOwner();
			if (theRabbitOwner != null) {
				final List<VObject> otherObjects = Factories.VOBJECT.findByOwner(theRabbitOwner);
				for (final VObject otherObject : otherObjects) {
					if (!otherObject.equals(theRabbit)) {
						for (final SubscriptionData otherSubscription : SubscriptionData.findAllByObject(otherObject)) {
							if ((otherSubscription.getApplication() != null) && Application.ApplicationClass.NATIVE.equals(otherSubscription.getApplication().getApplicationClass()) && otherSubscription.getApplication().isInteractive() && otherSubscription.getApplication().getTemp().getLink().contains("appletId")) {
								result.add(otherSubscription);
							}
						}
					}
				}
			}
		}
		myForm.setResult(result);
		return mapping.findForward("MyServices");
	}

	/**
	 * For the DecouvrirNabaztag bloc
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward DecouvrirNabaztag(ActionMapping mapping, ActionForm form, @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final BlocForm myForm = (BlocForm) form;
		myForm.setTitle("Découvrir Nabaztag");
		return mapping.findForward("DecouvrirNabaztag");
	}

	private static final Random rnd = new Random();

	/**
	 * For the nablives bloc
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward nablives(ActionMapping mapping, ActionForm form, @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final BlocForm myForm = (BlocForm) form;
		myForm.setTitle("nablives");
		myForm.setNablives(Dico.NABLIVES.get(rnd.nextInt(Dico.NABLIVES.size())).getDico_key());
		return mapping.findForward("nablives");
	}

	/**
	 * for the generic bloc
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward generic(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final Lang languser = SessionTools.getLangFromSession(session, request);
		final BlocForm myForm = (BlocForm) form;
		String forward = StringShop.EMPTY_STRING;
		final String param1 = myForm.getParam1();
		BlocAction.LOGGER.debug("Param de Bloc.do : " + ((param1 == null) ? "null" : param1));
		if ((param1 != null) && !param1.equals("0")) {
			final List<String> DicoKeys = Arrays.asList(param1.split(";"));
			final List<TreeMap<String, String>> contentGenericBloc = new ArrayList<TreeMap<String, String>>();
			String mainTitle = StringShop.EMPTY_STRING;
			for (final String DicoKey : DicoKeys) {
				final TreeMap<String, String> subBloc = new TreeMap<String, String>();
				subBloc.put("title", DicoTools.dico(languser, "genericBloc/" + DicoKey + "_title"));
				subBloc.put("content", DicoTools.dico(languser, "genericBloc/" + DicoKey));
				mainTitle = DicoTools.dico(languser, "genericBloc/" + DicoKey + "_mainTitle");

				if (!mainTitle.equals(StringShop.EMPTY_STRING) && !mainTitle.equals("NOLOC")) {
					myForm.setMainTitle(mainTitle);
				}
				subBloc.put("DicoKey", DicoKey);
				contentGenericBloc.add(subBloc);
			}

			myForm.setContentGenericBloc(contentGenericBloc);
			forward = "generic";
		} else {
			myForm.setTitle("Error");
			myForm.setDesc("Error loading");
			forward = "Error";
		}
		return mapping.findForward(forward);
	}

}

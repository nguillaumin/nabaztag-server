package net.violet.mynabaztag.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvBourseFullForm;
import net.violet.platform.applications.SubscriptionManager;
import net.violet.platform.applications.TradeFullHandler;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.BourseDataFactory;
import net.violet.platform.dataobjects.SrvBourseData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.struts.DispatchActionForLoggedUserWithObject;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class MySrvBourseFullAction extends DispatchActionForLoggedUserWithObject {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(MySrvBourseFullAction.class);

	public static final int ttsBourse = 89;
	static final String FLASH_ONE = "FLASH_BOURSEFULL_ONE";
	static final String FLASH_TWO = "FLASH_BOURSEFULL_TWO";

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final Lang lang = SessionTools.getLangFromSession(session, request);

		final MySrvBourseFullForm myForm = (MySrvBourseFullForm) form;

		final List<SubscriptionData> subscriptionsList = SubscriptionData.findByApplicationAndObject(Application.NativeApplication.BOURSE_FULL.getApplication(), theObject);
		// on verifie si l'utilisateur est enregistré
		final int isReg = (subscriptionsList.isEmpty()) ? 0 : 1;

		myForm.setServiceName(Application.NativeApplication.BOURSE_FULL.getApplication().getName());

		myForm.setIsReg(isReg);

		// on crée la liste des indices comme dans la version Free
		myForm.setIndicList(BourseDataFactory.generateListFrequence(lang));

		// TimezoneImpl de l'utilisateur
		myForm.setNbrValue(0);
		myForm.setMaxValue(5);

		myForm.setSupervisedList(SrvBourseData.getSupervisedInfoBourse(theObject));

		return mapping.getInputForward();
	}

	public ActionForward activate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvBourseFullForm myForm = (MySrvBourseFullForm) form;

		final ApplicationData bourse = ApplicationData.getData(Application.NativeApplication.BOURSE_FULL.getApplication());

		final String alertName = myForm.getAlertName();
		final String valName = myForm.getValName();
		final String indic = myForm.getIndic();

		// BEGIN VALIDATE
		boolean duplicateName = false;

		// on verifie si le nom de l'alerte n'existe pas deja
		for (final Iterator<SubscriptionData> subscriptionsIterator = SubscriptionData.findByApplicationAndObject(bourse.getReference(), theObject).iterator(); subscriptionsIterator.hasNext() && !duplicateName;) {
			final Object setting = subscriptionsIterator.next().getSettings().get(TradeFullHandler.ALERT_NAME);
			duplicateName = setting.toString().equals(alertName + StringShop.DOLLAR);
		}

		if (duplicateName) {
			myForm.setFalseValue(false);
			myForm.setDuplicateName(duplicateName);
		} else {
			try {
				final List<String> flashes = new ArrayList<String>();
				if (myForm.getHorraire1() != null && !myForm.getHorraire1().equals(StringShop.EMPTY_STRING))
					flashes.add(myForm.getHorraire1());
				if (myForm.getHorraire2() != null && !myForm.getHorraire2().equals(StringShop.EMPTY_STRING))
					flashes.add(myForm.getHorraire2());
				TradeFullHandler.createOrUpdateSubscription(null, VObjectData.getData(theObject), alertName, indic, valName, myForm.getLumiere() > 0, flashes, !(myForm.getWeekend() > 0));

			} catch (final Exception e) {
				MySrvBourseFullAction.LOGGER.fatal(e, e);
				return load(mapping, form, request, response);
			}
		}

		myForm.setAlertName(alertName);
		myForm.setIndic(indic);
		myForm.setValueTo(0);
		myForm.setValName(valName);

		return load(mapping, form, request, response);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvBourseFullForm myForm = (MySrvBourseFullForm) form;

		final String alertName = myForm.getAlertName();
		final String valName = myForm.getValName();
		final String indic = myForm.getIndic();

		final SubscriptionData theSubscription = SubscriptionData.find(myForm.getValueTo());
		try {
			final List<String> flashes = new ArrayList<String>();
			if (myForm.getHorraire1() != null && !myForm.getHorraire1().equals(StringShop.EMPTY_STRING))
				flashes.add(myForm.getHorraire1());
			if (myForm.getHorraire2() != null && !myForm.getHorraire2().equals(StringShop.EMPTY_STRING))
				flashes.add(myForm.getHorraire2());
			TradeFullHandler.createOrUpdateSubscription(theSubscription, VObjectData.getData(theObject), alertName, indic, valName, myForm.getLumiere() > 0, flashes, !(myForm.getWeekend() > 0));
		} catch (final Exception e) {
			MySrvBourseFullAction.LOGGER.fatal(e, e);
			return load(mapping, form, request, response);
		}

		myForm.setAlertName(alertName);
		myForm.setIndic(indic);
		myForm.setValueTo(0);
		myForm.setValName(valName);

		return load(mapping, form, request, response);
	}

	public ActionForward display(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final MySrvBourseFullForm myForm = (MySrvBourseFullForm) form;

		int weekEnd = 0;
		int lumiere = 0;
		String horraire1 = StringShop.EMPTY_STRING;
		String horraire2 = StringShop.EMPTY_STRING;

		String alertName = StringShop.EMPTY_STRING;
		String valName = StringShop.EMPTY_STRING;
		String indic = StringShop.EMPTY_STRING;

		final int valueTo = myForm.getValueTo();
		// Recup des valeurs depuis l'id, et on les set lumiere, flash_voc,
		// etc...
		final SubscriptionData theSubscription = SubscriptionData.find(valueTo);

		final SrvBourseData srvBourseData = SrvBourseData.getSrvBourseDataFromSubscription(theSubscription);
		String code = "nothing";
		if (!srvBourseData.getSrv_name().equals(StringShop.EMPTY_STRING)) {
			lumiere = srvBourseData.getSrv_passive();
			code = srvBourseData.getSrv_src();
			alertName = srvBourseData.getSrv_name();
			horraire1 = srvBourseData.getTime1();
			horraire2 = srvBourseData.getTime2();
			weekEnd = (int) srvBourseData.getWeekend();
			valName = srvBourseData.getSrv_val_name();
			if (valName.equals(StringShop.EMPTY_STRING)) {
				indic = "nothing";
			}
		}

		// Cas d'une valeur, on recupere le nom de la valeur
		indic = code;

		// On met les valeurs recupéré dans la form
		myForm.setLumiere(lumiere);
		myForm.setAlertName(alertName);
		myForm.setValName(valName);
		myForm.setIndic(indic);
		myForm.setWeekend(weekEnd);
		myForm.setHorraire1(horraire1);
		myForm.setHorraire2(horraire2);
		myForm.setValueTo(valueTo);
		return load(mapping, form, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvBourseFullForm myForm = (MySrvBourseFullForm) form;

		final int valueTo = myForm.getValueTo();
		if (valueTo != 0) { // si on a une valeur precise
			try {
				SubscriptionManager.deleteSubscription(SubscriptionData.find(valueTo));
			} catch (final Exception e) {
				MySrvBourseFullAction.LOGGER.fatal(e, e);
			}
		} else { // sinon on supprime toutes les entrees

			for (final SubscriptionData aSubscription : SubscriptionData.findByApplicationAndObject(Application.NativeApplication.BOURSE_FULL.getApplication(), theObject)) {

				try {
					SubscriptionManager.deleteSubscription(aSubscription);
				} catch (final Exception e) {
					MySrvBourseFullAction.LOGGER.fatal(e, e);
				}
			}
		}
		myForm.setValueTo(0);
		return load(mapping, form, request, response);
	}

}

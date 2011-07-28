package net.violet.mynabaztag.action;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvTrafficForm;
import net.violet.platform.applications.SubscriptionManager;
import net.violet.platform.applications.TraficHandler;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData.SchedulingAtomData;
import net.violet.platform.schedulers.DailyHandler;
import net.violet.platform.struts.DispatchActionForLoggedUserWithObject;
import net.violet.platform.util.SessionTools;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class MySrvTrafficAction extends DispatchActionForLoggedUserWithObject {

	private static final Logger LOGGER = Logger.getLogger(MySrvTrafficAction.class);

	private static final List<String> SRVTRAFIC_PORTES = Arrays.asList("Chapelle", "Bagnolet", "Bercy", "Italie", "Orleans", "Auteuil", "Maillot");

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final User theUser = SessionTools.getUserFromSession(request);
		final VObject theObject = SessionTools.getRabbitFromSession(session);

		final MySrvTrafficForm myForm = (MySrvTrafficForm) form;

		final List<SubscriptionData> subscriptionsList = SubscriptionData.findByApplicationAndObject(Application.NativeApplication.TRAFIC.getApplication(), theObject);
		final int isReg = (subscriptionsList.isEmpty()) ? 0 : 1;

		int passiveAlert = 0;
		int vocal = 0;
		int weekend = 0;

		myForm.setIsReg(isReg);
		myForm.setServiceName(Application.NativeApplication.TRAFIC.getApplication().getName());
		myForm.setDepartList(MySrvTrafficAction.SRVTRAFIC_PORTES);

		for (final SubscriptionData aSubscription : subscriptionsList) {

			final Map<String, Object> theSettings = aSubscription.getSettings();
			myForm.setDepart(theSettings.get(TraficHandler.START).toString());
			myForm.setArrivee(theSettings.get(TraficHandler.END).toString());

			boolean firstFlash = true;
			for (final SubscriptionSchedulingData scheduling : SubscriptionSchedulingData.findAllBySubscription(aSubscription)) {
				if (SchedulingType.SCHEDULING_TYPE.Ambiant == scheduling.getType()) {
					passiveAlert = 1;
				}
				if (SchedulingType.SCHEDULING_TYPE.VoiceTrigger == scheduling.getType()) {
					vocal = 1;
				}
				if (SchedulingType.SCHEDULING_TYPE.Daily == scheduling.getType()) {

					final SchedulingAtomData atom = scheduling.getSchedulingAtom(DailyHandler.Weekday.MONDAY, theUser.getTimezone().getJavaTimeZone());
					if (firstFlash) {
						myForm.setHorraire1(atom.getTimeFormated(theUser.use24()));
						firstFlash = false;
						if (SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(scheduling, DailyHandler.Weekday.SUNDAY.getValue()) == null) {
							weekend = 1;
						}
					} else {
						myForm.setHorraire2(atom.getTimeFormated(theUser.use24()));
					}

				}

			}

		}

		myForm.setLumiere(passiveAlert);
		myForm.setVocal(vocal);
		myForm.setWeekEnd(weekend);

		return mapping.getInputForward();
	}

	public ActionForward activate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvTrafficForm myForm = (MySrvTrafficForm) form;

		try {
			TraficHandler.createOrUpdateSubscription(null, VObjectData.getData(theObject), myForm.getDepart(), myForm.getArrivee(), myForm.getLumiere() > 0, Arrays.asList(new String[] { myForm.getHorraire1(), myForm.getHorraire2() }), !(myForm.getWeekEnd() > 0));
		} catch (final Exception e) {
			MySrvTrafficAction.LOGGER.fatal(e, e);
		}

		return load(mapping, form, request, response);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvTrafficForm myForm = (MySrvTrafficForm) form;

		// the new version
		for (final SubscriptionData subscription : SubscriptionData.findByApplicationAndObject(Application.NativeApplication.TRAFIC.getApplication(), theObject)) {
			try {
				TraficHandler.createOrUpdateSubscription(subscription, VObjectData.getData(theObject), myForm.getDepart(), myForm.getArrivee(), myForm.getLumiere() > 0, Arrays.asList(new String[] { myForm.getHorraire1(), myForm.getHorraire2() }), !(myForm.getWeekEnd() > 0));
			} catch (final Exception e) {
				MySrvTrafficAction.LOGGER.fatal(e, e);
			}
		}

		return load(mapping, form, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);

		for (final SubscriptionData aSubscription : SubscriptionData.findByApplicationAndObject(Application.NativeApplication.TRAFIC.getApplication(), theObject)) {
			try {
				SubscriptionManager.deleteSubscription(aSubscription);
			} catch (final Exception e) {
				MySrvTrafficAction.LOGGER.fatal(e, e);
			}
		}

		return load(mapping, form, request, response);
	}

}

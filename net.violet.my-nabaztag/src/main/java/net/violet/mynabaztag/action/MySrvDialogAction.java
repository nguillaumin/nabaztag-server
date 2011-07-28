package net.violet.mynabaztag.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvDialogForm;
import net.violet.platform.applications.EarsCommunionHandler;
import net.violet.platform.applications.SubscriptionManager;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Notification;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Notification.NOTIFICATION_STATUS;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.NotificationData;
import net.violet.platform.dataobjects.SrvDialogData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.struts.DispatchActionForLoggedUserWithObject;
import net.violet.platform.util.SessionTools;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class MySrvDialogAction extends DispatchActionForLoggedUserWithObject {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(MySrvDialogAction.class);

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvDialogForm myForm = (MySrvDialogForm) form;

		final Application earsCommunion = Application.NativeApplication.EARS_COMMUNION.getApplication();

		final List<NotificationData> theNotifications = NotificationData.getReceived(VObjectData.getData(theObject), 0, 0);

		myForm.setServiceName(earsCommunion.getProfile().getTitle());

		int hasSomethingToDisplay = (theNotifications.size() > 0) ? 1 : 0;
		int isMarried = 0;
		int isWaiting = 0;

		String friendName = myForm.getFriendName();
		int friendId = myForm.getFriendId();

		SrvDialogData refused = null;
		SrvDialogData separated = null;
		SrvDialogData accepted = null;
		final List<SrvDialogData> waiting = new ArrayList<SrvDialogData>();
		final List<SrvDialogData> cancelled = new ArrayList<SrvDialogData>();

		for (final NotificationData notification : theNotifications) {

			final String theStatus = notification.getStatus();

			final VObject friendObject = notification.getSender().getReference();

			if (NOTIFICATION_STATUS.REJECTED.toString().equals(theStatus)) {
				refused = new SrvDialogData(theObject, friendObject, notification);
			} else if (NOTIFICATION_STATUS.FINISHED.toString().equals(theStatus)) {
				separated = new SrvDialogData(theObject, friendObject, notification);
			} else if (NOTIFICATION_STATUS.PENDING.toString().equals(theStatus)) {
				waiting.add(new SrvDialogData(friendObject, theObject, notification));
			} else if (NOTIFICATION_STATUS.CANCELLED.toString().equals(theStatus)) {
				cancelled.add(new SrvDialogData(theObject, friendObject, notification));
			} else if (NOTIFICATION_STATUS.ACCEPTED.toString().equals(theStatus)) {
				accepted = new SrvDialogData(theObject, friendObject, notification);
			}
		}

		final List<Subscription> theSubscriptions = Factories.SUBSCRIPTION.findByApplicationAndObject(earsCommunion, theObject);

		if (!theSubscriptions.isEmpty()) {
			final Map<String, Object> theSettings = theSubscriptions.get(0).getSettings();
			final VObjectData theFriendObject = VObjectData.find(Long.parseLong(theSettings.get(EarsCommunionHandler.FRIEND_OBJECT_ID).toString()));
			friendId = (int) theFriendObject.getId();
			friendName = theFriendObject.getObject_login();
			if (NOTIFICATION_STATUS.ACCEPTED.toString().equals(theSettings.get(EarsCommunionHandler.STATUS))) {
				isMarried = 1;
			} else {
				isWaiting = 1;
			}
		}

		// Info quand on se loggue sur Nabaztaland
		if (hasSomethingToDisplay > 0) {
			myForm.setRefusedInfo(refused);
			myForm.setAcceptedInfo(accepted);
			myForm.setSeparatedInfo(separated);
			myForm.setWaitingList(waiting);
			myForm.setCancelList(cancelled);
		}

		myForm.setFriendId(friendId);
		myForm.setFriendName(friendName);
		myForm.setIsMarried(isMarried);
		myForm.setIsWaiting(isWaiting);
		myForm.setHasSomethingToDisplay(hasSomethingToDisplay);

		return mapping.getInputForward();
	}

	/**
	 * cas ou des demandes de communions ont été effectués, et qu'on les refuse
	 * toutes.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward denyAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);

		new EarsCommunionHandler().denyAllRequests(VObjectData.getData(theObject));

		return load(mapping, form, request, response);
	}

	/**
	 * cas ou des demandes de communions ont été effectués, et qu'on en a
	 * accepté une, on refuse donc toutes les autres par conséquent.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward accept(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvDialogForm myForm = (MySrvDialogForm) form;

		new EarsCommunionHandler().acceptRequest(VObjectData.getData(theObject), myForm.getFriendId());

		return load(mapping, form, request, response);
	}

	/**
	 * cas ou on fait une demande a qq'un
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvDialogForm myForm = (MySrvDialogForm) form;

		int error_same = 0;
		int error_dne = 0; // does not exist

		final String friendName = myForm.getFriendName();
		final VObject theFriend = Factories.VOBJECT.findByName(friendName);
		if (theFriend == null) {
			error_dne = 1;
		} else {
			if (theFriend.getId().longValue() == theObject.getId().longValue()) {
				error_same = 1;
			} else {
				try {
					new EarsCommunionHandler().askForFriendship(VObjectData.getData(theObject), VObjectData.getData(theFriend));
				} catch (final Exception e) {
					MySrvDialogAction.LOGGER.fatal(e, e);
					return load(mapping, form, request, response);
				}
			}
		}
		myForm.setError_dne(error_dne);
		myForm.setError_same(error_same);
		return load(mapping, form, request, response);
	}

	/**
	 * cas ou on divorce ou supprime une notification recu
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final MySrvDialogForm myForm = (MySrvDialogForm) form;
		final long notificationId = myForm.getNotificationId();

		if (notificationId != 0) {
			final Notification theNotification = Factories.NOTIFICATION.find(notificationId);
			if (theNotification != null) {
				theNotification.delete();
			}
		} else {
			final Application earsCommunion = Application.NativeApplication.EARS_COMMUNION.getApplication();

			final List<SubscriptionData> theSubscriptions = SubscriptionData.findByApplicationAndObject(earsCommunion, theObject);

			if (!theSubscriptions.isEmpty()) {
				SubscriptionManager.deleteSubscription(SubscriptionData.findByApplicationAndObject(earsCommunion, theObject).get(0));
			}
		}

		return load(mapping, form, request, response);
	}

	/**
	 * cas ou on annule une demande
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);

		final Application earsCommunion = Application.NativeApplication.EARS_COMMUNION.getApplication();
		SubscriptionManager.deleteSubscription(SubscriptionData.findByApplicationAndObject(earsCommunion, theObject).get(0));

		return load(mapping, form, request, response);
	}

}

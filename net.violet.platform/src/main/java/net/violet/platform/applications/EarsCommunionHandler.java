package net.violet.platform.applications;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.common.StringShop;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.api.exceptions.NoSuchSubscriptionException;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Notification.NOTIFICATION_STATUS;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.NotificationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.message.MessageServices;
import net.violet.platform.util.DicoTools;

import org.apache.log4j.Logger;

public class EarsCommunionHandler implements ApplicationHandler, SettingsEditor, Notifier {

	private static final Logger LOGGER = Logger.getLogger(EarsCommunionHandler.class);

	private static final ApplicationData EAR_APPLICATION = ApplicationData.getData(Application.NativeApplication.EARS_COMMUNION.getApplication());

	public static final String FRIEND_OBJECT_ID = "friend_object_id";
	public static final String STATUS = "status";

	private static final String ACCEPT_MESSAGE = "accept";
	private static final String ASK_MESSAGE = "ask";
	private static final String CANCEL_MESSAGE = "cancel"; // separation
	private static final String REJECT_MESSAGE = "reject";

	public void askForFriendship(VObjectData inObject, VObjectData friend) {

		final Map<String, Object> askerSettings = new HashMap<String, Object>();
		askerSettings.put(EarsCommunionHandler.STATUS, NOTIFICATION_STATUS.PENDING.toString());
		askerSettings.put(EarsCommunionHandler.FRIEND_OBJECT_ID, friend.getId());
		SubscriptionData.create(EarsCommunionHandler.EAR_APPLICATION, inObject, askerSettings);
		try {
			this.add(inObject);
		} catch (final NoSuchSubscriptionException e) {
			EarsCommunionHandler.LOGGER.fatal(e, e);
		}

	}

	public void denyAllRequests(VObjectData inObject) {

		for (final NotificationData aNotification : NotificationData.getReceived(inObject, 0, 0)) {
			if (NOTIFICATION_STATUS.PENDING.toString().equals(aNotification.getStatus())) {
				try {
					this.reject(aNotification.getSender());
				} catch (final NoSuchSubscriptionException e) {
					EarsCommunionHandler.LOGGER.fatal(e, e);
				}
			}
		}
	}

	public void acceptRequest(VObjectData inObject, int friendId) {

		final VObjectData theFriend = VObjectData.getData(Factories.VOBJECT.find(friendId));

		for (final NotificationData aNotification : NotificationData.getReceived(inObject, 0, 0)) {
			if (NOTIFICATION_STATUS.PENDING.toString().equals(aNotification.getStatus()) && aNotification.getSender().equals(theFriend)) {
				try {
					this.accept(theFriend, inObject);
				} catch (final NoSuchSubscriptionException e) {
					EarsCommunionHandler.LOGGER.fatal(e, e);
				}
				break;
			}
		}
	}

	public void checkSettings(VObjectData object, Map<String, Object> settings) throws InvalidSettingException, MissingSettingException {
		if (!settings.containsKey(EarsCommunionHandler.FRIEND_OBJECT_ID)) {
			throw new MissingSettingException(EarsCommunionHandler.FRIEND_OBJECT_ID);
		}
		final String friend_object_id = settings.get(EarsCommunionHandler.FRIEND_OBJECT_ID).toString();
		try {
			final VObject theObject = Factories.VOBJECT.find(Long.parseLong(friend_object_id));
			if (theObject == null) {
				throw new InvalidSettingException(EarsCommunionHandler.FRIEND_OBJECT_ID, friend_object_id);
			}
		} catch (final NumberFormatException e) {
			throw new InvalidSettingException(EarsCommunionHandler.FRIEND_OBJECT_ID, friend_object_id);
		}
		if (!settings.containsKey(EarsCommunionHandler.STATUS)) {
			throw new MissingSettingException(EarsCommunionHandler.STATUS);
		}
	}

	public SubscriptionData create(VObjectData object, Map<String, Object> settings) {
		boolean create = true;
		//temporary : when webui will be changed,remove this code
		final List<Subscription> theSubscription = Factories.SUBSCRIPTION.findByApplicationAndObject(EarsCommunionHandler.EAR_APPLICATION.getReference(), object.getReference());
		if (!theSubscription.isEmpty()) {
			final PojoMap theOldSettings = theSubscription.get(0).getSettings();
			final String theOldFriendObjectId = theOldSettings.get(EarsCommunionHandler.FRIEND_OBJECT_ID).toString();
			if (theOldFriendObjectId.equals(settings.get(EarsCommunionHandler.FRIEND_OBJECT_ID).toString())) {
				create = false;
			}
		}
		return create ? SubscriptionData.create(EarsCommunionHandler.EAR_APPLICATION, object, settings) : SubscriptionData.getData(theSubscription.get(0));
	}

	public void delete(SubscriptionData subscription) {
		subscription.delete();
	}

	public String getSubscriptionInformation(SubscriptionData subscription) {
		return null;
	}

	public Map<String, Object> getUISettings(SubscriptionData subscription, APICaller inApiCaller) {
		final Map<String, Object> settings = subscription.getSettings();
		final Long friend_object_id = Long.parseLong(settings.get(EarsCommunionHandler.FRIEND_OBJECT_ID).toString());
		settings.put(EarsCommunionHandler.FRIEND_OBJECT_ID, VObjectData.find(friend_object_id).getApiId(inApiCaller));
		return settings;
	}

	public void update(SubscriptionData subscription, Map<String, Object> settings) {
		subscription.setSettings(settings);
	}

	public void editSettings(VObjectData object, Map<String, Object> settings, List<Map<String, Object>> originalSchedulings, String callerKey, boolean updateSubscription) {
		originalSchedulings.clear(); // we haven't need this information for this application
		final String friend_object_id = settings.get(EarsCommunionHandler.FRIEND_OBJECT_ID).toString();
		final VObjectData theFriend = VObjectData.findByAPIId(friend_object_id, callerKey);
		if (theFriend != null) {
			settings.put(EarsCommunionHandler.FRIEND_OBJECT_ID, theFriend.getId());

			final List<Subscription> theSubscription = Factories.SUBSCRIPTION.findByApplicationAndObject(EarsCommunionHandler.EAR_APPLICATION.getReference(), object.getReference());
			if (!theSubscription.isEmpty()) { // updating subscription
				final PojoMap theOldSettings = theSubscription.get(0).getSettings();
				final String status = (String) theOldSettings.get(EarsCommunionHandler.STATUS);
				final Long theOldFriendObjectId = Long.parseLong(theOldSettings.get(EarsCommunionHandler.FRIEND_OBJECT_ID).toString());
				if (!theOldFriendObjectId.equals(theFriend.getId())) { // he wants change partner
					try {
						this.remove(object, !updateSubscription);
					} catch (final NoSuchSubscriptionException e) {
						EarsCommunionHandler.LOGGER.fatal(e, e);
					}
					settings.put(EarsCommunionHandler.STATUS, NOTIFICATION_STATUS.PENDING.toString());
				} else { // nothing to do
					settings.put(EarsCommunionHandler.STATUS, status);
				}
			} else { // adding subscription
				settings.put(EarsCommunionHandler.STATUS, NOTIFICATION_STATUS.PENDING.toString());
			}
		}
	}

	public void accept(VObjectData inSender, VObjectData inRecipient) throws NoSuchSubscriptionException {
		List<Subscription> theSubscription = Factories.SUBSCRIPTION.findByApplicationAndObject(EarsCommunionHandler.EAR_APPLICATION.getReference(), inSender.getReference());
		if (!theSubscription.isEmpty()) {
			final Map<String, Object> theSettings = theSubscription.get(0).getSettings();
			theSettings.put(EarsCommunionHandler.STATUS, NOTIFICATION_STATUS.ACCEPTED.toString());

			this.update(SubscriptionData.getData(theSubscription.get(0)), theSettings);

			theSettings.put(EarsCommunionHandler.FRIEND_OBJECT_ID, inSender.getId());
			theSubscription = Factories.SUBSCRIPTION.findByApplicationAndObject(EarsCommunionHandler.EAR_APPLICATION.getReference(), inRecipient.getReference());
			if (!theSubscription.isEmpty()) {
				this.update(SubscriptionData.getData(theSubscription.get(0)), theSettings);
			} else {
				this.create(inRecipient, theSettings);
			}
			NotificationData.sendAcceptNotification(inSender, EarsCommunionHandler.EAR_APPLICATION);
			MessageServices.sendDialogMessage(inRecipient.getOwner().getReference(), EarsCommunionHandler.ACCEPT_MESSAGE, inSender.getReference(),
				inRecipient.getObject_login() + StringShop.SPACE + DicoTools.dico(inSender.getPreferences().getLang().getReference(), "srv_communion/message_accept"));
		} else {
			throw new NoSuchSubscriptionException();
		}
	}

	public void add(VObjectData inObject) throws NoSuchSubscriptionException {
		final List<Subscription> theSubscription = Factories.SUBSCRIPTION.findByApplicationAndObject(EarsCommunionHandler.EAR_APPLICATION.getReference(), inObject.getReference());
		if (!theSubscription.isEmpty()) {
			try {
				final VObjectData theFriendObject = VObjectData.find(theSubscription.get(0).getSettings().getLong(EarsCommunionHandler.FRIEND_OBJECT_ID));
				NotificationData theNotificationData = NotificationData.findPending(theFriendObject, EarsCommunionHandler.EAR_APPLICATION);
				if (theNotificationData.isValid() && theNotificationData.getRecipient().equals(inObject)) { // he has already pending notification for this object
					this.accept(theFriendObject, inObject);
				} else {
					theNotificationData = NotificationData.findPending(inObject, EarsCommunionHandler.EAR_APPLICATION);
					if (theNotificationData.isEmpty() && NOTIFICATION_STATUS.PENDING.toString().equals(theSubscription.get(0).getSettings().getString(EarsCommunionHandler.STATUS))) { // sends just one time a pending notification (updating case)
						NotificationData.sendPendingNotification(inObject, theFriendObject, EarsCommunionHandler.EAR_APPLICATION);
		//				MessageServices.sendDialogMessage(inObject.getOwner().getReference(), EarsCommunionHandler.ASK_MESSAGE, theFriendObject.getReference(),
		//					inObject.getObject_login() + StringShop.SPACE + DicoTools.dico(theFriendObject.getPreferences().getLang().getReference(), "srv_communion/message_request"));
					}
				}
			} catch (final InvalidParameterException e) {
				EarsCommunionHandler.LOGGER.fatal(e, e);
			}
		} else {
			throw new NoSuchSubscriptionException();
		}
	}

	public void reject(VObjectData inSender) throws NoSuchSubscriptionException {
		final List<Subscription> theSubscription = Factories.SUBSCRIPTION.findByApplicationAndObject(EarsCommunionHandler.EAR_APPLICATION.getReference(), inSender.getReference());
		if (!theSubscription.isEmpty()) {
			try {
				final VObjectData theFriendObject = VObjectData.find(theSubscription.get(0).getSettings().getLong(EarsCommunionHandler.FRIEND_OBJECT_ID));
				this.delete(SubscriptionData.getData(theSubscription.get(0)));
				NotificationData.sendRejectNotification(inSender, EarsCommunionHandler.EAR_APPLICATION);
				MessageServices.sendDialogMessage(theFriendObject.getOwner().getReference(), EarsCommunionHandler.REJECT_MESSAGE, inSender.getReference(),
					theFriendObject.getObject_login() + StringShop.SPACE + DicoTools.dico(inSender.getPreferences().getLang().getReference(), "srv_communion/refused_your_demand"));
			} catch (final InvalidParameterException e) {
				EarsCommunionHandler.LOGGER.fatal(e, e);
			}
		} else {
			throw new NoSuchSubscriptionException();
		}
	}

	public void remove(VObjectData inSender, boolean delete) throws NoSuchSubscriptionException {
		List<Subscription> theSubscription = Factories.SUBSCRIPTION.findByApplicationAndObject(EarsCommunionHandler.EAR_APPLICATION.getReference(), inSender.getReference());
		if (!theSubscription.isEmpty()) {
			final Map<String, Object> theSettings = theSubscription.get(0).getSettings();

			if (theSettings.get(EarsCommunionHandler.STATUS).equals(NOTIFICATION_STATUS.ACCEPTED.toString())) {
				final VObjectData theFriendObject = VObjectData.find(Long.parseLong(theSettings.get(EarsCommunionHandler.FRIEND_OBJECT_ID).toString()));
				if (delete) {
					this.delete(SubscriptionData.getData(theSubscription.get(0)));
				}

				theSubscription = Factories.SUBSCRIPTION.findByApplicationAndObject(EarsCommunionHandler.EAR_APPLICATION.getReference(), theFriendObject.getReference());
				if (!theSubscription.isEmpty()) { //delete friend subscription
					this.delete(SubscriptionData.getData(theSubscription.get(0)));
				}

				NotificationData.sendFinishNotification(inSender, theFriendObject, EarsCommunionHandler.EAR_APPLICATION);
				MessageServices.sendDialogMessage(inSender.getOwner().getReference(), EarsCommunionHandler.CANCEL_MESSAGE, theFriendObject.getReference(),
					inSender.getObject_login() + StringShop.SPACE + DicoTools.dico(theFriendObject.getPreferences().getLang().getReference(), "srv_communion/message_divorce"));
			} else {
				this.cancel(inSender, delete);
			}
		} else {
			throw new NoSuchSubscriptionException();
		}
	}

	public void cancel(VObjectData inSender, boolean delete) throws NoSuchSubscriptionException {
		final List<Subscription> theSubscription = Factories.SUBSCRIPTION.findByApplicationAndObject(EarsCommunionHandler.EAR_APPLICATION.getReference(), inSender.getReference());
		if (!theSubscription.isEmpty()) {
			if (delete) {
				this.delete(SubscriptionData.getData(theSubscription.get(0)));
			}
			NotificationData.sendCancelNotification(inSender, EarsCommunionHandler.EAR_APPLICATION);
		} else {
			throw new NoSuchSubscriptionException();
		}
	}
}

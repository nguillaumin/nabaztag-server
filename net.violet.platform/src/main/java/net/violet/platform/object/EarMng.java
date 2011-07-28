/*
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.violet.platform.object;

import java.util.List;

import net.violet.platform.applications.EarsCommunionHandler;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Notification.NOTIFICATION_STATUS;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.MessageServices;
import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.JabberMessageFactory;

public class EarMng {

	public static void earNotify(VObject object, int left, int right) {
		if (object != null) {
			object.setLeftAndRight(left, right);

			if (object.isXMPP()) {
				EarMng.sendNotifyEars(object, left, right);
			}

			final Application earsCommunion = Application.NativeApplication.EARS_COMMUNION.getApplication();
			final List<SubscriptionData> subscriptions = SubscriptionData.findByApplicationAndObject(earsCommunion, object);

			for (final SubscriptionData aSubscription : subscriptions) {
				final Object objectSubscriptionSetting = aSubscription.getSettings().get(EarsCommunionHandler.STATUS);
				if (NOTIFICATION_STATUS.ACCEPTED.toString().equals(objectSubscriptionSetting.toString())) {

					final VObject friendObject = Factories.VOBJECT.find(Long.parseLong(aSubscription.getSettings().get(EarsCommunionHandler.FRIEND_OBJECT_ID).toString()));
					if (friendObject != null) {
						friendObject.setLeftAndRight(left, right);

						if (friendObject.isXMPP()) {// si l'objet communié est
							// en mode jabber
							EarMng.sendNotifyEars(friendObject, left, right);
						}
					}
				}
			}

		}
	}

	private static void sendNotifyEars(VObject inObject, int inLeft, int inRight) {
		final MessageDraft theMessage = new MessageDraft(inObject);
		theMessage.setEars(inLeft, inRight);
		theMessage.setSourceUpdate(true);
		theMessage.setTTLInSecond(Constantes.QUEUE_TTL_SOURCES);
		MessageServices.sendUsingXmpp(theMessage, JabberMessageFactory.SOURCES_MODE);
	}

}

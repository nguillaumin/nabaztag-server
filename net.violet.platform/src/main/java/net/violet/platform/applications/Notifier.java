package net.violet.platform.applications;

import net.violet.platform.api.exceptions.NoSuchSubscriptionException;
import net.violet.platform.dataobjects.VObjectData;

/**
 * Notifier manages the sending of notification when adding an application.
 */
public interface Notifier {

	void add(VObjectData inSender) throws NoSuchSubscriptionException;

	void accept(VObjectData inSender, VObjectData inRecipient) throws NoSuchSubscriptionException;

	void reject(VObjectData inSender) throws NoSuchSubscriptionException;

	void remove(VObjectData inSender, boolean delete) throws NoSuchSubscriptionException;

	void cancel(VObjectData inSender, boolean delete) throws NoSuchSubscriptionException;

}

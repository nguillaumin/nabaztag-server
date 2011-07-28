package net.violet.platform.dataobjects;

import net.violet.platform.datamodel.VObject;

public final class SrvDialogData {

	private final VObjectData friend;
	private final VObjectData initiator;
	private final NotificationData notification;

	public SrvDialogData(VObject inInitiator, VObject inFriend, NotificationData inNotificationData) {
		this.initiator = VObjectData.getData(inInitiator);
		this.friend = VObjectData.getData(inFriend);
		this.notification = inNotificationData;

	}

	@Override
	public String toString() {
		String string = "Initiator : " + this.initiator.getReference().getId() + "\n";
		string += "Friend : " + this.friend.getReference().getId() + "\n";
		return string;
	}

	/**
	 * @return the obj_id attribute AKA srvdialog_obj
	 */
	public long getObj_id() {
		return this.initiator.getId();
	}

	/**
	 * @return the obj_name attribute
	 */
	public String getObj_name() {
		return this.initiator.getObject_login();
	}

	public String getFriend_name() {
		return this.friend.getObject_login();
	}

	/**
	 * @return the friend attribute
	 */
	public VObjectData getFriend() {
		return this.friend;
	}

	public NotificationData getNotification() {
		return this.notification;
	}

	/**
	 * @return the object attribute
	 */
	public VObjectData getObject() {
		return this.initiator;
	}

	public static final long hasSomethingToDisplay(VObject object) {
		return NotificationData.getReceived(VObjectData.getData(object), 0, 0).size() > 0 ? 1 : 0;
	}
}

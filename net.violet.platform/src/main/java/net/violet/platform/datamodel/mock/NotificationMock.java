package net.violet.platform.datamodel.mock;

import java.sql.Timestamp;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Notification;
import net.violet.platform.datamodel.VObject;

public class NotificationMock extends AbstractMockRecord<Notification, NotificationMock> implements Notification {

	private final NOTIFICATION_STATUS mStatus;
	private final Timestamp mCreation_time;
	private final VObject mSender;
	private final VObject mRecipient;
	private final Application mApplication;

	public NotificationMock(long inId, VObject inSender, VObject inRecipient, Application inApplication, NOTIFICATION_STATUS inStatus) {
		super(inId);
		this.mSender = inSender;
		this.mRecipient = inRecipient;
		this.mStatus = inStatus;
		this.mApplication = inApplication;
		this.mCreation_time = new Timestamp(System.currentTimeMillis());
	}

	public Application getApplication() {
		return this.mApplication;
	}

	public Timestamp getNotificationDate() {
		return this.mCreation_time;
	}

	public VObject getRecipient() {
		return this.mRecipient;
	}

	public VObject getSender() {
		return this.mSender;
	}

	public String getStatus() {
		return this.mStatus.toString();
	}
}

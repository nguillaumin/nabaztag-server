package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.NoSuchElementException;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;

public class NotificationImpl extends ObjectRecord<Notification, NotificationImpl> implements Notification {

	public static final SQLObjectSpecification<NotificationImpl> SPECIFICATION = new SQLObjectSpecification<NotificationImpl>("notifications", NotificationImpl.class, new SQLKey[] { new SQLKey("id") });

	private static final String[] NEW_COLUMNS = new String[] { "sender_id", "application_id", "recipient_id", "status", "creation_time", };

	protected long id;
	protected long sender_id;
	protected long recipient_id;
	protected long application_id;
	protected String status;
	protected Timestamp creation_time;

	private final SingleAssociationNotNull<Notification, VObject, VObjectImpl> mSender;

	private final SingleAssociationNotNull<Notification, VObject, VObjectImpl> mRecipient;

	private final SingleAssociationNotNull<Notification, Application, ApplicationImpl> mApplication;

	protected NotificationImpl() {
		this.mSender = new SingleAssociationNotNull<Notification, VObject, VObjectImpl>(this, "sender_id", VObjectImpl.SPECIFICATION);
		this.mRecipient = new SingleAssociationNotNull<Notification, VObject, VObjectImpl>(this, "recipient_id", VObjectImpl.SPECIFICATION);
		this.mApplication = new SingleAssociationNotNull<Notification, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION);
	}

	protected NotificationImpl(long id) throws NoSuchElementException, SQLException {
		init(id);
		this.mSender = new SingleAssociationNotNull<Notification, VObject, VObjectImpl>(this, "sender_id", VObjectImpl.SPECIFICATION);
		this.mRecipient = new SingleAssociationNotNull<Notification, VObject, VObjectImpl>(this, "recipient_id", VObjectImpl.SPECIFICATION);
		this.mApplication = new SingleAssociationNotNull<Notification, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION);
	}

	public NotificationImpl(VObject inSender, VObject inRecipient, Application inApplication, NOTIFICATION_STATUS inStatus) throws SQLException {

		this.sender_id = inSender.getId();
		this.recipient_id = inRecipient.getId();
		this.application_id = inApplication.getId();
		this.status = inStatus.toString();
		this.creation_time = new Timestamp(System.currentTimeMillis());

		this.mSender = new SingleAssociationNotNull<Notification, VObject, VObjectImpl>(this, "sender_id", VObjectImpl.SPECIFICATION);
		this.mRecipient = new SingleAssociationNotNull<Notification, VObject, VObjectImpl>(this, "recipient_id", VObjectImpl.SPECIFICATION);
		this.mApplication = new SingleAssociationNotNull<Notification, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION);
		init(NotificationImpl.NEW_COLUMNS);
	}

	@Override
	public SQLObjectSpecification<NotificationImpl> getSpecification() {
		return NotificationImpl.SPECIFICATION;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public VObject getSender() {
		return this.mSender.get(this.sender_id);
	}

	public VObject getRecipient() {
		return this.mRecipient.get(this.recipient_id);
	}

	public Application getApplication() {
		return this.mApplication.get(this.application_id);
	}

	public Timestamp getNotificationDate() {
		return this.creation_time;
	}

	public String getStatus() {
		return this.status;
	}

}

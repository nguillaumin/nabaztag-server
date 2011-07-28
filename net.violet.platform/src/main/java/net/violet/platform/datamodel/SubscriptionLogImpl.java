package net.violet.platform.datamodel;

import java.sql.SQLException;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.platform.datamodel.Files.CATEGORIES;
import net.violet.platform.datamodel.factories.Factories;

public class SubscriptionLogImpl extends ObjectRecord<SubscriptionLog, SubscriptionLogImpl> implements SubscriptionLog {

	public static final SQLObjectSpecification<SubscriptionLogImpl> SPECIFICATION = new SQLObjectSpecification<SubscriptionLogImpl>("subscription_logs", SubscriptionLogImpl.class, new SQLKey("subscription_id"));
	private static final String[] NEW_COLUMNS = new String[] { "subscription_id", "log_file_id" };

	protected long subscription_id;
	protected long log_file_id;

	private final SingleAssociationNotNull<SubscriptionLog, Subscription, SubscriptionImpl> subscription;
	private final SingleAssociationNotNull<SubscriptionLog, Files, FilesImpl> logFile;

	protected SubscriptionLogImpl(long id) throws SQLException {
		init(id);
		this.subscription = new SingleAssociationNotNull<SubscriptionLog, Subscription, SubscriptionImpl>(this, "subscription_id", SubscriptionImpl.SPECIFICATION, SubscriptionImpl.SPECIFICATION.getTableKeys()[0]);
		this.logFile = new SingleAssociationNotNull<SubscriptionLog, Files, FilesImpl>(this, "log_file_id", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
	}

	protected SubscriptionLogImpl() {
		this.subscription = new SingleAssociationNotNull<SubscriptionLog, Subscription, SubscriptionImpl>(this, "subscription_id", SubscriptionImpl.SPECIFICATION, SubscriptionImpl.SPECIFICATION.getTableKeys()[0]);
		this.logFile = new SingleAssociationNotNull<SubscriptionLog, Files, FilesImpl>(this, "log_file_id", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
	}

	public SubscriptionLogImpl(Subscription subscription, Files file) throws SQLException {
		this.subscription_id = subscription.getId();
		this.log_file_id = file.getId();

		init(SubscriptionLogImpl.NEW_COLUMNS);

		this.subscription = new SingleAssociationNotNull<SubscriptionLog, Subscription, SubscriptionImpl>(this, "subscription_id", SubscriptionImpl.SPECIFICATION, SubscriptionImpl.SPECIFICATION.getTableKeys()[0]);
		this.logFile = new SingleAssociationNotNull<SubscriptionLog, Files, FilesImpl>(this, "log_file_id", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
	}

	public SubscriptionLogImpl(Subscription subscription) throws SQLException {
		this(subscription, Factories.FILES.createFile(MimeType.MIME_TYPES.JSON, CATEGORIES.BROAD));
	}

	@Override
	public Long getId() {
		return this.subscription_id;
	}

	@Override
	public SQLObjectSpecification<SubscriptionLogImpl> getSpecification() {
		return SubscriptionLogImpl.SPECIFICATION;
	}

	public Files getLogFile() {
		return this.logFile.get(this.log_file_id);
	}

	public Subscription getSubscription() {
		return this.subscription.get(this.subscription_id);
	}

	@Override
	protected void doDelete() throws SQLException {
		getLogFile().scheduleDeletion();
		super.doDelete();
	}

}

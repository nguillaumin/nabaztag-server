package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionLog;
import net.violet.platform.datamodel.Files.CATEGORIES;
import net.violet.platform.datamodel.factories.Factories;

public class SubscriptionLogMock extends AbstractMockRecord<SubscriptionLog, SubscriptionLogMock> implements SubscriptionLog {

	protected long log_file_id;

	public SubscriptionLogMock(Subscription subscription, Files file) {
		super(subscription.getId());
		this.log_file_id = file.getId();
	}

	public SubscriptionLogMock(Subscription subscription) {
		super(subscription.getId());
		Factories.FILES.createFile(MimeType.MIME_TYPES.JSON, CATEGORIES.BROAD);
		this.log_file_id = Factories.FILES.createFile(MimeType.MIME_TYPES.JSON, CATEGORIES.BROAD).getId();
	}

	public Files getLogFile() {
		return Factories.FILES.find(this.log_file_id);
	}

	public Subscription getSubscription() {
		return Factories.SUBSCRIPTION.find(this.getId());
	}

}

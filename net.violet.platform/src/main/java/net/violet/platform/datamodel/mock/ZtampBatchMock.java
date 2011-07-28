package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ZtampBatch;

public class ZtampBatchMock extends AbstractMockRecord<ZtampBatch, ZtampBatchMock> implements ZtampBatch {

	private final long appletId;
	private final String param;
	private final String prefix;

	public ZtampBatchMock(long inId, Application application, String param, String prefix) {
		super(inId);
		this.appletId = application.getId();
		this.param = param;
		this.prefix = prefix;
	}

	public Long getDefaultAppletId() {
		return this.appletId;
	}

	public String getDefaultAppletParam() {
		return this.param;
	}

	public String getNamePrefix() {
		return this.prefix;
	}

}

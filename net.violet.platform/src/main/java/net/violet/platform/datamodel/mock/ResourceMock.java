package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.ApplicationContent;
import net.violet.platform.datamodel.Resource;
import net.violet.platform.datamodel.factories.Factories;

public class ResourceMock extends AbstractMockRecord<Resource, ResourceMock> implements Resource {

	private final String path;
	private final long contentId;

	protected ResourceMock(long inContentId, String inPath) {
		super(0L);
		this.path = inPath;
		this.contentId = inContentId;
	}

	public ApplicationContent getContent() {
		return Factories.APPLICATION_CONTENT.find(this.contentId);
	}

	public String getPath() {
		return this.path;
	}

}

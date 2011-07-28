package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationContent;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.factories.Factories;

public class ApplicationContentMock extends AbstractMockRecord<ApplicationContent, ApplicationContentMock> implements ApplicationContent {

	private final long applicationId;
	private final long filesId;

	public ApplicationContentMock(long inApplicationId, long inFilesId) {
		super(0L);
		this.applicationId = inApplicationId;
		this.filesId = inFilesId;
	}

	public ApplicationContentMock(Application inApplication, Files inFiles) {
		this(inApplication.getId(), inFiles.getId());
	}

	public Application getApplication() {
		return Factories.APPLICATION.find(this.applicationId);
	}

	public Files getFiles() {
		return Factories.FILES.find(this.filesId);
	}

}

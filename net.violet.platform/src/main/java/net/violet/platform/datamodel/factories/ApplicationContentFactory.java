package net.violet.platform.datamodel.factories;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationContent;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.factories.common.FilesAccessor;

public interface ApplicationContentFactory extends RecordFactory<ApplicationContent>, FilesAccessor {

	ApplicationContent create(Application inApplication, Files inFile);
}

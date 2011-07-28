package net.violet.platform.datamodel;

import net.violet.db.records.Record;

public interface ApplicationContent extends Record<ApplicationContent> {

	Application getApplication();

	Files getFiles();

}

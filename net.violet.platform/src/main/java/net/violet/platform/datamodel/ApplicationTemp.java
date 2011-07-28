package net.violet.platform.datamodel;

import net.violet.db.records.Record;

public interface ApplicationTemp extends Record<ApplicationTemp> {

	String getLink();

	String getShortcut();

	String getImage();

	String getIcone();

	long getType();

	Application getApplication();

	void setShorcut(String shorcut);

	void setLink(String link);
}

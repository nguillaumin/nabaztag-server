package net.violet.platform.datamodel;

import net.violet.db.records.Record;

public interface ApplicationSetting extends Record<ApplicationSetting> {

	class FeedHandler {

		public static final String URL = "url";
		public static final String FEED_ID = "feed_id";
	}

	class Player {

		public static final String APPLET_ID = "appletId";
	}

	String getKey();

	String getValue();

	void setKey(String key);

	void setValue(String value);

	Application getApplication();

}

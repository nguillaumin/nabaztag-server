package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.UserPrefs;
import net.violet.platform.datamodel.factories.UserPrefsFactory;
import net.violet.platform.datamodel.mock.UserPrefsMock;

public class UserPrefsFactoryMock extends RecordFactoryMock<UserPrefs, UserPrefsMock> implements UserPrefsFactory {

	UserPrefsFactoryMock() {
		super(UserPrefsMock.class);
	}

}

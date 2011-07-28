package net.violet.platform.datamodel.factories.implementations;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.UserPrefs;
import net.violet.platform.datamodel.UserPrefsImpl;
import net.violet.platform.datamodel.factories.UserPrefsFactory;

public class UserPrefsFactoryImpl extends RecordFactoryImpl<UserPrefs, UserPrefsImpl> implements UserPrefsFactory {

	UserPrefsFactoryImpl() {
		super(UserPrefsImpl.SPECIFICATION);
	}

}

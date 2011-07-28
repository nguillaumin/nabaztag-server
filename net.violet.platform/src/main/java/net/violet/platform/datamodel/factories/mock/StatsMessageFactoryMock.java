package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.StatsMessage;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.StatsMessageFactory;
import net.violet.platform.datamodel.mock.StatsMessageMock;

public class StatsMessageFactoryMock extends RecordFactoryMock<StatsMessage, StatsMessageMock> implements StatsMessageFactory {

	StatsMessageFactoryMock() {
		super(StatsMessageMock.class);
	}

	public void insert(User user, VObject object, String canal) {
	}

}

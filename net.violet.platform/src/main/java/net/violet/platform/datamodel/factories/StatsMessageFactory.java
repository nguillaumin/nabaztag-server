package net.violet.platform.datamodel.factories;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.StatsMessage;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;

public interface StatsMessageFactory extends RecordFactory<StatsMessage> {

	void insert(User user, VObject object, String canal);
}

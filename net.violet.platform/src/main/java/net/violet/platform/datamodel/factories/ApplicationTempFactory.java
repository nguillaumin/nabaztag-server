package net.violet.platform.datamodel.factories;

import java.sql.SQLException;
import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationTemp;

public interface ApplicationTempFactory extends RecordFactory<ApplicationTemp> {

	List<ApplicationTemp> findAll();

	ApplicationTemp create(Application record, String inTypeName, String shortcut, String inLink) throws SQLException;
}

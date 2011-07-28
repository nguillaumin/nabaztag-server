package net.violet.platform.datamodel.factories;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Ztamp;
import net.violet.platform.datamodel.factories.common.FilesAccessor;

public interface ZtampFactory extends RecordFactory<Ztamp>, FilesAccessor {

	Ztamp findBySerial(String serial);

}

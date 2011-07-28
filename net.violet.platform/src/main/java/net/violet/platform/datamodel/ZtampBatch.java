package net.violet.platform.datamodel;

import net.violet.db.records.Record;

public interface ZtampBatch extends Record<ZtampBatch> {

	Long getDefaultAppletId();

	String getDefaultAppletParam();

	String getNamePrefix();

}

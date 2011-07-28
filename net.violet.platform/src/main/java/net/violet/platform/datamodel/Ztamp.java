package net.violet.platform.datamodel;

import net.violet.db.records.Record;
import net.violet.platform.datamodel.Hardware.HARDWARE;

public interface Ztamp extends Record<Ztamp> {

	ZtampBatch getBatch();

	String getSerial();

	Files getVisual();

	HARDWARE getHardware();

	void setBatch(ZtampBatch theBatch);

	boolean isDuplicated();
}

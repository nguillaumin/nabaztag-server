package net.violet.platform.datamodel;

import net.violet.db.records.Record;
import net.violet.platform.datamodel.Hardware.HARDWARE;

/**
 * References Violet's stampz so that they can be
 * distinguished from other RFID tags (which can also be used).
 * 
 *
 */
public interface Ztamp extends Record<Ztamp> {

	ZtampBatch getBatch();

	String getSerial();

	Files getVisual();

	HARDWARE getHardware();

	void setBatch(ZtampBatch theBatch);

	boolean isDuplicated();
}

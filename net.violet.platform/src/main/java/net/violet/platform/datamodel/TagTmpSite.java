package net.violet.platform.datamodel;

import net.violet.db.records.Record;
import net.violet.platform.datamodel.Hardware.HARDWARE;

public interface TagTmpSite extends Record<TagTmpSite> {

	HARDWARE getHardware();

	long getLast_day();

	String getLoc();

	String getSerial();

	String getIp();

	void setLast_day(long inTime);

}

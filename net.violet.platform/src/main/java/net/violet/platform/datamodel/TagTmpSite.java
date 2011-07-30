package net.violet.platform.datamodel;

import net.violet.db.records.Record;
import net.violet.platform.datamodel.Hardware.HARDWARE;

/**
 * Temporary records objects connected to the platform
 * but that doesn't have a Violet account.
 * 
 *
 */
public interface TagTmpSite extends Record<TagTmpSite> {

	HARDWARE getHardware();

	long getLast_day();

	String getLoc();

	String getSerial();

	String getIp();

	void setLast_day(long inTime);

}

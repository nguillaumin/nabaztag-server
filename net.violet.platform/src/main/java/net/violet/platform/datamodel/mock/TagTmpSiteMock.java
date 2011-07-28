/**
 * 
 */
package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.TagTmpSite;
import net.violet.platform.datamodel.Hardware.HARDWARE;

/**
 * @author slorg1
 */
public class TagTmpSiteMock extends AbstractMockRecord<TagTmpSite, TagTmpSiteMock> implements TagTmpSite {

	private final String mSerial;
	private final HARDWARE mHardware;

	public TagTmpSiteMock(long inId, String inSerial, HARDWARE inHardware) {
		super(inId);
		this.mSerial = inSerial;
		this.mHardware = inHardware;
	}

	public HARDWARE getHardware() {
		return this.mHardware;
	}

	public String getIp() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getLast_day() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getLoc() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSerial() {
		return this.mSerial;
	}

	public void setLast_day(long inTime) {
		// TODO Auto-generated method stub

	}

}

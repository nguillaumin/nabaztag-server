package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Ztamp;
import net.violet.platform.datamodel.ZtampBatch;
import net.violet.platform.datamodel.Hardware.HARDWARE;

/**
 * @author slorg1
 */
public class ZtampMock extends AbstractMockRecord<Ztamp, ZtampMock> implements Ztamp {

	private final String mSerial;
	private final HARDWARE mHardware;
	private ZtampBatch mZtampBatch;
	private final Files mVisual;

	public ZtampMock(long inId, String inSerial, HARDWARE inHardware, ZtampBatch inBatch, Files inVisual) {
		super(inId);
		this.mSerial = inSerial;
		this.mHardware = inHardware;
		this.mZtampBatch = inBatch;
		this.mVisual = inVisual;
	}

	public ZtampBatch getBatch() {
		return this.mZtampBatch;
	}

	public HARDWARE getHardware() {
		return this.mHardware;
	}

	public String getSerial() {
		return this.mSerial;
	}

	public Files getVisual() {
		return this.mVisual;
	}

	public void setBatch(ZtampBatch theBatch) {
		this.mZtampBatch = theBatch;
	}

	public boolean isDuplicated() {
		return false;
	}

}

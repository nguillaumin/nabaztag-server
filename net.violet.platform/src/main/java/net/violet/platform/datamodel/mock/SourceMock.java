package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Source;

public class SourceMock extends AbstractMockRecord<Source, SourceMock> implements Source {

	private final String mPath;
	private long mVal;
	private long mTime;
	private final long mSrv;

	public SourceMock(long inId, String path, long val, long time) {
		this(inId, path, val, time, 0);
	}

	public SourceMock(long inId, String path, long val, long time, long srv) {
		super(inId);
		this.mPath = path;
		this.mVal = val;
		this.mTime = time;
		this.mSrv = srv;
	}

	public SourceMock(long inId, String path, long val) {
		super(inId);
		this.mPath = path;
		this.mVal = val;
		this.mTime = 0;
		this.mSrv = 0;
	}

	public String getSource_dico() {
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public long getSource_id() {
		return this.getId();
	}

	public String getSource_path() {
		return this.mPath;
	}

	public long getSource_srv() {
		return this.mSrv;
	}

	public long getSource_time() {
		return this.mTime;
	}

	public long getSource_val() {
		return this.mVal;
	}

	public void setTime(long time) {
		this.mTime = time;

	}

	public void setVal(int val) {
		this.mVal = val;
	}

}

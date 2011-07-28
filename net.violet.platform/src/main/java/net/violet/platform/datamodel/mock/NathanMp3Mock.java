package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.NathanMp3;
import net.violet.platform.datamodel.NathanVersion;

public class NathanMp3Mock extends AbstractMockRecord<NathanMp3, NathanMp3Mock> implements NathanMp3 {

	private final NathanVersion mNathanVersion;
	private final Files mFile;
	private final int mOffset;

	public NathanMp3Mock(long inId, NathanVersion inVersion, Files inFile, int offset) {
		super(inId);
		this.mNathanVersion = inVersion;
		this.mFile = inFile;
		this.mOffset = offset;
	}

	public Files getFile() {
		return this.mFile;
	}

	public NathanVersion getNathanVersion() {
		return this.mNathanVersion;
	}

	public int getOffset() {
		return this.mOffset;
	}

}

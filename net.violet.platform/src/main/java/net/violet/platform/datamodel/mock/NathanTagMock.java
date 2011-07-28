package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.NathanTag;

public class NathanTagMock extends AbstractMockRecord<NathanTag, NathanTagMock> implements NathanTag {

	protected NathanTagMock(long inId) {
		super(inId);
	}

	public Long getCateg() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}

package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.NabcastCateg;

public class NabcastCategMock extends AbstractMockRecord<NabcastCateg, NabcastCategMock> implements NabcastCateg {

	protected NabcastCategMock(long inId) {
		super(inId);
		// TODO Auto-generated constructor stub
	}

	public String getNabcastcateg_desc() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getNabcastcateg_id() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getNabcastcateg_lang() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getNabcastcateg_val() {
		// TODO Auto-generated method stub
		return null;
	}

}

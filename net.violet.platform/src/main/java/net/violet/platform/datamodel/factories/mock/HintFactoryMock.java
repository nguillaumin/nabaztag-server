package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Hint;
import net.violet.platform.datamodel.factories.HintFactory;
import net.violet.platform.datamodel.mock.HintMock;

public class HintFactoryMock extends RecordFactoryMock<Hint, HintMock> implements HintFactory {

	public HintFactoryMock() {
		super(HintMock.class);
	}

}

package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Categ;
import net.violet.platform.datamodel.factories.CategFactory;
import net.violet.platform.datamodel.mock.CategMock;

public class CategFactoryMock extends RecordFactoryMock<Categ, CategMock> implements CategFactory {

	public CategFactoryMock() {
		super(CategMock.class);
	}

	public Categ createNewCateg(long id) {
		return new CategMock(id);
	}

	public Categ findByName(String inType) {
		for (final Categ inCateg : findAll()) {
			if (inType.equals(inCateg.getName())) {
				return inCateg;
			}
		}
		return null;
	}
}

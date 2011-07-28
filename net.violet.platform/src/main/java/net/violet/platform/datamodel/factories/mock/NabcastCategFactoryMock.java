package net.violet.platform.datamodel.factories.mock;

import java.util.ArrayList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.NabcastCateg;
import net.violet.platform.datamodel.factories.NabcastCategFactory;
import net.violet.platform.datamodel.mock.NabcastCategMock;

public class NabcastCategFactoryMock extends RecordFactoryMock<NabcastCateg, NabcastCategMock> implements NabcastCategFactory {

	public NabcastCategFactoryMock() {
		super(NabcastCategMock.class);
	}

	public List<NabcastCateg> getNabcastCateg(long lang) {
		// TODO: fill up.
		return new ArrayList<NabcastCateg>();
	}

	public NabcastCateg findByName(String name) {
		// TODO: fill up.
		return null;
	}

	public List<NabcastCateg> findAllByLang(Lang inLang) {
		// TODO: fill up.
		return new ArrayList<NabcastCateg>();
	}
}

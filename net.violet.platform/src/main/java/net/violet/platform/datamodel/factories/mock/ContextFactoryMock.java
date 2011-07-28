package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Context;
import net.violet.platform.datamodel.factories.ContextFactory;
import net.violet.platform.datamodel.mock.ContextMock;

public class ContextFactoryMock extends RecordFactoryMock<Context, ContextMock> implements ContextFactory {

	public ContextFactoryMock() {
		super(ContextMock.class);
	}

	public Context findByName(String inName) {
		for (final Context theContext : findAll()) {
			if (theContext.getName().equals(inName)) {
				return theContext;
			}
		}
		return null;
	}

}

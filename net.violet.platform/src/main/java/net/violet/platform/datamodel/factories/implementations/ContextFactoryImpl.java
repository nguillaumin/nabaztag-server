package net.violet.platform.datamodel.factories.implementations;

import java.util.Collections;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Context;
import net.violet.platform.datamodel.ContextImpl;
import net.violet.platform.datamodel.factories.ContextFactory;

public class ContextFactoryImpl extends RecordFactoryImpl<Context, ContextImpl> implements ContextFactory {

	public ContextFactoryImpl() {
		super(ContextImpl.SPECIFICATION);
	}

	public Context findByName(String inName) {
		return find(" name = ? ", Collections.singletonList((Object) inName));
	}

}

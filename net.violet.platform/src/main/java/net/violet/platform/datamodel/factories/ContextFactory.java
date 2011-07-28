package net.violet.platform.datamodel.factories;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Context;

public interface ContextFactory extends RecordFactory<Context> {

	/**
	 * Finds an context according to name context.
	 * 
	 * @param inName name context.
	 * @return the Context object, or null.
	 */
	Context findByName(String inName);

}

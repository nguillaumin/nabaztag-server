package net.violet.platform.datamodel;

import java.util.List;

import net.violet.db.records.Record;

/**
 * Web site contexts (Unused ?)
 * 
 *
 */
public interface Context extends Record<Context> {

	/**
	 * Get name context
	 */
	String getName();

	/**
	 * get all hint for a given context
	 */
	List<Hint> getAllHints();

	/**
	 * add hint to context
	 */
	void addHint(Hint inHint);

	/**
	 * remove hint to context
	 */
	void removeHint(Hint inHint);

}

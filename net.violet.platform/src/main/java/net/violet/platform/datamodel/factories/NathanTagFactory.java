package net.violet.platform.datamodel.factories;

import java.util.List;
import java.util.Map;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.NathanTag;

public interface NathanTagFactory extends RecordFactory<NathanTag> {

	/**
	 * Returns all the available tags.
	 * 
	 * @return a list of NathanTag objects.
	 */
	List<NathanTag> findAllTags();

	/**
	 * Returns all the available tags stored in a Map where the key (an integer)
	 * is the category id and the value is a list of NathanTag belonging to the
	 * category.
	 * 
	 * @return a Map associating category ids with their NathanTag objects.
	 */
	Map<Integer, List<NathanTag>> findAllTagsSorted();

	/**
	 * Returns all the tags belonging to the specified category.
	 * 
	 * @param categoryId the category
	 * @return a List of NathanTag objects.
	 */
	List<NathanTag> findAllTagsByCategory(int categoryId);
}

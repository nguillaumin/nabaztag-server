package net.violet.platform.datamodel.factories.implementations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.NathanTag;
import net.violet.platform.datamodel.NathanTagImpl;
import net.violet.platform.datamodel.factories.NathanTagFactory;

public class NathanTagFactoryImpl extends RecordFactoryImpl<NathanTag, NathanTagImpl> implements NathanTagFactory {

	public NathanTagFactoryImpl() {
		super(NathanTagImpl.SPECIFICATION);
	}

	public List<NathanTag> findAllTags() {
		return findAll(null, null, null);
	}

	public List<NathanTag> findAllTagsByCategory(int categoryId) {
		return findAll("tag_categ = ? ", Arrays.asList((Object) categoryId), " tag_id ");
	}

	public Map<Integer, List<NathanTag>> findAllTagsSorted() {
		final Map<Integer, List<NathanTag>> result = new TreeMap<Integer, List<NathanTag>>();

		for (int categ = 0; categ < NathanTag.NB_CATEG; categ++) {
			final List<NathanTag> list = findAllTagsByCategory(categ);
			if (!list.isEmpty()) {
				result.put(categ, list);
			}
		}

		return result;
	}

}

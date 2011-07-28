package net.violet.platform.datamodel.factories.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.NathanTag;
import net.violet.platform.datamodel.factories.NathanTagFactory;
import net.violet.platform.datamodel.mock.NathanTagMock;

public class NathanTagFactoryMock extends RecordFactoryMock<NathanTag, NathanTagMock> implements NathanTagFactory {

	public NathanTagFactoryMock() {
		super(NathanTagMock.class);
	}

	public List<NathanTag> findAllTags() {
		return findAll();
	}

	public List<NathanTag> findAllTagsByCategory(int categoryId) {
		final List<NathanTag> result = new ArrayList<NathanTag>();
		for (final NathanTag tag : findAll()) {
			if (tag.getCateg() == categoryId) {
				result.add(tag);
			}
		}
		return result;
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

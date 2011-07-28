/**
 * 
 */
package net.violet.db.records.factories;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.db.records.AbstractMockRecord;
import net.violet.db.records.Record;

/**
 *
 */
public class RecordFactoryMock<Intf extends Record<Intf>, Impl extends AbstractMockRecord<Intf, Impl>> implements RecordFactory<Intf> {

	private final String mSpecification;

	/**
	 * Constructeur à partir de la spécification.
	 */
	protected RecordFactoryMock(Class<Impl> inSpecification) {
		this.mSpecification = inSpecification.getName();
	}

	/**
	 * Finds a Mock Record by its id
	 * 
	 * @param id
	 * @return
	 */
	public final Intf find(long id) {
		return findAllMapped().get(id);
	}

	public final Map<Long, Intf> findAllMapped() {
		return Collections.unmodifiableMap(AbstractMockRecord.<Intf> getInstancesList(this.mSpecification));
	}

	public final List<Intf> findAll() {
		return new LinkedList<Intf>(findAllMapped().values());
	}

	protected List<Intf> getSkipList(List<Intf> inListResult, int inSkip, int inGetCount) {

		int count = 10;
		if (inGetCount > 0) {
			count = inGetCount;
		}

		int skip = 0;
		if (inSkip > 0) {
			skip = inSkip;
		}
		final int sizeList = inListResult.size();
		final int maxSize = count + skip;
		if (maxSize <= sizeList) {
			return inListResult.subList(maxSize - count, maxSize);
		} else if (skip < sizeList) {
			return inListResult.subList(skip, sizeList);
		}

		return Collections.emptyList();
	}

	/**
	 * Charge les données pour les tests.
	 */
	public void loadCache() {
		// Par défaut, ne fait rien.
	}
}

package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Categ;

public class CategMock extends AbstractMockRecord<Categ, CategMock> implements Categ {

	/**
	 * Champs de l'enregistrement.
	 */

	private String name = "categorie n'existe pas";

	public CategMock(long inId) {
		super(inId);
	}

	public CategMock(long inId, String categName) {
		super(inId);
		this.name = categName;
	}

	public String getName() {
		return this.name;
	}

}

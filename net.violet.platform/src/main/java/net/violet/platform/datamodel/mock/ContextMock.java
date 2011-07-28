package net.violet.platform.datamodel.mock;

import java.util.ArrayList;
import java.util.List;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Context;
import net.violet.platform.datamodel.Hint;

public class ContextMock extends AbstractMockRecord<Context, ContextMock> implements Context {

	private final String name;

	private final List<Hint> listHints = new ArrayList<Hint>();

	public ContextMock(long inId, String inName) {
		super(inId);
		this.name = inName;
	}

	public void addHint(Hint inHint) {
		this.listHints.add(inHint);
	}

	public List<Hint> getAllHints() {
		return this.listHints;
	}

	public String getName() {
		return this.name;
	}

	public void removeHint(Hint inHint) {
		this.listHints.remove(inHint);
	}

}

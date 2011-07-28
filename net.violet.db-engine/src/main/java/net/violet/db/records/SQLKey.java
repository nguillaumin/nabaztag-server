package net.violet.db.records;

import java.io.Serializable;
import java.util.Arrays;

public class SQLKey implements Serializable {

	private final String[] keys;

	public SQLKey(String... inKeys) {
		this.keys = inKeys;
	}

	public String[] getKeys() {
		return this.keys;
	}

	@Override
	public boolean equals(Object inSQLKey) {
		return (inSQLKey instanceof SQLKey) && Arrays.equals(this.keys, ((SQLKey) inSQLKey).getKeys());
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(this.keys);
	}

	@Override
	public String toString() {
		final StringBuilder theStr = new StringBuilder("\nnet.violet.platform.datamodel.SQLKey---------\nClés : ");
		for (int i = 0; i < this.keys.length - 1; i++) {
			theStr.append(this.keys[i]).append(", ");
		}
		theStr.append(this.keys[this.keys.length - 1]);
		theStr.append("\n-----------------------------------------------\n");
		return theStr.toString();
	}

}

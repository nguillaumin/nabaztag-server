package net.violet.platform.journal;


public class JournalEntry {

	private final long when;
	private final String what;

	public JournalEntry(long when, String what) {
		this.when = when;
		this.what = what;
	}

	public JournalEntry(String what) {
		this(System.currentTimeMillis(), what);
	}

	public long getWhen() {
		return this.when;
	}

	public String getWhat() {
		return this.what;
	}

	@Override
	public String toString() {
		return this.when + " " + this.what;
	}

}

package net.violet.platform.api.maps;

import net.violet.platform.journal.JournalEntry;
import net.violet.platform.util.CCalendar;

public class LoggerEntryMap extends AbstractAPIMap {

	public static final String WHEN = "when";
	public static final String WHAT = "what";

	public LoggerEntryMap(JournalEntry entry, boolean useDate) {
		super(2);
		put(LoggerEntryMap.WHAT, entry.getWhat());
		put(LoggerEntryMap.WHEN, useDate ? new CCalendar(entry.getWhen()).getUTCDateTimeFormated() : entry.getWhen());
	}

}

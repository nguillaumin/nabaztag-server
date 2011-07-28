package net.violet.platform.dataobjects;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.Agenda;
import net.violet.platform.datamodel.AgendaImpl;
import net.violet.platform.util.StringShop;

public final class AgendaData extends RecordData<Agenda> {

	private static final List<AgendaData> CACHE = new LinkedList<AgendaData>();

	static {
		for (final Map.Entry<Long, Agenda> theEntry : AgendaImpl.listAll().entrySet()) {
			if ((theEntry.getKey() < 8) && (theEntry.getValue() instanceof AgendaImpl)) {
				AgendaData.CACHE.add(new AgendaData(theEntry.getValue()));
			}
		}
	}

	private AgendaData(Agenda inAgenda) {
		super(inAgenda);
	}

	public long getAgenda_id() {
		final Agenda theAgenda = getRecord();
		if (theAgenda != null) {
			return theAgenda.getAgenda_id();
		}
		return 0;
	}

	public String getAgenda_key() {
		final Agenda theAgenda = getRecord();
		if (theAgenda != null) {
			return theAgenda.getAgenda_key();
		}
		return StringShop.EMPTY_STRING;
	}

	/**
	 * Accesseur sur toutes les animations pour une langue
	 */
	public static List<AgendaData> listAll() {
		return AgendaData.CACHE;
	}
}

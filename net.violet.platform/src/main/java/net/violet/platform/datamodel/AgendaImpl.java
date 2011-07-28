package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.SortedMap;
import java.util.TreeMap;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;

import org.apache.log4j.Logger;

public class AgendaImpl extends ObjectRecord<Agenda, AgendaImpl> implements Agenda {


	private static final Logger LOGGER = Logger.getLogger(AgendaImpl.class);

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<AgendaImpl> SPECIFICATION = new SQLObjectSpecification<AgendaImpl>("agenda", AgendaImpl.class, new SQLKey("agenda_id"));

	/**
	 * Cache de toutes les entrées (pour tous les jours).
	 */
	private static final SortedMap<Long, Agenda> AGENDAS = AgendaImpl.createAgendaCache();

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected AgendaImpl(long id) throws SQLException {
		init(id);
	}

	protected AgendaImpl() {
		// This space for rent.
	}

	/**
	 * Champs de l'enregistrement.
	 */
	protected long agenda_id;
	protected String agenda_key;

	/**
	 * Accesseur à partir d'un id.
	 * 
	 * @param id id de l'objet.
	 * @return l'enregistrement (ou <code>null</code>).
	 */
	public static Agenda find(long id) {
		Agenda agenda = null;
		try {
			agenda = AbstractSQLRecord.findByKey(AgendaImpl.SPECIFICATION, AgendaImpl.SPECIFICATION.getTableKeys()[0], id);
		} catch (final SQLException se) {
			AgendaImpl.LOGGER.fatal(se, se);
		}
		return agenda;
	}

	@Override
	public Long getId() {
		return getAgenda_id();
	}

	@Override
	public SQLObjectSpecification<AgendaImpl> getSpecification() {
		return AgendaImpl.SPECIFICATION;
	}

	public final long getAgenda_id() {
		return this.agenda_id;
	}

	public final String getAgenda_key() {
		return this.agenda_key;
	}

	public static SortedMap<Long, Agenda> listAll() {
		return AgendaImpl.AGENDAS;
	}

	private static SortedMap<Long, Agenda> createAgendaCache() {
		final SortedMap<Long, Agenda> theResult = new TreeMap<Long, Agenda>();
		try {
			for (final Agenda theAgenda : AbstractSQLRecord.findAll(AgendaImpl.SPECIFICATION, null, null)) {
				theResult.put(theAgenda.getAgenda_id(), theAgenda);
			}
		} catch (final SQLException se) {
			AgendaImpl.LOGGER.fatal(se, se);
		}
		return theResult;
	}
}

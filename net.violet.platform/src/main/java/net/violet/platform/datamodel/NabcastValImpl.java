package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.platform.util.CCalendar;

import org.apache.log4j.Logger;

public class NabcastValImpl extends ObjectRecord<NabcastVal, NabcastValImpl> implements NabcastVal {

	private static final Logger LOGGER = Logger.getLogger(NabcastValImpl.class);

	/**
	 * Spécification
	 */
	static final SQLObjectSpecification<NabcastValImpl> SPECIFICATION = new SQLObjectSpecification<NabcastValImpl>("nabcastval", NabcastValImpl.class, new SQLKey("nabcastval_id"));

	/**
	 * Champs de l'enregistrement.
	 */
	protected long nabcastval_id;
	protected String nabcastval_title;
	protected Long nabcastval_idmusic;
	protected Long nabcastval_nabcast;
	protected Integer nabcastval_time;
	protected Long nabcastval_date;

	private final SingleAssociationNotNull<NabcastVal, Nabcast, NabcastImpl> nabcast;

	private final SingleAssociationNotNull<NabcastVal, Music, MusicImpl> music;

	protected NabcastValImpl() {
		super();
		this.nabcast = new SingleAssociationNotNull<NabcastVal, Nabcast, NabcastImpl>(this, "nabcastval_nabcast", NabcastImpl.SPECIFICATION);
		this.music = new SingleAssociationNotNull<NabcastVal, Music, MusicImpl>(this, "nabcastval_idmusic", MusicImpl.SPECIFICATION);
	}

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected NabcastValImpl(long id) throws SQLException {
		super();
		init(id);
		this.nabcast = new SingleAssociationNotNull<NabcastVal, Nabcast, NabcastImpl>(this, "nabcastval_nabcast", NabcastImpl.SPECIFICATION);
		this.music = new SingleAssociationNotNull<NabcastVal, Music, MusicImpl>(this, "nabcastval_idmusic", MusicImpl.SPECIFICATION);
	}

	public NabcastValImpl(String title, long musicId, Nabcast theNabcast, Integer date_prog) throws SQLException {
		this(title, musicId, theNabcast.getId(), date_prog);
	}

	public NabcastValImpl(String title, long musicId, long inNabcastId, Integer date_prog) throws SQLException {
		super();
		this.nabcastval_title = title;
		this.nabcastval_idmusic = musicId;
		this.nabcastval_nabcast = inNabcastId;
		this.nabcastval_date = Long.valueOf(CCalendar.getCurrentTimeInSecond());
		this.nabcastval_time = date_prog;
		init(NabcastVal.NEW_COLUMNS);
		this.nabcast = new SingleAssociationNotNull<NabcastVal, Nabcast, NabcastImpl>(this, "nabcastval_nabcast", NabcastImpl.SPECIFICATION);
		this.music = new SingleAssociationNotNull<NabcastVal, Music, MusicImpl>(this, "nabcastval_idmusic", MusicImpl.SPECIFICATION);
	}

	/**
	 * delete NabcastValImpl param nabcastid : id du NabcastValImpl
	 * 
	 * @return le log de la requete 0 = erreur, 1 = OK
	 */
	public static void deleteNabcastVal(long nabcastValid) {
		final NabcastVal nbVal = NabcastValImpl.find(nabcastValid);
		if (nbVal != null) {
			nbVal.delete();
		}
	}

	/**
	 * Accesseur à partir d'un id.
	 * 
	 * @param id id de l'objet.
	 * @return l'enregistrement (ou <code>null</code>).
	 */
	public static NabcastVal find(long id) {
		NabcastVal result = null;
		try {
			result = AbstractSQLRecord.findByKey(NabcastValImpl.SPECIFICATION, NabcastValImpl.SPECIFICATION.getTableKeys()[0], id);
		} catch (final SQLException se) {
			NabcastValImpl.LOGGER.fatal(se, se);
		}
		return result;
	}

	public static NabcastVal findLastByNabcast(Nabcast nabcast) {
		NabcastVal nbVal = null;
		if (nabcast != null) {
			try {
				nbVal = AbstractSQLRecord.find(NabcastValImpl.SPECIFICATION, "nabcastval_nabcast=? AND nabcastval_time<?", Arrays.asList(new Object[] { nabcast.getId(), CCalendar.getCurrentTimeInSecond() }));
			} catch (final SQLException se) {
				NabcastValImpl.LOGGER.fatal(se, se);
			}
		}
		return nbVal;
	}

	public static NabcastVal findProgrammedNabcast(long id) {
		NabcastVal nbVal = null;
		try {
			nbVal = AbstractSQLRecord.find(NabcastValImpl.SPECIFICATION, "nabcastval_id=? AND nabcastval_time>?", Arrays.asList(new Object[] { id, CCalendar.getCurrentTimeInSecond() }));
		} catch (final SQLException se) {
			NabcastValImpl.LOGGER.fatal(se, se);
		}
		return nbVal;
	}

	/**
	 * Permet de récupérer le dernier post du nabcast fais dans les 24 heure qui
	 * précède Elle est utilisé par le crawler qui vérifie si les nabcasts sont
	 * mis a jour
	 * 
	 * @param id id du nabcast concerné
	 * @return
	 */
	public static NabcastVal findNabcastPostIntoOneDay(long inNabcastId) {
		final int timecheck = CCalendar.getCurrentTimeInSecond() - (3600 * 24);// regarde si un nabcast a été posté une
		// fois
		// au
		// moins
		// dans
		// les
		// 24
		// heures
		final List<Object> values = Arrays.asList(new Object[] { inNabcastId, timecheck });
		NabcastVal result = null;
		try {
			result = AbstractSQLRecord.find(NabcastValImpl.SPECIFICATION, null, "nabcastval_nabcast = ? and nabcastval_time > ? ", values, " nabcastval_time desc ");
		} catch (final SQLException se) {
			NabcastValImpl.LOGGER.fatal(se, se);
		}
		return result;
	}

	public static List<NabcastVal> findLastByNabcastAndDate(Nabcast nabcast, int timeOneWeekBefore) {
		final List<NabcastVal> list = new ArrayList<NabcastVal>();
		if (nabcast != null) {
			try {
				list.addAll(AbstractSQLRecord.findAll(NabcastValImpl.SPECIFICATION, "nabcastval_nabcast=? and nabcastval_date>?", Arrays.asList(new Object[] { nabcast.getId(), timeOneWeekBefore })));
			} catch (final SQLException se) {
				NabcastValImpl.LOGGER.fatal(se, se);
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.NabcastVal#getId()
	 */
	@Override
	public Long getId() {
		return getNabcastval_id();
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.NabcastVal#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<NabcastValImpl> getSpecification() {
		return NabcastValImpl.SPECIFICATION;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.NabcastVal#getNabcastval_id()
	 */
	public final long getNabcastval_id() {
		return this.nabcastval_id;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.NabcastVal#getNabcastval_title()
	 */
	public final String getNabcastval_title() {
		return this.nabcastval_title;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.NabcastVal#getNabcastval_idmusic()
	 */
	public final long getNabcastval_idmusic() {
		return this.nabcastval_idmusic;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.NabcastVal#getNabcast()
	 */
	public final Nabcast getNabcast() {
		return this.nabcast.get(this.nabcastval_nabcast);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.NabcastVal#getNabcastval_time()
	 */
	public final Integer getNabcastval_time() {
		return this.nabcastval_time;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.NabcastVal#getNabcastval_date()
	 */
	public final long getNabcastval_date() {
		return this.nabcastval_date;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.NabcastVal#getMusic()
	 */
	public Music getMusic() {
		return this.music.get(this.nabcastval_idmusic);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.violet.platform.datamodel.NabcastVal#setMusic(net.violet.platform
	 * .datamodel.MusicImpl)
	 */
	public void setMusic(MusicImpl inMusic) {
		if (inMusic.getId() != this.nabcastval_idmusic) {
			this.music.set(inMusic);
			this.nabcastval_idmusic = inMusic.getId();
		}
	}

	public static List<NabcastVal> getSongNabcast(long id, int nbrnabcast, int index, int afterBefore, int desc) {
		final List<NabcastVal> list = new ArrayList<NabcastVal>();
		try {
			String condition = "nabcastval_nabcast=?";
			final List<Object> values = new ArrayList<Object>();
			values.add(id);
			if (afterBefore == 0) {
				condition += " AND nabcastval_time <= ?";
				values.add(CCalendar.getCurrentTimeInSecond());
			} else if (afterBefore == 1) {
				condition += " AND nabcastval_time > ?";
				values.add(CCalendar.getCurrentTimeInSecond());
			}

			String order = "nabcastval_time";

			if (desc == 1) {
				order += " DESC";
			}

			if (nbrnabcast > 0) {
				order += " LIMIT " + index + net.violet.common.StringShop.COMMA + nbrnabcast;
			}

			list.addAll(AbstractSQLRecord.findAll(NabcastValImpl.SPECIFICATION, condition, values, order));
		} catch (final SQLException se) {
			NabcastValImpl.LOGGER.fatal(se, se);
		}
		return list;
	}

}

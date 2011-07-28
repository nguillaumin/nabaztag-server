package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;

import org.apache.log4j.Logger;

public class MusicStyleImpl extends ObjectRecord<MusicStyle, MusicStyleImpl> implements MusicStyle {

	private static final Logger LOGGER = Logger.getLogger(MusicStyleImpl.class);

	public static final SQLObjectSpecification<MusicStyleImpl> SPECIFICATION = new SQLObjectSpecification<MusicStyleImpl>("musicstyle", MusicStyleImpl.class, new SQLKey("musicstyle_id"));

	protected MusicStyleImpl(long id) throws SQLException {
		super();
		init(id);
	}

	protected MusicStyleImpl() {
		super();
	}

	protected long musicstyle_id;
	protected String musicstyle_name;
	protected Long style_private;

	public static MusicStyle find(long id) {
		MusicStyle result = null;
		try {
			result = AbstractSQLRecord.findByKey(MusicStyleImpl.SPECIFICATION, MusicStyleImpl.SPECIFICATION.getTableKeys()[0], id);
		} catch (final SQLException se) {
			MusicStyleImpl.LOGGER.fatal(se, se);
		}
		return result;
	}

	/**
	 * Finds all the MusicImpl styles by {@link LangImpl}
	 * 
	 * @param inLangId
	 * @return
	 */
	public static List<MusicStyle> findAllClinByLang(Long inLangId) {
		final List<MusicStyle> theResult = new ArrayList<MusicStyle>();

		try {
			theResult.addAll(AbstractSQLRecord.findAll(MusicStyleImpl.SPECIFICATION, new String[] { "music" }, "musicstyle_id = music_styleid AND music_lang= ? AND music_type = 1 AND style_private=0 group by musicstyle_id", Collections.singletonList((Object) inLangId)));
		} catch (final SQLException e) {
			MusicStyleImpl.LOGGER.fatal(e, e);
		}

		return theResult;
	}

	/**
	 * Finds all the MusicImpl styles by {@link LangImpl}
	 * 
	 * @param inLangId
	 * @return
	 */
	public static List<MusicStyle> findAllBiblioByLang(long inLangId) {
		final List<MusicStyle> theResult = new ArrayList<MusicStyle>();

		try {
			theResult.addAll(AbstractSQLRecord.findAll(MusicStyleImpl.SPECIFICATION, new String[] { "music" }, "musicstyle_id = music_styleid AND music_lang= ? AND music_type = 2 AND style_private=0 group by musicstyle_id", Collections.singletonList((Object) inLangId)));
		} catch (final SQLException e) {
			MusicStyleImpl.LOGGER.fatal(e, e);
		}

		return theResult;
	}

	/**
	 * Accesseur à partir de la langue pour la biblio
	 * 
	 * @param inUser l'user.
	 * @return les objets music associés à cet user.
	 */
	public static List<MusicStyle> findAllCategForBiblio() {
		final List<MusicStyle> theResult = new ArrayList<MusicStyle>();
		try {
			theResult.addAll(AbstractSQLRecord.findAll(MusicStyleImpl.SPECIFICATION, new String[] { "music" }, "musicstyle_id = music_styleid AND music_type = 2 and style_private=0 group by musicstyle_id", null));
		} catch (final SQLException anException) {
			MusicStyleImpl.LOGGER.fatal(anException, anException);
		}
		return theResult;
	}

	public final long getMusicstyle_id() {
		return this.musicstyle_id;
	}

	@Override
	public Long getId() {
		return getMusicstyle_id();
	}

	@Override
	public SQLObjectSpecification<MusicStyleImpl> getSpecification() {
		return MusicStyleImpl.SPECIFICATION;
	}

	public final String getMusicstyle_name() {
		return this.musicstyle_name;
	}

	public final long getStyle_private() {
		return this.style_private;
	}

}

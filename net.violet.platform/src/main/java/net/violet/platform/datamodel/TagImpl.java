package net.violet.platform.datamodel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.SgbdConnection;
import net.violet.db.records.SgbdResult;
import net.violet.db.records.SgbdConnection.SGBD_ACCESS;
import net.violet.db.records.associations.SingleAssociationNotNull;

import org.apache.log4j.Logger;

public class TagImpl extends ObjectRecord<Tag, TagImpl> implements Tag {


	private static final Logger LOGGER = Logger.getLogger(TagImpl.class);

	/**
	 * Spécification.
	 */
	static final SQLObjectSpecification<TagImpl> SPECIFICATION = new SQLObjectSpecification<TagImpl>("tag", TagImpl.class, new SQLKey(new String[] { "tag_mp3", "tag_word", }));

	/**
	 * Champs de l'enregistrement.
	 */
	protected String tag_word;
	protected long tag_mp3;
	protected long tag_lang;

	private final SingleAssociationNotNull<Tag, Lang, LangImpl> lang;

	private int frequency;

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { "tag_mp3", "tag_word", "tag_lang", };

	/**
	 * Constructeur publique
	 * 
	 * @param idMp3
	 * @param word
	 * @param lang
	 * @throws SQLException
	 */
	public TagImpl(long idMp3, String word, Lang inLang) throws SQLException {
		super();
		this.tag_mp3 = idMp3;
		this.tag_word = word;
		this.tag_lang = inLang.getId();

		init(TagImpl.NEW_COLUMNS);

		this.lang = new SingleAssociationNotNull<Tag, Lang, LangImpl>(this, "tag_lang", LangImpl.SPECIFICATION);
	}

	public TagImpl() {
		super();
		this.lang = new SingleAssociationNotNull<Tag, Lang, LangImpl>(this, "tag_lang", LangImpl.SPECIFICATION);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Tag#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<TagImpl> getSpecification() {
		return TagImpl.SPECIFICATION;
	}

	/**
	 * Accesseur à partir de l'id d'un mp3
	 * 
	 * @param idMp3
	 * @return
	 */
	public static List<Tag> findByMp3(long idMp3) {
		List<Tag> tags = new ArrayList<Tag>();
		try {
			tags = new ArrayList<Tag>(AbstractSQLRecord.findAll(TagImpl.SPECIFICATION, "tag_mp3=?", Collections.singletonList((Object) idMp3)));
		} catch (final SQLException se) {
			TagImpl.LOGGER.fatal(se, se);
		}
		return tags;
	}

	/**
	 * Accesseur à partir d'une langue
	 * 
	 * @param lang
	 * @return
	 */
	public static List<Tag> findByLang(Lang lang) {
		List<Tag> tags = new ArrayList<Tag>();
		try {
			tags = new ArrayList<Tag>(AbstractSQLRecord.findAll(TagImpl.SPECIFICATION, "tag_lang=?", Collections.singletonList((Object) lang.getId())));
		} catch (final SQLException se) {
			TagImpl.LOGGER.fatal(se, se);
		}
		return tags;
	}

	/**
	 * @param idMp3
	 * @param word
	 * @return
	 */
	public static Tag findByMp3AndWord(long idMp3, String word) {
		Tag tag = null;
		try {
			tag = AbstractSQLRecord.findByKey(TagImpl.SPECIFICATION, TagImpl.SPECIFICATION.getTableKeys()[0], new Object[] { idMp3, word });
		} catch (final SQLException se) {
			TagImpl.LOGGER.fatal(se, se);
		}
		return tag;
	}

	public static List<Tag> getWordCloud(int limit, Lang lang) {
		final List<Tag> tags = new ArrayList<Tag>();
		final String query = "select count(tag_word), tag_word AS " + TagImpl.SPECIFICATION.getTablePrefix() + "tag_word, tag_mp3 AS " + TagImpl.SPECIFICATION.getTablePrefix() + "tag_mp3, tag_lang AS " + TagImpl.SPECIFICATION.getTablePrefix() + "tag_lang from tag where tag_lang=? group by tag_word order by 1 desc limit 0,?";
		final SgbdConnection connection = new SgbdConnection(SGBD_ACCESS.READONLY);

		try {
			final SgbdResult theSgbdResult = connection.doQueryPS(query, Arrays.asList(new Object[] { lang.getId(), limit }), 0, false);
			try {
				final ResultSet rs = theSgbdResult.getResultSet();
				while (rs.next()) {
					final TagImpl tag = (TagImpl) ObjectRecord.createObject(TagImpl.SPECIFICATION, rs);
					tag.frequency = rs.getInt(1);
					tags.add(tag);
				}
			} finally {
				theSgbdResult.close();
			}
		} catch (final SQLException se) {
			TagImpl.LOGGER.fatal(se, se);
		} finally {
			connection.close();
		}
		return tags;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Tag#getLang()
	 */
	public Lang getLang() {
		return this.lang.get(this.tag_lang);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Tag#getTag_word()
	 */
	public String getTag_word() {
		return this.tag_word;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Tag#getFrequency()
	 */
	public int getFrequency() {
		return this.frequency;
	}

}

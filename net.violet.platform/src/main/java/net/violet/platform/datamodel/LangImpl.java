package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.platform.util.StringShop;

/**
 * Classe pour les langues.
 */
public final class LangImpl extends ObjectRecord<Lang, LangImpl> implements Lang {

	public static final SQLObjectSpecification<LangImpl> SPECIFICATION = new SQLObjectSpecification<LangImpl>("lang", LangImpl.class, new SQLKey[] { new SQLKey(StringShop.LANG_ID), new SQLKey("lang_isocode") });

	/**
	 * Correspondance id my -> id help.
	 */
	public static final Map<Long, Long> HELP_LANG_ID;
	static {
		HELP_LANG_ID = new HashMap<Long, Long>();
		LangImpl.HELP_LANG_ID.put(1L, 2L); // fr
		LangImpl.HELP_LANG_ID.put(2L, 3L); // us
		LangImpl.HELP_LANG_ID.put(3L, 3L); // uk
		LangImpl.HELP_LANG_ID.put(4L, 5L); // de
		LangImpl.HELP_LANG_ID.put(5L, 3L); // es
		LangImpl.HELP_LANG_ID.put(6L, 6L); // it
		LangImpl.HELP_LANG_ID.put(21L, 7L); // br
	}

	// / Champs SQL
	protected long lang_id;
	protected String lang_title;
	protected long lang_type;
	protected String lang_isocode;
	protected int lang_newtype;

	/**
	 * Constructeur par défaut.
	 */
	protected LangImpl() {
		// This space for rent.
	}

	/**
	 * Constructeur à partir d'un id.
	 */
	protected LangImpl(long inId) throws SQLException {
		init(inId);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Lang#getId()
	 */
	@Override
	public Long getId() {
		return this.lang_id;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Lang#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<LangImpl> getSpecification() {
		return LangImpl.SPECIFICATION;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Lang#getTitle()
	 */
	public String getTitle() {
		return this.lang_title;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Lang#getIsoCode()
	 */
	public String getIsoCode() {
		return this.lang_isocode;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Lang#getType()
	 */
	public long getType() {
		return this.lang_type;
	}

	/**
	 * 1 => langue proposé par le site (affichage, application) 2 => langue
	 * proposé dans le tts 4 => langue proposé dans la reco 8 => langue proposé
	 * pour l'objet
	 */
	public int getNewType() {
		return this.lang_newtype;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Lang#getHelpLangId()
	 */
	public long getHelpLangId() {
		final long myID = getId();
		final Long theHelpID = LangImpl.HELP_LANG_ID.get(myID);
		long theResult;
		if (theHelpID == null) {
			theResult = Lang.DEFAULT_HELP_LANG_ID; // anglais
		} else {
			theResult = theHelpID.longValue();
		}
		return theResult;
	}

	@Override
	public String toString() {
		return this.lang_title + " [" + this.lang_id + "]";
	}

	/**
	 * @return le code court (sur deux carcatères, qui est toujours le début du
	 *         code ISO)
	 * @see net.violet.platform.datamodel.Lang#getIETFCode()
	 */
	public String getIETFCode() {
		return this.lang_isocode.substring(0, 2);
	}

}

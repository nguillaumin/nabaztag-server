package net.violet.platform.datamodel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.SgbdConnection;
import net.violet.db.records.SgbdResult;
import net.violet.db.records.SgbdConnection.SGBD_ACCESS;
import net.violet.db.records.associations.SingleAssociationNotNull;

import org.apache.log4j.Logger;

public class NabcastImpl extends ObjectRecord<Nabcast, NabcastImpl> implements Nabcast {


	private static final Logger LOGGER = Logger.getLogger(NabcastImpl.class);

	/**
	 * Spécification
	 */
	static final SQLObjectSpecification<NabcastImpl> SPECIFICATION = new SQLObjectSpecification<NabcastImpl>("nabcast", NabcastImpl.class, new SQLKey("nabcast_id"));

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { "nabcast_title", "nabcast_categ", "nabcast_desc", "nabcast_private", "nabcast_author", "nabcast_mailnotified", "nabcast_update", "nabcast_lang", "nabcast_color_sign", "nabcast_anim_sign", "nabcast_music_sign", "nabcast_shortcut", };

	/**
	 * Champs de l'enregistrement.
	 */
	protected long nabcast_id;
	protected String nabcast_title;
	protected long nabcast_categ;
	protected String nabcast_desc;
	protected int nabcast_private;
	protected long nabcast_author;
	protected int nabcast_mailnotified;
	protected int nabcast_update;
	protected String nabcast_color_sign;
	protected long nabcast_music_sign;
	protected long nabcast_anim_sign;
	protected long nabcast_lang;
	protected int nabcast_postupdate;
	protected String nabcast_shortcut;

	private final SingleAssociationNotNull<Nabcast, User, UserImpl> author;
	private final SingleAssociationNotNull<Nabcast, NabcastCateg, NabcastCategImpl> nabcastCateg;

	protected NabcastImpl() {
		this.author = new SingleAssociationNotNull<Nabcast, User, UserImpl>(this, "nabcast_author", UserImpl.SPECIFICATION);
		this.nabcastCateg = new SingleAssociationNotNull<Nabcast, NabcastCateg, NabcastCategImpl>(this, "nabcast_categ", NabcastCategImpl.SPECIFICATION);
	}

	public NabcastImpl(String title, long categ, String desc, User theAuthor, String colorSign, long musicSign, long animSign, Lang lang, String shortcut) throws SQLException {
		this.nabcast_title = title;
		this.nabcast_categ = categ;
		this.nabcast_desc = desc;
		this.nabcast_private = 1;
		this.nabcast_author = theAuthor.getId();
		this.nabcast_mailnotified = 0;
		this.nabcast_update = 0;
		this.nabcast_lang = lang.getId();
		this.nabcast_color_sign = colorSign;
		this.nabcast_anim_sign = animSign;
		this.nabcast_music_sign = musicSign;
		this.nabcast_shortcut = shortcut;

		init(NabcastImpl.NEW_COLUMNS);

		this.author = new SingleAssociationNotNull<Nabcast, User, UserImpl>(this, "nabcast_author", UserImpl.SPECIFICATION);
		this.nabcastCateg = new SingleAssociationNotNull<Nabcast, NabcastCateg, NabcastCategImpl>(this, "nabcast_categ", NabcastCategImpl.SPECIFICATION);
	}

	/**
	 * Accesseur à partir d'un id.
	 * 
	 * @param id id de l'objet.
	 * @return l'enregistrement (ou <code>null</code>).
	 */
	public static Nabcast find(long id) {
		Nabcast result = null;
		try {
			result = AbstractSQLRecord.findByKey(NabcastImpl.SPECIFICATION, NabcastImpl.SPECIFICATION.getPrimaryKey(), id);
		} catch (final SQLException se) {
			NabcastImpl.LOGGER.fatal(se, se);
		}
		return result;
	}

	/**
	 * Accesseur à partir d'un shortcut.
	 * 
	 * @param shortcut shortcut de l'objet.
	 * @return l'enregistrement (ou <code>null</code>).
	 */
	public static List<Nabcast> findAllByShortcut(String inShortcut) {
		final List<Object> theValues = Collections.singletonList((Object) inShortcut);
		List<Nabcast> theResult = null;
		try {
			theResult = new ArrayList<Nabcast>(AbstractSQLRecord.findAll(NabcastImpl.SPECIFICATION, "nabcast_shortcut = ?", theValues));
		} catch (final SQLException anException) {
			NabcastImpl.LOGGER.fatal(anException, anException);
		}
		return theResult;
	}

	public static List<Nabcast> findAllByAuthor(UserImpl author) {
		final List<Nabcast> theResult = new ArrayList<Nabcast>();
		try {
			theResult.addAll(AbstractSQLRecord.findAll(NabcastImpl.SPECIFICATION, "nabcast_author = ?", Collections.singletonList((Object) author.getId())));
		} catch (final SQLException anException) {
			NabcastImpl.LOGGER.fatal(anException, anException);
		}
		return theResult;
	}

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected NabcastImpl(long id) throws SQLException {
		init(id);
		this.author = new SingleAssociationNotNull<Nabcast, User, UserImpl>(this, "nabcast_author", UserImpl.SPECIFICATION);
		this.nabcastCateg = new SingleAssociationNotNull<Nabcast, NabcastCateg, NabcastCategImpl>(this, "nabcast_categ", NabcastCategImpl.SPECIFICATION);
	}

	@Override
	public Long getId() {
		return getNabcast_id();
	}

	@Override
	public SQLObjectSpecification<NabcastImpl> getSpecification() {
		return NabcastImpl.SPECIFICATION;
	}

	public long getNabcast_anim_sign() {
		return this.nabcast_anim_sign;
	}

	public void setNabcastInfo(long animSign, NabcastCateg categ, String colorSign, String desc, long lang, long musicSign, String shortcut, String title) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setNabcast_anim_sign(theUpdateMap, animSign);
		setNabcastCateg(theUpdateMap, categ);
		setNabcast_color_sign(theUpdateMap, colorSign);
		setNabcast_desc(theUpdateMap, desc);
		setNabcast_lang(theUpdateMap, lang);
		setNabcast_music_sign(theUpdateMap, musicSign);
		setNabcast_shortcut(theUpdateMap, shortcut);
		setNabcast_title(theUpdateMap, title);
		update(theUpdateMap);
	}

	private void setNabcast_anim_sign(Map<String, Object> inUpdateMap, long nabcast_anim_sign) {
		if (this.nabcast_anim_sign != nabcast_anim_sign) {
			this.nabcast_anim_sign = nabcast_anim_sign;
			inUpdateMap.put("nabcast_anim_sign", nabcast_anim_sign);
		}
	}

	private void setNabcastCateg(Map<String, Object> inUpdateMap, NabcastCateg nabcastCateg) {
		if (this.nabcast_categ != nabcastCateg.getId()) {
			this.nabcast_categ = nabcastCateg.getId();
			this.nabcastCateg.set(nabcastCateg);
			inUpdateMap.put("nabcast_categ", this.nabcast_categ);
		}
	}

	private void setNabcast_color_sign(Map<String, Object> inUpdateMap, String nabcast_color_sign) {
		if (!this.nabcast_color_sign.equals(nabcast_color_sign)) {
			this.nabcast_color_sign = nabcast_color_sign;
			inUpdateMap.put("nabcast_color_sign", nabcast_color_sign);
		}
	}

	private void setNabcast_desc(Map<String, Object> inUpdateMap, String nabcast_desc) {
		if (!this.nabcast_desc.equals(nabcast_desc)) {
			this.nabcast_desc = nabcast_desc;
			inUpdateMap.put("nabcast_desc", nabcast_desc);
		}
	}

	private void setNabcast_lang(Map<String, Object> inUpdateMap, long nabcast_lang) {
		if (this.nabcast_lang != nabcast_lang) {
			this.nabcast_lang = nabcast_lang;
			inUpdateMap.put("nabcast_lang", nabcast_lang);
		}
	}

	private void setNabcast_music_sign(Map<String, Object> inUpdateMap, long nabcast_music_sign) {
		if (this.nabcast_music_sign != nabcast_music_sign) {
			this.nabcast_music_sign = nabcast_music_sign;
			inUpdateMap.put("nabcast_music_sign", nabcast_music_sign);
		}
	}

	private void setNabcast_shortcut(Map<String, Object> inUpdateMap, String nabcast_shortcut) {
		if (((this.nabcast_shortcut == null) && (nabcast_shortcut != null)) || !this.nabcast_shortcut.equals(nabcast_shortcut)) {
			this.nabcast_shortcut = nabcast_shortcut;
			inUpdateMap.put("nabcast_shortcut", nabcast_shortcut);
		}
	}

	private void setNabcast_title(Map<String, Object> inUpdateMap, String nabcast_title) {
		if (!this.nabcast_title.equals(nabcast_title)) {
			this.nabcast_title = nabcast_title;
			inUpdateMap.put("nabcast_title", nabcast_title);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Nabcast#getNabcastCateg()
	 */
	public NabcastCateg getNabcastCateg() {
		return this.nabcastCateg.get(this.nabcast_categ);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Nabcast#getNabcast_color_sign()
	 */
	public String getNabcast_color_sign() {
		return this.nabcast_color_sign;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Nabcast#getNabcast_desc()
	 */
	public String getNabcast_desc() {
		return this.nabcast_desc;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Nabcast#getNabcast_lang()
	 */
	public long getNabcast_lang() {
		return this.nabcast_lang;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Nabcast#getNabcast_music_sign()
	 */
	public long getNabcast_music_sign() {
		return this.nabcast_music_sign;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Nabcast#getNabcast_postupdate()
	 */
	public int getNabcast_postupdate() {
		return this.nabcast_postupdate;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Nabcast#setPostUpdate(int)
	 */
	public void setPostUpdate(int postUpdate) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setNabcast_postupdate(theUpdateMap, postUpdate);
		update(theUpdateMap);
	}

	private void setNabcast_postupdate(Map<String, Object> inUpdateMap, int nabcast_postupdate) {
		if (this.nabcast_postupdate != nabcast_postupdate) {
			this.nabcast_postupdate = nabcast_postupdate;
			inUpdateMap.put("nabcast_postupdate", nabcast_postupdate);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Nabcast#getNabcast_private()
	 */
	public int getNabcast_private() {
		return this.nabcast_private;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Nabcast#setPrivate(int)
	 */
	public void setPrivate(int isPrivate) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setNabcast_private(theUpdateMap, isPrivate);
		update(theUpdateMap);
	}

	private void setNabcast_private(Map<String, Object> inUpdateMap, int nabcast_private) {
		if (this.nabcast_private != nabcast_private) {
			this.nabcast_private = nabcast_private;
			inUpdateMap.put("nabcast_private", nabcast_private);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Nabcast#setUpdate(int)
	 */
	public void setUpdate(int inUpdate) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setNabcast_update(theUpdateMap, inUpdate);
		update(theUpdateMap);
	}

	private void setNabcast_update(Map<String, Object> inUpdateMap, int nabcast_update) {
		if (this.nabcast_update != nabcast_update) {
			this.nabcast_update = nabcast_update;
			inUpdateMap.put("nabcast_update", nabcast_update);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Nabcast#setMailNotified(int)
	 */
	public void setMailNotified(int inNotified) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setNabcast_mailnotified(theUpdateMap, inNotified);
		update(theUpdateMap);
	}

	private void setNabcast_mailnotified(Map<String, Object> inUpdateMap, int nabcast_mailnotified) {
		if (this.nabcast_mailnotified != nabcast_mailnotified) {
			this.nabcast_mailnotified = nabcast_mailnotified;
			inUpdateMap.put("nabcast_mailnotified", nabcast_mailnotified);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Nabcast#getNabcast_shortcut()
	 */
	public String getNabcast_shortcut() {
		return this.nabcast_shortcut;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Nabcast#getNabcast_title()
	 */
	public String getNabcast_title() {
		return this.nabcast_title;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Nabcast#getNabcast_id()
	 */
	public long getNabcast_id() {
		return this.nabcast_id;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Nabcast#getNabcast_mailnotified()
	 */
	public int getNabcast_mailnotified() {
		return this.nabcast_mailnotified;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Nabcast#getNabcast_update()
	 */
	public int getNabcast_update() {
		return this.nabcast_update;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Nabcast#getAuthor()
	 */
	public User getAuthor() {
		return this.author.get(this.nabcast_author);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Nabcast#getSubscribers()
	 */
	public List<Subscriber> getSubscribers() {
		return SubscriberImpl.findByNabcast(this);
	}

	public static List<Nabcast> findByCateg(long idCateg, long langCategorie) {
		final List<Nabcast> list = new ArrayList<Nabcast>();
		try {
			final List<Object> values = new ArrayList<Object>();
			values.add(langCategorie);
			values.add(0);
			String condition = "nabcast_lang =? and nabcast_private=?";
			if (idCateg > 0) {
				values.add(idCateg);
				condition += " and nabcast_categ=?";
			}
			list.addAll(AbstractSQLRecord.findAll(NabcastImpl.SPECIFICATION, condition, values));
		} catch (final SQLException se) {
			NabcastImpl.LOGGER.fatal(se, se);
		}
		return list;
	}

	public static List<Nabcast> getListNabcastByCateg(long idCateg, int index, int nbr) {
		final List<Nabcast> list = new ArrayList<Nabcast>();
		final SgbdConnection theConnection = new SgbdConnection(SGBD_ACCESS.READONLY);
		try {
			String query = "SELECT nabcast_id as " + NabcastImpl.SPECIFICATION.getTablePrefix() + "nabcast_id, " + "nabcast_title as " + NabcastImpl.SPECIFICATION.getTablePrefix() + "nabcast_title, " + "nabcast_categ as " + NabcastImpl.SPECIFICATION.getTablePrefix() + "nabcast_categ, " + "nabcast_desc as " + NabcastImpl.SPECIFICATION.getTablePrefix() + "nabcast_desc, " + "nabcast_postupdate as " + NabcastImpl.SPECIFICATION.getTablePrefix() + "nabcast_postupdate, " + "nabcast_private as " + NabcastImpl.SPECIFICATION.getTablePrefix() + "nabcast_private, " + "nabcast_author as " + NabcastImpl.SPECIFICATION.getTablePrefix() + "nabcast_author, " + "nabcast_mailnotified as " + NabcastImpl.SPECIFICATION.getTablePrefix() + "nabcast_mailnotified, " + "nabcast_update as "
					+ NabcastImpl.SPECIFICATION.getTablePrefix() + "nabcast_update, " + "nabcast_color_sign as " + NabcastImpl.SPECIFICATION.getTablePrefix() + "nabcast_color_sign, " + "nabcast_anim_sign as " + NabcastImpl.SPECIFICATION.getTablePrefix() + "nabcast_anim_sign, " + "nabcast_music_sign as " + NabcastImpl.SPECIFICATION.getTablePrefix() + "nabcast_music_sign, " + "nabcast_lang as " + NabcastImpl.SPECIFICATION.getTablePrefix() + "nabcast_lang, count(subscriber_nabcast) countsubs " + "FROM nabcast,subscriber, user " + "WHERE nabcast_private=0 AND nabcast_author = user_id AND nabcast_categ=? and subscriber_nabcast=nabcast_id group by subscriber_nabcast order by nabcast_postupdate desc, countsubs desc";
			final List<Object> vals = new ArrayList<Object>();

			vals.add(idCateg);

			if (nbr > 0) {
				query += " LIMIT ?,?";
				vals.add(index);
				vals.add(nbr);
			}

			final SgbdResult theSgbdResult = theConnection.doQueryPS(query, vals, 0, false);
			try {
				final ResultSet theResultSet = theSgbdResult.getResultSet();
				while (theResultSet.next()) {
					list.add(ObjectRecord.createObject(NabcastImpl.SPECIFICATION, theResultSet));
				}
			} finally {
				theSgbdResult.close();
			}
		} catch (final SQLException se) {
			NabcastImpl.LOGGER.fatal(se, se);
		} finally {
			theConnection.close();
		}
		return list;
	}

	private static final int NABCAST_TTL = 30;

	public static int walkNabcastActive(RecordWalker<Nabcast> inWalker) {
		final List<Object> theValues = Collections.singletonList((Object) NabcastImpl.NABCAST_TTL);
		final String condition = " nabcast_update <= ? ";
		int theResult = 0;
		try {
			theResult = AbstractSQLRecord.walk(NabcastImpl.SPECIFICATION, condition, theValues, null /*
																																				 * order
																																				 * by
																																				 */, 0 /* skip */, inWalker);
		} catch (final SQLException anException) {
			NabcastImpl.LOGGER.fatal(anException, anException);
		}
		return theResult;
	}
}

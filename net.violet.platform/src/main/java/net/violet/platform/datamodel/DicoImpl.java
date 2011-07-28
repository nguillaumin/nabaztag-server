package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNull;
import net.violet.platform.datamodel.util.UpdateMap;
import net.violet.platform.util.CCalendar;

public final class DicoImpl extends ObjectRecord<Dico, DicoImpl> implements Dico {

	public static final SQLObjectSpecification<DicoImpl> SPECIFICATION = new SQLObjectSpecification<DicoImpl>("dico", DicoImpl.class, new SQLKey[] { new SQLKey("dico_id"), new SQLKey(new String[] { "dico_key", "dico_lang" }) });

	private static final String[] NEW_COLUMNS = new String[] { "dico_key", "dico_lang", "dico_text", "dico_page", "dico_creation" };

	protected long dico_id;
	protected String dico_key;
	protected Long dico_lang;
	protected String dico_text;
	protected String dico_page;
	protected Integer dico_creation;

	private final SingleAssociationNull<Dico, Lang, LangImpl> mLang;

	protected DicoImpl() {
		this.mLang = new SingleAssociationNull<Dico, Lang, LangImpl>(this, "dico_lang", LangImpl.SPECIFICATION, LangImpl.SPECIFICATION.getPrimaryKey());
	}

	protected DicoImpl(long inId) throws SQLException {
		init(inId);
		this.mLang = new SingleAssociationNull<Dico, Lang, LangImpl>(this, "dico_lang", LangImpl.SPECIFICATION, LangImpl.SPECIFICATION.getPrimaryKey());
	}

	public DicoImpl(String inKey, Lang inLang, String inText, String inPage) throws SQLException {

		this.dico_key = inKey.replaceAll(Dico.LOC_KEY, net.violet.common.StringShop.EMPTY_STRING);
		this.dico_lang = inLang.getId();
		this.dico_text = inText;
		this.dico_page = inPage;
		this.dico_creation = CCalendar.getCurrentTimeInSecond();
		init(DicoImpl.NEW_COLUMNS);
		this.mLang = new SingleAssociationNull<Dico, Lang, LangImpl>(this, "dico_lang", LangImpl.SPECIFICATION, LangImpl.SPECIFICATION.getPrimaryKey());
	}

	@Override
	public Long getId() {
		return this.dico_id;
	}

	@Override
	public SQLObjectSpecification<DicoImpl> getSpecification() {
		return DicoImpl.SPECIFICATION;
	}

	public String getDico_key() {
		return this.dico_key;
	}

	public String getDico_text() {
		return this.dico_text;
	}

	public String getDico_page() {
		return this.dico_page;
	}

	private static List<Dico> listKeyByPageAndLang(String page, long lang) {
		try {
			return new LinkedList<Dico>(AbstractSQLRecord.findAll(DicoImpl.SPECIFICATION, "dico_page=? and dico_lang=?", Arrays.asList(new Object[] { page, lang })));
		} catch (final SQLException se) {
			// This space for rent.
		}
		return Collections.emptyList();
	}

	// Utilisé dans les JSP.
	public static List<String> listKey(String page) {
		final List<String> listKey = new ArrayList<String>();
		for (final Dico dico : DicoImpl.listKeyByPageAndLang(page, 1)) {
			listKey.add(dico.getDico_key());
		}
		return listKey;
	}

	public Lang getDicoLang() {
		return this.mLang.get(this.dico_lang);
	}

	/**
	 * @see net.violet.platform.datamodel.Dico#setText(java.lang.String)
	 */
	public void setText(String translation) {

		final UpdateMap updater = new UpdateMap();
		this.dico_text = updater.updateField("dico_text", this.dico_text, translation);

		update(updater);
	}

	public void update(String inKey, Lang inLang, String inText, String inPage) {
		final UpdateMap updater = new UpdateMap();
		this.dico_text = updater.updateField("dico_text", this.dico_text, inText);
		this.dico_key = updater.updateField("dico_key", this.dico_key, inKey);
		this.dico_lang = updater.updateField("dico_lang", this.dico_lang, inLang.getId());
		this.dico_page = updater.updateField("dico_page", this.dico_page, inPage);
		if (!updater.isEmpty()) {
			this.dico_creation = updater.updateField("dico_creation", this.dico_creation, CCalendar.getCurrentTimeInSecond());
		}

		update(updater);
	}

}

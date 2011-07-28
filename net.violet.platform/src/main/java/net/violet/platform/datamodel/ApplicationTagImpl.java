package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNull;

public class ApplicationTagImpl extends ObjectRecord<ApplicationTag, ApplicationTagImpl> implements ApplicationTag {

	public static final SQLObjectSpecification<ApplicationTagImpl> SPECIFICATION = new SQLObjectSpecification<ApplicationTagImpl>("application_tag", ApplicationTagImpl.class, new SQLKey("tag_id"));

	private static final String[] NEW_COLUMNS = new String[] { "tag_name", "lang_id", };

	protected long tag_id;
	protected String tag_name;
	protected long lang_id;
	protected int tag_size;

	private final SingleAssociationNull<ApplicationTag, Lang, LangImpl> mLang;

	public ApplicationTagImpl() {
		this.mLang = new SingleAssociationNull<ApplicationTag, Lang, LangImpl>(this, "lang_id", LangImpl.SPECIFICATION, LangImpl.SPECIFICATION.getPrimaryKey());
	}

	public ApplicationTagImpl(long inId) throws SQLException {
		init(inId);
		this.mLang = new SingleAssociationNull<ApplicationTag, Lang, LangImpl>(this, "lang_id", LangImpl.SPECIFICATION, LangImpl.SPECIFICATION.getPrimaryKey());
	}

	public ApplicationTagImpl(String inName, Lang inLang) throws SQLException {
		this.tag_name = inName;
		this.lang_id = inLang.getId();

		init(ApplicationTagImpl.NEW_COLUMNS);

		this.mLang = new SingleAssociationNull<ApplicationTag, Lang, LangImpl>(this, "lang_id", LangImpl.SPECIFICATION, LangImpl.SPECIFICATION.getPrimaryKey());
	}

	@Override
	public SQLObjectSpecification<ApplicationTagImpl> getSpecification() {
		return ApplicationTagImpl.SPECIFICATION;
	}

	public Lang getLang() {
		return this.mLang.get(this.lang_id);
	}

	public String getName() {
		return this.tag_name;
	}

	public int getSize() {
		return this.tag_size;
	}

	public void setSize(int inSize) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setTag_size(theUpdateMap, inSize);
		update(theUpdateMap);
	}

	private void setTag_size(Map<String, Object> inUpdateMap, int inSize) {
		if (this.tag_size != inSize) {
			this.tag_size = inSize;
			inUpdateMap.put("tag_size", inSize);
		}
	}

}

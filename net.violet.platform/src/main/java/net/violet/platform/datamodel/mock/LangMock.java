package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Lang;

public class LangMock extends AbstractMockRecord<Lang, LangMock> implements Lang {

	public static final MockBuilder<Lang> BUILDER = new MockBuilder<Lang>() {

		@Override
		protected Lang build(String[] inParamValues) {
			return new LangMock(Long.parseLong(inParamValues[0]), inParamValues[1], Long.parseLong(inParamValues[3]), inParamValues[4], Integer.parseInt(inParamValues[5]));
		}
	};

	// / Champs SQL
	protected String lang_title;
	protected long lang_type;
	protected String lang_isocode;
	protected int lang_newtype;

	protected LangMock(long inId, String inTitle, long inType, String inISOCode, int inNewtype) {
		super(inId);
		this.lang_title = inTitle;
		this.lang_type = inType;
		this.lang_isocode = inISOCode;
		this.lang_newtype = inNewtype;
	}

	public long getHelpLangId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getIsoCode() {
		return this.lang_isocode;
	}

	public String getTitle() {
		return this.lang_title;
	}

	public long getType() {
		return this.lang_type;
	}

	public int getNewType() {
		return this.lang_newtype;
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

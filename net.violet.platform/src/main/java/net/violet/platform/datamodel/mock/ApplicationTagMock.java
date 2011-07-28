package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.ApplicationTag;
import net.violet.platform.datamodel.Lang;

public class ApplicationTagMock extends AbstractMockRecord<ApplicationTag, ApplicationTagMock> implements ApplicationTag {

	private static long ID = 1;

	private final String tag_name;
	private final Lang theLang;
	private int tag_size;

	public ApplicationTagMock(long id, String inName, Lang inLang, int inSize) {
		super(id);
		this.tag_name = inName;
		this.theLang = inLang;
		this.tag_size = inSize;
	}

	public ApplicationTagMock(String inName, Lang inLang, int inSize) {
		super(ApplicationTagMock.ID++);
		this.tag_name = inName;
		this.theLang = inLang;
		this.tag_size = inSize;
	}

	public ApplicationTagMock(String inName, Lang inLang) {
		this(inName, inLang, 0);
	}

	public Lang getLang() {
		return this.theLang;
	}

	public String getName() {
		return this.tag_name;
	}

	public int getSize() {
		return this.tag_size;
	}

	public void setSize(int inSize) {
		this.tag_size = inSize;
	}

}

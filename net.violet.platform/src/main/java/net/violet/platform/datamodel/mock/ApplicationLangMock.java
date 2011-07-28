package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationLang;
import net.violet.platform.datamodel.Lang;

/**
 *
 * @author christophe - Violet
 */
public class ApplicationLangMock extends AbstractMockRecord<ApplicationLang, ApplicationLangMock> implements ApplicationLang {

	private final Application mApplication;
	private final Lang mLang;
	private long mRank;

	public ApplicationLangMock(Application inApp, Lang inLang, int inRank) {
		super(0); // generate an auto-incremented id
		this.mApplication = inApp;
		this.mLang = inLang;
		this.mRank = inRank;
	}

	public Application getApplication() {
		return this.mApplication;
	}

	public Lang getLang() {
		return this.mLang;
	}

	public long getRank() {
		return this.mRank;
	}

	public void setRank(long rnk) {
		this.mRank = rnk;
	}

}

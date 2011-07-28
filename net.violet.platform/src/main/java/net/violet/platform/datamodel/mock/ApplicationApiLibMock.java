package net.violet.platform.datamodel.mock;

import java.util.Date;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.applets.AppLanguages;
import net.violet.platform.datamodel.ApplicationApiLib;
import net.violet.platform.datamodel.Files;

/**
 * @author christophe - Violet
 */
public class ApplicationApiLibMock extends AbstractMockRecord<ApplicationApiLib, ApplicationApiLibMock> implements ApplicationApiLib {

	private final AppLanguages mLang;
	private final String mApiVersion;
	private final String mCode;
	private final byte[] mByteCode;
	private final Date mReleaseDate;

	public ApplicationApiLibMock(AppLanguages inLang, String inApiVersion, String inCode) {
		super(0);
		this.mLang = inLang;
		this.mApiVersion = inApiVersion;
		this.mCode = inCode;
		this.mByteCode = new byte[0];
		this.mReleaseDate = new Date();
	}

	public ApplicationApiLibMock(AppLanguages inLang, String inApiVersion, byte[] inByteCode) {
		super(0);
		this.mLang = inLang;
		this.mApiVersion = inApiVersion;
		this.mCode = net.violet.common.StringShop.EMPTY_STRING;
		this.mByteCode = inByteCode;
		this.mReleaseDate = new Date();
	}

	public String getApiVersion() {
		return this.mApiVersion;
	}

	public AppLanguages getLanguage() {
		return this.mLang;
	}

	public String getCode() {
		return this.mCode;
	}

	public byte[] getByteCode() {
		return this.mByteCode;
	}

	public Date getReleaseDate() {
		return this.mReleaseDate;
	}

	public Files getSourceFile() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.violet.platform.datamodel.ApplicationApiLib#setSourceFile(net.violet
	 * .platform.datamodel.Files, java.util.Date)
	 */
	public void setSourceFile(Files inSrcFile, Date inReleaseDate) {
		// TODO Auto-generated method stub

	}
}

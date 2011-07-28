package net.violet.platform.datamodel.mock;

import java.sql.Timestamp;
import java.util.Date;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.applets.AppLanguages;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationApiLib;
import net.violet.platform.datamodel.ApplicationPackage;
import net.violet.platform.datamodel.Files;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author christophe - Violet
 */
public class ApplicationPackageMock extends AbstractMockRecord<ApplicationPackage, ApplicationPackageMock> implements ApplicationPackage {

	private final String mSource;
	private final AppLanguages mLang;
	private final String mApiVersion;
	private final Timestamp modification_date;

	protected ApplicationPackageMock(Application app, AppLanguages lang, String inApiVersion, String src, Date modificationDate) {
		super(app.getId());
		this.mSource = src;
		this.mLang = lang;
		this.mApiVersion = inApiVersion;
		this.modification_date = new Timestamp(modificationDate.getTime());
	}

	public AppLanguages getLanguage() {
		return this.mLang;
	}

	public String getApiVersion() {
		return this.mApiVersion;
	}

	public String getSource() {
		return this.mSource;
	}

	public Files getSourceFile() {
		throw new NotImplementedException();
	}

	public byte[] getByteSource() {
		throw new NotImplementedException();
	}

	public Date getModificationDate() {
		return this.modification_date;
	}

	public ApplicationApiLib getApiLib() {
		throw new NotImplementedException();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.violet.platform.datamodel.ApplicationPackage#updateSourceFile(com
	 * .violet.platform.datamodel.Files)
	 */
	public void updateSourceFile(Files newSourceFile, String inApiVersion) {
		// TODO Auto-generated method stub

	}

}

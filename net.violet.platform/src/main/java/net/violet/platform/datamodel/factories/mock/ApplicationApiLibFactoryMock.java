package net.violet.platform.datamodel.factories.mock;

import java.util.ArrayList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.applets.AppLanguages;
import net.violet.platform.datamodel.ApplicationApiLib;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.factories.ApplicationApiLibFactory;
import net.violet.platform.datamodel.mock.ApplicationApiLibMock;

/**
 * @author christophe - Violet
 */
public class ApplicationApiLibFactoryMock extends RecordFactoryMock<ApplicationApiLib, ApplicationApiLibMock> implements ApplicationApiLibFactory {

	ApplicationApiLibFactoryMock() {
		super(ApplicationApiLibMock.class);
	}

	/**
	 * @see net.violet.platform.datamodel.factories.ApplicationApiLibFactory#findByLanguageAndVersion(net.violet.platform.applets.AppLanguages,
	 *      java.lang.String)
	 */
	public ApplicationApiLib findByLanguageAndVersion(AppLanguages inLanguage, String inVersion) {
		for (final ApplicationApiLib apiLib : findAll()) {
			if (apiLib.getLanguage().equals(inLanguage) && apiLib.getApiVersion().equals(inVersion)) {
				return apiLib;
			}
		}
		return null;

	}

	/**
	 * @see net.violet.platform.datamodel.factories.ApplicationApiLibFactory#findAllByLanguage(net.violet.platform.applets.AppLanguages)
	 */
	public List<ApplicationApiLib> findAllByLanguage(AppLanguages inLanguage) {
		final List<ApplicationApiLib> theResult = new ArrayList<ApplicationApiLib>();
		for (final ApplicationApiLib theAppli : findAll()) {
			if (theAppli.getLanguage().equals(inLanguage)) {
				theResult.add(theAppli);
			}
		}
		return theResult;

	}

	/**
	 * @see net.violet.platform.datamodel.factories.ApplicationApiLibFactory#findLastVersionForLanguage(net.violet.platform.applets.AppLanguages)
	 */
	public ApplicationApiLib findLastVersionForLanguage(AppLanguages inLanguage) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.violet.platform.datamodel.factories.ApplicationApiLibFactory#create
	 * (net.violet.platform.applets.AppLanguages, java.lang.String,
	 * java.lang.String, java.lang.Long)
	 */
	public ApplicationApiLib create(AppLanguages language, String version, Files inCodeFile) {
		throw new UnsupportedOperationException();
	}

	public boolean usesFiles(Files inFile) {
		return false;
	}
}

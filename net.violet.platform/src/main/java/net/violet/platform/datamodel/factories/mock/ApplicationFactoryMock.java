package net.violet.platform.datamodel.factories.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCategory;
import net.violet.platform.datamodel.ApplicationSetting;
import net.violet.platform.datamodel.ApplicationTag;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.ApplicationFactory;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationMock;

public class ApplicationFactoryMock extends RecordFactoryMock<Application, ApplicationMock> implements ApplicationFactory {

	ApplicationFactoryMock() {
		super(ApplicationMock.class);
	}

	@Override
	public void loadCache() {
		ApplicationMock.BUILDER.generateValuesFromInitFile(10, net.violet.platform.util.Constantes.OS_PATH + "net/violet/platform/datamodel/mock/applicationInit");
	}

	public Application findByName(String theApplicationName) {
		for (final Application theAppli : findAll()) {
			if (theAppli.getName().equals(theApplicationName)) {
				return theAppli;
			}
		}

		return null;
	}

	public List<Application> findByCategory(ApplicationCategory inCategory, int inSkip, int inGetCount) {
		final List<Application> result = new ArrayList<Application>();
		if (inCategory != null) {
			for (final Application application : findAll()) {
				if (application.isVisible() && inCategory.equals(application.getCategory())) {
					result.add(application);
				}
			}
			return getSkipList(result, inSkip, inGetCount);
		}
		return Collections.emptyList();
	}

	public List<Application> findAllByTag(ApplicationTag inTag) {
		final List<Application> result = new ArrayList<Application>();
		for (final Application application : findAll()) {
			if (application.isVisible() && application.getTags().contains(inTag)) {
				result.add(application);
			}
		}
		return result;
	}

	public List<Application> findAllByTagAndLang(ApplicationTag inTag, Lang inLang, int inSkip, int inGetCount) {
		final List<Application> result = new ArrayList<Application>();
		for (final Application application : findAll()) {
			if (application.isVisible() && application.getTags().contains(inTag) && inTag.getLang().equals(inLang)) {
				result.add(application);
			}
		}
		return getSkipList(result, inSkip, inGetCount);
	}

	public long countByTagAndLang(ApplicationTag inTag, Lang inLang) {
		long theResult = 0;
		for (final Application application : findAll()) {
			if (application.isVisible() && application.getTags().contains(inTag) && inTag.getLang().equals(inLang)) {
				theResult++;
			}
		}
		return theResult;
	}

	public List<Application> findAllByTagAndLangAndHardware(ApplicationTag inTag, Lang inLang, HARDWARE inHardware, int inSkip, int inGetCount) {
		final List<Application> result = new ArrayList<Application>();
		for (final Application application : findAll()) {
			if (application.isVisible() && application.getTags().contains(inTag) && inTag.getLang().equals(inLang) && application.getSupportedHardwares().contains(inHardware)) {
				result.add(application);
			}
		}
		return getSkipList(result, inSkip, inGetCount);
	}

	public long countByTagAndLangAndHardware(ApplicationTag inTag, Lang inLang, HARDWARE inHardware) {
		long theResult = 0;
		for (final Application application : findAll()) {
			if (application.isVisible() && application.getTags().contains(inTag) && inTag.getLang().equals(inLang) && application.getSupportedHardwares().contains(inHardware)) {
				theResult++;
			}
		}
		return theResult;
	}

	public List<Application> findAllByLangAndRank(Lang lang, int inSkip, int inGetCount) {
		throw new UnsupportedOperationException();
	}

	public List<Application> findAllByLangAndCateg(Lang inLang, ApplicationCategory inCateg) {
		final List<Application> result = new ArrayList<Application>();

		for (final Application application : findAll()) {
			if (application.getLangs().contains(inLang) && application.isVisible() && application.getCategory().equals(inCateg)) {
				result.add(application);
			}
		}

		return result;
	}

	public List<Application> findByCategoryAndHardware(ApplicationCategory inCategory, HARDWARE inHardware, int inSkip, int inGetCount) {
		final List<Application> result = new ArrayList<Application>();
		for (final Application application : findAll()) {
			if (application.isVisible() && application.getCategory().equals(inCategory) && application.getSupportedHardwares().contains(inHardware)) {
				result.add(application);
			}
		}
		return getSkipList(result, inSkip, inGetCount);
	}

	public List<Application> findByCategoryAndLang(ApplicationCategory inCategory, Lang inLang, int inSkip, int inGetCount) {
		final List<Application> result = new ArrayList<Application>();
		for (final Application application : findAll()) {
			if ((application != null) && (application.getCategory() != null) && application.isVisible() && application.getCategory().equals(inCategory) && application.getLangs().contains(inLang)) {
				result.add(application);
			}
		}
		return getSkipList(result, inSkip, inGetCount);
	}

	public List<Application> findByCategoryAndLangAndHardware(ApplicationCategory inCategory, Lang inLang, HARDWARE inHardware, int inSkip, int inGetCount) {
		final List<Application> result = new ArrayList<Application>();
		for (final Application application : findAll()) {
			if (application.isVisible() && application.getCategory().equals(inCategory) && application.getLangs().contains(inLang) && application.getSupportedHardwares().contains(inHardware)) {
				result.add(application);
			}
		}
		return getSkipList(result, inSkip, inGetCount);
	}

	public long countByCategory(ApplicationCategory inCategory) {
		long theResult = 0;
		for (final Application application : findAll()) {
			if (application.isVisible() && application.getCategory().equals(inCategory)) {
				theResult++;
			}
		}

		return theResult;
	}

	public long countByCategoryAndHardware(ApplicationCategory inCategory, HARDWARE inHardware) {
		long theResult = 0;
		for (final Application application : findAll()) {
			if (application.isVisible() && application.getCategory().equals(inCategory) && application.getSupportedHardwares().contains(inHardware)) {
				theResult++;
			}
		}
		return theResult;
	}

	public long countByCategoryAndLang(ApplicationCategory inCategory, Lang inLang) {
		long theResult = 0;
		for (final Application application : findAll()) {
			if (application.getCategory().equals(inCategory) && application.getLangs().contains(inLang)) {
				theResult++;
			}
		}
		return theResult;
	}

	public long countByCategoryAndLangAndHardware(ApplicationCategory inCategory, Lang inLang, HARDWARE inHardware) {
		long theResult = 0;
		for (final Application application : findAll()) {
			if (application.getCategory().equals(inCategory) && application.getLangs().contains(inLang) && application.getSupportedHardwares().contains(inHardware)) {
				theResult++;
			}
		}
		return theResult;
	}

	public Application findByLink(String link) {
		for (final Application application : findAll()) {
			if (application.getLink().equals(link)) {
				return application;
			}
		}

		return null;
	}

	public List<Application> findByLangAndApplicationNameStartingWith(Lang inLang, String startWith) {
		final List<Application> result = new ArrayList<Application>();
		for (final Application application : findAll()) {
			if (application.getLangs().contains(inLang) && application.getName().startsWith(startWith)) {
				result.add(application);
			}
		}
		return result;
	}

	public Application create(User inAuthor, String inName, ApplicationClass inClass, ApplicationCategory inCategory, boolean isInteractive, boolean isVisible, boolean isRemovable) {
		return new ApplicationMock(0L, inAuthor, inName, inClass, inCategory, isInteractive, isVisible, isRemovable);
	}

	public List<Application> findAllBySetting(String key, String value) {
		final List<Application> applications = new ArrayList<Application>();
		for (final Application anApplication : findAll()) {
			final ApplicationSetting setting = Factories.APPLICATION_SETTING.findByApplicationAndKey(anApplication, key);
			if ((setting != null) && setting.getValue().equals(value)) {
				applications.add(anApplication);
			}
		}
		return applications;
	}

}

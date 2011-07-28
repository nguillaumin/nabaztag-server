package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationLang;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.factories.ApplicationLangFactory;
import net.violet.platform.datamodel.mock.ApplicationLangMock;

/**
 *
 * @author christophe - Violet
 */
public class ApplicationLangFactoryMock extends RecordFactoryMock<ApplicationLang, ApplicationLangMock> implements ApplicationLangFactory {

	/**
	 * @param inSpecification
	 */
	ApplicationLangFactoryMock() {
		super(ApplicationLangMock.class);
	}

	/**
	 * @see net.violet.platform.datamodel.factories.ApplicationLangFactory#find(net.violet.platform.datamodel.Application, net.violet.platform.datamodel.Lang)
	 */
	public ApplicationLang find(Application inApp, Lang inLang) {
		ApplicationLang found = null;

		for (final ApplicationLang appLang : findAllMapped().values()) {
			if (appLang.getApplication().equals(inApp) && appLang.getLang().equals(inLang)) {
				found = appLang;
				break;
			}
		}

		return found;
	}

}

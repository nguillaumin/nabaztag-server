package net.violet.platform.datamodel.factories;

import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationLang;
import net.violet.platform.datamodel.Lang;

/**
 *
 * @author christophe - Violet
 */
public interface ApplicationLangFactory {

	ApplicationLang find(Application inApp, Lang inLang);
}

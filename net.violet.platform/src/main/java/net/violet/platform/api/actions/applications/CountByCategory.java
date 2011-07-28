package net.violet.platform.api.actions.applications;

import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.actions.AbstractAction.APIMethodParam.Level;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchCategoryException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ApplicationCategoryData;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.SiteLangData;
import net.violet.platform.util.Constantes;


public class CountByCategory extends AbstractAction {

	@APIMethodParam(Level.OPTIONAL)
	private static final String LANGUAGE = "language";
	@APIMethodParam(Level.OPTIONAL)
	private static final String HARDWARE = "hw_type";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws NoSuchCategoryException, InvalidParameterException {

		final ApplicationCategoryData theCategory = ApplicationCategoryData.findByName(inParam.getMainParamAsString());
		if (theCategory == null) {
			throw new NoSuchCategoryException(inParam.getMainParamAsString());
		}

		final String thelanguageCode = inParam.getString(CountByCategory.LANGUAGE, false);
		final String theHardwareType = inParam.getString(CountByCategory.HARDWARE, false);
		final long theCount;

		if ((thelanguageCode != null) && (theHardwareType == null)) { // count par catégorie et langue

			final SiteLangData languageData = SiteLangData.getByISOCode(thelanguageCode);

			theCount = ApplicationData.countByCategoryAndLang(theCategory, languageData);

		} else if (theHardwareType != null) {
			final ObjectType theObjectType = ObjectType.findByName(theHardwareType);

			if (!theObjectType.isPrimaryObject()) {
				throw new InvalidParameterException(APIErrorMessage.NOT_A_PRIMITIVE_TYPE, theHardwareType);
			}

			if (thelanguageCode == null) { // count par catégorie et hardware
				theCount = ApplicationData.countByCategoryAndObjectType(theCategory, theObjectType);
			} else { // count par catégorie et langue et hardware
				theCount = ApplicationData.countByCategoryAndObjectTypeAndLang(theCategory, theObjectType, SiteLangData.getByISOCode(thelanguageCode));
			}
		} else { // count uniquement par catégorie
			theCount = ApplicationData.countByCategory(theCategory);
		}

		return theCount;
	}

	public long getExpirationTime() {
		return Constantes.TWO_HOURS_IN_S;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

	public ActionType getType() {
		return ActionType.GET;
	}

	public boolean isCacheable() {
		return true;
	}

}

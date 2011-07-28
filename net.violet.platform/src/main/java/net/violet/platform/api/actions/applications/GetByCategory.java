package net.violet.platform.api.actions.applications;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchCategoryException;
import net.violet.platform.api.maps.applications.ApplicationInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ApplicationCategoryData;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.SiteLangData;
import net.violet.platform.util.Constantes;

public class GetByCategory extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws NoSuchCategoryException, InvalidParameterException {

		final ApplicationCategoryData theCategory = ApplicationCategoryData.findByName(inParam.getMainParamAsString());
		if (theCategory == null) {
			throw new NoSuchCategoryException(inParam.getMainParamAsString());
		}

		final int skip = inParam.getInt("skip", 0);
		final int count = inParam.getInt("count", 32); //Le remettre à 10 lorsqu'ils auront corriger leur problème de cache( demande d'Olivier)

		final String theHardwareType = inParam.getString("hw_type", false);
		final String language = inParam.getString("language", false);

		final List<ApplicationData> appList;

		if ((language != null) && (theHardwareType == null)) { // recherche par catégorie et langue
			final SiteLangData languageData = SiteLangData.getByISOCode(language);

			appList = ApplicationData.findByCategoryAndLang(theCategory, languageData, skip, count);

		} else if (theHardwareType != null) {
			final ObjectType theObjectType = ObjectType.findByName(theHardwareType);

			if (!theObjectType.isPrimaryObject()) {
				throw new InvalidParameterException(APIErrorMessage.NOT_A_PRIMITIVE_TYPE, theHardwareType);
			}

			if (language == null) { // recherche par catégorie et hardware
				appList = ApplicationData.findByCategoryAndObjectType(theCategory, theObjectType, skip, count);

			} else { // recherche par catégorie et langue et hardware
				appList = ApplicationData.findByCategoryAndObjectTypeAndLang(theCategory, theObjectType, SiteLangData.getByISOCode(language), skip, count);
			}
		} else { // recherche uniquement par catégorie
			appList = ApplicationData.findByCategory(theCategory, skip, count);
		}

		final List<ApplicationInformationMap> appInfoMaps = new ArrayList<ApplicationInformationMap>();

		for (final ApplicationData appData : appList) {
			appInfoMaps.add(new ApplicationInformationMap(inParam.getCaller(), appData));
		}

		return appInfoMaps;
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

package net.violet.platform.api.actions.admin.applications;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchCategoryException;
import net.violet.platform.api.maps.applications.ApplicationAdminMap;
import net.violet.platform.api.maps.applications.ApplicationInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ApplicationCategoryData;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SiteLangData;
import net.violet.platform.util.Constantes;

public class GetWithRank extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws NoSuchCategoryException, InvalidParameterException {

		final ApplicationCategoryData theCategory = ApplicationCategoryData.findByName(inParam.getMainParamAsString());
		if (theCategory == null) {
			throw new NoSuchCategoryException(inParam.getMainParamAsString());
		}

		final int skip = inParam.getInt("skip", 0);
		final int count = inParam.getInt("count", 32); //Le remettre à 10 lorsqu'ils auront corriger leur problème de cache( demande d'Olivier)

		final String language = inParam.getString("language", true);

		final SiteLangData languageData = SiteLangData.getByISOCode(language);

		final List<ApplicationData> appList = ApplicationData.findByCategoryAndLang(theCategory, languageData, skip, count);

		final List<ApplicationInformationMap> appInfoMaps = new ArrayList<ApplicationInformationMap>();

		for (final ApplicationData appData : appList) {
			final ApplicationInformationMap anInformationMap = new ApplicationInformationMap(inParam.getCaller(), appData);
			anInformationMap.put(ApplicationAdminMap.RANK, ApplicationData.getRank(appData.getReference(), languageData.getReference()));
			appInfoMaps.add(anInformationMap);
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

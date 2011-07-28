package net.violet.platform.api.actions.applications;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.applications.ApplicationInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SiteLangData;
import net.violet.platform.util.Constantes;

public class GetApplications extends AbstractAction {

	public static final String LANGUAGE_PARAM = "language";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException {

		final String startWith = inParam.getMainParamAsString();
		final String language = inParam.getString(GetApplications.LANGUAGE_PARAM, true);

		final List<ApplicationInformationMap> appInfoMaps = new ArrayList<ApplicationInformationMap>();

		final SiteLangData theLangData = SiteLangData.getByISOCode(language);

		if (!startWith.matches("net.violet.(rss|podcast|webradio|js|external).")) {
			throw new InvalidParameterException(APIErrorMessage.BAD_FORMAT, startWith);
		}

		final List<ApplicationData> appList = ApplicationData.findByLangAndApplicationNameStartingWith(theLangData, startWith);
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

package net.violet.platform.api.actions.admin.applications;

import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchApplicationException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SiteLangData;
import net.violet.platform.util.Constantes;

public class SetRank extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws NoSuchApplicationException, InvalidParameterException {

		final ApplicationData theApplication = getRequestedApplication(inParam, null);
		final String theLanguage = inParam.getString("language", true);
		final String theRank = inParam.getString("rank", true);

		final SiteLangData languageData = SiteLangData.getByISOCode(theLanguage);
		try {
			ApplicationData.setRank(theApplication.getReference(), languageData.getReference(), theRank);
		} catch (final NumberFormatException e) {
			throw new InvalidParameterException(APIErrorMessage.NOT_A_LONG, "rank", theRank);
		}

		return null;
	}

	public long getExpirationTime() {
		return Constantes.TWO_HOURS_IN_S;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

	public ActionType getType() {
		return ActionType.UPDATE;
	}

	public boolean isCacheable() {
		return true;
	}

}

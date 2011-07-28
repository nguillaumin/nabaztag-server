package net.violet.platform.api.actions.applications;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchTagException;
import net.violet.platform.api.maps.applications.ApplicationInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ApplicationTagData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.SiteLangData;

public class GetByTag extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws NoSuchTagException, InvalidParameterException {

		final String tagId = inParam.getMainParamAsString();

		final int skip = inParam.getInt("skip", 0);
		final int count = inParam.getInt("count", 10);

		final String thelanguageCode = inParam.getString("language", true);
		final String theHardwareType = inParam.getString("hw_type", false);

		final SiteLangData languageData = SiteLangData.getByISOCode(thelanguageCode);

		final ApplicationTagData theTag = ApplicationTagData.findByAPIId(tagId, inParam.getCallerAPIKey());
		if (theTag == null) {
			throw new NoSuchTagException();
		}

		final List<ApplicationData> inAppList;

		if (theHardwareType != null) { // recherche par tag et langue et
			// hardware
			final ObjectType theObjectType = ObjectType.findByName(theHardwareType);

			if (!theObjectType.isPrimaryObject()) {
				throw new InvalidParameterException(APIErrorMessage.NOT_A_PRIMITIVE_TYPE, theHardwareType);
			}

			inAppList = ApplicationData.findAllByTagAndLangAndObjectType(theTag, languageData, theObjectType, skip, count);

		} else { // recherche par tag et langue
			inAppList = ApplicationData.findAllByTagAndLang(theTag, languageData, skip, count);
		}

		final List<ApplicationInformationMap> theResult = new ArrayList<ApplicationInformationMap>();

		for (final ApplicationData application : inAppList) {
			theResult.add(new ApplicationInformationMap(inParam.getCaller(), application));
		}

		return theResult;
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.GET;
	}

	public boolean isCacheable() {
		return false;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}

package net.violet.platform.api.actions.applications;

import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchTagException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ApplicationTagData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.SiteLangData;

public class CountByTag extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws NoSuchTagException, InvalidParameterException {

		final String tagId = inParam.getMainParamAsString();

		final String thelanguageCode = inParam.getString("language", true);
		final String theHardwareType = inParam.getString("hw_type", false);

		final SiteLangData languageData = SiteLangData.getByISOCode(thelanguageCode);

		final ApplicationTagData theTag = ApplicationTagData.findByAPIId(tagId, inParam.getCallerAPIKey());
		if (theTag == null) {
			throw new NoSuchTagException();
		}

		final long theCount;

		if (theHardwareType != null) { // count par tag et langue et hardware
			final ObjectType theObjectType = ObjectType.findByName(theHardwareType);

			if (!theObjectType.isPrimaryObject()) {
				throw new InvalidParameterException(APIErrorMessage.NOT_A_PRIMITIVE_TYPE, theHardwareType);
			}

			theCount = ApplicationData.countByTagAndLangAndObjectType(theTag, languageData, theObjectType);

		} else { // count par tag et langue
			theCount = ApplicationData.countByTagAndLang(theTag, languageData);
		}

		return theCount;
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

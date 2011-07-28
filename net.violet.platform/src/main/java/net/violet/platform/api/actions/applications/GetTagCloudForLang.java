package net.violet.platform.api.actions.applications;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ApplicationTagData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.SiteLangData;

public class GetTagCloudForLang extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException {

		final String langCode = inParam.getMainParamAsString();
		final SiteLangData theLang = SiteLangData.getByISOCode(langCode);

		final int count = inParam.getInt("count", 10);

		final String theHardwareType = inParam.getString("hw_type", false);

		final List<ApplicationTagData> inTagList;

		if (theHardwareType != null) { // recherche par langue et hardware
			final ObjectType theObjectType = ObjectType.findByName(theHardwareType);

			if (!theObjectType.isPrimaryObject()) {
				throw new InvalidParameterException(APIErrorMessage.NOT_A_PRIMITIVE_TYPE, theHardwareType);
			}

			inTagList = ApplicationTagData.findAllByLanguageAndObjectType(theLang, theObjectType, 0, count);
		} else { // recherche par langue
			inTagList = ApplicationTagData.findAllByLanguage(theLang, 0, count);
		}

		final List<TagInformationMap> theResult = new ArrayList<TagInformationMap>();
		for (final ApplicationTagData tag : inTagList) {
			theResult.add(new TagInformationMap(tag, inParam.getCaller()));
		}

		return theResult;
	}

	public long getExpirationTime() {
		return 0;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

	public ActionType getType() {
		return ActionType.GET;
	}

	public boolean isCacheable() {
		return false;
	}

	private static class TagInformationMap extends AbstractAPIMap {

		public TagInformationMap(ApplicationTagData tag, APICaller inCaller) {
			super();

			put("id", tag.getApiId(inCaller));
			put("name", tag.getName());
			put("lang", tag.getLang().getLang_iso_code());
			put("size", tag.getSize());

			seal();
		}

	}

}

package net.violet.platform.api.actions.dico;

import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.DicoInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.AbstractLangData;
import net.violet.platform.dataobjects.DicoData;
import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.dataobjects.SiteLangData;

public class Update extends AbstractAction {

	public static final String KEY_PARAM = "key";
	public static final String LANGUAGE_PARAM = "language";
	public static final String TEXT_PARAM = "text";
	public static final String PAGE_PARAM = "page";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException {

		final String theAPIId = inParam.getMainParamAsString();
		final String theKey = inParam.getString(Update.KEY_PARAM, true);
		final String theLanguage = inParam.getString(Update.LANGUAGE_PARAM, true);
		final String theText = inParam.getString(Update.TEXT_PARAM, true);

//		TODO: temporaire pour l'admin (langue API et My)
		AbstractLangData inLangData = null;
		try {
			inLangData = SiteLangData.getByISOCode(theLanguage);
		} catch (final InvalidParameterException e) {
			inLangData = ObjectLangData.getByISOCode(theLanguage);
		}

		final DicoData theDico = DicoData.findByAPIId(theAPIId, inParam.getCallerAPIKey());
		if (theDico == null) {
			throw new InvalidParameterException(theAPIId);
		}
		final String thePage = inParam.getString(Update.PAGE_PARAM, theDico.getPage());

		theDico.updateLine(theKey, inLangData, theText, thePage);

		return new DicoInformationMap(theDico, inParam.getCaller());
	}

	public long getExpirationTime() {
		return 0L;
	}

	public boolean isCacheable() {
		return false;
	}

	public ActionType getType() {
		return ActionType.UPDATE;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}

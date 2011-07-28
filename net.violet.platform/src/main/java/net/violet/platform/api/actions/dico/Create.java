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

public class Create extends AbstractAction {

	public static final String KEY_PARAM = "key";
	public static final String LANGUAGE_PARAM = "language";
	public static final String TEXT_PARAM = "text";
	public static final String PAGE_PARAM = "page";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException {

		final String theKey = inParam.getString(Create.KEY_PARAM, true);
		final String theLanguage = inParam.getString(Create.LANGUAGE_PARAM, true);
		final String theText = inParam.getString(Create.TEXT_PARAM, true);
		final String thePage = inParam.getString(Create.PAGE_PARAM, net.violet.common.StringShop.EMPTY_STRING);

//		TODO: temporaire pour l'admin (langue API et My)
		AbstractLangData inLangData = null;
		try {
			inLangData = SiteLangData.getByISOCode(theLanguage);
		} catch (final InvalidParameterException e) {
			inLangData = ObjectLangData.getByISOCode(theLanguage);
		}

		final DicoData theDicoEntry = DicoData.createLigne(theKey, inLangData, theText, thePage);

		return theDicoEntry.isValid() ? new DicoInformationMap(theDicoEntry, inParam.getCaller()) : null;
	}

	public long getExpirationTime() {
		return 0L;
	}

	public boolean isCacheable() {
		return false;
	}

	public ActionType getType() {
		return ActionType.CREATE;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}

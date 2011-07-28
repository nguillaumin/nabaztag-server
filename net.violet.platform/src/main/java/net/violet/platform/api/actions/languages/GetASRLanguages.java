package net.violet.platform.api.actions.languages;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.maps.LangInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.AsrLangData;
import net.violet.platform.util.Constantes;

public class GetASRLanguages extends AbstractLanguagesAction {

	private static final List<LangInformationMap> mAllASRLanguages = new ArrayList<LangInformationMap>();

	static {
		for (final AsrLangData theLangData : AsrLangData.getAllASRLanguages()) {
			GetASRLanguages.mAllASRLanguages.add(new LangInformationMap(theLangData));
		}
	}

	@Override
	protected Object doProcessRequest(ActionParam inParam) {
		return GetASRLanguages.mAllASRLanguages;
	}

	public ActionType getType() {
		return ActionType.GET;
	}

	public boolean isCacheable() {
		return true;
	}

	public long getExpirationTime() {
		return Constantes.ONE_DAY_IN_S;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}

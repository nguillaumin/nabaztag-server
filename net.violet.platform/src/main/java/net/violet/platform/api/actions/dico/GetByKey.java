package net.violet.platform.api.actions.dico;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchMessageException;
import net.violet.platform.api.maps.DicoInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.AbstractLangData;
import net.violet.platform.dataobjects.DicoData;
import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.dataobjects.SiteLangData;

public class GetByKey extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException {

		final String theDicoKey = inParam.getMainParamAsString();
		final String theLanguage = inParam.getString(Create.LANGUAGE_PARAM, false);

		final List<DicoData> theDataList = new LinkedList<DicoData>();
		if (theLanguage != null) {
//			TODO: temporaire pour l'admin (langue API et My)
			AbstractLangData inLangData = null;
			try {
				inLangData = SiteLangData.getByISOCode(theLanguage);
			} catch (final InvalidParameterException e) {
				inLangData = ObjectLangData.getByISOCode(theLanguage);
			}
			try {
				theDataList.add(DicoData.findDicoDataByDicoKeyAndLang(theDicoKey, inLangData.getReference()));
			} catch (final NoSuchMessageException e) {}
		} else {
			theDataList.addAll(DicoData.findByDicoKey(theDicoKey));
		}

		final List<DicoInformationMap> theDicoList = new ArrayList<DicoInformationMap>();
		final APICaller theCaller = inParam.getCaller();
		for (final DicoData inData : theDataList) {
			theDicoList.add(new DicoInformationMap(inData, theCaller));
		}
		return theDicoList;
	}

	public long getExpirationTime() {
		return 0L;
	}

	public boolean isCacheable() {
		return false;
	}

	public ActionType getType() {
		return ActionType.GET;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}

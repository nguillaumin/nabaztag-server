package net.violet.platform.api.actions.dico;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.DicoInformationMap;
import net.violet.platform.dataobjects.DicoData;

public class Export extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException {

		final int theIndex = inParam.getInt("index", 0);

		final List<DicoInformationMap> theExportList = new ArrayList<DicoInformationMap>();
		for (final DicoData aDicoData : DicoData.findAllForExport(theIndex)) {
			theExportList.add(new DicoInformationMap(aDicoData, inParam.getCaller()));
		}
		return theExportList;
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

}

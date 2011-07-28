package net.violet.platform.api.actions.stores;

import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchStoreException;
import net.violet.platform.api.maps.store.StoreInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.StoreData;

public class Get extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchStoreException {

		final String storeAPIId = inParam.getMainParamAsString();

		final APICaller theCaller = inParam.getCaller();

		final StoreData theStoreData = StoreData.findByAPIId(storeAPIId, theCaller.getAPIKey());
		if (theStoreData == null) {
			throw new NoSuchStoreException();
		}

		return new StoreInformationMap(theCaller, theStoreData);
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
}

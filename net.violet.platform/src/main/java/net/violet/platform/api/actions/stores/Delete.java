package net.violet.platform.api.actions.stores;

import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchStoreException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.StoreData;

public class Delete extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchStoreException, InternalErrorException {

		final String storeAPIId = inParam.getMainParamAsString();

		final StoreData theStoreData = StoreData.findByAPIId(storeAPIId, inParam.getCaller().getAPIKey());
		if (theStoreData == null) {
			throw new NoSuchStoreException();
		}

		if (!theStoreData.delete()) {
			throw new InternalErrorException(APIErrorMessage.DELETE_FAILED.getMessage());
		}

		return null;
	}

	public long getExpirationTime() {
		return 0;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

	public ActionType getType() {
		return ActionType.DELETE;
	}

	public boolean isCacheable() {
		return false;
	}
}

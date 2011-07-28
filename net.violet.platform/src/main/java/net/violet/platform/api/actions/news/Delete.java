package net.violet.platform.api.actions.news;

import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchNewsException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.NewsData;

public class Delete extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchNewsException, InternalErrorException {

		final String newsAPIId = inParam.getMainParamAsString();

		final NewsData theNewsData = NewsData.findByAPIId(newsAPIId, inParam.getCaller().getAPIKey());
		if (theNewsData == null) {
			throw new NoSuchNewsException();
		}

		if (!theNewsData.delete()) {
			throw new InternalErrorException(APIErrorMessage.DELETE_FAILED.getMessage());
		}
		return null;
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.DELETE;
	}

	public boolean isCacheable() {
		return false;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_ALL;
	}
}

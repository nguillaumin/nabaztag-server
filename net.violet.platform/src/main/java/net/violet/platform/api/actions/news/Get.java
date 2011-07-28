package net.violet.platform.api.actions.news;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchNewsException;
import net.violet.platform.api.maps.news.NewsInformationMap;
import net.violet.platform.dataobjects.NewsData;

public class Get extends AbstractAction {

	//private static final Logger LOGGER = Logger.getLogger(GetNews.class);

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchNewsException {

		final String newsAPIId = inParam.getMainParamAsString();

		final APICaller caller = inParam.getCaller();

		final NewsData theNewsData = NewsData.findByAPIId(newsAPIId, caller.getAPIKey());
		if (theNewsData == null) {
			throw new NoSuchNewsException();
		}

		return new NewsInformationMap(inParam.getCaller(), theNewsData);
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.GET;
	}

	public boolean isCacheable() {
		return true;
	}

}

package net.violet.mynabaztag.action;

import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.factories.ServiceFactory;

public class MySrvPodcastFullAction extends MySrvRssPodcastFullAbstractAction {

	@Override
	protected String getApplicationName() {
		return Application.NativeApplication.PODCAST_FULL.toString();
	}

	@Override
	protected ServiceFactory.SERVICE getService() {
		return ServiceFactory.SERVICE.PODCAST;
	}

}

package net.violet.mynabaztag.action;

import net.violet.mynabaztag.form.MySrvAbstractForm;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Service;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.ServiceFactory;

public class MySrvRssFreeAction extends MySrvRssPodcastFreeAbstractAction {

	@Override
	protected String getApplicationName(MySrvAbstractForm myForm) {
		final Application theApplication = Factories.APPLICATION.find(myForm.getApplicationId());
		if (theApplication != null) {
			return theApplication.getName();
		}
		return null;
	}

	@Override
	protected Service getService() {
		return ServiceFactory.SERVICE.RSS.getService();
	}

}

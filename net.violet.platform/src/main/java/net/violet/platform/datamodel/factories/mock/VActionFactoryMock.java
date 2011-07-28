package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.Record.RecordWalker;
import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.VAction;
import net.violet.platform.datamodel.factories.ServiceFactory;
import net.violet.platform.datamodel.factories.VActionFactory;
import net.violet.platform.datamodel.mock.VActionMock;

public class VActionFactoryMock extends RecordFactoryMock<VAction, VActionMock> implements VActionFactory {

	VActionFactoryMock() {
		super(VActionMock.class);
	}

	public int walk(RecordWalker<VAction> inWalker) {
		return 0;
	}

	public VAction findByUrl(String url) {
		for (final VAction theAction : findAll()) {
			if (theAction.getUrl().equals(url)) {
				return theAction;
			}
		}
		return null;
	}

	public VAction createNewVAction(int inLangId, String inUrl, ServiceFactory.SERVICE inService, String inAccess_right, Application inApplication) {
		return new VActionMock(0, inLangId, inUrl, inService, inAccess_right, inApplication);
	}

	public VAction findFirstByUrlAndService(String url, ServiceFactory.SERVICE inService) {
		for (final VAction action : findAll()) {
			if (action.getUrl().equals(url) && action.getService().equals(inService.getService())) {
				return action;
			}
		}
		return null;
	}

	public VAction findByApplication(Application inApplication) {
		for (final VAction theAction : findAll()) {
			if (theAction.getApplicationId() == inApplication.getId().longValue()) {
				return theAction;
			}
		}
		return null;
	}

	public VAction create(String inURL, int inLangId, String inAcces, Application inApplication, String inTypeName) {
		ServiceFactory.SERVICE theService = ServiceFactory.SERVICE.PODCAST;
		if (ServiceFactory.SERVICE.RSS.getLabel().equals(inTypeName)) {
			theService = ServiceFactory.SERVICE.RSS;
		}
		return new VActionMock(0, inLangId, inURL, theService, inAcces, inApplication);
	}
}

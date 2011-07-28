package net.violet.platform.datamodel.factories;

import net.violet.db.records.Record.RecordWalker;
import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.VAction;

public interface VActionFactory extends RecordFactory<VAction> {

	int walk(RecordWalker<VAction> inWalker);

	VAction findByUrl(String inUrl);

	VAction createNewVAction(int inLangId, String inUrl, ServiceFactory.SERVICE inServiceId, String inAccess_right, Application inApplication);

	VAction findFirstByUrlAndService(String url, ServiceFactory.SERVICE inService);

	VAction findByApplication(Application inApplication);

	VAction create(String inURL, int inLangId, String inAcces, Application inApplication, String inTypeName);
}

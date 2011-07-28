package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.Record.RecordWalker;
import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.VAction;
import net.violet.platform.datamodel.VActionImpl;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.ServiceFactory;
import net.violet.platform.datamodel.factories.VActionFactory;

public class VActionFactoryImpl extends RecordFactoryImpl<VAction, VActionImpl> implements VActionFactory {

	private static final String DEFAULT_CONTENT_TITLE = "new content";

	VActionFactoryImpl() {
		super(VActionImpl.SPECIFICATION);
	}

	public int walk(RecordWalker<VAction> inWalker) {
		return walk(null, null, "id desc", 0, inWalker);
	}

	public int walkByServiceAndAccessRight(int service, String accessRight, RecordWalker<VAction> inWalker) {
		return walk(" service_id = ? AND access_right = ? ", Arrays.asList(new Object[] { service, accessRight }), null, 0, inWalker);
	}

	@Override
	public long count(String[] inJoinTables, String inCondition, List<Object> inValues, String inGroupBy) {
		return count(inJoinTables, inCondition, inValues, inGroupBy);
	}

	public VAction findByUrl(String url) {
		final List<Object> theValues = Collections.singletonList((Object) url);
		return find("url = ?", theValues);
	}

	public VAction createNewVAction(int inLangId, String inUrl, ServiceFactory.SERVICE inService, String inAccess_right, Application inApplication) {

		try {
			final VAction action = new VActionImpl(inLangId, inUrl, inService, inAccess_right, inApplication);

			if (Factories.CONTENT.insert(action, Factories.FILES.find(Files.NEW_CONTENT_FILE_ID), VActionFactoryImpl.DEFAULT_CONTENT_TITLE) == 0) {
				action.delete();
				return null;
			}

			return action;
		} catch (final SQLException e) {
			return null;
		}
	}

	public VAction findFirstByUrlAndService(String url, ServiceFactory.SERVICE inService) {
		final List<Object> theValues = Arrays.asList(new Object[] { url, inService.getService().getId() });
		return find("url = ? AND service_id = ?", theValues);
	}

	public VAction findByApplication(Application inApplication) {
		return findByKey(1, inApplication.getId());
	}

	public VAction create(String inURL, int inLangId, String inAccess, Application inApplication, String inTypeName) {
		ServiceFactory.SERVICE theService = ServiceFactory.SERVICE.PODCAST;
		if (ServiceFactory.SERVICE.RSS.getLabel().equals(inTypeName)) {
			theService = ServiceFactory.SERVICE.RSS;
		}
		return createNewVAction(inLangId, inURL, theService, inAccess, inApplication);
	}

}

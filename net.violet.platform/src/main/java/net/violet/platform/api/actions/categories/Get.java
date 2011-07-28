package net.violet.platform.api.actions.categories;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ApplicationCategoryData;
import net.violet.platform.util.Constantes;

public class Get extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) {
		final List<String> resultList = new ArrayList<String>();
		for (final ApplicationCategoryData category : ApplicationCategoryData.findAll()) {
			resultList.add(category.getName());
		}
		return resultList;
	}

	public long getExpirationTime() {
		return Constantes.ONE_DAY_IN_S;
	}

	public ActionType getType() {
		return ActionType.GET;
	}

	public boolean isCacheable() {
		return true;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

}

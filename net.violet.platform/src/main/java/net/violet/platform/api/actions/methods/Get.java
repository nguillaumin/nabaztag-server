package net.violet.platform.api.actions.methods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.violet.platform.api.actions.APIController;
import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.datamodel.Application;

public class Get extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException {
		final String theMethodName = inParam.getMainParamAsString();
		final Action theAction = APIController.getAction(theMethodName);
		return new MethodInformationMap(theAction);
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

	protected static class MethodInformationMap extends AbstractAPIMap {

		public static final String NAME = "name";
		public static final String METHOD_CLASS = "method_class";
		public static final String APP_CLASS = "app_class";
		public static final String ERRORS = "errors";
		public static final String PARAMS = "params";
		public static final String TTL = "ttl";

		public MethodInformationMap(Action inAction) {
			super(6);
			put(MethodInformationMap.NAME, inAction.getName());
			put(MethodInformationMap.METHOD_CLASS, inAction.getType().name());
			final List<String> theApplicationClasses = new LinkedList<String>();
			for (final Application.ApplicationClass theAppClass : inAction.getAuthorizedApplicationClasses()) {
				theApplicationClasses.add(theAppClass.toString());
			}
			put(MethodInformationMap.APP_CLASS, theApplicationClasses);

			final List<String> theErrorsClasses = new LinkedList<String>();
			for (final Class<? extends APIException> apiException : inAction.getErrors()) {
				theErrorsClasses.add(apiException.getName().substring(apiException.getName().lastIndexOf(".") + 1, apiException.getName().lastIndexOf("Exception")));
			}
			Collections.sort(theErrorsClasses);
			put(MethodInformationMap.ERRORS, theErrorsClasses);

			final List<String> theParams = new ArrayList<String>(inAction.getParams());
			Collections.sort(theParams);
			put(MethodInformationMap.PARAMS, theParams);

			put(MethodInformationMap.TTL, inAction.getExpirationTime());
			// seal();
		}

	}

}

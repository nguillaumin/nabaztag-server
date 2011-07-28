package net.violet.platform.api.actions.admin.object;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.TagTmpSiteData;

public class SearchWaitingObject extends AbstractAction {

	public static String TYPE = "type";
	public static String LASTTIME = "lasttime";
	public static String IP = "ip";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchObjectException {

		final String serial = inParam.getMainParamAsString();
		final TagTmpSiteData tagTmpData = TagTmpSiteData.findBySerial(serial);

		if (tagTmpData == null) {
			throw new NoSuchObjectException();
		}

		final Map<String, String> theResult = new HashMap<String, String>();
		theResult.put(SearchWaitingObject.TYPE, tagTmpData.getObjectType().getTypeName());
		theResult.put(SearchWaitingObject.LASTTIME, tagTmpData.getLastDay().toString());
		theResult.put(SearchWaitingObject.IP, tagTmpData.getIp());
		return theResult;
	}

	public long getExpirationTime() {
		return 0;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Collections.emptyList();
	}

	public ActionType getType() {
		return ActionType.GET;
	}

	public boolean isCacheable() {
		return false;
	}
}

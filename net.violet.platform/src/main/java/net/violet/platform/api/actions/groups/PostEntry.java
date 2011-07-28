package net.violet.platform.api.actions.groups;

import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchGroupException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.GroupData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.feeds.FeedsManager;

public class PostEntry extends AbstractAction {

	@APIMethodParam
	private final static String AUTHOR = "author";
	@APIMethodParam
	private final static String TITLE = "title";
	@APIMethodParam
	private final static String CONTENT = "content";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws ForbiddenException, InvalidParameterException, InternalErrorException, NoSuchGroupException {

		final VObjectData theAuthor = VObjectData.findByAPIId(inParam.getString(PostEntry.AUTHOR, true), inParam.getCallerAPIKey());

		final GroupData theGroup = GroupData.findByAPIId(inParam.getMainParamAsString(), inParam.getCallerAPIKey());
		if ((theGroup == null) || !theGroup.isValid()) {
			throw new NoSuchGroupException();
		}

		if (!theGroup.getMembers().contains(theAuthor)) {
			throw new ForbiddenException();
		}

		try {
			FeedsManager.addEntry(theGroup, theAuthor, inParam.getString(PostEntry.TITLE, true), inParam.getString(PostEntry.CONTENT, true));
		} catch (final Exception e) {
			throw new InternalErrorException();
		}

		return null;
	}

	public long getExpirationTime() {
		return 0;
	}

	public boolean isCacheable() {
		return false;
	}

	public ActionType getType() {
		return ActionType.CREATE;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}

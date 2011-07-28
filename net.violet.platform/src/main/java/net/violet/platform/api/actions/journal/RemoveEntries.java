package net.violet.platform.api.actions.journal;

import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchSubscriptionException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.journal.Journal;

public class RemoveEntries extends AbstractAction {

	public static final String INDEX = "index";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchSubscriptionException {

		final SubscriptionData theSubscription = SubscriptionData.findByAPIId(inParam.getMainParamAsString(), inParam.getCallerAPIKey());
		if ((theSubscription == null) || !theSubscription.isValid()) {
			throw new NoSuchSubscriptionException();
		}

		final Journal theLogger = new Journal(theSubscription);
		if (inParam.hasParam(RemoveEntries.INDEX)) {
			try {
				theLogger.removeEntry(inParam.getInt(RemoveEntries.INDEX, true));
			} catch (final IndexOutOfBoundsException e) {
				throw new InvalidParameterException(RemoveEntries.INDEX);
			}
		} else {
			theLogger.clear();
		}

		theLogger.flush();

		return null;
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.DELETE;
	}

	public boolean isCacheable() {
		return false;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}

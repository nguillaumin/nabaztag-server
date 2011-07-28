package net.violet.platform.api.actions.journal;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchSubscriptionException;
import net.violet.platform.api.maps.LoggerEntryMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.journal.Journal;
import net.violet.platform.journal.JournalEntry;

public class Get extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchSubscriptionException {

		final SubscriptionData theSubscription = SubscriptionData.findByAPIId(inParam.getMainParamAsString(), inParam.getCallerAPIKey());
		if ((theSubscription == null) || !theSubscription.isValid()) {
			throw new NoSuchSubscriptionException();
		}

		final Journal theLogger = new Journal(theSubscription);
		final List<LoggerEntryMap> theEntries = new ArrayList<LoggerEntryMap>();
		for (final JournalEntry anEntry : theLogger.getEntries()) {
			theEntries.add(new LoggerEntryMap(anEntry, true));
		}

		theLogger.flush();

		return theEntries;
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.GET;
	}

	public boolean isCacheable() {
		return false;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}

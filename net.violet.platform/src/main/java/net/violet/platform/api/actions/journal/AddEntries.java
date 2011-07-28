package net.violet.platform.api.actions.journal;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.APICaller.CallerClass;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchSubscriptionException;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.journal.Journal;
import net.violet.platform.journal.JournalEntry;

public class AddEntries extends AbstractAction {

	@APIMethodParam
	public static final String ENTRIES = "entries";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchSubscriptionException, ForbiddenException {

		final SubscriptionData theSubscription = SubscriptionData.findByAPIId(inParam.getMainParamAsString(), inParam.getCallerAPIKey());
		if ((theSubscription == null) || !theSubscription.isValid()) {
			throw new NoSuchSubscriptionException();
		}

		// checks if the api caller is the application involved in the subscription, otherwise it can NOT insert any log!
		if (!theSubscription.getApplication().equals(inParam.getCaller().getApplication())) {
			throw new ForbiddenException();
		}

		final List<JournalEntry> theEntries = new ArrayList<JournalEntry>();
		for (String anEntry : inParam.<String> getList(AddEntries.ENTRIES, true)) {
			if (anEntry.length() > 250) {
				anEntry = anEntry.substring(0, 250);
			}
			theEntries.add(new JournalEntry(anEntry));
		}

		final Journal theLogger = new Journal(theSubscription);
		theLogger.addEntries(theEntries);
		theLogger.flush();

		return null;
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.CREATE;
	}

	public boolean isCacheable() {
		return false;
	}

	@Override
	public List<CallerClass> getAuthorizedCallerClasses() {
		return APICaller.APPLICATIONS;
	}
}

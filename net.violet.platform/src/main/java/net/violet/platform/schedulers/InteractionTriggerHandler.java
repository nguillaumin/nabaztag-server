package net.violet.platform.schedulers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.violet.common.StringShop;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.api.maps.SchedulingInformationMap;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.dataobjects.VObjectData;

public class InteractionTriggerHandler implements SchedulingHandler {

	public static final String EVENT = "event";
	public static final String TARGET = "target";
	public static final String VALIDITY = "validity";
	public static final String VALIDITY_FROM = "from";
	public static final String VALIDITY_TO = "to";

	public void deleteElements(SubscriptionSchedulingData scheduling) {
		//nothing to do
	}

	public void executeWhenDone(SubscriptionSchedulingData scheduling) {
		// nothing to do right here
	}

	public Map<String, String> generateSettings(VObjectData object, Map<String, Object> settings, String callerKey) {
		final Map<String, String> theResult = new HashMap<String, String>();

		theResult.put(InteractionTriggerHandler.EVENT, settings.get(InteractionTriggerHandler.EVENT).toString());

		if (settings.containsKey(InteractionTriggerHandler.TARGET)) {
			final String targetId = settings.get(InteractionTriggerHandler.TARGET).toString();
			final VObjectData theObject = (callerKey != null) ? VObjectData.findByAPIId(targetId, callerKey) : VObjectData.find(Long.parseLong(targetId));
			theResult.put(InteractionTriggerHandler.TARGET, String.valueOf(theObject.getId()));
		}

		if (settings.containsKey(InteractionTriggerHandler.VALIDITY)) {

			final Map<String, Map<String, Map<String, Object>>> theValidities = (Map<String, Map<String, Map<String, Object>>>) settings.get(InteractionTriggerHandler.VALIDITY);
			for (final Entry<String, Map<String, Map<String, Object>>> validityEntry : theValidities.entrySet()) {
				final SchedulingAtom from = new SchedulingAtom(validityEntry.getValue().get(InteractionTriggerHandler.VALIDITY_FROM));
				final SchedulingAtom to = new SchedulingAtom(validityEntry.getValue().get(InteractionTriggerHandler.VALIDITY_TO));
				theResult.put(validityEntry.getKey(), from.getHour() + ":" + from.getMinute() + "-" + to.getHour() + ":" + to.getMinute());
			}
		}

		return theResult;
	}

	public void checkSettings(VObjectData object, Map<String, Object> settings, String callerKey) throws InvalidSettingException, MissingSettingException {
		if (!settings.containsKey(InteractionTriggerHandler.EVENT)) {
			throw new MissingSettingException(InteractionTriggerHandler.EVENT);
		}

		//the target key is optional, but if present, it has to refer to an object owned by the given object's owner
		// i.e. a user can only choose one of his/her own objects as target.
		if (settings.containsKey(InteractionTriggerHandler.TARGET)) {
			final String targetApiId = settings.get(InteractionTriggerHandler.TARGET).toString();
			final VObjectData theTarget = (callerKey != null) ? VObjectData.findByAPIId(targetApiId, callerKey) : VObjectData.find(Long.parseLong(targetApiId));
			if ((theTarget == null) || !theTarget.isValid() || !theTarget.getOwner().equals(object.getOwner())) {
				throw new InvalidSettingException(InteractionTriggerHandler.TARGET, targetApiId);
			}
		}

		if (settings.containsKey(InteractionTriggerHandler.VALIDITY)) {
			final Map<String, Map<String, Map<String, Object>>> theValidities = (Map<String, Map<String, Map<String, Object>>>) settings.get(InteractionTriggerHandler.VALIDITY);
			for (final Entry<String, Map<String, Map<String, Object>>> anEntry : theValidities.entrySet()) {
				//checks if all the keys are valid days labels
				if (!DailyHandler.Weekday.isValidLabel(anEntry.getKey())) {
					throw new InvalidSettingException(InteractionTriggerHandler.VALIDITY, anEntry.getKey());
				}
				// checks if the two required times are given
				if (!anEntry.getValue().containsKey(InteractionTriggerHandler.VALIDITY_FROM) || !anEntry.getValue().containsKey(InteractionTriggerHandler.VALIDITY_TO)) {
					throw new InvalidSettingException(InteractionTriggerHandler.VALIDITY, StringShop.EMPTY_STRING);
				}

				// checks if 'to' is after 'from'
				final Map<String, Object> from = anEntry.getValue().get(InteractionTriggerHandler.VALIDITY_FROM);
				final Map<String, Object> to = anEntry.getValue().get(InteractionTriggerHandler.VALIDITY_TO);

				if (!SchedulingAtom.isValid(from, false, false) || !SchedulingAtom.isValid(to, false, false)) {
					throw new InvalidSettingException(InteractionTriggerHandler.VALIDITY_FROM + "/" + InteractionTriggerHandler.VALIDITY_TO, "not valid atom");
				}

				if (new SchedulingAtom(from).isAfter(new SchedulingAtom(to))) {
					throw new InvalidSettingException(InteractionTriggerHandler.VALIDITY, StringShop.EMPTY_STRING);
				}

			}
		}

	}

	private static final Pattern TIME_PERIOD = Pattern.compile("^(\\d{1,2}):(\\d{1,2})-(\\d{1,2}):(\\d{1,2})$");

	public SchedulingInformationMap getSchedulingInformation(SubscriptionSchedulingData scheduling, APICaller caller) {

		final SchedulingInformationMap infoMap = new SchedulingInformationMap(scheduling);

		final Map<String, SubscriptionSchedulingSettingsData> settings = SubscriptionSchedulingSettingsData.findAllBySubscriptionSchedulingAsMap(scheduling);

		infoMap.put(InteractionTriggerHandler.EVENT, settings.get(InteractionTriggerHandler.EVENT).getValue());

		if (settings.containsKey(InteractionTriggerHandler.TARGET)) {
			final String targetId = settings.get(InteractionTriggerHandler.TARGET).getValue();
			final VObjectData theTarget = VObjectData.find(Long.parseLong(targetId));
			if (theTarget.isValid()) {
				infoMap.put(InteractionTriggerHandler.TARGET, theTarget.getApiId(caller));
			}
		}

		final Map<String, Map<String, Map<String, Object>>> validities = new HashMap<String, Map<String, Map<String, Object>>>();
		for (final DailyHandler.Weekday aDay : DailyHandler.Weekday.getAllDays()) {
			if (settings.containsKey(aDay.getValue())) {
				final String validity = settings.get(aDay.getValue()).getValue();

				final Matcher theMatcher = InteractionTriggerHandler.TIME_PERIOD.matcher(validity);
				if (theMatcher.matches()) {

					final SchedulingAtom from = new SchedulingAtom(Integer.parseInt(theMatcher.group(1)), Integer.parseInt(theMatcher.group(2)), null, null);
					final SchedulingAtom to = new SchedulingAtom(Integer.parseInt(theMatcher.group(3)), Integer.parseInt(theMatcher.group(4)), null, null);

					final Map<String, Map<String, Object>> theTimes = new HashMap<String, Map<String, Object>>();
					theTimes.put(InteractionTriggerHandler.VALIDITY_FROM, from.getSchedulingAtomMap(caller));
					theTimes.put(InteractionTriggerHandler.VALIDITY_TO, to.getSchedulingAtomMap(caller));
					validities.put(aDay.getValue(), theTimes);
				}
			}
		}

		if (!validities.isEmpty()) {
			infoMap.put(InteractionTriggerHandler.VALIDITY, validities);
		}

		return infoMap;
	}

}

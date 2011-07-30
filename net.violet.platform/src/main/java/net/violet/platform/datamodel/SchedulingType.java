package net.violet.platform.datamodel;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.Record;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.StringShop;

/**
 * Types for scheduling applications installed on the objects
 * 
 *
 */
public interface SchedulingType extends Record<SchedulingType> {

	String TYPE_KEY = StringShop.TYPE;

	/**
	 * Different states returned when trying to remove a file
	 */
	// Beware : the ID_TYPE array requires ids to be consecutives.
	enum SCHEDULING_TYPE {
		Daily(1), //, DailyGenerator.getInstance()),
		DailyWithMedia(2), //, DailyWithMediaGenerator.getInstance()),
		DailyWithDuration(3), //, DailyWithDurationGenerator.getInstance()),
		Weekly(4), //, WeeklyGenerator.getInstance()),
		Frequency(13), //, FrequencyGenerator.getInstance()),
		RandomWithFrequency(5), //, FrequencyGenerator.getInstance()),
		VoiceTrigger(6), //, KeyWordGenerator.getInstance()),
		InteractionTrigger(7), //, InteractionTriggerGenerator.getInstance()),
		NewContent(8), //, DefaultSchedulingHandler.getInstance()),
		NewContentWithFrequency(9), //, FrequencyGenerator.getInstance()),
		NewContentWithKeywordAndMedia(10), //, NewContentWithKeywordAndMediaGenerator.getInstance()),
		Ambiant(11), //, AmbiantGenerator.getInstance()),
		AmbiantWithKeyword(12);//, AmbiantWithKeywordGenerator.getInstance());

		private static final Map<String, SCHEDULING_TYPE> NAME_TYPE = new HashMap<String, SCHEDULING_TYPE>();
		private static final SCHEDULING_TYPE[] ID_TYPE;
		static {
			ID_TYPE = new SCHEDULING_TYPE[SCHEDULING_TYPE.values().length];
			for (final SCHEDULING_TYPE aType : SCHEDULING_TYPE.values()) {
				SCHEDULING_TYPE.NAME_TYPE.put(aType.getLabel(), aType);
				SCHEDULING_TYPE.ID_TYPE[(int) (aType.getRecord().getId() - 1)] = aType;
			}
		}

		public static SCHEDULING_TYPE find(int inId) {
			return ((inId > 0) && ((inId - 1) < SCHEDULING_TYPE.ID_TYPE.length)) ? SCHEDULING_TYPE.ID_TYPE[inId - 1] : null;
		}

		public static SCHEDULING_TYPE findByLabel(String label) {
			return SCHEDULING_TYPE.NAME_TYPE.get(label);
		}

		private final SchedulingType mType;

		private SCHEDULING_TYPE(long inId) {
			this.mType = Factories.SCHEDULING_TYPE.find(inId);
		}

		public String getLabel() {
			return this.mType.getLabel();
		}

		public SchedulingType getRecord() {
			return this.mType;
		}

		@Override
		public String toString() {
			return this.mType.getLabel();
		}

//		public static void createUpdate(SubscriptionData inSubscription, VObjectData inVObject, List<Map<String, Object>> inSchedulingList, String inApiKey) throws InvalidParameterException {
//
//			SCHEDULING_TYPE.LOGGER.debug(inSchedulingList);
//
//			if ((inSchedulingList == null) || inSchedulingList.isEmpty()) {
//				throw new InvalidParameterException(APIErrorMessage.INVALID_PARAMETER, "schedulings list is null or empty");
//			}
//
//			final Map<SchedulingType.SCHEDULING_TYPE, List<SubscriptionSchedulingData>> theMap = SubscriptionSchedulingData.findAllBySubscriptionAsMap(inSubscription);
//			final List<Map<String, Object>> theList = new LinkedList<Map<String, Object>>(inSchedulingList);
//
//			for (final Iterator<Map<String, Object>> iterator = theList.iterator(); iterator.hasNext();) {
//				final Map<String, Object> aScheduling = iterator.next();
//				final SCHEDULING_TYPE theType = SCHEDULING_TYPE.valueOf(String.valueOf(aScheduling.get(SchedulingType.TYPE_KEY)));
//
//				final List<SubscriptionSchedulingData> schedulings = theMap.get(theType);
//
//				if (schedulings != null) {
//					final SubscriptionSchedulingData scheduling = schedulings.remove(0);
//
//					if (schedulings.isEmpty()) {
//						theMap.remove(theType);
//					}
//
//					final Map<String, Object> theScMap = new HashMap<String, Object>(aScheduling);
//					theType.getGenerator().createOrUpdateElementAndSettings(inSubscription, inVObject, Collections.singletonList(theScMap), scheduling, inApiKey);
//					iterator.remove();
//				}
//			}
//
//			final List<SubscriptionSchedulingData> theRemainer = new LinkedList<SubscriptionSchedulingData>();
//			for (final List<SubscriptionSchedulingData> aList : theMap.values()) {
//				theRemainer.addAll(aList);
//			}
//
//			for (final Iterator<Map<String, Object>> iterator = theList.iterator(); iterator.hasNext();) {
//				final Map<String, Object> aScheduling = iterator.next();
//				final SCHEDULING_TYPE theType = SCHEDULING_TYPE.valueOf(String.valueOf(aScheduling.get(SchedulingType.TYPE_KEY)));
//				final SubscriptionSchedulingData scheduling;
//
//				if (theRemainer.isEmpty()) {
//					scheduling = null;
//				} else {
//					scheduling = theRemainer.remove(0);
//					scheduling.getType().getGenerator().doDelete(scheduling);
//					scheduling.setType(theType);
//				}
//
//				final Map<String, Object> theScMap = new HashMap<String, Object>(aScheduling);
//				theType.getGenerator().createOrUpdateElementAndSettings(inSubscription, inVObject, Collections.singletonList(theScMap), scheduling, inApiKey);
//				iterator.remove();
//			}
//
//			for (final Iterator<SubscriptionSchedulingData> iterator = theRemainer.iterator(); iterator.hasNext();) {
//				final SubscriptionSchedulingData aScheduling = iterator.next();
//				aScheduling.getType().getGenerator().delete(aScheduling);
//				iterator.remove();
//			}
//		}

	};

	String getLabel();

	Timestamp getCoveredFor();

	void setCoveredFor(Timestamp inTimestamp);

//	class DefaultSchedulingHandler extends HandlerSkeleton<SubscriptionScheduling, SubscriptionSchedulingSettingsData, SubscriptionSchedulingData> {
//
//		private static final DefaultSchedulingHandler DEFAULT_GENERATOR = new DefaultSchedulingHandler();
//
//		public static DefaultSchedulingHandler getInstance() {
//			return DefaultSchedulingHandler.DEFAULT_GENERATOR;
//		}
//
//		@Override
//		protected Map<String, String> process(SubscriptionSchedulingData inElement, VObjectData inObject, List<Map<String, Object>> inSettings, String callerApiKey) throws InvalidParameterException {
//			return Collections.emptyMap();
//		}
//
//		@Override
//		protected SubscriptionSchedulingData createElement(RecordData inParent, VObjectData inObject, List<Map<String, Object>> inSettings, String callerApiKey) {
//			final Map<String, Object> theSetting = inSettings.get(0);
//			final SCHEDULING_TYPE theType = SCHEDULING_TYPE.valueOf(String.valueOf(theSetting.get(SchedulingType.TYPE_KEY)));
//			return SubscriptionSchedulingData.create((SubscriptionData) inParent, theType);
//		}
//
//		@Override
//		protected void doValidate(VObjectData inVObject, RecordData inParent, List<Map<String, Object>> inSettings, String callerApiKey, SubscriptionSchedulingData inElement) throws InvalidParameterException {
//			if (!(inParent instanceof SubscriptionData) || (((SubscriptionData) inParent).getReference() == null)) {
//				throw new InvalidParameterException(APIErrorMessage.INVALID_PARAMETER, "Not a valid subscription");
//			}
//		}
//
//	}
//
//	class DailyGenerator extends DefaultSchedulingHandler {
//
//		private static final DefaultSchedulingHandler DAILY_GENERATOR = new DailyGenerator();
//
//		public static DefaultSchedulingHandler getInstance() {
//			return DailyGenerator.DAILY_GENERATOR;
//		}
//
//		@Override
//		protected void doValidate(VObjectData inVObject, RecordData inParent, List<Map<String, Object>> inSettings, String callerApiKey, SubscriptionSchedulingData inElement) throws InvalidParameterException {
//			super.doValidate(inVObject, inParent, inSettings, callerApiKey, inElement);
//
//			boolean allNull = true;
//			for (final Entry<String, Object> anEntry : inSettings.get(0).entrySet()) {
//				if ((anEntry.getValue() != null) && !anEntry.getKey().equals(SchedulingType.TYPE_KEY)) {
//					if (!DAYS.isValidLabel(anEntry.getKey())) {
//						throw new InvalidParameterException(APIErrorMessage.INVALID_PARAMETER, anEntry.getKey(), "Not a valid day");
//					}
//
//					allNull = false;
//					if (!isValidAtom(this, (Map<String, Object>) anEntry.getValue())) {
//						throw new InvalidParameterException(APIErrorMessage.INVALID_PARAMETER, anEntry.getKey() + " => " + anEntry.getValue().toString(), "Not a valid atom");
//					}
//
//				}
//			}
//
//			if (allNull) {
//				throw new InvalidParameterException(APIErrorMessage.MISSING_PARAMETER, "All scheduling atoms are equal to nil");
//			}
//		}
//
//		@Override
//		protected Map<String, String> process(SubscriptionSchedulingData inScheduling, VObjectData inObject, List<Map<String, Object>> inSettings, String callerApiKey) {
//			final Map<String, String> theResult = new HashMap<String, String>();
//			for (final Entry<String, Object> anEntry : inSettings.get(0).entrySet()) {
//				if ((anEntry.getValue() != null) && !anEntry.getKey().equals(SchedulingType.TYPE_KEY)) {
//					theResult.putAll(createRow(anEntry.getKey(), (Map<String, Object>) anEntry.getValue(), callerApiKey));
//				}
//			}
//			return theResult;
//		}
//
//		protected Map<String, String> createRow(String inDay, Map<String, Object> inAtom, String inApiKey) {
//			final CCalendar theCalendar = new CCalendar(true, TimeZone.getTimeZone("UTC"));
//			theCalendar.setHour((Integer) inAtom.get("time_h"));
//			theCalendar.setMinute((Integer) inAtom.get("time_m"));
//
//			return Collections.singletonMap(inDay, theCalendar.getTimeFormated(true));
//		}
//
//		protected boolean isValidAtom(SchedulingType.DefaultSchedulingHandler inType, Map<String, Object> inAtom) {
//			try {
//				if ((inType != null) && (inAtom != null) && (inAtom.size() >= 2)) {
//					// Enjoy !!!
//					final Object theHours, theMinutes;
//					int aTime;
//					return (((theHours = inAtom.get("time_h")) != null) && ((theMinutes = inAtom.get("time_m")) != null) && (((aTime = Integer.parseInt(theHours.toString())) >= 0) && (aTime < 24) && ((aTime = Integer.parseInt(theMinutes.toString())) >= 0) && (aTime < 60)));
//				}
//			} catch (final NumberFormatException e) {}
//			return false;
//		}
//
//	}
//
//	class DailyWithDurationGenerator extends DailyGenerator {
//
//		private static final DefaultSchedulingHandler DAILY_WITH_DURATION_GENERATOR = new DailyWithDurationGenerator();
//
//		public static DefaultSchedulingHandler getInstance() {
//			return DailyWithDurationGenerator.DAILY_WITH_DURATION_GENERATOR;
//		}
//
//		@Override
//		protected boolean isValidAtom(SchedulingType.DefaultSchedulingHandler inType, Map<String, Object> inAtom) {
//			try {
//				final Object theDuration;
//				return ((inAtom.size() == 3) && super.isValidAtom(inType, inAtom) && ((theDuration = inAtom.get("duration")) != null) && (Integer.parseInt(theDuration.toString()) > 0));
//			} catch (final NumberFormatException e) {}
//			return false;
//		}
//
//		@Override
//		protected Map<String, String> createRow(String inDay, Map<String, Object> inAtom, String inApiKey) {
//			final Map<String, String> theResult = new HashMap<String, String>(super.createRow(inDay, inAtom, inApiKey));
//			theResult.put(SubscriptionSchedulingSettings.DAILY.DURATION_SUFFIXE(inDay), String.valueOf(inAtom.get("duration")));
//
//			return theResult;
//		}
//	}
//
//	class DailyWithMediaGenerator extends DailyGenerator {
//
//		private static final DefaultSchedulingHandler DAILY_WITH_MEDIA_GENERATOR = new DailyWithMediaGenerator();
//
//		public static DefaultSchedulingHandler getInstance() {
//			return DailyWithMediaGenerator.DAILY_WITH_MEDIA_GENERATOR;
//		}
//
//		@Override
//		protected boolean isValidAtom(SchedulingType.DefaultSchedulingHandler inType, Map<String, Object> inAtom) {
//			try {
//				return ((inAtom.size() == 3) && super.isValidAtom(inType, inAtom) && (inAtom.get("media") != null));
//			} catch (final NumberFormatException e) {}
//			return false;
//		}
//
//		@Override
//		protected Map<String, String> createRow(String inDay, Map<String, Object> inAtom, String inApiKey) {
//			final Map<String, String> theResult = new HashMap<String, String>(super.createRow(inDay, inAtom, inApiKey));
//			final String mediaId = String.valueOf((inApiKey == null) ? inAtom.get("media") : MusicData.findByAPIId((String) inAtom.get("media"), inApiKey).getId());
//			theResult.put(SubscriptionSchedulingSettings.DAILY.MEDIA_SUFFIXE(inDay), mediaId);
//
//			return theResult;
//		}
//
//	}
//
//	class WeeklyGenerator extends DailyGenerator {
//
//		private static final DefaultSchedulingHandler WEEKLY_GENERATOR = new WeeklyGenerator();
//
//		public static DefaultSchedulingHandler getInstance() {
//			return WeeklyGenerator.WEEKLY_GENERATOR;
//		}
//
//		@Override
//		protected void doValidate(VObjectData inVObject, RecordData inParent, List<Map<String, Object>> inSettings, String callerApiKey, SubscriptionSchedulingData inElement) throws InvalidParameterException {
//			if (!(inParent instanceof SubscriptionData) || (((SubscriptionData) inParent).getReference() == null)) {
//				throw new InvalidParameterException(APIErrorMessage.INVALID_PARAMETER, "Not a valid subscription");
//			}
//
//			final Object theLabel;
//			final Map<String, Object> theScheduling = inSettings.get(0);
//			if (((theLabel = theScheduling.get("weekday")) == null) || !DAYS.isValidLabel(theLabel.toString())) {
//				throw new InvalidParameterException(APIErrorMessage.INVALID_PARAMETER, "weekday", String.valueOf(theLabel));
//			}
//
//			if (!isValidAtom(this, theScheduling)) {
//				throw new InvalidParameterException(APIErrorMessage.INVALID_PARAMETER, "Not a valid atom", theScheduling.toString());
//			}
//		}
//
//		@Override
//		protected Map<String, String> process(SubscriptionSchedulingData inScheduling, VObjectData inObject, List<Map<String, Object>> inSettings, String callerApiKey) {
//			final Map<String, String> theResult = new HashMap<String, String>();
//			final Map<String, Object> theSettings = inSettings.get(0);
//			theResult.putAll(createRow((String) theSettings.get("weekday"), theSettings, callerApiKey));
//			return theResult;
//		}
//
//	}
//
//	class FrequencyGenerator extends DefaultSchedulingHandler {
//
//		private static final DefaultSchedulingHandler FREQUENCY_GENERATOR = new FrequencyGenerator();
//
//		public static DefaultSchedulingHandler getInstance() {
//			return FrequencyGenerator.FREQUENCY_GENERATOR;
//		}
//
//		@Override
//		protected void doValidate(VObjectData inVObject, RecordData inParent, List<Map<String, Object>> inSettings, String callerApiKey, SubscriptionSchedulingData inElement) throws InvalidParameterException {
//			super.doValidate(inVObject, inParent, inSettings, callerApiKey, inElement);
//			final Map<String, Object> theSettings = inSettings.get(0);
//			final Object theFrequency;
//			if (!(((theFrequency = theSettings.get("frequency")) != null) && (FREQUENCY.findByLabel(theFrequency.toString()) != null))) {
//				throw new InvalidParameterException(APIErrorMessage.INVALID_PARAMETER, "frequency", String.valueOf(theFrequency));
//			}
//		}
//
//		@Override
//		protected Map<String, String> process(SubscriptionSchedulingData inScheduling, VObjectData inObject, List<Map<String, Object>> inSettings, String callerApiKey) {
//			final Map<String, String> theResult = new HashMap<String, String>();
//			final Map<String, Object> theSettings = inSettings.get(0);
//			theResult.put(SubscriptionSchedulingSettings.FREQUENCY.LABEL_KEY, (String) theSettings.get("frequency"));
//
//			if (theSettings.containsKey("last_time")) {
//				theResult.put(FREQUENCY.LAST_TIME_KEY, theSettings.get("last_time").toString());
//			} else {
//				final TimeZone theTimeZone = inObject.getReference().getTimeZone().getJavaTimeZone();
//				theResult.put(FREQUENCY.LAST_TIME_KEY, new CCalendar(false, theTimeZone).getTimestamp());
//			}
//
//			return theResult;
//		}
//	}
//
//	public class InteractionTriggerGenerator extends DefaultSchedulingHandler {
//
//		public static final String TARGET = "target";
//		public static final String VALIDITY = "validity";
//		public static final String EVENT = "event";
//		private static final DefaultSchedulingHandler INTERACTION_TRIGGER_GENERATOR = new InteractionTriggerGenerator();
//
//		public static DefaultSchedulingHandler getInstance() {
//			return InteractionTriggerGenerator.INTERACTION_TRIGGER_GENERATOR;
//		}
//
//		@Override
//		protected void doValidate(VObjectData inVObject, RecordData inParent, List<Map<String, Object>> inSettings, String callerApiKey, SubscriptionSchedulingData inElement) throws InvalidParameterException {
//			super.doValidate(inVObject, inParent, inSettings, callerApiKey, inElement);
//
//			for (final Map<String, Object> aSetting : inSettings) {
//				if (aSetting.containsKey(InteractionTriggerGenerator.TARGET)) {
//					final String targetId = aSetting.get(InteractionTriggerGenerator.TARGET).toString();
//					final VObjectData theTarget = VObjectData.findByAPIId(targetId, callerApiKey);
//					if ((theTarget == null) || !theTarget.isValid() || !theTarget.getOwner().equals(inVObject.getOwner())) {
//						throw new InvalidParameterException(InteractionTriggerGenerator.TARGET, targetId);
//					}
//					break;
//				}
//
//				if (aSetting.containsKey(InteractionTriggerGenerator.VALIDITY)) {
//					final Map<String, Map<String, Map<String, Integer>>> theValidities = (Map<String, Map<String, Map<String, Integer>>>) aSetting.get(InteractionTriggerGenerator.VALIDITY);
//					for (final Entry<String, Map<String, Map<String, Integer>>> anEntry : theValidities.entrySet()) {
//						//checks if all the keys are valid days labels
//						if (!DAYS.isValidLabel(anEntry.getKey())) {
//							throw new InvalidParameterException(InteractionTriggerGenerator.VALIDITY, anEntry.getKey());
//						}
//						// checks if the two required times are present
//						if (!anEntry.getValue().containsKey("from") || !anEntry.getValue().containsKey("to")) {
//							throw new InvalidParameterException(InteractionTriggerGenerator.VALIDITY);
//						}
//
//						// checks if 'to' is after 'from'
//						final int from = anEntry.getValue().get("from").get("time_h") * 60 + anEntry.getValue().get("from").get("time_m");
//						final int to = anEntry.getValue().get("to").get("time_h") * 60 + anEntry.getValue().get("to").get("time_m");
//						if ((from < 0) || (to < 0) || (from > 1439) || (to > 1439) || (from > to)) {
//							throw new InvalidParameterException(InteractionTriggerGenerator.VALIDITY);
//						}
//
//					}
//				}
//			}
//
//		}
//
//		@Override
//		protected Map<String, String> process(SubscriptionSchedulingData inScheduling, VObjectData inObject, List<Map<String, Object>> inSettings, String callerApiKey) {
//			final Map<String, String> theResult = new HashMap<String, String>();
//			final Map<String, Object> theSettings = inSettings.get(0);
//
//			for (final Entry<String, Object> anEntry : theSettings.entrySet()) {
//				if (!anEntry.getKey().equals(SchedulingType.TYPE_KEY)) {
//					// special treatment for the validity parameter
//					if (anEntry.getKey().equals(InteractionTriggerGenerator.VALIDITY)) {
//						final Map<String, Map<String, Map<String, Integer>>> theValidities = (Map<String, Map<String, Map<String, Integer>>>) anEntry.getValue();
//						for (final Entry<String, Map<String, Map<String, Integer>>> validityEntry : theValidities.entrySet()) {
//							final String from = validityEntry.getValue().get("from").get("time_h") + ":" + validityEntry.getValue().get("from").get("time_m");
//							final String to = validityEntry.getValue().get("to").get("time_h") + ":" + validityEntry.getValue().get("to").get("time_m");
//							theResult.put(validityEntry.getKey(), from + "-" + to);
//						}
//					} else {
//						theResult.put(anEntry.getKey(), anEntry.getValue().toString());
//					}
//				}
//			}
//
//			if (!theResult.containsKey("event")) {
//				theResult.put("event", ZtampDetectionEvent.NAME);
//			}
//
//			if (theResult.containsKey(InteractionTriggerGenerator.TARGET)) {
//				final String targetId = theResult.get(InteractionTriggerGenerator.TARGET);
//				theResult.put(InteractionTriggerGenerator.TARGET, String.valueOf(VObjectData.findByAPIId(targetId, callerApiKey).getId()));
//			}
//
//			return theResult;
//		}
//
//	}
//
//	class KeyWordGenerator extends DefaultSchedulingHandler {
//
//		private static final DefaultSchedulingHandler KEY_WORD_GENERATOR = new KeyWordGenerator();
//
//		public static DefaultSchedulingHandler getInstance() {
//			return KeyWordGenerator.KEY_WORD_GENERATOR;
//		}
//
//		@Override
//		protected void doValidate(VObjectData inVObject, RecordData inParent, List<Map<String, Object>> inSettings, String callerApiKey, SubscriptionSchedulingData inElement) throws InvalidParameterException {
//			super.doValidate(inVObject, inParent, inSettings, callerApiKey, inElement);
//			final Map<String, Object> theSettings = inSettings.get(0);
//			final Object theKeyWord;
//			if ((theKeyWord = theSettings.get(KeywordHandler.KEYWORD)) == null) {
//				throw new InvalidParameterException(KeywordHandler.KEYWORD, String.valueOf(theKeyWord));
//			}
//		}
//
//		@Override
//		protected Map<String, String> process(SubscriptionSchedulingData inScheduling, VObjectData inObject, List<Map<String, Object>> inSettings, String callerApiKey) {
//			final Map<String, String> theResult = new HashMap<String, String>();
//			final Map<String, Object> theSettings = inSettings.get(0);
//			theResult.put(KeywordHandler.KEYWORD, (String) theSettings.get(KeywordHandler.KEYWORD));
//			return theResult;
//		}
//	}
//
//	class NewContentWithKeywordAndMediaGenerator extends DefaultSchedulingHandler {
//
//		private static final DefaultSchedulingHandler NEW_CONTENT_WITH_KEYWORD_AND_MEDIA_GENERATOR = new NewContentWithKeywordAndMediaGenerator();
//
//		public static DefaultSchedulingHandler getInstance() {
//			return NewContentWithKeywordAndMediaGenerator.NEW_CONTENT_WITH_KEYWORD_AND_MEDIA_GENERATOR;
//		}
//
//		@Override
//		protected void doValidate(VObjectData inVObject, RecordData inParent, List<Map<String, Object>> inSettings, String callerApiKey, SubscriptionSchedulingData inElement) throws InvalidParameterException {
//			super.doValidate(inVObject, inParent, inSettings, callerApiKey, inElement);
//			final Map<String, Object> theSettings = inSettings.get(0);
//			final Object theMedia;
//			final Object theKeyWord;
//
//			if (!(((theKeyWord = theSettings.get(KeywordHandler.KEYWORD)) != null) & ((theMedia = theSettings.get("media")) != null))) {
//				throw new InvalidParameterException(APIErrorMessage.INVALID_PARAMETER, "keyword and/or media", String.valueOf(theKeyWord) + net.violet.common.StringShop.SLASH + String.valueOf(theMedia));
//			}
//		}
//
//		@Override
//		protected Map<String, String> process(SubscriptionSchedulingData inScheduling, VObjectData inObject, List<Map<String, Object>> inSettings, String callerApiKey) {
//			final Map<String, String> theResult = new HashMap<String, String>();
//			final Map<String, Object> theSettings = inSettings.get(0);
//			final String mediaId = String.valueOf((callerApiKey == null) ? theSettings.get("media") : MusicData.findByAPIId((String) theSettings.get("media"), callerApiKey).getId());
//			theResult.put(KeywordHandler.KEYWORD, (String) theSettings.get(KeywordHandler.KEYWORD));
//			theResult.put(NewContentWithKeywordAndMediaHandler.MEDIA, mediaId);
//			theResult.put(MailAlertHandler.NEW_CONTENT_FLAG, "0");
//			return theResult;
//		}
//	}
//
//	class AmbiantGenerator extends DefaultSchedulingHandler {
//
//		private static final DefaultSchedulingHandler AMBIANT_GENERATOR = new AmbiantGenerator();
//
//		public static DefaultSchedulingHandler getInstance() {
//			return AmbiantGenerator.AMBIANT_GENERATOR;
//		}
//
//		@Override
//		public void doDelete(SubscriptionSchedulingData inScheduling) {
//			final ThreadWatcher theWatcher = new ThreadWatcher();
//
//			final MessageProcessUnit theMessage = new MessageProcessUnit(inScheduling, null, theWatcher, ACTION.REMOVE) {
//
//				@Override
//				public void runWhenSuccessful() {
//					stopWatching();
//					processed();
//				}
//			};
//
//			theMessage.startWatching();
//			theMessage.processing();
//			AbstractMessageFactory.sendMessage(theMessage, MESSAGE_TYPE.SOURCE_MESSAGE);
//			theWatcher.await();
//		}
//
//		@Override
//		public void runWhenSuccessful(SubscriptionSchedulingData inScheduling) {
//			final ThreadWatcher theWatcher = new ThreadWatcher();
//
//			final MessageProcessUnit theMessage = new MessageProcessUnit(inScheduling, null, theWatcher, ACTION.ADD) {
//
//				@Override
//				public void runWhenSuccessful() {
//					stopWatching();
//					processed();
//				}
//			};
//
//			theMessage.startWatching();
//			theMessage.processing();
//			AbstractMessageFactory.sendMessage(theMessage, MESSAGE_TYPE.SOURCE_MESSAGE);
//			theWatcher.await();
//		}
//
//		@Override
//		protected Map<String, String> process(SubscriptionSchedulingData inScheduling, VObjectData inObject, List<Map<String, Object>> inSettings, String callerApiKey) {
//			final Map<String, String> theResult = new HashMap<String, String>();
//			final Map<String, Object> theSettings = inSettings.get(0);
//
//			if (theSettings.containsKey(SubscriptionSchedulingSettings.Ambiant.LAST_TIME)) {
//				theResult.put(SubscriptionSchedulingSettings.Ambiant.LAST_TIME, theSettings.get(SubscriptionSchedulingSettings.Ambiant.LAST_TIME).toString());
//			} else {
//				theResult.put(SubscriptionSchedulingSettings.Ambiant.LAST_TIME, "0");
//			}
//
//			return theResult;
//		}
//
//	}
//
//	class AmbiantWithKeywordGenerator extends AmbiantGenerator {
//
//		private final KeyWordGenerator mKeyWord;
//
//		protected AmbiantWithKeywordGenerator(KeyWordGenerator inKeyWord) {
//			this.mKeyWord = inKeyWord;
//		}
//
//		private static final DefaultSchedulingHandler AMBIANT_WITH_KEY_WORD_GENERATOR = new AmbiantWithKeywordGenerator((KeyWordGenerator) KeyWordGenerator.getInstance());
//
//		public static DefaultSchedulingHandler getInstance() {
//			return AmbiantWithKeywordGenerator.AMBIANT_WITH_KEY_WORD_GENERATOR;
//		}
//
//		@Override
//		protected void doValidate(VObjectData inObject, RecordData inParent, List<Map<String, Object>> inSettings, String callerApiKey, SubscriptionSchedulingData inElement) throws InvalidParameterException {
//			this.mKeyWord.doValidate(inObject, inParent, inSettings, callerApiKey, inElement);
//			super.doValidate(inObject, inParent, inSettings, callerApiKey, inElement);
//		}
//
//		@Override
//		protected Map<String, String> process(SubscriptionSchedulingData inScheduling, VObjectData inObject, List<Map<String, Object>> inSettings, String callerApiKey) {
//			final Map<String, String> theResult = new HashMap<String, String>();
//			theResult.putAll(super.process(inScheduling, inObject, inSettings, callerApiKey));
//			theResult.putAll(this.mKeyWord.process(inScheduling, inObject, inSettings, callerApiKey));
//
//			return theResult;
//		}
//
//	}

}

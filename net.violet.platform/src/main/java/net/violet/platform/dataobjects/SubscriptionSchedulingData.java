package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.AbstractSettingRecordData.AbstractSettingableRecordData;
import net.violet.platform.schedulers.DailyHandler;
import net.violet.platform.schedulers.DailyWithDurationHandler;
import net.violet.platform.schedulers.DailyWithMediaHandler;
import net.violet.platform.util.CCalendar;

import org.apache.log4j.Logger;

public class SubscriptionSchedulingData extends AbstractSettingableRecordData<SubscriptionScheduling, SubscriptionSchedulingSettingsData> {

	private static final Logger LOGGER = Logger.getLogger(SubscriptionSchedulingData.class);

	public static SubscriptionSchedulingData getData(SubscriptionScheduling inSubscriptionSettings) {
		try {
			return RecordData.getData(inSubscriptionSettings, SubscriptionSchedulingData.class, SubscriptionScheduling.class);
		} catch (final InstantiationException e) {
			SubscriptionSchedulingData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			SubscriptionSchedulingData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			SubscriptionSchedulingData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			SubscriptionSchedulingData.LOGGER.fatal(e, e);
		}

		return null;
	}

	protected SubscriptionSchedulingData(SubscriptionScheduling inRecord) {
		super(inRecord);
	}

	public SubscriptionData getSubscription() {
		return SubscriptionData.getData(getRecord().getSubscription());
	}

	public static SubscriptionSchedulingData updateForSubscription(SubscriptionData inSubscriptionData, SchedulingType.SCHEDULING_TYPE inSchedulingType) {

		final Subscription inSubscription = inSubscriptionData.getReference();
		final List<SubscriptionScheduling> theSubscriptionScheduling = Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscriptionAndType(inSubscription, inSchedulingType);

		if (theSubscriptionScheduling.isEmpty()) {
			return SubscriptionSchedulingData.getData(Factories.SUBSCRIPTION_SCHEDULING.create(inSubscription, inSchedulingType));
		}

		return SubscriptionSchedulingData.getData(theSubscriptionScheduling.get(0));
	}

	public static List<SubscriptionSchedulingData> findAllBySubscription(SubscriptionData inSubscription) {
		return SubscriptionSchedulingData.generateList(Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscription(inSubscription.getReference()));
	}

	public static Map<SchedulingType.SCHEDULING_TYPE, List<SubscriptionSchedulingData>> findAllBySubscriptionAsMap(SubscriptionData inSubscription) {
		return SubscriptionSchedulingData.generateMap(Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscription(inSubscription.getReference()));
	}

	public static List<SubscriptionSchedulingData> findAllBySubscriptionAndType(SubscriptionData inSubscription, SCHEDULING_TYPE inType) {
		final List<SubscriptionScheduling> schedulings = Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscriptionAndType(inSubscription.getReference(), inType);
		SubscriptionSchedulingData.LOGGER.debug("Thierry : schedulings by type (" + inType + ") = " + schedulings);
		return SubscriptionSchedulingData.generateList(schedulings);
	}

	private static List<SubscriptionSchedulingData> generateList(List<SubscriptionScheduling> inList) {
		final List<SubscriptionSchedulingData> list = new LinkedList<SubscriptionSchedulingData>();
		for (final SubscriptionScheduling scheduling : inList) {
			list.add(SubscriptionSchedulingData.getData(scheduling));
		}
		return list;
	}

	@Override
	protected ObjectClass getObjectClass() {
		//FIXME : refactor the Settingable abstract class to avoid implementing this method inherited from APIRecordData
		throw new UnsupportedOperationException();
	}

	private static Map<SchedulingType.SCHEDULING_TYPE, List<SubscriptionSchedulingData>> generateMap(List<SubscriptionScheduling> inList) {
		final Map<SchedulingType.SCHEDULING_TYPE, List<SubscriptionSchedulingData>> theMap = new HashMap<SCHEDULING_TYPE, List<SubscriptionSchedulingData>>();
		for (final SubscriptionScheduling scheduling : inList) {
			List<SubscriptionSchedulingData> theElement = theMap.get(scheduling.getType());

			if (theElement == null) {
				theElement = new LinkedList<SubscriptionSchedulingData>();
			}

			theElement.add(SubscriptionSchedulingData.getData(scheduling));

			theMap.put(scheduling.getType(), theElement);
		}
		return theMap;
	}

	public SchedulingType.SCHEDULING_TYPE getType() {
		final SubscriptionScheduling theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getType();
		}
		return null;
	}

	public SubscriptionScheduling getReference() {
		return getRecord();
	}

	public void setType(SchedulingType.SCHEDULING_TYPE ambiant) {
		final SubscriptionScheduling theRecord = getRecord();
		if (theRecord != null) {
			theRecord.setType(ambiant);
		}
	}

	public static SubscriptionSchedulingData create(SubscriptionData inSubscription, SchedulingType.SCHEDULING_TYPE inType) {
		return SubscriptionSchedulingData.getData(Factories.SUBSCRIPTION_SCHEDULING.create(inSubscription.getReference(), inType));

	}

	/**
	 * This method should only be used with Daily scheduling (you could have
	 * figured it considering that one of the parameters is a DAYS). It returns
	 * a SchedulingAtomData object which is an agregation of all the scheduling
	 * settings for the current scheduling, i.e. the time, the duration and the
	 * media. One or both of the duration and media parameter is null (they are
	 * optional and mutually exclusive) but the time parameter must be present.
	 * If the setting defining the time is not found the method returns null.
	 * 
	 * @param inDay
	 * @param inTimeZone
	 * @return
	 */
	public SchedulingAtomData getSchedulingAtom(DailyHandler.Weekday inDay, TimeZone inTimeZone) {
		final Map<String, SubscriptionSchedulingSettingsData> theMap = SubscriptionSchedulingSettingsData.findAllBySubscriptionSchedulingAsMap(SubscriptionSchedulingData.getData(getRecord()));
		final SubscriptionSchedulingSettingsData timeSetting = theMap.get(inDay.getValue());
		final SubscriptionSchedulingSettingsData mediaSetting = theMap.get(inDay.getValue() + DailyWithMediaHandler.MEDIA_SUFFIXE);
		final SubscriptionSchedulingSettingsData durationSetting = theMap.get(inDay.getValue() + DailyWithDurationHandler.DURATION_SUFFIXE);

		final CCalendar theCalendar;
		if (timeSetting != null) {
			try {
				theCalendar = CCalendar.getSQLCCalendar(timeSetting.getValue(), inTimeZone);
			} catch (final ParseException e) {
				SubscriptionSchedulingData.LOGGER.fatal(e, e);
				return null;
			}
		} else {
			return null;
		}

		final Integer media;
		if (mediaSetting != null) {
			media = Integer.parseInt(mediaSetting.getValue());
		} else {
			media = null;
		}

		final Integer duration;
		if (durationSetting != null) {
			duration = Integer.parseInt(durationSetting.getValue());
		} else {
			duration = null;
		}

		return new SchedulingAtomData(theCalendar, media, duration);
	}

	public static class SchedulingAtomData {

		private final CCalendar mTime;
		private final Integer mMedia;
		private final Integer mDuration;

		/**
		 * @param inTime
		 * @param inMedia
		 * @param inDuration
		 */
		private SchedulingAtomData(CCalendar inTime, Integer inMedia, Integer inDuration) {
			this.mTime = inTime;
			this.mMedia = inMedia;
			this.mDuration = inDuration;
		}

		public final String getTimeFormated(boolean inUse24) {
			return this.mTime.getTimeFormated(inUse24);
		}

		public final int getTimeH() {
			return this.mTime.getHour();
		}

		public final int getTimeM() {
			return this.mTime.getMinute();
		}

		public final Integer getMedia() {
			return this.mMedia;
		}

		public final Integer getDuration() {
			return this.mDuration;
		}

		public boolean isNull() {
			if ((this.mTime == null) && (this.mMedia == null) && (this.mDuration == null)) {
				return true;
			}
			return false;
		}

	}

	@Override
	public SubscriptionSchedulingSettingsData createSetting(String inKey, String inValue) {
		return (getRecord() == null) ? null : SubscriptionSchedulingSettingsData.getData(Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(getRecord(), inKey, inValue));
	}

	@Override
	public List<SubscriptionSchedulingSettingsData> getAllSettings() {
		return SubscriptionSchedulingSettingsData.findAllBySubscriptionScheduling(this);
	}

	public static List<SubscriptionSchedulingData> findAllByObjectAndType(VObjectData object, SCHEDULING_TYPE type) {
		return SubscriptionSchedulingData.generateList(Factories.SUBSCRIPTION_SCHEDULING.findAllByObjectAndType(object.getRecord(), type));
	}

}

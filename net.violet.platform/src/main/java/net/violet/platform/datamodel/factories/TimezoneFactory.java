package net.violet.platform.datamodel.factories;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;
import java.util.Map.Entry;

import net.violet.db.records.Record.RecordWalker;
import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Timezone;

public interface TimezoneFactory extends RecordFactory<Timezone> {

	Timezone findByJavaId(String timezone);

	List<String> findAllNames();

	List<Timezone> findAll();

	/**
	 * Walks through the time zones.
	 * 
	 * Not Cached
	 * 
	 * @param recordWalker
	 * @return
	 */
	int walk(RecordWalker<Timezone> recordWalker);

	/**
	 * Walks through the time zone based on a uniq set of (offset,uses_dst) (groups the time zones)
	 * 
	 * @param recordWalker
	 * 
	 * @return
	 */
	int walkDistincts(RecordWalker<Timezone> recordWalker);

	/**
	 * Finds a {@link List} of {@link Timezone}'s java ID for a given offset
	 * 
	 * @param inOffset in milli seconds
	 * 
	 * @return a {@link List} of {@link Timezone}'s java ID or <code>null</code> if no {@link Timezone}s are found for the given offset.
	 */
	List<String> findAllByOffset(int inOffset);

	List<Timezone> findAllFriends(Timezone inTimezone);

	abstract class TimeZoneFactoryCommon implements TimezoneFactory {

		/**
		 * As they are being returned as is, these Collections need to be read only to prevent data corruption
		 */

		private final Map<String, Timezone> timeZonesByJavaId;
		private final Map<Long, Timezone> timeZonesById;
		private final Map<Integer, List<String>> timeZoneJavaIdByOffset;
		private volatile Map<Timezone, List<Timezone>> timeZoneByGroup = Collections.emptyMap(); // Time zones having the same offset for a given date (refreshed every 1 hour)
		private volatile Map<Timezone, List<Timezone>> timeZoneWithFriends = Collections.emptyMap(); // Time zones having the same offset for a given date (refreshed every 1 hour)

		/** Cache for all the time zones' names sorted and uniq*/
		private final List<String> timeZonesByName;

		private final Timer cacheRefresher = new Timer();

		{
			final Map<String, Timezone> tzByJavaId = new HashMap<String, Timezone>();
			final Map<Long, Timezone> tzById = new HashMap<Long, Timezone>();
			final Map<Integer, List<String>> tzByOffset = new HashMap<Integer, List<String>>();
			final Set<String> timeZones = new TreeSet<String>(new Comparator<String>() {

				public int compare(String o1, String o2) {
					return o1.toLowerCase().compareTo(o2.toLowerCase());
				}
			});

			walk(new RecordWalker<Timezone>() {

				public void process(final Timezone inTimeZone) {
					tzByJavaId.put(inTimeZone.getTimezone_javaId(), inTimeZone);
					tzById.put(inTimeZone.getId(), inTimeZone);
					timeZones.add(inTimeZone.getTimezone_javaId());

					List<String> theList = tzByOffset.get(inTimeZone.getJavaTimeZone().getRawOffset());

					if (theList == null) {
						theList = new LinkedList<String>();
						tzByOffset.put(inTimeZone.getJavaTimeZone().getRawOffset(), theList);
					}

					theList.add(inTimeZone.getTimezone_javaId());
				}
			});

			// as they are being returned as is, these Collections need to be read only to prevent data corruption
			this.timeZonesByJavaId = Collections.unmodifiableMap(tzByJavaId);
			this.timeZonesById = Collections.unmodifiableMap(tzById);
			this.timeZonesByName = Collections.unmodifiableList(new LinkedList<String>(timeZones));

			for (final Entry<Integer, List<String>> someTimeZones : tzByOffset.entrySet()) {
				someTimeZones.setValue(Collections.unmodifiableList(someTimeZones.getValue()));
			}

			this.timeZoneJavaIdByOffset = Collections.unmodifiableMap(tzByOffset);

			final TimerTask thetask = new TimerTask() {

				@Override
				public void run() {
					final long now = System.currentTimeMillis();

					class TimezoneKey {

						private final Timezone timezone;

						private TimezoneKey(Timezone inTimezone) {
							this.timezone = inTimezone;
						}

						@Override
						public int hashCode() {
							return this.timezone.getJavaTimeZone().getOffset(now);
						}

						@Override
						public boolean equals(Object obj) {
							return (obj instanceof TimezoneKey) && (this.timezone.getJavaTimeZone().getOffset(now) == ((TimezoneKey) obj).timezone.getJavaTimeZone().getOffset(now));
						}

					}

					final Map<TimezoneKey, List<Timezone>> tzByOffsetNDst = new HashMap<TimezoneKey, List<Timezone>>();
					walk(new RecordWalker<Timezone>() {

						public void process(final Timezone inTimeZone) {
							final TimezoneKey aTimeZone = new TimezoneKey(inTimeZone);

							final List<Timezone> theTimeZones = tzByOffsetNDst.get(aTimeZone);

							if (theTimeZones != null) {
								theTimeZones.add(inTimeZone);
							} else {
								final List<Timezone> aList = new LinkedList<Timezone>();
								tzByOffsetNDst.put(aTimeZone, aList);
								aList.add(inTimeZone);
							}
						}
					});

					final Map<Timezone, List<Timezone>> theResult = new HashMap<Timezone, List<Timezone>>();
					final Map<Timezone, List<Timezone>> theFriendsMap = new HashMap<Timezone, List<Timezone>>();
					for (final Entry<TimezoneKey, List<Timezone>> someTimeZones : tzByOffsetNDst.entrySet()) {
						final List<Timezone> theFriends = Collections.unmodifiableList(someTimeZones.getValue());
						theResult.put(someTimeZones.getKey().timezone, theFriends);

						for (final Timezone aTimezone : theFriends) {
							theFriendsMap.put(aTimezone, theFriends);
						}
					}

					TimeZoneFactoryCommon.this.timeZoneByGroup = Collections.unmodifiableMap(theResult);
					TimeZoneFactoryCommon.this.timeZoneWithFriends = Collections.unmodifiableMap(theFriendsMap);
				}
			};

			this.cacheRefresher.schedule(thetask, 0, 3600000);

		}

		public Timezone findByJavaId(String timezone) {
			return this.timeZonesByJavaId.get(timezone);
		}

		public List<String> findAllNames() {
			return this.timeZonesByName;
		}

		public Timezone find(long id) {
			return this.timeZonesById.get(id);
		}

		public Map<Long, Timezone> findAllMapped() {
			return this.timeZonesById;
		}

		public List<Timezone> findAll() {
			return Collections.unmodifiableList(new LinkedList<Timezone>(this.timeZonesById.values()));
		}

		/**
		 * 
		 * @param inOffset in milliseconds
		 * @return
		 */
		public List<String> findAllByOffset(int inOffset) {
			return this.timeZoneJavaIdByOffset.get(inOffset);
		}

		public abstract int walk(RecordWalker<Timezone> recordWalker);

		public int walkDistincts(RecordWalker<Timezone> inWalker) {
			int amountProcessed = 0;

			for (final Timezone someTimezones : this.timeZoneByGroup.keySet()) {
				inWalker.process(someTimezones);
				amountProcessed++;
			}

			return amountProcessed;
		}

		public List<Timezone> findAllFriends(Timezone inTimeZone) {
			return this.timeZoneWithFriends.get(inTimeZone);
		}

	}

}

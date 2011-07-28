package net.violet.platform.datamodel.factories.implementations;

import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import net.violet.db.records.Record.RecordWalker;
import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.TimezoneImpl;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.TimezoneFactory;

public class TimezoneFactoryImpl extends RecordFactoryImpl<Timezone, TimezoneImpl> implements TimezoneFactory {

	private final TimezoneFactory.TimeZoneFactoryCommon factory = new TimezoneFactory.TimeZoneFactoryCommon() {

		@Override
		public int walk(RecordWalker<Timezone> recordWalker) {
			return TimezoneFactoryImpl.this.walk(recordWalker);
		}

	};

	public TimezoneFactoryImpl() {
		super(TimezoneImpl.SPECIFICATION);
	}

	public int walk(RecordWalker<Timezone> inWalker) {
		return walk(null, null, null, 0, inWalker);
	}

	public List<Timezone> findAll() {
		return this.factory.findAll();
	}

	public List<String> findAllNames() {
		return this.factory.findAllNames();
	}

	public Timezone findByJavaId(String timezone) {
		return this.factory.findByJavaId(timezone);
	}

	public int walkDistincts(RecordWalker<Timezone> inWalker) {
		return this.factory.walkDistincts(inWalker);
	}

	public List<String> findAllByOffset(int inOffset) {
		return this.factory.findAllByOffset(inOffset);
	}

	public List<Timezone> findAllFriends(Timezone inTimezone) {
		return this.factory.findAllFriends(inTimezone);
	}

	static String getTimeZonesCondition(Timezone inTimezone) {
		final SortedSet<Long> mySortedSet = new TreeSet<Long>() {

			@Override
			public String toString() {
				final StringBuilder theBuilder = new StringBuilder();

				for (final Iterator<Long> i = iterator(); i.hasNext();) {
					theBuilder.append(i.next());
					if (i.hasNext()) {
						theBuilder.append(",");
					}
				}

				return theBuilder.toString();
			}
		};

		for (final Timezone aTimezone : Factories.TIMEZONE.findAllFriends(inTimezone)) {
			mySortedSet.add(aTimezone.getId());
		}

		return new StringBuilder(" ( ").append(mySortedSet.toString()).append(" ) ").toString();
	}
}

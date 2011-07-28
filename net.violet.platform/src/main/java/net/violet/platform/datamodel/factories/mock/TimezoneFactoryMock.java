package net.violet.platform.datamodel.factories.mock;

import java.util.List;

import net.violet.db.records.Record.RecordWalker;
import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.factories.TimezoneFactory;
import net.violet.platform.datamodel.mock.TimezoneMock;

public class TimezoneFactoryMock extends RecordFactoryMock<Timezone, TimezoneMock> implements TimezoneFactory {

	private static final void reload() {
		TimezoneMock.BUILDER.generateValuesFromInitFile(3, net.violet.platform.util.Constantes.OS_PATH + "net/violet/platform/datamodel/mock/timezoneInit");
	}

//	important to stay there as the this.factory needs it to be run before it is instanciated.
	{
		TimezoneFactoryMock.reload();
	}

	private TimezoneFactory.TimeZoneFactoryCommon factory = new TimezoneFactory.TimeZoneFactoryCommon() {

		@Override
		public int walk(RecordWalker<Timezone> recordWalker) {
			return TimezoneFactoryMock.this.walk(recordWalker);
		}
	};

	@Override
	public void loadCache() {
		TimezoneFactoryMock.reload();
		this.factory = new TimezoneFactory.TimeZoneFactoryCommon() {

			@Override
			public int walk(RecordWalker<Timezone> recordWalker) {
				return TimezoneFactoryMock.this.walk(recordWalker);
			}
		};
	}

	public TimezoneFactoryMock() {
		super(TimezoneMock.class);
	}

	public int walk(RecordWalker<Timezone> recordWalker) {
		int amountProcessed = 0;

		for (final Timezone aTz : findAll()) {
			recordWalker.process(aTz);
			amountProcessed++;
		}

		return amountProcessed;
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
}

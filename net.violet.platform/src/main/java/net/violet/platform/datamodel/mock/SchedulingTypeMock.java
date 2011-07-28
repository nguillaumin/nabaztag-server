package net.violet.platform.datamodel.mock;

import java.sql.Timestamp;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.util.CCalendar;

public class SchedulingTypeMock extends AbstractMockRecord<SchedulingType, SchedulingTypeMock> implements SchedulingType {

	public static final MockBuilder<SchedulingType> BUILDER = new MockBuilder<SchedulingType>() {

		@Override
		protected SchedulingType build(String[] inParamValues) {
			return new SchedulingTypeMock(Long.parseLong(inParamValues[0]), inParamValues[1], new Timestamp(CCalendar.parseTimestamp(inParamValues[2]).getTime()));
		}
	};

	private final String mLabel;
	private Timestamp timestamp;

	protected SchedulingTypeMock(long inId, String inLabel, Timestamp inTimestamp) {
		super(inId);
		this.mLabel = inLabel;
		this.timestamp = inTimestamp;
	}

	public String getLabel() {
		return this.mLabel;
	}

	public Timestamp getCoveredFor() {
		return this.timestamp;
	}

	public void setCoveredFor(Timestamp inTimestamp) {
		if (inTimestamp != null) {
			this.timestamp = inTimestamp;
		}
	}

}

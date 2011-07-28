/**
 * 
 */
package net.violet.platform.datamodel.mock;

import java.util.TimeZone;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.factories.TimezoneFactory;

/**
 * @author slorg1
 */
public class TimezoneMock extends AbstractMockRecord<Timezone, TimezoneMock> implements Timezone {

	public static final MockBuilder<Timezone> BUILDER = new MockBuilder<Timezone>() {

		@Override
		protected Timezone build(String[] inParamValues) {
			return new TimezoneMock(Long.parseLong(inParamValues[0]), inParamValues[1], inParamValues[2]);
		}
	};

	protected long timezone_id;
	private final String mJavaId;
	private final String mName;

	/**
	 * Only used by the builder to initialise the {@link Timezone}s in the memory.
	 * To get a time zone use getParisTimezone() (for Paris, a few others exist) or the findByJavaId in the factory {@link TimezoneFactory}
	 * 
	 * @param inId
	 * @param inJavaId
	 * @param inName
	 * @param inOffset
	 * @param inUsesDst
	 */
	private TimezoneMock(long inId, String inJavaId, String inName) {
		super(inId);
		this.timezone_id = inId;
		this.mJavaId = inJavaId;
		this.mName = inName;
	}

	public long getTimezone_id() {
		return getId();
	}

	public String getTimezone_javaId() {
		return this.mJavaId;
	}

	public String getTimezone_name() {
		return this.mName;
	}

	public TimeZone getJavaTimeZone() {
		return TimeZone.getTimeZone(this.mJavaId);
	}
}

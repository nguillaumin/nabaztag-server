package net.violet.platform.datamodel;

import java.util.TimeZone;

import net.violet.db.records.Record;

public interface Timezone extends Record<Timezone> {

	long getTimezone_id();

	String getTimezone_javaId();

	String getTimezone_name();

	TimeZone getJavaTimeZone();

}

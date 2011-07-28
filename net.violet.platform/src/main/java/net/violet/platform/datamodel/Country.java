package net.violet.platform.datamodel;

import net.violet.db.records.Record;

public interface Country extends Record<Country> {

	String getName();

	String getCode();

	Continent getContinent();

	Lang getMainLanguage();

}

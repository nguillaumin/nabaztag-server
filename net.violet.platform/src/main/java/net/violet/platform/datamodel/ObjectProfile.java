package net.violet.platform.datamodel;

import java.math.BigDecimal;
import java.sql.Timestamp;

import net.violet.db.records.Record;

public interface ObjectProfile extends Record<ObjectProfile> {

	Files getPicture();

	String getDescription();

	BigDecimal getLongitudeGPS();

	BigDecimal getLatitudeGPS();

	void setPositionGPS(BigDecimal inLatitude, BigDecimal inLongitude);

	void setPicture(Files inPictureFile);

	void setDescription(String inDescription);

	void setProfile(Files inPictureFile, String inDescription);

	Timestamp getUpdateTime();

	String getLabel();

	void setLabel(String label);

}

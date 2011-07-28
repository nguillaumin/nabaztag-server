package net.violet.platform.datamodel.mock;

import java.math.BigDecimal;
import java.sql.Timestamp;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.ObjectProfile;
import net.violet.platform.datamodel.VObject;

public class ObjectProfileMock extends AbstractMockRecord<ObjectProfile, ObjectProfileMock> implements ObjectProfile {

	private String description;
	private Files picture;
	private BigDecimal longitudeGPS;
	private BigDecimal latitudeGPS;
	private String label;

	public ObjectProfileMock(VObject inObject, String inDescription, Files inPictureFile, String inLabel) {
		super(inObject.getId());
		this.description = inDescription;
		this.picture = inPictureFile;
		this.label = inLabel;
	}

	public ObjectProfileMock(VObject inObject, String inDescription, Files inPictureFile) {
		this(inObject, inDescription, inPictureFile, inObject.getObject_login());
	}

	public String getDescription() {
		return this.description;
	}

	public Files getPicture() {
		return this.picture;
	}

	public void setDescription(String inDescription) {
		this.description = inDescription;
	}

	public void setPicture(Files inPictureFile) {
		this.picture = inPictureFile;
	}

	public void setProfile(Files inPictureFile, String inDescription) {
		this.picture = inPictureFile;
		this.description = inDescription;
	}

	public Timestamp getUpdateTime() {
		throw new UnsupportedOperationException();
	}

	public BigDecimal getLatitudeGPS() {
		return this.latitudeGPS;
	}

	public BigDecimal getLongitudeGPS() {
		return this.longitudeGPS;
	}

	public void setPositionGPS(BigDecimal inLatitude, BigDecimal inLongitude) {
		this.latitudeGPS = inLatitude;
		this.longitudeGPS = inLongitude;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}

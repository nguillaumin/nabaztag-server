package net.violet.platform.dataobjects;

import java.math.BigDecimal;

import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.GPSInformationMap;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.ObjectProfile;
import net.violet.platform.datamodel.factories.Factories;

public class ObjectProfileData extends RecordData<ObjectProfile> {

	public ObjectProfileData(ObjectProfile inProfile) {
		super(inProfile);
	}

	public FilesData getPictureFiles() {
		final ObjectProfile theRecord = getRecord();
		if (theRecord != null) {
			return FilesData.getData(theRecord.getPicture());
		}

		return null;
	}

	public String getDescription() {
		final ObjectProfile theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getDescription();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getLongitudeGPS() {
		final ObjectProfile theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getLongitudeGPS() != null)) {
			return theRecord.getLongitudeGPS().toString();
		}

		return null;
	}

	public String getLatitudeGPS() {
		final ObjectProfile theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getLatitudeGPS() != null)) {
			return theRecord.getLatitudeGPS().toString();
		}

		return null;
	}

	public void setPicture(FilesData inPictureFile) {
		final ObjectProfile theRecord = getRecord();
		if (theRecord != null) {
			theRecord.setPicture(inPictureFile.getRecord());
		}
	}

	public void setDescription(String inDescription) {
		final ObjectProfile theRecord = getRecord();
		if ((theRecord != null) && (inDescription != null)) {
			theRecord.setDescription(inDescription);
		}
	}

	public void setProfile(Files inPictureFile, String description) {
		final ObjectProfile theRecord = getRecord();
		if (theRecord != null) {
			theRecord.setProfile(inPictureFile, description);
		}
	}

	public void setGPSInformation(String inLatitude, String inLongitude) throws InvalidParameterException {
		final ObjectProfile theRecord = getRecord();
		if (theRecord != null) {
			final BigDecimal theLatitude;
			final BigDecimal theLongitude;
			try {
				theLatitude = new BigDecimal(inLatitude);
			} catch (final NumberFormatException nfe) {
				throw new InvalidParameterException(APIErrorMessage.INVALID_PARAMETER, GPSInformationMap.LATITUDE);
			}
			try {
				theLongitude = new BigDecimal(inLongitude);
			} catch (final NumberFormatException nfe) {
				throw new InvalidParameterException(APIErrorMessage.INVALID_PARAMETER, GPSInformationMap.LONGITUDE);
			}
			setGPSInformation(theLatitude, theLongitude);
		}
	}

	public void setGPSInformation(BigDecimal inLatitude, BigDecimal inLongitude) {
		final ObjectProfile theRecord = getRecord();
		if (theRecord != null) {
			theRecord.setPositionGPS(inLatitude, inLongitude);
		}
	}

	public ObjectProfile getReference() {
		return getRecord();
	}

	public static ObjectProfileData createObjectProfile(VObjectData inObject) {
		return new ObjectProfileData(Factories.VOBJECT.createObjectProfile(inObject.getReference()));
	}

	public String getLabel() {
		if (getRecord() != null) {
			return getRecord().getLabel();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public void setLabel(String label) {
		if (getRecord() != null) {
			getRecord().setLabel(label);
		}

	}

}

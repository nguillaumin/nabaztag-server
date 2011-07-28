package net.violet.platform.dataobjects;

import java.util.Date;

import net.violet.platform.datamodel.TagTmpSite;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.StringShop;

public class TagTmpSiteData extends RecordData<TagTmpSite> {

	/**
	 * Constructeur à partir d'un TagTmpSite
	 */
	public TagTmpSiteData(TagTmpSite inTagTmpSite) {
		super(inTagTmpSite);
	}

	public String getSerial() {
		final TagTmpSite theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getSerial();
		}

		return StringShop.EMPTY_STRING;
	}

	public ObjectType getObjectType() {
		final TagTmpSite theRecord = getRecord();
		if (theRecord != null) {
			return ObjectType.findByHardware(theRecord.getHardware());
		}

		return null;
	}

	public static TagTmpSiteData findBySerial(String inSerial) {
		final TagTmpSite result = Factories.TAG_TMP_SITE.findBySerial(inSerial);
		if (result != null) {
			return new TagTmpSiteData(result);
		}

		return null;
	}

	public String getIp() {
		final TagTmpSite theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getIp();
		}
		return StringShop.EMPTY_STRING;
	}

	public Date getLastDay() {
		final TagTmpSite theRecord = getRecord();
		if (theRecord != null) {
			return new Date(theRecord.getLast_day() * 1000);
		}
		return null;
	}
}

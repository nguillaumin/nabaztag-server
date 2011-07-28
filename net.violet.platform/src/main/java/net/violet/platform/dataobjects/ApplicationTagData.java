package net.violet.platform.dataobjects;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.datamodel.ApplicationTag;
import net.violet.platform.datamodel.factories.Factories;

public class ApplicationTagData extends APIData<ApplicationTag> {

	public ApplicationTagData(ApplicationTag inRecord) {
		super(inRecord);
	}

	public static List<ApplicationTagData> findAllByLanguage(SiteLangData inLang, int inSkip, int inGetCount) {
		return ApplicationTagData.generateList(Factories.APPLICATION_TAG.findAllByLanguage(inLang.getReference(), inSkip, inGetCount));
	}

	public static List<ApplicationTagData> findAllByLanguageAndObjectType(SiteLangData inLang, ObjectType inObjectType, int inSkip, int inGetCount) {
		return ApplicationTagData.generateList(Factories.APPLICATION_TAG.findAllByLanguageAndHardware(inLang.getReference(), inObjectType.getPrimaryHardware(), inSkip, inGetCount));
	}

	private static List<ApplicationTagData> generateList(List<ApplicationTag> inList) {
		final List<ApplicationTagData> theResult = new ArrayList<ApplicationTagData>();
		for (final ApplicationTag tag : inList) {
			theResult.add(new ApplicationTagData(tag));
		}

		return theResult;
	}

	public String getName() {
		final ApplicationTag record = getRecord();
		if (record != null) {
			return record.getName();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public SiteLangData getLang() {
		final ApplicationTag record = getRecord();
		if (record != null) {
			return SiteLangData.get(record.getLang());
		}

		return null;
	}

	@Override
	protected ObjectClass getObjectClass() {
		return ObjectClass.APPLICATION_TAG;
	}

	public int getSize() {
		final ApplicationTag record = getRecord();
		if (record != null) {
			return record.getSize();
		}

		return 0;
	}

	public static ApplicationTagData findByAPIId(String inAPIId, String inAPIKey) {
		ApplicationTagData theResult = null;

		final long theID = APIData.fromObjectID(inAPIId, ObjectClass.APPLICATION_TAG, inAPIKey);
		if (theID != 0) {
			final ApplicationTag theTag = Factories.APPLICATION_TAG.find(theID);
			if (theTag != null) {
				theResult = new ApplicationTagData(theTag);
			}
		}

		return theResult;
	}

}

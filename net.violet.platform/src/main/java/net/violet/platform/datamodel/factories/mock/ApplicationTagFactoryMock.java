package net.violet.platform.datamodel.factories.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationTag;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.ApplicationTagFactory;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationTagMock;

public class ApplicationTagFactoryMock extends RecordFactoryMock<ApplicationTag, ApplicationTagMock> implements ApplicationTagFactory {

	public ApplicationTagFactoryMock() {
		super(ApplicationTagMock.class);
	}

	public List<ApplicationTag> findAllByLanguage(Lang inLang, int inSkip, int inGetCount) {
		final List<ApplicationTag> theResult = new ArrayList<ApplicationTag>();
		for (final ApplicationTag applicationTag : findAll()) {
			if (applicationTag.getLang().equals(inLang)) {
				theResult.add(applicationTag);
			}
		}

		Collections.sort(theResult, new Comparator<ApplicationTag>() {

			public int compare(ApplicationTag o1, ApplicationTag o2) {
				return o2.getSize() - o1.getSize();
			}
		});

		return getSkipList(theResult, inSkip, inGetCount);
	}

	public List<ApplicationTag> findAllByLanguageAndHardware(Lang inLang, HARDWARE inHardware, int inSkip, int inGetCount) {

		throw new UnsupportedOperationException();
	}

	public ApplicationTag getTag(String inName, Lang inLang) {
		for (final ApplicationTag application : findAll()) {
			if (application.getLang().equals(inLang) && application.getName().equals(inName)) {
				return application;
			}
		}

		return null;
	}

	public ApplicationTag createTag(String inName, Lang inLang) {
		ApplicationTag result = getTag(inName, inLang);
		if (result == null) {
			result = new ApplicationTagMock(inName, inLang);
		}

		return result;
	}

	public void addTagToApplication(Application inApplication, ApplicationTag inTag) {
		if (inApplication.getTags().contains(inTag)) {
			return;
		}

		inApplication.getTags().add(inTag);
		setNewSize(inTag);
	}

	private void setNewSize(ApplicationTag inTag) {
		final int tagSize = 100 * Factories.APPLICATION.findAllByTag(inTag).size() / Factories.APPLICATION.findAllMapped().size();
		inTag.setSize(tagSize);
	}

	public void removeTagFromApplication(Application inApplication, ApplicationTag inTag) {
		if (!inApplication.getTags().contains(inTag)) {
			return;
		}

		inApplication.getTags().remove(inTag);
		setNewSize(inTag);
	}

}

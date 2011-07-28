package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.TagTmpSite;
import net.violet.platform.datamodel.factories.TagTmpSiteFactory;
import net.violet.platform.datamodel.mock.TagTmpSiteMock;

public class TagTmpSiteFactoryMock extends RecordFactoryMock<TagTmpSite, TagTmpSiteMock> implements TagTmpSiteFactory {

	TagTmpSiteFactoryMock() {
		super(TagTmpSiteMock.class);
	}

	public TagTmpSite findBySerial(String serial) {
		for (final TagTmpSite thetagTmp : findAll()) {
			if (thetagTmp.getSerial().equals(serial)) {
				return thetagTmp;
			}
		}
		return null;
	}

}

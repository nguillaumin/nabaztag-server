package net.violet.platform.datamodel.factories.implementations;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.TagTmpSite;
import net.violet.platform.datamodel.TagTmpSiteImpl;
import net.violet.platform.datamodel.factories.TagTmpSiteFactory;

public class TagTmpSiteFactoryImpl extends RecordFactoryImpl<TagTmpSite, TagTmpSiteImpl> implements TagTmpSiteFactory {

	TagTmpSiteFactoryImpl() {
		super(TagTmpSiteImpl.SPECIFICATION);
	}

	public TagTmpSite findBySerial(String serial) {
		return findByKey(0, serial);
	}

}

package net.violet.platform.datamodel.factories;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.TagTmpSite;

public interface TagTmpSiteFactory extends RecordFactory<TagTmpSite> {

	TagTmpSite findBySerial(String serial);

}

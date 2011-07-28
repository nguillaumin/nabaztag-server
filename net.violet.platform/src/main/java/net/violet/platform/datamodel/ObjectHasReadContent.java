package net.violet.platform.datamodel;

import net.violet.db.records.associations.AssoRecord;

public interface ObjectHasReadContent extends AssoRecord<VAction, ObjectHasReadContent> {

	VAction getAction();

	void setContent(Content inContent);

	Content getContent();

}

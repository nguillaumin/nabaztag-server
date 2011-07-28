package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Content;
import net.violet.platform.datamodel.ObjectHasReadContent;
import net.violet.platform.datamodel.VAction;
import net.violet.platform.datamodel.VObject;

public class ObjectHasReadContentMock extends AbstractMockRecord<ObjectHasReadContent, ObjectHasReadContentMock> implements ObjectHasReadContent {

	private final VAction mAction;
	private final VObject mObject;
	private Content mContent;

	public ObjectHasReadContentMock(VAction inAction, Content inContent, VObject inObject) {
		super(0);
		this.mAction = inAction;
		this.mObject = inObject;
		this.mContent = inContent;
	}

	public long countObjectHasReadContent() {
		// TODO Auto-generated method stub
		return 0;
	}

	public VAction getAction() {
		return this.mAction;
	}

	public Content getContent() {
		return this.mContent;
	}

	public void setContent(Content inContent) {
		this.mContent = inContent;
	}

	public VObject getObject() {
		return this.mObject;
	}

}

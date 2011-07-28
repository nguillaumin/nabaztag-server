package net.violet.platform.datamodel;

import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.SQLKey;
import net.violet.db.records.associations.AssociationRecord;
import net.violet.db.records.associations.SQLAssociationSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.platform.util.StringShop;

public class ObjectHasReadContentImpl extends AssociationRecord<VAction, ObjectHasReadContent, ObjectHasReadContentImpl> implements ObjectHasReadContent {

	public static final SQLAssociationSpecification<ObjectHasReadContentImpl> SPECIFICATION = new SQLAssociationSpecification<ObjectHasReadContentImpl>("object_has_read_content", ObjectHasReadContentImpl.class, new SQLKey("object_id", StringShop.ACTION_ID));

	protected long object_id;
	protected long action_id;
	protected long content_id;

	/**
	 * Action associée.
	 */
	private final SingleAssociationNotNull<ObjectHasReadContent, VAction, VActionImpl> mAction;

	/**
	 * Contenu associée.
	 */
	private final SingleAssociationNotNull<ObjectHasReadContent, Content, ContentImpl> mContent;

	@Override
	public SQLAssociationSpecification<ObjectHasReadContentImpl> getSpecification() {
		return ObjectHasReadContentImpl.SPECIFICATION;
	}

	public ObjectHasReadContentImpl(VActionImpl inAction, ContentImpl inContent, VObject inObject) {
		this.action_id = inAction.getId();
		this.object_id = inObject.getId();
		this.content_id = inContent.getId();
		this.mAction = new SingleAssociationNotNull<ObjectHasReadContent, VAction, VActionImpl>(this, StringShop.ACTION_ID, inAction);
		this.mContent = new SingleAssociationNotNull<ObjectHasReadContent, Content, ContentImpl>(this, StringShop.CONTENT_ID, inContent);
	}

	public ObjectHasReadContentImpl() {
		this.mAction = new SingleAssociationNotNull<ObjectHasReadContent, VAction, VActionImpl>(this, StringShop.ACTION_ID, VActionImpl.SPECIFICATION);
		this.mContent = new SingleAssociationNotNull<ObjectHasReadContent, Content, ContentImpl>(this, StringShop.CONTENT_ID, ContentImpl.SPECIFICATION);
	}

	public VAction getAction() {
		return this.mAction.get(this.action_id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.violet.platform.datamodel.ObjectHasReadContent#setParameters(com.
	 * violet.platform.datamodel.VAction, net.violet.platform.datamodel.Content,
	 * net.violet.platform.datamodel.VObject)
	 */
	public void setContent(Content inContent) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setContent_id(theUpdateMap, inContent);
		update(theUpdateMap);
	}

	public Content getContent() {
		return this.mContent.get(this.content_id);
	}

	private void setContent_id(Map<String, Object> inUpdateMap, Content inContent) {
		if ((inContent != null) && (this.content_id != inContent.getId())) {
			this.content_id = inContent.getId();
			inUpdateMap.put(StringShop.CONTENT_ID, this.content_id);
		}
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

}

package net.violet.platform.datamodel.factories.implementations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Content;
import net.violet.platform.datamodel.ContentImpl;
import net.violet.platform.datamodel.ObjectHasReadContent;
import net.violet.platform.datamodel.ObjectHasReadContentImpl;
import net.violet.platform.datamodel.VAction;
import net.violet.platform.datamodel.VActionImpl;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.ObjectHasReadContentFactory;
import net.violet.platform.util.StringShop;

public class ObjectHasReadContentFactoryImpl extends RecordFactoryImpl<ObjectHasReadContent, ObjectHasReadContentImpl> implements ObjectHasReadContentFactory {

	public ObjectHasReadContentFactoryImpl() {
		super(ObjectHasReadContentImpl.SPECIFICATION);
	}

	public long countByContent(Content inContent) {
		final List<Object> values = Arrays.asList(new Object[] { inContent.getId(), inContent.getAction().getId() });
		return count(null, StringShop.CONTENT_ID_CONDITION + StringShop.CONDITION_ACCUMULATOR + StringShop.ACTION_ID_CONDITION, values, null);
	}

	public ObjectHasReadContent create(VAction inAction, Content inContent, VObject inObject) {
		return new ObjectHasReadContentImpl((VActionImpl) inAction, (ContentImpl) inContent, inObject);
	}

	public ObjectHasReadContent findByActionAndVObject(VAction inAction, VObject inVObject) {
		return findByKey(0, new Object[] { inVObject.getId(), inAction.getId() });
	}

	public List<ObjectHasReadContent> findAllByObject(VObject inVObject) {
		return (inVObject != null) ? findAll("object_id = ?", Arrays.asList(new Object[] { inVObject.getId() })) : (List<ObjectHasReadContent>) (Object) Collections.emptyList();
	}

	public List<ObjectHasReadContent> findAllByAction(VAction inAction) {
		return (inAction != null) ? findAll("action_id = ?", Arrays.asList(new Object[] { inAction.getId() })) : (List<ObjectHasReadContent>) (Object) Collections.emptyList();
	}

}

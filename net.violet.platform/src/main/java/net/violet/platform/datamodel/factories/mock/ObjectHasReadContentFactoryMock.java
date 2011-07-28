package net.violet.platform.datamodel.factories.mock;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Content;
import net.violet.platform.datamodel.ObjectHasReadContent;
import net.violet.platform.datamodel.VAction;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.ObjectHasReadContentFactory;
import net.violet.platform.datamodel.mock.ObjectHasReadContentMock;

public class ObjectHasReadContentFactoryMock extends RecordFactoryMock<ObjectHasReadContent, ObjectHasReadContentMock> implements ObjectHasReadContentFactory {

	ObjectHasReadContentFactoryMock() {
		super(ObjectHasReadContentMock.class);
	}

	public long countByContent(Content inContent) {
		final VAction theAction = inContent.getAction();

		int count = 0;

		for (final ObjectHasReadContent anObjectHasReadContent : findAllMapped().values()) {
			if ((anObjectHasReadContent.getContent() == inContent) && (anObjectHasReadContent.getAction() == theAction)) {
				count++;
			}
		}
		return count;
	}

	public ObjectHasReadContent create(VAction inAction, Content inContent, VObject inObject) {
		return new ObjectHasReadContentMock(inAction, inContent, inObject);
	}

	public Map<VAction, List<ObjectHasReadContent>> findByObject(VObject inObject) {
		final Collection<ObjectHasReadContent> theList = findAll();
		final Map<VAction, List<ObjectHasReadContent>> result = new HashMap<VAction, List<ObjectHasReadContent>>();

		for (final ObjectHasReadContent ohrc : theList) {
			if (((ObjectHasReadContentMock) ohrc).getObject().getId().equals(inObject.getId())) {
				if (result.get(ohrc.getAction()) == null) {
					result.put(ohrc.getAction(), new LinkedList<ObjectHasReadContent>());
				}
				result.get(ohrc.getAction()).add(ohrc);
			}
		}
		return result;
	}

	public ObjectHasReadContent findByActionAndVObject(VAction inAction, VObject inVObject) {
		for (final ObjectHasReadContent ohrc : findAll()) {
			if (ohrc.getAction().getId().equals(inAction.getId()) && ((ObjectHasReadContentMock) ohrc).getObject().getId().equals(inVObject.getId())) {
				return ohrc;
			}
		}
		return null;
	}

	public List<ObjectHasReadContent> findAllByObject(VObject inObject) {
		final List<ObjectHasReadContent> theResult = new LinkedList<ObjectHasReadContent>();
		for (final ObjectHasReadContent ohrc : findAll()) {
			if (((ObjectHasReadContentMock) ohrc).getObject().getId().equals(inObject.getId())) {
				theResult.add(ohrc);
			}
		}

		return theResult;
	}

	public List<ObjectHasReadContent> findAllByAction(VAction inAction) {
		final List<ObjectHasReadContent> theResult = new LinkedList<ObjectHasReadContent>();
		for (final ObjectHasReadContent ohrc : findAll()) {
			if (((ObjectHasReadContentMock) ohrc).getAction().getId().equals(inAction.getId())) {
				theResult.add(ohrc);
			}
		}

		return theResult;
	}

}

package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Content;
import net.violet.platform.datamodel.ObjectHasReadContent;
import net.violet.platform.datamodel.VAction;
import net.violet.platform.datamodel.VObject;

public interface ObjectHasReadContentFactory extends RecordFactory<ObjectHasReadContent> {

	/**
	 * Compte le nombre d'objets pour un contenu donné.
	 */
	long countByContent(Content inContent);

	ObjectHasReadContent create(VAction inAction, Content inContent, VObject inObject);

	ObjectHasReadContent findByActionAndVObject(VAction inAction, VObject inVObject);

	List<ObjectHasReadContent> findAllByObject(VObject theObject);

	List<ObjectHasReadContent> findAllByAction(VAction action);

}

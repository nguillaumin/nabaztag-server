package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Group;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.common.FilesAccessor;

public interface GroupFactory extends RecordFactory<Group>, FilesAccessor {

	Group create(String name, String description, VObject creator, Files picture, Files stream, Lang language, Feed feed);

	List<Group> findAllByMember(VObject record);

}

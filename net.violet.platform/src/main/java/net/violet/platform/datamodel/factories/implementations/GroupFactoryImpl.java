package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Group;
import net.violet.platform.datamodel.GroupImpl;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.GroupFactory;

import org.apache.log4j.Logger;

public class GroupFactoryImpl extends RecordFactoryImpl<Group, GroupImpl> implements GroupFactory {

	private static final Logger LOGGER = Logger.getLogger(GroupFactoryImpl.class);

	GroupFactoryImpl() {
		super(GroupImpl.SPECIFICATION);
	}

	public Group create(String name, String description, VObject creator, Files picture, Files stream, Lang language, Feed feed) {
		try {
			return new GroupImpl(name, description, creator, picture, stream, language, feed);
		} catch (final SQLException e) {
			GroupFactoryImpl.LOGGER.fatal(e, e);
		}

		return null;
	}

	public boolean usesFiles(Files file) {
		return count(null, "picture_id = ? OR stream_id = ?", Arrays.asList(new Object[] { file.getId(), file.getId() }), null) > 0;
	}

	public List<Group> findAllByMember(VObject member) {
		return findAll(new String[] { "group_has_members" }, "groups.id = group_has_members.group_id AND group_has_members.object_id = ?", Collections.<Object> singletonList(member.getId()), null);
	}

}

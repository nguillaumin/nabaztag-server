package net.violet.platform.datamodel.factories.mock;

import java.util.ArrayList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Group;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.GroupFactory;
import net.violet.platform.datamodel.mock.GroupMock;

public class GroupFactoryMock extends RecordFactoryMock<Group, GroupMock> implements GroupFactory {

	GroupFactoryMock() {
		super(GroupMock.class);
	}

	public Group create(String name, String description, VObject creator, Files picture, Files stream, Lang language, Feed feed) {
		return new GroupMock(name, description, creator, picture, stream, language, feed);
	}

	public boolean usesFiles(Files file) {
		for (final Group aGroup : findAll()) {
			if (aGroup.getPicture().equals(file) || aGroup.getStream().equals(file)) {
				return true;
			}
		}
		return false;
	}

	public List<Group> findAllByMember(VObject record) {
		final List<Group> result = new ArrayList<Group>();
		for (final Group aGroup : findAll()) {
			if (aGroup.getMembers().contains(record)) {
				result.add(aGroup);
			}
		}
		return result;
	}

}

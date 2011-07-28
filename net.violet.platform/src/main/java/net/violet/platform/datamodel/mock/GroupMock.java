package net.violet.platform.datamodel.mock;

import java.util.ArrayList;
import java.util.List;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Group;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;

public class GroupMock extends AbstractMockRecord<Group, GroupMock> implements Group {

	private final String name;
	private final String description;
	private final long creatorId;
	private final long pictureId;
	private final long streamId;
	private final long langId;
	private final List<VObject> members;
	private final long feedId;

	public GroupMock(String name, String description, VObject creator, Files picture, Files stream, Lang lang, Feed feed) {
		this(name, description, creator.getId(), picture.getId(), stream.getId(), lang.getId(), feed.getId());
	}

	public GroupMock(String name, String description, long creatorId, long pictureId, long streamId, long langId, long feedId) {
		super(0);
		this.name = name;
		this.description = description;
		this.creatorId = creatorId;
		this.pictureId = pictureId;
		this.streamId = streamId;
		this.langId = langId;
		this.members = new ArrayList<VObject>();
		this.feedId = feedId;
	}

	public Feed getFeed() {
		return Factories.FEED.find(this.feedId);
	}

	public VObject getCreator() {
		return Factories.VOBJECT.find(this.creatorId);
	}

	public String getDescription() {
		return this.description;
	}

	public String getName() {
		return this.name;
	}

	public Files getPicture() {
		return Factories.FILES.find(this.pictureId);
	}

	public Files getStream() {
		return Factories.FILES.find(this.streamId);
	}

	public Lang getLanguage() {
		return Factories.LANG.find(this.langId);
	}

	public List<VObject> getMembers() {
		return this.members;
	}

}

package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.Group;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Files.CATEGORIES;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.feeds.FeedsManager;

import org.apache.log4j.Logger;

public class GroupData extends APIData<Group> {

	private static final Logger LOGGER = Logger.getLogger(GroupData.class);

	public static GroupData getData(Group group) {
		try {
			return RecordData.getData(group, GroupData.class, Group.class);
		} catch (final InstantiationException e) {
			GroupData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			GroupData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			GroupData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			GroupData.LOGGER.fatal(e, e);
		}

		return null;
	}

	protected GroupData(Group inRecord) {
		super(inRecord);
	}

	/**
	 * Creates a new group. The stream file is created and filled up.
	 * @param creator
	 * @param name
	 * @param description
	 * @param picture
	 * @return
	 */
	public static GroupData create(VObjectData creator, String name, String description, FilesData picture, TtsLangData language) {
		final FilesData streamFile = FilesData.create(MimeType.MIME_TYPES.XML, CATEGORIES.BROADCAST);

		final FeedData theFeed = FeedData.getFeed(streamFile.getUrl(), language, Feed.Type.GROUP);
		if (theFeed == null) {
			streamFile.scheduleDeletion();
			return null;
		}

		final Group newGroup = Factories.GROUP.create(name, description, creator.getRecord(), picture.getRecord(), streamFile.getRecord(), language.getRecord(), theFeed.getRecord());
		if (newGroup == null) {
			return null;
		}

		final GroupData theGroup = GroupData.getData(newGroup);
		try {
			FeedsManager.createEmptyFeed(theGroup);
		} catch (final Exception e) {
			GroupData.LOGGER.fatal(e, e);
			streamFile.scheduleDeletion();
			theGroup.delete();
			return null;
		}

		return theGroup;
	}

	public String getName() {
		return getRecord().getName();
	}

	public String getDescription() {
		return getRecord().getDescription();
	}

	public VObjectData getCreator() {
		return VObjectData.getData(getRecord().getCreator());
	}

	public FilesData getPicture() {
		return FilesData.getData(getRecord().getPicture());
	}

	@Override
	protected ObjectClass getObjectClass() {
		return ObjectClass.GROUP;
	}

	public FilesData getStream() {
		return FilesData.getData(getRecord().getStream());
	}

	public ObjectLangData getLanguage() {
		return ObjectLangData.get(getRecord().getLanguage());
	}

	public static GroupData findByAPIId(String apiId, String apiKey) {
		GroupData result = null;
		final long theID = APIData.fromObjectID(apiId, ObjectClass.GROUP, apiKey);
		if (theID != 0) {
			final Group theGroup = Factories.GROUP.find(theID);
			if (theGroup != null) {
				result = GroupData.getData(theGroup);
			}
		}

		return result;
	}

	public void addMember(VObjectData newMember) {
		final Group record = getRecord();
		if (record != null) {
			record.getMembers().add(newMember.getRecord());
		}
	}

	public List<VObjectData> getMembers() {
		final List<VObjectData> theMembers = new ArrayList<VObjectData>();
		for (final VObject anObject : getRecord().getMembers()) {
			theMembers.add(VObjectData.getData(anObject));
		}
		return theMembers;
	}

	public void removeMember(VObjectData theObject) {
		getRecord().getMembers().remove(theObject.getRecord());
	}

	@Override
	protected boolean doDelete() {
		getRecord().getMembers().clear();
		getPicture().scheduleDeletion();
		getStream().scheduleDeletion();
		return true;
	}

	public FeedData getFeed() {
		return FeedData.getData(getRecord().getFeed());
	}

	public static List<GroupData> findAllByMember(VObjectData member) {
		return GroupData.generateList(Factories.GROUP.findAllByMember(member.getRecord()));
	}

	private static List<GroupData> generateList(List<Group> list) {
		final List<GroupData> theList = new ArrayList<GroupData>();
		for (final Group aGroup : list) {
			theList.add(GroupData.getData(aGroup));
		}
		return theList;
	}

}

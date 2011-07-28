package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.ListAssociation;
import net.violet.db.records.associations.SingleAssociationNotNull;

import org.apache.log4j.Logger;

public class GroupImpl extends ObjectRecord<Group, GroupImpl> implements Group {

	private static final Logger LOGGER = Logger.getLogger(GroupImpl.class);

	public static final SQLObjectSpecification<GroupImpl> SPECIFICATION = new SQLObjectSpecification<GroupImpl>("groups", GroupImpl.class, new SQLKey[] { new SQLKey("id") });

	private static final String[] NEW_COLUMNS = new String[] { "name", "description", "creator_id", "picture_id", "stream_id", "language_id", "feed_id" };

	protected long id;
	protected String name;
	protected String description;
	protected long creator_id;
	protected long picture_id;
	protected long stream_id;
	protected long language_id;
	protected long feed_id;

	private List<VObject> members;

	private final SingleAssociationNotNull<Group, VObject, VObjectImpl> creator;
	private final SingleAssociationNotNull<Group, Files, FilesImpl> picture;
	private final SingleAssociationNotNull<Group, Files, FilesImpl> stream;
	private final SingleAssociationNotNull<Group, Lang, LangImpl> language;
	private final SingleAssociationNotNull<Group, Feed, FeedImpl> feed;

	protected GroupImpl() {
		this.creator = new SingleAssociationNotNull<Group, VObject, VObjectImpl>(this, "creator_id", VObjectImpl.SPECIFICATION);
		this.picture = new SingleAssociationNotNull<Group, Files, FilesImpl>(this, "picture_id", FilesImpl.SPECIFICATION);
		this.stream = new SingleAssociationNotNull<Group, Files, FilesImpl>(this, "stream_id", FilesImpl.SPECIFICATION);
		this.language = new SingleAssociationNotNull<Group, Lang, LangImpl>(this, "language_id", LangImpl.SPECIFICATION);
		this.feed = new SingleAssociationNotNull<Group, Feed, FeedImpl>(this, "feed_id", FeedImpl.SPECIFICATION);
	}

	protected GroupImpl(long id) throws NoSuchElementException, SQLException {
		init(id);
		this.creator = new SingleAssociationNotNull<Group, VObject, VObjectImpl>(this, "creator_id", VObjectImpl.SPECIFICATION);
		this.picture = new SingleAssociationNotNull<Group, Files, FilesImpl>(this, "picture_id", FilesImpl.SPECIFICATION);
		this.stream = new SingleAssociationNotNull<Group, Files, FilesImpl>(this, "stream_id", FilesImpl.SPECIFICATION);
		this.language = new SingleAssociationNotNull<Group, Lang, LangImpl>(this, "language_id", LangImpl.SPECIFICATION);
		this.feed = new SingleAssociationNotNull<Group, Feed, FeedImpl>(this, "feed_id", FeedImpl.SPECIFICATION);
	}

	public GroupImpl(String name, String description, VObject creator, Files picture, Files stream, Lang language, Feed feed) throws SQLException {
		this.creator_id = creator.getId();
		this.name = name;
		this.description = description;
		this.stream_id = stream.getId();
		this.picture_id = picture.getId();
		this.language_id = language.getId();
		this.feed_id = feed.getId();

		init(GroupImpl.NEW_COLUMNS);

		this.creator = new SingleAssociationNotNull<Group, VObject, VObjectImpl>(this, "creator_id", VObjectImpl.SPECIFICATION);
		this.picture = new SingleAssociationNotNull<Group, Files, FilesImpl>(this, "picture_id", FilesImpl.SPECIFICATION);
		this.stream = new SingleAssociationNotNull<Group, Files, FilesImpl>(this, "stream_id", FilesImpl.SPECIFICATION);
		this.language = new SingleAssociationNotNull<Group, Lang, LangImpl>(this, "language_id", LangImpl.SPECIFICATION);
		this.feed = new SingleAssociationNotNull<Group, Feed, FeedImpl>(this, "feed_id", FeedImpl.SPECIFICATION);
	}

	@Override
	public SQLObjectSpecification<GroupImpl> getSpecification() {
		return GroupImpl.SPECIFICATION;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public VObject getCreator() {
		return this.creator.get(this.creator_id);
	}

	public String getDescription() {
		return this.description;
	}

	public String getName() {
		return this.name;
	}

	public Feed getFeed() {
		return this.feed.get(this.feed_id);
	}

	public Files getPicture() {
		return this.picture.get(this.picture_id);
	}

	public Files getStream() {
		return this.stream.get(this.stream_id);
	}

	public Lang getLanguage() {
		return this.language.get(this.language_id);
	}

	public List<VObject> getMembers() {
		if (this.members == null) {
			try {
				this.members = ListAssociation.createListAssociation(this, VObjectImpl.SPECIFICATION, "group_has_members", "group_id", "object_id");
			} catch (final SQLException anException) {
				GroupImpl.LOGGER.fatal(anException, anException);
			}
		}
		return this.members;
	}

}

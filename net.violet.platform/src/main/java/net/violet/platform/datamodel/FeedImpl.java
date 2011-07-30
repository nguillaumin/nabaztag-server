package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.sql.Timestamp;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.util.UpdateMap;

public class FeedImpl extends ObjectRecord<Feed, FeedImpl> implements Feed {

	public static final SQLObjectSpecification<FeedImpl> SPECIFICATION = new SQLObjectSpecification<FeedImpl>("feeds", FeedImpl.class, new SQLKey[] { new SQLKey("id") });

	private static final String[] NEW_COLUMNS = new String[] { "url", "language_id", "type", "access_right" };

	protected long id;
	protected String url;
	protected long language_id;	
	protected Timestamp last_modified;
	protected String etag;
	protected String type;
	protected String access_right;

	private final SingleAssociationNotNull<Feed, Lang, LangImpl> language;

	protected FeedImpl(long id) throws SQLException {
		init(id);
		this.language = new SingleAssociationNotNull<Feed, Lang, LangImpl>(this, "language_id", LangImpl.SPECIFICATION);
	}

	protected FeedImpl() {
		this.language = new SingleAssociationNotNull<Feed, Lang, LangImpl>(this, "language_id", LangImpl.SPECIFICATION);
	}

	public FeedImpl(String url, Lang language, Type type, AccessRight accessRight) throws SQLException {
		this.language_id = language.getId();
		this.url = url;
		this.type = type.toString();
		this.access_right = accessRight.toString();

		init(FeedImpl.NEW_COLUMNS);

		this.language = new SingleAssociationNotNull<Feed, Lang, LangImpl>(this, "language_id", LangImpl.SPECIFICATION);
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public SQLObjectSpecification<FeedImpl> getSpecification() {
		return FeedImpl.SPECIFICATION;
	}

	public String getETag() {
		return this.etag;
	}

	public Lang getLanguage() {
		return this.language.get(this.language_id);
	}

	public Timestamp getLastModified() {
		return this.last_modified;
	}

	public String getUrl() {
		return this.url;
	}

	public Type getType() {
		return Type.findByLabel(this.type);
	}

	public AccessRight getAccessRight() {
		return AccessRight.findByLabel(this.access_right);
	}

	public void updateInformation(String inEtag, Timestamp lastModified) {
		final UpdateMap map = new UpdateMap();
		this.etag = map.updateField("etag", this.etag, inEtag);
		this.last_modified = map.updateField("last_modified", this.last_modified, lastModified);
		update(map);
	}

	public void updateLang(Lang lang) {
		final UpdateMap map = new UpdateMap();
		this.language_id = map.updateField("language_id", this.language_id, lang.getId());
		update(map);
	}

	@Override
	protected void doDelete() throws SQLException {
		for (final FeedItem anItem : Factories.FEED_ITEM.findAllByFeed(this)) {
			anItem.delete();
		}
		super.doDelete();
	}
}

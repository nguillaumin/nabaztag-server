/**
 * 
 */
package net.violet.platform.datamodel.mock;

import java.sql.Timestamp;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.factories.Factories;

public class FeedMock extends AbstractMockRecord<Feed, FeedMock> implements Feed {

	private final String url;
	private long languageId;
	private String etag;
	private Timestamp lastModified;
	private final Type type;
	private final AccessRight accessRight;

	protected FeedMock(String url, long languageId, Type type, AccessRight accessRight) {
		super(0);
		this.url = url;
		this.languageId = languageId;
		this.type = type;
		this.accessRight = accessRight;
	}

	public FeedMock(String url, Lang language, Type type, AccessRight accessRight) {
		this(url, language.getId(), type, accessRight);
	}

	public String getETag() {
		return this.etag;
	}

	public AccessRight getAccessRight() {
		return this.accessRight;
	}

	public Lang getLanguage() {
		return Factories.LANG.find(this.languageId);
	}

	public Timestamp getLastModified() {
		return this.lastModified;
	}

	public String getUrl() {
		return this.url;
	}

	public Type getType() {
		return this.type;
	}

	public void updateInformation(String inEtag, Timestamp inLastModified) {
		this.etag = inEtag;
		this.lastModified = inLastModified;
	}

	public void updateLang(Lang lang) {
		this.languageId = lang.getId();
	}

}

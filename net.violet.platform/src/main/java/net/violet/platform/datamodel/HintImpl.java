package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.NoSuchElementException;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;

public class HintImpl extends ObjectRecord<Hint, HintImpl> implements Hint {

	public static final SQLObjectSpecification<HintImpl> SPECIFICATION = new SQLObjectSpecification<HintImpl>("hint", HintImpl.class, new SQLKey("id"), "modification_date");

	private static final String[] NEW_COLUMNS = new String[] { "title", "content", "picture", "picture_width", "picture_height", "link", "modification_date", };

	protected long id;
	protected String title;
	protected String content;
	protected String picture;
	protected Integer picture_width;
	protected Integer picture_height;
	protected String link;
	protected Timestamp modification_date;
	protected Timestamp creation_date;

	protected HintImpl() {
	}

	protected HintImpl(long id) throws NoSuchElementException, SQLException {
		init(id);
	}

	public HintImpl(String inTitle, String inContent, String inPicture, Integer inPictureWidth, Integer inPictureHeight, String inLink) throws SQLException {

		this.title = inTitle;
		this.content = inContent;
		this.picture = inPicture;
		this.picture_height = inPictureHeight;
		this.picture_width = inPictureWidth;
		this.link = inLink;

		init(HintImpl.NEW_COLUMNS);
	}

	@Override
	public SQLObjectSpecification<HintImpl> getSpecification() {
		return HintImpl.SPECIFICATION;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public String getContent() {
		return this.content;
	}

	public Timestamp getCreationDate() {
		return this.creation_date;
	}

	public String getLink() {
		return this.link;
	}

	public Timestamp getModificationDate() {
		return this.modification_date;
	}

	public String getPicture() {
		return this.picture;
	}

	public Integer getPictureHeight() {
		return this.picture_height;
	}

	public Integer getPictureWidth() {
		return this.picture_width;
	}

	public String getTitle() {
		return this.title;
	}

}

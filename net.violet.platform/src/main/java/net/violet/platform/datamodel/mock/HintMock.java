package net.violet.platform.datamodel.mock;

import java.sql.Timestamp;
import java.util.Calendar;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Hint;

public class HintMock extends AbstractMockRecord<Hint, HintMock> implements Hint {

	private final String title;
	private final String content;
	private final String picture;
	private final Integer picture_width;
	private final Integer picture_height;
	private final String link;
	private final Timestamp modification_date;
	private final Timestamp creation_date;

	public HintMock(long inId, String inTitle, String inContent, String inPicture, Integer inPictureWidth, Integer inPictureHeight, String inLink) {
		super(inId);
		this.title = inTitle;
		this.content = inContent;
		this.picture = inPicture;
		this.picture_height = inPictureHeight;
		this.picture_width = inPictureWidth;
		this.link = inLink;
		this.modification_date = new Timestamp(Calendar.getInstance().getTimeInMillis());
		this.creation_date = new Timestamp(Calendar.getInstance().getTimeInMillis());
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

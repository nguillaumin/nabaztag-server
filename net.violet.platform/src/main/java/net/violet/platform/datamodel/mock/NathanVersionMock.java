package net.violet.platform.datamodel.mock;

import java.sql.Date;
import java.util.List;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.NathanTag;
import net.violet.platform.datamodel.NathanVersion;
import net.violet.platform.datamodel.VObject;

public class NathanVersionMock extends AbstractMockRecord<NathanVersion, NathanVersionMock> implements NathanVersion {

	private String inIsbn;
	private VObject inAuthor;
	private boolean isOfficial;

	public NathanVersionMock(long inId) {
		super(inId);
	}

	public NathanVersionMock(long inId, VObject author, String isbn) {
		super(inId);
		this.inAuthor = author;
		this.inIsbn = isbn;

	}

	public NathanVersionMock(long inId, VObject author, String isbn, boolean isOfficial) {
		super(inId);
		this.inAuthor = author;
		this.inIsbn = isbn;
		this.isOfficial = isOfficial;
	}

	public void decreaseNb() {
		// TODO Auto-generated method stub

	}

	public VObject getAuthor() {
		return this.inAuthor;
	}

	public Date getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getIsbn() {
		return this.inIsbn;
	}

	public long getNb() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean getOfficial() {
		return this.isOfficial;
	}

	public boolean getShared() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<NathanTag> getTags() {
		// TODO Auto-generated method stub
		return null;
	}

	public void increaseNb() {
		// TODO Auto-generated method stub

	}

	public void setStatus(Status status) {
		// TODO Auto-generated method stub

	}

	public void setVersionInformation(String description, Status status, boolean shared, List<NathanTag> tagsList) {
		// TODO Auto-generated method stub

	}

	public void setPreview(long previewId) {
		// TODO Auto-generated method stub

	}

	public Long getPreview() {
		// TODO Auto-generated method stub
		return null;
	}

}

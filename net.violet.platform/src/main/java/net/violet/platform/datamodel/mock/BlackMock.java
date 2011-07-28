package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Black;
import net.violet.platform.datamodel.User;

public class BlackMock extends AbstractMockRecord<Black, BlackMock> implements Black {

	private User mUser;
	private User mBlacked;
	private String mComment;

	public BlackMock(User inUser, User inBlacked, String inComment) {
		super(0);
		this.mUser = inUser;
		this.mBlacked = inBlacked;
		this.mComment = inComment;
	}

	public User getUser() {
		return this.mUser;
	}

	public User getBlacked() {
		return this.mBlacked;
	}

	public String getComment() {
		return this.mComment;
	}

	public void setInformation(User inUser, User inBlacked, String inComment) {
		this.mUser = inUser;
		this.mBlacked = inBlacked;
		this.mComment = inComment;
	}

	public void setComment(String comment) {
		this.mComment = comment;
	}

}

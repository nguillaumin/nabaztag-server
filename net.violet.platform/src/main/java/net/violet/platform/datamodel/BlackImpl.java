package net.violet.platform.datamodel;

import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.SQLKey;
import net.violet.db.records.associations.AssociationRecord;
import net.violet.db.records.associations.SQLAssociationSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;

public class BlackImpl extends AssociationRecord<User, Black, BlackImpl> implements Black {

	/**
	 * Spécification
	 */
	public static final SQLAssociationSpecification<BlackImpl> SPECIFICATION = new SQLAssociationSpecification<BlackImpl>("black", BlackImpl.class, new SQLKey("black_user", "black_blacked"));

	/**
	 * Champs de l'enregistrement.
	 */
	protected long black_user;
	protected long black_blacked;
	protected String black_comment;

	private final SingleAssociationNotNull<Black, User, UserImpl> mUser;
	private final SingleAssociationNotNull<Black, User, UserImpl> mBlacked;

	protected BlackImpl() {
		this.mUser = new SingleAssociationNotNull<Black, User, UserImpl>(this, "black_user", UserImpl.SPECIFICATION);
		this.mBlacked = new SingleAssociationNotNull<Black, User, UserImpl>(this, "black_blacked", UserImpl.SPECIFICATION);
	}

	public BlackImpl(String inComment) {
		this.black_comment = inComment;
		this.mUser = new SingleAssociationNotNull<Black, User, UserImpl>(this, "black_user", UserImpl.SPECIFICATION);
		this.mBlacked = new SingleAssociationNotNull<Black, User, UserImpl>(this, "black_blacked", UserImpl.SPECIFICATION);
	}

	public BlackImpl(User inUser, User inBlacked, String inComment) {
		this.black_user = inUser.getId();
		this.black_blacked = inBlacked.getId();
		this.black_comment = inComment;

		this.mUser = new SingleAssociationNotNull<Black, User, UserImpl>(this, "black_user", UserImpl.SPECIFICATION);
		this.mBlacked = new SingleAssociationNotNull<Black, User, UserImpl>(this, "black_blacked", UserImpl.SPECIFICATION);
	}

	@Override
	public SQLAssociationSpecification<BlackImpl> getSpecification() {
		return BlackImpl.SPECIFICATION;
	}

	public User getUser() {
		return this.mUser.get(this.black_user);
	}

	public User getBlacked() {
		return this.mBlacked.get(this.black_blacked);
	}

	public String getComment() {
		return this.black_comment;
	}

	public Long getId() {
		return this.black_user;
	}

	public void setComment(String comment) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setBlack_comment(theUpdateMap, comment);
		update(theUpdateMap);
	}

	private void setBlack_comment(Map<String, Object> inUpdateMap, String black_comment) {
		if (!black_comment.equals(this.black_comment)) {
			this.black_comment = black_comment;
			inUpdateMap.put("black_comment", black_comment);
		}
	}
}

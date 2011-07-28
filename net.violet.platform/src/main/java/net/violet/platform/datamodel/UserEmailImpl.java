package net.violet.platform.datamodel;

import java.sql.SQLException;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.platform.datamodel.util.UpdateMap;

public class UserEmailImpl extends ObjectRecord<UserEmail, UserEmailImpl> implements UserEmail {

	public static final SQLObjectSpecification<UserEmailImpl> SPECIFICATION = new SQLObjectSpecification<UserEmailImpl>("user_alternate_email", UserEmailImpl.class, new SQLKey("id"));

	private static final String[] NEW_COLUMNS = new String[] { "user_id", "user_alternate_address" };

	@Override
	public SQLObjectSpecification<UserEmailImpl> getSpecification() {
		return UserEmailImpl.SPECIFICATION;
	}

	protected long id;
	protected long user_id;
	protected String user_alternate_address;

	private final SingleAssociationNotNull<UserEmail, User, UserImpl> user;

	protected UserEmailImpl() {
		this.user = new SingleAssociationNotNull<UserEmail, User, UserImpl>(this, "user_id", UserImpl.SPECIFICATION);
	}

	protected UserEmailImpl(long inId) throws SQLException {
		init(inId);
		this.user = new SingleAssociationNotNull<UserEmail, User, UserImpl>(this, "user_id", UserImpl.SPECIFICATION);
	}

	public UserEmailImpl(User inUser, String email) throws SQLException {
		this.user_id = inUser.getId();
		this.user_alternate_address = email;

		init(UserEmailImpl.NEW_COLUMNS);

		this.user = new SingleAssociationNotNull<UserEmail, User, UserImpl>(this, "user_id", UserImpl.SPECIFICATION);
	}

	public User getUser() {
		return this.user.get(this.user_id);
	}

	public String getAddress() {
		return this.user_alternate_address;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public void setAddress(String inAddress) {
		final UpdateMap updater = new UpdateMap();
		this.user_alternate_address = updater.updateField("user_alternate_address", this.user_alternate_address, inAddress);
		update(updater);
	}
}

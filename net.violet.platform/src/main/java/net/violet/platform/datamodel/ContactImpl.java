package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;

public class ContactImpl extends ObjectRecord<Contact, ContactImpl> implements Contact {

	public static final SQLObjectSpecification<ContactImpl> SPECIFICATION = new SQLObjectSpecification<ContactImpl>("contact", ContactImpl.class, new SQLKey[] { new SQLKey("id") });

	private static final String[] NEW_COLUMNS = new String[] { "person_id", "contact_id", "status", "invitation_date", };

	protected long id;
	protected long person_id;
	protected long contact_id;
	protected String status;
	protected Timestamp invitation_date;

	private final SingleAssociationNotNull<Contact, User, UserImpl> mOwner;

	private final SingleAssociationNotNull<Contact, User, UserImpl> mContact;

	protected ContactImpl() {
		this.mOwner = new SingleAssociationNotNull<Contact, User, UserImpl>(this, "person_id", UserImpl.SPECIFICATION);
		this.mContact = new SingleAssociationNotNull<Contact, User, UserImpl>(this, "contact_id", UserImpl.SPECIFICATION);
	}

	protected ContactImpl(long id) throws NoSuchElementException, SQLException {
		init(id);
		this.mOwner = new SingleAssociationNotNull<Contact, User, UserImpl>(this, "person_id", UserImpl.SPECIFICATION);
		this.mContact = new SingleAssociationNotNull<Contact, User, UserImpl>(this, "contact_id", UserImpl.SPECIFICATION);
	}

	public ContactImpl(User inUser, User inUserContact, Contact.STATUS inStatus) throws SQLException {

		this.person_id = inUser.getId();
		this.contact_id = inUserContact.getId();
		this.status = inStatus.toString();
		this.invitation_date = new Timestamp(System.currentTimeMillis());

		this.mOwner = new SingleAssociationNotNull<Contact, User, UserImpl>(this, "person_id", UserImpl.SPECIFICATION);
		this.mContact = new SingleAssociationNotNull<Contact, User, UserImpl>(this, "contact_id", UserImpl.SPECIFICATION);
		init(ContactImpl.NEW_COLUMNS);
	}

	@Override
	public SQLObjectSpecification<ContactImpl> getSpecification() {
		return ContactImpl.SPECIFICATION;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public User getPerson() {
		return this.mOwner.get(this.person_id);
	}

	public User getContact() {
		return this.mContact.get(this.contact_id);
	}

	public Timestamp getInvitationDate() {
		return this.invitation_date;
	}

	public String getStatus() {
		return this.status;
	}

	public void changeContact(Contact.STATUS inStatus) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setStatus(theUpdateMap, inStatus.toString());
		update(theUpdateMap);
	}

	private void setStatus(Map<String, Object> inUpdateMap, String inValue) {
		if (!inValue.equals(this.status)) {
			this.status = inValue;
			inUpdateMap.put("status", inValue);
		}
	}

}

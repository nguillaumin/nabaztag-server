package net.violet.platform.datamodel.mock;

import java.sql.Timestamp;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Contact;
import net.violet.platform.datamodel.User;

public class ContactMock extends AbstractMockRecord<Contact, ContactMock> implements Contact {

	private String status;
	private final Timestamp invitation_date;
	private final User mOwner;
	private final User mContact;

	public ContactMock(long inId, User inUser, User inContact, Contact.STATUS inStatus) {
		super(inId);
		this.mOwner = inUser;
		this.mContact = inContact;
		this.status = inStatus.toString();
		this.invitation_date = new Timestamp(System.currentTimeMillis());
	}

	public User getContact() {
		return this.mContact;
	}

	public Timestamp getInvitationDate() {
		return this.invitation_date;
	}

	public User getPerson() {
		return this.mOwner;
	}

	public String getStatus() {
		return this.status;
	}

	public void changeContact(STATUS inStatus) {
		this.status = inStatus.toString();
	}

}

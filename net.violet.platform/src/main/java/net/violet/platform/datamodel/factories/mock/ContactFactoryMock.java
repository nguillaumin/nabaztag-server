package net.violet.platform.datamodel.factories.mock;

import java.util.ArrayList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Contact;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.Contact.STATUS;
import net.violet.platform.datamodel.factories.ContactFactory;
import net.violet.platform.datamodel.mock.ContactMock;

public class ContactFactoryMock extends RecordFactoryMock<Contact, ContactMock> implements ContactFactory {

	ContactFactoryMock() {
		super(ContactMock.class);
	}

	private static long DB_ID = 1;

	public long countContactByUser(User inUser) {
		int result = 0;
		for (final Contact theContact : findAllMapped().values()) {
			if (theContact.getPerson().getId().equals(inUser.getId()) && (theContact.getStatus().equals(Contact.STATUS.ACCEPTED.toString()) || theContact.getStatus().equals(Contact.STATUS.AUTOMATICALLY_ACCEPTED.toString()))) {
				result += 1;
			}
		}
		return result;
	}

	public long countReceivedContactRequest(User inUser) {
		int result = 0;
		for (final Contact theContact : findAllMapped().values()) {
			if (theContact.getContact().getId().equals(inUser.getId()) && theContact.getStatus().equals(Contact.STATUS.PENDING.toString())) {
				result += 1;
			}
		}
		return result;
	}

	public long countSentContactRequest(User inUser) {
		int result = 0;
		for (final Contact theContact : findAllMapped().values()) {
			if (theContact.getPerson().getId().equals(inUser.getId()) && (theContact.getStatus().equals(Contact.STATUS.PENDING.toString()) || theContact.getStatus().equals(Contact.STATUS.REJECTED.toString()))) {
				result += 1;
			}
		}
		return result;
	}

	public List<Contact> findAllContactByUser(User inUser, int inSkip, int inGetCount) {

		final List<Contact> theResult = new ArrayList<Contact>();
		for (final Contact theContact : findAllMapped().values()) {
			if (theContact.getPerson().getId().equals(inUser.getId()) && (theContact.getStatus().equals(Contact.STATUS.ACCEPTED.toString()) || theContact.getStatus().equals(Contact.STATUS.AUTOMATICALLY_ACCEPTED.toString()))) {
				theResult.add(theContact);
			}
		}

		return getSkipList(theResult, inSkip, inGetCount);
	}

	public List<Contact> findAllReceivedContactRequest(User inUser, int inSkip, int inGetCount) {
		final List<Contact> theResult = new ArrayList<Contact>();
		for (final Contact theContact : findAllMapped().values()) {
			if (theContact.getContact().getId().equals(inUser.getId()) && theContact.getStatus().equals(Contact.STATUS.PENDING.toString())) {
				theResult.add(theContact);
			}
		}

		return getSkipList(theResult, inSkip, inGetCount);
	}

	public List<Contact> findAllSentContactRequest(User inUser, int inSkip, int inGetCount) {
		final List<Contact> theResult = new ArrayList<Contact>();
		for (final Contact theContact : findAllMapped().values()) {
			if (theContact.getPerson().getId().equals(inUser.getId()) && (theContact.getStatus().equals(Contact.STATUS.PENDING.toString()) || theContact.getStatus().equals(Contact.STATUS.REJECTED.toString()))) {
				theResult.add(theContact);
			}
		}

		return getSkipList(theResult, inSkip, inGetCount);
	}

	public Contact createContact(User inUser, User inContact, STATUS inStatus) {
		return new ContactMock(ContactFactoryMock.DB_ID++, inUser, inContact, inStatus);
	}

	public Contact getContactByUserAndContact(User inUser, User inContact) {
		Contact theResult = null;
		for (final Contact theContact : findAllMapped().values()) {
			if (theContact.getPerson().getId().equals(inUser.getId()) && theContact.getContact().getId().equals(inContact.getId())) {
				theResult = theContact;
			}
		}

		return theResult;
	}

	public List<Contact> findAllWhoHaveMeOnTheirFriendList(User inUser) {
		final List<Contact> theResult = new ArrayList<Contact>();
		for (final Contact theContact : findAllMapped().values()) {
			if (theContact.getContact().getId().equals(inUser.getId())) {
				theResult.add(theContact);
			}
		}
		return theResult;
	}
}

package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Contact;
import net.violet.platform.datamodel.ContactImpl;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.Contact.STATUS;
import net.violet.platform.datamodel.factories.ContactFactory;

import org.apache.log4j.Logger;

public class ContactFactoryImpl extends RecordFactoryImpl<Contact, ContactImpl> implements ContactFactory {

	private static final Logger LOGGER = Logger.getLogger(ContactFactoryImpl.class);

	public ContactFactoryImpl() {
		super(ContactImpl.SPECIFICATION);
	}

	public long countContactByUser(User inUser) {
		return count(null, " person_id = ? and ( status = ? or status = ? ) ", Arrays.asList(new Object[] { inUser.getId(), Contact.STATUS.ACCEPTED.toString(), Contact.STATUS.AUTOMATICALLY_ACCEPTED.toString() }), null);
	}

	public long countReceivedContactRequest(User inUser) {
		return count(null, " contact_id = ? and status = ? ", Arrays.asList(new Object[] { inUser.getId(), Contact.STATUS.PENDING.toString() }), null);
	}

	public long countSentContactRequest(User inUser) {
		return count(null, " person_id = ? and status = ? ", Arrays.asList(new Object[] { inUser.getId(), Contact.STATUS.PENDING.toString() }), null);
	}

	public List<Contact> findAllContactByUser(User inUser, int inSkip, int inGetCount) {
		return findAll(" person_id = ? and ( status = ? or status = ? ) ", Arrays.asList(new Object[] { inUser.getId(), Contact.STATUS.ACCEPTED.toString(), Contact.STATUS.AUTOMATICALLY_ACCEPTED.toString() }), " invitation_date ", inSkip, inGetCount);
	}

	public List<Contact> findAllWhoHaveMeOnTheirFriendList(User inUser) {
		return findAll(" contact_id = ?", Arrays.asList(new Object[] { inUser.getId() }));
	}

	public List<Contact> findAllReceivedContactRequest(User inUser, int inSkip, int inGetCount) {
		return findAll(" contact_id = ? and status = ? ", Arrays.asList(new Object[] { inUser.getId(), Contact.STATUS.PENDING.toString() }), " invitation_date ", inSkip, inGetCount);
	}

	public List<Contact> findAllSentContactRequest(User inUser, int inSkip, int inGetCount) {
		return findAll(" person_id = ? and ( status = ? or status = ? ) ", Arrays.asList(new Object[] { inUser.getId(), Contact.STATUS.PENDING.toString(), Contact.STATUS.REJECTED.toString() }), " invitation_date ", inSkip, inGetCount);
	}

	public Contact createContact(User inUser, User inContact, STATUS inStatus) {
		try {
			return new ContactImpl(inUser, inContact, inStatus);
		} catch (final SQLException e) {
			ContactFactoryImpl.LOGGER.fatal(e, e);
		}
		return null;
	}

	public Contact getContactByUserAndContact(User inUser, User inContact) {
		return find(" person_id = ? and contact_id = ? ", Arrays.asList(new Object[] { inUser.getId(), inContact.getId() }));
	}

}

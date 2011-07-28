package net.violet.platform.dataobjects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.violet.platform.datamodel.Contact;
import net.violet.platform.datamodel.factories.Factories;

public class ContactData extends APIData<Contact> {

	/**
	 * Constructeur à partir d'un contact
	 */

	public ContactData(Contact inContact) {
		super(inContact);
	}

	/**
	 * Accesseur à partir d'un ID application.
	 * 
	 * @return ContactData ou <code>null</code> si le contact n'existe pas.
	 */
	public static ContactData findByAPIId(String inAPIId, String inAPIKey) {

		ContactData theResult = null;
		final long theID = APIData.fromObjectID(inAPIId, ObjectClass.CONTACT, inAPIKey);

		if (theID != 0) {
			final Contact contact = Factories.CONTACT.find(theID);

			if (contact != null) {
				theResult = new ContactData(contact);
			}
		}

		return theResult;
	}

	public static List<ContactData> getContacts(UserData inUserData, int inSkip, int inGetCount) {
		final List<ContactData> theResult = new ArrayList<ContactData>();
		for (final Contact theContact : Factories.CONTACT.findAllContactByUser(inUserData.getRecord(), inSkip, inGetCount)) {
			theResult.add(new ContactData(theContact));
		}

		return theResult;
	}

	public static long getCountContact(UserData inUserData) {
		return Factories.CONTACT.countContactByUser(inUserData.getRecord());
	}

	public static List<ContactData> getReceivedContactRequest(UserData inUserData, int inSkip, int inGetCount) {
		final List<ContactData> theResult = new ArrayList<ContactData>();
		for (final Contact theContact : Factories.CONTACT.findAllReceivedContactRequest(inUserData.getRecord(), inSkip, inGetCount)) {
			theResult.add(new ContactData(theContact));
		}

		return theResult;
	}

	public static long getCountReceivedContactRequest(UserData inUserData) {
		return Factories.CONTACT.countReceivedContactRequest(inUserData.getRecord());
	}

	@Override
	protected ObjectClass getObjectClass() {
		return ObjectClass.CONTACT;
	}

	public static List<ContactData> getSentContactRequest(UserData inUserData, int inSkip, int inGetCount) {
		final List<ContactData> theResult = new ArrayList<ContactData>();
		for (final Contact theContact : Factories.CONTACT.findAllSentContactRequest(inUserData.getRecord(), inSkip, inGetCount)) {
			theResult.add(new ContactData(theContact));
		}

		return theResult;
	}

	public static long getCountSentContactRequest(UserData inUserData) {
		return Factories.CONTACT.countSentContactRequest(inUserData.getRecord());
	}

	public static ContactData createContact(UserData inUser, UserData inContact, Contact.STATUS inStatus) {
		return new ContactData(Factories.CONTACT.createContact(inUser.getRecord(), inContact.getRecord(), inStatus));
	}

	public static ContactData getContactByUserAndContact(UserData inUser, UserData inContact) {
		return new ContactData(Factories.CONTACT.getContactByUserAndContact(inUser.getRecord(), inContact.getRecord()));
	}

	public static boolean deleteContact(ContactData inContactData) {
		boolean theResult = false;
		if (inContactData.isValid()) {
			theResult = inContactData.getRecord().delete();
		}

		return theResult;
	}

	public UserData getPerson() {
		final Contact theRecord = getRecord();
		if (theRecord != null) {
			return UserData.getData(theRecord.getPerson());
		}

		return UserData.getData(null);
	}

	public UserData getContact() {
		final Contact theRecord = getRecord();
		if (theRecord != null) {
			return UserData.getData(theRecord.getContact());
		}

		return UserData.getData(null);
	}

	public String getStatus() {
		final Contact theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getStatus().toLowerCase();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public Date getInvitationDate() {
		final Contact theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getInvitationDate();
		}
		return null;
	}

	public void acceptContact() {
		final Contact theRecord = getRecord();
		if (theRecord != null) {
			theRecord.changeContact(Contact.STATUS.ACCEPTED);
		}
	}

}

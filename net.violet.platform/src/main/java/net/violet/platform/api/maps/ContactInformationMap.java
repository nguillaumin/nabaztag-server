package net.violet.platform.api.maps;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.dataobjects.ContactData;

public class ContactInformationMap extends AbstractAPIMap {

	public ContactInformationMap(APICaller inCaller, ContactData inContact) {
		super(5);

		put("id", inContact.getApiId(inCaller));
		put("person_id", inContact.getPerson().getApiId(inCaller));
		put("contact_id", inContact.getContact().getApiId(inCaller));
		put("status", inContact.getStatus());
		put("date", inContact.getInvitationDate());

	}

}

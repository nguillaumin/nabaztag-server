package net.violet.platform.dataobjects;

import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.StringShop;

public class BlackData extends UserData {

	private final String black_comment;

	public BlackData(User user, String black_comment) {
		super(user);
		this.black_comment = black_comment;
	}

	public String getBlack_comment() {
		if (this.black_comment != null) {
			return this.black_comment;
		}

		return StringShop.EMPTY_STRING;
	}

	/**
	 * Uses this method to black the inBlack user for the inUser user. This
	 * method does check if the black user is in the main user's contacts, if
	 * he/she is the contact information is first deleted.
	 * 
	 * @param inUser
	 * @param inBlack
	 */
	public static void addToBlackList(UserData inUser, UserData inBlack) {

		final ContactData contact = ContactData.getContactByUserAndContact(inUser, inBlack);
		if (contact != null) {
			ContactData.deleteContact(contact);
		}

		Factories.BLACK.createNewBlack(inUser.getReference(), inBlack.getReference());

	}

	public static void removeFromBlackList(UserData inUser, UserData inBlack) {
		Factories.BLACK.removeFromBlackList(inUser.getReference(), inBlack.getReference());
	}

}

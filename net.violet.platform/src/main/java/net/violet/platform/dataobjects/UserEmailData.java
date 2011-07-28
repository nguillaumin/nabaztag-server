package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.violet.platform.datamodel.UserEmail;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class UserEmailData extends RecordData<UserEmail> {

	private static final Logger LOGGER = Logger.getLogger(UserEmailData.class);

	public static UserEmailData getData(UserEmail inUserEmail) {
		try {
			return RecordData.getData(inUserEmail, UserEmailData.class, UserEmail.class);
		} catch (final InstantiationException e) {
			UserEmailData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			UserEmailData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			UserEmailData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			UserEmailData.LOGGER.fatal(e, e);
		}

		return null;
	}

	/**
	 * Construct a UserData container USED BY REFLECTION
	 */
	protected UserEmailData(UserEmail inUserEmail) {
		super(inUserEmail);
	}

	public String getAddress() {
		final UserEmail theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getAddress() != null)) {
			return theRecord.getAddress();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public static List<UserEmailData> findByUser(UserData inUser) {
		return UserEmailData.generateList(Factories.USER_EMAIL.findAllByUser(inUser.getRecord()));
	}

	private static List<UserEmailData> generateList(Collection<UserEmail> inEmails) {
		final List<UserEmailData> resultList = new ArrayList<UserEmailData>();
		for (final UserEmail email : inEmails) {
			resultList.add(new UserEmailData(email));
		}

		return resultList;
	}

	public static boolean isAddressAvailableForUser(UserData inUser, String inAddress) {
		final UserEmailData theEmail = UserEmailData.findByAddress(inAddress);
		return (theEmail == null) || theEmail.isValid() || inUser.equals(theEmail.getUser());
	}

	public static UserEmailData findByAddress(String inAddress) {
		final UserEmail theEmail = Factories.USER_EMAIL.findByAddress(inAddress);
		if (theEmail != null) {
			return UserEmailData.getData(theEmail);
		}

		return null;
	}

	public UserData getUser() {
		final UserEmail record = getRecord();
		if (record != null) {
			return UserData.getData(record.getUser());
		}

		return null;
	}

	public static void setAddressesForUser(UserData inUser, List<String> inAddresses) {
		final List<String> theEmailsToAdd = new ArrayList<String>(inAddresses);
		final List<UserEmailData> theExistingEmails = UserEmailData.findByUser(inUser);

		for (final Iterator<UserEmailData> theIterator = theExistingEmails.iterator(); theIterator.hasNext();) {
			final String address = theIterator.next().getAddress();
			if (theEmailsToAdd.contains(address)) {
				theEmailsToAdd.remove(address);
				theIterator.remove();

			}
		}

		// At this point theEmailsToAdd only contains the new addresses and theExistingEmails the addresses to remove

		for (final String anEmailToAdd : theEmailsToAdd) {
			if (!theExistingEmails.isEmpty()) {
				theExistingEmails.remove(0).setAddress(anEmailToAdd);
			} else {
				UserEmailData.createForUser(inUser, anEmailToAdd);
			}
		}

		for (final UserEmailData emailToRemove : theExistingEmails) {
			emailToRemove.delete();
		}

	}

	public static UserEmailData createForUser(UserData inUser, String inAddress) {
		return UserEmailData.getData(Factories.USER_EMAIL.create(inUser.getRecord(), inAddress));

	}

	public void setAddress(String inAddress) {
		final UserEmail record = getRecord();
		if (record != null) {
			record.setAddress(inAddress);
		}

	}

}

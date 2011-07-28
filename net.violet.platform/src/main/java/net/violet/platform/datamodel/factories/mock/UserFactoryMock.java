package net.violet.platform.datamodel.factories.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Annu;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.UserFactory;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.dataobjects.AnnuData;
import net.violet.platform.util.CCalendar;

public class UserFactoryMock extends RecordFactoryMock<User, UserMock> implements UserFactory {

	UserFactoryMock() {
		super(UserMock.class);
	}

	public User findByEmail(String email) {
		User theResult = null;
		for (final User theUser : findAllMapped().values()) {
			if (email.equals(theUser.getUser_email())) {
				theResult = theUser;
				break;
			}
		}
		return theResult;
	}

	public User createNewUser(String inEmail, String inPassword, Lang inLang, String inCountry, String inFirstName, String inLastName, long inTimeZone) {
		return new UserMock(0, inEmail, inPassword, inEmail, inLang, inCountry, inFirstName, inLastName, Factories.TIMEZONE.find(inTimeZone));
	}

	public List<User> searchUsers(String inFirstName, String inLastName, Integer olderThan, Integer youngerThan, AnnuData.Gender gender, String theCity, String theCountry, int skip, int count) {
		final List<User> theResult = new ArrayList<User>();

		final List<User> theValues = new ArrayList<User>(findAllMapped().values());
		Collections.sort(theValues, new Comparator<User>() {

			public int compare(User o1, User o2) {
				return o1.getUser_email().compareTo(o2.getUser_email());
			}
		});

		for (final User aUser : getSkipList(theValues, skip, count)) {
			final Annu theAnnu = aUser.getAnnu();
			final String firstName = theAnnu.getAnnu_prenom();
			final String lastName = theAnnu.getAnnu_nom();

			if (theAnnu.getAnnu_confirm() != 1) {
				continue;
			}

			if (((firstName == null) || firstName.equals(net.violet.common.StringShop.EMPTY_STRING)) && ((lastName == null) || lastName.equals(net.violet.common.StringShop.EMPTY_STRING))) {
				continue;
			}

			if ((inFirstName != null) && !inFirstName.equals(firstName)) {
				continue;
			}
			if ((inLastName != null) && !inLastName.equals(lastName)) {
				continue;
			}

			final CCalendar now = new CCalendar(true);
			if (olderThan != null) {
				now.add(Calendar.YEAR, -olderThan);
				if (now.getTime().before(theAnnu.getAnnu_datebirth())) {
					continue;
				}
				now.add(Calendar.YEAR, olderThan);
			}

			if (youngerThan != null) {
				now.add(Calendar.YEAR, -youngerThan);
				if (now.getTime().after(theAnnu.getAnnu_datebirth())) {
					continue;
				}
			}

			if (gender != null) {
				if (gender == AnnuData.Gender.UNKNOWN) { // we look for people with unknon gender
					if (theAnnu.getAnnu_sexe() != null) {
						continue;
					}

				} else if (!gender.getCodeAnnu().equals(theAnnu.getAnnu_sexe())) {
					continue;
				}

			}

			if ((theCity != null) && !theCity.equals(theAnnu.getAnnu_city())) {
				continue;
			}

			if ((theCountry != null) && !theCountry.equals(theAnnu.getAnnu_country())) {
				continue;
			}

			theResult.add(aUser);
		}

		return theResult;
	}

	public boolean updateMyLogin(String oldLogin, String newLogin) {
		return true;
	}

	public List<User> findAll(int theIndex) {
		return Collections.emptyList();
	}
}

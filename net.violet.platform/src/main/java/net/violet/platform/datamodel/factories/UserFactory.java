package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.dataobjects.AnnuData;

public interface UserFactory extends RecordFactory<User> {

	/**
	 * Finds a User according to the provided email address.
	 * 
	 * @param email the email address
	 * @return the User object, null if there is none.
	 */
	User findByEmail(String email);

	/**
	 * Creates a new user according to the provided parameters. This method does
	 * NOT check if the given email address already exists in the database.
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	User createNewUser(String inEmail, String inPassword, Lang inLang, String inCountry, String inFirstName, String inLastName, long inTimeZone);

	/**
	 * Searchs the users matching the provided parameters. Only parameters which are not null are used. A User object is in the returned list only if it
	 * matches all the none-null parameters and if it is visible. NB : nowadays, the annu_confirm field is used to know if a user is visible or not. The
	 * returned list is ordered by last name.
	 * 
	 * NB : if a user left his/her first name AND last name empty he/she will not be added to the returned list.
	 * 
	 * @param theFirstName
	 * @param theLastName
	 * @param olderThan
	 * @param youngerThan
	 * @param gender
	 * @param theCity
	 * @param theCountry
	 * @param skip
	 * @param count
	 * @return
	 */
	List<User> searchUsers(String theFirstName, String theLastName, Integer olderThan, Integer youngerThan, AnnuData.Gender gender, String theCity, String theCountry, int skip, int count);

	List<User> findAll(int theIndex);
}

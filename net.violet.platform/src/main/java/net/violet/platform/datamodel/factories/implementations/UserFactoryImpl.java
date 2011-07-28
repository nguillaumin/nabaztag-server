package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.UserImpl;
import net.violet.platform.datamodel.factories.UserFactory;
import net.violet.platform.dataobjects.AnnuData;
import net.violet.platform.util.CCalendar;

import org.apache.log4j.Logger;

public class UserFactoryImpl extends RecordFactoryImpl<User, UserImpl> implements UserFactory {

	private static final Logger LOGGER = Logger.getLogger(UserFactoryImpl.class);

	public UserFactoryImpl() {
		super(UserImpl.SPECIFICATION);
	}

	public User findByEmail(String email) {
		return find(" user_email = ? ", Arrays.asList(new Object[] { email }));
	}

	public User createNewUser(String inEmail, String inPassword, Lang inLang, String inCountry, String inFirstName, String inLastName, long inTimeZone) {
		try {
			return new UserImpl(inPassword, inEmail, inLang, inCountry, inFirstName, inLastName, inTimeZone);
		} catch (final SQLException e) {
			UserFactoryImpl.LOGGER.fatal(e, e);
			return null;
		}
	}

	public List<User> searchUsers(String theFirstName, String theLastName, Integer olderThan, Integer youngerThan, AnnuData.Gender gender, String theCity, String theCountry, int skip, int count) {

		final StringBuilder theCondition = new StringBuilder(" user_id = annu_user and annu_confirm = 1  AND ( (annu_prenom IS NOT NULL and annu_prenom != '') OR (annu_nom IS NOT NULL and annu_nom != '' ) ) ");
		final String[] joinTables = { "annu" };

		// TODO caution : firstName and lastName fields are also present in the user table but seem useless.
		if (theFirstName != null) {
			theCondition.append(" and annu_prenom like '" + theFirstName + "%' ");
		}

		if (theLastName != null) {
			theCondition.append(" and annu_nom like '" + theLastName + "%' ");
		}

		final CCalendar now = new CCalendar(true);

		if (olderThan != null) {
			now.add(Calendar.YEAR, -olderThan);
			theCondition.append(" and annu_datebirth < '" + now.getYear() + "-" + now.getMonth() + "-" + now.getDay() + "' ");
			now.add(Calendar.YEAR, olderThan);
		}

		if (youngerThan != null) {
			now.add(Calendar.YEAR, -youngerThan);
			theCondition.append(" and annu_datebirth > '" + now.getYear() + "-" + now.getMonth() + "-" + now.getDay() + "'");
		}

		if (gender != null) {
			if (gender == AnnuData.Gender.UNKNOWN) {
				theCondition.append(" and annu_sexe is null ");
			} else {
				theCondition.append(" and annu_sexe = '" + gender.getCodeAnnu() + "' ");
			}
		}

		if (theCity != null) {
			theCondition.append(" and annu_city = '" + theCity + "' ");
		}

		if (theCountry != null) {
			theCondition.append(" and annu_country = '" + theCountry + "' ");
		}

		return findAll(joinTables, theCondition.toString(), null, "user_email", skip, count);
	}

	public List<User> findAll(int theIndex) {
		return findAll("1 = 1", null, "user_id", theIndex, 5000);
	}
}

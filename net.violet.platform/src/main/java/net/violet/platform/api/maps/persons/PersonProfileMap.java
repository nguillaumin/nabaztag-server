package net.violet.platform.api.maps.persons;

import java.sql.Date;
import java.util.TimeZone;

import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.api.maps.PictureInformationMap;
import net.violet.platform.dataobjects.AnnuData;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.CCalendar;

public class PersonProfileMap extends AbstractAPIMap {

	public static final String FIRST_NAME = "first_name";
	public static final String LAST_NAME = "last_name";

	public PersonProfileMap(UserData inUser) {

		final AnnuData annu = inUser.getAnnu();

		if (annu.isEmpty()) {
			put("first_name", null);
			put("last_name", null);
			put("birth_date", null);

			put("gender", null);

			put("city", null);
			put("zip_code", null);
			put("country", null);
			put("description", null);

			put("picture", null);
			put("modification_date", null);

		} else {
			put("first_name", annu.getFirstName());
			put("last_name", annu.getLastName());
			put("birth_date", inUser.getBirthDate());

			put("gender", AnnuData.Gender.getValue(inUser.getAnnu_sexe()).getProfileCode());

			put("city", inUser.getAnnu_city());
			put("zip_code", inUser.getAnnu_zip());
			put("country", inUser.getPays_nom());
			put("description", inUser.getAnnu_desc());

			final FilesData thePictureFiles = inUser.getAnnu_pictureFiles();
			if ((thePictureFiles != null) && thePictureFiles.isValid()) {
				put("picture", new PictureInformationMap(thePictureFiles));
			} else {
				putNullValue("picture");
			}

			final CCalendar theCalendar = new CCalendar(annu.getUpdateTime().getTime(), TimeZone.getTimeZone("UTC"));
			put("modification_date", new Date(theCalendar.getTimeInMillis()));
		}

	}

}

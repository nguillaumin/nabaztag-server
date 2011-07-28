package net.violet.platform.admin.stats;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.violet.common.StringShop;
import net.violet.db.connector.Connector.ConnectionMode;
import net.violet.db.records.SgbdConnection;
import net.violet.db.records.SgbdResult;
import net.violet.db.records.SgbdConnection.SGBD_ACCESS;
import net.violet.platform.datamodel.Hardware;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.MailTools;

public class StatsGenerator {

	private static String FILE_PATH = "/usr/local/violet/rsc/admin/stats_DATE.csv";
	private static String RECIPIENT = "franck@violet.net";

	private static final String TIME_48H = "172800";
	private static final String TIME_7D = "12614400";
	private static final Map<String, String> SCHEDULE = new LinkedHashMap<String, String>();
	private static final Map<String, String> HARDWARE = new LinkedHashMap<String, String>();

	static {
		new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		StatsGenerator.SCHEDULE.put("15DAYS", "1296000");
		StatsGenerator.SCHEDULE.put("1MONTH", "2592000");
		StatsGenerator.SCHEDULE.put("2MONTH", "5184000");
		StatsGenerator.SCHEDULE.put("3MONTH", "7776000");
		StatsGenerator.SCHEDULE.put("6MONTH", "15811200");
		StatsGenerator.SCHEDULE.put("1YEAR", "31536000");

		StatsGenerator.HARDWARE.put("TAG", Hardware.HARDWARE.V1.getId().toString());
		StatsGenerator.HARDWARE.put("TAGTAG", Hardware.HARDWARE.V2.getId().toString());
		StatsGenerator.HARDWARE.put("NANO", Hardware.HARDWARE.NANOZTAG.getId().toString());
		StatsGenerator.HARDWARE.put("ZTAMP", Hardware.HARDWARE.ZTAMP.getId().toString());
		StatsGenerator.HARDWARE.put("BKZ", Hardware.HARDWARE.BOOK.getId().toString());
		StatsGenerator.HARDWARE.put("RFID", Hardware.HARDWARE.OTHER_RFID.getId().toString());
	}

	//PROFILS
	private static final String PFL_TAG = "SELECT count(object_id) FROM (SELECT object_id FROM object WHERE object_hardware = ? GROUP BY object_owner HAVING count(object_owner) = 1 ) AS nb_user_with_only_one";
	private static final String PFL_TAGS = "SELECT count(object_id) FROM (SELECT object_id FROM object WHERE object_hardware = ? GROUP BY object_owner HAVING count(object_owner) > 1 ) AS nb_user_with_more_than_one_";

	//USER
	private static final String USR_TOTAL = "SELECT count(user_id) FROM user";
	private static final String USR_NO_OBJ = "SELECT count(user_id) FROM user WHERE user_id NOT IN (SELECT object_owner FROM object);";
	private static final String USR_OBJ = "SELECT count(user_id)  FROM user  WHERE user_id IN ( SELECT object_owner FROM object );";
	private static final String USR_TAG_TAGTAG_ALIVE = "SELECT count( DISTINCT (object_owner) ) FROM object WHERE (object_hardware = ? OR object_hardware = ?) AND object_lastping > (SELECT UNIX_TIMESTAMP() - ?)";
	private static final String USR_MIR_ZTA_NANO_BKZ_RFID_ALIVE = "SELECT count( DISTINCT (object_owner) ) FROM object WHERE (object_hardware = ? OR object_hardware = ? OR object_hardware = ? OR object_hardware = ? OR object_hardware = ?) AND object_lastping > (SELECT UNIX_TIMESTAMP() - ?)";
	private static final String USR_ACCOUNT_LESS_48 = "SELECT count(user_id) FROM user WHERE user_creationDate > (SELECT UNIX_TIMESTAMP() - ?)";
	private static final String USR_ACCOUNT_MORE_ = "SELECT count(user_id) FROM user WHERE user_creationDate < (SELECT UNIX_TIMESTAMP() - ?)";
	private static final String USR_CONTACT_2MORE = "SELECT count(nb_contact) FROM ( SELECT count(contact_id) AS nb_contact	FROM contact WHERE status IN ('ACCEPTED','AUTOMATICALLY_ACCEPTED') GROUP BY person_id HAVING nb_contact > 2) AS nb_tot";

	//MESSAGE
	private static final String MSG_TOTAL = "SELECT count(id) FROM stats_message WHERE time > (SELECT SUBDATE(CURRENT_TIMESTAMP(), INTERVAL 1 DAY))";
	private static final String MSG_TAG_ = "SELECT count(id) FROM stats_message WHERE hardware_id = ? AND time > (SELECT SUBDATE(CURRENT_TIMESTAMP(), INTERVAL 1 DAY))";

	//APPLICATION
	private static final String APP = "SELECT count(application_id) FROM subscription, object WHERE subscription.object_id = object.object_id AND object_hardware = ?";
	private static final String APP_ = "SELECT application_name, count(application.application_id) FROM application, subscription, object	WHERE application.application_id = subscription.application_id	AND object_hardware = ?	AND subscription.object_id = object.object_id GROUP BY application.application_id ORDER BY count(application.application_id ) DESC LIMIT 15";

	//OBJECT
	private static final String OBJ_OBJECT_TOTAL = "SELECT count(object_id) FROM object WHERE object_hardware = ?";
	private static final String OBJ_OBJECT_LESS_ = "SELECT count(object_id) FROM object WHERE object_hardware = ? AND object_lastping > (SELECT UNIX_TIMESTAMP() - ?)";
	private static final String OBJ_OBJECT_MORE_ = "SELECT count(object_id) FROM object WHERE object_hardware = ? AND object_lastping < (SELECT UNIX_TIMESTAMP() - ?)";

	static int PFL_TAG_TAGTAG = 0;
	static int PFL_TAGTAG_MIR_ZTA = 0;
	static int PFL_MIR = 0;
	static int PFL_MIRS = 0;
	static int PFL_TAGTAG_ZTA = 0;
	static int PFL_NANOS = 0;

	/**
	 * Generate the stats data and record into a file
	 * @param arg
	 */
	public static void main(String[] arg) {

		final Map<String, String> dataToWrite = new LinkedHashMap<String, String>();
		final SgbdConnection theConnection = new SgbdConnection();
		final SgbdConnection theConnectionStats = new SgbdConnection(ConnectionMode.STATS, SGBD_ACCESS.READONLY);
		List<Object> theValues;

		try {

			// Profils
			final long start = System.currentTimeMillis();
			int size = 1;
			int nbRows = 0;
			while (size > 0) {
				final List<User> theUsers = Factories.USER.findAll(nbRows);
				for (final User aUser : theUsers) {
					boolean isV1 = false;
					boolean isV2 = false;
					boolean isMirror = false;
					boolean isZtamp = false;
					boolean isNano = false;
					boolean isRFID = false;
					int nbMirror = 0;
					int nbNano = 0;

					for (final VObject anObject : Factories.VOBJECT.findByOwner(aUser)) {
						if (Hardware.HARDWARE.V1.equals(anObject.getHardware())) {
							isV1 = true;
						} else if (Hardware.HARDWARE.V2.equals(anObject.getHardware())) {
							isV2 = true;
						} else if (Hardware.HARDWARE.MIRROR.equals(anObject.getHardware())) {
							isMirror = true;
							nbMirror++;
						} else if (Hardware.HARDWARE.ZTAMP.equals(anObject.getHardware())) {
							isZtamp = true;
						} else if (Hardware.HARDWARE.NANOZTAG.equals(anObject.getHardware())) {
							isNano = true;
							nbNano++;
						} else if (Hardware.HARDWARE.OTHER_RFID.equals(anObject.getHardware())) {
							isRFID = true;
						}
					}
					if (isV1 && isV2) {
						StatsGenerator.PFL_TAG_TAGTAG++;
					}
					if (isV2 && isMirror && isZtamp) {
						StatsGenerator.PFL_TAGTAG_MIR_ZTA++;
					}
					if ((nbMirror == 1) && (isZtamp || isNano || isRFID)) {
						StatsGenerator.PFL_MIR++;
					}
					if ((nbMirror > 1) && (isZtamp || isNano || isRFID)) {
						StatsGenerator.PFL_MIRS++;
					}
					if (isV2 && (isZtamp || isNano || isRFID)) {
						StatsGenerator.PFL_TAGTAG_ZTA++;
					}
					if (nbNano > 1) {
						StatsGenerator.PFL_NANOS++;
					}

				}
				size = theUsers.size();
				nbRows += 5000;
				System.err.println("NB USERS PROCESSED = " + nbRows);
			}
			final long middle = System.currentTimeMillis();
			final long total = (middle - start) / (1000 * 60);
			System.err.println("--------------->middle time = " + total + " minutes");

			// Profil data
			theValues = Arrays.asList(new Object[] { Hardware.HARDWARE.V1.getId() });
			final int userWith1Tag = theConnection.doQueryPS(StatsGenerator.PFL_TAG, theValues);
			dataToWrite.put("PFL_TAG", String.valueOf(userWith1Tag));

			theValues = Arrays.asList(new Object[] { Hardware.HARDWARE.V2.getId() });
			final int userWith1Tagtag = theConnection.doQueryPS(StatsGenerator.PFL_TAG, theValues);
			dataToWrite.put("PFL_TAGTAG", String.valueOf(userWith1Tagtag));

			theValues = Arrays.asList(new Object[] { Hardware.HARDWARE.V1.getId() });
			final int userWithNTag = theConnection.doQueryPS(StatsGenerator.PFL_TAGS, theValues);
			dataToWrite.put("PFL_TAGS", String.valueOf(userWithNTag));

			theValues = Arrays.asList(new Object[] { Hardware.HARDWARE.V2.getId() });
			final int userWithNTagtag = theConnection.doQueryPS(StatsGenerator.PFL_TAGS, theValues);
			dataToWrite.put("PFL_TAGTAGS", String.valueOf(userWithNTagtag));

			dataToWrite.put("PFL_TAG_TAGTAG", String.valueOf(StatsGenerator.PFL_TAG_TAGTAG));
			dataToWrite.put("PFL_TAGTAG_MIR_ZTA", String.valueOf(StatsGenerator.PFL_TAGTAG_MIR_ZTA));
			dataToWrite.put("PFL_MIR", String.valueOf(StatsGenerator.PFL_MIR));
			dataToWrite.put("PFL_MIRS", String.valueOf(StatsGenerator.PFL_MIRS));
			dataToWrite.put("PFL_TAGTAG_ZTA", String.valueOf(StatsGenerator.PFL_TAGTAG_ZTA));
			dataToWrite.put("PFL_NANOS", String.valueOf(StatsGenerator.PFL_NANOS));

			// User data
			final int nbUsers = theConnection.doQueryIntV(StatsGenerator.USR_TOTAL);
			dataToWrite.put("USR_TOTAL", String.valueOf(nbUsers));

			final int nbUsersWithoutObject = theConnection.doQueryIntV(StatsGenerator.USR_NO_OBJ);
			dataToWrite.put("USR_NO_OBJ", String.valueOf(nbUsersWithoutObject));

			final int nbUsersWithObject = theConnection.doQueryIntV(StatsGenerator.USR_OBJ);
			dataToWrite.put("USR_OBJ", String.valueOf(nbUsersWithObject));

			theValues = Arrays.asList(new Object[] { Hardware.HARDWARE.V1.getId(), Hardware.HARDWARE.V2.getId(), StatsGenerator.TIME_48H });
			final int nbUsersTagTagtagAlive = theConnection.doQueryPS(StatsGenerator.USR_TAG_TAGTAG_ALIVE, theValues);
			dataToWrite.put("USR_TAG_TAGTAG_ALIVE", String.valueOf(nbUsersTagTagtagAlive));

			theValues = Arrays.asList(new Object[] { Hardware.HARDWARE.MIRROR.getId(), Hardware.HARDWARE.ZTAMP.getId(), Hardware.HARDWARE.NANOZTAG.getId(), Hardware.HARDWARE.BOOK.getId(), Hardware.HARDWARE.OTHER_RFID.getId(), StatsGenerator.TIME_7D });
			final int nbUsersOthersProducts = theConnection.doQueryPS(StatsGenerator.USR_MIR_ZTA_NANO_BKZ_RFID_ALIVE, theValues);
			dataToWrite.put("USR_MIR_ZTA_NANO_BKZ_RFID_ALIVE", String.valueOf(nbUsersOthersProducts));

			theValues = Arrays.asList(new Object[] { StatsGenerator.TIME_48H });
			final int nbUsersAccountLess48H = theConnection.doQueryPS(StatsGenerator.USR_ACCOUNT_LESS_48, theValues);
			dataToWrite.put("USR_ACCOUNT_LESS_48", String.valueOf(nbUsersAccountLess48H));

			theValues = Arrays.asList(new Object[] { StatsGenerator.TIME_48H });
			final int nbUsersAccountMoreThan48 = theConnection.doQueryPS(StatsGenerator.USR_ACCOUNT_MORE_, theValues);
			dataToWrite.put("USR_ACCOUNT_MORE_" + "48", String.valueOf(nbUsersAccountMoreThan48));

			for (final Map.Entry<String, String> anEntry : StatsGenerator.SCHEDULE.entrySet()) {
				theValues = Arrays.asList(new Object[] { anEntry.getValue() });
				final int nbUsersAccountMoreThanX = theConnection.doQueryPS(StatsGenerator.USR_ACCOUNT_MORE_, theValues);
				dataToWrite.put("USR_ACCOUNT_MORE_" + anEntry.getKey(), String.valueOf(nbUsersAccountMoreThanX));
			}

			final int nbUsersWithAtLeast2Contact = theConnection.doQueryIntV(StatsGenerator.USR_CONTACT_2MORE);
			dataToWrite.put("USR_CONTACT_2MORE", String.valueOf(nbUsersWithAtLeast2Contact));

			// Messages
			final int nbMsg24H = theConnectionStats.doQueryIntV(StatsGenerator.MSG_TOTAL);
			dataToWrite.put("MSG_TOTAL", String.valueOf(nbMsg24H));

			for (final Map.Entry<String, String> anEntry : StatsGenerator.HARDWARE.entrySet()) {
				theValues = Arrays.asList(new Object[] { anEntry.getValue() });
				final int nbMsgPerProducts = theConnectionStats.doQueryPS(StatsGenerator.MSG_TAG_, theValues);
				dataToWrite.put("MSG_TAG_" + anEntry.getKey(), String.valueOf(nbMsgPerProducts));
			}

			//Application
			for (final Map.Entry<String, String> anEntry : StatsGenerator.HARDWARE.entrySet()) {
				theValues = Arrays.asList(new Object[] { anEntry.getValue() });
				final int NbAppRecorded = theConnection.doQueryPS(StatsGenerator.APP, theValues);
				dataToWrite.put("APP_" + anEntry.getKey(), String.valueOf(NbAppRecorded));

				final SgbdResult mostPopularApp = theConnection.doQueryPS(StatsGenerator.APP_, theValues, 0);
				int i = 0;
				if (mostPopularApp != null) {
					final ResultSet theResulset = mostPopularApp.getResultSet();
					while (theResulset.next()) {
						dataToWrite.put("APP_" + anEntry.getKey() + "_" + i++, theResulset.getString(1) + ";" + theResulset.getString(2));
					}
					theResulset.close();
				}
			}

			//Objects
			StatsGenerator.HARDWARE.put("MIRROR", Hardware.HARDWARE.MIRROR.getId().toString());
			for (final Map.Entry<String, String> anEntry : StatsGenerator.HARDWARE.entrySet()) {

				final String delay = (anEntry.getKey().equals("TAG") || anEntry.getKey().equals("TAGTAG")) ? StatsGenerator.TIME_48H : StatsGenerator.TIME_7D;
				final String time = (anEntry.getKey().equals("TAG") || anEntry.getKey().equals("TAGTAG")) ? "48" : "7DAYS";

				theValues = Arrays.asList(new Object[] { anEntry.getValue() });
				final int NbObjRegistered = theConnection.doQueryPS(StatsGenerator.OBJ_OBJECT_TOTAL, theValues);
				dataToWrite.put("OBJ_OBJECT_TOTAL".replace("OBJECT", anEntry.getKey()), String.valueOf(NbObjRegistered));

				theValues = Arrays.asList(new Object[] { anEntry.getValue(), delay });
				final int NbObjLess = theConnection.doQueryPS(StatsGenerator.OBJ_OBJECT_LESS_, theValues);
				dataToWrite.put("OBJ_OBJECT_LESS_".replace("OBJECT", anEntry.getKey()) + time, String.valueOf(NbObjLess));

				theValues = Arrays.asList(new Object[] { anEntry.getValue(), delay });
				final int nbObjectMore48or7 = theConnection.doQueryPS(StatsGenerator.OBJ_OBJECT_MORE_, theValues);
				dataToWrite.put("OBJ_OBJECT_MORE_".replace("OBJECT", anEntry.getKey()) + time, String.valueOf(nbObjectMore48or7));

				for (final Map.Entry<String, String> aSchedule : StatsGenerator.SCHEDULE.entrySet()) {
					theValues = Arrays.asList(new Object[] { anEntry.getValue(), aSchedule.getValue() });
					final int nbObjectMore = theConnection.doQueryPS(StatsGenerator.OBJ_OBJECT_MORE_, theValues);
					dataToWrite.put("OBJ_OBJECT_MORE_".replace("OBJECT", anEntry.getKey()) + aSchedule.getKey(), String.valueOf(nbObjectMore));
				}
			}
			System.err.println("--------------->OWARI !!");
			final long end = System.currentTimeMillis();
			final long totalEnd = (end - start) / (1000 * 60);
			System.err.println("--------------->total time = " + totalEnd + " minutes");
		} catch (final Exception e) {
			System.err.println("Woooooooups !!" + e.getMessage());
		}

		theConnection.close();
		theConnectionStats.close();

		// create file
		final String creationDate = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()).replace("/", "_");
		final String CSVfile = StatsGenerator.FILE_PATH.replace("DATE", creationDate);
		System.err.println("FILE = " + CSVfile);
		final File finalFile = new File(CSVfile);
		if (finalFile.exists()) {
			finalFile.delete();
		}

		FileWriter writer = null;
		String line = StringShop.EMPTY_STRING;
		try {
			writer = new FileWriter(CSVfile, true);

			for (final Map.Entry<String, String> aSet : dataToWrite.entrySet()) {
				line = aSet.getKey() + ";" + aSet.getValue();
				writer.write(line + "\n");
			}
		} catch (final IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (final IOException e) {}
		}
		MailTools.sendFromStats(StatsGenerator.RECIPIENT, "ADMIN - STATS (" + creationDate + ") ", "", CSVfile);

		System.exit(0);
	}

	private static long CACHE_TIME = 0;
	private static String NABAZTAG_STATS_RESULT = "";
	private static final String HUMMM = "...";
	//syc 26 juillet 2010 // private static final String KEY = "81b91b5e3cf46ae596635087adb5f68b";
	private static final String KEY = "81b91b5e3cf46ae596635087adb5f68z";

	private static final String V1_ONLINE_TOTAL = "SELECT count(object_id) FROM object WHERE object_hardware = " + Hardware.HARDWARE.V1.getId() + " and object_mode=" + VObject.MODE_PING;
	private static final String V2_ONLINE_TOTAL = "SELECT count(object_id) FROM object WHERE object_hardware = " + Hardware.HARDWARE.V2.getId() + " and object_mode=" + VObject.MODE_XMPP;

	public static String getNabaztagOnline(String inKey) {
		final String result;
		if (StatsGenerator.KEY.equals(inKey)) {
			if (StatsGenerator.CACHE_TIME < (System.currentTimeMillis() - (3600000 * 4))) {
				final SgbdConnection theConnection = new SgbdConnection();
				final int V1 = theConnection.doQueryIntV(StatsGenerator.V1_ONLINE_TOTAL);
				final int V2 = theConnection.doQueryIntV(StatsGenerator.V2_ONLINE_TOTAL);
				theConnection.close();
				StatsGenerator.NABAZTAG_STATS_RESULT = "V1:" + V1 + ";V2:" + V2;
				StatsGenerator.CACHE_TIME = System.currentTimeMillis();
			}
			result = StatsGenerator.NABAZTAG_STATS_RESULT;
		} else {
			result = StatsGenerator.HUMMM;
		}
		return result;
	}
}

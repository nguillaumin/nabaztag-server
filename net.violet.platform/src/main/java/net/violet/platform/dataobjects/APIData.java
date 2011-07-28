package net.violet.platform.dataobjects;

import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.violet.common.StringShop;
import net.violet.common.utils.DigestTools;
import net.violet.db.records.Record;
import net.violet.platform.api.callers.APICaller;

public abstract class APIData<T extends Record<T>> extends RecordData<T> {

	private static final String SECRET_SESAME = "e7c3e19686aa5503a900cb903e9875fb";
	private static final String VORTEX_DOOR = "bdc1d6d7eec4d5ab155b1916472c87dd";
	private static final Pattern API_ID_REGEX = Pattern.compile("([a-f0-9]+)([A-Z]{1,2})([a-f0-9]{8})");

	protected APIData(T inRecord) {
		super(inRecord);
	}

	protected enum ObjectClass {

		APPLICATION("A"),
		APPLICATION_TAG("T"),
		APPLICATION_CATEGORIE("AC"),
		ANIM("AI"),
		CONTACT("C"),
		DICO("D"),
		FILES("F"),
		GROUP("G"),
		HINT("H"),
		MESSAGE("M"),
		MUSIC("MU"),
		NEWS("NE"),
		PRESS("PE"),
		STORE("SE"),
		SUBSCRIPTION("S"),
		USER("U"),
		VOBJECT("O"),
		NABCAST_RESOURCE("NR"),
		APPLICATION_CONTENT("CT"),
		NOTIFICATION("NO");

		private final String id;

		private ObjectClass(String inID) {
			this.id = inID;
		}

		public String getId() {
			return this.id;
		}

	}

	protected abstract ObjectClass getObjectClass();

	public String getApiId(APICaller caller) {
		return getApiId(caller.getAPIKey());
	}

	public String getApiId(String callerApiKey) {
		final T theRecord = getRecord();
		if (theRecord != null) {
			final String hexObjectID = Long.toHexString(theRecord.getId());
			final String classId = getObjectClass().getId();
			final char[] md5IDSignature = DigestTools.digest(hexObjectID + classId + APIData.VORTEX_DOOR, DigestTools.Algorithm.MD5).toCharArray();
			final char[] md5APIKeySignature = DigestTools.digest(callerApiKey + APIData.SECRET_SESAME, DigestTools.Algorithm.MD5).toCharArray();
			final StringWriter sbObjectID = new StringWriter(16);
			sbObjectID.append(hexObjectID).append(classId);
			for (int i = 0; i < 4; i++) {
				sbObjectID.append(md5IDSignature[i]).append(md5APIKeySignature[i]);
			}
			return sbObjectID.toString();

		}

		return StringShop.EMPTY_STRING;
	}

	/**
	 * Conversion d'un ID application en ID natif.
	 * 
	 * @param inAPIID ID de l'application.
	 * @param inClass classe de l'objet (e.g. <code>"O"</code>, etc.).
	 * @param inAPIKey clé de l'application.
	 * @return l'ID ou <code>0</code> si la somme de contrôle est incorrecte ou
	 *         si la classe ne correspond pas.
	 */
	public static long fromObjectID(String inAPIId, ObjectClass inObjectClass, String inAPIKey) {
		long decypheredId = 0;

		// Récupération de l'ID.
		final String classId = inObjectClass.getId();
		final Matcher apiIdMatcher = APIData.API_ID_REGEX.matcher(inAPIId);

		if (apiIdMatcher.matches() && apiIdMatcher.group(2).equals(classId)) {

			final String hexObjectID = apiIdMatcher.group(1);
			final char[] md5IDSignature = DigestTools.digest(hexObjectID + classId + APIData.VORTEX_DOOR, DigestTools.Algorithm.MD5).toCharArray();

			if (APIData.isConformToSignature(apiIdMatcher.group(3).toCharArray(), md5IDSignature)) {
				try {
					decypheredId = Long.parseLong(apiIdMatcher.group(1), 16);

				} catch (final NumberFormatException anException) {
					// This space for rent.
				}
			}
		}

		return decypheredId;
	}

	private static boolean isConformToSignature(final char[] inPotential, final char[] inSignature) {
		for (int i = 0; i < 4; i++) {
			if (inPotential[i * 2] != inSignature[i]) {
				return false;
			}
		}
		return true;
	}

}

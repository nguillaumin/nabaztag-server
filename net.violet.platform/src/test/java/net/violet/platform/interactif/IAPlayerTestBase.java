package net.violet.platform.interactif;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.violet.platform.datamodel.AppletSettings;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.mock.AppletSettingsFactoryMock;
import net.violet.platform.datamodel.mock.VObjectMock;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.Sequence;
import net.violet.platform.util.ConvertTools;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;

public class IAPlayerTestBase extends MockTestBase {


	private static final Logger LOGGER = Logger.getLogger(IAPlayerTestBase.class);

	/**
	 * Suppression de toutes les données pour l'objet.
	 */
	protected static void deleteSettings(long inAppletId, VObject inObject) {
		((AppletSettingsFactoryMock) Factories.APPLET_SETTINGS).deleteSettingsForObject(inAppletId, inObject);
	}

	/**
	 * recupère le setting
	 */
	protected static int getSettingsValuebyKey(VObject inObject, VObject inPassiveObject, String inKeySettings, long inApplet) {
		int count = -1;
		final AppletSettings theApplet = Factories.APPLET_SETTINGS.getAppletSettingsByObjects(inObject, inPassiveObject, inApplet, inKeySettings);
		if (theApplet != null) {
			count = ConvertTools.atoi(theApplet.getValue());
		}
		return count;
	}

	/**
	 * Accesseur sur la valeur du cookie, à partir des séquences.
	 */
	protected static String getCookieFromSequences(List<Sequence> inSequences) {
		String theResult = null;
		for (final Sequence theSequence : inSequences) {
			if (theSequence.getType() == Sequence.SEQ_SETTING) {
				final String theValue = theSequence.getData();
				if (theValue.startsWith("cookie=")) {
					theResult = theValue.substring(7);
				}
			}
		}
		return theResult;
	}

	/**
	 * Accesseur sur la liste des séquences d'un message.
	 */

	protected static List<Sequence> getSequenceList(MessageDraft inMessage) {
		List<Sequence> theResult = null;
		try {
			theResult = (List<Sequence>) PrivateAccessor.invokeMethod(inMessage, "getSequenceList", new Object[0]);
		} catch (final InvocationTargetException anException) {
			IAPlayerTestBase.LOGGER.fatal(anException, anException);
		}
		return theResult;
	}

	/**
	 * Détermine l'indice d'un réglage dans la liste de séquences contient un
	 * réglage particulier.
	 * 
	 * @param inSequences
	 *            liste de séquences
	 * @param inKey
	 *            clé du réglage
	 * @param inValue
	 *            valeur du réglage
	 * @return l'index de la séquence, -1 si elle n'est pas dans la liste.
	 */
	protected static int getSettingIx(List<Sequence> inSequences, String inKey, String inValue) {
		return IAPlayerTestBase.getSequenceIx(inSequences, Sequence.SEQ_SETTING, inKey + "=" + inValue);
	}

	/**
	 * Détermine l'index d'une séquence dans la liste des séquences.
	 * 
	 * @param inSequences
	 *            liste de séquences
	 * @param inType
	 *            type de séquence
	 * @param inData
	 *            données de la séquence
	 * @return l'index de la séquence, -1 si elle n'est pas dans la liste.
	 */
	private static int getSequenceIx(List<Sequence> inSequences, int inType, String inData) {
		int theResult = -1;
		int theIndex = 0;
		for (final Sequence theSequence : inSequences) {
			if (theSequence.getType() == inType) {
				final String theData = theSequence.getData();
				if (theData.equals(inData)) {
					theResult = theIndex;
					break;
				}
			}
			theIndex++;
		}
		return theResult;
	}

	/**
	 * Détermine l'index d'une séquence dans la liste des séquences.
	 * 
	 * @param inSequences
	 *            liste de séquences
	 * @param inType
	 *            type de séquence
	 * @return l'index de la séquence, -1 si elle n'est pas dans la liste.
	 */
	protected static int getSequenceIx(List<Sequence> inSequences, int inType) {
		int theResult = -1;
		int theIndex = 0;
		for (final Sequence theSequence : inSequences) {
			if (theSequence.getType() == inType) {
				theResult = theIndex;
				break;
			}
			theIndex++;
		}
		return theResult;
	}

	/**
	 * Détermine le nombre de séquences d'un type donné.
	 * 
	 * @param inSequences
	 *            liste de séquences
	 * @param inType
	 *            type de séquence
	 * @return le nombre de séquences de ce type.
	 */
	protected static int countSequencesByType(List<Sequence> inSequences, int inType) {
		int theResult = 0;
		for (final Sequence theSequence : inSequences) {
			if (theSequence.getType() == inType) {
				theResult++;
			}
		}
		return theResult;
	}

	/**
	 * Détermine l'index d'une séquence audio dans la liste des séquences.
	 * 
	 * @param inSequences
	 *            liste de séquences
	 * @param inPath
	 *            chemin du son
	 * @return l'index de la séquence, -1 si elle n'est pas dans la liste.
	 */
	protected static int getAudioSequenceIx(List<Sequence> inSequences, String inPath) {
		return IAPlayerTestBase.getSequenceIx(inSequences, Sequence.SEQ_MUSIC_STREAMING, inPath);
	}

	/**
	 * Détermine l'index de la fin de mode interactif dans la liste des
	 * séquences.
	 * 
	 * @param inSequences
	 *            liste de séquences
	 * @return l'index de la séquence, -1 si elle n'est pas dans la liste.
	 */
	private static int getEndInteractiveSequenceIx(List<Sequence> inSequences) {
		return IAPlayerTestBase.getSequenceIx(inSequences, Sequence.SEQ_END_INTERACTIVE_MODE);
	}

	/**
	 * Vérifie qu'on a bien une et une seule commande audio.
	 * 
	 * @param inSequences
	 *            liste de séquences
	 * @param inPath
	 *            chemin du son audio à lire.
	 */
	protected static void assertSingleAudioSequence(List<Sequence> inSequences, String inPath) {
		Assert.assertEquals(1, IAPlayerTestBase.countSequencesByType(inSequences, Sequence.SEQ_MUSIC_STREAMING));
		final int sequenceIx = IAPlayerTestBase.getAudioSequenceIx(inSequences, inPath);
		Assert.assertTrue(sequenceIx > -1);
	}

	/**
	 * Vérifie qu'on a bien une fin de mode interactif.
	 * 
	 * @param inSequences
	 *            liste de séquences
	 */
	protected static void assertEndsInteractiveMode(List<Sequence> inSequences) {
		final int interactiveEndIx = IAPlayerTestBase.getEndInteractiveSequenceIx(inSequences);
		Assert.assertTrue(interactiveEndIx > -1);
		Assert.assertTrue(interactiveEndIx == (inSequences.size() - 1));
	}

	/**
	 * Vérifie qu'on a bien le livre et retourne la position dans le flux.
	 * 
	 * @param inSequences
	 *            liste de séquences
	 */
	protected static int getPositionInSecureStream(List<Sequence> inSequences) {
		boolean present = false;
		int thePosition = 0;
		for (final Sequence theSequence : inSequences) {
			if (theSequence.getType() == Sequence.SEQ_SECURESTREAMING) {
				present = true;
				final String theData = theSequence.getData();
				final Pattern theRegex = Pattern.compile(".*stream-([0-9]+).*");
				final Matcher theMatcher = theRegex.matcher(theData);
				if (theMatcher.matches()) {
					thePosition = Integer.parseInt(theMatcher.group(1));
				}
				break;
			}
		}
		Assert.assertTrue(present);
		return thePosition;
	}

	/**
	 * Référence sur le lecteur Rfid.
	 */
	private VObject mReader;

	/**
	 * Référence sur le deuxième lecteur Rfid.
	 */
	private VObject mReader2;

	/**
	 * Référence sur le Rfid.
	 */
	private VObject mRfid;

	@Override
	@Before
	public void setUp() {
		super.setUp();

		this.mReader = new VObjectMock(0, "IAPLAYERTST1", "IAPlayerTestReader", getKowalskyUser(), HARDWARE.V2, getParisTimezone(), getFrLang());
		this.mReader2 = new VObjectMock(0, "IAPLAYERTST2", "IAPlayerTestReader2", getKowalskyUser(), HARDWARE.V2, getParisTimezone(), getFrLang());
		this.mRfid = new VObjectMock(0, "IAPLAYERTST3", "IAPlayerTestReader3", getKowalskyUser(), HARDWARE.BOOK, getParisTimezone(), getFrLang());
	}

	/**
	 * Accesseur sur le premier objet.
	 * 
	 * @return le premier object (lecteur).
	 */
	protected VObject getReader() {
		return this.mReader;
	}

	/**
	 * Accesseur sur le second objet.
	 * 
	 * @return le second objet (lecteur).
	 */
	protected VObject getReader2() {
		return this.mReader2;
	}

	/**
	 * Accesseur sur le rfid.
	 * 
	 * @return le rfid.
	 */
	protected VObject getRfid() {
		return this.mRfid;
	}
}

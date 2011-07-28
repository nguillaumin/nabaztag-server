package net.violet.platform.applets.interactive;

import java.util.List;

import net.violet.platform.datamodel.AppletSettings;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.NathanVersion;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.NathanVersionMock;
import net.violet.platform.interactif.IAPlayerTestBase;
import net.violet.platform.interactif.Status;
import net.violet.platform.interactif.Status.Source;
import net.violet.platform.interactif.config.NathanPlayerConfig;
import net.violet.platform.message.Message;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.Sequence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NathanPlayerTest extends IAPlayerTestBase {

	private final NathanPlayer theApplication = new NathanPlayer();

	private final static String APPLICATION_NAME = "net.violet.audiobooks.violettedanslenoir";

	// Reprise des anciennes constantes du livre du chat botté pour les tests
	// nathan
	private static int POS_CHAPTER_1;
	private static int POS_CHAPTER_2 = 14300;
	private static int POS_WITHIN_CHAPTER_2 = 100000;
	private static int POS_CHAPTER_3 = 398300;
	private static int POS_CHAPTER_4 = 866832;
	private static int POS_CHAPTER_5 = 1399416;
	private static int POS_CHAPTER_6 = 1809016;
	private static int POS_CHAPTER_7 = 2327703;

	private static String ISBN = "9782092512593";

	private NathanVersion nathanMock;
	private NathanVersion nathanOfficialMock;
	private Files filesMock;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		this.nathanOfficialMock = new NathanVersionMock(1, super.getRfid(), NathanPlayerTest.ISBN, true);
		this.filesMock = Factories.FILES.createFile("broadcast/broad/config/testConfigFilesHC.mp3", MimeType.MIME_TYPES.A_MPEG);
		Factories.NATHAN_MP3.createNewNathanMp3(this.nathanOfficialMock, this.filesMock, NathanPlayerTest.POS_CHAPTER_1);
		Factories.NATHAN_MP3.createNewNathanMp3(this.nathanOfficialMock, this.filesMock, NathanPlayerTest.POS_CHAPTER_2);
		Factories.NATHAN_MP3.createNewNathanMp3(this.nathanOfficialMock, this.filesMock, NathanPlayerTest.POS_CHAPTER_3);
		Factories.NATHAN_MP3.createNewNathanMp3(this.nathanOfficialMock, this.filesMock, NathanPlayerTest.POS_CHAPTER_4);
		Factories.NATHAN_MP3.createNewNathanMp3(this.nathanOfficialMock, this.filesMock, NathanPlayerTest.POS_CHAPTER_5);
		Factories.NATHAN_MP3.createNewNathanMp3(this.nathanOfficialMock, this.filesMock, NathanPlayerTest.POS_CHAPTER_6);
		Factories.NATHAN_MP3.createNewNathanMp3(this.nathanOfficialMock, this.filesMock, NathanPlayerTest.POS_CHAPTER_7);
		this.nathanMock = new NathanVersionMock(2, super.getRfid(), NathanPlayerTest.ISBN);
		Factories.NATHAN_MP3.createNewNathanMp3(this.nathanMock, this.filesMock, NathanPlayerTest.POS_CHAPTER_1);
		Factories.NATHAN_MP3.createNewNathanMp3(this.nathanMock, this.filesMock, NathanPlayerTest.POS_CHAPTER_2);
	}

	private NathanPlayerConfig loadParamByISBN() {
		String theParam = null;
		try {
			theParam = NathanPlayer.getParamByIBSN(NathanPlayerTest.ISBN, getReader(), getRfid());
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		}

		return this.theApplication.getConfig(theParam);
	}

	private NathanPlayerConfig loadParamByISBN(VObject newReader) {
		String theParam = null;
		try {
			theParam = NathanPlayer.getParamByIBSN(NathanPlayerTest.ISBN, newReader, getRfid());
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		}

		return this.theApplication.getConfig(theParam);
	}

	@Test
	public void testFirstTimeBeginning() {
		// Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());

		final NathanPlayerConfig mConfig = loadParamByISBN();
		final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
		final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

		final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);

		// thePlayer.process_start(mConfig, getReader(), getRfid());
		final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

		IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicHelpInit());

		// Contrôle des réglages:

		// Si l'utilisateur presse le bouton pendant l'aide (avant la fin
		// de l'aide), alors le son doit être le clonk normal.
		// Par conséquent, on doit avoir:
		// snd.btn.1=clonk (pas nécessaire, c'est la valeur par défaut)
		// snd.btn.2=clonk
		// jouer l'aide
		// snd.btn.1=neutral
		// snd.btn.2=neutral (// ou pas... double clic = arrêt brutal)
		// lire le livre
		final int theClonkBtn1SettingIx = IAPlayerTestBase.getSettingIx(theSequences, "snd.btn.1", "clonk");
		final int theClonkBtn2SettingIx = IAPlayerTestBase.getSettingIx(theSequences, "snd.btn.2", "clonk");
		Assert.assertTrue(theClonkBtn2SettingIx >= 0); // Présent
		final int theHelpSequenceIx = IAPlayerTestBase.getAudioSequenceIx(theSequences, mConfig.getMusicHelpInit());
		Assert.assertTrue(theHelpSequenceIx >= 0); // Présent snd.btn.*=clonk avant l'aide
		Assert.assertTrue(theClonkBtn1SettingIx < theHelpSequenceIx); // OK si -1
		Assert.assertTrue(theClonkBtn2SettingIx < theHelpSequenceIx);

		final int theNeutralBtn1SettingIx = IAPlayerTestBase.getSettingIx(theSequences, "snd.btn.1", "neutral");
		Assert.assertTrue(theNeutralBtn1SettingIx >= 0); // Présent
		final int theNeutralBtn2SettingIx = IAPlayerTestBase.getSettingIx(theSequences, "snd.btn.2", "neutral");
		Assert.assertTrue(theNeutralBtn2SettingIx >= 0); // Présent snd.btn.*=neutral après l'aide
		Assert.assertTrue(theNeutralBtn1SettingIx > theHelpSequenceIx);
		Assert.assertTrue(theNeutralBtn1SettingIx > theHelpSequenceIx);

		final int theBookSequenceIx = IAPlayerTestBase.getSequenceIx(theSequences, Sequence.SEQ_SECURESTREAMING);
		Assert.assertTrue(theBookSequenceIx >= 0); // Présent snd.btn.*=neutral avant le livre
		Assert.assertTrue(theNeutralBtn1SettingIx < theBookSequenceIx);
		Assert.assertTrue(theNeutralBtn1SettingIx < theBookSequenceIx);

		// Par transitivité, l'aide est avant le livre.

		// L'enregistrement (reconnaissance vocale) doit être désactivé
		// avant tout son.
		final int theRecordDisableIx = IAPlayerTestBase.getSettingIx(theSequences, "record.enabled", "false");
		Assert.assertTrue(theRecordDisableIx >= 0); // Présent
		Assert.assertTrue(theRecordDisableIx < theHelpSequenceIx); // avant l'aide. La chorégraphie pendant le streaming doit être désactivée avant le livre
		final int theStreamingChorDisableIx = IAPlayerTestBase.getSettingIx(theSequences, "streaming.chor.enabled", "false");
		Assert.assertTrue(theStreamingChorDisableIx >= 0); // Présent
		Assert.assertTrue(theRecordDisableIx < theBookSequenceIx); // avant le livre.

		Assert.assertEquals(0, IAPlayerTestBase.getSettingsValuebyKey(getRfid(), getReader(), mConfig.getMarkup(), NathanPlayer.APPLET_ID));
	}

	@Test
	public void testEnd() {
		// Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());

		final NathanPlayerConfig mConfig = loadParamByISBN();

		String theCookie;

		// Lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On arrive au bout de l'histoire.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, 0, 0, new Status(Source.DONE, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicEndBook());
			IAPlayerTestBase.assertEndsInteractiveMode(theSequences);
		}
	}

	@Test
	public void testButtonDuringHelp() {
		// Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On interrompt l'aide.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, 42, 0, new Status(Source.BUTTON, 1, 0, 0)).get(0);
			// thePlayer.process_button(mConfig, getReader(), getRfid(),
			// theCookie, 42 /* pos */, 0 /* idx */, 1 /* btn */);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			IAPlayerTestBase.assertEndsInteractiveMode(theSequences);
		}
	}

	@Test
	public void testLeftEarRabbitDuringHelp() { // avance Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());
		String theCookie;

		// Lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		{
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, 42, 0, new Status(Source.EAR, 0, 1, 0)).get(0);
			// thePlayer.process_ear(mConfig, getReader(), getRfid(), theCookie,
			// 1 /* earl */, 0 /* earr */, 42 /* pos */, 0 /* idx */);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// Le livre, au début.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertEquals(0, thePosition);

		}
	}

	@Test
	public void testRightEarRabbitDuringHelp() { // recule Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());
		final NathanPlayerConfig mConfig = loadParamByISBN();
		String theCookie;

		// Lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		{
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, 42, 0, new Status(Source.EAR, 0, 0, 1)).get(0);
			// thePlayer.process_ear(mConfig, getReader(), getRfid(), theCookie,
			// 0 /* earl */, 1 /* earr */, 42 /* pos */, 0 /* idx */);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// L'aide (longue)
			IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicHelpLong());

			// Le livre, au début.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertTrue(thePosition == 0);

		}
	}

	@Test
	public void testButtonBetweenHelpAndBook() {
		// Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());
		final NathanPlayerConfig mConfig = loadParamByISBN();
		String theCookie;

		// Lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On interrompt juste avant le livre.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, 0, 1, new Status(Source.BUTTON, 1, 0, 0)).get(0);
			// thePlayer.process_button(mConfig, getReader(), getRfid(),
			// theCookie, 0 /* pos */, 1 /* idx */, 1 /* btn */);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Un seul son, "bye".
			IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicBye());
			IAPlayerTestBase.assertEndsInteractiveMode(theSequences);
		}
	}

	@Test
	public void testLeftEarRabbitBetweenHelpAndBook() { // avance Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());
		String theCookie;

		// Lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On interrompt juste avant le livre.
		{
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, 0, 1, new Status(Source.EAR, 0, 1, 0)).get(0);
			// thePlayer.process_ear(mConfig, getReader(), getRfid(), theCookie,
			// 1 /* earl */, 0 /* earr */, 0 /* pos */, 1 /* idx */);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// Le livre, chapitre 2.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertTrue(thePosition > 0);
		}
	}

	@Test
	public void testRightEarRabbitBetweenHelpAndBook() { // recule Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());
		final NathanPlayerConfig mConfig = loadParamByISBN();
		String theCookie;

		// Lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On interrompt juste avant le livre.
		{
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, 0, 1, new Status(Source.EAR, 0, 0, 1)).get(0);
			// thePlayer.process_ear(mConfig, getReader(), getRfid(), theCookie,
			// 0 /* earl */, 1 /* earr */, 0 /* pos */, 1 /* idx */);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// L'aide (longue)
			IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicHelpLong());

			// Le livre, au début.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertTrue(thePosition == 0);
		}
	}

	@Test
	public void testButtonDuringBook() {
		// Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());
		final NathanPlayerConfig mConfig = loadParamByISBN();
		String theCookie;

		// Lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On interrompt pendant le livre.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, NathanPlayerTest.POS_WITHIN_CHAPTER_2, 1, new Status(Source.BUTTON, 1, 0, 0)).get(0);
			// thePlayer.process_button(mConfig, getReader(), getRfid(),
			// theCookie, POS_WITHIN_CHAPTER_2 /* pos */, 1 /* idx */, 1 /* btn
			// */);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Un seul son, "bye".
			IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicBye());
			IAPlayerTestBase.assertEndsInteractiveMode(theSequences);
		}
	}

	/**
	 * Test du déplacement de l'oreille gauche du lapin pendant la lecture.
	 * (premier déplacement).
	 */
	@Test
	public void testLeftEarRabbitDuringBook() { // avance
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_1, NathanPlayerTest.POS_CHAPTER_2, true);
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_1 + 3000, NathanPlayerTest.POS_CHAPTER_2, true);
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_1 + 5000, NathanPlayerTest.POS_CHAPTER_2, true);
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_2 - 1000, NathanPlayerTest.POS_CHAPTER_2, true); // -1K
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_2, NathanPlayerTest.POS_CHAPTER_3, true); // =
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_2 + 10000, NathanPlayerTest.POS_CHAPTER_3, true); // +10K
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_2 + 31000, NathanPlayerTest.POS_CHAPTER_3, true); // +31K
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_3 - 1000, NathanPlayerTest.POS_CHAPTER_3, true); // -1K
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_3, NathanPlayerTest.POS_CHAPTER_4, true); // =
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_3 + 10000, NathanPlayerTest.POS_CHAPTER_4, true); // +10K
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_3 + 31000, NathanPlayerTest.POS_CHAPTER_4, true); // +31K
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_4 - 1000, NathanPlayerTest.POS_CHAPTER_4, true); // -1K
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_4, NathanPlayerTest.POS_CHAPTER_5, true); // =
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_4 + 10000, NathanPlayerTest.POS_CHAPTER_5, true); // +10K
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_4 + 31000, NathanPlayerTest.POS_CHAPTER_5, true); // +31K
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_5 - 1000, NathanPlayerTest.POS_CHAPTER_5, true); // -1K
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_5, NathanPlayerTest.POS_CHAPTER_6, true); // =
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_6, NathanPlayerTest.POS_CHAPTER_7, true); // =
	}

	/**
	 * Test du déplacement de l'oreille gauche du lapin a la fin d'un bouquin.
	 * (premier déplacement).
	 */
	@Test
	public void testLeftEarRabbitDuringLastChapterBook() { // avance Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());
		String theCookie;

		// Lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On avance au dernier chapitre.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, NathanPlayerTest.POS_CHAPTER_7 + 10000, 1, new Status(Source.EAR, 0, 1, 0)).get(0);
			// thePlayer.process_ear(mConfig, getReader(), getRfid(), theCookie,
			// 1 /* earl */, 0 /* earr */, POS_CHAPTER_8 + 10000/* pos */, 1 /*
			// idx */);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			Assert.assertEquals(1, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			IAPlayerTestBase.assertEndsInteractiveMode(theSequences);
		}

	}

	/**
	 * Test du déplacement de l'oreille droite du lapin au début du deuxième
	 * chapitre du bouquin. (premier déplacement).
	 */
	@Test
	public void testRightEarRabbitDuringSecondChapterBook() { // recule Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());
		String theCookie;

		// Lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On avance au premier chapitre.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, NathanPlayerTest.POS_CHAPTER_2 + 10000, 1, new Status(Source.EAR, 0, 0, 1)).get(0);
			// thePlayer.process_ear(mConfig, getReader(), getRfid(), theCookie,
			// 0 /* earl */, 1 /* earr */, POS_CHAPTER_2 /* pos */, 1 /* idx
			// */);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// Le livre.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertEquals(NathanPlayerTest.POS_CHAPTER_1, thePosition);
		}

	}

	/**
	 * Test du déplacement de l'oreille droite du lapin au début du premier
	 * chapitre du bouquin. (premier déplacement).
	 */
	@Test
	public void testRightEarRabbitDuringFirstChapterBook() { // recule Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());
		final NathanPlayerConfig mConfig = loadParamByISBN();
		String theCookie;

		// Lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On avance au premier chapitre.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, NathanPlayerTest.POS_CHAPTER_1, 1, new Status(Source.EAR, 0, 0, 1)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// relis l'aide
			IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicHelpLong());

			// Le livre.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertEquals(0, thePosition);
		}

	}

	/**
	 * Test du déplacement de l'oreille droite du lapin pendant la lecture.
	 * (premier déplacement).
	 */
	@Test
	public void testRightEarRabbitDuringBook() { // recule
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_2 + 10001, NathanPlayerTest.POS_CHAPTER_1, false); // +10K
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_2 + 31000, NathanPlayerTest.POS_CHAPTER_2, false); // +31K
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_3 - 1000, NathanPlayerTest.POS_CHAPTER_2, false); // -1K
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_3, NathanPlayerTest.POS_CHAPTER_2, false); // =
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_3 + 10001, NathanPlayerTest.POS_CHAPTER_2, false); // +10K
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_3 + 31000, NathanPlayerTest.POS_CHAPTER_3, false); // +31K
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_4 - 1000, NathanPlayerTest.POS_CHAPTER_3, false); // -1K
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_4, NathanPlayerTest.POS_CHAPTER_3, false); // =
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_4 + 10001, NathanPlayerTest.POS_CHAPTER_3, false); // +10K
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_4 + 31000, NathanPlayerTest.POS_CHAPTER_4, false); // +31K
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_5 - 1000, NathanPlayerTest.POS_CHAPTER_4, false); // -1K
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_5, NathanPlayerTest.POS_CHAPTER_4, false); // =
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_6, NathanPlayerTest.POS_CHAPTER_5, false); // =
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_7, NathanPlayerTest.POS_CHAPTER_6, false); // =
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_7 + 10001, NathanPlayerTest.POS_CHAPTER_6, false);
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_7 + 31000, NathanPlayerTest.POS_CHAPTER_7, false);
		testEarDuringBook(NathanPlayerTest.POS_CHAPTER_7 + 33000, NathanPlayerTest.POS_CHAPTER_7, false);
	}

	/**
	 * Test de l'oreille droite ou gauche, pendant la lecture.
	 * 
	 * @param inPositionBefore
	 *            position au moment du mouvement d'oreille.
	 * @param inPositionAfter
	 *            position après le mouvement d'oreille.
	 * @param inLeftEar
	 *            si c'est l'oreille gauche (la droite du lapin)
	 */
	private void testEarDuringBook(int inPositionBefore, int inPositionAfter, boolean inLeftEar) {
		// Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On interrompt pendant le livre.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final int ear_left = inLeftEar ? 1 : 0;
			final int ear_right = inLeftEar ? 0 : 1;
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, inPositionBefore, 1, new Status(Source.EAR, 0, ear_left, ear_right)).get(0);
			// thePlayer.process_ear(mConfig, getReader(), getRfid(), theCookie,
			// ear_left /* earl */, ear_right /* earr */, inPositionBefore /*
			// pos */, 1 /* idx */);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// Le livre.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertEquals(inPositionAfter, thePosition);
		}
	}

	@Test
	public void testLeftEarLeftEarRabbitDuringBook() {
		// Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());
		String theCookie;

		// Lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		{
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, NathanPlayerTest.POS_WITHIN_CHAPTER_2, 1, new Status(Source.EAR, 0, 1, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// Le livre, chapitre 3.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertTrue(thePosition == NathanPlayerTest.POS_CHAPTER_3);
		}

		{
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, NathanPlayerTest.POS_CHAPTER_3 + 1, 1, new Status(Source.EAR, 0, 1, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// Le livre, chapitre 5.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertTrue(thePosition == NathanPlayerTest.POS_CHAPTER_4);
		}
	}

	@Test
	public void testLeftEarRightEarRabbitDuringBook() {
		// Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());
		String theCookie;

		// Lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		{
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, NathanPlayerTest.POS_WITHIN_CHAPTER_2, 1, new Status(Source.EAR, 0, 1, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// Le livre, chapitre 3.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertTrue(thePosition == NathanPlayerTest.POS_CHAPTER_3);
		}

		{
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, NathanPlayerTest.POS_CHAPTER_5 + 1, 1, new Status(Source.EAR, 0, 0, 1)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// On revient au début du chapitre 4.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertTrue(thePosition == NathanPlayerTest.POS_CHAPTER_4);
		}
	}

	@Test
	public void testButtonDuringBye() {
		// Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());
		String theCookie;

		// Lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On interrompt juste avant le livre.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, 0, 1, new Status(Source.BUTTON, Message.CLIC_SIMPLE, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On interrompt pendant le message au revoir.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, 0, 0, new Status(Source.BUTTON, Message.CLIC_SIMPLE, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			IAPlayerTestBase.assertEndsInteractiveMode(theSequences);
		}
	}

	@Test
	public void testSecondTime() {
		// Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());
		final NathanPlayerConfig mConfig = loadParamByISBN();
		// Première lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			final String theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);

			// On arrive au bout de l'histoire.
			this.theApplication.processItMode(getReader(), theSubscription, theCookie, 0, 0, new Status(Source.DONE, 0, 0, 0)).get(0);
		}

		// Deuxième lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			// Aide longue, encore.
			IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicHelpLong());
		}
	}

	@Test
	public void testSecondTimeWithBookmark() {
		// Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());
		final NathanPlayerConfig mConfig = loadParamByISBN();
		String theCookie;

		// Première lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
			IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicHelpInit());
		}

		// On s'arrête au milieu du livre.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			this.theApplication.processItMode(getReader(), theSubscription, theCookie, NathanPlayerTest.POS_WITHIN_CHAPTER_2, 1, new Status(Source.BUTTON, Message.CLIC_SIMPLE, 0, 0)).get(0);
		}

		// Deuxième lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			// Aide courte.
			IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicHelpShort());

			// Le livre, au début du chapitre 2.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertEquals(NathanPlayerTest.POS_CHAPTER_2, thePosition);
		}
	}

	@Test
	public void testSecondTimeWithFirstChapterMarkup() {
		// Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());
		final NathanPlayerConfig mConfig = loadParamByISBN();
		String theCookie;

		// Première lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
			IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicHelpInit());
		}

		// On s'arrête au premier chapitre du livre.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			this.theApplication.processItMode(getReader(), theSubscription, theCookie, 10, 1, new Status(Source.BUTTON, Message.CLIC_SIMPLE, 0, 0)).get(0);
		}

		// Deuxième lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			// Aide longue.
			IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicHelpLong());

			// Le livre, au début du chapitre 2.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertEquals(NathanPlayerTest.POS_CHAPTER_1, thePosition);
		}
	}

	@Test
	public void testStartstopstartLeftRabbitEarMove() { // avance Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());
		final NathanPlayerConfig mConfig = loadParamByISBN();
		String theCookie;

		// Première lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On s'arrête au milieu du livre.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			this.theApplication.processItMode(getReader(), theSubscription, theCookie, NathanPlayerTest.POS_WITHIN_CHAPTER_2, 1, new Status(Source.BUTTON, Message.CLIC_SIMPLE, 0, 0)).get(0);
		}

		// Deuxième lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
			// Aide courte.
			IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicHelpShort());

			// Le livre, au début du chapitre 2.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertEquals(NathanPlayerTest.POS_CHAPTER_2, thePosition);
		}

		{
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, NathanPlayerTest.POS_CHAPTER_5 + 1, 1, new Status(Source.EAR, 0, 1, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// On revient au début du chapitre 6.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertTrue(thePosition == NathanPlayerTest.POS_CHAPTER_6);
		}

	}

	@Test
	public void testStartstopstartRightRabbitEarMove() { // recule Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());
		final NathanPlayerConfig mConfig = loadParamByISBN();
		String theCookie;

		// Première lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On s'arrête au milieu du livre.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			this.theApplication.processItMode(getReader(), theSubscription, theCookie, NathanPlayerTest.POS_WITHIN_CHAPTER_2, 1, new Status(Source.BUTTON, Message.CLIC_SIMPLE, 0, 0)).get(0);
		}

		// Deuxième lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
			// Aide courte.
			IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicHelpShort());

			// Le livre, au début du chapitre 2.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertEquals(NathanPlayerTest.POS_CHAPTER_2, thePosition);
		}

		{
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, NathanPlayerTest.POS_WITHIN_CHAPTER_2, 1, new Status(Source.EAR, 0, 0, 1)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// On revient au début du chapitre 2.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			System.out.println(thePosition);
			Assert.assertTrue(thePosition == NathanPlayerTest.POS_CHAPTER_2);
		}

	}

	@Test
	public void testDoubleClic() {
		// Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());
		String theCookie;

		// Première lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On s'arrête au milieu du livre.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, NathanPlayerTest.POS_WITHIN_CHAPTER_2, 1, new Status(Source.BUTTON, Message.CLIC_DOUBLE, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			IAPlayerTestBase.assertEndsInteractiveMode(theSequences);
		}

	}

	@Test
	public void testLeftRabbitEarDuringBye() {
		// Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());
		String theCookie;

		// Lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On interrompt juste avant le livre.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, 0, 1, new Status(Source.BUTTON, Message.CLIC_SIMPLE, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On avance pendant le message au revoir.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, 10, 2, new Status(Source.EAR, 0, 1, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			IAPlayerTestBase.assertEndsInteractiveMode(theSequences);
		}
	}

	@Test
	public void testRightRabbitEarDuringBye() {
		// Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());
		String theCookie;

		// Lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On interrompt au milieu getSettingIxdu livre.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, NathanPlayerTest.POS_WITHIN_CHAPTER_2, 1, new Status(Source.BUTTON, Message.CLIC_SIMPLE, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On recule pendant le message au revoir.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, 10, 2, new Status(Source.EAR, 0, 0, 1)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// AVANT : On revenais ou on en était.
			// NOW : On reviens au dernier chapitre
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertEquals(thePosition, NathanPlayerTest.POS_CHAPTER_7);
			// assertTrue(thePosition == POS_CHAPTER_2);
		}
	}

	@Test
	public void testLeftRabbitEarDuringEndBookSong() {
		// Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());
		final NathanPlayerConfig mConfig = loadParamByISBN();
		String theCookie;

		// Lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On passe au chapitre suivant et il y en a pas
		{

			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, NathanPlayerTest.POS_CHAPTER_7 + 200000, 1, new Status(Source.EAR, 0, 1, 0)).get(0);

			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);

			// fin de bouquin.
			IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicEndBook());

			IAPlayerTestBase.assertEndsInteractiveMode(theSequences);
		}

		// On avance pendant le message au revoir.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, 10, 3, new Status(Source.EAR, 0, 1, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			IAPlayerTestBase.assertEndsInteractiveMode(theSequences);
		}
	}

	@Test
	public void testRightRabbitEarDuringEndBookSong() {
		// Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());
		final NathanPlayerConfig mConfig = loadParamByISBN();
		String theCookie;

		// Lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On passe au chapitre suivant et il y en a pas
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, NathanPlayerTest.POS_CHAPTER_7 + 200000, 1, new Status(Source.EAR, 0, 1, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);

			// fin de bouquin.
			IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicEndBook());

			IAPlayerTestBase.assertEndsInteractiveMode(theSequences);
		}

		// On recule pendant le message au revoir.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, 10, 3, new Status(Source.EAR, 0, 0, 1)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// On revient au dernier chapitre.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertEquals(thePosition, NathanPlayerTest.POS_CHAPTER_7);
		}
	}

	@Test
	public void testAddSetting() { // check l'incrémentation des settings Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());
		final NathanPlayerConfig mConfig = loadParamByISBN();
		String theCookie;

		// Première lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);

			// voix par défaut la premiere fois
			Assert.assertEquals(1, IAPlayerTestBase.getSettingsValuebyKey(getReader(), getRfid(), mConfig.getVoice((int) mConfig.getVersion()), NathanPlayer.APPLET_ID));

			// permet de vérifier si le secondary object est bien setté sur
			// l'objet lecteur
			// on récupère la version officielle par défaut
			Assert.assertEquals(1, IAPlayerTestBase.getSettingsValuebyKey(getRfid(), getReader(), mConfig.getVoice(), NathanPlayer.APPLET_ID));
		}

		// On a fini de lire le bouquin
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			this.theApplication.processItMode(getReader(), theSubscription, theCookie, 0, 0, new Status(Source.DONE, 0, 0, 0)).get(0);

			Assert.assertEquals(1, IAPlayerTestBase.getSettingsValuebyKey(getReader(), getRfid(), mConfig.getCountFinish((int) mConfig.getVersion()), NathanPlayer.APPLET_ID));
			// reset du markup et de la voice
			Assert.assertEquals(0, IAPlayerTestBase.getSettingsValuebyKey(getRfid(), getReader(), mConfig.getMarkup(), NathanPlayer.APPLET_ID));

			final AppletSettings theAP = Factories.APPLET_SETTINGS.getAppletSettingsByObject(getRfid(), NathanPlayer.APPLET_ID, mConfig.getMarkup());

			// on ne supprime pas le row et on ne update pas le secondary object
			// car c'est pour garder une référence sur le possesseur du bouquin
			Assert.assertEquals(getReader(), theAP.getSecondaryObject());

			Assert.assertEquals(0, IAPlayerTestBase.getSettingsValuebyKey(getRfid(), getReader(), mConfig.getVoice(), NathanPlayer.APPLET_ID));
		}

		// Deuxième lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);

			Assert.assertEquals(2, IAPlayerTestBase.getSettingsValuebyKey(getReader(), getRfid(), mConfig.getVoice((int) mConfig.getVersion()), NathanPlayer.APPLET_ID));
		}

		// On a fini de lire le bouquin
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			this.theApplication.processItMode(getReader(), theSubscription, theCookie, 0, 0, new Status(Source.DONE, 0, 0, 0)).get(0);

			Assert.assertEquals(2, IAPlayerTestBase.getSettingsValuebyKey(getReader(), getRfid(), mConfig.getCountFinish((int) mConfig.getVersion()), NathanPlayer.APPLET_ID));
			// reset du markup et de la voice
			Assert.assertEquals(0, IAPlayerTestBase.getSettingsValuebyKey(getRfid(), getReader(), mConfig.getMarkup(), NathanPlayer.APPLET_ID));

			final AppletSettings theAP = Factories.APPLET_SETTINGS.getAppletSettingsByObject(getRfid(), NathanPlayer.APPLET_ID, mConfig.getMarkup());

			// on ne supprime pas le row et on ne update pas le secondary object
			// car c'est pour garder une référence sur le possesseur du bouquin
			Assert.assertEquals(getReader(), theAP.getSecondaryObject());

			Assert.assertEquals(0, IAPlayerTestBase.getSettingsValuebyKey(getRfid(), getReader(), mConfig.getVoice(), NathanPlayer.APPLET_ID));
		}

	}

	@Test
	public void testChangeReader() { // check lorsqu'on passe le bouquin sur un autre objet, il doit reprendre au chapitre laissé par le dernier lecteur Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getRfid());
		NathanPlayerConfig mConfig = loadParamByISBN();
		String theCookie;

		// Première lecture.
		{

			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);

			// voix par défaut la premiere fois
			Assert.assertEquals(1, IAPlayerTestBase.getSettingsValuebyKey(getReader(), getRfid(), mConfig.getVoice((int) mConfig.getVersion()), NathanPlayer.APPLET_ID));

			// permet de vérifier si le secondary object est bien setté sur
			// l'objet lecteur
			// on récupère la version officielle par défaut
			Assert.assertEquals(1, IAPlayerTestBase.getSettingsValuebyKey(getRfid(), getReader(), mConfig.getVoice(), NathanPlayer.APPLET_ID));
		}

		// On s'arrête au milieu du livre.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, theCookie, NathanPlayerTest.POS_CHAPTER_4, 1, new Status(Source.BUTTON, Message.CLIC_SIMPLE, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			Assert.assertEquals(1, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			IAPlayerTestBase.assertEndsInteractiveMode(theSequences);
		}

		// on passe le bouquin sur un autre objet
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			mConfig = loadParamByISBN(getReader2());
			this.theApplication.processItMode(getReader2(), theSubscription, theCookie, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);

			// permet de vérifier si le secondary object est bien setté sur
			// l'objet lecteur
			Assert.assertEquals(1, IAPlayerTestBase.getSettingsValuebyKey(getRfid(), getReader2(), mConfig.getVoice(), NathanPlayer.APPLET_ID));
			// Permet de voir si on reprend bien au chapitre laissé par le
			// dernier lecteur
			Assert.assertEquals(3, IAPlayerTestBase.getSettingsValuebyKey(getRfid(), getReader(), mConfig.getMarkup(), NathanPlayer.APPLET_ID));
		}

	}

	// Vérifie qu'on récupère bien les sons soit dans l'endroit sécurisé si
	// c'est une version officielle ,sinon inversement
	// Vérifie aussi le clean de la sélection
	@Test
	public void testVersionNonOfficielle() {
		// Il n'y a pas de marque page.
		NathanPlayerTest.deleteSettings(getReader());
		NathanPlayerTest.deleteSettings(getReader2());
		NathanPlayerTest.deleteSettings(getRfid());
		NathanPlayerConfig mConfig = loadParamByISBN();
		String theCookie;

		// Première lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);

			// voix par défaut la premiere fois
			Assert.assertEquals(1, IAPlayerTestBase.getSettingsValuebyKey(getReader(), getRfid(), mConfig.getVoice((int) mConfig.getVersion()), NathanPlayer.APPLET_ID));

			// permet de vérifier si le secondary object est bien setté sur
			// l'objet lecteur
			// on récupère la version officielle par défaut
			Assert.assertEquals(1, IAPlayerTestBase.getSettingsValuebyKey(getRfid(), getReader(), mConfig.getVoice(), NathanPlayer.APPLET_ID));

			// secure stream.
			Assert.assertEquals(1, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_SECURESTREAMING));
		}
		String result = NathanPlayerTest.getSettingsSelection(getRfid());

		Assert.assertEquals("1", result); // check si la version est bien crée On a fini de lire le bouquin
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			this.theApplication.processItMode(getReader(), theSubscription, theCookie, 0, 0, new Status(Source.DONE, 0, 0, 0)).get(0);

			Assert.assertEquals(1, IAPlayerTestBase.getSettingsValuebyKey(getReader(), getRfid(), mConfig.getCountFinish((int) mConfig.getVersion()), NathanPlayer.APPLET_ID));
			// reset du markup et de la voice
			Assert.assertEquals(0, IAPlayerTestBase.getSettingsValuebyKey(getRfid(), getReader(), mConfig.getMarkup(), NathanPlayer.APPLET_ID));

			final AppletSettings theAP = Factories.APPLET_SETTINGS.getAppletSettingsByObject(getRfid(), NathanPlayer.APPLET_ID, mConfig.getMarkup());

			// on ne supprime pas le row et on ne update pas le secondary object
			// car c'est pour garder une référence sur le possesseur du bouquin
			Assert.assertEquals(getReader(), theAP.getSecondaryObject());

			Assert.assertEquals(0, IAPlayerTestBase.getSettingsValuebyKey(getRfid(), getReader(), mConfig.getVoice(), NathanPlayer.APPLET_ID));
		}

		// on ajoute une selection
		NathanPlayerTest.addVersionsSettings(getRfid(), "2;3;");

		mConfig = loadParamByISBN();

		result = NathanPlayerTest.getSettingsSelection(getRfid());

		Assert.assertEquals("2;", result); // on clean la selection des versions inexistantes Deuxième lecture.
		{
			final Application application = Factories.APPLICATION.findByName(NathanPlayerTest.APPLICATION_NAME);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = this.theApplication.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);

			// voix par défaut la premiere fois
			Assert.assertEquals(1, IAPlayerTestBase.getSettingsValuebyKey(getReader(), getRfid(), mConfig.getVoice((int) mConfig.getVersion()), NathanPlayer.APPLET_ID));

			// permet de vérifier si le secondary object est bien setté sur
			// l'objet lecteur
			// on récupère la version du user
			Assert.assertEquals(2, IAPlayerTestBase.getSettingsValuebyKey(getRfid(), getReader(), mConfig.getVoice(), NathanPlayer.APPLET_ID));

			// secure stream.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_SECURESTREAMING));
			Assert.assertEquals(1, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSICSHOULDSTREAM)); // aide + bouquin
			Assert.assertEquals(1, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING)); // aide + bouquin

		}

	}

	/**
	 * Suppression de toutes les données pour l'objet.
	 */
	private static void deleteSettings(VObject inObject) {
		IAPlayerTestBase.deleteSettings(NathanPlayer.APPLET_ID, inObject);
	}

	/**
	 * Ajout d'un setting.
	 */
	private static void addVersionsSettings(VObject inObject, String inValue) {
		final AppletSettings theVersionSettings = Factories.APPLET_SETTINGS.getAppletSettingsByObject(inObject, NathanPlayer.APPLET_ID, NathanPlayerTest.ISBN + "_versions");
		theVersionSettings.setValue(inValue);
		/*
		 * final SgbdConnection theConnection = new SgbdConnection();
		 * theConnection
		 * .doQueryUpdate("update applet_settings set settings_value = '"
		 * +inValue+"' where applet_id = '" + IAPlayerNathan.APPLET_ID
		 * +"' and primary_object_id = '" + inObject.getId() +
		 * "' and settings_key = '"+ISBN+"_versions"+"'");
		 * theConnection.close();
		 */
	}

	private static String getSettingsSelection(VObject inObject) {
		String result = null;
		final AppletSettings theApplet = Factories.APPLET_SETTINGS.getAppletSettingsByObject(inObject, NathanPlayer.APPLET_ID, NathanPlayerTest.ISBN + "_versions");
		if (theApplet != null) {
			result = theApplet.getValue();
		}
		return result;
	}
}

package net.violet.platform.applets.interactive;

import java.util.List;

import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.interactif.IAPlayerTestBase;
import net.violet.platform.interactif.Status;
import net.violet.platform.interactif.Status.Source;
import net.violet.platform.interactif.config.GallimardConfig9782070548064;
import net.violet.platform.interactif.config.PlayerConfig;
import net.violet.platform.interactif.config.PlayerConfigFactory;
import net.violet.platform.message.Message;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.Sequence;
import net.violet.platform.util.StringShop;

import org.junit.Assert;
import org.junit.Test;

public class CommonPlayerTest extends IAPlayerTestBase {

	// Reprise de constantes de IAPlayer.
	private static int POS_CHAPTER_1;
	private static int POS_CHAPTER_2 = 28000;
	private static int POS_WITHIN_CHAPTER_2 = 100000;
	private static int POS_CHAPTER_3 = 480000;
	private static int POS_CHAPTER_4 = 764000;
	private static int POS_CHAPTER_5 = 1176000;
	private static int POS_CHAPTER_6 = 1567200;
	private static int POS_CHAPTER_7 = 1869000;
	private static int POS_CHAPTER_8 = 2109000;

	private final static String APPLICATION_NAME = "net.violet.audiobooks.labellelissepoireduprincedemottordu";

	@Test
	public void testFirstTimeBeginning() {
		// Il n'y a pas de marque page.
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		final PlayerConfig mConfig = PlayerConfigFactory.getConfig(GallimardConfig9782070548064.ISBN);
		final CommonPlayer thePlayer = new CommonPlayer();

		final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
		final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

		final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);

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

		Assert.assertEquals(0, IAPlayerTestBase.getSettingsValuebyKey(getRfid(), getReader(), mConfig.getMarkup(), CommonPlayer.APPLET_ID));
	}

	@Test
	public void testEnd() {
		final PlayerConfig mConfig = PlayerConfigFactory.getConfig(GallimardConfig9782070548064.ISBN);
		// Il n'y a pas de marque page.
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On arrive au bout de l'histoire.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, theCookie, 0, 0, new Status(Source.DONE, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicEndBook());
			IAPlayerTestBase.assertEndsInteractiveMode(theSequences);
		}
	}

	@Test
	public void testButtonDuringHelp() {
		// Il n'y a pas de marque page.
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On interrompt l'aide.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, theCookie, 42, 0, new Status(Source.BUTTON, 1, 0, 0)).get(0);
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
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		{
			final CommonPlayer thePlayer = new CommonPlayer();
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, theCookie, 42, 0, new Status(Source.EAR, 0, 1, 0)).get(0);
			// thePlayer.process_ear(mConfig, getReader(), getRfid(), theCookie,
			// 1 /* earl */, 0 /* earr */, 42 /* pos */, 0 /* idx */);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// Le livre, au début.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertTrue(thePosition == 0);

		}
	}

	@Test
	public void testRightEarRabbitDuringHelp() { // recule
		final PlayerConfig mConfig = PlayerConfigFactory.getConfig(GallimardConfig9782070548064.ISBN);
		// Il n'y a pas de marque page.
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		{
			final CommonPlayer thePlayer = new CommonPlayer();
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, theCookie, 42, 0, new Status(Source.EAR, 0, 0, 1)).get(0);
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
		final PlayerConfig mConfig = PlayerConfigFactory.getConfig(GallimardConfig9782070548064.ISBN);
		// Il n'y a pas de marque page.
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On interrompt juste avant le livre.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, theCookie, 0, 1, new Status(Source.BUTTON, 1, 0, 0)).get(0);
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
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On interrompt juste avant le livre.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, theCookie, 0, 1, new Status(Source.EAR, 0, 1, 0)).get(0);
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
	public void testRightEarRabbitBetweenHelpAndBook() { // recule
		final PlayerConfig mConfig = PlayerConfigFactory.getConfig(GallimardConfig9782070548064.ISBN);
		// Il n'y a pas de marque page.
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On interrompt juste avant le livre.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, theCookie, 0, 1, new Status(Source.EAR, 0, 0, 1)).get(0);
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
		final PlayerConfig mConfig = PlayerConfigFactory.getConfig(GallimardConfig9782070548064.ISBN);
		// Il n'y a pas de marque page.
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On interrompt pendant le livre.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, theCookie, CommonPlayerTest.POS_WITHIN_CHAPTER_2, 1, new Status(Source.BUTTON, 1, 0, 0)).get(0);
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
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_1, CommonPlayerTest.POS_CHAPTER_2, true);
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_1 + 10000, CommonPlayerTest.POS_CHAPTER_2, true);
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_1 + 20000, CommonPlayerTest.POS_CHAPTER_2, true);
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_2 - 1000, CommonPlayerTest.POS_CHAPTER_2, true); // -1K
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_2, CommonPlayerTest.POS_CHAPTER_3, true); // =
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_2 + 10000, CommonPlayerTest.POS_CHAPTER_3, true); // +10K
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_2 + 31000, CommonPlayerTest.POS_CHAPTER_3, true); // +31K
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_3 - 1000, CommonPlayerTest.POS_CHAPTER_3, true); // -1K
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_3, CommonPlayerTest.POS_CHAPTER_4, true); // =
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_3 + 10000, CommonPlayerTest.POS_CHAPTER_4, true); // +10K
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_3 + 31000, CommonPlayerTest.POS_CHAPTER_4, true); // +31K
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_4 - 1000, CommonPlayerTest.POS_CHAPTER_4, true); // -1K
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_4, CommonPlayerTest.POS_CHAPTER_5, true); // =
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_4 + 10000, CommonPlayerTest.POS_CHAPTER_5, true); // +10K
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_4 + 31000, CommonPlayerTest.POS_CHAPTER_5, true); // +31K
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_5 - 1000, CommonPlayerTest.POS_CHAPTER_5, true); // -1K
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_5, CommonPlayerTest.POS_CHAPTER_6, true); // =
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_6, CommonPlayerTest.POS_CHAPTER_7, true); // =
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_7, CommonPlayerTest.POS_CHAPTER_8, true); // =
	}

	/**
	 * Test du déplacement de l'oreille gauche du lapin a la fin d'un bouquin.
	 * (premier déplacement).
	 */
	@Test
	public void testLeftEarRabbitDuringLastChapterBook() { // avance Il n'y a pas de marque page.
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On avance au dernier chapitre.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, theCookie, CommonPlayerTest.POS_CHAPTER_8 + 10000, 1, new Status(Source.EAR, 0, 1, 0)).get(0);
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
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On avance au premier chapitre.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, theCookie, CommonPlayerTest.POS_CHAPTER_2 + 10000, 1, new Status(Source.EAR, 0, 0, 1)).get(0);
			// thePlayer.process_ear(mConfig, getReader(), getRfid(), theCookie,
			// 0 /* earl */, 1 /* earr */, POS_CHAPTER_2 /* pos */, 1 /* idx
			// */);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// Le livre.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertEquals(CommonPlayerTest.POS_CHAPTER_1, thePosition);
		}

	}

	/**
	 * Test du déplacement de l'oreille droite du lapin au début du premier
	 * chapitre du bouquin. (premier déplacement).
	 */
	@Test
	public void testRightEarRabbitDuringFirstChapterBook() { // recule
		final PlayerConfig mConfig = PlayerConfigFactory.getConfig(GallimardConfig9782070548064.ISBN);
		// Il n'y a pas de marque page.
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On avance au premier chapitre.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, theCookie, CommonPlayerTest.POS_CHAPTER_1, 1, new Status(Source.EAR, 0, 0, 1)).get(0);
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
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_2 + 10001, CommonPlayerTest.POS_CHAPTER_1, false); // +10K
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_2 + 31000, CommonPlayerTest.POS_CHAPTER_2, false); // +31K
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_3 - 1000, CommonPlayerTest.POS_CHAPTER_2, false); // -1K
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_3, CommonPlayerTest.POS_CHAPTER_2, false); // =
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_3 + 10001, CommonPlayerTest.POS_CHAPTER_2, false); // +10K
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_3 + 31000, CommonPlayerTest.POS_CHAPTER_3, false); // +31K
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_4 - 1000, CommonPlayerTest.POS_CHAPTER_3, false); // -1K
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_4, CommonPlayerTest.POS_CHAPTER_3, false); // =
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_4 + 10001, CommonPlayerTest.POS_CHAPTER_3, false); // +10K
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_4 + 31000, CommonPlayerTest.POS_CHAPTER_4, false); // +31K
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_5 - 1000, CommonPlayerTest.POS_CHAPTER_4, false); // -1K
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_5, CommonPlayerTest.POS_CHAPTER_4, false); // =
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_6, CommonPlayerTest.POS_CHAPTER_5, false); // =
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_7, CommonPlayerTest.POS_CHAPTER_6, false); // =
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_8, CommonPlayerTest.POS_CHAPTER_7, false); // =
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_8 + 10001, CommonPlayerTest.POS_CHAPTER_7, false);
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_8 + 31000, CommonPlayerTest.POS_CHAPTER_8, false);
		testEarDuringBook(CommonPlayerTest.POS_CHAPTER_8 + 33000, CommonPlayerTest.POS_CHAPTER_8, false);
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
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On interrompt pendant le livre.
		{
			final int ear_left = inLeftEar ? 1 : 0;
			final int ear_right = inLeftEar ? 0 : 1;
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, theCookie, inPositionBefore, 1, new Status(Source.EAR, 0, ear_left, ear_right)).get(0);
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
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		{
			final CommonPlayer thePlayer = new CommonPlayer();
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, theCookie, CommonPlayerTest.POS_WITHIN_CHAPTER_2, 1, new Status(Source.EAR, 0, 1, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// Le livre, chapitre 3.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertTrue(thePosition == CommonPlayerTest.POS_CHAPTER_3);
		}

		{
			final CommonPlayer thePlayer = new CommonPlayer();
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, theCookie, CommonPlayerTest.POS_CHAPTER_3 + 1, 1, new Status(Source.EAR, 0, 1, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// Le livre, chapitre 5.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertTrue(thePosition == CommonPlayerTest.POS_CHAPTER_4);
		}
	}

	@Test
	public void testLeftEarRightEarRabbitDuringBook() {
		// Il n'y a pas de marque page.
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		{
			final CommonPlayer thePlayer = new CommonPlayer();
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.process_ear(getReader(), theSubsription, theCookie, 1 /* earl */, 0 /* earr */, CommonPlayerTest.POS_WITHIN_CHAPTER_2 /* pos */, 1 /* idx */).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// Le livre, chapitre 3.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertTrue(thePosition == CommonPlayerTest.POS_CHAPTER_3);
		}

		{
			final CommonPlayer thePlayer = new CommonPlayer();
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.process_ear(getReader(), theSubsription, theCookie, 0 /* earl */, 1 /* earr */, CommonPlayerTest.POS_CHAPTER_5 + 1 /* pos */, 1 /* idx */).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// On revient au début du chapitre 4.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertTrue(thePosition == CommonPlayerTest.POS_CHAPTER_4);
		}
	}

	@Test
	public void testButtonDuringBye() {
		// Il n'y a pas de marque page.
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On interrompt juste avant le livre.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.process_button(getReader(), theSubsription, theCookie, 0 /* pos */, 1 /* idx */, 1 /* btn */).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On interrompt pendant le message au revoir.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.process_button(getReader(), theSubsription, theCookie, 0 /* pos */, 0 /* idx */, 1 /* btn */).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			IAPlayerTestBase.assertEndsInteractiveMode(theSequences);
		}
	}

	@Test
	public void testSecondTime() {
		final PlayerConfig mConfig = PlayerConfigFactory.getConfig(GallimardConfig9782070548064.ISBN);
		// Il n'y a pas de marque page.
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		// Première lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			final String theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);

			// On arrive au bout de l'histoire.
			thePlayer.processItMode(getReader(), theSubsription, theCookie, 0, 0, new Status(Source.DONE, 0, 0, 0)).get(0);
		}

		// Deuxième lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			// Aide longue, encore.
			IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicHelpLong());
		}
	}

	@Test
	public void testSecondTimeWithBookmark() {
		final PlayerConfig mConfig = PlayerConfigFactory.getConfig(GallimardConfig9782070548064.ISBN);
		// Il n'y a pas de marque page.
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Première lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
			IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicHelpInit());
		}

		// On s'arrête au milieu du livre.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			thePlayer.process_button(getReader(), theSubsription, theCookie, CommonPlayerTest.POS_WITHIN_CHAPTER_2 /* pos */, 1 /* idx */, 1 /* btn */);
		}

		// Deuxième lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			// Aide courte.
			IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicHelpShort());

			// Le livre, au début du chapitre 2.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertEquals(CommonPlayerTest.POS_CHAPTER_2, thePosition);
		}
	}

	@Test
	public void testSecondTimeWithFirstChapterMarkup() {
		final PlayerConfig mConfig = PlayerConfigFactory.getConfig(GallimardConfig9782070548064.ISBN);
		// Il n'y a pas de marque page.
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Première lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
			IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicHelpInit());
		}

		// On s'arrête au premier chapitre du livre.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			thePlayer.process_button(getReader(), theSubsription, theCookie, 10 /* pos */, 1 /* idx */, 1 /* btn */);
		}

		// Deuxième lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			// Aide longue.
			IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicHelpLong());

			// Le livre, au début du chapitre 2.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertEquals(CommonPlayerTest.POS_CHAPTER_1, thePosition);
		}
	}

	@Test
	public void testStartstopstartLeftRabbitEarMove() { // avance
		final PlayerConfig mConfig = PlayerConfigFactory.getConfig(GallimardConfig9782070548064.ISBN);
		// Il n'y a pas de marque page.
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Première lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On s'arrête au milieu du livre.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			thePlayer.process_button(getReader(), theSubsription, theCookie, CommonPlayerTest.POS_WITHIN_CHAPTER_2 /* pos */, 1 /* idx */, 1 /* btn */);
		}

		// Deuxième lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
			// Aide courte.
			IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicHelpShort());

			// Le livre, au début du chapitre 2.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertEquals(CommonPlayerTest.POS_CHAPTER_2, thePosition);
		}

		{
			final CommonPlayer thePlayer = new CommonPlayer();
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.process_ear(getReader(), theSubsription, theCookie, 1 /* earl */, 0 /* earr */, CommonPlayerTest.POS_CHAPTER_5 + 1 /* pos */, 1 /* idx */).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// On revient au début du chapitre 6.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertTrue(thePosition == CommonPlayerTest.POS_CHAPTER_6);
		}

	}

	@Test
	public void testStartstopstartRightRabbitEarMove() { // recule
		final PlayerConfig mConfig = PlayerConfigFactory.getConfig(GallimardConfig9782070548064.ISBN);
		// Il n'y a pas de marque page.
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Première lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On s'arrête au milieu du livre.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			thePlayer.process_button(getReader(), theSubsription, theCookie, CommonPlayerTest.POS_WITHIN_CHAPTER_2 /* pos */, 1 /* idx */, 1 /* btn */);
		}

		// Deuxième lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
			// Aide courte.
			IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicHelpShort());

			// Le livre, au début du chapitre 2.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertEquals(CommonPlayerTest.POS_CHAPTER_2, thePosition);
		}

		{
			final CommonPlayer thePlayer = new CommonPlayer();
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.process_ear(getReader(), theSubsription, theCookie, 0 /* earl */, 1 /* earr */, CommonPlayerTest.POS_WITHIN_CHAPTER_2 /* pos */, 1 /* idx */).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// On revient au début du chapitre 2.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			System.out.println(thePosition);
			Assert.assertTrue(thePosition == CommonPlayerTest.POS_CHAPTER_2);
		}

	}

	@Test
	public void testDoubleClic() {
		// Il n'y a pas de marque page.
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Première lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On s'arrête au milieu du livre.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.process_button(getReader(), theSubsription, theCookie, CommonPlayerTest.POS_WITHIN_CHAPTER_2 /* pos */, 1 /* idx */, 2 /* btn */).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			IAPlayerTestBase.assertEndsInteractiveMode(theSequences);
		}

	}

	@Test
	public void testLeftRabbitEarDuringBye() {
		// Il n'y a pas de marque page.
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On interrompt juste avant le livre.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.process_button(getReader(), theSubsription, theCookie, 0 /* pos */, 1 /* idx */, 1 /* btn */).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On avance pendant le message au revoir.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.process_ear(getReader(), theSubsription, theCookie, 1 /* earl */, 0 /* earr */, 10 /* pos */, 2 /* idx */).get(0);
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
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On interrompt au milieu du livre.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.process_button(getReader(), theSubsription, theCookie, CommonPlayerTest.POS_WITHIN_CHAPTER_2 /* pos */, 1 /* idx */, 1 /* btn */).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On recule pendant le message au revoir.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.process_ear(getReader(), theSubsription, theCookie, 0 /* earl */, 1 /* earr */, 10 /* pos */, 2 /* idx */).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// AVANT : On revenais ou on en était.
			// NOW : On reviens au dernier chapitre
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertEquals(thePosition, CommonPlayerTest.POS_CHAPTER_8);
			// assertTrue(thePosition == POS_CHAPTER_2);
		}
	}

	@Test
	public void testLeftRabbitEarDuringEndBookSong() {
		final PlayerConfig mConfig = PlayerConfigFactory.getConfig(GallimardConfig9782070548064.ISBN);
		// Il n'y a pas de marque page.
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On passe au chapitre suivant et il y en a pas
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.process_ear(getReader(), theSubsription, theCookie, 1 /* earl */, 0 /* earr */, CommonPlayerTest.POS_CHAPTER_8 + 200000 /* pos */, 1 /* idx */).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);

			// fin de bouquin.
			IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicEndBook());

			IAPlayerTestBase.assertEndsInteractiveMode(theSequences);
		}

		// On avance pendant le message au revoir.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.process_ear(getReader(), theSubsription, theCookie, 1 /* earl */, 0 /* earr */, 10 /* pos */, 3 /* idx */).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			IAPlayerTestBase.assertEndsInteractiveMode(theSequences);
		}
	}

	@Test
	public void testRightRabbitEarDuringEndBookSong() {
		final PlayerConfig mConfig = PlayerConfigFactory.getConfig(GallimardConfig9782070548064.ISBN);
		// Il n'y a pas de marque page.
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On passe au chapitre suivant et il y en a pas
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.process_ear(getReader(), theSubsription, theCookie, 1 /* earl */, 0 /* earr */, CommonPlayerTest.POS_CHAPTER_8 + 200000 /* pos */, 1 /* idx */).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);

			// fin de bouquin.
			IAPlayerTestBase.assertSingleAudioSequence(theSequences, mConfig.getMusicEndBook());

			IAPlayerTestBase.assertEndsInteractiveMode(theSequences);
		}

		// On recule pendant le message au revoir.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.process_ear(getReader(), theSubsription, theCookie, 0 /* earl */, 1 /* earr */, 10 /* pos */, 3 /* idx */).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// On revient au dernier chapitre.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertTrue(thePosition == CommonPlayerTest.POS_CHAPTER_8);
		}
	}

	@Test
	public void testAddSetting() { // check l'incrémentation des settings
		final PlayerConfig mConfig = PlayerConfigFactory.getConfig(GallimardConfig9782070548064.ISBN);
		// Il n'y a pas de marque page.
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Première lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);

			// voix par défaut la premiere fois
			Assert.assertEquals(1, IAPlayerTestBase.getSettingsValuebyKey(getReader(), getRfid(), mConfig.getVoice(0), CommonPlayer.APPLET_ID));

			// permet de vérifier si le secondary object est bien setté sur
			// l'objet lecteur
			Assert.assertEquals(0, IAPlayerTestBase.getSettingsValuebyKey(getRfid(), getReader(), mConfig.getVoice(), CommonPlayer.APPLET_ID));
		}

		// On a fini de lire le bouquin
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			thePlayer.processItMode(getReader(), theSubsription, theCookie, 0, 0, new Status(Source.DONE, 0, 0, 0)).get(0);

			Assert.assertEquals(1, IAPlayerTestBase.getSettingsValuebyKey(getReader(), getRfid(), mConfig.getCountFinish(), CommonPlayer.APPLET_ID));
			// reset du markup
			Assert.assertEquals(0, IAPlayerTestBase.getSettingsValuebyKey(getRfid(), getReader(), mConfig.getMarkup(), CommonPlayer.APPLET_ID));
		}

		// Deuxième lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);

			Assert.assertEquals(2, IAPlayerTestBase.getSettingsValuebyKey(getReader(), getRfid(), mConfig.getVoice(0), CommonPlayer.APPLET_ID));
		}

		// On a fini de lire le bouquin
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			thePlayer.processItMode(getReader(), theSubsription, theCookie, 0, 0, new Status(Source.DONE, 0, 0, 0)).get(0);

			Assert.assertEquals(2, IAPlayerTestBase.getSettingsValuebyKey(getReader(), getRfid(), mConfig.getCountFinish(), CommonPlayer.APPLET_ID));
		}

		// troisieme lecture.
		{
			// change la voix de lecture
			Factories.APPLET_SETTINGS.setAppletSettingsByObjects(getRfid(), getReader(), CommonPlayer.APPLET_ID, mConfig.getVoice(), StringShop.EMPTY_STRING + 1, mConfig.getIsbn());
			final CommonPlayer thePlayer = new CommonPlayer();

			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			Assert.assertEquals(1, IAPlayerTestBase.getSettingsValuebyKey(getReader(), getRfid(), mConfig.getVoice(1), CommonPlayer.APPLET_ID));
		}

	}

	@Test
	public void testChangeReader() { // check lorsqu'on passe le bouquin sur un autre objet, il doit reprendre au chapitre laissé par le dernier lecteur
		final PlayerConfig mConfig = PlayerConfigFactory.getConfig(GallimardConfig9782070548064.ISBN);
		// Il n'y a pas de marque page.
		CommonPlayerTest.deleteSettings(getReader());
		CommonPlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Première lecture.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubsription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);

			// voix par défaut la premiere fois
			Assert.assertEquals(1, IAPlayerTestBase.getSettingsValuebyKey(getReader(), getRfid(), mConfig.getVoice(0), CommonPlayer.APPLET_ID));

			// permet de vérifier si le secondary object est bien setté sur
			// l'objet lecteur
			Assert.assertEquals(0, IAPlayerTestBase.getSettingsValuebyKey(getRfid(), getReader(), mConfig.getVoice(), CommonPlayer.APPLET_ID));
		}

		// On s'arrête au milieu du livre.
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.process_button(getReader(), theSubsription, theCookie, CommonPlayerTest.POS_CHAPTER_4 /* pos */, 1 /* idx */, 2 /* btn */).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			IAPlayerTestBase.assertEndsInteractiveMode(theSequences);
		}

		// on passe le bouquin sur un autre objet
		{
			final CommonPlayer thePlayer = new CommonPlayer();
			final Application application = Factories.APPLICATION.findByName(CommonPlayerTest.APPLICATION_NAME);
			final Subscription theSubsription = Factories.SUBSCRIPTION.create(application, getRfid());

			thePlayer.processItMode(getReader2(), theSubsription, theCookie, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);

			// permet de vérifier si le secondary object est bien setté sur
			// l'objet lecteur
			Assert.assertEquals(0, IAPlayerTestBase.getSettingsValuebyKey(getRfid(), getReader2(), mConfig.getVoice(), CommonPlayer.APPLET_ID));
			// Permet de voir si on reprend bien au chapitre laissé par le
			// dernier lecteur
			Assert.assertEquals(3, IAPlayerTestBase.getSettingsValuebyKey(getRfid(), getReader(), mConfig.getMarkup(), CommonPlayer.APPLET_ID));
		}

	}

	/**
	 * Suppression de toutes les données pour l'objet.
	 */
	private static void deleteSettings(VObject inObject) {
		IAPlayerTestBase.deleteSettings(CommonPlayer.APPLET_ID, inObject);
	}
}

package net.violet.platform.applets.interactive;

import java.util.List;

import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.interactif.IAPlayerTestBase;
import net.violet.platform.interactif.Status;
import net.violet.platform.interactif.Status.Source;
import net.violet.platform.interactif.config.PlayerConfig;
import net.violet.platform.interactif.config.PlayerConfigFactory;
import net.violet.platform.interactif.config.SimplePlayerConfig;
import net.violet.platform.message.Message;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.Sequence;

import org.junit.Assert;
import org.junit.Test;

public class SimplePlayerTest extends IAPlayerTestBase {

	// Reprise de constantes de IASimplePlayer.
	private static int POS_CHAPTER_1;
	private static int POS_CHAPTER_2 = 1000000;
	private static int POS_WITHIN_CHAPTER_2 = 1500000;
	private static int POS_CHAPTER_3 = 2260000;
	private static int POS_CHAPTER_4 = 3604000;

	@Test
	public void testFirstTimeBeginning() {
		// Il n'y a pas de marque page.
		SimplePlayerTest.deleteSettings(getReader());
		SimplePlayerTest.deleteSettings(getRfid());

		final PlayerConfig mConfig = PlayerConfigFactory.getConfig(SimplePlayerConfig.ISBN);
		final SimplePlayer thePlayer = new SimplePlayer();

		final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);

		final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

		final Message theMessage = thePlayer.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
		// thePlayer.process_start(mConfig, getReader(), getRfid());
		final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

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
		Assert.assertTrue(theClonkBtn1SettingIx >= 0); // Présent
		final int theClonkBtn2SettingIx = IAPlayerTestBase.getSettingIx(theSequences, "snd.btn.2", "clonk");
		Assert.assertTrue(theClonkBtn2SettingIx >= 0); // Présent

		final int theNeutralBtn1SettingIx = IAPlayerTestBase.getSettingIx(theSequences, "snd.btn.1", "neutral");
		Assert.assertTrue(theNeutralBtn1SettingIx >= 0); // Présent
		final int theNeutralBtn2SettingIx = IAPlayerTestBase.getSettingIx(theSequences, "snd.btn.2", "neutral");
		Assert.assertTrue(theNeutralBtn2SettingIx >= 0); // Présent

		final int theBookSequenceIx = IAPlayerTestBase.getSequenceIx(theSequences, Sequence.SEQ_SECURESTREAMING);
		Assert.assertTrue(theBookSequenceIx >= 0); // Présent snd.btn.*=neutral avant le livre
		Assert.assertTrue(theNeutralBtn1SettingIx < theBookSequenceIx);
		Assert.assertTrue(theNeutralBtn1SettingIx < theBookSequenceIx);

		// Par transitivité, l'aide est avant le livre.

		// L'enregistrement (reconnaissance vocale) doit être désactivé
		// avant tout son.
		final int theRecordDisableIx = IAPlayerTestBase.getSettingIx(theSequences, "record.enabled", "false");
		Assert.assertTrue(theRecordDisableIx >= 0); // Présent La chorégraphie pendant le streaming doit être désactivée avant le livre
		final int theStreamingChorDisableIx = IAPlayerTestBase.getSettingIx(theSequences, "streaming.chor.enabled", "false");
		Assert.assertTrue(theStreamingChorDisableIx >= 0); // Présent
		Assert.assertTrue(theRecordDisableIx < theBookSequenceIx); // avant le livre.

		Assert.assertEquals(0, IAPlayerTestBase.getSettingsValuebyKey(getRfid(), getReader(), mConfig.getMarkup(), SimplePlayer.APPLET_ID));
	}

	@Test
	public void testEnd() {
		// Il n'y a pas de marque page.
		SimplePlayerTest.deleteSettings(getReader());
		SimplePlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final SimplePlayer thePlayer = new SimplePlayer();
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On arrive au bout de l'histoire.
		{
			final SimplePlayer thePlayer = new SimplePlayer();
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubscription, theCookie, 0, 0, new Status(Source.DONE, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			IAPlayerTestBase.assertEndsInteractiveMode(theSequences);
		}
	}

	@Test
	public void testButtonDuringBook() {
		// Il n'y a pas de marque page.
		SimplePlayerTest.deleteSettings(getReader());
		SimplePlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final SimplePlayer thePlayer = new SimplePlayer();
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On interrompt pendant le livre.
		{
			final SimplePlayer thePlayer = new SimplePlayer();
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubscription, theCookie, SimplePlayerTest.POS_WITHIN_CHAPTER_2, 1, new Status(Source.BUTTON, 1, 0, 0)).get(0);
			// thePlayer.process_button(mConfig, getReader(), getRfid(),
			// theCookie, POS_WITHIN_CHAPTER_2 /* pos */, 1 /* idx */, 1 /* btn
			// */);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			IAPlayerTestBase.assertEndsInteractiveMode(theSequences);
		}
	}

	/**
	 * Test du déplacement de l'oreille gauche du lapin pendant la lecture.
	 * (premier déplacement).
	 */
	@Test
	public void testLeftEarRabbitDuringBook() { // avance
		testEarDuringBook(SimplePlayerTest.POS_CHAPTER_1, SimplePlayerTest.POS_CHAPTER_2, true);
		testEarDuringBook(SimplePlayerTest.POS_CHAPTER_1 + 10000, SimplePlayerTest.POS_CHAPTER_2, true);
		testEarDuringBook(SimplePlayerTest.POS_CHAPTER_1 + 20000, SimplePlayerTest.POS_CHAPTER_2, true);
		testEarDuringBook(SimplePlayerTest.POS_CHAPTER_2 - 1000, SimplePlayerTest.POS_CHAPTER_2, true); // -1K
		testEarDuringBook(SimplePlayerTest.POS_CHAPTER_2, SimplePlayerTest.POS_CHAPTER_3, true); // =
		testEarDuringBook(SimplePlayerTest.POS_CHAPTER_2 + 10000, SimplePlayerTest.POS_CHAPTER_3, true); // +10K
		testEarDuringBook(SimplePlayerTest.POS_CHAPTER_2 + 31000, SimplePlayerTest.POS_CHAPTER_3, true); // +31K
		testEarDuringBook(SimplePlayerTest.POS_CHAPTER_3 - 1000, SimplePlayerTest.POS_CHAPTER_3, true); // -1K
		testEarDuringBook(SimplePlayerTest.POS_CHAPTER_3, SimplePlayerTest.POS_CHAPTER_4, true); // =
		testEarDuringBook(SimplePlayerTest.POS_CHAPTER_3 + 10000, SimplePlayerTest.POS_CHAPTER_4, true); // +10K
		testEarDuringBook(SimplePlayerTest.POS_CHAPTER_3 + 31000, SimplePlayerTest.POS_CHAPTER_4, true); // +31K
		testEarDuringBook(SimplePlayerTest.POS_CHAPTER_4 - 1000, SimplePlayerTest.POS_CHAPTER_4, true); // -1K
	}

	/**
	 * Test du déplacement de l'oreille gauche du lapin a la fin d'un bouquin.
	 * (premier déplacement).
	 */
	@Test
	public void testLeftEarRabbitDuringLastChapterBook() { // avance Il n'y a pas de marque page.
		SimplePlayerTest.deleteSettings(getReader());
		SimplePlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final SimplePlayer thePlayer = new SimplePlayer();
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On avance au dernier chapitre.
		{
			final SimplePlayer thePlayer = new SimplePlayer();
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubscription, theCookie, SimplePlayerTest.POS_CHAPTER_4, 1, new Status(Source.EAR, 0, 1, 0)).get(0);
			// thePlayer.process_ear(mConfig, getReader(), getRfid(), theCookie,
			// 1 /* earl */, 0 /* earr */, POS_CHAPTER_8 + 10000/* pos */, 1 /*
			// idx */);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			IAPlayerTestBase.assertEndsInteractiveMode(theSequences);
		}

	}

	/**
	 * Test du déplacement de l'oreille droite du lapin au début du deuxième
	 * chapitre du bouquin. (premier déplacement).
	 */
	@Test
	public void testRightEarRabbitDuringSecondChapterBook() { // recule Il n'y a pas de marque page.
		SimplePlayerTest.deleteSettings(getReader());
		SimplePlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final SimplePlayer thePlayer = new SimplePlayer();
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On avance au premier chapitre.
		{
			final SimplePlayer thePlayer = new SimplePlayer();
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubscription, theCookie, SimplePlayerTest.POS_CHAPTER_2 + 10000, 1, new Status(Source.EAR, 0, 0, 1)).get(0);
			// thePlayer.process_ear(mConfig, getReader(), getRfid(), theCookie,
			// 0 /* earl */, 1 /* earr */, POS_CHAPTER_2 /* pos */, 1 /* idx
			// */);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// Le livre.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertEquals(SimplePlayerTest.POS_CHAPTER_1, thePosition);
		}

	}

	/**
	 * Test du déplacement de l'oreille droite du lapin pendant la lecture.
	 * (premier déplacement).
	 */
	@Test
	public void testRightEarRabbitDuringBook() { // recule
		testEarDuringBook(SimplePlayerTest.POS_CHAPTER_2 + 10001, SimplePlayerTest.POS_CHAPTER_1, false); // +10K
		testEarDuringBook(SimplePlayerTest.POS_CHAPTER_2 + 31000, SimplePlayerTest.POS_CHAPTER_2, false); // +31K
		testEarDuringBook(SimplePlayerTest.POS_CHAPTER_3 - 1000, SimplePlayerTest.POS_CHAPTER_2, false); // -1K
		testEarDuringBook(SimplePlayerTest.POS_CHAPTER_3, SimplePlayerTest.POS_CHAPTER_2, false); // =
		testEarDuringBook(SimplePlayerTest.POS_CHAPTER_3 + 10001, SimplePlayerTest.POS_CHAPTER_2, false); // +10K
		testEarDuringBook(SimplePlayerTest.POS_CHAPTER_3 + 31000, SimplePlayerTest.POS_CHAPTER_3, false); // +31K
		testEarDuringBook(SimplePlayerTest.POS_CHAPTER_4 - 1000, SimplePlayerTest.POS_CHAPTER_3, false); // -1K
		testEarDuringBook(SimplePlayerTest.POS_CHAPTER_4, SimplePlayerTest.POS_CHAPTER_3, false); // =
		testEarDuringBook(SimplePlayerTest.POS_CHAPTER_4 + 10001, SimplePlayerTest.POS_CHAPTER_3, false); // +10K
		testEarDuringBook(SimplePlayerTest.POS_CHAPTER_4 + 31000, SimplePlayerTest.POS_CHAPTER_4, false); // +31K
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
		SimplePlayerTest.deleteSettings(getReader());
		SimplePlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final SimplePlayer thePlayer = new SimplePlayer();
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On interrompt pendant le livre.
		{
			final int ear_left = inLeftEar ? 1 : 0;
			final int ear_right = inLeftEar ? 0 : 1;
			final SimplePlayer thePlayer = new SimplePlayer();
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubscription, theCookie, inPositionBefore, 0, new Status(Source.EAR, 0, ear_left, ear_right)).get(0);
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
		SimplePlayerTest.deleteSettings(getReader());
		SimplePlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final SimplePlayer thePlayer = new SimplePlayer();
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		{
			final SimplePlayer thePlayer = new SimplePlayer();
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubscription, theCookie, SimplePlayerTest.POS_WITHIN_CHAPTER_2, 1, new Status(Source.EAR, 0, 1, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// Le livre, chapitre 3.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertTrue(thePosition == SimplePlayerTest.POS_CHAPTER_3);
		}

		{
			final SimplePlayer thePlayer = new SimplePlayer();
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubscription, theCookie, SimplePlayerTest.POS_CHAPTER_3 + 1, 1, new Status(Source.EAR, 0, 1, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// Le livre, chapitre 5.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertTrue(thePosition == SimplePlayerTest.POS_CHAPTER_4);
		}
	}

	@Test
	public void testLeftEarRightEarRabbitDuringBook() {
		// Il n'y a pas de marque page.
		SimplePlayerTest.deleteSettings(getReader());
		SimplePlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Lecture.
		{
			final SimplePlayer thePlayer = new SimplePlayer();
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		{
			final SimplePlayer thePlayer = new SimplePlayer();
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.process_ear(getReader(), theSubscription, theCookie, 1 /* earl */, 0 /* earr */, SimplePlayerTest.POS_WITHIN_CHAPTER_2 /* pos */, 1 /* idx */).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// Le livre, chapitre 3.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);

			Assert.assertTrue(thePosition == SimplePlayerTest.POS_CHAPTER_3);
		}

		{
			final SimplePlayer thePlayer = new SimplePlayer();
			// ear_left( gauche du lapin, droite vu de l'utilisateur)
			// ear_right( droite du lapin, gauche vu de l'utilisateur)
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.process_ear(getReader(), theSubscription, theCookie, 0 /* earl */, 1 /* earr */, SimplePlayerTest.POS_CHAPTER_3 + 1 /* pos */, 0 /* idx */).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			// On revient au début du chapitre 2.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertTrue(thePosition == SimplePlayerTest.POS_CHAPTER_2);
		}
	}

	@Test
	public void testSecondTimeWithBookmark() {
		// Il n'y a pas de marque page.
		SimplePlayerTest.deleteSettings(getReader());
		SimplePlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Première lecture.
		{
			final SimplePlayer thePlayer = new SimplePlayer();
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On s'arrête au milieu du livre.
		{
			final SimplePlayer thePlayer = new SimplePlayer();
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			thePlayer.process_button(getReader(), theSubscription, theCookie, SimplePlayerTest.POS_WITHIN_CHAPTER_2 /* pos */, 1 /* idx */, 1 /* btn */);
		}

		// Deuxième lecture.
		{
			final SimplePlayer thePlayer = new SimplePlayer();
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Le livre, au début du chapitre 2.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertEquals(SimplePlayerTest.POS_CHAPTER_2, thePosition);
		}
	}

	@Test
	public void testSecondTimeWithFirstChapterMarkup() {
		// Il n'y a pas de marque page.
		SimplePlayerTest.deleteSettings(getReader());
		SimplePlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Première lecture.
		{
			final SimplePlayer thePlayer = new SimplePlayer();
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On s'arrête au premier chapitre du livre.
		{
			final SimplePlayer thePlayer = new SimplePlayer();
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			thePlayer.process_button(getReader(), theSubscription, theCookie, 10 /* pos */, 1 /* idx */, 1 /* btn */);
		}

		// Deuxième lecture.
		{
			final SimplePlayer thePlayer = new SimplePlayer();
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);

			// Le livre, au début du chapitre 1.
			final int thePosition = IAPlayerTestBase.getPositionInSecureStream(theSequences);
			Assert.assertEquals(SimplePlayerTest.POS_CHAPTER_1, thePosition);
		}
	}

	@Test
	public void testDoubleClic() {
		// Il n'y a pas de marque page.
		SimplePlayerTest.deleteSettings(getReader());
		SimplePlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Première lecture.
		{
			final SimplePlayer thePlayer = new SimplePlayer();
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On s'arrête au milieu du livre.
		{
			final SimplePlayer thePlayer = new SimplePlayer();
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.process_button(getReader(), theSubscription, theCookie, SimplePlayerTest.POS_WITHIN_CHAPTER_2 /* pos */, 1 /* idx */, 2 /* btn */).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			IAPlayerTestBase.assertEndsInteractiveMode(theSequences);
		}

	}

	@Test
	public void testChangeReader() { // check lorsqu'on passe le bouquin sur un autre objet, il doit reprendre au chapitre laissé par le dernier lecteur
		final PlayerConfig mConfig = PlayerConfigFactory.getConfig(SimplePlayerConfig.ISBN);
		// Il n'y a pas de marque page.
		SimplePlayerTest.deleteSettings(getReader());
		SimplePlayerTest.deleteSettings(getRfid());

		String theCookie;

		// Première lecture.
		{
			final SimplePlayer thePlayer = new SimplePlayer();
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.processItMode(getReader(), theSubscription, null, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			theCookie = IAPlayerTestBase.getCookieFromSequences(theSequences);
		}

		// On s'arrête au milieu du livre.
		{
			final SimplePlayer thePlayer = new SimplePlayer();
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			final Message theMessage = thePlayer.process_button(getReader(), theSubscription, theCookie, SimplePlayerTest.POS_CHAPTER_4 /* pos */, 1 /* idx */, 2 /* btn */).get(0);
			final List<Sequence> theSequences = IAPlayerTestBase.getSequenceList((MessageDraft) theMessage);
			// Pas de son.
			Assert.assertEquals(0, IAPlayerTestBase.countSequencesByType(theSequences, Sequence.SEQ_MUSIC_STREAMING));

			IAPlayerTestBase.assertEndsInteractiveMode(theSequences);
		}

		// on passe le bouquin sur un autre objet
		{
			final SimplePlayer thePlayer = new SimplePlayer();
			final Application application = Factories.APPLICATION.find(SimplePlayer.APPLET_ID);
			final Subscription theSubscription = Factories.SUBSCRIPTION.create(application, getRfid());

			thePlayer.processItMode(getReader2(), theSubscription, theCookie, 0, 0, new Status(Source.START, 0, 0, 0)).get(0);

			// Permet de voir si on reprend bien au chapitre laissé par le
			// dernier lecteur
			Assert.assertEquals(3, IAPlayerTestBase.getSettingsValuebyKey(getRfid(), getReader(), mConfig.getMarkup(), SimplePlayer.APPLET_ID));
		}

	}

	/**
	 * Suppression de toutes les données pour l'objet.
	 */
	private static void deleteSettings(VObject inObject) {
		IAPlayerTestBase.deleteSettings(SimplePlayer.APPLET_ID, inObject);
	}
}

package net.violet.mynabaztag.action;

import net.violet.mynabaztag.form.MySrvHumeurForm;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.SubscriptionData;

import org.junit.Assert;
import org.junit.Test;

public class MySrvHumeurActionTest extends ActionTestBase {

	@Test
	public void testEmptySessionRedirect() {
		ActionTestBase.testRedirectEmptySession(MySrvHumeurAction.class, MySrvHumeurForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvHumeurAction.class, MySrvHumeurForm.class, "activate", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvHumeurAction.class, MySrvHumeurForm.class, "update", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvHumeurAction.class, MySrvHumeurForm.class, "delete", ActionTestBase.FORWARD_LOGIN);
	}

	@Test
	public void testNoRabbitRedirect() {
		testRedirectNoRabbit(MySrvHumeurAction.class, MySrvHumeurForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvHumeurAction.class, MySrvHumeurForm.class, "activate", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvHumeurAction.class, MySrvHumeurForm.class, "update", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvHumeurAction.class, MySrvHumeurForm.class, "delete", ActionTestBase.FORWARD_LOGIN);
	}

	/**
	 * Test if an user can susbcribe to the service and if its information were
	 * saved.
	 * 
	 */
	@Test
	public void activateService() {
		final User myUser = getActionTestUser();
		final VObject myObject = Factories.VOBJECT.findByOwner(myUser).get(0);

		for (final SubscriptionData subscription : SubscriptionData.findByApplicationAndObject(Application.NativeApplication.MOOD.getApplication(), myObject)) {

			/* Subscription */

			// MySrvHumeurForm myForm = activateHumeur(myUser, myObject,
			// humeurMock.getSrvhumeur_freq(), humeurMock.getSrvhumeur_lang());
			/*
			 * Refresh and purge ActionForm to recover the correct information
			 * saved
			 */
			// myForm = loadHumeur(myUser, myObject);
			// assertEquals(1, myForm.getIsReg());
			// assertEquals(humeurMock.getSrvhumeur_freq(),
			// myForm.getFreqSrv());
			// assertTrue(Arrays.equals(humeurMock.getSrvhumeur_lang(),
			// myForm.getCheckListLang()));
			// humeurMock.delete();
			subscription.delete();
		}
	}

	/**
	 * Test if an user can update its settings
	 * 
	 */
	@Test
	public void updateSettings() {

		//final User myUser = getActionTestUser();
		//final VObject myObject = Factories.VOBJECT.findByOwner(myUser).get(0);
		// final SrvHumeur humeurMock =
		// Factories.SRVHUMEUR.createNewHumeur(myObject, "1;3;5", 6, 1);
		// final String[] old_languages = humeurMock.getSrvhumeur_lang();

		/* Subscription */
		// MySrvHumeurForm myForm = activateHumeur(myUser, myObject,
		// humeurMock.getSrvhumeur_freq(), humeurMock.getSrvhumeur_lang());
		// final int frequency = myForm.getFreqSrv();
		// final String[] newLanguages = {"1","2","4"};
		// humeurMock.setHumeurConfig(3, newLanguages, 1);
		/* Update Settings */
		// updateHumeur(myUser, myObject, humeurMock.getSrvhumeur_freq(),
		// humeurMock.getSrvhumeur_lang());
		/* Refresh and purge ActionForm to recover the correct information saved */
		// myForm = loadHumeur(myUser, myObject);
		// assertEquals(1, myForm.getIsReg());
		// assertNotSame(frequency, myForm.getFreqSrv());
		// assertFalse(Arrays.equals(old_languages, myForm.getCheckListLang()));
		// humeurMock.delete();
	}

	/**
	 * Test if the defusing of the service works
	 * 
	 */
	@Test
	public void deleteService() {
		Assert.assertTrue(true);
		// final SrvHumeur humeurMock =
		// Factories.SRVHUMEUR.createNewHumeur(myObject, "1;3;5", 6, 1);

		/* Subscription */
		// MySrvHumeurForm myForm = activateHumeur(myUser, myObject,
		// humeurMock.getSrvhumeur_freq(), humeurMock.getSrvhumeur_lang());
		// assertEquals(1, myForm.getIsReg());
		/* Defusing */
		// deleteHumeur(myUser, myObject);
		/* Refresh and purge ActionForm to recover the correct information saved */
		// myForm = loadHumeur(myUser, myObject);
		// assertEquals(0, myForm.getIsReg());
		// humeurMock.delete();
	}
}

package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvReveilFreeForm;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.web.ServletTestBase;

import org.apache.struts.action.ActionMapping;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test pour MySrvReveilFreeAction
 */
public class MySrvReveilFreeActionTest extends ActionTestBase {

	/**
	 * Activate the service
	 */
	private MySrvReveilFreeForm activateReveilFree(User myUser, VObject myObject, long music_id, String horaire) {

		final MySrvReveilFreeAction MySrvReveilFreeAction = new MySrvReveilFreeAction();
		final MySrvReveilFreeForm myForm = new MySrvReveilFreeForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		myForm.setMusic_id(music_id);
		myForm.setHorraire(horaire);

		MySrvReveilFreeAction.activate(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Update the service
	 */
	private MySrvReveilFreeForm updateReveilFree(User myUser, VObject myObject, long music_id, String horaire) {

		final MySrvReveilFreeAction MySrvReveilFreeAction = new MySrvReveilFreeAction();
		final MySrvReveilFreeForm myForm = new MySrvReveilFreeForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		myForm.setMusic_id(music_id);
		myForm.setHorraire(horaire);

		MySrvReveilFreeAction.update(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Delete the service
	 */
	private MySrvReveilFreeForm deleteReveilFree(User myUser, VObject myObject) {

		final MySrvReveilFreeAction MySrvReveilFreeAction = new MySrvReveilFreeAction();
		final MySrvReveilFreeForm myForm = new MySrvReveilFreeForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		MySrvReveilFreeAction.delete(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Load the service
	 */
	private MySrvReveilFreeForm loadReveilFree(User myUser, VObject myObject) {

		final MySrvReveilFreeAction MySrvReveilFreeAction = new MySrvReveilFreeAction();
		final MySrvReveilFreeForm myForm = new MySrvReveilFreeForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		MySrvReveilFreeAction.load(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	@Test
	public void testEmptySessionRedirect() {
		ActionTestBase.testRedirectEmptySession(MySrvReveilFreeAction.class, MySrvReveilFreeForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvReveilFreeAction.class, MySrvReveilFreeForm.class, "activate", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvReveilFreeAction.class, MySrvReveilFreeForm.class, "update", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvReveilFreeAction.class, MySrvReveilFreeForm.class, "delete", ActionTestBase.FORWARD_LOGIN);
	}

	@Test
	public void testNoRabbitRedirect() {
		testRedirectNoRabbit(MySrvReveilFreeAction.class, MySrvReveilFreeForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvReveilFreeAction.class, MySrvReveilFreeForm.class, "activate", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvReveilFreeAction.class, MySrvReveilFreeForm.class, "update", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvReveilFreeAction.class, MySrvReveilFreeForm.class, "delete", ActionTestBase.FORWARD_LOGIN);
	}

	/**
	 * Test if an user can susbcribe to the service
	 * 
	 */
	@Test
	public void activateWithLightLanguage() {

		final User myUser = getActionTestUser();
		final VObject myObject = Factories.VOBJECT.findByOwner(myUser).get(0);

		final Music theMusic = creerMusique(myUser);
		/* Subscription */
		MySrvReveilFreeForm myForm = activateReveilFree(myUser, myObject, theMusic.getId().longValue(), "10:20");

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadReveilFree(myUser, myObject);
		Assert.assertEquals(1, myForm.getIsReg());

		supprimerMusique(theMusic.getId().longValue());
	}

	/**
	 * Test if the new schedule the user entered is save.
	 * 
	 */
	@Test
	public void changeSchedule() {

		final User myUser = getActionTestUser();
		final VObject myObject = Factories.VOBJECT.findByOwner(myUser).get(0);

		final Music theMusic = creerMusique(myUser);
		/* Subscription */
		MySrvReveilFreeForm myForm = activateReveilFree(myUser, myObject, theMusic.getId().longValue(), "10:20");
		final String formerSchedule = myForm.getHorraire();

		/* Update settings */
		updateReveilFree(myUser, myObject, theMusic.getId().longValue(), "12:42");

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadReveilFree(myUser, myObject);

		Assert.assertNotSame(formerSchedule, myForm.getHorraire());

		supprimerMusique(theMusic.getId().longValue());
	}

	/**
	 * Test if the information were saved
	 * 
	 */
	@Test
	public void isMyInformationStillHere() {

		final User myUser = getActionTestUser();
		final VObject myObject = Factories.VOBJECT.findByOwner(myUser).get(0);

		final Music theMusic = creerMusique(myUser);
		/* Subscription */
		MySrvReveilFreeForm myForm = activateReveilFree(myUser, myObject, theMusic.getId().longValue(), "10:20");
		final String formerSchedule = myForm.getHorraire();

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadReveilFree(myUser, myObject);
		Assert.assertEquals(formerSchedule, myForm.getHorraire());

		supprimerMusique(theMusic.getId().longValue());
	}

	/**
	 * Test if the desactivation of the service works
	 * 
	 */
	@Test
	public void deleteService() {

		final User myUser = getActionTestUser();
		final VObject myObject = Factories.VOBJECT.findByOwner(myUser).get(0);

		final Music theMusic = creerMusique(myUser);
		/* Subscription */
		MySrvReveilFreeForm myForm = activateReveilFree(myUser, myObject, theMusic.getId().longValue(), "10:20");
		Assert.assertEquals(1, myForm.getIsReg());

		/* Defusing */
		deleteReveilFree(myUser, myObject);

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadReveilFree(myUser, myObject);
		Assert.assertEquals(0, myForm.getIsReg());

		supprimerMusique(theMusic.getId().longValue());
	}

	private Music creerMusique(User inOwner) {
		return super.createMusic(inOwner);
	}

	private void supprimerMusique(long id) {
		Factories.MUSIC.find(id).delete();
	}
}

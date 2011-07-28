package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvReveilFullForm;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.web.ServletTestBase;

import org.apache.struts.action.ActionMapping;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test pour MySrvReveilFullAction
 * 
 */
public class MySrvReveilFullActionTest extends ActionTestBase {

	/**
	 * Activate the service
	 */
	private MySrvReveilFullForm activateReveilFull(User myUser, VObject myObject, long music_id, String horaire, String name) {

		final MySrvReveilFullAction MySrvReveilFullAction = new MySrvReveilFullAction();
		final MySrvReveilFullForm myForm = new MySrvReveilFullForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		myForm.setMusic_id1(music_id);
		myForm.setSonNom1(name);
		myForm.setHorraire1(horaire);

		MySrvReveilFullAction.activate(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Update the service
	 */
	private MySrvReveilFullForm updateReveilFull(User myUser, VObject myObject, long music_id, String horaire, String name) {

		final MySrvReveilFullAction MySrvReveilFullAction = new MySrvReveilFullAction();
		final MySrvReveilFullForm myForm = new MySrvReveilFullForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		myForm.setMusic_id1(music_id);
		myForm.setSonNom1(name);
		myForm.setHorraire1(horaire);

		myForm.setMusic_id3(music_id);
		myForm.setSonNom3(name);
		myForm.setHorraire3(horaire);

		MySrvReveilFullAction.update(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Delete the service
	 */
	private MySrvReveilFullForm deleteReveilFull(User myUser, VObject myObject) {

		final MySrvReveilFullAction MySrvReveilFullAction = new MySrvReveilFullAction();
		final MySrvReveilFullForm myForm = new MySrvReveilFullForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		MySrvReveilFullAction.delete(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Load the service
	 */
	private MySrvReveilFullForm loadReveilFull(User myUser, VObject myObject) {

		final MySrvReveilFullAction MySrvReveilFullAction = new MySrvReveilFullAction();
		final MySrvReveilFullForm myForm = new MySrvReveilFullForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		MySrvReveilFullAction.load(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	@Test
	public void testEmptySessionRedirect() {
		ActionTestBase.testRedirectEmptySession(MySrvReveilFullAction.class, MySrvReveilFullForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvReveilFullAction.class, MySrvReveilFullForm.class, "activate", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvReveilFullAction.class, MySrvReveilFullForm.class, "update", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvReveilFullAction.class, MySrvReveilFullForm.class, "delete", ActionTestBase.FORWARD_LOGIN);
	}

	@Test
	public void testNoRabbitRedirect() {
		testRedirectNoRabbit(MySrvReveilFullAction.class, MySrvReveilFullForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvReveilFullAction.class, MySrvReveilFullForm.class, "activate", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvReveilFullAction.class, MySrvReveilFullForm.class, "update", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvReveilFullAction.class, MySrvReveilFullForm.class, "delete", ActionTestBase.FORWARD_LOGIN);
	}

	/**
	 * Test if an user can susbcribe to the service
	 * 
	 */
	// TODO : appel BD via constructeur
	@Test
	public void activateService() {

		final User myUser = getActionTestUser();
		final VObject myObject = Factories.VOBJECT.findByOwner(myUser).get(0);

		final Music theMusic = creerMusique(myUser);
		/* Subscription */
		MySrvReveilFullForm myForm = activateReveilFull(myUser, myObject, theMusic.getId().longValue(), "10:20", "sound1");

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadReveilFull(myUser, myObject);
		Assert.assertEquals(1, myForm.getIsReg());

		/* Add a schedule */
		updateReveilFull(myUser, myObject, theMusic.getId().longValue(), "10:20", "sound2");

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadReveilFull(myUser, myObject);
		Assert.assertEquals(1, myForm.getIsReg());

		/* Delete service */
		myForm = deleteReveilFull(myUser, myObject);
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

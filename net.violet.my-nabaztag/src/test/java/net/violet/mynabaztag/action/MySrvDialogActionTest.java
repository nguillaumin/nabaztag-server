package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvDialogForm;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.web.ServletTestBase;

import org.apache.struts.action.ActionMapping;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests pour MySrvDialogAction
 */
public class MySrvDialogActionTest extends ActionTestBase {

	/**
	 * Effectue une demande de communion d'oreilles
	 */
	private MySrvDialogForm askDialog(User myUser, VObject myObject, String nabName) {

		final MySrvDialogAction mySrvDialogAction = new MySrvDialogAction();
		final MySrvDialogForm myForm = new MySrvDialogForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		myForm.setFriendName(nabName);

		mySrvDialogAction.add(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Accepte une demande de communion d'oreilles
	 */
	private MySrvDialogForm acceptDialog(User myUser, VObject myObject, VObject friend) {

		final MySrvDialogAction mySrvDialogAction = new MySrvDialogAction();
		final MySrvDialogForm myForm = new MySrvDialogForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		myForm.setFriendId(friend.getId().intValue());
		myForm.setFriendName(friend.getObject_login());

		mySrvDialogAction.accept(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Annule une demande de communion d'oreilles
	 */
	private MySrvDialogForm cancelDialog(User myUser, VObject myObject, String nabName) {

		final MySrvDialogAction mySrvDialogAction = new MySrvDialogAction();
		final MySrvDialogForm myForm = new MySrvDialogForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		myForm.setFriendName(nabName);

		mySrvDialogAction.cancel(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Refuse toutes les demandes de communion d'oreilles
	 */
	private MySrvDialogForm denyAll(User myUser, VObject myObject, String nabName) {

		final MySrvDialogAction mySrvDialogAction = new MySrvDialogAction();
		final MySrvDialogForm myForm = new MySrvDialogForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		myForm.setFriendName(nabName);

		mySrvDialogAction.denyAll(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Divorce
	 */
	private MySrvDialogForm deleteDialog(User myUser, VObject myObject, String nabName) {

		final MySrvDialogAction mySrvDialogAction = new MySrvDialogAction();
		final MySrvDialogForm myForm = new MySrvDialogForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		myForm.setFriendName(nabName);

		mySrvDialogAction.delete(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Load the service
	 */
	private MySrvDialogForm loadDialog(User myUser, VObject myObject) {

		final MySrvDialogAction mySrvDialogAction = new MySrvDialogAction();
		final MySrvDialogForm myForm = new MySrvDialogForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		mySrvDialogAction.load(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	@Test
	public void testNoRabbitRedirect() {
		testRedirectNoRabbit(MySrvDialogAction.class, MySrvDialogForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvDialogAction.class, MySrvDialogForm.class, "denyAll", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvDialogAction.class, MySrvDialogForm.class, "accept", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvDialogAction.class, MySrvDialogForm.class, "add", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvDialogAction.class, MySrvDialogForm.class, "delete", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvDialogAction.class, MySrvDialogForm.class, "cancel", ActionTestBase.FORWARD_LOGIN);
	}

	@Test
	public void testEmptySessionRedirect() {
		ActionTestBase.testRedirectEmptySession(MySrvDialogAction.class, MySrvDialogForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvDialogAction.class, MySrvDialogForm.class, "denyAll", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvDialogAction.class, MySrvDialogForm.class, "accept", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvDialogAction.class, MySrvDialogForm.class, "add", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvDialogAction.class, MySrvDialogForm.class, "delete", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvDialogAction.class, MySrvDialogForm.class, "cancel", ActionTestBase.FORWARD_LOGIN);
	}

	/**
	 * Ask with own NabName
	 * 
	 */
	@Test
	public void AskWithOwnName() {

		final User myUser = getActionTestUser();
		final VObject myObject = Factories.VOBJECT.findByOwner(myUser).get(0);
		final MySrvDialogForm myForm = askDialog(myUser, myObject, myObject.getObject_login());

		Assert.assertEquals(1, myForm.getError_same());
	}

	/**
	 * Ask with a NabName which doesn't exist
	 * 
	 */
	@Test
	public void AskWithInexistingNabName() {

		final User myUser = getActionTestUser();
		final VObject myObject = Factories.VOBJECT.findByName("ActionTestObject");
		final MySrvDialogForm myForm = askDialog(myUser, myObject, "Kami no Usagi");

		Assert.assertEquals(1, myForm.getError_dne());
	}

	/**
	 * Ask to an user without rabbit
	 * 
	 */
	@Test
	public void AskToSomeoneWithoutAnyRabbit() {

		final User myUser = getActionTestUser();
		final VObject myObject = Factories.VOBJECT.findByName("ActionTestObject");
		final MySrvDialogForm myForm = askDialog(myUser, myObject, "ActionTestUser4");

		Assert.assertEquals(1, myForm.getError_dne());
	}

	/**
	 * Ask a friend who accept, then accept another asking and delete it.
	 * 
	 */
	// @Test
	public void oneAskThenAcceptThenAnotherAskThenAcceptAndDelete() {
		final User myUser = getActionTestUser();
		final VObject myObject = Factories.VOBJECT.findByName("ActionTestObject");
		final User myUser2 = getActionTestUser2();
		final VObject myObject2 = Factories.VOBJECT.findByOwner(myUser2).get(0);

		/* SrvDialog srvDial = *///Factories.SRVDIALOG.createNewDialog(myObject,
		// myObject2, 1);
		MySrvDialogForm myForm = askDialog(myUser, myObject, "ActionTestUser2");

		Assert.assertEquals(1, myForm.getIsWaiting());

		myForm = acceptDialog(myUser2, myObject2, myObject);

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadDialog(myUser2, myObject2);

		Assert.assertEquals(1, myForm.getIsMarried());

		/* Another asking */
		final User myUser3 = getActionTestUser3();
		final VObject myObject3 = Factories.VOBJECT.findByOwner(myUser3).get(0);
		myForm = askDialog(myUser3, myObject3, "ActionTestUser2");

		myForm = acceptDialog(myUser2, myObject2, myObject3);

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadDialog(myUser2, myObject2);
		Assert.assertEquals(1, myForm.getIsMarried());

		myForm = deleteDialog(myUser2, myObject2, "ActionTestUser3");

		myForm = new MySrvDialogForm();
		myForm = loadDialog(myUser2, myObject2);

		Assert.assertEquals(0, myForm.getIsMarried());
	}

	/**
	 * Ask a friend and then, cancel this asking.
	 * 
	 */
	// @Test
	public void AskThenDeny() {

		final User myUser = getActionTestUser();
		final VObject myObject = Factories.VOBJECT.findByName("ActionTestObject");
		MySrvDialogForm myForm = askDialog(myUser, myObject, "ActionTestUser2");

		Assert.assertEquals(1, myForm.getIsWaiting());
		myForm = cancelDialog(myUser, myObject, "ActionTestUser2");

		Assert.assertEquals(0, myForm.getIsWaiting());
	}

	/**
	 * Two asked, but deny all
	 * 
	 */
	// @Test
	public void twoAskedButDenyAll() {

		final User myUser = getActionTestUser();
		final VObject myObject = Factories.VOBJECT.findByName("ActionTestObject");
		MySrvDialogForm myForm = askDialog(myUser, myObject, "ActionTestUser2");
		Assert.assertEquals(1, myForm.getIsWaiting());

		final User myUser3 = getActionTestUser3();
		final VObject myObject3 = Factories.VOBJECT.findByOwner(myUser3).get(0);
		myForm = askDialog(myUser3, myObject3, "ActionTestUser2");
		Assert.assertEquals(1, myForm.getIsWaiting());

		myForm = denyAll(myUser, myObject, "ActionTestUser2");

		Assert.assertEquals(0, myForm.getIsMarried());
	}
}

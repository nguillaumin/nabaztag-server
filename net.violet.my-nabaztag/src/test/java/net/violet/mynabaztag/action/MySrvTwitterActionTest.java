package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvTwitterForm;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.web.ServletTestBase;

import org.junit.Test;

public class MySrvTwitterActionTest extends ActionTestBase {

	/**
	 * Simulate the subscription of the service.
	 * 
	 * @param myUser
	 *            the user.
	 * @param myObject
	 *            the object.
	 * @param login
	 *            the login of the Twitter account.
	 * @param password
	 *            the password associated.
	 * @return the <code>MySrvTwitterForm</code> created.s
	 */
	private MySrvTwitterForm activateTwitterlService(User myUser, VObject myObject, String login, String password) {
		final MySrvTwitterAction myAction = new MySrvTwitterAction();
		final MySrvTwitterForm myForm = new MySrvTwitterForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();

		myForm.setLogin(login);
		myForm.setPassword(password);
		myForm.setTwitterLang(myUser.getLang().getId().intValue());
		myAction.activate(ActionTestBase.createMapping(), myForm, myRequest, myResponse);

		return myForm;
	}

	/**
	 * Simulate the update of the settings of Twitter service.
	 * 
	 * @param myUser
	 *            the user.
	 * @param myObject
	 *            the object.
	 * @param login
	 *            the login of the Twitter account.
	 * @param password
	 *            the password associated.
	 * @return the <code>MySrvTwitterForm</code> created.s
	 */
	private MySrvTwitterForm updateTwitterService(User myUser, VObject myObject, String login, String password) {
		final MySrvTwitterAction myAction = new MySrvTwitterAction();
		final MySrvTwitterForm myForm = new MySrvTwitterForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();

		myForm.setLogin(login);
		myForm.setPassword(password);
		myForm.setTwitterLang(myUser.getLang().getId().intValue());
		myAction.update(ActionTestBase.createMapping(), myForm, myRequest, myResponse);

		return myForm;
	}

	/**
	 * Simulate the termination of the Twitter service.
	 * 
	 * @param myUser
	 *            the user.
	 * @param myObject
	 *            the object.
	 * @param login
	 *            the login of the Twitter account.
	 * @param password
	 *            the password associated.
	 * @return the <code>MySrvTwitterForm</code> created.s
	 */
	private MySrvTwitterForm deleteTwitterlService(User myUser, VObject myObject) {
		final MySrvTwitterAction myAction = new MySrvTwitterAction();
		final MySrvTwitterForm myForm = new MySrvTwitterForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();

		// myForm.setLogin(login);
		// myForm.setPassword(password);
		// myForm.setTwitterLang((int)myUser.getLang().getId());
		myAction.delete(ActionTestBase.createMapping(), myForm, myRequest, myResponse);

		return myForm;
	}

	private void initFileForAnnonce() {
		final String filePath = "broadcast/broad/config/Twitter/Twitter-annonce.mp3";
		createFileById(Application.NativeApplication.TWITTER.getApplication().getProfile().getAnnounceFile().getId(), filePath);
	}

	@Test
	public void testEmptySessionRedirect() {
		ActionTestBase.testRedirectEmptySession(MySrvTwitterAction.class, MySrvTwitterForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvTwitterAction.class, MySrvTwitterForm.class, "activate", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvTwitterAction.class, MySrvTwitterForm.class, "update", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvTwitterAction.class, MySrvTwitterForm.class, "delete", ActionTestBase.FORWARD_LOGIN);
	}

	@Test
	public void testNoRabbitRedirect() {
		testRedirectNoRabbit(MySrvTwitterAction.class, MySrvTwitterForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvTwitterAction.class, MySrvTwitterForm.class, "activate", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvTwitterAction.class, MySrvTwitterForm.class, "update", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvTwitterAction.class, MySrvTwitterForm.class, "delete", ActionTestBase.FORWARD_LOGIN);
	}

	// TODO : appel BD via constructeur
	// @Test TODO FIXME test
	public void checkAll() {
		final User inUser = getActionTestUser();

		// We work with the name because of the getScenarios method which
		// doesn't retrieve information from the database at each time
		final String V1Name = "ActionTestObject";
		final String V2Name = "ActionTestObjectV1";

		initFileForAnnonce();

		// Test pour V1
		updateWithoutActivate(inUser, V1Name);
		activateWithWrongLogin(inUser, V1Name);
		activateWithGoodLogin(inUser, V1Name);
		updateWithWrongLogin(inUser, V1Name);
		updateWithGoodLogin(inUser, V1Name);
		deleteTwitterService(inUser, V1Name);

		// Test pour V2
		updateWithoutActivate(inUser, V1Name);
		activateWithWrongLogin(inUser, V2Name);
		activateWithGoodLogin(inUser, V2Name);
		updateWithWrongLogin(inUser, V2Name);
		updateWithGoodLogin(inUser, V2Name);
		deleteTwitterService(inUser, V2Name);
	}

	/**
	 * Test if the user can't subscribe to this service because of a wrong
	 * identification
	 * 
	 * @param inUser
	 * @param nameObject
	 */
	private void activateWithWrongLogin(User inUser, String nameObject) {
		final VObject myObject = Factories.VOBJECT.findByName(nameObject);

		activateTwitterlService(inUser, myObject, "TheAmazingVioletCorporation", "violet");
	}

	/**
	 * Test if the user can subscribe to this service
	 * 
	 * @param inUser
	 * @param nameObject
	 */
	private void activateWithGoodLogin(User inUser, String nameObject) {
		final VObject myObject = Factories.VOBJECT.findByName(nameObject);

		activateTwitterlService(inUser, myObject, "tnabaztag", "violet");
	}

	/**
	 * Test if the user can't update the service settings because of a wrong
	 * identification
	 * 
	 * @param inUser
	 * @param nameObject
	 */
	private void updateWithWrongLogin(User inUser, String nameObject) {
		final VObject myObject = Factories.VOBJECT.findByName(nameObject);

		updateTwitterService(inUser, myObject, "TheAmazingVioletCorporation", "violet");
	}

	/**
	 * Test if the user can't update the service settings if this one wasn't
	 * activate before
	 * 
	 * @param inUser
	 * @param nameObject
	 */
	private void updateWithoutActivate(User inUser, String nameObject) {
		final VObject myObject = Factories.VOBJECT.findByName(nameObject);

		updateTwitterService(inUser, myObject, "tnabaztag", "violet");
	}

	/**
	 * Test if the user can update the service settings
	 * 
	 * @param inUser
	 * @param nameObject
	 */
	private void updateWithGoodLogin(User inUser, String nameObject) {
		final VObject myObject = Factories.VOBJECT.findByName(nameObject);

		updateTwitterService(inUser, myObject, "tnabaztag", "violet");
	}

	private void deleteTwitterService(User inUser, String nameObject) {
		final VObject myObject = Factories.VOBJECT.findByName(nameObject);

		deleteTwitterlService(inUser, myObject);
	}

}

package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvGmailForm;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.web.ServletTestBase;

import org.junit.Test;

/**
 * Test pour le service GMail
 */
public class MySrvGmailActionTest extends ActionTestBase {

	/**
	 * Simulate the subscription of the service.
	 * 
	 * @param myUser
	 *            the user.
	 * @param myObject
	 *            the object.
	 * @param login
	 *            the login of the GMAil account.
	 * @param password
	 *            the password associated.
	 * @return the <code>MySrvGmailForm</code> created.s
	 */
	private MySrvGmailForm activateGMailService(User myUser, VObject myObject, String login, String password) {
		final MySrvGmailAction myAction = new MySrvGmailAction();
		final MySrvGmailForm myForm = new MySrvGmailForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();

		myForm.setLogin(login);
		myForm.setPassword(password);
		myForm.setGmailLang(myUser.getLang().getId().intValue());
		myAction.activate(ActionTestBase.createMapping(), myForm, myRequest, myResponse);

		return myForm;
	}

	/**
	 * Simulate the update of the settings of GMail service.
	 * 
	 * @param myUser
	 *            the user.
	 * @param myObject
	 *            the object.
	 * @param login
	 *            the login of the GMAil account.
	 * @param password
	 *            the password associated.
	 * @return the <code>MySrvGmailForm</code> created.s
	 */
	private MySrvGmailForm updateGMailService(User myUser, VObject myObject, String login, String password) {
		final MySrvGmailAction myAction = new MySrvGmailAction();
		final MySrvGmailForm myForm = new MySrvGmailForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();

		myForm.setLogin(login);
		myForm.setPassword(password);
		myForm.setGmailLang(myUser.getLang().getId().intValue());
		myAction.update(ActionTestBase.createMapping(), myForm, myRequest, myResponse);

		return myForm;
	}

	/**
	 * Simulate the termination of the GMail service.
	 * 
	 * @param myUser
	 *            the user.
	 * @param myObject
	 *            the object.
	 * @param login
	 *            the login of the GMAil account.
	 * @param password
	 *            the password associated.
	 * @return the <code>MySrvGmailForm</code> created.s
	 */
	private MySrvGmailForm deleteGMailService(User myUser, VObject myObject) {
		final MySrvGmailAction myAction = new MySrvGmailAction();
		final MySrvGmailForm myForm = new MySrvGmailForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();

		// myForm.setLogin(login);
		// myForm.setPassword(password);
		// myForm.setGmailLang((int)myUser.getLang().getId());
		myAction.delete(ActionTestBase.createMapping(), myForm, myRequest, myResponse);

		return myForm;
	}

//	private void initFileForAnnonce() {
//		final String filePath = "broadcast/broad/config/gmail/gmail-annonce.mp3";
//		createFileById(Application.NativeApplication.GMAIL.getApplication().getProfile().getAnnounceFile().getId(), filePath);
//	}

	@Test
	public void testEmptySessionRedirect() {
		ActionTestBase.testRedirectEmptySession(MySrvGmailAction.class, MySrvGmailForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvGmailAction.class, MySrvGmailForm.class, "activate", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvGmailAction.class, MySrvGmailForm.class, "update", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvGmailAction.class, MySrvGmailForm.class, "delete", ActionTestBase.FORWARD_LOGIN);
	}

	@Test
	public void testNoRabbitRedirect() {
		testRedirectNoRabbit(MySrvGmailAction.class, MySrvGmailForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvGmailAction.class, MySrvGmailForm.class, "activate", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvGmailAction.class, MySrvGmailForm.class, "update", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvGmailAction.class, MySrvGmailForm.class, "delete", ActionTestBase.FORWARD_LOGIN);
	}

	// TODO : passer ScenarioHasAction dans les Mocks
	@Test
	public void checkAll() {
		final User inUser = getActionTestUser();

		// We work with the name because of the getScenarios method which
		// doesn't retrieve information from the database at each time
		final String V1Name = "ActionTestObject";
		final String V2Name = "ActionTestObjectV1";

		// Test pour V2
		updateWithoutActivate(inUser, V1Name);
		activateWithWrongLogin(inUser, V2Name);
		deleteGMailService(inUser, V2Name);

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

		activateGMailService(inUser, myObject, "TheAmazingVioletCorporation", "violet");
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

		updateGMailService(inUser, myObject, "TheAmazingVioletCorporation", "murasaki");
	}

	private void deleteGMailService(User inUser, String nameObject) {
		final VObject myObject = Factories.VOBJECT.findByName(nameObject);

		deleteGMailService(inUser, myObject);
	}

}

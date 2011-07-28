package net.violet.mynabaztag.action;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvClockForm;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.web.ServletTestBase;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests pour MySrvClockAction
 */
public class MySrvClockActionTest extends ActionTestBase {

	private final String[] types = { "2", "3" };
	private final String[] languages = { "1", "3", "5" };

	/**
	 * Simulate the subscription of the service
	 * 
	 * @param myTypes
	 *            the modulation of voices
	 * @param myLanguages
	 *            the languages of the different voices
	 * @return the <code>ActionForm</code> created
	 */
	private MySrvClockForm activate(String[] myTypes, String[] myLanguages) {
		final User myUser = getActionTestUser();
		final VObject myObject = Factories.VOBJECT.findByOwner(myUser).get(0);
		final MySrvClockAction myAction = new MySrvClockAction();
		final MySrvClockForm myForm = new MySrvClockForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();

		myForm.setCheckListClockType(myTypes);
		myForm.setCheckListLang(myLanguages);
		myForm.setDispatch("create");
		myAction.doExecute(ActionTestBase.createMapping(), myForm, myRequest, myResponse);

		return myForm;
	}

	/**
	 * Simulate the loading of the page because of information presently
	 * existing
	 * 
	 * @return the <code>ActionForm</code> created
	 */
	private MySrvClockForm load() {
		final User myUser = getActionTestUser();
		final VObject myObject = Factories.VOBJECT.findByOwner(myUser).get(0);
		final MySrvClockAction myAction = new MySrvClockAction();
		final MySrvClockForm myForm = new MySrvClockForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();

		myAction.load(ActionTestBase.createMapping(), myForm, myRequest, myResponse);

		return myForm;

	}

	/**
	 * Simulate the update of the user settings
	 * 
	 * @param myTypes
	 *            the modulation of voices
	 * @param myLanguages
	 *            the languages of the different voices
	 * @return the <code>ActionForm</code> created
	 */
	private MySrvClockForm update(String[] myTypes, String[] myLanguages) {
		final User myUser = getActionTestUser();
		final VObject myObject = Factories.VOBJECT.findByOwner(myUser).get(0);
		final MySrvClockAction myAction = new MySrvClockAction();
		final MySrvClockForm myForm = new MySrvClockForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();

		myForm.setCheckListClockType(myTypes);
		myForm.setCheckListLang(myLanguages);
		myAction.update(ActionTestBase.createMapping(), myForm, myRequest, myResponse);

		return myForm;
	}

	/**
	 * Simulate the defusing of the service
	 * 
	 * @return the <code>ActionForm</code> created
	 */
	private MySrvClockForm delete() {

		final User myUser = getActionTestUser();
		final VObject myObject = Factories.VOBJECT.findByOwner(myUser).get(0);
		final MySrvClockAction myAction = new MySrvClockAction();
		final MySrvClockForm myForm = new MySrvClockForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();

		myAction.delete(ActionTestBase.createMapping(), myForm, myRequest, myResponse);

		return myForm;
	}

	@Test
	public void testEmptySessionRedirectLoad() {
		final MySrvClockForm theForm = new MySrvClockForm();
		theForm.setDispatch("load");
		ActionTestBase.testRedirectEmptySession(MySrvClockAction.class, theForm, "load", ActionTestBase.FORWARD_LOGIN);
	}

	@Test
	public void testEmptySessionRedirectCreate() {
		final MySrvClockForm theForm = new MySrvClockForm();
		theForm.setDispatch("create");
		ActionTestBase.testRedirectEmptySession(MySrvClockAction.class, theForm, "create", ActionTestBase.FORWARD_LOGIN);
	}

	@Test
	public void testEmptySessionRedirectUpdate() {
		final MySrvClockForm theForm = new MySrvClockForm();
		theForm.setDispatch("update");
		ActionTestBase.testRedirectEmptySession(MySrvClockAction.class, theForm, "update", ActionTestBase.FORWARD_LOGIN);
	}

	@Test
	public void testEmptySessionRedirectDelete() {
		final MySrvClockForm theForm = new MySrvClockForm();
		theForm.setDispatch("delete");
		ActionTestBase.testRedirectEmptySession(MySrvClockAction.class, theForm, "delete", ActionTestBase.FORWARD_LOGIN);
	}

	/**
	 * Test if the user can subscribe to this service
	 * 
	 */
	private void activateTalkingClockWith() {
		activate(this.types, this.languages);
	}

	/**
	 * Test if the user's information have been preserved.
	 * 
	 */
	@Test
	public void isMyInformationStillHere() {
		activateTalkingClockWith();

		final MySrvClockForm myForm = load();
		Assert.assertTrue(Arrays.equals(this.types, myForm.getCheckListClockType()));
		Assert.assertTrue(Arrays.equals(this.languages, myForm.getCheckListLang()));
		deleteTalkingClock();
	}

	/**
	 * Test if the user settings have been accurately updated
	 * 
	 */
	@Test
	public void updateTalkingClock() {
		final String[] oTypes = { "1", "2" };
		final String[] oLanguages = { "3", "6" };
		activateTalkingClockWith();
		update(oTypes, oLanguages);
		final MySrvClockForm myForm = load();

		Assert.assertFalse(Arrays.equals(this.types, myForm.getCheckListClockType()));
		Assert.assertFalse(Arrays.equals(this.languages, myForm.getCheckListLang()));
		deleteTalkingClock();
	}

	private void deleteTalkingClock() {

		final MySrvClockForm myForm = delete();
		System.out.println(myForm.getCheckListClockType());
	}
}

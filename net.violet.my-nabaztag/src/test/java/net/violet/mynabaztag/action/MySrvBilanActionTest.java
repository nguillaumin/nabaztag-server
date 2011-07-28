package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvBilanForm;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.web.ServletTestBase;

import org.apache.struts.action.ActionMapping;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests pour MySrvBilanAction
 */
public class MySrvBilanActionTest extends ActionTestBase {

	private MySrvBilanForm activateBilan(User myUser, VObject myObject, int day, String horaire) {

		final MySrvBilanAction mySrvBilanAction = new MySrvBilanAction();
		final MySrvBilanForm myForm = new MySrvBilanForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		myForm.setFreqSrv(day);
		myForm.setHorraire(horaire);

		mySrvBilanAction.activate(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	private MySrvBilanForm updateBilan(User myUser, VObject myObject, int day, String horaire) {

		final MySrvBilanAction mySrvBilanAction = new MySrvBilanAction();
		final MySrvBilanForm myForm = new MySrvBilanForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		myForm.setFreqSrv(day);
		myForm.setHorraire(horaire);

		mySrvBilanAction.update(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	private MySrvBilanForm deleteBilan(User myUser, VObject myObject) {

		final MySrvBilanAction mySrvBilanAction = new MySrvBilanAction();
		final MySrvBilanForm myForm = new MySrvBilanForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		mySrvBilanAction.delete(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	private MySrvBilanForm loadBilan(User myUser, VObject myObject) {

		final MySrvBilanAction mySrvBilanAction = new MySrvBilanAction();
		final MySrvBilanForm myForm = new MySrvBilanForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		mySrvBilanAction.load(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	@Test
	public void testEmptySessionRedirect() {
		ActionTestBase.testRedirectEmptySession(MySrvBilanAction.class, MySrvBilanForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvBilanAction.class, MySrvBilanForm.class, "activate", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvBilanAction.class, MySrvBilanForm.class, "update", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvBilanAction.class, MySrvBilanForm.class, "delete", ActionTestBase.FORWARD_LOGIN);
	}

	@Test
	public void testNoRabbitRedirect() {
		testRedirectNoRabbit(MySrvBilanAction.class, MySrvBilanForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvBilanAction.class, MySrvBilanForm.class, "activate", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvBilanAction.class, MySrvBilanForm.class, "update", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvBilanAction.class, MySrvBilanForm.class, "delete", ActionTestBase.FORWARD_LOGIN);
	}

	// @Test FIXME
	public void checkAll() {
		final User myUser = getActionTestUser();
		final VObject myObjectv1 = Factories.VOBJECT.findByOwnerAndHardware(myUser, HARDWARE.V1);
		final VObject myObjectv2 = Factories.VOBJECT.findByOwnerAndHardware(myUser, HARDWARE.V2);

		// v1
		activateService(myUser, myObjectv1);
		updateService(myUser, myObjectv1);
		// v2
		activateService(myUser, myObjectv2);
		updateService(myUser, myObjectv2);
	}

	private void activateService(User myUser, VObject myObject) {

		/* Subscription */
		MySrvBilanForm myForm = activateBilan(myUser, myObject, 6, "10:20"); // dimanche

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadBilan(myUser, myObject);

		Assert.assertEquals(1, myForm.getIsReg());

		deleteService(myUser, myObject);

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadBilan(myUser, myObject);
		Assert.assertEquals(0, myForm.getIsReg());
	}

	private void updateService(User myUser, VObject myObject) {

		/* Subscription */
		MySrvBilanForm myForm = activateBilan(myUser, myObject, 6, "10:20");
		final String formerSchedule1 = myForm.getHorraire();

		/* Update settings */
		updateBilan(myUser, myObject, 1, "11:45");// mardi

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadBilan(myUser, myObject);

		Assert.assertEquals(1, myForm.getIsReg());

		Assert.assertNotSame(formerSchedule1, myForm.getHorraire());

		deleteService(myUser, myObject);
		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadBilan(myUser, myObject);
		Assert.assertEquals(0, myForm.getIsReg());
	}

	private void deleteService(User myUser, VObject myObject) {
		Assert.assertEquals(0, deleteBilan(myUser, myObject).getIsReg());
	}
}

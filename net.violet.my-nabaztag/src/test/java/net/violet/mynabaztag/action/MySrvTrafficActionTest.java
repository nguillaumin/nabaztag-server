package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvTrafficForm;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.SourceMock;
import net.violet.platform.util.StringShop;
import net.violet.platform.web.ServletTestBase;

import org.apache.struts.action.ActionMapping;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test pour MySrvTrafficAction
 */
public class MySrvTrafficActionTest extends ActionTestBase {

	/**
	 * Activate the service
	 */
	private MySrvTrafficForm activateTraffic(User myUser, VObject myObject, String outset, String destination, String horaire1, String horaire2, int light) {

		final MySrvTrafficAction mySrvMeteoFreeAction = new MySrvTrafficAction();
		final MySrvTrafficForm myForm = new MySrvTrafficForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		myForm.setDepart(outset);
		myForm.setArrivee(destination);
		myForm.setHorraire1(horaire1);
		myForm.setHorraire2(horaire2);
		myForm.setLumiere(light);

		mySrvMeteoFreeAction.activate(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Update the service
	 */
	private MySrvTrafficForm updateTraffic(User myUser, VObject myObject, String outset, String destination, String horaire1, String horaire2, int light) {

		final MySrvTrafficAction mySrvMeteoFreeAction = new MySrvTrafficAction();
		final MySrvTrafficForm myForm = new MySrvTrafficForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		myForm.setDepart(outset);
		myForm.setArrivee(destination);
		myForm.setHorraire1(horaire1);
		myForm.setHorraire2(horaire2);
		myForm.setLumiere(light);

		mySrvMeteoFreeAction.update(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Delete the service
	 */
	private MySrvTrafficForm deleteTraffic(User myUser, VObject myObject) {

		final MySrvTrafficAction mySrvMeteoFreeAction = new MySrvTrafficAction();
		final MySrvTrafficForm myForm = new MySrvTrafficForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		mySrvMeteoFreeAction.delete(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Load the service
	 */
	private MySrvTrafficForm loadTraffic(User myUser, VObject myObject) {

		final MySrvTrafficAction mySrvMeteoFreeAction = new MySrvTrafficAction();
		final MySrvTrafficForm myForm = new MySrvTrafficForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		mySrvMeteoFreeAction.load(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	@Test
	public void testEmptySessionRedirect() {
		ActionTestBase.testRedirectEmptySession(MySrvTrafficAction.class, MySrvTrafficForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvTrafficAction.class, MySrvTrafficForm.class, "activate", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvTrafficAction.class, MySrvTrafficForm.class, "update", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvTrafficAction.class, MySrvTrafficForm.class, "delete", ActionTestBase.FORWARD_LOGIN);
	}

	public void testNoRabbitRedirect() {
		testRedirectNoRabbit(MySrvTrafficAction.class, MySrvTrafficForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvTrafficAction.class, MySrvTrafficForm.class, "activate", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvTrafficAction.class, MySrvTrafficForm.class, "update", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvTrafficAction.class, MySrvTrafficForm.class, "delete", ActionTestBase.FORWARD_LOGIN);
	}

	@Test
	public void checkAll() {
		final User myUser = getActionTestUser();
		final VObject myObjectv1 = Factories.VOBJECT.findByOwnerAndHardware(myUser, HARDWARE.V1);
		final VObject myObjectv2 = Factories.VOBJECT.findByOwnerAndHardware(myUser, HARDWARE.V2);

		// v1
		activateWithLightLanguage(myUser, myObjectv1);
		activateService2flash(myUser, myObjectv1);
		suppressAudioFlashes(myUser, myObjectv1);
		// v2
		activateWithLightLanguage(myUser, myObjectv2);
		activateService2flash(myUser, myObjectv2);
		suppressAudioFlashes(myUser, myObjectv2);
	}

	/**
	 * Test if an user can susbcribe to the service with only the
	 * "light language"
	 * 
	 */
	public void activateWithLightLanguage(User myUser, VObject myObject) {

		new SourceMock(0, "trafic.Italie.Orleans", 0);
		/* Subscription */
		MySrvTrafficForm myForm = activateTraffic(myUser, myObject, "Italie", "Orleans", StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, 1);

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadTraffic(myUser, myObject);

		// aucun flash audio
		Assert.assertEquals(1, myForm.getIsReg());

		/* delete */
		deleteService(myUser, myObject);
	}

	/**
	 * Activation avec deux flash puis supression de ceux là
	 */
	public void suppressAudioFlashes(User myUser, VObject myObject) {

		/* Subscription */
		MySrvTrafficForm myForm = activateTraffic(myUser, myObject, "Italie", "Orleans", "10:00", StringShop.EMPTY_STRING, 1);

		// 2 flash audio
		Assert.assertEquals(1, myForm.getIsReg());

		/* Update settings */
		updateTraffic(myUser, myObject, "Italie", "Orleans", StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, 1);

		// aucun flash audio
		Assert.assertEquals(1, myForm.getIsReg());

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadTraffic(myUser, myObject);
		Assert.assertEquals(StringShop.EMPTY_STRING, myForm.getHorraire1());

		/* delete */
		deleteService(myUser, myObject);
	}

	public void activateService2flash(User myUser, VObject myObject) {

		/* Subscription */
		MySrvTrafficForm myForm = activateTraffic(myUser, myObject, "Italie", "Orleans", "10:00", StringShop.EMPTY_STRING, 1);
		final String formerSchedule1 = myForm.getHorraire1();

		// 2 flash audio
		Assert.assertEquals(1, myForm.getIsReg());

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadTraffic(myUser, myObject);
		Assert.assertEquals(formerSchedule1, myForm.getHorraire1());

		/* delete */
		deleteService(myUser, myObject);
	}

	private void deleteService(User myUser, VObject myObject) {
		/* delete */
		Assert.assertEquals(0, deleteTraffic(myUser, myObject).getIsReg());
	}

}

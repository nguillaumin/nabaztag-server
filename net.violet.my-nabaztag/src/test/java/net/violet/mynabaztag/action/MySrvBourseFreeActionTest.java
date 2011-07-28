package net.violet.mynabaztag.action;

import java.sql.Time;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvBourseFreeForm;
import net.violet.mynabaztag.form.MySrvBourseFullForm;
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
 * Tests pour MySrvBourseFreeAction
 */
public class MySrvBourseFreeActionTest extends ActionTestBase {

	/**
	 * Activate the service
	 */
	private MySrvBourseFreeForm activateBourseFree(User myUser, VObject myObject, String indic, String horaire1, String horaire2, int light) {

		final MySrvBourseFreeAction mySrvBourseFreeAction = new MySrvBourseFreeAction();
		final MySrvBourseFreeForm myForm = new MySrvBourseFreeForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		myForm.setIndic(indic);
		myForm.setHorraire1(horaire1);
		myForm.setHorraire2(horaire2);
		myForm.setLumiere(light);

		mySrvBourseFreeAction.activate(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Update the service
	 */
	private MySrvBourseFreeForm updateBourseFree(User myUser, VObject myObject, String indic, String horaire1, String horaire2, int light) {

		final MySrvBourseFreeAction mySrvBourseFreeAction = new MySrvBourseFreeAction();
		final MySrvBourseFreeForm myForm = new MySrvBourseFreeForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		myForm.setIndic(indic);
		myForm.setHorraire1(horaire1);
		myForm.setHorraire2(horaire2);
		myForm.setLumiere(light);

		mySrvBourseFreeAction.update(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Delete the service
	 */
	private MySrvBourseFreeForm deleteBourseFree(User myUser, VObject myObject) {

		final MySrvBourseFreeAction mySrvBourseFreeAction = new MySrvBourseFreeAction();
		final MySrvBourseFreeForm myForm = new MySrvBourseFreeForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		mySrvBourseFreeAction.delete(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Load the service
	 */
	private MySrvBourseFreeForm loadBourseFree(User myUser, VObject myObject) {

		final MySrvBourseFreeAction mySrvBourseFreeAction = new MySrvBourseFreeAction();
		final MySrvBourseFreeForm myForm = new MySrvBourseFreeForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		mySrvBourseFreeAction.load(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	@Test
	public void testEmptySessionRedirect() {
		ActionTestBase.testRedirectEmptySession(MySrvBourseFreeAction.class, MySrvBourseFreeForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvBourseFreeAction.class, MySrvBourseFreeForm.class, "activate", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvBourseFreeAction.class, MySrvBourseFreeForm.class, "update", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvBourseFreeAction.class, MySrvBourseFreeForm.class, "delete", ActionTestBase.FORWARD_LOGIN);
	}

	@Test
	public void testNoRabbitRedirect() {
		testRedirectNoRabbit(MySrvBourseFreeAction.class, MySrvBourseFreeForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvBourseFreeAction.class, MySrvBourseFreeForm.class, "activate", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvBourseFreeAction.class, MySrvBourseFreeForm.class, "update", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvBourseFreeAction.class, MySrvBourseFreeForm.class, "delete", ActionTestBase.FORWARD_LOGIN);
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

		// test if bourse full already activated
		activateWithFullOwned(myUser, myObjectv1);

	}

	/**
	 * Test if an user can susbcribe to the service with only the
	 * "light language"
	 * 
	 */
	private void activateWithLightLanguage(User myUser, VObject myObject) {

		// final SrvMock srvMock = new SrvMock(2, myObject, "money.NIKKEI 225");
		new SourceMock(0, "money.NIKKEI 225", 0);
		/* Subscription */
		MySrvBourseFreeForm myForm = activateBourseFree(myUser, myObject, "money.NIKKEI 225", StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, 1);

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadBourseFree(myUser, myObject);
		Assert.assertEquals(1, myForm.getIsReg());

		/* delete */
		deleteService(myUser, myObject);
	}

	/**
	 * Activation avec deux flash puis supression de ceux là
	 */
	private void suppressAudioFlashes(User myUser, VObject myObject) {

		/* Subscription */
		MySrvBourseFreeForm myForm = activateBourseFree(myUser, myObject, "money.NIKKEI 225", Time.valueOf("10:00:00").toString(), Time.valueOf("11:00:00").toString(), 1);

		Assert.assertEquals(1, myForm.getIsReg());

		/* Update settings - Case the schedule setted was the second one */
		myForm = updateBourseFree(myUser, myObject, "money.NIKKEI 225", StringShop.EMPTY_STRING, "11:00", 1);
		Assert.assertEquals(1, myForm.getIsReg());

		/* Update settings */
		myForm = updateBourseFree(myUser, myObject, "money.NIKKEI 225", StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, 1);

		Assert.assertEquals(1, myForm.getIsReg());

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadBourseFree(myUser, myObject);
		Assert.assertEquals(StringShop.EMPTY_STRING, myForm.getHorraire1());
		Assert.assertEquals(StringShop.EMPTY_STRING, myForm.getHorraire2());

		/* delete */
		deleteService(myUser, myObject);
	}

	private void activateService2flash(User myUser, VObject myObject) {

		/* Subscription */
		MySrvBourseFreeForm myForm = activateBourseFree(myUser, myObject, "money.NIKKEI 225", Time.valueOf("10:00:00").toString(), Time.valueOf("11:00:00").toString(), 1);
		final String formerSchedule1 = myForm.getHorraire1();
		final String formerSchedule2 = myForm.getHorraire2();

		// 2 flash audio
		Assert.assertEquals(1, myForm.getIsReg());

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadBourseFree(myUser, myObject);
		Assert.assertEquals(formerSchedule1, myForm.getHorraire1());
		Assert.assertEquals(formerSchedule2, myForm.getHorraire2());

		/* delete */
		deleteService(myUser, myObject);
	}

	private void activateWithFullOwned(User myUser, VObject myObject) {

		/* Activate a bourse full account */
		final MySrvBourseFullAction mySrvBourseFullAction = new MySrvBourseFullAction();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();
		final MySrvBourseFullForm myForm = new MySrvBourseFullForm();

		myForm.setAlertName("mon alerte");
		myForm.setValName(StringShop.EMPTY_STRING);
		myForm.setIndic("money.NIKKEI 225");
		myForm.setHorraire1(StringShop.EMPTY_STRING);
		myForm.setHorraire2(StringShop.EMPTY_STRING);
		myForm.setLumiere(1);
		myForm.setWeekend(0);

		mySrvBourseFullAction.activate(actionMap, myForm, myRequest, myResponse);

		/*
		 * Creation de TTS non possible, donc l'inscription ne peut pas se
		 * terminer
		 */

		// assertEquals(0, myForm.getIsReg());
		/* Defusing the bourse full created */
		// myForm = new MySrvBourseFullForm();
		// mySrvBourseFullAction.delete(actionMap, myForm, myRequest,
		// myResponse);
		/* Refresh and purge ActionForm to recover the correct information saved */
		// assertEquals(0, myForm.getIsReg());
	}

	private void deleteService(User myUser, VObject myObject) {
		/* delete */
		Assert.assertEquals(0, deleteBourseFree(myUser, myObject).getIsReg());
	}
}

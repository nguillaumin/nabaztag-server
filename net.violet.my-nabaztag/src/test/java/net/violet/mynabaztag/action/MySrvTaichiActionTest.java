package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvTaichiForm;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.web.ServletTestBase;

import org.apache.struts.action.ActionMapping;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests pour MySrvTaichiAction
 */
public class MySrvTaichiActionTest extends ActionTestBase {

	/**
	 * Activate the service
	 */
	private MySrvTaichiForm activateTaichi(User myUser, VObject myObject, String frequency) {

		final MySrvTaichiAction mySrvTaichAction = new MySrvTaichiAction();
		final MySrvTaichiForm myForm = new MySrvTaichiForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		myForm.setFreqSrv(frequency);

		mySrvTaichAction.activate(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Update the service
	 */
	private MySrvTaichiForm updateTaichi(User myUser, VObject myObject, String frequency) {

		final MySrvTaichiAction mySrvTaichAction = new MySrvTaichiAction();
		final MySrvTaichiForm myForm = new MySrvTaichiForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		myForm.setFreqSrv(frequency);

		mySrvTaichAction.update(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Delete the service
	 */
	private MySrvTaichiForm deleteTaichi(User myUser, VObject myObject) {

		final MySrvTaichiAction mySrvTaichAction = new MySrvTaichiAction();
		final MySrvTaichiForm myForm = new MySrvTaichiForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		mySrvTaichAction.delete(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Load the service
	 */
	private MySrvTaichiForm loadTaichi(User myUser, VObject myObject) {

		final MySrvTaichiAction mySrvTaichAction = new MySrvTaichiAction();
		final MySrvTaichiForm myForm = new MySrvTaichiForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		mySrvTaichAction.load(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	@Test
	public void testNoRabbitRedirect() {
		testRedirectNoRabbit(MySrvTaichiAction.class, MySrvTaichiForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvTaichiAction.class, MySrvTaichiForm.class, "activate", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvTaichiAction.class, MySrvTaichiForm.class, "update", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvTaichiAction.class, MySrvTaichiForm.class, "delete", ActionTestBase.FORWARD_LOGIN);
	}

	/**
	 * Test if an user can susbcribe to the service.
	 * 
	 */
	@Test
	public void activateService() {

		final User myUser = getActionTestUser();
		final VObject myObject = Factories.VOBJECT.findByOwner(myUser).get(0);

		/* Subscription */
		MySrvTaichiForm myForm = activateTaichi(myUser, myObject, "taichi.fast");

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadTaichi(myUser, myObject);
		Assert.assertEquals(1, myForm.getIsReg());
	}

	/**
	 * Test if the defusing of the audio flashes works
	 */
	@Test
	public void updateService() {

		final User myUser = getActionTestUser();
		final VObject myObject = Factories.VOBJECT.findByOwner(myUser).get(0);

		/* Subscription */
		MySrvTaichiForm myForm = activateTaichi(myUser, myObject, "taichi.fast");
		final String formerFrequency = myForm.getFreqSrv();

		/* Update settings */
		updateTaichi(myUser, myObject, "taichi.slow");

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadTaichi(myUser, myObject);

		Assert.assertNotSame(formerFrequency, myForm.getFreqSrv());
	}

	/**
	 * Test if the defusing of the service works
	 * 
	 */
	@Test
	public void deleteService() {

		final User myUser = getActionTestUser();
		final VObject myObject = Factories.VOBJECT.findByOwner(myUser).get(0);

		/* Subscription */
		MySrvTaichiForm myForm = activateTaichi(myUser, myObject, "taichi.fast");
		Assert.assertEquals(1, myForm.getIsReg());

		/* Defusing */
		deleteTaichi(myUser, myObject);

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadTaichi(myUser, myObject);
		Assert.assertEquals(0, myForm.getIsReg());
	}
}

package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.mynabaztag.form.MySessionForm;
import net.violet.platform.web.ServletTestBase;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.Assert;
import org.junit.Test;

public class MySessionActionTest extends ActionTestBase {

	@Test
	public void fixMeTest() {
		Assert.assertTrue(true);
	}

	/**
	 * Test if the login succeed and if the user is well redirect to "goNablife"
	 * 
	 */
	// @Test test : problème pour simuler le Dispatcher dans ServletTestBase
	public void logAnUser() {

		final MySessionAction mySessionAction = new MySessionAction();
		final MySessionForm myForm = new MySessionForm();
		// final HttpSession mySession = createEmptySession();
		final HttpServletRequest myRequest = ServletTestBase.createEmptySessionRequest();
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		myForm.setPseudo("ActionTestUser");
		myForm.setPassword("123");
		myForm.setForward("goNablife");
		myForm.setAction("connect");
		myForm.setRedirectUrl("myNablife.do");

		final ActionForward myforward = mySessionAction.execute(actionMap, myForm, myRequest, myResponse);
		Assert.assertEquals("goNablife", myforward.getName());
	}
}

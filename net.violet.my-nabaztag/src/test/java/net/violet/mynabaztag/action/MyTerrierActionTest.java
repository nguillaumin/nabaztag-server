package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.mynabaztag.form.MyTerrierForm;
import net.violet.platform.web.ServletTestBase;

import org.apache.struts.action.ActionMapping;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test pour MyTerrierAction
 */
public class MyTerrierActionTest extends ActionTestBase {

	@Test
	public void testEmptySessionRedirect() {
		ActionTestBase.testRedirectEmptySession(MyTerrierAction.class, MyTerrierForm.class, "execute", ActionTestBase.FORWARD_INPUT);
	}

	@Test
	public void testNoRabbitRedirect() {
		testRedirectNoRabbit(MyTerrierAction.class, MyTerrierForm.class, "execute", ActionTestBase.FORWARD_INPUT);
	}

	/**
	 * Test if an user is logged or not in MyTerrier
	 * 
	 */
	@Test
	public void goToMyTerrierWithoutLogin() {
		final MyTerrierAction myTerrierAction = new MyTerrierAction();
		final MyTerrierForm myForm = new MyTerrierForm();
		final HttpServletRequest myRequest = ServletTestBase.createEmptySessionRequest();
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		myTerrierAction.execute(actionMap, myForm, myRequest, myResponse);
		Assert.assertEquals(0, myForm.getUserId());
	}
}

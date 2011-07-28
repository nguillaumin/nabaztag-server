package net.violet.mynabaztag.action;

import net.violet.mynabaztag.form.MyToolsApiForm;

import org.junit.Test;


public class MyToolsApiActionTest extends ActionTestBase {

	/**
	 * Tests pour MyToolsApiAction.
	 */

	@Test
	public void testEmptySessionRedirect() {
		ActionTestBase.testRedirectEmptySession(MyToolsApiAction.class, MyToolsApiForm.class, "execute", ActionTestBase.FORWARD_LOGIN);
	}

	@Test
	public void testNoRabbitRedirect() {
		testRedirectNoRabbit(MyToolsApiAction.class, MyToolsApiForm.class, "execute", ActionTestBase.FORWARD_INPUT);
	}
}

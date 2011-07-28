package net.violet.mynabaztag.action;

import net.violet.mynabaztag.form.MyToolsForm;

import org.junit.Test;


public class MyToolsActionTest extends ActionTestBase {

	/**
	 * Tests pour MyToolsAction.
	 */

	@Test
	public void testEmptySessionRedirect() {
		ActionTestBase.testRedirectEmptySession(MyToolsAction.class, MyToolsForm.class, "execute", ActionTestBase.FORWARD_INPUT);
	}

	@Test
	public void testNoRabbitRedirect() {
		testRedirectNoRabbit(MyToolsAction.class, MyToolsForm.class, "execute", ActionTestBase.FORWARD_INPUT);
	}
}

package net.violet.mynabaztag.action;

import net.violet.mynabaztag.form.MyToolsNabgetsForm;

import org.junit.Test;


public class MyToolsNabgetsActionTest extends ActionTestBase {

	/**
	 * Tests pour MyToolsNabgetsAction.
	 */

	@Test
	public void testEmptySessionRedirect() {
		ActionTestBase.testRedirectEmptySession(MyToolsNabgetsAction.class, MyToolsNabgetsForm.class, "execute", ActionTestBase.FORWARD_INPUT);
	}

	@Test
	public void testNoRabbitRedirect() {
		testRedirectNoRabbit(MyToolsNabgetsAction.class, MyToolsNabgetsForm.class, "execute", ActionTestBase.FORWARD_INPUT);
	}
}

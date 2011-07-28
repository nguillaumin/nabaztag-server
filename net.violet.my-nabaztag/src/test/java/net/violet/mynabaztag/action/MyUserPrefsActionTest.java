package net.violet.mynabaztag.action;

import net.violet.mynabaztag.form.MyUserPrefsForm;

import org.junit.Test;


public class MyUserPrefsActionTest extends ActionTestBase {

	/**
	 * Tests pour MyUserPrefsAction.
	 */

	@Test
	public void testEmptySessionRedirect() {
		ActionTestBase.testRedirectEmptySession(MyUserPrefsAction.class, MyUserPrefsForm.class, "execute", ActionTestBase.FORWARD_INPUT);
	}

	@Test
	public void testNoRabbitRedirect() {
		testRedirectNoRabbit(MyUserPrefsAction.class, MyUserPrefsForm.class, "execute", ActionTestBase.FORWARD_INPUT);
	}
}

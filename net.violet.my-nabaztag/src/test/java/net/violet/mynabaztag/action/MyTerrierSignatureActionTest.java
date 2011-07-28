package net.violet.mynabaztag.action;

import net.violet.mynabaztag.form.MyTerrierSignatureForm;

import org.junit.Test;


public class MyTerrierSignatureActionTest extends ActionTestBase {

	/**
	 * Tests pour MyTerrierSignatureAction.
	 */

	@Test
	public void testEmptySessionRedirect() {
		ActionTestBase.testRedirectEmptySession(MyTerrierSignatureAction.class, MyTerrierSignatureForm.class, "execute", ActionTestBase.FORWARD_LOGIN);
	}

	@Test
	public void testNoRabbitRedirect() {
		testRedirectNoRabbit(MyTerrierSignatureAction.class, MyTerrierSignatureForm.class, "execute", ActionTestBase.FORWARD_INPUT);
	}
}

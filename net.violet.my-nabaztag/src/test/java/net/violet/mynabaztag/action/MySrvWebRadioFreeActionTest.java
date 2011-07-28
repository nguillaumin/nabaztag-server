package net.violet.mynabaztag.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvWebRadioFreeConfigForm;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.StringShop;
import net.violet.platform.web.ServletTestBase;

import org.apache.struts.action.ActionMapping;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test pour MySrvWebRadioFreeAction
 */
public class MySrvWebRadioFreeActionTest extends ActionTestBase {

	/**
	 * Test if an object if the user is a V1 one in this case, this object
	 * doesn't be able to see the podcast's subcription page
	 */
	@Test
	public void testifObjectCanSeePodcastOrNot() {
		final MySrvWebRadioFreeAction MySrvWebRadioFreeAction = new MySrvWebRadioFreeAction();
		final MySrvWebRadioFreeConfigForm myForm = new MySrvWebRadioFreeConfigForm();
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		final User myUser = getActionTestUser();
		final List<VObject> myObjects = Factories.VOBJECT.findByOwner(myUser);

		HttpSession session;
		HttpServletRequest myRequest;

		for (final VObject myObject : myObjects) {
			session = ActionTestBase.createUserRabbitSession(myUser, myObject);
			myRequest = ServletTestBase.createRequest(session);
			myForm.setDispatch(StringShop.EMPTY_STRING);
			MySrvWebRadioFreeAction.execute(actionMap, myForm, myRequest, myResponse);

			if (myObject.getHardware() == HARDWARE.V1) {
				Assert.assertEquals("true", myForm.getIsV1());
			} else {
				Assert.assertEquals("false", myForm.getIsV1());
			}

		}
	}
}

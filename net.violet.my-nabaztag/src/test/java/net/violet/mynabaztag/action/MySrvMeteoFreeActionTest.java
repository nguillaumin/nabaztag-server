package net.violet.mynabaztag.action;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvMeteoFreeForm;
import net.violet.platform.applications.WeatherHandler;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.SubscriptionMock;
import net.violet.platform.datamodel.mock.SubscriptionSchedulingMock;
import net.violet.platform.util.StringShop;
import net.violet.platform.web.ServletTestBase;

import org.apache.struts.action.ActionMapping;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests pour MySrvMeteoAction
 */
public class MySrvMeteoFreeActionTest extends ActionTestBase {

	/**
	 * Activate the service
	 */
	private MySrvMeteoFreeForm activateMeteo(User myUser, VObject myObject, String city, int degree, String horaire1, String horaire2, int light) {

		final MySrvMeteoFreeAction mySrvMeteoFreeAction = new MySrvMeteoFreeAction();
		final MySrvMeteoFreeForm myForm = new MySrvMeteoFreeForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		myForm.setIdVille(city);
		myForm.setTypedeg(degree);
		myForm.setHorraire1(horaire1);
		myForm.setHorraire2(horaire2);
		myForm.setLumiere(light);

		mySrvMeteoFreeAction.activate(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Update the service
	 */
	private MySrvMeteoFreeForm updateMeteo(User myUser, VObject myObject, String city, int degree, String horaire1, String horaire2, int light) {

		final MySrvMeteoFreeAction mySrvMeteoFreeAction = new MySrvMeteoFreeAction();
		final MySrvMeteoFreeForm myForm = new MySrvMeteoFreeForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		myForm.setIdVille(city);
		myForm.setTypedeg(degree);
		myForm.setHorraire1(horaire1);
		myForm.setHorraire2(horaire2);
		myForm.setLumiere(light);

		mySrvMeteoFreeAction.update(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Load the service
	 */
	private MySrvMeteoFreeForm loadMeteo(User myUser, VObject myObject) {

		final MySrvMeteoFreeAction mySrvMeteoFreeAction = new MySrvMeteoFreeAction();
		final MySrvMeteoFreeForm myForm = new MySrvMeteoFreeForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		mySrvMeteoFreeAction.load(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	@Test
	public void testEmptySessionRedirect() {
		ActionTestBase.testRedirectEmptySession(MySrvMeteoFreeAction.class, MySrvMeteoFreeForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvMeteoFreeAction.class, MySrvMeteoFreeForm.class, "activate", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvMeteoFreeAction.class, MySrvMeteoFreeForm.class, "update", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvMeteoFreeAction.class, MySrvMeteoFreeForm.class, "delete", ActionTestBase.FORWARD_LOGIN);
	}

	@Test
	public void testNoRabbitRedirect() {
		testRedirectNoRabbit(MySrvMeteoFreeAction.class, MySrvMeteoFreeForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvMeteoFreeAction.class, MySrvMeteoFreeForm.class, "activate", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvMeteoFreeAction.class, MySrvMeteoFreeForm.class, "update", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvMeteoFreeAction.class, MySrvMeteoFreeForm.class, "delete", ActionTestBase.FORWARD_LOGIN);
	}

	/**
	 * Test if an user can susbcribe to the service with only the
	 * "light language"
	 * 
	 */
	// @Test
	public void activateWithWrongSrc() {

		final User myUser = getActionTestUser();
		final VObject myObject = Factories.VOBJECT.findByOwner(myUser).get(0);
		final Application weatherApp = new ApplicationMock(1, "net.violet.weather", myUser, new Date());

		/* Subscription */
		MySrvMeteoFreeForm myForm = activateMeteo(myUser, myObject, "Nmeteo.Jupiter.Europe.weather", 1, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, 1);

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadMeteo(myUser, myObject);
		Assert.assertEquals(0, myForm.getIsReg());
		weatherApp.delete();
	}

	/**
	 * Test if the defusing of the audio flashes works
	 * 
	 */
	// @Test
	public void suppressAudioFlashes() {

		final User myUser = getActionTestUser();
		final VObject myObject = Factories.VOBJECT.findByOwner(myUser).get(0);
		final Application weatherApp = new ApplicationMock(1, "net.violet.weather", myUser, new Date());
		final Subscription theSubscription = new SubscriptionMock(1, weatherApp, myObject);
		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(WeatherHandler.UNIT, "1");
		settings.put(WeatherHandler.LANGUAGE, "fr-FR");
		settings.put(WeatherHandler.SOURCE, "Nmeteo.JAPON.Osaka.weather");
		theSubscription.setSettings(settings);
		Factories.SOURCE.createNewSource("Nmeteo.JAPON.Osaka.weather", 1);
		final SubscriptionScheduling thesubsSchedul = new SubscriptionSchedulingMock(1, theSubscription, SchedulingType.SCHEDULING_TYPE.Daily);
		final SubscriptionScheduling thesubsSchedul2 = new SubscriptionSchedulingMock(2, theSubscription, SchedulingType.SCHEDULING_TYPE.Ambiant);

		/* Subscription */
		MySrvMeteoFreeForm myForm = activateMeteo(myUser, myObject, "Nmeteo.JAPON.Osaka.weather", 1, Time.valueOf("10:00:00").toString(), StringShop.EMPTY_STRING, 1);

		final String formerSchedule1 = myForm.getHorraire1();
		/* Update settings */
		thesubsSchedul.delete();
		updateMeteo(myUser, myObject, "Nmeteo.JAPON.Osaka.weather", 1, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, 1);
		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadMeteo(myUser, myObject);
		Assert.assertNotSame(formerSchedule1, myForm.getHorraire1());
		thesubsSchedul2.delete();
		theSubscription.delete();
		weatherApp.delete();
	}

}

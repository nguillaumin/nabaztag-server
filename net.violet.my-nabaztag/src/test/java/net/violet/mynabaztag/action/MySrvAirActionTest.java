package net.violet.mynabaztag.action;

import java.sql.Time;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvAirForm;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.SourceMock;
import net.violet.platform.util.StringShop;
import net.violet.platform.web.ServletTestBase;

import org.apache.struts.action.ActionMapping;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests pour MySrvAirAction
 */
public class MySrvAirActionTest extends ActionTestBase {

	/**
	 * Activate the service
	 */
	private MySrvAirForm activateAir(User myUser, VObject myObject, String city, String flash1, String flash2, int light) {

		final MySrvAirAction mySrvAirAction = new MySrvAirAction();
		final MySrvAirForm myForm = new MySrvAirForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		myForm.setVille(city);
		myForm.setHorraire1(flash1);
		myForm.setHorraire2(flash2);
		myForm.setLumiere(light);
		myForm.setLangSrv(myObject.getPreferences().getLangPreferences().getId().intValue());

		mySrvAirAction.activate(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Update the service
	 */
	private MySrvAirForm updateAir(User myUser, VObject myObject, String city, String horaire1, String horaire2, int light) {

		final MySrvAirAction mySrvAirAction = new MySrvAirAction();
		final MySrvAirForm myForm = new MySrvAirForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		myForm.setVille(city);
		myForm.setHorraire1(horaire1);
		myForm.setHorraire2(horaire2);
		myForm.setLumiere(light);
		myForm.setLangSrv(myObject.getPreferences().getLangPreferences().getId().intValue());

		mySrvAirAction.update(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Delete the service
	 */
	private MySrvAirForm deleteAir(User myUser, VObject myObject) {

		final MySrvAirAction mySrvAirAction = new MySrvAirAction();
		final MySrvAirForm myForm = new MySrvAirForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		mySrvAirAction.delete(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Load the service
	 */
	private MySrvAirForm loadAir(User myUser, VObject myObject) {

		final MySrvAirAction mySrvAirAction = new MySrvAirAction();
		final MySrvAirForm myForm = new MySrvAirForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		mySrvAirAction.load(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	@Test
	public void testEmptySessionRedirect() {
		ActionTestBase.testRedirectEmptySession(MySrvAirAction.class, MySrvAirForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvAirAction.class, MySrvAirForm.class, "activate", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvAirAction.class, MySrvAirForm.class, "update", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvAirAction.class, MySrvAirForm.class, "delete", ActionTestBase.FORWARD_LOGIN);
	}

	@Test
	public void testNoRabbitRedirect() {
		testRedirectNoRabbit(MySrvAirAction.class, MySrvAirForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvAirAction.class, MySrvAirForm.class, "activate", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvAirAction.class, MySrvAirForm.class, "update", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvAirAction.class, MySrvAirForm.class, "delete", ActionTestBase.FORWARD_LOGIN);
	}

	@Test
	public void checkAll() {
		final User myUser = getActionTestUser();
		final VObject myObjectv2 = Factories.VOBJECT.findByOwnerAndHardware(myUser, HARDWARE.V2);

		activateWithLightLanguage(myUser, myObjectv2);
		activateService2flash(myUser, myObjectv2);
		suppressAudioFlashes(myUser, myObjectv2);
	}

	/**
	 * Test if an user can susbcribe to the service with only the
	 * "light language"
	 * 
	 */
	private void activateWithLightLanguage(User myUser, VObject myObject) {

		/* Subscription */
		new SourceMock(0, "air.paris.today", 0);
		MySrvAirForm myForm = activateAir(myUser, myObject, "air.paris.today", StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, 1);

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadAir(myUser, myObject);

		// aucun flash audio
		// assertEquals(0, theConnection.doQueryIntV(
		// "SELECT COUNT(srvair_id) FROM srvair WHERE srvair_id="
		// +theSrv.get(0).getId()));
		Assert.assertEquals(1, myForm.getIsReg());

		/* delete */
		deleteService(myUser, myObject);
	}

	/**
	 * Activation avec deux flash puis supression de ceux là
	 */
	private void suppressAudioFlashes(User myUser, VObject myObject) {

		/* Subscription */
		MySrvAirForm myForm = activateAir(myUser, myObject, "air.belgique.Bruxelles.today", Time.valueOf("10:00:00").toString(), StringShop.EMPTY_STRING, 1);

		// 2 flash audio
		// assertEquals(2, theConnection.doQueryIntV(
		// "SELECT COUNT(srvair_id) FROM srvair WHERE srvair_id="
		// +theSrv.get(0).getId()));
		Assert.assertEquals(1, myForm.getIsReg());

		/* Update settings - Case the schedule setted was the second one */
		updateAir(myUser, myObject, "air.belgique.Bruxelles.today", StringShop.EMPTY_STRING, Time.valueOf("11:00:00").toString(), 1);

		/* Update settings */
		updateAir(myUser, myObject, "air.belgique.Bruxelles.today", StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, 1);

		// aucun flash audio
		// assertEquals(0, theConnection.doQueryIntV(
		// "SELECT COUNT(srvair_id) FROM srvair WHERE srvair_id="
		// +theSrv2.get(0).getId()));
		Assert.assertEquals(1, myForm.getIsReg());

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadAir(myUser, myObject);
		Assert.assertEquals(StringShop.EMPTY_STRING, myForm.getHorraire1());
		Assert.assertEquals(StringShop.EMPTY_STRING, myForm.getHorraire2());

		/* delete */
		deleteService(myUser, myObject);
	}

	private void activateService2flash(User myUser, VObject myObject) {

		/* Subscription */
		new SourceMock(0, "air.belgique.Bruxelles.today", 0);
		MySrvAirForm myForm = activateAir(myUser, myObject, "air.belgique.Bruxelles.today", Time.valueOf("10:00:00").toString(), Time.valueOf("11:00:00").toString(), 1);
		final String formerSchedule1 = myForm.getHorraire1();
		final String formerSchedule2 = myForm.getHorraire2();

		// 2 flash audio
		// assertEquals(2, theConnection.doQueryIntV(
		// "SELECT COUNT(srvair_id) FROM srvair WHERE srvair_id="
		// +theSrv.get(0).getId()));
		Assert.assertEquals(1, myForm.getIsReg());

		// final long hour = ConvertTools.atol(theConnection.doQueryString(
		// "SELECT HOUR(from_unixtime(srvair_nexttime)) FROM srvair WHERE srvair_id="
		// +theSrv.get(0).getId()+" AND srvair_time LIKE '10:%'"));

		// final long minute = ConvertTools.atol(theConnection.doQueryString(
		// "SELECT MINUTE(from_unixtime(srvair_nexttime)) FROM srvair WHERE srvair_id="
		// +theSrv.get(0).getId()+" AND srvair_time LIKE '10:%'"));

		// assertEquals(10, hour);
		// assertEquals(00, minute);
		// final long hour2 = ConvertTools.atol(theConnection.doQueryString(
		// "SELECT HOUR(from_unixtime(srvair_nexttime)) FROM srvair WHERE srvair_id="
		// +theSrv.get(0).getId()+" AND srvair_time LIKE '11:%'"));

		// final long minute2 = ConvertTools.atol(theConnection.doQueryString(
		// "SELECT MINUTE(from_unixtime(srvair_nexttime)) FROM srvair WHERE srvair_id="
		// +theSrv.get(0).getId()+" AND srvair_time LIKE '11:%'"));
		// assertEquals(11, hour2);
		// assertEquals(00, minute2);

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadAir(myUser, myObject);
		Assert.assertEquals(formerSchedule1, myForm.getHorraire1());
		Assert.assertEquals(formerSchedule2, myForm.getHorraire2());
		Assert.assertEquals(myForm.getVille(), "air.belgique.Bruxelles.today");

		/* delete */
		deleteService(myUser, myObject);
	}

	private void deleteService(User myUser, VObject myObject) {
		/* delete */
		Assert.assertEquals(0, deleteAir(myUser, myObject).getIsReg());

	}
}

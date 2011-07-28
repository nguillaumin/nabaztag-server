package net.violet.mynabaztag.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvBourseFullForm;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.TtsVoice;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.TtsVoiceMock;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.util.StringShop;
import net.violet.platform.web.ServletTestBase;

import org.apache.struts.action.ActionMapping;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests pour MySrvBourseFullAction.
 * 
 */
public class MySrvBourseFullActionTest extends ActionTestBase {

	private final String indic_name = "money.NIKKEI 225";
	private final String alert_name = "Watashi no Keihou";
	private final String alert_name2 = "ni-Keihou";
	private final String alert_name_22char = "abcdefghijklmnopqrstuvwxyz";

	/**
	 * Activate the service
	 */
	private MySrvBourseFullForm activateBourseFull(User myUser, VObject myObject, String alertName, String valName, String indic, String horaire1, String horaire2, int light, int weekend) {

		final MySrvBourseFullAction mySrvBourseFullAction = new MySrvBourseFullAction();
		final MySrvBourseFullForm myForm = new MySrvBourseFullForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		myForm.setAlertName(alertName);
		myForm.setValName(valName);
		myForm.setIndic(indic);
		myForm.setHorraire1(horaire1);
		myForm.setHorraire2(horaire2);
		myForm.setLumiere(light);
		myForm.setWeekend(weekend);

		mySrvBourseFullAction.activate(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Delete the service
	 */
	private MySrvBourseFullForm deleteBourseFull(User myUser, VObject myObject, int alter_id) {

		final MySrvBourseFullAction mySrvBourseFullAction = new MySrvBourseFullAction();
		final MySrvBourseFullForm myForm = new MySrvBourseFullForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		myForm.setValueTo(alter_id);

		mySrvBourseFullAction.delete(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	/**
	 * Load the service
	 */
	private MySrvBourseFullForm loadBourseFull(User myUser, VObject myObject) {

		final MySrvBourseFullAction mySrvBourseFullAction = new MySrvBourseFullAction();
		final MySrvBourseFullForm myForm = new MySrvBourseFullForm();
		final HttpSession session = ActionTestBase.createUserRabbitSession(myUser, myObject);
		final HttpServletRequest myRequest = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final ActionMapping actionMap = ActionTestBase.createMapping();

		mySrvBourseFullAction.load(actionMap, myForm, myRequest, myResponse);
		return myForm;
	}

	@Test
	public void testEmptySessionRedirect() {
		ActionTestBase.testRedirectEmptySession(MySrvBourseFullAction.class, MySrvBourseFullForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvBourseFullAction.class, MySrvBourseFullForm.class, "activate", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvBourseFullAction.class, MySrvBourseFullForm.class, "update", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvBourseFullAction.class, MySrvBourseFullForm.class, "display", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MySrvBourseFullAction.class, MySrvBourseFullForm.class, "delete", ActionTestBase.FORWARD_LOGIN);
	}

	@Test
	public void testNoRabbitRedirect() {
		testRedirectNoRabbit(MySrvBourseFullAction.class, MySrvBourseFullForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvBourseFullAction.class, MySrvBourseFullForm.class, "activate", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvBourseFullAction.class, MySrvBourseFullForm.class, "update", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvBourseFullAction.class, MySrvBourseFullForm.class, "display", ActionTestBase.FORWARD_LOGIN);
		testRedirectNoRabbit(MySrvBourseFullAction.class, MySrvBourseFullForm.class, "delete", ActionTestBase.FORWARD_LOGIN);
	}

	@Test
	public void checkAll() {
		final TtsVoice theTtsVoice = new TtsVoiceMock(1, "Heather English United States F HQ", "heather22k", getFrLang(), "Heather", true, false);
		final User myUser = getActionTestUser();
		final VObject myObjectv2 = Factories.VOBJECT.findByOwnerAndHardware(myUser, HARDWARE.V2);

		activateWithLightLanguage(myUser, myObjectv2);

		// suppressAudioFlashes(myUser, myObjectv2);
		// isMyInformationStillHere(myUser, myObjectv2);
		// activateWithWrongPersonalIndic(myUser, myObjectv2);
		// activateWithGoodPersonalIndic(myUser, myObjectv2);
		// addSeveralAlertsAndDeleteOne(myUser, myObjectv2);
		// addSeveralAlertsAndDeleteAll(myUser, myObjectv2);
		theTtsVoice.delete();
	}

	private void activateWithLightLanguage(User myUser, VObject myObject) {

		/* Subscription */
		final MySrvBourseFullForm myForm = activateBourseFull(myUser, myObject, this.alert_name_22char, StringShop.EMPTY_STRING, this.indic_name, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, 1, 1);

		/*
		 * Creation de TTS non possible, donc l'inscription ne peut pas se
		 * terminer
		 */

		/* Simulate the TTS was created */
		final Music theMusic = createMusic(myUser);

		// une row dans srv
		// List<SubscriptionData> subscriptions =
		// SubscriptionData.findByApplicationAndObject
		// (Application.NativeApplication.BOURSE_FULL.getApplication(),
		// myObject);
		// assertTrue(!subscriptions.isEmpty());
		// assertEquals(1,subscriptions.size());
		// final int id = (int)subscriptions.get(0).getId();

		/* Refresh and purge ActionForm to recover the correct information saved */
		// myForm = loadBourseFull(myUser, myObject);
		// assertEquals(1, myForm.getIsReg());
		/* delete */
		// deleteService(myUser,myObject, myForm, id);
		/* Supprime le mock */
		theMusic.delete();
	}

	public void addSeveralAlertsAndDeleteOne(User myUser, VObject myObject) {

		/* Subscription */
		MySrvBourseFullForm myForm = activateBourseFull(myUser, myObject, this.alert_name, StringShop.EMPTY_STRING, this.indic_name, "10:20", "11:11", 1, 1);
		Assert.assertEquals(1, myForm.getIsReg());
		/*
		 * Simulate the TTS was created (Update in SrvBourseImpl and INSERT in
		 * music)
		 */
		createMusic(myUser);

		/* Second Alert with name of the first one */
		myForm = activateBourseFull(myUser, myObject, this.alert_name, StringShop.EMPTY_STRING, this.indic_name, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, 1, 1);
		Assert.assertEquals(1, myForm.getIsReg());
		List<SubscriptionData> subscriptions = SubscriptionData.findByApplicationAndObject(Application.NativeApplication.BOURSE_FULL.getApplication(), myObject);
		final int id1 = (int) subscriptions.get(0).getId();

		/* Second Alert */
		myForm = activateBourseFull(myUser, myObject, this.alert_name2, StringShop.EMPTY_STRING, this.indic_name, "20:40", "22:42", 1, 1);
		Assert.assertEquals(1, myForm.getIsReg());
		subscriptions = SubscriptionData.findByApplicationAndObject(Application.NativeApplication.BOURSE_FULL.getApplication(), myObject);
		final int id2 = (int) subscriptions.get(1).getId();
		// final Srv theSrv2 = Factories.SRV.findByObjectAndName(myObject,
		// alert_name);
		// final int id2 = theSrv2.getId().intValue();

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadBourseFull(myUser, myObject);
		Assert.assertEquals(2, myForm.getSupervisedList().size());
		/* Defusing */
		deleteBourseFull(myUser, myObject, id1);

		/* Refresh and purge ActionForm to recover the correct information saved */
		myForm = loadBourseFull(myUser, myObject);
		Assert.assertEquals(1, myForm.getSupervisedList().size());

		/* Defusing */
		deleteBourseFull(myUser, myObject, id2);
		/* delete the music created */
	}

}

package net.violet.platform.message.application.factories;

import java.util.List;

import net.violet.platform.daemons.schedulers.AbstractScheduler.MessageNSettingProcessUnit;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.SubscriptionMock;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.Sequence;
import net.violet.platform.message.SequenceImpl;
import net.violet.platform.message.application.factories.AbstractMessageFactory.Message2Send;
import net.violet.platform.message.elements.Expression;
import net.violet.platform.message.elements.StreamWrapper;
import net.violet.platform.schedulers.DailyHandler;
import net.violet.platform.schedulers.DailyWithDurationHandler;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.JabberMessageFactory;

import org.junit.Assert;
import org.junit.Test;

public class WebRadioMessageFactoryTest extends MockTestBase {

	private static final String WR_URL = "http://129.79.9.25:8000";

	private void validateCommonMessage(Message2Send inMessage) {
		Assert.assertNull(inMessage.getBodies());
		Assert.assertNull(inMessage.getBody());
		Assert.assertNull(inMessage.getSignature());
		Assert.assertEquals(Palette.RANDOM, inMessage.getColorPal());
		Assert.assertEquals(WebRadioMessageFactory.TITLE, inMessage.getTitle());
		Assert.assertEquals(Constantes.QUEUE_TTL_FIVE_MINUTES, inMessage.getTTL());

	}

	@Test
	public void getMessageStartNoDeliveryDateTest() {
		final long now = System.currentTimeMillis();
		final List<Message2Send> theMessageList = WebRadioMessageFactory.getMessage2Send(WebRadioMessageFactoryTest.WR_URL, getBrewsterObject(), null, 0, now);

		Assert.assertEquals(1, theMessageList.size());

		final Message2Send theMessage = theMessageList.get(0);
		Assert.assertEquals(JabberMessageFactory.IDLE_MODE, theMessage.getMode());
		validateCommonMessage(theMessage);
		final MessageDraft theMessageDraft = theMessage.generateMessageDraft();
		Assert.assertNull(theMessageDraft.getDeliveryDate());

		for (final Object theObject : theMessageDraft.getSequencePart()) {
			boolean isInstance;

			if ((isInstance = (theObject instanceof StreamWrapper)) == true) {
				Assert.assertEquals(new SequenceImpl(Sequence.SEQ_STREAMING_ID, String.valueOf(now)), ((StreamWrapper) theObject).getSequence(null).get(0));
			} else if ((isInstance = (theObject instanceof Expression)) == true) {

				final Expression theExpression = (Expression) theObject;

				Assert.assertEquals(WebRadioMessageFactoryTest.WR_URL, theExpression.getPojo().get("url"));
				Assert.assertEquals("net.violet.webradio", theExpression.getModality());
			}

			Assert.assertTrue(isInstance);
		}
	}

	@Test
	public void getMessageStartDeliveryDateTest() {
		final long now = System.currentTimeMillis();
		final CCalendar theDeliveryDate = new CCalendar(now + 60000L);
		final List<Message2Send> theMessageList = WebRadioMessageFactory.getMessage2Send(WebRadioMessageFactoryTest.WR_URL, getBrewsterObject(), theDeliveryDate, 0, now);

		Assert.assertEquals(1, theMessageList.size());

		final Message2Send theMessage = theMessageList.get(0);
		Assert.assertEquals(JabberMessageFactory.IDLE_MODE, theMessage.getMode());
		validateCommonMessage(theMessage);
		final MessageDraft theMessageDraft = theMessage.generateMessageDraft();
		Assert.assertNotNull(theMessageDraft.getDeliveryDate());
		Assert.assertEquals(theDeliveryDate, theMessageDraft.getDeliveryDate());

		for (final Object theObject : theMessageDraft.getSequencePart()) {
			boolean isInstance;

			if ((isInstance = (theObject instanceof StreamWrapper)) == true) {
				Assert.assertEquals(new SequenceImpl(Sequence.SEQ_STREAMING_ID, String.valueOf(now)), ((StreamWrapper) theObject).getSequence(null).get(0));
			} else if ((isInstance = (theObject instanceof Expression)) == true) {

				final Expression theExpression = (Expression) theObject;

				Assert.assertEquals(WebRadioMessageFactoryTest.WR_URL, theExpression.getPojo().get("url"));
				Assert.assertEquals("net.violet.webradio", theExpression.getModality());
			}

			Assert.assertTrue(isInstance);
		}
	}

	@Test
	public void getMessageStartNEndNoDeliveryDateTest() {
		final long now = System.currentTimeMillis();
		final int playingFor = 10;
		final List<Message2Send> theMessageList = WebRadioMessageFactory.getMessage2Send(WebRadioMessageFactoryTest.WR_URL, getBrewsterObject(), null, playingFor, now);

		Assert.assertEquals(2, theMessageList.size());

		Message2Send theMessage = theMessageList.get(0);
		validateCommonMessage(theMessage);

		MessageDraft theMessageDraft = theMessage.generateMessageDraft();
		Assert.assertNull(theMessageDraft.getDeliveryDate());
		Assert.assertEquals(JabberMessageFactory.IDLE_MODE, theMessage.getMode());

		for (final Object anObject : theMessageDraft.getSequencePart()) {
			boolean isInstance;

			if ((isInstance = (anObject instanceof StreamWrapper)) == true) {
				Assert.assertEquals(new SequenceImpl(Sequence.SEQ_STREAMING_ID, String.valueOf(now)), ((StreamWrapper) anObject).getSequence(null).get(0));
			} else if ((isInstance = (anObject instanceof Expression)) == true) {

				final Expression theExpression = (Expression) anObject;

				Assert.assertEquals(WebRadioMessageFactoryTest.WR_URL, theExpression.getPojo().get("url"));
				Assert.assertEquals("net.violet.webradio", theExpression.getModality());
			}

			Assert.assertTrue(isInstance);
		}

		theMessage = theMessageList.get(1);
		validateCommonMessage(theMessage);

		theMessageDraft = theMessage.generateMessageDraft();
		Assert.assertNotNull(theMessageDraft.getDeliveryDate());
		Assert.assertEquals(CCalendar.getTimeInFuture(now, playingFor * 60000L), theMessageDraft.getDeliveryDate());
		Assert.assertEquals(1, theMessageDraft.getSequencePart().size());
		Assert.assertEquals(JabberMessageFactory.STREAMING_MODE, theMessage.getMode());
		final Object theObject = theMessageDraft.getSequencePart().get(0);

		boolean isInstance;

		if ((isInstance = (theObject instanceof StreamWrapper)) == true) {
			Assert.assertEquals(new SequenceImpl(Sequence.SEQ_STREAMING_STOP, String.valueOf(now)), ((StreamWrapper) theObject).getSequence(null).get(0));
		}

		Assert.assertTrue(isInstance);
	}

	@Test
	public void getMessageStartNEndDeliveryDateTest() {
		final long now = System.currentTimeMillis();
		final CCalendar theDeliveryDate = new CCalendar(now + 60000L);
		final int playingFor = 10;
		final List<Message2Send> theMessageList = WebRadioMessageFactory.getMessage2Send(WebRadioMessageFactoryTest.WR_URL, getBrewsterObject(), theDeliveryDate, playingFor, now);

		Assert.assertEquals(2, theMessageList.size());

		Message2Send theMessage = theMessageList.get(0);
		validateCommonMessage(theMessage);

		MessageDraft theMessageDraft = theMessage.generateMessageDraft();
		Assert.assertNotNull(theMessageDraft.getDeliveryDate());
		Assert.assertEquals(theDeliveryDate, theMessageDraft.getDeliveryDate());
		Assert.assertEquals(JabberMessageFactory.IDLE_MODE, theMessage.getMode());

		for (final Object anObject : theMessageDraft.getSequencePart()) {
			boolean isInstance;

			if ((isInstance = (anObject instanceof StreamWrapper)) == true) {
				Assert.assertEquals(new SequenceImpl(Sequence.SEQ_STREAMING_ID, String.valueOf(now)), ((StreamWrapper) anObject).getSequence(null).get(0));
			} else if ((isInstance = (anObject instanceof Expression)) == true) {

				final Expression theExpression = (Expression) anObject;

				Assert.assertEquals(WebRadioMessageFactoryTest.WR_URL, theExpression.getPojo().get("url"));
				Assert.assertEquals("net.violet.webradio", theExpression.getModality());
			}

			Assert.assertTrue(isInstance);
		}

		theMessage = theMessageList.get(1);
		validateCommonMessage(theMessage);

		theMessageDraft = theMessage.generateMessageDraft();
		Assert.assertNotNull(theMessageDraft.getDeliveryDate());
		theDeliveryDate.addMillis(playingFor * 60000);
		Assert.assertEquals(theDeliveryDate, theMessageDraft.getDeliveryDate());
		Assert.assertEquals(1, theMessageDraft.getSequencePart().size());
		Assert.assertEquals(JabberMessageFactory.STREAMING_MODE, theMessage.getMode());
		final Object theObject = theMessageDraft.getSequencePart().get(0);

		boolean isInstance;

		if ((isInstance = (theObject instanceof StreamWrapper)) == true) {
			Assert.assertEquals(new SequenceImpl(Sequence.SEQ_STREAMING_STOP, String.valueOf(now)), ((StreamWrapper) theObject).getSequence(null).get(0));
		}

		Assert.assertTrue(isInstance);
	}

	@Test
	public void testMessageNull() {
		final AbstractMessageFactory theWrFactory = AbstractMessageFactory.getFactoryByApplication(Factories.APPLICATION.findByName("net.violet.webradio.U105"));

		Assert.assertTrue(theWrFactory.getMessage(null).isEmpty());
	}

	@Test
	public void testMessageNSettingProcessUnit() {
		final Application theApplication = Factories.APPLICATION.findByName("net.violet.webradio.U105");
		Factories.APPLICATION_SETTING.create(theApplication, "url", "webradioUrl");
		final Subscription theSubscription = new SubscriptionMock(1, theApplication, getKowalskyObject());
		final CCalendar theDeliveryDate = new CCalendar(false);
		theDeliveryDate.addMillis(3600000L);

		final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
		final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.DailyWithDuration);
		theScheduling.createSetting(DailyHandler.Weekday.THURSDAY.getValue(), "10:30:00");
		final SubscriptionSchedulingSettingsData theSchedulingSetting = theScheduling.createSetting(DailyHandler.Weekday.THURSDAY.getValue(), "10:30:00");
		theScheduling.createSetting(DailyHandler.Weekday.THURSDAY.getValue() + DailyWithDurationHandler.DURATION_SUFFIXE, "113233313");

		final MessageNSettingProcessUnit theMessage = new MessageNSettingProcessUnit(theSchedulingSetting, theScheduling, null, null) {

			@Override
			public void runWhenSuccessful() {

			}
		};
		final AbstractMessageFactory theWrFactory = AbstractMessageFactory.getFactoryByApplication(Factories.APPLICATION.findByName("net.violet.webradio.U105"));

		Assert.assertEquals(2, theWrFactory.getMessage(theMessage).size());
	}

}

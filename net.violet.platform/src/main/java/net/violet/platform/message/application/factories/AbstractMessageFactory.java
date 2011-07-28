package net.violet.platform.message.application.factories;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.violet.common.StringShop;
import net.violet.common.utils.concurrent.BlockingExecutor;
import net.violet.common.utils.concurrent.ThreadWatcher;
import net.violet.platform.applications.AirHandler;
import net.violet.platform.daemons.schedulers.AbstractScheduler.MessageProcessUnit;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Source;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.message.Message;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.MessageServices;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.message.MessageServices.Body;
import net.violet.platform.schedulers.AmbiantHandler;
import net.violet.platform.schedulers.KeywordHandler;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.JabberMessageFactory;

import org.apache.log4j.Logger;

public abstract class AbstractMessageFactory {

	private static final Logger LOGGER = Logger.getLogger(AbstractMessageFactory.class);

	private static final Random randomGenerator = new Random(System.currentTimeMillis());

	public static <T> T randomElementFromArray(T[] inList) {
		final int theConfigFileId = AbstractMessageFactory.randomGenerator.nextInt(inList.length);
		return inList[theConfigFileId];
	}

	public static enum MESSAGE_TYPE {
		SOURCE_MESSAGE, FILES_MESSAGE;
	};

	protected static final Map<Application.NativeApplication, AbstractMessageFactory> MESSAGE_FACTORIES;

	private static BlockingExecutor<MessageSenderUnit> MESSAGE_FILE_SENDER = new BlockingExecutor<MessageSenderUnit>(10, new BlockingExecutor.Worker<MessageSenderUnit>() {

		public void process(MessageSenderUnit inUnit) {
			final AbstractMessageFactory theFactory = inUnit.getFactory();
			theFactory.sendFilesMessage(inUnit.getUnit());
			inUnit.runWhenSuccessful();

		}
	}, "MessageFiles-Sender");
	private static BlockingExecutor<MessageSenderUnit> MESSAGE_SOURCE_SENDER = new BlockingExecutor<MessageSenderUnit>(10, new BlockingExecutor.Worker<MessageSenderUnit>() {

		public void process(MessageSenderUnit inUnit) {
			final AbstractMessageFactory theFactory = inUnit.getFactory();
			theFactory.sendSourceMessage(inUnit.getUnit());
			inUnit.runWhenSuccessful();
		}
	}, "MessageSource-Sender");

	static {
		final Map<Application.NativeApplication, AbstractMessageFactory> theMessageFactories = new HashMap<Application.NativeApplication, AbstractMessageFactory>();

		theMessageFactories.put(Application.NativeApplication.WEATHER, new WeatherMessageFactory());
		theMessageFactories.put(Application.NativeApplication.AIR, new AirMessageFactory());
		AbstractMessageFactory theFactory = new VActionMessageFactory();
		theMessageFactories.put(Application.NativeApplication.RSS_FREE, theFactory);
		theMessageFactories.put(Application.NativeApplication.RSS_FULL, theFactory);
		theMessageFactories.put(Application.NativeApplication.PODCAST_FREE, theFactory);
		theMessageFactories.put(Application.NativeApplication.PODCAST_FULL, theFactory);
		theMessageFactories.put(Application.NativeApplication.BILAN, new BilanMessageFactory());
		theFactory = new TradeMessageFactory();
		theMessageFactories.put(Application.NativeApplication.BOURSE_FREE, theFactory);
		theMessageFactories.put(Application.NativeApplication.BOURSE_FULL, theFactory);
		theMessageFactories.put(Application.NativeApplication.MOOD, new MoodMessageFactory());
		theMessageFactories.put(Application.NativeApplication.TRAFIC, new TraficMessageFactory());
		theMessageFactories.put(Application.NativeApplication.WEBRADIO, new WebRadioMessageFactory());
		theFactory = new AlarmsMessageFactory();
		theMessageFactories.put(Application.NativeApplication.ALARM_CLOCK, theFactory);
		theMessageFactories.put(Application.NativeApplication.REMINDER, theFactory);
		theMessageFactories.put(Application.NativeApplication.CLOCK, new ClockMessageFactory());
		theMessageFactories.put(Application.NativeApplication.MAIL, new MailMessageFactory());
		theMessageFactories.put(Application.NativeApplication.TAICHI, new TaichiMessageFactory());

		MESSAGE_FACTORIES = Collections.unmodifiableMap(theMessageFactories);
	}

	public static final AbstractMessageFactory getFactoryByApplication(Application inApplication) {
		return AbstractMessageFactory.MESSAGE_FACTORIES.get(Application.NativeApplication.findByApplication(inApplication));
	}

	/**
	 * @param inSubscription
	 * @param inMessageType
	 */
	public static void sendMessage(SubscriptionSchedulingData inSubscriptionSchedulingData, MESSAGE_TYPE inMessageType) {
		AbstractMessageFactory.sendMessage(new MessageProcessUnit(inSubscriptionSchedulingData, null, null) {

			@Override
			public void runWhenSuccessful() {
				// NOTHING TO DO HERE
			}

		}, inMessageType);
	}

	/**
	 * @param inSubscription
	 * @param inDeliveryDate may be <code>null</code> (sends the message asap)
	 * @param inMessageType
	 */
	public static void sendMessage(final MessageProcessUnit inUnit, final MESSAGE_TYPE inMessageType) {
		final AbstractMessageFactory theFactory = AbstractMessageFactory.getFactoryByApplication(inUnit.get().getSubscription().getApplication().getReference());

		try {

			if (inMessageType == MESSAGE_TYPE.FILES_MESSAGE) {
				AbstractMessageFactory.MESSAGE_FILE_SENDER.put(new MessageSenderUnit(inUnit, theFactory));
			} else if ((inMessageType == MESSAGE_TYPE.SOURCE_MESSAGE) && (theFactory.getSource() != null)) {
				AbstractMessageFactory.MESSAGE_SOURCE_SENDER.put(new MessageSenderUnit(inUnit, theFactory));
			}
		} catch (final InterruptedException e) {
			AbstractMessageFactory.LOGGER.fatal(e, e);
		}
	}

	private void sendSourceMessage(MessageProcessUnit inMessage) {
		final MessageDraft theMessage = getSourceMessage(inMessage, System.currentTimeMillis());

		if (theMessage != null) {
			if (theMessage.getReceiver() == null) {
				AbstractMessageFactory.LOGGER.fatal("receiver null :" + theMessage.getTitle() + " on source :" + getSource());
			}
			MessageServices.sendUsingXmpp(theMessage, JabberMessageFactory.SOURCES_MODE);
		}

	}

	private void sendFilesMessage(final MessageProcessUnit inUnit) {
		AbstractMessageFactory.sendMessage(getMessage(inUnit));
	}

	public static void sendMessage(List<Message2Send> inMessage2Send) {

		for (final Message2Send aMessage : inMessage2Send) {
			try {
				final Body[] theBody = aMessage.getBodies();
				MessageServices.send(aMessage.generateMessageDraft(), aMessage.getDeliveryDate(), aMessage.getMode(), theBody);
				aMessage.runWhenSent();
			} catch (final Exception e) {
				AbstractMessageFactory.LOGGER.fatal(e, e);
			}
		}
	}

	protected MessageDraft getSourceMessage(SubscriptionSchedulingData inSchedulingData, long inLastTime) {
		final ThreadWatcher theWatcher = new ThreadWatcher();
		final MessageDraft theDraft = getSourceMessage(new MessageProcessUnit(inSchedulingData, null, theWatcher, AbstractMessageFactory.ACTION.ADD) {

			@Override
			public void runWhenSuccessful() {

			}
		}, inLastTime);

		theWatcher.await();

		return theDraft;
	}

	protected MessageDraft getSourceMessage(MessageProcessUnit inMessageProcessUnit, long inLastTime) {
		final SubscriptionSchedulingData theSubscriptionSchedulingData = inMessageProcessUnit.get();
		final SubscriptionData theSubscription = theSubscriptionSchedulingData.getSubscription();
		final SubscriptionSchedulingSettingsData lastTimeSetting = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(theSubscriptionSchedulingData, AmbiantHandler.LAST_TIME);
		MessageDraft theMessage = null;
		try {
			final long objectLastUpdate;

			if ((lastTimeSetting != null) && lastTimeSetting.isValid()) {
				objectLastUpdate = Long.parseLong(lastTimeSetting.getValue());
			} else {
				objectLastUpdate = 0;
			}

			final String theSourcePath = getAmbiantSourcePath(theSubscriptionSchedulingData);

			if (theSourcePath != null) {
				final Source theSource = Factories.SOURCE.findByPath(theSourcePath);

				if (theSource != null) {
					final long sourceLastUpdate = theSource.getSource_time() * 1000;

					if ((inMessageProcessUnit.isAdd() && (objectLastUpdate < sourceLastUpdate)) || (!inMessageProcessUnit.isAdd())) {
						theMessage = generateSourceMessage(theSubscription.getObject().getReference(), theSource, inMessageProcessUnit.isAdd());
					}
				}
			}
		} finally {
			if (theMessage != null) {
				if ((lastTimeSetting == null) || (!lastTimeSetting.isValid())) {
					theSubscriptionSchedulingData.createSetting(AmbiantHandler.LAST_TIME, Long.toString(inLastTime));
				} else {
					lastTimeSetting.setValue(Long.toString(inLastTime));
				}
			}

		}

		return theMessage;
	}

	protected String getAmbiantSourcePath(SubscriptionSchedulingData inSubscriptionSchedulingData) {
		final Object sourceSetting = inSubscriptionSchedulingData.getSubscription().getSettings().get(AirHandler.SOURCE_SETTING);

		if (sourceSetting != null) {
			if (inSubscriptionSchedulingData.getType() == SCHEDULING_TYPE.Ambiant) {
				return sourceSetting.toString();

			} else if (inSubscriptionSchedulingData.getType() == SCHEDULING_TYPE.AmbiantWithKeyword) {
				final String keyword = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(inSubscriptionSchedulingData, KeywordHandler.KEYWORD).getValue();
				return sourceSetting.toString() + StringShop.POINT + keyword;
			}
		}
		return null;
	}

	private MessageDraft generateSourceMessage(VObject inObject, Source inSource, boolean isAdd) {
		final MessageDraft theMessage = new MessageDraft(inObject);
		if (isAdd) {
			theMessage.setSourceValue(getSource(), (int) inSource.getSource_val());
		} else {
			theMessage.setSourceValue(Message.SOURCE.DELETE, getSource().getId());
		}
		theMessage.setSourceUpdate(true);
		theMessage.setTTLInSecond(Constantes.QUEUE_TTL_SOURCES);
		return theMessage;
	}

	protected Message.SOURCE getSource() {
		return null;
	}

	public abstract List<Message2Send> getMessage(MessageProcessUnit inUnit);

	public static abstract class Message2Send {

		private final SubscriptionSchedulingData mSubscriptionSchedulingData;
		private final CCalendar scheduledDeliveryDate;
		private VObject receiver;

		private Body[] mBodies = null;

		/**
		 * @param inVObject
		 * @param inSubscriptionSchedulingData
		 * @param inScheduledDeliveryDate
		 */
		protected Message2Send(SubscriptionSchedulingData inSubscriptionSchedulingData, VObject inVObject, CCalendar inScheduledDeliveryDate) {
			this.mSubscriptionSchedulingData = inSubscriptionSchedulingData;
			this.scheduledDeliveryDate = inScheduledDeliveryDate;
			this.receiver = inVObject;
		}

		/**
		 * @param inSubscriptionSchedulingData
		 * @param inScheduledDeliveryDate
		 */
		protected Message2Send(SubscriptionSchedulingData inSubscriptionSchedulingData, CCalendar inScheduledDeliveryDate) {
			this.mSubscriptionSchedulingData = inSubscriptionSchedulingData;
			this.scheduledDeliveryDate = inScheduledDeliveryDate;
			this.receiver = this.mSubscriptionSchedulingData.getSubscription().getObject().getReference();
		}

		protected CCalendar getDeliveryDate() {
			return this.scheduledDeliveryDate;
		}

		protected Message2Send(net.violet.platform.datamodel.Message inMessage, VObject inVObject) {
			this.mSubscriptionSchedulingData = null;
			this.scheduledDeliveryDate = inMessage.getTimeOfDelivery();
			this.receiver = inVObject;
		}

		protected abstract Files[] getBody();

		protected Body[] getBodies() {
			if (this.mBodies == null) {
				synchronized (this.receiver) {
					if (this.mBodies == null) {
						final Files[] files = getBody();
						if (files != null) {
							final Body[] body = new Body[files.length];

							for (int i = 0; i < files.length; i++) {
								final Files theFile = files[i];
								if (theFile == null) {
									AbstractMessageFactory.LOGGER.fatal("File is null : " + getTitle() + " involved subscription : " + getSubscription().getId());
								} else {
									body[i] = new Body(theFile, getColorPal(), isStream());
								}
							}
							this.mBodies = body;
						}
					}
				}
			}
			return this.mBodies;
		}

		protected abstract String getTitle();

		protected abstract MessageSignature getSignature();

		protected int getMode() {
			return JabberMessageFactory.IDLE_MODE;
		}

		protected VObject getRecipient() {
			return this.receiver;
		}

		public void setRecipient(VObject inRecipient) {
			this.receiver = inRecipient;
		}

		protected int getTTL() {
			return Constantes.QUEUE_TTL_SERVICE;
		}

		protected Palette getColorPal() {
			return Palette.RANDOM;
		}

		protected boolean isStream() {
			return false;
		}

		protected final SubscriptionData getSubscription() {
			return this.mSubscriptionSchedulingData.getSubscription();
		}

		public void runWhenSent() {

		}

		public MessageDraft generateMessageDraft() {
			final MessageDraft msgDraft = MessageServices.createMessageDraft(getRecipient(), getTitle(), getBodies(), getSignature());
			msgDraft.setTTLInSecond(getTTL());

			if (this.scheduledDeliveryDate != null) {
				msgDraft.setDeliveryDate(this.scheduledDeliveryDate);
			}

			return msgDraft;
		}

	}

	private static class MessageSenderUnit {

		private final MessageProcessUnit mUnit;
		private final AbstractMessageFactory mFactory;

		private MessageSenderUnit(MessageProcessUnit inUnit, AbstractMessageFactory inFactory) {
			this.mUnit = inUnit;
			this.mFactory = inFactory;
		}

		private MessageProcessUnit getUnit() {
			return this.mUnit;
		}

		private AbstractMessageFactory getFactory() {
			return this.mFactory;
		}

		public void runWhenSuccessful() {
			this.mUnit.runWhenSuccessful();
		}

	}

	public static enum ACTION {
		ADD, REMOVE;
	}

}

package net.violet.platform.message.application.factories;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.violet.platform.applications.VActionFreeHandler;
import net.violet.platform.applications.VActionFullHandler;
import net.violet.platform.daemons.schedulers.AbstractScheduler.MessageProcessUnit;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationSetting;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Service;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.ServiceFactory.SERVICE;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ApplicationSettingData;
import net.violet.platform.dataobjects.FeedData;
import net.violet.platform.dataobjects.FeedItemData;
import net.violet.platform.dataobjects.FeedSubscriptionData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.util.CCalendar;

class VActionMessageFactory extends AbstractMessageFactory {

	protected static String buildTitle(String serviceLabel, String contentTitle) {
		return serviceLabel.toUpperCase() + ": " + contentTitle;
	}

	protected abstract class VActionMessage2Send extends Message2Send {

		private final Service mService;

		protected VActionMessage2Send(SubscriptionSchedulingData inSubscriptionScheduling, Service inService, CCalendar inDeliveryDate) {
			super(inSubscriptionScheduling, inDeliveryDate);
			this.mService = inService;
		}

		@Override
		protected final int getTTL() {
			return this.mService.getTtl();
		}

		protected String getServiceLabel() {
			return this.mService.getLabel();
		}
	}

	private FeedData getFeed(Subscription subscription) {
		if (subscription.getApplication().equals(Application.NativeApplication.RSS_FULL.getApplication()) || subscription.getApplication().equals(Application.NativeApplication.PODCAST_FULL.getApplication())) {
			final Object feedSetting = subscription.getSettings().get(VActionFullHandler.FEED);
			return FeedData.findById(Long.parseLong(feedSetting.toString()));
		}

		final String feedId = ApplicationSettingData.findByApplicationAndKey(ApplicationData.getData(subscription.getApplication()), ApplicationSetting.FeedHandler.FEED_ID).getValue();
		return FeedData.findById(Long.parseLong(feedId));
	}

	@Override
	public List<Message2Send> getMessage(MessageProcessUnit inUnit) {

		final SubscriptionSchedulingData theScheduling = inUnit.get();
		final SubscriptionData theSubscription = theScheduling.getSubscription();
		final Map<String, Object> theSettings = theSubscription.getSettings();

		final FeedData feed = getFeed(theSubscription.getReference());
		final VObject theRecipient = theSubscription.getObject().getReference();

		final int theNbNews = Integer.parseInt(theSettings.get(VActionFreeHandler.NB_NEWS).toString());

		final List<FeedItemData> itemsToSend;

		if (theScheduling.getType() == SCHEDULING_TYPE.InteractionTrigger) {
			itemsToSend = FeedItemData.findAllByFeed(feed, theNbNews);
		} else {
			// some subscriptions failed and did not create the FeedSubscription, here we try to save them !
			FeedSubscriptionData feedSubscription = FeedSubscriptionData.findByObjectAndFeed(theSubscription.getObject(), feed);
			if ((feedSubscription == null) || !feedSubscription.isValid()) {
				feedSubscription = FeedSubscriptionData.create(feed, theSubscription.getObject(), theNbNews);
			}

			itemsToSend = FeedItemData.findUnreadForSubscription(feedSubscription, theNbNews);
		}

		/************************************************/

		if (!itemsToSend.isEmpty()) {

			final Files[] theBody = new Files[itemsToSend.size() + 1];
			int i = 0;

			if (!theSettings.containsKey(VActionFullHandler.FILE)) {
				theBody[i++] = theSubscription.getApplication().getProfile().getFile().getReference();
			} else {
				theBody[i++] = Factories.FILES.find(Long.parseLong(theSettings.get(VActionFullHandler.FILE).toString()));
			}

			for (final FeedItemData anItem : itemsToSend) {
				theBody[i++] = anItem.getContents().get(0); // here we suppose that there is only one content per item, may change soon be careful!
			}

			final FeedItemData theLastKnownItem = itemsToSend.get(itemsToSend.size() - 1);

			final Service theService = feed.getType() == Feed.Type.PODCAST ? SERVICE.PODCAST.getService() : SERVICE.RSS.getService();
			return Collections.singletonList((Message2Send) new VActionMessage2Send(inUnit.get(), theService, inUnit.getProcessConditioner()) {

				@Override
				protected boolean isStream() {
					return SERVICE.PODCAST.getService().equals(theService);
				}

				@Override
				protected String getTitle() {
					return VActionMessageFactory.buildTitle(getServiceLabel(), theLastKnownItem.getTitle());
				}

				@Override
				protected MessageSignature getSignature() {
					return new MessageSignature(theService);
				}

				@Override
				protected Files[] getBody() {
					return theBody;
				}

				@Override
				public void runWhenSent() {
					// If the message went through then we believe the user got that news
					final FeedSubscriptionData subscription = FeedSubscriptionData.findByObjectAndFeed(VObjectData.getData(theRecipient), feed);
					if (subscription != null) {
						subscription.setLastReadItem(theLastKnownItem);
					}
				}
			});
		}
		return Collections.emptyList();
	}
}

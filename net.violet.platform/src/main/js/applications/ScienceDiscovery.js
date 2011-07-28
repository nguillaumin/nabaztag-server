/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "ScienceDiscovery",
	name: "net.violet.js.sciencediscovery"
};

/**
 * Event handlers
 */
appEventHandlers = {

	scienceDiscoveryFeed: "http://science.discovery.com/blocks/top-highlights.xml",

	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		// HTTP GET on the RSS Feed URL
		var feedContent = violet.net.httpRequest(this.scienceDiscoveryFeed);
		var channel = feedContent.body.rss.channel, feedItems = channel.item;

		// display the title of the feed
		var msg = violet.objects.createMessage(objectId);
		msg.addTextToSpeech(channel.title, "en").async()
			.showBubble(channel.title, channel.description, {pictureurl: channel.image.url, displaytime: 3000})
			.alternativelyAddTextToSpeech(channel.description, "en", "UK-Penelope")
		;

		// add the items
		for (var i = 0, len = feedItems.length, feedItem; (i < len) && (feedItem = feedItems[i]); i++) {

			msg.addTextToSpeech(feedItem.title, "en").async()
				.showBubble(feedItem.title, feedItem.description,
						{url: feedItem.link, pictureurl: feedItem["media:thumbnail"].attr_url, displaytime: 4000}
				)
				.alternativelyAddTextToSpeech(feedItem.description, "en", "UK-Penelope")
			;
		}
		return msg;
	}
};

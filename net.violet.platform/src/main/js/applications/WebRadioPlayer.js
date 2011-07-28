/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "WebRadioPlayer",
	name: "net.violet.js.webradioplayer"
};

/**
 * Event handlers
 */
appEventHandlers = {

	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		// read the initial context (settings of the application)
		var settings = new Settings(event.settings);
		var url = settings.getString("url", true);
		var radioName = settings.getString("name", appTitle);

		// get the application title and picture (for the bubble)
		var application = event.application; // a modification in the event object hierarchy
		var appInfo  = violet.applications.getProfile(this, application.id);
		var appTitle = violet.site.getLocalizedString(this, {id: event.trigger.object.owner.lang, key:appInfo.title});

		return violet.objects.createMessage(objectId)
			.playLibSound("iTunesPlayer").async()
			.showBubble(appTitle, radioName, {pictureurl: appInfo.picture, displaytime: 5000})
			.openWebRadio(url)
			.alternativelyPlayAudioStream(url, {streaming: true, withEar: true})
		;
	}
};

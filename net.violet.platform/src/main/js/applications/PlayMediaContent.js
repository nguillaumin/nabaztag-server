/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "PlayMediaContent",
	name: "net.violet.js.playmediacontent"
};

/**
 * Event handlers
 */
appEventHandlers = {

	notSupportedContent: "LOC_net.violet.application.messages.unsupported_content",

	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		// read the initial context (settings of the application)
		var settings = event.settings;
		var userLang = event.trigger.object.owner.lang;

		var msgNotSupportedContent = violet.site.getLocalizedString(this, {id: userLang, key: this.notSupportedContent});

		var sender = event.trigger.object.owner;

		// read file to send from settings
		var fileId = settings.mediaFileID;
		if (fileId == null) {
			return violet.objects.createMessage(objectId)
				.playLibSound("SendMediaContent")
				.addTextToSpeech("Error : no file to send. Check your parameters.", "en")
			;
		}

		// get the file from the library of the sender
		var itemInfo = violet.libraries.get(this, {id: fileId, userId: sender.id});
		if (itemInfo.length > 0) {
			// API call return an array !
			itemInfo = itemInfo[0];
		}
		var itemProfile = violet.libraries.getProfile(this, {id: itemInfo.id, userId: sender.id});
		var displayTime = (itemInfo && itemInfo.mime_type && itemInfo.mime_type.startsWith("audio")) ? 10000: 5000;
		debug("received info on file to send : " + itemInfo + ", " + itemProfile);

		// get the application installer title and picture
		var application = event.application || event.trigger.application; // a modification in the event object hierarchy
		var appInfo  = violet.applications.getProfile(this, application.id);
		var appTitle = violet.site.getLocalizedString(this, {id: userLang, key:appInfo.title});

		// return the message
		return violet.objects.createMessage(objectId)
			.playLibSound("SendMediaContent").async()
			.showBubble(appTitle, itemProfile.name, {pictureurl: appInfo.picture, displaytime: displayTime}).async()
			.sendMedia(itemInfo.url, itemInfo.mime_type)
			.alternativelyAddTextToSpeech(msgNotSupportedContent, userLang)
		;

	}
};

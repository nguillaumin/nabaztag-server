/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "Sibyl",
	name: "net.violet.js.sibyl"
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
		var settings = event.settings;
		var userLang = settings.lang || event.trigger.object.owner.lang;

		// do the random
		var rnd = Math.round(Math.random() * 100) % settings.messages.length;
		var message = settings.messages[rnd];
		// fill the message with the template replacement variables ${}
		var msg = message.text.replaceTemplateVariables(Event.getVariables(this, event));

		var voice = message.voice;
		var lang = message.lang;

		// get the application title and picture (for the bubble)
		var application = event.application;
		var appInfo  = violet.applications.getProfile(this, application.id);
		var appTitle = violet.site.getLocalizedString(this, {id: userLang, key:appInfo.title});

		// return a spoken message according to the result of the post
		return violet.objects.createMessage(objectId)
			.showBubble(appTitle, msg, {pictureurl: appInfo.picture, displaytime: 5000}).async()
			.addTextToSpeech(msg, lang, voice)
		;
	}
};

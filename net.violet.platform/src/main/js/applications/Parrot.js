/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "Parrot",
	name: "net.violet.js.parrot"
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

		// creates a greeting message : apply the template replacement variables
		var msg = settings.message.replaceTemplateVariables(Event.getVariables(this, event));

		// get the application title and picture (for the bubble)
		var application = event.application;
		var appInfo  = violet.applications.getProfile(this, application.id);
		var appTitle = violet.site.getLocalizedString(this, {id: userLang, key:appInfo.title});

		// return a spoken message according to the result of the post
		return violet.objects.createMessage(objectId)
			.showBubble(appTitle, msg, {pictureurl: appInfo.picture, displaytime: 5000}).async()
			.addTextToSpeech(msg, userLang, settings.voice)
		;
	}
};

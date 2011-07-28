/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "CallUrl",
	name: "net.violet.js.callurl"
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

		// replacement vars in the url
		var urlCalled = settings.url.replaceTemplateVariables(Event.getVariables(this, event), encodeURIComponent);

		// ping the url
		var resp = violet.net.httpRequest(urlCalled);

		// get the application title and picture (for the bubble)
		var application = event.application || event.trigger.application; // a modification in the event object hierarchy
		var appInfo  = violet.applications.getProfile(this, application.id);
		var appTitle = violet.site.getLocalizedString(this, {id: event.trigger.object.owner.lang, key:appInfo.title});

		// return a message according to the result of the post
		var msg = urlCalled + "\nHTTP response status : " + resp.status
						+ ((resp.error) ? "!\n(" + resp.error + ")" : ".");

		return violet.objects.createMessage(objectId)
			.playLibSound("CallUrl").async()
			.showBubble(appTitle, msg, {pictureurl: appInfo.picture, displaytime: 5000})
		;
	}
};

/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "FacebookStatus",
	name: "net.violet.js.facebookstatus"
};

/**
 * Event handlers
 */
appEventHandlers = {

	UPDATE_STATUS_URL : "http://social.carracoo.net/nabaztag/setStatus.php",

	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		// read the initial context (settings of the application)
		var settings        = event.settings;
		var lang            = event.trigger.object.owner.lang;
		var myStatuses      = settings.status.split('\n');
		var newRandomStatus = myStatuses[Math.floor(Math.random() * myStatuses.length)];

		// create the new status message with the replacement vars $
		var msg = newRandomStatus.replaceTemplateVariables(Event.getVariables(this, event));

		// send the request to facebook web service to update the status
		var params = {
			vid: event.trigger.object.owner.id,
			status: newRandomStatus
		};
		var options = {
			method: "GET",
			headers: {
				accept: "application/json"
			}
		};
		var resp = violet.net.httpRequest(this.UPDATE_STATUS_URL, params, options);
		debug("Facebook update status web service response :" + resp);

		// get the application title and picture (for the bubble)
		var application = event.application; // a modification in the event object hierarchy
		var appInfo   = violet.applications.getProfile(this, application.id);
		var userInfo  = violet.people.getProfile(this, event.trigger.object.owner.id);
		var appTitle  = violet.site.getLocalizedString(this, {id: lang, key:appInfo.title});

		return violet.objects.createMessage(objectId)
			.playLibSound("FacebookStatus").async()
			.showBubble(appTitle, msg, {pictureurl: appInfo.picture, displaytime: 5000}).async()
			.alternativelyAddTextToSpeech(msg, lang)
		;
	}
};

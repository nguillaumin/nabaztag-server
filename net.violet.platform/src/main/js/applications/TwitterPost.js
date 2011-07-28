/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "TwitterPost",
  apiVersion: "0.6",
	name: "net.violet.js.twitterpost"
};

/**
 * Event handlers
 */
appEventHandlers = {

	sentMessages: {
		fr: ". Twitt envoyé !",
		en: ". Twitt sent !",
		de: ". Twitt gesendet !",
		it: ". Twitt inviato !",
		es: ". Tu Twitt fue enviado !",
		pt: ". Twitt twitt !"
	},

	errorMessages: {
		fr: "Une erreur ${status} a eu lieu lors du poste sur Twitter : ${error}",
		en: "a ${status} error occured when posting to twitter : ${error}",
		de: "a ${status} error occured when posting to twitter : ${error}",
		it: "a ${status} error occured when posting to twitter : ${error}",
		es: "a ${status} error occured when posting to twitter : ${error}",
		pt: "a ${status} error occured when posting to twitter : ${error}"
	},

	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		// read the initial context (settings of the application)
		var settings = event.settings;
		var userLang = event.trigger.object.owner.lang;
		if (!this.sentMessages[userLang]) userLang = "en";

		// creates a greeting message : apply the template replacement variables
		var msg = (settings.message) ? settings.message.replaceTemplateVariables(Event.getVariables(this, event)) : "";

		// post the presence message to twitter account
		var twitterUrl = "http://twitter.com/statuses/update.json";
		var options = {
			method: "POST", user: settings.login, pwd: settings.pwd,
			headers: {accept: "application/json"}
		};
		var resp = violet.net.httpRequest(twitterUrl, {status: msg}, options);

		// return a spoken message according to the result of the post
		var msg = (resp.status == 200)
						? (msg + this.sentMessages[userLang]) // ok
						: (this.errorMessages[userLang].replaceTemplateVariables(resp)); // error

		// get the application title and picture (for the bubble)
		var application = event.application || event.trigger.application; // a modification in the event object hierarchy
		var appInfo  = violet.applications.getProfile(this, application.id);
		var appTitle = violet.site.getLocalizedString(this, {id: userLang, key:appInfo.title});

		return violet.objects.createMessage(objectId)
			.playLibSound("TwitterPost").async()
			.showBubble(appTitle, msg, {pictureurl: appInfo.picture, displaytime: 5000}).async()
			.alternativelyAddTextToSpeech(msg, userLang)
		;
	}
};

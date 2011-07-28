/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "RollingDice",
	name: "net.violet.js.rollingdice"
};

/**
 * Event handlers
 */
appEventHandlers = {


	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		var settings = new Settings(event);
		var lang   = settings.getString("lang", event.trigger.object.owner.lang);
		var facets = settings.getNum("facets", 6);
		var dices  = settings.getNum("count", 3);

		// throw the dices and concatenate the result
		var strResultMsg = "";
		for (var i = 1; i <= dices; i++) {
			var rnd = Math.ceil(Math.random() * facets);
			strResultMsg += (rnd + ". ");
		}

		// get the application title and picture (for the bubble)
		var application = event.application || event.trigger.application; // a modification in the event object hierarchy
		var appInfo  = violet.applications.getProfile(this, application.id);
		var appTitle = violet.site.getLocalizedString(this, {id: lang, key:appInfo.title});

		// return a spoken message according to the result of the post
		return violet.objects.createMessage(objectId)
			.playLibSound("RollingDice") // we should have a specific jingle here
			.addTextToSpeech(strResultMsg, lang, settings.voice).async()
			.showBubble(appTitle, strResultMsg, {pictureurl: appInfo.picture, displaytime: 2000 + 1000*dices})
		;

	}
};

/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "LaunchBrowser",
	name: "net.violet.js.launchbrowser"
};

/**
 * Event handlers
 */
appEventHandlers = {

	notSupported: {
		"fr": "Je ne peux pas jouer cette application. Montrez votre objet à un mirror !",
		"en": "I can't execute this application... Show your Object to a Mirror!",
		"de": "Ich kann diese Applikation nicht starten. Zeigen Sie Ihr Objekt einem Mirror!",
		"it": "Non posso far funzionare quest'applicazione . Mostra il tuo Oggetto al Mirror",
		"es": "No puedo activar esta aplicación. Muestra tu objeto a un mirror!",
		"ja": "このアプリケーションを実行できません。そのアイテムをミラーにかざしてください！"
	},

	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		// read the initial context (settings of the application)
		var settings = event.settings;
		var userLang = event.trigger.object.owner.lang;
		if (!this.notSupported[userLang]) userLang = "en";
		var notSupported = this.notSupported[userLang];

		// get the application title and picture (for the bubble)
		var application = event.application || event.trigger.application; // a modification in the event object hierarchy
		var appInfo  = violet.applications.getProfile(this, application.id);
		var appTitle = violet.site.getLocalizedString(this, {id: userLang, key:appInfo.title});

		// add the replacement vars ${} in the url
		var finalUrl = settings.url.replaceTemplateVariables(Event.getVariables(this, event), encodeURIComponent);

		// open a browser to the desired url
		return violet.objects.createMessage(objectId)
			.playLibSound("LaunchBrowser").async()
			.showBubble(appTitle, settings.url, {pictureurl: appInfo.picture, displaytime: 3000}).async()
			.browsePage(finalUrl)
			.alternativelyAddTextToSpeech(notSupported, userLang)
			//.alternativelyAddTextToSpeech("LOC_/net.violet.js.applications/dico/unsupportedDevice", lang)
		;
	}
};

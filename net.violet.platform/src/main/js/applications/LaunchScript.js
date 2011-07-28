/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "LaunchScript",
  apiVersion: "0.7",
	name: "net.violet.js.launchscript"
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

		var settings = new Settings(event);
		var fileName = settings.getString("script", "");
		var type = settings.getString("type", "");

		var lang = event.trigger.object.owner.lang;
		if (!this.notSupported[lang]) lang = "en";
		var notSupported = this.notSupported[lang];

		// get the application title and picture (for the bubble)
		var application = event.application || event.trigger.application; // a modification in the event object hierarchy
		var appInfo  = violet.applications.getProfile(this, application.id);
		var appTitle = violet.site.getLocalizedString(this, {id: lang, key:appInfo.title});

		// return a spoken message according to the result of the post
		return violet.objects.createMessage(objectId) // .deliverAt(event.trigger.deliveryDate)
			.showBubble(appTitle, fileName, {pictureurl: appInfo.picture, displaytime: 3000}).async()
			.playLibSound("FileLauncher") // we should have a specific jingle here
			.launchScript(fileName, type)
			.alternativelyAddTextToSpeech(notSupported, lang)
			//.alternativelyAddTextToSpeech("LOC_/net.violet.js.applications/dico/unsupportedDevice", lang)
		;
	}
};

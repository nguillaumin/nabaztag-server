/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "FileLauncher",
	name: "net.violet.js.filelauncher"
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

		var lang = event.trigger.object.owner.lang;
		if (!this.notSupported[lang]) lang = "en";
		var notSupportedMsg = this.notSupported[lang];

		// get the application title and picture (for the bubble)
		var application = event.application;
		var appInfo  = violet.applications.getProfile(this, application.id);
		var appTitle = violet.site.getLocalizedString(this, {id: lang, key:appInfo.title});

		// read the file path and extract the filemane
		var settings = new Settings(event.settings);
		var filePath = settings.getString("path", true).replace("file://", "");
		if (!filePath) throw "Empty filename !";
		var fileName = filePath.match(/([^\/\\]+\.?\w+)$/)[1];
		// return a spoken message according to the result of the post
		return violet.objects.createMessage(objectId)
			.playLibSound("FileLauncher").async()
			.showBubble(appTitle, "Trying to launch " + fileName, {pictureurl: appInfo.picture, displaytime: 3000}).async()
			.launchFile(filePath)
			.alternativelyAddTextToSpeech(notSupportedMsg, lang)
			//.alternativelyAddTextToSpeech("LOC_/net.violet.js.applications/dico/unsupportedDevice", lang)
		;
	}
};

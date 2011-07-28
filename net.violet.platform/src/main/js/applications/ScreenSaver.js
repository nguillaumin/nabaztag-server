/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "ScreenSaver",
	name: "net.violet.js.screensaver"
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
		var msg = this.notSupported[lang];

		// return a spoken message according to the result of the post
		return violet.objects.createMessage(objectId)
			.playLibSound("ScreenSaver")
			.launchScreenSaver()
			.alternativelyAddTextToSpeech(msg, lang)
			//.alternativelyAddTextToSpeech("LOC_/net.violet.js.applications/dico/unsupportedDevice", lang)
		;
	}
};

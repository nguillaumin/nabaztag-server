/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "LaunchYouTube",
	name: "net.violet.js.launchyoutube"
};

/**
 * Event handlers
 */
appEventHandlers = {

	// uncomment the good server definition depending of the execution environment
	SERVER: "api.violet.net",  // PROD
	//SERVER: "192.168.1.11", // DEV
	//SERVER: "object-pp.nabaztag.com", // PRE-PROD

	URL_SCHEME: "http://${server}/OS/ztampApp?application=youtube&id=${videoId}&background=${background}",

	notSupported: {
		"fr": "Je ne peux pas jouer cette application. Montrez votre objet à un mirror !",
		"en": "I can't execute this application... Show your Object to a Mirror!",
		"de": "Ich kann diese Applikation nicht starten. Zeigen Sie Ihr Objekt einem Mirror!",
		"it": "Non posso far funzionare quest'applicazione . Mostra il tuo Oggetto al Mirror",
		"es": "No puedo activar esta aplicación. Muestra tu objeto a un mirror!",
		"ja": "このアプリケーションを実行できません。そのアイテムをミラーにかざしてください！"
	},

	bubbleMsg: {
		"fr": "Lancement de votre vidéo en cours...",
		"en": "Launching your video...",
		"de": "",
		"it": "",
		"es": "",
		"ja": ""
	},

	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {
		// read the initial context (settings of the application)
		var settings = event.settings;
		var lang = event.trigger.object.owner.lang;
		if (!this.bubbleMsg[lang]) lang = "en";

		// get the application title and picture (for the bubble)
		var application = event.application || event.trigger.application; // a modification in the event object hierarchy
		var appInfo  = violet.applications.getProfile(this, application.id);
		var appTitle = violet.site.getLocalizedString(this, {id: lang, key:appInfo.title});

		// get the Violet URL to the mebedded video
		var videoId  = settings.url.replace("http://www.youtube.com/watch?v=","");
		var background = settings.background || "White";
		var finalUrl = this.URL_SCHEME.replaceTemplateVariables({server: this.SERVER, videoId: videoId, background: background});

		// open a browser to the desired url
		return violet.objects.createMessage(objectId)
			.playLibSound("LaunchYouTube").async()
			.showBubble(appTitle, this.bubbleMsg[lang], {pictureurl: appInfo.picture, displaytime: 3000}).async()
			.browsePage(finalUrl)
			.alternativelyAddTextToSpeech(this.notSupported[lang], lang)
		;
	}
};

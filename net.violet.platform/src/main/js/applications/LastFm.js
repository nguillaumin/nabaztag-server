/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "LastFm",
	name: "net.violet.js.lastfm"
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

	introMsg: {
		"fr": "Connexion à votre radio Last.fm en cours...",
		"en": "Connexion to your Last.fm radio in progress..."
	},

	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		// read the initial context (settings of the application)
		var settings = event.settings;
		var lang = event.trigger.object.owner.lang;
		if (!this.introMsg[lang]) lang = "en";
		var notSupported = this.notSupported[lang];

		// get the application title and picture (for the bubble)
		var application = event.application || event.trigger.application; // a modification in the event object hierarchy
		var appInfo  = violet.applications.getProfile(this, application.id);
		var appTitle = violet.site.getLocalizedString(this, {id: lang, key:appInfo.title});

		var username = settings.username;
		var url = "http://www.last.fm" + ((username) ? "/listen/user/" + username + "/recommended": "");
		var introMsg = this.introMsg[lang]; //.replaceTemplateVariables({username: username});

		// open a browser to the desired url
		return violet.objects.createMessage(objectId)
			.playLibSound("LastFm").async()
			.showBubble(appTitle, introMsg, {pictureurl: appInfo.picture, displaytime: 3000}).async()
			.browsePage(url)
			.alternativelyAddTextToSpeech(notSupported, lang)
		;
	}
};

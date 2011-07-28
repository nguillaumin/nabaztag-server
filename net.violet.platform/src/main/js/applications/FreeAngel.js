/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "FreeAngel",
	name: "net.violet.js.freeangel"
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

	DEFAULT_PROFILE: null, // "enfant", "ado", "adulte"

	onSuccess: {
		"enfant": "Le mode enfant est maintenant activé.",
		"ado": "Le mode adolescent est maintenant activé.",
		"adulte": "Le contrôle parental est maintenant désactivé.",
		"noweb": "Le contrôle parental est maintenant activé."
	},
	onFailure: "Ooops, il y a eu une erreur. Aucune modification du contrôle parental n’a été prise en compte.",

	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		var lang = event.trigger.object.owner.lang;
		if (!this.notSupported[lang]) lang = "fr";
		var notSupportedMsg = this.notSupported[lang];

		// get the application title and picture (for the bubble)
		var application = event.application;
		var appInfo  = violet.applications.getProfile(this, application.id);
		var appTitle = violet.site.getLocalizedString(this, {id: lang, key:appInfo.title});


		// determine the command option to be run depenting on event and settings
		var settings = new Settings(event.settings);
		var commandOption = (event.trigger.name == 'ZtampDetection')
				? (this.DEFAULT_PROFILE || settings.getString("profile", true)) // activate the browsing mode set
				: "noweb";	// turn off browsing
		var commandLine  = "FreeA -" + commandOption;

		// activate the service
		return violet.objects.createMessage(objectId)
			.playLibSound("FreeAngel").async()
			.showBubble(appTitle, this.onSuccess[commandOption], {pictureurl: appInfo.picture, displaytime: 3000}).async()
			.launchApplication(commandLine, {on_success: this.onSuccess[commandOption], on_failure: this.onFailure})
			.alternativelyAddTextToSpeech(notSupportedMsg, lang) // This application can only be launched from a mirror !
		;

	}
};

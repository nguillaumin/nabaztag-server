/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "ColoriGOMixte",
	name: "net.violet.js.colorigomixte"
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

	DEFAULT_PROFILE: "http://www.colorigo.com/coloriages/mixte/index.php", // "http://www.colorigo.com/coloriages/garcon/index.php", "http://www.colorigo.com/coloriages/fille/index.php",

	onSuccess: "ColoriGO démarre dans quelques secondes.",
	onFailure: "Ooops, il y a eu une erreur.",

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
		var commandLine  = "Colorigo " + this.DEFAULT_PROFILE;
		debug("launching " + commandLine);

		// activate the service
		return violet.objects.createMessage(objectId)
			.playLibSound("ColoriGOMixte").async()
			.showBubble(appTitle, this.onSuccess, {pictureurl: appInfo.picture, displaytime: 3000}).async()
			.launchApplication(commandLine, {on_success: this.onSuccess, on_failure: this.onFailure})
			.alternativelyAddTextToSpeech(notSupportedMsg, lang) // This application can only be launched from a mirror !
		;

	}
};

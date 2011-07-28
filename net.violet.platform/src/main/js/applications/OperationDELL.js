/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "OperationDELL",
	name: "net.violet.js.operationDELL"
};

/**
 * Event handlers
 */
appEventHandlers = {

	appPath: "eLeadLauncher.bat",
	urlDELL: "http://www.dell.com/virtualization",

	notSupported: {
		"fr": "Pour démarrer l'application, montrez votre stampe à un mirror !",
		"en": "I can't execute this application... Show your Object to a Mirror!",
		"de": "Ich kann diese Applikation nicht starten. Zeigen Sie Ihr Objekt einem Mirror!",
		"it": "Non posso far funzionare quest'applicazione . Mostra il tuo Oggetto al Mirror",
		"es": "No puedo activar esta aplicación. Muestra tu objeto a un mirror!"
	},


	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		// read the initial context (settings of the application)
		var settings = event.settings;
		var lang = event.trigger.object.owner.lang;
		var msg  = (this.notSupported[lang]) ? this.notSupported[lang] : this.notSupported["fr"];

		var now = new Date();
		var endOfEvent = DateHelper.parseISO("2009-05-29T20:00:00+01:00");

		if (now > endOfEvent) {
			return [];

		} else {
			// open a browser to the desired url
			return violet.objects.createMessage(objectId)
				.playLibSound("LaunchBrowser").async()
				.launchScript(this.appPath, "windows")
				.alternativelyAddTextToSpeech(this.notSupported["fr"], "fr", "FR-Anastasie")
			;
		}
	}
};

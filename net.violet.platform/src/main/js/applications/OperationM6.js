/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "OperationM6",
  apiVersion: "0.6",
	name: "net.violet.js.operationm6"
};

/**
 * Event handlers
 */
appEventHandlers = {

	siteUrl: "http://www.m6.fr/html/carte_voeux_2009/index_m6web.php",

	notSupported: {
		"fr": "Je ne peux pas jouer cette application. Montrez votre objet à un mirror !",
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

		// open a browser to the desired url
		return violet.objects.createMessage(objectId)
			//.playLibSound("LaunchBrowser")
			.browsePage(this.siteUrl)
			.alternativelyAddTextToSpeech(msg, lang)
			//.alternativelyAddTextToSpeech("LOC_/net.violet.js.applications/dico/unsupportedDevice", lang)
		;
	}
};

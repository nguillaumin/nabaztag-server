/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "YoureNotAlone",
	name: "net.violet.js.yourenotalone"
};

/**
 * Event handlers
 */
appEventHandlers = {

	// uncomment the good server definition depending of the execution environment
	//SERVER: "api.violet.net",  // PROD
	SERVER: "192.168.1.11", // DEV
	//SERVER: "object-pp.violet.net", // PRE-PROD

	STATIC_URL: "http://${server}/OS/ztampApp?application=counter",
	WEB_SERVICE_URL: "http://${server}/OS/ztampApp?application=counter&event=${event.name}&ztampserial=${ztamp.serial}&reader=${reader.serial}",

	notSupported: {
		"fr": "Je ne peux pas jouer cette application. Montrez votre objet à un mirror !",
		"en": "I can't execute this application... Show your Object to a Mirror!",
		"de": "Ich kann diese Applikation nicht starten. Zeigen Sie Ihr Objekt einem Mirror!",
		"it": "Non posso far funzionare quest'applicazione . Mostra il tuo Oggetto al Mirror",
		"es": "No puedo activar esta aplicación. Muestra tu objeto a un mirror!",
		"ja": "このアプリケーションを実行できません。そのアイテムをミラーにかざしてください！"
	},

	bubbleMsg: {
		"fr": "Il y a actuellement ${count} Objets connectés à l'Internet des Objets !",
		"en": "There are currently ${count} Objects connected to the Internet of Things!",
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
		var lang = event.trigger.object.owner.lang;
		if (!this.bubbleMsg[lang]) lang = "en";
		var notSupported = this.notSupported[lang];

		// replacement vars in the url
		var reader = (event.trigger.reader) || (event.trigger.target);
		var replacementVals = {
			server: this.SERVER,
			event: event.trigger,
			ztamp: event.trigger.object,
			reader: reader
		};
		// prepare the URLs
		var pingUrl   = this.WEB_SERVICE_URL.replaceTemplateVariables(replacementVals);
		var staticUrl = this.STATIC_URL.replaceTemplateVariables(replacementVals);

		// ping the url to know how many objects are connected
		debug(pingUrl);
		var resp = violet.net.httpRequest(pingUrl);
		debug("response received : " + resp);

		var bubbleMsg = this.bubbleMsg[lang].replaceTemplateVariables({count: resp.body});

		// get the application title and picture (for the bubble)
		var application = event.application;
		var appInfo  = violet.applications.getProfile(this, application.id);
		var appTitle = violet.site.getLocalizedString(this, {id: lang, key:appInfo.title});

		// return a spoken message according to the result of the post
		return violet.objects.createMessage(objectId)
			.showBubble(appTitle, bubbleMsg, {pictureurl: appInfo.picture, url: staticUrl, displaytime: 5000}).async()
			.addTextToSpeech(bubbleMsg, lang)
		;

	}
};

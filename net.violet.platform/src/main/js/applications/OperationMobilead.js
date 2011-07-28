/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "OperationMobilead",
	name: "net.violet.js.operationmobilead"
};

/**
 * Event handlers
 */
appEventHandlers = {

	ACTIVE_MOBILEAD_URL: "http://x2d.fr?d=${ztamp.serial}&r=${object.serial}&s=m",
  EXPIRED_MOBILEAD_URL: "http://www.mobilead.fr/",
  END_OF_EVENT: DateHelper.parseISO("2011-01-01T00:00:00+01:00"),

	notSupported: {
		"fr": "Cette application fonctionne uniquement avec le mir:ror !",
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
    if (!this.notSupported[lang]) lang = "fr";

		// define the replacement vars for the url
		var now = new Date();

		var replacementVals = {
			object: event.trigger.target,
			ztamp: event.trigger.object,
		};
		// on URL-encode les variables de remplacements !
		var finalUrl = 
      ((now < this.END_OF_EVENT) ? this.ACTIVE_MOBILEAD_URL : this.EXPIRED_MOBILEAD_URL)
            .replaceTemplateVariables(replacementVals, encodeURIComponent);

		// get the application title and picture (for the bubble)
		var application = event.application;
		var appInfo  = violet.applications.getProfile(this, application.id);
		var appTitle = violet.site.getLocalizedString(this, {id: lang, key:appInfo.title});

    // play jingle, show bubble, open web page..
		return violet.objects.createMessage(objectId)
			.playLibSound("LaunchBrowser").async()
      .showBubble(appTitle, finalUrl, {pictureurl: appInfo.picture, displaytime: 3000}).async()
			.browsePage(finalUrl)
			.alternativelyAddTextToSpeech(this.notSupported["fr"], "fr", "FR-Anastasie")
		;

	}
};

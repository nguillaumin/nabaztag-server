/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "JSBookReader",
  apiVersion: "0.7",
	name: "net.violet.js.bookreader"
};

/**
 * Event handlers
 */
appEventHandlers = {
	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		// read the initial context (settings of the application)
		var settings = event.settings;

		// read the book title, description and content
		return violet.objects.createMessage(objectId)
			//.addTextToSpeech(settings.title, settings.lang)
			//.addTextToSpeech(settings.description, settings.lang)
			.playAudioStream(settings.bookmp3)
		;
	}
};

/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "Discovery",
  apiVersion: "0.4",
	name: "net.violet.js.discovery"
};

/**
 * Event handlers
 */
appEventHandlers = {

	discoveryUrl: "http://www.dailymotion.com/video/x6vcsv_cetait-ca-lappartement-discovery-ch_creation",

	notSupported: {
		"fr": "Je suis désolé, cette application est prévue pour mirror !",
		"en": "I'm sorry. This application can only be run by Mirror !"
	},

	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		// read the initial context (settings of the application)
		var settings = event.settings;
		var lang = event.trigger.object.owner.lang;
		var msg  = (this.notSupported[lang]) ? this.notSupported[lang] : this.notSupported["en"];

		// return a spoken message according to the result of the post
		return violet.objects.createMessage(objectId)
			.browsePage(this.discoveryUrl)
			.alternativelyAddTextToSpeech(msg, lang)
			//.alternativelyAddTextToSpeech("LOC_/net.violet.js.applications/dico/unsupportedDevice", lang)
		;
	}
};

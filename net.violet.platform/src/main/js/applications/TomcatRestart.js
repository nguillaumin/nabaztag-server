/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "TomcatRestart",
  apiVersion: "0.4",
	name: "net.violet.js.tomcatrestart"
};

/**
 * Event handlers
 */
appEventHandlers = {
	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		// read initial context (settings of the application)
		var ztamp = event.trigger.object;

		// read the book title, description and content
		return violet.objects.createMessage(objectId)
			.playLibSound("SiffletSirene")
			.addTextToSpeech(ztamp.owner.firstname + " redémarre Tomcat !", "fr")
			.playLibSound("CoussinPeteur")
		;
	}
};

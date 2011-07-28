/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "LEDitBE",
	name: "net.violet.js.leditbe"
};

/**
 * Event handlers
 */
appEventHandlers = {

	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		// make an RGB random
		var r = Math.floor(Math.random()*255);
		var g = Math.floor(Math.random()*255);
		var b = Math.floor(Math.random()*255);

		return violet.objects.createMessage(objectId)
			.playModality("org.experimental.led", {r: r, g: g, b: b})
		;
	}
};

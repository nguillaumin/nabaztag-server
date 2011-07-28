/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "SlideShow",
	name: "net.violet.js.slideshow"
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

	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		var urlList = [
			"http://farm4.static.flickr.com/3156/2884339833_48ed8a692e_o.jpg",
			"http://farm2.static.flickr.com/1081/527678562_01e38ca700_o.jpg",
			"http://farm1.static.flickr.com/91/244892203_e596e61219_o.jpg",
			"http://farm1.static.flickr.com/133/351841516_e139db2b5a_o.jpg",
			"http://farm2.static.flickr.com/1176/1044412374_8771634e68_o.jpg"
		];

		var lang = event.trigger.object.owner.lang;
		if (!this.notSupported[lang]) lang = "en";
		var msg = this.notSupported[lang];

		// return a spoken message according to the result of the post
		return violet.objects.createMessage(objectId)
			.playLibSound("PlaySlideShow").async()
			.playSlideShow(urlList)
			.alternativelyAddTextToSpeech(msg, lang)
			//.alternativelyAddTextToSpeech("LOC_/net.violet.js.applications/dico/unsupportedDevice", lang)
		;
	}
};

/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "NabaztagDemo",
	name: "net.violet.js.nabaztagdemo"
};

/**
 * Event handlers
 */
appEventHandlers = {

	mediaContents: {
			fr: [
				"broadcast/broad/config/nabaztagdemo/fr_01.mp3",
				"broadcast/broad/config/nabaztagdemo/fr_02.mp3",
				"broadcast/broad/config/nabaztagdemo/fr_03.mp3",
				"broadcast/broad/config/nabaztagdemo/fr_04.mp3"
			],
			en: [
				"broadcast/broad/config/nabaztagdemo/en_01.mp3",
				"broadcast/broad/config/nabaztagdemo/en_02.mp3",
				"broadcast/broad/config/nabaztagdemo/en_03.mp3",
				"broadcast/broad/config/nabaztagdemo/en_04.mp3"
			]
	},
	notSupported: {
		"fr": "Cette application ne peut être jouée que par Nabaztagg",
		"en": "This application can only be played on Nabaztagg"
	},

	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		// read the initial context (settings of the application)
		var settings = event.settings;
		var lang = settings.lang || event.trigger.object.owner.lang;
		if (!this.mediaContents[lang]) lang = "en";

		// do the random
		var rnd = Math.round(Math.random() * 100) % this.mediaContents[lang].length;
		var media = this.mediaContents[lang][rnd];

		// get the application title and picture (for the bubble)
		var application = event.application;
		var appInfo  = violet.applications.getProfile(this, application.id);
		var appTitle = violet.site.getLocalizedString(this, {id: lang, key:appInfo.title});

		if (event.trigger.target.hw_type.startsWith("violet.nabaztag")) {
			// play the demo
			return violet.objects.createMessage(objectId)
				.playLibSound("NabaztagDemo").async()
				.showBubble(appTitle, appTitle + "...", {pictureurl: appInfo.picture, displaytime: 5000}).async()
				.playAudioStream(media, {streaming: true, withEar: true})
			;
		} else {
			// warn that it can not be done
			return violet.objects.createMessage(objectId)
				.playLibSound("NabaztagDemo").async()
				.showBubble(appTitle, appTitle + "...", {pictureurl: appInfo.picture, displaytime: 2000}).async()
				.addTextToSpeech(this.notSupported[lang], lang)
			;
		}
	}
};

/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "AskTheGuru",
	name: "net.violet.js.asktheguru"
};

/**
 * Event handlers
 */
appEventHandlers = {

	mediaRoot: "broadcast/broad/config/asktheguru",

	mediaCountFor: { // just tell us how many files we have in each lang
		fr: 184,
		en: 184,
		us: 184,
		es: 184,
		de: 182
	},
	bubbleText: {
		"fr": "Le Gourou réfléchit...",
		"en": "Guru meditation.."
	},

	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		// read the initial context (settings of the application)
		var settings  = event.settings;
		var mediaLang = settings.lang;
		var msgLang   = (this.bubbleText[mediaLang] ? mediaLang : "en");

		// do the random and add 1000 (just to have the 000 padding)
		var rnd = "" + (1000 + Math.round(Math.random() * this.mediaCountFor[mediaLang]));
		// each file is classifiedd inside its lang folder and is named 001.mp3, 002.mp3...
		var media = this.mediaRoot + "/" + mediaLang + "/" + rnd.substring(1) + ".mp3";

		// get the application title and picture (for the bubble)
		var application = event.application;
		var appInfo  = violet.applications.getProfile(this, application.id);
		var appTitle = violet.site.getLocalizedString(this, {id: msgLang, key:appInfo.title});

		// return a spoken message according to the result of the post
		return violet.objects.createMessage(objectId)
			//.playLibSound("AskTheGuru")
			.showBubble(appTitle, this.bubbleText[msgLang], {pictureurl: appInfo.picture, displaytime: 2000}).async()
			.playAudioStream(media, {streaming: true, withEar: true})
		;
	}
};

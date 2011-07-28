/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "iTunesPlay",
	name: "net.violet.js.itunesplay"
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

		var settings = event.settings;
		var action = "play";
		var resume = "Playing ";

		var playOptions = {};

		if(settings.action == "play_album"){
			playOptions.albumname  = settings.object_name;
			resume = "album " + settings.object_name + " ";
		}else if(settings.action == "play_artist"){
			playOptions.artistname  = settings.object_name;
			resume = "artist " + settings.object_name + " ";
		}else if(settings.action == "play_track"){
			playOptions.trackname  = settings.object_name;
			resume = "track " + settings.object_name + " ";
		}else if(settings.action == "play_playlist"){
			playOptions.playlist  = settings.object_name;
			resume = "playlist " + settings.object_name + " ";
		}

		if (settings.volume) playOptions.volume = settings.volume;
		if (settings.random) playOptions.random	= settings.random;
		if (settings.repeat) playOptions.repeat	= settings.repeat;


		var lang = event.trigger.object.owner.lang;
		if (!this.notSupported[lang]) lang = "en";

		// get the application installer title and picture
		var application = event.application || event.trigger.application; // a modification in the event object hierarchy
		var appInfo  = violet.applications.getProfile(this, application.id);
		var appTitle = violet.site.getLocalizedString(this, {id: lang, key:appInfo.title});

		// return a spoken message according to the result of the post
		return violet.objects.createMessage(objectId)
			/*.playLibSound("iTunesPlayer").async()*/
			.showBubble(appTitle, resume, {pictureurl: appInfo.picture, displaytime: 3000}).async()
			.tellITunes(action, playOptions)
			.alternativelyAddTextToSpeech(this.notSupported[lang], lang)
		;
	}
};

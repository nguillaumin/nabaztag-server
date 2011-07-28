/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "FlickrsFavorites",
	name: "net.violet.js.flickrsfavorites"
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

	unknownUser: {
		"fr": "Flickr n'a pas trouvé d'utilisateur nommé ${userName} !",
		"en": "Flickr doesn't know any user named ${userName}!",
		"de": "Flickr nicht kennt ${userName}!",
		"it": "",
		"es": "",
		"ja": ""
	},

	FLICKR_API: "http://api.flickr.com/services/rest/",
	API_KEY: "d0875f68d2b71f23827f253f3d608472",
	FAVORITES_FEED: "http://api.flickr.com/services/feeds/photos_faves.gne",

	_usersIds: {}, // store flickr users ids as they are retrieved


	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		var lang = event.trigger.object.owner.lang;
		if (!this.notSupported[lang]) lang = "en";
		var msg = this.notSupported[lang];

		// read the initial context (settings of the application)
		var settings = event.settings;

		var userName = settings.userName;
		var maxItems = settings.maxItems || 5;

		// HTTP GET on the Flickr API to get the user's ID
		var userId = this.getFlickrUserId(userName);

		if (userId == null) { // unknown user
			throw this.unknownUser[lang].replaceTemplateVariables({userName: userName});
		}

		// HTTP GET on the Flickr Feed for this user's favorites
		var feedContent = violet.net.httpRequest(
			this.FAVORITES_FEED, {id: userId, format: "rss2"}
		);

		var channel = feedContent.body.rss.channel, feedItems = channel.item;

		// display the title of the feed
		var msg = violet.objects.createMessage(objectId);
		msg.addTextToSpeech(channel.title, "en").async()
			.showBubble(channel.title, channel.description, {pictureurl: this.getAuthorsPicture(userName), displaytime: 3000})
		;

		// add the items
		if (feedItems) {
			var authorsPicture = {};
			for (var i = 0, len = Math.min(maxItems, feedItems.length), feedItem; (i < len) && (feedItem = feedItems[i]); i++) {
				var authorsName = feedItem["media:credit"]["_text"];
				if (!authorsPicture[authorsName]) {
					authorsPicture[authorsName] = this.getAuthorsPicture(authorsName);
				}
				if (feedItem.title) {
					msg.addTextToSpeech(feedItem.title, "en").async();
				}
				msg.showBubble(feedItem.title, feedItem.description,
					{url: feedItem.link, pictureurl: authorsPicture[authorsName], displaytime: 4000}
				);
			}
		}
		return msg;
	},

	/*
	 * Call Flickr API to retrieve user's id
	 */
	getFlickrUserId: function(inUserName) {

		var userId = this._usersIds[inUserName];
		if (userId !== undefined) return userId; // allready in the cache

		// HTTP GET on the Flickr API to get the user's ID
		var apiResponse = violet.net.httpRequest(
			this.FLICKR_API,
			{method: "flickr.people.findByUsername", username: inUserName,
			 api_key: this.API_KEY, format: "json", nojsoncallback: 1}
		);
		debug("flickr.people.findByUsername(" + inUserName + ") => " + apiResponse);
		// {"user":{"id":"80794892@N00", "nsid":"80794892@N00", "username":{"_content":"joshua black wilkins"}}, "stat":"ok"}

		if (apiResponse.body && apiResponse.body.user) {
			userId = this._usersIds[inUserName] = apiResponse.body.user.id; // use id or nsid ?
		} else {
			userId = this._usersIds[inUserName] = null;
		}

		return userId;
	},

	/*
	 * Call Flickr API to retrieve user's buddy icon
	 */
	getAuthorsPicture: function(inUserName) {

		var userId = this.getFlickrUserId(inUserName);

		if (userId == null) { // user not found
			return "http://www.flickr.com/images/buddyicon.jpg";
		}

		// HTTP GET
		var apiResponse = violet.net.httpRequest(
			this.FLICKR_API,
			{method: "flickr.people.getInfo", user_id: userId,
			 api_key: this.API_KEY, format: "json", nojsoncallback: 1}
		);
		debug("flickr.people.getInfo(" + userId + ") => " + apiResponse);

		if (apiResponse.body && apiResponse.body.person) {
			var userInfo = apiResponse.body.person;
			return "http://farm" + userInfo.iconfarm + ".static.flickr.com/" + userInfo.iconserver + "/buddyicons/" + userId + ".jpg";
		} else {
			return "http://www.flickr.com/images/buddyicon.jpg";
		}
	}
};

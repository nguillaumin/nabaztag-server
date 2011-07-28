/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "Mailbox",
	name: "net.violet.js.mailbox"
};

/**
 * Event handlers
 */
appEventHandlers = {

	oneMessage: {
		fr: "Vous avez ${1} message dans votre boîte aux lettres !",
		en: "You have ${1} message in your inbox !",
		de: "Sie haben ${1} Nachricht in Ihrer Mailbox!",
		es: "Tienes ${1} mensaje en tu buzón !",
		pt: "You have ${1} message in you inbox !",
		it: "Hai ricevuto ${1} nuovo messaggio",
		ja: "受信ボックスに、 ${1} 件の、メッセージがあります！"
	},

	someMessages: {
		fr: "Vous avez ${1} messages dans votre boîte aux lettres !",
		en: "You have ${1} messages in your inbox !",
		de: "Sie haben ${1} Nachrichten in Ihrer Mailbox!",
		es: "Tienes ${1} mensajes en tu buzón !",
		pt: "You have ${1} messages in you inbox !",
		it: "Hai ricevuto ${1} nuovi messaggi",
		ja: "受信ボックスに、 ${1} 件の、メッセージがあります！"
	},

	introMessage: {
		fr: "Un message de ${1}",
		en: "A message from ${1}",
		de: "Eine Nachricht von ${1}",
		es: "Un mensaje de ${1}",
		pt: "A message from ${1}",
		it: "Un messaggio da ${1}",
		ja: "${1} からの、メッセージです"
	},

	emptyBox: {
		fr: "Pas de nouveau message.",
		en: "No new message.",
		de: "Keine neue Nachricht.",
		es: "No hay ningún nuevo mensaje.",
		pt: "No new message.",
		it: "Nessun messaggio",
		ja: "新しいメッセージは、ありません"
	},

	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		// read the applications settings
		var settings = event.settings;
		var ztamp  = event.trigger.object;
		var userLang = ztamp.owner.lang;
		var userId = ztamp.owner.id;
		if (!this.oneMessage[userLang]) userLang="en"; // traduction not set !

		// ask the current list of message in the ztamp box
		var messages = violet.messages.get(this, {id: "inbox", object: ztamp.id, userId: userId});
		var msgCount = messages.length;

		// get the application title and picture (for the bubble)
		var application = event.application || event.trigger.application; // a modification in the event object hierarchy
		var appInfo  = violet.applications.getProfile(this, application.id);
		var appTitle = violet.site.getLocalizedString(this, {id: userLang, key:appInfo.title});

		if (msgCount == 0) {
			// show bubble but no tts on nabaztag
			return violet.objects.createMessage(objectId)
				.showBubble(appTitle, this.emptyBox[userLang], {pictureurl: appInfo.picture, displaytime: 2000}).async()
				// .alternativelyAddTextToSpeech(this.emptyBox[userLang], userLang) 
			;

		} else {
			var announce = ((msgCount > 1) ? this.someMessages[userLang]: this.oneMessage[userLang])
					.replaceTemplateVariables([msgCount]);

			// build a concatenation of an announce and all inbox messages
			var finalMessage = violet.objects.createMessage(objectId)
				.playLibSound("MailPost").async()
				.showBubble(appTitle, announce, {pictureurl: appInfo.picture, displaytime: 3000}).async()
				.addTextToSpeech(announce, userLang)
			;
			var msgIds = [];

			// gather all the messages sequences into one
			for (var i = msgCount; i--; ) { // do it in reverse order because the latest message should be played first
				var msg = messages[i], pojoContent = msg.content;
				if (pojoContent) {
					debug("Adding inbox message[" + i + "] : " + pojoContent);
					var senderId = pojoContent.from;
					var sender = violet.people.getProfile(this, senderId);
					finalMessage
						.addTextToSpeech(this.introMessage[userLang].replaceTemplateVariables([sender.first_name]), userLang)
						.addSequence(pojoContent.sequence)
					;
				}

				msgIds.push(msg.id);
			}

			// mark messages as read
			violet.messages.markAsRead(this, {id: objectId, messageIds: msgIds, userId: userId, archive: true});

			return finalMessage;
		}
	}
};

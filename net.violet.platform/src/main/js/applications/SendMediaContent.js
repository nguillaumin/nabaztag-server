/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "SendMediaContent",
	name: "net.violet.js.sendmediacontent"
};

/**
 * Event handlers
 */
appEventHandlers = {

	dedicatedIntros: {
		fr: "Cher ${user.firstname}, ",
		en: "Dear ${user.firstname}, ",
		de: "Lieber ${user.firstname}, ",
		es: "Estimado ${user.firstname}, ",
		pt: "Dear ${user.firstname}, ",
		it: "Caro ${user.firstname}, ",
		ja: "${user.firstname} さん, "
	},

	confirmationMessages: {
		fr: "votre contenu multimédia a été envoyé à ${dest.label} !",
		en: "your media file has been sent to ${dest.label}!",
		de: "Ihre Nachricht an ${dest.label} wurde gesendet!",
		es: "a su mensaje ha sido enviado ${dest.label}!",
		pt: "o seu conteúdo multimédia foi enviado a ${dest.label}!", // / ficheiro / mensagem
		it: "il tuo contenuto multimedia è stato spedito à ${dest.label}!", // / file / messagio
		ja: "ファイルが ${dest.label} に送信されました！"
	},

	notSupportedContent: "LOC_net.violet.application.messages.unsupported_content",

	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		// read the initial context (settings of the application)
		var settings = event.settings;
		var userLang = event.trigger.object.owner.lang;
		if (!this.confirmationMessages[userLang]) userLang="en";

		var msgNotSupportedContent = violet.site.getLocalizedString(this, {id: userLang, key: this.notSupportedContent});

		var sender = event.trigger.object.owner;

		// read destine's name
		var destId = settings.objectDestID;
		var dest   = violet.objects.getProfile(this, {id: destId, userId: sender.id});
		debug("received info on dest object : " + dest);

		// read file to send from settings
		var fileId = settings.mediaFileID;
		if (fileId == null) {
			return violet.objects.createMessage(objectId)
				.playLibSound("SendMediaContent")
				.addTextToSpeech("Error : no file to send. Check your parameters.", "en")
			;
		}

		// get the file from the library of the sender
		var file = violet.libraries.get(this, {id: fileId, userId: sender.id});
		if (file && file.length) file = file[0]; // API call return an array
		debug("received info on file to send : " + file);

		// return two messages : for the target object and for the dest
		var msgToDest = violet.objects.createMessage(destId)
			.playLibSound("SendMediaContent")
			.sendMedia(file.url, file.mime_type)
			.alternativelyAddTextToSpeech(msgNotSupportedContent, userLang)
			.serialize()
		;
		msgToDest.from  = sender.id;
		msgToDest.title = "Media delivery";

		// send the media file via the API
		debug("send message : " + msgToDest);
		violet.messages.sendMessage(this, {message: msgToDest, mailboxmessage: true});

		// confirm the sending was made
		var confirmationMsg =
			((sender.firstname && !sender.firstname.isEmpty())
				? this.dedicatedIntros[userLang].replaceTemplateVariables({user: sender}) // add 'dear xx'
				: "")
			+ this.confirmationMessages[userLang].replaceTemplateVariables({dest: dest})
		;
		var msgToReader = violet.objects.createMessage(objectId)
			.playLibSound("SendMediaContent")
			.addTextToSpeech(confirmationMsg, userLang)
		;

		return msgToReader;

	}
};

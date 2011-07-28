/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "MailPost",
	name: "net.violet.js.mailpost"
};

/**
 * Event handlers
 */
appEventHandlers = {

	//chorUrl: "broadcast/broad/001/075/772/647/5617931.chor",

	confirmationMessages: {
		fr: "Cher ${ztamp.owner.firstname}, votre message à ${dest} a été envoyé !",
		en: "Dear ${ztamp.owner.firstname}, your message to ${dest} has been sent !",
		de: "Sehr geehrter Herr ${ztamp.owner.firstname}, Ihre Nachricht an ${dest} wurde gesendet!",
		es: "Estimado ${ztamp.owner.firstname}, a su mensaje ha sido enviado ${dest}!",
		pt: "Caro ${ztamp.owner.firstname}, sua mensagem foi enviada para ${dest}!",
		it: "Caro ${ztamp.owner.firstname}, il tuo messaggio è stato inviato ${dest}!",
		ja: "親愛なる ${ztamp.owner.firstname} さん、${dest} へメッセージを送信しました！"
	},

	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		// read the applications settings
		var settings = new Settings(event.settings);

		// creates the body message : apply the template replacement variables
		var lang = event.trigger.object.owner.lang;
		if (!this.confirmationMessages[lang]) lang="en";

		// template variables for the message body and confirmation
		var replacementVals = Event.getVariables(this, event);
		replacementVals.dest = settings.emailDest;
		var subject = settings.getString("subject", {defaultValue:""}).replaceTemplateVariables(replacementVals);
		var message = settings.getString("message", {defaultValue:""}).replaceTemplateVariables(replacementVals);
		// emails addresses are comma separated
		var dest = (settings.emailDest) ? settings.emailDest.split(",") : [];
		var cc   = (settings.emailCC) ? settings.emailCC.split(",") : [];

		// send the mail
		var ok = violet.net.sendMail(this, objectId, event, dest, cc, subject, message);

		// get the application title and picture (for the bubble)
		var application = event.application || event.trigger.application; // a modification in the event object hierarchy
		var appInfo  = violet.applications.getProfile(this, application.id);
		var appTitle = violet.site.getLocalizedString(this, {id: lang, key:appInfo.title});

		// return a spoken message according to the result of the post
		if (ok) {
			var confirmationMsg = this.confirmationMessages[lang].replaceTemplateVariables(replacementVals);
			return violet.objects.createMessage(objectId)
				.addPalette("violet")
				.playLibSound("MailPost").async()
				.showBubble(appTitle, confirmationMsg, {pictureurl: appInfo.picture, displaytime: 5000}).async()
				.alternativelyAddTextToSpeech(confirmationMsg, lang)
			;
		} else {
			return violet.objects.createMessage(objectId)
				.playLibSound("MailPost").async()
				.showBubble(appTitle, "An error occured when posting your email ! Try again !",
									  {pictureurl: appInfo.picture, displaytime: 10000}).async()
				.alternativelyAddTextToSpeech("An error occured when posting your email ! Try again !", "en")
			;
		}
	}
};

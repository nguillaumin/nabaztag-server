/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "ApplicationInstaller",
	name: "net.violet.js.applicationinstaller"
};

/**
 * Event handlers
 */
appEventHandlers = {

	// appInstallUrl: "http://my.violet.net/people/${user.id}/applications?co=${ztamp.id}",

	dedicatedIntros: {
		fr: "Cher ${user.firstname}, ",
		en: "Dear ${user.firstname}, ",
		de: "Lieber ${user.firstname}, ",
		es: "Estimado ${user.firstname}, ",
		pt: "Dear ${user.firstname}, ",
		it: "Caro ${user.firstname}, ",
		ja: "${user.firstname} さん, "
	},

	localizedMessageForMirror: {
		fr: "Ce ztamp n'est encore associé à aucune application ! Choisissez maintenant les applications que vous voulez installer !",
		en: "This ztamp has no application installed! You can now choose which applications to play next time !",
		de: "Dieser Ztamp ist mit keiner Applikation verbunden! Wählen sie jetzt die Applikationen aus, die ausgeführt werden sollen.",
		es: "Esté ztamp aún no está vinculado con ninguna aplicación ! Tenes que eligir las aplicaciones que van a vincular!",
		pt: "This ztamp has no application installed! You can now choose which applications to play next time !",
		it: "Questo Ztamp non è stato associato ad alcuna applicazione! Scegliete le applicazioni che desiderate installare !",
		ja: "このズタンプにはアプリケーションがインストールされていません！　次回使う時にはインストールしておいてください。"
	},

	localizedMessageForOthers: {
		fr: "Ce ztamp n'est encore associé à aucune application ! Allez sur violet.net pour choisir les applications que vous voulez lui associer !",
		en: "This ztamp has no application installed! Go to violet.net and choose which application to launch next time !",
		de: "Dieser Ztamp ist mit keiner Applikation verbunden! Gehen Sie zu Violet.net, um die Applikationen auszuwählen, die sie ihm zuweisen wollen.",
		es: "Esté ztamp aún no está vinculado con ninguna aplicación ! ve a Violet.net para elegir las aplicaciones a las que quieras vincularle !",
		pt: "This ztamp has no application installed! Go to violet.net and choose which application to launch next time !",
		it: "Questo Ztamp non è stato associato ad alcuna applicazione! Vai su Violet.net per scegliere le applicazioni da eseguire !",
		ja: "このズタンプにはアプリケーションがインストールされていません！　ヴィオレ　ドット　ネットでアプリケーションを設定してください。"
	},


	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		// read the initial context (settings of the application)
		var newZtamp = event.trigger.object;
		var user = newZtamp.owner;
		var application = event.application || event.trigger.application; // a modification in the event object hierarchy

		// prepare an introductional text to speech
		var lang = (this.dedicatedIntros[user.lang]) ? user.lang : "en";
		debug("Application installer lang : " + lang);

		var introMessage =
			(user.firstname && !user.firstname.isEmpty())
				? this.dedicatedIntros[lang].replaceTemplateVariables({user: user}) // add 'dear xx'
				: "";

		var msgToPlay = event.trigger.target.hw_type.contains("violet.mirror")
					? this.localizedMessageForMirror
					: this.localizedMessageForOthers;

		introMessage += msgToPlay[lang];

		// var installUrl = this.appInstallUrl.replaceTemplateVariables({ztamp: newZtamp, user: user});
		// get the application installer title and picture
		var appInfo = violet.applications.getProfile(this, application.id);

		// open a browser to install a new application
		return violet.objects.createMessage(objectId)
			.playLibSound("GuitareAccord").async(true)
			.showBubble("mirware (by violet)", introMessage, {pictureurl: appInfo.picture}).async(true)
			.doObjectRegistration(newZtamp)
			.alternativelyAddTextToSpeech(introMessage, user.lang)
			//.browsePage(installUrl) // parameters
		;
	}
};

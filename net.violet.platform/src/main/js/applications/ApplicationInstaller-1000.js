/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "ApplicationInstaller-1000",
  apiVersion: "0.5",
	name: "net.violet.js.applicationinstaller-1000"
};

/**
 * Event handlers
 */
appEventHandlers = {

	appInstallUrl: "http://my.violet.net/people/${user.id}/applications?co=${ztamp.id}",

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
	
	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		// read the initial context (settings of the application)
		var newZtamp = event.trigger.object;
		var user = newZtamp.owner;

		// prepare an introductional text to speech
		var lang = (this.localizedMessageForMirror[user.lang]) ? user.lang : "en";

		var introMessage =
			(user.firstname && !user.firstname.isEmpty())
				? this.dedicatedIntros[lang].replaceTemplateVariables({user: user}) // add 'dear xx'
				: "";
		introMessage += this.localizedMessageForMirror[lang];

		var installUrl = this.appInstallUrl.replaceTemplateVariables({ztamp: newZtamp, user: user});

		// open a browser to install a new application
		return violet.objects.createMessage(objectId)
			.playLibSound("GuitareAccord")
			.addTextToSpeech(introMessage, lang)
			.browsePage(installUrl)
		;
	}
};

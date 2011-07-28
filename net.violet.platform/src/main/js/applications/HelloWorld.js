
appDescr = {
	apiKey: "HelloWorld",
	name: "net.violet.js.helloworld"
};


/**
 * Event handlers
 */
appEventHandlers = {

	resources: {
		genders: ["M", "F"],
		langs: ["fr", "en", "de", "es", "pt", "it"],
		langNames: ["français", "anglais", "allemand", "espagnol", "portugais", "italien"],
		messages: {
			fr: "Cher ${object.owner.firstname}, Bonne Journée le monde !",
			en: "Dear ${object.owner.firstname} ! Oï to the World !",
			de: "Sehr geehrte ${object.owner.firstname}, Hallo Welt!",
			es: "Estimado ${object.owner.firstname}, Hola mundo!",
			pt: "Caro ${object.owner.firstname}, Olá, mundo!",
			it: "${object.owner.firstname} cari, Ciao, mondo!"
		}
	},

	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		// read the initial context (settings of the application)
		var settings = event.settings;

		var rnd100   = Math.round(Math.random() * 100);
		var lang     = this.resources.langs[rnd100 % 6]; // 0..5
		var langName = this.resources.langNames[rnd100 % 6]; // 0..5
		var gender   = this.resources.genders[rnd100 % 2];   // 0..1

		var msg = this.resources.messages[lang].replaceTemplateVariables(event.trigger);
		debug("HelloWorld : [" + msg + "] (" + lang + ") " + gender);

		return violet.objects.createMessage(objectId)
			.showBubble(settings.title, settings.message, settings)
			.addTextToSpeech(msg, lang, null, gender);

	}
};

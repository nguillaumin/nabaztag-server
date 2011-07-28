/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "Chrono",
	name: "net.violet.js.chrono"
};

/**
 * Event handlers
 */
appEventHandlers = {

	goMessage: {
		fr: "Top Chrono !",
		en: "Steady..!? Go!",
		de: "Fertig..!? Los!",
		es: "Top cronómetro !",
		it: "Top crono !",
		pt: "Top cronômetro !",
		ja: "計測　開始！"
	},

	and: {
		fr: " et ",
		en: " and ",
		de: " und ",
		es: " y ",
		it: " e ",
		pt: " e ",
		ja: " と "
	},

	formats: {
		"one_second": {
			fr: " 1 seconde !",
			en: " 1 second !",
			de: " 1 sekunde !",
			es: " 1 segundo !",
			it: " 1 secondo !",
			pt: " 1 segundo !",
			ja: " 1 びょう !"
		},
		"more_than_one_second": {
			fr: " ${1} secondes !",
			en: " ${1} seconds !",
			de: " ${1} sekunden !",
			es: " ${1} segundos !",
			it: " ${1} secondi !",
			pt: " ${1} segundos !",
			ja: " ${1} びょう !"
		},
		"one_minute": {
			fr: "1 minute",
			en: "1 minute",
			de: "1 minute",
			es: "1 minuto",
			it: "1 minuto",
			pt: "1 minuto",
			ja: "1 ふん"
		},
		"more_than_one_minute": {
			fr: "${1} minutes",
			en: "${1} minutes",
			de: "${1} minuten",
			es: "${1} minutos",
			it: "${1} minuti",
			pt: "${1} minutos",
			ja: "${1} ふん"
		},
		"one_hour": {
			fr: "1 heure",
			en: "1 hour",
			de: "1 stunde",
			es: "1 hora",
			it: "1 ora",
			pt: "1 hora",
			ja: "1 じかん"
		},
		"more_than_one_hour": {
			fr: "${1} heures",
			en: "${1} hours",
			de: "${1} stunden",
			es: "${1} horas",
			it: "${1} ore",
			pt: "${1} horas",
			ja: "${1} じかん"
		},
		"one_day": {
			fr: "1 jour",
			en: "1 day",
			de: "1 tag",
			es: "1 día",
			it: "1 giorni",
			pt: "1 dia",
			ja: "1 にち"
		},
		"more_than_one_day": {
			fr: "${1} jours",
			en: "${1} days",
			de: "${1} Tags",
			es: "${1} día",
			it: "${1} giorni",
			pt: "${1} dia",
			ja: "${1} にち"
		}
	},

	delays: { // (in seconds)
		second: 1,
		minute: 60,
		hour: 60*60,
		day: 24*60*60
	},

	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		var ztamp = event.trigger.object;

		// get the current subscription id (to save the settings)
		// note : this API call usually returns a list so we take the first element of the list
		// because this application can only be installed on a ztamp (SchedulingType="InteractionTrigger")
		var application = event.application;
		var subscr = violet.subscriptions.get(this, {id: ztamp.id, application_id: application.id})[0];

		// read the applications settings
		var settings = new Settings(event.settings);

		var lastTime = settings.getDate("lastTime");
		var voice    = settings.getString("voice");
		var userLang = settings.getString("lang");

		if (!userLang && voice) { // temporary patch while the VoicePicker doesn't set automatically the voice's language
			userLang = voice.substr(0,2).toLowerCase();
		}
		if (!this.goMessage[userLang]) userLang = "en";
		var ttsMsg = this.goMessage[userLang];

		if (lastTime == null) { // start a new chrono action
			// save the new settings
			settings.lastTime = now();
			violet.subscriptions.setSettings(this, {id: subscr.id, settings: settings});

		} else { // stop the chrono !
			var elapsedSecs = (now() - lastTime) / 1000;

			// *DESACTIVATED FOR NOW*
			// the stop chrono message may be prefixed or postponed with bits of speech,
			// with the usual replacement variables
			/*var ttsMsg = settings.getString("introduction", "")
									 + " " + this.getHumanReadable(elapsedSecs, userLang)
									 + " " + settings.getString("conclusion", "");

			var replacementValues = {
				object: event.trigger.target,
				ztamp: event.trigger.object
			};
			ttsMsg = ttsMsg.replaceTemplateVariables(replacementValues);*/

			// Just tell the elapsed time
			ttsMsg = this.getHumanReadable(elapsedSecs, userLang);

			// save the new settings
			delete settings.lastTime;
			violet.subscriptions.setSettings(this, {id: subscr.id, settings: settings});
		}

		// get the application title and picture (for the bubble)
		var appInfo  = violet.applications.getProfile(this, application.id);
		var appTitle = violet.site.getLocalizedString(this, {id: userLang, key:appInfo.title});

		// play the message
		return violet.objects.createMessage(objectId)
			.playLibSound("Chrono").async()
			.showBubble(appTitle, ttsMsg, {pictureurl: appInfo.picture, displaytime: 5000}).async()
			.addTextToSpeech(ttsMsg, userLang, settings.voice)
		;

	},

  /*
   * returns a 'human readable' form of time passed
   */
  getHumanReadable: function (pElapsedSecs, pLang) {

		var timelaps_d = Math.floor(pElapsedSecs / 86400);  // how many days were elapsed ?
		pElapsedSecs = pElapsedSecs - (timelaps_d * 86400); // take off the days to process the remaining
		var timelaps_h = Math.floor(pElapsedSecs / 3600);   // how many hours ?
		pElapsedSecs = pElapsedSecs - (timelaps_h * 3600);  // take off the hours ?
		var timelaps_m = Math.floor(pElapsedSecs / 60);     // how many minutes ?
		var timelaps_s = Math.round(pElapsedSecs - (timelaps_m * 60));  // how many remaining seconds ?

		var humantime = "";

		if (timelaps_d > 0) { // one day at least
			humantime += (timelaps_d == 1) ? this.formats.one_day[pLang]: this.formats.more_than_one_day[pLang].replaceTemplateVariables([timelaps_d]);
			humantime += ", ";
		}

		if (timelaps_h > 0) { // one hour at least
			humantime += (timelaps_h == 1) ? this.formats.one_hour[pLang]: this.formats.more_than_one_hour[pLang].replaceTemplateVariables([timelaps_h]);
			humantime += ", ";
		}

		if (timelaps_m > 0) { // one minute at least
			humantime += (timelaps_m == 1) ? this.formats.one_minute[pLang]: this.formats.more_than_one_minute[pLang].replaceTemplateVariables([timelaps_m]);
			humantime += this.and[pLang];
		}

		if (timelaps_s > 0) { // one second at least
			humantime += (timelaps_s == 1) ? this.formats.one_second[pLang]: this.formats.more_than_one_second[pLang].replaceTemplateVariables([timelaps_s]);
		}

		return humantime;
  }

};

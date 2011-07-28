/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "Remember",
	name: "net.violet.js.remember"
};

/**
 * Event handlers
 */
appEventHandlers = {

	formats: {
		"oneMinuteAgo": { // temps écoulé < 1 minute
			fr: "il y a 1 minute",
			en: "1 minute ago",
			de: "vor 1 minute",
			es: "hace 1 minuto",
			it: "da 1 minuto",
			pt: "1 minute ago",
			ja: "1 ふんまえ"
		},
		"minutesAgo": { // temps écoulé < 1 heure
			fr: "il y a ${1} minutes", // il y a 5 minutes
			en: "${1} minutes ago",    // 5 minutes ago
			de: "vor ${1} minuten",
			es: "hace ${1} minutos",   // hace 5 minutos
			it: "da ${1} minuti",      // da 5 minuti
			pt: "${1} minutes ago",
			ja: "${1} ふんまえ"
		},
		"oneHourAgo": { // temps écoulé < 1 heure
			fr: "il y a 1 heure",
			en: "1 hour ago",
			de: "vor 1 stunde",
			es: "hace 1 hora",
			it: "da 1 ora",
			pt: "1 hour ago",
			ja: "1 じかんまえ"
		},
		"hoursAgo": { // temps écoulé < 1 journée
			fr: "il y a ${1} heures", // il y a 2 heures
			en: "${1} hours ago",     // 2 hours ago
			de: "vor ${1} stunden",
			es: "hace ${1} horas",    // hace 2 horas
			it: "da ${1} ore",        // da 2 ore
			pt: "${1} minutes ago",
			ja: "${1} じかんまえ"
		},
		"yesterday": { // hier
			fr: "'hier à 'HH' heures 'MM", // hier à 16 heures 15
			en: "'yesterday at 'h MM TT",  // yesterday at 4 15 PM
			de: "'Gestern um 'HH' Uhr 'MM",
			es: "'Ayer a 'HH' horas 'MM",  // ayer a 16 horas 15
			it: "'ieri alle 'HH' e 'MM' minuti'",  // ieri alle 16 e 15 minuti
			pt: "' at 'h MM TT",
			ja: "'きのうの 'HH'じ 'MM'ふん'"
		},
		"currentWeek": { // la semaine dernière
			fr: "dddd' à 'HH' heures 'MM", // mardi à 18 heures 15
			en: "dddd' at 'h MM TT",       // tuesday at 6 15 PM
			de: "dddd' um 'HH' Uhr 'MM",   // dienstag um 18 Uhr 15
			es: "'el 'dddd' a las 'HH' horas 'MM", // el martes a las 18 horas 15
			it: "dddd' alle 'HH' e 'MM' minuti'",  // martedì alle 18 e 15 minuti
			pt: "' at 'h MM TT",
			ja: "dddd' の 'HH'じ 'MM'ふん'"
		},
		"preciselyWhen": { // encore avant..
			fr: "dddd d mmmm' à 'HH' heures 'MM", // lundi 7 octobre à 18 heures 35
			en: "dddd', the 'dS' of 'mmmm' at 'h MM TT", // monday, the 7th of october at 6 35 PM
			de: "dddd d mmmm' um 'HH' Uhr 'MM", // Montag, 7. Oktober um 18 Uhr 35
			es: "dddd d ' de 'mmmm' a las 'HH' horas 'MM'", // el lunes 7 de octubre a las 18 horas 35
			it: "dddd d mmmm' alle 'HH' ore e 'MM' minuti'",  // lunedì 7 ottobre alle 18 ore e 35 minuti
			pt: "dddd d mmmm' at 'HH' hour 'MM",
			ja: "mmmm d'にち 'dddd' の 'HH'じ 'MM'ふん'"
		}
	},
	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		var ztamp = event.trigger.object;

		// get the current subscription id
		// note : this API call usually returns a list so we take the first element of the list
		// because this application can only be installed on a ztamp (SchedulingType="InteractionTrigger")
		var application = event.application;
		var subscr = violet.subscriptions.get(this, {id: ztamp.id, application_id: application.id})[0];

		// read the applications settings
		var settings = new Settings(event.settings);
		var timezone = event.trigger.target.tz;

		// creates the body message : apply the template replacement variables
		var lastTime = settings.getLocaleDate("lastTime", timezone, now()); //new LocaleDate(timezone).setTime( settings.getDate("lastTime", now()).getTime() );
		debug("last time was : " + lastTime.toString());
		var userLang = settings.getString("lang", ztamp.owner.lang);

		// increment current counter and determine if the limit was reached
		var ttsMsg = settings.getString("introduction", "")
							   + " " + this.getHumanReadable(lastTime, userLang)
								 + " " + settings.getString("conclusion", "");

		var replacementValues = {
			object: event.trigger.target,
			ztamp: ztamp
		};
		ttsMsg = ttsMsg.replaceTemplateVariables(replacementValues);

		// save the new settings
		settings.lastTime = now();
		violet.subscriptions.setSettings(this, {id: subscr.id, settings: settings});

		// get the application title and picture (for the bubble)
		var appInfo  = violet.applications.getProfile(this, application.id);
		var appTitle = violet.site.getLocalizedString(this, {id: userLang, key:appInfo.title});

		return violet.objects.createMessage(objectId)
			.showBubble(appTitle, ttsMsg, {pictureurl: appInfo.picture, displaytime: 3000}).async()
			.playLibSound("Remember")
			.addTextToSpeech(ttsMsg, userLang, settings.voice)
		;

	},

  /*
   * returns a 'human readable' form of time passed
   */
  getHumanReadable: function (pDate, pLang) {

		if (!pDate || !pDate.getMinutes) return "some time ago, i don't know when.";
		if (!pLang) pLang = "en";

		var timelaps_s = ((now().getTime() - pDate.getTime()) / 1000); // elapsed time in seconds
		var timelaps_d = Math.floor(timelaps_s / 86400);

		debug("Elapsed time in seconds : " + timelaps_s);

		if (timelaps_d == 0) { // today
			if (timelaps_s < 120)   return this.getFormat("oneMinuteAgo", pLang);
			if (timelaps_s < 3600)  return this.getFormat("minutesAgo", pLang).replaceTemplateVariables([Math.floor(timelaps_s/60)]);
			// if (timelaps_s < 7200)  return "1 hour ago";
			if (timelaps_s < 86400) return this.getFormat("hoursAgo", pLang).replaceTemplateVariables([Math.floor(timelaps_s/3600)]); // return (Math.floor(timelaps_s/3600) + " hours ago");
		}

		if (timelaps_d == 1) { // Yesterday
				return DateHelper.format(pDate, this.getFormat("yesterday", pLang), pLang); // "Yesterday at hh:mm"
		}

		if (timelaps_d <= 6) { // This week
				return DateHelper.format(pDate, this.getFormat("currentWeek", pLang), pLang);
		}

		// Some days ago
		return	DateHelper.format(pDate, this.getFormat("preciselyWhen", pLang), pLang);
  },

	getFormat: function(pFormatName, pLang) {
		var fmt = (this.formats[pFormatName]) ? this.formats[pFormatName] : this.formats.preciselyWhen;
		return fmt[pLang] ? fmt[pLang] : fmt.en;
	}
};

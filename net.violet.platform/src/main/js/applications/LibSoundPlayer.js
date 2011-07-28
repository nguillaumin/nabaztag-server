/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "LibSoundPlayer",
  apiVersion: "0.4",
	name: "net.violet.js.libsoundplayer"
};

/**
 * Event handlers
 */
appEventHandlers = {

	soundNames: [
		"AccordeonAccord", "AccordeonTrad", "AncheGrave", "AncheJoyeuse",
		"ClarinetteAigre", "ClavecinEspoir", "ClavecinLent", "ClavecinQuestion", "ClavecinRapide",
		"Corne", "CoussinPeteur", "Didgeridoo",
		"FluteAsthme", "FluteHesitante", "FluteMontagne", "FluteTendre", "FluteZen",
		"GuimbardeGling", "GuimbardeHello", "GuimbardeSlap", "GuimbardeVive",
		"GuitareAccord", "GuitareArpege", "GuitareQuestion",
		"HarmonicaGlisse", "HarmonicaMou", "HarmonicaVif",
		"HarpeAppel", "HarpeGlisse", "HautboisAigre",
		"JazzoBavard", "JazzoCoulisse",
		"KazooAnnonce", "KazooCircus", "KazooFarceur",
		"MelodicaTango", "MelodicaVif", "Piano",
		"SiffletPolice", "SiffletSirene", "SiffletTrain",
		"TromboneBrintzingue", "TromboneIndicatif", "TromboneNasillard", "TromboneNavire", "TromboneVif", "TromboneWah",
		"Trompe", "TrompetteBrintzingue", "TrompetteVarietoche"
	],

	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		// prepare an introductional text to speech
		var message = violet.objects.createMessage(objectId);

		for (var soundName, i = 0; soundName = this.soundNames[i]; i++) {
			message.addTextToSpeech(soundName, "fr")
				.playLibSound(soundName);
		}

		return message;

	}
};

/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "RandomLibSound",
  apiVersion: "0.4",
	name: "net.violet.js.randomlibsound"
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

		var rnd100 = Math.round(Math.random() * 100);
		var sound  = this.soundNames[rnd100 % this.soundNames.length]; // 0..5

		// play the random sound
		return violet.objects.createMessage(objectId)
				.playLibSound(sound);

	}
};

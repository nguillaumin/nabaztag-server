/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "OperationFuturEnSeine",
	name: "net.violet.js.operationfuturenseine"
};

Object.complete(Array.prototype, {
	pickRandom: function() {
		return this[Math.floor(Math.random()*this.length)];
	}
});

/**
 * Event handlers
 */
appEventHandlers = {

	invitations: [
		"Bonjour ! Bienvenue sur le stand Violet et GS1 ! Je suis votre clé pour entrer dans l'internet des objets. En remplissant le formulaire qui va s'afficher, vous participerez également au tirage au sort pour gagner un lapin Nabaztag.",
		"Bienvenue sur le stand GS1 Violet ! Vous entrez ici dans l'Internet des objets. En plus, si vous remplissez le formulaire qui va s'afficher vous participerez au tirage au sort permettant de gagner un lapin Nabaztag ! Plutôt chouette non ?",
		"Bonjour et bienvenue. Ici sur le stand GS1 Violet, vous pouvez prendre connaissance de ce qu'est l'internet des objets, et en plus, gagner une chance de remporter le lapin Nabaztag offert par tirage au sort. C'est maintenant.. C'est Futur en Seine !",
		"Bonjour ! Je suis un nanoztag et je suis votre clé pour entrer dans l'internet des objets sur le stand Violet GS1. En remplissant le formulaire qui va s'afficher, vous pourrez en plus gagner un lapin Nabaztag par tirage au sort. Tous en scène !! C'est Futur en Seine !"
	],
	voices: [
		"FR-Anastasie", "FR-Archibald", "FR-Gertrude", "FR-Julie", "FR-Maxence", "FR-Philomene"
	],
	siteUrl: "http://mobilissimo.fr/gs1enseine/index.php?nano=${serial}",
	logoGS1: "<img alt='GS1' src='http://www.futur-en-seine.org/img/partners/gs1.gif' />",
	logoViolet: "<img alt='Violet' src='http://www.futur-en-seine.org/img/partners/violet.gif' />",


	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		var now = new Date();
		var endOfEvent = DateHelper.parseISO("2009-06-07T23:00:00+01:00");

		if (now > endOfEvent) {
			return [];

		} else {
			var invit = this.invitations.pickRandom();
			var invitHTML = "<div>" + invit + "<br/>" + this.logoGS1 + this.logoViolet + "</div>";
			var voice = this.voices.pickRandom();

			// get the application title and picture (for the bubble)
			var appInfo  = violet.applications.getProfile(this, event.application.id);

			// open a browser to the desired url
			return violet.objects.createMessage(objectId)
				.playLibSound("LaunchBrowser")
				.addTextToSpeech(invit, "fr", voice).async()
				.showBubble("BIENVENUE !", invitHTML, {pictureurl: appInfo.picture, displaytime: 10000})
				.browsePage(this.siteUrl.replaceTemplateVariables({serial: event.trigger.object.serial}))
			;
		}
	}
};

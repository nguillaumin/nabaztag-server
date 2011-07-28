/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "OperationAmusement",
	name: "net.violet.js.operationamusement"
};

/**
 * Event handlers
 */
appEventHandlers = {

	// redirector URL (uncomment the good line depending of the deployment platform)
	// redirectorUrl: "http://nabdev.violet.net/OS/redirector", // dev nabdev.violet.net/192.168.1.11
	// redirectorUrl: "http://object-pp.violet.net/OS/redirector", // pre-prod
	redirectorUrl: "http://api.violet.net/OS/redirector", // prod

	fallbackUrl: "http://www.futureof.net",

	mediaContents: [
		{
			title: "Superfluidity",
			text: "Dispositif multi-utilisateur par Electronic Shadow.\nUn environnement collectif en perpétuelle construction",
			ttsVoice: "US-Clarence",
			mainMedia: "http://www.futureof.net/art010101012/ES.html",
			altMedia: "broadcast/broad/config/amusement/ES.mp3",
			picture: "broadcast/broad/applications/pictures/OperationAmusement-img0.gif"
		},
		{
			title: "Légèreté digitale",
			text: "3D motion par Gkaster.\nUn hommage à TRON et d'autres films de science Fiction",
			ttsVoice: "FR-Anastasie",
			mainMedia: "http://www.futureof.net/art010101012/GK.html",
			altMedia: "broadcast/broad/config/amusement/GK.mp3",
			picture: "broadcast/broad/applications/pictures/OperationAmusement-img1.gif"
		},
		{
			title: "Hurdler",
			text: "Jeu vidéo par Messhof.\nLe jeu a beau être pixelisé, les cycles d'animation sont réalistes car 'rotoscopés'",
			ttsVoice: "US-Darleen",
			mainMedia: "http://www.futureof.net/art010101012/MH.html",
			altMedia: "broadcast/broad/config/amusement/MH.mp3",
			picture: "broadcast/broad/applications/pictures/OperationAmusement-img2.gif"
		},
		{
			title: "Origamer",
			text: "Mixed media par Factoid.\nCette experience est l'occasion de travailler à la frontière de l'installation et du jeu vidéo",
			mainMedia: "http://www.futureof.net/art010101012/FA.html",
			ttsVoice: "UK-Penelope",
			altMedia: "broadcast/broad/config/amusement/FA.mp3",
			picture: "broadcast/broad/applications/pictures/OperationAmusement-img3.gif"
		},
		{
			title: "Surprise !",
			text: "Photo par Philippe Jarrigeon.\nJ'ai toujours été fasciné par les effets spéciaux utilisés au début du siècle dernier",
			ttsVoice: "FR-Archibald",
			mainMedia: "http://www.futureof.net/art010101012/PH.html",
			picture: "broadcast/broad/applications/pictures/OperationAmusement-img4.gif"
		}
	],


	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		// determine the target object
		var readerObject = event.trigger.target;

		// determine the plage of valid content for nabaztag/mirror
		// the contents that have alternate audio sound for nabaztag do come first
		var plage = (readerObject.hw_type == "violet.mirror") ? this.mediaContents.length : 4;
		var rnd100 = Math.round(Math.random() * 100);
		// get a random content inside the valid plage
		var content = this.mediaContents[rnd100 % plage]; // 0..plage-1

		// get a timestamp from the API to provide to the redirector service
		var secret = violet.secrets.createTimestamp(this, {});

		// create the message
		var resp = violet.objects.createMessage(objectId);

		resp
			.showBubble(content.title, content.text, {pictureurl: content.picture, displaytime: 5000}).async()
			.addTextToSpeech(content.title, null, content.ttsVoice).async()
			.browsePage(this.redirectorUrl, {url: content.mainMedia, secret: secret, fallbackUrl: this.fallbackUrl})
		;

		// for nabaztag tag
		if (content.altMedia) resp.alternativelyPlayAudioStream(content.altMedia, {streaming: true, withEar: true})

		return resp;
	}
};

/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "SleepMaster",
	name: "net.violet.js.sleepmaster"
};

/**
 * Event handlers
 */
appEventHandlers = {

	willAwake: {
		"fr": "${object.name} va bientôt se réveiller !",
		"en": "${object.name} will soon awake !",
		"de": "",
		"it": "",
		"es": "",
		"ja": "${object.name} は、もうすぐ、めざめます！"
	},
	willSleep: {
		"fr": "${object.name} va bientôt s'endormir !",
		"en": "${object.name} will soon go to sleep !",
		"de": "${object.name} wurde !",
		"it": "${object.name} !",
		"es": "${object.name} !",
		"ja": "${object.name} は、もうすぐ、ねむります！"
	},
	willNotDoAnything: {
		"fr": "${object.name} n'est pas connecté !",
		"en": "${object.name} is not connected !",
		"de": "${object.name} ist nicht !",
		"it": "${object.name} !",
		"es": "${object.name} !",
		"ja": "${object.name} は、接続されていません！"
	},
	cannotSleep: {
		"fr": "${object.name} ne peut pas s'endormir !",
		"en": "${object.name} cannot go to sleep !",
		"de": "${object.name} wurde nicht !",
		"it": "${object.name} !",
		"es": "${object.name} !",
		"ja": "${object.name} は、ねむりません！"
	},
	onSleep: {
		"fr": {type: "audio", value:"broadcast/broad/config/sleepmaster/fr/gotosleep.mp3"},
		"en": {type: "audio", value:"broadcast/broad/config/clin/uk/Solo9.mp3"},
		"de": {type: "tts", value:"Bye Bye !!", voice:"US-Billye"},
		"it": {type: "tts", value:"Bye Bye !!", voice:"US-Billye"},
		"es": {type: "tts", value:"Bye Bye !!", voice:"US-Billye"},
		"ja": {type: "tts", value:"Bye Bye !!", voice:"US-Billye"}
	},
	sigh: "broadcast/broad/config/sleepmaster/sigh.mp3",

	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		// read the initial context (settings of the application)
		var user = event.trigger.object.owner;
		var userLang = user.lang;
		if (!this.willAwake[userLang]) {
			userLang = "en";
		}

		// who is the targetted object ?
		var target = event.trigger.target;

		// feedback for the reader object
		var msgToReader = "";
		var replacementValues = {object: target};

		if (!target.hw_type.contains("violet.nabaztag")) {
			msgToReader = this.cannotSleep[userLang].replaceTemplateVariables(replacementValues);

		} else {
			// ask for the target object status
			var targetID = event.settings.targetID || objectId; // just in case we change for a setting instead of the target property
			var targetStatus = violet.objects.getStatus(this, targetID);

			if (targetStatus == "awaken") {
				// message for target : say byebye
				var msgToTarget = violet.objects.createMessage(targetID)
					.from(user.id).title("Time to sleep")
					.playLibSound("SleepMaster")
				;

				var resourceForTarget = this.onSleep[userLang];
				if (resourceForTarget.type == "tts") {
					msgToTarget.addTextToSpeech(resourceForTarget.value, null, resourceForTarget.voice);
				} else {
					msgToTarget.playAudioStream(resourceForTarget.value);
				}
				msgToTarget.send(this); // send from this application

				// go to sleep
				violet.objects.setStatus(this, {id: targetID, status: "asleep"});

				// feedback for the reader object if he is different
				if (event.trigger.reader && event.trigger.reader.id != targetID) {
					msgToReader = this.willSleep[userLang].replaceTemplateVariables(replacementValues);
				}

			} else if (targetStatus == "asleep") {
				// send awake order
				violet.objects.setStatus(this, {id: targetID, status: "awaken"});

				// message for target : say hello
				violet.objects.createMessage(targetID)
					.from(user.id).title("Time to wake up")
					.playLibSound("SleepMaster")
					.playAudioStream(this.sigh)
					.send(this)
				;

				// feedback for the reader object if he is different
				if (event.trigger.reader && event.trigger.reader.id != targetID) {
					msgToReader = this.willAwake[userLang].replaceTemplateVariables(replacementValues);
				}

			} else if (targetStatus == "disconnected") {
				msgToReader = this.willNotDoAnything[userLang].replaceTemplateVariables(replacementValues);
			}
		}

		if (msgToReader) {
			// feedback on the reader object
			var reader = (event.trigger.reader || target);
			// get the application title and picture (for the bubble)
			var application = event.application; // a modification in the event object hierarchy
			var appInfo  = violet.applications.getProfile(this, application.id);
			var appTitle = violet.site.getLocalizedString(this, {id: userLang, key:appInfo.title});

			// display the action result in a bubble or in a tts on the reader object
			// if it was not the target object itself
			return violet.objects.createMessage(reader.id)
				.playLibSound("SleepMaster").async()
				.showBubble(appTitle, msgToReader, {pictureurl: appInfo.picture, displaytime: 3000}).async()
				.alternativelyAddTextToSpeech(msgToReader, userLang)
			;
		} else {
			return [];
		}
	}
};

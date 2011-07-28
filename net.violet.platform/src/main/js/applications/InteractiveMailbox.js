/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "InteractiveMailbox",
  apiVersion: "0.4",
	name: "net.violet.js.interactivemailbox"
};

/**
 * Event handlers
 */
appEventHandlers = {

	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {
		// read the applications settings
		var settings = event.settings;
		var ztamp  = event.trigger.object;

		// ask the current list of message in the ztamp box
		var messages = violet.messages.get(this, {id: "inbox", object: ztamp.id, userId: ztamp.owner.id});
		var msgCount = messages.length;

		if (msgCount == 0) {
			return violet.objects.createMessage(objectId)
				.addTextToSpeech("No new message.", "en")
			;

		} else {
			// we've got something to read : pass in interactive mode
			var msgIds = [];
			for (var i = 0, len = messages.length; i < len; i++) {
				msgIds.push(messages[i].id);
			}

			return violet.objects.createMessage(objectId)
				.saveAnnotations({userId: ztamp.owner.id, msgIds: msgIds}) // remember the context
				.startInteractive()
			;
		}
	},

	/**
	 * STARTED
	 */
	onStartInteractiveEvent: function(objectId, event) {

		// read the saved annotations
		var userId = event.settings.userId;
		var msgIds = event.settings.msgIds;

		// ask the API the content of the messages
		debug("read from saved context : userId = " + userId + ", msgIds = " + msgIds);
		var pojoMessages = violet.messages.play(this, {id: objectId, messageIds: msgIds});

		// build the final message gathering all the pojo sequences into one
		var finalMessage = violet.objects.createMessage(objectId)
			.playLibSound("MailPost")
			.addTextToSpeech("You have " + msgCount + " messages in you inbox !", "en")
		;
		for (var i = 0, len = pojoMessages.length; i < len; i++) {
			// identify the sender of each message
			var senderId = pojoMessages[i].from;
			var sender = violet.people.getProfile(this, senderId);
			finalMessage
				.addTextToSpeech("A message from " + sender.firstname)
				.addSequence(pojoMessages[i].sequence)
			;
		}
		return finalMessage;
	}, // StartInteractiveEvent


	/**
	 * END OF SEQUENCE
	 */
	onEndOfSequenceEvent: function(objectId, event) {

		var userId = event.settings.userId;
		var msgIds = event.settings.msgIds;

		// Archive the readen messages
		msgIds.forEach(function(msgId) {
			violet.messages.archive(this, {id: msgId, userId: userId});
		});

		return violet.objects.createMessage(objectId)
			.endInteractive()
		;
	} // EndOfSequenceEvent


};

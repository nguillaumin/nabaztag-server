/**
 * Application Descriptor
 */
appDescr = {
	apiKey: "ZtampCounter",
	name: "net.violet.js.ztampcounter"
};

/**
 * Event handlers
 */
appEventHandlers = {
	/**
	 * ZTAMP DETECTION
	 */
	onTriggerEvent: function(objectId, event) {

		var ztamp = event.trigger.object;
		var application = event.application;

		// get the current subscription id
		// note : this API call usually returns a list so we take the first element of the list
		// because this application can only be installed on a ztamp (SchedulingType="InteractionTrigger")
		var subscr = violet.subscriptions.get(this, {id: ztamp.id, application_id: application.id})[0];

		// read the applications settings
		var settings = new Settings(event.settings);
		var timezone = event.trigger.target.tz;

		var now = new LocaleDate(timezone);

		// creates the body message : apply the template replacement variables
		var initialValue = settings.getNum("initialValue", true);
		var currentCount = settings.getNum("currentCount", initialValue);
		var countLimit   = settings.getNum("countLimit", true);
		var incr         = settings.getNum("increment", true);
		var resetOptions = settings.getString("resetOptions", true); // never | counter | day | week | month | year

		var lastTime     = settings.getLocaleDate("lastTime", timezone, now);
		debug("lastTime:" + lastTime.toString());
		var startedOn     = settings.startedOn = settings.getLocaleDate("startedOn", timezone, now); // note : create settings.startedOn if it wasn't defined
		debug("startedOn:" + startedOn.toString());

		// next counter value candidate unless we reset the count
		var nextValue = (currentCount + incr);

		// determine if we have reached the count limit and if we must reset the counter
		var reached = (incr > 0) ? (nextValue >= countLimit) : (nextValue <= countLimit);
		var reset = false;


		if (resetOptions == "never") {

		} else if (resetOptions == "counter") {
			// reset when count limit is reached
			reset = reached;

		} else { // reset when day, week, month or year has changed
			reset = ((resetOptions == "day")  && (startedOn.getDay()   != now.getDay()))
					|| ((resetOptions == "week")  && (startedOn.getWeek(1) != now.getWeek(1)))
					|| ((resetOptions == "month") && (startedOn.getMonth() != now.getMonth()))
					|| ((resetOptions == "year")  && (startedOn.getYear()  != now.getYear()));
			if (reset) reached = false; // this doesn't have sense now
		}

		if (reset) {
			// reset counter
			settings.currentCount = nextValue = initialValue;
			settings.startedOn = now; // new serie

		} else {
			// take the new counter value
			settings.currentCount = nextValue;
		}

		var ttsMsg  = (nextValue + " !"); // default message is just the current number
		if (reached && settings.messageWhenReached) {
			ttsMsg = settings.messageWhenReached;

		} else if (settings.messageWhenCount) {
			ttsMsg  = settings.messageWhenCount;
		}

		// determines the replacement values for the spoken message
		var replacementValues = {
			object: event.trigger.target,
			ztamp: ztamp,
			currently: {hours: now.getHours(), minutes: now.getMinutes(), seconds: now.getSeconds()},
			lastTime: {hours: lastTime.getHours(), minutes: lastTime.getMinutes(), seconds: lastTime.getSeconds()},
			currentCount: nextValue
		};
		ttsMsg = ttsMsg.replaceTemplateVariables(replacementValues);

		// determines the new settings and save them
		settings.lastTime = now;
		violet.subscriptions.setSettings(this, {id: subscr.id, settings: settings});

		// get the application title and picture (for the bubble)
		var appInfo  = violet.applications.getProfile(this, application.id);
		var appTitle = violet.site.getLocalizedString(this, {id: settings.lang, key:appInfo.title});

		return violet.objects.createMessage(objectId)
			.showBubble(appTitle, ttsMsg, {pictureurl: appInfo.picture, displaytime: 5000}).async()
			.playLibSound("ZtampCounter")
			.addTextToSpeech(ttsMsg, settings.lang, settings.voice)
		;

	}
};

/**
 * Violet JavaScript library for Interactive Applications
 * JS API LIB version 0.2
 * Implements Public API calls and event handlers
 */
toString = function() {
	return "JS API LIB version 0.2";
};

//  -------------------------------------------------------------------------80
/**
 * JavaScript goodness
 */
if (!String.prototype.startsWith) {
	String.prototype.startsWith = function(pattern) {
		return this.indexOf(pattern) === 0;
	};
}

/**
 * Send debug information to the Java environment or to the firebug console
 */
debug = Packages.net.violet.platform.applets.js.helpers.JSDebug.write;

/**
 * Define the InteractiveApplication constructor
 */
InteractiveApplication = function(appDescr, eventMap) {

	for (var attrName in appDescr) {
		this[attrName] = appDescr[attrName];
	};
	debug("Created new instance of " + this);

	// prepare the event handlers map
	this.eventHandlers = {};

	for (var attr in eventMap) {
		// event handlers are functions whose name is : on<EventName>
		if (attr && attr.startsWith("on") && (typeof eventMap[attr] == "function")) {
			// lowercase the eventname
			this.eventHandlers[attr.toLowerCase()] = eventMap[attr];
		}
	}

};

/**
 * Define the prototype of every InteractiveApplication
 */
InteractiveApplication.prototype = {
	/**
	 * return a simple response with a text to speach message to the defaut actor of the application
	 */
	createTextMessage : function(objectId, textToSpeach) {
		var seq = {to: objectId, sequence: [{type: "net.violet.tts", text: textToSpeach, voice: "default"}]};
		return [seq];
	},

	/**
	 * return the event handler associated with this event name
	 */
	getEventHandler: function(evtName) {
		var eventHnd = this.eventHandlers[evtName.toLowerCase()];
		return (eventHnd ? eventHnd : this.emptyEventHnd);
	},

	/**
	 * dummy event handler returning a null sequence
	 */
	emptyEventHnd: function() {
		return [];
	},

	//---------------------------------------------------------------------------//

	/**
	 * process an event fired by objectId and return an Interactive Sequence to be played
	 */
	processEvent: function(objectId, event) {

		// log the event and application context
		var ctx = event.context || {};
		debug(this + " PROCESSING EVENT " + event.type + " FOR OBJECT " + objectId + " in context : " + ctx);

		// get the correct event handler..
		var evtName  = event.type;
		var eventHnd = this.getEventHandler("on" + evtName);
		// ..handle it !
		return eventHnd.apply(this, [objectId, event]);

	},

	toString: function() {
		return this.name;
	}
};

/**
 * Global entry points
 */
createApplicationInstance = function() {
	// create a new InteractiveApplication
	return new InteractiveApplication(this.appDescr, this.appEventHandlers);
};

processEvent = function(objectId, event) {
	if (!this.app) {
		this.app = createApplicationInstance();
	}
	return this.app.processEvent(objectId, event);
};

//  -------------------------------------------------------------------------80

/*
 * Create the public API calls in the global namespace
 */
(function(globalCtx) {

	var apiMethodNames = {
		"violet.objects" : ["getInfo", "setName", "getProfile", "getPreferences", "setPreferences", "getStatus", "setStatus", "findByName", "getNewComers", "search"],
		"violet.people"  : ["findByEmailAddress", "create", "getInfo", "exists"] // .. to be continued
	};

	var createNamespace = function(namespace) {
		var container = globalCtx;
		var nameParts = namespace.split(".");
		for (var i = 0, len = nameParts.length - 1; i <= len; i++) {
			var attrName = nameParts[i];
			if (!container[attrName]) container[attrName] = {}; // create attribute in container
			container = container[attrName]; // enter into it
		}
		return container;
	};

	var APICall = function(actionName) {
		return function(caller, params) {
			debug("APICall from " + caller);
			return Packages.net.violet.platform.applets.js.helpers.AppAPICaller.call(caller.apiKey, actionName, ((typeof params == 'string') ? {id: params} : params));
		};
	};

	for (namespace in apiMethodNames) {
		var container = createNamespace(namespace);
		for (var i = 0, fnNames = apiMethodNames[namespace], len = fnNames.length; i < len; i++) {
			var fnName = fnNames[i];
			container[fnName] = new APICall(namespace + "." + fnName);
		}
	}
})(this);

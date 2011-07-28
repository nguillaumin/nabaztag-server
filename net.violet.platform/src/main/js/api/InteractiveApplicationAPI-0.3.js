/**
 * Violet JavaScript library for Interactive Applications
 * JS API LIB version 0.3
 * Implements
 * - Public API calls
 * - event handlers
 * - Response builder for returning sequences
 */
toString = function() {
	return "JS API LIB version 0.3";
};

/**
 * Send debug information to the Java environment or to the firebug console
 */
debug = Packages.net.violet.platform.applets.js.helpers.JSDebug.write;


//----------------------------------------------------------------------------80
/**
 * JavaScript goodness
 */

/**
 * Copy all attributes in source to destination
 * except if the (optional) filter tell us not to
 */
Object.extend = function(dest, src, filter) {
  if (filter) {
    for (var attr in src)
      if (filter(src, attr)) dest[attr] = src[attr];

  } else {
    for (var attr in src)
      dest[attr] = src[attr];
  }
  return dest;
};

/**
 * String extensions
 */
if (!String.prototype.startsWith) {
	String.prototype.startsWith = function(pattern) {
		return this.indexOf(pattern) === 0;
	};
}

/**
 * Array extensions
 */
Array.contains = function(array, elt) {
  var i = array.length;
  while (i-- > 0) {
    if (array[i] == elt) return true;
  }
  return false;
};

if (!Array.prototype.contains) {
	Array.prototype.contains = function() {
		for (var i = 0, len = arguments.length; i < len; i++) {
			if (!Array.contains(this, arguments[i])) return false;
		}
		return true;
	};
}

if (!Array.prototype.push) {
	Array.prototype.push = function(x){
		this[this.length] = x;
		return true;
	}
};

if (!Array.prototype.pop) {
	Array.prototype.pop = function(){
  		var elt = this[this.length-1];
  		this.length--;
  		return elt;
	}
};

if (!Array.prototype.forEach) {
	Array.prototype.forEach = function(func, context) {
		for (var i = 0, len = this.length; i < len; i++) {
			func.call(context, this[i], i, this);
		}
	}
};

if (!Array.prototype.filteredCopy) {
	Array.prototype.filteredCopy = function(filter, context) {
		var copy = [];
		for (var i = 0, len = this.length; i < len; i++) {
			var filteredElt = filter.call(context, this[i], i, this);
			if (filteredElt !== undefined) copy.push(filteredElt);
		}
		return copy;
	}
};

//----------------------------------------------------------------------------80
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
		} else {
			this[attr] = eventMap[attr];
		}
	}

};

/**
 * Define the prototype of every InteractiveApplication
 */
InteractiveApplication.prototype = {

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
		var eventResponse = eventHnd.apply(this, [objectId, event]);

		if (eventResponse.constructor === Array) {
			// the event handler returned an array of messages : serialize them
			return eventResponse.filteredCopy(violet.filters.serializeMessages);
		} else {
			// the event handler returned a single message
			return (eventResponse.serialize) ? [eventResponse.serialize()] : [eventResponse];
		}

	},

	toString: function() {
		return this.name;
	}
};

//----------------------------------------------------------------------------80
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

//----------------------------------------------------------------------------80
/*
 * Create the public API calls in the global namespace
 */
(function(globalCtx) {

	var apiMethodNames = {
		"violet.applications" : ["countByCategory", "countByTag", "getByCategory", "getByTag", "getInfo", "getPackage", "getProfile", "getSchedulingMask", "getTagCloudForLang"],
		"violet.blacklists" : ["add", "get", "remove"],
		"violet.categories" : ["get"],
		"violet.cities" : ["getPeopleCities"],
		"violet.contacts" : ["acceptRequest", "add", "count", "countReceivedRequests", "countSentRequests", "delete", "get", "getReceivedContactRequests", "getSentContactRequests", "rejectRequest", "retractRequest"],
		"violet.countries" : ["getCodes"],
		"violet.hints" : ["findByContext"],
		"violet.libraries" : ["count", "delete", "get", "getByCategory", "getCategories", "getForSignature", "getProfile", "getRecent", "getShuffle", "put", "setProfile"],
		"violet.languages" : ["getASRLanguages", "getObjectLanguages", "getSiteLanguages", "getTTSLanguages"],
		"violet.messages" : ["archive", "count", "delete", "get", "replay", "sendMessage", "sendMusicMessage", "sendTTSMessage", "sendVocalMessage"],
		"violet.objects" : ["create", "findByName", "getInfo", "getNewComers", "getPreferences", "getProfile", "getStatus", "search", "setName", "setPreferences", "setProfile", "setStatus"],
		"violet.people"  : ["checkPassword", "create", "exists", "findByEmailAddress", "getInfo", "getObjects", "getPreferences", "getProfile", "search", "setEmail", "setPassword", "setPreferences", "setProfile", "tellMyFriends"],
		"violet.sessions" : ["create", "getUser", "update"],
		"violet.site" : ["getLocalizedString"],
		"violet.subscriptions" : ["create", "delete", "get", "setScheduling", "setSettings"],
		"violet.timezones" : ["get", "getByCountry", "getByOffset", "getCurrentOffset"],
		"violet.voices" : ["getByLang", "synthetize"]
	};

	var _namespace = function(namespace, content) {
		var container = globalCtx;
		var nameParts = namespace.split(".");
		for (var i = 0, len = nameParts.length - 1; i <= len; i++) {
			var attrName = nameParts[i];
			if (!container[attrName]) container[attrName] = {}; // create attribute in container
			container = container[attrName]; // enter into it
		}

		if (content) {
			Object.extend(container, content);
		}
		return container;
	};

	/*
   * APICall constructor (depending on global proxy object _jsProxyAPICall)
	 */
	var APICall = function(actionName) {
		return function(caller, params) {
			debug("APICall from " + caller);
			return _jsProxyAPICall(caller.apiKey, actionName, ((typeof params == 'string') ? {id: params} : params));
		};
	};

	/*
   * create all the public API call
	 */
	for (baseName in apiMethodNames) {
		var container = _namespace(baseName);
		for (var i = 0, fnNames = apiMethodNames[baseName], len = fnNames.length; i < len; i++) {
			var fnName = fnNames[i];
			container[fnName] = new APICall(baseName + "." + fnName);
		}
	}

	/*
   * create violet.net.httpRequest (depending on global proxy object _jsProxyHttpCall)
	 */
	_namespace("violet.net", {
		httpRequest: function(url, params, options) {
			return _jsProxyHttpCall(url, params || {}, options || {});
		}
	});

	/*
   * register the local function _namespace as violet.util.namespace
	 */
	_namespace("violet.util").namespace = _namespace;
})(this);


//----------------------------------------------------------------------------80

violet.util.namespace("violet.filters", {
	stringValue: function(val) {
		if (typeof val == 'string') {
			return val;
		} else if ((typeof val == 'number') || (typeof val == 'boolean')) {
			return "" + val;
		} else {
			return undefined;
		}
	},
	serializeMessages: function(msg) {
		if (msg == undefined || msg == null) return undefined;
		return (msg.serialize) ? msg.serialize() : msg;
	}
});

violet.util.namespace("violet.objects", {
	// the constructor of a new message
	message: function(to) {
		this.dest = (to.constructor === Array) ? to : [to];
		this.seq = [];
	},

	createMessage: function(to) {
		return new violet.objects.message(to);
	}
});

/**
 * Define the message methods
 */
violet.objects.message.prototype = {
	serialize: function() {
		return {to: this.dest, sequence: this.seq};
	},

	saveAnnotations: function(map) {
		this.seq.push(Object.extend({type: "annotation"}, map));
		return this;
	},

	setSettings: function(map) {
		var settings = Object.extend({type: "directive"}, map, violet.filters.stringValues);
		this.seq.push(settings);
		return this;
	},

	startInteractive: function() {
		this.seq.push({type: "directive", action: "start-interactive"});
		return this;
	},

	stopInteractive: function() {
		this.seq.push({type: "directive", action: "stop-interactive"});
		return this;
	},

	async: function() {
		var len = this.seq.length;
		if (len > 0) Object.extend(this.seq[len-1], {async: true});
		return this;
	}

};

/**
 * This special set of methods add a modality to the message sequence
 * each method doXXX will have a variation of type alternativelyDoXXX
 */
var messageSequenceMethods = {
	addTextToSpeech: function(text, lang, voice, gender) {
		var modality = ((lang) ? "net.violet.tts." + lang : "net.violet.tts");
		var ttsMap = {type: "expression", modality: modality, text: text};
		if (voice) {
			ttsMap.voice = voice;
		} else if (gender) { // voice and gender are optionals but mutually exclusive
			ttsMap.gender = gender;
		}
		this.seq.push(ttsMap);
		return this;
	},

	playAudioStream: function(url, options) {
		var sndMap = {type: "expression", modality: "net.violet.sound.mp3", url: url};
		if (options) {
			Object.extend(sndMap, options);
		}
		this.seq.push(sndMap);
		return this;
	},

	playLibSound: function(sndName, options) {
		return this.playAudioStream("broadcast/data/config/signature/" + sndName + ".mp3", options);
	},

	addPalette: function(color) {
		this.seq.push({type: "expression", modality: "net.violet.palette", color: color});
		return this;
	},

	playChoreography: function(data) {
		var chorMap = {type: "expression", modality: "net.violet.choreography"};
		if (data.indexOf(",") > 0) { // this is the text language of the chreography : 0, 1, led, 1, 255, 255, 0, ..
			Object.extend(chorMap, {choreography: data});
		} else { // this must be the url or resource name
			Object.extend(chorMap, {url: data});
		}
		this.seq.push(chorMap);
		return this;
	},

	wait: function(time) {
		this.seq.push({type: "expression", modality: "net.violet.wait", time_ms: time});
		return this;
	}
};

Object.extend(violet.objects.message.prototype, messageSequenceMethods);

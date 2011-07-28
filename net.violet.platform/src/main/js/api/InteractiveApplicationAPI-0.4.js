/**
 * Violet JavaScript library for Interactive Applications
 * JS API LIB version 0.4
 * Implements
 * - Public API calls
 * - Event handlers
 * - Response builder for returning sequences
 * - all modality methods have their alternative
 *
 * Note : for internal (trusted applications only)
 */
toString = function() {
	return "JS API LIB version 0.4";
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
 * Copy all attributes in source that are not present in destination
 */
Object.complete = function(dest, src) {
  for (var attr in src) {
      if (!dest[attr]) dest[attr] = src[attr];
  }
  return dest;
};

/**
 * Extract a value from an object using a JSON path
 * Usage exemple :
 *   var quidam = {name: "john", city: "Paris", friends: [{name: "chris"}, {name: "henry"}]}
 *   Object.extractValue(quidam, "name") (returns "john")
 *   Object.extractValue(quidam, "friends[1].name") (returns "henry")
 *   Object.extractValue(quidam, "objects[0].name") (returns undefined)
 */
Object.extractValue = function(obj, path) {
	var value = obj, properties = path.split(".");
  for (var i = 0, key; (key = properties[i]) && (value !== undefined); i++) {
		var match = /^(\w+)(?:\[([0-9]+)\])?/.exec(key), key = match[1], index = match[2];
		value = value[key];
		if ((value !== undefined) && (index !== undefined)) value = value[index];
  }
  return value;
};

/**
 * returns only the primitive values (strings, number, booleans, dates)
 * of this object seen as an hashmap
 * under the (dumb) form of a list of {key: , value: } objects
 * (needed for subscription settings)
 */
Object.toKeyValueList = function(obj) {
	var keyValueList = [];
	if (obj) {
		for (var attrName in obj) {
			var val = obj[attrName];
			if (Object.isPrimitiveType(val)) { // note : obj.hasOwnProperty() doesn't seem to exist in Rhino
				keyValueList.push({key: attrName, value: val});
			}
		}
	}
	return keyValueList;
};

Object.isPrimitiveType = function(val) {
	return (["string", "number", "boolean", "date"].contains($typeOf(val)));
};

$typeOf = function(obj) {
	if (obj == null || obj == undefined) return "undefined"; // note : typeof null usually return "object"
	var type = (typeof obj);
	if (type == "object") { // lets refine a little
		if (obj.getUTCDay) return "date";
		if (obj.constructor === Array) return "array";
	}
	return type;
};

/**
 * String extensions
 */
Object.complete(String.prototype, {
	startsWith: function(pattern) {
		return this.indexOf(pattern) === 0;
	},

	contains: function(strSearched) {
		return this.indexOf(strSearched) !== -1;
	},

	trim: function() {
		return this.replace(/^\s+|\s+$/g, '');
	},

  isEmpty: function() {
    return (this.trim() == '');
  },

	clean: function() {
		return this.replace(/\s+/g, ' ').trim();
	},

	camelCase: function() {
		return this.replace(/-\D/g, function(match){
			return match.charAt(1).toUpperCase();
		});
	},

	hyphenate: function() {
		return this.replace(/[A-Z]/g, function(match){
			return ('-' + match.charAt(0).toLowerCase());
		});
	},

	capitalize: function() {
		return this.replace(/\b[a-z]/g, function(match){
			return match.toUpperCase();
		});
	},

  toJSONString: function () {
    var m = {'\b': '\\b', '\t': '\\t', '\n': '\\n', '\f': '\\f', '\r': '\\r', '"' : '\\"', '\\': '\\\\'};
    if (/["\\\x00-\x1f]/.test(this)) {
      return '"' + this.replace(/([\x00-\x1f\\"])/g, function(a, b) {
				var c = m[b];
				if (c) return c;

				c = b.charCodeAt();
				return '\\u00' + Math.floor(c/16).toString(16) + (c%16).toString(16);
      }) + '"';
    } else {
      return '"' + this + '"';
    }
  },

  /**
   * Build a new String from this String seen as a template and using the given replacement values.
   * The template language provides two means of expressing replacement values ${expr} :
   * - 1based-indexed values will find replacement in an array like in
	   "Hi ! I am ${1}, here are my friends ${2} and ${3}".replaceTemplateVariables(["john", "chris", "henry"]);
     (returns "Hi ! I am John, here are my friends chris and henry")

   * - json path expression to replace expressions with values extirped from an object like in :
     var quidam = {name: "john", city: "Paris", friends: [{name: "chris"}, {name: "henry"}]};
     "Hi ! I am ${name}, here are my friends ${friends[0].name} and ${friends[1].name}".replaceTemplateVariables(quidam)
     (returns the same "Hi ! I am John, here are my friends chris and henry")
	 */
	replaceTemplateVariables: function(replacements) {

		if (!replacements) return this;

		if (replacements.constructor === Array) {
			return this.replace(/\$\{([0-9]+)\}/g, function(found, idx) {
				return replacements[idx-1];
			});

		} else {
			return this.replace(/\$\{([\w|\.|\[|\]]+)\}/g, function(found, expr) { // found is the whole matched expression surrounded by $() while expr is the extracted inner value
				return Object.extractValue(replacements, expr);
			});
		}
	}
});

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

Object.complete(Array.prototype, {
	contains : function() {
		for (var i = 0, len = arguments.length; i < len; i++) {
			if (!Array.contains(this, arguments[i])) return false;
		}
		return true;
	},
	push : function(x) {
		this[this.length] = x;
		return this;
	},
	pop : function() {
		var elt = this[this.length-1];
		this.length--;
		return elt;
	},
  pluck: function(property) {
    var results = [];
    this.forEach(function(value) {
      results.push(value[property]);
    });
    return results;
  },
	forEach : function(func, context) {
		for (var i = 0, len = this.length; i < len; i++) {
			func.call(context, this[i], i, this);
		}
	},
	last : function() {
		var len = this.length;
		return (len) ? this[len-1] : undefined;
	},
	filteredCopy : function(filter, context) {
		var copy = [];
		for (var i = 0, len = this.length; i < len; i++) {
			var filteredElt = filter.call(context, this[i], i, this);
			if (filteredElt !== undefined) copy.push(filteredElt);
		}
		return copy;
	}
});

/*--------------------------------------------------------------------------*/

DateHelper = {

  ISO8601RegEx: new RegExp("([0-9]{4})(-([0-9]{2})(-([0-9]{2})" +
        "([T ]([0-9]{2}):([0-9]{2})(:([0-9]{2})(\.([0-9]+))?)?" +
        "(Z|(([-+])([0-9]{2}):([0-9]{2})))?)?)?)?"),

  /*
   * Parse a date in ISO-8601 format (http://www.w3.org/TR/NOTE-datetime)
   **/
  parseISO: function (strDate) {
		if (!strDate) return null;
		if (strDate.getUTCDay) return strDate; // allready a date !

    var d = strDate.match(this.ISO8601RegEx);

    var offset = 0;
    var date = new Date(d[1], 0, 1);

    if (d[3])  { date.setMonth(d[3] - 1); }
    if (d[5])  { date.setDate(d[5]); }
    if (d[7])  { date.setHours(d[7]); }
    if (d[8])  { date.setMinutes(d[8]); }
    if (d[10]) { date.setSeconds(d[10]); }
    if (d[12]) { date.setMilliseconds(Number("0." + d[12]) * 1000); }
    if (d[14]) {
        offset = (Number(d[16]) * 60) + Number(d[17]);
        offset *= ((d[15] == '-') ? 1 : -1);
    }

    offset -= date.getTimezoneOffset();
    var time = (Number(date) + (offset * 60 * 1000));
    date.setTime(Number(time));

    return date;
  },

  /*
   * Return TRUE if pString is a date according to pFormat (eg. "DD/MM/YYYY", "YYYYMMDD", ..)
   **/
  parseDate: function(pString, pFormat) {

    pFormat = pFormat.toUpperCase();

    if (pString.isEmpty()) return null;
    if (!pFormat) pFormat="YYYYMMDD";

    var yIndex = pFormat.indexOf("YYYY");
    if (yIndex == -1) return false;
    var year = pString.substring(yIndex,yIndex+4);
    if (isNaN(year) || year > 2100 || year < 1900) return false;

    var mIndex = pFormat.indexOf("MM");
    if (mIndex == -1) return false;
    var month = pString.substring(mIndex,mIndex+2);
    if (isNaN(month) || month > 12 || month < 1) return false;

    var dIndex = pFormat.indexOf("DD");
    if (dIndex == -1) return false;
    var day = pString.substring(dIndex,dIndex+2);
    if (isNaN(day) || day > DateHelper._getMaxDay(year,month) || day < 1) return false;

    return new Date(year, month, day);
  },

  _zeropad: function(n){
    return ((n < 10) ? "0" : "") + n;
  },

  _getMaxDay: function(year, month) {
    if (month == 4 || month == 6 || month == 9 || month == 11) return 30;
    if (month == 2) {
      if (year%4 == 0 && year%100 != 0 || year%400 == 0) return 29;
      else return 28;
    }
    return 31;
  },

 /* Format a date into ISO 8601 conformant format
  * Accepted values for the format are numbers between 1-6 :
  *   1 : Year > YYYY (eg 1997)
  *   2 : Year and month > YYYY-MM (eg 1997-07)
  *   3 : Complete date  > YYYY-MM-DD (eg 1997-07-16)
  *   4 : Complete date plus hours and minutes > YYYY-MM-DDThh:mmTZD (eg 1997-07-16T19:20+01:00)
  *   5 : Complete date plus hours, minutes and seconds > YYYY-MM-DDThh:mm:ssTZD (eg 1997-07-16T19:20:30+01:00)
  *   6 : Complete date plus hours, minutes, seconds and a decimal fraction of a second > YYYY-MM-DDThh:mm:ss.sTZD (eg 1997-07-16T19:20:30.45+01:00)
  * offset is the time zone offset : Z, +01:00
  **/
  toISOFormat: function (date, fmt, offset) {
		debug("formatting date " + date + " in " + fmt + " with tz : " + offset);

    if (!fmt) { fmt = 5; }

    if (!offset) {
        offset = 'Z';

    } else {
        var d = offset.match(/([-+])([0-9]{2}):([0-9]{2})/);
        var offsetnum = (Number(d[2]) * 60) + Number(d[3]);
        offsetnum *= ((d[1] == '-') ? -1 : 1);
        date = new Date(Number(Number(date) + (offsetnum * 60000)));
    }

    var str = "" + date.getUTCFullYear();
    if (fmt > 1) { str += "-" + this._zeropad(date.getUTCMonth() + 1); }
    if (fmt > 2) { str += "-" + this._zeropad(date.getUTCDate()); }
    if (fmt > 3) {
      str += "T" + this._zeropad(date.getUTCHours()) +
             ":" + this._zeropad(date.getUTCMinutes());
      if (fmt > 5) {
        var secs = Number(date.getUTCSeconds() + "." +
                   ((date.getUTCMilliseconds() < 100) ? '0' : '') +
                   this._zeropad(date.getUTCMilliseconds()));
        str += ":" + this._zeropad(secs);
      } else if (fmt > 4) {
        str += ":" + this._zeropad(date.getUTCSeconds());
      }

      str += offset;
    }
    return str;
  },

  /*
   * Format a date with specified format where
   * DD : day, MM : month, YYYY or YY : year
   **/
  formatDate: function (pDate, pFmt){
    pFmt = pFmt.toUpperCase();
    var vDay      = this._zeropad(pDate.getDate());
    var vMonth    = this._zeropad(pDate.getMonth() + 1);
    var vYearLong = pDate.getFullYear();
    var vYear = (pFmt.indexOf("YYYY") > -1 ? vYearLong : vYearLong.toString().substring(3,4));

    var vDateString = pFmt.replace(/DD/g, vDay).replace(/MM/g, vMonth).replace(/Y{1,4}/g, vYear);

    return vDateString;
  },

  /*
   * Same that formatDate plus the following :
   * hh : hour, mm : minute, ss : seconds
   **/
  formatDateTime: function (pDate, pFmt){
    var vDay      = this._zeropad(pDate.getDate());
    var vMonth    = this._zeropad(pDate.getMonth() + 1);
    var vYearLong = pDate.getFullYear();
    var vYear = (pFmt.indexOf("YYYY") > -1 ? vYearLong : vYearLong.toString().substring(3,4));

    var vHour   = this._zeropad(pDate.getHours());
    var vMinute = this._zeropad(pDate.getMinutes());
    var vSecond = this._zeropad(pDate.getSeconds());

    var vDateString = pFmt.replace(/DD/g, vDay).replace(/MM/g, vMonth).replace(/Y{1,4}/g, vYear);
    vDateString = vDateString.replace(/hh/g, vHour).replace(/mm/g, vMinute).replace(/ss/g, vSecond);

    return vDateString;
  },

  /*
   * Display time according to AM/PM notation
   */
  getAMPMTime: function (pDate) {
    var time    = (pDate || new Date());
    var hour   = this._zeropad(time.getHours());
    var minute = this._zeropad(time.getMinutes());
    var second = this._zeropad(time.getSeconds());
    var ap = "AM";
    if (hour   > 11) { ap = "PM";        }
    if (hour   > 12) { hour = hour - 12; }
    if (hour   == 0) { hour = 12;        }
    var timeString = hour   + ':' +
                     minute + ':' +
                     second + " " + ap;
    return timeString;
  }
};

var now = function() {
	return new Date();
};

/*--------------------------------------------------------------------------*/
/**
 * JSONHelper.parse(x) securely parse a JSON string and produces a JavaScript object.
 * JSONHelper.serialize(x) takes a JavaScript object and produces a JSON text.
 */
var JSONHelper = {

  // form of a JSON expr
  jsonRex: /^("(\\.|[^"\\\n\r])*?"|[,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t])+?$/,

  parse: function(str) {
    try {
      if ((str != null) && this.jsonRex.test(str)) {
        var withISODates = str.replace(/"(\d{4}-\d{2}-\d{2}[T ]{1}\d{2}:\d{2}:\d{2})"/g, 'DateHelper.parseISO("$1")');
        var obj = eval('(' + withISODates + ')');
        return obj;
      }
    } catch (e) {
      alert(e.message);
    }
    return null;
  },

  serialize: function(arg) {
    return this.toJsonStringArray(arg).join('');
  },

  toJsonStringArray: function(arg, out) {
    out = out || new Array();
    var u; // undefined

    switch (typeof arg) {
    case 'object':
      if (arg) {
        if (arg.constructor == Array) {
          out.push('[');
          for (var i = 0; i < arg.length; ++i) {
            if (i > 0) out.push(',');
            this.toJsonStringArray(arg[i], out);
          }
          out.push(']');
          return out;
        } else if (arg.constructor == Date) {
          out.push('"');
          out.push(DateHelper.toISOFormat(arg));
          out.push('"');
        } else if (typeof arg.toString != 'undefined') {
          out.push('{');
          var first = true;
          for (var i in arg) {
            var curr = out.length; // Record position to allow undo when arg[i] is undefined.
            if (!first) out.push(',');
            this.toJsonStringArray(i, out);
            out.push(':');
            this.toJsonStringArray(arg[i], out);
            if (out[out.length - 1] == u) {
              out.splice(curr, out.length - curr);
            } else {
              first = false;
            }
          }
          out.push('}');
          return out;
        }
        return out;
      }
      out.push('null');
      return out;
    case 'unknown':
    case 'undefined':
    case 'function':
      out.push(u);
      return out;
    case 'string':
      out.push(arg.toJSONString());
      return out;
    default:
      out.push(String(arg));
      return out;
    }
  }
};

Object.prototype.toString = function() {
	return JSONHelper.serialize(this);
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
		var evtName = event.type;
		debug(this + " PROCESSING EVENT " + evtName + " FOR OBJECT " + objectId + " in context : " + ctx);

		// get the correct event handler..
		var eventHnd = this.getEventHandler("on" + evtName);

		try {
			// ..handle it !
			var eventResponse = eventHnd.apply(this, [objectId, event]);

			if (!eventResponse) { // undefined response !
				return [violet.objects.createMessage(objectId)
					.addTextToSpeech("No response defined for event : " + evtName + ". Application is stopping now !")
					.stopInteractive() // the platform will have to propagate this to all involved objects
					.serialize()];

			} else if (eventResponse.constructor === Array) {
				// the event handler returned an array of messages : serialize them
				return eventResponse.filteredCopy(violet.filters.serializeMessages);

			} else {
				// the event handler returned a single message
				return (eventResponse.serialize) ? [eventResponse.serialize()] : [eventResponse];
			}

		} catch (errorMsg) {
			debug(errorMsg + " occured in " + this + " when processing event " + evtName + " for target " + objectId);
			return [violet.objects.createMessage(objectId)
				.addTextToSpeech("Handling of event " + evtName + " generated and error : " + errorMsg)
				.stopInteractive() // the platform will have to propagate this to all involved objects
				.serialize()];
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
	if (!this.app) { // this is the trigger event that starts the application !
		this.app = createApplicationInstance();
		this.app.trigger = event.trigger;
	}
	return this.app.processEvent(objectId, event);
};

/**
 *
 * Version for private API (trusted native applications)
 */
captureEventContext = function(inApp, inObjectId, inEvent) {
	debug("captureEventContext : " + inApp + ", " + inObjectId + ", " + inEvent);
	return {
		apiKey: (inApp) ? inApp.apiKey : null,
		objectId: inObjectId,
		event: inEvent
	};
};

/**
 * Version for public API
 */
// captureEventContext = function() {
// 	return new Continuation();
// };

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
		"violet.messages" : ["archive", "count", "delete", "get", "markAsRead", "replay", "sendMessage", "sendMusicMessage", "sendTTSMessage", "sendVocalMessage"],
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
   * APICall constructor (depending on proxy method violet.__proxy__.makeAPICall)
	 */
	var APICall = function(actionName) {
		return function(app, params) {
			if (arguments.length != 2) throw ("Warning ! call of " + actionName + " for trusted (native) applications requires the 1st parameter to be the caller (application itself) and the 2nd the API call parameters !");
			var resp = globalCtx.violet.__proxy__.makeAPICall(captureEventContext(app), actionName, ((typeof params == 'string') ? {id: params} : params));
			// test if API call returned an error
			if (resp && resp.type && resp.type == "error") throw resp.message;
			return resp;
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
   * create violet.net.httpRequest (depending on proxy method violet.__proxy__.makeHttpCall)
	 */
	_namespace("violet.net", {
		httpRequest: function(url, params, options) {
			return globalCtx.violet.__proxy__.makeHttpCall(captureEventContext(), url, params || {}, options || {});
		},

		/*
     * note : this is the non trusted version for the public API
     * where we build a continuation to find the event context and ztamp owner..
     * native applications will have instead a trusted version with one more parameter
     * that ask for the event context
		   sendMail: function(to, cc, subject, text) {
		   	return globalCtx.violet.__proxy__.sendMail(captureEventContext(), to, cc, subject, text);
		   } */

		/**
		 * note : this is the trusted version where we directly ask the application
		 * to provide the object id and event to determine the sender of the mail
		 */
		sendMail : function(app, objectId, event, to, cc, subject, text) {
			return globalCtx.violet.__proxy__.sendMail(captureEventContext(app, objectId, event), to, cc, subject, text);
		}
	});

	/*
   * register the local function _namespace as violet.util.namespace
	 */
	_namespace("violet.util").namespace = _namespace;
})(this);

//----------------------------------------------------------------------------80

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

	/**
   * Add the content of an allready built sequence to the existing one
	 */
	addSequence: function(seqParts) {
		if (seqParts && seqParts.length) { // must be a list
			for (var i = 0, len = seqParts.length; i < len; i++) {
				var seqPart = seqParts[i];
				if (seqPart.type && ["expression", "directive", "annotation"].contains(seqPart.type)) {
					debug("Adding " + seqPart + " to sequence list");
					this.seq.push(seqPart);

				} else {
					throw ("message.addSequence() : This element doesn't seem to be a valid sequence part : " + seqPart);
				}
			}
		}
		return this;
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
	},

	quality: function(factor) {
		if (factor == undefined || factor < 0 || factor > 1) throw "The quality factor for an expression must be a decimal value between 0 and 1.";
		var lastSeq = this.seq.last();
		if (lastSeq.type != "expression") throw "A quality factor cannot be applied to a " + lastSeq.type;
		if (lastSeq.alt) lastSeq = lastSeq.alt.last(); // take the last alternative
		lastSeq.quality = factor;
		return this;
	},

	forNabaztag: function() {
		var lastSeq = this.seq.last();
		if (lastSeq && ["net.violet.choreography"].contains(lastSeq.modality)) {
			lastSeq.modality += ".nabaztag";
		}
		return this;
	}

};

violet.util.namespace("violet.maps", {
	// Map the first part of a given content type to the correct violet modality of expression
	ModalityForContentTypes : {
		"image": "net.violet.picture",
		"images": "net.violet.picture", // mime type erroné retourné par l'API violet.libraries.get
		"audio": "net.violet.sound",
		"video": "net.violet.video"
	}
});

(function() {

	/**
	 * This special set of methods add a modality to the message sequence
	 * each method doXXX will have a variation of type alternativelyDoXXX
	 */
	var modalityMethods = {
		addTextToSpeech: function(text, lang, voice, gender) {

			if (!lang) lang="en";
			if (!gender || !["M", "F"].contains(gender)) gender = "F";

			var modality = ((lang) ? "net.violet.tts." + lang : "net.violet.tts");
			// fix : we have to remove CR, LF from spoken text (pb with TTS generation)..
			var ttsMap = {type: "expression", modality: modality, text: text.replace(/[\n|\r]+/g, " ")};

			if (voice)  ttsMap.voice = voice;
			if (lang)   ttsMap.lang = lang;
			if (gender) ttsMap.gender = gender;

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
			return this.playAudioStream("broadcast/broad/config/signature/" + sndName + ".mp3", options);
		},

		/**
     * 2 usages :
		 *	1 array argument of urls : playSlideShow([img1, img2, ..])
		 *	each argument is an url : playSlideShow(img1, img2, ..)
		 */
		playSlideShow: function(inUrls) {
			var urls = arguments;
			if ((arguments.length == 1) && (arguments[0].constructor == Array)) {
				urls = arguments[0];
			}
			this.seq.push({type: "expression", modality: "net.violet.slideshow", urls: urls});
			return this;
		},

		launchScreenSaver: function() {
			this.seq.push({type: "expression", modality: "net.violet.screensaver"});
			return this;
		},

		lockComputer: function() {
			this.seq.push({type: "expression", modality: "net.violet.lockcomputer"});
			return this;
		},

		launchFile: function(inUrl) {
			var launchMap = {type: "expression", modality: "net.violet.launch.file"};
			launchMap.url = (inUrl) ? inUrl : null; // first launch may not have defined the parameter
 			this.seq.push(launchMap);
			return this;
		},

		launchApplication: function(inMimeType) {
 			this.seq.push({type: "expression", modality: "net.violet.launch.app", app_mime_type: inMimeType});
			return this;
		},

		/**
		 * Launch an OS specific script file
		 */
		launchScript: function(inScr, inOSName) {
			var modality = ["apple", "windows"].contains(inOSName) ? "net.violet.osscript." + inOSName + "script" : "net.violet.osscript";
			var launchMap = {type: "expression", modality: modality};
			launchMap.script = (inScr) ? inScr : "";
 			this.seq.push(launchMap);
			return this;
		},

		addPalette: function(paletteName) {
			this.seq.push({type: "expression", modality: "net.violet.palette", name: paletteName});
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

		displayImage: function(url) {
			this.seq.push({type: "expression", modality: "net.violet.picture", url: url});
			return this;
		},

		playMovie: function(url) {
			this.seq.push({type: "expression", modality: "net.violet.video", url: url});
			return this;
		},

		sendMedia: function(url, mimeType) {
			var contentType = (mimeType.contains("/")) ? mimeType.split("/")[0] : mimeType;
			var modality = violet.maps.ModalityForContentTypes[contentType];

			if (modality) { // mapping exists
				var expr = {type: "expression", modality: modality, url: url};
				if ((contentType == "audio") || (contentType == "video")) {
					expr.streaming = true;
				}
				this.seq.push(expr);
				return this;

			} else { // find the adequate application to open this file on the computer
				return this.launchFile(url);
			}
		},

		wait: function(time) {
			this.seq.push({type: "expression", modality: "net.violet.wait", time_ms: time});
			return this;
		},

		browsePage: function(inUrl, inParamsMap) {
			var i = 0, fullUrl = inUrl;
			if (inParamsMap) {
				if (inUrl.indexOf("?") == -1) fullUrl += "?";
				for (var paramName in inParamsMap) {
					fullUrl += ((i > 0) ? "&" : "") + encodeURIComponent(paramName) + "=" + encodeURIComponent(inParamsMap[paramName]);
					i = i + 1;
				}
			}
			debug("browsePage:" + fullUrl);
			this.seq.push({type: "expression", modality: "net.violet.browser", url: fullUrl});
			return this;
		}
	};

	// add the modality methods to the message prototype
	var msgProto = violet.objects.message.prototype;
	Object.extend(msgProto, modalityMethods);


	msgProto._makeLastSeqAlt = function() {
		if (this.seq.length < 2) return; // we need at least two existing sequences to make one
		// the last sequence added is in fact an alternative to the previous one
		var newAlt  = this.seq.pop();
		var lastSeq = this.seq.last();
		if (lastSeq.alt) {
			lastSeq.alt.push(newAlt);
		} else {
			lastSeq.alt = [newAlt];
		}
	};
	var makeAlternativeMethodFrom = function(methodName) {
		return function() {
			this[methodName].apply(this, arguments);
			this._makeLastSeqAlt();
			return this;
		}
	};

	// add the alternative modality methods to the message prototype
	for (methodName in modalityMethods) {
		msgProto["alternatively" + methodName.capitalize()] = makeAlternativeMethodFrom(methodName);
	}
})();

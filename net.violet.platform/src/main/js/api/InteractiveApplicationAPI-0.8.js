/**
 * Violet JavaScript library for Interactive Applications
 * JS API LIB version 0.8
 * Implements
 * - Public API calls
 * - Event handlers
 * - Response builder for returning sequences
 * - Settings Helper
 * - all modality methods have their alternative
 * - 2 modes for templates replacements variables
 * - added functions on Number.prototype
 * - main entry point become processEvent(event)
 * - Event object helper
 *
 * Added in this release :
 * - Web Radio modality
 * - Support of the new resources API functions
 *
 * Note : for internal (trusted applications only)
 */
toString = function() {
	return "JS API LIB version 0.8";
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
	if (!src) return dest;
	if (!dest) dest = {};
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

Object.isArray = function(obj) {
	return Object.prototype.toString.call(obj) === '[object Array]';
};

Object.isFunction = function(obj) {
	return Object.prototype.toString.call(obj) === '[object Function]';
};

Object.isDate = function(obj) {
	return (obj && obj.getUTCDay);
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
	startsWith: function(c) {
		return (this.indexOf(c) == 0);
	},

	endsWith: function(c) {
		return (this.lastIndexOf(c) == (this.length - 1));
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
	replaceTemplateVariables : function(replacements, filter) {

		if (!replacements) return this;
		if (!filter) {
			filter = function(input) {
				return input;
			};
		}

		if (replacements.length) { // an array with at least 1 element
			return this.replace(/\$\{([0-9]+)\}/g, function(found, idx) {
				return filter(replacements[idx-1]);
			});

		} else { // a key/value map
			return this.replace(/\$\{([\w|\.|\[|\]]+)(?:\:\:(.+))?\}/g, function(found, expr, jsExpr) { // found is the whole matched expression surrounded by $() while expr is the extracted inner value
				var newValue = filter(Object.extractValue(replacements, expr));
				if (jsExpr) {
					newValue = eval("Number(" + newValue + ")." + jsExpr);
				}
				return newValue;
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
	pickRandom: function() {
		return this[Math.floor(Math.random()*this.length)];
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

/*----------------------------------------------------------------------------*/

DateHelper = {

	// Some common format strings
	masks : {
		"default":      "ddd mmm dd yyyy HH:MM:ss",
		shortDate:      "m/d/yy",
		mediumDate:     "mmm d, yyyy",
		longDate:       "mmmm d, yyyy",
		fullDate:       "dddd, mmmm d, yyyy",
		shortTime:      "h:MM TT",
		mediumTime:     "h:MM:ss TT",
		longTime:       "h:MM:ss TT Z",
		isoDate:        "yyyy-mm-dd",
		isoTime:        "HH:MM:ss",
		isoDateTime:    "yyyy-mm-dd'T'HH:MM:ss",
		isoUtcDateTime: "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
	},

	// Internationalization strings
	dayNames: {
		"fr": [
			"Di", "Lu", "Ma", "Me", "Je", "Ve", "Sa",
			"Dimanche", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"
		],
		"en": [
			"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
			"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
		],
		"it": [
			"Dom", "Lun", "Mar", "Mer", "Gio", "Ven", "Sab",
			"Domenica", "Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato"
		],
		"es": [
			"Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb",
			"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"
		],
		"pt": [
			"Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb",
			"Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado"
		],
		"de": [
			"So", "Mo", "Di", "Mi", "Do", "Fr", "Sa",
			"Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag"
		]
	},
	monthNames: {
		"fr": [
			"Jan", "Fev", "Mar", "Avr", "Mai", "Jun", "Jui", "Aoû", "Sep", "Oct", "Nov", "Déc",
			"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
		],
		"en": [
			"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
			"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
		],
		"it": [
			"Gen", "Feb", "Mar", "Apr", "Mag", "Giu", "Lug", "Ago", "Set", "Ott", "Nov", "Dic",
			"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"
		],
		"es": [
			"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic",
			"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
		],
		"pt": [
			"Jen", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez",
			"Jeneiro", "Fevreiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
		],
		"de": [
			"Jan", "Feb", "Mär", "Apr", "Mai", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dez",
			"Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"
		]
	},

	ISO8601RegEx: new RegExp("([0-9]{4})(-([0-9]{2})(-([0-9]{2})" +
				"([T ]([0-9]{2}):([0-9]{2})(:([0-9]{2})(\.([0-9]+))?)?" +
				"(Z|(([-+])([0-9]{2}):([0-9]{2})))?)?)?)?"),

	durationsOf: { // (in ms)
		seconds: 1000,
		minutes: 60000,
		hours: 3600000,
		days: 86400000,
		weeks: 604800000
	},

	/**
	 * Parse a date in ISO-8601 format (http://www.w3.org/TR/NOTE-datetime)
	 */
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

		// fix a serious problem when returned date doesn't herit of the Date prototype (??!)
		if (!date.getWeek) date.getWeek = Date.prototype.getWeek;

		return date;
	},

	/**
	 * Return TRUE if pString is a date according to pFormat (eg. "DD/MM/YYYY", "YYYYMMDD", ..)
	 */
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
		if (isNaN(day) || day > DateHelper.countDays(year,month) || day < 1) return false;

		return new Date(year, month, day);
	},

	_zeropad: function(n){
		return ((n < 10) ? "0" : "") + n;
	},

	countDays: function(year, month) {
		/* see http://jszen.blogspot.com/2007/07/some-target-contributions-to-calendar.html
		var d = new Date(year, month + 1, 0); // nMonth is 0 thru 11
		return (d.getDate());*/

		if (month == 4 || month == 6 || month == 9 || month == 11) return 30;
		if (month == 2) {
			if (year%4 == 0 && year%100 != 0 || year%400 == 0) return 29;
			else return 28;
		}
		return 31;
	},

 /**
	* Format a date into ISO 8601 conformant format
	* Accepted values for the format are numbers between 1-6 :
	*   1 : Year > YYYY (eg 1997)
	*   2 : Year and month > YYYY-MM (eg 1997-07)
	*   3 : Complete date  > YYYY-MM-DD (eg 1997-07-16)
	*   4 : Complete date plus hours and minutes > YYYY-MM-DDThh:mmTZD (eg 1997-07-16T19:20+01:00)
	*   5 : Complete date plus hours, minutes and seconds > YYYY-MM-DDThh:mm:ssTZD (eg 1997-07-16T19:20:30+01:00)
	*   6 : Complete date plus hours, minutes, seconds and a decimal fraction of a second > YYYY-MM-DDThh:mm:ss.sTZD (eg 1997-07-16T19:20:30.45+01:00)
	* offset is the time zone offset : Z, +01:00
	*/
	toISOFormat: function (date, fmt, offset) {
		debug("formatting date " + date + " in " + fmt + " with tz : " + offset);

		if (!fmt) fmt = 5;

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
	}

};

/**
 * Date Format 1.2.2
 * (c) 2007-2008 Steven Levithan <stevenlevithan.com>
 * MIT license
 * Includes enhancements by Scott Trenda <scott.trenda.net> and Kris Kowal <cixar.com/~kris.kowal/>
 *
 * Accepts a date, a mask, or a date and a mask.
 * Returns a formatted version of the given date.
 * The date defaults to the current date/time.
 * The mask defaults to dateFormat.masks.default.
 */
DateHelper.format = function () {

	// Regexes and supporting functions are cached through closure
	var	token = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g,
		timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g,
		timezoneClip = /[^-+\dA-Z]/g,
		pad = function (val, len) {
			val = String(val);
			len = len || 2;
			while (val.length < len) val = "0" + val;
			return val;
		};

	return function (date, mask, lang, utc) {

		// Passing date through Date applies Date.parse, if necessary
		debug("DateHelper.format(" + arguments + ")");
		if (date && !date.getUTCDate) date = new Date(date);
		if (isNaN(date)) throw new SyntaxError("invalid date");

		mask = String(DateHelper.masks[mask] || mask || DateHelper.masks["default"]);
		lang = lang || "en";

		// Allow setting the utc argument via the mask
		if (mask.slice(0, 4) == "UTC:") {
			mask = mask.slice(4);
			utc = true;
		}

		var	_ = utc ? "getUTC" : "get",
			d = date[_ + "Date"](),
			D = date[_ + "Day"](),
			m = date[_ + "Month"](),
			y = date[_ + "FullYear"](),
			H = date[_ + "Hours"](),
			M = date[_ + "Minutes"](),
			s = date[_ + "Seconds"](),
			L = date[_ + "Milliseconds"](),
			o = utc ? 0 : date.getTimezoneOffset(),
			flags = {
				d:    d,
				dd:   pad(d),
				ddd:  DateHelper.dayNames[lang][D],
				dddd: DateHelper.dayNames[lang][D + 7],
				m:    m + 1,
				mm:   pad(m + 1),
				mmm:  DateHelper.monthNames[lang][m],
				mmmm: DateHelper.monthNames[lang][m + 12],
				yy:   String(y).slice(2),
				yyyy: y,
				h:    H % 12 || 12,
				hh:   pad(H % 12 || 12),
				H:    H,
				HH:   pad(H),
				M:    M,
				MM:   pad(M),
				s:    s,
				ss:   pad(s),
				l:    pad(L, 3),
				L:    pad(L > 99 ? Math.round(L / 10) : L),
				t:    H < 12 ? "a"  : "p",
				tt:   H < 12 ? "am" : "pm",
				T:    H < 12 ? "A"  : "P",
				TT:   H < 12 ? "AM" : "PM",
				Z:    utc ? "UTC" : (date.timezone ? date.timezone : (String(date).match(timezone) || [""]).pop().replace(timezoneClip, "")),
				o:    (o > 0 ? "-" : "+") + pad(Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o) % 60, 4),
				S:    ["th", "st", "nd", "rd"][d % 10 > 3 ? 0 : (d % 100 - d % 10 != 10) * d % 10]
			};

		return mask.replace(token, function ($0) {
			return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1);
		});
	};
}();


now = function() {
	return new Date();
};

Date.fromISO = DateHelper.parseISO;

/**
* Append a duration to this date
* @param int inQty number of units to add
* @param string inUnit (seconds, minutes, days, weeks)
* @return Date
*/
Date.prototype.add = function(inQty, inUnit) {
	var unit = inUnit.toLowercase();
	if (!unit.endsWith("s")) unit += "s";
	var delay = DateHelper.durationsOf[unit] || 0;
	return new Date(this.getTime() + (inQty || 0) * delay);
};
Date.prototype.minus = function(inQty, inUnit) {
	return this.add(-1*inQty, inUnit);
};
// For convenience...
Date.prototype.format = function(mask, lang, utc) {
	return DateHelper.format(this, mask, lang, utc);
};
/**
 * Returns the week number for this date.
 * Week number one is the week that contains the first of january
 * @param int dayOffset defines the first day of week (0 : Sunday, 1 : Monday, ..) default 1
 * @return int
 */
Date.prototype.getWeek = function(dayOffset) {
	var jan1st = new Date(this.getFullYear(), 0, 1);
	var offset = (typeof(dayOffset) == 'number' ? dayOffset : 1); // monday default
	var elapsedDays = ((this - jan1st) / 86400000); // elapsed days from the 1st of january

	var elapsedDaysFrom1stDayOfYear = elapsedDays + jan1st.getDay() - offset;
	return Math.ceil((elapsedDaysFrom1stDayOfYear + 0.1)/ 7); // + 0.1 is because we want midnight (0:00) to be allready the next day !
};

/**
 * Constructor for a date in a different time zone
 * 2 signatures are accepted for the call
		new LocaleDate(timezone, [year, [month, [day, [hour, [minutes, [seconds]]]]]])
		new LocaleDate(timezone, [date])
 */
LocaleDate = function() {
	// build the array map of arguments to calendar fields and store it in a closure
	var calendarField = [-1, java.util.Calendar.YEAR, java.util.Calendar.MONTH, java.util.Calendar.DAY_OF_MONTH, java.util.Calendar.HOUR_OF_DAY, java.util.Calendar.MINUTE, java.util.Calendar.SECOND];
	return function() {
		var args = arguments, len = args.length;
		if (!len) throw "Missing arguments! constructor signature is : new LocaleDate(timezone, [year, [month, [day, [hour, [minutes, [seconds]]]]]])";
		var timezone = this.timezone = args[0];

		var cal = java.util.Calendar.getInstance(java.util.TimeZone.getTimeZone(timezone));
		// this.timezone = cal.getTimeZone().getDisplayName(false, 0);

		if (len == 2 && args[1].getUTCDate) {
			// constructor call is of the form : new LocaleDate(timezone, date)
			cal.setTimeInMillis(args[1].getTime());

		} else { // map each of the remaining args to a Calendar field
			var i = 1;
			//if (args[2]) args[2] = args[2]-1; // this is the month value, and we want it to be 1-based, but calendar field is 0-based (!)
			while (args[i] && calendarField[i]) {
				cal.set(calendarField[i], args[i++]);
			}
		}
		this.utc = new Date(cal.getTimeInMillis());
		this.calendar = cal;
	};
}(); // build the closure and return the inner function

/**
 * Implement all the Date methods so that we can use LocaleDate everywhere we used a Date
 */
LocaleDate.prototype = {

	getDate:            function() {return this.calendar.get(java.util.Calendar.DAY_OF_MONTH);},
	getDay:             function() {return this.calendar.get(java.util.Calendar.DAY_OF_WEEK)-1;},
	getFullYear:        function() {return this.calendar.get(java.util.Calendar.YEAR);},
	getHours:           function() {return this.calendar.get(java.util.Calendar.HOUR_OF_DAY);},
	getMilliseconds:    function() {return 0;},
	getMinutes:         function() {return this.calendar.get(java.util.Calendar.MINUTE);},
	getMonth:           function() {return this.calendar.get(java.util.Calendar.MONTH);},
	getSeconds:         function() {return this.calendar.get(java.util.Calendar.SECOND);},
	getTime:            function() {return this.calendar.getTimeInMillis();},
	getTimezoneOffset:  function() {return (this.calendar.get(java.util.Calendar.ZONE_OFFSET) + this.calendar.get(java.util.Calendar.DST_OFFSET)) / 60000;},
	getYear:            this.getFullYear, //function() {return this.calendar.get(java.util.Calendar.YEAR);},
	getUTCDate:         function() {return this.utc.getUTCDate();},
	getUTCDay:          function() {return this.utc.getUTCDay();},
	getUTCFullYear:     function() {return this.utc.getUTCFullYear();},
	getUTCHours:        function() {return this.utc.getUTCHours();},
	getUTCMilliseconds: function() {return 0;},
	getUTCMinutes:      function() {return this.utc.getUTCMinutes();},
	getUTCMonth:        function() {return this.utc.getUTCMonth();},
	getUTCSeconds:      function() {return this.utc.getUTCSeconds();},
	toString:           function() {return DateHelper.format(this, "ddd mmm dd yyyy HH:MM:ss ", "en") + this.timezone;},
	toDateString:       function() {return DateHelper.format(this, "ddd mmm dd yyyy", "en");},
	toTimeString:       function() {return DateHelper.format(this, "HH:MM:ss " + this.timezone, "en");},
	valueOf:            function() {return this.calendar.getTimeInMillis();},
	setTime: function(timeValue) {
		this.calendar.setTimeInMillis(timeValue);
		this.utc = new Date(timeValue);
		return this;
	},
	toJSONString: function() {return JSONHelper.serialize(this.utc);}

};
LocaleDate.prototype.getWeek = Date.prototype.getWeek;
LocaleDate.prototype.format  = Date.prototype.format;


/*----------------------------------------------------------------------------*/
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
		// FIXME go into endless recursive loop sometimes
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
	processEvent: function(event) {

		// log the event and application context
		var ctx = event.settings || {};
		var evtName  = "on" + event.name + "Event";
		var object = (event.target || event.object || event.trigger.target || event.trigger.object);
		var objectId = object.id;
		// build the log
		debug(">> PROCESSING OF " + this + "." + evtName + "(" + ctx + ") for " + object.name + " (" + object.serial + ")");

		// get the correct event handler..
		var eventHnd = this.getEventHandler(evtName);

		try {
			// ..handle it !
			var eventResponse = eventHnd.apply(this, [objectId, event]);

			if (!eventResponse) { // undefined response !
				return [violet.objects.createMessage(objectId)
					.addTextToSpeech("No response defined for event : " + evtName + ". Application is stopping now !", "en", "US-Liberty")
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
			debug(this + "." + evtName + "() FAILED WITH ERROR : " + errorMsg);
			return [violet.objects.createMessage(objectId)
				.showBubble("Application Error", this + "\nencountered an error:\n" + errorMsg)
				.alternativelyAddTextToSpeech(errorMsg, "en", "US-Liberty")
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

/*
processEvent = function(objectId, event) {
	if (!this.app) { // this is the trigger event that starts the application !
		this.app = createApplicationInstance();
		this.app.trigger = event.trigger;
	}
	return this.app.processEvent(objectId, event);
};*/

/**
 *
 * Version for private API (trusted native applications)
 */
captureEventContext = function(inApp, inObjectId, inEvent) {
	// debug("captureEventContext : " + inApp + ", " + inObjectId + ", " + inEvent);
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

	/**
	 * Constructor for Settings helper
	 */
	Settings = function(map) {
		return (map && map.settings) ? Object.extend(this, map.settings) : Object.extend(this, map); // copy the map content
	};

	Object.extend(Settings.prototype, {
		_buildOption: function(inOptions) {
			if (inOptions == undefined) return {required: false};
			var optionType = $typeOf(inOptions);
			if (optionType == 'boolean') return {required: inOptions};
			if (["string", "date", "number"].contains(optionType)) return {defaultValue: inOptions};
			return inOptions;
		},
		getString: function(key, inOptions) {
			var options = this._buildOption(inOptions);
			var s = this[key];
			if (!s) {
				if (options.required) throw "String parameter " + key + " is missing !";
				return ($typeOf(options.defaultValue) == 'string') ? options.defaultValue : undefined;
			}
			return String(s);
		},
		getDate: function(key, inOptions) {
			var options = this._buildOption(inOptions);
			var d = this[key];
			if (!d) {
				if (options.required) throw "Date parameter " + key + " is missing !";
				return ($typeOf(options.defaultValue) == 'date') ? options.defaultValue : undefined;
			}
			var type = $typeOf(d);
			if (type == 'date') return d;
			if (type == 'string') return DateHelper.parseISO(d);
			if (d.utc && d.timezone) return DateHelper.parseISO(d.utc); // this is a LocaleDate with timezone
			throw "Setting value under key '" + key + "' (" + d + " ) is not a Date ! ";
		},
		getLocaleDate: function(key, timezone, inOptions) {
			debug("getLocaleDate(" + key + "," + timezone + "," + inOptions + ")");
			if (arguments.length < 2) throw "Missing arguments for settings.getLocaleDate(key, timezone[, options])";
			if ($typeOf(arguments[0]) != 'string' || (arguments[1] && $typeOf(arguments[1]) != 'string')) throw "Wrong arguments types for settings.getLocaleDate(key, timezone[, options])";
			var options = this._buildOption(inOptions);
			var d = this[key];
			if (!d) {
				if (options.required) throw "Date parameter " + key + " is missing !";
				return ($typeOf(options.defaultValue) == 'date') ? options.defaultValue : undefined;
			}
			if (d.utc && d.timezone) {
				return new LocaleDate(timezone || d.timezone, d.utc); // we retrieve this LocaleDate in the new timezone or in its original timezone
			}
			var d = this.getDate(key, inOptions);
			return new LocaleDate(timezone, d);
		},
		getNum: function(key, inOptions) {
			var options = this._buildOption(inOptions);
			debug("getNum('" + key + "'," + options);
			var n = this[key];
			if (n == null || n == undefined) { // we cannot do !n here because 0 is a valid number
				if (options.required) throw "Numeric parameter " + key + " is missing !";
				return ($typeOf(options.defaultValue) == 'number') ? options.defaultValue : undefined;
			}
			return Number(n);
		},
		getBoolean: function(key, defaultValue) {
			var ysn = this[key];
			if (ysn == null || ysn == undefined) return (defaultValue !== undefined) ? defaultValue : null;
			return (String(ysn).toLowerCase() == "true");
		}

	});

	Event = function(inEvt) {
		Object.extend(this, inEvt);
	};

	/**
   * Get the standard replacement variables from this trigger event
	 */
	Event.getVariables = function(inApp, inEvt) {
		if (!inEvt.trigger) return {};
		var targetObject = inEvt.trigger.target;
		var readerObject = inEvt.trigger.reader;
		// look for the time zone of the target object
		// var offset = violet.timezones.getCurrentOffset(inApp, targetObject.tz);
		// var now = new Date(inEvt.trigger.when + offset*60000);
		var now = new LocaleDate(targetObject.tz);
		var df = DateHelper.format(now, "h,hh,H,HH,M,MM,s,ss,TT").split(",");
		// build the container of replacement variables
		var vars = {
			object: targetObject,
			ztamp: inEvt.trigger.object,
			hours:   df[0], HOURS:   df[1],
			hours24: df[2], HOURS24: df[3],
			minutes: df[4], MINUTES: df[5],
			seconds: df[6], SECONDS: df[7],
			AMPM: df[8]
		};
		if (readerObject) vars.reader = readerObject;
		return vars;
	};

	Event.prototype.getVariables = function(inApp) {
		return Event.getVariables(inApp, this);
	};

//----------------------------------------------------------------------------80
/*
 * Create the public API calls in the global namespace
 */
(function(globalCtx) {

	var apiMethodNames = {
		"violet.applications" : ["countByCategory", "countByTag", "getByCategory", "getByTag", "getInfo", "getPackage", "getProfile", "getSchedulingMask", "getTagCloudForLang", "fetchResource", "uploadResource", "createResource", "deleteResource", "generateAlternateResource", "loadResources", "updateDico", "uploadDico", "loadDico", "updateData", "loadData"],
		"violet.blacklists" : ["add", "get", "remove"],
		"violet.categories" : ["get"],
		"violet.cities" : ["getPeopleCities"],
		"violet.contacts" : ["acceptRequest", "add", "count", "countReceivedRequests", "countSentRequests", "delete", "get", "getReceivedContactRequests", "getSentContactRequests", "rejectRequest", "retractRequest"],
		"violet.countries" : ["getCodes"],
		"violet.events" : ["send"],
		"violet.hints" : ["findByContext"],
		"violet.journal" : ["addEntries", "get", "removeEntries"],
		"violet.libraries" : ["count", "delete", "get", "getByCategory", "getCategories", "getForSignature", "getProfile", "getRecent", "getShuffle", "put", "setProfile"],
		"violet.languages" : ["getASRLanguages", "getObjectLanguages", "getSiteLanguages", "getTTSLanguages"],
		"violet.locker" : ["store", "retrieve"],
		"violet.messages" : ["archive", "count", "delete", "get", "markAsRead", "replay", "sendMessage", "sendMusicMessage", "sendTTSMessage", "sendVocalMessage"],
		"violet.objects" : ["create", "findByName", "getInfo", "getNewComers", "getPreferences", "getProfile", "getStatus", "search", "setName", "setPreferences", "setProfile", "setStatus"],
		"violet.people"  : ["checkPassword", "create", "exists", "findByEmailAddress", "getInfo", "getObjects", "getPreferences", "getProfile", "search", "setEmail", "setPassword", "setPreferences", "setProfile", "tellMyFriends"],
		"violet.secrets" : ["createTimestamp", "checkTimestamp"],
		"violet.sessions" : ["create", "getUser", "update"],
		"violet.site" : ["getLocalizedString"],
		"violet.subscriptions" : ["create", "delete", "get", "setScheduling", "setSettings"],
		"violet.timezones" : ["get", "getByCountry", "getByOffset", "getCurrentOffset"],
		"violet.voices" : ["getAll", "getByLang", "synthetize"]
	};

	var _namespace = function(namespace, newContent) {
		var container = globalCtx;
		var nameParts = namespace.split(".");

		for (var i = 0, len = nameParts.length - 1; i <= len; i++) {
			var attrName = nameParts[i];
			if (!container[attrName]) container[attrName] = {}; // create attribute in container
			container = container[attrName]; // enter into it
		}

		return Object.extend(container, newContent);
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
	/**
	 * Wrap some API method to check input params, or do somethog after..
	 */


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
		this.dest = (to) ? ((to.constructor === Array) ? to : [to])
										 : [];
		this.seq = [];
	},

	createMessage: function(to) {
		return new violet.objects.message(to);
	}
});

/*
 * Define the message methods
 */
violet.objects.message.prototype = {

	/**
	 * Set the emitter
	 */
	from: function(drWho) {
		this._from = drWho;
		return this;
	},

	/**
	 * Set the title
	 */
	title: function(inTitle) {
		this._title = inTitle;
		return this;
	},

	serialize: function() {
		var finalMsg = {to: this.dest, sequence: this.seq};
		if (this._from)  finalMsg.from  = this._from;
		if (this._title) finalMsg.title = this._title;
		if (this._application) finalMsg.application = this._application;
		return finalMsg;
	},

	send: function(appSender) {
		if (!appSender.apiKey) throw "The method message.send() must be called with the application as param (use 'this' in the application context)";
		this._application = appSender.apiKey;
		violet.messages.sendMessage(appSender, {message: this.serialize()});
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
// Default voices for tts
violet.util.namespace("violet.tts", {
	DEFAULT_VOICES : {
		"fr": "FR-Anastasie",
		"en": "US-Liberty",
		"ja": "JP-Tamura"
	}
});

(function() {

	/**
	 * This special set of methods add a modality to the message sequence
	 * each method doXXX will have a variation of type alternativelyDoXXX
	 */
	var messageMethods = {
		/**
     * Play an unknown modality of expression
		 */
		playModality: function(name, params) {
			var map = {type: "expression", modality: name};
			this.seq.push(Object.extend(map, params));
			return this;
		},

		addTextToSpeech: function(text, lang, voice, gender) {

			if (!text || !text.length) return this; // mute TTS ? drop it !
			if (!lang) lang = (voice) ? voice.substr(0,2).toLowerCase() : "en";
			if (!voice && violet.tts.DEFAULT_VOICES[lang]) voice = violet.tts.DEFAULT_VOICES[lang];
			if (gender && !["M", "F"].contains(gender)) gender = "F";

			// fix : we have to remove CR, LF from spoken text (pb with TTS generation)..
			var ttsMap = {type: "expression", modality: "net.violet.tts", text: text.replace(/[\n|\r|\t|\"]+/g, " ")};

			if (voice)  ttsMap.voice  = voice;
			if (lang)   ttsMap.lang   = lang;
			if (gender) ttsMap.gender = gender;

			this.seq.push(ttsMap);
			return this;
		},

		playAudioStream: function(inUrl, inOptions) {
			var sndMap = {type: "expression", modality: "net.violet.sound", url: inUrl, streaming: true, withEar: true};
			this.seq.push(Object.extend(sndMap, inOptions));
			return this;
		},

		playLibSound: function(sndName, inOptions) {
			var options = Object.extend({streaming: false}, inOptions);
			return this.playAudioStream("broadcast/broad/config/signature/" + sndName + ".mp3", options);
		},

		/**
		 * 2 usages :
		 *	1 array argument of urls : playSlideShow([img1, img2, ..])
		 *	each argument is an url : playSlideShow(img1, img2, ..)
		 */
		playSlideShow: function(inUrls) {
			var urls = arguments;
			if ((arguments.length == 1) && Object.isArray(arguments[0])) {
				urls = arguments[0];
			}
			this.seq.push({type: "expression", modality: "net.violet.slideshow", urls: urls});
			return this;
		},

		launchScreenSaver: function() {
			this.seq.push({type: "expression", modality: "net.violet.screensaver"});
			return this;
		},

		launchFile: function(inUrl) {
			var launchMap = {type: "expression", modality: "net.violet.launch.file"};
			launchMap.url = (inUrl) ? inUrl : null; // first launch may not have defined the parameter
			this.seq.push(launchMap);
			return this;
		},

		/**
     * Launch an application from a command line with optional arguments
     * Additional options can be provided :
	   *  - on_success: message to display on success
     *  - on_failure: message to display on failure
		 */
		launchApplication: function(commandLine, options) {
			// parse the command line to find the array of parameters
			var commandParts = commandLine.split(" ");
			// reglue together arguments that were between quotes(")
			var args = [];
			for (var i = 0, len = commandParts.length; i < len; i++) {
				if (commandParts[i].startsWith('"')) {
					// reglue command parts that were between quotes
					var arg = commandParts[i].substr(1); // get rid of the leading quote
					while (++i < len) {
						arg += (" " + commandParts[i]);
						if (commandParts[i].endsWith('"')) {
							arg = arg.replace(/\"$/, ""); // get rid of the last quote
							break;
						}
					}
					args.push(arg);
				} else {
					args.push(commandParts[i]);
				}
			}
			var program = args.shift(); // takes the first argument of the array
			var launchExpr = {type: "expression", modality: "net.violet.launch.app", program: program, program_args: args};
			if (options) Object.extend(launchExpr, options);
			this.seq.push(launchExpr);
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

		lockComputer: function() {
			this.seq.push({type: "expression", modality: "net.violet.lockcomputer"});
			return this;
		},

		/**
		 * Launch an iTunes action
		 * ex: tellITunes("play", {artistname: "bowie", albumname: "live", random: true, volume: 10}) // play each tracks of bowie's live albums in random order
					 tellITunes("pause");
		 */
		tellITunes: function(inAction, inOptions) {
			var modality = "net.violet.itunes." + (["play", "toggle", "next", "previous", "quit"].contains(inAction) ? inAction : "play");
			var iTunesMap = {type: "expression", modality: modality};
			if (inAction == "play") Object.extend(iTunesMap, inOptions); // options apply only on the play modality
			this.seq.push(iTunesMap);
			return this;
		},

		/**
		 * Call s.o. with Skype
		 * Target can be a telephone number or a Skype ID
		 */
		callWithSkype: function(inTarget) {
			this.seq.push({type: "expression", modality: "net.violet.skype.call", target: inTarget});
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

		displayImage: function(inUrl) {
			this.seq.push({type: "expression", modality: "net.violet.picture", url: inUrl});
			return this;
		},

		playMovie: function(inUrl) {
			this.seq.push({type: "expression", modality: "net.violet.video", url: inUrl});
			return this;
		},

		openWebRadio: function(inUrl) {
			this.seq.push({type: "expression", modality: "net.violet.webradio", url: inUrl, background: true});
			return this;
		},

		sendMedia: function(inUrl, mimeType) {
			var contentType = (mimeType.contains("/")) ? mimeType.split("/")[0] : mimeType;
			var modality = violet.maps.ModalityForContentTypes[contentType];

			if (modality) { // mapping exists
				var expr = {type: "expression", modality: modality, url: inUrl};
				if ((contentType == "audio") || (contentType == "video")) {
					expr.streaming = true;
					expr.withEar = true;
				}
				this.seq.push(expr);
				return this;

			} else { // find the adequate application to open this file on the computer
				return this.launchFile(inUrl);
			}
		},

		/**
		 * The bubble modality.
		 * (display a message in a system bubble)
				title          title of the bubble.
				bubbletext     text to display on the bubble.
				width          width of the bubble.
				height         height of the bubble.
				scrollingspeed relative speed. Between 0 (no scrolling) to 10 (max speed).
				pictureurl     url of the picture to display (optional).
				url            url that will be open in a browser when user clicks
											 on the bubble (optional).
				closable       boolean. true if the bubble can be closed or not.
				displaytime    time in ms to display the bubble
		 */
		showBubble: function(inTitle, inText, inOptions) {
			var bubMap = {type: "expression", modality: "net.violet.bubble"};
			bubMap.title = (inTitle || "");
			bubMap.bubbletext = (inText || "");

			var defaultOptions = {
				pictureurl: "http://my.violet.net/images/footer.png",
				displaytime: 10000,
				closable: true
			};
			Object.extend(bubMap, defaultOptions);

			if (inOptions) Object.extend(bubMap, inOptions);

			this.seq.push(bubMap);
			return this;
		},

		/**
		 * Play the application signature consisting of a jingle
		 * and a bubble with the title and the image of the application
		 * or a TTS if the bubble is not supported
		 */
		playSignature: function(inApp, inLang, inIntroMsg) {

			this.showBubble(inApp.getTitle(inLang), inIntroMsg, {pictureurl: inApp.getPicture()}).async()
			return this;
		},

		wait: function(time) {
			this.seq.push({type: "expression", modality: "net.violet.wait", time_ms: time});
			return this;
		},

		/**
		 * Open a browser to display the requested URL
		 * Additional URL params can be passed as a map
		 */
		browsePage: function(inUrl, inParamsMap, inOptions) {
			if (!inUrl) throw "Cannot browse undefined page URL.";
			var finalUrl = (inUrl.startsWith("http") ? inUrl : "http://" + inUrl);
			var i = 0;
			if (inParamsMap) {
				if (inUrl.indexOf("?") == -1) finalUrl += "?";
				for (var paramName in inParamsMap) {
					// stop URL encoding params. mirware will take care of that for us
					// finalUrl += ((i > 0) ? "&" : "") + encodeURIComponent(paramName) + "=" + encodeURIComponent(inParamsMap[paramName]);
					finalUrl += ((i > 0) ? "&" : "") + paramName + "=" + inParamsMap[paramName];
					i = i + 1;
				}
			}
			debug("browsePage:" + finalUrl);
			this.seq.push({type: "expression", modality: "net.violet.browser", url: finalUrl});
			return this;
		},

		/**
		 * Pass in edit mode on a newly registered object
		 */
		doObjectRegistration: function(object) {
			var regMap = {type: "expression", modality: "net.violet.objects.registration"};
			Object.extend(regMap, {serial: object.serial});
			this.seq.push(regMap);
			return this;
		}

	};

	// add the modality methods to the message prototype
	var msgProto = violet.objects.message.prototype;
	Object.extend(msgProto, messageMethods);


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
	for (methodName in messageMethods) {
		msgProto["alternatively" + methodName.capitalize()] = makeAlternativeMethodFrom(methodName);
	}
})();

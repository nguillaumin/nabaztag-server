/**
 * Violet JavaScript library for Interactive Applications
 * API version 0.1
 * Implements Public API calls
 *
 */

// Send debug information to the Java environment or to the firebug console
debug = Packages.net.violet.platform.applets.js.helpers.JSDebug.write;

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

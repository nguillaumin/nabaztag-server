/*
 * Compatibility Plugin for jQuery 1.1 (on top of jQuery 1.2)
 * By John Resig
 * Dual licensed under MIT and GPL.
 *
 * For XPath compatibility with 1.1, you should also include the XPath
 * compatability plugin.
 */

(function(jQuery){

	// You should now use .slice() instead of eq/lt/gt
	// And you should use .filter(":contains(text)") instead of .contains()
	jQuery.each( [ "eq", "lt", "gt", "contains" ], function(i,n){
		jQuery.fn[ n ] = function(num,fn) {
			return this.filter( ":" + n + "(" + num + ")", fn );
		};
	});

	// This is no longer necessary in 1.2
	jQuery.fn.evalScripts = function(){};

	// You should now be using $.ajax() instead
	jQuery.fn.loadIfModified = function() {
		var old = jQuery.ajaxSettings.ifModified;
		jQuery.ajaxSettings.ifModified = true;
	
		var ret = jQuery.fn.load.apply( this, arguments );
	
		jQuery.ajaxSettings.ifModified = old;

		return ret;
	};

	// You should now be using $.ajax() instead
	jQuery.getIfModified = function() {
		var old = jQuery.ajaxSettings.ifModified;
		jQuery.ajaxSettings.ifModified = true;
	
		var ret = jQuery.get.apply( jQuery, arguments );
	
		jQuery.ajaxSettings.ifModified = old;

		return ret;
	};

	jQuery.ajaxTimeout = function( timeout ) {
		jQuery.ajaxSettings.timeout = timeout;
	};

})(jQuery);

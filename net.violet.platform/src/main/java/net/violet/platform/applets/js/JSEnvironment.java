package net.violet.platform.applets.js;

import java.lang.reflect.Member;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.applets.AppResourcesLoader;
import net.violet.platform.util.cache.Cache;
import net.violet.platform.util.cache.GenerationException;
import net.violet.platform.util.cache.ValueGenerator;

import org.apache.log4j.Logger;
import org.mozilla.javascript.ClassShutter;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.FunctionObject;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.UniqueTag;

/**
 * Environnement d'exécution des applications JavaScript
 */
public final class JSEnvironment {

	private static final Logger LOGGER = Logger.getLogger(JSEnvironment.class);

	public static final Cache<String, Script> API_CACHE = new Cache<String, Script>(new ValueGenerator<String, Script>() {

		public Script generateValue(String key) throws GenerationException {
			JSEnvironment.LOGGER.debug("Getting sources for JavaScript API " + key);
			final String src = AppResourcesLoader.LOADER.getApiSource(key);
			try {
				return JsScriptFactory.prepareScript("api-" + key + ".js", src);
			} catch (final APIException e) {
				JSEnvironment.LOGGER.fatal(e, e);
				throw new GenerationException(e.getMessage(), e);
			}

		}
	});

	// Special API versions
	private static final String BLANK_API_VERSION = "0.0";

	// Instance unique.
	private static final JSEnvironment SINGLE_INSTANCE = new JSEnvironment();

	// Context Factory (partagée).
	private final ContextFactory mContextFactory;

	// Shared scopes (prototypes of all new scopes for every API version)
	private final Map<String, ScriptableObject> mSharedScopes;

	/**
	 * Constructeur par défaut.
	 */
	private JSEnvironment() {

		// init our Context factory and make it the global one
		this.mContextFactory = new JsAppContextFactory();
		ContextFactory.initGlobal(this.mContextFactory);

		// initialize the cache of shared scopes (one per API library version)
		this.mSharedScopes = new HashMap<String, ScriptableObject>(6);
	}

	/**
	 * Accesseur sur l'instance unique.
	 */
	public static JSEnvironment getInstance() {
		return JSEnvironment.SINGLE_INSTANCE;
	}

	/**
	 * Get a new Context to execute a Rhino Script call
	 * 
	 * @return
	 */
	public Context getNewContext() {
		return this.mContextFactory.enter();
	}

	/**
	 * the Prototype of every application scope for this version of the api :
	 * Init all the functions and global objects declaration of the library
	 * 
	 * @see http://www.mozilla.org/rhino/scopes.html for a description of the
	 *      shared scopes approach
	 * @return a sealed scope containing all the API objects initialized
	 * @throws APIException
	 */
	private synchronized ScriptableObject getApiLibPrototypeScope(Context inContext, String inApiVersion) {

		// Look up in cache
		ScriptableObject sharedScope = this.mSharedScopes.get(inApiVersion);

		if (sharedScope == null) {

			// builts the standard ECMA global objects : String, Function,
			// Object..
			sharedScope = inContext.initStandardObjects();

			// Init the hidden entry points to the platform :
			// these entry points are wrapped Java method calls accessible in
			// the global scope
			initVioletProxyObject(inContext, sharedScope);

			if (!isBlankApi(inApiVersion)) {
				// Get the script of the API library
				Script apiScript;
				try {
					apiScript = JSEnvironment.API_CACHE.get(inApiVersion);
					// Create the API objects and functions
					apiScript.exec(inContext, sharedScope);
				} catch (final GenerationException e) {
					JSEnvironment.LOGGER.fatal(e, e);
				}
			}

			// this prototype must not be modified now
			sharedScope.sealObject();

			// Keep it in cache as it will be reused as the prototype of every
			// newly created application scope
			this.mSharedScopes.put(inApiVersion, sharedScope);
		}

		return sharedScope;
	}

	/**
	 * Create a new scope for the execution of an application in a given version
	 * of the API the new scope receive for prototype the shared scope prototype
	 * of this API version
	 * 
	 * @see http://www.mozilla.org/rhino/scopes.html for a description of the
	 *      shared scopes approach
	 * @param inApiVersion
	 * @return
	 * @throws APIException
	 */
	public Scriptable getNewApplicationScope(Context inContext, String inApiVersion, boolean isNative) {

		final ScriptableObject sharedScope = getApiLibPrototypeScope(inContext, inApiVersion);

		final Scriptable newScope = inContext.newObject(sharedScope);
		newScope.setPrototype(sharedScope);
		newScope.setParentScope(null);

		// run additional scripts depending of the application profile
		patchApplicationScope(inContext, (ScriptableObject) newScope, isNative);

		return newScope;
	}

	/**
	 * This one has no predefined objects inside, (except the standard ECMA
	 * objects) (used by JsDateHelper to create native JavaScript Date objects)
	 * 
	 * @param inContext
	 * @return the standard ECMA global scope (initStandardObjects and no more)
	 * @throws APIException
	 */
	public Scriptable getBlankScope(Context inContext) {
		// we use the version 0.0 of the API lib which is in fact a non existing
		// version
		return getNewApplicationScope(inContext, "0.0", false);
	}

	/**
	 * Creates the violet.__proxy__ object and its methods in the global API
	 * scope
	 * 
	 * @param inScope
	 */
	private void initVioletProxyObject(Context inContext, ScriptableObject inScope) {

		ScriptableObject violetProxy = null;
		try {
			inContext.evaluateString(inScope, "this.violet = {}; this.violet.__proxy__ = {};", "InitVioletProxy", 1, null);
		} catch (final Exception ignore) {
			ignore.printStackTrace();
		}
		violetProxy = (ScriptableObject) inScope.get("violet", inScope);
		violetProxy = (ScriptableObject) violetProxy.get("__proxy__", violetProxy);

		for (final String methodName : JSProxy.getExposedMethodNames()) {
			final Member javaMethod = JSProxy.getExposedMethod(methodName);
			final FunctionObject jsWrappedMethodCall = new FunctionObject(methodName, javaMethod, inScope);
			violetProxy.defineProperty(methodName, jsWrappedMethodCall, ScriptableObject.DONTENUM);

		}

	}

	/**
	 * Apply some patches to the application scope The patch is executed in the
	 * application scope only and can be used to mask different implementations
	 * in the API scope. (the API scope is the prototype of every application
	 * scope)
	 * 
	 * @param inAppScope
	 */
	private void patchApplicationScope(Context inContext, ScriptableObject inAppScope, boolean isNative) {

		if (isNative) {
			/*
			 * Override some proxy function definitions for trusted (native)
			 * applications allow native apps to provide themself the
			 * informations about their id and the event context
			 */
			// String scriptSrc =
			// getAdditionalScript(AdditionalScript.NATIVE_APPS);
			// if (scriptSrc.length() > 0) {
			// inContext.evaluateString(inAppScope, scriptSrc,
			// AdditionalScript.NATIVE_APPS.fileName(), 1, null);
			// }
		}
	}

	/**
	 * Find the interactive application instance within the application scope.
	 * If the instance doesn't exist, create it and register it in the
	 * application global scope
	 * 
	 * @param inContext
	 * @param inAppScope a scope where the api and applications scripts have
	 *            been executed
	 * @return
	 * @throws APIException
	 */
	public ScriptableObject getAppInstance(Context inContext, Scriptable inAppScope, String inApiVersion) throws APIException {

		if (isBlankApi(inApiVersion) || (inAppScope.get("processEvent", inAppScope) != UniqueTag.NOT_FOUND)) {
			// there is no API ! the application script is complete by itself
			// the processEvent() function is registered as a global function
			// so the application instance is the scope itself !
			return (ScriptableObject) inAppScope;
		}

		/**
		 * In the subsequent versions of the API, the application script
		 * contains just the event handlers and the API contains the Prototype
		 * of the every new InteractiveApplication.., so we must call the global
		 * constructor createInstance() to create the new application instance
		 */
		Object appInstance = inAppScope.get("app", inAppScope);

		if (appInstance == UniqueTag.NOT_FOUND) {
			// first time call : create a new instance in the global
			// applications scope
			// under the name 'app'
			appInstance = createScriptableInstance(inContext, inAppScope);
			inAppScope.put("app", inAppScope, appInstance);
		}

		return (ScriptableObject) appInstance;
	}

	/**
	 * @param inApiVersion
	 * @return
	 */
	private boolean isBlankApi(String inApiVersion) {
		return JSEnvironment.BLANK_API_VERSION.equals(inApiVersion);
	}

	/**
	 * @param inContext
	 * @param inAppScope
	 * @return
	 * @throws APIException
	 */
	private ScriptableObject createScriptableInstance(Context inContext, Scriptable inAppScope) throws APIException {
		Function fnConstr;

		try {
			fnConstr = (Function) inAppScope.getPrototype().get("createApplicationInstance", inAppScope);
			return (ScriptableObject) fnConstr.call(inContext, inAppScope, inAppScope, Context.emptyArgs);

		} catch (final Exception e) {
			JSEnvironment.LOGGER.error("Unable to create new instance of InteractiveApplication using createApplicationInstance", e);
			throw new APIException(APIErrorMessage.JAVASCRIPT_EXECUTION_ERROR, e.getMessage());
		}
	}

	/**
	 * Creation of new Contexts for the execution of our JavaScript application
	 */
	static class JsAppContextFactory extends ContextFactory {

		private static final int OPT_LEVEL = -1;
		private static final ClassShutter mClassShutter = new JsAppClassShutter();

		/**
		 * Associate the security to the newly created Context
		 * 
		 * @see org.mozilla.javascript.ContextFactory#onContextCreated(org.mozilla.javascript.Context)
		 */
		@Override
		protected void onContextCreated(Context cx) {
			super.onContextCreated(cx);
			cx.setLanguageVersion(Context.VERSION_1_5);
			cx.setOptimizationLevel(JsAppContextFactory.OPT_LEVEL);
			cx.setClassShutter(JsAppContextFactory.mClassShutter);

		}

		@Override
		protected boolean hasFeature(Context cx, int featureIndex) {

			switch (featureIndex) {
			case Context.FEATURE_DYNAMIC_SCOPE:
				return false;

			case Context.FEATURE_E4X:
				return false;

				// case Context.FEATURE_STRICT_VARS:
				// return strictVars;

			default:
				return super.hasFeature(cx, featureIndex);
			}
		}
	}

	/**
	 * Hide compiled classes
	 */
	static class JsAppClassShutter implements ClassShutter {

		// the set of auhorized packages names
		final String[] authorizedPackages = new String[] { "net.violet.js", "net.violet.platform.api", "net.violet.platform.applets.js.helpers", "org.mozilla.javascript", "java.util", "sun.reflect.generics", "sun.util.calendar", "adapter" };

		// some additional authorized classes
		final Set<String> authorizedClassSet = new HashSet<String>(Arrays.asList("java.lang.IllegalArgumentException", "java.lang.Class", "java.lang.Object", "java.lang.Package", "java.lang.String"));

		/**
		 * Tell which Java classes are visible from the JavaScript applications
		 */
		public boolean visibleToScripts(String inClassName) {

			// les packages de l'environnement javascript Violet
			for (final String authorizedPackage : this.authorizedPackages) {
				if (inClassName.startsWith(authorizedPackage)) {
					return true;
				}
			}

			// une liste
			final boolean visible = this.authorizedClassSet.contains(inClassName);
			if (!visible) {
				JSEnvironment.LOGGER.warn("ClassShutter : Denying access to external Class : " + inClassName);
			}
			return visible;

		}
	}

}

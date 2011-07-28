package net.violet.platform.api.callers;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ApplicationData;

/**
 * Interface for the different objects that can make API calls
 * 
 * @author christophe - Violet
 */
public interface APICaller {

	/**
	 * An Enum containing available classes for API callers.
	 */
	enum CallerClass {
		APPLICATION,
		COMPONENT,
		OBJECT;

		private final static Map<String, CallerClass> CLASS_BY_NAME;

		static {
			final Map<String, CallerClass> theMap = new HashMap<String, CallerClass>();
			for (final CallerClass aClass : CallerClass.values()) {
				theMap.put(aClass.name().toUpperCase(), aClass);
			}
			CLASS_BY_NAME = Collections.unmodifiableMap(theMap);
		}

		public static CallerClass getByName(String inName) {
			if (inName != null) {
				return CallerClass.CLASS_BY_NAME.get(inName.toUpperCase());
			}

			return null;
		}
	};

	// some composition of authorized caller classes
	List<CallerClass> ALL_CALLERS = Arrays.asList(CallerClass.APPLICATION, CallerClass.COMPONENT, CallerClass.OBJECT);
	List<CallerClass> APPLICATIONS = Arrays.asList(CallerClass.APPLICATION);
	List<CallerClass> COMPONENTS = Arrays.asList(CallerClass.COMPONENT);

	/**
	 * Returns the class of the APICaller (see {@link CallerClass})
	 */
	CallerClass getCallerClass();

	boolean isApplication();

	boolean isObject();

	boolean isPlatformComponent();

	/**
	 * Credentials information (public key and private key)
	 * 
	 * @return
	 */
	String getAPIKey();

	String getAPIPassword();

	/**
	 * @return the class of the application or <code>null</code> if the emitter
	 *         is not an application.
	 */
	ApplicationClass getApplicationClass();

	/**
	 * @return the application or <code>null</code> if the emitter is not an
	 *         application.
	 */
	ApplicationData getApplication();

}

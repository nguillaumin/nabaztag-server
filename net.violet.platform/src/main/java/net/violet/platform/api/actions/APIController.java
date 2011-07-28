package net.violet.platform.api.actions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;

import org.apache.commons.lang.StringUtils;

/**
 * @author christophe - Violet
 */
public class APIController {

	// the internal cache of resolved actions instances
	private static final Map<String, Action> actionMap = new WeakHashMap<String, Action>();

	/**
	 * List of objects used as mutex held by threads creating an {@link Action} for an actionKey
	 */
	private static final Map<String, Object> RUNNING_THREAD = new HashMap<String, Object>();

	private static final Lock LOCK_THREADS = new ReentrantLock();

	private static final Pattern ROOT_REGEX = Pattern.compile("^(\\w+)\\.(\\w+)\\.(\\w+)$");

	// name of the package (root directory) where all the API actions
	// implementations reside
	private static final Map<String, String> PACKAGE_ROOTS = Collections.singletonMap("violet", "net.violet.platform.api.actions.");

	/**
	 * @param actionKey
	 * @return
	 * @throws InvalidParameterException
	 * @throws APIException
	 */
	public static Action getAction(String actionKey) throws InvalidParameterException {

		// Try to get the action instance from the cache..
		Action action = APIController.actionMap.get(actionKey);

//		It exists we return it.
		if (action != null) {
			return action;
		}

//		It does not exist we might have to create.
//		Does anyone else run to create it?
		Object theMutex = APIController.RUNNING_THREAD.get(actionKey);
		if (theMutex == null) {
//			If not we might be the one to do it. Thus we create our mutex.
			theMutex = new Object();
		}

		while (true) { //ugly but necessary (so far)

//			We lock on our mutex.
			synchronized (theMutex) {

//				Double check that while we were waiting for the lock no one actually did the job for us.
				action = APIController.actionMap.get(actionKey);

				if (action != null) {
//					yay! Nothing to do for us!
					return action;
				}

//				We lock the whole shebang
				APIController.LOCK_THREADS.lock();
//				Check is no one if create its own mutex for my actionKey...
				if (!APIController.RUNNING_THREAD.containsKey(actionKey)) {
//					We tell everybody that we are dealing with the creation.
					APIController.RUNNING_THREAD.put(actionKey, theMutex);

//					Release the lock so the rest of the scum can use our work.
					APIController.LOCK_THREADS.unlock();

					final Matcher theMatcher = APIController.ROOT_REGEX.matcher(actionKey);
					if (theMatcher.matches()) {
						final String theRoot = APIController.PACKAGE_ROOTS.get(theMatcher.group(1));
						if (theRoot == null) {
							//must release running thread especially when an exception appears
							APIController.removeRunningThread(actionKey);
							throw new InvalidParameterException(APIErrorMessage.MISSING_PARAMETER, actionKey);
						}
						final String className = theRoot + theMatcher.group(2) + "." + StringUtils.capitalize(theMatcher.group(3));
						try {
							final Class actionClass = Class.forName(className);
							action = (Action) actionClass.newInstance();
							// .. and add it to the cache..
							APIController.actionMap.put(actionKey, action);
						} catch (final ClassNotFoundException e) {
							throw new InvalidParameterException(APIErrorMessage.INVALID_PARAMETER_BECAUSE, "className", className);
						} catch (final InstantiationException e) {
							throw new InvalidParameterException(APIErrorMessage.NO_SUCH_METHOD, net.violet.common.StringShop.EMPTY_STRING);
						} catch (final IllegalAccessException e) {
							throw new InvalidParameterException(APIErrorMessage.NO_SUCH_METHOD, net.violet.common.StringShop.EMPTY_STRING);
						} finally {
							//must release running thread especially when an exception appears
							APIController.removeRunningThread(actionKey);
						}
					} else {
						//must release running thread especially when an exception appears
						APIController.removeRunningThread(actionKey);
						throw new InvalidParameterException(APIErrorMessage.NO_SUCH_METHOD, net.violet.common.StringShop.EMPTY_STRING);
					}

//					We leave our deed is done.
					return action;

				}

//				.. if it did I need to get the good stuff and wait for it to be finished.
				theMutex = APIController.RUNNING_THREAD.get(actionKey);
				APIController.LOCK_THREADS.unlock();
			}
		}

	}

	private static void removeRunningThread(String inActionKey) {
//		Relock to remove ourselves from the running mutex.
		APIController.LOCK_THREADS.lock();
		APIController.RUNNING_THREAD.remove(inActionKey);
		APIController.LOCK_THREADS.unlock();
	}
}

package net.violet.platform.api.actions.secrets;

import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.APICaller.CallerClass;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.util.SecretTimestamp;

import org.apache.log4j.Logger;

/**
 * Control a secret timestamp against expiration.
 * The expiration of a timestamp is only 30 seconds from now.
 * @author christophe - Violet
 */
public class CheckTimestamp extends AbstractAction {

	private static final Logger LOGGER = Logger.getLogger(CheckTimestamp.class);

	// the expiration delay (in ms)
	private final static int EXPIRATION_DELAY = 30 * 1000;

	/**
	 * @see net.violet.platform.api.actions.AbstractAction#doProcessRequest(net.violet.platform.api.actions.ActionParam)
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) throws APIException {

		final String cypheredTimeStamp = inParam.getMainParamAsString();

		try {
			return SecretTimestamp.isValid(cypheredTimeStamp, CheckTimestamp.EXPIRATION_DELAY);

		} catch (final Exception e) {
			CheckTimestamp.LOGGER.warn("Bad or expired timestamp : " + e.getMessage());
			return false;
		}
	}

//  -------------------------------------------------------------------------80

	public ActionType getType() {
		return ActionType.GET;
	}

	public boolean isCacheable() {
		return false;
	}

	public long getExpirationTime() {
		return 0;
	}

	/**
	 * Returns the list of caller classes authorized to call this action.
	 * 
	 * @return
	 */
	@Override
	public List<CallerClass> getAuthorizedCallerClasses() {
		return APICaller.APPLICATIONS;
	}

	/**
	 * Returns the list of application classes authorized to call this action.
	 * Default is to allow all application classes.
	 * 
	 * @return
	 */
	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_ALL;
	}

}

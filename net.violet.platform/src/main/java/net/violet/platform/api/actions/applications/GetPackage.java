package net.violet.platform.api.actions.applications;

import java.util.Date;
import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.actions.AbstractAction.APIMethodParam.Level;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchApplicationException;
import net.violet.platform.api.exceptions.NotModifiedException;
import net.violet.platform.api.maps.applications.ApplicationPackageMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ApplicationPackageData;

import org.apache.log4j.Logger;

/**
 * Récupère les sources d'une application hosted
 * 
 * @author christophe - Violet
 */

public class GetPackage extends AbstractApplicationAction {

	@APIMethodParam(Level.OPTIONAL)
	public static final String IF_MODIFIED_SINCE = "if_modified_since";

	private static final Logger LOGGER = Logger.getLogger(GetPackage.class);

	/**
	 * Returns the applicative resources of an application : returned key values
	 * are : api_key, name, source, source_type, modification_date,
	 * creation_date
	 * 
	 * @return the application package or <code>null</code> if the application
	 *         has no package.
	 * @throws NoSuchApplicationException
	 * @throws InvalidParameterException
	 * @throws NotModifiedException
	 * @throws NotModifiedException if the requested package has not changed.
	 * @see net.violet.platform.api.actions.Action#processRequest(java.util.Map)
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchApplicationException, NotModifiedException {

		final ApplicationData appData = getRequestedApplication(inParam, null);
		// note : appData cannot be null or we had an exception thrown
		final ApplicationPackageData appPackData = appData.getPackage();

		if (GetPackage.LOGGER.isDebugEnabled()) {
			GetPackage.LOGGER.debug("Application package : " + appPackData);
		}

		if ((appPackData == null) || (appPackData.getReference() == null)) {
			return null;
		}

		// Did the client provide the last date he obtained the resource ?
		final Date lastTime = inParam.getDate(GetPackage.IF_MODIFIED_SINCE);

		if (lastTime != null) { // Yes > check against the package date
			final Date packageModificationDate = appPackData.getModificationDate();

			if (lastTime.after(packageModificationDate)) {
				GetPackage.LOGGER.info("Application package not modified");
				throw new NotModifiedException(); // not really an error
			}
		}

		return new ApplicationPackageMap(appData, appPackData);
	}

	/**
	 * Read Only action
	 * 
	 * @see net.violet.platform.api.actions.Action#getType()
	 */
	public ActionType getType() {
		return ActionType.GET;
	}

	/**
	 * Do not cache these informations : there is already the last_modified
	 * parameter to deal with cacheable data
	 * 
	 * @see net.violet.platform.api.actions.Action#isCacheable()
	 */
	public boolean isCacheable() {
		return false;
	}

	/**
	 * @see net.violet.platform.api.actions.Action#getExpirationTime()
	 */
	public long getExpirationTime() {
		return 0; // expires immediately
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}

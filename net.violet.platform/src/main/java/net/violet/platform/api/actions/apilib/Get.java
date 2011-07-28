package net.violet.platform.api.actions.apilib;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NotModifiedException;
import net.violet.platform.api.maps.applications.ApplicationApiLibMap;
import net.violet.platform.applets.AppLanguages;
import net.violet.platform.datamodel.ApplicationApiLib;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.StringShop;

/**
 * API method to retrieve the API library.
 */
public class Get extends AbstractAction {

	private static final String MODIFIED_SINCE = "if_modified_since";
	private static final String VERSION = "version";

	/**
	 * Return the API package
	 * 
	 * @throws InvalidParameterException
	 * @throws NotModifiedException
	 * @see net.violet.platform.api.actions.AbstractAction#doProcessRequest(net.violet.platform.api.actions.ActionParam)
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NotModifiedException {

		final String languageName = inParam.getMainParamAsString();
		final String apiVersion = inParam.getString(Get.VERSION, false);

		final AppLanguages appLanguage = AppLanguages.findByLabel(languageName);

		if (appLanguage == null) {
			throw new InvalidParameterException(APIErrorMessage.NO_SUCH_APPLANGUAGE, StringShop.EMPTY_STRING);
		}

		final ApplicationApiLib apiLib;
		if (apiVersion != null) {
			apiLib = Factories.APPLICATION_API_LIB.findByLanguageAndVersion(appLanguage, apiVersion);
		} else {
			apiLib = Factories.APPLICATION_API_LIB.findLastVersionForLanguage(appLanguage);
		}

		if (apiLib == null) {
			if (apiVersion == null) {
				throw new InvalidParameterException(APIErrorMessage.NO_SUCH_APPLANGUAGE, StringShop.EMPTY_STRING);
			}
			throw new InvalidParameterException(APIErrorMessage.NO_SUCH_APIVERSION, StringShop.EMPTY_STRING);
		}

		// Did the client provide the last date he obtained the resource ?
		final Date lastTime = inParam.getDate(Get.MODIFIED_SINCE);

		if (lastTime != null) { // Yes > check against the package date
			final Date releaseDate = apiLib.getReleaseDate();

			if (lastTime.after(releaseDate)) {
				throw new NotModifiedException(); // not really an error
			}
		}

		return new ApplicationApiLibMap(apiLib);
	}

	/**
	 * Returns the list of application classes authorized to call this action.
	 * 
	 * @return
	 */
	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Collections.emptyList();
	}

	/**
	 * READ-ONLY call
	 * 
	 * @see net.violet.platform.api.actions.Action#getType()
	 */
	public ActionType getType() {
		return ActionType.GET;
	}

	/**
	 * NOT CACHEABLE
	 * 
	 * @see net.violet.platform.api.actions.Action#isCacheable()
	 */
	public boolean isCacheable() {
		return false;
	}

	public long getExpirationTime() {
		return 0;
	}

}

package net.violet.platform.api.actions.applications;

import java.util.Collections;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.config.WidgetConfig;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchApplicationException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.util.Constantes;

import org.apache.log4j.Logger;

/**
 * Return the abstract configuration interface that must be displayed in HTML to
 * define the application configuration
 */
public class GetConfigurationInterface extends AbstractApplicationAction {

	static final Logger LOGGER = Logger.getLogger(AbstractAction.class);

	/**
	 * Input param : the unique id of an application Return a list of
	 * ConfigurationWidget
	 * 
	 * @see net.violet.platform.api.actions.AbstractAction#doProcessRequest(net.violet.platform.api.actions.ActionParam)
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) throws NoSuchApplicationException, InvalidParameterException {

		final ApplicationData app = getRequestedApplication(inParam, null);
		final FilesData settingFile = app.getProfile().getSettingFile();
		if (settingFile != null) {
			GetConfigurationInterface.LOGGER.debug("Found setting file + " + settingFile.getId());
		}

		if ((settingFile == null) || settingFile.isEmpty()) {
			return Collections.emptyList();
		}

		return WidgetConfig.loadConfig(settingFile, app.hasExtendedConfigurationFile());
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
	 * Application Configuration can be cached
	 * 
	 * @see net.violet.platform.api.actions.Action#isCacheable()
	 */
	public boolean isCacheable() {
		return true;
	}

	/**
	 * @see net.violet.platform.api.actions.Action#getExpirationTime()
	 */
	public long getExpirationTime() {
		return Constantes.ONE_DAY_IN_S;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

}

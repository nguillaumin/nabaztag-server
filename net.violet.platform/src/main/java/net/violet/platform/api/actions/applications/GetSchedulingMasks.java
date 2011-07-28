package net.violet.platform.api.actions.applications;

import java.util.Collections;
import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.config.MaskConfig;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchApplicationException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.util.Constantes;

public class GetSchedulingMasks extends AbstractApplicationAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchApplicationException {

		final ApplicationData app = getRequestedApplication(inParam, null);
		final String hardwareType = inParam.getString("hw_type", true);

		final ObjectType theType = ObjectType.findByName(hardwareType);
		if (theType == null) {
			throw new InvalidParameterException(APIErrorMessage.NOT_A_HARDWARE_NAME, hardwareType);
		}

		final FilesData schedulingFile;
		if (app.hasExtendedConfigurationFile()) {
			schedulingFile = app.getProfile().getSettingFile(); // scheduling informations are contained in the setting files !
		} else {
			schedulingFile = app.getProfile().getSchedulingFile(); // applications have a dedicated scheduling file
		}

		if ((schedulingFile == null) || schedulingFile.isEmpty()) {
			return Collections.emptyList();
		}

		return MaskConfig.loadConfig(schedulingFile.getReference(), theType);
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
	 * Do cache these informations !
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

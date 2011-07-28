package net.violet.platform.api.actions.libraries;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.BadMimeTypeException;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchFileException;
import net.violet.platform.api.maps.ItemInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.UserData;

public class Put extends AbstractItemAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchFileException, ForbiddenException, InvalidSessionException, BadMimeTypeException {

		final String fileAPIId = inParam.getString("file", true);

		final FilesData filesData = FilesData.getFilesData(fileAPIId, inParam.getCallerAPIKey());

		final UserData theUserData = SessionManager.getUserFromSessionParam(inParam);

		final MusicData newMusic = MusicData.createNewItem(theUserData.getReference(), filesData);

		if (newMusic != null) {
			final ItemInformationMap result = new ItemInformationMap(inParam.getCaller(), newMusic);
			return result;
		}
		return null;
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.CREATE;
	}

	public boolean isCacheable() {
		return false;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}

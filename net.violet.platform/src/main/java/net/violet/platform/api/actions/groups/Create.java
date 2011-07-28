package net.violet.platform.api.actions.groups;

import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchFileException;
import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.api.maps.PictureInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.GroupData;
import net.violet.platform.dataobjects.TtsLangData;
import net.violet.platform.dataobjects.VObjectData;

public class Create extends AbstractAction {

	@APIMethodParam
	private final static String NAME = "name";
	@APIMethodParam
	private final static String DESCRIPTION = "description";
	@APIMethodParam
	private final static String PICTURE = "picture";
	@APIMethodParam
	private final static String LANGUAGE = "language";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchFileException, InternalErrorException {

		final VObjectData theCreator = VObjectData.findByAPIId(inParam.getMainParamAsString(), inParam.getCallerAPIKey());

		final FilesData filesData = FilesData.getFilesData(inParam.getString(Create.PICTURE, true), inParam.getCallerAPIKey());
		final TtsLangData language = TtsLangData.findByISOCode(inParam.getString(Create.LANGUAGE, true));

		final GroupData theNewGroup = GroupData.create(theCreator, inParam.getString(Create.NAME, true), inParam.getString(Create.DESCRIPTION, true), filesData, language);
		if ((theNewGroup == null) || !theNewGroup.isValid()) {
			throw new InternalErrorException();
		}

		theNewGroup.addMember(theCreator);

		return new GroupInformationMap(theNewGroup, inParam.getCaller());
	}

	public long getExpirationTime() {
		return 0;
	}

	public boolean isCacheable() {
		return false;
	}

	public ActionType getType() {
		return ActionType.CREATE;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

	private static class GroupInformationMap extends AbstractAPIMap {

		public static final String ID = "id";
		public static final String CREATOR = "creator";
		public static final String FEED = "feed";

		public GroupInformationMap(GroupData group, APICaller caller) {
			super();

			put(GroupInformationMap.ID, group.getApiId(caller));
			put(Create.NAME, group.getName());
			put(Create.DESCRIPTION, group.getDescription());
			put(GroupInformationMap.CREATOR, group.getCreator().getApiId(caller));
			put(Create.PICTURE, new PictureInformationMap(group.getPicture()));
			put(GroupInformationMap.FEED, group.getStream().getPath());
			put(Create.LANGUAGE, group.getLanguage().getLang_iso_code());
		}

	}

}

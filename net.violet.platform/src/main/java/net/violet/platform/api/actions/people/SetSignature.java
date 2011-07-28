package net.violet.platform.api.actions.people;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchItemException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Signature;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.AnimData;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.UserData;

/**
 * @throws InvalidParameterException
 * @throws NoSuchPersonException
 * @throws ForbiddenException
 * @throws InvalidSessionException
 * @throws NoSuchItemException
 * @see
 */
public class SetSignature extends AbstractUserAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchPersonException, ForbiddenException, InvalidSessionException {

		final UserData user = getRequestedUser(inParam, null);

		// Check Session
		AbstractUserAction.doesSessionBelongToUser(user, inParam);

		// Retrieve the existing signature
		Signature.ColorType color = user.getColorType();
		if (color == null) {
			color = Signature.ColorType.MAGENTA; // violet default value
		}

		final PojoMap signature = inParam.getPojoMap("signature", true);
		final String anim_name = signature.getString("anim", false);

		AnimData theAnimData = null;
		if (anim_name != null) {
			theAnimData = AnimData.findByName(anim_name);
			if (theAnimData == null) {
				throw new InvalidParameterException(APIErrorMessage.NOT_AN_ANIM_NAME, net.violet.common.StringShop.EMPTY_STRING);
			}
		} else {
			theAnimData = user.getAnimation();
		}

		final String colorName = signature.getString("color", color.getLabel());

		final Signature.ColorType colorType = Signature.ColorType.getColorTypeByLabel(colorName);
		if (colorType == null) {
			throw new InvalidParameterException(APIErrorMessage.INVALID_COLOR, colorName);
		}

		final String musicId = signature.getString("item", false);
		MusicData musicData;
		if (musicId != null) {
			musicData = MusicData.findByAPIId(musicId, inParam.getCallerAPIKey());
		} else {
			musicData = user.getMusicData();
		}

		user.setSignatureInformation(theAnimData, colorType, musicData);
		return null;
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.UPDATE;
	}

	public boolean isCacheable() {
		return false;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}

package net.violet.platform.api.actions.press;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchFileException;
import net.violet.platform.api.exceptions.NoSuchPressException;
import net.violet.platform.api.exceptions.NoSuchProductException;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.api.maps.press.PressInformationMap;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.PressData;
import net.violet.platform.dataobjects.ProductData;
import net.violet.platform.dataobjects.SiteLangData;

public class Update extends AbstractAction {

	//private static final Logger LOGGER = Logger.getLogger(Create.class);
	public static final String PRESS_PARAM = "press";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchProductException, NoSuchFileException, NoSuchPressException {

		final String theAPIId = inParam.getMainParamAsString();
		final PressData thePress = PressData.findByAPIId(theAPIId, inParam.getCallerAPIKey());

		if (thePress == null) {
			throw new NoSuchPressException();
		}

		final PojoMap thePressMap = inParam.getPojoMap(Update.PRESS_PARAM, true);

		final String theLangCode = thePressMap.getString(PressInformationMap.LANGUAGE, thePress.getLanguage());
		final SiteLangData theLang = SiteLangData.getByISOCode(theLangCode);
		final String theTitle = thePressMap.getString(PressInformationMap.TITLE, thePress.getTitle());
		final String theAbstract = thePressMap.getString(PressInformationMap.ABSTRACT, thePress.getAbstract());
		final String theUrl = thePressMap.getString(PressInformationMap.URL, thePress.getUrl());
		final String theProductName = thePressMap.getString(PressInformationMap.PRODUCT, thePress.getProduct());
		final String thePictureAPIId = thePressMap.getString(PressInformationMap.PICTURE, thePress.getPicture());

		final ProductData theProduct = ProductData.findByName(theProductName);
		if (theProduct.getReference() == null) {
			throw new NoSuchProductException(APIErrorMessage.NO_SUCH_PRODUCT, theProductName);
		}

		FilesData thePicture = null;
		if (thePictureAPIId != null) {
			thePicture = FilesData.getFilesData(thePictureAPIId, inParam.getCallerAPIKey());
		}

		thePress.update(theLang, theTitle, theAbstract, thePicture, theUrl, theProduct);

		return new PressInformationMap(inParam.getCaller(), thePress);
	}

	public long getExpirationTime() {
		return 0L;
	}

	public boolean isCacheable() {
		return false;
	}

	public ActionType getType() {
		return ActionType.UPDATE;
	}

}

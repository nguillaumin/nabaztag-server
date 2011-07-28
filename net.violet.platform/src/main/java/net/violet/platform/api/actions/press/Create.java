package net.violet.platform.api.actions.press;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchFileException;
import net.violet.platform.api.exceptions.NoSuchProductException;
import net.violet.platform.api.maps.press.PressInformationMap;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.PressData;
import net.violet.platform.dataobjects.ProductData;
import net.violet.platform.dataobjects.SiteLangData;

public class Create extends AbstractAction {

	//private static final Logger LOGGER = Logger.getLogger(Create.class);

	public static final String LANGUAGE_PARAM = "language";
	public static final String TITLE_PARAM = "title";
	public static final String ABSTRACT_PARAM = "abstract";
	public static final String URL_PARAM = "url";
	public static final String PRODUCT_PARAM = "product";
	public static final String PICTURE_PARAM = "picture";
	public static final String FILE_PARAM = "file";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchProductException, NoSuchFileException {

		final String theLangCode = inParam.getString(Create.LANGUAGE_PARAM, true);
		final SiteLangData theLang = SiteLangData.getByISOCode(theLangCode);
		final String theTitle = inParam.getString(Create.TITLE_PARAM, true);
		final String theAbstract = inParam.getString(Create.ABSTRACT_PARAM, true);
		final String theUrl = inParam.getString(Create.URL_PARAM, true);
		final String theProductName = inParam.getString(Create.PRODUCT_PARAM, true);
		final String thePictureAPIId = inParam.getString(Create.PICTURE_PARAM, false);

		final ProductData theProduct = ProductData.findByName(theProductName);
		if (theProduct.getReference() == null) {
			throw new NoSuchProductException(APIErrorMessage.NO_SUCH_PRODUCT, theProductName);
		}
		FilesData thePicture = null;
		if (thePictureAPIId != null) {
			thePicture = FilesData.getFilesData(thePictureAPIId, inParam.getCallerAPIKey());
		}
		final PressData thePress = PressData.create(theLang, theTitle, theAbstract, thePicture, theUrl, theProduct);

		return new PressInformationMap(inParam.getCaller(), thePress);
	}

	public long getExpirationTime() {
		return 0L;
	}

	public boolean isCacheable() {
		return false;
	}

	public ActionType getType() {
		return ActionType.CREATE;
	}
}

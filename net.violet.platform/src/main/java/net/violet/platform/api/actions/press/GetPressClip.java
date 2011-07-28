package net.violet.platform.api.actions.press;

import java.util.LinkedList;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchProductException;
import net.violet.platform.api.maps.press.PressInformationMap;
import net.violet.platform.datamodel.Product;
import net.violet.platform.dataobjects.PressData;
import net.violet.platform.dataobjects.ProductData;
import net.violet.platform.dataobjects.SiteLangData;

public class GetPressClip extends AbstractAction {

	public static String LANGUAGE_PARAM = "language";
	public static String PRODUCT_PARAM = "product";
	public static String SKIP_PARAM = "skip";
	public static String COUNT_PARAM = "count";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchProductException {

		final int skip = inParam.getInt(GetPressClip.SKIP_PARAM, 0);
		final int count = inParam.getInt(GetPressClip.COUNT_PARAM, 10);
		final String theLangCode = inParam.getString(GetPressClip.LANGUAGE_PARAM, true);
		final String theProductName = inParam.getString(GetPressClip.PRODUCT_PARAM, true);

		final ProductData theProductData = ProductData.findByName(theProductName);
		final SiteLangData theLang = SiteLangData.getByISOCode(theLangCode);
		final Product theProduct = theProductData.getReference();

		if (theProduct == null) {
			throw new NoSuchProductException(APIErrorMessage.NO_SUCH_PRODUCT, theProductName);
		}

		final List<PressInformationMap> pressFromProductAndLanguage = new LinkedList<PressInformationMap>();

		for (final PressData aPressData : PressData.findByLangAndProduct(theLang.getReference(), theProduct, skip, count)) {
			pressFromProductAndLanguage.add(new PressInformationMap(inParam.getCaller(), aPressData));
		}

		return pressFromProductAndLanguage;
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.GET;
	}

	public boolean isCacheable() {
		return true;
	}

}

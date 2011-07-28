package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Press;
import net.violet.platform.datamodel.Product;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class PressData extends APIData<Press> {

	private static final Logger LOGGER = Logger.getLogger(PressData.class);

	public static PressData getData(Press inPress) {
		try {
			return RecordData.getData(inPress, PressData.class, Press.class);
		} catch (final InstantiationException e) {
			PressData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			PressData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			PressData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			PressData.LOGGER.fatal(e, e);
		}

		return null;
	}

	public static List<PressData> findByLangAndProduct(Lang inLang, Product inProduct, int skip, int count) {
		return PressData.generateList(Factories.PRESS.getPressByLangAndProduct(inLang, inProduct, skip, count));
	}

	private static List<PressData> generateList(List<Press> inList) {
		final List<PressData> result = new ArrayList<PressData>();
		for (final Press aPress : inList) {
			result.add(PressData.getData(aPress));
		}
		return result;
	}

	protected PressData(Press inRecord) {
		super(inRecord);
	}

	public String getTitle() {
		final Press thePress = getRecord();
		if ((thePress != null) && (thePress.getTitle() != null)) {
			return thePress.getTitle();
		}
		return net.violet.common.StringShop.EMPTY_STRING;

	}

	public String getProduct() {
		final Press thePress = getRecord();
		if ((thePress != null) && (thePress.getPressProduct() != null)) {
			return thePress.getPressProduct().getName();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getLanguage() {
		final Press thePress = getRecord();
		if ((thePress != null) && (thePress.getPressLang() != null)) {
			return thePress.getPressLang().getTitle();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getPicture() {
		final Press thePress = getRecord();
		if ((thePress != null) && (thePress.getPicture() != null)) {
			return thePress.getPicture().getPath();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getAbstract() {
		final Press thePress = getRecord();
		if ((thePress != null) && (thePress.getAbstract() != null)) {
			return thePress.getAbstract();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getUrl() {
		final Press thePress = getRecord();
		if ((thePress != null) && (thePress.getUrl() != null)) {
			return thePress.getUrl();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public static PressData findByAPIId(String inAPIId, String inAPIKey) {
		PressData theResult = null;
		final long theID = APIData.fromObjectID(inAPIId, ObjectClass.PRESS, inAPIKey);
		if (theID != 0) {
			final Press press = Factories.PRESS.find(theID);
			if (press != null) {
				theResult = PressData.getData(press);
			}
		}
		return theResult;
	}

	@Override
	protected ObjectClass getObjectClass() {
		return ObjectClass.PRESS;
	}

	public static PressData create(SiteLangData inLang, String inTitle, String inAbstract, FilesData inPictureData, String inUrl, ProductData inProduct) {
		Files thePicture = null;
		if (inPictureData != null) {
			thePicture = inPictureData.getReference();
		}

		final Press thePress = Factories.PRESS.create(inLang.getRecord(), inTitle, inAbstract, thePicture, inUrl, inProduct.getRecord());
		if (thePress != null) {
			return PressData.getData(thePress);
		}
		return PressData.getData(null);
	}

	public void update(SiteLangData inLang, String inTitle, String inAbstract, FilesData inPicture, String inUrl, ProductData inProduct) {
		if (this.getRecord() != null) {
			Files thePicture = null;
			if (inPicture != null) {
				thePicture = inPicture.getReference();
			}
			this.getRecord().update(inLang.getReference(), inTitle, inAbstract, thePicture, inUrl, inProduct.getReference());
		}
	}
}

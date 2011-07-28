package net.violet.platform.dataobjects;

import java.util.Date;

import net.violet.platform.datamodel.Hint;
import net.violet.platform.datamodel.factories.Factories;

public class HintData extends APIData<Hint> {

	/**
	 * Constructeur à partir d'un context
	 */
	public HintData(Hint inHint) {
		super(inHint);
	}

	/**
	 * Accesseur à partir d'un ID application.
	 * 
	 * @return Hint ou <code>null</code> si le context n'existe pas.
	 */
	public static HintData findByAPIId(String inAPIId, String inAPIKey) {

		HintData theResult = null;
		final long theID = APIData.fromObjectID(inAPIId, ObjectClass.HINT, inAPIKey);

		if (theID != 0) {
			final Hint hint = Factories.HINT.find(theID);

			if (hint != null) {
				theResult = new HintData(hint);
			}
		}

		return theResult;
	}

	public String getTitle() {
		final Hint theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getTitle();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getContent() {
		final Hint theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getContent();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	@Override
	protected ObjectClass getObjectClass() {
		return ObjectClass.HINT;
	}

	public String getPicture() {
		final Hint theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getPicture();
		}

		return null;
	}

	public Integer getPictureHeight() {
		final Hint theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getPictureHeight();
		}

		return null;
	}

	public Integer getPictureWidth() {
		final Hint theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getPictureWidth();
		}

		return null;
	}

	public String getLink() {
		final Hint theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getLink();
		}

		return null;
	}

	public Date getCreationDate() {
		final Hint theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getCreationDate();
		}

		return null;
	}

	public Date getModificationDate() {
		final Hint theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getModificationDate();
		}

		return null;
	}
}

package net.violet.platform.dataobjects;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.datamodel.Context;
import net.violet.platform.datamodel.Hint;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.StringShop;

public class ContextData extends RecordData<Context> {

	/**
	 * Constructeur à partir d'un context
	 */
	public ContextData(Context inContext) {
		super(inContext);
	}

	public List<HintData> getListHints() {
		final List<HintData> theResult = new ArrayList<HintData>();
		final Context theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getAllHints() != null)) {
			for (final Hint theHint : theRecord.getAllHints()) {
				theResult.add(new HintData(theHint));
			}
		}
		return theResult;
	}

	public String getName() {
		final Context theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getName();
		}

		return StringShop.EMPTY_STRING;
	}

	public static ContextData findByName(String inName) {
		final Context result = Factories.CONTEXT.findByName(inName);
		if (result != null) {
			return new ContextData(result);
		}

		return null;
	}

}

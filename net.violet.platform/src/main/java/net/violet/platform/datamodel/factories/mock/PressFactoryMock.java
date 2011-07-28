package net.violet.platform.datamodel.factories.mock;

import java.util.LinkedList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Press;
import net.violet.platform.datamodel.Product;
import net.violet.platform.datamodel.factories.PressFactory;
import net.violet.platform.datamodel.mock.PressMock;

public class PressFactoryMock extends RecordFactoryMock<Press, PressMock> implements PressFactory {

	PressFactoryMock() {
		super(PressMock.class);
	}

	public List<Press> getPressByLangAndProduct(Lang lang, Product product, int inSkip, int inCount) {
		final List<Press> thePressResult = new LinkedList<Press>();
		for (final Press aPress : findAll()) {
			if (aPress.getPressLang().equals(lang) && aPress.getPressProduct().equals(product)) {
				thePressResult.add(aPress);
			}
		}
		return thePressResult;
	}

	public Press create(Lang inLang, String inTitle, String inAbstract, Files inPicture, String inUrl, Product inProduct) {
		return new PressMock(inLang, inTitle, inAbstract, inPicture, inUrl, inProduct);
	}

	public boolean usesFiles(Files inFile) {
		return false;
	}
}

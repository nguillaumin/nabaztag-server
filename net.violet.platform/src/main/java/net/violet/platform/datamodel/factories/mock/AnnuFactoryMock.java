package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Annu;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.AnnuFactory;
import net.violet.platform.datamodel.mock.AnnuMock;

public class AnnuFactoryMock extends RecordFactoryMock<Annu, AnnuMock> implements AnnuFactory {

	AnnuFactoryMock() {
		super(AnnuMock.class);
	}

	public boolean usesFiles(Files inFile) {
		for (final Annu a : findAll()) {
			if (a.getPictureFile().equals(inFile)) {
				return true;
			}
		}
		return false;
	}

	public Annu create(User user, String country, Lang lang) {
		return new AnnuMock(user, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, 0, new java.sql.Date(System.currentTimeMillis()), country, lang);
	}
}

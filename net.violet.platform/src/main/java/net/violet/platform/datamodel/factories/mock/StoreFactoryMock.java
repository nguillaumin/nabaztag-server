package net.violet.platform.datamodel.factories.mock;

import java.util.LinkedList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Country;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Store;
import net.violet.platform.datamodel.StoreCity;
import net.violet.platform.datamodel.Store.STORE_STATUS;
import net.violet.platform.datamodel.Store.STORE_TYPE;
import net.violet.platform.datamodel.factories.StoreFactory;
import net.violet.platform.datamodel.mock.StoreMock;

public class StoreFactoryMock extends RecordFactoryMock<Store, StoreMock> implements StoreFactory {

	StoreFactoryMock() {
		super(StoreMock.class);
	}

	public List<Store> getStoreByCountry(String countryCode) {
		final List<Store> theResult = new LinkedList<Store>();
		for (final Store inStore : findAll()) {
			final StoreCity city = inStore.getStoreCity();
			final Country country = city.getCountry();
			final String mockCode = country.getCode();
			if (countryCode.equals(mockCode)) {
				theResult.add(inStore);
			}
		}
		return theResult;
	}

	public Store create(String inName, STORE_TYPE inType, Files inPicture, String inAddress, String inZipcode, StoreCity inStoreCity, STORE_STATUS inStatus, String inURL, Long inRankValue, String inComment) {
		return new StoreMock(0L, inName, inType, inPicture, inAddress, inZipcode, inStoreCity, inStatus, inURL, inRankValue, inComment);
	}

	public boolean usesFiles(Files inFile) {
		return false;
	}
}

package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Store;
import net.violet.platform.datamodel.StoreCity;
import net.violet.platform.datamodel.Store.STORE_STATUS;
import net.violet.platform.datamodel.Store.STORE_TYPE;
import net.violet.platform.datamodel.factories.common.FilesAccessor;

public interface StoreFactory extends RecordFactory<Store>, FilesAccessor {

	List<Store> getStoreByCountry(String countryCode);

	Store create(String inName, STORE_TYPE inType, Files thePicture, String inAddress, String inZipcode, StoreCity theStoreCity, STORE_STATUS inStatus, String inURL, Long inRankValue, String inComment);

}

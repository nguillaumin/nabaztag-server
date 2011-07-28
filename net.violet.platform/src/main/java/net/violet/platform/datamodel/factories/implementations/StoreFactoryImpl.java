package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Store;
import net.violet.platform.datamodel.StoreCity;
import net.violet.platform.datamodel.StoreImpl;
import net.violet.platform.datamodel.Store.STORE_STATUS;
import net.violet.platform.datamodel.Store.STORE_TYPE;
import net.violet.platform.datamodel.factories.StoreFactory;

import org.apache.log4j.Logger;

public class StoreFactoryImpl extends RecordFactoryImpl<Store, StoreImpl> implements StoreFactory {

	private static final Logger LOGGER = Logger.getLogger(StoreFactoryImpl.class);

	StoreFactoryImpl() {
		super(StoreImpl.SPECIFICATION);
	}

	public List<Store> getStoreByCountry(String country_code) {
		final String[] inJoinTables = { "store_city", "pays" };
		final String inOrderBy = "pays.pays_code";
		final List<Object> values = Arrays.asList(new Object[] { country_code });
		final String condition = "store_city.id = store.city AND store_city.pays_id = pays.pays_id AND pays.pays_code = ?";
		final List<Store> result = findAll(inJoinTables, condition, values, inOrderBy);
		return result;
	}

	public Store create(String inName, STORE_TYPE inType, Files inPicture, String inAddress, String inZipcode, StoreCity inStoreCity, STORE_STATUS inStatus, String inURL, Long inRankValue, String inComment) {

		try {
			return new StoreImpl(inName, inType, inPicture, inAddress, inZipcode, inStoreCity, inStatus, inURL, inRankValue, inComment);
		} catch (final SQLException e) {
			StoreFactoryImpl.LOGGER.fatal(e, e);
		}
		return null;
	}

	public boolean usesFiles(Files inFile) {
		//TODO Uncomment when the table will used
		//return count(null, " picture = ? ", Collections.singletonList((Object) inFile.getId()), null) > 0;
		return false;
	}
}

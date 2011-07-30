package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * Lists stores where Violet's objects were sold.
 * 
 *
 */
public interface Store extends Record<Store> {

	public static enum STORE_TYPE {
		physical,
		online;
	}

	public static enum STORE_STATUS {
		prefered,
		network,
		normal;
	}

	String getUrl();

	String getName();

	String getType();

	public StoreCity getStoreCity();

	Files getPicture();

	String getAddress();

	String getZipCode();

	String getStatus();

	Long getRank();

	String getComment();

	void update(String inName, STORE_TYPE inType, Files thePicture, String inAddress, String inZipcode, StoreCity inCity, STORE_STATUS inStatus, String inURL, long inRank, String inComment);

}

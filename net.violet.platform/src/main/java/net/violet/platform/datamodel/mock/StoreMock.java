package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Store;
import net.violet.platform.datamodel.StoreCity;

public class StoreMock extends AbstractMockRecord<Store, StoreMock> implements Store {

	/**
	 * Record fields.
	 */

	private String name = "Store Vide";
	private String url = "URL Vide";
	private String address = "Empty address";
	private String zipcode = "00000";
	private String storeType = "phisical";
	private String storeStatus = "store";
	private Long rank = 1L;
	private String comment = "empty comment";

	private StoreCity city = null;
	private Files picture = new FilesMock("mock/picture1", MimeType.MIME_TYPES.JPEG);

	public StoreMock(long inId) {
		super(inId);
	}

	public StoreMock(long inId, String storeName) {
		super(inId);
		this.name = storeName;
		this.picture = new FilesMock("mock/picture", MimeType.MIME_TYPES.JPEG);
	}

	public StoreMock(long inId, String inName, STORE_TYPE inType, Files inPicture, String inAddress, String inZipCode, StoreCity inCity, STORE_STATUS inStatus, String inURL, Long inRank, String inComment) {
		super(inId);
		this.name = inName;
		this.storeType = inType.name();
		this.picture = inPicture;
		this.address = inAddress;
		this.zipcode = inZipCode;
		this.city = inCity;
		this.storeStatus = inStatus.name();
		this.url = inURL;
		this.rank = inRank;
		this.comment = inComment;
	}

	public StoreMock(long inId, String inName, String inUrl) {
		super(inId);
		this.name = inName;
		this.url = inUrl;
		this.picture = new FilesMock("mock/picture", MimeType.MIME_TYPES.JPEG);
	}

	public StoreMock(long inId, String inName, String inAddress, String inZipCode, StoreCity inCity) {
		this(inId, inName, STORE_TYPE.online, null, inAddress, inZipCode, inCity, STORE_STATUS.normal, net.violet.common.StringShop.EMPTY_STRING, 1L, net.violet.common.StringShop.EMPTY_STRING);
		this.picture = new FilesMock("mock/empty/picture", MimeType.MIME_TYPES.JPEG);
	}

	public StoreMock(long inId, String inName, StoreCity inCity, String inUrl) {
		this(inId, inName, STORE_TYPE.online, null, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, inCity, STORE_STATUS.normal, inUrl, 1L, net.violet.common.StringShop.EMPTY_STRING);
		this.picture = new FilesMock("mock/empty/picture", MimeType.MIME_TYPES.JPEG);
	}

	public String getName() {
		return this.name;
	}

	public StoreCity getStoreCity() {
		return this.city;
	}

	public String getUrl() {
		return this.url;
	}

	public String getType() {
		return this.storeType;
	}

	public Files getPicture() {
		return this.picture;
	}

	public String getAddress() {
		return this.address;
	}

	public String getZipCode() {
		return this.zipcode;
	}

	public String getStatus() {
		return this.storeStatus;
	}

	public String getComment() {
		return this.comment;
	}

	public Long getRank() {
		return this.rank;
	}

	public void update(String inName, STORE_TYPE inType, Files thePicture, String inAddress, String inZipcode, StoreCity inCity, STORE_STATUS inStatus, String inURL, long inRank, String inComment) {
		this.name = inName;
		if (inType != null) {
			this.storeType = inType.name();
		}
		if (thePicture != null) {
			this.picture = thePicture;
		}
		this.address = inAddress;
		this.zipcode = inZipcode;
		this.city = inCity;
		if (inStatus != null) {
			this.storeStatus = inStatus.name();
		}
		this.url = inURL;
		this.rank = inRank;
		this.comment = inComment;
	}

}

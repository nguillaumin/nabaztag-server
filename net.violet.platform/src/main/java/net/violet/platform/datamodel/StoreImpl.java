package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNull;

public class StoreImpl extends ObjectRecord<Store, StoreImpl> implements Store {

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<StoreImpl> SPECIFICATION = new SQLObjectSpecification<StoreImpl>("store", StoreImpl.class, new SQLKey("id"));

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] {

	"name", "storetype", "picture", "address", "zipcode", "city", "status", "url", "rank", "comment" };

	/**
	 * Champs de l'enregistrement.
	 */
	protected long id;
	protected String name;
	protected String storetype;
	protected Long picture;
	protected String address;
	protected String zipcode;
	protected Long city;
	protected String status;
	protected String url;
	protected Long rank;
	protected String comment;

	private final SingleAssociationNull<Store, StoreCity, StoreCityImpl> mStoreCity;
	private final SingleAssociationNull<Store, Files, FilesImpl> mPicture;

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected StoreImpl(long id) throws SQLException {
		init(id);
		this.mStoreCity = new SingleAssociationNull<Store, StoreCity, StoreCityImpl>(this, "city", StoreCityImpl.SPECIFICATION, StoreCityImpl.SPECIFICATION.getPrimaryKey());
		this.mPicture = new SingleAssociationNull<Store, Files, FilesImpl>(this, "picture", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
	}

	/**
	 * Constructeur par défaut (enregistrement existant).
	 */
	protected StoreImpl() {
		this.mPicture = new SingleAssociationNull<Store, Files, FilesImpl>(this, "picture", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
		this.mStoreCity = new SingleAssociationNull<Store, StoreCity, StoreCityImpl>(this, "city", StoreCityImpl.SPECIFICATION, StoreCityImpl.SPECIFICATION.getPrimaryKey());
	}

	/**
	 * Constructeur pour un nouvel enregistrement.
	 */
	public StoreImpl(String inName, STORE_TYPE inType, Files inPicture, String inAddress, String inZipcode, StoreCity inStoreCity, STORE_STATUS inStatus, String inURL, Long inRankValue, String inComment) throws SQLException {
		this.name = inName;
		this.storetype = inType.toString();
		if (inPicture != null) {
			this.picture = inPicture.getId();
		}
		this.address = inAddress;
		this.zipcode = inZipcode;
		if (inStoreCity != null) {
			this.city = inStoreCity.getId();
		}
		this.status = inStatus.toString();
		this.url = inURL;
		this.rank = inRankValue;
		this.comment = inComment;
		init(StoreImpl.NEW_COLUMNS);

		//TODO: if type = online, status ??? + est URL correct? 
		if (inType.equals(STORE_TYPE.online)) {
			this.url = inURL;
			//else ??  error
		}
		this.mPicture = new SingleAssociationNull<Store, Files, FilesImpl>(this, "picture", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
		this.mStoreCity = new SingleAssociationNull<Store, StoreCity, StoreCityImpl>(this, "city", StoreCityImpl.SPECIFICATION, StoreCityImpl.SPECIFICATION.getPrimaryKey());
	}

	public StoreImpl(String inStoreName, STORE_TYPE inStoreType, STORE_STATUS inStoreStatus, String inStoreURL) throws SQLException {

		this(inStoreName, inStoreType, null, null, null, null, inStoreStatus, inStoreURL, null, null);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<StoreImpl> getSpecification() {
		return StoreImpl.SPECIFICATION;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.#getName()
	 */
	public String getName() {
		return this.name;
	}

	public String getUrl() {
		return this.url;
	}

	public StoreCity getStoreCity() {
		return this.mStoreCity.get(this.city);
	}

	public String getType() {
		return this.storetype;
	}

	public Files getPicture() {
		return this.mPicture.get(this.picture);
	}

	public String getAddress() {
		return this.address;
	}

	public String getZipCode() {
		return this.zipcode;
	}

	public String getStatus() {
		return this.status;
	}

	public String getComment() {
		return this.comment;
	}

	public Long getRank() {
		return this.rank;
	}

	public void update(String inName, STORE_TYPE inType, Files inPicture, String inAddress, String inZipcode, StoreCity inCity, STORE_STATUS inStatus, String inURL, long inRank, String inComment) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setName(theUpdateMap, inName);
		setType(theUpdateMap, inType);
		setPicture(theUpdateMap, inPicture);
		setAddress(theUpdateMap, inAddress);
		setZipcode(theUpdateMap, inZipcode);
		setStoreCity(theUpdateMap, inCity);
		setStatus(theUpdateMap, inStatus);
		setUrl(theUpdateMap, inURL);
		setRank(theUpdateMap, inRank);
		setComment(theUpdateMap, inComment);
		update(theUpdateMap);
	}

	private void setName(Map<String, Object> inUpdateMap, String inName) {
		if ((inName != null) && !inName.equals(this.name)) {
			this.name = inName;
			inUpdateMap.put("name", this.name);
		}
	}

	private void setType(Map<String, Object> inUpdateMap, STORE_TYPE inType) {
		if ((inType != null) && !inType.equals(this.storetype)) {
			this.storetype = inType.name();
			inUpdateMap.put("storetype", this.storetype);
		}
	}

	private void setPicture(Map<String, Object> inUpdateMap, Files inPicture) {
		if (!this.mPicture.equals(inPicture) && (inPicture != null)) {
			this.picture = inPicture.getId();
			this.mPicture.set(inPicture);
			inUpdateMap.put("picture", this.picture);
		}
	}

	private void setAddress(Map<String, Object> inUpdateMap, String inAddress) {
		if ((inAddress != null) && !inAddress.equals(this.address)) {
			this.address = inAddress;
			inUpdateMap.put("address", this.address);
		}
	}

	private void setZipcode(Map<String, Object> inUpdateMap, String inZipcode) {
		if ((inZipcode != null) && !inZipcode.equals(this.zipcode)) {
			this.zipcode = inZipcode;
			inUpdateMap.put("zipcode", this.zipcode);
		}
	}

	private void setStoreCity(Map<String, Object> inUpdateMap, StoreCity inStoreCity) {
		if (!this.mStoreCity.equals(inStoreCity) && (inStoreCity != null)) {
			this.city = inStoreCity.getId();
			this.mStoreCity.set(inStoreCity);
			inUpdateMap.put("city", this.city);
		}
	}

	private void setStatus(Map<String, Object> inUpdateMap, STORE_STATUS inStatus) {
		if ((inStatus != null) && !inStatus.equals(this.status)) {
			this.status = inStatus.name();
			inUpdateMap.put("status", this.status);
		}
	}

	private void setUrl(Map<String, Object> inUpdateMap, String inURL) {
		if ((inURL != null) && !inURL.equals(this.url)) {
			this.url = inURL;
			inUpdateMap.put("url", this.url);

		}
	}

	private void setRank(Map<String, Object> inUpdateMap, Long inRank) {
		if (inRank != this.rank) {
			this.rank = inRank;
			inUpdateMap.put("rank", this.rank);
		}
	}

	private void setComment(Map<String, Object> inUpdateMap, String inComment) {
		if ((inComment != null) && !inComment.equals(this.comment)) {
			this.comment = inComment;
			inUpdateMap.put("comment", this.comment);
		}
	}

}

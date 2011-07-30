package net.violet.platform.datamodel;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNull;

public class ObjectProfileImpl extends ObjectRecord<ObjectProfile, ObjectProfileImpl> implements ObjectProfile {

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<ObjectProfileImpl> SPECIFICATION = new SQLObjectSpecification<ObjectProfileImpl>("object_profile", ObjectProfileImpl.class, new SQLKey("object_id"), "object_profile_update_time");

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { "object_id", "object_profile_picture", "object_profile_description", "object_profile_creation_time", "object_label" };

	/**
	 * Champs de l'enregistrement.
	 */
	protected long object_id;
	protected String object_label;
	protected Long object_profile_picture;
	protected String object_profile_description;
	protected BigDecimal object_longitudeGPS;
	protected BigDecimal object_latitudeGPS;
	protected Timestamp object_profile_creation_time;
	protected Timestamp object_profile_update_time;
	
	
	

	private final SingleAssociationNull<ObjectProfile, Files, FilesImpl> mPictureFile;

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected ObjectProfileImpl(long id) throws SQLException {
		init(id);
		this.mPictureFile = new SingleAssociationNull<ObjectProfile, Files, FilesImpl>(this, "object_profile_picture", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());

	}

	/**
	 * Constructeur par défaut (enregistrement existant).
	 */
	protected ObjectProfileImpl() {
		// This space for rent.
		this.mPictureFile = new SingleAssociationNull<ObjectProfile, Files, FilesImpl>(this, "object_profile_picture", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());

	}

	/**
	 * Constructeur pour un nouvel enregistrement.
	 */
	public ObjectProfileImpl(VObject inObject, Files inPictureFile, String inDescription, String inLabel) throws SQLException {
		this.object_id = inObject.getId();
		if (inPictureFile != null) {
			this.object_profile_picture = inPictureFile.getId();
		} else {
			this.object_profile_picture = null;
		}
		this.object_profile_description = inDescription;
		this.object_profile_creation_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		this.object_label = inLabel;

		init(ObjectProfileImpl.NEW_COLUMNS);

		this.mPictureFile = new SingleAssociationNull<ObjectProfile, Files, FilesImpl>(this, "object_profile_picture", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
	}

	/**
	 * Constructeur pour un nouvel enregistrement.
	 */
	public ObjectProfileImpl(VObject inObject, Files inPictureFile, String inDescription) throws SQLException {
		this(inObject, inPictureFile, inDescription, inObject.getObject_login());
	}

	@Override
	public SQLObjectSpecification<ObjectProfileImpl> getSpecification() {
		return ObjectProfileImpl.SPECIFICATION;
	}

	public String getDescription() {
		return this.object_profile_description;
	}

	public Files getPicture() {
		return this.mPictureFile.get(this.object_profile_picture);
	}

	public void setProfile(Files inPictureFile, String inDescription) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setObject_profile_description(theUpdateMap, inDescription);
		setObject_profile_picture(theUpdateMap, inPictureFile);
		update(theUpdateMap);
	}

	public void setDescription(String inDescription) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setObject_profile_description(theUpdateMap, inDescription);
		update(theUpdateMap);
	}

	public void setPicture(Files inPictureFile) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setObject_profile_picture(theUpdateMap, inPictureFile);
		update(theUpdateMap);
	}

	public void setLabel(String inLabel) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setObject_label(theUpdateMap, inLabel);
		update(theUpdateMap);
	}

	private void setObject_label(Map<String, Object> inUpdateMap, String inLabel) {
		if ((inLabel != null) && !inLabel.equals(this.object_label)) {
			this.object_label = inLabel;
			inUpdateMap.put("object_label", inLabel);
		}
	}

	private void setObject_profile_description(Map<String, Object> inUpdateMap, String inDescription) {
		if ((inDescription != null) && !inDescription.equals(this.object_profile_description)) {
			this.object_profile_description = inDescription;
			inUpdateMap.put("object_profile_description", inDescription);
		}
	}

	private void setObject_profile_picture(Map<String, Object> inUpdateMap, Files inPictureFile) {
		if ((inPictureFile != null) && !inPictureFile.getId().equals(this.object_profile_picture)) {
			this.object_profile_picture = inPictureFile.getId();
			this.mPictureFile.set(inPictureFile);
			inUpdateMap.put("object_profile_picture", this.object_profile_picture);
		} else if (inPictureFile == null) {
			this.object_profile_picture = null;
			this.mPictureFile.set(inPictureFile);
			inUpdateMap.put("object_profile_picture", this.object_profile_picture);
		}
	}

	public Timestamp getUpdateTime() {
		return this.object_profile_update_time;
	}

	public BigDecimal getLatitudeGPS() {
		return this.object_latitudeGPS;
	}

	public BigDecimal getLongitudeGPS() {
		return this.object_longitudeGPS;
	}

	public void setPositionGPS(BigDecimal inLatitude, BigDecimal inLongitude) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		if ((inLongitude != null) && !inLongitude.equals(this.object_longitudeGPS)) {
			this.object_longitudeGPS = inLongitude;
			theUpdateMap.put("object_longitudeGPS", inLongitude);
		}
		if ((inLatitude != null) && !inLatitude.equals(this.object_latitudeGPS)) {
			this.object_latitudeGPS = inLatitude;
			theUpdateMap.put("object_latitudeGPS", inLatitude);
		}
		update(theUpdateMap);
	}

	public String getLabel() {
		return this.object_label;
	}

}

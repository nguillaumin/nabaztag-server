package net.violet.platform.datamodel;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.ListAssociation;
import net.violet.db.records.associations.SingleAssociationNull;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class NathanVersionImpl extends ObjectRecord<NathanVersion, NathanVersionImpl> implements NathanVersion {

	private static final Logger LOGGER = Logger.getLogger(NathanVersionImpl.class);

	/**
	 * Specification.
	 */
	public static final SQLObjectSpecification<NathanVersionImpl> SPECIFICATION = new SQLObjectSpecification<NathanVersionImpl>("nathan_version", NathanVersionImpl.class, new SQLKey[] { new SQLKey("version_id") });

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { "version_author", "version_date", "version_shared", "version_official", "version_isbn", "version_description", };

	protected long version_id;
	protected long version_author;
	protected String version_description;
	protected Timestamp version_date;
	protected String version_status;
	protected boolean version_shared;
	protected boolean version_official;
	protected long version_nb;
	protected String version_isbn;
	protected long version_preview;

	/**
	 * Author object
	 */
	private final SingleAssociationNull<NathanVersion, VObject, VObjectImpl> mAuthorObject;

	/**
	 * Liste des tags de cette version.
	 */
	private List<NathanTag> versionTags;

	public NathanVersionImpl() {
		// This space for rent.
		this.mAuthorObject = new SingleAssociationNull<NathanVersion, VObject, VObjectImpl>(this, "version_author", VObjectImpl.SPECIFICATION, VObjectImpl.SPECIFICATION.getPrimaryKey());
	}

	/**
	 * Constructeur à partir d'un id (enregistrement existant).
	 */
	public NathanVersionImpl(long inId) throws SQLException {
		init(inId);
		this.mAuthorObject = new SingleAssociationNull<NathanVersion, VObject, VObjectImpl>(this, "version_author", VObjectImpl.SPECIFICATION, VObjectImpl.SPECIFICATION.getPrimaryKey());
	}

	/**
	 * Constructeur à partir de valeurs (nouvelle version).
	 */
	public NathanVersionImpl(VObject author, String isbn) throws SQLException {

		this.version_author = author.getId();
		this.version_date = new Timestamp(Calendar.getInstance().getTimeInMillis());
		this.version_shared = false;
		this.version_official = false;
		this.version_isbn = isbn;
		this.version_description = net.violet.common.StringShop.EMPTY_STRING;

		init(NathanVersionImpl.NEW_COLUMNS);

		this.mAuthorObject = new SingleAssociationNull<NathanVersion, VObject, VObjectImpl>(this, "version_author", VObjectImpl.SPECIFICATION, VObjectImpl.SPECIFICATION.getPrimaryKey());
	}

	@Override
	public SQLObjectSpecification<NathanVersionImpl> getSpecification() {
		return NathanVersionImpl.SPECIFICATION;
	}

	public VObject getAuthor() {
		return this.mAuthorObject.get(this.version_author);
	}

	public Date getDate() {
		return new Date(this.version_date.getTime());
	}

	public String getDescription() {
		return this.version_description;
	}

	public boolean getOfficial() {
		return this.version_official;
	}

	public boolean getShared() {
		return this.version_shared;
	}

	public String getStatus() {
		return this.version_status;
	}

	public long getNb() {
		return this.version_nb;
	}

	public String getIsbn() {
		return this.version_isbn;
	}

	public List<NathanTag> getTags() {
		if (this.versionTags == null) {
			try {
				this.versionTags = ListAssociation.createListAssociation(this, NathanTagImpl.SPECIFICATION, "nathan_tag_version", "version_id", "tag_id");
			} catch (final SQLException e) {
				NathanVersionImpl.LOGGER.fatal(e, e);
			}
		}
		return this.versionTags;
	}

	public void increaseNb() {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setVersion_nb(theUpdateMap, getNb() + 1);
		update(theUpdateMap);
	}

	public void decreaseNb() {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setVersion_nb(theUpdateMap, getNb() - 1);
		update(theUpdateMap);
	}

	public void setPreview(long previewId) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setVersion_preview(theUpdateMap, previewId);
		update(theUpdateMap);
	}

	private void setVersion_preview(Map<String, Object> inUpdateMap, long value) {
		if (this.version_preview != value) {
			this.version_preview = value;
			inUpdateMap.put("version_preview", this.version_preview);
		}
	}

	public void setStatus(Status status) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setVersion_status(theUpdateMap, status.toString());
		update(theUpdateMap);
	}

	public void setVersionInformation(String description, Status status, boolean shared, List<NathanTag> tagsList) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setVersion_description(theUpdateMap, description);
		setVersion_status(theUpdateMap, status.toString());
		setVersion_shared(theUpdateMap, shared);
		setVersion_tags(tagsList);
		update(theUpdateMap);
	}

	private void setVersion_tags(List<NathanTag> tagsList) {
		for (final NathanTag tag : Factories.NATHAN_TAG.findAllTags()) {
			this.getTags().remove(tag);
		}

		for (final NathanTag tag : tagsList) {
			this.getTags().add(tag);
		}
	}

	private void setVersion_nb(Map<String, Object> inUpdateMap, long value) {
		if (this.version_nb != value) {
			this.version_nb = value;
			inUpdateMap.put("version_nb", this.version_nb);
		}

	}

	private void setVersion_status(Map<String, Object> inUpdateMap, String status) {
		if (!this.version_status.equals(status)) {
			this.version_status = status;
			inUpdateMap.put("version_status", this.version_status);
		}
	}

	private void setVersion_shared(Map<String, Object> inUpdateMap, boolean shared) {
		if (shared != this.version_shared) {
			this.version_shared = shared;
			inUpdateMap.put("version_shared", this.version_shared);
		}
	}

	private void setVersion_description(Map<String, Object> inUpdateMap, String description) {
		if (!description.equals(this.version_description)) {
			this.version_description = description;
			inUpdateMap.put("version_description", this.version_description);
		}
	}

	public Long getPreview() {
		return this.version_preview;
	}

}

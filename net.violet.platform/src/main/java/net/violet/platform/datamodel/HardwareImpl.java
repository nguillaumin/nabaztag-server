package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.ListAssociation;
import net.violet.db.records.associations.SingleAssociationNull;
import net.violet.platform.datamodel.MimeType.MIME_TYPES;

import org.apache.log4j.Logger;

public class HardwareImpl extends ObjectRecord<Hardware, HardwareImpl> implements Hardware {

	private static final Logger LOGGER = Logger.getLogger(HardwareImpl.class);

	public static final SQLObjectSpecification<HardwareImpl> SPECIFICATION = new SQLObjectSpecification<HardwareImpl>("hardware", HardwareImpl.class, new SQLKey("id"));

	protected long id;
	protected String label;
	protected String type;
	protected Long picture_file_id;

	private final SingleAssociationNull<Hardware, Files, FilesImpl> mPictureFile;

	private ListAssociation<HardwareImpl, MimeType, MimeTypeImpl> supportedTypes;
	private final List<MimeType.MIME_TYPES> SUPPORTED_TYPES = new LinkedList<MimeType.MIME_TYPES>();

	protected HardwareImpl(long id) throws SQLException {
		init(id);
		this.mPictureFile = new SingleAssociationNull<Hardware, Files, FilesImpl>(this, "picture_file_id", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
	}

	protected HardwareImpl() {
		this.mPictureFile = new SingleAssociationNull<Hardware, Files, FilesImpl>(this, "picture_file_id", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
	}

	/**
	 * Accesseur à partir d'un id.
	 * 
	 * @param id id de l'objet.
	 * @return l'enregistrement (ou <code>null</code>).
	 */
	public static Hardware find(long id) {
		Hardware hardware = null;
		try {
			hardware = AbstractSQLRecord.findByKey(HardwareImpl.SPECIFICATION, HardwareImpl.SPECIFICATION.getTableKeys()[0], id);
		} catch (final SQLException se) {
			HardwareImpl.LOGGER.fatal(se, se);
		}
		return hardware;
	}

	@Override
	public Long getId() {
		return new Long(this.id);
	}

	@Override
	public SQLObjectSpecification<HardwareImpl> getSpecification() {
		return HardwareImpl.SPECIFICATION;
	}

	/**
	 * @see net.violet.platform.datamodel.Hardware#getType()
	 */
	public final String getType() {
		return this.type;
	}

	/**
	 * @see net.violet.platform.datamodel.Hardware#getLabel()
	 */
	public final String getLabel() {
		return this.label;
	}

	/**
	 * @return lowercase concatenation of the label and version of this model
	 */
	public final String getModelName() {
		return (this.label + ((this.type == null) ? net.violet.common.StringShop.EMPTY_STRING : this.type)).toLowerCase();
	}

	public Files getPictureFile() {
		return this.mPictureFile.get(this.picture_file_id);
	}

	public List<MimeType.MIME_TYPES> getSupportedMimeTypes() {
		getSupportedMimeTypesList();
		return this.SUPPORTED_TYPES;
	}

	private ListAssociation<HardwareImpl, MimeType, MimeTypeImpl> getSupportedMimeTypesList() {

		if (this.supportedTypes == null) {
			synchronized (this.SUPPORTED_TYPES) {
				if (this.supportedTypes == null) {
					try {
						this.supportedTypes = ListAssociation.createListAssociation(this, MimeTypeImpl.SPECIFICATION, "hardware_supports_types", "hardware_id", "mime_type_id");

						for (final MimeType aType : this.supportedTypes) {
							this.SUPPORTED_TYPES.add(MIME_TYPES.find(aType));
						}
					} catch (final SQLException sqle) {
						HardwareImpl.LOGGER.error(sqle, sqle);
					}
				}
			}
		}

		return this.supportedTypes;
	}
}

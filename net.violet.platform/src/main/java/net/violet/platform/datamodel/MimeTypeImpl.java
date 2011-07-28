package net.violet.platform.datamodel;

import java.sql.SQLException;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;

/**
 * Classe pour un objet.
 */
public final class MimeTypeImpl extends ObjectRecord<MimeType, MimeTypeImpl> implements MimeType {

	public static final SQLObjectSpecification<MimeTypeImpl> SPECIFICATION = new SQLObjectSpecification<MimeTypeImpl>("mime_types", MimeTypeImpl.class, new SQLKey("id"));

	private static final String[] NEW_COLUMNS = new String[] { "label", "extension" };

	protected long id;
	protected String label;
	protected String extension;

	@Override
	public SQLObjectSpecification<MimeTypeImpl> getSpecification() {
		return MimeTypeImpl.SPECIFICATION;
	}

	public MimeTypeImpl(String inLabel, String inExtension) throws SQLException {
		this.label = inLabel;
		this.extension = inExtension;

		init(MimeTypeImpl.NEW_COLUMNS);
	}

	protected MimeTypeImpl(long id) throws SQLException {
		init(id);
	}

	protected MimeTypeImpl() {
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public String getExtension() {
		return this.extension;
	}

	public String getLabel() {
		return this.label;
	}

}

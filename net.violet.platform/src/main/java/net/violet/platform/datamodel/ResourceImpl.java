package net.violet.platform.datamodel;

import java.sql.SQLException;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;

public class ResourceImpl extends ObjectRecord<Resource, ResourceImpl> implements Resource {

	public static final SQLObjectSpecification<ResourceImpl> SPECIFICATION = new SQLObjectSpecification<ResourceImpl>("resources", ResourceImpl.class, new SQLKey("id"));

	private static final String[] NEW_COLUMNS = new String[] { "path", "content_id" };

	protected long id;
	protected String path;
	protected long content_id;

	private final SingleAssociationNotNull<Resource, ApplicationContent, ApplicationContentImpl> content;

	/**
	 * Constructeur par défaut (enregistrement existant).
	 */
	protected ResourceImpl() {
		this.content = new SingleAssociationNotNull<Resource, ApplicationContent, ApplicationContentImpl>(this, "content_id", ApplicationContentImpl.SPECIFICATION, ApplicationContentImpl.SPECIFICATION.getPrimaryKey());
	}

	/**
	 * Constructeur à partir d'un id (enregistrement existant).
	 */
	protected ResourceImpl(long inId) throws SQLException {
		init(inId);
		this.content = new SingleAssociationNotNull<Resource, ApplicationContent, ApplicationContentImpl>(this, "content_id", ApplicationContentImpl.SPECIFICATION, ApplicationContentImpl.SPECIFICATION.getPrimaryKey());
	}

	/**
	 * Constructeur à partir de valeurs
	 */
	public ResourceImpl(String inPath, ApplicationContent inContent) throws SQLException {
		this.content_id = inContent.getId();
		this.path = inPath;

		init(ResourceImpl.NEW_COLUMNS);

		this.content = new SingleAssociationNotNull<Resource, ApplicationContent, ApplicationContentImpl>(this, "content_id", ApplicationContentImpl.SPECIFICATION, ApplicationContentImpl.SPECIFICATION.getPrimaryKey());
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public SQLObjectSpecification<ResourceImpl> getSpecification() {
		return ResourceImpl.SPECIFICATION;
	}

	public ApplicationContent getContent() {
		return this.content.get(this.content_id);
	}

	public String getPath() {
		return this.path;
	}

}

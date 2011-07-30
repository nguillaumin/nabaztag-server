package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.sql.Timestamp;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.platform.dataobjects.ObjectType;

public class NabcastResourceImpl extends ObjectRecord<NabcastResource, NabcastResourceImpl> implements NabcastResource {

	public static final SQLObjectSpecification<NabcastResourceImpl> SPECIFICATION = new SQLObjectSpecification<NabcastResourceImpl>("nabcast_resources", NabcastResourceImpl.class, new SQLKey("content_id"));

	private static final String[] NEW_COLUMNS = new String[] { "content_id", "expiration_date", "release_date", "object_reader", "label" };

	protected long content_id;
	protected String label;
	protected String object_reader;
	protected Timestamp expiration_date;
	protected Timestamp release_date;	

	private final SingleAssociationNotNull<NabcastResource, ApplicationContent, ApplicationContentImpl> content;

	/**
	 * Constructeur par défaut (enregistrement existant).
	 */
	protected NabcastResourceImpl() {
		this.content = new SingleAssociationNotNull<NabcastResource, ApplicationContent, ApplicationContentImpl>(this, "content_id", ApplicationContentImpl.SPECIFICATION, ApplicationContentImpl.SPECIFICATION.getPrimaryKey());
	}

	/**
	 * Constructeur à partir d'un id (enregistrement existant).
	 */
	protected NabcastResourceImpl(long inId) throws SQLException {
		init(inId);
		this.content = new SingleAssociationNotNull<NabcastResource, ApplicationContent, ApplicationContentImpl>(this, "content_id", ApplicationContentImpl.SPECIFICATION, ApplicationContentImpl.SPECIFICATION.getPrimaryKey());
	}

	public NabcastResourceImpl(ApplicationContent inContent, ObjectType inType, String inLabel) throws SQLException {
		this(inContent, null, null, inType, inLabel);
	}

	public NabcastResourceImpl(ApplicationContent inContent, Timestamp inReleaseDate, ObjectType inType, String inLabel) throws SQLException {
		this(inContent, inReleaseDate, null, inType, inLabel);
	}

	/**
	 * Constructeur à partir de valeurs
	 * @param inLabel 
	 */
	public NabcastResourceImpl(ApplicationContent inContent, Timestamp inReleaseDate, Timestamp inExpirationDate, ObjectType inType, String inLabel) throws SQLException {
		this.content_id = inContent.getId();
		this.expiration_date = inExpirationDate;
		this.release_date = inReleaseDate;
		this.object_reader = inType.getTypeName();
		this.label = inLabel;

		init(NabcastResourceImpl.NEW_COLUMNS);

		this.content = new SingleAssociationNotNull<NabcastResource, ApplicationContent, ApplicationContentImpl>(this, "content_id", ApplicationContentImpl.SPECIFICATION, ApplicationContentImpl.SPECIFICATION.getPrimaryKey());
	}

	@Override
	public Long getId() {
		return this.content_id;
	}

	@Override
	public SQLObjectSpecification<NabcastResourceImpl> getSpecification() {
		return NabcastResourceImpl.SPECIFICATION;
	}

	public Timestamp getExpirationDate() {
		return this.expiration_date;
	}

	public Timestamp getReleaseDate() {
		return this.release_date;
	}

	public ApplicationContent getContent() {
		return this.content.get(this.content_id);
	}

	public ObjectType getObjectReader() {
		return ObjectType.findByName(this.object_reader);
	}

	public String getLabel() {
		return this.label;
	}

}

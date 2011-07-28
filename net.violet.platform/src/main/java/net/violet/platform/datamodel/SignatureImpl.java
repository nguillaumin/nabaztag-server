package net.violet.platform.datamodel;

import java.sql.SQLException;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;

public class SignatureImpl extends ObjectRecord<Signature, SignatureImpl> implements Signature {

	public static final SQLObjectSpecification<SignatureImpl> SPECIFICATION = new SQLObjectSpecification<SignatureImpl>("signatures", SignatureImpl.class, new SQLKey("id"));

	private static final String[] NEW_COLUMNS = new String[] { "file_id", "anim_id", "color" };

	protected long id;
	protected long file_id;
	protected long anim_id;
	protected String color;

	private final SingleAssociationNotNull<Signature, Files, FilesImpl> file;
	private final SingleAssociationNotNull<Signature, Anim, AnimImpl> anim;

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected SignatureImpl(long id) throws SQLException {
		init(id);
		this.file = new SingleAssociationNotNull<Signature, Files, FilesImpl>(this, "file_id", FilesImpl.SPECIFICATION);
		this.anim = new SingleAssociationNotNull<Signature, Anim, AnimImpl>(this, "anim_id", AnimImpl.SPECIFICATION);
	}

	/**
	 * Constructeur par défaut.
	 */
	protected SignatureImpl() {
		this.file = new SingleAssociationNotNull<Signature, Files, FilesImpl>(this, "file_id", FilesImpl.SPECIFICATION);
		this.anim = new SingleAssociationNotNull<Signature, Anim, AnimImpl>(this, "anim_id", AnimImpl.SPECIFICATION);
	}

	public SignatureImpl(Files inFile, Anim inAnim, ColorType inColor) throws SQLException {
		this.file_id = inFile.getId();
		this.anim_id = inAnim.getId();
		this.color = inColor.getValue();

		init(SignatureImpl.NEW_COLUMNS);

		this.file = new SingleAssociationNotNull<Signature, Files, FilesImpl>(this, "file_id", FilesImpl.SPECIFICATION);
		this.anim = new SingleAssociationNotNull<Signature, Anim, AnimImpl>(this, "anim_id", AnimImpl.SPECIFICATION);
	}

	@Override
	public SQLObjectSpecification<SignatureImpl> getSpecification() {
		return SignatureImpl.SPECIFICATION;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public Anim getAnim() {
		return this.anim.get(this.anim_id);
	}

	public ColorType getColor() {
		return ColorType.getColorByValue(this.color);
	}

	public Files getFile() {
		return this.file.get(this.file_id);
	}

}

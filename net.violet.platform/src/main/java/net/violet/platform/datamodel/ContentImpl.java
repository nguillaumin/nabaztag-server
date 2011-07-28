package net.violet.platform.datamodel;

import java.sql.SQLException;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.StringShop;

public class ContentImpl extends ObjectRecord<Content, ContentImpl> implements Content {

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<ContentImpl> SPECIFICATION = new SQLObjectSpecification<ContentImpl>("content", ContentImpl.class, new SQLKey[] { new SQLKey("id"), new SQLKey(StringShop.FILE_ID) });

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	public static final String[] NEW_COLUMNS = new String[] { StringShop.ACTION_ID, StringShop.FILE_ID, "title", "link", "id_xml", };

	/**
	 * Champs de l'enregistrement.
	 */
	protected long id;
	protected long action_id;
	protected long file_id;
	protected String title;
	protected String link;
	protected String id_xml;

	private final SingleAssociationNotNull<Content, Files, FilesImpl> file;
	/**
	 * Action associée avec ce contenu.
	 */
	private final SingleAssociationNotNull<Content, VAction, VActionImpl> mAction;

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected ContentImpl(long id) throws SQLException {
		init(id);

		this.file = new SingleAssociationNotNull<Content, Files, FilesImpl>(this, StringShop.FILE_ID, FilesImpl.SPECIFICATION);
		this.mAction = new SingleAssociationNotNull<Content, VAction, VActionImpl>(this, StringShop.ACTION_ID, VActionImpl.SPECIFICATION);
	}

	protected ContentImpl() {
		this.file = new SingleAssociationNotNull<Content, Files, FilesImpl>(this, StringShop.FILE_ID, FilesImpl.SPECIFICATION);
		this.mAction = new SingleAssociationNotNull<Content, VAction, VActionImpl>(this, StringShop.ACTION_ID, VActionImpl.SPECIFICATION);
	}

	/**
	 * Constructeur à partir de valeurs (nouveau contenu).
	 */
	public ContentImpl(VActionImpl inAction, Files inFile, String inTitle, String inLink, String inIdXml) {
		this.title = inTitle;
		this.link = inLink;
		this.id_xml = inIdXml;

		this.file_id = inFile.getId();
		this.action_id = inAction.getId();
		this.file = new SingleAssociationNotNull<Content, Files, FilesImpl>(this, StringShop.FILE_ID, FilesImpl.SPECIFICATION);
		this.mAction = new SingleAssociationNotNull<Content, VAction, VActionImpl>(this, StringShop.ACTION_ID, inAction);
	}

	@Override
	public SQLObjectSpecification<ContentImpl> getSpecification() {
		return ContentImpl.SPECIFICATION;
	}

	public final VAction getAction() {
		return this.mAction.get(this.action_id);
	}

	public final Files getFile() {
		return this.file.get(this.file_id);
	}

	public final String getTitle() {
		return this.title;
	}

	public final String getLink() {
		return this.link;
	}

	public final String getId_xml() {
		return this.id_xml;
	}

	public long countObjectHasReadContent() {
		return Factories.OBJECT_HAS_READ_CONTENT.countByContent(this);
	}
}

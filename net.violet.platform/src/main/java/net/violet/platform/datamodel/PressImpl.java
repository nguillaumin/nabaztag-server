package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNull;

public class PressImpl extends ObjectRecord<Press, PressImpl> implements Press {

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<PressImpl> SPECIFICATION = new SQLObjectSpecification<PressImpl>("press", PressImpl.class, new SQLKey("id"));

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] {

	"lang", "title", "pressabstract", "picture", "url", "product" };

	/**
	 * Champs de l'enregistrement.
	 */
	protected long id;
	protected int lang;
	protected String title;
	protected String pressabstract;
	protected Long picture;
	protected String url;
	protected long product;

	private final SingleAssociationNull<Press, Lang, LangImpl> mLang;
	private final SingleAssociationNull<Press, Product, ProductImpl> mProduct;
	private final SingleAssociationNull<Press, Files, FilesImpl> mPicture;

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected PressImpl(long id) throws SQLException {
		init(id);
		this.mLang = new SingleAssociationNull<Press, Lang, LangImpl>(this, "lang", LangImpl.SPECIFICATION, LangImpl.SPECIFICATION.getPrimaryKey());
		this.mProduct = new SingleAssociationNull<Press, Product, ProductImpl>(this, "product", ProductImpl.SPECIFICATION, ProductImpl.SPECIFICATION.getPrimaryKey());
		this.mPicture = new SingleAssociationNull<Press, Files, FilesImpl>(this, "picture", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
	}

	/**
	 * Constructeur par défaut (enregistrement existant).
	 */
	protected PressImpl() {
		this.mLang = new SingleAssociationNull<Press, Lang, LangImpl>(this, "lang", LangImpl.SPECIFICATION, LangImpl.SPECIFICATION.getPrimaryKey());
		this.mProduct = new SingleAssociationNull<Press, Product, ProductImpl>(this, "product", ProductImpl.SPECIFICATION, ProductImpl.SPECIFICATION.getPrimaryKey());
		this.mPicture = new SingleAssociationNull<Press, Files, FilesImpl>(this, "picture", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
	}

	/**
	 * Constructeur pour un nouvel enregistrement.
	 * @param inLang
	 * @param inTitle
	 * @param inAbstract
	 * @param inPicture
	 * @param inUrl 
	 * @param inProduct
	 */
	public PressImpl(Lang inLang, String inTitle, String inAbstract, Files inPicture, String inUrl, Product inProduct) throws SQLException {

		this.lang = inLang.getId().intValue();
		this.title = inTitle;
		this.pressabstract = inAbstract;
		if (inPicture != null) {
			this.picture = inPicture.getId();
		}
		this.url = inUrl;
		this.product = inProduct.getId();

		this.mLang = new SingleAssociationNull<Press, Lang, LangImpl>(this, "lang", LangImpl.SPECIFICATION, LangImpl.SPECIFICATION.getPrimaryKey());
		this.mProduct = new SingleAssociationNull<Press, Product, ProductImpl>(this, "product", ProductImpl.SPECIFICATION, ProductImpl.SPECIFICATION.getPrimaryKey());
		this.mPicture = new SingleAssociationNull<Press, Files, FilesImpl>(this, "picture", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());

		init(PressImpl.NEW_COLUMNS);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<PressImpl> getSpecification() {
		return PressImpl.SPECIFICATION;
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
	 * @see net.violet.platform.datamodel.#getTitle()
	 */
	public String getTitle() {
		return this.title;
	}

	public String getAbstract() {
		return this.pressabstract;
	}

	public Lang getPressLang() {
		return this.mLang.get(this.lang);
	}

	public Product getPressProduct() {
		return this.mProduct.get(this.product);
	}

	public Files getPicture() {
		return this.mPicture.get(this.picture);
	}

	public String getUrl() {
		return this.url;
	}

	public void update(Lang inLang, String inTitle, String inAbstract, Files inPicture, String inUrl, Product inProduct) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setLang(theUpdateMap, inLang);
		setTitle(theUpdateMap, inTitle);
		setAbstract(theUpdateMap, inAbstract);
		setPicture(theUpdateMap, inPicture);
		setUrl(theUpdateMap, inUrl);
		setProduct(theUpdateMap, inProduct);
		update(theUpdateMap);
	}

	private void setLang(Map<String, Object> inUpdateMap, Lang inLang) {
		if (inLang != null) {
			if (!this.mLang.equals(inLang)) {
				this.lang = inLang.getId().intValue();
				this.mLang.set(inLang);
				inUpdateMap.put("lang", this.lang);
			}
		}
	}

	private void setTitle(Map<String, Object> inUpdateMap, String inTitle) {
		if (!inTitle.equals(this.title)) {
			this.title = inTitle;
			inUpdateMap.put("title", this.title);
		}
	}

	private void setAbstract(Map<String, Object> inUpdateMap, String inAbstract) {
		if (!inAbstract.equals(this.pressabstract)) {
			this.pressabstract = inAbstract;
			inUpdateMap.put("pressabstract", this.pressabstract);
		}
	}

	private void setPicture(Map<String, Object> inUpdateMap, Files inPicture) {
		if (!this.mPicture.equals(inPicture) && (inPicture != null)) {
			this.picture = inPicture.getId();
			this.mPicture.set(inPicture);
			inUpdateMap.put("picture", this.picture);
		}
	}

	private void setUrl(Map<String, Object> inUpdateMap, String inUrl) {
		if (!inUrl.equals(this.url)) {
			this.url = inUrl;
			inUpdateMap.put("url", this.url);
		}
	}

	private void setProduct(Map<String, Object> inUpdateMap, Product inProduct) {
		if (inProduct != null) {
			if (!this.mProduct.equals(inProduct)) {
				this.product = inProduct.getId();
				this.mProduct.set(inProduct);
				inUpdateMap.put("product", this.product);
			}
		}
	}
}

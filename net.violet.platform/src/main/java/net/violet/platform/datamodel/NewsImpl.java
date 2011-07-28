package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNull;

public class NewsImpl extends ObjectRecord<News, NewsImpl> implements News {

	//private static final Logger LOGGER = Logger.getLogger();
	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<NewsImpl> SPECIFICATION = new SQLObjectSpecification<NewsImpl>("news", NewsImpl.class, new SQLKey("id"));

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] {

	"lang", "title", "newsabstract", "picture_small", "picture_big", "intro", "body", "date_pub", "date_exp", "product" };

	/**
	 * Champs de l'enregistrement.
	 */
	protected long id;
	protected int lang;
	protected String title;
	protected String newsabstract;
	protected Long picture_small;
	protected Long picture_big;
	protected String intro;
	protected String body;
	protected Timestamp date_pub;
	protected Timestamp date_exp;
	protected long product;

	private final SingleAssociationNull<News, Lang, LangImpl> mLang;
	private final SingleAssociationNull<News, Product, ProductImpl> mProduct;
	private final SingleAssociationNull<News, Files, FilesImpl> mFileSmall;
	private final SingleAssociationNull<News, Files, FilesImpl> mFileBig;

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected NewsImpl(long id) throws SQLException {
		init(id);
		this.mLang = new SingleAssociationNull<News, Lang, LangImpl>(this, "lang", LangImpl.SPECIFICATION, LangImpl.SPECIFICATION.getPrimaryKey());
		this.mProduct = new SingleAssociationNull<News, Product, ProductImpl>(this, "product", ProductImpl.SPECIFICATION, ProductImpl.SPECIFICATION.getPrimaryKey());
		this.mFileSmall = new SingleAssociationNull<News, Files, FilesImpl>(this, "product", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
		this.mFileBig = new SingleAssociationNull<News, Files, FilesImpl>(this, "product", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
	}

	/**
	 * Constructeur par défaut (enregistrement existant).
	 */
	protected NewsImpl() {
		this.mLang = new SingleAssociationNull<News, Lang, LangImpl>(this, "lang", LangImpl.SPECIFICATION, LangImpl.SPECIFICATION.getPrimaryKey());
		this.mProduct = new SingleAssociationNull<News, Product, ProductImpl>(this, "product", ProductImpl.SPECIFICATION, ProductImpl.SPECIFICATION.getPrimaryKey());
		this.mFileSmall = new SingleAssociationNull<News, Files, FilesImpl>(this, "product", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
		this.mFileBig = new SingleAssociationNull<News, Files, FilesImpl>(this, "product", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
	}

	/**
	 * Constructeur pour un nouvel enregistrement.
	 */
	public NewsImpl(Lang inLang, String inTitle, String inAbstract, Files inSmall, Files inBig, String inIntro, String inBody, Date inDatePub, Date inDateExp, Product inProduct) throws SQLException {

		this.lang = inLang.getId().intValue();
		this.title = inTitle;
		this.newsabstract = inAbstract;
		if (inSmall != null) {
			this.picture_small = inSmall.getId();
		}
		if (inBig != null) {
			this.picture_big = inBig.getId();
		}
		this.intro = inIntro;
		this.body = inBody;
		if (inDatePub != null) {
			this.date_pub = new Timestamp(inDatePub.getTime());
		}
		final Timestamp dateExp = new Timestamp(inDateExp.getTime());
		this.date_exp = dateExp;
		this.product = inProduct.getId();
		this.mLang = new SingleAssociationNull<News, Lang, LangImpl>(this, "lang", LangImpl.SPECIFICATION, LangImpl.SPECIFICATION.getPrimaryKey());
		this.mProduct = new SingleAssociationNull<News, Product, ProductImpl>(this, "product", ProductImpl.SPECIFICATION, ProductImpl.SPECIFICATION.getPrimaryKey());
		this.mFileSmall = new SingleAssociationNull<News, Files, FilesImpl>(this, "product", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
		this.mFileBig = new SingleAssociationNull<News, Files, FilesImpl>(this, "product", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
		init(NewsImpl.NEW_COLUMNS);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<NewsImpl> getSpecification() {
		return NewsImpl.SPECIFICATION;
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
		return this.newsabstract;
	}

	public Lang getNewsLang() {
		return this.mLang.get(this.lang);
	}

	public Product getNewsProduct() {
		return this.mProduct.get(this.product);
	}

	public String getBody() {
		return this.body;
	}

	public String getIntro() {
		return this.intro;
	}

	public Files getPicture_b() {
		return this.mFileBig.get(this.picture_big);
	}

	public Files getPicture_s() {
		return this.mFileSmall.get(this.picture_small);
	}

	public Timestamp getDateExp() {
		return this.date_exp;
	}

	public Timestamp getDatePub() {
		return this.date_pub;
	}

	public void update(Lang inLang, String inTitle, String inAbstract, Files inBig, Files inSmall, String inIntro, String inBody, Date inDatePub, Date inDateExp, Product inProduct) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setLang(theUpdateMap, inLang);
		setTitle(theUpdateMap, inTitle);
		setAbstract(theUpdateMap, inAbstract);
		setSmall(theUpdateMap, inSmall);
		setBig(theUpdateMap, inBig);
		setIntro(theUpdateMap, inIntro);
		setBody(theUpdateMap, inBody);
		setDatePub(theUpdateMap, inDatePub);
		setDateExp(theUpdateMap, inDateExp);
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
		if (!inAbstract.equals(this.newsabstract)) {
			this.newsabstract = inAbstract;
			inUpdateMap.put("newsabstract", this.newsabstract);
		}
	}

	private void setIntro(Map<String, Object> inUpdateMap, String inIntro) {
		if (!inIntro.equals(this.intro)) {
			this.intro = inIntro;
			inUpdateMap.put("intro", this.intro);
		}
	}

	private void setBody(Map<String, Object> inUpdateMap, String inBody) {
		if (!inBody.equals(this.body)) {
			this.body = inBody;
			inUpdateMap.put("body", this.body);
		}
	}

	private void setDatePub(Map<String, Object> inUpdateMap, Date inDatePub) {
		if (inDatePub != null) {
			if (!this.date_pub.equals(inDatePub)) {
				this.date_pub = new Timestamp(inDatePub.getTime());
				inUpdateMap.put("date_pub", this.date_pub);
			}
		}
	}

	private void setDateExp(Map<String, Object> inUpdateMap, Date inDateExp) {
		if (inDateExp != null) {
			if (!this.date_exp.equals(inDateExp)) {
				this.date_exp = new Timestamp(inDateExp.getTime());
				inUpdateMap.put("date_exp", this.date_exp);
			}
		}
	}

	private void setSmall(Map<String, Object> inUpdateMap, Files inSmall) {
		if (!this.mFileSmall.equals(inSmall) && (inSmall != null)) {
			this.picture_small = inSmall.getId();
			this.mFileSmall.set(inSmall);
			inUpdateMap.put("picture_small", this.picture_small);
		}
	}

	private void setBig(Map<String, Object> inUpdateMap, Files inBig) {
		if (!this.mFileBig.equals(inBig) && (inBig != null)) {
			this.picture_big = inBig.getId();
			this.mFileBig.set(inBig);
			inUpdateMap.put("picture_big", this.picture_big);
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

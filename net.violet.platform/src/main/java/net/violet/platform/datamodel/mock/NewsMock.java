package net.violet.platform.datamodel.mock;

import java.sql.Timestamp;
import java.util.Date;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.News;
import net.violet.platform.datamodel.Product;

public class NewsMock extends AbstractMockRecord<News, NewsMock> implements News {

	private Lang lang;
	private Product product;
	private String title;
	private Files pictureSmall;
	private Files pictureBig;
	private String newsAbstract;
	private String intro;
	private String body;
	private Timestamp datePub;
	private Timestamp dateExp;

	public NewsMock(Long inId, Lang inLang, String inTitle, String inAbstract, Files inSmall, Files inBig, String inIntro, String inBody, Date inDatePub, Date inDateExp, Product inProduct) {
		super(inId);
		this.lang = inLang;
		this.product = inProduct;
		this.title = inTitle;
		this.newsAbstract = inAbstract;
		this.body = inBody;
		this.intro = inIntro;
		this.pictureSmall = inSmall;
		this.pictureBig = inBig;
		if (inDateExp != null) {
			this.dateExp = new Timestamp(inDateExp.getTime());
		}
		if (inDatePub != null) {
			this.dateExp = new Timestamp(inDatePub.getTime());
		}
		this.product = inProduct;
	}

	public NewsMock(Long inId, Lang inLang, Product inProduct, String inTitle) {
		this(inId, inLang, inTitle, null, null, null, null, null, null, null, inProduct);
	}

	public NewsMock(Long inId, Lang inLang, Product inProduct, String inTitle, String inBody) {
		this(inId, inLang, inTitle, null, null, null, null, inBody, null, null, inProduct);
	}

	public Lang getNewsLang() {
		return this.lang;
	}

	public Product getNewsProduct() {
		return this.product;
	}

	public String getTitle() {
		return this.title;
	}

	public String getAbstract() {
		return this.newsAbstract;
	}

	public String getBody() {
		return this.body;
	}

	public Timestamp getDateExp() {
		return this.dateExp;
	}

	public Timestamp getDatePub() {
		return this.datePub;
	}

	public String getIntro() {
		return this.intro;
	}

	public Files getPicture_b() {
		return this.pictureBig;
	}

	public Files getPicture_s() {
		return this.pictureSmall;
	}

	public void update(Lang inLang, String inTitle, String inAbstract, Files inBig, Files inSmall, String inIntro, String inBody, Date inDatePub, Date inDateExp, Product inProduct) {
		this.lang = inLang;
		this.product = inProduct;
		this.title = inTitle;
		this.newsAbstract = inAbstract;
		this.body = inBody;
		this.intro = inIntro;
		this.pictureSmall = inSmall;
		this.pictureBig = inBig;
		if (inDateExp != null) {
			this.dateExp = new Timestamp(inDateExp.getTime());
		}
		if (inDatePub != null) {
			this.dateExp = new Timestamp(inDatePub.getTime());
		}
		this.product = inProduct;
	}
}

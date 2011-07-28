package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Press;
import net.violet.platform.datamodel.Product;

public class PressMock extends AbstractMockRecord<Press, PressMock> implements Press {

	private Lang lang;
	private Product product;
	private Files file;
	private String title;
	private String url;
	private String abstractPress;

	public PressMock(long inId, Lang inLang, String inTitle, String inAbstract, Files inFile, String inUrl, Product inProduct) {
		super(inId);
		this.lang = inLang;
		this.product = inProduct;
		this.file = inFile;
		this.title = inTitle;
		this.url = inUrl;
		this.abstractPress = inAbstract;
	}

	public PressMock(Lang inLang, String inTitle, String inAbstract, Files inPicture, String inUrl, Product inProduct) {
		this(0L, inLang, inTitle, inAbstract, inPicture, inUrl, inProduct);
	}

	public Lang getPressLang() {
		return this.lang;
	}

	public Product getPressProduct() {
		return this.product;
	}

	public String getTitle() {
		return this.title;
	}

	public String getAbstract() {
		return this.abstractPress;
	}

	public Files getPicture() {
		return this.file;
	}

	public String getUrl() {
		return this.url;
	}

	public void update(Lang inLang, String inTitle, String inAbstract, Files inPicture, String inUrl, Product inProduct) {
		this.lang = inLang;
		this.product = inProduct;
		this.file = inPicture;
		this.title = inTitle;
		this.url = inUrl;
		this.abstractPress = inAbstract;
	}
}

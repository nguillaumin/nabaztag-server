package net.violet.vadmin.forms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.violet.common.StringShop;

import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;

public final class AdminAddPressForm extends AdminForm {

	private static final long serialVersionUID = 1L;

	private String dispatch;

	private String title = StringShop.EMPTY_STRING;
	private String summary = StringShop.EMPTY_STRING;;
	private String url = StringShop.EMPTY_STRING;;
	private String product;
	private String path;
	private FormFile imageFile;

	private List<String> productList = Collections.emptyList();
	private ArrayList<LabelValueBean> namesList = new ArrayList<LabelValueBean>();

	public String getDispatch() {
		return this.dispatch;
	}

	public void setDispatch(String dispatch) {
		this.dispatch = dispatch;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String extract) {
		this.summary = extract;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getProduct() {
		return this.product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public List<String> getProductList() {
		return this.productList;
	}

	public void setProductList(List<String> productList) {
		this.productList = productList;
	}

	public ArrayList<LabelValueBean> getNamesList() {
		for (final String inProductName : this.productList) {
			this.namesList.add(new LabelValueBean(inProductName, inProductName));
		}
		return this.namesList;
	}

	public void setNamesList(ArrayList<LabelValueBean> inNamesList) {
		this.namesList = inNamesList;
	}

	public FormFile getImageFile() {
		return this.imageFile;
	}

	public void setImageFile(FormFile imageFile) {
		this.imageFile = imageFile;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}

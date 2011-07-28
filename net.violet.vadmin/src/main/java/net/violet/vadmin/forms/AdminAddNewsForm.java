package net.violet.vadmin.forms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.violet.common.StringShop;

import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;

public final class AdminAddNewsForm extends AdminForm {

	private static final long serialVersionUID = 1L;

	private String dispatch;

	private String title = StringShop.EMPTY_STRING;
	private String intro = StringShop.EMPTY_STRING;
	private String body = StringShop.EMPTY_STRING;
	private String extract = StringShop.EMPTY_STRING;
	private String pubYear = StringShop.EMPTY_STRING;;
	private String pubMonth = StringShop.EMPTY_STRING;;
	private String pubDay = StringShop.EMPTY_STRING;;
	private String expYear = StringShop.EMPTY_STRING;;
	private String expMonth = StringShop.EMPTY_STRING;;
	private String expDay = StringShop.EMPTY_STRING;;
	private String product;
	private FormFile smallImageFile;
	private FormFile bigImageFile;

	private List<String> productList = Collections.emptyList();
	private ArrayList<LabelValueBean> namesList = new ArrayList<LabelValueBean>();

	public String getDispatch() {
		return dispatch;
	}

	public void setDispatch(String dispatch) {
		this.dispatch = dispatch;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getExtract() {
		return extract;
	}

	public void setExtract(String extract) {
		this.extract = extract;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public List<String> getProductList() {
		return productList;
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

	public void setNamesList(ArrayList<LabelValueBean> namesList) {
		this.namesList = namesList;
	}

	
	public FormFile getSmallImageFile() {
		return smallImageFile;
	}

	
	public void setSmallImageFile(FormFile smallImageFile) {
		this.smallImageFile = smallImageFile;
	}

	
	public FormFile getBigImageFile() {
		return bigImageFile;
	}

	
	public void setBigImageFile(FormFile bigImageFile) {
		this.bigImageFile = bigImageFile;
	}

	
	public String getPubYear() {
		return pubYear;
	}

	
	public void setPubYear(String pubYear) {
		this.pubYear = pubYear;
	}

	
	public String getPubMonth() {
		return pubMonth;
	}

	
	public void setPubMonth(String pubMonth) {
		this.pubMonth = pubMonth;
	}

	
	public String getPubDay() {
		return pubDay;
	}

	
	public void setPubDay(String pubDay) {
		this.pubDay = pubDay;
	}

	
	public String getExpYear() {
		return expYear;
	}

	
	public void setExpYear(String expYear) {
		this.expYear = expYear;
	}

	
	public String getExpMonth() {
		return expMonth;
	}

	
	public void setExpMonth(String expMonth) {
		this.expMonth = expMonth;
	}

	
	public String getExpDay() {
		return expDay;
	}

	
	public void setExpDay(String expDay) {
		this.expDay = expDay;
	}

	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

}

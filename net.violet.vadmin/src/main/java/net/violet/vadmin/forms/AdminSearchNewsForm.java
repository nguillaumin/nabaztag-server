package net.violet.vadmin.forms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.violet.vadmin.objects.data.GetNewsData;

import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;


public final class AdminSearchNewsForm extends AdminForm {

	private static final long serialVersionUID = 1L;
	
	private String dispatch;
	private String display;
	private String product;
	private String idNews;
	private String title;
	private String summary;
	private String intro;
	private String body;
	private String pubYear;
	private String pubMonth;
	private String pubDay;
	private String expYear;
	private String expMonth;
	private String expDay;
	private String datePub;
	private String dateExp;
	private FormFile smallImageFile;
	private FormFile bigImageFile;

	private GetNewsData theNewsData;
	private List<GetNewsData> newsList = Collections.emptyList();
	private List<String> productList = Collections.emptyList();
	private ArrayList<LabelValueBean> namesList = new ArrayList<LabelValueBean>();

	public String getDispatch() {
		return this.dispatch;
	}

	public void setDispatch(String dispatch) {
		this.dispatch = dispatch;
	}

	public String getDisplay() {
		return this.display;
	}

	public void setDisplay(String display) {
		this.display = display;
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

	public List<GetNewsData> getNewsList() {
		return this.newsList;
	}

	public void setNewsList(List<GetNewsData> newsList) {
		this.newsList = newsList;
	}

	public String getIdNews() {
		return idNews;
	}

	public void setIdNews(String idNews) {
		this.idNews = idNews;
	}

	public GetNewsData getTheNewsData() {
		return theNewsData;
	}

	public void setTheNewsData(GetNewsData theNewsData) {
		this.theNewsData = theNewsData;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDatePub() {
		return datePub;
	}

	public void setDatePub(String datePub) {
		this.datePub = datePub;
	}

	public String getDateExp() {
		return dateExp;
	}

	public void setDateExp(String dateExp) {
		this.dateExp = dateExp;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
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

}

package net.violet.vadmin.objects.data;

import net.violet.commondev.utils.StringShop;

public class GetNewsData  extends AdminData{

	private String id;
	private String lang;
	private String title;
	private String newsabstract;
	private String picture_small;
	private String picture_smallURL;
	private String picture_big;
	private String picture_bigURL;
	private String intro;
	private String body;
	private String date_pub;
	private String date_exp;
	private String pubYear = StringShop.EMPTY_STRING;;
	private String pubMonth = StringShop.EMPTY_STRING;;
	private String pubDay = StringShop.EMPTY_STRING;;
	private String expYear = StringShop.EMPTY_STRING;;
	private String expMonth = StringShop.EMPTY_STRING;;
	private String expDay = StringShop.EMPTY_STRING;;
	private String product;
	

	public String getPicture_small() {
		return this.picture_small;
	}

	public void setPicture_small(String picture_small) {
		this.picture_small = picture_small;
		this.picture_smallURL = makePictureURL(picture_small);
	}
	
	
	public String getPicture_smallURL() {
		return picture_smallURL;
	}
	
	public void setPicture_smallURL(String picture_smallURL) {
		this.picture_smallURL = picture_smallURL;
	}

	public void setPicture_smallURL() {
		if(this.picture_small != null)	
			this.picture_smallURL = makePictureURL(picture_small);
	}

	
	public String getPicture_big() {
		return this.picture_big;
	}

	public void setPicture_big(String picture_big) {
		this.picture_big = picture_big;
		this.picture_bigURL = makePictureURL(picture_big);
	}
	
	
	public String getPicture_bigURL() {
		return picture_bigURL;
	}

	
	public void setPicture_bigURL(String picture_bigURL) {
		this.picture_bigURL = picture_bigURL;
	}

	
	public void setPicture_bigURL() {
		if(this.picture_big != null)	
			this.picture_bigURL = makePictureURL(picture_big);
	}


	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNewsabstract() {
		return this.newsabstract;
	}

	public void setNewsabstract(String newsabstract) {
		this.newsabstract = newsabstract;
	}

	public String getIntro() {
		return this.intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getProduct() {
		return this.product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	
	public String getDate_pub() {
		return this.date_pub;
	}

	public void setDate_pub(String date_pub) {
		this.date_pub = date_pub;
	}

	public void setDate_pub(Object inDatePub) {
		this.date_pub = StringShop.EMPTY_STRING;
		if (inDatePub != null) {
			this.date_pub = inDatePub.toString();
		}
	}

	public String getDate_exp() {
		return this.date_exp;
	}

	public void setDate_exp(String date_exp) {
		this.date_exp = date_exp;
	}

	public void setDate_exp(Object InDateExp) {
		this.date_exp = StringShop.EMPTY_STRING;
		if (InDateExp != null) {
			this.date_exp = InDateExp.toString();
		}
	}
}

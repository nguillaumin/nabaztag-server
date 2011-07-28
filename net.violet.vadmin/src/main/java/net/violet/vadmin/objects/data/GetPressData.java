package net.violet.vadmin.objects.data;


public class GetPressData extends AdminData{

	private String id;
	private String lang;
	private String title;
	private String summary;
	private String url;
	private String product;
	private String picture;
	private String pictureURL;



	public String getPicture() {
		return this.picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
		this.pictureURL = makePictureURL(picture);
	}
	
	public String getPictureURL() {
		return pictureURL;
	}

	public void setPictureURL(String pictureURL) {	
		this.pictureURL=pictureURL;
	}
	
	public void setPictureURL() {
		if(picture != null)	
			this.pictureURL = makePictureURL(picture);
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

	public String getProduct() {
		return this.product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

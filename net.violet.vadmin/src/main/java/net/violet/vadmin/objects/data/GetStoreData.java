package net.violet.vadmin.objects.data;

import net.violet.common.StringShop;

public class GetStoreData  extends AdminData{

	private String id = StringShop.EMPTY_STRING;
	private String name = StringShop.EMPTY_STRING;
	private String type = StringShop.EMPTY_STRING;
	private String address = StringShop.EMPTY_STRING;
	private String zipCode = StringShop.EMPTY_STRING;
	private String city = StringShop.EMPTY_STRING;
	private String status = StringShop.EMPTY_STRING;
	private String url = StringShop.EMPTY_STRING;
	private String rank = StringShop.EMPTY_STRING;
	private String comment = StringShop.EMPTY_STRING;
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


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String inType) {
		this.type = inType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUrl() {
		if (url != null)
			return url;
		return StringShop.EMPTY_STRING;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getComment() {
		if (comment != null)
			return comment;
		return StringShop.EMPTY_STRING;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}

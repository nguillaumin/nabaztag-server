package net.violet.vadmin.objects.data;

import java.util.List;

import net.violet.commondev.utils.StringShop;


public class GetAdminData  extends AdminData{
	
	private String title;
	private String description;
	private String instructions;
	private String category;
	private String visible;
	private String picture;
	private String pictureURL;
	private String icon;
	private String iconURL;
	private String announcement;
	
	
	private String language;
	private String shortcut;
	private String url;
	private String id;
	private String name;
	private String owner;
	private String creation_date;
	private String link;
	private List<String> supported_hardware;
	private List<String> languages;
	
	
	public List<String> getLanguages() {
		return languages;
	}
	
	public void setLanguages(List<String> inLanguages) {
		this.languages = inLanguages;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String inCategory) {
		this.category = inCategory;
	}

	public String getUrl() {
		return url;
	}
	
	public void setUrl(String inURL) {
		this.url = inURL;
	}
	
	public String getLink() {
		return link;
	}

	public void setLink(String inLink) {
		this.link = inLink;
	}

	public String getShortcut() {
		if(shortcut != null)
			return shortcut;
		return StringShop.EMPTY_STRING;
	}
	
	public void setShortcut(String inShortcut) {
		this.shortcut = inShortcut;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String inId) {
		this.id = inId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String inName) {
		this.name = inName;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public void setOwner(String inOwner) {
		this.owner = inOwner;
	}
	
	public List<String> getSupported_hardware() {
		return supported_hardware;
	}
	
	public void setSupported_hardware(List<String> supported_hardware) {
		this.supported_hardware = supported_hardware;
	}
	
	public String getCreation_date() {
		return creation_date;
	}
	
	public void setCreation_date(String inCreation_date) {
		this.creation_date = inCreation_date;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String inTitle) {
		this.title = inTitle;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String inDescription) {
		this.description = inDescription;
	}
	
	public String getInstructions() {
		return instructions;
	}
	
	public void setInstructions(String inInstructions) {
		this.instructions = inInstructions;
	}
	
	public String getVisible() {
		return visible;
	}
	
	public void setVisible(String inVisible) {
		this.visible = inVisible;
	}
	
	public String getPicture() {
		return picture;
	}
	
	public void setPicture(String inPicture) {
		this.picture = inPicture;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String inIcon) {
		this.icon = inIcon;
	}
	
	public String getPictureURL() {
		return pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL =  makePictureURL(pictureURL);
	}
	
	public String getIconURL() {
		return iconURL;
	}

	public void setIconURL(String iconURL) {
		this.iconURL = makePictureURL(iconURL);
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String inLanguage) {
		this.language = inLanguage;
	}

	
	public String getAnnouncement() {
		return announcement;
	}

	
	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}

}

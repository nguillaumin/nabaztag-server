package net.violet.vadmin.objects.data;


public class ServiceData {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String language;
	private String url;
	
	public ServiceData(String inId, String inLanguage, String inUrl){
		this.id = inId;
		this.language = inLanguage;
		this.url = inUrl;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}

	
	public String getUrl() {
		return url;
	}

	
	public void setUrl(String url) {
		this.url = url;
	}
	

}

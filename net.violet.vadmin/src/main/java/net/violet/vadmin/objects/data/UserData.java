package net.violet.vadmin.objects.data;

import java.util.List;


public class UserData {

	private String user_id;
	private String user_firstName;
	private String user_lastName;
	private String user_mail;
	private String user_password;
	private String user_lang;
	private String user_creationDate;
	private List<ObjectData> user_objects;
	
	
	public String getUser_id() {
		return user_id;
	}
	
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public String getUser_firstName() {
		return user_firstName;
	}
	
	public void setUser_firstName(String user_firstName) {
		this.user_firstName = user_firstName;
	}
	
	public String getUser_lastName() {
		return user_lastName;
	}
	
	public void setUser_lastName(String user_lastName) {
		this.user_lastName = user_lastName;
	}
	
	public String getUser_mail() {
		return user_mail;
	}
	
	public void setUser_mail(String user_mail) {
		this.user_mail = user_mail;
	}
	
	public String getUser_password() {
		return user_password;
	}
	
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	
	public String getUser_lang() {
		return user_lang;
	}
	
	public void setUser_lang(String user_lang) {
		this.user_lang = user_lang;
	}
	
	public String getUser_creationDate() {
		return user_creationDate;
	}
	
	public void setUser_creationDate(String user_creationDate) {
		this.user_creationDate = user_creationDate;
	}

	
	public List<ObjectData> getUser_objects() {
		return user_objects;
	}

	
	public void setUser_objects(List<ObjectData> user_objects) {
		this.user_objects = user_objects;
	}
	
	
}

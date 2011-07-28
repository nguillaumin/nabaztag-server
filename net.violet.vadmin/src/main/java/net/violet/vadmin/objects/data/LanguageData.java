package net.violet.vadmin.objects.data;

import java.io.Serializable;

public class LanguageData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String iso_code;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIso_code() {
		return this.iso_code;
	}

	public void setIso_code(String iso_code) {
		this.iso_code = iso_code;
	}

}

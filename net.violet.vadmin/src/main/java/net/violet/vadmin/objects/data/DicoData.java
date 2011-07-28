package net.violet.vadmin.objects.data;

import java.io.Serializable;


public class DicoData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String iso_code;
	private String value;
	

	public DicoData(String inId, String inIsoCode, String inValue){
		id = inId;
		iso_code = inIsoCode;
		value = inValue;
	}
	
	public DicoData(String inIsoCode, String inValue){
		iso_code = inIsoCode;
		value = inValue;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIso_code() {
		return iso_code;
	}

	public void setIso_code(String iso_code) {
		this.iso_code = iso_code;
	}

	
	public String getId() {
		return id;
	}

	
	public void setId(String id) {
		this.id = id;
	}

}

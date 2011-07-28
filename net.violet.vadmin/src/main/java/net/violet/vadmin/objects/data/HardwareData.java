package net.violet.vadmin.objects.data;

public class HardwareData {

	private String name;
	private String code;

	public HardwareData(String inName, String inCode){
		this.name = inName;
		this.code = inCode;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}

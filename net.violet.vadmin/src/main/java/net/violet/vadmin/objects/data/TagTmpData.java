package net.violet.vadmin.objects.data;

public class TagTmpData {

	private String tagTmp_type;
	private String tagTmp_lastDay;
	private String tagTmp_ip;

	public TagTmpData(String inType, String inLastDay, String inIP){
		this.tagTmp_type = inType;
		this.tagTmp_lastDay = inLastDay;
		this.tagTmp_ip = inIP;
	}
	
	public String getTagTmp_type() {
		return this.tagTmp_type;
	}

	public void setTagTmp_type(String tagTmp_type) {
		this.tagTmp_type = tagTmp_type;
	}

	public String getTagTmp_lastDay() {
		return this.tagTmp_lastDay;
	}

	public void setTagTmp_lastDay(String tagTmp_lastDay) {
		this.tagTmp_lastDay = tagTmp_lastDay;
	}

	public String getTagTmp_ip() {
		return this.tagTmp_ip;
	}

	public void setTagTmp_ip(String tagTmp_ip) {
		this.tagTmp_ip = tagTmp_ip;
	}

}

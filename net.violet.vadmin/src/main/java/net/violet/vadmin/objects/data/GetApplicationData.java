package net.violet.vadmin.objects.data;

import java.io.Serializable;


public class GetApplicationData extends AdminData implements Serializable{
	
	private String id;
	private String name;
	private String rank;
	
	public GetApplicationData(String inId, String inName, String inRank){
		this.id = inId;
		this.name = inName;
		this.rank = inRank;
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

	
	public String getRank() {
		return rank;
	}

	
	public void setRank(String rank) {
		this.rank = rank;
	}
}

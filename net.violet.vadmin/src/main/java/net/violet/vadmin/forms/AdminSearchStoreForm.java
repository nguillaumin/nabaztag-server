package net.violet.vadmin.forms;

import java.util.List;

import net.violet.vadmin.objects.data.GetStoreData;

import org.apache.struts.upload.FormFile;


public final class AdminSearchStoreForm extends AdminLocationForm {

	private static final long serialVersionUID = 1L;
	
	private String idStore;
	private GetStoreData theStoreData;
	private List<GetStoreData> storeList;
	private FormFile imageFile;

	public String getIdStore() {
		return idStore;
	}

	public void setIdStore(String idStore) {
		this.idStore = idStore;
	}

	public GetStoreData getTheStoreData() {
		return theStoreData;
	}

	public void setTheStoreData(GetStoreData theStoreData) {
		this.theStoreData = theStoreData;
	}

	public List<GetStoreData> getStoreList() {
		return storeList;
	}

	public void setStoreList(List<GetStoreData> storeList) {
		this.storeList = storeList;
	}

	public FormFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(FormFile imageFile) {
		this.imageFile = imageFile;
	}
}

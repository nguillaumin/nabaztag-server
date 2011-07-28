package net.violet.vadmin.forms;

import org.apache.struts.upload.FormFile;


public final class AdminAddStoreForm extends AdminLocationForm {

	private static final long serialVersionUID = 1L;
	
	private FormFile imageFile;

	
	public FormFile getImageFile() {
		return imageFile;
	}

	
	public void setImageFile(FormFile imageFile) {
		this.imageFile = imageFile;
	}
	
}

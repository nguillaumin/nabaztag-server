package net.violet.mynabaztag.form;

import org.apache.struts.upload.FormFile;

public class MyTerrierEditRabbitImageForm extends AbstractForm {

	public static final long serialVersionUID = 1;

	private int userImage;
	private int modify;
	private FormFile imageFile;

	public int getUserImage() {
		return this.userImage;
	}

	public void setUserImage(int userImage) {
		this.userImage = userImage;
	}

	public int getModify() {
		return this.modify;
	}

	public void setModify(int modify) {
		this.modify = modify;
	}

	public FormFile getImageFile() {
		return this.imageFile;
	}

	public void setImageFile(FormFile imageFile) {
		this.imageFile = imageFile;
	}
}

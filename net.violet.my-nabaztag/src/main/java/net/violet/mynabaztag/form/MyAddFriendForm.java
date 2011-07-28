package net.violet.mynabaztag.form;

import net.violet.platform.dataobjects.UserData;

public class MyAddFriendForm extends AbstractForm {

	public static final long serialVersionUID = 1;

	private int friend_id; // celui que l'on souhaite ajouter a sa liste d'amis.
	private UserData Profil;
	private int mode;

	public int getMode() {
		return this.mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public UserData getProfil() {
		return this.Profil;
	}

	public void setProfil(UserData profil) {
		this.Profil = profil;
	}

	public int getFriend_id() {
		return this.friend_id;
	}

	public void setFriend_id(int friend_id) {
		this.friend_id = friend_id;
	}
}

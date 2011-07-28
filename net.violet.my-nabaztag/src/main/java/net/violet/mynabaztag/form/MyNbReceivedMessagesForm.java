package net.violet.mynabaztag.form;

import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.StringShop;

public class MyNbReceivedMessagesForm extends AbstractForm {

	// test de cette constante
	public static final long serialVersionUID = 1;

	private int userId;
	private int friendObjectId;
	private String langUser = StringShop.EMPTY_STRING;
	private UserData userData = UserData.getData(null);
	private String affNbMessages = StringShop.EMPTY_STRING;

	public UserData getUserData() {
		return this.userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	public int getFriendObjectId() {
		return this.friendObjectId;
	}

	public void setFriendObjectId(int friendObjectId) {
		this.friendObjectId = friendObjectId;
	}

	public String getLangUser() {
		return this.langUser;
	}

	public void setLangUser(String langUser) {
		this.langUser = langUser;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAffNbMessages() {
		return this.affNbMessages;
	}

	public void setAffNbMessages(String affNbMessages) {
		this.affNbMessages = affNbMessages;
	}

}

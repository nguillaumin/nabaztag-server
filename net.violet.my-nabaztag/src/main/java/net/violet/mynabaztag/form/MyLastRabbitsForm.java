package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.StringShop;

public class MyLastRabbitsForm extends AbstractForm {

	private static final long serialVersionUID = -269420139984228140L;

	private String userLang = StringShop.EMPTY_STRING;
	private List<UserData> rabbitsList = new ArrayList<UserData>();

	public String getUserLang() {
		return this.userLang;
	}

	public void setUserLang(String userLang) {
		this.userLang = userLang;
	}

	public List<UserData> getRabbitsList() {
		return this.rabbitsList;
	}

	public void setRabbitsList(List<UserData> rabbitsList) {
		this.rabbitsList = rabbitsList;
	}
}

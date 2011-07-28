package net.violet.mynabaztag.form;

import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.StringShop;

public class MyToolsNabgetsForm extends AbstractForm {

	public static final long serialVersionUID = 1;

	private int userId;
	private String langUser = "2";
	private UserData userData = UserData.getData(null);
	private String onglet = StringShop.EMPTY_STRING;

	/**
	 * @return the userData
	 */
	public final UserData getUserData() {
		return this.userData;
	}

	/**
	 * @param userData the userData to set
	 */
	public final void setUserData(UserData userData) {
		this.userData = userData;
	}

	/**
	 * @return the langUser
	 */
	public final String getLangUser() {
		return this.langUser;
	}

	/**
	 * @param langUser the langUser to set
	 */
	public final void setLangUser(String langUser) {
		this.langUser = langUser;
	}

	/**
	 * @return the onglet
	 */
	public final String getOnglet() {
		return this.onglet;
	}

	/**
	 * @param onglet the onglet to set
	 */
	public final void setOnglet(String onglet) {
		this.onglet = onglet;
	}

	/**
	 * @return the userId
	 */
	public final int getUserId() {
		return this.userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public final void setUserId(int userId) {
		this.userId = userId;
	}
}

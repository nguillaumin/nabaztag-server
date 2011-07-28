package net.violet.mynabaztag.form;

import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.StringShop;

public class MyTerrierForm extends AbstractForm {

	// test de cette constante
	public static final long serialVersionUID = 1;

	private int userId;
	private String langUser = "2";
	private UserData userData = UserData.getData(null);
	private String onglet = StringShop.EMPTY_STRING;
	private String popup = StringShop.EMPTY_STRING;

	/**
	 * @return the popup
	 */
	public final String getPopup() {
		return this.popup;
	}

	/**
	 * @param popup the popup to set
	 */
	public final void setPopup(String popup) {
		this.popup = popup;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLangUser() {
		return this.langUser;
	}

	public void setLangUser(String langUser) {
		this.langUser = langUser;
	}

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
}

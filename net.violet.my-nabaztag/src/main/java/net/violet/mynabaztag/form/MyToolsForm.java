package net.violet.mynabaztag.form;

import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.StringShop;

public class MyToolsForm extends AbstractForm {

	public static final long serialVersionUID = 1;

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

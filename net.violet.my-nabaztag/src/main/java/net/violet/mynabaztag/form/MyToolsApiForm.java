package net.violet.mynabaztag.form;

import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.StringShop;

public class MyToolsApiForm extends AbstractForm {

	public static final long serialVersionUID = 1;

	private UserData userData = UserData.getData(null);
	private String onglet = StringShop.EMPTY_STRING;
	private String CheckAPI = StringShop.EMPTY_STRING;
	private long extConnect;
	private String numSerie = StringShop.EMPTY_STRING;
	private int mode;

	public int getMode() {
		return this.mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	/**
	 * @return the numSerie
	 */
	public final String getNumSerie() {
		return this.numSerie;
	}

	/**
	 * @param numSerie the numSerie to set
	 */
	public final void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	/**
	 * @return the extConnect
	 */
	public final long getExtConnect() {
		return this.extConnect;
	}

	/**
	 * @param extConnect the extConnect to set
	 */
	public final void setExtConnect(long extConnect) {
		this.extConnect = extConnect;
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

	/**
	 * @return the checkAPI
	 */
	public final String getCheckAPI() {
		return this.CheckAPI;
	}

	/**
	 * @param checkAPI the checkAPI to set
	 */
	public final void setCheckAPI(String checkAPI) {
		this.CheckAPI = checkAPI;
	}
}

package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.StringShop;

public class MyMessagesForm extends AbstractForm {

	// test de cette constante
	public static final long serialVersionUID = 1;

	private long userId;
	private String friendObjectName = StringShop.EMPTY_STRING;
	private String langUser = StringShop.EMPTY_STRING;
	private List<UserData> listeAmis = new ArrayList<UserData>();
	private UserData userData = UserData.getData(null);
	private String onglet = StringShop.EMPTY_STRING;
	private String pseudoAleatoire = StringShop.EMPTY_STRING;
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

	/**
	 * @return the onglet
	 */
	public String getOnglet() {
		return this.onglet;
	}

	/**
	 * @param onglet the onglet to set
	 */
	public void setOnglet(String onglet) {
		this.onglet = onglet;
	}

	/**
	 * @return the userData
	 */
	public UserData getUserData() {
		return this.userData;
	}

	public List<UserData> getListeAmis() {
		return this.listeAmis;
	}

	public void setListeAmis(List<UserData> listeAmis) {
		this.listeAmis = listeAmis;
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getLangUser() {
		return this.langUser;
	}

	public void setLangUser(String langUser) {
		this.langUser = langUser;
	}

	public String getFriendObjectName() {
		return this.friendObjectName;
	}

	public void setFriendObjectName(String friendObjectName) {
		this.friendObjectName = friendObjectName;
	}

	/**
	 * @param userData the userData to set
	 */
	public final void setUserData(UserData userData) {
		this.userData = userData;
	}

	/**
	 * @return the pseudoAleatoire
	 */
	public String getPseudo() {
		return this.pseudoAleatoire;
	}

	/**
	 * @param pseudo the pseudo to set
	 */
	public void setPseudo(String pseudo) {
		this.pseudoAleatoire = pseudo;
	}
}

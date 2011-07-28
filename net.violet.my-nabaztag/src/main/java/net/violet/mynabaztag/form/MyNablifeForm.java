package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.MotherTongueLangData;
import net.violet.platform.dataobjects.NabcastData;
import net.violet.platform.dataobjects.NablifeServicesData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.StringShop;

public class MyNablifeForm extends AbstractForm {

	// test de cette constante
	public static final long serialVersionUID = 1;

	private int userId;
	private int friendObjectId;
	private String langUser = "2";
	private String userPseudo = StringShop.EMPTY_STRING;
	private long nbMsgReceived;
	private UserData userData = UserData.getData(null);
	private String popup = StringShop.EMPTY_STRING;
	private int notLogged;
	private String onglet = StringShop.EMPTY_STRING;
	private String serviceToConfigure = StringShop.EMPTY_STRING;
	private String categoryId = "-1";
	private String categoryLang = "-1";
	private int objectId;
	private NablifeServicesData srvListData = new NablifeServicesData(null, null);
	private List<NabcastData> nabcastData = new ArrayList<NabcastData>();
	private boolean fromSearch;
	private String userTimeZone;
	private String searched = StringShop.EMPTY_STRING;
	private int badLogin;
	private List<MotherTongueLangData> langList = new ArrayList<MotherTongueLangData>();

	public List<MotherTongueLangData> getLangList() {
		return this.langList;
	}

	public void setLangList(List<MotherTongueLangData> langList) {
		this.langList = langList;
	}

	public List<NabcastData> getNabcastData() {
		return this.nabcastData;
	}

	public void setNabcastData(List<NabcastData> nabcasts) {
		this.nabcastData = nabcasts;
	}

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

	public long getNbMsgReceived() {
		return this.nbMsgReceived;
	}

	public void setNbMsgReceived(long nbMsgReceived) {
		this.nbMsgReceived = nbMsgReceived;
	}

	public String getUserPseudo() {
		return this.userPseudo;
	}

	public void setUserPseudo(String userPseudo) {
		this.userPseudo = userPseudo;
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

	public int getFriendObjectId() {
		return this.friendObjectId;
	}

	public void setFriendObjectId(int friendObjectId) {
		this.friendObjectId = friendObjectId;
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

	public int getNotLogged() {
		return this.notLogged;
	}

	public void setNotLogged(int notLogged) {
		this.notLogged = notLogged;
	}

	public String getOnglet() {
		return this.onglet;
	}

	public void setOnglet(String onglet) {
		this.onglet = onglet;
	}

	public String getServiceToConfigure() {
		return this.serviceToConfigure;
	}

	public void setServiceToConfigure(String serviceToConfigure) {
		this.serviceToConfigure = serviceToConfigure;
	}

	public String getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryLang() {
		return this.categoryLang;
	}

	public void setCategoryLang(String categoryLang) {
		this.categoryLang = categoryLang;
	}

	public int getObjectId() {
		return this.objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public NablifeServicesData getSrvListData() {
		return this.srvListData;
	}

	public void setSrvListData(NablifeServicesData srvListData) {
		this.srvListData = srvListData;
	}

	public boolean isFromSearch() {
		return this.fromSearch;
	}

	public void setFromSearch(boolean fromSearch) {
		this.fromSearch = fromSearch;
	}

	public String getUserTimeZone() {
		return this.userTimeZone;
	}

	public void setUserTimeZone(String userTimeZone) {
		this.userTimeZone = userTimeZone;
	}

	public String getSearched() {
		return this.searched;
	}

	public void setSearched(String searched) {
		this.searched = searched;
	}

	public int getBadLogin() {
		return this.badLogin;
	}

	public void setBadLogin(int badLogin) {
		this.badLogin = badLogin;
	}

}

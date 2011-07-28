package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.util.StringShop;


public class MyTerrierInfoForm extends AbstractForm {

	// test de cette constante
	public static final long serialVersionUID = 1;

	private String langUser = StringShop.EMPTY_STRING;
	private int userId;

	private String pseudo = StringShop.EMPTY_STRING;
	private String pseudoTmp = StringShop.EMPTY_STRING;

	private String email = StringShop.EMPTY_STRING;
	private String emailTmp = StringShop.EMPTY_STRING;

	private String queFaire = StringShop.EMPTY_STRING;

	private String err_message_pseudo = StringShop.EMPTY_STRING;
	private String err_message_mail = StringShop.EMPTY_STRING;
	private String err_message_password = StringShop.EMPTY_STRING;

	private String timeZone = StringShop.EMPTY_STRING;

	private String oldPassword = StringShop.EMPTY_STRING;
	private String newPassword = StringShop.EMPTY_STRING;
	private String newPasswordVerif = StringShop.EMPTY_STRING;

	private int msgSent;
	private int msgPlayed;
	private int msgReceived;
	private int newsletter;

	private int authorisation;
	private int notification;
	private int filtre;
	private int antispam;

	private int user_24;

	private String layout = "layout_green";

	List listTimeZone = new ArrayList();

	private String macAddress = StringShop.EMPTY_STRING;
	private int isConnected;
	private String[] checkListLang = {};

	public int getIsConnected() {
		return this.isConnected;
	}

	public void setIsConnected(int isConnected) {
		this.isConnected = isConnected;
	}

	public String getMacAddress() {
		return this.macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String[] getCheckListLang() {
		return this.checkListLang;
	}

	public void setCheckListLang(String[] checkListLang) {
		this.checkListLang = checkListLang;
	}

	public String getLayout() {
		return this.layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public int getUser_24() {
		return this.user_24;
	}

	public void setUser_24(int user_24) {
		this.user_24 = user_24;
	}

	public int getAntispam() {
		return this.antispam;
	}

	public void setAntispam(int antispam) {
		this.antispam = antispam;
	}

	public int getAuthorisation() {
		return this.authorisation;
	}

	public void setAuthorisation(int authorisation) {
		this.authorisation = authorisation;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailTmp() {
		return this.emailTmp;
	}

	public void setEmailTmp(String emailTmp) {
		this.emailTmp = emailTmp;
	}

	public String getErr_message_mail() {
		return this.err_message_mail;
	}

	public void setErr_message_mail(String err_message_mail) {
		this.err_message_mail = err_message_mail;
	}

	public String getErr_message_password() {
		return this.err_message_password;
	}

	public void setErr_message_password(String err_message_password) {
		this.err_message_password = err_message_password;
	}

	public String getErr_message_pseudo() {
		return this.err_message_pseudo;
	}

	public void setErr_message_pseudo(String err_message_pseudo) {
		this.err_message_pseudo = err_message_pseudo;
	}

	public int getFiltre() {
		return this.filtre;
	}

	public void setFiltre(int filtre) {
		this.filtre = filtre;
	}

	public String getLangUser() {
		return this.langUser;
	}

	public void setLangUser(String langUser) {
		this.langUser = langUser;
	}

	public List getListTimeZone() {
		return this.listTimeZone;
	}

	public void setListTimeZone(List listTimeZone) {
		this.listTimeZone = listTimeZone;
	}

	public int getMsgPlayed() {
		return this.msgPlayed;
	}

	public void setMsgPlayed(int msgPlayed) {
		this.msgPlayed = msgPlayed;
	}

	public int getMsgReceived() {
		return this.msgReceived;
	}

	public void setMsgReceived(int msgReceived) {
		this.msgReceived = msgReceived;
	}

	public int getMsgSent() {
		return this.msgSent;
	}

	public void setMsgSent(int msgSent) {
		this.msgSent = msgSent;
	}

	public String getNewPassword() {
		return this.newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPasswordVerif() {
		return this.newPasswordVerif;
	}

	public void setNewPasswordVerif(String newPasswordVerif) {
		this.newPasswordVerif = newPasswordVerif;
	}

	public int getNewsletter() {
		return this.newsletter;
	}

	public void setNewsletter(int newsletter) {
		this.newsletter = newsletter;
	}

	public int getNotification() {
		return this.notification;
	}

	public void setNotification(int notification) {
		this.notification = notification;
	}

	public String getOldPassword() {
		return this.oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getPseudo() {
		return this.pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getPseudoTmp() {
		return this.pseudoTmp;
	}

	public void setPseudoTmp(String pseudoTmp) {
		this.pseudoTmp = pseudoTmp;
	}

	public String getQueFaire() {
		return this.queFaire;
	}

	public void setQueFaire(String queFaire) {
		this.queFaire = queFaire;
	}

	public String getTimeZone() {
		return this.timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}

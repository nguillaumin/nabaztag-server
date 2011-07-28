package net.violet.mynabaztag.form;

import net.violet.platform.util.StringShop;

public class MySessionForm extends AbstractForm {

	public static final long serialVersionUID = 1;

	private String langUser = StringShop.EMPTY_STRING;
	private String action = StringShop.EMPTY_STRING;
	private String password = StringShop.EMPTY_STRING;
	private String pseudo = StringShop.EMPTY_STRING;
	private String forward = StringShop.EMPTY_STRING;
	private int userId;
	private String url = "login";
	private int loginError;
	private String redirectUrl = StringShop.EMPTY_STRING;
	private String redirectUrlBadLogin = StringShop.EMPTY_STRING;

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getForward() {
		return this.forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getPseudo() {
		return this.pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLangUser() {
		return this.langUser;
	}

	public void setLangUser(String langUser) {
		this.langUser = langUser;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String inValue) {
		this.url = inValue;
	}

	public int getLoginError() {
		return this.loginError;
	}

	public void setLoginError(int loginError) {
		this.loginError = loginError;
	}

	public String getRedirectUrl() {
		return this.redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getRedirectUrlBadLogin() {
		return this.redirectUrlBadLogin;
	}

	public void setRedirectUrlBadLogin(String redirectUrlBadLogin) {
		this.redirectUrlBadLogin = redirectUrlBadLogin;
	}

}

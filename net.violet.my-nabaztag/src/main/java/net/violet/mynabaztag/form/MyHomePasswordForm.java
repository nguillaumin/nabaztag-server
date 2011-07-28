package net.violet.mynabaztag.form;

import net.violet.platform.util.StringShop;

public final class MyHomePasswordForm extends AbstractForm {

	private static final long serialVersionUID = 1L;

	private String user_login = StringShop.EMPTY_STRING;
	private String user_mail = StringShop.EMPTY_STRING;
	private int mode;
	private int ok = -1;

	public int getOk() {
		return this.ok;
	}

	public void setOk(int ok) {
		this.ok = ok;
	}

	public String getUser_login() {
		return this.user_login;
	}

	public void setUser_login(String user_login) {
		this.user_login = user_login;
	}

	public String getUser_mail() {
		return this.user_mail;
	}

	public void setUser_mail(String user_mail) {
		this.user_mail = user_mail;
	}

	public int getMode() {
		return this.mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}
}

package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.Collection;

import net.violet.platform.dataobjects.TtsLangData;

public class MySrvTwitterForm extends AbstractForm {

	private static final long serialVersionUID = 491597568795993297L;

	private int twitterLang;
	private Collection<TtsLangData> langList = new ArrayList<TtsLangData>();
	private String login;
	private String password;

	public Collection<TtsLangData> getLangList() {
		return this.langList;
	}

	public void setLangList(Collection<TtsLangData> langList) {
		this.langList = langList;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public int getTwitterLang() {
		return this.twitterLang;
	}

	public void setTwitterLang(int twitterLang) {
		this.twitterLang = twitterLang;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

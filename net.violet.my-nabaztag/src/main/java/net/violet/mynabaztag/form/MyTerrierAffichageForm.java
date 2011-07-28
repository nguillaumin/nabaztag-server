package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.Collection;

import net.violet.platform.dataobjects.TtsLangData;
import net.violet.platform.util.StringShop;

public class MyTerrierAffichageForm extends AbstractForm {

	private static final long serialVersionUID = 4837993982373465830L;

	private String langUser = StringShop.EMPTY_STRING;
	private String[] checkListLang = {};
	private String layout = "layout_green";
	private int mode;
	private Collection<TtsLangData> langList = new ArrayList<TtsLangData>();

	public Collection<TtsLangData> getLangList() {
		return this.langList;
	}

	public void setLangList(Collection<TtsLangData> langList) {
		this.langList = langList;
	}

	public int getMode() {
		return this.mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String getLangUser() {
		return this.langUser;
	}

	public void setLangUser(String langUser) {
		this.langUser = langUser;
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
}

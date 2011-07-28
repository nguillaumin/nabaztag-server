package net.violet.vadmin.forms;

import java.util.Collections;
import java.util.List;

import net.violet.vadmin.objects.data.LanguageData;


public class AdminForm extends AbstractForm {

	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String language;
	private List<LanguageData> langList = Collections.emptyList();

	
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<LanguageData> getLangList() {
		return this.langList;
	}

	public void setLangList(List<LanguageData> inSitelangs) {
		this.langList = inSitelangs;
	}

	public List<LanguageData> getSitelangs() {
		return this.langList;
	}

	public void setSitelangs(List<LanguageData> sitelangs) {
		this.langList = sitelangs;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String langService) {
		this.language = langService;
	}
}

package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.CategData;
import net.violet.platform.dataobjects.TagData;
import net.violet.platform.util.StringShop;

public class MyMessagesNabshareForm extends AbstractForm {

	// test de cette constante
	public static final long serialVersionUID = 1;

	private String langUser = StringShop.EMPTY_STRING;
	private List<CategData> listeCateg = new ArrayList<CategData>();
	private List<TagData> listeWordCloud = new ArrayList<TagData>();

	public List<TagData> getListeWordCloud() {
		return this.listeWordCloud;
	}

	public void setListeWordCloud(List<TagData> listeWordCloud) {
		this.listeWordCloud = listeWordCloud;
	}

	public String getLangUser() {
		return this.langUser;
	}

	public void setLangUser(String langUser) {
		this.langUser = langUser;
	}

	public List<CategData> getListeCateg() {
		return this.listeCateg;
	}

	public void setListeCateg(List<CategData> listeCateg) {
		this.listeCateg = listeCateg;
	}
}

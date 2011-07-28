package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.util.StringShop;

public class MyMessagesNabshareChoiceForm extends AbstractForm {

	// test de cette constante
	public static final long serialVersionUID = 1;

	private int idCateg;
	private String langUser = StringShop.EMPTY_STRING;
	private String tag = StringShop.EMPTY_STRING;
	private List<MusicData> listeCateg = new ArrayList<MusicData>();
	private MusicData listeNabshareRand = MusicData.getData(null);

	public int getIdCateg() {
		return this.idCateg;
	}

	public void setIdCateg(int idCateg) {
		this.idCateg = idCateg;
	}

	public String getLangUser() {
		return this.langUser;
	}

	public void setLangUser(String langUser) {
		this.langUser = langUser;
	}

	public List<MusicData> getListeCateg() {
		return this.listeCateg;
	}

	public void setListeCateg(List<MusicData> listeCateg) {
		this.listeCateg = listeCateg;
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public MusicData getListeNabshareRand() {
		return this.listeNabshareRand;
	}

	public void setListeNabshareRand(MusicData listeNabshareRand) {
		this.listeNabshareRand = listeNabshareRand;
	}
}

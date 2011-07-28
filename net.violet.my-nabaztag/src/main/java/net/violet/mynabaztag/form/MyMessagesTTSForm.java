package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.VoiceData;
import net.violet.platform.util.StringShop;

public class MyMessagesTTSForm extends AbstractForm {

	// test de cette constante
	public static final long serialVersionUID = 1;

	private int idVoice;
	private String langUser = StringShop.EMPTY_STRING;
	private List<VoiceData> listeVoix = new ArrayList<VoiceData>();

	public String getLangUser() {
		return this.langUser;
	}

	public void setLangUser(String langUser) {
		this.langUser = langUser;
	}

	public List<VoiceData> getListeVoix() {
		return this.listeVoix;
	}

	public void setListeVoix(List<VoiceData> listeVoix) {
		this.listeVoix = listeVoix;
	}

	public int getIdVoice() {
		return this.idVoice;
	}

	public void setIdVoice(int idVoice) {
		this.idVoice = idVoice;
	}
}

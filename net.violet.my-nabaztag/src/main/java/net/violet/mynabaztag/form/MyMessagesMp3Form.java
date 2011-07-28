package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.util.StringShop;

import org.apache.struts.upload.FormFile;

public class MyMessagesMp3Form extends AbstractForm {

	// test de cette constante
	public static final long serialVersionUID = 1;

	private int idMp3;
	private String langUser = StringShop.EMPTY_STRING;
	private List<MusicData> listeMp3 = new ArrayList<MusicData>();
	private List<MusicData> listeMp3Rand = new ArrayList<MusicData>();
	private FormFile musicFile;

	public FormFile getMusicFile() {
		return this.musicFile;
	}

	public void setMusicFile(FormFile musicFile) {
		this.musicFile = musicFile;
	}

	public String getLangUser() {
		return this.langUser;
	}

	public void setLangUser(String langUser) {
		this.langUser = langUser;
	}

	public int getIdMp3() {
		return this.idMp3;
	}

	public void setIdMp3(int idMp3) {
		this.idMp3 = idMp3;
	}

	public List<MusicData> getListeMp3() {
		return this.listeMp3;
	}

	public void setListeMp3(List<MusicData> listeMp3) {
		this.listeMp3 = listeMp3;
	}

	public void setListeMp3Rand(List<MusicData> listeMp3Rand) {
		this.listeMp3Rand = listeMp3Rand;
	}

	public List<MusicData> getListeMp3Rand() {
		return this.listeMp3Rand;
	}
}

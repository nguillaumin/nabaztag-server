package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.CategData;
import net.violet.platform.util.StringShop;

import org.apache.struts.upload.FormFile;

public class MyManageMp3Form extends AbstractForm {

	// test de cette constante
	public static final long serialVersionUID = 1;

	private long idMp3;
	private int categId;
	private String musicName = StringShop.EMPTY_STRING;
	private String shareMp3 = StringShop.EMPTY_STRING;
	private String cloudTag = StringShop.EMPTY_STRING;
	private FormFile musicFile;
	private String queFaire = StringShop.EMPTY_STRING;
	private String message = StringShop.EMPTY_STRING;
	private List<CategData> ListeCateg = new ArrayList<CategData>();

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getQueFaire() {
		return this.queFaire;
	}

	public void setQueFaire(String queFaire) {
		this.queFaire = queFaire;
	}

	public String getCloudTag() {
		return this.cloudTag;
	}

	public void setCloudTag(String cloudTag) {
		this.cloudTag = cloudTag;
	}

	public String getShareMp3() {
		return this.shareMp3;
	}

	public void setShareMp3(String shareMp3) {
		this.shareMp3 = shareMp3;
	}

	public FormFile getMusicFile() {
		return this.musicFile;
	}

	public void setMusicFile(FormFile musicFile) {
		this.musicFile = musicFile;
	}

	public String getMusicName() {
		return this.musicName;
	}

	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}

	public long getIdMp3() {
		return this.idMp3;
	}

	public void setIdMp3(long idMp3) {
		this.idMp3 = idMp3;
	}

	public List<CategData> getListeCateg() {
		return this.ListeCateg;
	}

	public void setListeCateg(List<CategData> listeCateg) {
		this.ListeCateg = listeCateg;
	}

	public int getCategId() {
		return this.categId;
	}

	public void setCategId(int categId) {
		this.categId = categId;
	}
}

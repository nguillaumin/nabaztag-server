package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.violet.platform.dataobjects.CategData;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.TagData;

public class MyListeMp3Form extends AbstractForm {

	// test de cette constante
	public static final long serialVersionUID = 1;

	private int mp3Id;
	private int categId;
	private List<MusicData> listeMp3User1 = new ArrayList<MusicData>();
	private List<MusicData> listeMp3User2 = new ArrayList<MusicData>();
	private List<MusicData> listeMp3User3 = new ArrayList<MusicData>();
	private List<TagData> listeTags = new ArrayList<TagData>();
	private List<CategData> listeCateg = new LinkedList<CategData>();

	public int getMp3Id() {
		return this.mp3Id;
	}

	public void setMp3Id(int mp3Id) {
		this.mp3Id = mp3Id;
	}

	public List<MusicData> getListeMp3User1() {
		return this.listeMp3User1;
	}

	public void setListeMp3User1(List<MusicData> listeMp3User1) {
		this.listeMp3User1 = listeMp3User1;
	}

	public List<MusicData> getListeMp3User2() {
		return this.listeMp3User2;
	}

	public void setListeMp3User2(List<MusicData> listeMp3User2) {
		this.listeMp3User2 = listeMp3User2;
	}

	public List<MusicData> getListeMp3User3() {
		return this.listeMp3User3;
	}

	public void setListeMp3User3(List<MusicData> listeMp3User3) {
		this.listeMp3User3 = listeMp3User3;
	}

	public List<TagData> getListeTags() {
		return this.listeTags;
	}

	public void setListeTags(List<TagData> listeTags) {
		this.listeTags = listeTags;
	}

	public List<CategData> getListeCateg() {
		return this.listeCateg;
	}

	public void setListeCateg(List<CategData> listeCateg) {
		this.listeCateg = listeCateg;
	}

	public int getCategId() {
		return this.categId;
	}

	public void setCategId(int categId) {
		this.categId = categId;
	}
}

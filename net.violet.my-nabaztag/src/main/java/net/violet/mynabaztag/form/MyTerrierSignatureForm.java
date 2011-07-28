package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.AnimData;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.util.StringShop;

public class MyTerrierSignatureForm extends AbstractForm {

	// test de cette constante
	public static final long serialVersionUID = 1;

	private String userColorSign = StringShop.EMPTY_STRING;
	private int userMusic;
	private int userColor;
	private List<MusicData> musicList = new ArrayList<MusicData>();
	private List<AnimData> AnimList = new ArrayList<AnimData>();
	private int mode;
	private String user_signature = StringShop.EMPTY_STRING;

	public int getMode() {
		return this.mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public List<AnimData> getAnimList() {
		return this.AnimList;
	}

	public void setAnimList(List<AnimData> animList) {
		this.AnimList = animList;
	}

	public List<MusicData> getMusicList() {
		return this.musicList;
	}

	public void setMusicList(List<MusicData> musicList) {
		this.musicList = musicList;
	}

	public int getUserColor() {
		return this.userColor;
	}

	public void setUserColor(int userColor) {
		this.userColor = userColor;
	}

	public String getUserColorSign() {
		return this.userColorSign;
	}

	public void setUserColorSign(String userColorSign) {
		this.userColorSign = userColorSign;
	}

	public int getUserMusic() {
		return this.userMusic;
	}

	public void setUserMusic(int userMusic) {
		this.userMusic = userMusic;
	}

	public String getUser_signature() {
		return this.user_signature;
	}

	public void setUser_signature(String user_signature) {
		this.user_signature = user_signature;
	}
}

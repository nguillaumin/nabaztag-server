package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.util.StringShop;

public final class MySrvSoundForm extends AbstractForm {

	private static final long serialVersionUID = 1L;

	private int user_id;
	private int user_main;
	private int langUser;
	private long srvSound_id;

	private String name = StringShop.EMPTY_STRING;

	private List<MusicData> sonList = new ArrayList<MusicData>();

	public int getLangUser() {
		return this.langUser;
	}

	public void setLangUser(int langUser) {
		this.langUser = langUser;
	}

	public List<MusicData> getSonList() {
		return this.sonList;
	}

	public void setSonList(List<MusicData> sonList) {
		this.sonList = sonList;
	}

	public int getUser_id() {
		return this.user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getUser_main() {
		return this.user_main;
	}

	public void setUser_main(int user_main) {
		this.user_main = user_main;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSrvSound_id() {
		return this.srvSound_id;
	}

	public void setSrvSound_id(long music_id) {
		this.srvSound_id = music_id;
	}

}

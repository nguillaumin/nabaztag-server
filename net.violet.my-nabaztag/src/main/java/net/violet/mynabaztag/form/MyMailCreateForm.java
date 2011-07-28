package net.violet.mynabaztag.form;

import net.violet.platform.util.StringShop;

public class MyMailCreateForm extends AbstractForm {

	public static final long serialVersionUID = 1;

	private String keywords = StringShop.EMPTY_STRING;
	private long sounds;
	private int light;

	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keyword) {
		this.keywords = keyword;
	}

	public long getSounds() {
		return this.sounds;
	}

	public void setSounds(long sound) {
		this.sounds = sound;
	}

	public int getLight() {
		return this.light;
	}

	public void setLight(int light) {
		this.light = light;
	}

}

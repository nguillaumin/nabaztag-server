package net.violet.mynabaztag.form;

import net.violet.platform.util.StringShop;

public final class MySrvReveilFreeForm extends AbstractForm {

	private static final long serialVersionUID = 1L;

	private String horraire = StringShop.EMPTY_STRING;
	private String sonNom = StringShop.EMPTY_STRING;
	private long music_id;

	private int isReg;

	public String getHorraire() {
		return this.horraire;
	}

	public void setHorraire(String horraire) {
		this.horraire = horraire;
	}

	public int getIsReg() {
		return this.isReg;
	}

	public void setIsReg(int isReg) {
		this.isReg = isReg;
	}

	public String getSonNom() {
		return this.sonNom;
	}

	public void setSonNom(String sonNom) {
		this.sonNom = sonNom;
	}

	public long getMusic_id() {
		return this.music_id;
	}

	public void setMusic_id(long music_id) {
		this.music_id = music_id;
	}

}

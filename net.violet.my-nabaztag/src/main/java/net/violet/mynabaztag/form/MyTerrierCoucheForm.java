package net.violet.mynabaztag.form;

import net.violet.platform.util.StringShop;

public class MyTerrierCoucheForm extends AbstractForm {

	// test de cette constante
	public static final long serialVersionUID = 1;

	private int user_main;

	// Gestion
	private int add;
	private int delete;
	private int isReg;

	// horraire Semaine
	private String startW = StringShop.EMPTY_STRING;
	private String endW = StringShop.EMPTY_STRING;

	// horraire Week End
	private String startWe = StringShop.EMPTY_STRING;
	private String endWe = StringShop.EMPTY_STRING;

	public int getAdd() {
		return this.add;
	}

	public void setAdd(int add) {
		this.add = add;
	}

	public int getDelete() {
		return this.delete;
	}

	public void setDelete(int delete) {
		this.delete = delete;
	}

	public String getEndW() {
		return this.endW;
	}

	public void setEndW(String endW) {
		this.endW = endW;
	}

	public String getEndWe() {
		return this.endWe;
	}

	public void setEndWe(String endWe) {
		this.endWe = endWe;
	}

	public String getStartW() {
		return this.startW;
	}

	public void setStartW(String startW) {
		this.startW = startW;
	}

	public String getStartWe() {
		return this.startWe;
	}

	public void setStartWe(String startWe) {
		this.startWe = startWe;
	}

	public int getIsReg() {
		return this.isReg;
	}

	public void setIsReg(int isReg) {
		this.isReg = isReg;
	}

	public int getUser_main() {
		return this.user_main;
	}

	public void setUser_main(int user_main) {
		this.user_main = user_main;
	}

}

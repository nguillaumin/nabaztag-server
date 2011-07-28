package net.violet.mynabaztag.form;

import net.violet.platform.util.StringShop;

public final class MySrvHorlogeForm extends AbstractForm {

	private static final long serialVersionUID = 1L;

	private int user_id;
	private int user_main;
	private int langUser;

	private int delete;
	private int add;

	private int langFr;
	private int langEn;

	private int typeNormal;
	private int typeDelire;

	private int isReg;
	private String serviceName = StringShop.EMPTY_STRING;

	private String[] checkListLang = {};

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

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

	public int getIsReg() {
		return this.isReg;
	}

	public void setIsReg(int isReg) {
		this.isReg = isReg;
	}

	public int getLangEn() {
		return this.langEn;
	}

	public void setLangEn(int langEn) {
		this.langEn = langEn;
	}

	public int getLangFr() {
		return this.langFr;
	}

	public void setLangFr(int langFr) {
		this.langFr = langFr;
	}

	public int getLangUser() {
		return this.langUser;
	}

	public void setLangUser(int langUser) {
		this.langUser = langUser;
	}

	public int getTypeDelire() {
		return this.typeDelire;
	}

	public void setTypeDelire(int typeDelire) {
		this.typeDelire = typeDelire;
	}

	public int getTypeNormal() {
		return this.typeNormal;
	}

	public void setTypeNormal(int typeNormal) {
		this.typeNormal = typeNormal;
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

	public String[] getCheckListLang() {
		return this.checkListLang;
	}

	public void setCheckListLang(String[] checkListLang) {
		this.checkListLang = checkListLang;
	}
}

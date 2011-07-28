package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.violet.platform.dataobjects.FrequenceData;
import net.violet.platform.dataobjects.ObjectLangData;

public final class MySrvHumeurForm extends AbstractForm {

	private static final long serialVersionUID = 1L;

	private int freqSrv;
	private List<FrequenceData> freqSrvList = new ArrayList<FrequenceData>();

	private int isReg;
	private Collection<ObjectLangData> langList = new ArrayList<ObjectLangData>();
	private String[] checkListLang = {};

	public String[] getCheckListLang() {
		return this.checkListLang;
	}

	public void setCheckListLang(String[] checkListLang) {
		this.checkListLang = checkListLang;
	}

	public Collection<ObjectLangData> getLangList() {
		return this.langList;
	}

	public void setLangList(Collection<ObjectLangData> langList) {
		this.langList = langList;
	}

	/**
	 * @return the freqSrv
	 */
	public int getFreqSrv() {
		return this.freqSrv;
	}

	/**
	 * @param freqSrv the freqSrv to set
	 */
	public void setFreqSrv(int freqSrv) {
		this.freqSrv = freqSrv;
	}

	/**
	 * @return the isReg
	 */
	public int getIsReg() {
		return this.isReg;
	}

	/**
	 * @param isReg the isReg to set
	 */
	public void setIsReg(int isReg) {
		this.isReg = isReg;
	}

	/**
	 * @return the freqSrvList
	 */
	public List<FrequenceData> getFreqSrvList() {
		return this.freqSrvList;
	}

	/**
	 * @param freqSrvList the freqSrvList to set
	 */
	public void setFreqSrvList(List<FrequenceData> freqSrvList) {
		this.freqSrvList = freqSrvList;
	}
}

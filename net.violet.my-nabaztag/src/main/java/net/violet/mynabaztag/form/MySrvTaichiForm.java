package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.FrequenceData;
import net.violet.platform.util.StringShop;

public final class MySrvTaichiForm extends AbstractForm {

	private static final long serialVersionUID = 1L;

	private String freqSrv = StringShop.EMPTY_STRING;
	private List<FrequenceData> freqSrvList = new ArrayList<FrequenceData>();
	private int isReg;

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

	/**
	 * @return the freqSrv
	 */
	public String getFreqSrv() {
		return this.freqSrv;
	}

	/**
	 * @param freqSrv the freqSrv to set
	 */
	public void setFreqSrv(String freqSrv) {
		this.freqSrv = freqSrv;
	}
}

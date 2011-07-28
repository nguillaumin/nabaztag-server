package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.FrequenceData;
import net.violet.platform.util.StringShop;

public final class MySrvBilanForm extends AbstractForm {

	private static final long serialVersionUID = 1L;

	private int freqSrv;
	private List<FrequenceData> freqSrvList = new ArrayList<FrequenceData>();
	private int isReg;
	private String horraire = StringShop.EMPTY_STRING;

	private String serviceName = StringShop.EMPTY_STRING;

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @param horraire the horraire to set
	 */
	public void setHorraire(String horraire) {
		this.horraire = horraire;
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

	/**
	 * @param freqSrv the freqSrv to set
	 */
	public void setFreqSrv(int freqSrv) {
		this.freqSrv = freqSrv;
	}

	/**
	 * @return the freqSrv
	 */
	public int getFreqSrv() {
		return this.freqSrv;
	}

	/**
	 * @return the horraire
	 */
	public String getHorraire() {
		return this.horraire;
	}
}

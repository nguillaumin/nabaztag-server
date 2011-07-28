package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.violet.platform.dataobjects.AgendaData;
import net.violet.platform.dataobjects.FrequenceData;
import net.violet.platform.util.StringShop;

import org.apache.struts.action.ActionMapping;

public final class MySrvWebRadioFreeConfigForm extends AbstractForm {

	private static final long serialVersionUID = 1L;

	private String isV1 = "true";

	private int wradioId;
	private String srvName;
	private String listeningPeriod;
	private String desc;
	private String logo;
	private int applicationId = 0;
	private int subscriptionId = 0;
	private String dispatch;
	private List<FrequenceData> periodList = new ArrayList<FrequenceData>();
	private String[] checkListHours = new String[7];
	private String[] listeningPeriodeSelected = { "900000", "900000", "900000", "900000", "900000", "900000", "900000" };
	private String isRegistered;

	/**
	 * Used to set the encoding for the form this fixes problems with accents
	 * when text's going from the form (JSP) to the form object (JAVA) *
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		System.out.println("reset");

		setListeningPeriod(StringShop.EMPTY_STRING);
		setDispatch(StringShop.EMPTY_STRING);
		setWradioId(0);

		super.reset(mapping, request);
	}

	/**
	 * @return the listeningPeriod
	 */
	public String getListeningPeriod() {
		return this.listeningPeriod;
	}

	/**
	 * @param listeningPeriod the listeningPeriod to set
	 */
	public void setListeningPeriod(String listeningPeriod) {
		this.listeningPeriod = listeningPeriod;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return this.desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the logoPath
	 */
	public String getLogo() {
		return this.logo;
	}

	/**
	 * @param logoPath the logoPath to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * @return the srvName
	 */
	public String getSrvName() {
		return this.srvName;
	}

	/**
	 * @param srvName the srvName to set
	 */
	public void setSrvName(String srvName) {
		this.srvName = srvName;
	}

	/**
	 * @return the dispatcher
	 */
	public String getDispatch() {
		return this.dispatch;
	}

	/**
	 * @param dispatcher the dispatcher to set
	 */
	public void setDispatch(String dispatch) {
		this.dispatch = dispatch;
	}

	/**
	 * @return the listHours
	 */
	public List<AgendaData> getListHours() {
		return AgendaData.listAll();
	}

	/**
	 * @return the periodList
	 */
	public List<FrequenceData> getPeriodList() {
		return this.periodList;
	}

	/**
	 * @param periodList the periodList to set
	 */
	public void setPeriodList(List<FrequenceData> periodList) {
		this.periodList = periodList;
	}

	/**
	 * @return the listeningPeriodeSelected
	 */
	public String[] getListeningPeriodeSelected() {
		return this.listeningPeriodeSelected;
	}

	/**
	 * @param listeningPeriodeSelected the listeningPeriodeSelected to set
	 */
	public void setListeningPeriodeSelected(String[] listeningPeriodeSelected) {
		this.listeningPeriodeSelected = listeningPeriodeSelected;
	}

	/**
	 * @return the checkListHours
	 */
	public String[] getCheckListHours() {
		return this.checkListHours;
	}

	/**
	 * @param checkListHours the checkListHours to set
	 */
	public void setCheckListHours(String[] checkListHours) {
		this.checkListHours = checkListHours;
	}

	/**
	 * @return the webradioId
	 */
	public int getWradioId() {
		return this.wradioId;
	}

	/**
	 * @param webradioId the webradioId to set
	 */
	public void setWradioId(int wradioId) {
		this.wradioId = wradioId;
	}

	public String getIsV1() {
		return this.isV1;
	}

	public void setIsV1(String isV1) {
		this.isV1 = isV1;
	}

	public int getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(int srvId) {
		this.applicationId = srvId;
	}

	public int getSubscriptionId() {
		return this.subscriptionId;
	}

	public void setSubscriptionId(int subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public String getIsRegistered() {
		return this.isRegistered;
	}

	public void setIsRegistered(String isRegister) {
		this.isRegistered = isRegister;
	}

}

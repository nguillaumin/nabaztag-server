package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.FrequenceData;
import net.violet.platform.util.StringShop;

public final class MySrvBourseFullForm extends AbstractForm {

	private static final long serialVersionUID = 1L;

	private int isReg;

	private int valueTo;
	private int nbrValue;
	private int maxValue;

	private int falseValue;
	private int duplicateName;

	private List supervisedList = new ArrayList();

	private String alertName = StringShop.EMPTY_STRING;
	private String valName = StringShop.EMPTY_STRING;

	private String indic = StringShop.EMPTY_STRING;
	private List<FrequenceData> indicList = new ArrayList<FrequenceData>();

	private String horraire1 = StringShop.EMPTY_STRING;
	private String horraire2 = StringShop.EMPTY_STRING;

	private int weekend;
	private int lumiere;

	private String serviceName = StringShop.EMPTY_STRING;

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getAlertName() {
		return this.alertName;
	}

	public void setAlertName(String inAlertName) {
		this.alertName = inAlertName;
	}

	public String getHorraire1() {
		return this.horraire1;
	}

	public void setHorraire1(String inHoraire1) {
		this.horraire1 = inHoraire1;
	}

	public String getHorraire2() {
		return this.horraire2;
	}

	public void setHorraire2(String inHoraire2) {
		this.horraire2 = inHoraire2;
	}

	public String getIndic() {
		return this.indic;
	}

	public void setIndic(String inIndic) {
		this.indic = inIndic;
	}

	public List<FrequenceData> getIndicList() {
		return this.indicList;
	}

	public void setIndicList(List<FrequenceData> inIndicList) {
		this.indicList = inIndicList;
	}

	public int getIsReg() {
		return this.isReg;
	}

	public void setIsReg(int inIsReg) {
		this.isReg = inIsReg;
	}

	public int getLumiere() {
		return this.lumiere;
	}

	public void setLumiere(int inLumiere) {
		this.lumiere = inLumiere;
	}

	public int getMaxValue() {
		return this.maxValue;
	}

	public void setMaxValue(int inMaxValue) {
		this.maxValue = inMaxValue;
	}

	public int getNbrValue() {
		return this.nbrValue;
	}

	public void setNbrValue(int inNbrValue) {
		this.nbrValue = inNbrValue;
	}

	public List getSupervisedList() {
		return this.supervisedList;
	}

	public void setSupervisedList(List inSupervisedList) {
		this.supervisedList = inSupervisedList;
	}

	public int getValueTo() {
		return this.valueTo;
	}

	public void setValueTo(int inValueTo) {
		this.valueTo = inValueTo;
	}

	public int getWeekend() {
		return this.weekend;
	}

	public void setWeekend(int inWeekEnd) {
		this.weekend = inWeekEnd;
	}

	public String getValName() {
		return this.valName;
	}

	public void setValName(String inValName) {
		this.valName = inValName;
	}

	public int getDuplicateName() {
		return this.duplicateName;
	}

	public void setDuplicateName(int inDuplicateName) {
		this.duplicateName = inDuplicateName;
	}

	public void setDuplicateName(boolean inDuplicateName) {
		setDuplicateName((inDuplicateName) ? 1 : 0);
	}

	public int getFalseValue() {
		return this.falseValue;
	}

	public void setFalseValue(int inFalseValue) {
		this.falseValue = inFalseValue;
	}

	public void setFalseValue(boolean inValidIndic) {
		setFalseValue((!inValidIndic) ? 1 : 0);
	}
}

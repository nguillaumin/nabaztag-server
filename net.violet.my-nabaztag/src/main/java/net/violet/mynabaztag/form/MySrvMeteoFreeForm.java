package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.dataobjects.SourceData;
import net.violet.platform.util.StringShop;

public final class MySrvMeteoFreeForm extends AbstractForm {

	private static final long serialVersionUID = 1L;

	private String idVille = StringShop.EMPTY_STRING;
	private List<SourceData> villeList = new ArrayList<SourceData>();
	private String horraire1 = StringShop.EMPTY_STRING;
	private String horraire2 = StringShop.EMPTY_STRING;
	private int langSrv;
	private int lumiere;
	private int vocal;
	private int typedeg = 1; // 1 degrès 2 farenheit

	private int isReg;
	private String serviceName = StringShop.EMPTY_STRING;

	private Collection<ObjectLangData> langList = new ArrayList<ObjectLangData>();

	public Collection<ObjectLangData> getLangList() {
		return this.langList;
	}

	public void setLangList(Collection<ObjectLangData> langList) {
		this.langList = langList;
	}

	public int getTypedeg() {
		return this.typedeg;
	}

	public void setTypedeg(int typedeg) {
		this.typedeg = typedeg;
	}

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public int getIsReg() {
		return this.isReg;
	}

	public void setIsReg(int isReg) {
		this.isReg = isReg;
	}

	public String getHorraire1() {
		return this.horraire1;
	}

	public void setHorraire1(String horraire1) {
		this.horraire1 = horraire1;
	}

	public String getHorraire2() {
		return this.horraire2;
	}

	public void setHorraire2(String horraire2) {
		this.horraire2 = horraire2;
	}

	public String getIdVille() {
		return this.idVille;
	}

	public void setIdVille(String idVille) {
		this.idVille = idVille;
	}

	public int getLangSrv() {
		return this.langSrv;
	}

	public void setLangSrv(int langSrv) {
		this.langSrv = langSrv;
	}

	public int getLumiere() {
		return this.lumiere;
	}

	public void setLumiere(int lumiere) {
		this.lumiere = lumiere;
	}

	public List<SourceData> getVilleList() {
		return this.villeList;
	}

	public void setVilleList(List<SourceData> villeList) {
		this.villeList = villeList;
	}

	public int getVocal() {
		return this.vocal;
	}

	public void setVocal(int vocal) {
		this.vocal = vocal;
	}
}

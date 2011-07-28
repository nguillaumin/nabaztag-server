package net.violet.mynabaztag.form;

import net.violet.platform.util.StringShop;

public class MyMessagesSendClinForm extends AbstractForm {

	// test de cette constante
	public static final long serialVersionUID = 1;

	private String friendObjectName = StringShop.EMPTY_STRING;
	private int color = -1;
	private int choixJourDiff;
	private int choixMoisDiff;
	private int choixAnneeDiff;
	private int choixHeureDiff;
	private int choixMinuteDiff;
	private int idClin;
	private String langUser = StringShop.EMPTY_STRING;
	private String destName = StringShop.EMPTY_STRING;
	private String sendLater = StringShop.EMPTY_STRING;
	private String erreur = StringShop.EMPTY_STRING;
	private int send;
	private String url = StringShop.EMPTY_STRING;
	private int categId;

	public String getErreur() {
		return this.erreur;
	}

	public void setErreur(String erreur) {
		this.erreur = erreur;
	}

	public int getColor() {
		return this.color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public String getLangUser() {
		return this.langUser;
	}

	public void setLangUser(String langUser) {
		if (langUser == null) {
			this.langUser = StringShop.EMPTY_STRING;
		} else {
			this.langUser = langUser;
		}
	}

	public String getDestName() {
		return this.destName;
	}

	public void setDestName(String destName) {
		if (destName == null) {
			this.destName = StringShop.EMPTY_STRING;
		} else {
			this.destName = destName;
		}
	}

	public String getFriendObjectName() {
		return this.friendObjectName;
	}

	public void setFriendObjectName(String friendObjectName) {
		this.friendObjectName = friendObjectName;
	}

	public int getChoixAnneeDiff() {
		return this.choixAnneeDiff;
	}

	public void setChoixAnneeDiff(int choixAnneeDiff) {
		this.choixAnneeDiff = choixAnneeDiff;
	}

	public int getChoixJourDiff() {
		return this.choixJourDiff;
	}

	public void setChoixJourDiff(int choixJourDiff) {
		this.choixJourDiff = choixJourDiff;
	}

	public int getChoixMoisDiff() {
		return this.choixMoisDiff;
	}

	public void setChoixMoisDiff(int choixMoisDiff) {
		this.choixMoisDiff = choixMoisDiff;
	}

	public int getChoixHeureDiff() {
		return this.choixHeureDiff;
	}

	public void setChoixHeureDiff(int choixHeureDiff) {
		this.choixHeureDiff = choixHeureDiff;
	}

	public int getChoixMinuteDiff() {
		return this.choixMinuteDiff;
	}

	public void setChoixMinuteDiff(int choixMinuteDiff) {
		this.choixMinuteDiff = choixMinuteDiff;
	}

	public String getSendLater() {
		return this.sendLater;
	}

	public void setSendLater(String sendLater) {
		if (sendLater == null) {
			this.sendLater = StringShop.EMPTY_STRING;
		} else {
			this.sendLater = sendLater;
		}
	}

	public int getIdClin() {
		return this.idClin;
	}

	public void setIdClin(int idClin) {
		this.idClin = idClin;
	}

	public int getSend() {
		return this.send;
	}

	public void setSend(int send) {
		this.send = send;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getCategId() {
		return this.categId;
	}

	public void setCategId(int categId) {
		this.categId = categId;
	}
}

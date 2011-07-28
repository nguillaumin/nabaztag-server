package net.violet.mynabaztag.form;

import net.violet.platform.util.StringShop;

public class MyMessagesSendVocalForm extends AbstractForm {

	// test de cette constante
	public static final long serialVersionUID = 1;

	private String friendObjectName = StringShop.EMPTY_STRING;
	private int color = -1;
	private int choixJourDiff;
	private int choixMoisDiff;
	private int choixAnneeDiff;
	private int choixHeureDiff;
	private int choixMinuteDiff;
	private String destName = StringShop.EMPTY_STRING;
	private String sendLater = StringShop.EMPTY_STRING;
	private String erreur = StringShop.EMPTY_STRING;
	private int send;
	private String musicName = StringShop.EMPTY_STRING;
	private String url = StringShop.EMPTY_STRING;
	private String idMp3 = StringShop.EMPTY_STRING;
	private String saveMp3Perso = StringShop.EMPTY_STRING;

	public String getIdMp3() {
		return this.idMp3;
	}

	public void setIdMp3(String idMp3) {
		this.idMp3 = idMp3;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

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

	public String getDestName() {
		return this.destName;
	}

	public String getSaveMp3Perso() {
		return this.saveMp3Perso;
	}

	public void setSaveMp3Perso(String saveMp3Perso) {
		if (saveMp3Perso == null) {
			this.saveMp3Perso = StringShop.EMPTY_STRING;
		} else {
			this.saveMp3Perso = saveMp3Perso;
		}
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

	public int getSend() {
		return this.send;
	}

	public void setSend(int send) {
		this.send = send;
	}

	public String getMusicName() {
		return this.musicName;
	}

	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}
}

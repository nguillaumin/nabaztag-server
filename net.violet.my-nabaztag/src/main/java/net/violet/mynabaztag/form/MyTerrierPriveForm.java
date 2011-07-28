package net.violet.mynabaztag.form;

public class MyTerrierPriveForm extends AbstractForm {

	private static final long serialVersionUID = -747468138651125880L;

	private long authorisation;
	private long notification;
	private long filtre;
	private long antispam;

	private int mode;

	public int getMode() {
		return this.mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public long getNotification() {
		return this.notification;
	}

	public void setNotification(long notification) {
		this.notification = notification;
	}

	public long getAntispam() {
		return this.antispam;
	}

	public void setAntispam(long antispam) {
		this.antispam = antispam;
	}

	public long getAuthorisation() {
		return this.authorisation;
	}

	public void setAuthorisation(long authorisation) {
		this.authorisation = authorisation;
	}

	public long getFiltre() {
		return this.filtre;
	}

	public void setFiltre(long filtre) {
		this.filtre = filtre;
	}
}

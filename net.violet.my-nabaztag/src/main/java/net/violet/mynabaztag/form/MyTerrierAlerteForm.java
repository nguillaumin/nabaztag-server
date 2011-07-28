package net.violet.mynabaztag.form;

public class MyTerrierAlerteForm extends AbstractForm {

	private static final long serialVersionUID = 7944735104009988649L;

	private int msgSent;
	private int msgPlayed;
	private int msgReceived;
	private int newsletter;

	private int mode;

	public int getMode() {
		return this.mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getMsgPlayed() {
		return this.msgPlayed;
	}

	public void setMsgPlayed(int msgPlayed) {
		this.msgPlayed = msgPlayed;
	}

	public int getMsgReceived() {
		return this.msgReceived;
	}

	public void setMsgReceived(int msgReceived) {
		this.msgReceived = msgReceived;
	}

	public int getMsgSent() {
		return this.msgSent;
	}

	public void setMsgSent(int msgSent) {
		this.msgSent = msgSent;
	}

	public int getNewsletter() {
		return this.newsletter;
	}

	public void setNewsletter(int newsletter) {
		this.newsletter = newsletter;
	}
}

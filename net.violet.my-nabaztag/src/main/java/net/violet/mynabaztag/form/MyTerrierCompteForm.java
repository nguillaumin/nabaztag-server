package net.violet.mynabaztag.form;

import net.violet.platform.util.StringShop;

public class MyTerrierCompteForm extends AbstractForm {

	private static final long serialVersionUID = 2430096008458886564L;

	private String pseudo = StringShop.EMPTY_STRING;
	private String email = StringShop.EMPTY_STRING;

	private String macAddress = StringShop.EMPTY_STRING;
	private int isConnected;

	public String getPseudo() {
		return this.pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIsConnected() {
		return this.isConnected;
	}

	public void setIsConnected(int isConnected) {
		this.isConnected = isConnected;
	}

	public String getMacAddress() {
		return this.macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
}

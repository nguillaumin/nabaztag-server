package net.violet.mynabaztag.form;

import net.violet.platform.util.StringShop;

public class MyUserPrefsForm extends AbstractForm {

	// test de cette constante
	public static final long serialVersionUID = 1;

	private String mode = StringShop.EMPTY_STRING;
	private String champ = StringShop.EMPTY_STRING; // champ de la table à updater
	private String setValue = StringShop.EMPTY_STRING; // valeur à setter
	private String output = StringShop.EMPTY_STRING;

	public String getChamp() {
		return this.champ;
	}

	public void setChamp(String champ) {
		this.champ = champ;
	}

	public String getMode() {
		return this.mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getSetValue() {
		return this.setValue;
	}

	public void setSetValue(String setValue) {
		this.setValue = setValue;
	}

	public String getOutput() {
		return this.output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
}

package net.violet.mynabaztag.form;

import net.violet.platform.util.MyConstantes;
import net.violet.platform.util.StringShop;

public class MyMessagesVocalForm extends AbstractForm {

	// test de cette constante
	public static final long serialVersionUID = 1;

	private String langUser = StringShop.EMPTY_STRING;
	private String idMp3 = StringShop.EMPTY_STRING;
	private String serverPath = MyConstantes.RED5_URL_SERVER;

	public String getIdMp3() {
		return this.idMp3;
	}

	public void setIdMp3(String idMp3) {
		this.idMp3 = idMp3;
	}

	public String getLangUser() {
		return this.langUser;
	}

	public void setLangUser(String langUser) {
		this.langUser = langUser;
	}

	public String getServerPath() {
		return this.serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}
}

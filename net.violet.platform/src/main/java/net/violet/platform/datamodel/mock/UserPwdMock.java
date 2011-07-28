package net.violet.platform.datamodel.mock;

import net.violet.common.StringShop;
import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.UserPwd;

public class UserPwdMock extends AbstractMockRecord<UserPwd, UserPwdMock> implements UserPwd {

	private String pseudo;
	private String password;

	public UserPwdMock(long inId) {
		super(inId);
		new UserPwdMock(inId, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING);
	}

	public UserPwdMock(long inId, String inPseudo, String inPassword) {
		super(inId);
		this.pseudo = inPseudo;
		this.password = inPassword;
	}

	public String getPseudo() {
		return this.pseudo;
	}

	public String getPwd() {
		return this.password;
	}

	public void setPseudo(String inPseudo) {
		this.pseudo = inPseudo;
	}

	public void setPwd(String inPwd) {
		this.password = inPwd;
	}

}

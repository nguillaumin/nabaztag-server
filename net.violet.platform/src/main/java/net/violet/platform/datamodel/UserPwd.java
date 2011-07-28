package net.violet.platform.datamodel;

import net.violet.db.records.Record;

public interface UserPwd extends Record<UserPwd> {

	String getPwd();

	void setPseudo(String inPseudo);

	String getPseudo();

	void setPwd(String inPwd);

}

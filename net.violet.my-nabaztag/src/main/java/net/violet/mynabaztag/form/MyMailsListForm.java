package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.MailUserData;
import net.violet.platform.util.StringShop;

public class MyMailsListForm extends AbstractForm {

	public static final long serialVersionUID = 1;

	private boolean justList;
	private String serviceName = StringShop.EMPTY_STRING;
	private int isReg;
	private List<MailUserData> mailsAccounts = new ArrayList<MailUserData>();

	public int getIsReg() {
		return this.isReg;
	}

	public void setIsReg(int isReg) {
		this.isReg = isReg;
	}

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public List<MailUserData> getMailsAccounts() {
		return this.mailsAccounts;
	}

	public void setMailsAccounts(List<MailUserData> mailsAccounts) {
		this.mailsAccounts = mailsAccounts;
	}

	public boolean isJustList() {
		return this.justList;
	}

	public void setJustList(boolean justList) {
		this.justList = justList;
	}
}

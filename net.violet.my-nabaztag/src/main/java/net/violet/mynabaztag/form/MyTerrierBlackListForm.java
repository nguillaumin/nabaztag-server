package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.BlackData;

public class MyTerrierBlackListForm extends AbstractForm {

	private static final long serialVersionUID = 1;

	/* GENERAL */
	private String[] checkBlackList;
	private List<BlackData> listBlack = new ArrayList<BlackData>();

	/**
	 * @return the checkListFriends
	 */
	public final String[] getCheckBlackList() {
		return this.checkBlackList;
	}

	/**
	 * @param checkListFriends the checkListFriends to set
	 */
	public final void setCheckBlackList(String[] checkBlackList) {
		this.checkBlackList = checkBlackList;
	}

	/**
	 * @return the listBlack
	 */
	public final List<BlackData> getListBlack() {
		return this.listBlack;
	}

	/**
	 * @param listBlack the listBlack to set
	 */
	public final void setListBlack(List<BlackData> listBlack) {
		this.listBlack = listBlack;
	}
}

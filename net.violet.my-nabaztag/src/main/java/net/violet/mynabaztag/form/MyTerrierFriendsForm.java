package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.StringShop;

public class MyTerrierFriendsForm extends AbstractForm {

	private static final long serialVersionUID = -7100294752891177825L;

	private String[] checkListFriends;
	private List<UserData> listFriends = new ArrayList<UserData>();

	private List<UserData> reqList = new ArrayList<UserData>();
	private List<UserData> ansList = new ArrayList<UserData>();
	private int friend_id;

	private String name = StringShop.EMPTY_STRING;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the checkListMsg
	 */
	public final String[] getCheckListFriends() {
		return this.checkListFriends;
	}

	/**
	 * @param checkListMsg the checkListMsg to set
	 */
	public final void setCheckListFriends(String[] checkListFriends) {
		this.checkListFriends = checkListFriends;
	}

	/**
	 * @return the listFriends
	 */
	public final List<UserData> getListFriends() {
		return this.listFriends;
	}

	/**
	 * @param listFriends the listFriends to set
	 */
	public final void setListFriends(List<UserData> listFriends) {
		this.listFriends = listFriends;
	}

	public List<UserData> getAnsList() {
		return this.ansList;
	}

	public void setAnsList(List<UserData> ansList) {
		this.ansList = ansList;
	}

	public List<UserData> getReqList() {
		return this.reqList;
	}

	public void setReqList(List<UserData> reqList) {
		this.reqList = reqList;
	}

	public int getFriend_id() {
		return this.friend_id;
	}

	public void setFriend_id(int friend_id) {
		this.friend_id = friend_id;
	}
}

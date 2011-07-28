package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.SrvDialogData;
import net.violet.platform.util.StringShop;

public final class MySrvDialogForm extends AbstractForm {

	private static final long serialVersionUID = 1L;

	private long subscriptionId;
	private long notificationId = 0;

	private int hasSomethingToDisplay;
	private int isMarried;
	private int isWaiting;

	private String friendName = StringShop.EMPTY_STRING;
	private int friendId;
	private String friendZone = StringShop.EMPTY_STRING;

	private int error_same;
	private int error_dne;

	// Les listes d'avertissements
	private SrvDialogData separatedInfo;
	private SrvDialogData refusedInfo;
	private SrvDialogData acceptedInfo;
	private List<SrvDialogData> waitingList = new ArrayList<SrvDialogData>();
	private List<SrvDialogData> cancelList = new ArrayList<SrvDialogData>();
	private String serviceName = StringShop.EMPTY_STRING;

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getFriendName() {
		return this.friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public int getHasSomethingToDisplay() {
		return this.hasSomethingToDisplay;
	}

	public void setHasSomethingToDisplay(int hasSomethingToDisplay) {
		this.hasSomethingToDisplay = hasSomethingToDisplay;
	}

	public int getIsMarried() {
		return this.isMarried;
	}

	public void setIsMarried(int isMarried) {
		this.isMarried = isMarried;
	}

	public int getIsWaiting() {
		return this.isWaiting;
	}

	public void setIsWaiting(int isWaiting) {
		this.isWaiting = isWaiting;
	}

	public List<SrvDialogData> getCancelList() {
		return this.cancelList;
	}

	public void setCancelList(List<SrvDialogData> cancelList) {
		this.cancelList = cancelList;
	}

	public SrvDialogData getSeparatedInfo() {
		return this.separatedInfo;
	}

	public void setSeparatedInfo(SrvDialogData separatedInfo) {
		this.separatedInfo = separatedInfo;
	}

	public List<SrvDialogData> getWaitingList() {
		return this.waitingList;
	}

	public void setWaitingList(List<SrvDialogData> waitingList) {
		this.waitingList = waitingList;
	}

	public int getFriendId() {
		return this.friendId;
	}

	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}

	public SrvDialogData getRefusedInfo() {
		return this.refusedInfo;
	}

	public void setRefusedInfo(SrvDialogData refusedInfo) {
		this.refusedInfo = refusedInfo;
	}

	public SrvDialogData getAcceptedInfo() {
		return this.acceptedInfo;
	}

	public void setAcceptedInfo(SrvDialogData acceptedInfo) {
		this.acceptedInfo = acceptedInfo;
	}

	public int getError_dne() {
		return this.error_dne;
	}

	public void setError_dne(int error_dne) {
		this.error_dne = error_dne;
	}

	public int getError_same() {
		return this.error_same;
	}

	public void setError_same(int error_same) {
		this.error_same = error_same;
	}

	public String getFriendZone() {
		return this.friendZone;
	}

	public void setFriendZone(String friendZone) {
		this.friendZone = friendZone;
	}

	public long getSubscriptionId() {
		return this.subscriptionId;
	}

	public void setSubscriptionId(long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public long getNotificationId() {
		return this.notificationId;
	}

	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}
}

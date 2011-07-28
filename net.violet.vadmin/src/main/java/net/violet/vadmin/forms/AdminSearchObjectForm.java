package net.violet.vadmin.forms;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.violet.common.StringShop;
import net.violet.vadmin.objects.data.ObjectData;
import net.violet.vadmin.objects.data.ServiceData;
import net.violet.vadmin.objects.data.UserData;

import org.apache.struts.action.ActionMapping;

public final class AdminSearchObjectForm extends AdminForm {

	private static final long serialVersionUID = 1L;

	private String dispatch;
	private String macAddress;
	private String mailAddress;
	private String hardware;
	private String name;
	private String serviceName = StringShop.EMPTY_STRING;
	private String[] objectChecked = {};

	private UserData userData;
	private ObjectData objectData;
	private List<ObjectData> listObjectData = Collections.emptyList();
	private List<UserData> listUserData = Collections.emptyList();
	private List<ServiceData> listServicesData = Collections.emptyList();

	private String actionId;
	private String objectId;
	private String userId;
	private String display;


	@Override
	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		super.reset(actionMapping, httpServletRequest);
        objectData = new ObjectData();
        userData = new UserData();
    }

	
	public String getDispatch() {
		return dispatch;
	}

	
	public void setDispatch(String dispatch) {
		this.dispatch = dispatch;
	}

	
	public String getMacAddress() {
		return macAddress;
	}

	
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	
	public String getMailAddress() {
		return mailAddress;
	}

	
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getHardware() {
		return hardware;
	}


	public void setHardware(String hardware) {
		this.hardware = hardware;
	}

	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	
	public String getServiceName() {
		return serviceName;
	}

	
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	
	public String[] getObjectChecked() {
		return objectChecked;
	}

	
	public void setObjectChecked(String[] objectChecked) {
		this.objectChecked = objectChecked;
	}

	
	public UserData getUserData() {
		return userData;
	}

	
	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	
	public ObjectData getObjectData() {
		return objectData;
	}

	
	public void setObjectData(ObjectData objectData) {
		this.objectData = objectData;
	}

	
	public List<ObjectData> getListObjectData() {
		return listObjectData;
	}

	
	public void setListObjectData(List<ObjectData> listObjectData) {
		this.listObjectData = listObjectData;
	}

	
	public List<UserData> getListUserData() {
		return listUserData;
	}

	
	public void setListUserData(List<UserData> listUserData) {
		this.listUserData = listUserData;
	}

	
	public List<ServiceData> getListServicesData() {
		return listServicesData;
	}

	
	public void setListServicesData(List<ServiceData> listServicesData) {
		this.listServicesData = listServicesData;
	}

	
	public String getActionId() {
		return actionId;
	}

	
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	
	public String getObjectId() {
		return objectId;
	}

	
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	
	public String getUserId() {
		return userId;
	}

	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	public String getDisplay() {
		return display;
	}

	
	public void setDisplay(String display) {
		this.display = display;
	}
}

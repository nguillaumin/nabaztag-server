package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.MessageReceivedData;
import net.violet.platform.dataobjects.MessageSentData;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.NabcastData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.StringShop;

public class MyProfilForm extends AbstractForm {

	public static final long serialVersionUID = 1;

	/* USER LOGUE */
	private String langUser = StringShop.EMPTY_STRING;
	private String userlog_id = StringShop.EMPTY_STRING;

	/* AFFICHAGE */
	private String user_id = StringShop.EMPTY_STRING;
	private UserData profil = UserData.getData(null);
	private List<MusicData> nabShares = new ArrayList<MusicData>();
	private List<MessageReceivedData> messagesReceived = new ArrayList<MessageReceivedData>();
	private List<MessageSentData> sendMessages = new ArrayList<MessageSentData>();
	private List<NabcastData> nabCast = new ArrayList<NabcastData>();

	int isFriend;

	public int getIsFriend() {
		return this.isFriend;
	}

	public void setIsFriend(int isFriend) {
		this.isFriend = isFriend;
	}

	public String getLangUser() {
		return this.langUser;
	}

	public void setLangUser(String langUser) {
		this.langUser = langUser;
	}

	public List<NabcastData> getNabCast() {
		return this.nabCast;
	}

	public void setNabCast(List<NabcastData> nabCast) {
		this.nabCast = nabCast;
	}

	public List<MusicData> getNabShares() {
		return this.nabShares;
	}

	public void setNabShares(List<MusicData> nabShares) {
		this.nabShares = nabShares;
	}

	public void setProfil(UserData inUser) {
		this.profil = inUser;
	}

	public UserData getProfil() {
		return this.profil;
	}

	public UserData getProfildata() {
		return this.profil;
	}

	public List<MessageReceivedData> getMessagesReceived() {
		return this.messagesReceived;
	}

	public void setMessagesReceived(List<MessageReceivedData> receiveMessages) {
		this.messagesReceived = receiveMessages;
	}

	public List<MessageSentData> getSendMessages() {
		return this.sendMessages;
	}

	public void setSendMessages(List<MessageSentData> sendMessages) {
		this.sendMessages = sendMessages;
	}

	public String getUser_id() {
		return this.user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUserlog_id() {
		return this.userlog_id;
	}

	public void setUserlog_id(String userlog_id) {
		this.userlog_id = userlog_id;
	}
}

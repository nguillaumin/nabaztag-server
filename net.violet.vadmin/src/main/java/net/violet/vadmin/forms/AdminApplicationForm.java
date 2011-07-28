package net.violet.vadmin.forms;

import java.util.List;

import net.violet.common.StringShop;
import net.violet.vadmin.objects.data.VoiceData;


public final class AdminApplicationForm extends ApplicationForm {

	private static final long serialVersionUID = 1L;
	
	private String applicationId; //Use only in the update of an application
	private String announcement; //Use only in the update of an application
	private String TTSLanguage = StringShop.EMPTY_STRING;
	private String TTSText = StringShop.EMPTY_STRING;
	private List<VoiceData> voices;
	private String formerURL;
 
	public String getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	
	public String getTTSLanguage() {
		return TTSLanguage;
	}

	
	public void setTTSLanguage(String language) {
		TTSLanguage = language;
	}

	
	public String getTTSText() {
		return TTSText;
	}

	
	public void setTTSText(String text) {
		TTSText = text;
	}

	
	public List<VoiceData> getVoices() {
		return voices;
	}

	
	public void setVoices(List<VoiceData> voices) {
		this.voices = voices;
	}

	
	public String getAnnouncement() {
		return announcement;
	}

	
	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}

	
	public String getFormerURL() {
		return formerURL;
	}

	
	public void setFormerURL(String formerURL) {
		this.formerURL = formerURL;
	}
}

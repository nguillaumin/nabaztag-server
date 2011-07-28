package net.violet.vadmin.actions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.commondev.utils.StringShop;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.files.Synthetize;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.NameAlreadyExistsException;
import net.violet.platform.api.maps.FilesInformationMap;
import net.violet.platform.api.maps.applications.ApplicationInformationMap;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ApplicationSettingData;
import net.violet.platform.dataobjects.TtsVoiceData;
import net.violet.platform.message.application.factories.WebRadioMessageFactory;
import net.violet.vadmin.exceptions.InvalidFile;
import net.violet.vadmin.forms.AdminApplicationForm;
import net.violet.vadmin.util.AdminConstantes;
import net.violet.vadmin.util.CommonTools;
import net.violet.vadmin.util.SessionTools;
import net.violet.vadmin.util.UploadTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;


public abstract class AdminApplicationAction extends AdminAction {

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final AdminApplicationForm myForm = (AdminApplicationForm) form;
		final ActionMessages errors = myForm.getErrors();

		myForm.setCatList(generateCategoriesList());
		myForm.setLangList(generateLanguagesList());
		myForm.setHardwareList(generateHardwareList());
		myForm.setVoices(generateTTSVoiceList());

		saveErrors(request, errors);
		return mapping.getInputForward();
	}


	public ActionForward callAPI(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final String currentSessionId = SessionTools.getSessionId(request.getSession());
		final AdminApplicationForm myForm = (AdminApplicationForm) form;
		final ActionMessages errors = new ActionMessages();
		final MessageResources messageResources = getResources(request);
		final String theSession = SessionTools.createSession(AdminConstantes.USER_EMAIL, AdminConstantes.USER_PASSWORD);

		final String applicationId = myForm.getApplicationId();
		final String ttsLanguage = myForm.getTTSLanguage();
		final String ttsText = myForm.getTTSText(); 
		final String service = myForm.getNameService();
		final String name = myForm.getName();
		final String category = myForm.getCat();
		final String url = myForm.getUrl();
		final String formerUrl = myForm.getFormerURL();
		final String title = myForm.getTitle();
		final String description = myForm.getDescription();
		final String howTo = myForm.getHowTo();
		final String isVisible = myForm.getIsVisible();
		final String[] languages = myForm.getLanguages();
		final String[] hardware = myForm.getHardware();
		final FormFile iconFile = myForm.getIconFile();
		final FormFile pictureFile = myForm.getPictureFile();
		final ApplicationType appliType = ApplicationType.findByName(service);
		String announcement = myForm.getAnnouncement();
		String iconId = myForm.getIconFileId();
		String pictureId = myForm.getPictureFileId();

		if (StringShop.EMPTY_STRING.equals(name)) {
			errors.add("emptyName", new ActionMessage("error.fill", messageResources.getMessage("common.name").toLowerCase()));
		}
		if (!service.equals("net.violet.js.") && StringShop.EMPTY_STRING.equals(url)) {
			errors.add("emptyUrl", new ActionMessage(StringShop.EMPTY_STRING));
		}
		if (0 == languages.length) {
			errors.add("emptyLanguages", new ActionMessage("error.at_least", messageResources.getMessage("common.language").toLowerCase()));
		}
		if (0 == hardware.length) {
			errors.add("emptyHardware", new ActionMessage("error.at_least", messageResources.getMessage("common.hardware").toLowerCase()));
		}

//		Creation TTS if necessary
		if(ApplicationType.EXTERNAL != appliType && hasToCreateTTS(ttsText)){
			try {
				announcement = CommonTools.generateTTS(ttsText, true, ttsLanguage);
			} catch (APIException e) {
				errors.add("ttsFailed", new ActionMessage("error.failed", messageResources.getMessage("common.tts_generation")));
			} 
		}

//		Check URL
		if(ApplicationType.WEBRADIO != appliType && ApplicationType.EXTERNAL != appliType && isAnotherUrl(formerUrl, url) && !CommonTools.checkURL(url)){
			errors.add("badUrl", new ActionMessage("error.invalid", messageResources.getMessage("common.url")));
		}

		
		if (errors.isEmpty()) {
			try {
				if (iconFile.getFileSize() != 0 && currentSessionId != null) {
					iconId = UploadTools.uploadFile(iconFile, currentSessionId);
				}
				if (pictureFile.getFileSize() != 0 && currentSessionId != null) {
					pictureId = UploadTools.uploadFile(pictureFile, currentSessionId);
				}
			}catch (final FileNotFoundException e1) {
				errors.add("uploadFailed", new ActionMessage("error.failed", messageResources.getMessage("common.upload")));
			} catch (final IOException e1) {
				errors.add("uploadFailed", new ActionMessage("error.failed", messageResources.getMessage("common.upload")));
			} catch (InvalidFile e) {
				errors.add("incorrectSize", new ActionMessage("error.file_size"));
			}
		}

		if (errors.isEmpty()) {
			final Map<String, Object> theParams = new HashMap<String, Object>();
			theParams.put("id", applicationId);
			theParams.put("session", theSession);
			theParams.put("name", service+name);
			theParams.put("category", category);
			theParams.put("visible", Boolean.valueOf(isVisible));
			theParams.put("languages", Arrays.asList(languages));
			theParams.put("hardwares", Arrays.asList(hardware));
			theParams.put("interactive", false);
			theParams.put("class", appliType.getApplicationClass());

			final Map<String, Object> theProfileParams = new HashMap<String, Object>();
			theProfileParams.put("title", getTitle(title));
			theProfileParams.put("description", getDescription(description));
			theProfileParams.put("open_source", false);
			theProfileParams.put("setting", appliType.getSetting());
			theProfileParams.put("scheduling", appliType.getScheduling());
			theProfileParams.put("announcement", announcement);
			theProfileParams.put("picture", pictureId);
			theProfileParams.put("icon", appliType.getIcon());
			theProfileParams.put("instructions", appliType.getHowTo());
			if (ApplicationType.EXTERNAL == appliType) {
				theProfileParams.put("url", url);
				theProfileParams.put("icon", iconId);
				theProfileParams.put("instructions", getHowTo(howTo));
			}

			theParams.put("profile", theProfileParams);

			try{
				ApplicationInformationMap theApplicationCreated = (ApplicationInformationMap) Admin.processRequest(getAction(), theParams);

				String applicationGereratedId = theApplicationCreated.getString(ApplicationInformationMap.ID);
				ApplicationData applicationData = ApplicationData.findByAPIId(applicationGereratedId, Admin.CALLER.getAPIKey(), true);
				try {
					// If the application is not a javascript application we have to create (or update) the SQL TEMP table.

					if (!service.equals("net.violet.js.")) {
//						ApplicationTempData theApplicationTempData = applicationData.getTemp();
//						if (theApplicationTempData == null) {
//							LOGGER.info("-------------------->CREATE TEMP");
//							ApplicationTemp theAppliTemp = Factories.APPLICATION_TEMP.create(applicationData.getReference(), appliType.getTypeName(), shortcut, StringShop.EMPTY_STRING);
//							theApplicationTempData = new ApplicationTempData(theAppliTemp);
//						} else {
//							LOGGER.info("-------------------->UPDATE TEMP");
//							theApplicationTempData.update(shortcut, StringShop.EMPTY_STRING);
//						}

						if (ApplicationType.WEBRADIO == appliType) {
							ApplicationSettingData theApplicationSettingData = ApplicationSettingData.findByApplicationAndKey(applicationData, WebRadioMessageFactory.URL);
							if(theApplicationSettingData == null || !theApplicationSettingData.isValid()) {
								applicationData.createSetting(WebRadioMessageFactory.URL, url);
							}else{
								theApplicationSettingData.setValue(url);
							}
						} else {
							if (!url.equals(formerUrl)) {
								Feed.Type type = appliType.getTypeName().equals("podcast") ? Feed.Type.PODCAST : Feed.Type.RSS;
								Factories.FEED.create(url, type,TtsVoiceData.findTtsVoiceByName(ttsLanguage).getLang(), Feed.AccessRight.FREE);
							}
						}
					}
				}catch (Exception e) {
					errors.add("createFailed", new ActionMessage("error.created", "application temp"));
				}
			}catch (NameAlreadyExistsException e) {
				errors.add("existingName", new ActionMessage("error.existing", messageResources.getMessage("common.name")));
			}catch (APIException e) {
				errors.add("createFailed", new ActionMessage("error.created", "application"));
			}
		}

		myForm.setErrors(errors);
		if (!errors.isEmpty()) {
			myForm.setNameService(myForm.getNameService());
			myForm.setLanguage(myForm.getLanguage());
			myForm.setApplicationId(applicationId);
			return getForward(mapping, myForm, request, response);
		}

		errors.add("success", new ActionMessage(getSuccessMessage(), "application"));
		myForm.setErrors(errors);
		return getForward(mapping, myForm, request, response);
	}

	abstract Action getAction();
	
	abstract ActionForward getForward(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response);
	
	abstract String getSuccessMessage();
	
	protected boolean isAnotherUrl(String formerURL, String newURL){
		return !newURL.equals(formerURL);
	}

	protected boolean hasToCreateTTS(String inText){
		return !StringShop.EMPTY_STRING.equals(inText);
	}

	protected String getTitle(String inTitle){
		return inTitle;
	}

	protected String getDescription(String inDescription){
		return inDescription;
	}
	
	protected String getHowTo(String inHowTo){
		return inHowTo;
	}

	public static enum ApplicationType {
		PODCAST("podcast", "1fb948dF02ed13e5", "1fb948fF220d5315", "native", "1fb94e7F629d1305"),
		RSS("rss", "1fb9485F62ad4305", "1fb9487Ff2fd33c5", "native", "1fb94e3F020d33e5"),
		WEBRADIO("webradio","1fb94a1F22ddf3f5", "1fb94a3F326d7375", "native", "1fb972dFc2bd9375"),
		JAVASCRIPT("js", null, null, "native", null),
		EXTERNAL("external", null, null, "external", null);

		private String inHowTo;
		private String selector;
		private String inLink;
		private String inSettingFile;
		private String inSchedulingFile;
		private String inIconFile;
		private String inApplicationClass;

		private ApplicationType(String inName, String inSettingFile, String inSchedulingFile, String inApplicationClass, String inIconFile) {
			this.selector = inName.toLowerCase();
			this.inHowTo = "LOC_srv_" + this.selector + "/how_to";
			this.inLink = "srv" + inName + "Free.do?dispatch=load&scenarioId=";
			this.inSettingFile = inSettingFile;
			this.inSchedulingFile = inSchedulingFile;
			this.inApplicationClass = inApplicationClass;
			this.inIconFile = inIconFile;
		}

		//Dico Key preposition 
		public String getHowTo() {
			return this.inHowTo;
		}

		public String getTypeName() {
			return this.selector;
		}

		public String getLink() {
			return this.inLink;
		}

		public String getSetting(){
			return this.inSettingFile;
		}

		public String getScheduling(){
			return this.inSchedulingFile;
		}

		public String getIcon(){
			return this.inIconFile;
		}
		
		public String getApplicationClass(){
			return this.inApplicationClass;
		}

		public static ApplicationType findByName(String inName) {
			for (final ApplicationType aType : ApplicationType.values()) {
				if (inName.contains(aType.getTypeName())) {
					return aType;
				}
			}
			return null;
		}
	}

	public ApplicationType getApplicationType(String inName) {

		if (inName.matches(".*\\.podcast\\..*")) {
			return ApplicationType.PODCAST;
		} else if (inName.matches(".*\\.rss\\..*")) {
			return ApplicationType.RSS;
		} else if (inName.matches(".*\\.webradio\\..*")) {
			return ApplicationType.WEBRADIO;
		}
		return null;
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward listen(ActionMapping mapping, ActionForm form, @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		
		final AdminApplicationForm myForm = (AdminApplicationForm) form;
		final Action theAction = new Synthetize();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("text", myForm.getTTSText());
		theParams.put("voice", myForm.getTTSLanguage());
		theParams.put("getInfo", true);
		FilesInformationMap theFilesInformationMap;
		try {
			theFilesInformationMap = (FilesInformationMap) Admin.processRequest(theAction, theParams);
			myForm.setUrl(theFilesInformationMap.getString("url"));
		} catch (APIException e) {
		}
		
		return mapping.findForward("listen");
	}

}

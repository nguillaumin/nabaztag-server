package net.violet.vadmin.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.categories.Get;
import net.violet.platform.api.actions.languages.GetSiteLanguages;
import net.violet.platform.api.actions.product.GetProducts;
import net.violet.platform.api.actions.voices.GetAll;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.maps.LangInformationMap;
import net.violet.platform.api.maps.VoiceInformationMap;
import net.violet.vadmin.objects.data.HardwareData;
import net.violet.vadmin.objects.data.LanguageData;
import net.violet.vadmin.objects.data.VoiceData;
import net.violet.vadmin.util.SessionTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AdminAction extends DispatchActionWithLog {

	@Override
	public ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (!SessionTools.isLogged(request)) {
			return mapping.findForward("adminrefused");
		}
		return dispatchExecute(mapping, form, request, response);
	}

	public Date getDate(int inYear, int inMonth, int inDay) {
		Calendar c = Calendar.getInstance();
		c.setLenient(false);
		c.set(inYear, inMonth, inDay);
		try {
			return c.getTime();
		} catch (IllegalArgumentException iAE) {}
		return null;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	protected List<String> generateProductsList() {
		final Action theAction = new GetProducts();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		List<String> namesList = null;
		try {
			namesList = (List<String>) Admin.processRequest(theAction, theParams);
		} catch (final APIException e) { //
		}
		if (namesList != null) {
			return namesList;
		}
		return new ArrayList<String>();
	}

	protected List<LanguageData> generateLanguagesList() {
		final List<LanguageData> langList = new ArrayList<LanguageData>();
		
		Action theAction = new GetSiteLanguages();
		Map<String, Object> theParams = new HashMap<String, Object>();
		List<LangInformationMap> theInformationMapList = null;
		try {
			theInformationMapList = (List<LangInformationMap>) Admin.processRequest(theAction, theParams);
		} catch (final APIException e) {
		}
		if (theInformationMapList != null) {
			for (final LangInformationMap inLangInfo : theInformationMapList) {
				langList.add(buildObjectData(inLangInfo));
			}
		}
		return langList;
	}
	
	private LanguageData buildObjectData(LangInformationMap inLangInformation) {

		final LanguageData theFormData = new LanguageData();

		if (inLangInformation != null) {
			theFormData.setName((String) inLangInformation.get("title"));
			theFormData.setIso_code((String) inLangInformation.get("isocode"));
		}
		return theFormData;
	}

	protected List<HardwareData> generateHardwareList() {
		List<HardwareData> theHardwareListData = new ArrayList<HardwareData>();
		theHardwareListData.add(new HardwareData("Nabaztag", "violet.nabaztag.tag"));
		theHardwareListData.add(new HardwareData("Nabaztag/tag", "violet.nabaztag.tagtag"));
		theHardwareListData.add(new HardwareData("Book", "violet.rfid.book"));
		theHardwareListData.add(new HardwareData("Ztamp", "violet.rfid.ztamp"));
		theHardwareListData.add(new HardwareData("Nanoztag", "violet.rfid.nanoztag"));
		theHardwareListData.add(new HardwareData("RFID Other", "violet.rfid.other"));
		theHardwareListData.add(new HardwareData("Mirror", "violet.mirror"));
		theHardwareListData.add(new HardwareData("Daldal", "violet.daldal"));
		return theHardwareListData;
	}
	
	
	protected List<VoiceData> generateTTSVoiceList() {
		
		final List<VoiceData> voiceList = new ArrayList<VoiceData>();
		List<VoiceInformationMap> theVoices = null;
		final Action theAction = new GetAll();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		try{
			theVoices = (List<VoiceInformationMap>) Admin.processRequest(theAction, theParams);
		}catch (APIException e) {
		}
		if (theVoices != null) {
			for (final VoiceInformationMap inVoiceInfo : theVoices) {
				voiceList.add(buildVoiceData(inVoiceInfo));
			}
		}
		return voiceList;
	}
	
	private VoiceData buildVoiceData(VoiceInformationMap inVoiceInformation) {

		final VoiceData theFormData = new VoiceData();
		if (inVoiceInformation != null) {
			theFormData.setId((String) inVoiceInformation.get("id"));
			theFormData.setLanguage((String) inVoiceInformation.get("language"));
			theFormData.setTitle((String) inVoiceInformation.get("title"));
		}
		return theFormData;
	}
	
	protected List<String> generateCategoriesList() {
		final Action theAction = new Get();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		try {
			return (List<String>) Admin.processRequest(theAction, theParams);
		} catch (final APIException e) {
		}

		return Collections.emptyList();
	}
}

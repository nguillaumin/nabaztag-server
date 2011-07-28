package net.violet.vadmin.actions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.common.StringShop;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchMessageException;
import net.violet.platform.api.maps.DicoInformationMap;
import net.violet.vadmin.forms.AdminDicoForm;
import net.violet.vadmin.objects.data.DicoData;
import net.violet.vadmin.objects.data.LanguageData;
import net.violet.vadmin.util.AdminConstantes;
import net.violet.vadmin.util.DicoAPI;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class AdminDicoAction extends AdminAction {

	private static final String DISPLAY_DICO = "displayDico";
	//TODO: Call the method from AdminAction when 'My' will disappear.
	public static final List<LanguageData> theLanguages = DicoAPI.generateTestLanguagesList();


	/**
	 * Load the page after any process
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final AdminDicoForm myForm = (AdminDicoForm) form;
		List<DicoData> dicos = new LinkedList<DicoData>();
		for(LanguageData aLanguageData : theLanguages) {
			dicos.add(new DicoData(aLanguageData.getIso_code(), StringShop.EMPTY_STRING));
		}
		myForm.setUrl(AdminConstantes.IMG_PATH+DicoAPI.CVS_FILE_PATH);
		myForm.setValues(dicos);

		saveErrors(request, myForm.getErrors());
		return mapping.getInputForward();
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addDico(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){

		final AdminDicoForm myForm = (AdminDicoForm) form;
		final ActionMessages errors = new ActionMessages();
		final String dicoKey = myForm.getDicoName();

		//check if name empty
		if(StringShop.EMPTY_STRING.equals(dicoKey)){
			errors.add("emptyKey", new ActionMessage(StringShop.EMPTY_STRING));
		}

		if(errors.isEmpty()) {
			for(DicoData aDico : myForm.getValues()){

				final Map<String, Object> theParams = new HashMap<String, Object>();
				theParams.put("key", dicoKey);
				theParams.put("language", aDico.getIso_code());
				theParams.put("text", aDico.getValue());
				theParams.put("page", StringShop.EMPTY_STRING);
				try {
					if(DicoAPI.create(theParams) == null){
						errors.add("keyExisting", new ActionMessage(StringShop.EMPTY_STRING));
					}
					else{
						errors.add("dicoCreated", new ActionMessage(StringShop.EMPTY_STRING));
					}
				} catch (InvalidParameterException e) {
					errors.add("invalidParameter", new ActionMessage(StringShop.EMPTY_STRING));
				} catch (APIException e) {
					errors.add("dicoNotCreated", new ActionMessage(StringShop.EMPTY_STRING));
				}
			}
		}
		myForm.setErrors(errors);
		return load(mapping, myForm, request, response);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward displayDico(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response){

		final List<DicoData> theDicoDataList = new LinkedList<DicoData>();
		final AdminDicoForm myForm = (AdminDicoForm) form;
		final ActionMessages errors = new ActionMessages();
		final String dicoKey = myForm.getDicoName();

		if(StringShop.EMPTY_STRING.equals(dicoKey)){
			errors.add("emptyKey", new ActionMessage(StringShop.EMPTY_STRING));
		}

		if(errors.isEmpty()) {

			final Map<String, Object> theParams = new HashMap<String, Object>();
			theParams.put("id", dicoKey);
			try {

				final List<DicoInformationMap> theDicoList = (List<DicoInformationMap>) DicoAPI.getByKey(theParams);


				Map<String, Map<String, String>> theDicoByIso = new HashMap<String, Map<String, String>>();

				if(!theDicoList.isEmpty()){
					for(DicoInformationMap aDicoMap : theDicoList){
						Map<String, String> theDicoValues = new HashMap<String, String>();
						theDicoValues.put("id", aDicoMap.get(DicoInformationMap.ID).toString());
						theDicoValues.put("value", aDicoMap.get(DicoInformationMap.TEXT).toString());
						theDicoByIso.put(aDicoMap.get(DicoInformationMap.LANGUAGE).toString(), theDicoValues);
					}

					for(LanguageData aLanguageData : theLanguages){
						DicoData theDicoData = new DicoData(StringShop.EMPTY_STRING, aLanguageData.getIso_code(), StringShop.EMPTY_STRING);
						Map<String, String> values = theDicoByIso.get(aLanguageData.getIso_code());
						if(values != null){
							theDicoData.setValue(values.get("value"));
							theDicoData.setId(values.get("id"));
						}
						theDicoDataList.add(theDicoData);
					}
				}
				myForm.setValues(theDicoDataList);
				myForm.setDisplay(DISPLAY_DICO);
			} catch (InvalidParameterException e) {
				errors.add("invalidParameter", new ActionMessage(StringShop.EMPTY_STRING));
			} catch (NoSuchMessageException e) {
				errors.add("inexistingKey", new ActionMessage(StringShop.EMPTY_STRING));
			} catch (APIException e) {
				errors.add("internalError", new ActionMessage(StringShop.EMPTY_STRING));
			}
		}
		saveErrors(request, errors);
		return mapping.getInputForward();
	}


	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response){

		final AdminDicoForm myForm = (AdminDicoForm) form;
		final ActionMessages errors = new ActionMessages();
		final String dicoKey = myForm.getDicoName();

		for(DicoData aDico : myForm.getValues()){

			final Map<String, Object> theParams = new HashMap<String, Object>();
			theParams.put("id", aDico.getId());
			theParams.put("key", dicoKey);
			theParams.put("language", aDico.getIso_code());
			theParams.put("text", aDico.getValue());
			theParams.put("page", StringShop.EMPTY_STRING);
			try {
				DicoInformationMap aDicoInformationMap;
//					Create a new entry
				if(StringShop.EMPTY_STRING.equals(aDico.getId())){
					aDicoInformationMap = (DicoInformationMap)DicoAPI.create(theParams);
				}else{
					aDicoInformationMap = (DicoInformationMap)DicoAPI.update(theParams);
				}

				if(aDicoInformationMap == null){
					errors.add("keyExisting", new ActionMessage(StringShop.EMPTY_STRING));
				}
			} catch (InvalidParameterException e) {
				errors.add("invalidParameter", new ActionMessage(StringShop.EMPTY_STRING));
			} catch (APIException e) {
				errors.add("dicoNotUpdated", new ActionMessage(StringShop.EMPTY_STRING));
			}
		}
		if(errors.isEmpty()){
			errors.add("dicoUpdated", new ActionMessage(StringShop.EMPTY_STRING));
		}
		myForm.setDicoName(dicoKey);
		saveErrors(request, errors);
		return mapping.getInputForward();
	}

	public ActionForward export(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){

		try {
			DicoAPI.exportDicoValues();
		} catch (APIException e) {
			e.printStackTrace();
		}
		saveErrors(request, new ActionMessages());
		return load(mapping, form, request, response);

	}

	public ActionForward importDico(ActionMapping mapping, ActionForm form, @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response){

		final AdminDicoForm myForm = (AdminDicoForm) form;

		try {
			DicoAPI.importDicoValues(myForm.getImportFile().getInputStream());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (APIException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();

	}


	public ActionForward createAndRetrieve(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		return load(mapping, form, request, response);
	}
}

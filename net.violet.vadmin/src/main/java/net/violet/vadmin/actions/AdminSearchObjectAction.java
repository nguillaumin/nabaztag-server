package net.violet.vadmin.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.common.StringShop;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.actions.admin.object.SearchObject;
import net.violet.platform.api.actions.admin.object.SearchWaitingObject;
import net.violet.platform.api.actions.admin.object.SetSerial;
import net.violet.platform.api.actions.admin.user.SearchUser;
import net.violet.platform.api.actions.objects.Delete;
import net.violet.platform.api.actions.people.SetEmail;
import net.violet.platform.api.actions.subscriptions.Get;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NameAlreadyExistsException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.api.exceptions.NotModifiedException;
import net.violet.platform.api.exceptions.ObjectAlreadyExistsException;
import net.violet.platform.api.maps.admin.AdminAccountInformationMap;
import net.violet.platform.api.maps.admin.AdminObjectInformationMap;
import net.violet.platform.api.maps.applications.ApplicationSubscriptionMap;
import net.violet.platform.api.maps.objects.ObjectInformationMap;
import net.violet.platform.api.maps.objects.ObjectPreferencesMap;
import net.violet.platform.api.maps.persons.PersonInformationMap;
import net.violet.platform.api.maps.persons.PersonPreferencesMap;
import net.violet.platform.api.maps.persons.PersonProfileMap;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.Feed.Type;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.vadmin.forms.AdminSearchObjectForm;
import net.violet.vadmin.objects.data.ObjectData;
import net.violet.vadmin.objects.data.ServiceData;
import net.violet.vadmin.objects.data.TagTmpData;
import net.violet.vadmin.objects.data.UserData;
import net.violet.vadmin.util.SessionTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

public class AdminSearchObjectAction extends AdminAction {

	private static final String RSSFULL_ID = "81fA324dc3a5";
	private static final String violetObject = "e64eOc27d4345"; // native id : 58958;
	private static final String displayListObjects = "displayList";
	private static final String displayObjectDetail = "displayDetail";
	private static final String displayObjectServices = "displayServices";
	private static final String displayUserList = "displayUserList";

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final AdminSearchObjectForm myForm = (AdminSearchObjectForm) form;
		myForm.setLangList(generateLanguagesList());
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
	public ActionForward searchByMacAddress(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final MessageResources messageResources = getResources(request);
		final AdminSearchObjectForm myForm = (AdminSearchObjectForm) form;
		final ActionMessages errors = myForm.getErrors();
		final String serial = myForm.getMacAddress();

		if(StringShop.EMPTY_STRING.equals(serial)){
			errors.add("serialEmpty", new ActionMessage("error.fill", messageResources.getMessage("common.serial").toLowerCase()));
		}
		else {	
			final Action theAction = new SearchObject();
			final Map<String, Object> theParams = new HashMap<String, Object>();
			theParams.put("sn", serial.trim());

			try {
				final AdminAccountInformationMap theResultMap = (AdminAccountInformationMap)Admin.processRequest(theAction, theParams);
				myForm.setUserData(createUserInformation(theResultMap));
				myForm.setObjectData(myForm.getUserData().getUser_objects().get(0));
				myForm.setDisplay(displayObjectDetail);
			} catch (final NoSuchObjectException e) {
				// The object can be in the tagTmp database
				TagTmpData theTagTmp = createTagTmpInformation(serial);
				if (theTagTmp != null) {
					errors.add("serialInTagTmp", new ActionMessage("message.serialInTagTmp", theTagTmp.getTagTmp_type(), theTagTmp.getTagTmp_lastDay(), theTagTmp.getTagTmp_ip()));
				} else {
					errors.add("serialWithoutObject", new ActionMessage("message.serialWithoutObject", messageResources.getMessage("common.name").toLowerCase()));
				}
			} catch (final APIException e) {
				errors.add("exception", new ActionMessage("error.exception"));
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
	public ActionForward searchByMailAddress(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final MessageResources messageResources = getResources(request);
		final AdminSearchObjectForm myForm = (AdminSearchObjectForm) form;
		final ActionMessages errors = myForm.getErrors();
		final String email = myForm.getMailAddress();

		if(StringShop.EMPTY_STRING.equals(email)){
			errors.add("mailEmpty", new ActionMessage("error.fill", messageResources.getMessage("common.email").toLowerCase()));
		}
		else{
			final Action theAction = new SearchUser();
			final Map<String, Object> theParams = new HashMap<String, Object>();
			theParams.put("email", email.trim());

			try {
				List<AdminAccountInformationMap> theResult = (List<AdminAccountInformationMap>)Admin.processRequest(theAction, theParams);
				myForm.setUserData(createUserInformation(theResult.get(0)));
				myForm.setDisplay(displayListObjects);
			} catch (final NoSuchPersonException e) {
				errors.add("mailWithoutUser", new ActionMessage("message.mailWithoutUser"));
			} catch (final APIException e) {
				errors.add("exception", new ActionMessage("error.exception"));
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
	public ActionForward searchByUserName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final MessageResources messageResources = getResources(request);
		final AdminSearchObjectForm myForm = (AdminSearchObjectForm) form;
		final ActionMessages errors = myForm.getErrors();
		final String userName = myForm.getName();

		if(StringShop.EMPTY_STRING.equals(userName)){
			errors.add("userNameEmpty", new ActionMessage("error.fill", messageResources.getMessage("common.email").toLowerCase()));
		}
		else {
		
			final Action theAction = new SearchUser();
			final Map<String, Object> theParams = new HashMap<String, Object>();
			theParams.put("name", userName);
			
			try {
				List<UserData> theUserList = new LinkedList<UserData>();
				for(AdminAccountInformationMap anAdminAccountMap : (List<AdminAccountInformationMap>)Admin.processRequest(theAction, theParams)){
					theUserList.add(createUserInformation(anAdminAccountMap));
				}
				myForm.setListUserData(theUserList);
				myForm.setDisplay(displayUserList);
			} catch (final NoSuchPersonException e) {
				errors.add("exception", new ActionMessage("error.exception"));
			} catch (final APIException e) {
				errors.add("exception", new ActionMessage("error.exception"));
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
	public ActionForward updateObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final MessageResources messageResources = getResources(request);
		final AdminSearchObjectForm myForm = (AdminSearchObjectForm) form;
		final ActionMessages errors = new ActionMessages();
		final ObjectData theObjectData = myForm.getObjectData();

		final Action theAction = new SetSerial();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(SetSerial.SERIAL, theObjectData.getObject_serial());
		theParams.put("id", theObjectData.getObject_id());

		try {
			Admin.processRequest(theAction, theParams);
			errors.add("updated", new ActionMessage("success.updated", messageResources.getMessage("common.serial").toLowerCase()));
		} catch (final InvalidParameterException e) {
			errors.add("invalidSerial", new ActionMessage("message.invalid_serial"));
		} catch (final ObjectAlreadyExistsException e) {
			errors.add("existingSerial", new ActionMessage("message.existing_serial"));
		} catch (final APIException e) {
			errors.add("exception", new ActionMessage("error.exception"));
		}
		
		myForm.setDisplay(displayObjectDetail);
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
	public ActionForward updateUserMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final MessageResources messageResources = getResources(request);
		final AdminSearchObjectForm myForm = (AdminSearchObjectForm) form;
		final ActionMessages errors = new ActionMessages();

		final UserData theUserData = myForm.getUserData();

		final String theSession = SessionTools.createSession(myForm.getMailAddress(), theUserData.getUser_password());
		final Action theAction = new SetEmail();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("id", theUserData.getUser_id());
		theParams.put("email", theUserData.getUser_mail());
		theParams.put("password", theUserData.getUser_password());
		theParams.put("session", theSession);

		try {
			Admin.processRequest(theAction, theParams);
			errors.add("updated", new ActionMessage("success.updated", messageResources.getMessage("common.email").toLowerCase()));
			myForm.setMailAddress(theUserData.getUser_mail());
		} catch (NameAlreadyExistsException e) {
			errors.add("existingMail", new ActionMessage("message.existing_mail"));
		} catch (InvalidParameterException e) {
			errors.add("invalidMail", new ActionMessage("message.invalid_mail"));
		} catch (APIException e) {
			errors.add("exception", new ActionMessage("error.exception"));
		}
		
		myForm.setUserData(theUserData);
		myForm.setDisplay(displayListObjects);
		myForm.setObjectData(myForm.getObjectData());
		myForm.setErrors(errors);

		return load(mapping, myForm, request, response);
	}

	/**
	 * Find all the RSS Full owned by the user
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findServices(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){

		final AdminSearchObjectForm myForm = (AdminSearchObjectForm) form;
		final ActionMessages errors = new ActionMessages();
		final String objectId = myForm.getObjectId();

		final Action theAction = new Get();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("id", objectId);
		theParams.put("application_id", RSSFULL_ID);

		try {
			List<ServiceData> servicesData = new LinkedList<ServiceData>();
			List<ApplicationSubscriptionMap> subscriptions = (List<ApplicationSubscriptionMap>)Admin.processRequest(theAction, theParams);
			for(ApplicationSubscriptionMap aSubscription : subscriptions){
				Map<String, Object> subscriptionSettings = (Map<String, Object>)aSubscription.get("settings");
				if(subscriptionSettings.containsKey("url")){
					String actionUrl = (String)subscriptionSettings.get("url");
					Feed feed = Factories.FEED.findByUrlAndType(actionUrl, Type.RSS);
					if( feed == null )
						feed = Factories.FEED.findByUrlAndType(actionUrl, Type.PODCAST);
					servicesData.add(new ServiceData(feed.getId().toString(), Factories.LANG.find(feed.getLanguage().getId()).getIETFCode(), feed.getUrl()));
				}
			}
			myForm.setListServicesData(servicesData);
			myForm.setDisplay(displayObjectServices);
			myForm.setObjectId(objectId);
		}catch (APIException e) {
			errors.add("exception", new ActionMessage("error.exception"));
		}

		myForm.setErrors(errors);
		return load(mapping, myForm, request, response);
	}

	/**
	 * Change the language of an Action
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward changeLang(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){

		final AdminSearchObjectForm myForm = (AdminSearchObjectForm) form;
		Feed theFeed = Factories.FEED.find(Long.parseLong(myForm.getActionId()));
		theFeed.updateLang(Factories.LANG.findByIsoCode(myForm.getLanguage()));

		return findServices(mapping, myForm, request, response);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward deleteObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final AdminSearchObjectForm myForm = (AdminSearchObjectForm) form;
		final ActionMessages errors = new ActionMessages();
		final String objectId = myForm.getObjectId();
		final String[] theRabbitsChecked = myForm.getObjectChecked();
		List<String> theRabbitsToKill = new ArrayList<String>();
		theRabbitsToKill.addAll(Arrays.asList(theRabbitsChecked));
		if (objectId != null) {
			theRabbitsToKill.add(objectId);
		}

		try {
			for (String aRabbitToKill : theRabbitsToKill) {
				deleteObject(aRabbitToKill);
			}
			errors.add("deleted", new ActionMessage("success.deleted"));
		} catch (final NotModifiedException e) {
			errors.add("objectUndeleted", new ActionMessage("message.object_undeleted"));
		} catch (final ForbiddenException e) {
			errors.add("objectUnallowed",new ActionMessage("message.object_unallowed"));
		} catch (final APIException e) {}

		myForm.setMailAddress(myForm.getMailAddress());
		myForm.setErrors(errors);
		return searchByMailAddress(mapping, myForm, request, response);

	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward deleteUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final AdminSearchObjectForm myForm = (AdminSearchObjectForm) form;
		final ActionMessages errors = new ActionMessages();

		UserData theUserData = myForm.getUserData();

		final Action theAction = new net.violet.platform.api.actions.admin.user.Delete();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY,theUserData.getUser_id());
		try {
			Admin.processRequest(theAction, theParams);
			errors.add("deleted", new ActionMessage("success.deleted"));
			myForm.setName(theUserData.getUser_firstName()+" "+theUserData.getUser_lastName());
			myForm.setErrors(errors);
			return load(mapping, myForm, request, response);
		} catch (ForbiddenException e) {
			errors.add("userUnallowed", new ActionMessage("message.user_unallowed"));
		} catch (APIException e) {}
		
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
	public ActionForward deleteMessages(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final AdminSearchObjectForm myForm = (AdminSearchObjectForm) form;
		final ActionMessages errors = new ActionMessages();

		ObjectData theObjectData = myForm.getObjectData();
		UserData theUserData = myForm.getUserData();

		int index = 0;
		final Action theAction = new net.violet.platform.api.actions.admin.messages.Delete();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, theUserData.getUser_id());
		theParams.put("index", index);
		if (theObjectData.getObject_id() != null) {
			theParams.put("object_id", theObjectData.getObject_id());
		}
		int sizeRow = 1000;
		try {
			while (sizeRow <= (Integer) Admin.processRequest(theAction, theParams)) {}
			errors.add("messagesDeleted", new ActionMessage(StringShop.EMPTY_STRING));
		} catch (APIException e) {
			errors.add("messagesError", new ActionMessage(StringShop.EMPTY_STRING));
		}

		myForm.setMailAddress(theUserData.getUser_mail());
		myForm.setErrors(errors);
		return searchByMailAddress(mapping, myForm, request, response);
	}

	/**
	 * 
	 * @param macAddress
	 * @return
	 */
	private TagTmpData createTagTmpInformation(String macAddress) {

		final Action theAction = new SearchWaitingObject();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("id", macAddress);

		try {
			Map<String, Object> theInformationMap = (Map<String, Object>) Admin.processRequest(theAction, theParams);
			return  new TagTmpData((String) theInformationMap.get(SearchWaitingObject.TYPE), (String) theInformationMap.get(SearchWaitingObject.LASTTIME), (String) theInformationMap.get(SearchWaitingObject.IP));
		} catch (APIException e) {
			return null;
		}
	}

	/**
	 * 
	 * @param inObjectId
	 * @throws NotModifiedException
	 * @throws ForbiddenException
	 * @throws NoSuchObjectException
	 * @throws APIException
	 */
	private void deleteObject(String inObjectId) throws NotModifiedException, ForbiddenException, NoSuchObjectException, APIException {

		if (violetObject.equals(inObjectId)) {
			throw new ForbiddenException();
		}

		final Action theAction = new Delete();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, inObjectId);
		Admin.processRequest(theAction, theParams);
	}

	/**
	 * 
	 * @param inInformationMap
	 * @return
	 */
	private UserData createUserInformation(final Object inInformationMap) {

		final Map<String, Object> theInformationMap = (Map<String, Object>) inInformationMap;
		final PersonInformationMap theUserInformationMap = (PersonInformationMap) theInformationMap.get(AdminAccountInformationMap.USER_INFORMATION);
		final PersonPreferencesMap theUserPreferencesMap = (PersonPreferencesMap) theInformationMap.get(AdminAccountInformationMap.USER_PREFERENCES);
		final PersonProfileMap theUserProfileMap = (PersonProfileMap) theInformationMap.get(AdminAccountInformationMap.USER_PROFILE);

		final UserData theUser = buildUserData(theUserInformationMap, theUserPreferencesMap, theUserProfileMap);

		List<ObjectData> theUserObjects = new LinkedList<ObjectData>();
		for(AdminObjectInformationMap anObjectInformationMap : (List<AdminObjectInformationMap>)theInformationMap.get(AdminAccountInformationMap.USER_OBJECTS)){
			theUserObjects.add(buildObjectData(anObjectInformationMap));
		}
		theUser.setUser_objects(theUserObjects);

		return theUser;
	}

	/**
	 * 
	 * @param inUserInformationMap
	 * @param inUserPreferencesMap
	 * @param inUserProfileMap
	 * @return
	 */
	private UserData buildUserData(PersonInformationMap inUserInformationMap, PersonPreferencesMap inUserPreferencesMap, PersonProfileMap inUserProfileMap){

		final UserData theUserData = new UserData();

		theUserData.setUser_firstName((String) inUserProfileMap.get(PersonProfileMap.FIRST_NAME));
		theUserData.setUser_lastName((String) inUserProfileMap.get(PersonProfileMap.LAST_NAME));
		theUserData.setUser_password(inUserInformationMap.get(AdminAccountInformationMap.USER_PASSWORD).toString());
		theUserData.setUser_mail((String) inUserInformationMap.get(PersonInformationMap.EMAIL));
		theUserData.setUser_id((String) inUserInformationMap.get(PersonInformationMap.ID));
		theUserData.setUser_creationDate(inUserInformationMap.get(PersonInformationMap.CREATION_DATE).toString());
		theUserData.setUser_lang((String) inUserPreferencesMap.get(PersonPreferencesMap.LANGUAGE));

		return theUserData;
	}

	/**
	 * 
	 * @param inAdminObjectInformation
	 * @return
	 */
	private ObjectData buildObjectData(AdminObjectInformationMap inAdminObjectInformation) {

		final ObjectData theFormData = new ObjectData();
		final ObjectInformationMap theInformationMap = (ObjectInformationMap) inAdminObjectInformation.get(AdminObjectInformationMap.OBJECT_INFORMATION);
		final ObjectPreferencesMap thePreferencesMap = (ObjectPreferencesMap) inAdminObjectInformation.get(AdminObjectInformationMap.OBJECT_PREFERENCES);

		theFormData.setObject_id((String) theInformationMap.get(ObjectInformationMap.ID));
		theFormData.setObject_login((String) theInformationMap.get(ObjectInformationMap.NAME));
		theFormData.setObject_hardware((String) theInformationMap.get(ObjectInformationMap.HW_TYPE));
		theFormData.setObject_serial((String) theInformationMap.get(ObjectInformationMap.SERIAL_NUMBER));
		theFormData.setObject_ping(theInformationMap.get(AdminObjectInformationMap.LAST_PING).toString());
		theFormData.setObject_timezone((String) thePreferencesMap.get(ObjectPreferencesMap.TIMEZONE));
		theFormData.setObject_private((thePreferencesMap.get(ObjectPreferencesMap.PRIVATE).toString()));
		theFormData.setObject_visible(thePreferencesMap.get(ObjectPreferencesMap.VISIBLE).toString());

		return theFormData;
	}
}
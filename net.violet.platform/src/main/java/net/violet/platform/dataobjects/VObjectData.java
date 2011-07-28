package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Pattern;

import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.BlackListedException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.api.exceptions.ObjectAlreadyExistsException;
import net.violet.platform.api.exceptions.ParentalFilterException;
import net.violet.platform.applications.ClockHandler;
import net.violet.platform.applications.MoodHandler;
import net.violet.platform.applications.SubscriptionManager;
import net.violet.platform.applications.TaichiHandler;
import net.violet.platform.datamodel.AppletSettings;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Event;
import net.violet.platform.datamodel.Hardware;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.MessageSent;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.Nabcast;
import net.violet.platform.datamodel.ObjectPreferences;
import net.violet.platform.datamodel.ObjectProfile;
import net.violet.platform.datamodel.ObjectSleep;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.UserPwd;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.TaichiDataFactory.TAICHI_FREQUENCY;
import net.violet.platform.message.application.factories.ClockMessageFactory;
import net.violet.platform.schedulers.DailyHandler;
import net.violet.platform.schedulers.FrequencyHandler;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.SleepTime;
import net.violet.vlisp.services.ManageMessageServices;

import org.apache.log4j.Logger;

public class VObjectData extends APIData<VObject> {

	private static final Logger LOGGER = Logger.getLogger(VObjectData.class);

	private static final CCalendar LAST_REFRESH_CACHE = new CCalendar(false);

	private static final Map<ObjectType, List<VObjectData>> objectDataCacheMap = new HashMap<ObjectType, List<VObjectData>>();

	private static final int CACHE_TTL = 1200000;

	/*
	 * A valid email username contains only alphanumeric chars, and additional
	 * '_', '-', '.' chars that cannot be found in first or last position the
	 * lenght of the name cannot exceed 50 characters
	 */
	private static final Pattern VALID_EMAIL_PATTERN = Pattern.compile("\\p{Alnum}[-_\\.\\p{Alnum}]{0,48}\\p{Alnum}");

	static {
		for (final ObjectType type : ObjectType.values()) {
			VObjectData.objectDataCacheMap.put(type, new ArrayList<VObjectData>());
		}
	}

	public static VObjectData getData(VObject inVObject) {
		try {
			return RecordData.getData(inVObject, VObjectData.class, VObject.class);
		} catch (final InstantiationException e) {
			VObjectData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			VObjectData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			VObjectData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			VObjectData.LOGGER.fatal(e, e);
		}

		return null;
	}

	/**
	 * USED BY REFLECTION
	 * 
	 * @param inObject
	 */
	public VObjectData(VObject inObject) {
		super(inObject);
	}

	@Override
	protected ObjectClass getObjectClass() {
		return ObjectClass.VOBJECT;
	}

	/**
	 * @return the attribute id
	 */
	public long getId() {
		final VObject theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getId();
		}

		return 0;
	}

	/**
	 * @return the attribute object_login
	 */
	public String getObject_login() {
		final VObject theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getObject_login() != null)) {
			return theRecord.getObject_login();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public UserData getOwner() {
		return UserData.getData(this.getRecord().getOwner());
	}

	public boolean hasInbox() {
		return (getObjectType().instanceOf(ObjectType.NABAZTAG)) || !(SubscriptionData.findByApplicationAndObject(Application.NativeApplication.INBOX.getApplication(), getRecord()).isEmpty());
	}

	/**
	 * Accesseur à partir d'un ID application.
	 * 
	 * @param isMandatory L'objet doit exister
	 * @return l'objet ou <code>null</code> si l'objet n'existe pas.
	 * @throws NoSuchObjectException
	 */
	public static VObjectData findByAPIId(String inAPIId, String inAPIKey) {
		VObjectData theResult = null;

		final long theID = APIData.fromObjectID(inAPIId, ObjectClass.VOBJECT, inAPIKey);
		if (theID != 0) {
			final VObject theObject = Factories.VOBJECT.find(theID);
			if (theObject != null) {
				theResult = VObjectData.getData(theObject);
			}
		}

		return theResult;
	}

	/**
	 * Returns the creation Date of the object. Caution : this method can return
	 * a null value (the only way to return an unvalid date).
	 * 
	 * @return the Date (java.util.Date) or null.
	 */
	public Date getCreationDate() {
		final VObject theRecord = getRecord();
		if (theRecord != null) {
			return new Date(theRecord.getObject_creation() * 1000);
		}

		return null;
	}

	/**
	 * Returns a string representation of the object timezone.
	 * 
	 * @return the timezone, or an empty string.
	 */
	public String getTimeZone() {
		final VObject theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getTimeZone().getTimezone_javaId();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public Date getLastPing() {
		final VObject theRecord = getRecord();
		if (theRecord != null) {
			return new Date(theRecord.getObject_lastping() * 1000);
		}
		return null;
	}

	/**
	 * Returns the object state.
	 * 
	 * @return the state, -1 if there is a problem.
	 */
	public int getState() {
		final VObject theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getObject_state();
		}

		return -1;
	}

	/**
	 * Returns the object state.
	 * 
	 * @return the state, -1 if there is a problem.
	 */
	public int getMode() {
		final VObject theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getObject_mode();
		}

		return -1;
	}

	public String getSerial() {
		final VObject theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getObject_serial();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public static List<VObjectData> findByOwner(UserData inOwner) {
		if ((inOwner == null) || (inOwner.getRecord() == null)) {
			return Collections.emptyList();
		}

		return VObjectData.generateList(Factories.VOBJECT.findByOwner(inOwner.getRecord()));
	}

	/**
	 * Returns a list of the most recent VObjectData objects (i.e. the most
	 * recent VObject objects). The returned list can be shorter than expected
	 * (i.e. size() < count).
	 * 
	 * @param count the maximum number of results to return (i.e. the returned
	 *            list maximum size)
	 * @return a list of VObjectData objects.
	 */
	public static List<VObjectData> findLastCreatedByType(int count, ObjectType inType) {
		synchronized (VObjectData.LAST_REFRESH_CACHE) {
			if ((VObjectData.objectDataCacheMap.get(inType).size() < count) || (System.currentTimeMillis() - VObjectData.LAST_REFRESH_CACHE.getTimeInMillis() > VObjectData.CACHE_TTL)) {
				VObjectData.objectDataCacheMap.put(inType, VObjectData.generateList(Factories.VOBJECT.findLastCreatedByHardwares(count, inType.getHardwares())));
				VObjectData.LAST_REFRESH_CACHE.setTimeInMillis(Calendar.getInstance().getTimeInMillis());
			}
		}

		//		Beware of concurrent modifications.

		final List<VObjectData> theList = new LinkedList<VObjectData>(VObjectData.objectDataCacheMap.get(inType));

		if (theList.size() <= count) {
			return theList;
		}

		return theList.subList(0, count);
	}

	/**
	 * Use this method to generate a list of VObjectData objects based on a list
	 * of VObject objects. If the provided list is null the returned list is
	 * empty but not null.
	 * 
	 * @param inObjectList the list to convert.
	 * @return a list of VObjectData.
	 */
	private static List<VObjectData> generateList(List<VObject> inObjectList) {
		if ((inObjectList != null) && (inObjectList.size() != 0)) {
			final List<VObjectData> resultList = new LinkedList<VObjectData>();

			for (final VObject anObject : inObjectList) {
				resultList.add(VObjectData.getData(anObject));
			}

			return resultList;
		}
		return Collections.emptyList();
	}

	/**
	 * Finds a VObjectData using the provided object name.
	 * 
	 * @param inObjectName the name of the object to look for.
	 * @return a VObjectData object, or null.
	 */
	public static VObjectData findByName(String inObjectName) {
		final VObject theObject = Factories.VOBJECT.findByName(inObjectName);
		if (theObject == null) {
			return null;
		}

		return VObjectData.getData(theObject);
	}

	public static VObjectData findBySerial(String inObjectSerial) {
		final VObject theObject = Factories.VOBJECT.findBySerial(inObjectSerial);
		if (theObject == null) {
			return null;
		}

		return VObjectData.getData(theObject);
	}

	public ObjectType getObjectType() {
		final VObject theRecord = getRecord();
		if (theRecord != null) {
			return ObjectType.findByHardware(theRecord.getHardware());
		}

		return null;
	}

	public void setName(String theName) {
		final VObject theRecord = getRecord();
		if (theRecord != null) {
			theRecord.setLogin(theName);
		}
	}

	public ObjectProfileData getProfile() {
		return new ObjectProfileData(this.getRecord().getProfile());
	}

	public ObjectPreferencesData getPreferences() {
		return new ObjectPreferencesData(this.getRecord().getPreferences());
	}

	public boolean isAsleep() {
		final VObject theRecord = getRecord();
		if (theRecord != null) {
			final int state = theRecord.getObject_state();
			return (state == VObject.STATUS_VEILLE) || (state == VObject.STATUS_FORCE_VEILLE) || (state == VObject.STATUS_WILLBE_FORCE_VEILLE) || (state == VObject.STATUS_WILLBE_VEILLE);
		}

		return false;
	}

	public boolean isAwaken() {
		final VObject theRecord = getRecord();
		if (theRecord != null) {
			final int state = theRecord.getObject_state();
			return (state == VObject.STATUS_ACTIF) || (state == VObject.STATUS_FORCE_ACTIF) || (state == VObject.STATUS_WILLBE_FORCE_ACTIF) || (state == VObject.STATUS_WILLBE_ACTIF);
		}

		return false;
	}

	public boolean isDisconnected() {
		final VObject theRecord = getRecord();
		if (theRecord != null) {
			return !theRecord.isConnected();
		}

		return false;
	}

	/**
	 * Retourne les heures de couché et de réveil de l'objet (ou
	 * <code>null</code>).
	 * 
	 * @param inDay le jour considéré.
	 * @return les heures de couché et de réveil.
	 */
	public ObjectSleepData getSleepObject(int inDay) {

		final ObjectSleep theObjectSleep = Factories.OBJECT_SLEEP.findInfoByObjectAndDay(getRecord(), inDay);

		if ((theObjectSleep != null) && theObjectSleep.getTimeAction().equals(ObjectSleep.SleepAction.SLEEP.toString())) {
			return new ObjectSleepData(theObjectSleep.getTimeTo(), theObjectSleep.getTimeFrom());
		}

		if ((theObjectSleep != null) && theObjectSleep.getTimeAction().equals(ObjectSleep.SleepAction.WAKE.toString())) {
			return new ObjectSleepData(theObjectSleep.getTimeFrom(), theObjectSleep.getTimeTo());
		}

		return null;
	}

	/**
	 * Finds objects according to the provided parameters (these parameters can be null). 
	 * Only visible objects are in the returned list, EXCEPT if the inUser parameter is not null. In this case, if an object is not visible
	 * but belongs to the given user then it will be added to the returned list.
	 * 
	 * An object will not be added to the returned list if its owner's name information are not filled (i.e. if the owner neither has a 
	 * first name nor a last name).
	 * 
	 * NB : nowadays objects do not have their own visibility parameter, we use the user visibility (annu_confirm) insteed.
	 * 
	 * @param theName
	 * @param theHardware
	 * @param theCity
	 * @param theCountry
	 * @param inUser
	 * @return
	 */
	public static List<VObjectData> searchObjects(String inName, ObjectType inObjectType, String inCity, String inCountry, int skip, int count) {
		final Set<Hardware.HARDWARE> theHardwares = (inObjectType != null) ? inObjectType.getHardwares() : null;
		return VObjectData.generateList(Factories.VOBJECT.searchObjects(inName, theHardwares, inCity, inCountry, skip, count));
	}

	public void setPreferences(boolean inNotifyReceived, TimezoneData theTimezone, SleepTime inSleepTime) {
		final VObject theRecord = getRecord();
		if (theRecord != null) {
			theRecord.getOwner().setNotifyMessageReceived(inNotifyReceived);
			theRecord.setTimeZone(theTimezone.getRecord());
			if (inSleepTime != null) {
				theRecord.setSleepTime(inSleepTime);
			}
		}
	}

	public VObject getReference() {
		return getRecord();
	}

	/**
	 * Check si la liste des objets existent et que le owner de chaque objet
	 * destinataire n'a pas mis de filtre parental ou blacklister la personne
	 * envoyeur
	 * 
	 * @param inParam
	 * @param inObjectList
	 * @return
	 * @throws NoSuchObjectException
	 * @throws InvalidParameterException
	 * @throws BlackListedException
	 * @throws ParentalFilterException
	 * @throws NoSuchPersonException
	 */
	public static List<VObjectData> checkObject(List<String> inObjectList, UserData inUserSender, String apiKey) throws NoSuchObjectException, InvalidParameterException, ParentalFilterException, BlackListedException, NoSuchPersonException {
		final List<VObjectData> theResult = new ArrayList<VObjectData>();

		for (final String theObjectId : inObjectList) {
			if (theObjectId == null) {
				throw new InvalidParameterException(APIErrorMessage.NOT_A_VALID_OBJECT, net.violet.common.StringShop.EMPTY_STRING);
			}

			final VObjectData object = VObjectData.findByAPIId(theObjectId, apiKey);
			if ((object == null) || !object.isValid()) {
				throw new NoSuchObjectException();
			}

			if (object.getOwner().isValid()) {
				ManageMessageServices.checkSendMessage(inUserSender.getReference(), object.getOwner().getReference());
			} else {
				throw new NoSuchPersonException(APIErrorMessage.NO_SUCH_PERSON);
			}

			theResult.add(object);
		}

		return theResult;
	}

	public static VObjectData createObject(ObjectType inObjectType, String inSerial, String inLabel, UserData inUser, String inLocation) {
		final String name = inObjectType.getPrimaryHardware().getLabel() + inSerial;
		final String theLabel;

		if (inObjectType.instanceOf(ObjectType.RFID) || (inLabel == null)) {
			theLabel = inObjectType.getPrimaryHardware().getLabel();
		} else {
			theLabel = inLabel;
		}

		final VObject theResult = Factories.VOBJECT.createObject(inObjectType.getPrimaryHardware(), inSerial, name, theLabel, inUser.getReference(), inLocation, inObjectType.getMode(), inObjectType.getState(), null);

		return VObjectData.getData(theResult);
	}

	/**
	 * Vérifie si le format du nom de l'objet est correct Note : le pseudo est
	 * utilisé comme username dans l'adresse email de l'objet (domaine
	 * @things.violet.net), c'est pourquoi il doit se conformer à la spec RFC
	 * 5322 (cf http://en.wikipedia.org/wiki/E-mail_address)
	 * 
	 * @param inName
	 * @return TRUE si le nom de l'objet est conforme à la spec RFC 5322
	 */
	public static boolean isNameValid(String inName) {

		// the first regular expression is good enough in 99% of the case
		final boolean seemsOk = VObjectData.VALID_EMAIL_PATTERN.matcher(inName).matches();

		if (seemsOk && (inName.indexOf("..", 0) > 0)) { // note : we know already that the dot cannot appear on first position (0), so > 0 is valid and faster
			// make a second verification that the dot (.) does not repeat itself
			return false;
		}
		return seemsOk;
	}

	public void setSerial(String serial) throws ObjectAlreadyExistsException, InvalidParameterException {

		if (!getObjectType().isValidSerial(serial)) {
			throw new InvalidParameterException(APIErrorMessage.NO_VALID_OBJECT_SERIAL, net.violet.common.StringShop.EMPTY_STRING);
		}

		if (VObjectData.findBySerial(serial) != null) {
			throw new ObjectAlreadyExistsException(APIErrorMessage.SERIAL_ALREADY_EXISTS);
		}

		final VObject theRecord = getRecord();
		if (theRecord != null) {
			theRecord.setSerial(serial);
		}
	}

	public TimeZone getJavaTimeZone() {
		if (getRecord() != null) {
			return getRecord().getTimeZone().getJavaTimeZone();
		}
		return TimeZone.getDefault();
	}

	/**
	 * This method is a convenient method which adds the default services to the VObject.
	 * Some subscriptions could failed but no message error is returned.
	 */
	public void addDefaultServices() {
		try {
			final String[] theUserLang = { String.valueOf(this.getPreferences().getLang().getId()) }; // this procedure has been Gerard Certified
			MoodHandler.createOrUpdateSubscription(null, this, theUserLang, FrequencyHandler.Frequency.OFTEN);
			TaichiHandler.createOrUpdateSubscription(null, this, TAICHI_FREQUENCY.FAST);
			final String[] theTypes = { ClockMessageFactory.CLOCK_TYPE.NORMAL.getId(), ClockMessageFactory.CLOCK_TYPE.FUNNY.getId() };
			ClockHandler.createOrUpdateSubscription(null, this, theUserLang, theTypes);
		} catch (final Exception e) {
			VObjectData.LOGGER.fatal(e, e);
		}

	}

	public boolean isDuplicated() {
		final VObject theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.isDuplicated();
		}

		return false;
	}

	public String getLocation() {
		final VObject theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getObject_loc();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	@Override
	protected boolean doDelete() {

		synchronized (this) { //Attempt to handle the race condition although it does not handle the multiple servers

			final VObject theObject = getRecord();

			if (theObject != null) {
				final User theOwner = theObject.getOwner();

				// Applet Settings
				for (final AppletSettings anAppletSettings : theObject.getAppletServices()) {
					anAppletSettings.delete();
				}

				// Messages
				final Messenger theMessenger = Factories.MESSENGER.findByObject(theObject);

				if (theMessenger != null) {
					for (final MessageReceived aMessageReceived : Factories.MESSAGE_RECEIVED.findMessageReceivedByRecipientOrSender(theMessenger)) {
						final Message aMessage = aMessageReceived.getMessage();
						aMessageReceived.delete();
						if (aMessage != null) {
							final Integer event_id = aMessage.getEvent_id();
							if (event_id != null) {
								final Event anEvent = Factories.EVENT.find(event_id);
								if (anEvent != null) {
									anEvent.delete();
								}
							}
							if (aMessage.isOrphan()) {
								aMessage.delete();
							}
						}
					}

					for (final MessageSent aMessageSent : Factories.MESSAGE_SENT.findMessageSentBySenderOrRecipient(theMessenger)) {
						final Message aMessage = aMessageSent.getMessage();
						aMessageSent.delete();
						if ((aMessage != null) && aMessage.isOrphan()) {
							aMessage.delete();
						}
					}
					theMessenger.delete();
				}

				for (final SubscriptionData aSubscription : SubscriptionData.findAllByObject(theObject)) {
					try {
						SubscriptionManager.deleteSubscription(aSubscription);
					} catch (final Exception e) {
						VObjectData.LOGGER.fatal(e, e);
					}
				}

				// if anything was left that we did not know about (hopefully highly unlikely)
//				for (final ObjectHasReadContent anOhrc : Factories.OBJECT_HAS_READ_CONTENT.findAllByObject(theObject)) {
//					final VAction theAction = anOhrc.getContent().getAction();
//					anOhrc.delete();
//
//					final Application theApplication = Factories.APPLICATION.find(theAction.getApplicationId());
//					theAction.deleteForApplication(theApplication);
//
//				}

				final ObjectPreferences theObjectPreferences = theObject.getPreferences();
				if (theObjectPreferences != null) {
					theObjectPreferences.delete();
				}
				final ObjectProfile theObjectProfile = theObject.getProfile();
				if (theObjectProfile != null) {
					theObjectProfile.delete();
				}

				for (final DailyHandler.Weekday aDay : DailyHandler.Weekday.getAllDays()) {
					for (final ObjectSleep anObjectSleep : Factories.OBJECT_SLEEP.findByObjectAndDay(theObject, aDay.getCalendarId())) {
						anObjectSleep.delete();
					}
				}

				// Nabcast
				for (final Nabcast aNabcast : theObject.getSubscribedNabcast().keySet()) {
					theObject.deabonne2Nabcast(aNabcast);
				}

				// Check if it was the last object of the user
				if (Factories.VOBJECT.findByOwner(theOwner).size() < 2) {
					theOwner.setUserWithoutObject();
				}

				final UserPwd theUserPwd = Factories.USER_PWD.getByPseudo(theObject.getObject_login(), theOwner);
				if (theUserPwd != null) {
					theUserPwd.delete();
				}
			}
			return super.doDelete();
		}
	}

	public static VObjectData find(long id) {
		return VObjectData.getData(Factories.VOBJECT.find(id));
	}

	public String getEmailAddress() {
		return getObject_login() + Constantes.POPMAIL;
	}

	public void changeStatus(int status) {
		final VObject theRecord = getRecord();
		if (theRecord != null) {
			theRecord.changeStatus(status);
		}
	}

	public boolean isCurrentlyRecipient() {
		if (getObjectType().instanceOf(ObjectType.RFID)) {
			return false;
		}

		final int mode = getMode();
		return (mode == VObject.MODE_PING) || (mode == VObject.MODE_XMPP);
	}

	public void setLastActivityTime() {
		final VObject theRecord = getRecord();
		if (theRecord != null) {
			theRecord.setLastActivityTime();
		}
	}

}

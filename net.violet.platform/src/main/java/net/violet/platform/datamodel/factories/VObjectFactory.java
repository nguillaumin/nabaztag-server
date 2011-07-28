package net.violet.platform.datamodel.factories;

import java.util.List;
import java.util.Set;

import net.violet.db.records.Record.RecordWalker;
import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.ObjectPreferences;
import net.violet.platform.datamodel.ObjectProfile;
import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;

public interface VObjectFactory extends RecordFactory<VObject> {

	/**
	 * Accesseur à partir d'un nom.
	 * 
	 * @param inName nom de l'objet.
	 * @return l'enregistrement (ou <code>null</code>).
	 */
	VObject findByName(String inName);

	/**
	 * Accesseur à partir d'un proprietaire.
	 * 
	 * @param owner user proprietaire de l'objet.
	 * @return les enregistrements.
	 */
	List<VObject> findByOwner(User owner);

	VObject findByOwnerAndHardware(User myUser, HARDWARE inHardware);

	VObject findRandomObject();

	VObject findBySerial(String inSerial);

	VObject findByJID(String inJID);

	VObject findByNameAndMD5Password(String inName, String inPassword);

	/**
	 * Returns a list of the most recent objects according to the provided hardware ids.
	 * 
	 * @param count the number of objects we want to retrieve.
	 * @param hardwares an array of hardware ids.
	 * @return a list of VObject objects, can be empty or shorter than expected.
	 */
	List<VObject> findLastCreatedByHardwares(int count, Set<HARDWARE> hardwares);

	/**
	 * Searchs for objects according to the provided parameters, some of them can be null and they will be ignored. Only visible objects matching ALL
	 * the valid parameters (i.e. not-null parameters) will be returned. If the provided user (inUser parameter) is not null the returned list will
	 * contain ALL the user objects, even those which are not visible. NB : nowadays, objects do not have their own visibility parameter so we use the
	 * user parameter (annu_confirm).
	 * 
	 * @param inName
	 * @param theType
	 * @param inCity
	 * @param inCountry
	 * @param skip
	 * @param count
	 * @return
	 */
	List<VObject> searchObjects(String inName, Set<HARDWARE> inHardwares, String inCity, String inCountry, int skip, int count);

	ObjectProfile createObjectProfile(VObject inObject);

	ObjectPreferences createObjectPreferences(VObject inObject, Lang inLang);

	ObjectPreferences createObjectPreferences(VObject inObject, Lang inLang, boolean inVisibilty);

	VObject createObject(HARDWARE inHardware, String inSerial, String inName, String inLabel, User inUser, String inLocation, int inMode, int inState, Files thePicture);

	int walkObjectsStatus(RecordWalker<VObject> inWalker, Timezone inTimezone, boolean postStateActif);

}

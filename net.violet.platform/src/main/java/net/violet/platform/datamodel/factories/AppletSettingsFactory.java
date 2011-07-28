package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.AppletSettings;
import net.violet.platform.datamodel.VObject;

public interface AppletSettingsFactory extends RecordFactory<AppletSettings> {

	/**
	 * gets settings values for an applet and a secondary object.
	 * 
	 * @param inObject
	 * @param appletId
	 * @param inSettingsKey
	 * @return
	 */
	List<AppletSettings> getAllAppletSettingsBySecondaryObjectAndKey(VObject inObject, long appletId, String inSettingsKey);

	/**
	 * récupère la valeur du setting de l'objet pour un applet demandé et une
	 * clé
	 * 
	 * @param inObject
	 * @param appletId
	 * @param inSettingsKey
	 * @return null ou AppletSettingsImpl
	 */
	AppletSettings getAppletSettingsByObject(VObject inObject, long appletId, String inSettingsKey);

	/**
	 * set la valeur du setting de l'objet pour un applet demandé ou la delete
	 * si inSettingsValue=null
	 * 
	 * @param inPrimaryObject
	 * @param inSecondaryObject
	 * @param appletId
	 * @param inSettingsKey
	 * @param inSettingsValue
	 */

	AppletSettings setAppletSettingsByObjects(VObject inPrimaryObject, VObject inSecondaryObject, long appletId, String inSettingsKey, String inSettingsValue, long inIsbn);

	/**
	 * récupère la valeur du setting d'un objet ou des deux objets si le
	 * deuxième existe pour un applet demandé et une clé
	 * 
	 * @param inPrimaryObject
	 * @param inSecondaryObject
	 * @param appletId
	 * @param inSettingsKey
	 * @return null ou AppletSettingsImpl
	 */
	AppletSettings getAppletSettingsByObjects(VObject inPrimaryObject, VObject inSecondaryObject, long appletId, String inSettingsKey);

	/**
	 * incrémente la valeur du setting des objets pour un applet demandé ou la
	 * delete si inSettingsValue=null utilisé pour compter le nombre de fois ou
	 * la lecture a été terminé sur un bouquin, et aussi le nombre de fois qui
	 * l'a été lu avec une voix précise
	 * 
	 * @param inPrimaryObject
	 * @param inSecondaryObject
	 * @param appletId
	 * @param inSettingsKey
	 * @param inSettingsValue
	 */

	void addCountAppletSettingsByObjects(VObject inPrimaryObject, VObject inSecondaryObject, long appletId, String inSettingsKey);
}

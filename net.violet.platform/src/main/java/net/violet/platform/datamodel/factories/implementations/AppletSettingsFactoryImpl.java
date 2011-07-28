package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.applets.interactive.SimplePlayer;
import net.violet.platform.datamodel.AppletSettings;
import net.violet.platform.datamodel.AppletSettingsImpl;
import net.violet.platform.datamodel.ApplicationImpl;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.AppletSettingsFactory;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.ConvertTools;

import org.apache.log4j.Logger;

public class AppletSettingsFactoryImpl extends RecordFactoryImpl<AppletSettings, AppletSettingsImpl> implements AppletSettingsFactory {

	private static final Logger LOGGER = Logger.getLogger(AppletSettingsFactoryImpl.class);

	public AppletSettingsFactoryImpl() {
		super(AppletSettingsImpl.SPECIFICATION);
	}

	/**
	 * gets settings values for an applet and a secondary object.
	 * 
	 * @param inObject
	 * @param appletId
	 * @param inSettingsKey
	 * @return
	 */
	public List<AppletSettings> getAllAppletSettingsBySecondaryObjectAndKey(VObject inObject, long appletId, String inSettingsKey) {

		final List<Object> theValues = Arrays.asList(new Object[] { appletId, inObject.getId(), inSettingsKey });

		return findAll("applet_id=? and secondary_object_id=? and settings_key=?", theValues);
	}

	/**
	 * récupère la valeur du setting de l'objet pour un applet demandé et une
	 * clé
	 * 
	 * @param inObject
	 * @param appletId
	 * @param inSettingsKey
	 * @return null ou AppletSettingsImpl
	 */
	public AppletSettings getAppletSettingsByObject(VObject inObject, long appletId, String inSettingsKey) {

		final List<Object> theValues = Arrays.asList(new Object[] { appletId, inObject.getId(), inSettingsKey });

		return find("applet_id=? and primary_object_id=? and settings_key=?", theValues);
	}

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

	public AppletSettings setAppletSettingsByObjects(VObject inPrimaryObject, VObject inSecondaryObject, long appletId, String inSettingsKey, String inSettingsValue, long inIsbn) {
		final long secondObjectId = (inSecondaryObject == null) ? 0 : inSecondaryObject.getId();
		final List<Object> theValues = Arrays.asList(new Object[] { appletId, inPrimaryObject.getId(), secondObjectId, inSettingsKey });
		final AppletSettingsImpl theAppletSettings = (AppletSettingsImpl) find("applet_id=? and primary_object_id=? and secondary_object_id=? and settings_key=?", theValues);
		try {

			if (theAppletSettings == null) {
				if (inSettingsValue != null) {
					new AppletSettingsImpl(appletId, inSettingsKey, inSettingsValue, inPrimaryObject, inSecondaryObject);
				}
			} else {
				if (inSettingsValue == null) {
					theAppletSettings.doDelete();
					if (SimplePlayer.APPLET_ID != appletId) {
						for (final Subscription aSubscription : Factories.SUBSCRIPTION.findByApplicationAndObject(ApplicationImpl.findByAppletId(appletId, inIsbn), inPrimaryObject)) {
							aSubscription.delete();
						}
					}
				} else {
					theAppletSettings.setValue(inSettingsValue);
				}
			}
		} catch (final SQLException anException) {
			AppletSettingsFactoryImpl.LOGGER.fatal(anException, anException);
		}
		return theAppletSettings;
	}

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
	public AppletSettings getAppletSettingsByObjects(VObject inPrimaryObject, VObject inSecondaryObject, long appletId, String inSettingsKey) {

		final long secondObjectId = (inSecondaryObject == null) ? 0 : inSecondaryObject.getId();
		final List<Object> theValues = Arrays.asList(new Object[] { appletId, inPrimaryObject.getId(), secondObjectId, inSettingsKey });

		return find("applet_id=? and primary_object_id=? and secondary_object_id=? and settings_key=?", theValues);
	}

	/**
	 * incrémente la valeur du setting des objets pour un applet demandé utilisé
	 * pour compter le nombre de fois ou la lecture a été terminé sur un
	 * bouquin, et aussi le nombre de fois qui l'a été lu avec une voix précise
	 * 
	 * @param inPrimaryObject
	 * @param inSecondaryObject
	 * @param appletId
	 * @param inSettingsKey
	 */

	public void addCountAppletSettingsByObjects(VObject inPrimaryObject, VObject inSecondaryObject, long appletId, String inSettingsKey) {
		final long secondObjectId = (inSecondaryObject == null) ? 0 : inSecondaryObject.getId();
		final List<Object> theValues = Arrays.asList(new Object[] { appletId, inPrimaryObject.getId(), secondObjectId, inSettingsKey });
		try {
			final AppletSettingsImpl theAppletSettings = (AppletSettingsImpl) find("applet_id=? and primary_object_id=? and secondary_object_id=? and settings_key=?", theValues);

			if (theAppletSettings == null) {
				new AppletSettingsImpl(appletId, inSettingsKey, "1", inPrimaryObject, inSecondaryObject);
			} else {
				final long count = ConvertTools.atol(theAppletSettings.getValue()) + 1;
				theAppletSettings.setValue(Long.toString(count));// incrémente la valeur
			}
		} catch (final SQLException anException) {
			AppletSettingsFactoryImpl.LOGGER.fatal(anException, anException);
		}
	}
}

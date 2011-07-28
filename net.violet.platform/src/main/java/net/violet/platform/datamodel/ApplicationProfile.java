package net.violet.platform.datamodel;

import java.util.Date;

import net.violet.db.records.Record;

public interface ApplicationProfile extends Record<ApplicationProfile> {

	String getTitle();

	String getDescription();

	String getInstructions();

	String getUrl();

	boolean isOpenSource();

	Date getModificationDate();

	Application getApplication();

	Files getConfigurationSettingFile();

	Files getConfigurationSchedulingFile();

	Files getPictureFile();

	Files getIconFile();

	Files getAnnounceFile();

	/**
	 * @param inTitleKey entrée dans le DicoTools.dico pour le tire de l'application
	 * @param inDescrKey entrée dans le DicoTools.dico pour la description de
	 *            l'application
	 * @param inINstrKey entrée dans le DicoTools.dico pour les instructions détaillées
	 * @param isOpenSource
	 * @param inConfigFile fichier de configuration XML ou JSON
	 * @param inSchedulingFile fichier
	 */
	void update(String inTitleKey, String inDescrKey, String inInstrKey, boolean isOpenSource, Files inConfigFile, Files inSchedulingFile, Files inPictureFile, Files inIconFile, Files inAnnounceFile);

	void update(Files inPictureFile, Files inIconFile, Files inAnnounceFile);

}

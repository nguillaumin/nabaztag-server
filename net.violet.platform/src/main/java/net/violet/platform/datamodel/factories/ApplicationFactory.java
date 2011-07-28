package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCategory;
import net.violet.platform.datamodel.ApplicationTag;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.datamodel.Hardware.HARDWARE;

public interface ApplicationFactory extends RecordFactory<Application> {

	/**
	 * Finds an application according to its name.
	 * 
	 * @param theApplicationName
	 * @return
	 */
	Application findByName(String theApplicationName);

	/**
	 * Returns all the applications belonging to the provided category.
	 * 
	 * @param inCategory
	 * @param inSkip number of application to skip
	 * @param inGetCount number of application to get
	 * @return
	 */
	List<Application> findByCategory(ApplicationCategory inCategory, int inSkip, int inGetCount);

	/**
	 * Return number of the applications belonging to the provided category.
	 * 
	 * @param inCategory
	 * @return
	 */
	long countByCategory(ApplicationCategory inCategory);

	/**
	 * Returns all the applications belonging to the provided category and
	 * hardware.
	 * 
	 * @param inCategory
	 * @param inHardware
	 * @param inSkip number of application to skip
	 * @param inGetCount number of application to get
	 * @return
	 */
	List<Application> findByCategoryAndHardware(ApplicationCategory inCategory, HARDWARE inHardware, int inSkip, int inGetCount);

	/**
	 * Return number of the applications belonging to the provided category and
	 * hardware.
	 * 
	 * @param inCategory
	 * @param inHardware
	 * @return
	 */
	long countByCategoryAndHardware(ApplicationCategory inCategory, HARDWARE inHardware);

	/**
	 * Returns all the applications belonging to the provided category and lang.
	 * 
	 * @param inCategory
	 * @param inLang
	 * @param inSkip number of application to skip
	 * @param inGetCount number of application to get
	 * @return
	 */
	List<Application> findByCategoryAndLang(ApplicationCategory inCategory, Lang inLang, int inSkip, int inGetCount);

	/**
	 * Return number of the applications belonging to the provided category and
	 * lang.
	 * 
	 * @param inCategory
	 * @param inLang
	 * @return
	 */
	long countByCategoryAndLang(ApplicationCategory inCategory, Lang inLang);

	/**
	 * Returns all the applications belonging to the provided category and lang
	 * and hardware.
	 * 
	 * @param inCategory
	 * @param inLang
	 * @param inHardware
	 * @param inSkip number of application to skip
	 * @param inGetCount number of application to get
	 * @return
	 */
	List<Application> findByCategoryAndLangAndHardware(ApplicationCategory inCategory, Lang inLang, HARDWARE inHardware, int inSkip, int inGetCount);

	/**
	 * Return number of the applications belonging to the provided category and
	 * lang and hardware.
	 * 
	 * @param inCategory
	 * @param inLang
	 * @param inHardware
	 * @return
	 */
	long countByCategoryAndLangAndHardware(ApplicationCategory inCategory, Lang inLang, HARDWARE inHardware);

	/**
	 * Returns a list of all applications linked to the provided ApplicationTag
	 * object.
	 * 
	 * @param inTag
	 * @return
	 */
	List<Application> findAllByTag(ApplicationTag inTag);

	/**
	 * Returns a list of all applications linked to the provided ApplicationTag
	 * object and Lang
	 * 
	 * @param inTag
	 * @param inLang
	 * @param inSkip number of application to skip
	 * @param inGetCount number of application to get
	 * @return
	 */
	List<Application> findAllByTagAndLang(ApplicationTag inTag, Lang inLang, int inSkip, int inGetCount);

	/**
	 * Return number of the applications linked to the provided ApplicationTag
	 * object and Lang
	 * 
	 * @param inTag
	 * @param inLang
	 * @return
	 */
	long countByTagAndLang(ApplicationTag inTag, Lang inLang);

	/**
	 * Returns a list of all applications linked to the provided ApplicationTag
	 * object and Lang and HARDWARE * @param inTag
	 * 
	 * @param inLang
	 * @param inHardware
	 * @param inSkip number of application to skip
	 * @param inGetCount number of application to get
	 * @return
	 */
	List<Application> findAllByTagAndLangAndHardware(ApplicationTag inTag, Lang inLang, HARDWARE inHardware, int inSkip, int inGetCount);

	/**
	 * Return number of the applications linked to the provided ApplicationTag
	 * object and Lang and HARDWARE * @param inTag
	 * 
	 * @param inLang
	 * @param inHardware
	 * @return
	 */
	long countByTagAndLangAndHardware(ApplicationTag inTag, Lang inLang, HARDWARE inHardware);

	/**
	 * Returns a list of all applications linked to the provided Lang and sorted
	 * by popularity
	 * 
	 * @param inLang
	 * @param inSkip number of application to skip
	 * @param inGetCount number of application to get
	 * @return
	 */
	List<Application> findAllByLangAndRank(Lang lang, int inSkip, int inGetCount);

	List<Application> findAllByLangAndCateg(Lang inLang, ApplicationCategory inCateg);

	public Application findByLink(String link);

	/**
	 * Returns a list of applications with the name starting with
	 * <code>name<code> (exemple : net.violet.rss.)
	 * 
	 * @param reference the language of the applications
	 * @param name the beginning of the application name (net.violet.rss.,
	 *            net.violet.podcast., net.violet.webradio.)
	 * @return a list of applications
	 */
	List<Application> findByLangAndApplicationNameStartingWith(Lang inLang, String startWith);

	Application create(User inAuthor, String inName, ApplicationClass inClass, ApplicationCategory inCategory, boolean isInteractive, boolean isVisible, boolean isRemovable);

	List<Application> findAllBySetting(String key, String value);

}

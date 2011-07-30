package net.violet.platform.datamodel;

import java.sql.Date;

import net.violet.db.records.Record;
import net.violet.platform.daemons.crawlers.feedHandler.ConnectionHandler.ConnectionSettings;

/**
 * Was used with RSS feeds and Podcasts, but deprecated now.
 * 
 *
 */
public interface VAction extends Record<VAction> {

	/**
	 * Accesseur sur la langue principale de l'utilisateur.
	 * 
	 * @return la langue principale.
	 */
	Lang getLang();

	Service getService();

	String getUrl();

	/**
	 * @return the idLastNews
	 */
	String getIdLastNews();

	/**
	 * @return the eTag
	 */
	String getETag();

	/**
	 * @return the lastModified
	 */
	Date getLastModified();

	/**
	 * @return the Id of the application
	 */
	long getApplicationId();

	void setVActionConnectionSettingsAndIdXML(ConnectionSettings inConnectionSettings, String inIdLastNewsRead);

	void setURLandLang(String inURL, int inLangId);

	String getAccess_right();

}

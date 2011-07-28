/**
 * 
 */
package net.violet.platform.datamodel.mock;

import java.sql.Date;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.daemons.crawlers.feedHandler.ConnectionHandler.ConnectionSettings;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Service;
import net.violet.platform.datamodel.VAction;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.ServiceFactory;

public class VActionMock extends AbstractMockRecord<VAction, VActionMock> implements VAction {

	private long mLang;
	private String mUrl;
	private final Service mService;
	private final String mAccessRight;
	private final long application_id;
	private String etag;
	private Date lastModified;
	private String idLastNews;

	public VActionMock(long inId, long inLang, String inURL, ServiceFactory.SERVICE inService, String inAccessRights, Application inApplication) {
		super(inId);

		this.mLang = inLang;
		this.mUrl = inURL;
		this.mService = inService.getService();
		this.mAccessRight = inAccessRights;
		this.application_id = inApplication.getId();
	}

	public String getAccess_right() {
		return this.mAccessRight;
	}

	public Lang getLang() {
		return Factories.LANG.find(this.mLang);
	}

	public Service getService() {
		return this.mService;
	}

	public String getUrl() {
		return this.mUrl;
	}

	public void setVActionConnectionSettingsAndIdXML(ConnectionSettings inConnectionSettings, String inIdLastNewsRead) {
		this.etag = inConnectionSettings.getETag();
		if (inConnectionSettings.getLastModified() != null) {
			this.lastModified = new Date(inConnectionSettings.getLastModifiedDate().getTime());
		}
		this.idLastNews = inIdLastNewsRead;
	}

	public String getETag() {
		return this.etag;
	}

	public String getIdLastNews() {
		return this.idLastNews;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public long getApplicationId() {
		return this.application_id;
	}

	public void setURL(String inURL) {
		this.mUrl = inURL;
	}

	public void setURLandLang(String inURL, int inLangId) {
		this.mUrl = inURL;
		this.mLang = inLangId;
	}
}

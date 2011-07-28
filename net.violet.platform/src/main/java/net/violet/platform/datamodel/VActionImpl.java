package net.violet.platform.datamodel;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.platform.daemons.crawlers.feedHandler.ConnectionHandler.ConnectionSettings;
import net.violet.platform.datamodel.factories.ServiceFactory;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;

public class VActionImpl extends ObjectRecord<VAction, VActionImpl> implements VAction {

	private static final Logger LOGGER = Logger.getLogger(VActionImpl.class);

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<VActionImpl> SPECIFICATION = new SQLObjectSpecification<VActionImpl>("action", VActionImpl.class, new SQLKey[] { new SQLKey("id"), new SQLKey("application_id") });

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { StringShop.LANG_ID, "url", StringShop.SERVICE_ID, "access_right", "application_id" };

	/**
	 * Champs de l'enregistrement.
	 */
	protected long id;
	protected int lang_id;
	protected String url;
	protected String access_right;
	protected long service_id;
	protected String id_last_news;
	protected String etag;
	protected Date last_modified;
	protected long application_id;

	public String getAccess_right() {
		return this.access_right;
	}

	/**
	 * Langue de l'action
	 */
	private final SingleAssociationNotNull<VAction, Lang, LangImpl> mLang;

	/**
	 * Référence sur le service.
	 */
	private final SingleAssociationNotNull<VAction, Service, ServiceImpl> service;

	/**
	 * Référence sur le service.
	 */
	private final SingleAssociationNotNull<VAction, Application, ApplicationImpl> application;

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected VActionImpl(long id) throws SQLException {
		init(id);
		this.mLang = new SingleAssociationNotNull<VAction, Lang, LangImpl>(this, StringShop.LANG_ID, LangImpl.SPECIFICATION);
		this.service = new SingleAssociationNotNull<VAction, Service, ServiceImpl>(this, StringShop.SERVICE_ID, ServiceImpl.SPECIFICATION);
		this.application = new SingleAssociationNotNull<VAction, Application, ApplicationImpl>(this, StringShop.APPLICATION_ID, ApplicationImpl.SPECIFICATION);
	}

	protected VActionImpl() {
		// This space for rent.
		this.mLang = new SingleAssociationNotNull<VAction, Lang, LangImpl>(this, StringShop.LANG_ID, LangImpl.SPECIFICATION);
		this.service = new SingleAssociationNotNull<VAction, Service, ServiceImpl>(this, StringShop.SERVICE_ID, ServiceImpl.SPECIFICATION);
		this.application = new SingleAssociationNotNull<VAction, Application, ApplicationImpl>(this, StringShop.APPLICATION_ID, ApplicationImpl.SPECIFICATION);
	}

	/**
	 * Constructeur à partir de valeurs (nouvelle action).
	 * 
	 * @param inLangId
	 * @param inUrl
	 * @param inAccess_right
	 * @param inServiceId
	 * @param inOutput_file
	 * @throws SQLException
	 */
	public VActionImpl(int inLangId, String inUrl, ServiceFactory.SERVICE inService, String inAccess_right, Application inApplication) throws SQLException {
		this.lang_id = inLangId;
		this.url = inUrl;
		this.service_id = inService.getService().getId();
		this.access_right = inAccess_right;
		this.application_id = inApplication.getId();

		init(VActionImpl.NEW_COLUMNS);

		this.mLang = new SingleAssociationNotNull<VAction, Lang, LangImpl>(this, StringShop.LANG_ID, LangImpl.SPECIFICATION);
		this.service = new SingleAssociationNotNull<VAction, Service, ServiceImpl>(this, StringShop.SERVICE_ID, ServiceImpl.SPECIFICATION);
		this.application = new SingleAssociationNotNull<VAction, Application, ApplicationImpl>(this, StringShop.APPLICATION_ID, ApplicationImpl.SPECIFICATION);

	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public SQLObjectSpecification<VActionImpl> getSpecification() {
		return VActionImpl.SPECIFICATION;
	}

	public Lang getLang() {
		return this.mLang.get(this.lang_id);
	}

	public Service getService() {
		return this.service.get(this.service_id);
	}

	public String getUrl() {
		return this.url;
	}

	public void setTheUrl(String url) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setUrl(theUpdateMap, url);
		update(theUpdateMap);
	}

	private void setUrl(Map<String, Object> inUpdateMap, String url) {
		if (!this.url.equals(url)) {
			this.url = url;
			inUpdateMap.put("url", url);
		}
	}

	/**
	 * Itérateur (statique) sur les objets qui ne sont plus actifs.
	 * 
	 * @param inWalker itérateur
	 * @param inTimeNoPing délai ou l'on considère que l'objet n'est plus actif
	 *            en seconde
	 * @return le nombre d'événements traités.
	 */
	public static int walkByService(RecordWalker<VAction> inWalker, long inServiceId, boolean isFree) {
		final List<Object> theValues = Collections.singletonList((Object) inServiceId);
		final String condition = " service_id = ? AND access_right = " + (isFree ? "'FREE'" : "'FULL'");
		int theResult = 0;
		try {
			theResult = AbstractSQLRecord.walk(VActionImpl.SPECIFICATION, condition, theValues, null, 0, inWalker);
		} catch (final SQLException anException) {
			VActionImpl.LOGGER.fatal(anException, anException);
		}
		return theResult;
	}

	public String getIdLastNews() {
		return this.id_last_news;
	}

	public void setIdLastNews(Map<String, Object> inUpdateMap, String idLastNews) {
		if ((idLastNews != null) && !idLastNews.equals(this.id_last_news)) {
			this.id_last_news = idLastNews;
			inUpdateMap.put("id_last_news", idLastNews);
		}
	}

	public String getETag() {
		return this.etag;
	}

	/**
	 * @param tag the eTag to set
	 */
	private void setETag(Map<String, Object> inUpdateMap, String tag) {
		this.etag = tag;
		inUpdateMap.put("etag", this.etag);
	}

	public Date getLastModified() {
		return this.last_modified;
	}

	/**
	 * @param lastModified the lastModified to set
	 */
	private void setLastModified(Map<String, Object> inUpdateMap, Date lastModified) {
		this.last_modified = lastModified;
		inUpdateMap.put("last_modified", lastModified);
	}

	public void setVActionConnectionSettingsAndIdXML(ConnectionSettings inConnectionSettings, String inIdLastNewsRead) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setETag(theUpdateMap, inConnectionSettings.getETag());
		if (inConnectionSettings.getLastModified() != null) {
			setLastModified(theUpdateMap, new Date(inConnectionSettings.getLastModifiedDate().getTime()));
		}
		setIdLastNews(theUpdateMap, inIdLastNewsRead);
		update(theUpdateMap);
	}

	public long getApplicationId() {
		return this.application.get(this.application_id).getId();
	}

	public void setURLandLang(String inURL, int inLangId) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setUrl(theUpdateMap, inURL);
		setLang(theUpdateMap, inLangId);
		update(theUpdateMap);
	}

	private void setLang(Map<String, Object> inUpdateMap, int inLangId) {
		if (this.lang_id != inLangId) {
			this.lang_id = inLangId;
			inUpdateMap.put("lang_id", inLangId);
		}
	}

}

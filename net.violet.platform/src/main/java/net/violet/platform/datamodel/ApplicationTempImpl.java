package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;

import org.apache.log4j.Logger;

public class ApplicationTempImpl extends ObjectRecord<ApplicationTemp, ApplicationTempImpl> implements ApplicationTemp {

	private static final Logger LOGGER = Logger.getLogger(ApplicationTempImpl.class);

	public static final SQLObjectSpecification<ApplicationTempImpl> SPECIFICATION = new SQLObjectSpecification<ApplicationTempImpl>("application_temp", ApplicationTempImpl.class, new SQLKey[] { new SQLKey("application_id") });

	private static final String[] NEW_COLUMNS = new String[] { "application_id", "application_link", "application_shortcut", "application_image", "application_icone", "application_stream_url", "application_type" };

	protected long application_id;
	protected String application_link;
	protected String application_shortcut;
	protected String application_image;
	protected String application_icone;
	protected long application_type;

	private final SingleAssociationNotNull<ApplicationTemp, Application, ApplicationImpl> mApplication;

	protected ApplicationTempImpl() {
		this.mApplication = new SingleAssociationNotNull<ApplicationTemp, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION);
	}

	protected ApplicationTempImpl(long appId) throws NoSuchElementException, SQLException {
		init(appId);
		this.mApplication = new SingleAssociationNotNull<ApplicationTemp, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION);
	}

	public ApplicationTempImpl(Application inApplication, String inLink, String inShortcut, String inImage, String inIcone) throws SQLException {
		this.application_id = inApplication.getId();
		this.application_link = inLink;
		this.application_shortcut = inShortcut;
		this.application_image = inImage;
		this.application_icone = inIcone;
		this.application_type = 0;

		ApplicationTempImpl.LOGGER.info("--------->" + this.application_type);
		init(ApplicationTempImpl.NEW_COLUMNS);
		this.mApplication = new SingleAssociationNotNull<ApplicationTemp, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION);
	}

	public static ApplicationTemp findByShortcut(String shortcut) {
		ApplicationTemp result = null;
		try {
			result = AbstractSQLRecord.find(ApplicationTempImpl.SPECIFICATION, "application_shortcut = ?", Arrays.asList(new Object[] { shortcut }));
		} catch (final SQLException e) {
			ApplicationTempImpl.LOGGER.fatal(e, e);
		}
		return result;
	}

	public static ApplicationTemp findByLink(String link) {
		ApplicationTemp result = null;
		try {
			result = AbstractSQLRecord.find(ApplicationTempImpl.SPECIFICATION, "application_link = ?", Arrays.asList(new Object[] { link }));
		} catch (final SQLException e) {
			ApplicationTempImpl.LOGGER.fatal(e, e);
		}
		return result;
	}

	/**
	 * @see net.violet.platform.datamodel.ObjectRecord#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<ApplicationTempImpl> getSpecification() {
		return ApplicationTempImpl.SPECIFICATION;
	}

	public Application getApplication() {
		return this.mApplication.get(this.application_id);
	}

	public String getIcone() {
		return this.application_icone;
	}

	public String getImage() {
		return this.application_image;
	}

	public String getLink() {
		return this.application_link;
	}

	public String getShortcut() {
		return this.application_shortcut;
	}

	public long getType() {
		return this.application_type;
	}

	public void setLink(String link) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setLink(theUpdateMap, link);
		update(theUpdateMap);
	}

	private void setLink(Map<String, Object> theUpdateMap, String link) {
		if (!this.application_link.equals(link)) {
			this.application_link = link;
			theUpdateMap.put("application_link", link);
		}

	}

	public void setShorcut(String shorcut) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setShorcut(theUpdateMap, shorcut);
		update(theUpdateMap);
	}

	private void setShorcut(Map<String, Object> theUpdateMap, String shorcut) {
		if (!this.application_shortcut.equals(shorcut)) {
			this.application_shortcut = shorcut;
			theUpdateMap.put("application_shortcut", shorcut);
		}
	}
}

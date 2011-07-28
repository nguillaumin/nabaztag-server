package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.SQLSpecification;
import net.violet.platform.datamodel.Hardware.HARDWARE;

import org.apache.log4j.Logger;

public class TagTmpSiteImpl extends ObjectRecord<TagTmpSite, TagTmpSiteImpl> implements TagTmpSite {

	private static final Logger LOGGER = Logger.getLogger(TagTmpSiteImpl.class);

	public static final SQLObjectSpecification<TagTmpSiteImpl> SPECIFICATION = new SQLObjectSpecification<TagTmpSiteImpl>("tag_tmp_site", TagTmpSiteImpl.class, new SQLKey("serial"));

	protected String serial;
	protected String loc;
	protected long last_day;
	protected int hardware;
	protected String ip;

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { "serial", "loc", "last_day", "hardware", "ip", };

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.TagTmpSite#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<TagTmpSiteImpl> getSpecification() {
		return TagTmpSiteImpl.SPECIFICATION;
	}

	TagTmpSiteImpl() {
		// This space for rent.
	}

	public <T extends ObjectRecord> TagTmpSiteImpl(String inSerial, String inLoc, long inLast_day, HARDWARE inHardware, String inIp) throws SQLException {
		this.serial = inSerial;
		this.loc = inLoc;
		this.last_day = inLast_day;
		this.hardware = inHardware.getId().intValue();
		this.ip = inIp;
		init(TagTmpSiteImpl.NEW_COLUMNS);
	}

	public static SQLSpecification<TagTmpSiteImpl> getSPECIFICATION() {
		return TagTmpSiteImpl.SPECIFICATION;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.TagTmpSite#getHardware()
	 */
	public HARDWARE getHardware() {
		return HARDWARE.findById(new Long(this.hardware));
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.TagTmpSite#getLast_day()
	 */
	public long getLast_day() {
		return this.last_day;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.TagTmpSite#getLoc()
	 */
	public String getLoc() {
		return this.loc;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.TagTmpSite#getSerial()
	 */
	public String getSerial() {
		return this.serial;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.TagTmpSite#getIp()
	 */
	public String getIp() {
		return this.ip;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.TagTmpSite#setLast_day(long)
	 */
	public void setLast_day(long inTime) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setLast_day(theUpdateMap, inTime);
		update(theUpdateMap);
	}

	private void setLast_day(Map<String, Object> inUpdateMap, long inTime) {
		if (inTime != this.last_day) {
			this.last_day = inTime;
			inUpdateMap.put("last_day", inTime);
		}
	}

	/**
	 * Itérateur (statique) sur les nouveaux objets en attente d'association
	 * 
	 * @param inWalker itérateur
	 * @return le nombre d'événements traités.
	 */
	public static int walkCheckNewObject(RecordWalker<TagTmpSite> inWalker) {
		final List<Object> theValues = Collections.singletonList((Object) HARDWARE.V2.getId());
		final String condition = " hardware = ? ";
		int theResult = 0;
		try {
			theResult = AbstractSQLRecord.walk(TagTmpSiteImpl.SPECIFICATION, condition, theValues, null /*
																																			 * order
																																			 * by
																																			 */, 0 /* skip */, inWalker);
		} catch (final SQLException anException) {
			TagTmpSiteImpl.LOGGER.fatal(anException, anException);
		}
		return theResult;
	}
}

package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;

import org.apache.log4j.Logger;

public class CrawlImpl extends ObjectRecord<Crawl, CrawlImpl> implements Crawl {

	private static final Logger LOGGER = Logger.getLogger(CrawlImpl.class);

	/**
	 * Spécification
	 */
	static final SQLObjectSpecification<CrawlImpl> SPECIFICATION = new SQLObjectSpecification<CrawlImpl>("crawl", CrawlImpl.class, new SQLKey("crawl_id"));

	/**
	 * Champs de l'enregistrement.
	 */
	protected long crawl_id;
	protected String crawl_source;
	protected Long crawl_type;
	protected String crawl_param;
	protected Long crawl_srv;

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { "crawl_source", "crawl_type", "crawl_param", "crawl_srv", };

	public CrawlImpl(String source, long type, String param, long srv) throws SQLException {
		super();
		this.crawl_source = source;
		this.crawl_type = type;
		this.crawl_param = param;
		this.crawl_srv = srv;
		init(CrawlImpl.NEW_COLUMNS);
	}

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected CrawlImpl(long id) throws SQLException {
		init(id);
	}

	protected CrawlImpl() {
		// This space for rent.
	}

	@Override
	public Long getId() {
		return this.crawl_id;
	}

	@Override
	public SQLObjectSpecification<CrawlImpl> getSpecification() {
		return CrawlImpl.SPECIFICATION;
	}

	@Deprecated
	public static Crawl findBySrc(String inSrc) {
		final List<Object> theValues = Arrays.asList(new Object[] { inSrc, });
		Crawl theResult = null;
		try {
			theResult = AbstractSQLRecord.find(CrawlImpl.SPECIFICATION, " crawl_source = ? ", theValues);
		} catch (final SQLException anException) {
			CrawlImpl.LOGGER.fatal(anException, anException);
		}
		return theResult;
	}

	/**
	 * Recupere les sources pour la BourseFull Test cravl_srv <> 0 pour eviter
	 * d'effacer nos propres services
	 * 
	 * @param inCrawlSrv
	 * @return
	 */
	public static Crawl findByCrawlSrv(long inCrawlSrv) {
		final List<Object> theValues = Arrays.asList(new Object[] { inCrawlSrv, });
		Crawl theResult = null;
		try {
			theResult = AbstractSQLRecord.find(CrawlImpl.SPECIFICATION, " crawl_srv = ? AND crawl_srv <> 0", theValues);
		} catch (final SQLException anException) {
			CrawlImpl.LOGGER.fatal(anException, anException);
		}
		return theResult;
	}

	public final String getCrawl_source() {
		return this.crawl_source;
	}

	public final String getCrawl_param() {
		return this.crawl_param;
	}

	/**
	 * Itérateur (statique) sur des objets déjà dans la base, à partir d'une
	 * condition.
	 * 
	 * @param inWalker itérateur
	 * @param inTypeSrc le type de source
	 * @return le nombre d'événements traités.
	 */
	public static int walkByType(RecordWalker<Crawl> inWalker, int inTypeCrawl) {
		final List<Object> theValues = Collections.singletonList((Object) inTypeCrawl);
		final String condition = " crawl_type = ? ";
		int theResult = 0;
		try {
			theResult = AbstractSQLRecord.walk(CrawlImpl.SPECIFICATION, condition, theValues, null /*
																																				 * order
																																				 * by
																																				 */, 0 /* skip */, inWalker);
		} catch (final SQLException anException) {
			CrawlImpl.LOGGER.fatal(anException, anException);
		}
		return theResult;
	}

	public void setCrawl_param(String inParam) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setCrawl_Param(theUpdateMap, inParam);
		update(theUpdateMap);
	}

	private void setCrawl_Param(Map<String, Object> inUpdateMap, String inValue) {
		if (!inValue.equals(this.crawl_param)) {
			this.crawl_param = inValue;
			inUpdateMap.put("crawl_param", inValue);
		}
	}
}

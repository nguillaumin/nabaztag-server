package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;

public class ServiceImpl extends ObjectRecord<Service, ServiceImpl> implements Service {


	private static final Logger LOGGER = Logger.getLogger(ServiceImpl.class);

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<ServiceImpl> SPECIFICATION = new SQLObjectSpecification<ServiceImpl>("service", ServiceImpl.class, new SQLKey("id"));

	/**
	 * Champs de l'enregistrement.
	 */
	protected long id;
	protected String label;
	protected String link;
	protected String img;
	protected int ttl;
	protected Long intro_file_id;
	protected Long outro_file_id;

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Service#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Service#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<ServiceImpl> getSpecification() {
		return ServiceImpl.SPECIFICATION;
	}

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected ServiceImpl(long id) throws SQLException {
		super();
		init(id);
	}

	protected ServiceImpl() {
		super();
	}

	/**
	 * Accesseur à partir d'un id.
	 * 
	 * @param id id de l'objet.
	 * @return l'enregistrement (ou <code>null</code>).
	 */
	static Service find(long id) {
		Service result = null;
		try {
			result = AbstractSQLRecord.findByKey(ServiceImpl.SPECIFICATION, ServiceImpl.SPECIFICATION.getTableKeys()[0], id);
		} catch (final SQLException se) {
			ServiceImpl.LOGGER.fatal(se, se);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Service#getImg()
	 */
	public String getImg() {
		return this.img;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Service#getLabel()
	 */
	public String getLabel() {
		return this.label;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Service#getLink()
	 */
	public String getLink() {
		return this.link;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Service#getTtl()
	 */
	public int getTtl() {
		return this.ttl;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Service#getMap()
	 */
	public Map<String, String> getMap() {
		final Map<String, String> serviceMap = new HashMap<String, String>();
		serviceMap.put("id", StringShop.EMPTY_STRING + this.id);
		serviceMap.put("label", this.label);
		serviceMap.put("link", this.link);
		serviceMap.put("ttl", StringShop.EMPTY_STRING + this.ttl);
		return serviceMap;
	}

	public Files getIntro() {
		return Factories.FILES.find(this.intro_file_id);
	}

	public Files getOutro() {
		return Factories.FILES.find(this.outro_file_id);
	}
}

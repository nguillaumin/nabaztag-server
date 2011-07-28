package net.violet.platform.datamodel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.ListAssociation;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;

public class NabLifeImpl extends ObjectRecord<NabLife, NabLifeImpl> implements NabLife {

	private static final Logger LOGGER = Logger.getLogger(NabLifeImpl.class);

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<NabLifeImpl> SPECIFICATION = new SQLObjectSpecification<NabLifeImpl>("nablife_service", NabLifeImpl.class, new SQLKey[] { new SQLKey("id"), new SQLKey("link"), new SQLKey("srvlist_id") });

	/**
	 * Champs de l'enregistrement.
	 */
	protected long id;
	protected long srvlist_id;
	protected String link;
	protected String setup;
	protected String description;
	protected String name;
	protected int nbvote;
	protected int enabled;
	protected String img;
	protected String shortcut;
	protected long service_id;
	protected int rank;
	protected String icone;
	protected long wrFlux_id;

	private List<Lang> langs;
	private final SingleAssociationNotNull<NabLife, Service, ServiceImpl> mService;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public SQLObjectSpecification<NabLifeImpl> getSpecification() {
		return NabLifeImpl.SPECIFICATION;
	}

	/**
	 * Constructeurs
	 */
	protected NabLifeImpl(long id) throws SQLException {
		init(id);
		this.mService = new SingleAssociationNotNull<NabLife, Service, ServiceImpl>(this, StringShop.SERVICE_ID, ServiceImpl.SPECIFICATION);
	}

	protected NabLifeImpl() {
		this.mService = new SingleAssociationNotNull<NabLife, Service, ServiceImpl>(this, StringShop.SERVICE_ID, ServiceImpl.SPECIFICATION);
	}

	public Service getService() {
		return this.mService.get(this.service_id);
	}

	public List<Lang> getLangs() {
		if (this.langs == null) {
			try {
				this.langs = ListAssociation.createListAssociation(this, LangImpl.SPECIFICATION, "nablife_service_lang", "nablife_service_id", StringShop.LANG_ID);
			} catch (final SQLException anException) {
				NabLifeImpl.LOGGER.fatal(anException, anException);
			}
		}
		return this.langs;
	}

	public String getDescription() {
		return this.description;
	}

	public int getEnabled() {
		return this.enabled;
	}

	public String getImg() {
		return this.img;
	}

	public String getLink() {
		return this.link;
	}

	public String getName() {
		return this.name;
	}

	public long getNbvote() {
		return this.nbvote;
	}

	public String getSetup() {
		return this.setup;
	}

	public String getShortcut() {
		return this.shortcut;
	}

	public long getWrFlux_id() {
		return this.wrFlux_id;
	}

	public String getIcone() {
		return this.icone;
	}

	public static NabLife doCreateObject(ResultSet inResultSet) throws SQLException {
		return AbstractSQLRecord.createObject(NabLifeImpl.SPECIFICATION, inResultSet);
	}

}

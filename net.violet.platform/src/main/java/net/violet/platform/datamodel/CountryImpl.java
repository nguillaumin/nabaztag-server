package net.violet.platform.datamodel;

import java.sql.SQLException;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;

public class CountryImpl extends ObjectRecord<Country, CountryImpl> implements Country {

	public static final SQLObjectSpecification<CountryImpl> SPECIFICATION = new SQLObjectSpecification<CountryImpl>("pays", CountryImpl.class, new SQLKey[] { new SQLKey("pays_id"), new SQLKey("pays_code") });

	protected long pays_id;
	protected Long pays_continent;
	protected String pays_nom;
	protected String pays_code;
	protected long main_lang_id;

	private final SingleAssociationNotNull<Country, Continent, ContinentImpl> continent;
	private final SingleAssociationNotNull<Country, Lang, LangImpl> lang;

	protected CountryImpl(long id) throws SQLException {
		super();
		init(id);
		this.continent = new SingleAssociationNotNull<Country, Continent, ContinentImpl>(this, "continent_id", ContinentImpl.SPECIFICATION, ContinentImpl.SPECIFICATION.getPrimaryKey());
		this.lang = new SingleAssociationNotNull<Country, Lang, LangImpl>(this, "lang_id", LangImpl.SPECIFICATION, LangImpl.SPECIFICATION.getPrimaryKey());

	}

	protected CountryImpl() {
		super();
		this.continent = new SingleAssociationNotNull<Country, Continent, ContinentImpl>(this, "continent_id", ContinentImpl.SPECIFICATION, ContinentImpl.SPECIFICATION.getPrimaryKey());
		this.lang = new SingleAssociationNotNull<Country, Lang, LangImpl>(this, "lang_id", LangImpl.SPECIFICATION, LangImpl.SPECIFICATION.getPrimaryKey());
	}

	@Override
	public Long getId() {
		return this.pays_id;
	}

	@Override
	public SQLObjectSpecification<CountryImpl> getSpecification() {
		return CountryImpl.SPECIFICATION;
	}

	public final String getName() {
		return this.pays_nom;
	}

	public final String getCode() {
		return this.pays_code;
	}

	public Lang getMainLanguage() {
		return this.lang.get(this.main_lang_id);
	}

	public Continent getContinent() {
		return this.continent.get(this.pays_continent);
	}
}

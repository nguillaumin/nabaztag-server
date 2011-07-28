package net.violet.platform.datamodel;

import net.violet.db.records.SQLKey;
import net.violet.db.records.associations.AssociationRecord;
import net.violet.db.records.associations.SQLAssociationSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.platform.datamodel.util.UpdateMap;

/**
 * This relation tells us that an application has translations in this language
 * and therefore can be installed by a user in this language.
 * The application it has a specific rank (popularity) in this language too
 * @author christophe - Violet
 */
public class ApplicationLangImpl extends AssociationRecord<Application, ApplicationLang, ApplicationLangImpl> implements ApplicationLang {

	public static final SQLAssociationSpecification<ApplicationLangImpl> SPECIFICATION = new SQLAssociationSpecification<ApplicationLangImpl>("application_lang", ApplicationLangImpl.class, new SQLKey("application_id", "lang_id"));

	protected long application_id;
	protected long lang_id;
	protected long rank;

	private final SingleAssociationNotNull<ApplicationLang, Application, ApplicationImpl> mApp;
	private final SingleAssociationNotNull<ApplicationLang, Lang, LangImpl> mLang;

	protected ApplicationLangImpl() {
		this.mApp = new SingleAssociationNotNull<ApplicationLang, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION);
		this.mLang = new SingleAssociationNotNull<ApplicationLang, Lang, LangImpl>(this, "lang_id", LangImpl.SPECIFICATION);
	}

	/**
	 * Full Constructor
	 * @param inApp
	 * @param inLang
	 * @param inRank
	 */
	public ApplicationLangImpl(Application inApp, Lang inLang, long inRank) {
		this.application_id = inApp.getId();
		this.lang_id = inLang.getId();
		this.rank = inRank;

		this.mApp = new SingleAssociationNotNull<ApplicationLang, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION);
		this.mLang = new SingleAssociationNotNull<ApplicationLang, Lang, LangImpl>(this, "lang_id", LangImpl.SPECIFICATION);
	}

	/**
	 * @see net.violet.platform.datamodel.ApplicationLang#getApplication()
	 */
	public Application getApplication() {
		return this.mApp.get(this.application_id);
	}

	/**
	 * @see net.violet.platform.datamodel.ApplicationLang#getLang()
	 */
	public Lang getLang() {
		return this.mLang.get(this.lang_id);
	}

	/**
	 * @see net.violet.platform.datamodel.ApplicationLang#getRank()
	 */
	public long getRank() {
		return this.rank;
	}

	/**
	 * @see net.violet.platform.datamodel.ApplicationLang#setRank(int)
	 */
	public void setRank(long rnk) {
		final UpdateMap updMap = new UpdateMap();
		this.rank = updMap.updateField("rank", this.rank, rnk);
		update(updMap);
	}

	/**
	 * Must implement this but we do not have a single ID !
	 * @see net.violet.platform.datamodel.Record#getId()
	 */
	public Long getId() {
		throw new UnsupportedOperationException("Table " + ApplicationLangImpl.SPECIFICATION.getTableName() + " does not have a primary key id !");
	}

	/**
	 * @see net.violet.platform.datamodel.AssociationRecord#getSpecification()
	 */
	@Override
	public SQLAssociationSpecification<ApplicationLangImpl> getSpecification() {
		return ApplicationLangImpl.SPECIFICATION;
	}
}

package net.violet.platform.datamodel;

import java.sql.SQLException;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;

public class ApplicationCategoryImpl extends ObjectRecord<ApplicationCategory, ApplicationCategoryImpl> implements ApplicationCategory {

	public static final SQLObjectSpecification<ApplicationCategoryImpl> SPECIFICATION = new SQLObjectSpecification<ApplicationCategoryImpl>("application_category", ApplicationCategoryImpl.class, new SQLKey("application_category_id"));

	protected long application_category_id;
	protected String application_category_name;

	protected ApplicationCategoryImpl(long id) throws SQLException {
		init(id);
	}

	protected ApplicationCategoryImpl() {
		// This space for rent.
	}

	@Override
	public SQLObjectSpecification<ApplicationCategoryImpl> getSpecification() {
		return ApplicationCategoryImpl.SPECIFICATION;
	}

	public String getName() {
		return this.application_category_name;
	}

}

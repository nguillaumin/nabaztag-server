package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.ApplicationCategory;

public class ApplicationCategoryMock extends AbstractMockRecord<ApplicationCategory, ApplicationCategoryMock> implements ApplicationCategory {

	public static final MockBuilder<ApplicationCategory> BUILDER = new MockBuilder<ApplicationCategory>() {

		@Override
		protected ApplicationCategory build(String[] inParamValues) {
			return new ApplicationCategoryMock(Long.parseLong(inParamValues[0]), inParamValues[1]);
		}
	};

	private final String categoryName;

	public ApplicationCategoryMock(long inId, String inName) {
		super(inId);
		this.categoryName = inName;
	}

	public String getName() {
		return this.categoryName;
	}

}

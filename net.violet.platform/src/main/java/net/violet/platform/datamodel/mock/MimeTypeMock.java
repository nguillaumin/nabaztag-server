package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.MimeType;

public class MimeTypeMock extends AbstractMockRecord<MimeType, MimeTypeMock> implements MimeType {

	public static final MockBuilder<MimeType> BUILDER = new MockBuilder<MimeType>() {

		@Override
		protected MimeType build(String[] inParamValues) {
			return new MimeTypeMock(Long.parseLong(inParamValues[0]), inParamValues[1], inParamValues[2]);
		}
	};

	private final String label;
	private final String extension;

	/**
	 * @param inId
	 * @param inName
	 * @param inUserId
	 * @param inDate
	 * @param inClass
	 */
	private MimeTypeMock(long inId, String inLabel, String inExtension) {
		super(inId);
		this.label = inLabel;
		this.extension = inExtension;
	}

	public String getExtension() {
		return this.extension;
	}

	public String getLabel() {
		return this.label;
	}

}

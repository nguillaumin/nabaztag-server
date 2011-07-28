package net.violet.platform.datamodel.mock;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Hardware;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.MimeType.MIME_TYPES;

public class HardwareMock extends AbstractMockRecord<Hardware, HardwareMock> implements Hardware {

	public static final MockBuilder<Hardware> BUILDER = new MockBuilder<Hardware>() {

		@Override
		protected Hardware build(String[] inParamValues) {
			return new HardwareMock(Long.parseLong(inParamValues[0]), inParamValues[1], inParamValues[2]);
		}
	};

	private final String mModelName;
	private final String mLabel;
	private final String mType;
	protected Files picture;
	private final Set<MimeType.MIME_TYPES> supportedTypes = new HashSet<MimeType.MIME_TYPES>();

	public HardwareMock(long inId, String inLabel, String inType) {
		super(inId);

		this.mType = inType;
		this.mModelName = (inLabel + ((inType == null) ? net.violet.common.StringShop.EMPTY_STRING : inType)).toLowerCase();
		this.mLabel = inLabel;
		this.picture = null;
	}

	/**
	 * @return lowercase concatenation of the label and version of this model
	 */
	public final String getModelName() {
		return this.mModelName;
	}

	public String getType() {
		return this.mType;
	}

	public Files getPictureFile() {
		return this.picture;
	}

	/**
	 * Extends the equality comparison to the HARDWARE enum object
	 * 
	 * @param inHARDWARE
	 * @return
	 */
	public boolean equals(HARDWARE inHARDWARE) {
		return (inHARDWARE != null) && (this.getId() == inHARDWARE.getId());
	}

	public String getLabel() {
		return this.mLabel;
	}

	public List<MIME_TYPES> getSupportedMimeTypes() {
		return new LinkedList<MIME_TYPES>(this.supportedTypes);
	}

	public void addMimeType(MimeType.MIME_TYPES inMime_types) {
		this.supportedTypes.add(inMime_types);
	}

}

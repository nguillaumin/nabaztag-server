package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Anim;

public class AnimMock extends AbstractMockRecord<Anim, AnimMock> implements Anim {

	public static final MockBuilder<Anim> BUILDER = new MockBuilder<Anim>() {

		@Override
		protected Anim build(String[] inParamValues) {
			return new AnimMock(Long.parseLong(inParamValues[0]), inParamValues[1], inParamValues[3]);
		}
	};

	private final long mId;
	private final String mName;
	private final String mSignature;
	private final String mUrl;

	protected AnimMock(long inId) {
		this(inId, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING);

	}

	public AnimMock(long inId, String theName) {
		this(inId, theName, net.violet.common.StringShop.EMPTY_STRING);
	}

	public AnimMock(long inId, String name, String url) {
		super(inId);
		this.mId = inId;
		this.mName = name;
		this.mSignature = net.violet.common.StringShop.EMPTY_STRING;
		this.mUrl = url;

	}

	public long getAnim_id() {
		return this.mId;
	}

	public String getAnim_name() {
		return this.mName;
	}

	public String getAnim_signature() {
		return this.mSignature;
	}

	public String getAnim_url() {
		return this.mUrl;
	}

}

package net.violet.platform.datamodel.mock;

import java.util.Map;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Service;
import net.violet.platform.datamodel.factories.Factories;

public class ServiceMock extends AbstractMockRecord<Service, ServiceMock> implements Service {

	public static final MockBuilder<Service> BUILDER = new MockBuilder<Service>() {

		@Override
		protected Service build(String[] inParamValues) {
			return new ServiceMock(Long.parseLong(inParamValues[0]), inParamValues[1], inParamValues[2], inParamValues[3], Integer.parseInt(inParamValues[4]), (inParamValues[5] != null) ? Long.parseLong(inParamValues[5]) : null, (inParamValues[6] != null) ? Long.parseLong(inParamValues[6]) : null);
		}
	};

	private final String mLabel;
	private final String mUrl;
	private final String mImg;
	private final int mTTL;
	private final Long mIntro;
	private final Long mOutro;

	public ServiceMock(long inId, String label, String url, String img, int inTTL, Long inIn, Long inOut) {
		super(inId);
		this.mLabel = label;
		this.mUrl = url;
		this.mImg = img;
		this.mTTL = inTTL;
		this.mIntro = inIn;
		this.mOutro = inOut;
	}

	public String getImg() {
		return this.mImg;
	}

	public String getLabel() {
		return this.mLabel;
	}

	public String getLink() {
		return this.mUrl;
	}

	public Map<String, String> getMap() {
		return null;
	}

	public int getTtl() {
		return this.mTTL;
	}

	public Files getIntro() {
		return Factories.FILES.find(this.mIntro);
	}

	public Files getOutro() {
		return Factories.FILES.find(this.mOutro);
	}

}

package net.violet.platform.web.apps;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.violet.common.StringShop;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.VObjectData;

public class OnlineObject extends HashMap<String, Object> {

	private static final String SECRET_KEY;

	static {
		final Application webUI = Factories.APPLICATION.findByName("WebUI");
		SECRET_KEY = Factories.APPLICATION_CREDENTIALS.findByApplication(webUI).getPublicKey();
	}

	private final String apiId;
	private final VObjectData ztamp;
	private final VObjectData reader;
	private final Date seenAt;

	public OnlineObject(VObjectData ztamp, VObjectData reader) {
		this.ztamp = ztamp;
		this.reader = reader;
		this.seenAt = new Date();
		this.apiId = ztamp.getApiId(OnlineObject.SECRET_KEY);

		// information used in javascript
		final Map<String, Object> location = new HashMap<String, Object>();
		location.put("lat", ztamp.getProfile().getLatitudeGPS());
		location.put("lng", ztamp.getProfile().getLongitudeGPS());
		put("location", location);

		put("name", getName());
		put("serial", getSerial());
		put("picture", getPictureUrl());
		put("profile", getProfileUrl());
		put("address", getLocation());

		// filtering information
		put("gender", ztamp.getOwner().getAnnu().getGender().getCodeAnnu());
		put("hardware", ztamp.getObjectType().getTypeName());
		put("last_time", this.seenAt.getTime());
		put("age", ztamp.getOwner().getAnnu().getAge());
	}

	public Date getSeenAt() {
		return this.seenAt;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof OnlineObject) && ((OnlineObject) obj).ztamp.equals(this.ztamp);
	}

	@Override
	public int hashCode() {
		return this.ztamp.hashCode();
	}

	/** methods below are used by the jsp to access useful information */

	public String getName() {
		final String name = this.ztamp.getProfile().getLabel();
		return name != null ? name : StringShop.EMPTY_STRING;
	}

	public String getProfileUrl() {
		return "http://my.violet.net/ztuffs/" + this.apiId;
	}

	public String getPictureUrl() {
		final String picture = this.ztamp.getProfile().getPictureFiles().getUrl();
		return picture != null ? picture : StringShop.EMPTY_STRING;
	}

	public String getLocation() {
		return this.ztamp.getOwner().getAnnu_city() + ", " + this.ztamp.getOwner().getPays_nom();
	}

	public VObjectData getReader() {
		return this.reader;
	}

	public String getSerial() {
		return this.ztamp.getSerial();
	}

}

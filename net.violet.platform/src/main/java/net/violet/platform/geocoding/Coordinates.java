package net.violet.platform.geocoding;

import java.math.BigDecimal;

public class Coordinates {

	private final BigDecimal latitude;
	private final BigDecimal longitude;

	public Coordinates(String latitude, String longitude) {
		this(new BigDecimal(latitude), new BigDecimal(longitude));
	}

	public Coordinates(BigDecimal latitude, BigDecimal longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return this.latitude;
	}

	public BigDecimal getLongitude() {
		return this.longitude;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Coordinates)) {
			return false;
		}

		final Coordinates coord = (Coordinates) obj;
		return coord.latitude.equals(this.latitude) && coord.longitude.equals(this.longitude);
	}

	@Override
	public int hashCode() {
		return this.latitude.hashCode() + this.longitude.hashCode();
	}
}

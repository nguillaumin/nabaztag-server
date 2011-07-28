package net.violet.platform.geocoding;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class GeoCoderTest {

	private final static Coordinates PARIS_COORDINATES = new Coordinates(new BigDecimal("48.8566667"), new BigDecimal("2.3509871"));

	@Test
	public void geoCodeTest() throws GeoCodingException {
		final String address = "Paris, FR";

		final Coordinates coord = GeoCoder.geoCodeAddress(address);

		Assert.assertNotNull(coord);
		Assert.assertEquals(GeoCoderTest.PARIS_COORDINATES.getLatitude(), coord.getLatitude());
		Assert.assertEquals(GeoCoderTest.PARIS_COORDINATES.getLongitude(), coord.getLongitude());
	}

	@Test
	public void geoCodeUnexistingPlaceTest() throws GeoCodingException {
		final String address = "Pirates Island, Neverland";

		final Coordinates coord = GeoCoder.geoCodeAddress(address);

		Assert.assertNull(coord);
	}

}

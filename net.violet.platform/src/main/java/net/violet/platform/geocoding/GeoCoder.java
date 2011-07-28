package net.violet.platform.geocoding;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.violet.common.utils.io.StreamTools;
import net.violet.platform.util.cache.Cache;
import net.violet.platform.util.cache.GenerationException;
import net.violet.platform.util.cache.ValueGenerator;

public class GeoCoder {

	private static final String GEOCODE_SERVER_URL = "http://maps.google.com/maps/geo?sensor=false&key=ABQIAAAAB7yqZ41gqENqqc8VwfD3ThQ2tmoUQsuJWyAJRdaRYWIWzZyo4hSHa0vceN0oT7pI9Vem8v_nlv8lbw&output=csv&oe=utf8&q=";

	// line example : 200,6,42.730070,-73.690570
	private final static Pattern MATCH_LINE = Pattern.compile("^(\\d+),(\\d),([^,]+),([^,]+)$");

	private final static Cache<String, Coordinates> COORDINATES_CACHE = new Cache<String, Coordinates>(new ValueGenerator<String, Coordinates>() {

		public Coordinates generateValue(String key) throws GenerationException {
			final URL theUrl;
			try {
				theUrl = new URL(GeoCoder.GEOCODE_SERVER_URL + URLEncoder.encode(key, "UTF-8"));
			} catch (final MalformedURLException e) {
				throw new GenerationException(e);
			} catch (final UnsupportedEncodingException e) {
				throw new GenerationException(e);
			}

			try {
				final String result = StreamTools.readString(theUrl);
				return GeoCoder.getBestMatch(result);
			} catch (final IOException e) {
				throw new GenerationException(e);
			}
		}

	});

	/**
	 * See documentation below.
	 * @param country
	 * @param city
	 * @return
	 * @throws GeoCodingException
	 */
	public static Coordinates geoCodeAddress(String country, String city) throws GeoCodingException {
		final String address = city != null ? city + " , " + country : country;
		return GeoCoder.geoCodeAddress(address);
	}

	/**
	 * Geocode the given address and returns the best matching coordinates (null if there is not any match).
	 * @param address
	 * @return
	 * @throws GeoCodingException
	 */
	public static Coordinates geoCodeAddress(String address) throws GeoCodingException {
		try {
			return GeoCoder.COORDINATES_CACHE.get(address);
		} catch (final GenerationException e) {
			throw new GeoCodingException(e);
		}
	}

	private static Coordinates getBestMatch(String content) {
		Coordinates bestMatch = null;
		int bestAccuracy = 0;

		for (final String aMatch : content.split("\n")) {
			final Matcher matcher = GeoCoder.MATCH_LINE.matcher(aMatch);
			if (matcher.matches() && matcher.group(1).equals("200")) {
				final int accuracy = Integer.parseInt(matcher.group(2));
				if (accuracy > bestAccuracy) {
					bestMatch = new Coordinates(matcher.group(3), matcher.group(4));
					bestAccuracy = accuracy;
				}
			}
		}

		return bestMatch;
	}

}

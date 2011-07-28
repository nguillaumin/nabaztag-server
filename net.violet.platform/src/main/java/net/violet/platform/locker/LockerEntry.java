package net.violet.platform.locker;

import java.util.Map;

import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.PojoMap;

import org.json.JSON;
import org.json.simple.serializer.JSONSerializer;

/**
 *
 * @author christophe - Violet
 */
public class LockerEntry implements JSON {

	// KEYS TO STORE THE 
	public final static String SECRET_KEY = "secret";
	public final static String TS_KEY = "ts";
	public final static String EXPIRATION_KEY = "expiration";

	private final PojoMap mEntryMap;

	/**
	 * Full constructor with
	 * @param inSecret
	 * @param inOwnersId
	 * @param inExpirationDelay in seconds
	 */
	public LockerEntry(String inSecret, long inExpirationDelay) {

		this.mEntryMap = new PojoMap(8);

		this.mEntryMap.put(LockerEntry.SECRET_KEY, inSecret);
		this.mEntryMap.put(LockerEntry.TS_KEY, System.currentTimeMillis());

		if (inExpirationDelay != -1) {
			this.mEntryMap.put(LockerEntry.EXPIRATION_KEY, inExpirationDelay);
		}
	}

	/**
	 * No expiration delay and no owner's id
	 * @param inSecret
	 */
	public LockerEntry(String inSecret) {
		this(inSecret, -1);
	}

	/**
	 * Zombie revitalizer
	 * @param inEntryMap the entry map as it has been serialized
	 * @throws InvalidParameterException 
	 */
	LockerEntry(Map<String, Object> inEntryMap) throws InvalidParameterException {

		final PojoMap entryMap = new PojoMap(inEntryMap);

		// check that our revitalized map is well formed
		entryMap.getString(LockerEntry.SECRET_KEY, true);
		entryMap.getLong(LockerEntry.TS_KEY, true);
		entryMap.getLong(LockerEntry.EXPIRATION_KEY, -1);

		this.mEntryMap = entryMap;
	}

//  GETTERS -----------------------------------------------------------------80

	public String getSecret() {
		return (String) this.mEntryMap.get(LockerEntry.SECRET_KEY);
	}

	public long getTimestamp() {
		final long ts = System.currentTimeMillis();
		try {
			return this.mEntryMap.getLong(LockerEntry.TS_KEY, ts);
		} catch (final InvalidParameterException e) {
			// cannot happen
			this.mEntryMap.put(LockerEntry.TS_KEY, ts);
			return ts;
		}
	}

	/**
	 * @return Expiration delay in seconds or -1 if no expiration
	 */
	public long getExpirationDelay() {
		try {
			return this.mEntryMap.getLong(LockerEntry.EXPIRATION_KEY, -1);

		} catch (final InvalidParameterException e) {
			return 0; // expires immediately
		}
	}

	/**
	 * @return Expiration delay in milliseconds or -1 if no expiration
	 */
	public long getExpirationDelayMs() {
		final long expirationDelay = getExpirationDelay();
		return (expirationDelay == -1) ? expirationDelay : expirationDelay * 1000;
	}

	public boolean hasExpired() {
		final long expirationDelayMs = getExpirationDelayMs();
		return ((expirationDelayMs != -1) && ((System.currentTimeMillis() - getTimestamp()) > expirationDelayMs));
	}

//  SERIALIZATION -----------------------------------------------------------80

	public String toJSONString() {
		return (new JSONSerializer()).serialize(this.mEntryMap);
	}

	@Override
	public String toString() {
		return toJSONString();
	}

}

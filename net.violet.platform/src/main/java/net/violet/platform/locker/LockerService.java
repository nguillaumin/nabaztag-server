package net.violet.platform.locker;

import java.util.UUID;

import net.violet.platform.util.Constantes;

import org.apache.log4j.Logger;

/**
 * The locker service : expirable content retrievable with a secret key
 * @author christophe - Violet
 */
public class LockerService {

	// Logger.
	private static final Logger LOGGER = Logger.getLogger(LockerService.class);

	public static final long DEFAULT_EXPIRATION_TIME = Constantes.ONE_DAY_IN_S;

	// the real guy behind
	private static LockerPersistenceManager manager;

	static {
		/*
		 * HERE WE COULD INITIALIZE ANOTHER PERSISTENCE MANAGER
		 */
		LockerService.manager = LockerFilePersistenceManager.getInstance();
	}

//  DEPOSIT SERVICE ---------------------------------------------------------80
	/**
	 * An anonymous secret is deposited into the locker 
	 * the possession of the key will be the only thing asked for retrieval
	 * @param inSecret
	 * @return
	 */
	public static String store(String inSecret) {

		return LockerService.store(inSecret, null, LockerService.DEFAULT_EXPIRATION_TIME);
	}

	/**
	 * A secret is deposited into the locker with the owner's id
	 * that will be asked for its retrieval with the key
	 * @param inSecret
	 * @param inOwnersId
	 * @return the locker's key for retrieval
	 */
	public static String store(String inSecret, String inOwnersId) {

		return LockerService.store(inSecret, inOwnersId, LockerService.DEFAULT_EXPIRATION_TIME);
	}

	/**
	 * A secret is deposited into the locker with the owner's id
	 * that will be necessary for its retrieval with the key
	 * @param inSecret
	 * @param inOwnersId
	 * @param inExpirationDelay
	 * @return the locker's key for retrieval
	 */
	public static String store(String inSecret, String inOwnersId, long inExpirationDelay) {

		final String lockersKey = LockerService.getNewKey();
		return LockerService.store(lockersKey, inSecret, inOwnersId, inExpirationDelay);
	}

	/**
	 * A secret is deposited into the locker with the owner's id
	 * that will be asked for its retrieval with the key
	 * @param inSecret
	 * @param inOwnersId
	 * @param inExpirationDelay
	 * @return the locker's key for retrieval
	 */
	public static String store(String inLockersKey, String inSecret, String inOwnersId, long inExpirationDelay) {

		if (inLockersKey == null) {
			return null;
		}

		final String finalKey = (inOwnersId == null) ? inLockersKey : inLockersKey + "@" + inOwnersId;
		final LockerEntry entry = new LockerEntry(inSecret, inExpirationDelay);
		LockerService.LOGGER.info("Storing secret " + inSecret + " under key " + inLockersKey);
		LockerService.manager.update(finalKey, entry);
		return inLockersKey;
	}

//  RETRIEVAL SERVICE -------------------------------------------------------80

	/**
	 * Anonymous retrieval : we just have the locker's key
	 * @param inLockerKey
	 * @return null if the secret was not found or had expired
	 */
	public static String retrieve(String inLockerKey) {

		return LockerService.retrieve(inLockerKey, null);
	}

	/**
	 * Owner's retrieval
	 * @param inLockerKey
	 * @param inOwnersId
	 * @return null if the secret was not found or had expired
	 */
	public static String retrieve(String inLockerKey, String inOwnersId) {

		if (inLockerKey == null) {
			return null;
		}

		final String finalKey = (inOwnersId == null) ? inLockerKey : inLockerKey + "@" + inOwnersId;

		// read once
		final LockerEntry secret = LockerService.manager.remove(finalKey);

		if (secret == null) {
			LockerService.LOGGER.info("No secret found under the key " + inLockerKey);
			return null;
		}

		if (secret.hasExpired()) {
			return null;
		}

		return secret.getSecret();

	}

	/**
	 * @return a new (unique) key for a locker deposit 
	 */
	static String getNewKey() {
		return UUID.randomUUID().toString();
	}

}

package net.violet.platform.util;

import java.util.Date;
import java.util.Random;

public class SecretTimestamp {

	// the length of our time stamp in hexa form is 11 characters right now
	// and it should not change before long (Mon Jun 23 08:20:44 CEST 2527 !!)
	private static int TS_LENGTH = 11; // Long.toString((new Date().getTime()), 16).length();

	/**
	 * Get a new secret time stamp of the current time in shuffled decodable hexa form
	 * @return
	 */
	public static String create() {

		final long ms = System.currentTimeMillis(); // number of milliseconds since January 1, 1970 UTC.
		// convert the timestamp to its hexadecimal representation
		final char[] timestamp = Long.toString(ms, 16).toCharArray();

		// determine a new shuffle table
		final int[] shuffle = SecretTimestamp.getShuffleArray(SecretTimestamp.TS_LENGTH);

		// create a new representation of the timestamp
		// by applying the shuffling to our hexadecimal letters
		// and prefixing the shuffled hexa letter by its original position (in hexa too)
		final char[] encoded = new char[SecretTimestamp.TS_LENGTH * 2];
		for (int i = 0; i < SecretTimestamp.TS_LENGTH; i++) {
			final int newPos = shuffle[i];

			encoded[newPos * 2] = Character.forDigit(i, 16); // indicate the original position
			encoded[newPos * 2 + 1] = timestamp[i]; // original timestamp letter
		}

		return new String(encoded);
	}

	/**
	 * Read a cyphered time stamp and decode it 
	 * @param inCypherTs
	 * @return the long value (number of ms since ..)
	 */
	public static long decode(String inCypherTs) {

		long ts;
		try {
			final char[] charSeq = inCypherTs.toCharArray();
			final char[] unshuffled = new char[SecretTimestamp.TS_LENGTH];

			for (int i = 0; i < SecretTimestamp.TS_LENGTH * 2; i += 2) {
				final int pos = Character.digit(charSeq[i], 16);
				unshuffled[pos] = charSeq[i + 1];
			}

			ts = Long.parseLong(new String(unshuffled), 16);

		} catch (final Exception e) { // NummPointerException, NumberFormatException, IndexOutOfBoundsException
			// All these means that the input was forged
			throw new IllegalArgumentException("Secret timestamp is invalid : " + inCypherTs);
		}
		return ts;
	}

	/**
	 * @param inCypherTs
	 * @return the date value of the decoded timestamp
	 */
	public static Date decodeAsDate(String inCypherTs) {

		return new Date(SecretTimestamp.decode(inCypherTs));
	}

	public static boolean isValid(String inCypherTs, int inExpirationDelay) {

		final long timestamp = SecretTimestamp.decode(inCypherTs);
		return (System.currentTimeMillis() - timestamp) < inExpirationDelay;
	}

	/**
	 * Creates a new array of shuffled number between 0 and dimension
	 * @param inDimension
	 * @return
	 */
	private static int[] getShuffleArray(int inDimension) {
		final Random rgen = new Random(); // Random number generator
		final int[] shuffle = new int[inDimension];

		//--- Initialize the array with the sequential number to shuffle
		for (int i = 0; i < shuffle.length; i++) {
			shuffle[i] = i;
		}

		//--- Shuffle by exchanging each element randomly
		for (int i = 0; i < shuffle.length; i++) {
			final int randomPosition = rgen.nextInt(shuffle.length);
			final int temp = shuffle[i];
			shuffle[i] = shuffle[randomPosition];
			shuffle[randomPosition] = temp;
		}

		return shuffle;
	}

	/**
	 * Just for tests
	 * @param args
	 */
	public static void main(String[] args) {
		// this is when we will need 12 hexadecimal chars for ou timestamp !
		final long tsLimit = Long.parseLong("100000000000", 16);
		System.out.println("Timestamp system will expirate on : " + new Date(tsLimit));

		for (int i = 0; i < 10; i++) {
			final String cts = SecretTimestamp.create();
			System.out.println("Timestamp : " + cts);
			System.out.println("is date : " + SecretTimestamp.decodeAsDate(cts));
		}
	}
}

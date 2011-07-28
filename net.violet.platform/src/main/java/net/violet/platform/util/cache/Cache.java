package net.violet.platform.util.cache;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * The basic class to instatiate a simple cache. The Key and Value params are used to define the classes of the 
 * objects used as Key/Value in the Cache.
 * 
 * This class is thread safe.
 * 
 * The valueGenerator is used as a strategy to create a value based on the given key, the ValueGenerator is called when
 * the get method is called and the cache does not have the key in memory.
 *
 * @param <Key>
 * @param <Value>
 */
public class Cache<Key, Value> {

	private final Map<Key, Value> cacheMap = new WeakHashMap<Key, Value>();
	private final ValueGenerator<Key, Value> generator;

	/**
	 * Creates a cache instance with the given value generation strategy.
	 * @param generator
	 */
	public Cache(ValueGenerator<Key, Value> generator) {
		this.generator = generator;
	}

	/**
	 * Clears the cache.
	 */
	public synchronized void clearCache() {
		this.cacheMap.clear();

	}

	/**
	 * Retrieves the value associated to the given key. If the key is not stored in the cache then the ValueGenerator is called to
	 * create the value which should be linked to the provided key, the value is then stored in the cache and returned.
	 * @param inKey
	 * @return
	 * @throws GenerationException if the value generation failed.
	 */
	public Value get(Key inKey) throws GenerationException {
		Value theValue = this.cacheMap.get(inKey);
		if (theValue == null) {
			synchronized (this) {
				theValue = this.cacheMap.get(inKey);
				if (theValue == null) {
					theValue = this.generator.generateValue(inKey);
					put(inKey, theValue);
				}
			}
		}

		return theValue;

	}

	/**
	 * Adds the given key/value to the cache.
	 * @param inKey
	 * @param inValue
	 * @return
	 */
	public synchronized Value put(Key inKey, Value inValue) {
		return this.cacheMap.put(inKey, inValue);
	}

	/**
	 * Removes the given key and its associated value from the cache.
	 * @param inKey
	 * @return
	 */
	public synchronized Value remove(Key inKey) {
		return this.cacheMap.get(inKey);
	}

	/**
	 * Returns the generator.
	 * @return
	 */
	protected ValueGenerator<Key, Value> getGenerator() {
		return this.generator;
	}
}

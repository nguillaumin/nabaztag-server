package net.violet.platform.util.cache;

/**
 * A ValueGenerator is used by a Cache object as a value generation strategy.
 * The only method is in charge of creating the value that should be stored in the cache
 * according to the given key.
 *
 * @param <Key>
 * @param <Value>
 */
public interface ValueGenerator<Key, Value> {

	Value generateValue(Key key) throws GenerationException;

}

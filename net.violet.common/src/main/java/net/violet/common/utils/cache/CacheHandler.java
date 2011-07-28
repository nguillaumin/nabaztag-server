package net.violet.common.utils.cache;

public interface CacheHandler<Key, CacheContent> {

	CacheContent get(Key inKey);

	void add(Key inKey, CacheContent inContent);

	void remove(Key inKey);

}

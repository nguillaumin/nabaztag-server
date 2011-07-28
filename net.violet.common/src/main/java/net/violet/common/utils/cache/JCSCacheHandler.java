package net.violet.common.utils.cache;

import java.io.Serializable;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.apache.log4j.Logger;

class JCSCacheHandler<Key extends Serializable, CacheContent> implements CacheHandler<Key, CacheContent> {

	private static final Logger LOGGER = Logger.getLogger(JCSCacheHandler.class);

	private final JCS cache;

	JCSCacheHandler(String inRegion) throws Exception {
		cache = JCS.getInstance(inRegion);
	}

	public CacheContent get(Serializable inKey) {
		return (CacheContent) cache.get(inKey);
	}

	public void add(Key inKey, CacheContent inContent) {
		try {
			cache.put(inKey, inContent);
		} catch (final CacheException e) {
			LOGGER.fatal(e, e);
		}
	}

	public void remove(Key inKey) {
		try {
			cache.remove(inKey);
		} catch (final CacheException e) {
			LOGGER.fatal(e, e);
		}
	}
}

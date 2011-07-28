package net.violet.common.utils.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class CacheHandlerFactory {

	private static final Logger LOGGER = Logger.getLogger(CacheHandlerFactory.class);

	private static final Map<String, CacheHandler> REGION_CACHES = new HashMap<String, CacheHandler>();

	public static <Key extends Serializable, CacheContent> CacheHandler<Key, CacheContent> getInstance(String inRegion) {
		CacheHandler<Key, CacheContent> theHandler = REGION_CACHES.get(inRegion);
		if (theHandler == null) {
			synchronized (REGION_CACHES) {
				theHandler = REGION_CACHES.get(inRegion);
				if (theHandler == null) {
					try {
						REGION_CACHES.put(inRegion, theHandler = new JCSCacheHandler<Key, CacheContent>(inRegion));
					} catch (Exception e) {
						LOGGER.fatal(e, e);
					}
				}
			}
		}

		return theHandler;
	}

}

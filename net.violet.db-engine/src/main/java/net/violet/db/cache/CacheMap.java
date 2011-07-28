package net.violet.db.cache;

import java.lang.ref.ReferenceQueue;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @param <K> classe pour les clés
 * @param <T> classe pour les objets dans le cache (collections/abstract record)
 * @param <R> classe pour la référence sur les objets
 */
public class CacheMap<K, T> {

	private static final Logger LOGGER = Logger.getLogger(CacheMap.class);

	/**
	 * Map avec les références.
	 */
	private final Map<K, CacheReference<K, T>> mMap;

	private final Map<K, T> mLinkedMap;

	private final ReferenceQueue<T> mReferenceQueue;

	private final Thread mCacheCleaner;

	/**
	 * Constructeur par défaut.
	 */
	public CacheMap(final int inSize) {
		this.mMap = new HashMap<K, CacheReference<K, T>>();

		this.mLinkedMap = new LinkedHashMap<K, T>(inSize) {

			@Override
			protected boolean removeEldestEntry(Map.Entry<K, T> entry) {
				return (size() > inSize);
			}
		};

		this.mReferenceQueue = new ReferenceQueue<T>();

		this.mCacheCleaner = new Thread() {

			@Override
			public void run() {
				try {
					while (true) {
						final CacheReference<K, T> theCacheReference = (CacheReference<K, T>) CacheMap.this.mReferenceQueue.remove();
						synchronized (CacheMap.this) {
							CacheMap.this.mMap.remove(theCacheReference.getKey());
						}
					}
				} catch (final InterruptedException e) {
					CacheMap.LOGGER.fatal(e, e);
				}
			}

		};

		this.mCacheCleaner.start();
	}

	public synchronized T get(K theRef) {
		T theRecord = this.mLinkedMap.get(theRef);

		if (theRecord != null) {
			// On supprime et on remet dans la map, pour être au-dessus de la
			// pile.
			this.mLinkedMap.remove(theRef);
			this.mLinkedMap.put(theRef, theRecord);

		} else {
			// S'il n'est pas dans la liste LinkedHashMap, il est forcément dans la mMap.
			final CacheReference<K, T> cacheReference = this.mMap.get(theRef);
			if (cacheReference != null) {
				theRecord = cacheReference.get();
				if (theRecord != null) {
					// On le remet dans la LinkedHashMap
					this.mLinkedMap.put(theRef, theRecord);
				}
			}
		}
		return theRecord;
	}

	public synchronized void remove(K theRef) {
		this.mLinkedMap.remove(theRef);
		this.mMap.remove(theRef);
	}

	public synchronized void put(K theRef, T theRecord) {
		this.mMap.put(theRef, new CacheReference<K, T>(theRef, theRecord, this.mReferenceQueue));
		this.mLinkedMap.put(theRef, theRecord);
	}

	public synchronized CacheReference<K, T> getCacheReference(K theRef) {
		return this.mMap.get(theRef);
	}

	public synchronized void putAll(List<K> theRefList, T theRecord) {
		this.mLinkedMap.put(theRefList.get(0), theRecord); // Uniquement avec la PK
		for (final K theRef : theRefList) {
			this.mMap.put(theRef, new CacheReference<K, T>(theRef, theRecord, this.mReferenceQueue));
		}
	}

	/**
	 * Vide le cache.
	 */
	void clear() {
		this.mLinkedMap.clear();
	}

	/**
	 * Vide le cache (utilisé pour les tests).
	 */
	void clearForTests() {
		this.mLinkedMap.clear();
		this.mMap.clear();
	}

	public Integer getMapSize() {
		return this.mMap.size();
	}

	public Integer getListSize() {
		return this.mLinkedMap.size();
	}

}

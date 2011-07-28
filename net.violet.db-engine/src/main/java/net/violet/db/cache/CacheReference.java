package net.violet.db.cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Classe pour une référence Weak sur un objet qu'on peut accéder via des clés.
 * Utilisé pour les AbstractRecord (clés SQL) et les DBCollection.
 * 
 * @param <K> classe des clés qui permettent d'accéder à cet objet.
 * @param <T> classe de l'objet auquel il est fait référence.
 */
public class CacheReference<K, T> extends WeakReference<T> {

	private final K mKey;

	/**
	 * Construit un nouveau {@link CacheReference}
	 */
	public CacheReference(K inKey, T referent, ReferenceQueue<? super T> q) {
		super(referent, q);
		this.mKey = inKey;
	}

	/**
	 * Retourne la clé.
	 * 
	 * @return la clé.
	 */
	public K getKey() {
		return this.mKey;
	}
}

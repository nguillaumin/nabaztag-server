package net.violet.db.cache;

/**
 * Interface pour un noeud du cache.
 */
public interface CacheNodeListener {

	void receiveMessage(CacheMessage inMessage);

	void addedMember(String inName);

	void disappearedMember(String inName);
}

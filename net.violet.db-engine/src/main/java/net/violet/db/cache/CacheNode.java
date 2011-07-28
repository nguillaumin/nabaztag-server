package net.violet.db.cache;

/**
 * Interface pour un noeud du cache.
 */
public interface CacheNode {

	/**
	 * Envoie un message vers les autres noeuds.
	 * 
	 * @param inMessage message à envoyer.
	 */
	void broadcastMessage(CacheMessage inMessage);

	/**
	 * Change le client du noeud (qui reçoit les messages).
	 * 
	 * @param inListener nouveau client du noeud.
	 */
	void setListener(CacheNodeListener inListener);

	/**
	 * Retourne la liste des membres du groupes.
	 * 
	 * @return la liste des adresses.
	 */
	String[] getMembers();

	/**
	 * Ferme le noeud.
	 */
	void close();
}

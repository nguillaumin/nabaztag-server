package net.violet.db.cache;

/**
 * Classe de base pour un noeud dans le cache distribué.
 */
abstract class AbstractCacheNode implements CacheNode {

	private CacheNodeListener mListener;

	public void setListener(CacheNodeListener inListener) {
		this.mListener = inListener;
	}

	protected void receiveMessage(CacheMessage inMessage) {
		if (this.mListener != null) {
			this.mListener.receiveMessage(inMessage);
		}
	}

	protected void addedMember(String inName) {
		if (this.mListener != null) {
			this.mListener.addedMember(inName);
		}
	}

	protected void disappearedMember(String inName) {
		if (this.mListener != null) {
			this.mListener.disappearedMember(inName);
		}
	}
}

package net.violet.db.cache;

/**
 * Message du cache envoyé à intervalles réguliers pour vérifier que tous les
 * noeuds sont bien connectés.
 */
public class PulseMessage implements CacheMessage {

	private final long mTime;

	public static PulseMessage getDefault() {
		return new PulseMessage(System.currentTimeMillis());
	}

	public PulseMessage(long inTime) {
		this.mTime = inTime;
	}

	public long getTime() {
		return this.mTime;
	}
}

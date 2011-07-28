package net.violet.platform.applets.tools;

import java.util.Date;

/**
 * @author christophe - Violet
 */
public class FixedDelayExpirable implements Expirable {

	private final static long DEFAULT_EXPIRATION_DELAY = 1000 * 60 * 10; // 10 minutes

	private final Date mLastRefresh;

	FixedDelayExpirable(Date inFreshDate) {
		this.mLastRefresh = inFreshDate;
	}

	/**
	 * La durée (en millisecondes) avant d'avoir à vérifier que l'objet n'a pas
	 * expiré
	 * 
	 * @return
	 */
	public long getExpirationPeriod() {
		return FixedDelayExpirable.DEFAULT_EXPIRATION_DELAY;
	}

	/**
	 * This implementation doesn't care about the passed repository date
	 * but just check if the expiratin delay has passed
	 * 
	 * @see net.violet.platform.applets.api.ScriptableApplet#hasBeenUpdated()
	 */
	public synchronized boolean needsRefresh(Date inRepositoryDate) {
		return ((new Date()).getTime() - this.mLastRefresh.getTime()) > getExpirationPeriod();
	}

}
